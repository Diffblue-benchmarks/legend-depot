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

public class TestPrometheusHandler
{
    DepotPrometheusMetricsHandler prometheusMetrics = (DepotPrometheusMetricsHandler) PrometheusMetricsFactory.configure(new PrometheusConfiguration(true,new DepotPrometheusMetricsHandler("test")));

    @BeforeEach
    public void setUp()
    {
        CollectorRegistry.defaultRegistry.clear();
    }

    @Test
    public void canConfigureHandler()
    {
       Assertions.assertNotNull(PrometheusMetricsFactory.getInstance());
    }

    @Test
    public void canCreateCounterAndIncrement()
    {
        prometheusMetrics.registerCounter("test1","help");
        Assertions.assertEquals(1, prometheusMetrics.allCounters.size());
        Assertions.assertEquals(1, prometheusMetrics.allErrorCounters.size());

        Assertions.assertEquals(0.0D, prometheusMetrics.allCounters.get("test_test1").get(),0.0D);
        Assertions.assertEquals(0.0D, prometheusMetrics.allErrorCounters.get("test_test1_errors").get(),0.0);

        prometheusMetrics.incrementCount("test1");
        Assertions.assertEquals(1.0D, prometheusMetrics.allCounters.get("test_test1").get(),0.0);
        Assertions.assertEquals(0.0D, prometheusMetrics.allErrorCounters.get("test_test1_errors").get(),0.0);

        prometheusMetrics.incrementErrorCount("test1");
        Assertions.assertEquals(1.0D, prometheusMetrics.allCounters.get("test_test1").get(),0.0);
        Assertions.assertEquals(1.0D, prometheusMetrics.allErrorCounters.get("test_test1_errors").get(),0.0);
    }

    @Test
    public void cannotRegisterSameCounterTwice()
    {
        prometheusMetrics.registerCounter("test2","help");
        prometheusMetrics.incrementCount("test2");
        Assertions.assertEquals(1, prometheusMetrics.allCounters.size());
        Assertions.assertEquals(1, prometheusMetrics.allErrorCounters.size());
        prometheusMetrics.registerCounter("test2","help");
        Assertions.assertEquals(1, prometheusMetrics.allCounters.size());
        Assertions.assertEquals(1, prometheusMetrics.allErrorCounters.size());
        prometheusMetrics.incrementCount("test2");
        Assertions.assertEquals(2.0D, prometheusMetrics.allCounters.get("test_test2").get(),0.0);
        Assertions.assertEquals(0.0D, prometheusMetrics.allErrorCounters.get("test_test2_errors").get(),0.0);

    }

    @Test
    public void incrementUnknownMetric()
    {
        prometheusMetrics.incrementCount("test2");
        Assertions.assertEquals(1, prometheusMetrics.allCounters.size());
        Assertions.assertEquals(0, prometheusMetrics.allErrorCounters.size());

        prometheusMetrics.incrementCount("test2");
        Assertions.assertEquals(2.0D, prometheusMetrics.allCounters.get("test_test2").get(),0.0);
        Assertions.assertNull(prometheusMetrics.allErrorCounters.get("test_test2_errors"));

        prometheusMetrics.incrementErrorCount("test2");
        Assertions.assertEquals(1.0D, prometheusMetrics.allErrorCounters.get("test_test2_errors").get(),0.0);
    }



    @Test
    public void testSummaryRegistration()
    {
        prometheusMetrics.registerSummary("test","test");
        Assertions.assertEquals(1,prometheusMetrics.allSummaries.keySet().size());

        prometheusMetrics.registerSummary("test","test");
        Assertions.assertEquals(1,prometheusMetrics.allSummaries.keySet().size());
    }

    @Test
    public void testObserveSummary()
    {
        prometheusMetrics.observe("testSummary", 1000L, 2000L);
        Assertions.assertEquals(1, prometheusMetrics.allSummaries.keySet().size());
        Assertions.assertNotNull(prometheusMetrics.allSummaries.get("test_testsummary"));
    }

