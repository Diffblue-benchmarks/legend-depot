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

package org.finos.legend.depot.core.services.api.metrics;

import org.finos.legend.depot.core.services.tracing.resources.TracingResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TestVoidPrometheusMetricsHandler
{
    private VoidPrometheusMetricsHandler handler;

    @BeforeEach
    public void setUp()
    {
        handler = new VoidPrometheusMetricsHandler();
    }

    @Test
    public void canIncrementCount()
    {
        handler.incrementCount("testCounter");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canIncrementErrorCount()
    {
        handler.incrementErrorCount("testCounter");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canRegisterCounter()
    {
        handler.registerCounter("testCounter", "help message");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canObserve()
    {
        handler.observe("testMetric", 1000L, 2000L);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canRegisterSummary()
    {
        handler.registerSummary("testSummary", "help message");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canRegisterResourceSummaries()
    {
        handler.registerResourceSummaries(TestTracingResource.class);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canSetGauge()
    {
        handler.setGauge("testGauge", 42.0);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canRegisterGauge()
    {
        handler.registerGauge("testGauge", "help message");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canIncreaseGauge()
    {
        handler.increaseGauge("testGauge", 5);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canSetGaugeWithLabels()
    {
        List<String> labelValues = Arrays.asList("label1", "label2");
        handler.setGauge("testGauge", 42.0, labelValues);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canRegisterGaugeWithLabels()
    {
        List<String> labelNames = Arrays.asList("name1", "name2");
        handler.registerGauge("testGauge", "help message", labelNames);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canRegisterHistogram()
    {
        handler.registerHistogram("testHistogram", "help message");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canObserveHistogram()
    {
        handler.observeHistogram("testHistogram", 1000L, 2000L);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canRegisterHistogramWithLabels()
    {
        List<String> labelNames = Arrays.asList("name1", "name2");
        handler.registerHistogram("testHistogram", "help message", labelNames);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canObserveHistogramWithLabels()
    {
        handler.observeHistogram("testHistogram", 1000L, 2000L, "value1", "value2");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void canObserveHistogramWithAmount()
    {
        handler.observeHistogram("testHistogram", 123.45);
        Assertions.assertNotNull(handler);
    }

    private static class TestTracingResource extends TracingResource
    {
    }
}
