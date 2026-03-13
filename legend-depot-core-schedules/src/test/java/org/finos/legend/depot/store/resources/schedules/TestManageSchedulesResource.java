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

package org.finos.legend.depot.store.resources.schedules;

import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.core.services.authorisation.BasicAuthorisationProvider;
import org.finos.legend.depot.services.schedules.SchedulesFactoryImpl;
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestManageSchedulesResource
{
    private ManageSchedulesResource resource;
    private SchedulesFactoryImpl schedulesFactory;
    private MockSchedulesStore schedulesStore;
    private MockScheduleInstancesStore scheduleInstancesStore;
    private AuthorisationProvider authorisationProvider;
    private Provider<Principal> principalProvider;

    @BeforeEach
    public void setUp()
    {
        schedulesStore = new MockSchedulesStore();
        scheduleInstancesStore = new MockScheduleInstancesStore();
        schedulesFactory = new SchedulesFactoryImpl(schedulesStore, scheduleInstancesStore, false);
        authorisationProvider = new BasicAuthorisationProvider();
        principalProvider = () -> () -> "test";
        resource = new ManageSchedulesResource(authorisationProvider, principalProvider, schedulesFactory, schedulesStore, scheduleInstancesStore);
    }

    @AfterEach
    public void tearDown()
    {
        schedulesFactory.deRegisterAll();
    }

    @Test
    public void testConstructor()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void testGetResourceName()
    {
        String resourceName = resource.getResourceName();

        Assertions.assertNotNull(resourceName);
        Assertions.assertEquals(ManageSchedulesResource.SCHEDULES_RESOURCE, resourceName);
    }

    @Test
    public void testGetSchedulerStatusAll()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");
        schedulesFactory.register("job2", 600000, 100000, () -> "test job 2");

        List<ScheduleInfo> schedules = resource.getSchedulerStatus(null);

        Assertions.assertNotNull(schedules);
        Assertions.assertEquals(2, schedules.size());
    }

    @Test
    public void testGetSchedulerStatusDisabled()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");
        schedulesFactory.register("job2", 600000, 100000, () -> "test job 2");
        schedulesFactory.toggleDisable("job1", true);

        List<ScheduleInfo> disabledSchedules = resource.getSchedulerStatus(true);

        Assertions.assertNotNull(disabledSchedules);
        Assertions.assertEquals(1, disabledSchedules.size());
        Assertions.assertTrue(disabledSchedules.stream().allMatch(s -> s.disabled));
    }

    @Test
    public void testGetSchedulerStatusEnabled()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");
        schedulesFactory.register("job2", 600000, 100000, () -> "test job 2");
        schedulesFactory.toggleDisable("job1", true);

        List<ScheduleInfo> enabledSchedules = resource.getSchedulerStatus(false);

        Assertions.assertNotNull(enabledSchedules);
        Assertions.assertEquals(1, enabledSchedules.size());
        Assertions.assertTrue(enabledSchedules.stream().allMatch(s -> !s.disabled));
    }

    @Test
    public void testGetSchedulerInstances()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");
        schedulesFactory.run("job1");
        schedulesFactory.run("job1");

        List<ScheduleInstance> instances = resource.getSchedulerInstances();

        Assertions.assertNotNull(instances);
        Assertions.assertEquals(2, instances.size());
    }

    @Test
    public void testForceScheduler()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");

        Response response = resource.forceScheduler("job1", false);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        Assertions.assertEquals(1, scheduleInstancesStore.getAll().size());
    }

    @Test
    public void testForceSchedulerWithForceRun()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");
        schedulesFactory.toggleDisable("job1", true);

        Response response = resource.forceScheduler("job1", true);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        Assertions.assertEquals(1, scheduleInstancesStore.getAll().size());
    }

    @Test
    public void testDeleteScheduler()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");
        schedulesFactory.register("job2", 600000, 100000, () -> "test job 2");
        Assertions.assertEquals(2, schedulesStore.getAll().size());

        Response response = resource.deleteScheduler("job1");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        Assertions.assertEquals(1, schedulesStore.getAll().size());
        Assertions.assertFalse(schedulesStore.getAll().stream().anyMatch(s -> s.name.equals("job1")));
    }

    @Test
    public void testDeleteSchedules()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");
        schedulesFactory.register("job2", 600000, 100000, () -> "test job 2");
        Assertions.assertEquals(2, schedulesStore.getAll().size());

        Response response = resource.deleteSchedules();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        Assertions.assertEquals(0, schedulesStore.getAll().size());
    }

    @Test
    public void testToggleSchedulerDisable()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");
        Assertions.assertFalse(schedulesStore.get("job1").get().disabled);

        Response response = resource.toggleScheduler("job1", true);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        Assertions.assertTrue(schedulesStore.get("job1").get().disabled);
    }

    @Test
    public void testToggleSchedulerEnable()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");
        schedulesFactory.toggleDisable("job1", true);
        Assertions.assertTrue(schedulesStore.get("job1").get().disabled);

        Response response = resource.toggleScheduler("job1", false);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        Assertions.assertFalse(schedulesStore.get("job1").get().disabled);
    }

    @Test
    public void testToggleSchedulerAllDisable()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");
        schedulesFactory.register("job2", 600000, 100000, () -> "test job 2");

        Response response = resource.toggleScheduler(true);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        Assertions.assertTrue(schedulesStore.getAll().stream().allMatch(s -> s.disabled));
    }

    @Test
    public void testToggleSchedulerAllEnable()
    {
        schedulesFactory.register("job1", 600000, 100000, () -> "test job 1");
        schedulesFactory.register("job2", 600000, 100000, () -> "test job 2");
        schedulesFactory.toggleDisableAll(true);

        Response response = resource.toggleScheduler(false);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        Assertions.assertTrue(schedulesStore.getAll().stream().allMatch(s -> !s.disabled));
    }

    private static class MockSchedulesStore implements SchedulesStore
    {
        Map<String, ScheduleInfo> schedules = new HashMap<>();

        @Override
        public Optional<ScheduleInfo> get(String name)
        {
            return schedules.containsKey(name) ? Optional.of(schedules.get(name)) : Optional.empty();
        }

        @Override
        public List<ScheduleInfo> getAll()
        {
            return schedules.values().stream().collect(Collectors.toList());
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

    private static class MockScheduleInstancesStore implements ScheduleInstancesStore
    {
        List<ScheduleInstance> instances = new ArrayList<>();
        int ids = 1;

        @Override
        public void insert(ScheduleInstance instance)
        {
            instance.setId(String.valueOf(ids));
            ids++;
            instances.add(instance);
        }

        @Override
        public long delete(long l)
        {
            List<ScheduleInstance> toDeletedInstances = instances.stream().filter(i -> i.isExpired()).collect(Collectors.toList());
            instances.removeAll(toDeletedInstances);
            return toDeletedInstances.size();
        }

        @Override
        public List<ScheduleInstance> find(String scheduleName)
        {
            return instances.stream().filter(i -> i.getSchedule().equals(scheduleName)).collect(Collectors.toList());
        }

        @Override
        public List<ScheduleInstance> getAll()
        {
            return instances;
        }
    }
}
