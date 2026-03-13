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

public class MetadataNotificationTest
{
    @Test
    public void testSimpleConstructor()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("PROD-1", notification.getProjectId());
        Assertions.assertEquals("org.finos", notification.getGroupId());
        Assertions.assertEquals("legend-depot", notification.getArtifactId());
        Assertions.assertEquals("1.0.0", notification.getVersionId());
        Assertions.assertEquals(0, notification.getAttempt());
        Assertions.assertEquals(2, notification.getMaxAttempts());
        Assertions.assertFalse(notification.isFullUpdate());
        Assertions.assertFalse(notification.isTransitive());
        Assertions.assertEquals(Priority.LOW, notification.getEventPriority());
    }

    @Test
    public void testConstructorWithFullUpdateAndTransitive()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0", true, true, "parent-1");
        Assertions.assertTrue(notification.isFullUpdate());
        Assertions.assertTrue(notification.isTransitive());
        Assertions.assertEquals("parent-1", notification.getParentEventId());
    }

    @Test
    public void testConstructorWithPriority()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0", false, false, null, Priority.HIGH);
        Assertions.assertEquals(Priority.HIGH, notification.getEventPriority());
    }

    @Test
    public void testIncreaseAttempts()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals(0, notification.getAttempt());
        notification.increaseAttempts();
        Assertions.assertEquals(1, notification.getAttempt());
        notification.increaseAttempts();
        Assertions.assertEquals(2, notification.getAttempt());
    }

    @Test
    public void testRetriesExceeded()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        Assertions.assertFalse(notification.retriesExceeded());
        notification.increaseAttempts();
        Assertions.assertFalse(notification.retriesExceeded());
        notification.increaseAttempts();
        Assertions.assertTrue(notification.retriesExceeded());
    }

    @Test
    public void testComplete()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        Assertions.assertNull(notification.getCompleted());
        notification.complete();
        Assertions.assertNotNull(notification.getCompleted());
    }

    @Test
    public void testAddError()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        notification.addError("test error");
        MetadataNotificationResponse response = notification.getCurrentResponse();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.hasErrors());
        Assertions.assertEquals(1, response.getErrors().size());
        Assertions.assertEquals("test error", response.getErrors().get(0));
    }

    @Test
    public void testCombineResponse()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessage("msg1");
        response.addError("err1");

        notification.combineResponse(response);
        MetadataNotificationResponse currentResponse = notification.getCurrentResponse();
        Assertions.assertNotNull(currentResponse);
        Assertions.assertEquals(1, currentResponse.getErrors().size());
        Assertions.assertEquals(1, currentResponse.getMessages().size());
    }

    @Test
    public void testCombineNullResponse()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        notification.combineResponse(null);
        Assertions.assertNull(notification.getCurrentResponse());
    }

    @Test
    public void testGetStatusWithNoResponse()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals(MetadataNotificationStatus.SUCCESS, notification.getStatus());
    }

    @Test
    public void testGetStatusWithErrors()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        notification.addError("error");
        Assertions.assertEquals(MetadataNotificationStatus.FAILED, notification.getStatus());
    }

    @Test
    public void testSetFullUpdateReturnsSelf()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        MetadataNotification result = notification.setFullUpdate(true);
        Assertions.assertSame(notification, result);
        Assertions.assertTrue(notification.isFullUpdate());
    }

    @Test
    public void testResponsesPerAttempt()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        notification.addError("error at attempt 0");
        notification.increaseAttempts();
        notification.addError("error at attempt 1");

        Assertions.assertEquals(2, notification.getResponses().size());
    }

    @Test
    public void testSetMaxAttempts()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals(2, notification.getMaxAttempts());
        notification.setMaxAttempts(5);
        Assertions.assertEquals(5, notification.getMaxAttempts());
    }

    @Test
    public void testDefaultConstructor()
    {
        MetadataNotification notification = new MetadataNotification();
        Assertions.assertNull(notification.getProjectId());
        Assertions.assertNull(notification.getGroupId());
    }
}
