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

package org.finos.legend.depot.store.resources.versions;

import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ManageProjectsVersionsResourceTest
{
    protected ManageProjectsService manageProjectsService;
    protected AuthorisationProvider authorisationProvider;
    protected Provider<Principal> principalProvider;
    protected ManageProjectsVersionsResource resource;

    @BeforeEach
    public void setUp()
    {
        manageProjectsService = mock(ManageProjectsService.class);
        authorisationProvider = mock(AuthorisationProvider.class);
        principalProvider = mock(Provider.class);
    }

    @Test
    public void canConstruct()
    {
        resource = new ManageProjectsVersionsResource(manageProjectsService, authorisationProvider, principalProvider);

        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetResourceName()
    {
        resource = new ManageProjectsVersionsResource(manageProjectsService, authorisationProvider, principalProvider);

        String resourceName = resource.getResourceName();

        Assertions.assertNotNull(resourceName);
        Assertions.assertEquals("Versions", resourceName);
    }

    @Test
    public void canFindProjectVersionWithNullExcluded()
    {
        resource = new ManageProjectsVersionsResource(manageProjectsService, authorisationProvider, principalProvider);
        List<StoreProjectVersionData> expectedVersions = Arrays.asList(
                new StoreProjectVersionData("org.example", "artifact1", "1.0.0"),
                new StoreProjectVersionData("org.example", "artifact2", "2.0.0")
        );
        when(manageProjectsService.findVersion(null)).thenReturn(expectedVersions);

        List<StoreProjectVersionData> versions = resource.findProjectVersion(null);

        Assertions.assertNotNull(versions);
        Assertions.assertEquals(expectedVersions, versions);
        verify(authorisationProvider).authorise(eq(principalProvider), eq("Versions"));
        verify(manageProjectsService).findVersion(null);
    }

    @Test
    public void canFindProjectVersionWithTrueExcluded()
    {
        resource = new ManageProjectsVersionsResource(manageProjectsService, authorisationProvider, principalProvider);
        List<StoreProjectVersionData> expectedVersions = Arrays.asList(
                new StoreProjectVersionData("org.example", "artifact1", "1.0.0")
        );
        when(manageProjectsService.findVersion(true)).thenReturn(expectedVersions);

        List<StoreProjectVersionData> versions = resource.findProjectVersion(true);

        Assertions.assertNotNull(versions);
        Assertions.assertEquals(expectedVersions, versions);
        verify(authorisationProvider).authorise(eq(principalProvider), eq("Versions"));
        verify(manageProjectsService).findVersion(true);
    }

    @Test
    public void canFindProjectVersionWithFalseExcluded()
    {
        resource = new ManageProjectsVersionsResource(manageProjectsService, authorisationProvider, principalProvider);
        List<StoreProjectVersionData> expectedVersions = Arrays.asList(
                new StoreProjectVersionData("org.example", "artifact2", "2.0.0")
        );
        when(manageProjectsService.findVersion(false)).thenReturn(expectedVersions);

        List<StoreProjectVersionData> versions = resource.findProjectVersion(false);

        Assertions.assertNotNull(versions);
        Assertions.assertEquals(expectedVersions, versions);
        verify(authorisationProvider).authorise(eq(principalProvider), eq("Versions"));
        verify(manageProjectsService).findVersion(false);
    }

    @Test
    public void canExcludeProjectVersion()
    {
        resource = new ManageProjectsVersionsResource(manageProjectsService, authorisationProvider, principalProvider);
        String groupId = "org.example";
        String artifactId = "artifact1";
        String versionId = "1.0.0";
        String exclusionReason = "Security vulnerability";
        StoreProjectVersionData expectedResult = new StoreProjectVersionData(groupId, artifactId, versionId);
        when(manageProjectsService.excludeProjectVersion(eq(groupId), eq(artifactId), eq(versionId), eq(exclusionReason))).thenReturn(expectedResult);

        StoreProjectVersionData result = resource.excludeProjectVersion(groupId, artifactId, versionId, exclusionReason);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
        verify(manageProjectsService).excludeProjectVersion(groupId, artifactId, versionId, exclusionReason);
    }
}
