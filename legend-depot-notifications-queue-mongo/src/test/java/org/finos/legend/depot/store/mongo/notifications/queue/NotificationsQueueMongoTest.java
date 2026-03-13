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

package org.finos.legend.depot.store.mongo.notifications.queue;

import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import com.mongodb.client.MongoClients;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.domain.notifications.Priority;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;

public class NotificationsQueueMongoTest
{
    private static MongoServer server;
    private static NotificationsQueueMongo queue;

    @BeforeAll
    public static void setup()
    {
        server = new MongoServer(new MemoryBackend());
        InetSocketAddress addr = server.bind();
        String connectionString = "mongodb://" + addr.getHostString() + ":" + addr.getPort();
        MongoDatabase database = MongoClients.create(connectionString).getDatabase("test");
        queue = new NotificationsQueueMongo(database);
    }

    @BeforeEach
    public void clearQueue()
    {
        queue.deleteAll();
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
    public void testPushAndGetAll()
    {
        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0", false, false, null, Priority.HIGH);
        queue.push(event);

        List<MetadataNotification> all = queue.getAll();
        Assertions.assertEquals(1, all.size());
    }

    @Test
    public void testPushReturnsEventId()
    {
        MetadataNotification event = new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0");
        String eventId = queue.push(event);
        Assertions.assertNotNull(eventId);
    }

    @Test
    public void testSize()
    {
        Assertions.assertEquals(0, queue.size());
        queue.push(new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0"));
        Assertions.assertEquals(1, queue.size());
    }

    @Test
    public void testGetFirstInQueue()
    {
        queue.push(new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0"));

        Optional<MetadataNotification> first = queue.getFirstInQueue();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertEquals("org.finos", first.get().getGroupId());
    }

    @Test
    public void testGetFirstInQueueRemovesItem()
    {
        queue.push(new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0"));

        queue.getFirstInQueue();
        Assertions.assertEquals(0, queue.size());
    }

    @Test
    public void testGetFirstInQueueEmptyReturnsEmpty()
    {
        Optional<MetadataNotification> first = queue.getFirstInQueue();
        Assertions.assertFalse(first.isPresent());
    }

    @Test
    public void testDeleteAll()
    {
        queue.push(new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0"));
        queue.push(new MetadataNotification("PROD-2", "org.finos", "legend-engine", "2.0.0"));

        long deleted = queue.deleteAll();
        Assertions.assertEquals(2, deleted);
        Assertions.assertEquals(0, queue.size());
    }

    @Test
    public void testPullAll()
    {
        queue.push(new MetadataNotification("PROD-1", "org.finos", "legend-depot", "1.0.0"));
        queue.push(new MetadataNotification("PROD-2", "org.finos", "legend-engine", "2.0.0"));

        List<MetadataNotification> pulled = queue.pullAll();
        Assertions.assertEquals(2, pulled.size());
        Assertions.assertEquals(0, queue.size());
    }
}
