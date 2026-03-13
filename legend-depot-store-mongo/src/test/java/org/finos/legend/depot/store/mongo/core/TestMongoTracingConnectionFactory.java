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
import io.opentracing.Tracer;
import io.opentracing.contrib.mongo.TracingMongoClient;
import io.opentracing.noop.NoopTracerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMongoTracingConnectionFactory
{
    @Test
    public void canCreateFactoryWithTracingClient()
    {
        Tracer tracer = NoopTracerFactory.create();
        MongoConfiguration config = new MongoConfiguration("test-db", "mongodb://localhost:27017", true);

        MongoTracingConnectionFactory factory = new MongoTracingConnectionFactory("test-app", config, tracer);

        assertNotNull(factory.getClient());
        assertTrue(factory.getClient() instanceof TracingMongoClient);
    }

    @Test
    public void canCreateFactoryWithPlainClientForSrvUri()
    {
        Tracer tracer = NoopTracerFactory.create();
        MongoConfiguration config = new MongoConfiguration("test-db", "mongodb+srv://cluster.example.com/test", true);

        MongoTracingConnectionFactory factory = new MongoTracingConnectionFactory("test-app", config, tracer);

        assertNotNull(factory.getClient());
        assertTrue(factory.getClient() instanceof MongoClient);
        assertTrue(!(factory.getClient() instanceof TracingMongoClient));
    }
}
