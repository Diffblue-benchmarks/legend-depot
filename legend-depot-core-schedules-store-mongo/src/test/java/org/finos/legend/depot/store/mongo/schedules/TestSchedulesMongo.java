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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class TestSchedulesMongo extends TestStoreMongo
{
    private SchedulesMongo schedulesMongo;

    @BeforeEach
    public void setUp()
    {
        schedulesMongo = new SchedulesMongo(getMongoDatabase());
    }

    @Test
    public void testGetReturnsEmptyWhenNotFound()
    {
        Optional<ScheduleInfo> result = schedulesMongo.get("nonexistent");
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testGetReturnsScheduleWhenFound()
    {
        ScheduleInfo info = new ScheduleInfo("testSchedule");
        schedulesMongo.createOrUpdate(info);

        Optional<ScheduleInfo> result = schedulesMongo.get("testSchedule");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("testSchedule", result.get().name);
    }

    @Test
    public void testGetAllReturnsEmptyListWhenNoSchedules()
    {
        List<ScheduleInfo> result = schedulesMongo.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllReturnsAllSchedules()
    {
        schedulesMongo.createOrUpdate(new ScheduleInfo("schedule1"));
        schedulesMongo.createOrUpdate(new ScheduleInfo("schedule2"));

        List<ScheduleInfo> result = schedulesMongo.getAll();
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testDeleteRemovesSchedule()
    {
        ScheduleInfo info = new ScheduleInfo("toDelete");
        schedulesMongo.createOrUpdate(info);

        schedulesMongo.delete("toDelete");

        Optional<ScheduleInfo> result = schedulesMongo.get("toDelete");
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteDoesNotFailWhenNotFound()
    {
        schedulesMongo.delete("doesNotExist");
        Assertions.assertTrue(schedulesMongo.getAll().isEmpty());
    }

    @Test
    public void testBuildIndexes()
    {
        List<IndexModel> indexes = SchedulesMongo.buildIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertFalse(indexes.isEmpty());
    }

    @Test
    public void testCreateOrUpdateUpdatesExistingSchedule()
    {
        ScheduleInfo info = new ScheduleInfo("updateSchedule");
        info.frequency = 1000L;
        schedulesMongo.createOrUpdate(info);

        ScheduleInfo updated = new ScheduleInfo("updateSchedule");
        updated.frequency = 2000L;
        schedulesMongo.createOrUpdate(updated);

        Optional<ScheduleInfo> result = schedulesMongo.get("updateSchedule");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(Long.valueOf(2000L), result.get().frequency);
    }
}