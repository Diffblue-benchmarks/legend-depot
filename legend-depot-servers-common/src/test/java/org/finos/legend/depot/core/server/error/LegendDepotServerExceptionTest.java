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

import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LegendDepotServerExceptionTest
{
    @Test
    public void testConstructorWithMessageStatusAndCause()
    {
        Throwable cause = new RuntimeException("cause");
        LegendDepotServerException ex = new LegendDepotServerException("msg", Response.Status.NOT_FOUND, cause);
        Assertions.assertEquals("msg", ex.getMessage());
        Assertions.assertEquals(Response.Status.NOT_FOUND, ex.getStatus());
        Assertions.assertEquals(cause, ex.getCause());
    }

    @Test
    public void testConstructorWithMessageStatusAndCauseNullStatus()
    {
        Throwable cause = new RuntimeException("cause");
        LegendDepotServerException ex = new LegendDepotServerException("msg", null, cause);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, ex.getStatus());
        Assertions.assertEquals(cause, ex.getCause());
    }

    @Test
    public void testConstructorWithMessageAndStatus()
    {
        LegendDepotServerException ex = new LegendDepotServerException("msg", Response.Status.BAD_REQUEST);
        Assertions.assertEquals("msg", ex.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
    }

    @Test
    public void testConstructorWithMessageAndNullStatus()
    {
        LegendDepotServerException ex = new LegendDepotServerException("msg", (Response.Status) null);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, ex.getStatus());
    }

    @Test
    public void testConstructorWithMessageAndCause()
    {
        Throwable cause = new RuntimeException("cause");
        LegendDepotServerException ex = new LegendDepotServerException("msg", cause);
        Assertions.assertEquals("msg", ex.getMessage());
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, ex.getStatus());
        Assertions.assertEquals(cause, ex.getCause());
    }

    @Test
    public void testConstructorWithMessageOnly()
    {
        LegendDepotServerException ex = new LegendDepotServerException("msg");
        Assertions.assertEquals("msg", ex.getMessage());
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, ex.getStatus());
    }

    @Test
    public void testGetStatus()
    {
        LegendDepotServerException ex = new LegendDepotServerException("msg", Response.Status.FORBIDDEN);
        Assertions.assertEquals(Response.Status.FORBIDDEN, ex.getStatus());
    }

    @Test
    public void testValidateNonNullWithValidValue()
    {
        String result = LegendDepotServerException.validateNonNull("value", "must not be null");
        Assertions.assertEquals("value", result);
    }

    @Test
    public void testValidateNonNullThrowsOnNull()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
            () -> LegendDepotServerException.validateNonNull(null, "must not be null"));
        Assertions.assertEquals("must not be null", ex.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
    }

    @Test
    public void testValidateNonNullWithStatusThrowsOnNull()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
            () -> LegendDepotServerException.validateNonNull(null, "must not be null", Response.Status.NOT_FOUND));
        Assertions.assertEquals("must not be null", ex.getMessage());
        Assertions.assertEquals(Response.Status.NOT_FOUND, ex.getStatus());
    }

    @Test
    public void testValidateNonNullWithStatusValidValue()
    {
        String result = LegendDepotServerException.validateNonNull("value", "must not be null", Response.Status.NOT_FOUND);
        Assertions.assertEquals("value", result);
    }

    @Test
    public void testValidateWithPredicateAndMessagePasses()
    {
        String result = LegendDepotServerException.validate("hello", s -> s.startsWith("h"), "invalid");
        Assertions.assertEquals("hello", result);
    }

    @Test
    public void testValidateWithPredicateAndMessageFails()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
            () -> LegendDepotServerException.validate("world", s -> s.startsWith("h"), "invalid"));
        Assertions.assertEquals("invalid", ex.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
    }

    @Test
    public void testValidateWithPredicateMessageAndStatusPasses()
    {
        String result = LegendDepotServerException.validate("hello", s -> s.startsWith("h"), "invalid", Response.Status.NOT_FOUND);
        Assertions.assertEquals("hello", result);
    }

    @Test
    public void testValidateWithPredicateMessageAndStatusFails()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
            () -> LegendDepotServerException.validate("world", s -> s.startsWith("h"), "invalid", Response.Status.NOT_FOUND));
        Assertions.assertEquals("invalid", ex.getMessage());
        Assertions.assertEquals(Response.Status.NOT_FOUND, ex.getStatus());
    }

    @Test
    public void testValidateWithPredicateMessageAndNullStatusFails()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
            () -> LegendDepotServerException.validate("world", s -> s.startsWith("h"), "invalid", null));
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
    }

    @Test
    public void testValidateWithFunctionMessagePasses()
    {
        String result = LegendDepotServerException.validate("hello", s -> s.startsWith("h"), s -> "invalid: " + s);
        Assertions.assertEquals("hello", result);
    }

    @Test
    public void testValidateWithFunctionMessageFails()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
            () -> LegendDepotServerException.validate("world", s -> s.startsWith("h"), s -> "invalid: " + s));
        Assertions.assertEquals("invalid: world", ex.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
    }

    @Test
    public void testValidateWithFunctionMessageAndStatusPasses()
    {
        String result = LegendDepotServerException.validate("hello", s -> s.startsWith("h"), s -> "invalid: " + s, Response.Status.FORBIDDEN);
        Assertions.assertEquals("hello", result);
    }

    @Test
    public void testValidateWithFunctionMessageAndStatusFails()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
            () -> LegendDepotServerException.validate("world", s -> s.startsWith("h"), s -> "invalid: " + s, Response.Status.FORBIDDEN));
        Assertions.assertEquals("invalid: world", ex.getMessage());
        Assertions.assertEquals(Response.Status.FORBIDDEN, ex.getStatus());
    }

    @Test
    public void testValidateWithFunctionMessageAndNullStatusFails()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
            () -> LegendDepotServerException.validate("world", s -> s.startsWith("h"), s -> "invalid: " + s, null));
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
    }

    @Test
    public void testValidateWithNullFunctionMessageFails()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
            () -> LegendDepotServerException.<String>validate("world", s -> s.startsWith("h"), (java.util.function.Function<? super String, String>) null, null));
        Assertions.assertNull(ex.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
    }
}
