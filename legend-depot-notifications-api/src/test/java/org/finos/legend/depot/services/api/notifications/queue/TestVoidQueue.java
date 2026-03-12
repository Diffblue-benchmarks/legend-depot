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

public class TestVoidQueue
{
    private final VoidQueue queue = new VoidQueue();

    @Test
    public void testGetAllReturnsEmptyList()
    {
        Assertions.assertTrue(queue.getAll().isEmpty());
    }

    @Test
    public void testPullAllReturnsEmptyList()
    {
        Assertions.assertTrue(queue.pullAll().isEmpty());
    }

    @Test
    public void testGetFirstInQueueReturnsEmpty()
    {
        Assertions.assertFalse(queue.getFirstInQueue().isPresent());
    }

    @Test
    public void testGetByEventIdReturnsEmpty()
    {
        Assertions.assertFalse(queue.get("event-123").isPresent());
    }

    @Test
    public void testPushReturnsNull()
    {
        MetadataNotification notification = new MetadataNotification("PROD-1", "org.finos", "artifact", "1.0.0");
        Assertions.assertNull(queue.push(notification));
    }

    @Test
    public void testSizeReturnsZero()
    {
        Assertions.assertEquals(0, queue.size());
    }

    @Test
    public void testDeleteAllReturnsZero()
    {
        Assertions.assertEquals(0, queue.deleteAll());
    }
}
