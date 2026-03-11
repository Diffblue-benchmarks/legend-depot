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

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.core.services.tracing.TracerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMonitoringModule
{
    @Test
    public void canInitTracerFactoryWithNullConfig()
    {
        MonitoringModule module = new MonitoringModule();
        TracerFactory result = module.initTracerFactory(null);
        Assertions.assertNotNull(result);
    }

    @Test
    public void canInitTracerFactoryWithDisabledConfig()
    {
        MonitoringModule module = new MonitoringModule();
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setEnabled(false);
        TracerFactory result = module.initTracerFactory(config);
        Assertions.assertNotNull(result);
    }

    @Test
    public void canInitialisePrometheusMetricsWithNullConfig()
    {
        MonitoringModule module = new MonitoringModule();
        PrometheusMetricsHandler result = module.initialisePrometheusMetrics(null);
        Assertions.assertNotNull(result);
    }

    @Test
    public void canInitialisePrometheusMetricsWithDisabledConfig()
    {
        MonitoringModule module = new MonitoringModule();
        PrometheusConfiguration config = new PrometheusConfiguration();
        config.setEnabled(false);
        PrometheusMetricsHandler result = module.initialisePrometheusMetrics(config);
        Assertions.assertNotNull(result);
    }

    @Test
    public void canConfigureModuleAndExposeBindings()
    {
        Injector injector = Guice.createInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(OpenTracingConfiguration.class).toInstance(new OpenTracingConfiguration());
                bind(PrometheusConfiguration.class).toInstance(new PrometheusConfiguration());
                install(new MonitoringModule());
            }
        });

        TracerFactory tracerFactory = injector.getInstance(TracerFactory.class);
        Assertions.assertNotNull(tracerFactory);

        PrometheusMetricsHandler metricsHandler = injector.getInstance(PrometheusMetricsHandler.class);
        Assertions.assertNotNull(metricsHandler);
    }
}
