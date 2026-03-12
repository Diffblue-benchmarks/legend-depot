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

public class TestVoidPrometheusMetricsHandler
{
    private final VoidPrometheusMetricsHandler handler = new VoidPrometheusMetricsHandler();

    @Test
    public void testIncrementCountDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.incrementCount("test_counter"));
    }

    @Test
    public void testIncrementErrorCountDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.incrementErrorCount("test_error_counter"));
    }

    @Test
    public void testRegisterCounterDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerCounter("counter", "help"));
    }

    @Test
    public void testObserveDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.observe("metric", 0L, 100L));
    }

    @Test
    public void testRegisterSummaryDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerSummary("summary", "help"));
    }

    @Test
    public void testSetGaugeDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.setGauge("gauge", 42.0));
    }

    @Test
    public void testRegisterGaugeDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerGauge("gauge", "help"));
    }

    @Test
    public void testIncreaseGaugeDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.increaseGauge("gauge", 5));
    }

    @Test
    public void testSetGaugeWithLabelsDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.setGauge("gauge", 42.0, Arrays.asList("label1")));
    }

    @Test
    public void testRegisterGaugeWithLabelsDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerGauge("gauge", "help", Arrays.asList("label1")));
    }

    @Test
    public void testRegisterHistogramDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerHistogram("histogram", "help"));
    }

    @Test
    public void testObserveHistogramDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.observeHistogram("histogram", 0L, 100L));
    }

    @Test
    public void testObserveHistogramAmountDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.observeHistogram("histogram", 5.0));
    }

    @Test
    public void testRegisterHistogramWithLabelsDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.registerHistogram("histogram", "help", Arrays.asList("label1")));
    }

    @Test
    public void testObserveHistogramWithLabelsDoesNotThrow()
    {
        Assertions.assertDoesNotThrow(() -> handler.observeHistogram("histogram", 0L, 100L, "value1"));
    }
}
