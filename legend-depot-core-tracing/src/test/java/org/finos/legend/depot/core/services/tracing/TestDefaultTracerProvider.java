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
    DefaultTracerProvider tracerProvider = new DefaultTracerProvider();

    @BeforeEach
    public void setUp()
    {
        CollectorRegistry.defaultRegistry.clear();
    }

    @Test
    public void testCreateThrowsWhenUriIsNull()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> tracerProvider.create(config));
    }

    @Test
    public void testCreateThrowsWhenUriIsEmpty()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri("");

        Assertions.assertThrows(IllegalArgumentException.class, () -> tracerProvider.create(config));
    }

    @Test
    public void testCreateWithValidUri()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri("http://localhost:9411/api/v2/spans");
        config.setServiceName("test-service");

        Tracer tracer = tracerProvider.create(config);
        Assertions.assertNotNull(tracer);
    }

    @Test
    public void testCreateWithNullServiceNameUsesDefault()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri("http://localhost:9411/api/v2/spans");
        config.setServiceName(null);

        Tracer tracer = tracerProvider.create(config);
        Assertions.assertNotNull(tracer);
    }

    @Test
    public void testCreateWithEmptyServiceNameUsesDefault()
    {
        OpenTracingConfiguration config = new OpenTracingConfiguration();
        config.setOpenTracingUri("http://localhost:9411/api/v2/spans");
        config.setServiceName("");

        Tracer tracer = tracerProvider.create(config);
        Assertions.assertNotNull(tracer);
    }

    @Test
    public void testGetMemoryMetricsReporter()
    {
        InMemoryReporterMetrics metrics = tracerProvider.getMemoryMetricsReporter();
        Assertions.assertNotNull(metrics);
    }
}
