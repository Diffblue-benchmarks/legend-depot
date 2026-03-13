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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestNotificationsQueueSchedulesModule
{
    private NotificationsQueueSchedulesModule module;
    private SchedulesFactory schedulesFactory;
    private QueueManagerConfiguration config;
    private NotificationsQueueManager notificationsManager;

    @BeforeEach
    public void setUp()
    {
        module = new NotificationsQueueSchedulesModule();
        schedulesFactory = mock(SchedulesFactory.class);
        config = new QueueManagerConfiguration();
        notificationsManager = mock(NotificationsQueueManager.class);
    }

    @Test
    public void initQueueWithZeroWorkersShouldThrowException()
    {
        config.setNumberOfQueueWorkers(0);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> module.initQueue(schedulesFactory, config, notificationsManager)
        );

        Assertions.assertEquals("Number of queue workers must be a positive number >1 ", exception.getMessage());
    }

    @Test
    public void initQueueWithNegativeWorkersShouldThrowException()
    {
        config.setNumberOfQueueWorkers(-1);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> module.initQueue(schedulesFactory, config, notificationsManager)
        );

        Assertions.assertEquals("Number of queue workers must be a positive number >1 ", exception.getMessage());
    }

    @Test
    public void initQueueWithOneWorker()
    {
        config.setNumberOfQueueWorkers(1);
        config.setQueueDelay(5000L);
        config.setQueueInterval(10000L);

        boolean result = module.initQueue(schedulesFactory, config, notificationsManager);

        Assertions.assertTrue(result);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> delayCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> intervalCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Supplier> taskCaptor = ArgumentCaptor.forClass(Supplier.class);

        verify(schedulesFactory, times(1)).register(
                nameCaptor.capture(),
                delayCaptor.capture(),
                intervalCaptor.capture(),
                taskCaptor.capture()
        );

        Assertions.assertEquals("queue-observer_1", nameCaptor.getValue());
        Assertions.assertEquals(5000L, delayCaptor.getValue());
        Assertions.assertEquals(10000L, intervalCaptor.getValue());
    }

    @Test
    public void initQueueWithMultipleWorkers()
    {
        config.setNumberOfQueueWorkers(3);
        config.setQueueDelay(5000L);
        config.setQueueInterval(10000L);

        boolean result = module.initQueue(schedulesFactory, config, notificationsManager);

        Assertions.assertTrue(result);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> delayCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> intervalCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Supplier> taskCaptor = ArgumentCaptor.forClass(Supplier.class);

        verify(schedulesFactory, times(3)).register(
                nameCaptor.capture(),
                delayCaptor.capture(),
                intervalCaptor.capture(),
                taskCaptor.capture()
        );

        Assertions.assertEquals("queue-observer_1", nameCaptor.getAllValues().get(0));
        Assertions.assertEquals("queue-observer_2", nameCaptor.getAllValues().get(1));
        Assertions.assertEquals("queue-observer_3", nameCaptor.getAllValues().get(2));

        for (int i = 0; i < 3; i++)
        {
            Assertions.assertEquals(5000L, delayCaptor.getAllValues().get(i));
            Assertions.assertEquals(10000L, intervalCaptor.getAllValues().get(i));
        }
    }

    @Test
    public void initQueueRegistersNotificationsManagerHandle()
    {
        config.setNumberOfQueueWorkers(1);
        when(notificationsManager.handle()).thenReturn(5);

        boolean result = module.initQueue(schedulesFactory, config, notificationsManager);

        Assertions.assertTrue(result);
        ArgumentCaptor<Supplier> taskCaptor = ArgumentCaptor.forClass(Supplier.class);
        verify(schedulesFactory, times(1)).register(
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.anyLong(),
                org.mockito.ArgumentMatchers.anyLong(),
                taskCaptor.capture()
        );

        Supplier<Object> capturedTask = taskCaptor.getValue();
        Object taskResult = capturedTask.get();
        Assertions.assertEquals(5, taskResult);
        verify(notificationsManager, times(1)).handle();
    }
}
