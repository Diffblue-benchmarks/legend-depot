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

package org.finos.legend.depot.store.resources.projects;

import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestManageProjectsResource
{
    protected ManageProjectsService projectApi;
    protected AuthorisationProvider authorisationProvider;
    protected Provider<Principal> principalProvider;
    protected ManageProjectsResource resource;

    @BeforeEach
    public void setUp()
    {
        projectApi = mock(ManageProjectsService.class);
        authorisationProvider = mock(AuthorisationProvider.class);
        principalProvider = mock(Provider.class);
        resource = new ManageProjectsResource(projectApi, authorisationProvider, principalProvider);
    }

    @Test
    public void canConstructResource()
    {
        Assertions.assertNotNull(resource);
        Assertions.assertNotNull(new ManageProjectsResource(projectApi, authorisationProvider, principalProvider));
    }

    @Test
    public void canGetResourceName()
    {
        String resourceName = resource.getResourceName();
        Assertions.assertNotNull(resourceName);
        Assertions.assertEquals("Projects", resourceName);
    }

    @Test
    public void canUpdateProject()
    {
        StoreProjectData expectedProject = new StoreProjectData("proj1", "com.example", "test-artifact", "master", "1.0.0");
        when(projectApi.createOrUpdate(any(StoreProjectData.class))).thenReturn(expectedProject);

        StoreProjectData result = resource.updateProject("proj1", "com.example", "test-artifact", "master", "1.0.0");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("proj1", result.getProjectId());
        Assertions.assertEquals("com.example", result.getGroupId());
        Assertions.assertEquals("test-artifact", result.getArtifactId());
        Assertions.assertEquals("master", result.getDefaultBranch());
        Assertions.assertEquals("1.0.0", result.getLatestVersion());
        verify(projectApi).createOrUpdate(any(StoreProjectData.class));
        verify(authorisationProvider).authorise(eq(principalProvider), eq("Projects"));
    }

    @Test
    public void canDeleteProject()
    {
        when(projectApi.delete(eq("com.example"), eq("test-artifact"))).thenReturn(1L);

        long result = resource.deleteProject("com.example", "test-artifact");

        Assertions.assertEquals(1L, result);
        verify(projectApi).delete(eq("com.example"), eq("test-artifact"));
        verify(authorisationProvider).authorise(eq(principalProvider), eq("Projects"));
    }
}
