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

package org.finos.legend.depot.store.api.admin.schedules;

import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TestSchedulesStore
{
    private SchedulesStore store;

    private static class InMemorySchedulesStore implements SchedulesStore
    {
        Map<String, ScheduleInfo> schedules = new HashMap<>();

        @Override
        public List<ScheduleInfo> getAll()
        {
            return new ArrayList<>(schedules.values());
        }

        @Override
        public Optional<ScheduleInfo> get(String name)
        {
            return schedules.containsKey(name) ? Optional.of(schedules.get(name)) : Optional.empty();
        }

        @Override
        public ScheduleInfo createOrUpdate(ScheduleInfo scheduleInfo)
        {
            schedules.put(scheduleInfo.name, scheduleInfo);
            return scheduleInfo;
        }

        @Override
        public void delete(String name)
        {
            schedules.remove(name);
        }
    }

    @BeforeEach
    public void setUp()
    {
        store = new InMemorySchedulesStore();
    }

    @Test
    public void testGetAllReturnsEmptyWhenNoSchedules()
    {
        List<ScheduleInfo> result = store.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllReturnsAllSchedules()
    {
        store.createOrUpdate(new ScheduleInfo("job1"));
        store.createOrUpdate(new ScheduleInfo("job2"));
        List<ScheduleInfo> result = store.getAll();
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testGetReturnsEmptyWhenNotFound()
    {
        Optional<ScheduleInfo> result = store.get("nonexistent");
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testGetReturnsScheduleWhenFound()
    {
        store.createOrUpdate(new ScheduleInfo("job1"));
        Optional<ScheduleInfo> result = store.get("job1");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("job1", result.get().getName());
    }

    @Test
    public void testCreateOrUpdateCreatesNewSchedule()
    {
        ScheduleInfo info = new ScheduleInfo("job1");
        ScheduleInfo created = store.createOrUpdate(info);
        Assertions.assertNotNull(created);
        Assertions.assertEquals("job1", created.getName());
        Assertions.assertEquals(1, store.getAll().size());
    }

    @Test
    public void testCreateOrUpdateUpdatesExistingSchedule()
    {
        ScheduleInfo info = new ScheduleInfo("job1");
        store.createOrUpdate(info);

        ScheduleInfo updated = new ScheduleInfo("job1");
        updated.setDisabled(true);
        store.createOrUpdate(updated);

        Assertions.assertEquals(1, store.getAll().size());
        Assertions.assertTrue(store.get("job1").get().isDisabled());
    }

    @Test
    public void testDeleteRemovesSchedule()
    {
        store.createOrUpdate(new ScheduleInfo("job1"));
        Assertions.assertEquals(1, store.getAll().size());

        store.delete("job1");

        Assertions.assertTrue(store.getAll().isEmpty());
        Assertions.assertFalse(store.get("job1").isPresent());
    }

    @Test
    public void testDeleteNonExistentScheduleDoesNotThrow()
    {
        store.delete("nonexistent");
        Assertions.assertTrue(store.getAll().isEmpty());
    }
}
