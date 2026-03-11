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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestArtifactsRefreshResource
{
    private final ArtifactsRefreshService artifactsRefreshService = mock(ArtifactsRefreshService.class);
    private final AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    @SuppressWarnings("unchecked")
    private final Provider<Principal> principalProvider = mock(Provider.class);

    private ArtifactsRefreshResource resource;

    @BeforeEach
    public void setUp()
    {
        doNothing().when(authorisationProvider).authorise(any(), anyString());
        resource = new ArtifactsRefreshResource(artifactsRefreshService, authorisationProvider, principalProvider);
    }

    @Test
    public void testConstructor()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void testGetResourceName()
    {
        Assertions.assertEquals(ArtifactsRefreshResource.ARTIFACTS_RESOURCE, "ArtifactsRefresh");
    }

    @Test
    public void testUpdateProjectVersion()
    {
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse();
        when(artifactsRefreshService.refreshVersionForProject(anyString(), anyString(), anyString(), anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateProjectVersion("examples.metadata", "test", "1.0.0", false, false);

        Assertions.assertNotNull(response);
        verify(authorisationProvider).authorise(any(), anyString());
        verify(artifactsRefreshService).refreshVersionForProject(anyString(), anyString(), anyString(), anyBoolean(), anyBoolean(), anyString());
    }

    @Test
    public void testUpdateProjectAllVersions()
    {
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse();
        when(artifactsRefreshService.refreshAllVersionsForProject(anyString(), anyString(), anyBoolean(), anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateProjectAllVersions("examples.metadata", "test", false, false, false);

        Assertions.assertNotNull(response);
        verify(authorisationProvider).authorise(any(), anyString());
        verify(artifactsRefreshService).refreshAllVersionsForProject(anyString(), anyString(), anyBoolean(), anyBoolean(), anyBoolean(), anyString());
    }

    @Test
    public void testUpdateAllProjectsAllVersions()
    {
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse();
        when(artifactsRefreshService.refreshAllVersionsForAllProjects(anyBoolean(), anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateAllProjectsAllVersions(false, false, false);

        Assertions.assertNotNull(response);
        verify(authorisationProvider).authorise(any(), anyString());
        verify(artifactsRefreshService).refreshAllVersionsForAllProjects(anyBoolean(), anyBoolean(), anyBoolean(), anyString());
    }

    @Test
    public void testUpdateAllProjectsMaster()
    {
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse();
        when(artifactsRefreshService.refreshDefaultSnapshotsForAllProjects(anyBoolean(), anyBoolean(), anyString()))
                .thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.updateAllProjectsMaster(false, false);

        Assertions.assertNotNull(response);
        verify(authorisationProvider).authorise(any(), anyString());
        verify(artifactsRefreshService).refreshDefaultSnapshotsForAllProjects(anyBoolean(), anyBoolean(), anyString());
    }
}
