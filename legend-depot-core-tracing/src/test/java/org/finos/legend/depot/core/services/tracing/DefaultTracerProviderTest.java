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

public class DefaultTracerProviderTest
{
    private DefaultTracerProvider provider;
    private OpenTracingConfiguration configuration;

    @BeforeEach
    public void setUp()
    {
        provider = new DefaultTracerProvider();
        configuration = new OpenTracingConfiguration();
        CollectorRegistry.defaultRegistry.clear();
    }

    @Test
    public void testCreateWithNullUri()
    {
        configuration.setOpenTracingUri(null);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            provider.create(configuration);
        });
        Assertions.assertEquals("Invalid uri, openTracingUri cannot be empty", exception.getMessage());
    }

    @Test
    public void testCreateWithEmptyUri()
    {
        configuration.setOpenTracingUri("");
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            provider.create(configuration);
        });
        Assertions.assertEquals("Invalid uri, openTracingUri cannot be empty", exception.getMessage());
    }

    @Test
    public void testCreateWithValidUriAndCustomServiceName()
    {
        configuration.setOpenTracingUri("http://localhost:9411/api/v2/spans");
        configuration.setServiceName("custom-service");
        Tracer tracer = provider.create(configuration);
        Assertions.assertNotNull(tracer);
    }

    @Test
    public void testCreateWithValidUriAndNullServiceName()
    {
        configuration.setOpenTracingUri("http://localhost:9411/api/v2/spans");
        configuration.setServiceName(null);
        Tracer tracer = provider.create(configuration);
        Assertions.assertNotNull(tracer);
    }

    @Test
    public void testCreateWithValidUriAndEmptyServiceName()
    {
        configuration.setOpenTracingUri("http://localhost:9411/api/v2/spans");
        configuration.setServiceName("");
        Tracer tracer = provider.create(configuration);
        Assertions.assertNotNull(tracer);
    }

    @Test
    public void testCreateWithInvalidUri()
    {
        configuration.setOpenTracingUri("invalid://malformed url");
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            provider.create(configuration);
        });
        Assertions.assertEquals("Invalid openTracingUri provided", exception.getMessage());
        Assertions.assertNotNull(exception.getCause());
    }

    @Test
    public void testGetMemoryMetricsReporter()
    {
        InMemoryReporterMetrics metrics = provider.getMemoryMetricsReporter();
        Assertions.assertNotNull(metrics);
    }
}
