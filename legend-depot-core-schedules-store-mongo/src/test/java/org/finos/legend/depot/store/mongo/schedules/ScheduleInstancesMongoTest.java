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

package org.finos.legend.depot.store.mongo.schedules;

import com.mongodb.client.model.IndexModel;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInstance;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class ScheduleInstancesMongoTest extends TestStoreMongo
{
    private ScheduleInstancesMongo scheduleInstancesMongo;

    @BeforeEach
    public void setUp()
    {
        scheduleInstancesMongo = new ScheduleInstancesMongo(mongoProvider);
    }

    @Test
    public void testGetAllReturnsEmptyWhenNoData()
    {
        List<ScheduleInstance> result = scheduleInstancesMongo.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllReturnsStoredInstances()
    {
        ScheduleInstance instance = new ScheduleInstance("testSchedule", new Date(System.currentTimeMillis() + 10000));
        scheduleInstancesMongo.createOrUpdate(instance);

        List<ScheduleInstance> result = scheduleInstancesMongo.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("testSchedule", result.get(0).getSchedule());
    }

    @Test
    public void testFindByScheduleName()
    {
        ScheduleInstance instance1 = new ScheduleInstance("schedule-a", new Date(System.currentTimeMillis() + 10000));
        ScheduleInstance instance2 = new ScheduleInstance("schedule-b", new Date(System.currentTimeMillis() + 10000));
        scheduleInstancesMongo.createOrUpdate(instance1);
        scheduleInstancesMongo.createOrUpdate(instance2);

        List<ScheduleInstance> result = scheduleInstancesMongo.find("schedule-a");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("schedule-a", result.get(0).getSchedule());
    }

    @Test
    public void testFindReturnsEmptyWhenNoMatch()
    {
        List<ScheduleInstance> result = scheduleInstancesMongo.find("nonexistent");

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testDeleteRemovesExpiredInstances()
    {
        long now = System.currentTimeMillis();
        ScheduleInstance expired = new ScheduleInstance("expired-schedule", new Date(now - 10000));
        ScheduleInstance valid = new ScheduleInstance("valid-schedule", new Date(now + 10000));
        scheduleInstancesMongo.createOrUpdate(expired);
        scheduleInstancesMongo.createOrUpdate(valid);

        long deleted = scheduleInstancesMongo.delete(now);

        Assertions.assertEquals(1, deleted);
        List<ScheduleInstance> remaining = scheduleInstancesMongo.getAll();
        Assertions.assertEquals(1, remaining.size());
        Assertions.assertEquals("valid-schedule", remaining.get(0).getSchedule());
    }

    @Test
    public void testDeleteReturnsZeroWhenNothingExpired()
    {
        ScheduleInstance valid = new ScheduleInstance("valid-schedule", new Date(System.currentTimeMillis() + 10000));
        scheduleInstancesMongo.createOrUpdate(valid);

        long deleted = scheduleInstancesMongo.delete(System.currentTimeMillis() - 5000);

        Assertions.assertEquals(0, deleted);
    }

    @Test
    public void testBuildIndexesReturnsNonEmptyList()
    {
        List<IndexModel> indexes = ScheduleInstancesMongo.buildIndexes();

        Assertions.assertNotNull(indexes);
        Assertions.assertFalse(indexes.isEmpty());
    }
}
