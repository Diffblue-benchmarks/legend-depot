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

package org.finos.legend.depot.services.guice;

import org.finos.legend.depot.services.api.notifications.NotificationsService;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.function.Supplier;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestNotificationsSchedulesModule
{
    @Test
    public void testNotificationsCleanUpRegistersSchedule()
    {
        NotificationsSchedulesModule module = new NotificationsSchedulesModule();
        SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
        NotificationsService notificationsService = mock(NotificationsService.class);

        boolean result = module.notificationsCleanUp(schedulesFactory, notificationsService);

        Assertions.assertTrue(result);
        ArgumentCaptor<Supplier> supplierCaptor = ArgumentCaptor.forClass(Supplier.class);
        verify(schedulesFactory).register(
                eq("clean-notifications-schedule"),
                eq(SchedulesFactory.MINUTE),
                eq(1 * SchedulesFactory.HOUR),
                supplierCaptor.capture()
        );
        Assertions.assertNotNull(supplierCaptor.getValue());
    }

    @Test
    public void testNotificationsCleanUpScheduleInvokesDeleteOldNotifications()
    {
        NotificationsSchedulesModule module = new NotificationsSchedulesModule();
        SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
        NotificationsService notificationsService = mock(NotificationsService.class);

        module.notificationsCleanUp(schedulesFactory, notificationsService);

        ArgumentCaptor<Supplier> supplierCaptor = ArgumentCaptor.forClass(Supplier.class);
        verify(schedulesFactory).register(
                eq("clean-notifications-schedule"),
                eq(SchedulesFactory.MINUTE),
                eq(1 * SchedulesFactory.HOUR),
                supplierCaptor.capture()
        );

        supplierCaptor.getValue().get();
        verify(notificationsService).deleteOldNotifications(30);
    }

}
