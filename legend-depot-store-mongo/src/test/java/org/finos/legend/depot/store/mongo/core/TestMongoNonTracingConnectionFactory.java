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

import com.mongodb.ServerAddress;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestMongoNonTracingConnectionFactory
{
    private MongoServer server = new MongoServer(new MemoryBackend());
    private InetSocketAddress serverAddress = server.bind();

    @AfterEach
    public void tearDown()
    {
        server.shutdown();
    }

    @Test
    public void canCreateNonTracingConnectionFactory()
    {
        String url = "mongodb://" + serverAddress.getHostName() + ":" + serverAddress.getPort();
        MongoConfiguration config = new MongoConfiguration("test-db", url, false);

        MongoNonTracingConnectionFactory factory = new MongoNonTracingConnectionFactory("test-app", config);

        assertNotNull(factory.getClient());
        assertNotNull(factory.getDatabase());
    }

    @Test
    public void throwsExceptionForNullConfiguration()
    {
        assertThrows(IllegalArgumentException.class, () -> new MongoNonTracingConnectionFactory("test-app", null));
    }
}
