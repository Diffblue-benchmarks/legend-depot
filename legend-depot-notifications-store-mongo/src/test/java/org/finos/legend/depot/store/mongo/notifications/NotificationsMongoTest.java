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

package org.finos.legend.depot.store.mongo.notifications;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.domain.notifications.Priority;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotificationsMongoTest extends TestStoreMongo
{
    private NotificationsMongo notificationsMongo;

    @Test
    public void canCreateNotificationsMongo()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);
        assertNotNull(notificationsMongo);
    }

    @Test
    public void canGetCollection()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);
        MongoCollection collection = notificationsMongo.getCollection();
        assertNotNull(collection);
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = NotificationsMongo.buildIndexes();
        assertNotNull(indexes);
        assertEquals(5, indexes.size());
    }

    @Test
    public void canGetKeyFilter()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);
        MetadataNotification notification = new MetadataNotification("project1", "group1", "artifact1", "1.0.0");
        Bson filter = notificationsMongo.getKeyFilter(notification);
        assertNotNull(filter);
    }

    @Test
    public void canGetAll()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);
        MetadataNotification notification = new MetadataNotification("project1", "group1", "artifact1", "1.0.0");
        notification.setCreated(new Date());
        notification.setUpdated(new Date());
        notificationsMongo.createOrUpdate(notification);

        List<MetadataNotification> notifications = notificationsMongo.getAll();
        assertNotNull(notifications);
        assertFalse(notifications.isEmpty());
    }

    @Test
    public void canValidateNewData()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);
        MetadataNotification notification = new MetadataNotification("project1", "group1", "artifact1", "1.0.0");
        notificationsMongo.validateNewData(notification);
    }

    @Test
    public void canGetByEventId()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);
        MetadataNotification notification = new MetadataNotification("project1", "group1", "artifact1", "1.0.0");
        notification.setEventId("event1");
        notification.setCreated(new Date());
        notification.setUpdated(new Date());

        Document doc = new Document();
        doc.put("eventId", "event1");
        doc.put("projectId", "project1");
        doc.put("groupId", "group1");
        doc.put("artifactId", "artifact1");
        doc.put("versionId", "1.0.0");
        doc.put("created", notification.getCreated());
        doc.put("updated", notification.getUpdated());
        mongoProvider.getCollection("notifications").insertOne(doc);

        Optional<MetadataNotification> result = notificationsMongo.get("event1");
        assertTrue(result.isPresent());
        assertEquals("event1", result.get().getEventId());
    }

    @Test
    public void canGetByEventIdNotFound()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);
        Optional<MetadataNotification> result = notificationsMongo.get("nonexistent");
        assertFalse(result.isPresent());
    }

    @Test
    public void canFindWithAllParameters()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);

        Date now = new Date();
        Document doc = new Document();
        doc.put("eventId", "event1");
        doc.put("parentEventId", "parent1");
        doc.put("projectId", "project1");
        doc.put("groupId", "group1");
        doc.put("artifactId", "artifact1");
        doc.put("versionId", "1.0.0");
        doc.put("created", now.getTime());
        doc.put("updated", now.getTime());
        doc.put("status", "SUCCESS");
        mongoProvider.getCollection("notifications").insertOne(doc);

        List<MetadataNotification> results = notificationsMongo.find(
            "group1",
            "artifact1",
            "1.0.0",
            "event1",
            "parent1",
            true,
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now().plusDays(1)
        );

        assertNotNull(results);
        assertTrue(results.size() > 0);
    }

    @Test
    public void canFindWithPartialParameters()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);

        Date now = new Date();
        Document doc = new Document();
        doc.put("eventId", "event1");
        doc.put("projectId", "project1");
        doc.put("groupId", "group1");
        doc.put("artifactId", "artifact1");
        doc.put("versionId", "1.0.0");
        doc.put("created", now.getTime());
        doc.put("updated", now.getTime());
        mongoProvider.getCollection("notifications").insertOne(doc);

        List<MetadataNotification> results = notificationsMongo.find(
            "group1",
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );

        assertNotNull(results);
        assertTrue(results.size() > 0);
    }

    @Test
    public void canFindWithNullParameters()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);
        List<MetadataNotification> results = notificationsMongo.find(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );

        assertNotNull(results);
    }

    @Test
    public void canDeleteById()
    {
        notificationsMongo = new NotificationsMongo(mongoProvider);

        ObjectId objectId = new ObjectId();
        Document doc = new Document();
        doc.put("_id", objectId);
        doc.put("eventId", "event1");
        doc.put("projectId", "project1");
        doc.put("groupId", "group1");
        doc.put("artifactId", "artifact1");
        doc.put("versionId", "1.0.0");
        doc.put("created", new Date());
        doc.put("updated", new Date());
        mongoProvider.getCollection("notifications").insertOne(doc);

        notificationsMongo.delete(objectId.toString());

        long count = mongoProvider.getCollection("notifications").countDocuments();
        assertEquals(0, count);
    }
}
