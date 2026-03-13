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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MongoNonTracingConnectionFactoryTest
{
    private MongoServer server;
    private String mongoUrl;

    @BeforeEach
    public void setUp()
    {
        server = new MongoServer(new MemoryBackend());
        java.net.InetSocketAddress serverAddress = server.bind();
        mongoUrl = "mongodb://" + serverAddress.getHostString() + ":" + serverAddress.getPort();
    }

    @AfterEach
    public void tearDown()
    {
        if (server != null)
        {
            server.shutdown();
        }
    }

    @Test
    public void canCreateNonTracingConnectionFactory()
    {
        MongoConfiguration config = new MongoConfiguration("test-db", mongoUrl, false);
        MongoNonTracingConnectionFactory factory = new MongoNonTracingConnectionFactory("test-app", config);

        assertNotNull(factory);
        assertNotNull(factory.getClient());
        assertNotNull(factory.getDatabase());
        assertEquals("test-app", factory.getApplicationName());
        assertEquals(mongoUrl, factory.getMongoURI());
    }

    @Test
    public void canGetDatabaseFromFactory()
    {
        MongoConfiguration config = new MongoConfiguration("test-db", mongoUrl, false);
        MongoNonTracingConnectionFactory factory = new MongoNonTracingConnectionFactory("test-app", config);

        MongoDatabase database = factory.getDatabase();
        assertNotNull(database);
        assertEquals("test-db", database.getName());
    }
}
