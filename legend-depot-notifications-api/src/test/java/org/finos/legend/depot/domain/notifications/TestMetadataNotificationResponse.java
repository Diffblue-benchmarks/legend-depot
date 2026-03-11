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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestMetadataNotificationResponse
{
    @Test
    void testGetStatusReturnsSuccessWhenNoErrors()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        assertEquals(MetadataNotificationStatus.SUCCESS, response.getStatus());
    }

    @Test
    void testGetStatusReturnsFailedWhenErrorsExist()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addError("some error");
        assertEquals(MetadataNotificationStatus.FAILED, response.getStatus());
    }

    @Test
    void testToString()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessage("msg1");
        response.addError("err1");
        String result = response.toString();
        assertTrue(result.contains("msg1"));
        assertTrue(result.contains("err1"));
        assertTrue(result.startsWith("MetadataEventResponse{"));
    }

    @Test
    void testGetErrors()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        assertNotNull(response.getErrors());
        assertTrue(response.getErrors().isEmpty());

        response.addError("error1");
        assertEquals(1, response.getErrors().size());
        assertEquals("error1", response.getErrors().get(0));
    }

    @Test
    void testGetMessages()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        assertNotNull(response.getMessages());
        assertTrue(response.getMessages().isEmpty());

        response.addMessage("message1");
        assertEquals(1, response.getMessages().size());
        assertEquals("message1", response.getMessages().get(0));
    }

    @Test
    void testAddErrorReturnsSelf()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        MetadataNotificationResponse result = response.addError("err");
        assertEquals(response, result);
        assertEquals(1, response.getErrors().size());
    }

    @Test
    void testAddMessageReturnsSelf()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        MetadataNotificationResponse result = response.addMessage("msg");
        assertEquals(response, result);
        assertEquals(1, response.getMessages().size());
    }

    @Test
    void testAddMessagesReturnsSelf()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        List<String> msgs = Arrays.asList("a", "b", "c");
        MetadataNotificationResponse result = response.addMessages(msgs);
        assertEquals(response, result);
        assertEquals(3, response.getMessages().size());
    }

    @Test
    void testLogError()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.logError("logged error");
        assertEquals(1, response.getErrors().size());
        assertEquals("logged error", response.getErrors().get(0));
    }

    @Test
    void testHasErrorsReturnsFalseWhenEmpty()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        assertFalse(response.hasErrors());
    }

    @Test
    void testHasErrorsReturnsTrueWhenErrorsExist()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addError("err");
        assertTrue(response.hasErrors());
    }

    @Test
    void testCombineWithNonNullResponse()
    {
        MetadataNotificationResponse response1 = new MetadataNotificationResponse();
        response1.addMessage("msg1").addError("err1");

        MetadataNotificationResponse response2 = new MetadataNotificationResponse();
        response2.addMessage("msg2").addError("err2");

        MetadataNotificationResponse result = response1.combine(response2);
        assertEquals(response1, result);
        assertEquals(Arrays.asList("err1", "err2"), result.getErrors());
        assertEquals(Arrays.asList("msg1", "msg2"), result.getMessages());
    }

    @Test
    void testCombineWithNullResponse()
    {
        MetadataNotificationResponse response = new MetadataNotificationResponse();
        response.addMessage("msg1");

        MetadataNotificationResponse result = response.combine(null);
        assertEquals(response, result);
        assertEquals(1, result.getMessages().size());
    }
}
