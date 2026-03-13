//  Copyright 2023 Goldman Sachs
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
    public void canCreateExceptionWithMessageStatusAndCause()
    {
        String message = "Test error message";
        Response.Status status = Response.Status.NOT_FOUND;
        Throwable cause = new RuntimeException("Root cause");

        LegendDepotServerException exception = new LegendDepotServerException(message, status, cause);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(status, exception.getStatus());
        Assertions.assertEquals(cause, exception.getCause());
    }

    @Test
    public void canCreateExceptionWithMessageStatusAndCauseWithNullStatus()
    {
        String message = "Test error message";
        Throwable cause = new RuntimeException("Root cause");

        LegendDepotServerException exception = new LegendDepotServerException(message, null, cause);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(cause, exception.getCause());
    }

    @Test
    public void canCreateExceptionWithMessageAndStatus()
    {
        String message = "Test error message";
        Response.Status status = Response.Status.BAD_REQUEST;

        LegendDepotServerException exception = new LegendDepotServerException(message, status);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(status, exception.getStatus());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    public void canCreateExceptionWithMessageAndStatusWithNullStatus()
    {
        String message = "Test error message";

        LegendDepotServerException exception = new LegendDepotServerException(message, (Response.Status) null);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    public void canCreateExceptionWithMessageAndCause()
    {
        String message = "Test error message";
        Throwable cause = new RuntimeException("Root cause");

        LegendDepotServerException exception = new LegendDepotServerException(message, cause);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(cause, exception.getCause());
    }

    @Test
    public void canCreateExceptionWithMessageOnly()
    {
        String message = "Test error message";

        LegendDepotServerException exception = new LegendDepotServerException(message);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    public void canGetStatus()
    {
        String message = "Test error message";
        Response.Status status = Response.Status.FORBIDDEN;

        LegendDepotServerException exception = new LegendDepotServerException(message, status);

        Response.Status retrievedStatus = exception.getStatus();

        Assertions.assertEquals(status, retrievedStatus);
    }

    @Test
    public void canValidateNonNullWithValidArgument()
    {
        String testValue = "test";
        String message = "Value must not be null";

        String result = LegendDepotServerException.validateNonNull(testValue, message);

        Assertions.assertEquals(testValue, result);
    }

    @Test
    public void canValidateNonNullWithNullArgumentThrowsException()
    {
        String message = "Value must not be null";

        LegendDepotServerException exception = Assertions.assertThrows(
            LegendDepotServerException.class,
            () -> LegendDepotServerException.validateNonNull(null, message)
        );

        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void canValidateNonNullWithStatusWithValidArgument()
    {
        String testValue = "test";
        String message = "Value must not be null";
        Response.Status status = Response.Status.NOT_FOUND;

        String result = LegendDepotServerException.validateNonNull(testValue, message, status);

        Assertions.assertEquals(testValue, result);
    }

    @Test
    public void canValidateNonNullWithStatusWithNullArgumentThrowsException()
    {
        String message = "Value must not be null";
        Response.Status status = Response.Status.NOT_FOUND;

        LegendDepotServerException exception = Assertions.assertThrows(
            LegendDepotServerException.class,
            () -> LegendDepotServerException.validateNonNull(null, message, status)
        );

        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(status, exception.getStatus());
    }

    @Test
    public void canValidateWithPredicateWithValidArgument()
    {
        Integer testValue = 10;
        String message = "Value must be positive";

        Integer result = LegendDepotServerException.validate(testValue, x -> x > 0, message);

        Assertions.assertEquals(testValue, result);
    }

    @Test
    public void canValidateWithPredicateWithInvalidArgumentThrowsException()
    {
        Integer testValue = -5;
        String message = "Value must be positive";

        LegendDepotServerException exception = Assertions.assertThrows(
            LegendDepotServerException.class,
            () -> LegendDepotServerException.validate(testValue, x -> x > 0, message)
        );

        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void canValidateWithPredicateAndStatusWithValidArgument()
    {
        Integer testValue = 10;
        String message = "Value must be positive";
        Response.Status status = Response.Status.FORBIDDEN;

        Integer result = LegendDepotServerException.validate(testValue, x -> x > 0, message, status);

        Assertions.assertEquals(testValue, result);
    }

    @Test
    public void canValidateWithPredicateAndStatusWithInvalidArgumentThrowsException()
    {
        Integer testValue = -5;
        String message = "Value must be positive";
        Response.Status status = Response.Status.FORBIDDEN;

        LegendDepotServerException exception = Assertions.assertThrows(
            LegendDepotServerException.class,
            () -> LegendDepotServerException.validate(testValue, x -> x > 0, message, status)
        );

        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(status, exception.getStatus());
    }

    @Test
    public void canValidateWithPredicateAndStatusWithNullStatusThrowsExceptionWithDefaultStatus()
    {
        Integer testValue = -5;
        String message = "Value must be positive";

        LegendDepotServerException exception = Assertions.assertThrows(
            LegendDepotServerException.class,
            () -> LegendDepotServerException.validate(testValue, x -> x > 0, message, null)
        );

        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void canValidateWithPredicateAndMessageFunctionWithValidArgument()
    {
        Integer testValue = 10;

        Integer result = LegendDepotServerException.validate(
            testValue,
            x -> x > 0,
            x -> "Value " + x + " must be positive"
        );

        Assertions.assertEquals(testValue, result);
    }

    @Test
    public void canValidateWithPredicateAndMessageFunctionWithInvalidArgumentThrowsException()
    {
        Integer testValue = -5;

        LegendDepotServerException exception = Assertions.assertThrows(
            LegendDepotServerException.class,
            () -> LegendDepotServerException.validate(
                testValue,
                x -> x > 0,
                x -> "Value " + x + " must be positive"
            )
        );

        Assertions.assertEquals("Value -5 must be positive", exception.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void canValidateWithPredicateMessageFunctionAndStatusWithValidArgument()
    {
        Integer testValue = 10;
        Response.Status status = Response.Status.FORBIDDEN;

        Integer result = LegendDepotServerException.validate(
            testValue,
            x -> x > 0,
            x -> "Value " + x + " must be positive",
            status
        );

        Assertions.assertEquals(testValue, result);
    }

    @Test
    public void canValidateWithPredicateMessageFunctionAndStatusWithInvalidArgumentThrowsException()
    {
        Integer testValue = -5;
        Response.Status status = Response.Status.FORBIDDEN;

        LegendDepotServerException exception = Assertions.assertThrows(
            LegendDepotServerException.class,
            () -> LegendDepotServerException.validate(
                testValue,
                x -> x > 0,
                x -> "Value " + x + " must be positive",
                status
            )
        );

        Assertions.assertEquals("Value -5 must be positive", exception.getMessage());
        Assertions.assertEquals(status, exception.getStatus());
    }

    @Test
    public void canValidateWithPredicateMessageFunctionAndStatusWithNullStatusThrowsExceptionWithDefaultStatus()
    {
        Integer testValue = -5;

        LegendDepotServerException exception = Assertions.assertThrows(
            LegendDepotServerException.class,
            () -> LegendDepotServerException.validate(
                testValue,
                x -> x > 0,
                x -> "Value " + x + " must be positive",
                null
            )
        );

        Assertions.assertEquals("Value -5 must be positive", exception.getMessage());
        Assertions.assertEquals(Response.Status.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void canValidateWithPredicateMessageFunctionAndStatusWithNullMessageFunctionThrowsExceptionWithNullMessage()
    {
        Integer testValue = -5;
        Response.Status status = Response.Status.FORBIDDEN;

        LegendDepotServerException exception = Assertions.assertThrows(
            LegendDepotServerException.class,
            () -> LegendDepotServerException.validate(
                testValue,
                x -> x > 0,
                (java.util.function.Function<Integer, String>) null,
                status
            )
        );

        Assertions.assertNull(exception.getMessage());
        Assertions.assertEquals(status, exception.getStatus());
    }
}
