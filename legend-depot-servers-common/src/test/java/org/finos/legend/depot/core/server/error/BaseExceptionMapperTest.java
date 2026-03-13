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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseExceptionMapperTest
{
    private static class TestExceptionMapper extends BaseExceptionMapper<Exception>
    {
        protected TestExceptionMapper(boolean includeStackTrace)
        {
            super(includeStackTrace);
        }

        @Override
        public Response toResponse(Exception exception)
        {
            return buildDefaultResponse(exception);
        }
    }

    @Test
    public void canCreateWithStackTraceEnabled()
    {
        // Execute
        TestExceptionMapper mapper = new TestExceptionMapper(true);

        // Verify
        assertNotNull(mapper);
        assertTrue(mapper.includeStackTrace);
    }

    @Test
    public void canCreateWithStackTraceDisabled()
    {
        // Execute
        TestExceptionMapper mapper = new TestExceptionMapper(false);

        // Verify
        assertNotNull(mapper);
        assertFalse(mapper.includeStackTrace);
    }
}
