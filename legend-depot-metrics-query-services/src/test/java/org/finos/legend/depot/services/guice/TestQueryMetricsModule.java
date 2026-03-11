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

import com.google.inject.Guice;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsService;
import org.finos.legend.depot.services.metrics.query.InMemoryQueryMetricsRegistry;
import org.finos.legend.depot.services.metrics.query.QueryMetricsServiceImpl;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.metrics.query.QueryMetricsMongo;
import org.finos.legend.depot.store.api.metrics.query.QueryMetrics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestQueryMetricsModule extends TestStoreMongo
{
    @Test
    public void canCreateInjectorWithModule()
    {
        Injector injector = Guice.createInjector(
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(QueryMetrics.class).toInstance(new QueryMetricsMongo(mongoProvider));
                    }
                },
                new QueryMetricsModule()
        );

        QueryMetricsService service = injector.getInstance(QueryMetricsService.class);
        Assertions.assertNotNull(service);
        Assertions.assertTrue(service instanceof QueryMetricsServiceImpl);
    }

    @Test
    public void canGetQueryMetricsRegistryFromModule()
    {
        Injector injector = Guice.createInjector(
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(QueryMetrics.class).toInstance(new QueryMetricsMongo(mongoProvider));
                    }
                },
                new QueryMetricsModule()
        );

        QueryMetricsRegistry registry = injector.getInstance(Key.get(QueryMetricsRegistry.class, Names.named("queryMetricsRegistry")));
        Assertions.assertNotNull(registry);
        Assertions.assertTrue(registry instanceof InMemoryQueryMetricsRegistry);
    }

    @Test
    public void registryIsSingleton()
    {
        Injector injector = Guice.createInjector(
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(QueryMetrics.class).toInstance(new QueryMetricsMongo(mongoProvider));
                    }
                },
                new QueryMetricsModule()
        );

        Key<QueryMetricsRegistry> key = Key.get(QueryMetricsRegistry.class, Names.named("queryMetricsRegistry"));
        QueryMetricsRegistry registry1 = injector.getInstance(key);
        QueryMetricsRegistry registry2 = injector.getInstance(key);
        Assertions.assertSame(registry1, registry2);
    }
}
