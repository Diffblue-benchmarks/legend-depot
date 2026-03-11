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

import org.finos.legend.depot.services.api.notifications.queue.QueueManagerConfiguration;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.notifications.NotificationsQueueManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;

public class TestNotificationsQueueSchedulesModule
{

    private final SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    private final NotificationsQueueManager notificationsManager = mock(NotificationsQueueManager.class);
    private final NotificationsQueueSchedulesModule module = new NotificationsQueueSchedulesModule();

    @Test
    public void canInitQueueWithSingleWorker()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();
        config.setNumberOfQueueWorkers(1);
        config.setQueueDelay(1000L);
        config.setQueueInterval(2000L);

        boolean result = module.initQueue(schedulesFactory, config, notificationsManager);

        Assertions.assertTrue(result);
        verify(schedulesFactory, times(1)).register(eq("queue-observer_1"), eq(1000L), eq(2000L), any());
    }

    @Test
    public void canInitQueueWithMultipleWorkers()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();
        config.setNumberOfQueueWorkers(3);
        config.setQueueDelay(500L);
        config.setQueueInterval(1000L);

        boolean result = module.initQueue(schedulesFactory, config, notificationsManager);

        Assertions.assertTrue(result);
        verify(schedulesFactory, times(3)).register(anyString(), eq(500L), eq(1000L), any());
        verify(schedulesFactory).register(eq("queue-observer_1"), eq(500L), eq(1000L), any());
        verify(schedulesFactory).register(eq("queue-observer_2"), eq(500L), eq(1000L), any());
        verify(schedulesFactory).register(eq("queue-observer_3"), eq(500L), eq(1000L), any());
    }

    @Test
    public void initQueueThrowsForZeroWorkers()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();
        config.setNumberOfQueueWorkers(0);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                module.initQueue(schedulesFactory, config, notificationsManager));
    }

    @Test
    public void initQueueThrowsForNegativeWorkers()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();
        config.setNumberOfQueueWorkers(-1);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                module.initQueue(schedulesFactory, config, notificationsManager));
    }

    @Test
    public void canConfigureModule()
    {
        Assertions.assertNotNull(module);
    }
}
