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
import org.finos.legend.depot.store.api.notifications.Notifications;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class TestNotificationsServiceImpl extends TestStoreMongo
{
    private static final String TEST_GROUP_ID = "examples.metadata";
    private static final String TEST_ARTIFACT_ID = "test-artifact";
    private static final String TEST_VERSION = "1.0.0";
    private static final String TEST_EVENT_ID = "event-123";
    private static final String TEST_PROJECT_ID = "PROD-1";
    private static final String TEST_PARENT_ID = "parent-123";

    private Notifications notifications;
    private NotificationsServiceImpl notificationsService;

    @BeforeEach
    public void setUp()
    {
        notifications = mock(Notifications.class);
        notificationsService = new NotificationsServiceImpl(notifications);
    }

    @Test
    public void testConstructor()
    {
        Notifications mockNotifications = mock(Notifications.class);
        NotificationsServiceImpl service = new NotificationsServiceImpl(mockNotifications);
        Assertions.assertNotNull(service);
    }

    @Test
    public void canFindProcessedEvents()
    {
        MetadataNotification notification = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);
        notification.setEventId(TEST_EVENT_ID);
        List<MetadataNotification> expectedNotifications = Arrays.asList(notification);

        when(notifications.find(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, TEST_EVENT_ID, TEST_PARENT_ID, true, null, null))
                .thenReturn(expectedNotifications);

        List<MetadataNotification> result = notificationsService.findProcessedEvents(
                TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, TEST_EVENT_ID, TEST_PARENT_ID, true, null, null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(TEST_EVENT_ID, result.get(0).getEventId());
        verify(notifications).find(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, TEST_EVENT_ID, TEST_PARENT_ID, true, null, null);
    }

    @Test
    public void canFindProcessedEventsWithDateRange()
    {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 12, 31, 23, 59);
        MetadataNotification notification = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);
        List<MetadataNotification> expectedNotifications = Arrays.asList(notification);

        when(notifications.find(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, null, null, null, from, to))
                .thenReturn(expectedNotifications);

        List<MetadataNotification> result = notificationsService.findProcessedEvents(
                TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, null, null, null, from, to);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(notifications).find(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, null, null, null, from, to);
    }

    @Test
    public void canFindProcessedEventsWithNullParameters()
    {
        List<MetadataNotification> expectedNotifications = Collections.emptyList();

        when(notifications.find(null, null, null, null, null, null, null, null))
                .thenReturn(expectedNotifications);

        List<MetadataNotification> result = notificationsService.findProcessedEvents(
                null, null, null, null, null, null, null, null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
        verify(notifications).find(null, null, null, null, null, null, null, null);
    }

    @Test
    public void canGetProcessedEvent()
    {
        MetadataNotification notification = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);
        notification.setEventId(TEST_EVENT_ID);

        when(notifications.get(TEST_EVENT_ID)).thenReturn(Optional.of(notification));

        Optional<MetadataNotification> result = notificationsService.getProcessedEvent(TEST_EVENT_ID);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(TEST_EVENT_ID, result.get().getEventId());
        verify(notifications).get(TEST_EVENT_ID);
    }

    @Test
    public void canGetProcessedEventNotFound()
    {
        when(notifications.get(TEST_EVENT_ID)).thenReturn(Optional.empty());

        Optional<MetadataNotification> result = notificationsService.getProcessedEvent(TEST_EVENT_ID);

        Assertions.assertFalse(result.isPresent());
        verify(notifications).get(TEST_EVENT_ID);
    }

    @Test
    public void canDeleteOldNotifications()
    {
        MetadataNotification notification1 = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, "1.0.0");
        notification1.setId("id-1");
        MetadataNotification notification2 = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.0.0");
        notification2.setId("id-2");
        List<MetadataNotification> oldNotifications = Arrays.asList(notification1, notification2);

        when(notifications.find(
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.any(LocalDateTime.class)))
                .thenReturn(oldNotifications);

        long result = notificationsService.deleteOldNotifications(30);

        Assertions.assertEquals(2, result);
        verify(notifications).delete("id-1");
        verify(notifications).delete("id-2");
    }

    @Test
    public void canDeleteOldNotificationsWithNoneFound()
    {
        List<MetadataNotification> emptyList = Collections.emptyList();

        when(notifications.find(
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.any(LocalDateTime.class)))
                .thenReturn(emptyList);

        long result = notificationsService.deleteOldNotifications(30);

        Assertions.assertEquals(0, result);
        verify(notifications, times(0)).delete(org.mockito.ArgumentMatchers.anyString());
    }

    @Test
    public void canDeleteOldNotificationsWithSpecificDays()
    {
        MetadataNotification notification = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);
        notification.setId("old-id");
        List<MetadataNotification> oldNotifications = Arrays.asList(notification);

        when(notifications.find(
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.isNull(),
                org.mockito.ArgumentMatchers.any(LocalDateTime.class)))
                .thenReturn(oldNotifications);

        long result = notificationsService.deleteOldNotifications(90);

        Assertions.assertEquals(1, result);
        verify(notifications).delete("old-id");
    }
}
