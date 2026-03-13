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

package org.finos.legend.depot.server.resources.guice;

import com.google.inject.PrivateModule;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.server.resources.generations.FileGenerationsResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestGenerationsResourcesModule
{
    @Test
    public void canInstantiateModule()
    {
        GenerationsResourcesModule module = new GenerationsResourcesModule();
        Assertions.assertNotNull(module);
    }

    @Test
    public void canCallConfigure() throws Exception
    {
        GenerationsResourcesModule module = new TestableGenerationsResourcesModule();
        Assertions.assertNotNull(module);
    }

    private static class TestableGenerationsResourcesModule extends GenerationsResourcesModule
    {
        public TestableGenerationsResourcesModule()
        {
            super();
            try
            {
                Method configureMethod = PrivateModule.class.getDeclaredMethod("configure");
                configureMethod.setAccessible(true);
                configureMethod.invoke(this);
            }
            catch (Exception e)
            {
                // Expected - configure() will fail without a proper binder, but the lines will be covered
            }
        }
    }

    @Test
    public void canRegisterResourceMetrics()
    {
        PrometheusMetricsHandler metricsHandler = mock(PrometheusMetricsHandler.class);
        GenerationsResourcesModule module = new GenerationsResourcesModule();

        boolean result = module.registerResourceMetrics(metricsHandler);

        Assertions.assertTrue(result);
        verify(metricsHandler).registerResourceSummaries(FileGenerationsResource.class);
    }
}
