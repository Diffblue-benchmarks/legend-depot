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
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public class TestMongoStoreAdministrationResource extends TestStoreMongo
{
    private MongoAdminStore adminStore;
    private StorageMetricsHandler storageMetrics;
    private MongoStoreAdministrationResource resource;

    private final AuthorisationProvider authProvider = (principalProvider, role) -> { };
    private final Provider<Principal> principalProvider = () -> () -> "testUser";

    @BeforeEach
    public void setUp()
    {
        adminStore = new MongoAdminStore(getMongoDatabase());
        storageMetrics = new StorageMetricsHandler(adminStore, new VoidPrometheusMetricsHandler());
        resource = new MongoStoreAdministrationResource(adminStore, authProvider, principalProvider, storageMetrics);
    }

    @Test
    public void getResourceNameReturnsExpectedValue()
    {
        Assertions.assertEquals(MongoStoreAdministrationResource.STORE_ADMINISTRATION_RESOURCE, resource.getResourceName());
    }

    @Test
    public void getIndexedReturnsEmptyMapWhenNoCollections()
    {
        Map<String, List<Document>> indexes = resource.getIndexed();
        Assertions.assertNotNull(indexes);
        Assertions.assertTrue(indexes.isEmpty());
    }

    @Test
    public void createIndexesIfAbsentReturnsEmptyListWhenNoIndexesRegistered()
    {
        List<String> result = resource.createIndexesIfAbsent();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void getCollectionsReturnsEmptyListWhenNoCollections()
    {
        List<String> collections = resource.getCollections();
        Assertions.assertNotNull(collections);
        Assertions.assertTrue(collections.isEmpty());
    }

    @Test
    public void getCollectionsReturnsCollectionsAfterInsert()
    {
        getMongoDatabase().getCollection("test-collection").insertOne(new Document("key", "value"));
        List<String> collections = resource.getCollections();
        Assertions.assertNotNull(collections);
        Assertions.assertTrue(collections.contains("test-collection"));
    }

    @Test
    public void getIndexedReturnsIndexesAfterInsert()
    {
        getMongoDatabase().getCollection("col1").insertOne(new Document("key", "value"));
        Map<String, List<Document>> indexes = resource.getIndexed();
        Assertions.assertNotNull(indexes);
        Assertions.assertFalse(indexes.isEmpty());
    }

    @Test
    public void deleteCollectionsRemovesCollection()
    {
        getMongoDatabase().getCollection("to-delete").insertOne(new Document("key", "value"));
        Assertions.assertTrue(adminStore.getAllCollections().contains("to-delete"));
        Response response = resource.deleteCollections("to-delete");
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        Assertions.assertFalse(adminStore.getAllCollections().contains("to-delete"));
    }

    @Test
    public void deleteCollectionsReturnsNoContent()
    {
        getMongoDatabase().getCollection("test-col").insertOne(new Document("key", "value"));
        Response response = resource.deleteCollections("test-col");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void getCollectionStatsReturnsNonNull()
    {
        Object stats = resource.getCollectionStats();
        Assertions.assertNotNull(stats);
    }

    @Test
    public void runPipelineReturnsResponse() throws Exception
    {
        getMongoDatabase().getCollection("test-col").insertOne(new Document("field1", "value1"));
        Response response = resource.runPipeline("test-col", "[{\"$match\": {\"field1\": \"value1\"}}]");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
