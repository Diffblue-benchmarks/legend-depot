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

package org.finos.legend.depot.core.services.authorisation.resources;

import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.core.services.authorisation.BasicAuthorisationProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;

public class TestAuthorisedResource
{
    private static class ConcreteAuthorisedResource extends AuthorisedResource
    {
        public ConcreteAuthorisedResource(AuthorisationProvider authorisationProvider, Provider<Principal> principalProvider)
        {
            super(authorisationProvider, principalProvider);
        }

        @Override
        protected String getResourceName()
        {
            return "admin";
        }
    }

    @Test
    public void canCreateAuthorisedResource()
    {
        AuthorisationProvider provider = new BasicAuthorisationProvider();
        Provider<Principal> principalProvider = () -> () -> "test";
        ConcreteAuthorisedResource resource = new ConcreteAuthorisedResource(provider, principalProvider);
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canValidateUser()
    {
        AuthorisationProvider provider = new BasicAuthorisationProvider();
        Provider<Principal> principalProvider = () -> () -> "test";
        ConcreteAuthorisedResource resource = new ConcreteAuthorisedResource(provider, principalProvider);
        resource.validateUser();
        Assertions.assertTrue(true);
    }

    @Test
    public void failValidateUnauthorisedUser()
    {
        AuthorisationProvider provider = new BasicAuthorisationProvider();
        Provider<Principal> principalProvider = () -> () -> "notauthorised";
        ConcreteAuthorisedResource resource = new ConcreteAuthorisedResource(provider, principalProvider);
        Assertions.assertThrows(SecurityException.class, () -> resource.validateUser());
    }
}
