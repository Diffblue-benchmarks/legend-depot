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
    public void canRegisterGaugeAndSetValue()
    {
        prometheusMetrics.registerGauge("gauge1", "help");
        Assertions.assertEquals(1, prometheusMetrics.allGauges.size());

        prometheusMetrics.setGauge("gauge1", 42.0);
        Assertions.assertEquals(42.0D, prometheusMetrics.allGauges.get("test_gauge1").get(), 0.0D);
    }

    @Test
    public void canRegisterGaugeWithLabels()
    {
        prometheusMetrics.registerGauge("gauge2", "help", java.util.Arrays.asList("env", "region"));
        Assertions.assertEquals(1, prometheusMetrics.allGauges.size());
        Assertions.assertNotNull(prometheusMetrics.allGauges.get("test_gauge2"));
    }

    @Test
    public void canSetGaugeWithLabels()
    {
        prometheusMetrics.registerGauge("gauge3", "help", java.util.Arrays.asList("env"));
        prometheusMetrics.setGauge("gauge3", 10.0, java.util.Arrays.asList("prod"));
        Assertions.assertNotNull(prometheusMetrics.allGauges.get("test_gauge3"));
    }

    @Test
    public void setGaugeWithLabelsThrowsIfNotRegistered()
    {
        Assertions.assertThrows(UnsupportedOperationException.class, () ->
        {
            prometheusMetrics.setGauge("unregistered_gauge", 1.0, java.util.Arrays.asList("val1"));
        });
    }

    @Test
    public void canSetGaugeWithoutPriorRegistration()
    {
        prometheusMetrics.setGauge("gauge4", 99.0);
        Assertions.assertEquals(1, prometheusMetrics.allGauges.size());
        Assertions.assertEquals(99.0D, prometheusMetrics.allGauges.get("test_gauge4").get(), 0.0D);
    }

    @Test
    public void canIncreaseGauge()
    {
        prometheusMetrics.registerGauge("gauge5", "help");
        prometheusMetrics.increaseGauge("gauge5", 5);
        Assertions.assertEquals(5.0D, prometheusMetrics.allGauges.get("test_gauge5").get(), 0.0D);

        prometheusMetrics.increaseGauge("gauge5", 3);
        Assertions.assertEquals(8.0D, prometheusMetrics.allGauges.get("test_gauge5").get(), 0.0D);
    }

    @Test
    public void canRegisterHistogramAndObserve()
    {
        prometheusMetrics.registerHistogram("hist1", "help");
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.size());
        Assertions.assertNotNull(prometheusMetrics.allHistograms.get("test_hist1"));
    }

    @Test
    public void canRegisterHistogramWithLabels()
    {
        prometheusMetrics.registerHistogram("hist2", "help", java.util.Arrays.asList("method"));
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.size());
        Assertions.assertNotNull(prometheusMetrics.allHistograms.get("test_hist2"));
    }

    @Test
    public void canObserveHistogramWithStartEnd()
    {
        prometheusMetrics.observeHistogram("hist3", 100L, 200L);
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.size());
        Assertions.assertNotNull(prometheusMetrics.allHistograms.get("test_hist3"));
    }

    @Test
    public void canObserveHistogramWithAmount()
    {
        prometheusMetrics.observeHistogram("hist4", 5.5);
        Assertions.assertEquals(1, prometheusMetrics.allHistograms.size());
        Assertions.assertNotNull(prometheusMetrics.allHistograms.get("test_hist4"));
    }

    @Test
    public void canObserveHistogramWithLabels()
    {
        prometheusMetrics.registerHistogram("hist5", "help", java.util.Arrays.asList("method"));
        prometheusMetrics.observeHistogram("hist5", 100L, 200L, "GET");
        Assertions.assertNotNull(prometheusMetrics.allHistograms.get("test_hist5"));
    }

    @Test
    public void observeHistogramWithLabelsThrowsIfNotRegistered()
    {
        Assertions.assertThrows(UnsupportedOperationException.class, () ->
        {
            prometheusMetrics.observeHistogram("unregistered_hist", 100L, 200L, "GET");
        });
    }

    @Test
    public void canObserveSummary()
    {
        prometheusMetrics.observe("summary1", 1000L, 2000L);
        Assertions.assertEquals(1, prometheusMetrics.allSummaries.size());
        Assertions.assertNotNull(prometheusMetrics.allSummaries.get("test_summary1"));
    }
}
