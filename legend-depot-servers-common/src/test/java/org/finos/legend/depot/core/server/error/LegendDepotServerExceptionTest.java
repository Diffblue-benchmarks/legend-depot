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
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class LegendDepotServerExceptionTest
{
    @Test
    public void testDefaultStatus()
    {
        LegendDepotServerException ex = new LegendDepotServerException("error");
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, ex.getStatus());
        Assertions.assertEquals("error", ex.getMessage());
    }

    @Test
    public void testCustomStatus()
    {
        LegendDepotServerException ex = new LegendDepotServerException("not found", Response.Status.NOT_FOUND);
        Assertions.assertEquals(Response.Status.NOT_FOUND, ex.getStatus());
    }

    @Test
    public void testNullStatusDefaultsToInternalServerError()
    {
        LegendDepotServerException ex = new LegendDepotServerException("error", (Response.Status) null);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, ex.getStatus());
    }

    @Test
    public void testWithCause()
    {
        RuntimeException cause = new RuntimeException("root cause");
        LegendDepotServerException ex = new LegendDepotServerException("wrapper", cause);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, ex.getStatus());
        Assertions.assertSame(cause, ex.getCause());
    }

    @Test
    public void testWithCauseAndStatus()
    {
        RuntimeException cause = new RuntimeException("root");
        LegendDepotServerException ex = new LegendDepotServerException("bad request", Response.Status.BAD_REQUEST, cause);
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
        Assertions.assertSame(cause, ex.getCause());
    }

    @Test
    public void testValidateNonNullSuccess()
    {
        String result = LegendDepotServerException.validateNonNull("hello", "must not be null");
        Assertions.assertEquals("hello", result);
    }

    @Test
    public void testValidateNonNullThrowsOnNull()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validateNonNull(null, "value is null"));
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
    }

    @Test
    public void testValidateNonNullWithCustomStatus()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validateNonNull(null, "not found", Response.Status.NOT_FOUND));
        Assertions.assertEquals(Response.Status.NOT_FOUND, ex.getStatus());
    }

    @Test
    public void testValidatePredicateSuccess()
    {
        int result = LegendDepotServerException.validate(5, x -> x > 0, "must be positive");
        Assertions.assertEquals(5, result);
    }

    @Test
    public void testValidatePredicateFailure()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validate(-1, x -> x > 0, "must be positive"));
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
        Assertions.assertEquals("must be positive", ex.getMessage());
    }

    @Test
    public void testValidateWithMessageFunction()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validate("bad", s -> s.length() > 5, s -> "Too short: " + s));
        Assertions.assertEquals("Too short: bad", ex.getMessage());
    }
}
