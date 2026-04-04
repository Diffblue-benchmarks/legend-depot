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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

public class TestPrometheusHandlerGaugeHistogram
{
    DepotPrometheusMetricsHandler prometheusMetrics = (DepotPrometheusMetricsHandler) PrometheusMetricsFactory.configure(new PrometheusConfiguration(true, new DepotPrometheusMetricsHandler("test")));

    @BeforeEach
    public void setUp()
    {
        CollectorRegistry.defaultRegistry.clear();
    }

    @Test
    public void testObserveSummary()
    {
        prometheusMetrics.observe("mysummary", 1000L, 3000L);
        Assertions.assertEquals(1, prometheusMetrics.allSummaries.size());
    }

    @Test
    public void testRegisterGauge()
    {
        prometheusMetrics.registerGauge("gauge1", "help");
        Assertions.assertEquals(1, prometheusMetrics.allGauges.size());
        Assertions.assertNotNull(prometheusMetrics.allGauges.get("test_gauge1"));
    }

    @Test
    public void testRegisterGaugeWithLabels()
    {
        prometheusMetrics.registerGauge("gauge2", "help", Arrays.asList("label1"));
        Assertions.assertEquals(1, prometheusMetrics.allGauges.size());
        Assertions.assertNotNull(prometheusMetrics.allGauges.get("test_gauge2"));
    }

    @Test
    public void testSetGauge()
    {
        prometheusMetrics.setGauge("gauge3", 42.0);
        Assertions.assertEquals(1, prometheusMetrics.allGauges.size());
        Assertions.assertEquals(42.0, prometheusMetrics.allGauges.get("test_gauge3").get(), 0.0);
    }

    @Test
    public void testSetGaugeWithLabels()
    {
        prometheusMetrics.registerGauge("gauge4", "help", Arrays.asList("label1"));
        prometheusMetrics.setGauge("gauge4", 7.0, Arrays.asList("val1"));
        Assertions.assertEquals(7.0, prometheusMetrics.allGauges.get("test_gauge4").labels("val1").get(), 0.0);
    }

    @Test
    public void testSetGaugeWithLabelsThrowsIfNotRegistered()
    {
        Assertions.assertThrows(UnsupportedOperationException.class, () ->
            prometheusMetrics.setGauge("gauge_unregistered", 1.0, Arrays.asList("val1")));
    }

    @Test
    public void testIncreaseGauge()
    {
        prometheusMetrics.increaseGauge("gauge5", 3);
        Assertions.assertEquals(1, prometheusMetrics.allGauges.size());
        Assertions.assertEquals(3.0, prometheusMetrics.allGauges.get("test_gauge5").get(), 0.0);
    }

    @Test
    public void testRegisterHistogram()
    {
        prometheusMetrics.registerHistogram("hist1", "help");
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.size());
        Assertions.assertNotNull(prometheusMetrics.allHistograms.get("test_hist1"));
    }

    @Test
    public void testRegisterHistogramWithLabels()
    {
        prometheusMetrics.registerHistogram("hist2", "help", Arrays.asList("label1"));
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.size());
        Assertions.assertNotNull(prometheusMetrics.allHistograms.get("test_hist2"));
    }

    @Test
    public void testObserveHistogramWithStartEnd()
    {
        prometheusMetrics.observeHistogram("hist3", 1000L, 3000L);
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.size());
    }

    @Test
    public void testObserveHistogramWithAmount()
    {
        prometheusMetrics.observeHistogram("hist4", 5.0);
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.size());
    }

    @Test
    public void testObserveHistogramWithLabels()
    {
        prometheusMetrics.registerHistogram("hist5", "help", Arrays.asList("label1"));
        prometheusMetrics.observeHistogram("hist5", 1000L, 2000L, "val1");
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.size());
    }

    @Test
    public void testObserveHistogramWithLabelsThrowsIfNotRegistered()
    {
        Assertions.assertThrows(UnsupportedOperationException.class, () ->
            prometheusMetrics.observeHistogram("hist_unregistered", 1000L, 2000L, "val1"));
    }
}
