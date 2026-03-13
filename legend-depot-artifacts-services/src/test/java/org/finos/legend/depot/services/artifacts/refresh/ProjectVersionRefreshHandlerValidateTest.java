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

package org.finos.legend.depot.services.artifacts.refresh;

import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.services.api.artifacts.configuration.IncludeProjectPropertiesConfiguration;
import org.finos.legend.depot.services.api.artifacts.refresh.RefreshDependenciesService;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.store.api.admin.artifacts.ArtifactsFilesStore;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ProjectVersionRefreshHandlerValidateTest
{
    private ManageProjectsService mockProjects;
    private ArtifactRepository mockRepository;
    private Queue mockQueue;
    private ArtifactsFilesStore mockArtifacts;
    private RefreshDependenciesService mockRefreshDeps;
    private ProjectVersionRefreshHandler handler;

    @BeforeEach
    public void setup()
    {
        mockProjects = Mockito.mock(ManageProjectsService.class);
        mockRepository = Mockito.mock(ArtifactRepository.class);
        mockQueue = Mockito.mock(Queue.class);
        mockArtifacts = Mockito.mock(ArtifactsFilesStore.class);
        mockRefreshDeps = Mockito.mock(RefreshDependenciesService.class);
        IncludeProjectPropertiesConfiguration propsConfig = new IncludeProjectPropertiesConfiguration(Collections.emptyList(), Collections.emptyList());

        handler = new ProjectVersionRefreshHandler(mockProjects, mockRepository, mockQueue, mockArtifacts, propsConfig, mockRefreshDeps, 5);
    }

    @Test
    public void testValidateWithValidRelease()
    {
        when(mockProjects.findCoordinates("org.finos", "legend-depot")).thenReturn(Optional.of(new StoreProjectData("PROD-1", "org.finos", "legend-depot")));
        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");

        List<String> errors = handler.validate(event);
        Assertions.assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateWithInvalidGroupId()
    {
        MetadataNotification event = new MetadataNotification("PROD-1", "123invalid", "legend-depot", "1.0.0");

        List<String> errors = handler.validate(event);
        Assertions.assertFalse(errors.isEmpty());
        Assertions.assertTrue(errors.stream().anyMatch(e -> e.contains("invalid groupId")));
    }

    @Test
    public void testValidateWithInvalidArtifactId()
    {
        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "InvalidArtifact", "1.0.0");

        List<String> errors = handler.validate(event);
        Assertions.assertFalse(errors.isEmpty());
        Assertions.assertTrue(errors.stream().anyMatch(e -> e.contains("invalid artifactId")));
    }

    @Test
    public void testValidateWithInvalidVersionId()
    {
        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "invalid");

        List<String> errors = handler.validate(event);
        Assertions.assertFalse(errors.isEmpty());
        Assertions.assertTrue(errors.stream().anyMatch(e -> e.contains("invalid versionId")));
    }

    @Test
    public void testValidateWithMismatchedProjectId()
    {
        StoreProjectData existing = new StoreProjectData("PROD-99", "org.finos", "legend-depot");
        when(mockProjects.findCoordinates("org.finos", "legend-depot")).thenReturn(Optional.of(existing));
        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");

        List<String> errors = handler.validate(event);
        Assertions.assertFalse(errors.isEmpty());
        Assertions.assertTrue(errors.stream().anyMatch(e -> e.contains("Invalid projectId")));
    }

    @Test
    public void testValidateSnapshotExceedsLimit()
    {
        when(mockProjects.findCoordinates("org.finos", "legend-depot")).thenReturn(Optional.of(new StoreProjectData("PROD-1", "org.finos", "legend-depot")));
        StoreProjectVersionData sv1 = new StoreProjectVersionData("org.finos", "legend-depot", "branch1-SNAPSHOT");
        StoreProjectVersionData sv2 = new StoreProjectVersionData("org.finos", "legend-depot", "branch2-SNAPSHOT");
        StoreProjectVersionData sv3 = new StoreProjectVersionData("org.finos", "legend-depot", "branch3-SNAPSHOT");
        StoreProjectVersionData sv4 = new StoreProjectVersionData("org.finos", "legend-depot", "branch4-SNAPSHOT");
        StoreProjectVersionData sv5 = new StoreProjectVersionData("org.finos", "legend-depot", "branch5-SNAPSHOT");
        when(mockProjects.findSnapshotVersions("org.finos", "legend-depot")).thenReturn(Arrays.asList(sv1, sv2, sv3, sv4, sv5));

        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "new-branch-SNAPSHOT");

        List<String> errors = handler.validate(event);
        Assertions.assertTrue(errors.stream().anyMatch(e -> e.contains("reached the limit")));
    }

    @Test
    public void testValidateSnapshotWithinLimit()
    {
        when(mockProjects.findCoordinates("org.finos", "legend-depot")).thenReturn(Optional.of(new StoreProjectData("PROD-1", "org.finos", "legend-depot")));
        StoreProjectVersionData sv1 = new StoreProjectVersionData("org.finos", "legend-depot", "branch1-SNAPSHOT");
        when(mockProjects.findSnapshotVersions("org.finos", "legend-depot")).thenReturn(Arrays.asList(sv1));

        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "new-branch-SNAPSHOT");

        List<String> errors = handler.validate(event);
        Assertions.assertTrue(errors.stream().noneMatch(e -> e.contains("reached the limit")));
    }
}
