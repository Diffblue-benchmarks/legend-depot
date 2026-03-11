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

import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestLegendDepotServerException
{
    @Test
    void canCreateWithMessageStatusAndCause()
    {
        Throwable cause = new RuntimeException("root cause");
        LegendDepotServerException exception = new LegendDepotServerException("test error", Response.Status.NOT_FOUND, cause);

        assertEquals("test error", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND, exception.getStatus());
        assertSame(cause, exception.getCause());
    }

    @Test
    void canCreateWithMessageStatusAndCauseDefaultsStatusWhenNull()
    {
        Throwable cause = new RuntimeException("root cause");
        LegendDepotServerException exception = new LegendDepotServerException("test error", null, cause);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertSame(cause, exception.getCause());
    }

    @Test
    void canCreateWithMessageAndStatus()
    {
        LegendDepotServerException exception = new LegendDepotServerException("test error", Response.Status.BAD_REQUEST);

        assertEquals("test error", exception.getMessage());
        assertEquals(Response.Status.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void canCreateWithMessageAndStatusDefaultsStatusWhenNull()
    {
        LegendDepotServerException exception = new LegendDepotServerException("test error", (Response.Status) null);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    void canCreateWithMessageAndCause()
    {
        Throwable cause = new RuntimeException("root cause");
        LegendDepotServerException exception = new LegendDepotServerException("test error", cause);

        assertEquals("test error", exception.getMessage());
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertSame(cause, exception.getCause());
    }

    @Test
    void canCreateWithMessageOnly()
    {
        LegendDepotServerException exception = new LegendDepotServerException("test error");

        assertEquals("test error", exception.getMessage());
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    void canValidateNonNullReturnsValueWhenNotNull()
    {
        String result = LegendDepotServerException.validateNonNull("hello", "should not be null");
        assertEquals("hello", result);
    }

    @Test
    void canValidateNonNullThrowsWhenNull()
    {
        LegendDepotServerException exception = assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validateNonNull(null, "value is null"));
        assertEquals("value is null", exception.getMessage());
        assertEquals(Response.Status.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void canValidateNonNullWithStatusThrowsWhenNull()
    {
        LegendDepotServerException exception = assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validateNonNull(null, "value is null", Response.Status.NOT_FOUND));
        assertEquals("value is null", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND, exception.getStatus());
    }

    @Test
    void canValidateWithPredicateAndMessageReturnsValueWhenValid()
    {
        String result = LegendDepotServerException.validate("abc", s -> s.length() == 3, "invalid length");
        assertEquals("abc", result);
    }

    @Test
    void canValidateWithPredicateAndMessageThrowsWhenInvalid()
    {
        LegendDepotServerException exception = assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validate("ab", s -> s.length() == 3, "invalid length"));
        assertEquals("invalid length", exception.getMessage());
        assertEquals(Response.Status.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void canValidateWithPredicateAndMessageAndStatusThrowsWithCustomStatus()
    {
        LegendDepotServerException exception = assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validate("ab", s -> s.length() == 3, "invalid length", Response.Status.FORBIDDEN));
        assertEquals("invalid length", exception.getMessage());
        assertEquals(Response.Status.FORBIDDEN, exception.getStatus());
    }

    @Test
    void canValidateWithPredicateAndFunctionReturnsValueWhenValid()
    {
        Integer result = LegendDepotServerException.validate(5, i -> i > 0, i -> "negative: " + i);
        assertEquals(5, result);
    }

    @Test
    void canValidateWithPredicateAndFunctionThrowsWhenInvalid()
    {
        LegendDepotServerException exception = assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validate(-1, i -> i > 0, i -> "negative: " + i));
        assertEquals("negative: -1", exception.getMessage());
        assertEquals(Response.Status.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void canValidateWithPredicateAndFunctionAndStatusThrowsWithCustomStatus()
    {
        LegendDepotServerException exception = assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validate(-1, i -> i > 0, i -> "negative: " + i, Response.Status.CONFLICT));
        assertEquals("negative: -1", exception.getMessage());
        assertEquals(Response.Status.CONFLICT, exception.getStatus());
    }
}
