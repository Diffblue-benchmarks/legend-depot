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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;

public class TestAuthorisedResource
{
    private TestableAuthorisationProvider authorisationProvider;
    private Provider<Principal> principalProvider;
    private TestableAuthorisedResource resource;

    @BeforeEach
    public void setup()
    {
        authorisationProvider = new TestableAuthorisationProvider();
        principalProvider = () -> () -> "testUser";
        resource = new TestableAuthorisedResource(authorisationProvider, principalProvider);
    }

    @Test
    public void canValidateUser()
    {
        resource.validateUser();
        Assertions.assertTrue(authorisationProvider.authoriseCalled);
        Assertions.assertEquals(principalProvider, authorisationProvider.receivedPrincipalProvider);
        Assertions.assertEquals("testResource", authorisationProvider.receivedResourceName);
    }

    private static class TestableAuthorisedResource extends AuthorisedResource
    {
        public TestableAuthorisedResource(AuthorisationProvider authorisationProvider, Provider<Principal> principalProvider)
        {
            super(authorisationProvider, principalProvider);
        }

        @Override
        protected String getResourceName()
        {
            return "testResource";
        }
    }

    private static class TestableAuthorisationProvider implements AuthorisationProvider
    {
        boolean authoriseCalled = false;
        Provider<Principal> receivedPrincipalProvider;
        String receivedResourceName;

        @Override
        public void authorise(Provider<Principal> principalProvider, String resourceName)
        {
            this.authoriseCalled = true;
            this.receivedPrincipalProvider = principalProvider;
            this.receivedResourceName = resourceName;
        }
    }
}
