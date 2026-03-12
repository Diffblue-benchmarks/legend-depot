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

package org.finos.legend.depot.core.services.api.metrics.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPrometheusConfiguration
{
    @Test
    public void testDefaultsDisabled()
    {
        PrometheusConfiguration config = new PrometheusConfiguration();
        Assertions.assertFalse(config.isEnabled());
        Assertions.assertNull(config.getMetricsHandler());
    }

    @Test
    public void testSetEnabled()
    {
        PrometheusConfiguration config = new PrometheusConfiguration();
        config.setEnabled(true);
        Assertions.assertTrue(config.isEnabled());
    }

    @Test
    public void testConstructorWithParams()
    {
        PrometheusConfiguration config = new PrometheusConfiguration(true, null);
        Assertions.assertTrue(config.isEnabled());
        Assertions.assertNull(config.getMetricsHandler());
    }
}
