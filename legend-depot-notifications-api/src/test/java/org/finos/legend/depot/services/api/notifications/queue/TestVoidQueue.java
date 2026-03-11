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

package org.finos.legend.depot.services.api.notifications.queue;

import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class TestVoidQueue
{
    private final VoidQueue voidQueue = new VoidQueue();

    @Test
    public void canGetAllReturnsEmptyList()
    {
        List<MetadataNotification> result = voidQueue.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void canPullAllReturnsEmptyList()
    {
        List<MetadataNotification> result = voidQueue.pullAll();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void canGetFirstInQueueReturnsEmpty()
    {
        Optional<MetadataNotification> result = voidQueue.getFirstInQueue();
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void canGetByIdReturnsEmpty()
    {
        Optional<MetadataNotification> result = voidQueue.get("test-event-id");
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void canPushReturnsNull()
    {
        MetadataNotification notification = new MetadataNotification();
        String result = voidQueue.push(notification);
        Assertions.assertNull(result);
    }

    @Test
    public void canSizeReturnsZero()
    {
        long result = voidQueue.size();
        Assertions.assertEquals(0, result);
    }

    @Test
    public void canDeleteAllReturnsZero()
    {
        long result = voidQueue.deleteAll();
        Assertions.assertEquals(0, result);
    }
}
