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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestVoidPrometheusMetricsHandler
{
    VoidPrometheusMetricsHandler handler = new VoidPrometheusMetricsHandler();

    @Test
    public void testIncrementCount()
    {
        handler.incrementCount("counter");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testIncrementErrorCount()
    {
        handler.incrementErrorCount("counter");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testRegisterCounter()
    {
        handler.registerCounter("counter", "help");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testObserve()
    {
        handler.observe("metric", 0L, 100L);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testRegisterSummary()
    {
        handler.registerSummary("summary", "help");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testRegisterResourceSummaries()
    {
        handler.registerResourceSummaries(null);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testSetGauge()
    {
        handler.setGauge("gauge", 1.0D);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testRegisterGauge()
    {
        handler.registerGauge("gauge", "help");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testIncreaseGauge()
    {
        handler.increaseGauge("gauge", 5);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testSetGaugeWithLabels()
    {
        List<String> labelValues = Arrays.asList("val1", "val2");
        handler.setGauge("gauge", 1.0D, labelValues);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testRegisterGaugeWithLabels()
    {
        List<String> labelNames = Arrays.asList("label1", "label2");
        handler.registerGauge("gauge", "help", labelNames);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testRegisterHistogram()
    {
        handler.registerHistogram("histogram", "help");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testObserveHistogram()
    {
        handler.observeHistogram("histogram", 0L, 100L);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testRegisterHistogramWithLabels()
    {
        List<String> labelNames = Collections.singletonList("label1");
        handler.registerHistogram("histogram", "help", labelNames);
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testObserveHistogramWithLabelValues()
    {
        handler.observeHistogram("histogram", 0L, 100L, "val1", "val2");
        Assertions.assertNotNull(handler);
    }

    @Test
    public void testObserveHistogramDouble()
    {
        handler.observeHistogram("histogram", 1.5D);
        Assertions.assertNotNull(handler);
    }
}
