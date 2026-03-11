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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSchedulesMongo extends TestStoreMongo
{
    private SchedulesMongo schedulesMongo;

    @BeforeEach
    public void setUp()
    {
        schedulesMongo = new SchedulesMongo(getMongoDatabase());
    }

    @Test
    public void canCreateSchedulesMongo()
    {
        assertNotNull(schedulesMongo);
    }

    @Test
    public void canGetAllWhenEmpty()
    {
        List<ScheduleInfo> all = schedulesMongo.getAll();
        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    public void canCreateAndGetSchedule()
    {
        ScheduleInfo schedule = new ScheduleInfo("test-schedule");
        schedule.setFrequency(60000L);
        schedulesMongo.createOrUpdate(schedule);

        Optional<ScheduleInfo> result = schedulesMongo.get("test-schedule");
        assertTrue(result.isPresent());
        assertEquals("test-schedule", result.get().getName());
    }

    @Test
    public void canGetAllSchedules()
    {
        schedulesMongo.createOrUpdate(new ScheduleInfo("schedule-1"));
        schedulesMongo.createOrUpdate(new ScheduleInfo("schedule-2"));

        List<ScheduleInfo> all = schedulesMongo.getAll();
        assertEquals(2, all.size());
    }

    @Test
    public void canDeleteSchedule()
    {
        schedulesMongo.createOrUpdate(new ScheduleInfo("to-delete"));
        assertTrue(schedulesMongo.get("to-delete").isPresent());

        schedulesMongo.delete("to-delete");
        assertFalse(schedulesMongo.get("to-delete").isPresent());
    }

    @Test
    public void canGetNonExistentSchedule()
    {
        Optional<ScheduleInfo> result = schedulesMongo.get("non-existent");
        assertFalse(result.isPresent());
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = SchedulesMongo.buildIndexes();
        assertNotNull(indexes);
        assertEquals(1, indexes.size());
    }

    @Test
    public void canUpdateExistingSchedule()
    {
        ScheduleInfo schedule = new ScheduleInfo("update-test");
        schedule.setDisabled(false);
        schedulesMongo.createOrUpdate(schedule);

        ScheduleInfo updated = new ScheduleInfo("update-test");
        updated.setDisabled(true);
        schedulesMongo.createOrUpdate(updated);

        Optional<ScheduleInfo> result = schedulesMongo.get("update-test");
        assertTrue(result.isPresent());
        assertTrue(result.get().isDisabled());
    }
}
