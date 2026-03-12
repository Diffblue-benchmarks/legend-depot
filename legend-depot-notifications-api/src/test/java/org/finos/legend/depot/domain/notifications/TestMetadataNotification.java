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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMetadataNotification
{
    @Test
    public void testSimpleConstructor()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("PROD-1", notification.getProjectId());
        Assertions.assertEquals("org.finos", notification.getGroupId());
        Assertions.assertEquals("artifact", notification.getArtifactId());
        Assertions.assertEquals("1.0.0", notification.getVersionId());
        Assertions.assertEquals(0, notification.getAttempt());
        Assertions.assertEquals(2, notification.getMaxAttempts());
        Assertions.assertFalse(notification.isFullUpdate());
        Assertions.assertFalse(notification.isTransitive());
    }

    @Test
    public void testStatusDefaultsToSuccess()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        Assertions.assertEquals(MetadataNotificationStatus.SUCCESS, notification.getStatus());
    }

    @Test
    public void testAddErrorChangesStatusToFailed()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        notification.addError("something failed");
        Assertions.assertEquals(MetadataNotificationStatus.FAILED, notification.getStatus());
    }

    @Test
    public void testIncreaseAttempts()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        Assertions.assertEquals(0, notification.getAttempt());
        notification.increaseAttempts();
        Assertions.assertEquals(1, notification.getAttempt());
    }

    @Test
    public void testRetriesExceeded()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        Assertions.assertFalse(notification.retriesExceeded());
        notification.increaseAttempts();
        Assertions.assertFalse(notification.retriesExceeded());
        notification.increaseAttempts();
        Assertions.assertTrue(notification.retriesExceeded());
    }

    @Test
    public void testComplete()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        Assertions.assertNull(notification.getCompleted());
        notification.complete();
        Assertions.assertNotNull(notification.getCompleted());
    }

    @Test
    public void testCombineResponse()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessage("info");
        response.addError("error");

        notification.combineResponse(response);

        MetadataNotificationResponse current = notification.getCurrentResponse();
        Assertions.assertNotNull(current);
        Assertions.assertEquals(1, current.getMessages().size());
        Assertions.assertEquals(1, current.getErrors().size());
    }

    @Test
    public void testCombineNullResponseIsNoOp()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        notification.combineResponse(null);
        Assertions.assertNotNull(notification.getResponses());
    }

    @Test
    public void testSetAndGetEventPriority()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        notification.setEventPriority(Priority.HIGH);
        Assertions.assertEquals(Priority.HIGH, notification.getEventPriority());
    }

    @Test
    public void testConstructorWithFullUpdateAndTransitive()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0", true, true, "parent-event-1");
        Assertions.assertTrue(notification.isFullUpdate());
        Assertions.assertTrue(notification.isTransitive());
        Assertions.assertEquals("parent-event-1", notification.getParentEventId());
    }

    @Test
    public void testConstructorWithPriority()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0", false, false, null, Priority.HIGH);
        Assertions.assertEquals(Priority.HIGH, notification.getEventPriority());
    }

    @Test
    public void testSetResponse()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessage("done");
        notification.setResponse(response);

        Assertions.assertEquals(response, notification.getCurrentResponse());
    }

    @Test
    public void testSetMaxAttempts()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        notification.setMaxAttempts(5);
        Assertions.assertEquals(5, notification.getMaxAttempts());
    }

    @Test
    public void testFluentSetters()
    {
        MetadataNotification notification = new MetadataNotification();
        notification.setProjectId("PROD-1")
                .setFullUpdate(true)
                .setEventId("event-1")
                .setAttempt(3);

        Assertions.assertEquals("PROD-1", notification.getProjectId());
        Assertions.assertTrue(notification.isFullUpdate());
        Assertions.assertEquals("event-1", notification.getEventId());
        Assertions.assertEquals(3, notification.getAttempt());
    }
}
