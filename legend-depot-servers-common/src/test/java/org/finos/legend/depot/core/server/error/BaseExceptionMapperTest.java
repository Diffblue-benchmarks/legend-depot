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

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class BaseExceptionMapperTest
{
    /**
     * A concrete subclass that intercepts buildResponse calls so we can
     * inspect the arguments without needing a JAX-RS runtime (Jersey).
     */
    private static class CapturingExceptionMapper extends BaseExceptionMapper<Throwable>
    {
        Response.Status capturedStatus;
        ExtendedErrorMessage capturedMessage;

        CapturingExceptionMapper(boolean includeStackTrace)
        {
            super(includeStackTrace);
        }

        @Override
        protected Response buildResponse(Response.Status status, ExtendedErrorMessage errorMessage)
        {
            this.capturedStatus = status;
            this.capturedMessage = errorMessage;
            return null;
        }

        @Override
        public Response toResponse(Throwable throwable)
        {
            return buildDefaultResponse(throwable);
        }
    }

    @Test
    public void testConstructorSetsIncludeStackTraceTrue()
    {
        CapturingExceptionMapper mapper = new CapturingExceptionMapper(true);
        Assertions.assertTrue(mapper.includeStackTrace);
    }

    @Test
    public void testConstructorSetsIncludeStackTraceFalse()
    {
        CapturingExceptionMapper mapper = new CapturingExceptionMapper(false);
        Assertions.assertFalse(mapper.includeStackTrace);
    }

    @Test
    public void testBuildDefaultResponseForGenericExceptionUsesInternalServerError()
    {
        CapturingExceptionMapper mapper = new CapturingExceptionMapper(false);
        RuntimeException ex = new RuntimeException("generic error");
        mapper.buildDefaultResponse(ex);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, mapper.capturedStatus);
        Assertions.assertNotNull(mapper.capturedMessage);
        Assertions.assertEquals(500, mapper.capturedMessage.getCode());
        Assertions.assertEquals("generic error", mapper.capturedMessage.getMessage());
    }

    @Test
    public void testBuildDefaultResponseForClientErrorLegendException()
    {
        CapturingExceptionMapper mapper = new CapturingExceptionMapper(false);
        LegendDepotServerException ex = new LegendDepotServerException("not found", Response.Status.NOT_FOUND);
        mapper.buildDefaultResponse(ex);
        Assertions.assertEquals(Response.Status.NOT_FOUND, mapper.capturedStatus);
        Assertions.assertEquals(404, mapper.capturedMessage.getCode());
    }

    @Test
    public void testBuildDefaultResponseForServerErrorLegendException()
    {
        CapturingExceptionMapper mapper = new CapturingExceptionMapper(false);
        LegendDepotServerException ex = new LegendDepotServerException("service unavailable", Response.Status.SERVICE_UNAVAILABLE);
        mapper.buildDefaultResponse(ex);
        Assertions.assertEquals(Response.Status.SERVICE_UNAVAILABLE, mapper.capturedStatus);
        Assertions.assertEquals(503, mapper.capturedMessage.getCode());
    }

    @Test
    public void testBuildDefaultResponseIncludesStackTraceWhenEnabled()
    {
        CapturingExceptionMapper mapper = new CapturingExceptionMapper(true);
        RuntimeException ex = new RuntimeException("with stack");
        mapper.buildDefaultResponse(ex);
        Assertions.assertNotNull(mapper.capturedMessage.getStackTrace());
        Assertions.assertTrue(mapper.capturedMessage.getStackTrace().contains("RuntimeException"));
    }

    @Test
    public void testBuildDefaultResponseExcludesStackTraceWhenDisabled()
    {
        CapturingExceptionMapper mapper = new CapturingExceptionMapper(false);
        RuntimeException ex = new RuntimeException("no stack");
        mapper.buildDefaultResponse(ex);
        Assertions.assertNull(mapper.capturedMessage.getStackTrace());
    }

    @Test
    public void testBuildDefaultResponseForRedirectionStatusUsesInternalServerError()
    {
        CapturingExceptionMapper mapper = new CapturingExceptionMapper(false);
        // WebApplicationException with a known status whose family is REDIRECTION (not CLIENT_ERROR/SERVER_ERROR)
        // so buildDefaultResponse keeps status as INTERNAL_SERVER_ERROR
        LegendDepotServerException ex = new LegendDepotServerException("moved", Response.Status.MOVED_PERMANENTLY);
        mapper.buildDefaultResponse(ex);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, mapper.capturedStatus);
        Assertions.assertEquals(301, mapper.capturedMessage.getCode());
    }

    @Test
    public void testBuildDefaultResponseErrorMessageHasTimestamp()
    {
        CapturingExceptionMapper mapper = new CapturingExceptionMapper(false);
        RuntimeException ex = new RuntimeException("ts test");
        mapper.buildDefaultResponse(ex);
        Assertions.assertNotNull(mapper.capturedMessage.getTimestamp());
    }

    @Test
    public void testBuildResponsePassesStatusAndMessageDirectly()
    {
        CapturingExceptionMapper mapper = new CapturingExceptionMapper(false);
        ExtendedErrorMessage msg = ExtendedErrorMessage.newExtendedErrorMessage(500, "error", null, null, null);
        mapper.buildResponse(Response.Status.INTERNAL_SERVER_ERROR, msg);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, mapper.capturedStatus);
        Assertions.assertSame(msg, mapper.capturedMessage);
    }

    @Test
    public void testBuildResponseWithMismatchedStatusCodePassesGivenStatus()
    {
        CapturingExceptionMapper mapper = new CapturingExceptionMapper(false);
        ExtendedErrorMessage msg = ExtendedErrorMessage.newExtendedErrorMessage(500, "error", null, null, null);
        mapper.buildResponse(Response.Status.NOT_FOUND, msg);
        Assertions.assertEquals(Response.Status.NOT_FOUND, mapper.capturedStatus);
        Assertions.assertSame(msg, mapper.capturedMessage);
    }
}
