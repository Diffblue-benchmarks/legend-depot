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

package org.finos.legend.depot.core.services.authorisation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicAuthorisationProviderTest
{
    private BasicAuthorisationProvider createProvider(Map<String, List<String>> identities)
    {
        return new BasicAuthorisationProvider(identities);
    }

    @Test
    public void testAuthoriseSuccess()
    {
        Map<String, List<String>> identities = new HashMap<>();
        identities.put("admin", Arrays.asList("alice", "bob"));
        BasicAuthorisationProvider provider = createProvider(identities);

        Principal principal = () -> "alice";
        Assertions.assertDoesNotThrow(() -> provider.authorise(() -> principal, "admin"));
    }

    @Test
    public void testAuthoriseUnknownRoleThrows()
    {
        Map<String, List<String>> identities = new HashMap<>();
        identities.put("admin", Arrays.asList("alice"));
        BasicAuthorisationProvider provider = createProvider(identities);

        Principal principal = () -> "alice";
        SecurityException ex = Assertions.assertThrows(SecurityException.class,
                () -> provider.authorise(() -> principal, "unknown-role"));
        Assertions.assertTrue(ex.getMessage().contains("Unknown role"));
    }

    @Test
    public void testAuthoriseUnauthorisedUserThrows()
    {
        Map<String, List<String>> identities = new HashMap<>();
        identities.put("admin", Arrays.asList("alice", "bob"));
        BasicAuthorisationProvider provider = createProvider(identities);

        Principal principal = () -> "charlie";
        SecurityException ex = Assertions.assertThrows(SecurityException.class,
                () -> provider.authorise(() -> principal, "admin"));
        Assertions.assertTrue(ex.getMessage().contains("not authorised"));
        Assertions.assertTrue(ex.getMessage().contains("charlie"));
    }

    @Test
    public void testAuthoriseWithEmptyUserList()
    {
        Map<String, List<String>> identities = new HashMap<>();
        identities.put("admin", Collections.emptyList());
        BasicAuthorisationProvider provider = createProvider(identities);

        Principal principal = () -> "alice";
        Assertions.assertThrows(SecurityException.class,
                () -> provider.authorise(() -> principal, "admin"));
    }

    @Test
    public void testAuthoriseMultipleRoles()
    {
        Map<String, List<String>> identities = new HashMap<>();
        identities.put("admin", Arrays.asList("alice"));
        identities.put("viewer", Arrays.asList("bob", "charlie"));
        BasicAuthorisationProvider provider = createProvider(identities);

        Principal alice = () -> "alice";
        Principal bob = () -> "bob";

        Assertions.assertDoesNotThrow(() -> provider.authorise(() -> alice, "admin"));
        Assertions.assertDoesNotThrow(() -> provider.authorise(() -> bob, "viewer"));
        Assertions.assertThrows(SecurityException.class,
                () -> provider.authorise(() -> bob, "admin"));
    }
}
