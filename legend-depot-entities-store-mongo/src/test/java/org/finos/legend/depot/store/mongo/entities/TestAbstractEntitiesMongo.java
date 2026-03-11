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

import org.eclipse.collections.api.tuple.Pair;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.entities.test.EntitiesMongoTestUtils;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestAbstractEntitiesMongo extends TestStoreMongo
{
    private static final URL ENTITIES_FILE = TestAbstractEntitiesMongo.class.getClassLoader().getResource("data/versioned-entities.json");
    private static final URL CLASSIFIERS_FILE = TestAbstractEntitiesMongo.class.getClassLoader().getResource("data/classifiers.json");
    private EntitiesMongo entitiesMongo = new EntitiesMongo(mongoProvider);
    private EntitiesMongoTestUtils entityUtils = new EntitiesMongoTestUtils(mongoProvider);

    @BeforeEach
    public void setUp()
    {
        entityUtils.loadEntities(ENTITIES_FILE);
    }

    @Test
    public void canGetStoredEntitiesByVersion()
    {
        List<StoredEntity> entities = entitiesMongo.getStoredEntities("examples.metadata", "test", "2.2.0");
        Assertions.assertNotNull(entities);
        Assertions.assertEquals(3, entities.size());
    }

    @Test
    public void canGetStoredEntitiesByVersionReturnsEmptyForUnknown()
    {
        List<StoredEntity> entities = entitiesMongo.getStoredEntities("examples.metadata", "test", "9.9.9");
        Assertions.assertNotNull(entities);
        Assertions.assertTrue(entities.isEmpty());
    }

    @Test
    public void canGetEntityFromDependencies()
    {
        Set<ProjectVersion> dependencies = new HashSet<>();
        dependencies.add(new ProjectVersion("examples.metadata", "test", "2.2.0"));
        List<String> entityPaths = Arrays.asList("examples::metadata::test::TestProfile");

        List<Entity> result = entitiesMongo.getEntityFromDependencies(dependencies, entityPaths);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("examples::metadata::test::TestProfile", result.get(0).getPath());
    }

    @Test
    public void getEntityFromDependenciesStopsWhenAllFound()
    {
        Set<ProjectVersion> dependencies = new HashSet<>();
        dependencies.add(new ProjectVersion("examples.metadata", "test", "2.2.0"));
        dependencies.add(new ProjectVersion("examples.metadata", "nonexistent", "1.0.0"));
        List<String> entityPaths = Arrays.asList("examples::metadata::test::TestProfile");

        List<Entity> result = entitiesMongo.getEntityFromDependencies(dependencies, entityPaths);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("examples::metadata::test::TestProfile", result.get(0).getPath());
    }

    @Test
    public void getEntityFromDependenciesReturnsEmptyForNoMatch()
    {
        Set<ProjectVersion> dependencies = new HashSet<>();
        dependencies.add(new ProjectVersion("examples.metadata", "test", "2.2.0"));
        List<String> entityPaths = Arrays.asList("nonexistent::path");

        List<Entity> result = entitiesMongo.getEntityFromDependencies(dependencies, entityPaths);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void canGetStoredEntitiesCoordinates()
    {
        List<Pair<String, String>> coordinates = entitiesMongo.getStoredEntitiesCoordinates();
        Assertions.assertNotNull(coordinates);
        Assertions.assertFalse(coordinates.isEmpty());
        boolean found = coordinates.stream().anyMatch(p ->
                "examples.metadata".equals(p.getOne()) && "test".equals(p.getTwo()));
        Assertions.assertTrue(found);
    }

    @Test
    public void canCreateOrUpdateWithEntityDefinitions()
    {
        Map<String, Object> content = new HashMap<>();
        content.put("_type", "profile");
        content.put("name", "NewProfile");
        content.put("package", "test::pkg");
        Entity entity = new EntityDefinition(
                "test::pkg::NewProfile",
                "meta::pure::metamodel::extension::Profile",
                content
        );

        List<StoredEntity> result = entitiesMongo.createOrUpdate("examples.metadata", "test", "3.0.0", Arrays.asList(entity));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        List<Entity> retrieved = entitiesMongo.getAllEntities("examples.metadata", "test", "3.0.0");
        Assertions.assertEquals(1, retrieved.size());
        Assertions.assertEquals("test::pkg::NewProfile", retrieved.get(0).getPath());
        Assertions.assertEquals("meta::pure::metamodel::extension::Profile", retrieved.get(0).getClassifierPath());
    }

    @Test
    public void canFindEntitiesByClassifierWithEmptyVersionsFallsBackToReleased()
    {
        entityUtils.loadEntities(CLASSIFIERS_FILE);
        String classifier = "meta::pure::metamodel::extension::Profile";
        List result = entitiesMongo.findClassifierEntitiesByVersions(classifier, Collections.emptyList(), null, null);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }
}
