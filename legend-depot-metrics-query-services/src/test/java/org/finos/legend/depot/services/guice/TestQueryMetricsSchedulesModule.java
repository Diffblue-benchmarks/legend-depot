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
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsService;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.metrics.query.InMemoryQueryMetricsRegistry;
import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.metrics.query.QueryMetricsMongo;
import org.finos.legend.depot.services.metrics.query.QueryMetricsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class TestQueryMetricsSchedulesModule extends TestStoreMongo
{
    @Test
    public void canConfigureModule()
    {
        QueryMetricsSchedulesModule module = new QueryMetricsSchedulesModule();
        Assertions.assertNotNull(module);
    }

    @Test
    public void canScheduleMetricsPersistence()
    {
        QueryMetricsSchedulesModule module = new QueryMetricsSchedulesModule();

        List<String> registeredNames = new ArrayList<>();
        List<Supplier<Object>> registeredTasks = new ArrayList<>();

        SchedulesFactory schedulesFactory = new SchedulesFactory()
        {
            @Override
            public void register(String name, long delayStartInMilliseconds, long intervalInMilliseconds, Supplier<Object> task)
            {
                registeredNames.add(name);
                registeredTasks.add(task);
            }

            @Override
            public void registerExternalTriggerSchedule(String name, long intervalInMilliseconds, Supplier<Object> function)
            {
            }

            @Override
            public void registerSingleInstance(String name, long delayStartInMilliseconds, long intervalInMilliseconds, Supplier<Object> function)
            {
            }

            @Override
            public void deRegister(String name)
            {
            }

            @Override
            public void deRegisterAll()
            {
            }

            @Override
            public void trigger(String scheduleName, boolean forceRun)
            {
            }

            @Override
            public void run(String scheduleName)
            {
            }

            @Override
            public void toggleDisable(String scheduleName, boolean toggle)
            {
            }

            @Override
            public void toggleDisableAll(boolean toggle)
            {
            }
        };

        QueryMetricsMongo metricsStore = new QueryMetricsMongo(mongoProvider);
        QueryMetricsRegistry metricsRegistry = new InMemoryQueryMetricsRegistry();
        QueryMetricsService metricsService = new QueryMetricsServiceImpl(metricsStore);

        boolean result = module.scheduleMetricsPersistence(schedulesFactory, metricsRegistry, metricsService);

        Assertions.assertTrue(result);
        Assertions.assertEquals(1, registeredNames.size());
        Assertions.assertEquals("persist-query-metrics", registeredNames.get(0));
    }

    @Test
    public void canExecuteRegisteredScheduleTask()
    {
        QueryMetricsSchedulesModule module = new QueryMetricsSchedulesModule();

        List<Supplier<Object>> registeredTasks = new ArrayList<>();

        SchedulesFactory schedulesFactory = new SchedulesFactory()
        {
            @Override
            public void register(String name, long delayStartInMilliseconds, long intervalInMilliseconds, Supplier<Object> task)
            {
                registeredTasks.add(task);
            }

            @Override
            public void registerExternalTriggerSchedule(String name, long intervalInMilliseconds, Supplier<Object> function)
            {
            }

            @Override
            public void registerSingleInstance(String name, long delayStartInMilliseconds, long intervalInMilliseconds, Supplier<Object> function)
            {
            }

            @Override
            public void deRegister(String name)
            {
            }

            @Override
            public void deRegisterAll()
            {
            }

            @Override
            public void trigger(String scheduleName, boolean forceRun)
            {
            }

            @Override
            public void run(String scheduleName)
            {
            }

            @Override
            public void toggleDisable(String scheduleName, boolean toggle)
            {
            }

            @Override
            public void toggleDisableAll(boolean toggle)
            {
            }
        };

        QueryMetricsMongo metricsStore = new QueryMetricsMongo(mongoProvider);
        QueryMetricsRegistry metricsRegistry = new InMemoryQueryMetricsRegistry();
        QueryMetricsService metricsService = new QueryMetricsServiceImpl(metricsStore);

        metricsRegistry.record("group1", "art1", "1.0.0");

        module.scheduleMetricsPersistence(schedulesFactory, metricsRegistry, metricsService);

        Assertions.assertEquals(1, registeredTasks.size());
        Object taskResult = registeredTasks.get(0).get();
        Assertions.assertEquals(true, taskResult);
        Assertions.assertEquals(1, metricsStore.getAllStoredEntities().size());
    }
}
