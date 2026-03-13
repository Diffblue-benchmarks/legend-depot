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

public class ScheduleInfoTest
{
    @Test
    public void testConstructorWithName()
    {
        ScheduleInfo info = new ScheduleInfo("test-schedule");
        Assertions.assertEquals("test-schedule", info.getName());
        Assertions.assertFalse(info.isDisabled());
        Assertions.assertNull(info.getSingleInstance());
        Assertions.assertNull(info.getExternalTrigger());
        Assertions.assertNull(info.getFrequency());
    }

    @Test
    public void testDefaultConstructor()
    {
        ScheduleInfo info = new ScheduleInfo();
        Assertions.assertNull(info.getName());
        Assertions.assertNull(info.getId());
        Assertions.assertFalse(info.isDisabled());
    }

    @Test
    public void testSetDisabled()
    {
        ScheduleInfo info = new ScheduleInfo("test");
        info.setDisabled(true);
        Assertions.assertTrue(info.isDisabled());
    }

    @Test
    public void testSetSingleInstance()
    {
        ScheduleInfo info = new ScheduleInfo("test");
        info.setSingleInstance(true);
        Assertions.assertTrue(info.getSingleInstance());
    }

    @Test
    public void testSetExternalTrigger()
    {
        ScheduleInfo info = new ScheduleInfo("test");
        info.setExternalTrigger(true);
        Assertions.assertTrue(info.getExternalTrigger());
    }

    @Test
    public void testSetFrequency()
    {
        ScheduleInfo info = new ScheduleInfo("test");
        info.setFrequency(60000L);
        Assertions.assertEquals(60000L, info.getFrequency());
    }

    @Test
    public void testSetIdAndName()
    {
        ScheduleInfo info = new ScheduleInfo();
        info.setId("id-1");
        info.setName("schedule-name");
        Assertions.assertEquals("id-1", info.getId());
        Assertions.assertEquals("schedule-name", info.getName());
    }
}
