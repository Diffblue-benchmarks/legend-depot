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
import org.finos.legend.depot.domain.notifications.MetadataNotificationStatus;
import org.finos.legend.depot.services.api.notifications.NotificationHandler;
import org.finos.legend.depot.store.api.projects.UpdateProjects;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.notifications.NotificationsMongo;
import org.finos.legend.depot.store.mongo.notifications.queue.NotificationsQueueMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestNotificationsQueueManagerAdditional extends TestStoreMongo
{
    public static final String TEST_GROUP_ID = "examples.metadata";
    public static final String TEST_PROJECT_ID = "PROD-1";
    public static final String VERSION_ID = "2.3.1";

    protected UpdateProjects projectsStore = new ProjectsMongo(mongoProvider);
    private final NotificationsMongo notifications = new NotificationsMongo(mongoProvider);
    private final NotificationsQueueMongo queue = new NotificationsQueueMongo(mongoProvider);
    private final NotificationHandler notificationEventHandler = mock(NotificationHandler.class);
    private final NotificationsQueueManager eventsManager = new NotificationsQueueManager(notifications, queue, notificationEventHandler);

    @BeforeEach
    public void setUpData()
    {
        projectsStore.createOrUpdate(new StoreProjectData(TEST_PROJECT_ID, TEST_GROUP_ID, "test"));
        when(notificationEventHandler.validate(any(MetadataNotification.class))).thenReturn(Collections.emptyList());
        when(notificationEventHandler.handleNotification(any(MetadataNotification.class))).thenReturn(new MetadataNotificationResponse());
    }

    @Test
    public void handleReturnsZeroWhenQueueIsEmpty()
    {
        int result = eventsManager.handle();
        Assertions.assertEquals(0, result);
    }

    @Test
    public void handleEventCatchesExceptionFromHandler()
    {
        when(notificationEventHandler.handleNotification(any(MetadataNotification.class))).thenThrow(new RuntimeException("handler error"));

        MetadataNotification event = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, "test", VERSION_ID);
        event.setMaxAttempts(1);
        queue.push(event);

        eventsManager.handle();

        Assertions.assertTrue(queue.getAll().isEmpty());
        Assertions.assertFalse(notifications.getAll().isEmpty());
        MetadataNotification notification = notifications.getAll().get(0);
        Assertions.assertEquals(MetadataNotificationStatus.FAILED, notification.getStatus());
    }

    @Test
    public void handleEventCatchesExceptionAndRetriesWhenRetriesNotExceeded()
    {
        when(notificationEventHandler.handleNotification(any(MetadataNotification.class))).thenThrow(new RuntimeException("handler error"));

        MetadataNotification event = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, "test", VERSION_ID);
        queue.push(event);

        eventsManager.handle();

        Assertions.assertFalse(queue.getAll().isEmpty());
        MetadataNotification retryEvent = queue.getAll().get(0);
        Assertions.assertTrue(retryEvent.isFullUpdate());
    }

    @Test
    public void notifyPushesEventToQueueWhenValid()
    {
        String eventId = eventsManager.notify(TEST_PROJECT_ID, TEST_GROUP_ID, "test", VERSION_ID);
        Assertions.assertNotNull(eventId);
        Assertions.assertFalse(queue.getAll().isEmpty());
    }

    @Test
    public void notifyThrowsWhenValidationFails()
    {
        when(notificationEventHandler.validate(any(MetadataNotification.class))).thenReturn(Arrays.asList("bad version"));

        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            eventsManager.notify(TEST_PROJECT_ID, TEST_GROUP_ID, "test", "10.0.0");
        });
    }

    @Test
    public void handleAllProcessesAllEventsInQueue()
    {
        MetadataNotification event1 = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, "test", VERSION_ID);
        MetadataNotification event2 = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, "test", "3.0.0");
        queue.push(event1);
        queue.push(event2);

        eventsManager.handleAll();

        Assertions.assertTrue(queue.getAll().isEmpty());
        Assertions.assertEquals(2, notifications.getAll().size());
    }

    @Test
    public void handleAllDoesNothingWhenQueueIsEmpty()
    {
        eventsManager.handleAll();
        Assertions.assertTrue(queue.getAll().isEmpty());
        Assertions.assertTrue(notifications.getAll().isEmpty());
    }
}
