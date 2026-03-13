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
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAbstractMongoConnectionFactory
{
    private MongoServer server;
    private TestMongoConnectionFactory factory;

    @AfterEach
    public void tearDown()
    {
        if (factory != null && factory.getClient() != null)
        {
            factory.getClient().close();
        }
        if (server != null)
        {
            server.shutdown();
        }
    }

    @Test
    public void canCreateFactoryWithValidConfiguration()
    {
        server = new MongoServer(new MemoryBackend());
        ServerAddress address = new ServerAddress(server.bind());
        String mongoUrl = "mongodb://" + address.getHost() + ":" + address.getPort();

        MongoConfiguration config = new MongoConfiguration("test-db", mongoUrl, false);
        factory = new TestMongoConnectionFactory("testApp", config);

        assertNotNull(factory);
        assertEquals("testApp", factory.getApplicationName());
        assertEquals(mongoUrl, factory.getMongoURI());
    }

    @Test
    public void canGetDatabase()
    {
        server = new MongoServer(new MemoryBackend());
        ServerAddress address = new ServerAddress(server.bind());
        String mongoUrl = "mongodb://" + address.getHost() + ":" + address.getPort();

        MongoConfiguration config = new MongoConfiguration("test-db", mongoUrl, false);
        factory = new TestMongoConnectionFactory("testApp", config);

        MongoDatabase database = factory.getDatabase();
        assertNotNull(database);
        assertEquals("test-db", database.getName());
    }

    @Test
    public void canGetClient()
    {
        server = new MongoServer(new MemoryBackend());
        ServerAddress address = new ServerAddress(server.bind());
        String mongoUrl = "mongodb://" + address.getHost() + ":" + address.getPort();

        MongoConfiguration config = new MongoConfiguration("test-db", mongoUrl, false);
        factory = new TestMongoConnectionFactory("testApp", config);

        MongoClient client = factory.getClient();
        assertNotNull(client);
    }

    @Test
    public void canBuildMongoURI()
    {
        server = new MongoServer(new MemoryBackend());
        ServerAddress address = new ServerAddress(server.bind());
        String mongoUrl = "mongodb://" + address.getHost() + ":" + address.getPort();

        MongoConfiguration config = new MongoConfiguration("test-db", mongoUrl, false);
        factory = new TestMongoConnectionFactory("testApp", config);

        MongoClientURI uri = factory.buildMongoURI();
        assertNotNull(uri);
        assertEquals("testApp", uri.getOptions().getApplicationName());
    }

    @Test
    public void throwsExceptionForNullConfiguration()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            new TestMongoConnectionFactory("testApp", null);
        });
        assertEquals("Invalid mongo configuration provided", exception.getMessage());
    }

    @Test
    public void throwsExceptionForEmptyDatabase()
    {
        MongoConfiguration config = new MongoConfiguration("", "mongodb://localhost:27017", false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            new TestMongoConnectionFactory("testApp", config);
        });
        assertEquals("Invalid mongo configuration provided", exception.getMessage());
    }

    @Test
    public void throwsExceptionForNullDatabase()
    {
        MongoConfiguration config = new MongoConfiguration(null, "mongodb://localhost:27017", false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            new TestMongoConnectionFactory("testApp", config);
        });
        assertEquals("Invalid mongo configuration provided", exception.getMessage());
    }

    @Test
    public void throwsExceptionForEmptyUrl()
    {
        MongoConfiguration config = new MongoConfiguration("test-db", "", false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            new TestMongoConnectionFactory("testApp", config);
        });
        assertEquals("Invalid mongo configuration provided", exception.getMessage());
    }

    @Test
    public void throwsExceptionForNullUrl()
    {
        MongoConfiguration config = new MongoConfiguration("test-db", null, false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            new TestMongoConnectionFactory("testApp", config);
        });
        assertEquals("Invalid mongo configuration provided", exception.getMessage());
    }

    private static class TestMongoConnectionFactory extends AbstractMongoConnectionFactory
    {
        public TestMongoConnectionFactory(String applicationName, MongoConfiguration mongoConfiguration)
        {
            super(applicationName, mongoConfiguration);
            if (mongoConfiguration != null && mongoConfiguration.url != null && !mongoConfiguration.url.isEmpty())
            {
                client = new MongoClient(buildMongoURI());
            }
        }
    }
}
