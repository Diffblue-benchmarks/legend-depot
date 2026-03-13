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

import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.tuple.Tuples;
import org.finos.legend.depot.domain.entity.DepotEntity;
import org.finos.legend.depot.domain.entity.DepotEntityOverview;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.entities.StoredEntityData;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UpdateEntitiesTest
{
    private UpdateEntities<StoredEntityData> store;
    private List<Entity> testEntities;
    private StoredEntityData testStoredEntity;

    @BeforeEach
    public void setUp()
    {
        testEntities = new ArrayList<>();
        testStoredEntity = new StoredEntityData("com.example", "myartifact", "1.0.0");

        store = new UpdateEntities<StoredEntityData>()
        {
            @Override
            public List<StoredEntityData> createOrUpdate(String groupId, String artifactId, String versionId, List<Entity> entityDefinitions)
            {
                StoredEntityData entity1 = new StoredEntityData(groupId, artifactId, versionId);
                StoredEntityData entity2 = new StoredEntityData(groupId, artifactId, versionId);
                List<StoredEntityData> result = new ArrayList<>();
                result.add(entity1);
                result.add(entity2);
                return result;
            }

            @Override
            public long delete(String groupId, String artifactId)
            {
                return 10L;
            }

            @Override
            public long delete(String groupId, String artifactId, String versionId)
            {
                return 1L;
            }

            @Override
            public List<StoredEntityData> getStoredEntities(String groupId, String artifactId)
            {
                StoredEntityData entity = new StoredEntityData(groupId, artifactId, "2.0.0");
                return Collections.singletonList(entity);
            }

            @Override
            public List<Pair<String, String>> getStoredEntitiesCoordinates()
            {
                List<Pair<String, String>> coordinates = new ArrayList<>();
                coordinates.add(Tuples.pair("com.example", "artifact1"));
                coordinates.add(Tuples.pair("com.example", "artifact2"));
                return coordinates;
            }

            @Override
            public List<StoredEntityData> getAllStoredEntities()
            {
                StoredEntityData entity1 = new StoredEntityData("com.example", "artifact1", "1.0.0");
                StoredEntityData entity2 = new StoredEntityData("com.example", "artifact2", "2.0.0");
                List<StoredEntityData> result = new ArrayList<>();
                result.add(entity1);
                result.add(entity2);
                return result;
            }

            @Override
            public List<Entity> getAllEntities(String groupId, String artifactId, String versionId)
            {
                return null;
            }

            @Override
            public Optional<Entity> getEntity(String groupId, String artifactId, String versionId, String path)
            {
                return Optional.empty();
            }

            @Override
            public List<Entity> getEntityFromDependencies(Set<ProjectVersion> dependencies, List<String> entityPaths)
            {
                return null;
            }

            @Override
            public List<Entity> getEntitiesByPackage(String groupId, String artifactId, String versionId, String packageName, Set<String> classifierPaths, boolean includeSubPackages)
            {
                return null;
            }

            @Override
            public List<DepotEntity> findReleasedClassifierEntities(String classifier)
            {
                return null;
            }

            @Override
            public List<DepotEntity> findLatestClassifierEntities(String classifier)
            {
                return null;
            }

            @Override
            public List<DepotEntity> findClassifierEntitiesByVersions(String classifier, List<ProjectVersion> projectVersions)
            {
                return null;
            }

            @Override
            public List<DepotEntityOverview> findReleasedClassifierSummaries(String classifier)
            {
                return null;
            }

            @Override
            public List<DepotEntityOverview> findLatestClassifierSummaries(String classifier)
            {
                return null;
            }

            @Override
            public List<DepotEntityOverview> findClassifierSummariesByVersions(String classifier, List<ProjectVersion> projectVersions)
            {
                return null;
            }

            @Override
            public List<DepotEntity> findReleasedClassifierEntities(String classifier, String search, Integer limit)
            {
                return null;
            }

            @Override
            public List<DepotEntity> findLatestClassifierEntities(String classifier, String search, Integer limit)
            {
                return null;
            }

            @Override
            public List<DepotEntity> findClassifierEntitiesByVersions(String classifier, List<ProjectVersion> projectVersions, String search, Integer limit)
            {
                return null;
            }

            @Override
            public List<StoredEntityData> getStoredEntities(String groupId, String artifactId, String versionId)
            {
                return null;
            }
        };
    }

    @Test
    public void canCreateOrUpdate()
    {
        List<StoredEntityData> result = store.createOrUpdate("com.example", "myartifact", "1.0.0", testEntities);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("com.example", result.get(0).getGroupId());
        Assertions.assertEquals("myartifact", result.get(0).getArtifactId());
        Assertions.assertEquals("1.0.0", result.get(0).getVersionId());
    }

    @Test
    public void canDeleteByGroupAndArtifact()
    {
        long result = store.delete("com.example", "myartifact");

        Assertions.assertEquals(10L, result);
    }

    @Test
    public void canDeleteByGroupArtifactAndVersion()
    {
        long result = store.delete("com.example", "myartifact", "1.0.0");

        Assertions.assertEquals(1L, result);
    }

    @Test
    public void canGetStoredEntities()
    {
        List<StoredEntityData> result = store.getStoredEntities("com.example", "myartifact");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("com.example", result.get(0).getGroupId());
        Assertions.assertEquals("myartifact", result.get(0).getArtifactId());
        Assertions.assertEquals("2.0.0", result.get(0).getVersionId());
    }

    @Test
    public void canGetStoredEntitiesCoordinates()
    {
        List<Pair<String, String>> result = store.getStoredEntitiesCoordinates();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("com.example", result.get(0).getOne());
        Assertions.assertEquals("artifact1", result.get(0).getTwo());
        Assertions.assertEquals("com.example", result.get(1).getOne());
        Assertions.assertEquals("artifact2", result.get(1).getTwo());
    }

    @Test
    public void canGetAllStoredEntities()
    {
        List<StoredEntityData> result = store.getAllStoredEntities();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("com.example", result.get(0).getGroupId());
        Assertions.assertEquals("artifact1", result.get(0).getArtifactId());
        Assertions.assertEquals("1.0.0", result.get(0).getVersionId());
        Assertions.assertEquals("com.example", result.get(1).getGroupId());
        Assertions.assertEquals("artifact2", result.get(1).getArtifactId());
        Assertions.assertEquals("2.0.0", result.get(1).getVersionId());
    }
}
