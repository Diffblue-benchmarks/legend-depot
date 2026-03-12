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

public class TestLegendDepotServerException
{
    @Test
    public void testMessageOnlyConstructorDefaultsToInternalServerError()
    {
        LegendDepotServerException ex = new LegendDepotServerException("error");
        Assertions.assertEquals("error", ex.getMessage());
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, ex.getStatus());
    }

    @Test
    public void testMessageWithStatusConstructor()
    {
        LegendDepotServerException ex = new LegendDepotServerException("not found", Response.Status.NOT_FOUND);
        Assertions.assertEquals("not found", ex.getMessage());
        Assertions.assertEquals(Response.Status.NOT_FOUND, ex.getStatus());
    }

    @Test
    public void testMessageWithNullStatusDefaultsToInternalServerError()
    {
        LegendDepotServerException ex = new LegendDepotServerException("error", (Response.Status) null);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, ex.getStatus());
    }

    @Test
    public void testMessageWithCause()
    {
        RuntimeException cause = new RuntimeException("root cause");
        LegendDepotServerException ex = new LegendDepotServerException("wrapped", cause);
        Assertions.assertEquals("wrapped", ex.getMessage());
        Assertions.assertEquals(cause, ex.getCause());
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, ex.getStatus());
    }

    @Test
    public void testFullConstructor()
    {
        RuntimeException cause = new RuntimeException("root");
        LegendDepotServerException ex = new LegendDepotServerException("error", Response.Status.BAD_REQUEST, cause);
        Assertions.assertEquals("error", ex.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
        Assertions.assertEquals(cause, ex.getCause());
    }

    @Test
    public void testValidateNonNullPasses()
    {
        String result = LegendDepotServerException.validateNonNull("hello", "cannot be null");
        Assertions.assertEquals("hello", result);
    }

    @Test
    public void testValidateNonNullThrowsOnNull()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validateNonNull(null, "cannot be null"));
        Assertions.assertEquals("cannot be null", ex.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
    }

    @Test
    public void testValidateNonNullWithCustomStatus()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validateNonNull(null, "missing", Response.Status.NOT_FOUND));
        Assertions.assertEquals(Response.Status.NOT_FOUND, ex.getStatus());
    }

    @Test
    public void testValidateWithPredicatePass()
    {
        Integer result = LegendDepotServerException.validate(5, n -> n > 0, "must be positive");
        Assertions.assertEquals(5, result);
    }

    @Test
    public void testValidateWithPredicateFailure()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validate(-1, n -> n > 0, "must be positive"));
        Assertions.assertEquals("must be positive", ex.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, ex.getStatus());
    }

    @Test
    public void testValidateWithMessageFunction()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validate(-1, n -> n > 0, n -> "invalid value: " + n));
        Assertions.assertEquals("invalid value: -1", ex.getMessage());
    }

    @Test
    public void testValidateWithNullMessageFunction()
    {
        LegendDepotServerException ex = Assertions.assertThrows(LegendDepotServerException.class,
                () -> LegendDepotServerException.validate(-1, n -> n > 0, (java.util.function.Function<? super Integer, String>) null));
        Assertions.assertNull(ex.getMessage());
    }
}
