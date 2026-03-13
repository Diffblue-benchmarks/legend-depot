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

package org.finos.legend.depot.store.mongo.resources;

import org.bson.Document;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.tracing.resources.TracingResource;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoStoreAdministrationResourceTest extends TestStoreMongo
{
    private MongoStoreAdministrationResource resource;
    private MongoAdminStore adminStore;

    @BeforeEach
    public void setUp()
    {
        AuthorisationProvider authProvider = new AuthorisationProvider()
        {
            @Override
            public void authorise(Provider<Principal> principalProvider, String resourceName)
            {
            }
        };
        Provider<Principal> principalProvider = () -> null;
        PrometheusMetricsHandler metricsHandler = new PrometheusMetricsHandler()
        {
            @Override
            public void registerSummary(String summaryName, String helpMessage) {}
            @Override
            public void registerHistogram(String name, String helpMessage) {}
            @Override
            public void registerHistogram(String name, String help, List<String> labelNames) {}
            @Override
            public void observe(String summaryName, long start, long end) {}
            @Override
            public void observeHistogram(String name, long start, long end, String... labelValues) {}
            @Override
            public void observeHistogram(String name, long start, long end) {}
            @Override
            public void observeHistogram(String name, double amount) {}
            @Override
            public void registerCounter(String counter, String helpMessage) {}
            @Override
            public void incrementCount(String counter) {}
            @Override
            public void incrementErrorCount(String counter) {}
            @Override
            public void setGauge(String name, double value) {}
            @Override
            public void setGauge(String name, double value, List<String> labelValues) {}
            @Override
            public void registerGauge(String name, String help) {}
            @Override
            public void registerGauge(String name, String help, List<String> labelNames) {}
            @Override
            public void increaseGauge(String name, int value) {}
            @Override
            public void registerResourceSummaries(Class<? extends TracingResource> baseResource) {}
        };

        adminStore = new MongoAdminStore(getMongoDatabase());
        StorageMetricsHandler storageMetricsHandler = new StorageMetricsHandler(adminStore, metricsHandler);
        resource = new MongoStoreAdministrationResource(adminStore, authProvider, principalProvider, storageMetricsHandler);
    }

    @Test
    public void canGetAllIndexes()
    {
        getMongoDatabase().getCollection("testCollection1").insertOne(new Document("test", "value1"));
        getMongoDatabase().getCollection("testCollection2").insertOne(new Document("test", "value2"));

        Map<String, List<Document>> indexes = resource.getIndexed();

        assertNotNull(indexes);
        assertTrue(indexes.containsKey("testCollection1"));
        assertTrue(indexes.containsKey("testCollection2"));
        assertNotNull(indexes.get("testCollection1"));
        assertNotNull(indexes.get("testCollection2"));
        assertTrue(indexes.get("testCollection1").size() > 0);
        assertTrue(indexes.get("testCollection2").size() > 0);
    }

    @Test
    public void canGetIndexesForEmptyDatabase()
    {
        Map<String, List<Document>> indexes = resource.getIndexed();

        assertNotNull(indexes);
        assertEquals(0, indexes.size());
    }

    @Test
    public void canGetIndexesWithMultipleCollections()
    {
        getMongoDatabase().getCollection("collection1").insertOne(new Document("field1", "value1"));
        getMongoDatabase().getCollection("collection2").insertOne(new Document("field2", "value2"));
        getMongoDatabase().getCollection("collection3").insertOne(new Document("field3", "value3"));

        Map<String, List<Document>> indexes = resource.getIndexed();

        assertNotNull(indexes);
        assertEquals(3, indexes.size());
        assertTrue(indexes.containsKey("collection1"));
        assertTrue(indexes.containsKey("collection2"));
        assertTrue(indexes.containsKey("collection3"));
    }
}
