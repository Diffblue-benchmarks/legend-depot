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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScheduleInstanceTest
{
    @Test
    public void canCreateScheduleInstanceWithDefaultConstructor()
    {
        // Arrange & Act
        ScheduleInstance instance = new ScheduleInstance();

        // Assert
        assertNotNull(instance);
        assertNull(instance.getId());
        assertNull(instance.getSchedule());
        assertNull(instance.getExpires());
    }

    @Test
    public void canCreateScheduleInstanceWithParameterizedConstructor()
    {
        // Arrange
        String scheduleName = "test-schedule";
        Date expiresDate = new Date();

        // Act
        ScheduleInstance instance = new ScheduleInstance(scheduleName, expiresDate);

        // Assert
        assertNotNull(instance);
        assertEquals(scheduleName, instance.getSchedule());
        assertEquals(expiresDate, instance.getExpires());
    }

    @Test
    public void canGetId()
    {
        // Arrange
        ScheduleInstance instance = new ScheduleInstance();
        String id = "test-id-123";
        instance.setId(id);

        // Act
        String result = instance.getId();

        // Assert
        assertEquals(id, result);
    }

    @Test
    public void canSetId()
    {
        // Arrange
        ScheduleInstance instance = new ScheduleInstance();
        String id = "test-id-456";

        // Act
        instance.setId(id);

        // Assert
        assertEquals(id, instance.getId());
    }

    @Test
    public void canGetSchedule()
    {
        // Arrange
        ScheduleInstance instance = new ScheduleInstance();
        String scheduleName = "my-schedule";
        instance.setSchedule(scheduleName);

        // Act
        String result = instance.getSchedule();

        // Assert
        assertEquals(scheduleName, result);
    }

    @Test
    public void canSetSchedule()
    {
        // Arrange
        ScheduleInstance instance = new ScheduleInstance();
        String scheduleName = "another-schedule";

        // Act
        instance.setSchedule(scheduleName);

        // Assert
        assertEquals(scheduleName, instance.getSchedule());
    }

    @Test
    public void canGetExpires()
    {
        // Arrange
        ScheduleInstance instance = new ScheduleInstance();
        Date expiresDate = new Date();
        instance.setExpires(expiresDate);

        // Act
        Date result = instance.getExpires();

        // Assert
        assertEquals(expiresDate, result);
    }

    @Test
    public void canSetExpires()
    {
        // Arrange
        ScheduleInstance instance = new ScheduleInstance();
        Date expiresDate = new Date(System.currentTimeMillis() + 10000);

        // Act
        instance.setExpires(expiresDate);

        // Assert
        assertEquals(expiresDate, instance.getExpires());
    }

    @Test
    public void testIsExpiredReturnsTrueForPastDate()
    {
        // Arrange
        ScheduleInstance instance = new ScheduleInstance();
        Date pastDate = new Date(System.currentTimeMillis() - 10000);
        instance.setExpires(pastDate);

        // Act
        boolean result = instance.isExpired();

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsExpiredReturnsFalseForFutureDate()
    {
        // Arrange
        ScheduleInstance instance = new ScheduleInstance();
        Date futureDate = new Date(System.currentTimeMillis() + 10000);
        instance.setExpires(futureDate);

        // Act
        boolean result = instance.isExpired();

        // Assert
        assertFalse(result);
    }
}
