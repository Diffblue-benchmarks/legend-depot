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

import org.finos.legend.depot.store.model.admin.schedules.ScheduleInstance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ScheduleInstancesStoreTest
{
    private static class InMemoryScheduleInstancesStore implements ScheduleInstancesStore
    {
        private final List<ScheduleInstance> store = new ArrayList<>();
        private final AtomicLong idCounter = new AtomicLong(0);

        @Override
        public List<ScheduleInstance> getAll()
        {
            return new ArrayList<>(store);
        }

        @Override
        public List<ScheduleInstance> find(String scheduleName)
        {
            return store.stream()
                    .filter(i -> scheduleName.equals(i.getSchedule()))
                    .collect(Collectors.toList());
        }

        @Override
        public void insert(ScheduleInstance instance)
        {
            instance.setId(String.valueOf(idCounter.incrementAndGet()));
            store.add(instance);
        }

        @Override
        public long delete(long l)
        {
            int before = store.size();
            store.removeIf(i -> String.valueOf(l).equals(i.getId()));
            return before - store.size();
        }
    }

    @Test
    public void testGetAllReturnsEmptyWhenNoInstances()
    {
        ScheduleInstancesStore store = new InMemoryScheduleInstancesStore();
        List<ScheduleInstance> result = store.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllReturnsInsertedInstances()
    {
        ScheduleInstancesStore store = new InMemoryScheduleInstancesStore();
        ScheduleInstance instance = new ScheduleInstance("testSchedule", new Date());
        store.insert(instance);
        List<ScheduleInstance> result = store.getAll();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("testSchedule", result.get(0).getSchedule());
    }

    @Test
    public void testFindByScheduleName()
    {
        ScheduleInstancesStore store = new InMemoryScheduleInstancesStore();
        store.insert(new ScheduleInstance("scheduleA", new Date()));
        store.insert(new ScheduleInstance("scheduleB", new Date()));
        store.insert(new ScheduleInstance("scheduleA", new Date()));
        List<ScheduleInstance> result = store.find("scheduleA");
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testFindReturnsEmptyForUnknownSchedule()
    {
        ScheduleInstancesStore store = new InMemoryScheduleInstancesStore();
        store.insert(new ScheduleInstance("scheduleA", new Date()));
        List<ScheduleInstance> result = store.find("unknown");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testInsertAddsInstance()
    {
        ScheduleInstancesStore store = new InMemoryScheduleInstancesStore();
        ScheduleInstance instance = new ScheduleInstance("mySchedule", new Date());
        store.insert(instance);
        Assertions.assertNotNull(instance.getId());
        Assertions.assertEquals(1, store.getAll().size());
    }

    @Test
    public void testDeleteRemovesInstance()
    {
        ScheduleInstancesStore store = new InMemoryScheduleInstancesStore();
        ScheduleInstance instance = new ScheduleInstance("mySchedule", new Date());
        store.insert(instance);
        long id = Long.parseLong(instance.getId());
        long deleted = store.delete(id);
        Assertions.assertEquals(1, deleted);
        Assertions.assertTrue(store.getAll().isEmpty());
    }

    @Test
    public void testDeleteReturnsZeroForNonExistingId()
    {
        ScheduleInstancesStore store = new InMemoryScheduleInstancesStore();
        long deleted = store.delete(999L);
        Assertions.assertEquals(0, deleted);
    }
}
