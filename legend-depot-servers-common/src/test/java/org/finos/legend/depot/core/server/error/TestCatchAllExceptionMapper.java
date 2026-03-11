// Copyright 2021 Goldman Sachs
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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.RuntimeDelegate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestCatchAllExceptionMapper
{
    @BeforeAll
    static void initJaxRsRuntime()
    {
        RuntimeDelegate.setInstance(new TestRuntimeDelegate());
    }

    @Test
    void canCreateWithIncludeStackTrace()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(true);
        assertNotNull(mapper);
    }

    @Test
    void canCreateWithDefaultConstructor()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper();
        assertNotNull(mapper);
    }

    @Test
    void canHandleGenericThrowable()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper();
        Response response = mapper.toResponse(new RuntimeException("test error"));
        assertNotNull(response);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void canHandleWebApplicationException()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper();
        Response badRequestResponse = Response.status(Response.Status.BAD_REQUEST).build();
        WebApplicationException wae = new WebApplicationException("bad request", badRequestResponse);
        Response response = mapper.toResponse(wae);
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void canHandleRedirectionWebApplicationException()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper();
        Response redirectResponse = Response.status(Response.Status.MOVED_PERMANENTLY).build();
        WebApplicationException wae = new WebApplicationException("redirect", redirectResponse);
        Response response = mapper.toResponse(wae);
        assertNotNull(response);
        assertEquals(Response.Status.MOVED_PERMANENTLY.getStatusCode(), response.getStatus());
    }

    @Test
    void canHandleWebApplicationExceptionWithStackTrace()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(true);
        Response badRequestResponse = Response.status(Response.Status.BAD_REQUEST).build();
        WebApplicationException wae = new WebApplicationException("bad request with trace", badRequestResponse);
        Response response = mapper.toResponse(wae);
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
