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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class TestArtifactDependenciesRefreshResource
{
    public static final String TEST_GROUP_ID = "examples.metadata";
    public static final String TEST_ARTIFACT_ID = "test";
    public static final String TEST_VERSION_ID = "1.0.0";

    private final RefreshDependenciesService refreshDependenciesService = mock(RefreshDependenciesService.class);
    private final AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    @SuppressWarnings("unchecked")
    private final Provider<Principal> principalProvider = mock(Provider.class);

    private ArtifactDependenciesRefreshResource resource;

    @BeforeEach
    public void setUp()
    {
        doNothing().when(authorisationProvider).authorise(any(), any());
        resource = new ArtifactDependenciesRefreshResource(refreshDependenciesService, authorisationProvider, principalProvider);
    }

    @Test
    public void canCreateResource()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetResourceName()
    {
        Assertions.assertEquals(ArtifactDependenciesRefreshResource.ARTIFACTS_RESOURCE, "ArtifactsRefresh");
    }

    @Test
    public void canUpdateTransitiveDependencies()
    {
        StoreProjectVersionData expectedResult = new StoreProjectVersionData(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
        when(refreshDependenciesService.updateTransitiveDependencies(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID)).thenReturn(expectedResult);

        StoreProjectVersionData result = resource.updateTransitiveDependencies(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(TEST_GROUP_ID, result.getGroupId());
        Assertions.assertEquals(TEST_ARTIFACT_ID, result.getArtifactId());
        Assertions.assertEquals(TEST_VERSION_ID, result.getVersionId());
        verify(refreshDependenciesService).updateTransitiveDependencies(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
    }
}
