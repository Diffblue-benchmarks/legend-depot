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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ScheduleInstancesStoreTest
{
    private ScheduleInstancesStore store;

    @BeforeEach
    public void setUp()
    {
        // Create a simple in-memory implementation for testing
        store = new ScheduleInstancesStore()
        {
            private final List<ScheduleInstance> instances = new ArrayList<>();
            private int idCounter = 1;

            @Override
            public List<ScheduleInstance> getAll()
            {
                return new ArrayList<>(instances);
            }

            @Override
            public List<ScheduleInstance> find(String scheduleName)
            {
                List<ScheduleInstance> result = new ArrayList<>();
                for (ScheduleInstance instance : instances)
                {
                    if (instance.getSchedule() != null && instance.getSchedule().equals(scheduleName))
                    {
                        result.add(instance);
                    }
                }
                return result;
            }

            @Override
            public void insert(ScheduleInstance instance)
            {
                instance.setId(String.valueOf(idCounter++));
                instances.add(instance);
            }

            @Override
            public long delete(long l)
            {
                int originalSize = instances.size();
                instances.removeIf(ScheduleInstance::isExpired);
                return originalSize - instances.size();
            }
        };
    }

    @Test
    public void canGetAllInstances()
    {
        // Arrange
        ScheduleInstance instance1 = new ScheduleInstance("schedule1", new Date(System.currentTimeMillis() + 10000));
        ScheduleInstance instance2 = new ScheduleInstance("schedule2", new Date(System.currentTimeMillis() + 20000));

        // Act
        store.insert(instance1);
        store.insert(instance2);
        List<ScheduleInstance> result = store.getAll();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void canFindInstancesByScheduleName()
    {
        // Arrange
        ScheduleInstance instance1 = new ScheduleInstance("test-schedule", new Date(System.currentTimeMillis() + 10000));
        ScheduleInstance instance2 = new ScheduleInstance("other-schedule", new Date(System.currentTimeMillis() + 20000));
        ScheduleInstance instance3 = new ScheduleInstance("test-schedule", new Date(System.currentTimeMillis() + 30000));

        // Act
        store.insert(instance1);
        store.insert(instance2);
        store.insert(instance3);
        List<ScheduleInstance> result = store.find("test-schedule");

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        for (ScheduleInstance instance : result)
        {
            Assertions.assertEquals("test-schedule", instance.getSchedule());
        }
    }

    @Test
    public void canInsertInstance()
    {
        // Arrange
        ScheduleInstance instance = new ScheduleInstance("new-schedule", new Date(System.currentTimeMillis() + 10000));

        // Act
        store.insert(instance);
        List<ScheduleInstance> result = store.getAll();

        // Assert
        Assertions.assertNotNull(instance.getId());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("new-schedule", result.get(0).getSchedule());
    }

    @Test
    public void canDeleteExpiredInstances()
    {
        // Arrange
        ScheduleInstance expiredInstance = new ScheduleInstance("expired", new Date(System.currentTimeMillis() - 10000));
        ScheduleInstance validInstance = new ScheduleInstance("valid", new Date(System.currentTimeMillis() + 10000));

        // Act
        store.insert(expiredInstance);
        store.insert(validInstance);
        long deletedCount = store.delete(0);
        List<ScheduleInstance> remaining = store.getAll();

        // Assert
        Assertions.assertEquals(1, deletedCount);
        Assertions.assertEquals(1, remaining.size());
        Assertions.assertEquals("valid", remaining.get(0).getSchedule());
    }
}
