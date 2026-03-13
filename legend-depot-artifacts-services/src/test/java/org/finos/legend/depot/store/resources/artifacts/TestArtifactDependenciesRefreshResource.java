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
import org.finos.legend.depot.services.api.artifacts.refresh.RefreshDependenciesService;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class TestArtifactDependenciesRefreshResource
{
    private RefreshDependenciesService refreshDependenciesService;
    private AuthorisationProvider authorisationProvider;
    private Provider<Principal> principalProvider;
    private ArtifactDependenciesRefreshResource resource;

    @BeforeEach
    public void setUp()
    {
        refreshDependenciesService = mock(RefreshDependenciesService.class);
        authorisationProvider = mock(AuthorisationProvider.class);
        principalProvider = mock(Provider.class);
        resource = new ArtifactDependenciesRefreshResource(refreshDependenciesService, authorisationProvider, principalProvider);
    }

    @Test
    public void canConstructResourceWithDependencies()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canUpdateTransitiveDependencies()
    {
        String groupId = "examples.metadata";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";

        StoreProjectVersionData expectedData = new StoreProjectVersionData(groupId, artifactId, versionId);
        when(refreshDependenciesService.updateTransitiveDependencies(groupId, artifactId, versionId)).thenReturn(expectedData);

        StoreProjectVersionData result = resource.updateTransitiveDependencies(groupId, artifactId, versionId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(groupId, result.getGroupId());
        Assertions.assertEquals(artifactId, result.getArtifactId());
        Assertions.assertEquals(versionId, result.getVersionId());
        verify(refreshDependenciesService, times(1)).updateTransitiveDependencies(groupId, artifactId, versionId);
        verify(authorisationProvider, times(1)).authorise(principalProvider, ArtifactDependenciesRefreshResource.ARTIFACTS_RESOURCE);
    }

    @Test
    public void canGetResourceName()
    {
        String resourceName = resource.getResourceName();

        Assertions.assertNotNull(resourceName);
        Assertions.assertEquals(ArtifactDependenciesRefreshResource.ARTIFACTS_RESOURCE, resourceName);
    }

    @Test
    public void canUpdateTransitiveDependenciesWithDifferentVersions()
    {
        String groupId = "org.test";
        String artifactId = "my-artifact";
        String versionId = "2.5.3";

        StoreProjectVersionData expectedData = new StoreProjectVersionData(groupId, artifactId, versionId);
        when(refreshDependenciesService.updateTransitiveDependencies(groupId, artifactId, versionId)).thenReturn(expectedData);

        StoreProjectVersionData result = resource.updateTransitiveDependencies(groupId, artifactId, versionId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(groupId, result.getGroupId());
        Assertions.assertEquals(artifactId, result.getArtifactId());
        verify(refreshDependenciesService, times(1)).updateTransitiveDependencies(groupId, artifactId, versionId);
    }
}
