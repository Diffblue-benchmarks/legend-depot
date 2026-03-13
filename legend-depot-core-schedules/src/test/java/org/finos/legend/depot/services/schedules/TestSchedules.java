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

import org.finos.legend.depot.store.model.admin.schedules.ScheduleInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.finos.legend.depot.domain.DatesHandler.toDate;

public class TestSchedules
{
    private SchedulesFactoryImpl schedulesFactory;

    @BeforeEach
    public void setUp()
    {
        schedulesFactory = new SchedulesFactoryImpl(new MockScheduleStore(), new MockInstancesStore(),false);
        Assertions.assertTrue(schedulesFactory.tasksRegistry.isEmpty());
    }

    @AfterEach
    public void tearDown()
    {
        schedulesFactory.deRegisterAll();
    }


    @Test
    public void testDisabledAllToggle()
    {
        schedulesFactory.register("job3", 600000, 100000, () -> "hello toggles");
        schedulesFactory.register("job4", 600000, 100000, () -> "hello toggles again");
        schedulesFactory.toggleDisableAll(false);
        Assertions.assertTrue(schedulesFactory.schedulesStore.getAll().stream().allMatch(j -> !j.disabled));
        schedulesFactory.toggleDisableAll(true);
        Assertions.assertTrue(schedulesFactory.schedulesStore.getAll().stream().allMatch(j -> j.disabled));

        schedulesFactory.run("job3");
        Assertions.assertTrue(schedulesFactory.instancesStore.getAll().isEmpty());
    }

    @Test
    public void testDeregister()
    {
        schedulesFactory.register("job33", 600000, 100000, () -> "hello toggles");
        schedulesFactory.register("job34", 600000, 100000, () -> "hello toggles again");
        Assertions.assertEquals(2,schedulesFactory.schedulesStore.getAll().size());
        schedulesFactory.deRegister("job33");
        Assertions.assertEquals(1,schedulesFactory.schedulesStore.getAll().size());
        Assertions.assertFalse(schedulesFactory.tasksRegistry.contains("job33"));

    }

    @Test
    public void deleteExpired()
    {
        ScheduleInstance instance = new ScheduleInstance("job1", toDate(LocalDateTime.now().plusSeconds(10)));
        schedulesFactory.instancesStore.insert(instance);

        ScheduleInstance expired = new ScheduleInstance("expired", toDate(LocalDateTime.now().minusDays(10)));
        schedulesFactory.instancesStore.insert(expired);

        schedulesFactory.deleteExpired();

        Assertions.assertTrue(schedulesFactory.instancesStore.getAll().stream().noneMatch(i -> i.getSchedule().equals("expired")));
    }

    @Test
    public void canExecute()
    {
         schedulesFactory.register("multiInstance",10000000L,100000000L, () -> "happy run");
         schedulesFactory.registerSingleInstance("singleInstance",10000000L,100000000L, () -> "single run");
         Assertions.assertEquals(2, schedulesFactory.schedulesStore.getAll().size());

         schedulesFactory.run("multiInstance");
         schedulesFactory.run("multiInstance");
         schedulesFactory.run("multiInstance");

         Assertions.assertEquals(3,schedulesFactory.instancesStore.getAll().size());

         schedulesFactory.run("singleInstance");
         schedulesFactory.run("singleInstance");

        Assertions.assertEquals(4,schedulesFactory.instancesStore.getAll().size());

        Assertions.assertEquals(1,schedulesFactory.instancesStore.find("singleInstance").size());

        schedulesFactory.instancesStore.find("singleInstance").get(0).setExpires(toDate(LocalDateTime.now().minusMinutes(10)));
        schedulesFactory.run("singleInstance");

        Assertions.assertEquals(5,schedulesFactory.instancesStore.getAll().size());
    }

    @Test
    public void testConstructorWithHouseKeeper()
    {
        SchedulesFactoryImpl factory = new SchedulesFactoryImpl(new MockScheduleStore(), new MockInstancesStore(), true);
        Assertions.assertNotNull(factory);
        Assertions.assertNotNull(factory.schedulesStore);
        Assertions.assertNotNull(factory.instancesStore);
        factory.deRegisterAll();
    }

    @Test
    public void testRegisterExternalTriggerSchedule()
    {
        schedulesFactory.registerExternalTriggerSchedule("externalJob", 5000L, () -> "external result");

        Assertions.assertEquals(1, schedulesFactory.schedulesStore.getAll().size());
        Assertions.assertTrue(schedulesFactory.schedulesStore.get("externalJob").isPresent());
        Assertions.assertTrue(schedulesFactory.schedulesStore.get("externalJob").get().externalTrigger);
    }

