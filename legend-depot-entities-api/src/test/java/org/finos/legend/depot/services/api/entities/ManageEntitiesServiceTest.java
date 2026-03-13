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

package org.finos.legend.depot.services.api.entities;

import org.finos.legend.depot.domain.entity.ProjectVersionEntities;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ManageEntitiesServiceTest
{
    @Test
    public void canGetStoredEntities()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";

        List<TestStoredEntity> expectedEntities = new ArrayList<>();
        expectedEntities.add(new TestStoredEntity(groupId, artifactId, versionId));

        TestManageEntitiesService testService = new TestManageEntitiesService();
        testService.storedEntitiesToReturn = new ArrayList<>(expectedEntities);

        List<TestStoredEntity> entities = testService.getStoredEntities(groupId, artifactId, versionId);

        Assertions.assertNotNull(entities);
        Assertions.assertEquals(expectedEntities, entities);
        Assertions.assertEquals(groupId, testService.getStoredEntitiesGroupId);
        Assertions.assertEquals(artifactId, testService.getStoredEntitiesArtifactId);
        Assertions.assertEquals(versionId, testService.getStoredEntitiesVersionId);
    }

    @Test
    public void canDeleteByGroupAndArtifact()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        long expectedDeleteCount = 5L;

        TestManageEntitiesService testService = new TestManageEntitiesService();
        testService.deleteCountToReturn = expectedDeleteCount;

        long deleteCount = testService.delete(groupId, artifactId);

        Assertions.assertEquals(expectedDeleteCount, deleteCount);
        Assertions.assertEquals(groupId, testService.deleteGroupId);
        Assertions.assertEquals(artifactId, testService.deleteArtifactId);
        Assertions.assertNull(testService.deleteVersionId);
    }

    @Test
    public void canDeleteByGroupArtifactAndVersion()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        long expectedDeleteCount = 3L;

        TestManageEntitiesService testService = new TestManageEntitiesService();
        testService.deleteCountToReturn = expectedDeleteCount;

        long deleteCount = testService.delete(groupId, artifactId, versionId);

        Assertions.assertEquals(expectedDeleteCount, deleteCount);
        Assertions.assertEquals(groupId, testService.deleteGroupId);
        Assertions.assertEquals(artifactId, testService.deleteArtifactId);
        Assertions.assertEquals(versionId, testService.deleteVersionId);
    }

    @Test
    public void canCreateOrUpdate()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        List<Entity> entities = Collections.emptyList();

        TestManageEntitiesService testService = new TestManageEntitiesService();

        testService.createOrUpdate(groupId, artifactId, versionId, entities);

        Assertions.assertEquals(groupId, testService.createOrUpdateGroupId);
        Assertions.assertEquals(artifactId, testService.createOrUpdateArtifactId);
        Assertions.assertEquals(versionId, testService.createOrUpdateVersionId);
        Assertions.assertEquals(entities, testService.createOrUpdateEntities);
    }

    private static class TestStoredEntity extends StoredEntity
    {
        public TestStoredEntity(String groupId, String artifactId, String versionId)
        {
            super(groupId, artifactId, versionId);
        }

        @Override
        public String getId()
        {
            return getGroupId() + ":" + getArtifactId() + ":" + getVersionId();
        }
    }

    private static class TestManageEntitiesService implements ManageEntitiesService<TestStoredEntity>
    {
        List<TestStoredEntity> storedEntitiesToReturn = new ArrayList<>();
        String getStoredEntitiesGroupId;
        String getStoredEntitiesArtifactId;
        String getStoredEntitiesVersionId;

        long deleteCountToReturn = 0L;
        String deleteGroupId;
        String deleteArtifactId;
        String deleteVersionId;

        String createOrUpdateGroupId;
        String createOrUpdateArtifactId;
        String createOrUpdateVersionId;
        List<Entity> createOrUpdateEntities;

        @Override
        public List<TestStoredEntity> getStoredEntities(String groupId, String artifactId, String versionId)
        {
            this.getStoredEntitiesGroupId = groupId;
            this.getStoredEntitiesArtifactId = artifactId;
            this.getStoredEntitiesVersionId = versionId;
            return storedEntitiesToReturn;
        }

        @Override
        public long delete(String groupId, String artifactId)
        {
            this.deleteGroupId = groupId;
            this.deleteArtifactId = artifactId;
            return deleteCountToReturn;
        }

        @Override
        public long delete(String groupId, String artifactId, String versionId)
        {
            this.deleteGroupId = groupId;
            this.deleteArtifactId = artifactId;
            this.deleteVersionId = versionId;
            return deleteCountToReturn;
        }

        @Override
        public void createOrUpdate(String groupId, String artifactId, String versionId, List<Entity> entities)
        {
            this.createOrUpdateGroupId = groupId;
            this.createOrUpdateArtifactId = artifactId;
            this.createOrUpdateVersionId = versionId;
            this.createOrUpdateEntities = entities;
        }

        @Override
        public List<Entity> getEntities(String groupId, String artifactId, String versionId)
        {
            return new ArrayList<>();
        }

        @Override
        public List<Entity> getEntitiesByClassifier(String groupId, String artifactId, String versionId, String classifier)
        {
            return new ArrayList<>();
        }

        @Override
        public Optional<Entity> getEntity(String groupId, String artifactId, String versionId, String entityPath)
        {
            return Optional.empty();
        }

        @Override
        public List<Entity> getEntityFromDependencies(String groupId, String artifactId, String versionId, List<String> entityPaths, boolean includeOrigin)
        {
            return new ArrayList<>();
        }

        @Override
        public List<Entity> getEntitiesByPackage(String groupId, String artifactId, String versionId, String packageName, Set<String> classifierPaths, boolean includeSubPackages)
        {
            return new ArrayList<>();
        }

        @Override
        public List<ProjectVersionEntities> getDependenciesEntities(List<ProjectVersion> projectDependencies, boolean transitive, boolean includeOrigin)
        {
            return new ArrayList<>();
        }

        @Override
        public List<ProjectVersionEntities> getDependenciesEntitiesByClassifier(List<ProjectVersion> projectDependencies, String classifier, boolean transitive, boolean includeOrigin)
        {
            return new ArrayList<>();
        }
    }
}
