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
    public void testDefaultsDisabled()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        Assertions.assertFalse(config.isEnabled());
        Assertions.assertNull(config.getOpenTracingUri());
        Assertions.assertNull(config.getServiceName());
        Assertions.assertNull(config.getTracerProvider());
    }

    @Test
    public void testSetEnabled()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setEnabled(true);
        Assertions.assertTrue(config.isEnabled());
    }

    @Test
    public void testSetOpenTracingUri()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri("http://localhost:9411");
        Assertions.assertEquals("http://localhost:9411", config.getOpenTracingUri());
    }

    @Test
    public void testSetServiceName()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setServiceName("my-service");
        Assertions.assertEquals("my-service", config.getServiceName());
    }
}
