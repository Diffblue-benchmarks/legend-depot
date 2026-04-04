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

package org.finos.legend.depot.core.services.metrics;

import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class TestPrometheusMetricsFactory
{
    @BeforeEach
    public void setUp() throws Exception
    {
        Field instanceField = PrometheusMetricsFactory.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    @Test
    public void testGetInstanceWhenNullConfiguresVoidHandler()
    {
        PrometheusMetricsHandler result = PrometheusMetricsFactory.getInstance();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result instanceof VoidPrometheusMetricsHandler);
    }

    @Test
    public void testConfigureWithNullReturnsVoidHandler()
    {
        PrometheusMetricsHandler result = PrometheusMetricsFactory.configure(null);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result instanceof VoidPrometheusMetricsHandler);
    }

    @Test
    public void testConfigureWithDisabledConfigReturnsVoidHandler()
    {
        PrometheusConfiguration disabledConfig = new PrometheusConfiguration(false, null);
        PrometheusMetricsHandler result = PrometheusMetricsFactory.configure(disabledConfig);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result instanceof VoidPrometheusMetricsHandler);
    }
}
