package org.finos.legend.depot.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DatesHandlerDiffblueTest {
  /**
   * Test {@link DatesHandler#toTime(LocalDateTime)}.
   * <p>
   * Method under test: {@link DatesHandler#toTime(LocalDateTime)}
   */
  @Test
  @DisplayName("Test toTime(LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long DatesHandler.toTime(LocalDateTime)"})
  void testToTime() {
    // Arrange, Act and Assert
    assertEquals(0L, DatesHandler.toTime(LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Test {@link DatesHandler#toDate(Date)} with {@code Date}.
   * <ul>
   *   <li>Then return toLocalTime toString is {@code 00:00}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DatesHandler#toDate(Date)}
   */
  @Test
  @DisplayName("Test toDate(Date) with 'Date'; then return toLocalTime toString is '00:00'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"LocalDateTime DatesHandler.toDate(Date)"})
  void testToDateWithDate_thenReturnToLocalTimeToStringIs0000() {
    // Arrange and Act
    LocalDateTime actualToDateResult = DatesHandler
        .toDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    assertEquals("00:00", actualToDateResult.toLocalTime().toString());
    assertEquals("1970-01-01", actualToDateResult.toLocalDate().toString());
  }

  /**
   * Test {@link DatesHandler#toDate(long)} with {@code long}.
   * <p>
   * Method under test: {@link DatesHandler#toDate(long)}
   */
  @Test
  @DisplayName("Test toDate(long) with 'long'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"LocalDateTime DatesHandler.toDate(long)"})
  void testToDateWithLong() {
    // Arrange and Act
    LocalDateTime actualToDateResult = DatesHandler.toDate(10L);

    // Assert
    assertEquals("00:00:00.010", actualToDateResult.toLocalTime().toString());
    assertEquals("1970-01-01", actualToDateResult.toLocalDate().toString());
  }

  /**
   * Test {@link DatesHandler#parseDate(String)}.
   * <ul>
   *   <li>When {@code 20200301}.</li>
   *   <li>Then return toLocalTime toString is {@code 05:36:40.301}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DatesHandler#parseDate(String)}
   */
  @Test
  @DisplayName("Test parseDate(String); when '20200301'; then return toLocalTime toString is '05:36:40.301'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"LocalDateTime DatesHandler.parseDate(String)"})
  void testParseDate_when20200301_thenReturnToLocalTimeToStringIs053640301() {
    // Arrange and Act
    LocalDateTime actualParseDateResult = DatesHandler.parseDate("20200301");

    // Assert
    assertEquals("05:36:40.301", actualParseDateResult.toLocalTime().toString());
    assertEquals("1970-01-01", actualParseDateResult.toLocalDate().toString());
  }
}
