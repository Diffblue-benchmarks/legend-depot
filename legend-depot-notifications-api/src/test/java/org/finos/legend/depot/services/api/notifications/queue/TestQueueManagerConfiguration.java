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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestQueueManagerConfiguration
{
    @Test
    void testDefaultValues()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();

        assertEquals(20000L, config.getQueueInterval());
        assertEquals(60000L, config.getQueueDelay());
        assertEquals(1L, config.getNumberOfQueueWorkers());
    }

    @Test
    void testSetQueueInterval()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();
        config.setQueueInterval(5000L);

        assertEquals(5000L, config.getQueueInterval());
    }

    @Test
    void testSetQueueDelay()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();
        config.setQueueDelay(30000L);

        assertEquals(30000L, config.getQueueDelay());
    }

    @Test
    void testSetNumberOfQueueWorkers()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();
        config.setNumberOfQueueWorkers(4L);

        assertEquals(4L, config.getNumberOfQueueWorkers());
    }
}
