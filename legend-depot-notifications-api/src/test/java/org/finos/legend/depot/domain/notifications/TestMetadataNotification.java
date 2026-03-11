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

package org.finos.legend.depot.domain.notifications;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestMetadataNotification
{
    @Test
    void canCreateWithFullConstructor()
    {
        Date created = new Date();
        Date updated = new Date();
        Date completed = new Date();
        Map<Integer, MetadataNotificationResponse> responses = new HashMap<>();
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessage("test message");
        responses.put(0, response);

        MetadataNotification notification = new MetadataNotification(
                "proj1", "org.test", "artifact1", "1.0.0",
                "event1", "parentEvent1", true, true,
                3, 5, responses, created, updated, completed, Priority.HIGH);

        assertEquals("proj1", notification.getProjectId());
        assertEquals("org.test", notification.getGroupId());
        assertEquals("artifact1", notification.getArtifactId());
        assertEquals("1.0.0", notification.getVersionId());
        assertEquals("event1", notification.getEventId());
        assertEquals("parentEvent1", notification.getParentEventId());
        assertTrue(notification.isFullUpdate());
        assertTrue(notification.isTransitive());
        assertEquals(3, notification.getAttempt());
        assertEquals(5, notification.getMaxAttempts());
        assertEquals(created, notification.getCreated());
        assertEquals(updated, notification.getUpdated());
        assertEquals(completed, notification.getCompleted());
        assertEquals(Priority.HIGH, notification.getEventPriority());
        assertEquals(1, notification.getResponses().size());
    }

    @Test
    void canCreateWithFullConstructorNullDefaults()
    {
        MetadataNotification notification = new MetadataNotification(
                "proj1", "org.test", "artifact1", "1.0.0",
                null, null, null, null,
                null, null, null, null, null, null, Priority.LOW);

        assertEquals(0, notification.getAttempt());
        assertEquals(2, notification.getMaxAttempts());
        assertFalse(notification.isFullUpdate());
        assertFalse(notification.isTransitive());
        assertNotNull(notification.getResponses());
        assertTrue(notification.getResponses().isEmpty());
    }

    @Test
    void canCreateWithSimpleConstructor()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");

        assertEquals("proj1", notification.getProjectId());
        assertEquals("org.test", notification.getGroupId());
        assertEquals("artifact1", notification.getArtifactId());
        assertEquals("1.0.0", notification.getVersionId());
        assertNull(notification.getEventId());
        assertFalse(notification.isFullUpdate());
        assertFalse(notification.isTransitive());
        assertEquals(Priority.LOW, notification.getEventPriority());
    }

    @Test
    void canCreateWithParentEventConstructor()
    {
        MetadataNotification notification = new MetadataNotification(
                "proj1", "org.test", "artifact1", "1.0.0", true, false, "parentEvent1");

        assertEquals("proj1", notification.getProjectId());
        assertTrue(notification.isFullUpdate());
        assertFalse(notification.isTransitive());
        assertEquals("parentEvent1", notification.getParentEventId());
        assertEquals(Priority.LOW, notification.getEventPriority());
    }

    @Test
    void canCreateWithPriorityConstructor()
    {
        MetadataNotification notification = new MetadataNotification(
                "proj1", "org.test", "artifact1", "1.0.0", false, true, "parentEvent1", Priority.HIGH);

        assertFalse(notification.isFullUpdate());
        assertTrue(notification.isTransitive());
        assertEquals(Priority.HIGH, notification.getEventPriority());
    }

    @Test
    void canCreateWithDefaultConstructor()
    {
        MetadataNotification notification = new MetadataNotification();
        assertNotNull(notification);
        assertNull(notification.getProjectId());
    }

    @Test
    void canSetAndGetId()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertNull(notification.getId());
        notification.setId("id-123");
        assertEquals("id-123", notification.getId());
    }

    @Test
    void canSetAndGetEventId()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        MetadataNotification result = notification.setEventId("event-456");
        assertEquals("event-456", notification.getEventId());
        assertEquals(notification, result);
    }

    @Test
    void canSetAndGetCompleted()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertNull(notification.getCompleted());
        Date completed = new Date();
        notification.setCompleted(completed);
        assertEquals(completed, notification.getCompleted());
    }

    @Test
    void canSetEventPriority()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertEquals(Priority.LOW, notification.getEventPriority());
        notification.setEventPriority(Priority.HIGH);
        assertEquals(Priority.HIGH, notification.getEventPriority());
    }

    @Test
    void canSetAndGetProjectId()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        MetadataNotification result = notification.setProjectId("proj2");
        assertEquals("proj2", notification.getProjectId());
        assertEquals(notification, result);
    }

    @Test
    void canGetStatus()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertEquals(MetadataNotificationStatus.SUCCESS, notification.getStatus());
    }

    @Test
    void canSetAndGetParentEventId()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        notification.setParentEventId("parent-789");
        assertEquals("parent-789", notification.getParentEventId());
    }

    @Test
    void canSetFullUpdate()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertFalse(notification.isFullUpdate());
        MetadataNotification result = notification.setFullUpdate(true);
        assertTrue(notification.isFullUpdate());
        assertEquals(notification, result);
    }

    @Test
    void canSetTransitive()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertFalse(notification.isTransitive());
        notification.setTransitive(true);
        assertTrue(notification.isTransitive());
    }

    @Test
    void canSetAndGetCreated()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        Date created = new Date();
        notification.setCreated(created);
        assertEquals(created, notification.getCreated());
    }

    @Test
    void canSetMaxAttempts()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertEquals(2, notification.getMaxAttempts());
        notification.setMaxAttempts(10);
        assertEquals(10, notification.getMaxAttempts());
    }

    @Test
    void canComplete()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertNull(notification.getCompleted());
        MetadataNotification result = notification.complete();
        assertNotNull(notification.getCompleted());
        assertEquals(notification, result);
    }

    @Test
    void canIncreaseAttempts()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertEquals(0, notification.getAttempt());
        MetadataNotification result = notification.increaseAttempts();
        assertEquals(1, notification.getAttempt());
        assertEquals(notification, result);
    }

    @Test
    void canCheckRetriesExceeded()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertFalse(notification.retriesExceeded());
        notification.increaseAttempts();
        assertFalse(notification.retriesExceeded());
        notification.increaseAttempts();
        assertTrue(notification.retriesExceeded());
    }

    @Test
    void canGetResponsesWhenNull()
    {
        MetadataNotification notification = new MetadataNotification();
        Map<Integer, MetadataNotificationResponse> responses = notification.getResponses();
        assertNotNull(responses);
    }

    @Test
    void canSetResponses()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        Map<Integer, MetadataNotificationResponse> responses = new HashMap<>();
        responses.put(0, new MetadataNotificationResponse());
        notification.setResponses(responses);
        assertEquals(1, notification.getResponses().size());
    }

    @Test
    void canAddError()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        MetadataNotification result = notification.addError("something failed");
        assertEquals(notification, result);
        assertEquals(MetadataNotificationStatus.FAILED, notification.getStatus());
    }

    @Test
    void canSetResponse()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessage("done");
        notification.setResponse(response);
        assertEquals(response, notification.getResponses().get(0));
    }

    @Test
    void canCombineResponse()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessage("msg1");
        MetadataNotification result = notification.combineResponse(response);
        assertEquals(notification, result);
    }

    @Test
    void canCombineNullResponse()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        MetadataNotification result = notification.combineResponse(null);
        assertEquals(notification, result);
    }

    @Test
    void canGetCurrentResponse()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertNull(notification.getCurrentResponse());
        notification.addError("error");
        assertNotNull(notification.getCurrentResponse());
    }

    @Test
    void canSetAndGetUpdated()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        Date updated = new Date();
        MetadataNotification result = notification.setUpdated(updated);
        assertEquals(updated, notification.getUpdated());
        assertEquals(notification, result);
    }

    @Test
    void canSetAndGetAttempt()
    {
        MetadataNotification notification = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        MetadataNotification result = notification.setAttempt(5);
        assertEquals(5, notification.getAttempt());
        assertEquals(notification, result);
    }

    @Test
    void canTestEquals()
    {
        MetadataNotification n1 = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        MetadataNotification n2 = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertEquals(n1, n2);
    }

    @Test
    void canTestHashCode()
    {
        MetadataNotification n1 = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        MetadataNotification n2 = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        assertEquals(n1.hashCode(), n2.hashCode());
    }

    @Test
    void canTestNotEquals()
    {
        MetadataNotification n1 = new MetadataNotification("proj1", "org.test", "artifact1", "1.0.0");
        MetadataNotification n2 = new MetadataNotification("proj2", "org.test", "artifact1", "2.0.0");
        assertNotEquals(n1, n2);
    }
}