    @Test
    public void testRegisterGaugeWithoutLabels()
    {
        prometheusMetrics.registerGauge("testGauge", "help message");
        Assertions.assertEquals(1, prometheusMetrics.allGauges.keySet().size());
        Assertions.assertNotNull(prometheusMetrics.allGauges.get("test_testgauge"));
    }

    @Test
    public void testRegisterGaugeWithLabels()
    {
        prometheusMetrics.registerGauge("testGaugeLabels", "help message", java.util.Arrays.asList("label1", "label2"));
        Assertions.assertEquals(1, prometheusMetrics.allGauges.keySet().size());
        Assertions.assertNotNull(prometheusMetrics.allGauges.get("test_testgaugelabels"));
    }

    @Test
    public void testSetGaugeWithoutLabels()
    {
        prometheusMetrics.setGauge("testGaugeSet", 42.5);
        Assertions.assertEquals(1, prometheusMetrics.allGauges.keySet().size());
        Assertions.assertEquals(42.5, prometheusMetrics.allGauges.get("test_testgaugeset").get(), 0.01);
    }

    @Test
    public void testSetGaugeWithLabels()
    {
        prometheusMetrics.registerGauge("testGaugeSetLabels", "help", java.util.Arrays.asList("label1"));
        prometheusMetrics.setGauge("testGaugeSetLabels", 10.0, java.util.Arrays.asList("value1"));
        Assertions.assertEquals(1, prometheusMetrics.allGauges.keySet().size());
    }

    @Test
    public void testSetGaugeWithLabelsThrowsExceptionWhenNotRegistered()
    {
        Exception exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            prometheusMetrics.setGauge("unregisteredGauge", 10.0, java.util.Arrays.asList("value1"));
        });
        Assertions.assertEquals("Please register the gauge first if you need labels", exception.getMessage());
    }

    @Test
    public void testIncreaseGauge()
    {
        prometheusMetrics.increaseGauge("testGaugeInc", 5);
        Assertions.assertEquals(1, prometheusMetrics.allGauges.keySet().size());
        Assertions.assertEquals(5.0, prometheusMetrics.allGauges.get("test_testgaugeinc").get(), 0.01);

        prometheusMetrics.increaseGauge("testGaugeInc", 3);
        Assertions.assertEquals(8.0, prometheusMetrics.allGauges.get("test_testgaugeinc").get(), 0.01);
    }

    @Test
    public void testRegisterHistogramWithoutLabels()
    {
        prometheusMetrics.registerHistogram("testHistogram", "help message");
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.keySet().size());
        Assertions.assertNotNull(prometheusMetrics.allHistograms.get("test_testhistogram"));
    }

    @Test
    public void testRegisterHistogramWithLabels()
    {
        prometheusMetrics.registerHistogram("testHistogramLabels", "help message", java.util.Arrays.asList("label1", "label2"));
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.keySet().size());
        Assertions.assertNotNull(prometheusMetrics.allHistograms.get("test_testhistogramlabels"));
    }

    @Test
    public void testObserveHistogramWithLongValues()
    {
        prometheusMetrics.observeHistogram("testHistogramObserve", 1000L, 2000L);
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.keySet().size());
        Assertions.assertNotNull(prometheusMetrics.allHistograms.get("test_testhistogramobserve"));
    }

    @Test
    public void testObserveHistogramWithDoubleValue()
    {
        prometheusMetrics.observeHistogram("testHistogramObserveDouble", 42.5);
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.keySet().size());
        Assertions.assertNotNull(prometheusMetrics.allHistograms.get("test_testhistogramobservedouble"));
    }

    @Test
    public void testObserveHistogramWithLabels()
    {
        prometheusMetrics.registerHistogram("testHistogramWithLabels", "help", java.util.Arrays.asList("label1"));
        prometheusMetrics.observeHistogram("testHistogramWithLabels", 1000L, 2000L, "value1");
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.keySet().size());
    }

    @Test
    public void testObserveHistogramWithLabelsThrowsExceptionWhenNotRegistered()
    {
        Exception exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            prometheusMetrics.observeHistogram("unregisteredHistogram", 1000L, 2000L, "value1");
        });
        Assertions.assertEquals("Please register the histogram first if you need labels", exception.getMessage());
    }
}
