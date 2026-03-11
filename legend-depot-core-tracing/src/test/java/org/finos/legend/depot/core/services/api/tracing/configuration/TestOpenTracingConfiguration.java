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

package org.finos.legend.depot.core.services.api.tracing.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestOpenTracingConfiguration
{
    @Test
    public void canGetAndSetOpenTracingUri()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        Assertions.assertNull(config.getOpenTracingUri());

        config.setOpenTracingUri("http://localhost:9411");
        Assertions.assertEquals("http://localhost:9411", config.getOpenTracingUri());
    }

    @Test
    public void canGetAndSetServiceName()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        Assertions.assertNull(config.getServiceName());

        config.setServiceName("test-service");
        Assertions.assertEquals("test-service", config.getServiceName());
    }

    @Test
    public void canGetAndSetEnabled()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        Assertions.assertFalse(config.isEnabled());

        config.setEnabled(true);
        Assertions.assertTrue(config.isEnabled());
    }

    @Test
    public void canGetAndSetTracerProvider()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        Assertions.assertNull(config.getTracerProvider());

        TracerProvider provider = configuration -> null;
        config.setTracerProvider(provider);
        Assertions.assertEquals(provider, config.getTracerProvider());
    }
}
