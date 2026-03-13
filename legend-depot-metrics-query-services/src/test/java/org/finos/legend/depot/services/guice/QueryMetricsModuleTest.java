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

import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.metrics.query.InMemoryQueryMetricsRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QueryMetricsModuleTest
{
    @Test
    public void canGetQueryMetricsRegistry()
    {
        QueryMetricsModule module = new QueryMetricsModule();
        QueryMetricsRegistry registry = module.getQueryMetricsRegistry();
        Assertions.assertNotNull(registry);
        Assertions.assertTrue(registry instanceof InMemoryQueryMetricsRegistry);
    }

    @Test
    public void canGetQueryMetricsRegistryMultipleTimes()
    {
        QueryMetricsModule module = new QueryMetricsModule();
        QueryMetricsRegistry registry1 = module.getQueryMetricsRegistry();
        QueryMetricsRegistry registry2 = module.getQueryMetricsRegistry();
        Assertions.assertNotNull(registry1);
        Assertions.assertNotNull(registry2);
        Assertions.assertTrue(registry1 instanceof InMemoryQueryMetricsRegistry);
        Assertions.assertTrue(registry2 instanceof InMemoryQueryMetricsRegistry);
    }

    @Test
    public void testQueryMetricsRegistryIsNotNull()
    {
        QueryMetricsModule module = new QueryMetricsModule();
        QueryMetricsRegistry registry = module.getQueryMetricsRegistry();
        Assertions.assertNotNull(registry);
    }
}
