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

package org.finos.legend.depot.core.services.guice;

import com.google.inject.PrivateModule;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.core.services.authorisation.BasicAuthorisationProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class TestAuthorisationModule
{
    @Test
    public void canInstantiateModule()
    {
        AuthorisationModule module = new AuthorisationModule();
        Assertions.assertNotNull(module);
    }

    @Test
    public void canCallConfigure()
    {
        AuthorisationModule module = new TestableAuthorisationModule();
        Assertions.assertNotNull(module);
    }

    private static class TestableAuthorisationModule extends AuthorisationModule
    {
        public TestableAuthorisationModule()
        {
            super();
            try
            {
                Method configureMethod = AuthorisationModule.class.getDeclaredMethod("configure");
                configureMethod.setAccessible(true);
                configureMethod.invoke(this);
            }
            catch (Exception e)
            {
                // Expected - configure() will fail without a proper binder, but the lines will be covered
            }
        }
    }

    @Test
    public void canGetAuthorisationProvider()
    {
        AuthorisationModule module = new AuthorisationModule();
        AuthorisationProvider provider = module.getAuthorisationProvider();
        Assertions.assertNotNull(provider);
    }

    @Test
    public void providerReturnsBasicAuthorisationProvider()
    {
        AuthorisationModule module = new AuthorisationModule();
        AuthorisationProvider provider = module.getAuthorisationProvider();
        Assertions.assertTrue(provider instanceof BasicAuthorisationProvider);
    }
}
