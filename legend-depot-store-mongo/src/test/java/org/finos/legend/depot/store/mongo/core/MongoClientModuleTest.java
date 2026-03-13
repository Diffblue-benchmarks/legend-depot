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
import io.opentracing.Tracer;
import io.opentracing.noop.NoopTracerFactory;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.core.services.tracing.TracerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void canGetMongoDatabase()
    {
        ConnectionFactory connectionFactory = new TestConnectionFactory(mongoClient);

        MongoClientModule module = new MongoClientModule();
        MongoDatabase database = module.getMongoDatabase(connectionFactory);

        assertNotNull(database);
    }

    @Test
    public void canGetConnectionFactoryWithTracingEnabled()
    {
        String applicationName = "test-app";
        MongoConfiguration mongoConfiguration = new MongoConfiguration("test-db", "mongodb://localhost:27017", true);
        OpenTracingConfiguration openTracingConfiguration = new OpenTracingConfiguration();
        openTracingConfiguration.setEnabled(true);
        TracerFactory tracerFactory = TracerFactory.configure(null);

        MongoClientModule module = new MongoClientModule();
        ConnectionFactory connectionFactory = module.getConnectionFactory(applicationName, mongoConfiguration, openTracingConfiguration, tracerFactory);

        assertNotNull(connectionFactory);
        assertTrue(connectionFactory instanceof MongoTracingConnectionFactory);
    }

    @Test
    public void canGetConnectionFactoryWithTracingDisabledInOpenTracing()
    {
        String applicationName = "test-app";
        MongoConfiguration mongoConfiguration = new MongoConfiguration("test-db", "mongodb://localhost:27017", true);
        OpenTracingConfiguration openTracingConfiguration = new OpenTracingConfiguration();
        openTracingConfiguration.setEnabled(false);
        TracerFactory tracerFactory = TracerFactory.configure(null);

        MongoClientModule module = new MongoClientModule();
        ConnectionFactory connectionFactory = module.getConnectionFactory(applicationName, mongoConfiguration, openTracingConfiguration, tracerFactory);

        assertNotNull(connectionFactory);
        assertTrue(connectionFactory instanceof MongoNonTracingConnectionFactory);
    }

    @Test
    public void canGetConnectionFactoryWithTracingDisabledInMongoConfig()
    {
        String applicationName = "test-app";
        MongoConfiguration mongoConfiguration = new MongoConfiguration("test-db", "mongodb://localhost:27017", false);
        OpenTracingConfiguration openTracingConfiguration = new OpenTracingConfiguration();
        openTracingConfiguration.setEnabled(true);
        TracerFactory tracerFactory = TracerFactory.configure(null);

        MongoClientModule module = new MongoClientModule();
        ConnectionFactory connectionFactory = module.getConnectionFactory(applicationName, mongoConfiguration, openTracingConfiguration, tracerFactory);

        assertNotNull(connectionFactory);
        assertTrue(connectionFactory instanceof MongoNonTracingConnectionFactory);
    }

    @Test
    public void canGetConnectionFactoryWithBothTracingDisabled()
    {
        String applicationName = "test-app";
        MongoConfiguration mongoConfiguration = new MongoConfiguration("test-db", "mongodb://localhost:27017", false);
        OpenTracingConfiguration openTracingConfiguration = new OpenTracingConfiguration();
        openTracingConfiguration.setEnabled(false);
        TracerFactory tracerFactory = TracerFactory.configure(null);

        MongoClientModule module = new MongoClientModule();
        ConnectionFactory connectionFactory = module.getConnectionFactory(applicationName, mongoConfiguration, openTracingConfiguration, tracerFactory);

        assertNotNull(connectionFactory);
        assertTrue(connectionFactory instanceof MongoNonTracingConnectionFactory);
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
