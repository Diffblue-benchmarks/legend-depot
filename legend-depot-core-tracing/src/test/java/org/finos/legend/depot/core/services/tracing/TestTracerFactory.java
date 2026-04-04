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

import io.opentracing.noop.NoopTracerFactory;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TestTracerFactory
{
    @BeforeEach
    public void setUp()
    {
        TracerFactory.configure(null);
    }

    @Test
    public void testConfigureWithNullReturnsInstance()
    {
        TracerFactory factory = TracerFactory.configure(null);
        Assertions.assertNotNull(factory);
    }

    @Test
    public void testConfigureWithDisabledTracingReturnsInstance()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setEnabled(false);
        TracerFactory factory = TracerFactory.configure(config);
        Assertions.assertNotNull(factory);
    }

    @Test
    public void testConfigureWithEnabledTracingAndCustomProvider()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setEnabled(true);
        config.setTracerProvider(openTracingConfiguration -> NoopTracerFactory.create());
        TracerFactory factory = TracerFactory.configure(config);
        Assertions.assertNotNull(factory);
    }

    @Test
    public void testGetReturnsInstance()
    {
        Assertions.assertNotNull(TracerFactory.get());
    }

    @Test
    public void testGetInitializesInstanceWhenNull() throws Exception
    {
        Field instanceField = TracerFactory.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        TracerFactory factory = TracerFactory.get();
        Assertions.assertNotNull(factory);
    }

    @Test
    public void testGetTracerReturnsNonNull()
    {
        Assertions.assertNotNull(TracerFactory.getTracer());
    }

    @Test
    public void testAddTagsWithNullSpan()
    {
        Map<String, String> tags = new HashMap<>();
        tags.put("key", "value");
        TracerFactory.get().addTags(tags, null);
    }

    @Test
    public void testAddTagsWithEmptyTagsAndNullSpan()
    {
        TracerFactory.get().addTags(new HashMap<>(), null);
    }

    @Test
    public void testAddTagsWithNoActiveSpan()
    {
        Map<String, String> tags = new HashMap<>();
        tags.put("key", "value");
        TracerFactory.get().addTags(tags);
    }

    @Test
    public void testLogWithNull()
    {
        TracerFactory.get().log(null);
    }

    @Test
    public void testLogWithValueNoActiveSpan()
    {
        TracerFactory.get().log("test message");
    }

    @Test
    public void testExecuteWithTrace()
    {
        String result = TracerFactory.get().executeWithTrace("testLabel", () -> "result");
        Assertions.assertEquals("result", result);
    }

    @Test
    public void testExecuteWithTraceAndTags()
    {
        Map<String, String> tags = new HashMap<>();
        tags.put("key", "value");
        String result = TracerFactory.get().executeWithTrace("testLabel", () -> "result", tags);
        Assertions.assertEquals("result", result);
    }

    @Test
    public void testExecuteWithTraceThrowsRuntimeException()
    {
        Assertions.assertThrows(RuntimeException.class, () ->
            TracerFactory.get().executeWithTrace("testLabel", () ->
            {
                throw new RuntimeException("test error");
            })
        );
    }

    @Test
    public void testExecuteWithTraceWithTagsThrowsRuntimeException()
    {
        Map<String, String> tags = new HashMap<>();
        tags.put("key", "value");
        Assertions.assertThrows(RuntimeException.class, () ->
            TracerFactory.get().executeWithTrace("testLabel", () ->
            {
                throw new RuntimeException("test error");
            }, tags)
        );
    }
}
