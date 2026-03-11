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

class TestDepotServerExceptionMapper
{
    @Test
    void canCreateWithIncludeStackTrace()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper(true);
        assertNotNull(mapper);
    }

    @Test
    void canCreateWithDefaultConstructor()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        assertNotNull(mapper);
    }

    @Test
    void canHandleClientError()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        LegendDepotServerException exception = new LegendDepotServerException("bad request", Response.Status.BAD_REQUEST);

        Response response = mapper.toResponse(exception);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void canHandleServerError()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper(true);
        LegendDepotServerException exception = new LegendDepotServerException("internal error", Response.Status.INTERNAL_SERVER_ERROR);

        Response response = mapper.toResponse(exception);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void canHandleRedirectionWithValidUri()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        LegendDepotServerException exception = new LegendDepotServerException("http://example.com/redirect", Response.Status.MOVED_PERMANENTLY);

        Response response = mapper.toResponse(exception);

        assertEquals(Response.Status.MOVED_PERMANENTLY.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
        assertEquals("http://example.com/redirect", response.getLocation().toString());
    }

    @Test
    void canHandleRedirectionWithInvalidUri()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        LegendDepotServerException exception = new LegendDepotServerException("not a valid uri {}", Response.Status.MOVED_PERMANENTLY);

        Response response = mapper.toResponse(exception);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void canHandleRedirectionWithNullMessage()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        LegendDepotServerException exception = new LegendDepotServerException(null, Response.Status.MOVED_PERMANENTLY);

        Response response = mapper.toResponse(exception);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void canHandleNonErrorStatus()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        LegendDepotServerException exception = new LegendDepotServerException("info", Response.Status.OK);

        Response response = mapper.toResponse(exception);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
