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
import org.finos.legend.depot.services.api.artifacts.purge.ArtifactsPurgeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

public class TestArtifactsPurgeResource
{
    private static final String TEST_GROUP_ID = "examples.metadata";
    private static final String TEST_ARTIFACT_ID = "test";
    private static final String TEST_VERSION_ID = "2.0.0";

    private ArtifactsPurgeService artifactsPurgeService;
    private AuthorisationProvider authorisationProvider;
    private Provider<Principal> principalProvider;
    private ArtifactsPurgeResource resource;

    @BeforeEach
    public void setUp()
    {
        artifactsPurgeService = mock(ArtifactsPurgeService.class);
        authorisationProvider = mock(AuthorisationProvider.class);
        principalProvider = mock(Provider.class);
        resource = new ArtifactsPurgeResource(artifactsPurgeService, authorisationProvider, principalProvider);
    }

    @Test
    public void canInstantiateResource()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canEvictVersion()
    {
        MetadataNotificationResponse response = resource.evictVersion(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);

        Assertions.assertNotNull(response);
        verify(authorisationProvider, times(1)).authorise(principalProvider, ArtifactsPurgeResource.ARTIFACTS_RESOURCE);
        verify(artifactsPurgeService, times(1)).evict(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
    }

    @Test
    public void canDeleteVersion()
    {
        MetadataNotificationResponse response = resource.deleteVersion(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);

        Assertions.assertNotNull(response);
        verify(authorisationProvider, times(1)).authorise(principalProvider, ArtifactsPurgeResource.ARTIFACTS_RESOURCE);
        verify(artifactsPurgeService, times(1)).delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
    }

    @Test
    public void canDeleteSnapshotVersion()
    {
        String versions = "test-SNAPSHOT,test2-SNAPSHOT";
        String expectedResult = "Deleted all snapshot versions";
        when(artifactsPurgeService.deleteSnapshotVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID, java.util.Arrays.asList("test-SNAPSHOT", "test2-SNAPSHOT"))).thenReturn(expectedResult);

        String result = resource.deleteSnapShotVersion(TEST_GROUP_ID, TEST_ARTIFACT_ID, versions);

        Assertions.assertEquals(expectedResult, result);
        verify(artifactsPurgeService, times(1)).deleteSnapshotVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID, java.util.Arrays.asList("test-SNAPSHOT", "test2-SNAPSHOT"));
    }

    @Test
    public void canEvictOldVersions()
    {
        int versionsToKeep = 2;
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse();
        when(artifactsPurgeService.evictOldestProjectVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID, versionsToKeep)).thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.evictOldVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID, versionsToKeep);

        Assertions.assertNotNull(response);
        verify(authorisationProvider, times(1)).authorise(principalProvider, ArtifactsPurgeResource.ARTIFACTS_RESOURCE);
        verify(artifactsPurgeService, times(1)).evictOldestProjectVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID, versionsToKeep);
    }

    @Test
    public void canDeprecateVersion()
    {
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse();
        when(artifactsPurgeService.deprecate(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID)).thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.deprecateVersion(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);

        Assertions.assertNotNull(response);
        verify(authorisationProvider, times(1)).authorise(principalProvider, ArtifactsPurgeResource.ARTIFACTS_RESOURCE);
        verify(artifactsPurgeService, times(1)).deprecate(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
    }

    @Test
    public void canEvictVersionsNotUsed()
    {
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse();
        when(artifactsPurgeService.evictVersionsNotUsed()).thenReturn(expectedResponse);

        MetadataNotificationResponse response = resource.evictVersionsNotUsed();

        Assertions.assertNotNull(response);
        verify(authorisationProvider, times(1)).authorise(principalProvider, ArtifactsPurgeResource.ARTIFACTS_RESOURCE);
        verify(artifactsPurgeService, times(1)).evictVersionsNotUsed();
    }

    @Test
    public void canGetResourceName()
    {
        String resourceName = resource.getResourceName();

        Assertions.assertEquals(ArtifactsPurgeResource.ARTIFACTS_RESOURCE, resourceName);
    }
}
