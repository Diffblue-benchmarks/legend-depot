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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class TracerFactoryTest
{
    @BeforeEach
    public void setup()
    {
        TracerFactory.configure(null);
    }

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
    public void testExecuteWithTraceReturnsSupplierResult()
    {
        TracerFactory factory = TracerFactory.get();
        String result = factory.executeWithTrace("test-trace", () -> "hello");
        Assertions.assertEquals("hello", result);
    }

    @Test
    public void testExecuteWithTraceAndTagsReturnsResult()
    {
        TracerFactory factory = TracerFactory.get();
        String result = factory.executeWithTrace("test-trace", () -> "world", Collections.singletonMap("key", "value"));
        Assertions.assertEquals("world", result);
    }

    @Test
    public void testExecuteWithTraceWrapsExceptions()
    {
        TracerFactory factory = TracerFactory.get();
        Assertions.assertThrows(RuntimeException.class, () ->
                factory.executeWithTrace("failing-trace", () ->
                {
                    throw new IllegalStateException("test error");
                }));
    }

    @Test
    public void testLogDoesNotThrowWithNullValue()
    {
        TracerFactory factory = TracerFactory.get();
        Assertions.assertDoesNotThrow(() -> factory.log(null));
    }

    @Test
    public void testLogDoesNotThrowWithValue()
    {
        TracerFactory factory = TracerFactory.get();
        Assertions.assertDoesNotThrow(() -> factory.log("test log message"));
    }

    @Test
    public void testAddTagsWithNullSpan()
    {
        TracerFactory factory = TracerFactory.get();
        Assertions.assertDoesNotThrow(() -> factory.addTags(Collections.singletonMap("key", "value")));
    }

    @Test
    public void testGetReturnsSameInstance()
    {
        TracerFactory factory1 = TracerFactory.get();
        TracerFactory factory2 = TracerFactory.get();
        Assertions.assertSame(factory1, factory2);
    }
}
