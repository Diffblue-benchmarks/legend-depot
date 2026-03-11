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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestScheduleInstance
{
    @Test
    public void canCreateDefaultInstance()
    {
        ScheduleInstance instance = new ScheduleInstance();

        assertNull(instance.getId());
        assertNull(instance.getSchedule());
        assertNull(instance.getExpires());
    }

    @Test
    public void canCreateInstanceWithNameAndExpires()
    {
        Date expires = new Date(System.currentTimeMillis() + 60000);
        ScheduleInstance instance = new ScheduleInstance("testSchedule", expires);

        assertEquals("testSchedule", instance.getSchedule());
        assertEquals(expires, instance.getExpires());
        assertNull(instance.getId());
    }

    @Test
    public void canSetAndGetId()
    {
        ScheduleInstance instance = new ScheduleInstance();

        instance.setId("test-id");
        assertEquals("test-id", instance.getId());
    }

    @Test
    public void canSetAndGetSchedule()
    {
        ScheduleInstance instance = new ScheduleInstance();

        instance.setSchedule("mySchedule");
        assertEquals("mySchedule", instance.getSchedule());
    }

    @Test
    public void canSetAndGetExpires()
    {
        ScheduleInstance instance = new ScheduleInstance();
        Date expires = new Date();

        instance.setExpires(expires);
        assertEquals(expires, instance.getExpires());
    }

    @Test
    public void testIsExpiredWhenExpired()
    {
        Date pastDate = new Date(System.currentTimeMillis() - 60000);
        ScheduleInstance instance = new ScheduleInstance("testSchedule", pastDate);

        assertTrue(instance.isExpired());
    }

    @Test
    public void testIsExpiredWhenNotExpired()
    {
        Date futureDate = new Date(System.currentTimeMillis() + 60000);
        ScheduleInstance instance = new ScheduleInstance("testSchedule", futureDate);

        assertFalse(instance.isExpired());
    }
}
