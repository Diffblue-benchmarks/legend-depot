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

package org.finos.legend.depot.services.notifications;

import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.domain.notifications.MetadataNotificationResponse;
import org.finos.legend.depot.domain.notifications.Priority;
import org.finos.legend.depot.services.api.notifications.NotificationHandler;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.store.api.notifications.Notifications;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NotificationsQueueManagerTest
{
    private Notifications mockNotifications;
    private Queue mockQueue;
    private NotificationHandler mockHandler;
    private NotificationsQueueManager manager;

    @BeforeEach
    public void setup()
    {
        mockNotifications = Mockito.mock(Notifications.class);
        mockQueue = Mockito.mock(Queue.class);
        mockHandler = Mockito.mock(NotificationHandler.class);
        manager = new NotificationsQueueManager(mockNotifications, mockQueue, mockHandler);
    }

    @Test
    public void testHandleEventWithValidationErrors()
    {
        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        event.setCreated(new Date());
        when(mockHandler.validate(event)).thenReturn(Arrays.asList("invalid group", "invalid artifact"));

        manager.handleEvent(event);

        verify(mockNotifications).createOrUpdate(any(MetadataNotification.class));
        verify(mockHandler, never()).handleNotification(any());
    }

    @Test
    public void testHandleEventSuccess()
    {
        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0", false, false, null, Priority.HIGH);
        event.setCreated(new Date());
        when(mockHandler.validate(event)).thenReturn(Collections.emptyList());
        when(mockHandler.handleNotification(any())).thenReturn(new MetadataNotificationResponse());

        manager.handleEvent(event);

        verify(mockNotifications).createOrUpdate(any(MetadataNotification.class));
    }

    @Test
    public void testHandleEventWithErrorAndRetry()
    {
        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0", false, false, null, Priority.HIGH);
        event.setCreated(new Date());
        when(mockHandler.validate(event)).thenReturn(Collections.emptyList());
        MetadataNotificationResponse errorResponse = new MetadataNotificationResponse();
        errorResponse.addError("processing failed");
        when(mockHandler.handleNotification(any())).thenReturn(errorResponse);

        manager.handleEvent(event);

        verify(mockQueue).push(any(MetadataNotification.class));
        verify(mockNotifications, never()).createOrUpdate(any(MetadataNotification.class));
    }

    @Test
    public void testHandleEventWithErrorAndRetriesExceeded()
    {
        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0", false, false, null, Priority.HIGH);
        event.setCreated(new Date());
        event.setAttempt(1);
        when(mockHandler.validate(event)).thenReturn(Collections.emptyList());
        MetadataNotificationResponse errorResponse = new MetadataNotificationResponse();
        errorResponse.addError("processing failed");
        when(mockHandler.handleNotification(any())).thenReturn(errorResponse);

        manager.handleEvent(event);

        verify(mockNotifications).createOrUpdate(any(MetadataNotification.class));
    }

    @Test
    public void testNotifyWithValidEvent()
    {
        when(mockHandler.validate(any())).thenReturn(Collections.emptyList());
        when(mockQueue.push(any())).thenReturn("event-123");

        String eventId = manager.notify("PROD-1", "org.finos", "legend-depot", "1.0.0");

        Assertions.assertEquals("event-123", eventId);
        verify(mockQueue).push(any(MetadataNotification.class));
    }

    @Test
    public void testNotifyWithInvalidEventThrows()
    {
        when(mockHandler.validate(any())).thenReturn(Arrays.asList("validation error"));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> manager.notify("PROD-1", "org.finos", "legend-depot", "1.0.0"));
    }

    @Test
    public void testHandleWithEmptyQueue()
    {
        when(mockQueue.size()).thenReturn(0L);
        when(mockQueue.getFirstInQueue()).thenReturn(Optional.empty());

        int processed = manager.handle();

        Assertions.assertEquals(0, processed);
    }

    @Test
    public void testHandleEventWithExceptionDuringProcessing()
    {
        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0", false, false, null, Priority.HIGH);
        event.setCreated(new Date());
        when(mockHandler.validate(event)).thenReturn(Collections.emptyList());
        when(mockHandler.handleNotification(any())).thenThrow(new RuntimeException("unexpected error"));

        manager.handleEvent(event);

        verify(mockQueue).push(any(MetadataNotification.class));
    }
}
