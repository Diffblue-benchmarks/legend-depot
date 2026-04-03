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
    public void testDefaultConstructor()
    {
        ScheduleInfo info = new ScheduleInfo();

        Assertions.assertNull(info.getId());
        Assertions.assertNull(info.getName());
        Assertions.assertFalse(info.isDisabled());
        Assertions.assertNull(info.getSingleInstance());
        Assertions.assertNull(info.getExternalTrigger());
        Assertions.assertNull(info.getFrequency());
    }

    @Test
    public void testNameConstructor()
    {
        ScheduleInfo info = new ScheduleInfo("testSchedule");

        Assertions.assertEquals("testSchedule", info.getName());
        Assertions.assertNull(info.getId());
    }

    @Test
    public void testGetIdAndSetId()
    {
        ScheduleInfo info = new ScheduleInfo();

        info.setId("schedule-1");

        Assertions.assertEquals("schedule-1", info.getId());
    }

    @Test
    public void testGetNameAndSetName()
    {
        ScheduleInfo info = new ScheduleInfo();

        info.setName("mySchedule");

        Assertions.assertEquals("mySchedule", info.getName());
    }

    @Test
    public void testIsDisabledAndSetDisabled()
    {
        ScheduleInfo info = new ScheduleInfo();

        info.setDisabled(true);

        Assertions.assertTrue(info.isDisabled());
    }

    @Test
    public void testGetSingleInstanceAndSetSingleInstance()
    {
        ScheduleInfo info = new ScheduleInfo();

        info.setSingleInstance(Boolean.TRUE);

        Assertions.assertEquals(Boolean.TRUE, info.getSingleInstance());
    }

    @Test
    public void testGetExternalTriggerAndSetExternalTrigger()
    {
        ScheduleInfo info = new ScheduleInfo();

        info.setExternalTrigger(Boolean.FALSE);

        Assertions.assertEquals(Boolean.FALSE, info.getExternalTrigger());
    }

    @Test
    public void testGetFrequencyAndSetFrequency()
    {
        ScheduleInfo info = new ScheduleInfo();

        info.setFrequency(60000L);

        Assertions.assertEquals(Long.valueOf(60000L), info.getFrequency());
    }
}
