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

package org.finos.legend.depot.store.mongo.guice;

import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class TestManageMongoStoreSchedulesModule
{
    @Test
    public void scheduleStorageMetricsRegistersScheduleAndReturnsTrue()
    {
        SchedulesFactory schedulesFactory = Mockito.mock(SchedulesFactory.class);
        StorageMetricsHandler storageMetrics = Mockito.mock(StorageMetricsHandler.class);

        ManageMongoStoreSchedulesModule module = new ManageMongoStoreSchedulesModule();
        boolean result = module.scheduleStorageMetrics(schedulesFactory, storageMetrics);

        assertTrue(result);
        verify(storageMetrics).init();
        verify(schedulesFactory).register(eq("storage-metrics"), eq(5 * SchedulesFactory.MINUTE), eq(5 * SchedulesFactory.MINUTE), any());
    }
}
