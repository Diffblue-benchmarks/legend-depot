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

package org.finos.legend.depot.store.resources.artifacts;

import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.domain.notifications.MetadataNotificationResponse;
import org.finos.legend.depot.services.api.artifacts.refresh.ArtifactsRefreshService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestArtifactsRefreshResource
{
    private ArtifactsRefreshService artifactsRefreshService;
    private AuthorisationProvider authorisationProvider;
    private Provider<Principal> principalProvider;
    private ArtifactsRefreshResource resource;

    @BeforeEach
    public void setUp()
    {
        artifactsRefreshService = mock(ArtifactsRefreshService.class);
        authorisationProvider = mock(AuthorisationProvider.class);
        principalProvider = mock(Provider.class);
        Principal principal = mock(Principal.class);

        when(principalProvider.get()).thenReturn(principal);
        doNothing().when(authorisationProvider).authorise(any(Provider.class), anyString());

        resource = new ArtifactsRefreshResource(artifactsRefreshService, authorisationProvider, principalProvider);
    }

    @Test
    public void canConstructArtifactsRefreshResource()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetResourceName()
    {
        String resourceName = resource.getResourceName();

        Assertions.assertNotNull(resourceName);
        Assertions.assertEquals(ArtifactsRefreshResource.ARTIFACTS_RESOURCE, resourceName);
    }

    @Test
    public void canUpdateProjectVersion()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse().addMessage("Version updated");

        when(artifactsRefreshService.refreshVersionForProject(eq(groupId), eq(artifactId), eq(versionId), anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateProjectVersion(groupId, artifactId, versionId, false, false);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedResponse, response);
        verify(authorisationProvider).authorise(any(Provider.class), eq(ArtifactsRefreshResource.ARTIFACTS_RESOURCE));
        verify(artifactsRefreshService).refreshVersionForProject(eq(groupId), eq(artifactId), eq(versionId), eq(false), eq(false), anyString());
    }

    @Test
    public void canUpdateProjectVersionWithFullUpdateAndTransitive()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "2.0.0";
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse().addMessage("Version updated with full update");

        when(artifactsRefreshService.refreshVersionForProject(eq(groupId), eq(artifactId), eq(versionId), anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateProjectVersion(groupId, artifactId, versionId, true, true);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedResponse, response);
        verify(artifactsRefreshService).refreshVersionForProject(eq(groupId), eq(artifactId), eq(versionId), eq(true), eq(true), anyString());
    }

    @Test
    public void canUpdateProjectAllVersions()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse().addMessage("All versions updated");

        when(artifactsRefreshService.refreshAllVersionsForProject(eq(groupId), eq(artifactId), anyBoolean(), anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateProjectAllVersions(groupId, artifactId, false, false, false);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedResponse, response);
        verify(authorisationProvider).authorise(any(Provider.class), eq(ArtifactsRefreshResource.ARTIFACTS_RESOURCE));
        verify(artifactsRefreshService).refreshAllVersionsForProject(eq(groupId), eq(artifactId), eq(false), eq(false), eq(false), anyString());
    }

    @Test
    public void canUpdateProjectAllVersionsWithAllFlags()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse().addMessage("All versions updated with flags");

        when(artifactsRefreshService.refreshAllVersionsForProject(eq(groupId), eq(artifactId), anyBoolean(), anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateProjectAllVersions(groupId, artifactId, true, true, true);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedResponse, response);
        verify(artifactsRefreshService).refreshAllVersionsForProject(eq(groupId), eq(artifactId), eq(true), eq(true), eq(true), anyString());
    }

    @Test
    public void canUpdateAllProjectsAllVersions()
    {
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse().addMessage("All projects all versions updated");

        when(artifactsRefreshService.refreshAllVersionsForAllProjects(anyBoolean(), anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateAllProjectsAllVersions(false, false, false);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedResponse, response);
        verify(authorisationProvider).authorise(any(Provider.class), eq(ArtifactsRefreshResource.ARTIFACTS_RESOURCE));
        verify(artifactsRefreshService).refreshAllVersionsForAllProjects(eq(false), eq(false), eq(false), anyString());
    }

    @Test
    public void canUpdateAllProjectsAllVersionsWithAllFlags()
    {
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse().addMessage("All projects all versions updated with flags");

        when(artifactsRefreshService.refreshAllVersionsForAllProjects(anyBoolean(), anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateAllProjectsAllVersions(true, true, true);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedResponse, response);
        verify(artifactsRefreshService).refreshAllVersionsForAllProjects(eq(true), eq(true), eq(true), anyString());
    }

    @Test
    public void canUpdateAllProjectsMaster()
    {
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse().addMessage("All projects master snapshots updated");

        when(artifactsRefreshService.refreshDefaultSnapshotsForAllProjects(anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateAllProjectsMaster(false, false);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedResponse, response);
        verify(authorisationProvider).authorise(any(Provider.class), eq(ArtifactsRefreshResource.ARTIFACTS_RESOURCE));
        verify(artifactsRefreshService).refreshDefaultSnapshotsForAllProjects(eq(false), eq(false), anyString());
    }

    @Test
    public void canUpdateAllProjectsMasterWithAllFlags()
    {
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse().addMessage("All projects master snapshots updated with flags");

        when(artifactsRefreshService.refreshDefaultSnapshotsForAllProjects(anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateAllProjectsMaster(true, true);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedResponse, response);
        verify(artifactsRefreshService).refreshDefaultSnapshotsForAllProjects(eq(true), eq(true), anyString());
    }
}
