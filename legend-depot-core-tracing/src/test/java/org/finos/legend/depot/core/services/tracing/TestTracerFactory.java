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
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.TracerProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void canExecuteWithTraceSuccessfully()
    {
        String result = TracerFactory.get().executeWithTrace("testOperation", () -> "success");

        Assertions.assertEquals("success", result);
    }

    @Test
    public void canExecuteWithTraceWithTags()
    {
        Map<String, String> tags = new HashMap<>();
        tags.put("key1", "value1");
        tags.put("key2", "value2");

        String result = TracerFactory.get().executeWithTrace("testOperation", () -> "success", tags);

        Assertions.assertEquals("success", result);
    }

    @Test
    public void canExecuteWithTraceWithEmptyTags()
    {
        Map<String, String> emptyTags = new HashMap<>();

        Integer result = TracerFactory.get().executeWithTrace("testOperation", () -> 42, emptyTags);

        Assertions.assertEquals(42, result);
    }

    @Test
    public void canExecuteWithTraceReturnsNull()
    {
        String result = TracerFactory.get().executeWithTrace("testOperation", () -> null);

        Assertions.assertNull(result);
    }

    @Test
    public void testExecuteWithTraceHandlesExceptionWithoutActiveSpan()
    {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            TracerFactory.get().executeWithTrace("testOperation", () -> {
                throw new IllegalArgumentException("Test exception");
            });
        });

        Assertions.assertTrue(thrown.getMessage().contains("[testOperation]"));
        Assertions.assertTrue(thrown.getMessage().contains("Test exception"));
        Assertions.assertNotNull(thrown.getCause());
        Assertions.assertTrue(thrown.getCause() instanceof IllegalArgumentException);
    }

    @Test
    public void testExecuteWithTraceHandlesExceptionWithTags()
    {
        Map<String, String> tags = new HashMap<>();
        tags.put("operationType", "test");

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            TracerFactory.get().executeWithTrace("testOperation", () -> {
                throw new RuntimeException("Test runtime exception");
            }, tags);
        });

        Assertions.assertTrue(thrown.getMessage().contains("[testOperation]"));
        Assertions.assertTrue(thrown.getMessage().contains("Test runtime exception"));
        Assertions.assertNotNull(thrown.getCause());
        Assertions.assertTrue(thrown.getCause() instanceof RuntimeException);
    }

    @Test
    public void testExecuteWithTraceHandlesNullPointerException()
    {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            TracerFactory.get().executeWithTrace("testOperation", () -> {
                throw new NullPointerException("Null value encountered");
            });
        });

        Assertions.assertTrue(thrown.getMessage().contains("[testOperation]"));
        Assertions.assertTrue(thrown.getMessage().contains("Null value encountered"));
        Assertions.assertNotNull(thrown.getCause());
        Assertions.assertTrue(thrown.getCause() instanceof NullPointerException);
    }

    @Test
    public void testConfigureWithNullConfiguration()
    {
        TracerFactory factory = TracerFactory.configure(null);

        Assertions.assertNotNull(factory);
        Assertions.assertNotNull(TracerFactory.getTracer());
    }

    @Test
    public void testConfigureWithDisabledConfiguration()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setEnabled(false);

        TracerFactory factory = TracerFactory.configure(config);

        Assertions.assertNotNull(factory);
        Assertions.assertNotNull(TracerFactory.getTracer());
    }

    @Test
    public void testConfigureWithEnabledConfigurationAndCustomProvider()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setEnabled(true);
        config.setServiceName("test-service");
        TracerProvider customProvider = (configuration) -> NoopTracerFactory.create();
        config.setTracerProvider(customProvider);

        TracerFactory factory = TracerFactory.configure(config);

        Assertions.assertNotNull(factory);
        Assertions.assertNotNull(TracerFactory.getTracer());
    }

    @Test
    public void testConfigureWithEnabledConfigurationAndNullProvider()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setEnabled(true);
        config.setOpenTracingUri("http://localhost:9411/api/v2/spans");
        config.setServiceName("test-service");
        config.setTracerProvider(null);

        TracerFactory factory = TracerFactory.configure(config);

        Assertions.assertNotNull(factory);
        Assertions.assertNotNull(TracerFactory.getTracer());
    }
}
