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

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

public class TestTracingResourceMethods
{
    private ConcreteTracingResource resource;

    static class ConcreteTracingResource extends TracingResource
    {
        public ConcreteTracingResource()
        {
            super();
        }

        public <T> T callHandle(String label, Supplier<T> supplier)
        {
            return handle(label, supplier);
        }

        public <T> T callHandle(String metricName, String label, Supplier<T> supplier)
        {
            return handle(metricName, label, supplier);
        }

        public <T> Response callHandle(String label, Supplier<T> supplier, Request request, Supplier<String> etagSupplier)
        {
            return handle(label, supplier, request, etagSupplier);
        }

        public <T> Response callHandle(String metricName, String label, Supplier<T> supplier, Request request, Supplier<String> etagSupplier)
        {
            return handle(metricName, label, supplier, request, etagSupplier);
        }

        public <T> Response callHandleResponse(String label, Supplier<T> supplier)
        {
            return handleResponse(label, supplier);
        }

        public <T> Response callHandleResponse(String metricName, String label, Supplier<T> supplier)
        {
            return handleResponse(metricName, label, supplier);
        }
    }

    static class NotModifiedRequest implements Request
    {
        @Override
        public String getMethod()
        {
            return "GET";
        }

        @Override
        public Variant selectVariant(List<Variant> variants)
        {
            return null;
        }

        @Override
        public Response.ResponseBuilder evaluatePreconditions(EntityTag eTag)
        {
            return Response.notModified(eTag);
        }

        @Override
        public Response.ResponseBuilder evaluatePreconditions(Date lastModified)
        {
            return null;
        }

        @Override
        public Response.ResponseBuilder evaluatePreconditions(Date lastModified, EntityTag eTag)
        {
            return null;
        }

        @Override
        public Response.ResponseBuilder evaluatePreconditions()
        {
            return null;
        }
    }

    @BeforeEach
    public void setUp()
    {
        CollectorRegistry.defaultRegistry.clear();
        PrometheusMetricsFactory.configure(new PrometheusConfiguration(true, new DepotPrometheusMetricsHandler("test")));
        TracerFactory.configure(null);
        resource = new ConcreteTracingResource();
    }

    @Test
    public void testHandleWithMetricAndLabel()
    {
        String result = resource.callHandle("testMetric", "testLabel", () -> "hello");
        Assertions.assertEquals("hello", result);
    }

    @Test
    public void testHandleWithSingleLabel()
    {
        String result = resource.callHandle("testLabel", () -> "world");
        Assertions.assertEquals("world", result);
    }

    @Test
    public void testHandleResponseWithLabel()
    {
        Response response = resource.callHandleResponse("testLabel", () -> "data");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void testHandleResponseWithMetricAndLabel()
    {
        Response response = resource.callHandleResponse("testMetric", "testLabel", () -> "data");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void testHandleWithNullEtagAndNullRequest()
    {
        Response response = resource.callHandle("metric", "label", () -> "data", null, () -> null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void testHandleWithEtagAndNullRequest()
    {
        Response response = resource.callHandle("metric", "label", () -> "data", null, () -> "etag123");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void testHandleWithEtagAndRequestNotModified()
    {
        Response response = resource.callHandle("metric", "label", () -> "data", new NotModifiedRequest(), () -> "etag123");
        Assertions.assertEquals(304, response.getStatus());
    }

    @Test
    public void testHandleWithRequestEtagSingleLabel()
    {
        Response response = resource.callHandle("label", () -> "data", null, () -> null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void testHandleWithRequestEtagSingleLabelNotModified()
    {
        Response response = resource.callHandle("label", () -> "data", new NotModifiedRequest(), () -> "etag456");
        Assertions.assertEquals(304, response.getStatus());
    }

    @Test
    public void testHandleThrowsExceptionPropagates()
    {
        Assertions.assertThrows(RuntimeException.class, () ->
            resource.callHandle("testMetric", "testLabel", () -> { throw new RuntimeException("test error"); })
        );
    }

    @Test
    public void testHandleWithNullReturnValue()
    {
        Object result = resource.callHandle("testMetric", "testLabel", () -> null);
        Assertions.assertNull(result);
    }
}
