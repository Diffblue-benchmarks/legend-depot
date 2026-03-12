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

package org.finos.legend.depot.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

public class TestDatesHandler
{
    @Test
    public void testToTimeAndBackToDate()
    {
        LocalDateTime now = LocalDateTime.of(2023, 6, 15, 10, 30, 0);
        long time = DatesHandler.toTime(now);
        LocalDateTime result = DatesHandler.toDate(time);
        Assertions.assertEquals(now, result);
    }

    @Test
    public void testToDateFromJavaUtilDate()
    {
        Date javaDate = new Date(1679411706436L);
        LocalDateTime result = DatesHandler.toDate(javaDate);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testToDateFromJavaUtilDateAndBack()
    {
        LocalDateTime original = LocalDateTime.of(2023, 3, 21, 14, 0, 0);
        Date javaDate = DatesHandler.toDate(original);
        LocalDateTime roundTrip = DatesHandler.toDate(javaDate);
        Assertions.assertEquals(original, roundTrip);
    }

    @Test
    public void testParseDateWithIsoFormat()
    {
        LocalDateTime result = DatesHandler.parseDate("2023-03-21T14:02:49");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2023, result.getYear());
        Assertions.assertEquals(3, result.getMonthValue());
        Assertions.assertEquals(21, result.getDayOfMonth());
        Assertions.assertEquals(14, result.getHour());
        Assertions.assertEquals(2, result.getMinute());
        Assertions.assertEquals(49, result.getSecond());
    }

    @Test
    public void testParseDateWithEpochMillis()
    {
        LocalDateTime result = DatesHandler.parseDate("1679411706436");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2023, result.getYear());
    }

    @Test
    public void testParseDateInvalidStringThrowsException()
    {
        Assertions.assertThrows(NumberFormatException.class, () -> DatesHandler.parseDate("not-a-date"));
    }
}
