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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScheduleInfoTest
{
    @Test
    public void testDefaultConstructor()
    {
        // Act
        ScheduleInfo scheduleInfo = new ScheduleInfo();

        // Assert
        assertNotNull(scheduleInfo);
        assertNull(scheduleInfo.getId());
        assertNull(scheduleInfo.getName());
        assertFalse(scheduleInfo.isDisabled());
        assertNull(scheduleInfo.getSingleInstance());
        assertNull(scheduleInfo.getExternalTrigger());
        assertNull(scheduleInfo.getFrequency());
    }

    @Test
    public void testConstructorWithName()
    {
        // Arrange
        String expectedName = "test-schedule";

        // Act
        ScheduleInfo scheduleInfo = new ScheduleInfo(expectedName);

        // Assert
        assertNotNull(scheduleInfo);
        assertEquals(expectedName, scheduleInfo.getName());
        assertNull(scheduleInfo.getId());
        assertFalse(scheduleInfo.isDisabled());
        assertNull(scheduleInfo.getSingleInstance());
        assertNull(scheduleInfo.getExternalTrigger());
        assertNull(scheduleInfo.getFrequency());
    }

    @Test
    public void canGetAndSetId()
    {
        // Arrange
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        String expectedId = "schedule-123";

        // Act
        scheduleInfo.setId(expectedId);

        // Assert
        assertEquals(expectedId, scheduleInfo.getId());
    }

    @Test
    public void canGetAndSetName()
    {
        // Arrange
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        String expectedName = "my-schedule";

        // Act
        scheduleInfo.setName(expectedName);

        // Assert
        assertEquals(expectedName, scheduleInfo.getName());
    }

    @Test
    public void canGetAndSetDisabled()
    {
        // Arrange
        ScheduleInfo scheduleInfo = new ScheduleInfo();

        // Act
        scheduleInfo.setDisabled(true);

        // Assert
        assertTrue(scheduleInfo.isDisabled());
    }

    @Test
    public void canGetAndSetDisabledToFalse()
    {
        // Arrange
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.setDisabled(true);

        // Act
        scheduleInfo.setDisabled(false);

        // Assert
        assertFalse(scheduleInfo.isDisabled());
    }

    @Test
    public void canGetAndSetSingleInstance()
    {
        // Arrange
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        Boolean expectedValue = true;

        // Act
        scheduleInfo.setSingleInstance(expectedValue);

        // Assert
        assertEquals(expectedValue, scheduleInfo.getSingleInstance());
    }

    @Test
    public void canGetAndSetSingleInstanceToFalse()
    {
        // Arrange
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        Boolean expectedValue = false;

        // Act
        scheduleInfo.setSingleInstance(expectedValue);

        // Assert
        assertEquals(expectedValue, scheduleInfo.getSingleInstance());
    }

    @Test
    public void canGetAndSetExternalTrigger()
    {
        // Arrange
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        Boolean expectedValue = true;

        // Act
        scheduleInfo.setExternalTrigger(expectedValue);

        // Assert
        assertEquals(expectedValue, scheduleInfo.getExternalTrigger());
    }

    @Test
    public void canGetAndSetExternalTriggerToFalse()
    {
        // Arrange
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        Boolean expectedValue = false;

        // Act
        scheduleInfo.setExternalTrigger(expectedValue);

        // Assert
        assertEquals(expectedValue, scheduleInfo.getExternalTrigger());
    }

    @Test
    public void canGetAndSetFrequency()
    {
        // Arrange
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        Long expectedFrequency = 60000L;

        // Act
        scheduleInfo.setFrequency(expectedFrequency);

        // Assert
        assertEquals(expectedFrequency, scheduleInfo.getFrequency());
    }
}
