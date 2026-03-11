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
import io.prometheus.client.CollectorRegistry;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zipkin2.reporter.InMemoryReporterMetrics;

public class TestDefaultTracerProvider
{
    private DefaultTracerProvider provider = new DefaultTracerProvider();

    @BeforeEach
    public void setUp()
    {
        CollectorRegistry.defaultRegistry.clear();
    }

    @Test
    public void createThrowsExceptionWhenUriIsNull()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> provider.create(config));
    }

    @Test
    public void createThrowsExceptionWhenUriIsEmpty()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri("");

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> provider.create(config));
        Assertions.assertEquals("Invalid uri, openTracingUri cannot be empty", ex.getMessage());
    }

    @Test
    public void createTracerWithValidUri()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri("http://localhost:9411/api/v2/spans");

        Tracer tracer = provider.create(config);
        Assertions.assertNotNull(tracer);
        tracer.close();
    }

    @Test
    public void createTracerWithCustomServiceName()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri("http://localhost:9411/api/v2/spans");
        config.setServiceName("my-custom-service");

        Tracer tracer = provider.create(config);
        Assertions.assertNotNull(tracer);
        tracer.close();
    }

    @Test
    public void createTracerUsesDefaultServiceNameWhenNull()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri("http://localhost:9411/api/v2/spans");
        config.setServiceName(null);

        Tracer tracer = provider.create(config);
        Assertions.assertNotNull(tracer);
        tracer.close();
    }

    @Test
    public void createTracerUsesDefaultServiceNameWhenEmpty()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri("http://localhost:9411/api/v2/spans");
        config.setServiceName("");

        Tracer tracer = provider.create(config);
        Assertions.assertNotNull(tracer);
        tracer.close();
    }

    @Test
    public void getMemoryMetricsReporterReturnsValidMetrics()
    {
        InMemoryReporterMetrics metrics = provider.getMemoryMetricsReporter();
        Assertions.assertNotNull(metrics);
    }
}
