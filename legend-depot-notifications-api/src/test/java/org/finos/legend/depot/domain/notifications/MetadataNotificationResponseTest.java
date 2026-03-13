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

import java.util.Arrays;

public class MetadataNotificationResponseTest
{
    @Test
    public void testNewResponseIsSuccess()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        Assertions.assertEquals(MetadataNotificationStatus.SUCCESS, response.getStatus());
        Assertions.assertFalse(response.hasErrors());
        Assertions.assertTrue(response.getErrors().isEmpty());
        Assertions.assertTrue(response.getMessages().isEmpty());
    }

    @Test
    public void testAddError()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addError("test error");
        Assertions.assertTrue(response.hasErrors());
        Assertions.assertEquals(MetadataNotificationStatus.FAILED, response.getStatus());
        Assertions.assertEquals(1, response.getErrors().size());
        Assertions.assertEquals("test error", response.getErrors().get(0));
    }

    @Test
    public void testAddMessage()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessage("test message");
        Assertions.assertFalse(response.hasErrors());
        Assertions.assertEquals(1, response.getMessages().size());
        Assertions.assertEquals("test message", response.getMessages().get(0));
    }

    @Test
    public void testAddMessages()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessages(Arrays.asList("msg1", "msg2", "msg3"));
        Assertions.assertEquals(3, response.getMessages().size());
    }

    @Test
    public void testLogError()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.logError("logged error");
        Assertions.assertTrue(response.hasErrors());
        Assertions.assertEquals("logged error", response.getErrors().get(0));
    }

    @Test
    public void testCombine()
    {
        MetadataNotificationResponse response1 = new MetadataNotificationResponse();
        response1.addMessage("msg1");
        response1.addError("err1");

        MetadataNotificationResponse response2 = new MetadataNotificationResponse();
        response2.addMessage("msg2");
        response2.addError("err2");

        response1.combine(response2);
        Assertions.assertEquals(2, response1.getMessages().size());
        Assertions.assertEquals(2, response1.getErrors().size());
    }

    @Test
    public void testCombineNull()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessage("msg1");
        response.combine(null);
        Assertions.assertEquals(1, response.getMessages().size());
    }

    @Test
    public void testAddErrorReturnsSelf()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        MetadataNotificationResponse result = response.addError("error");
        Assertions.assertSame(response, result);
    }

    @Test
    public void testAddMessageReturnsSelf()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        MetadataNotificationResponse result = response.addMessage("message");
        Assertions.assertSame(response, result);
    }

    @Test
    public void testToString()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessage("msg");
        response.addError("err");
        String str = response.toString();
        Assertions.assertTrue(str.contains("msg"));
        Assertions.assertTrue(str.contains("err"));
    }
}
