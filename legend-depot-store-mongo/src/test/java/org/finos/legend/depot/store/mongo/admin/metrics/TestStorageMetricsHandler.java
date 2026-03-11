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
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestStorageMetricsHandler extends TestStoreMongo
{
    private MongoAdminStore adminStore;
    private VoidPrometheusMetricsHandler metricsHandler;
    private StorageMetricsHandler storageMetricsHandler;

    @BeforeEach
    public void setUp()
    {
        adminStore = new MongoAdminStore(getMongoDatabase());
        metricsHandler = new VoidPrometheusMetricsHandler();
        storageMetricsHandler = new StorageMetricsHandler(adminStore, metricsHandler);
    }

    @Test
    public void canCreateStorageMetricsHandler()
    {
        assertNotNull(storageMetricsHandler);
    }

    @Test
    public void canInitMetrics()
    {
        assertDoesNotThrow(() -> storageMetricsHandler.init());
    }

    @Test
    public void canReportMetricsOnEmptyDatabase()
    {
        storageMetricsHandler.init();
        Object result = storageMetricsHandler.reportMetrics();
        assertNotNull(result);
    }

    @Test
    public void canReportMetricsWithCollections()
    {
        storageMetricsHandler.init();
        getMongoDatabase().getCollection("testCollection").insertOne(new Document("key", "value"));
        Object result = storageMetricsHandler.reportMetrics();
        assertNotNull(result);
    }

    @Test
    public void canReportMetricsWithMultipleCollections()
    {
        storageMetricsHandler.init();
        getMongoDatabase().getCollection("collection1").insertOne(new Document("key1", "value1"));
        getMongoDatabase().getCollection("collection2").insertOne(new Document("key2", "value2"));
        getMongoDatabase().getCollection("collection3").insertOne(new Document("key3", "value3"));
        Object result = storageMetricsHandler.reportMetrics();
        assertNotNull(result);
    }
}
