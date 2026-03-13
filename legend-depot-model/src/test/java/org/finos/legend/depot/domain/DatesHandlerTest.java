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

public class DatesHandlerTest
{
    @Test
    public void testToTimeAndBack()
    {
        LocalDateTime original = LocalDateTime.of(2023, 6, 15, 10, 30, 0);
        long time = DatesHandler.toTime(original);
        LocalDateTime result = DatesHandler.toDate(time);
        Assertions.assertEquals(original, result);
    }

    @Test
    public void testToDateFromJavaUtilDate()
    {
        LocalDateTime original = LocalDateTime.of(2023, 6, 15, 10, 30, 0);
        Date javaDate = DatesHandler.toDate(original);
        LocalDateTime result = DatesHandler.toDate(javaDate);
        Assertions.assertEquals(original, result);
    }

    @Test
    public void testToDateFromLocalDateTime()
    {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        Date date = DatesHandler.toDate(localDateTime);
        Assertions.assertNotNull(date);
        LocalDateTime roundTrip = DatesHandler.toDate(date);
        Assertions.assertEquals(localDateTime, roundTrip);
    }

    @Test
    public void testParseDateWithIsoFormat()
    {
        LocalDateTime result = DatesHandler.parseDate("2023-06-15T10:30:00");
        Assertions.assertEquals(LocalDateTime.of(2023, 6, 15, 10, 30, 0), result);
    }

    @Test
    public void testParseDateWithEpochMillis()
    {
        LocalDateTime expected = LocalDateTime.of(2023, 6, 15, 10, 30, 0);
        long millis = DatesHandler.toTime(expected);
        LocalDateTime result = DatesHandler.parseDate(String.valueOf(millis));
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testToTimePositiveValue()
    {
        LocalDateTime date = LocalDateTime.of(2023, 6, 15, 10, 30, 0);
        long time = DatesHandler.toTime(date);
        Assertions.assertTrue(time > 0);
    }

    @Test
    public void testToDateFromEpochZero()
    {
        LocalDateTime result = DatesHandler.toDate(0L);
        Assertions.assertNotNull(result);
    }
}
