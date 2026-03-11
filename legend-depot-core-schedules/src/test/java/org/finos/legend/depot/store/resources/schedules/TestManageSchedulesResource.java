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
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInstance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Provider;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestManageSchedulesResource
{
    private ManageSchedulesResource resource;
    private SchedulesFactory schedulesFactory;
    private SchedulesStore schedulesStore;
    private ScheduleInstancesStore instancesStore;

    @BeforeEach
    public void setUp()
    {
        AuthorisationProvider authProvider = (principalProvider, role) -> { };
        Provider<Principal> principalProvider = () -> null;
        schedulesFactory = Mockito.mock(SchedulesFactory.class);
        schedulesStore = Mockito.mock(SchedulesStore.class);
        instancesStore = Mockito.mock(ScheduleInstancesStore.class);
        resource = new ManageSchedulesResource(authProvider, principalProvider, schedulesFactory, schedulesStore, instancesStore);
    }

    @Test
    public void testGetResourceName()
    {
        Assertions.assertEquals("Schedules", resource.getResourceName());
    }

    @Test
    public void testGetSchedulerStatusReturnsEnabledSchedules()
    {
        ScheduleInfo enabled = new ScheduleInfo("job1");
        enabled.disabled = false;
        ScheduleInfo disabled = new ScheduleInfo("job2");
        disabled.disabled = true;
        when(schedulesStore.getAll()).thenReturn(Arrays.asList(enabled, disabled));

        List<ScheduleInfo> result = resource.getSchedulerStatus(false);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("job1", result.get(0).name);
    }

    @Test
    public void testGetSchedulerStatusReturnsDisabledSchedules()
    {
        ScheduleInfo enabled = new ScheduleInfo("job1");
        enabled.disabled = false;
        ScheduleInfo disabled = new ScheduleInfo("job2");
        disabled.disabled = true;
        when(schedulesStore.getAll()).thenReturn(Arrays.asList(enabled, disabled));

        List<ScheduleInfo> result = resource.getSchedulerStatus(true);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("job2", result.get(0).name);
    }

    @Test
    public void testGetSchedulerInstances()
    {
        ScheduleInstance instance = Mockito.mock(ScheduleInstance.class);
        when(instancesStore.getAll()).thenReturn(Collections.singletonList(instance));

        List<ScheduleInstance> result = resource.getSchedulerInstances();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testForceScheduler()
    {
        doNothing().when(schedulesFactory).trigger("testJob", false);

        Response response = resource.forceScheduler("testJob", false);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(204, response.getStatus());
        verify(schedulesFactory).trigger("testJob", false);
    }

    @Test
    public void testForceSchedulerWithForceRun()
    {
        doNothing().when(schedulesFactory).trigger("testJob", true);

        Response response = resource.forceScheduler("testJob", true);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(204, response.getStatus());
        verify(schedulesFactory).trigger("testJob", true);
    }

    @Test
    public void testDeleteScheduler()
    {
        doNothing().when(schedulesFactory).deRegister("testJob");

        Response response = resource.deleteScheduler("testJob");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(204, response.getStatus());
        verify(schedulesFactory).deRegister("testJob");
    }

    @Test
    public void testDeleteSchedules()
    {
        doNothing().when(schedulesFactory).deRegisterAll();

        Response response = resource.deleteSchedules();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(204, response.getStatus());
        verify(schedulesFactory).deRegisterAll();
    }

    @Test
    public void testToggleSchedulerByName()
    {
        doNothing().when(schedulesFactory).toggleDisable("testJob", true);

        Response response = resource.toggleScheduler("testJob", true);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(204, response.getStatus());
        verify(schedulesFactory).toggleDisable("testJob", true);
    }

    @Test
    public void testToggleAllSchedulers()
    {
        doNothing().when(schedulesFactory).toggleDisableAll(true);

        Response response = resource.toggleScheduler(true);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(204, response.getStatus());
        verify(schedulesFactory).toggleDisableAll(true);
    }
}
