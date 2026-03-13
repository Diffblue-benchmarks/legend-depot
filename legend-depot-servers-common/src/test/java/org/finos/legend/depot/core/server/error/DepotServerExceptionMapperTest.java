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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;

public class DepotServerExceptionMapperTest
{
    @Test
    public void canConstructWithNoArguments() throws Exception
    {
        DepotServerExceptionMapper mapper = new DepotServerExceptionMapper();

        Assertions.assertNotNull(mapper);

        Field includeStackTraceField = mapper.getClass().getSuperclass().getDeclaredField("includeStackTrace");
        includeStackTraceField.setAccessible(true);
        boolean includeStackTrace = (boolean) includeStackTraceField.get(mapper);

        Assertions.assertFalse(includeStackTrace);
    }

    @Test
    public void canGetRedirectLocationWithValidUri() throws Exception
    {
        String validUri = "http://example.com/redirect";
        LegendDepotServerException exception = new LegendDepotServerException(validUri, Response.Status.MOVED_PERMANENTLY);

        Method getRedirectLocationMethod = DepotServerExceptionMapper.class.getDeclaredMethod("getRedirectLocation", LegendDepotServerException.class);
        getRedirectLocationMethod.setAccessible(true);

        URI result = (URI) getRedirectLocationMethod.invoke(null, exception);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(validUri, result.toString());
    }

    @Test
    public void canGetRedirectLocationWithNullMessage() throws Exception
    {
        LegendDepotServerException exception = new LegendDepotServerException(null, Response.Status.FOUND);

        Method getRedirectLocationMethod = DepotServerExceptionMapper.class.getDeclaredMethod("getRedirectLocation", LegendDepotServerException.class);
        getRedirectLocationMethod.setAccessible(true);

        URI result = (URI) getRedirectLocationMethod.invoke(null, exception);

        Assertions.assertNull(result);
    }

    @Test
    public void canGetRedirectLocationWithInvalidUri() throws Exception
    {
        String invalidUri = "not a valid uri with spaces";
        LegendDepotServerException exception = new LegendDepotServerException(invalidUri, Response.Status.TEMPORARY_REDIRECT);

        Method getRedirectLocationMethod = DepotServerExceptionMapper.class.getDeclaredMethod("getRedirectLocation", LegendDepotServerException.class);
        getRedirectLocationMethod.setAccessible(true);

        URI result = (URI) getRedirectLocationMethod.invoke(null, exception);

        Assertions.assertNull(result);
    }
}
