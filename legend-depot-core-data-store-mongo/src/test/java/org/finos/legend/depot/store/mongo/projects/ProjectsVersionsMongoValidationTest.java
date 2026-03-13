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

package org.finos.legend.depot.store.mongo.projects;

import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import com.mongodb.client.MongoClients;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

public class ProjectsVersionsMongoValidationTest
{
    private static MongoServer server;
    private static MongoDatabase database;
    private static ProjectsVersionsMongo store;

    @BeforeAll
    public static void setup()
    {
        server = new MongoServer(new MemoryBackend());
        InetSocketAddress addr = server.bind();
        String connectionString = "mongodb://" + addr.getHostString() + ":" + addr.getPort();
        database = MongoClients.create(connectionString).getDatabase("test");
        store = new ProjectsVersionsMongo(database);
    }

    @AfterAll
    public static void teardown()
    {
        if (server != null)
        {
            server.shutdown();
        }
    }

    @Test
    public void testFindWithNullVersionIdThrows()
    {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> store.find("org.finos", "legend-depot", null));
    }

    @Test
    public void testFindWithEmptyVersionIdThrows()
    {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> store.find("org.finos", "legend-depot", ""));
    }

    @Test
    public void testCreateOrUpdateWithInvalidGroupIdThrows()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("123invalid", "legend-depot", "1.0.0");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> store.createOrUpdate(data));
    }

    @Test
    public void testCreateOrUpdateWithInvalidArtifactIdThrows()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "InvalidArtifact", "1.0.0");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> store.createOrUpdate(data));
    }

    @Test
    public void testCreateOrUpdateWithInvalidVersionIdThrows()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "invalid");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> store.createOrUpdate(data));
    }

    @Test
    public void testCreateAndRetrieveVersion()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        store.createOrUpdate(data);

        Assertions.assertTrue(store.find("org.finos", "legend-depot", "1.0.0").isPresent());
    }

    @Test
    public void testFindByGroupAndArtifact()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-test", "2.0.0");
        store.createOrUpdate(data);

        Assertions.assertFalse(store.find("org.finos", "legend-test").isEmpty());
    }

    @Test
    public void testDelete()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-delete", "3.0.0");
        store.createOrUpdate(data);

        long deleted = store.delete("org.finos", "legend-delete", "3.0.0");
        Assertions.assertTrue(deleted > 0);
        Assertions.assertFalse(store.find("org.finos", "legend-delete", "3.0.0").isPresent());
    }

    @Test
    public void testGetVersionCount()
    {
        StoreProjectVersionData data1 = new StoreProjectVersionData("org.finos", "legend-count", "1.0.0");
        StoreProjectVersionData data2 = new StoreProjectVersionData("org.finos", "legend-count", "2.0.0");
        store.createOrUpdate(data1);
        store.createOrUpdate(data2);

        Assertions.assertEquals(2, store.getVersionCount("org.finos", "legend-count"));
    }
}