    @Test
    public void testTrigger()
    {
        schedulesFactory.register("triggerJob", 600000L, 100000L, () -> "trigger result");

        schedulesFactory.trigger("triggerJob", false);
        Assertions.assertEquals(1, schedulesFactory.instancesStore.getAll().size());

        schedulesFactory.trigger("nonExistent", false);
        Assertions.assertEquals(1, schedulesFactory.instancesStore.getAll().size());
    }

    @Test
    public void testTriggerForceRun()
    {
        schedulesFactory.register("forceJob", 600000L, 100000L, () -> "force result");
        schedulesFactory.toggleDisable("forceJob", true);

        schedulesFactory.trigger("forceJob", false);
        Assertions.assertEquals(0, schedulesFactory.instancesStore.getAll().size());

        schedulesFactory.trigger("forceJob", true);
        Assertions.assertEquals(1, schedulesFactory.instancesStore.getAll().size());
    }

    @Test
    public void testToggleDisable()
    {
        schedulesFactory.register("toggleJob", 600000L, 100000L, () -> "toggle result");

        Assertions.assertFalse(schedulesFactory.schedulesStore.get("toggleJob").get().disabled);

        schedulesFactory.toggleDisable("toggleJob", true);
        Assertions.assertTrue(schedulesFactory.schedulesStore.get("toggleJob").get().disabled);

        schedulesFactory.toggleDisable("toggleJob", false);
        Assertions.assertFalse(schedulesFactory.schedulesStore.get("toggleJob").get().disabled);
    }

    @Test
    public void testDeregisterAll()
    {
        schedulesFactory.register("job1", 600000L, 100000L, () -> "result1");
        schedulesFactory.register("job2", 600000L, 100000L, () -> "result2");
        schedulesFactory.register("job3", 600000L, 100000L, () -> "result3");

        Assertions.assertEquals(3, schedulesFactory.schedulesStore.getAll().size());
        Assertions.assertEquals(3, schedulesFactory.tasksRegistry.size());

        schedulesFactory.deRegisterAll();

        Assertions.assertEquals(0, schedulesFactory.schedulesStore.getAll().size());
        Assertions.assertEquals(0, schedulesFactory.tasksRegistry.size());
    }

    @Test
    public void testScheduleNotInStore()
    {
        schedulesFactory.register("tempJob", 600000L, 100000L, () -> "result");
        Assertions.assertEquals(1, schedulesFactory.schedulesStore.getAll().size());

        schedulesFactory.schedulesStore.delete("tempJob");

        schedulesFactory.run("tempJob");

        Assertions.assertEquals(0, schedulesFactory.tasksRegistry.size());
    }

    @Test
    public void testDisabledScheduleSkipsExecution()
    {
        schedulesFactory.register("disabledJob", 600000L, 100000L, () -> "disabled result");
        schedulesFactory.toggleDisable("disabledJob", true);

        schedulesFactory.run("disabledJob");

        Assertions.assertEquals(0, schedulesFactory.instancesStore.getAll().size());
    }

    @Test
    public void testSingleInstanceCannotExecute()
    {
        schedulesFactory.registerSingleInstance("singleJob", 600000L, 100000L, () -> "single result");

        schedulesFactory.run("singleJob");
        Assertions.assertEquals(1, schedulesFactory.instancesStore.getAll().size());

        schedulesFactory.run("singleJob");
        Assertions.assertEquals(1, schedulesFactory.instancesStore.getAll().size());
    }

    @Test
    public void testCanExecuteMethod()
    {
        schedulesFactory.register("execJob", 600000L, 100000L, () -> "exec result");

        Assertions.assertTrue(schedulesFactory.canExecute("execJob"));

        schedulesFactory.run("execJob");
        Assertions.assertFalse(schedulesFactory.canExecute("execJob"));

        ScheduleInstance instance = schedulesFactory.instancesStore.find("execJob").get(0);
        instance.setExpires(toDate(LocalDateTime.now().minusMinutes(10)));

        Assertions.assertTrue(schedulesFactory.canExecute("execJob"));
    }

    @Test
    public void testExecuteWithNoFunction()
    {
        schedulesFactory.register("noFuncJob", 600000L, 100000L, () -> "result");
        schedulesFactory.functions.remove("noFuncJob");

        schedulesFactory.trigger("noFuncJob", true);

        Assertions.assertEquals(0, schedulesFactory.instancesStore.getAll().size());
    }

    @Test
    public void testExecuteWithException()
    {
        schedulesFactory.register("errorJob", 600000L, 100000L, () -> {
            throw new RuntimeException("Test exception");
        });

        schedulesFactory.trigger("errorJob", true);

        Assertions.assertEquals(1, schedulesFactory.instancesStore.getAll().size());
    }

}
