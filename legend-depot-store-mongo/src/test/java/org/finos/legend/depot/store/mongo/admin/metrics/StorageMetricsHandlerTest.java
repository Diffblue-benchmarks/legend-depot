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

package org.finos.legend.depot.store.mongo.admin.metrics;

import org.bson.Document;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.tracing.resources.TracingResource;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageMetricsHandlerTest extends TestStoreMongo
{
    private MongoAdminStore adminStore;
    private TestPrometheusMetricsHandler metricsHandler;
    private StorageMetricsHandler storageMetricsHandler;

    @BeforeEach
    public void setUp()
    {
        adminStore = new MongoAdminStore(getMongoDatabase());
        metricsHandler = new TestPrometheusMetricsHandler();
        storageMetricsHandler = new StorageMetricsHandler(adminStore, metricsHandler);
    }

    @Test
    public void canConstructStorageMetricsHandler()
    {
        StorageMetricsHandler handler = new StorageMetricsHandler(adminStore, metricsHandler);
        assertNotNull(handler);
    }

    @Test
    public void canInitializeMetrics()
    {
        storageMetricsHandler.init();

        assertTrue(metricsHandler.registeredGauges.containsKey("storage_data_size_kb"));
        assertTrue(metricsHandler.registeredGauges.containsKey("storage_storage_size_kb"));
        assertTrue(metricsHandler.registeredGauges.containsKey("storage_index_size_kb"));
        assertTrue(metricsHandler.registeredGauges.containsKey("storage_collectionSize"));
        assertTrue(metricsHandler.registeredGauges.containsKey("storage_objectCount"));
        assertTrue(metricsHandler.registeredGauges.containsKey("storage_indexCount"));
        assertTrue(metricsHandler.registeredGauges.containsKey("storage_indexSize"));
        assertTrue(metricsHandler.registeredGauges.containsKey("storage_storageSize"));
        assertTrue(metricsHandler.registeredGauges.containsKey("storage_avgSize"));
    }

    @Test
    public void canReportMetricsWithDbStats()
    {
        getMongoDatabase().getCollection("testCollection").insertOne(new Document("key", "value"));

        Object result = storageMetricsHandler.reportMetrics();

        assertNotNull(result);
        StorageMetricsHandler.StorageStats stats = (StorageMetricsHandler.StorageStats) result;
        assertNotNull(stats.dbStats);
        assertNotNull(stats.collectionStats);
        assertTrue(metricsHandler.gaugeValues.containsKey("storage_data_size_kb"));
        assertTrue(metricsHandler.gaugeValues.containsKey("storage_storage_size_kb"));
        assertTrue(metricsHandler.gaugeValues.containsKey("storage_index_size_kb"));
    }

    @Test
    public void canReportMetricsWithCollectionStats()
    {
        getMongoDatabase().getCollection("testCollection1").insertOne(new Document("field", "value1"));
        getMongoDatabase().getCollection("testCollection2").insertOne(new Document("field", "value2"));

        Object result = storageMetricsHandler.reportMetrics();

        assertNotNull(result);
        StorageMetricsHandler.StorageStats stats = (StorageMetricsHandler.StorageStats) result;
        assertNotNull(stats.collectionStats);
        assertFalse(stats.collectionStats.isEmpty());
    }

    @Test
    public void canHandleMultipleCollections()
    {
        getMongoDatabase().getCollection("collection1").insertOne(new Document("data", "test1"));
        getMongoDatabase().getCollection("collection2").insertOne(new Document("data", "test2"));
        getMongoDatabase().getCollection("collection3").insertOne(new Document("data", "test3"));

        Object result = storageMetricsHandler.reportMetrics();

        assertNotNull(result);
        StorageMetricsHandler.StorageStats stats = (StorageMetricsHandler.StorageStats) result;
        assertNotNull(stats.collectionStats);
    }

    @Test
    public void canReportMetricsWithEmptyDatabase()
    {
        Object result = storageMetricsHandler.reportMetrics();

        assertNotNull(result);
        StorageMetricsHandler.StorageStats stats = (StorageMetricsHandler.StorageStats) result;
        assertNotNull(stats.dbStats);
        assertNotNull(stats.collectionStats);
    }

    @Test
    public void canLogDbStatsMetrics()
    {
        getMongoDatabase().getCollection("testCollection").insertOne(new Document("test", "data"));

        storageMetricsHandler.reportMetrics();

        assertTrue(metricsHandler.gaugeValues.containsKey("storage_data_size_kb"));
        assertTrue(metricsHandler.gaugeValues.containsKey("storage_storage_size_kb"));
        assertTrue(metricsHandler.gaugeValues.containsKey("storage_index_size_kb"));
    }

    @Test
    public void canLogCollectionStatsMetrics()
    {
        getMongoDatabase().getCollection("testCollection").insertOne(new Document("data", "value"));

        storageMetricsHandler.reportMetrics();

        assertFalse(metricsHandler.gaugeValuesWithLabels.isEmpty());
    }

    private static class TestPrometheusMetricsHandler implements PrometheusMetricsHandler
    {
        public Map<String, String> registeredGauges = new HashMap<>();
        public Map<String, Double> gaugeValues = new HashMap<>();
        public List<String> gaugeValuesWithLabels = new ArrayList<>();

        @Override
        public void registerSummary(String summaryName, String helpMessage)
        {
        }

        @Override
        public void registerHistogram(String name, String helpMessage)
        {
        }

        @Override
        public void registerHistogram(String name, String help, List<String> labelNames)
        {
        }

        @Override
        public void observe(String summaryName, long start, long end)
        {
        }

        @Override
        public void observeHistogram(String name, long start, long end, String... labelValues)
        {
        }

        @Override
        public void observeHistogram(String name, long start, long end)
        {
        }

        @Override
        public void observeHistogram(String name, double amount)
        {
        }

        @Override
        public void registerCounter(String counter, String helpMessage)
        {
        }

        @Override
        public void incrementCount(String counter)
        {
        }

        @Override
        public void incrementErrorCount(String counter)
        {
        }

        @Override
        public void setGauge(String name, double value)
        {
            gaugeValues.put(name, value);
        }

        @Override
        public void setGauge(String name, double value, List<String> labelValues)
        {
            gaugeValuesWithLabels.add(name + ":" + labelValues);
        }

        @Override
        public void registerGauge(String name, String help)
        {
            registeredGauges.put(name, help);
        }

        @Override
        public void registerGauge(String name, String help, List<String> labelNames)
        {
            registeredGauges.put(name, help);
        }

        @Override
        public void increaseGauge(String name, int value)
        {
        }

        @Override
        public void registerResourceSummaries(Class<? extends TracingResource> baseResource)
        {
        }
    }
}
