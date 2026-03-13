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

package org.finos.legend.depot.store.resources.notifications;

import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.services.api.notifications.NotificationsService;
import org.finos.legend.depot.services.notifications.NotificationsServiceImpl;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.notifications.NotificationsMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.finos.legend.depot.domain.DatesHandler.toDate;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestNotificationsResource extends TestStoreMongo
{
    public static final String VERSION = "1.0.0";
    private NotificationsMongo notificationsMongo;
    private NotificationsService notificationsService;
    private NotificationsResource resource;

    @BeforeEach
    public void setupNotificationsResource()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);
        notificationsService = new NotificationsServiceImpl(notificationsMongo);
        resource = new NotificationsResource(notificationsService);
    }

    @Test
    public void canRetrieveEventsByDate()
    {

        MetadataNotification event1 = new MetadataNotification("testproject1", "test.com", "test", VERSION);
        MetadataNotification event2 = new MetadataNotification("testproject2", "test.comm", "test", VERSION);
        MetadataNotification event3 = new MetadataNotification("testproject3", "test.org", "test", VERSION);
        MetadataNotification event4 = new MetadataNotification("testproject4", "org.test", "test", VERSION);

        LocalDateTime aPointInTime = LocalDateTime.parse("2019-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME);
        insertRaw(notificationsMongo.COLLECTION,event1.setUpdated(toDate(aPointInTime)));
        insertRaw(notificationsMongo.COLLECTION,event2.setUpdated(toDate(aPointInTime.plusHours(1))));
        insertRaw(notificationsMongo.COLLECTION,event3.setUpdated(toDate(aPointInTime.plusHours(2))));
        insertRaw(notificationsMongo.COLLECTION,event4.setUpdated(toDate(aPointInTime.plusHours(2).plusMinutes(35))));


        List<MetadataNotification> allEvents = resource.getPastEventNotifications(null,null,null,null,null,null,aPointInTime.minusDays(100).format(DateTimeFormatter.ISO_DATE_TIME), null);
        Assertions.assertNotNull(allEvents);
        Assertions.assertEquals(4, allEvents.size());

        LocalDateTime lunchTime = LocalDateTime.parse("2019-01-01T12:00:00", DateTimeFormatter.ISO_DATE_TIME);
        List<MetadataNotification> afterLunch = resource.getPastEventNotifications(null,null,null,null,null,null,lunchTime.format(DateTimeFormatter.ISO_DATE_TIME), null);
        Assertions.assertNotNull(afterLunch);
        Assertions.assertEquals(2, afterLunch.size());

    }

    @Test
    public void canRetrieveEventsByDateAsEpocMillis()
    {

        MetadataNotification event1 = new MetadataNotification("1", "test.com", "test", VERSION);
        MetadataNotification event2 = new MetadataNotification("2", "test.com", "test1", VERSION);

        notificationsMongo.insert(event1);
        insertRaw(notificationsMongo.COLLECTION,event2.setUpdated(toDate(LocalDateTime.now().plusDays(1))));
        Assertions.assertEquals(2, notificationsMongo.getAll().size());


        List<MetadataNotification> found = resource.getPastEventNotifications(null,null,null,null,null,null,null, String.valueOf(System.currentTimeMillis()));
        Assertions.assertNotNull(found);
        Assertions.assertEquals(1, found.size());
        Assertions.assertTrue(found.stream().anyMatch(e -> e.getProjectId().equals("1")));

    }


    @Test
    public void testDeleteOldNotifications()
    {
        MetadataNotification ev1 = new MetadataNotification("prod-123","test","artifacts","1.0.0");
        ev1.setUpdated(toDate(LocalDateTime.now().minusDays(12)));
        insertRaw(NotificationsMongo.COLLECTION,ev1);
        Assertions.assertEquals(1, notificationsMongo.getAll().size());
        MetadataNotification ev2 = new MetadataNotification("prod-123","test","artifacts","2.0.0");
        notificationsMongo.createOrUpdate(ev2);

        Assertions.assertEquals(2, notificationsMongo.getAll().size());

        long deleted = notificationsService.deleteOldNotifications(10);
        Assertions.assertEquals(1,deleted);
        Assertions.assertEquals(1, notificationsMongo.getAll().size());
    }

    @Test
    public void canConstructResourceWithAuthorisation()
    {
        AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
        Provider<Principal> principalProvider = mock(Provider.class);
        NotificationsService mockService = mock(NotificationsService.class);

        NotificationsResource resourceWithAuth = new NotificationsResource(mockService, authorisationProvider, principalProvider);

        Assertions.assertNotNull(resourceWithAuth);
    }

    @Test
    public void canConstructResourceWithServiceOnly()
    {
        NotificationsService mockService = mock(NotificationsService.class);

        NotificationsResource resourceSimple = new NotificationsResource(mockService);

        Assertions.assertNotNull(resourceSimple);
    }

    @Test
    public void canGetResourceName()
    {
        String resourceName = resource.getResourceName();

        Assertions.assertNotNull(resourceName);
        Assertions.assertEquals("Notifications", resourceName);
    }

    @Test
    public void canGetPastEventNotificationsWithAllParameters()
    {
        MetadataNotification event1 = new MetadataNotification("project1", "group1", "artifact1", "1.0.0");
        event1.setEventId("event-123");
        notificationsMongo.insert(event1);

        List<MetadataNotification> results = resource.getPastEventNotifications(
                "group1",
                "artifact1",
                "1.0.0",
                "event-123",
                null,
                true,
                LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_DATE_TIME),
                LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ISO_DATE_TIME)
        );

        Assertions.assertNotNull(results);
    }

    @Test
    public void canGetPastEventNotificationsWithDefaultDates()
    {
        MetadataNotification event = new MetadataNotification("project2", "group2", "artifact2", "2.0.0");
        notificationsMongo.insert(event);

        List<MetadataNotification> results = resource.getPastEventNotifications(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Assertions.assertNotNull(results);
    }

    @Test
    public void canGetPastEventNotificationsBySuccessFilter()
    {
        MetadataNotification event1 = new MetadataNotification("project3", "group3", "artifact3", "1.0.0");
        notificationsMongo.insert(event1);

        List<MetadataNotification> results = resource.getPastEventNotifications(
                null,
                null,
                null,
                null,
                null,
                true,
                null,
                null
        );

        Assertions.assertNotNull(results);
    }

    @Test
    public void canGetNotificationById()
    {
        MetadataNotification event = new MetadataNotification("project5", "group5", "artifact5", "1.0.0");
        event.setEventId("unique-event-id");
        notificationsMongo.insert(event);

        Optional<MetadataNotification> result = resource.getNotificationById("unique-event-id");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("unique-event-id", result.get().getEventId());
        Assertions.assertEquals("project5", result.get().getProjectId());
    }

    @Test
    public void canGetNotificationByIdNotFound()
    {
        Optional<MetadataNotification> result = resource.getNotificationById("non-existent-id");

        Assertions.assertFalse(result.isPresent());
    }
}
