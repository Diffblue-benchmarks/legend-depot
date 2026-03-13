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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EntitiesServiceTest
{
    @Test
    public void canGetDependenciesEntitiesByClassifierWithDefaultMethod()
    {
        TestEntitiesService service = new TestEntitiesService();

        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String classifier = "testClassifier";
        boolean transitive = true;
        boolean includeOrigin = false;

        List<ProjectVersionEntities> result = service.getDependenciesEntitiesByClassifier(
            groupId, artifactId, versionId, classifier, transitive, includeOrigin
        );

        Assertions.assertNotNull(result);
        Assertions.assertTrue(service.wasCalled);
        Assertions.assertNotNull(service.capturedProjectVersions);
        Assertions.assertEquals(1, service.capturedProjectVersions.size());

        ProjectVersion capturedVersion = service.capturedProjectVersions.get(0);
        Assertions.assertEquals(groupId, capturedVersion.getGroupId());
        Assertions.assertEquals(artifactId, capturedVersion.getArtifactId());
        Assertions.assertEquals(versionId, capturedVersion.getVersionId());

        Assertions.assertEquals(classifier, service.capturedClassifier);
        Assertions.assertEquals(transitive, service.capturedTransitive);
        Assertions.assertEquals(includeOrigin, service.capturedIncludeOrigin);
    }

    private static class TestEntitiesService implements EntitiesService<StoredEntity>
    {
        boolean wasCalled = false;
        List<ProjectVersion> capturedProjectVersions;
        String capturedClassifier;
        boolean capturedTransitive;
        boolean capturedIncludeOrigin;

        @Override
        public List<Entity> getEntities(String groupId, String artifactId, String versionId)
        {
            return Collections.emptyList();
        }

        @Override
        public List<Entity> getEntitiesByClassifier(String groupId, String artifactId, String versionId, String classifier)
        {
            return Collections.emptyList();
        }

        @Override
        public Optional<Entity> getEntity(String groupId, String artifactId, String versionId, String entityPath)
        {
            return Optional.empty();
        }

        @Override
        public List<Entity> getEntityFromDependencies(String groupId, String artifactId, String versionId, List<String> entityPaths, boolean includeOrigin)
        {
            return Collections.emptyList();
        }

        @Override
        public List<Entity> getEntitiesByPackage(String groupId, String artifactId, String versionId, String packageName, Set<String> classifierPaths, boolean includeSubPackages)
        {
            return Collections.emptyList();
        }

        @Override
        public List<ProjectVersionEntities> getDependenciesEntities(List<ProjectVersion> projectDependencies, boolean transitive, boolean includeOrigin)
        {
            return Collections.emptyList();
        }

        @Override
        public List<ProjectVersionEntities> getDependenciesEntitiesByClassifier(List<ProjectVersion> projectDependencies, String classifier, boolean transitive, boolean includeOrigin)
        {
            wasCalled = true;
            capturedProjectVersions = projectDependencies;
            capturedClassifier = classifier;
            capturedTransitive = transitive;
            capturedIncludeOrigin = includeOrigin;
            return Collections.emptyList();
        }
    }
}
