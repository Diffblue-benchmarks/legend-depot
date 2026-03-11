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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestBaseExceptionMapper
{
    @Test
    void canCreateWithIncludeStackTrace()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(true);
        assertNotNull(mapper);
    }

    @Test
    void canCreateWithoutIncludeStackTrace()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(false);
        assertNotNull(mapper);
    }

    @Test
    void canBuildDefaultResponseForRuntimeException()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(false);
        RuntimeException exception = new RuntimeException("test error");

        Response response = mapper.toResponse(exception);

        assertNotNull(response);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    void canBuildDefaultResponseForLegendDepotServerExceptionWithClientError()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(false);
        LegendDepotServerException exception = new LegendDepotServerException("bad request", Response.Status.BAD_REQUEST);

        Response response = mapper.toResponse(exception);

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    void canBuildDefaultResponseForLegendDepotServerExceptionWithServerError()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(true);
        LegendDepotServerException exception = new LegendDepotServerException("server error", Response.Status.INTERNAL_SERVER_ERROR);

        Response response = mapper.toResponse(exception);

        assertNotNull(response);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    void canBuildDefaultResponseForExceptionWithNonErrorStatus()
    {
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(false);
        LegendDepotServerException exception = new LegendDepotServerException("info", Response.Status.OK);

        Response response = mapper.toResponse(exception);

        assertNotNull(response);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
