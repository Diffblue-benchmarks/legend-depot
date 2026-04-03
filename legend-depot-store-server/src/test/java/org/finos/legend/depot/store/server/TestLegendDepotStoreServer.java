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

package org.finos.legend.depot.store.server;

import com.google.inject.Module;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestLegendDepotStoreServer
{
    @Test
    public void testConstructor()
    {
        LegendDepotStoreServer server = new LegendDepotStoreServer();
        Assertions.assertNotNull(server);
    }

    @Test
    public void testGetServerModulesNotEmpty()
    {
        LegendDepotStoreServer server = new LegendDepotStoreServer();
        List<Module> modules = server.getServerModules();
        Assertions.assertNotNull(modules);
        Assertions.assertFalse(modules.isEmpty());
        Assertions.assertEquals(36, modules.size());
    }

    @Test
    @Disabled("Requires full Dropwizard Bootstrap infrastructure to test configureObjectMapper")
    public void testConfigureObjectMapper()
    {
    }

    @Test
    @Disabled("Requires full Dropwizard JerseyEnvironment infrastructure to test registerJacksonJsonProvider")
    public void testRegisterJacksonJsonProvider()
    {
    }

    @Test
    @Disabled("Requires full Dropwizard Environment infrastructure to test initialiseCors")
    public void testInitialiseCors()
    {
    }

    @Test
    @Disabled("Invoking main would attempt to start a full server process")
    public void testMain()
    {
    }
}
