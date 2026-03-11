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

package org.finos.legend.depot.services.guice;

import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

public class TestNotificationsModule
{
    @Test
    public void registerMetricsWhenEnabled()
    {
        PrometheusMetricsHandler metricsHandler = mock(PrometheusMetricsHandler.class);
        PrometheusConfiguration configuration = new PrometheusConfiguration(true, metricsHandler);

        NotificationsModule module = new NotificationsModule();
        boolean result = module.registerMetrics(configuration);

        Assertions.assertTrue(result);
        verify(metricsHandler).registerCounter("notifications", "total notifications received");
        verify(metricsHandler).registerGauge("queue_waiting", "waiting in queue");
        verify(metricsHandler).registerHistogram("notification_complete", " time to precess notification", Arrays.asList("eventPriority"));
    }

    @Test
    public void registerMetricsWhenDisabled()
    {
        PrometheusMetricsHandler metricsHandler = mock(PrometheusMetricsHandler.class);
        PrometheusConfiguration configuration = new PrometheusConfiguration(false, metricsHandler);

        NotificationsModule module = new NotificationsModule();
        boolean result = module.registerMetrics(configuration);

        Assertions.assertTrue(result);
        verifyNoInteractions(metricsHandler);
    }
}
