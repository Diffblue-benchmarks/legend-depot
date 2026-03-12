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

package org.finos.legend.depot.store.model.admin.schedules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestScheduleInfo
{
    @Test
    public void testDefaultConstructor()
    {
        ScheduleInfo info = new ScheduleInfo();
        Assertions.assertNull(info.getName());
        Assertions.assertNull(info.getId());
        Assertions.assertFalse(info.isDisabled());
        Assertions.assertNull(info.getSingleInstance());
        Assertions.assertNull(info.getExternalTrigger());
        Assertions.assertNull(info.getFrequency());
    }

    @Test
    public void testNameConstructor()
    {
        ScheduleInfo info = new ScheduleInfo("test-schedule");
        Assertions.assertEquals("test-schedule", info.getName());
    }

    @Test
    public void testSettersAndGetters()
    {
        ScheduleInfo info = new ScheduleInfo();
        info.setId("id-1");
        info.setName("my-schedule");
        info.setDisabled(true);
        info.setSingleInstance(true);
        info.setExternalTrigger(false);
        info.setFrequency(60000L);

        Assertions.assertEquals("id-1", info.getId());
        Assertions.assertEquals("my-schedule", info.getName());
        Assertions.assertTrue(info.isDisabled());
        Assertions.assertTrue(info.getSingleInstance());
        Assertions.assertFalse(info.getExternalTrigger());
        Assertions.assertEquals(60000L, info.getFrequency());
    }
}
