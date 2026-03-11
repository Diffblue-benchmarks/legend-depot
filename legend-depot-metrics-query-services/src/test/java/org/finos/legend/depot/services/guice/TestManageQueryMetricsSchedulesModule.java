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
import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class TestManageQueryMetricsSchedulesModule
{
    @Test
    public void canConfigureModule()
    {
        ManageQueryMetricsSchedulesModule module = new ManageQueryMetricsSchedulesModule();
        Assertions.assertNotNull(module);
        Assertions.assertDoesNotThrow(() -> module.configure());
    }

    @Test
    public void canScheduleMetricsConsolidation()
    {
        ManageQueryMetricsSchedulesModule module = new ManageQueryMetricsSchedulesModule();

        String[] registeredName = new String[1];
        long[] registeredDelay = new long[1];
        long[] registeredInterval = new long[1];
        Supplier<Object>[] registeredTask = new Supplier[1];

        SchedulesFactory stubFactory = new SchedulesFactory()
        {
            @Override
            public void register(String name, long delayStartInMilliseconds, long intervalInMilliseconds, Supplier<Object> task)
            {
            }

            @Override
            public void registerExternalTriggerSchedule(String name, long intervalInMilliseconds, Supplier<Object> function)
            {
            }

            @Override
            public void registerSingleInstance(String name, long delayStartInMilliseconds, long intervalInMilliseconds, Supplier<Object> function)
            {
                registeredName[0] = name;
                registeredDelay[0] = delayStartInMilliseconds;
                registeredInterval[0] = intervalInMilliseconds;
                registeredTask[0] = function;
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

        boolean[] consolidateCalled = new boolean[1];
        QueryMetricsService stubMetricsService = new QueryMetricsService()
        {
            @Override
            public Optional<VersionQueryMetric> getSummary(String groupId, String artifactId, String versionId)
            {
                return Optional.empty();
            }

            @Override
            public List<VersionQueryMetric> getSummaryByProjectVersion()
            {
                return null;
            }

            @Override
            public List<VersionQueryMetric> findMetricsForProjectCoordinates(String groupId, String artifactId)
            {
                return null;
            }

            @Override
            public List<VersionQueryMetric> findReleasedVersionMetricsBefore(Date date)
            {
                return null;
            }

            @Override
            public List<VersionQueryMetric> findSnapshotVersionMetricsBefore(Date date)
            {
                return null;
            }

            @Override
            public List<VersionQueryMetric> getStaleMetrics(int ttlForVersionsInDays, int ttlForSnapshotsInDays)
            {
                return null;
            }

            @Override
            public void consolidateMetrics()
            {
                consolidateCalled[0] = true;
            }

            @Override
            public void persist(QueryMetricsRegistry registry)
            {
            }

            @Override
            public void delete(String groupId, String artifactId, String versionId)
            {
            }
        };

        boolean result = module.scheduleMetricsConsolidation(stubFactory, stubMetricsService);

        Assertions.assertTrue(result);
        Assertions.assertEquals("consolidate-query-metrics", registeredName[0]);
        Assertions.assertEquals(SchedulesFactory.MINUTE, registeredDelay[0]);
        Assertions.assertEquals(6 * SchedulesFactory.HOUR, registeredInterval[0]);
        Assertions.assertNotNull(registeredTask[0]);

        Object taskResult = registeredTask[0].get();
        Assertions.assertTrue(consolidateCalled[0]);
        Assertions.assertEquals(true, taskResult);
    }
}
