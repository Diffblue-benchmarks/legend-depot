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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestQueueManagerConfiguration
{
    @Test
    public void testDefaultValues()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();
        Assertions.assertEquals(20 * 1000L, config.getQueueInterval());
        Assertions.assertEquals(60 * 1000L, config.getQueueDelay());
        Assertions.assertEquals(1, config.getNumberOfQueueWorkers());
    }

    @Test
    public void testSetQueueInterval()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();
        config.setQueueInterval(5000L);
        Assertions.assertEquals(5000L, config.getQueueInterval());
    }

    @Test
    public void testSetQueueDelay()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();
        config.setQueueDelay(30000L);
        Assertions.assertEquals(30000L, config.getQueueDelay());
    }

    @Test
    public void testSetNumberOfQueueWorkers()
    {
        QueueManagerConfiguration config = new QueueManagerConfiguration();
        config.setNumberOfQueueWorkers(4);
        Assertions.assertEquals(4, config.getNumberOfQueueWorkers());
    }
}
