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
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.notifications.NotificationsQueueManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class TestNotificationsQueueManagerResource
{
    private static final String TEST_PROJECT_ID = "PROD-1";
    private static final String TEST_GROUP_ID = "examples.metadata";
    private static final String TEST_ARTIFACT_ID = "test";
    private static final String VERSION_ID = "2.3.1";
    private static final String EVENT_ID = "event-123";

    private NotificationsQueueManager notificationsManager;
    private AuthorisationProvider authorisationProvider;
    private Provider<Principal> principalProvider;
    private Queue queue;
    private NotificationsQueueManagerResource resource;

    @BeforeEach
    public void setUp()
    {
        notificationsManager = mock(NotificationsQueueManager.class);
        authorisationProvider = mock(AuthorisationProvider.class);
        principalProvider = mock(Provider.class);
        queue = mock(Queue.class);
        resource = new NotificationsQueueManagerResource(notificationsManager, authorisationProvider, principalProvider, queue);
    }

    @Test
    public void canCreateResource()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetResourceName()
    {
        String resourceName = resource.getResourceName();
        Assertions.assertEquals("Notifications", resourceName);
    }

    @Test
    public void canGetAllEventsInQueue()
    {
        MetadataNotification event1 = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, VERSION_ID);
        MetadataNotification event2 = new MetadataNotification("PROD-2", "test.group", "artifact", "1.0.0");
        List<MetadataNotification> mockEvents = Arrays.asList(event1, event2);
        when(queue.getAll()).thenReturn(mockEvents);

        List<MetadataNotification> result = resource.getAllEventsInQueue();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        verify(queue).getAll();
    }

    @Test
    public void canGetAllEventsInQueueCount()
    {
        when(queue.size()).thenReturn(5L);

        long count = resource.getAllEventsInQueueCount();

        Assertions.assertEquals(5L, count);
        verify(queue).size();
    }

    @Test
    public void canGetEventInQueue()
    {
        MetadataNotification event = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, VERSION_ID);
        when(queue.get(EVENT_ID)).thenReturn(Optional.of(event));

        Optional<MetadataNotification> result = resource.geEventsInQueue(EVENT_ID);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(TEST_PROJECT_ID, result.get().getProjectId());
        verify(queue).get(EVENT_ID);
    }

    @Test
    public void canGetEventInQueueWhenNotFound()
    {
        when(queue.get(EVENT_ID)).thenReturn(Optional.empty());

        Optional<MetadataNotification> result = resource.geEventsInQueue(EVENT_ID);

        Assertions.assertFalse(result.isPresent());
        verify(queue).get(EVENT_ID);
    }

    @Test
    public void canQueueEvent()
    {
        when(notificationsManager.notify(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, VERSION_ID)).thenReturn(EVENT_ID);

        String result = resource.queueEvent(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, VERSION_ID);

        Assertions.assertEquals(EVENT_ID, result);
        verify(notificationsManager).notify(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, VERSION_ID);
    }

    @Test
    public void canPurgeQueue()
    {
        when(queue.deleteAll()).thenReturn(10L);

        long deletedCount = resource.purgeQueue();

        Assertions.assertEquals(10L, deletedCount);
        verify(queue).deleteAll();
    }
}
