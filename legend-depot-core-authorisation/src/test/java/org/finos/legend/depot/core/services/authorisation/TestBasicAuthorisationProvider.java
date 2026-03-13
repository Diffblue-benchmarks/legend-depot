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

import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBasicAuthorisationProvider
{
    @Test
    public void canAuthorise()
    {
        AuthorisationProvider provider = new BasicAuthorisationProvider();
        provider.authorise(() -> () -> "test","admin");
        Assertions.assertTrue(true);
    }

    @Test
    public void failAuthorise()
    {
        AuthorisationProvider provider = new BasicAuthorisationProvider();
        Assertions.assertThrows(SecurityException.class, () -> provider.authorise(() -> () -> "test", "notknownrole"));
    }

    @Test
    public void userFailAuthorise()
    {
        AuthorisationProvider provider = new BasicAuthorisationProvider();
        Assertions.assertThrows(SecurityException.class, () -> provider.authorise(() -> () -> "notauthorised", "admin"));
    }

    @Test
    public void canCreateWithMap()
    {
        Map<String, List<String>> authMap = new HashMap<>();
        authMap.put("admin", Arrays.asList("user1", "user2"));
        BasicAuthorisationProvider provider = new BasicAuthorisationProvider(authMap);
        Assertions.assertNotNull(provider);
    }

    @Test
    public void canAuthoriseWithMapConstructor()
    {
        Map<String, List<String>> authMap = new HashMap<>();
        authMap.put("admin", Arrays.asList("user1", "user2"));
        authMap.put("viewer", Collections.singletonList("user3"));
        BasicAuthorisationProvider provider = new BasicAuthorisationProvider(authMap);
        provider.authorise(() -> () -> "user1", "admin");
        provider.authorise(() -> () -> "user2", "admin");
        provider.authorise(() -> () -> "user3", "viewer");
        Assertions.assertTrue(true);
    }

    @Test
    public void failAuthoriseUnknownRoleWithMapConstructor()
    {
        Map<String, List<String>> authMap = new HashMap<>();
        authMap.put("admin", Arrays.asList("user1", "user2"));
        BasicAuthorisationProvider provider = new BasicAuthorisationProvider(authMap);
        Assertions.assertThrows(SecurityException.class, () -> provider.authorise(() -> () -> "user1", "unknownRole"));
    }

    @Test
    public void failAuthoriseUnauthorisedUserWithMapConstructor()
    {
        Map<String, List<String>> authMap = new HashMap<>();
        authMap.put("admin", Arrays.asList("user1", "user2"));
        BasicAuthorisationProvider provider = new BasicAuthorisationProvider(authMap);
        Assertions.assertThrows(SecurityException.class, () -> provider.authorise(() -> () -> "user3", "admin"));
    }

    @Test
    public void canAuthoriseWithEmptyRole()
    {
        Map<String, List<String>> authMap = new HashMap<>();
        authMap.put("", Arrays.asList("user1"));
        BasicAuthorisationProvider provider = new BasicAuthorisationProvider(authMap);
        provider.authorise(() -> () -> "user1", "");
        Assertions.assertTrue(true);
    }

    @Test
    public void canAuthoriseMultipleUsersInRole()
    {
        Map<String, List<String>> authMap = new HashMap<>();
        authMap.put("developer", Arrays.asList("alice", "bob", "charlie"));
        BasicAuthorisationProvider provider = new BasicAuthorisationProvider(authMap);
        provider.authorise(() -> () -> "alice", "developer");
        provider.authorise(() -> () -> "bob", "developer");
        provider.authorise(() -> () -> "charlie", "developer");
        Assertions.assertTrue(true);
    }
}
