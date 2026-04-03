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

import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import org.jvnet.hk2.external.generator.ServiceLocatorGeneratorImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class DepotServerExceptionMapperTest
{
    @BeforeAll
    static void setUpJerseyRuntime()
    {
        JerseyGuiceUtils.install(new ServiceLocatorGeneratorImpl());
    }

    @AfterAll
    static void tearDownJerseyRuntime()
    {
        JerseyGuiceUtils.reset();
    }

    @Test
    public void testConstructorWithIncludeStackTrace()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper(true);
        Assertions.assertNotNull(mapper);
    }

    @Test
    public void testDefaultConstructor()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        Assertions.assertNotNull(mapper);
    }

    @Test
    public void testToResponseClientError()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        LegendDepotServerException exception = new LegendDepotServerException("Not found", Response.Status.NOT_FOUND);
        Response response = mapper.toResponse(exception);
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testToResponseClientErrorDoesNotIncludeStackTrace()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper(true);
        LegendDepotServerException exception = new LegendDepotServerException("Bad request", Response.Status.BAD_REQUEST);
        Response response = mapper.toResponse(exception);
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Assertions.assertNotNull(response.getEntity());
    }

    @Test
    public void testToResponseServerError()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        LegendDepotServerException exception = new LegendDepotServerException("Internal error", Response.Status.INTERNAL_SERVER_ERROR);
        Response response = mapper.toResponse(exception);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testToResponseServerErrorWithStackTrace()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper(true);
        LegendDepotServerException exception = new LegendDepotServerException("Internal error", Response.Status.INTERNAL_SERVER_ERROR);
        Response response = mapper.toResponse(exception);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testToResponseRedirectionWithValidUri()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        LegendDepotServerException exception = new LegendDepotServerException("http://example.com/redirect", Response.Status.MOVED_PERMANENTLY);
        Response response = mapper.toResponse(exception);
        Assertions.assertEquals(Response.Status.MOVED_PERMANENTLY.getStatusCode(), response.getStatus());
        Assertions.assertNotNull(response.getLocation());
    }

    @Test
    public void testToResponseRedirectionWithNullMessage()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        LegendDepotServerException exception = new LegendDepotServerException(null, Response.Status.MOVED_PERMANENTLY);
        Response response = mapper.toResponse(exception);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testToResponseRedirectionWithInvalidUri()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        LegendDepotServerException exception = new LegendDepotServerException("http://not a valid uri with spaces", Response.Status.MOVED_PERMANENTLY);
        Response response = mapper.toResponse(exception);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testToResponseDefaultCaseWithSuccessStatus()
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();
        LegendDepotServerException exception = new LegendDepotServerException("OK", Response.Status.OK);
        Response response = mapper.toResponse(exception);
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
