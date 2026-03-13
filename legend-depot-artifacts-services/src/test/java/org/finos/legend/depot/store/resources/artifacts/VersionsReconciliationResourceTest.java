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
import org.finos.legend.depot.domain.version.VersionMismatch;
import org.finos.legend.depot.services.api.artifacts.reconciliation.VersionsReconciliationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VersionsReconciliationResourceTest
{
    private VersionsReconciliationService mockReconciliationService;
    private AuthorisationProvider mockAuthorisationProvider;
    private Provider<Principal> mockPrincipalProvider;
    private VersionsReconciliationResource resource;

    @BeforeEach
    public void setUp()
    {
        mockReconciliationService = mock(VersionsReconciliationService.class);
        mockAuthorisationProvider = mock(AuthorisationProvider.class);
        mockPrincipalProvider = mock(Provider.class);
        resource = new VersionsReconciliationResource(mockReconciliationService, mockAuthorisationProvider, mockPrincipalProvider);
    }

    @Test
    public void canConstructVersionsReconciliationResource()
    {
        VersionsReconciliationResource newResource = new VersionsReconciliationResource(mockReconciliationService, mockAuthorisationProvider, mockPrincipalProvider);
        assertNotNull(newResource);
    }

    @Test
    public void canGetResourceName()
    {
        String resourceName = resource.getResourceName();
        assertNotNull(resourceName);
        assertEquals("Repository", resourceName);
    }

    @Test
    public void canGetVersionMissMatchesWithEmptyList()
    {
        when(mockReconciliationService.findVersionsMismatches()).thenReturn(Collections.emptyList());

        List<VersionMismatch> mismatches = resource.getVersionMissMatches();

        assertNotNull(mismatches);
        assertEquals(0, mismatches.size());
    }

    @Test
    public void canGetVersionMissMatchesWithResults()
    {
        VersionMismatch mismatch1 = new VersionMismatch("project-1", "test.group", "artifact-1",
                Collections.emptyList(), Collections.emptyList());

        VersionMismatch mismatch2 = new VersionMismatch("project-2", "test.group", "artifact-2",
                Collections.emptyList(), Collections.emptyList());

        List<VersionMismatch> expectedMismatches = Arrays.asList(mismatch1, mismatch2);
        when(mockReconciliationService.findVersionsMismatches()).thenReturn(expectedMismatches);

        List<VersionMismatch> mismatches = resource.getVersionMissMatches();

        assertNotNull(mismatches);
        assertEquals(2, mismatches.size());
        assertEquals("project-1", mismatches.get(0).projectId);
        assertEquals("project-2", mismatches.get(1).projectId);
    }
}
