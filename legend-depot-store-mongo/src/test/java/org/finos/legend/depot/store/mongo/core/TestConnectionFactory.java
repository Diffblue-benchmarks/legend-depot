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
import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestConnectionFactory
{
    private MongoServer server;
    private ConnectionFactory connectionFactory;
    private String mongoUri;

    @BeforeEach
    public void setUp()
    {
        server = new MongoServer(new MemoryBackend());
        java.net.InetSocketAddress serverAddress = server.bind();
        mongoUri = "mongodb://" + serverAddress.getHostString() + ":" + serverAddress.getPort() + "/test-db";

        MongoConfiguration config = new MongoConfiguration("test-db", mongoUri, false);
        connectionFactory = new MongoNonTracingConnectionFactory("test-app", config);
    }

    @AfterEach
    public void tearDown()
    {
        if (connectionFactory != null && connectionFactory.getClient() != null)
        {
            connectionFactory.getClient().close();
        }
        if (server != null)
        {
            server.shutdown();
        }
    }

    @Test
    public void canGetDatabase()
    {
        MongoDatabase database = connectionFactory.getDatabase();
        Assertions.assertNotNull(database);
        Assertions.assertEquals("test-db", database.getName());
    }

    @Test
    public void canGetClient()
    {
        MongoClient client = connectionFactory.getClient();
        Assertions.assertNotNull(client);
    }
}
