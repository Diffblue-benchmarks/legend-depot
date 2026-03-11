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
    public void canCreateModule()
    {
        GenerationsResourcesModule module = new GenerationsResourcesModule();
        Assertions.assertNotNull(module);
        Assertions.assertTrue(module instanceof PrivateModule);
    }

    @Test
    public void configureMethodExists() throws Exception
    {
        Method configureMethod = GenerationsResourcesModule.class.getDeclaredMethod("configure");
        Assertions.assertNotNull(configureMethod);
        Assertions.assertEquals(void.class, configureMethod.getReturnType());
        Assertions.assertEquals(0, configureMethod.getParameterCount());
    }

    @Test
    public void moduleReferencesExpectedResourceClasses() throws Exception
    {
        ClassLoader cl = GenerationsResourcesModule.class.getClassLoader();
        Class<?> resourceClass = cl.loadClass(FileGenerationsResource.class.getName());

        Assertions.assertNotNull(resourceClass);
        Assertions.assertEquals("FileGenerationsResource", resourceClass.getSimpleName());
    }

    @Test
    public void canRegisterResourceMetrics()
    {
        GenerationsResourcesModule module = new GenerationsResourcesModule();
        PrometheusMetricsHandler metricsHandler = mock(PrometheusMetricsHandler.class);

        boolean result = module.registerResourceMetrics(metricsHandler);

        Assertions.assertTrue(result);
        verify(metricsHandler).registerResourceSummaries(FileGenerationsResource.class);
    }
}
