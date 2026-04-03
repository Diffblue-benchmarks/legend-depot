// Copyright 2021 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.depot.store.api.entities;

import org.finos.legend.depot.domain.entity.DepotEntity;
import org.finos.legend.depot.domain.entity.DepotEntityOverview;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.entities.StoredEntityData;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TestEntities
{
    private static final String GROUP_ID = "test.group";
    private static final String ARTIFACT_ID = "test-artifact";
    private static final String VERSION_ID = "1.0.0";
    private static final String CLASSIFIER = "test::Classifier";

    private static class TestEntitiesImpl implements Entities<StoredEntityData>
    {
        List<ProjectVersion> capturedVersions;
        String capturedClassifier;

        @Override
        public List<Entity> getAllEntities(String groupId, String artifactId, String versionId)
        {
            return Collections.emptyList();
        }

        @Override
        public Optional<Entity> getEntity(String groupId, String artifactId, String versionId, String path)
        {
            return Optional.empty();
        }

        @Override
        public List<Entity> getEntityFromDependencies(Set<ProjectVersion> dependencies, List<String> entityPaths)
        {
            return Collections.emptyList();
        }

        @Override
        public List<Entity> getEntitiesByPackage(String groupId, String artifactId, String versionId, String packageName, Set<String> classifierPaths, boolean includeSubPackages)
        {
            return Collections.emptyList();
        }

        @Override
        public List<DepotEntity> findReleasedClassifierEntities(String classifier)
        {
            return Collections.emptyList();
        }

        @Override
        public List<DepotEntity> findLatestClassifierEntities(String classifier)
        {
            return Collections.emptyList();
        }

        @Override
        public List<DepotEntity> findClassifierEntitiesByVersions(String classifier, List<ProjectVersion> projectVersions)
        {
            this.capturedClassifier = classifier;
            this.capturedVersions = new ArrayList<>(projectVersions);
            return Collections.emptyList();
        }

        @Override
        public List<DepotEntityOverview> findReleasedClassifierSummaries(String classifier)
        {
            return Collections.emptyList();
        }

        @Override
        public List<DepotEntityOverview> findLatestClassifierSummaries(String classifier)
        {
            return Collections.emptyList();
        }

        @Override
        public List<DepotEntityOverview> findClassifierSummariesByVersions(String classifier, List<ProjectVersion> projectVersions)
        {
            return Collections.emptyList();
        }

        @Override
        public List<DepotEntity> findReleasedClassifierEntities(String classifier, String search, Integer limit)
        {
            return Collections.emptyList();
        }

        @Override
        public List<DepotEntity> findLatestClassifierEntities(String classifier, String search, Integer limit)
        {
            return Collections.emptyList();
        }

        @Override
        public List<DepotEntity> findClassifierEntitiesByVersions(String classifier, List<ProjectVersion> projectVersions, String search, Integer limit)
        {
            return Collections.emptyList();
        }

        @Override
        public List<StoredEntityData> getStoredEntities(String groupId, String artifactId, String versionId)
        {
            return Collections.emptyList();
        }
    }

    @Test
    public void testFindEntitiesByClassifierDelegatesToFindClassifierEntitiesByVersions()
    {
        TestEntitiesImpl entities = new TestEntitiesImpl();

        entities.findEntitiesByClassifier(GROUP_ID, ARTIFACT_ID, VERSION_ID, CLASSIFIER);

        Assertions.assertEquals(CLASSIFIER, entities.capturedClassifier);
        Assertions.assertNotNull(entities.capturedVersions);
        Assertions.assertEquals(1, entities.capturedVersions.size());
        ProjectVersion pv = entities.capturedVersions.get(0);
        Assertions.assertEquals(GROUP_ID, pv.getGroupId());
        Assertions.assertEquals(ARTIFACT_ID, pv.getArtifactId());
        Assertions.assertEquals(VERSION_ID, pv.getVersionId());
    }

    @Test
    public void testFindEntitiesByClassifierReturnsResult()
    {
        TestEntitiesImpl entities = new TestEntitiesImpl();

        List<DepotEntity> result = entities.findEntitiesByClassifier(GROUP_ID, ARTIFACT_ID, VERSION_ID, CLASSIFIER);

        Assertions.assertNotNull(result);
    }
}
