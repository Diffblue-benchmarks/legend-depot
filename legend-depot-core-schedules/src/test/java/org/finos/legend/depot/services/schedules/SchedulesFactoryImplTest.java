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

package org.finos.legend.depot.services.schedules;

import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SchedulesFactoryImplTest
{
    private SchedulesStore mockSchedulesStore;
    private ScheduleInstancesStore mockInstancesStore;
    private SchedulesFactoryImpl factory;

    @BeforeEach
    public void setup()
    {
        mockSchedulesStore = Mockito.mock(SchedulesStore.class);
        mockInstancesStore = Mockito.mock(ScheduleInstancesStore.class);
        factory = new SchedulesFactoryImpl(mockSchedulesStore, mockInstancesStore, false);
    }

    @Test
    public void testRegisterCreatesScheduleInfo()
    {
        when(mockSchedulesStore.get("test-schedule")).thenReturn(Optional.empty());

        factory.register("test-schedule", 1000, 60000, () -> "done");

        verify(mockSchedulesStore).createOrUpdate(any(ScheduleInfo.class));
        Assertions.assertTrue(factory.tasksRegistry.containsKey("test-schedule"));
        Assertions.assertTrue(factory.functions.containsKey("test-schedule"));
    }

    @Test
    public void testRegisterExternalTriggerSchedule()
    {
        when(mockSchedulesStore.get("external-schedule")).thenReturn(Optional.empty());

        factory.registerExternalTriggerSchedule("external-schedule", 60000, () -> "triggered");

        verify(mockSchedulesStore).createOrUpdate(any(ScheduleInfo.class));
        Assertions.assertTrue(factory.functions.containsKey("external-schedule"));
    }

    @Test
    public void testDeRegister()
    {
        when(mockSchedulesStore.get("test-schedule")).thenReturn(Optional.empty());
        factory.register("test-schedule", 1000, 60000, () -> "done");

        factory.deRegister("test-schedule");

        Assertions.assertFalse(factory.tasksRegistry.containsKey("test-schedule"));
        verify(mockSchedulesStore).delete("test-schedule");
    }

    @Test
    public void testDeRegisterNonExistentDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> factory.deRegister("non-existent"));
    }

    @Test
    public void testTriggerWithExistingSchedule()
    {
        ScheduleInfo info = new ScheduleInfo("test-schedule");
        info.frequency = 60000L;
        when(mockSchedulesStore.get("test-schedule")).thenReturn(Optional.of(info));

        factory.functions.put("test-schedule", () -> "executed");

        Assertions.assertDoesNotThrow(() -> factory.trigger("test-schedule", true));
    }

    @Test
    public void testTriggerWithNonExistentSchedule()
    {
        when(mockSchedulesStore.get("missing")).thenReturn(Optional.empty());
        Assertions.assertDoesNotThrow(() -> factory.trigger("missing", true));
    }

    @Test
    public void testToggleDisable()
    {
        ScheduleInfo info = new ScheduleInfo("test-schedule");
        when(mockSchedulesStore.get("test-schedule")).thenReturn(Optional.of(info));

        factory.toggleDisable("test-schedule", true);

        Assertions.assertTrue(info.isDisabled());
        verify(mockSchedulesStore).createOrUpdate(info);
    }

    @Test
    public void testCanExecuteWithNoInstances()
    {
        when(mockInstancesStore.find("test-schedule")).thenReturn(Collections.emptyList());
        Assertions.assertTrue(factory.canExecute("test-schedule"));
    }

    @Test
    public void testDeleteExpired()
    {
        when(mockInstancesStore.delete(Mockito.anyLong())).thenReturn(5L);
        long deleted = factory.deleteExpired();
        Assertions.assertEquals(5L, deleted);
    }

    @Test
    public void testDeRegisterAll()
    {
        ScheduleInfo info1 = new ScheduleInfo("schedule-1");
        ScheduleInfo info2 = new ScheduleInfo("schedule-2");
        when(mockSchedulesStore.getAll()).thenReturn(java.util.Arrays.asList(info1, info2));

        factory.deRegisterAll();

        verify(mockSchedulesStore).delete("schedule-1");
        verify(mockSchedulesStore).delete("schedule-2");
    }
}
