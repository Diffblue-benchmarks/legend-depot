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
import static org.junit.jupiter.api.Assertions.assertSame;

public class MongoClientModuleTest
{
    private MongoServer server;
    private MongoClient mongoClient;

    @BeforeEach
    public void setUp()
    {
        server = new MongoServer(new MemoryBackend());
        mongoClient = new MongoClient(new ServerAddress(server.bind()));
    }

    @AfterEach
    public void tearDown()
    {
        if (mongoClient != null)
        {
            mongoClient.close();
        }
        if (server != null)
        {
            server.shutdown();
        }
    }

    @Test
    public void canGetMongoClient()
    {
        ConnectionFactory connectionFactory = new TestConnectionFactory(mongoClient);

        MongoClientModule module = new MongoClientModule();
        MongoClient actualClient = module.getMongoClient(connectionFactory);

        assertNotNull(actualClient);
        assertSame(mongoClient, actualClient);
    }

    private static class TestConnectionFactory implements ConnectionFactory
    {
        private final MongoClient client;

        public TestConnectionFactory(MongoClient client)
        {
            this.client = client;
        }

        @Override
        public MongoDatabase getDatabase()
        {
            return client.getDatabase("test-db");
        }

        @Override
        public MongoClient getClient()
        {
            return client;
        }
    }
}
