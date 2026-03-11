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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class TestArtifactsPurgeResource
{
    public static final String TEST_GROUP_ID = "examples.metadata";
    public static final String TEST_ARTIFACT_ID = "test";
    public static final String TEST_VERSION_ID = "1.0.0";

    private final ArtifactsPurgeService purgeService = mock(ArtifactsPurgeService.class);
    private final AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    private final Provider<Principal> principalProvider = () -> () -> "test";
    private ArtifactsPurgeResource resource;

    @BeforeEach
    public void setUp()
    {
        resource = new ArtifactsPurgeResource(purgeService, authorisationProvider, principalProvider);
    }

    @Test
    public void canCreateResource()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void testGetResourceName()
    {
        Assertions.assertEquals("ArtifactsPurge", ArtifactsPurgeResource.ARTIFACTS_RESOURCE);
    }

    @Test
    public void canEvictVersion()
    {
        MetadataNotificationResponse response = resource.evictVersion(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
        Assertions.assertNotNull(response);
        verify(purgeService, times(1)).evict(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
    }

    @Test
    public void canDeleteVersion()
    {
        MetadataNotificationResponse response = resource.deleteVersion(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
        Assertions.assertNotNull(response);
        verify(purgeService, times(1)).delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
    }

    @Test
    public void canDeleteSnapShotVersion()
    {
        when(purgeService.deleteSnapshotVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID, java.util.Arrays.asList("test-SNAPSHOT", "test2-SNAPSHOT")))
                .thenReturn("Deleted all snapshot versions");
        String result = resource.deleteSnapShotVersion(TEST_GROUP_ID, TEST_ARTIFACT_ID, "test-SNAPSHOT,test2-SNAPSHOT");
        Assertions.assertEquals("Deleted all snapshot versions", result);
    }

    @Test
    public void canEvictOldVersions()
    {
        MetadataNotificationResponse expected = new MetadataNotificationResponse();
        when(purgeService.evictOldestProjectVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID, 2)).thenReturn(expected);
        MetadataNotificationResponse response = resource.evictOldVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID, 2);
        Assertions.assertNotNull(response);
        verify(purgeService, times(1)).evictOldestProjectVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID, 2);
    }

    @Test
    public void canDeprecateVersion()
    {
        MetadataNotificationResponse expected = new MetadataNotificationResponse();
        when(purgeService.deprecate(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID)).thenReturn(expected);
        MetadataNotificationResponse response = resource.deprecateVersion(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
        Assertions.assertNotNull(response);
        verify(purgeService, times(1)).deprecate(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
    }

    @Test
    public void canEvictVersionsNotUsed()
    {
        MetadataNotificationResponse expected = new MetadataNotificationResponse();
        when(purgeService.evictVersionsNotUsed()).thenReturn(expected);
        MetadataNotificationResponse response = resource.evictVersionsNotUsed();
        Assertions.assertNotNull(response);
        verify(purgeService, times(1)).evictVersionsNotUsed();
    }
}
