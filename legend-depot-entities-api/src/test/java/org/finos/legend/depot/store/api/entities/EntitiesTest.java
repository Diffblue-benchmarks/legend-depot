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

package org.finos.legend.depot.store.api.entities;

import org.finos.legend.depot.domain.entity.DepotEntity;
import org.finos.legend.depot.domain.entity.DepotEntityOverview;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EntitiesTest
{
    // Concrete test implementation to test the default method
    private static class TestEntities implements Entities<StoredEntity>
    {
        private List<DepotEntity> mockResult;

        public TestEntities(List<DepotEntity> mockResult)
        {
            this.mockResult = mockResult;
        }

        @Override
        public List<Entity> getAllEntities(String groupId, String artifactId, String versionId)
        {
            return new ArrayList<>();
        }

        @Override
        public Optional<Entity> getEntity(String groupId, String artifactId, String versionId, String path)
        {
            return Optional.empty();
        }

        @Override
        public List<Entity> getEntityFromDependencies(Set<ProjectVersion> dependencies, List<String> entityPaths)
        {
            return new ArrayList<>();
        }

        @Override
        public List<Entity> getEntitiesByPackage(String groupId, String artifactId, String versionId, String packageName, Set<String> classifierPaths, boolean includeSubPackages)
        {
            return new ArrayList<>();
        }

        @Override
        public List<DepotEntity> findReleasedClassifierEntities(String classifier)
        {
            return new ArrayList<>();
        }

        @Override
        public List<DepotEntity> findLatestClassifierEntities(String classifier)
        {
            return new ArrayList<>();
        }

        @Override
        public List<DepotEntity> findClassifierEntitiesByVersions(String classifier, List<ProjectVersion> projectVersions)
        {
            // Verify the parameters and return mock result
            return mockResult;
        }

        @Override
        public List<DepotEntityOverview> findReleasedClassifierSummaries(String classifier)
        {
            return new ArrayList<>();
        }

        @Override
        public List<DepotEntityOverview> findLatestClassifierSummaries(String classifier)
        {
            return new ArrayList<>();
        }

        @Override
        public List<DepotEntityOverview> findClassifierSummariesByVersions(String classifier, List<ProjectVersion> projectVersions)
        {
            return new ArrayList<>();
        }

        @Override
        public List<DepotEntity> findReleasedClassifierEntities(String classifier, String search, Integer limit)
        {
            return new ArrayList<>();
        }

        @Override
        public List<DepotEntity> findLatestClassifierEntities(String classifier, String search, Integer limit)
        {
            return new ArrayList<>();
        }

        @Override
        public List<DepotEntity> findClassifierEntitiesByVersions(String classifier, List<ProjectVersion> projectVersions, String search, Integer limit)
        {
            return new ArrayList<>();
        }

        @Override
        public List<StoredEntity> getStoredEntities(String groupId, String artifactId, String versionId)
        {
            return new ArrayList<>();
        }
    }

    @Test
    public void canFindEntitiesByClassifier()
    {

        List<DepotEntity> expectedEntities = new ArrayList<>();
        expectedEntities.add(new DepotEntity("test.group", "test-artifact", "1.0.0"));
        TestEntities entities = new TestEntities(expectedEntities);

        List<DepotEntity> result = entities.findEntitiesByClassifier("test.group", "test-artifact", "1.0.0", "meta::pure::metamodel::type::Class");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedEntities, result);

    }

    @Test
    public void canFindEntitiesByClassifierWithEmptyResult()
    {

        List<DepotEntity> emptyList = new ArrayList<>();
        TestEntities entities = new TestEntities(emptyList);

        List<DepotEntity> result = entities.findEntitiesByClassifier("test.group", "test-artifact", "2.0.0", "meta::pure::metamodel::type::Enumeration");

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());

    }

    @Test
    public void canFindEntitiesByClassifierWithMultipleResults()
    {

        List<DepotEntity> multipleEntities = new ArrayList<>();
        multipleEntities.add(new DepotEntity("test.group", "test-artifact", "3.0.0"));
        multipleEntities.add(new DepotEntity("test.group", "test-artifact", "3.0.0"));
        multipleEntities.add(new DepotEntity("test.group", "test-artifact", "3.0.0"));
        TestEntities entities = new TestEntities(multipleEntities);

        List<DepotEntity> result = entities.findEntitiesByClassifier("test.group", "test-artifact", "3.0.0", "meta::pure::metamodel::type::Class");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());

    }
}
