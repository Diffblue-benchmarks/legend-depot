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
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DatesHandlerTest
{
    @Test
    public void canGetEventByEpocMillis()
    {

        LocalDateTime date = DatesHandler.parseDate("1679411706436");
        LocalDateTime lunchTime = LocalDateTime.parse("2023-03-21T14:02:49", DateTimeFormatter.ISO_DATE_TIME);
        Assertions.assertNotNull(date);

    }

    @Test
    public void canConvertLocalDateTimeToTime()
    {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 21, 14, 0, 0);
        long time = DatesHandler.toTime(dateTime);
        Assertions.assertTrue(time > 0);
    }

    @Test
    public void canConvertDateToLocalDateTime()
    {
        Date date = new Date();
        LocalDateTime result = DatesHandler.toDate(date);
        Assertions.assertNotNull(result);
    }

    @Test
    public void canConvertEpochMillisToLocalDateTime()
    {
        long millis = System.currentTimeMillis();
        LocalDateTime result = DatesHandler.toDate(millis);
        Assertions.assertNotNull(result);
    }

    @Test
    public void canConvertLocalDateTimeToDate()
    {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 21, 14, 0, 0);
        Date result = DatesHandler.toDate(dateTime);
        Assertions.assertNotNull(result);
    }

    @Test
    public void canParseDateFromIsoString()
    {
        LocalDateTime result = DatesHandler.parseDate("2023-03-21T14:02:49");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2023, result.getYear());
        Assertions.assertEquals(3, result.getMonthValue());
        Assertions.assertEquals(21, result.getDayOfMonth());
    }

    @Test
    public void canRoundTripLocalDateTimeViaTime()
    {
        LocalDateTime original = LocalDateTime.of(2023, 6, 15, 10, 30, 0);
        long time = DatesHandler.toTime(original);
        LocalDateTime roundTripped = DatesHandler.toDate(time);
        Assertions.assertEquals(original, roundTripped);
    }

    @Test
    public void canRoundTripLocalDateTimeViaDate()
    {
        LocalDateTime original = LocalDateTime.of(2023, 6, 15, 10, 30, 0);
        Date date = DatesHandler.toDate(original);
        LocalDateTime roundTripped = DatesHandler.toDate(date);
        Assertions.assertEquals(original, roundTripped);
    }
}
