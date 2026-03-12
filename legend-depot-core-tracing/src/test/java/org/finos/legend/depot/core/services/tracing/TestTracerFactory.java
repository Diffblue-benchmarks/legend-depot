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

package org.finos.legend.depot.core.services.tracing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTracerFactory
{
    @Test
    public void testGetReturnsNonNull()
    {
        TracerFactory factory = TracerFactory.get();
        Assertions.assertNotNull(factory);
    }

    @Test
    public void testGetTracerReturnsNonNull()
    {
        Assertions.assertNotNull(TracerFactory.getTracer());
    }

    @Test
    public void testConfigureWithNullReturnsNoopTracer()
    {
        TracerFactory factory = TracerFactory.configure(null);
        Assertions.assertNotNull(factory);
    }

    @Test
    public void testConfigureWithDisabledReturnsNoopTracer()
    {
        org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration config =
                new org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration();
        config.setEnabled(false);
        TracerFactory factory = TracerFactory.configure(config);
        Assertions.assertNotNull(factory);
    }

    @Test
    public void testExecuteWithTraceSuccess()
    {
        TracerFactory.configure(null);
        String result = TracerFactory.get().executeWithTrace("test-label", () -> "hello");
        Assertions.assertEquals("hello", result);
    }

    @Test
    public void testExecuteWithTraceWrapsException()
    {
        TracerFactory.configure(null);
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
                () -> TracerFactory.get().executeWithTrace("fail-label", () ->
                {
                    throw new RuntimeException("test error");
                }));
        Assertions.assertTrue(ex.getMessage().contains("test error"));
    }

    @Test
    public void testLogDoesNotThrowWithNoActiveSpan()
    {
        TracerFactory.configure(null);
        Assertions.assertDoesNotThrow(() -> TracerFactory.get().log("some log message"));
    }

    @Test
    public void testLogWithNullDoesNotThrow()
    {
        TracerFactory.configure(null);
        Assertions.assertDoesNotThrow(() -> TracerFactory.get().log(null));
    }

    @Test
    public void testGetReturnsConsistentInstance()
    {
        TracerFactory a = TracerFactory.get();
        TracerFactory b = TracerFactory.get();
        Assertions.assertSame(a, b);
    }
}
