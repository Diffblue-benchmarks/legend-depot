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
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.resources.projects.ManageProjectsResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestManageProjectsResource
{
    private final ManageProjectsService manageProjectsService = mock(ManageProjectsService.class);
    private final AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    private final Provider<Principal> principalProvider = mock(Provider.class);
    private ManageProjectsResource resource;

    @BeforeEach
    public void setUp()
    {
        doNothing().when(authorisationProvider).authorise(any(), any());
        resource = new ManageProjectsResource(manageProjectsService, authorisationProvider, principalProvider);
    }

    @Test
    public void canGetResourceName()
    {
        Assertions.assertEquals("Projects", ManageProjectsResource.PROJECTS_RESOURCE);
    }

    @Test
    public void canUpdateProject()
    {
        StoreProjectData expected = new StoreProjectData("PROD-1", "examples.metadata", "test", "master", "1.0.0");
        when(manageProjectsService.createOrUpdate(any(StoreProjectData.class))).thenReturn(expected);

        StoreProjectData result = resource.updateProject("PROD-1", "examples.metadata", "test", "master", "1.0.0");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("PROD-1", result.getProjectId());
    }

    @Test
    public void canDeleteProject()
    {
        when(manageProjectsService.delete("examples.metadata", "test")).thenReturn(1L);

        long result = resource.deleteProject("examples.metadata", "test");

        Assertions.assertEquals(1L, result);
    }
}
