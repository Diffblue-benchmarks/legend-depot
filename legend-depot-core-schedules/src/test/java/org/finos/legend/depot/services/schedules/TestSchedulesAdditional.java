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

import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSchedulesAdditional
{
    private SchedulesFactoryImpl schedulesFactory;

    @BeforeEach
    public void setUp()
    {
        schedulesFactory = new SchedulesFactoryImpl(new MockScheduleStore(), new MockInstancesStore(), false);
    }

    @AfterEach
    public void tearDown()
    {
        schedulesFactory.deRegisterAll();
    }

    @Test
    public void testConstructorWithHouseKeeper()
    {
        SchedulesFactoryImpl factoryWithHouseKeeper = new SchedulesFactoryImpl(new MockScheduleStore(), new MockInstancesStore(), true);
        Assertions.assertNotNull(factoryWithHouseKeeper);
        factoryWithHouseKeeper.deRegisterAll();
    }

    @Test
    public void testRegisterExternalTriggerSchedule()
    {
        schedulesFactory.registerExternalTriggerSchedule("externalJob", 600000, () -> "external result");
        Assertions.assertEquals(1, schedulesFactory.schedulesStore.getAll().size());
        ScheduleInfo info = schedulesFactory.schedulesStore.get("externalJob").get();
        Assertions.assertTrue(info.getExternalTrigger());
        Assertions.assertNotNull(schedulesFactory.functions.get("externalJob"));
    }

    @Test
    public void testRunScheduleNotInStore()
    {
        schedulesFactory.register("disappearingJob", 600000, 100000, () -> "hello");
        Assertions.assertEquals(1, schedulesFactory.schedulesStore.getAll().size());
        Assertions.assertTrue(schedulesFactory.tasksRegistry.containsKey("disappearingJob"));

        schedulesFactory.schedulesStore.delete("disappearingJob");
        Assertions.assertFalse(schedulesFactory.schedulesStore.get("disappearingJob").isPresent());

        schedulesFactory.run("disappearingJob");

        Assertions.assertFalse(schedulesFactory.tasksRegistry.containsKey("disappearingJob"));
    }

    @Test
    public void testTriggerExistingSchedule()
    {
        schedulesFactory.register("triggerJob", 600000, 100000, () -> "triggered");
        Assertions.assertTrue(schedulesFactory.instancesStore.getAll().isEmpty());

        schedulesFactory.trigger("triggerJob", true);

        Assertions.assertEquals(1, schedulesFactory.instancesStore.getAll().size());
    }

    @Test
    public void testTriggerNonExistingSchedule()
    {
        schedulesFactory.trigger("nonExistent", true);
        Assertions.assertTrue(schedulesFactory.instancesStore.getAll().isEmpty());
    }

    @Test
    public void testExecuteWithNoFunction()
    {
        ScheduleInfo schedule = new ScheduleInfo("noFuncJob");
        schedule.frequency = 100000L;
        schedulesFactory.schedulesStore.createOrUpdate(schedule);

        schedulesFactory.trigger("noFuncJob", true);

        Assertions.assertTrue(schedulesFactory.instancesStore.getAll().isEmpty());
    }

    @Test
    public void testExecuteDisabledScheduleWithoutForceRun()
    {
        schedulesFactory.register("disabledJob", 600000, 100000, () -> "result");
        schedulesFactory.toggleDisable("disabledJob", true);

        schedulesFactory.trigger("disabledJob", false);

        Assertions.assertTrue(schedulesFactory.instancesStore.getAll().isEmpty());
    }

    @Test
    public void testExecuteWithException()
    {
        schedulesFactory.register("errorJob", 600000, 100000, () ->
        {
            throw new RuntimeException("test error");
        });

        schedulesFactory.trigger("errorJob", true);

        Assertions.assertEquals(1, schedulesFactory.instancesStore.getAll().size());
    }
}
