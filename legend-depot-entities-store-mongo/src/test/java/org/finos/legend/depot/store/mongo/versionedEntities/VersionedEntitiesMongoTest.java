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

package org.finos.legend.depot.store.mongo.versionedEntities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.depot.store.model.versionedEntities.StoredVersionedEntity;
import org.finos.legend.depot.store.model.versionedEntities.StoredVersionedEntityData;
import org.finos.legend.depot.store.model.versionedEntities.StoredVersionedEntityReference;
import org.finos.legend.depot.store.model.versionedEntities.StoredVersionedEntityStringData;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VersionedEntitiesMongoTest extends TestStoreMongo
{
    private VersionedEntitiesMongo versionedEntitiesMongo;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp()
    {
        versionedEntitiesMongo = new VersionedEntitiesMongo(mongoProvider);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void canResolveStoredVersionedEntityData()
    {
        EntityDefinition entityDef = new EntityDefinition("test::path", "test::classifier", Collections.emptyMap());
        Map<String, Object> entityAttributes = new HashMap<>();
        entityAttributes.put("path", "test::path");

        StoredVersionedEntityData storedEntity = new StoredVersionedEntityData(
                "org.example",
                "test-artifact",
                "1.0.0",
                entityDef,
                entityAttributes
        );

        insertRaw(VersionedEntitiesMongo.COLLECTION, storedEntity);

        Optional<Entity> result = versionedEntitiesMongo.getEntity("org.example", "test-artifact", "1.0.0", "test::path");

        Assertions.assertTrue(result.isPresent());
        Entity entity = result.get();
        Assertions.assertNotNull(entity);
        Assertions.assertEquals("test::path", entity.getPath());
        Assertions.assertEquals("test::classifier", entity.getClassifierPath());
    }

    @Test
    public void canResolveStoredVersionedEntityStringDataWithValidJson() throws JsonProcessingException
    {
        Map<String, Object> content = new HashMap<>();
        content.put("name", "TestEntity");

        EntityDefinition entityDef = new EntityDefinition("test::string::path", "test::string::classifier", content);
        String jsonData = objectMapper.writeValueAsString(entityDef);

        Map<String, Object> entityAttributes = new HashMap<>();
        entityAttributes.put("path", "test::string::path");

        StoredVersionedEntityStringData storedEntity = new StoredVersionedEntityStringData(
                "org.example",
                "test-string-artifact",
                "2.0.0",
                jsonData,
                entityAttributes
        );

        insertRaw(VersionedEntitiesMongo.COLLECTION, storedEntity);

        Optional<Entity> result = versionedEntitiesMongo.getEntity("org.example", "test-string-artifact", "2.0.0", "test::string::path");

        Assertions.assertTrue(result.isPresent());
        Entity entity = result.get();
        Assertions.assertNotNull(entity);
        Assertions.assertEquals("test::string::path", entity.getPath());
        Assertions.assertEquals("test::string::classifier", entity.getClassifierPath());
    }

    @Test
    public void throwsExceptionForStoredVersionedEntityStringDataWithInvalidJson()
    {
        Map<String, Object> entityAttributes = new HashMap<>();
        entityAttributes.put("path", "test::invalid::path");

        StoredVersionedEntityStringData storedEntity = new StoredVersionedEntityStringData(
                "org.example",
                "test-invalid-artifact",
                "3.0.0",
                "{ invalid json content }",
                entityAttributes
        );

        insertRaw(VersionedEntitiesMongo.COLLECTION, storedEntity);

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            versionedEntitiesMongo.getEntity("org.example", "test-invalid-artifact", "3.0.0", "test::invalid::path");
        });

        Assertions.assertTrue(exception.getMessage().contains("Error:"));
        Assertions.assertTrue(exception.getMessage().contains("org.example"));
        Assertions.assertTrue(exception.getMessage().contains("test-invalid-artifact"));
        Assertions.assertTrue(exception.getMessage().contains("3.0.0"));
        Assertions.assertTrue(exception.getMessage().contains("test::invalid::path"));
    }

    @Test
    public void throwsExceptionForUnknownStoredEntityType()
    {
        Map<String, Object> entityAttributes = new HashMap<>();
        entityAttributes.put("path", "test::reference::path");

        StoredVersionedEntityReference referenceEntity = new StoredVersionedEntityReference(
                "org.example",
                "test-reference-artifact",
                "4.0.0",
                "some-reference",
                entityAttributes
        );

        insertRaw(VersionedEntitiesMongo.COLLECTION, referenceEntity);

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            versionedEntitiesMongo.getEntity("org.example", "test-reference-artifact", "4.0.0", "test::reference::path");
        });

        Assertions.assertEquals("Unknown stored entity type", exception.getMessage());
    }

    @Test
    public void canAccessVersionedEntitiesCollection()
    {
        EntityDefinition entityDef = new EntityDefinition("test::collection::path", "test::collection::classifier", Collections.emptyMap());
        Map<String, Object> entityAttributes = new HashMap<>();
        entityAttributes.put("path", "test::collection::path");

        StoredVersionedEntityData storedEntity = new StoredVersionedEntityData(
                "org.collection",
                "test-collection-artifact",
                "5.0.0",
                entityDef,
                entityAttributes
        );

        insertRaw(VersionedEntitiesMongo.COLLECTION, storedEntity);

        List<StoredVersionedEntity> results = versionedEntitiesMongo.getStoredEntities("org.collection", "test-collection-artifact", "5.0.0");

        Assertions.assertNotNull(results);
        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals("org.collection", results.get(0).getGroupId());
        Assertions.assertEquals("test-collection-artifact", results.get(0).getArtifactId());
        Assertions.assertEquals("5.0.0", results.get(0).getVersionId());
    }
}
