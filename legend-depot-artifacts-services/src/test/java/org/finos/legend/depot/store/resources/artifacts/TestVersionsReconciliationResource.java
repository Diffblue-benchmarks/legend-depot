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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestVersionsReconciliationResource
{
    private final VersionsReconciliationService reconciliationService = mock(VersionsReconciliationService.class);
    private final AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    private final Provider<Principal> principalProvider = mock(Provider.class);
    private VersionsReconciliationResource resource;

    @BeforeEach
    public void setup()
    {
        resource = new VersionsReconciliationResource(reconciliationService, authorisationProvider, principalProvider);
    }

    @Test
    public void canCreateResource()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void testGetResourceName()
    {
        Assertions.assertEquals("Repository", resource.getResourceName());
    }

    @Test
    public void canGetVersionMissMatches()
    {
        List<VersionMismatch> expected = Arrays.asList(
                new VersionMismatch("PROD-A", "examples.metadata", "test1", Collections.singletonList("2.3.1"), Collections.emptyList())
        );
        when(reconciliationService.findVersionsMismatches()).thenReturn(expected);

        List<VersionMismatch> result = resource.getVersionMissMatches();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("PROD-A", result.get(0).projectId);
    }

    @Test
    public void canGetEmptyVersionMissMatches()
    {
        when(reconciliationService.findVersionsMismatches()).thenReturn(Collections.emptyList());

        List<VersionMismatch> result = resource.getVersionMissMatches();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }
}
