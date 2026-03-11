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

package org.finos.legend.depot.server.resources;

import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.resources.versions.ManageProjectsVersionsResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class TestManageProjectsVersionsResource
{
    private final ManageProjectsService manageProjectsService = mock(ManageProjectsService.class);
    private final AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    private final Provider<Principal> principalProvider = mock(Provider.class);
    private ManageProjectsVersionsResource resource;

    @BeforeEach
    public void setUp()
    {
        doNothing().when(authorisationProvider).authorise(any(), any());
        resource = new ManageProjectsVersionsResource(manageProjectsService, authorisationProvider, principalProvider);
    }

    @Test
    public void canGetResourceName()
    {
        Assertions.assertEquals("Versions", ManageProjectsVersionsResource.PROJECTS_VERSIONS_RESOURCE);
    }

    @Test
    public void canFindProjectVersions()
    {
        StoreProjectVersionData version1 = new StoreProjectVersionData("examples.metadata", "test", "1.0.0");
        StoreProjectVersionData version2 = new StoreProjectVersionData("examples.metadata", "test", "2.0.0");
        when(manageProjectsService.findVersion(null)).thenReturn(Arrays.asList(version1, version2));

        List<StoreProjectVersionData> result = resource.findProjectVersion(null);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void canFindProjectVersionsWithExcludedFilter()
    {
        when(manageProjectsService.findVersion(true)).thenReturn(Collections.emptyList());

        List<StoreProjectVersionData> result = resource.findProjectVersion(true);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void canExcludeProjectVersion()
    {
        StoreProjectVersionData excluded = new StoreProjectVersionData("examples.metadata", "test", "1.0.0");
        when(manageProjectsService.excludeProjectVersion("examples.metadata", "test", "1.0.0", "deprecated"))
                .thenReturn(excluded);

        StoreProjectVersionData result = resource.excludeProjectVersion("examples.metadata", "test", "1.0.0", "deprecated");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("examples.metadata", result.getGroupId());
        Assertions.assertEquals("test", result.getArtifactId());
        Assertions.assertEquals("1.0.0", result.getVersionId());
    }
}
