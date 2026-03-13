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

package org.finos.legend.depot.core.server.error;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtendedErrorMessageTest
{
    @Test
    public void canCreateExtendedErrorMessage()
    {
        Instant timestamp = Instant.now();
        ExtendedErrorMessage message = ExtendedErrorMessage.newExtendedErrorMessage(500, "Test message", "Test details", "Test stack trace", timestamp);
        assertNotNull(message);
        assertEquals(500, message.getCode());
        assertEquals("Test message", message.getMessage());
        assertEquals("Test details", message.getDetails());
        assertEquals("Test stack trace", message.getStackTrace());
        assertEquals(timestamp, message.getTimestamp());
    }

    @Test
    public void canGetTimestamp()
    {
        Instant timestamp = Instant.now();
        ExtendedErrorMessage message = ExtendedErrorMessage.newExtendedErrorMessage(500, "Test", null, null, timestamp);
        assertEquals(timestamp, message.getTimestamp());
    }

    @Test
    public void canGetStackTrace()
    {
        ExtendedErrorMessage message = ExtendedErrorMessage.newExtendedErrorMessage(500, "Test", null, "Stack trace", Instant.now());
        assertEquals("Stack trace", message.getStackTrace());
    }

    @Test
    public void canCreateFromThrowableWithStackTrace()
    {
        Exception exception = new RuntimeException("Test exception");
        ExtendedErrorMessage message = ExtendedErrorMessage.fromThrowable(exception, true);
        assertNotNull(message);
        assertEquals(500, message.getCode());
        assertEquals("Test exception", message.getMessage());
        assertNotNull(message.getStackTrace());
        assertTrue(message.getStackTrace().contains("RuntimeException"));
    }

    @Test
    public void canCreateFromThrowableWithoutStackTrace()
    {
        Exception exception = new RuntimeException("Test exception");
        ExtendedErrorMessage message = ExtendedErrorMessage.fromThrowable(exception, false);
        assertNotNull(message);
        assertEquals(500, message.getCode());
        assertEquals("Test exception", message.getMessage());
        assertNull(message.getStackTrace());
    }

    @Test
    public void canCreateFromLegendDepotServerException()
    {
        LegendDepotServerException exception = new LegendDepotServerException("Test exception", Response.Status.BAD_REQUEST);
        ExtendedErrorMessage message = ExtendedErrorMessage.fromLegendDepotServerException(exception, true);
        assertNotNull(message);
        assertEquals(400, message.getCode());
        assertEquals("Test exception", message.getMessage());
        assertNotNull(message.getStackTrace());
    }

    @Test
    public void canCreateFromLegendDepotServerExceptionWithoutStackTrace()
    {
        LegendDepotServerException exception = new LegendDepotServerException("Test exception", Response.Status.NOT_FOUND);
        ExtendedErrorMessage message = ExtendedErrorMessage.fromLegendDepotServerException(exception, false);
        assertNotNull(message);
        assertEquals(404, message.getCode());
        assertEquals("Test exception", message.getMessage());
        assertNull(message.getStackTrace());
    }

    @Test
    public void canCreateFromThrowableWithStatus()
    {
        Exception exception = new RuntimeException("Test exception");
        ExtendedErrorMessage message = ExtendedErrorMessage.fromThrowable(exception, Response.Status.BAD_REQUEST, null, null, true);
        assertNotNull(message);
        assertEquals(400, message.getCode());
        assertEquals("Test exception", message.getMessage());
        assertNotNull(message.getStackTrace());
    }

    @Test
    public void canCreateFromThrowableWithNullStatus()
    {
        Exception exception = new RuntimeException("Test exception");
        ExtendedErrorMessage message = ExtendedErrorMessage.fromThrowable(exception, (Response.Status) null, null, null, true);
        assertNotNull(message);
        assertEquals(500, message.getCode());
    }

    @Test
    public void canCreateFromThrowableWithStatusCode()
    {
        Exception exception = new RuntimeException("Test exception");
        ExtendedErrorMessage message = ExtendedErrorMessage.fromThrowable(exception, 503, null, null, true);
        assertNotNull(message);
        assertEquals(503, message.getCode());
        assertEquals("Test exception", message.getMessage());
        assertNotNull(message.getStackTrace());
    }

    @Test
    public void canCreateFromThrowableWithCustomMessage()
    {
        Exception exception = new RuntimeException("Original message");
        ExtendedErrorMessage message = ExtendedErrorMessage.fromThrowable(exception, 500, "Custom message", null, false);
        assertNotNull(message);
        assertEquals("Custom message", message.getMessage());
    }

    @Test
    public void canCreateFromThrowableWithCustomDetails()
    {
        Exception exception = new RuntimeException("Test exception");
        ExtendedErrorMessage message = ExtendedErrorMessage.fromThrowable(exception, 500, null, "Custom details", false);
        assertNotNull(message);
        assertEquals("Custom details", message.getDetails());
    }

    @Test
    public void canGetMessageFromThrowableWithNullMessage()
    {
        Exception cause = new RuntimeException("Cause message");
        Exception exception = new RuntimeException(null, cause);
        ExtendedErrorMessage message = ExtendedErrorMessage.fromThrowable(exception, false);
        assertNotNull(message);
        assertEquals("Cause message", message.getMessage());
    }

    @Test
    public void canGetMessageFromThrowableWithNullMessageAndNullCause()
    {
        Exception exception = new RuntimeException((String) null);
        ExtendedErrorMessage message = ExtendedErrorMessage.fromThrowable(exception, false);
        assertNotNull(message);
        assertNull(message.getMessage());
    }

    @Test
    public void canHandleThrowableAsLegendDepotServerException()
    {
        Throwable throwable = new LegendDepotServerException("Test", Response.Status.CONFLICT);
        ExtendedErrorMessage message = ExtendedErrorMessage.fromThrowable(throwable, true);
        assertNotNull(message);
        assertEquals(409, message.getCode());
    }
}
