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
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class TestSchedulesMongo extends TestStoreMongo
{
    private SchedulesMongo schedulesMongo = new SchedulesMongo(mongoProvider);

    @Test
    public void canCreateSchedulesMongoInstance()
    {
        SchedulesMongo mongo = new SchedulesMongo(mongoProvider);
        Assertions.assertNotNull(mongo);
        Assertions.assertNotNull(mongo.getCollection());
    }

    @Test
    public void canGetScheduleByName()
    {
        ScheduleInfo schedule = new ScheduleInfo("test-schedule");
        schedule.setFrequency(1000L);
        schedule.setDisabled(false);
        schedulesMongo.createOrUpdate(schedule);

        Optional<ScheduleInfo> result = schedulesMongo.get("test-schedule");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("test-schedule", result.get().getName());
    }

    @Test
    public void canGetAllSchedules()
    {
        ScheduleInfo schedule1 = new ScheduleInfo("schedule-1");
        schedule1.setFrequency(1000L);
        ScheduleInfo schedule2 = new ScheduleInfo("schedule-2");
        schedule2.setFrequency(2000L);
        ScheduleInfo schedule3 = new ScheduleInfo("schedule-3");
        schedule3.setFrequency(3000L);

        schedulesMongo.createOrUpdate(schedule1);
        schedulesMongo.createOrUpdate(schedule2);
        schedulesMongo.createOrUpdate(schedule3);

        List<ScheduleInfo> allSchedules = schedulesMongo.getAll();
        Assertions.assertNotNull(allSchedules);
        Assertions.assertEquals(3, allSchedules.size());
    }

    @Test
    public void canDeleteScheduleByName()
    {
        ScheduleInfo schedule = new ScheduleInfo("delete-schedule");
        schedule.setFrequency(5000L);
        schedulesMongo.createOrUpdate(schedule);

        Optional<ScheduleInfo> beforeDelete = schedulesMongo.get("delete-schedule");
        Assertions.assertTrue(beforeDelete.isPresent());

        schedulesMongo.delete("delete-schedule");

        Optional<ScheduleInfo> afterDelete = schedulesMongo.get("delete-schedule");
        Assertions.assertFalse(afterDelete.isPresent());
    }

    @Test
    public void canCreateOrUpdateSchedule()
    {
        ScheduleInfo schedule = new ScheduleInfo("update-schedule");
        schedule.setFrequency(1000L);
        schedule.setDisabled(false);

        schedulesMongo.createOrUpdate(schedule);
        Optional<ScheduleInfo> created = schedulesMongo.get("update-schedule");
        Assertions.assertTrue(created.isPresent());
        Assertions.assertEquals(1000L, created.get().getFrequency());

        schedule.setFrequency(2000L);
        schedulesMongo.createOrUpdate(schedule);
        Optional<ScheduleInfo> updated = schedulesMongo.get("update-schedule");
        Assertions.assertTrue(updated.isPresent());
        Assertions.assertEquals(2000L, updated.get().getFrequency());
    }

    @Test
    public void canGetEmptyWhenScheduleNotFound()
    {
        Optional<ScheduleInfo> result = schedulesMongo.get("non-existent");
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void canGetEmptyListWhenNoSchedules()
    {
        List<ScheduleInfo> allSchedules = schedulesMongo.getAll();
        Assertions.assertNotNull(allSchedules);
        Assertions.assertEquals(0, allSchedules.size());
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = SchedulesMongo.buildIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertEquals(1, indexes.size());
    }
}
