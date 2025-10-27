package org.finos.legend.depot.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.Test;

class DatesHandlerDiffblueTest {
  /**
   * Method under test: {@link DatesHandler#toTime(LocalDateTime)}
   */
  @Test
  void testToTime() {
    // Arrange, Act and Assert
    assertEquals(0L, DatesHandler.toTime(LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test: {@link DatesHandler#toDate(long)}
   */
  @Test
  void testToDate() {
    // Arrange and Act
    LocalDateTime actualToDateResult = DatesHandler.toDate(10L);

    // Assert
    assertEquals("00:00:00.010", actualToDateResult.toLocalTime().toString());
    assertEquals("1970-01-01", actualToDateResult.toLocalDate().toString());
  }

  /**
   * Method under test: {@link DatesHandler#toDate(Date)}
   */
  @Test
  void testToDate2() {
    // Arrange and Act
    LocalDateTime actualToDateResult = DatesHandler
        .toDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    assertEquals("00:00", actualToDateResult.toLocalTime().toString());
    assertEquals("1970-01-01", actualToDateResult.toLocalDate().toString());
  }

  /**
   * Method under test: {@link DatesHandler#toDate(java.util.Date)}
   */
  @Test
  void testToDate3() {
    // Arrange
    java.sql.Date date = mock(java.sql.Date.class);
    when(date.getTime()).thenReturn(10L);

    // Act
    LocalDateTime actualToDateResult = DatesHandler.toDate(date);

    // Assert
    verify(date).getTime();
    assertEquals("00:00:00.010", actualToDateResult.toLocalTime().toString());
    assertEquals("1970-01-01", actualToDateResult.toLocalDate().toString());
  }

  /**
   * Method under test: {@link DatesHandler#parseDate(String)}
   */
  @Test
  void testParseDate() {
    // Arrange and Act
    LocalDateTime actualParseDateResult = DatesHandler.parseDate("20200301");

    // Assert
    assertEquals("05:36:40.301", actualParseDateResult.toLocalTime().toString());
    assertEquals("1970-01-01", actualParseDateResult.toLocalDate().toString());
  }
}
