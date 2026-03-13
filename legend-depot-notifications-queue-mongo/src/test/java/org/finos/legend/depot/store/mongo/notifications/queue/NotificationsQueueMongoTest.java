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

import com.mongodb.client.model.IndexModel;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.domain.notifications.Priority;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class NotificationsQueueMongoTest extends TestStoreMongo
{
    private NotificationsQueueMongo queueMongo;

    @BeforeEach
    public void setupData()
    {
        queueMongo = new NotificationsQueueMongo(mongoProvider);
    }

    @Test
    public void canCreateNotificationsQueueMongo()
    {
        NotificationsQueueMongo queue = new NotificationsQueueMongo(mongoProvider);
        Assertions.assertNotNull(queue);
        Assertions.assertNotNull(queue.getCollection());
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = NotificationsQueueMongo.buildIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertEquals(1, indexes.size());
    }

    @Test
    public void canGetCollection()
    {
        Assertions.assertNotNull(queueMongo.getCollection());
        Assertions.assertEquals(NotificationsQueueMongo.COLLECTION, queueMongo.getCollection().getNamespace().getCollectionName());
    }

    @Test
    public void canValidateNewData()
    {
        MetadataNotification notification = new MetadataNotification("project1", "group1", "artifact1", "1.0.0");
        queueMongo.validateNewData(notification);
    }

    @Test
    public void canGetSize()
    {
        Assertions.assertEquals(0, queueMongo.size());
        queueMongo.push(new MetadataNotification("project1", "group1", "artifact1", "1.0.0"));
        Assertions.assertEquals(1, queueMongo.size());
    }

    @Test
    public void canPushNotification()
    {
        MetadataNotification notification = new MetadataNotification("project1", "group1", "artifact1", "1.0.0");
        String eventId = queueMongo.push(notification);
        Assertions.assertNotNull(eventId);
        Assertions.assertEquals(1, queueMongo.size());
    }

    @Test
    public void canPushNotificationWithoutEventId()
    {
        MetadataNotification notification = new MetadataNotification("project1", "group1", "artifact1", "1.0.0");
        String eventId = queueMongo.push(notification);
        Assertions.assertNotNull(eventId);
        Optional<MetadataNotification> retrieved = queueMongo.get(eventId);
        Assertions.assertTrue(retrieved.isPresent());
        Assertions.assertEquals(eventId, retrieved.get().getEventId());
    }

    @Test
    public void canGetAll()
    {
        queueMongo.push(new MetadataNotification("project1", "group1", "artifact1", "1.0.0"));
        queueMongo.push(new MetadataNotification("project2", "group2", "artifact2", "2.0.0"));
        List<MetadataNotification> all = queueMongo.getAll();
        Assertions.assertEquals(2, all.size());
    }

    @Test
    public void canGetEmptyList()
    {
        List<MetadataNotification> all = queueMongo.getAll();
        Assertions.assertNotNull(all);
        Assertions.assertEquals(0, all.size());
    }

    @Test
    public void canGetFirstInQueue()
    {
        MetadataNotification notification1 = new MetadataNotification("project1", "group1", "artifact1", "1.0.0", false, false, null, Priority.HIGH);
        MetadataNotification notification2 = new MetadataNotification("project2", "group2", "artifact2", "2.0.0", false, false, null, Priority.LOW);
        queueMongo.push(notification1);
        queueMongo.push(notification2);

        Optional<MetadataNotification> first = queueMongo.getFirstInQueue();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertEquals(1, queueMongo.size());
    }

    @Test
    public void canGetFirstInQueueWhenEmpty()
    {
        Optional<MetadataNotification> first = queueMongo.getFirstInQueue();
        Assertions.assertFalse(first.isPresent());
    }

    @Test
    public void canGetNotificationById()
    {
        MetadataNotification notification = new MetadataNotification("project1", "group1", "artifact1", "1.0.0");
        String eventId = queueMongo.push(notification);
        Optional<MetadataNotification> retrieved = queueMongo.get(eventId);
        Assertions.assertTrue(retrieved.isPresent());
        Assertions.assertEquals("project1", retrieved.get().getProjectId());
    }

    @Test
    public void canPullAll()
    {
        queueMongo.push(new MetadataNotification("project1", "group1", "artifact1", "1.0.0"));
        queueMongo.push(new MetadataNotification("project2", "group2", "artifact2", "2.0.0"));
        Assertions.assertEquals(2, queueMongo.size());

        List<MetadataNotification> pulled = queueMongo.pullAll();
        Assertions.assertEquals(2, pulled.size());
        Assertions.assertEquals(0, queueMongo.size());
    }

    @Test
    public void canDeleteAll()
    {
        queueMongo.push(new MetadataNotification("project1", "group1", "artifact1", "1.0.0"));
        queueMongo.push(new MetadataNotification("project2", "group2", "artifact2", "2.0.0"));
        Assertions.assertEquals(2, queueMongo.size());

        long deletedCount = queueMongo.deleteAll();
        Assertions.assertEquals(2, deletedCount);
        Assertions.assertEquals(0, queueMongo.size());
    }

    @Test
    public void canDeleteAllWhenEmpty()
    {
        long deletedCount = queueMongo.deleteAll();
        Assertions.assertEquals(0, deletedCount);
    }
}
