// Copyright 2021 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.finos.legend.depot.services.api.entities;

import org.finos.legend.depot.domain.entity.ProjectVersionEntities;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.depot.store.model.entities.StoredEntityData;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ManageEntitiesServiceTest
{
    private final ManageEntitiesService<StoredEntityData> service = new ManageEntitiesService<StoredEntityData>()
    {
        @Override
        public List<StoredEntityData> getStoredEntities(String groupId, String artifactId, String versionId)
        {
            return Collections.singletonList(new StoredEntityData(groupId, artifactId, versionId));
        }

        @Override
        public long delete(String groupId, String artifactId)
        {
            return 1L;
        }

        @Override
        public long delete(String groupId, String artifactId, String versionId)
        {
            return 2L;
        }

        @Override
        public void createOrUpdate(String groupId, String artifactId, String versionId, List<Entity> entities)
        {
            // no-op
        }

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
            return Collections.emptyList();
        }
    };

    @Test
    public void testGetStoredEntities()
    {
        List<StoredEntityData> result = service.getStoredEntities("group", "artifact", "1.0.0");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testDeleteByGroupAndArtifact()
    {
        long result = service.delete("group", "artifact");

        Assertions.assertEquals(1L, result);
    }

    @Test
    public void testDeleteByGroupArtifactAndVersion()
    {
        long result = service.delete("group", "artifact", "1.0.0");

        Assertions.assertEquals(2L, result);
    }

    @Test
    public void testCreateOrUpdate()
    {
        service.createOrUpdate("group", "artifact", "1.0.0", Collections.emptyList());
    }
}
