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
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.StoreException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.util.List;

public class ProjectsMongoTest
{
    private static MongoServer server;
    private static ProjectsMongo store;

    @BeforeAll
    public static void setup()
    {
        server = new MongoServer(new MemoryBackend());
        InetSocketAddress addr = server.bind();
        String connectionString = "mongodb://" + addr.getHostString() + ":" + addr.getPort();
        MongoDatabase database = MongoClients.create(connectionString).getDatabase("test");
        store = new ProjectsMongo(database);
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
    public void testCreateAndFindProject()
    {
        StoreProjectData project = new StoreProjectData("PROD-100", "org.finos", "legend-mongo-test");
        store.createOrUpdate(project);

        Assertions.assertTrue(store.find("org.finos", "legend-mongo-test").isPresent());
    }

    @Test
    public void testCreateInvalidProjectThrows()
    {
        StoreProjectData invalid = new StoreProjectData("INVALID", "org.finos", "legend-depot");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> store.createOrUpdate(invalid));
    }

    @Test
    public void testDuplicateCoordinatesThrows()
    {
        StoreProjectData project1 = new StoreProjectData("PROD-200", "org.finos", "legend-dup-test");
        store.createOrUpdate(project1);

        StoreProjectData project2 = new StoreProjectData("PROD-201", "org.finos", "legend-dup-test");
        Assertions.assertThrows(StoreException.class,
                () -> store.createOrUpdate(project2));
    }

    @Test
    public void testFindByProjectId()
    {
        StoreProjectData project = new StoreProjectData("PROD-300", "org.finos", "legend-find-by-id");
        store.createOrUpdate(project);

        List<StoreProjectData> found = store.findByProjectId("PROD-300");
        Assertions.assertFalse(found.isEmpty());
        Assertions.assertEquals("PROD-300", found.get(0).getProjectId());
    }

    @Test
    public void testDeleteProject()
    {
        StoreProjectData project = new StoreProjectData("PROD-400", "org.finos", "legend-delete-proj");
        store.createOrUpdate(project);

        long deleted = store.delete("org.finos", "legend-delete-proj");
        Assertions.assertTrue(deleted > 0);
        Assertions.assertFalse(store.find("org.finos", "legend-delete-proj").isPresent());
    }

    @Test
    public void testFindNonExistentProject()
    {
        Assertions.assertFalse(store.find("org.finos", "non-existent-project").isPresent());
    }

    @Test
    public void testUpdateExistingProject()
    {
        StoreProjectData project = new StoreProjectData("PROD-500", "org.finos", "legend-update-test");
        store.createOrUpdate(project);

        StoreProjectData updated = new StoreProjectData("PROD-500", "org.finos", "legend-update-test", "master", "1.0.0");
        store.createOrUpdate(updated);

        StoreProjectData found = store.find("org.finos", "legend-update-test").get();
        Assertions.assertEquals("1.0.0", found.getLatestVersion());
    }
}
