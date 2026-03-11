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

package org.finos.legend.depot.core.services.metrics;

import io.prometheus.client.CollectorRegistry;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.finos.legend.depot.core.services.tracing.TracerFactory;
import org.finos.legend.depot.core.services.tracing.resources.TracingResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class TestTracingResourceMethods
{
    private TestableTracingResource resource;

    static class TestableTracingResource extends TracingResource
    {
        public <T> T callHandle(String label, java.util.function.Supplier<T> supplier)
        {
            return handle(label, supplier);
        }

        public <T> T callHandle(String resourceAPIMetricName, String label, java.util.function.Supplier<T> supplier)
        {
            return handle(resourceAPIMetricName, label, supplier);
        }

        public <T> Response callHandleResponse(String label, java.util.function.Supplier<T> supplier)
        {
            return handleResponse(label, supplier);
        }

        public <T> Response callHandleResponse(String resourceAPIMetricName, String label, java.util.function.Supplier<T> supplier)
        {
            return handleResponse(resourceAPIMetricName, label, supplier);
        }

        public <T> Response callHandleWithRequest(String label, java.util.function.Supplier<T> supplier, javax.ws.rs.core.Request request, java.util.function.Supplier<String> etagSupplier)
        {
            return handle(label, supplier, request, etagSupplier);
        }

        public <T> Response callHandleWithRequest(String resourceAPIMetricName, String label, java.util.function.Supplier<T> supplier, javax.ws.rs.core.Request request, java.util.function.Supplier<String> etagSupplier)
        {
            return handle(resourceAPIMetricName, label, supplier, request, etagSupplier);
        }
    }

    @BeforeEach
    public void setUp()
    {
        CollectorRegistry.defaultRegistry.clear();
        PrometheusMetricsFactory.configure(new PrometheusConfiguration(true, new DepotPrometheusMetricsHandler("test")));
        TracerFactory.configure(null);
        resource = new TestableTracingResource();
    }

    @Test
    public void canCreateTracingResource()
    {
        TracingResource tr = new TestableTracingResource();
        Assertions.assertNotNull(tr);
    }

    @Test
    public void canHandleWithLabel()
    {
        String result = resource.callHandle("testMetric", () -> "hello");
        Assertions.assertEquals("hello", result);
    }

    @Test
    public void canHandleWithMetricNameAndLabel()
    {
        String result = resource.callHandle("testMetric", "testLabel", () -> "world");
        Assertions.assertEquals("world", result);
    }

    @Test
    public void canHandleResponse()
    {
        Response response = resource.callHandleResponse("testLabel", () -> "data");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void canHandleResponseWithMetricName()
    {
        Response response = resource.callHandleResponse("testMetric", "testLabel", () -> "data");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void canHandleWithRequestAndNullEtag()
    {
        Response response = resource.callHandleWithRequest("testLabel", () -> "data", null, () -> null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertNotNull(response.getMetadata().get("Cache-Control"));
    }

    @Test
    public void canHandleWithRequestAndEtag()
    {
        Response response = resource.callHandleWithRequest("testMetric", "testLabel", () -> "data", null, () -> "etag123");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertNotNull(response.getMetadata().get("ETag"));
    }

    @Test
    public void handleWithRequestShortcutDelegatesToFullHandle()
    {
        Response response = resource.callHandleWithRequest("testLabel", () -> "data", null, () -> "etag456");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void handleThrowsOnSupplierException()
    {
        Assertions.assertThrows(RuntimeException.class, () ->
            resource.callHandle("errorMetric", "errorLabel", () ->
            {
                throw new RuntimeException("test error");
            })
        );
    }

    @Test
    public void handleThrowsOnSupplierExceptionWithNullMessage()
    {
        Assertions.assertThrows(RuntimeException.class, () ->
            resource.callHandle("errorMetric", "errorLabel", () ->
            {
                throw new RuntimeException((String) null);
            })
        );
    }
}
