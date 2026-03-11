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

import io.opentracing.Tracer;
import io.opentracing.noop.NoopTracerFactory;
import io.opentracing.util.GlobalTracer;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestTracerFactory
{
    @BeforeEach
    public void setUp() throws Exception
    {
        Field instanceField = TracerFactory.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        Field tracerField = GlobalTracer.class.getDeclaredField("tracer");
        tracerField.setAccessible(true);
        tracerField.set(null, NoopTracerFactory.create());

        Field isRegisteredField = GlobalTracer.class.getDeclaredField("isRegistered");
        isRegisteredField.setAccessible(true);
        isRegisteredField.set(null, false);
    }

    @Test
    public void canConfigureWithNull()
    {
        TracerFactory factory = TracerFactory.configure(null);
        Assertions.assertNotNull(factory);
    }

    @Test
    public void canConfigureWithDisabledConfig()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setEnabled(false);
        TracerFactory factory = TracerFactory.configure(config);
        Assertions.assertNotNull(factory);
    }

    @Test
    public void canGetInstance()
    {
        TracerFactory factory = TracerFactory.get();
        Assertions.assertNotNull(factory);
    }

    @Test
    public void canGetTracer()
    {
        TracerFactory.configure(null);
        Tracer tracer = TracerFactory.getTracer();
        Assertions.assertNotNull(tracer);
    }

    @Test
    public void getInitializesWhenInstanceIsNull()
    {
        TracerFactory first = TracerFactory.get();
        TracerFactory second = TracerFactory.get();
        Assertions.assertNotNull(first);
        Assertions.assertNotNull(second);
    }

    @Test
    public void canExecuteWithTraceSuccessfully()
    {
        TracerFactory.configure(null);
        TracerFactory factory = TracerFactory.get();
        String result = factory.executeWithTrace("testLabel", () -> "hello");
        Assertions.assertEquals("hello", result);
    }

    @Test
    public void canExecuteWithTraceAndTags()
    {
        TracerFactory.configure(null);
        TracerFactory factory = TracerFactory.get();
        Map<String, String> tags = new HashMap<>();
        tags.put("key1", "value1");
        String result = factory.executeWithTrace("testLabel", () -> "world", tags);
        Assertions.assertEquals("world", result);
    }

    @Test
    public void executeWithTraceWrapsException()
    {
        TracerFactory.configure(null);
        TracerFactory factory = TracerFactory.get();
        Assertions.assertThrows(RuntimeException.class, () ->
            factory.executeWithTrace("failLabel", () ->
            {
                throw new RuntimeException("test error");
            })
        );
    }

    @Test
    public void executeWithTraceExceptionMessageContainsLabel()
    {
        TracerFactory.configure(null);
        TracerFactory factory = TracerFactory.get();
        try
        {
            factory.executeWithTrace("myLabel", () ->
            {
                throw new RuntimeException("boom");
            });
            Assertions.fail("Expected RuntimeException");
        }
        catch (RuntimeException e)
        {
            Assertions.assertTrue(e.getMessage().contains("myLabel"));
            Assertions.assertTrue(e.getMessage().contains("boom"));
        }
    }

    @Test
    public void canAddTagsWithNullSpan()
    {
        TracerFactory.configure(null);
        TracerFactory factory = TracerFactory.get();
        Map<String, String> tags = new HashMap<>();
        tags.put("key", "value");
        factory.addTags(tags, null);
    }

    @Test
    public void canAddTagsWithEmptyMap()
    {
        TracerFactory.configure(null);
        TracerFactory factory = TracerFactory.get();
        factory.addTags(Collections.emptyMap());
    }

    @Test
    public void canLogNullValue()
    {
        TracerFactory.configure(null);
        TracerFactory factory = TracerFactory.get();
        factory.log(null);
    }

    @Test
    public void canLogValueWithNoActiveSpan()
    {
        TracerFactory.configure(null);
        TracerFactory factory = TracerFactory.get();
        factory.log("test log message");
    }

    @Test
    public void executeWithTraceWithEmptyTags()
    {
        TracerFactory.configure(null);
        TracerFactory factory = TracerFactory.get();
        Integer result = factory.executeWithTrace("compute", () -> 42, Collections.emptyMap());
        Assertions.assertEquals(42, result);
    }

    @Test
    public void canConfigureWithEnabledConfigAndCustomTracerProvider()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setEnabled(true);
        config.setTracerProvider(configuration -> NoopTracerFactory.create());

        TracerFactory factory = TracerFactory.configure(config);
        Assertions.assertNotNull(factory);
        Assertions.assertNotNull(TracerFactory.getTracer());
    }

    @Test
    public void canConfigureWithEnabledConfigAndNullTracerProvider()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setEnabled(true);
        config.setTracerProvider(null);
        config.setOpenTracingUri(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> TracerFactory.configure(config));
    }
}
