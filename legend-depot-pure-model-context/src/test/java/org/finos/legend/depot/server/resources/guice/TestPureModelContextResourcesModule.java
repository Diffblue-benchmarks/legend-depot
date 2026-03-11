//  Copyright 2022 Goldman Sachs
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

package org.finos.legend.depot.server.resources.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.server.resources.pure.model.context.PureModelContextResource;
import org.finos.legend.depot.services.api.pure.model.context.PureModelContextService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestPureModelContextResourcesModule
{
    @Test
    public void canRegisterResourceMetrics()
    {
        PureModelContextResourcesModule module = new PureModelContextResourcesModule();
        PrometheusMetricsHandler metricsHandler = mock(PrometheusMetricsHandler.class);

        boolean result = module.registerResourceMetrics(metricsHandler);

        Assertions.assertTrue(result);
        verify(metricsHandler).registerResourceSummaries(PureModelContextResource.class);
    }

    @Test
    public void canConfigureModule()
    {
        PureModelContextResourcesModule module = new PureModelContextResourcesModule();
        PureModelContextService mockService = mock(PureModelContextService.class);
        PrometheusMetricsHandler mockMetrics = mock(PrometheusMetricsHandler.class);

        Injector injector = Guice.createInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(PureModelContextService.class).toInstance(mockService);
                bind(PrometheusMetricsHandler.class).toInstance(mockMetrics);
                install(module);
            }
        });

        PureModelContextResource resource = injector.getInstance(PureModelContextResource.class);
        Assertions.assertNotNull(resource);
    }
}
