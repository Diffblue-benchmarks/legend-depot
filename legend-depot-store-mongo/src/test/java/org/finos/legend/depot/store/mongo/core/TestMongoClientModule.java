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

package org.finos.legend.depot.store.mongo.core;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.finos.legend.depot.store.StorageConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestMongoClientModule
{
    private final MongoClientModule module = new MongoClientModule();
    private MongoServer server = new MongoServer(new MemoryBackend());
    private MongoClient mongoClient = new MongoClient(new ServerAddress(server.bind()));

    @AfterEach
    public void tearDown()
    {
        mongoClient.close();
        server.shutdown();
    }

    @Test
    public void getMongoConfigurationReturnsConfigWhenPresent()
    {
        MongoConfiguration mongoConfig = new MongoConfiguration("test-db", "mongodb://localhost:27017", false);
        List<StorageConfiguration> configurations = Collections.singletonList(mongoConfig);

        MongoConfiguration result = module.getMongoConfiguration(configurations);

        assertEquals(mongoConfig, result);
    }

    @Test
    public void getMongoConfigurationThrowsWhenNoMongoConfig()
    {
        List<StorageConfiguration> configurations = Collections.emptyList();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> module.getMongoConfiguration(configurations));
        assertEquals("mongo configuration not provided", exception.getMessage());
    }

    @Test
    public void getMongoConfigurationFiltersNullEntries()
    {
        List<StorageConfiguration> configurations = new ArrayList<>();
        configurations.add(null);
        MongoConfiguration mongoConfig = new MongoConfiguration("test-db", "mongodb://localhost:27017", false);
        configurations.add(mongoConfig);

        MongoConfiguration result = module.getMongoConfiguration(configurations);

        assertEquals(mongoConfig, result);
    }

    @Test
    public void getMongoConfigurationReturnsFirstWhenMultiple()
    {
        MongoConfiguration first = new MongoConfiguration("first-db", "mongodb://localhost:27017", false);
        MongoConfiguration second = new MongoConfiguration("second-db", "mongodb://localhost:27018", true);
        List<StorageConfiguration> configurations = Arrays.asList(first, second);

        MongoConfiguration result = module.getMongoConfiguration(configurations);

        assertEquals(first, result);
    }

    @Test
    public void getMongoDatabaseDelegatesToConnectionFactory()
    {
        MongoDatabase database = mongoClient.getDatabase("test-db");
        ConnectionFactory factory = new ConnectionFactory()
        {
            @Override
            public MongoDatabase getDatabase()
            {
                return database;
            }

            @Override
            public MongoClient getClient()
            {
                return mongoClient;
            }
        };

        MongoDatabase result = module.getMongoDatabase(factory);

        assertNotNull(result);
        assertEquals("test-db", result.getName());
    }

    @Test
    public void getMongoClientDelegatesToConnectionFactory()
    {
        ConnectionFactory factory = new ConnectionFactory()
        {
            @Override
            public MongoDatabase getDatabase()
            {
                return mongoClient.getDatabase("test-db");
            }

            @Override
            public MongoClient getClient()
            {
                return mongoClient;
            }
        };

        MongoClient result = module.getMongoClient(factory);

        assertNotNull(result);
        assertEquals(mongoClient, result);
    }
}
