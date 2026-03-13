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

import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.store.mongo.core.BaseMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TestNotificationKeyFilter
{
    private static final String TEST_GROUP_ID = "org.example";
    private static final String TEST_ARTIFACT_ID = "test-artifact";
    private static final String TEST_VERSION_ID = "1.0.0";
    private static final String TEST_PROJECT_ID = "test-project";

    @Test
    public void testPrivateConstructor() throws Exception
    {
        Constructor<NotificationKeyFilter> constructor = NotificationKeyFilter.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        NotificationKeyFilter instance = constructor.newInstance();
        Assertions.assertNotNull(instance);
    }

    @Test
    public void canGetFilterWithEventId()
    {
        String eventId = new ObjectId().toString();
        MetadataNotification notification = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
        notification.setEventId(eventId);

        Bson filter = NotificationKeyFilter.getFilter(notification);

        Assertions.assertNotNull(filter);
        Bson expectedFilter = Filters.eq(BaseMongo.ID_FIELD, new ObjectId(eventId));
        Assertions.assertEquals(expectedFilter.toString(), filter.toString());
    }

    @Test
    public void canGetFilterWithoutEventId()
    {
        MetadataNotification notification = new MetadataNotification(TEST_PROJECT_ID, TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);

        Bson filter = NotificationKeyFilter.getFilter(notification);

        Assertions.assertNotNull(filter);
        Bson expectedFilter = Filters.and(
                Filters.and(Filters.eq(BaseMongo.GROUP_ID, TEST_GROUP_ID)),
                Filters.eq(BaseMongo.ARTIFACT_ID, TEST_ARTIFACT_ID),
                Filters.eq(BaseMongo.VERSION_ID, TEST_VERSION_ID)
        );
        Assertions.assertEquals(expectedFilter.toString(), filter.toString());
    }
}
