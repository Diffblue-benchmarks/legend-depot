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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.Instant;

public class ExtendedErrorMessageTest
{
    @Test
    public void testNewExtendedErrorMessageCreatesInstance()
    {
        Instant now = Instant.now();
        ExtendedErrorMessage msg = ExtendedErrorMessage.newExtendedErrorMessage(500, "error", "details", "stack", now);
        Assertions.assertNotNull(msg);
        Assertions.assertEquals(500, msg.getCode());
        Assertions.assertEquals("error", msg.getMessage());
        Assertions.assertEquals(now, msg.getTimestamp());
        Assertions.assertEquals("stack", msg.getStackTrace());
    }

    @Test
    public void testGetTimestampReturnsConstructedTimestamp()
    {
        Instant now = Instant.now();
        ExtendedErrorMessage msg = ExtendedErrorMessage.newExtendedErrorMessage(200, "ok", null, null, now);
        Assertions.assertEquals(now, msg.getTimestamp());
    }

    @Test
    public void testGetTimestampNullWhenNotProvided()
    {
        ExtendedErrorMessage msg = ExtendedErrorMessage.newExtendedErrorMessage(200, "ok", null, null, null);
        Assertions.assertNull(msg.getTimestamp());
    }

    @Test
    public void testGetStackTraceReturnsConstructedStackTrace()
    {
        ExtendedErrorMessage msg = ExtendedErrorMessage.newExtendedErrorMessage(500, "err", null, "mystack", Instant.now());
        Assertions.assertEquals("mystack", msg.getStackTrace());
    }

    @Test
    public void testGetStackTraceNullWhenNotProvided()
    {
        ExtendedErrorMessage msg = ExtendedErrorMessage.newExtendedErrorMessage(500, "err", null, null, Instant.now());
        Assertions.assertNull(msg.getStackTrace());
    }

    @Test
    public void testFromThrowableWithGenericException()
    {
        RuntimeException ex = new RuntimeException("generic error");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, false);
        Assertions.assertNotNull(msg);
        Assertions.assertEquals(500, msg.getCode());
        Assertions.assertEquals("generic error", msg.getMessage());
        Assertions.assertNull(msg.getStackTrace());
    }

    @Test
    public void testFromThrowableWithStackTrace()
    {
        RuntimeException ex = new RuntimeException("with stack");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, true);
        Assertions.assertNotNull(msg);
        Assertions.assertNotNull(msg.getStackTrace());
        Assertions.assertTrue(msg.getStackTrace().contains("RuntimeException"));
    }

    @Test
    public void testFromThrowableWithLegendDepotServerException()
    {
        LegendDepotServerException ex = new LegendDepotServerException("not found", Response.Status.NOT_FOUND);
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, false);
        Assertions.assertNotNull(msg);
        Assertions.assertEquals(404, msg.getCode());
        Assertions.assertEquals("not found", msg.getMessage());
    }

    @Test
    public void testFromLegendDepotServerException()
    {
        LegendDepotServerException ex = new LegendDepotServerException("conflict", Response.Status.CONFLICT);
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromLegendDepotServerException(ex, false);
        Assertions.assertNotNull(msg);
        Assertions.assertEquals(409, msg.getCode());
        Assertions.assertEquals("conflict", msg.getMessage());
    }

    @Test
    public void testFromThrowableWithResponseStatus()
    {
        RuntimeException ex = new RuntimeException("custom");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, Response.Status.BAD_GATEWAY, "custom msg", "details", false);
        Assertions.assertNotNull(msg);
        Assertions.assertEquals(502, msg.getCode());
        Assertions.assertEquals("custom msg", msg.getMessage());
    }

    @Test
    public void testFromThrowableWithNullResponseStatusUsesDefault()
    {
        RuntimeException ex = new RuntimeException("fallback");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, (Response.Status) null, null, null, false);
        Assertions.assertNotNull(msg);
        Assertions.assertEquals(500, msg.getCode());
    }

    @Test
    public void testFromThrowableWithStatusCode()
    {
        RuntimeException ex = new RuntimeException("code test");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, 503, null, null, false);
        Assertions.assertNotNull(msg);
        Assertions.assertEquals(503, msg.getCode());
        Assertions.assertEquals("code test", msg.getMessage());
    }

    @Test
    public void testFromThrowableWithStatusCodeAndCustomMessage()
    {
        RuntimeException ex = new RuntimeException("ignored");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, 422, "override", "det", false);
        Assertions.assertNotNull(msg);
        Assertions.assertEquals(422, msg.getCode());
        Assertions.assertEquals("override", msg.getMessage());
    }

    @Test
    public void testGetMessageWithNullMessageFallsToCause()
    {
        RuntimeException cause = new RuntimeException("cause message");
        RuntimeException wrapper = new RuntimeException((String) null, cause);
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(wrapper, false);
        Assertions.assertEquals("cause message", msg.getMessage());
    }

    @Test
    public void testGetMessageWithNullMessageAndNullCause()
    {
        RuntimeException ex = new RuntimeException((String) null);
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, false);
        Assertions.assertNull(msg.getMessage());
    }

    @Test
    public void testGetStackTraceStaticMethod()
    {
        RuntimeException ex = new RuntimeException("stack test");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, true);
        Assertions.assertNotNull(msg.getStackTrace());
        Assertions.assertTrue(msg.getStackTrace().length() > 0);
    }

    @Test
    public void testTimestampIsSetWhenCreatedFromThrowable()
    {
        RuntimeException ex = new RuntimeException("ts test");
        ExtendedErrorMessage msg = ExtendedErrorMessage.fromThrowable(ex, false);
        Assertions.assertNotNull(msg.getTimestamp());
    }
}
