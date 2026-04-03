// Copyright 2024 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.depot.core.server.error;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatchAllExceptionMapperTest
{
    @Test
    public void testConstructorWithIncludeStackTrace()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(true);
        assertTrue(mapper.includeStackTrace);
    }

    @Test
    public void testConstructorWithoutIncludeStackTrace()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(false);
        assertFalse(mapper.includeStackTrace);
    }

    @Test
    public void testDefaultConstructorDoesNotIncludeStackTrace()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper();
        assertFalse(mapper.includeStackTrace);
    }

    @Test
    @Disabled("Requires JAX-RS RuntimeDelegate (Jersey + HK2 container) which is not available in unit tests")
    public void testToResponseWithRegularThrowable()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper();
        RuntimeException ex = new RuntimeException("test error");
        Response response = mapper.toResponse(ex);
        assertNotNull(response);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    @Disabled("Requires JAX-RS RuntimeDelegate (Jersey + HK2 container) which is not available in unit tests")
    public void testToResponseWithWebApplicationExceptionNonRedirect()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper();
        WebApplicationException ex = new WebApplicationException(Response.Status.NOT_FOUND);
        Response response = mapper.toResponse((Throwable) ex);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    @Disabled("Requires JAX-RS RuntimeDelegate (Jersey + HK2 container) which is not available in unit tests")
    public void testToResponseWithWebApplicationExceptionRedirect()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper();
        Response redirectResponse = Response.temporaryRedirect(URI.create("http://example.com")).build();
        WebApplicationException ex = new WebApplicationException(redirectResponse);
        Response response = mapper.toResponse((Throwable) ex);
        assertNotNull(response);
        assertEquals(Response.Status.TEMPORARY_REDIRECT.getStatusCode(), response.getStatus());
    }

    @Test
    @Disabled("Requires JAX-RS RuntimeDelegate (Jersey + HK2 container) which is not available in unit tests")
    public void testToResponseWithStackTraceIncluded()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(true);
        RuntimeException ex = new RuntimeException("error with stack trace");
        Response response = mapper.toResponse(ex);
        assertNotNull(response);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
