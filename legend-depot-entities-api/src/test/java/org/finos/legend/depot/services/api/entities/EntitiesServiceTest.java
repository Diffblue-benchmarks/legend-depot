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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EntitiesServiceTest
{
    private static class TestEntitiesService implements EntitiesService<StoredEntity>
    {
        List<ProjectVersion> lastDependenciesEntitiesArgs;
        boolean lastTransitive;
        boolean lastIncludeOrigin;
        String lastClassifier;

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
            this.lastDependenciesEntitiesArgs = projectDependencies;
            this.lastTransitive = transitive;
            this.lastIncludeOrigin = includeOrigin;
            this.lastClassifier = null;
            return Collections.emptyList();
        }

        @Override
        public List<ProjectVersionEntities> getDependenciesEntitiesByClassifier(List<ProjectVersion> projectDependencies, String classifier, boolean transitive, boolean includeOrigin)
        {
            this.lastDependenciesEntitiesArgs = projectDependencies;
            this.lastClassifier = classifier;
            this.lastTransitive = transitive;
            this.lastIncludeOrigin = includeOrigin;
            return Collections.emptyList();
        }
    }

    @Test
    public void testGetDependenciesEntitiesDefaultMethodDelegatesToListMethod()
    {
        TestEntitiesService service = new TestEntitiesService();

        service.getDependenciesEntities("com.example", "my-artifact", "1.0.0", true, false);

        Assertions.assertNotNull(service.lastDependenciesEntitiesArgs);
        Assertions.assertEquals(1, service.lastDependenciesEntitiesArgs.size());
        ProjectVersion pv = service.lastDependenciesEntitiesArgs.get(0);
        Assertions.assertEquals("com.example", pv.getGroupId());
        Assertions.assertEquals("my-artifact", pv.getArtifactId());
        Assertions.assertEquals("1.0.0", pv.getVersionId());
        Assertions.assertTrue(service.lastTransitive);
        Assertions.assertFalse(service.lastIncludeOrigin);
    }

    @Test
    public void testGetDependenciesEntitiesDefaultMethodWithIncludeOrigin()
    {
        TestEntitiesService service = new TestEntitiesService();

        service.getDependenciesEntities("org.finos", "legend", "2.0.0", false, true);

        Assertions.assertNotNull(service.lastDependenciesEntitiesArgs);
        Assertions.assertEquals(1, service.lastDependenciesEntitiesArgs.size());
        ProjectVersion pv = service.lastDependenciesEntitiesArgs.get(0);
        Assertions.assertEquals("org.finos", pv.getGroupId());
        Assertions.assertEquals("legend", pv.getArtifactId());
        Assertions.assertEquals("2.0.0", pv.getVersionId());
        Assertions.assertFalse(service.lastTransitive);
        Assertions.assertTrue(service.lastIncludeOrigin);
    }

    @Test
    public void testGetDependenciesEntitiesByClassifierDefaultMethodDelegatesToListMethod()
    {
        TestEntitiesService service = new TestEntitiesService();

        service.getDependenciesEntitiesByClassifier("com.example", "my-artifact", "1.0.0", "meta::pure::metamodel::type::Class", true, false);

        Assertions.assertNotNull(service.lastDependenciesEntitiesArgs);
        Assertions.assertEquals(1, service.lastDependenciesEntitiesArgs.size());
        ProjectVersion pv = service.lastDependenciesEntitiesArgs.get(0);
        Assertions.assertEquals("com.example", pv.getGroupId());
        Assertions.assertEquals("my-artifact", pv.getArtifactId());
        Assertions.assertEquals("1.0.0", pv.getVersionId());
        Assertions.assertEquals("meta::pure::metamodel::type::Class", service.lastClassifier);
        Assertions.assertTrue(service.lastTransitive);
        Assertions.assertFalse(service.lastIncludeOrigin);
    }

    @Test
    public void testGetDependenciesEntitiesByClassifierDefaultMethodWithIncludeOrigin()
    {
        TestEntitiesService service = new TestEntitiesService();

        service.getDependenciesEntitiesByClassifier("org.finos", "legend", "2.0.0", "meta::pure::metamodel::relationship::Association", false, true);

        Assertions.assertNotNull(service.lastDependenciesEntitiesArgs);
        Assertions.assertEquals(1, service.lastDependenciesEntitiesArgs.size());
        ProjectVersion pv = service.lastDependenciesEntitiesArgs.get(0);
        Assertions.assertEquals("org.finos", pv.getGroupId());
        Assertions.assertEquals("legend", pv.getArtifactId());
        Assertions.assertEquals("2.0.0", pv.getVersionId());
        Assertions.assertEquals("meta::pure::metamodel::relationship::Association", service.lastClassifier);
        Assertions.assertFalse(service.lastTransitive);
        Assertions.assertTrue(service.lastIncludeOrigin);
    }
}
