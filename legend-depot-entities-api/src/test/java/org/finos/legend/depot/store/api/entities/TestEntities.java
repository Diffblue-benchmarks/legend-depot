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
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestEntities
{
    @Test
    public void testFindEntitiesByClassifierDelegatesToFindClassifierEntitiesByVersions()
    {
        List<String> capturedClassifiers = new ArrayList<>();
        List<List<ProjectVersion>> capturedVersions = new ArrayList<>();
        List<DepotEntity> expectedResult = Collections.singletonList(new DepotEntity("org.test", "artifact", "1.0.0"));

        Entities<StoredEntity> entities = new Entities<StoredEntity>()
        {
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
                capturedClassifiers.add(classifier);
                capturedVersions.add(projectVersions);
                return expectedResult;
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
            public List<StoredEntity> getStoredEntities(String groupId, String artifactId, String versionId)
            {
                return Collections.emptyList();
            }
        };

        List<DepotEntity> result = entities.findEntitiesByClassifier("org.test", "artifact", "1.0.0", "meta::pure::metamodel::type::Class");

        assertEquals(expectedResult, result);
        assertEquals(1, capturedClassifiers.size());
        assertEquals("meta::pure::metamodel::type::Class", capturedClassifiers.get(0));
        assertEquals(1, capturedVersions.size());
        assertNotNull(capturedVersions.get(0));
        assertEquals(1, capturedVersions.get(0).size());
        ProjectVersion captured = capturedVersions.get(0).get(0);
        assertEquals("org.test", captured.getGroupId());
        assertEquals("artifact", captured.getArtifactId());
        assertEquals("1.0.0", captured.getVersionId());
    }
}
