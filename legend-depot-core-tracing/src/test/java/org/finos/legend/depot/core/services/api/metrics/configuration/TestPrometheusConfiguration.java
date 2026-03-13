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

import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.metrics.DepotPrometheusMetricsHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPrometheusConfiguration
{
    @Test
    public void canCreateConfigurationWithNoArgConstructor()
    {
        PrometheusConfiguration config = new PrometheusConfiguration();
        Assertions.assertFalse(config.isEnabled());
        Assertions.assertNull(config.getMetricsHandler());
    }

    @Test
    public void canGetAndSetEnabled()
    {
        PrometheusConfiguration config = new PrometheusConfiguration();
        Assertions.assertFalse(config.isEnabled());

        config.setEnabled(true);
        Assertions.assertTrue(config.isEnabled());

        config.setEnabled(false);
        Assertions.assertFalse(config.isEnabled());
    }

    @Test
    public void canGetAndSetMetricsHandler()
    {
        PrometheusConfiguration config = new PrometheusConfiguration();
        Assertions.assertNull(config.getMetricsHandler());

        PrometheusMetricsHandler handler = new DepotPrometheusMetricsHandler("test");
        config.setMetricsHandler(handler);
        Assertions.assertEquals(handler, config.getMetricsHandler());
    }
}
