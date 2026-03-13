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

import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.server.resources.dependencies.DependenciesResource;
import org.finos.legend.depot.server.resources.projects.ProjectsResource;
import org.finos.legend.depot.server.resources.versions.ProjectsVersionsResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class TestCoreDataResourcesModule
{
    @Test
    public void canCreateModule()
    {
        CoreDataResourcesModule module = new CoreDataResourcesModule();
        Assertions.assertNotNull(module);
    }

    @Test
    public void canRegisterResourceMetrics()
    {
        PrometheusMetricsHandler metricsHandler = mock(PrometheusMetricsHandler.class);
        CoreDataResourcesModule module = new CoreDataResourcesModule();

        boolean result = module.registerResourceMetrics(metricsHandler);

        Assertions.assertTrue(result);
        verify(metricsHandler, times(1)).registerResourceSummaries(ProjectsResource.class);
        verify(metricsHandler, times(1)).registerResourceSummaries(ProjectsVersionsResource.class);
        verify(metricsHandler, times(1)).registerResourceSummaries(DependenciesResource.class);
    }
}
