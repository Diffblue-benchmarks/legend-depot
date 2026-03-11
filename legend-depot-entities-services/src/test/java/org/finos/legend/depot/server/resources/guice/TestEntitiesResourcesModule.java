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
import org.finos.legend.depot.server.resources.entities.EntitiesDependenciesResource;
import org.finos.legend.depot.server.resources.entities.EntitiesResource;
import org.finos.legend.depot.server.resources.entities.EntityClassifierResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestEntitiesResourcesModule
{
    @Test
    public void canRegisterResourceMetrics()
    {
        PrometheusMetricsHandler metricsHandler = mock(PrometheusMetricsHandler.class);

        EntitiesResourcesModule module = new EntitiesResourcesModule();
        boolean result = module.registerResourceMetrics(metricsHandler);

        Assertions.assertTrue(result);
        verify(metricsHandler).registerResourceSummaries(EntitiesResource.class);
        verify(metricsHandler).registerResourceSummaries(EntitiesDependenciesResource.class);
        verify(metricsHandler).registerResourceSummaries(EntityClassifierResource.class);
    }
}
