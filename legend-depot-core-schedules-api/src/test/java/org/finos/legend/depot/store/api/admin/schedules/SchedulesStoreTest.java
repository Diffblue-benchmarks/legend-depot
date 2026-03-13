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

public class SchedulesStoreTest
{
    private SchedulesStore schedulesStore;

    @BeforeEach
    public void setUp()
    {
        // Arrange - Create a simple test implementation
        schedulesStore = new TestSchedulesStoreImpl();
    }

    @Test
    public void canGetAll()
    {
        // Arrange
        ScheduleInfo schedule1 = new ScheduleInfo("schedule1");
        ScheduleInfo schedule2 = new ScheduleInfo("schedule2");
        schedulesStore.createOrUpdate(schedule1);
        schedulesStore.createOrUpdate(schedule2);

        // Act
        List<ScheduleInfo> result = schedulesStore.getAll();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void canGetByName()
    {
        // Arrange
        ScheduleInfo schedule = new ScheduleInfo("testSchedule");
        schedule.setFrequency(1000L);
        schedulesStore.createOrUpdate(schedule);

        // Act
        Optional<ScheduleInfo> result = schedulesStore.get("testSchedule");

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("testSchedule", result.get().getName());
        Assertions.assertEquals(1000L, result.get().getFrequency());
    }

    @Test
    public void canGetNonExistentSchedule()
    {
        // Act
        Optional<ScheduleInfo> result = schedulesStore.get("nonExistent");

        // Assert
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void canCreateOrUpdate()
    {
        // Arrange
        ScheduleInfo schedule = new ScheduleInfo("newSchedule");
        schedule.setDisabled(true);
        schedule.setSingleInstance(true);

        // Act
        ScheduleInfo result = schedulesStore.createOrUpdate(schedule);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("newSchedule", result.getName());
        Assertions.assertTrue(result.isDisabled());
        Assertions.assertTrue(result.getSingleInstance());
    }

    @Test
    public void canUpdateExistingSchedule()
    {
        // Arrange
        ScheduleInfo schedule = new ScheduleInfo("schedule");
        schedule.setFrequency(500L);
        schedulesStore.createOrUpdate(schedule);

        schedule.setFrequency(1000L);

        // Act
        ScheduleInfo updated = schedulesStore.createOrUpdate(schedule);

        // Assert
        Assertions.assertNotNull(updated);
        Assertions.assertEquals(1000L, updated.getFrequency());
    }

    @Test
    public void canDelete()
    {
        // Arrange
        ScheduleInfo schedule = new ScheduleInfo("toDelete");
        schedulesStore.createOrUpdate(schedule);

        // Act
        schedulesStore.delete("toDelete");
        Optional<ScheduleInfo> result = schedulesStore.get("toDelete");

        // Assert
        Assertions.assertFalse(result.isPresent());
    }

    /**
     * Simple test implementation of SchedulesStore for testing the interface contract
     */
    private static class TestSchedulesStoreImpl implements SchedulesStore
    {
        private final Map<String, ScheduleInfo> store = new HashMap<>();

        @Override
        public List<ScheduleInfo> getAll()
        {
            return new ArrayList<>(store.values());
        }

        @Override
        public Optional<ScheduleInfo> get(String name)
        {
            return Optional.ofNullable(store.get(name));
        }

        @Override
        public ScheduleInfo createOrUpdate(ScheduleInfo scheduleInfo)
        {
            store.put(scheduleInfo.getName(), scheduleInfo);
            return scheduleInfo;
        }

        @Override
        public void delete(String name)
        {
            store.remove(name);
        }
    }
}
