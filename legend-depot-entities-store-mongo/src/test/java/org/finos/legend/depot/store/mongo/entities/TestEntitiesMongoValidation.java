//  Copyright 2021 Goldman Sachs
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

package org.finos.legend.depot.store.mongo.entities;

import com.mongodb.client.model.IndexModel;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.depot.store.model.entities.StoredEntityData;
import org.finos.legend.depot.store.model.entities.StoredEntityStringData;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.entities.test.EntitiesMongoTestUtils;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEntitiesMongoValidation extends TestStoreMongo
{
    private static final URL ENTITIES_FILE = TestEntitiesMongoValidation.class.getClassLoader().getResource("data/versioned-entities.json");
    private EntitiesMongo entitiesMongo = new EntitiesMongo(mongoProvider);
    private final EntitiesMongoTestUtils entityUtils = new EntitiesMongoTestUtils(mongoProvider);

    @BeforeEach
    public void setUp()
    {
    }

    @Test
    public void canConstructEntitiesMongoWithDocumentClass()
    {
        EntitiesMongo<StoredEntityData> typedMongo = new EntitiesMongo<>(mongoProvider, StoredEntityData.class);
        Assertions.assertNotNull(typedMongo);
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = EntitiesMongo.buildIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertEquals(4, indexes.size());
    }

    @Test
    public void validateNewDataRejectsInvalidGroupId()
    {
        StoredEntityData entity = createStoredEntityData("", "test", "1.0.0",
                "examples::metadata::test::TestProfile", "meta::pure::metamodel::extension::Profile");
        Assertions.assertThrows(IllegalArgumentException.class, () -> entitiesMongo.createOrUpdate(Arrays.asList(entity)));
    }

    @Test
    public void validateNewDataRejectsInvalidArtifactId()
    {
        StoredEntityData entity = createStoredEntityData("examples.metadata", "123invalid", "1.0.0",
                "examples::metadata::test::TestProfile", "meta::pure::metamodel::extension::Profile");
        Assertions.assertThrows(IllegalArgumentException.class, () -> entitiesMongo.createOrUpdate(Arrays.asList(entity)));
    }

    @Test
    public void validateNewDataRejectsInvalidVersionId()
    {
        StoredEntityData entity = createStoredEntityData("examples.metadata", "test", "invalid-version",
                "examples::metadata::test::TestProfile", "meta::pure::metamodel::extension::Profile");
        Assertions.assertThrows(IllegalArgumentException.class, () -> entitiesMongo.createOrUpdate(Arrays.asList(entity)));
    }

    @Test
    public void validateNewDataRejectsInvalidEntityPath()
    {
        StoredEntityData entity = createStoredEntityData("examples.metadata", "test", "1.0.0",
                "invalid path", "meta::pure::metamodel::extension::Profile");
        Assertions.assertThrows(IllegalArgumentException.class, () -> entitiesMongo.createOrUpdate(Arrays.asList(entity)));
    }

    @Test
    public void validateNewDataRejectsMultipleErrors()
    {
        StoredEntityData entity = createStoredEntityData("", "123invalid", "invalid-version",
                "invalid path", "meta::pure::metamodel::extension::Profile");
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> entitiesMongo.createOrUpdate(Arrays.asList(entity)));
        Assertions.assertTrue(ex.getMessage().contains("invalid data"));
    }

    @Test
    public void canCreateOrUpdateEntitiesWithEntityDefinitions()
    {
        EntityDefinition entityDef = new EntityDefinition(
                "examples::metadata::test::TestProfile",
                "meta::pure::metamodel::extension::Profile",
                Collections.singletonMap("package", "examples::metadata::test"));

        List<StoredEntity> result = entitiesMongo.createOrUpdate("examples.metadata", "test", "1.0.0",
                Collections.singletonList(entityDef));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void canCreateOrUpdateMultipleEntitiesWithEntityDefinitions()
    {
        EntityDefinition entityDef1 = new EntityDefinition(
                "examples::metadata::test::TestProfile",
                "meta::pure::metamodel::extension::Profile",
                Collections.singletonMap("package", "examples::metadata::test"));

        EntityDefinition entityDef2 = new EntityDefinition(
                "examples::metadata::test::ClientBasic",
                "meta::pure::metamodel::type::Class",
                Collections.singletonMap("package", "examples::metadata::test"));

        List<StoredEntity> result = entitiesMongo.createOrUpdate("examples.metadata", "test", "1.0.0",
                Arrays.asList(entityDef1, entityDef2));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void findLatestClassifierEntitiesWithNullLimit()
    {
        entityUtils.loadEntities(TestEntitiesMongoValidation.class.getClassLoader().getResource("data/classifiers.json"));
        String classifier = "meta::pure::metamodel::extension::Profile";
        List entities = entitiesMongo.findLatestClassifierEntities(classifier, null, null);
        Assertions.assertNotNull(entities);
        Assertions.assertTrue(entities.size() > 0);
    }

    @Test
    public void resolvedToEntityDefinitionWithStoredEntityData()
    {
        entityUtils.loadEntities(ENTITIES_FILE);
        List<Entity> entities = entitiesMongo.getAllEntities("examples.metadata", "test", "2.2.0");
        Assertions.assertNotNull(entities);
        Assertions.assertFalse(entities.isEmpty());
    }

    @Test
    public void resolvedToEntityDefinitionWithStoredEntityStringData()
    {
        EntityDefinition entityDef = new EntityDefinition(
                "examples::metadata::test::TestProfile",
                "meta::pure::metamodel::extension::Profile",
                Collections.singletonMap("package", "examples::metadata::test"));

        entitiesMongo.createOrUpdate("examples.metadata", "test", "1.0.0",
                Collections.singletonList(entityDef));

        List<Entity> entities = entitiesMongo.getAllEntities("examples.metadata", "test", "1.0.0");
        Assertions.assertNotNull(entities);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("examples::metadata::test::TestProfile", entities.get(0).getPath());
    }

    private StoredEntityData createStoredEntityData(String groupId, String artifactId, String versionId,
                                                    String entityPath, String classifierPath)
    {
        Map<String, Object> content = new HashMap<>();
        content.put("package", entityPath.contains("::") ? entityPath.substring(0, entityPath.lastIndexOf("::")) : entityPath);
        EntityDefinition entityDef = new EntityDefinition(entityPath, classifierPath, content);
        Map<String, Object> entityAttributes = new HashMap<>();
        entityAttributes.put("path", entityPath);
        entityAttributes.put("classifierPath", classifierPath);
        entityAttributes.put("package", content.get("package"));
        return new StoredEntityData(groupId, artifactId, versionId, entityDef, entityAttributes);
    }
}
