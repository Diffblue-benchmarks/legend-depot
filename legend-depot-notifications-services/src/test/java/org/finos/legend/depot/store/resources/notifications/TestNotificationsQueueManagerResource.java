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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

public class TestNotificationsQueueManagerResource
{
    public static final String TEST_PROJECT_ID = "PROD-1";
    public static final String TEST_GROUP_ID = "examples.metadata";
    public static final String TEST_ARTIFACT_ID = "test";
    public static final String VERSION_ID = "2.3.1";

    private final NotificationsQueueManager notificationsManager = mock(NotificationsQueueManager.class);
    private final AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    @SuppressWarnings("unchecked")
    private final Provider<Principal> principalProvider = mock(Provider.class);
    private final Queue queue = mock(Queue.class);

    private NotificationsQueueManagerResource resource;

    @BeforeEach
    public void setUp()
    {
        doNothing().when(authorisationProvider).authorise(any(), any());
        resource = new NotificationsQueueManagerResource(notificationsManager, authorisationProvider, principalProvider, queue);
    }

    @Test
    public void canGetResourceName()
    {
        Assertions.assertEquals("Notifications", resource.getResourceName());
    }

    @Test
    public void canGetAllEventsInQueue()
    {
        MetadataNotification event = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, VERSION_ID);
        when(queue.getAll()).thenReturn(Arrays.asList(event));

        List<MetadataNotification> result = resource.getAllEventsInQueue();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(authorisationProvider).authorise(any(), any());
    }

    @Test
    public void canGetAllEventsInQueueEmpty()
    {
        when(queue.getAll()).thenReturn(Collections.emptyList());

        List<MetadataNotification> result = resource.getAllEventsInQueue();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void canGetAllEventsInQueueCount()
    {
        when(queue.size()).thenReturn(5L);

        long count = resource.getAllEventsInQueueCount();
        Assertions.assertEquals(5L, count);
    }

    @Test
    public void canGetEventInQueueById()
    {
        MetadataNotification event = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, VERSION_ID);
        when(queue.get("event-1")).thenReturn(Optional.of(event));

        Optional<MetadataNotification> result = resource.geEventsInQueue("event-1");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(TEST_GROUP_ID, result.get().getGroupId());
    }

    @Test
    public void canGetEventInQueueByIdNotFound()
    {
        when(queue.get("nonexistent")).thenReturn(Optional.empty());

        Optional<MetadataNotification> result = resource.geEventsInQueue("nonexistent");
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void canQueueEvent()
    {
        when(notificationsManager.notify(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, VERSION_ID)).thenReturn("event-123");

        String eventId = resource.queueEvent(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, VERSION_ID);
        Assertions.assertEquals("event-123", eventId);
        verify(notificationsManager).notify(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, VERSION_ID);
    }

    @Test
    public void canPurgeQueue()
    {
        when(queue.deleteAll()).thenReturn(3L);

        long deleted = resource.purgeQueue();
        Assertions.assertEquals(3L, deleted);
        verify(authorisationProvider).authorise(any(), any());
        verify(queue).deleteAll();
    }
}
