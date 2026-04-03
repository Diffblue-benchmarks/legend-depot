// Copyright 2021 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.finos.legend.depot.store.model.admin.schedules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ScheduleInstanceTest
{
    @Test
    public void testDefaultConstructor()
    {
        ScheduleInstance instance = new ScheduleInstance();

        Assertions.assertNull(instance.getId());
        Assertions.assertNull(instance.getSchedule());
        Assertions.assertNull(instance.getExpires());
    }

    @Test
    public void testParameterizedConstructor()
    {
        Date expires = new Date(System.currentTimeMillis() + 10000L);
        ScheduleInstance instance = new ScheduleInstance("testSchedule", expires);

        Assertions.assertEquals("testSchedule", instance.getSchedule());
        Assertions.assertEquals(expires, instance.getExpires());
    }

    @Test
    public void testSetAndGetId()
    {
        ScheduleInstance instance = new ScheduleInstance();

        instance.setId("id-123");

        Assertions.assertEquals("id-123", instance.getId());
    }

    @Test
    public void testSetAndGetExpires()
    {
        ScheduleInstance instance = new ScheduleInstance();
        Date expires = new Date(System.currentTimeMillis() + 5000L);

        instance.setExpires(expires);

        Assertions.assertEquals(expires, instance.getExpires());
    }

    @Test
    public void testSetAndGetSchedule()
    {
        ScheduleInstance instance = new ScheduleInstance();

        instance.setSchedule("mySchedule");

        Assertions.assertEquals("mySchedule", instance.getSchedule());
    }

    @Test
    public void testIsExpiredWhenNotExpired()
    {
        Date futureDate = new Date(System.currentTimeMillis() + 60000L);
        ScheduleInstance instance = new ScheduleInstance("schedule", futureDate);

        Assertions.assertFalse(instance.isExpired());
    }

    @Test
    public void testIsExpiredWhenExpired()
    {
        Date pastDate = new Date(System.currentTimeMillis() - 60000L);
        ScheduleInstance instance = new ScheduleInstance("schedule", pastDate);

        Assertions.assertTrue(instance.isExpired());
    }
}
