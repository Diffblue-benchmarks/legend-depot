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

import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestVoidPrometheusMetricsHandler
{
    VoidPrometheusMetricsHandler handler = new VoidPrometheusMetricsHandler();

    @Test
    public void canIncrementCount()
    {
        Assertions.assertDoesNotThrow(() -> handler.incrementCount("test_counter"));
    }

    @Test
    public void canIncrementErrorCount()
    {
        Assertions.assertDoesNotThrow(() -> handler.incrementErrorCount("test_counter"));
    }

    @Test
    public void canRegisterCounter()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerCounter("test_counter", "help message"));
    }

    @Test
    public void canObserve()
    {
        Assertions.assertDoesNotThrow(() -> handler.observe("test_metric", 100L, 200L));
    }

    @Test
    public void canRegisterSummary()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerSummary("test_summary", "help message"));
    }

    @Test
    public void canRegisterResourceSummaries()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerResourceSummaries(null));
    }

    @Test
    public void canSetGauge()
    {
        Assertions.assertDoesNotThrow(() -> handler.setGauge("test_gauge", 1.0));
    }

    @Test
    public void canRegisterGauge()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerGauge("test_gauge", "help message"));
    }

    @Test
    public void canIncreaseGauge()
    {
        Assertions.assertDoesNotThrow(() -> handler.increaseGauge("test_gauge", 5));
    }

    @Test
    public void canSetGaugeWithLabels()
    {
        Assertions.assertDoesNotThrow(() -> handler.setGauge("test_gauge", 1.0, Arrays.asList("label1")));
    }

    @Test
    public void canRegisterGaugeWithLabels()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerGauge("test_gauge", "help message", Arrays.asList("label1")));
    }

    @Test
    public void canRegisterHistogram()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerHistogram("test_histogram", "help message"));
    }

    @Test
    public void canObserveHistogram()
    {
        Assertions.assertDoesNotThrow(() -> handler.observeHistogram("test_histogram", 100L, 200L));
    }

    @Test
    public void canRegisterHistogramWithLabels()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerHistogram("test_histogram", "help message", Arrays.asList("label1")));
    }

    @Test
    public void canObserveHistogramWithLabels()
    {
        Assertions.assertDoesNotThrow(() -> handler.observeHistogram("test_histogram", 100L, 200L, "label1", "label2"));
    }

    @Test
    public void canObserveHistogramWithAmount()
    {
        Assertions.assertDoesNotThrow(() -> handler.observeHistogram("test_histogram", 1.5));
    }
}
