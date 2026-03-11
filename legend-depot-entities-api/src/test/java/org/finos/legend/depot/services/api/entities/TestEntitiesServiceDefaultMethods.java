package org.finos.legend.depot.services.api.entities;

import org.finos.legend.depot.domain.entity.ProjectVersionEntities;
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

class TestEntitiesServiceDefaultMethods
{
    private List<ProjectVersion> capturedDependencies;
    private boolean capturedTransitive;
    private boolean capturedIncludeOrigin;
    private String capturedClassifier;

    private final EntitiesService<StoredEntityData> service = new EntitiesService<StoredEntityData>()
    {
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
            capturedDependencies = projectDependencies;
            capturedTransitive = transitive;
            capturedIncludeOrigin = includeOrigin;
            return Collections.singletonList(new ProjectVersionEntities("g", "a", "1.0.0", Collections.emptyList()));
        }

        @Override
        public List<ProjectVersionEntities> getDependenciesEntitiesByClassifier(List<ProjectVersion> projectDependencies, String classifier, boolean transitive, boolean includeOrigin)
        {
            capturedDependencies = projectDependencies;
            capturedClassifier = classifier;
            capturedTransitive = transitive;
            capturedIncludeOrigin = includeOrigin;
            return Collections.singletonList(new ProjectVersionEntities("g", "a", "1.0.0", Collections.emptyList()));
        }
    };

    @Test
    void testGetDependenciesEntitiesDefaultDelegatesToListOverload()
    {
        List<ProjectVersionEntities> result = service.getDependenciesEntities("org.test", "my-artifact", "2.0.0", true, false);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, capturedDependencies.size());
        Assertions.assertEquals("org.test", capturedDependencies.get(0).getGroupId());
        Assertions.assertEquals("my-artifact", capturedDependencies.get(0).getArtifactId());
        Assertions.assertEquals("2.0.0", capturedDependencies.get(0).getVersionId());
        Assertions.assertTrue(capturedTransitive);
        Assertions.assertFalse(capturedIncludeOrigin);
    }

    @Test
    void testGetDependenciesEntitiesByClassifierDefaultDelegatesToListOverload()
    {
        List<ProjectVersionEntities> result = service.getDependenciesEntitiesByClassifier("org.test", "my-artifact", "3.0.0", "meta::pure::Class", false, true);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, capturedDependencies.size());
        Assertions.assertEquals("org.test", capturedDependencies.get(0).getGroupId());
        Assertions.assertEquals("my-artifact", capturedDependencies.get(0).getArtifactId());
        Assertions.assertEquals("3.0.0", capturedDependencies.get(0).getVersionId());
        Assertions.assertEquals("meta::pure::Class", capturedClassifier);
        Assertions.assertFalse(capturedTransitive);
        Assertions.assertTrue(capturedIncludeOrigin);
    }
}
