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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestScheduleInfo
{
    @Test
    void canCreateWithDefaultConstructor()
    {
        ScheduleInfo info = new ScheduleInfo();

        assertNull(info.getId());
        assertNull(info.getName());
        assertFalse(info.isDisabled());
        assertNull(info.getSingleInstance());
        assertNull(info.getExternalTrigger());
        assertNull(info.getFrequency());
    }

    @Test
    void canCreateWithName()
    {
        ScheduleInfo info = new ScheduleInfo("testSchedule");

        assertEquals("testSchedule", info.getName());
        assertNull(info.getId());
        assertFalse(info.isDisabled());
    }

    @Test
    void canSetAndGetId()
    {
        ScheduleInfo info = new ScheduleInfo();
        info.setId("id-123");

        assertEquals("id-123", info.getId());
    }

    @Test
    void canSetAndGetName()
    {
        ScheduleInfo info = new ScheduleInfo();
        info.setName("mySchedule");

        assertEquals("mySchedule", info.getName());
    }

    @Test
    void canSetAndGetDisabled()
    {
        ScheduleInfo info = new ScheduleInfo();
        info.setDisabled(true);

        assertTrue(info.isDisabled());
    }

    @Test
    void canSetAndGetSingleInstance()
    {
        ScheduleInfo info = new ScheduleInfo();
        info.setSingleInstance(Boolean.TRUE);

        assertEquals(Boolean.TRUE, info.getSingleInstance());
    }

    @Test
    void canSetAndGetExternalTrigger()
    {
        ScheduleInfo info = new ScheduleInfo();
        info.setExternalTrigger(Boolean.TRUE);

        assertEquals(Boolean.TRUE, info.getExternalTrigger());
    }

    @Test
    void canSetAndGetFrequency()
    {
        ScheduleInfo info = new ScheduleInfo();
        info.setFrequency(5000L);

        assertEquals(Long.valueOf(5000L), info.getFrequency());
    }
}
