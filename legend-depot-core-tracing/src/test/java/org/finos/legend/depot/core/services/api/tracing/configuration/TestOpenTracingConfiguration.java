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
    OpenTracingConfiguration config = new OpenTracingConfiguration();

    @Test
    public void testGetSetOpenTracingUri()
    {
        Assertions.assertNull(config.getOpenTracingUri());
        config.setOpenTracingUri("http://tracing:9411");
        Assertions.assertEquals("http://tracing:9411", config.getOpenTracingUri());
    }

    @Test
    public void testGetSetServiceName()
    {
        Assertions.assertNull(config.getServiceName());
        config.setServiceName("my-service");
        Assertions.assertEquals("my-service", config.getServiceName());
    }

    @Test
    public void testIsSetEnabled()
    {
        Assertions.assertEquals(false, config.isEnabled());
        config.setEnabled(true);
        Assertions.assertEquals(true, config.isEnabled());
    }

    @Test
    public void testGetSetTracerProvider()
    {
        Assertions.assertNull(config.getTracerProvider());
        TracerProvider provider = configuration -> null;
        config.setTracerProvider(provider);
        Assertions.assertNotNull(config.getTracerProvider());
        Assertions.assertEquals(provider, config.getTracerProvider());
    }
}
