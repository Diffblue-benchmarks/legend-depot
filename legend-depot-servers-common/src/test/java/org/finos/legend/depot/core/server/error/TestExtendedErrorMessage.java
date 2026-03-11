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

class TestExtendedErrorMessage
{
    @Test
    void canCreateViaNewExtendedErrorMessage()
    {
        Instant now = Instant.now();
        ExtendedErrorMessage msg = ExtendedErrorMessage.newExtendedErrorMessage(500, "error", "details", "trace", now);

        assertEquals(500, msg.getCode());
        assertEquals("error", msg.getMessage());
        assertEquals("details", msg.getDetails());
        assertEquals("trace", msg.getStackTrace());
        assertEquals(now, msg.getTimestamp());
    }

    @Test
    void canCreateFromThrowableWithStackTrace()
    {
        RuntimeException ex = new RuntimeException("test error");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, true);

        assertEquals(500, msg.getCode());
        assertEquals("test error", msg.getMessage());
        assertNotNull(msg.getStackTrace());
        assertTrue(msg.getStackTrace().contains("RuntimeException"));
        assertNotNull(msg.getTimestamp());
    }

    @Test
    void canCreateFromThrowableWithoutStackTrace()
    {
        RuntimeException ex = new RuntimeException("test error");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, false);

        assertEquals(500, msg.getCode());
        assertEquals("test error", msg.getMessage());
        assertNull(msg.getStackTrace());
    }

    @Test
    void canCreateFromLegendDepotServerException()
    {
        LegendDepotServerException ex = new LegendDepotServerException("depot error", Response.Status.BAD_REQUEST);
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, true);

        assertEquals(400, msg.getCode());
        assertEquals("depot error", msg.getMessage());
        assertNotNull(msg.getStackTrace());
    }

    @Test
    void canCreateFromLegendDepotServerExceptionDirectly()
    {
        LegendDepotServerException ex = new LegendDepotServerException("direct depot error", Response.Status.FORBIDDEN);
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromLegendDepotServerException(ex, false);

        assertEquals(403, msg.getCode());
        assertEquals("direct depot error", msg.getMessage());
        assertNull(msg.getStackTrace());
    }

    @Test
    void canCreateFromThrowableWithStatusEnum()
    {
        RuntimeException ex = new RuntimeException("status error");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, Response.Status.BAD_GATEWAY, "custom msg", "custom details", true);

        assertEquals(502, msg.getCode());
        assertEquals("custom msg", msg.getMessage());
        assertEquals("custom details", msg.getDetails());
        assertNotNull(msg.getStackTrace());
    }

    @Test
    void canCreateFromThrowableWithNullStatus()
    {
        RuntimeException ex = new RuntimeException("null status");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, (Response.Status) null, null, null, false);

        assertEquals(500, msg.getCode());
        assertEquals("null status", msg.getMessage());
    }

    @Test
    void canCreateFromThrowableWithStatusCode()
    {
        RuntimeException ex = new RuntimeException("code error");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, 503, "custom", "detail", true);

        assertEquals(503, msg.getCode());
        assertEquals("custom", msg.getMessage());
        assertNotNull(msg.getTimestamp());
    }

    @Test
    void canGetMessageFromCauseWhenMessageIsNull()
    {
        RuntimeException cause = new RuntimeException("cause message");
        RuntimeException ex = new RuntimeException(null, cause);
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, false);

        assertEquals("cause message", msg.getMessage());
    }

    @Test
    void canHandleNullMessageWithNoCause()
    {
        RuntimeException ex = new RuntimeException((String) null);
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, false);

        assertNull(msg.getMessage());
    }
}
