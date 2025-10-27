package org.finos.legend.depot.store.model.admin.schedules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.Test;

class ScheduleInstanceDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ScheduleInstance#ScheduleInstance()}
   *   <li>{@link ScheduleInstance#setExpires(Date)}
   *   <li>{@link ScheduleInstance#setId(String)}
   *   <li>{@link ScheduleInstance#setSchedule(String)}
   *   <li>{@link ScheduleInstance#getExpires()}
   *   <li>{@link ScheduleInstance#getId()}
   *   <li>{@link ScheduleInstance#getSchedule()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ScheduleInstance actualScheduleInstance = new ScheduleInstance();
    Date expires = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualScheduleInstance.setExpires(expires);
    actualScheduleInstance.setId("42");
    actualScheduleInstance.setSchedule("Schedule");
    Date actualExpires = actualScheduleInstance.getExpires();
    String actualId = actualScheduleInstance.getId();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Schedule", actualScheduleInstance.getSchedule());
    assertSame(expires, actualExpires);
  }

  /**
   * Method under test: {@link ScheduleInstance#isExpired()}
   */
  @Test
  void testIsExpired() {
    // Arrange
    java.sql.Date expires = mock(java.sql.Date.class);
    when(expires.getTime()).thenReturn(10L);

    ScheduleInstance scheduleInstance = new ScheduleInstance();
    scheduleInstance.setExpires(expires);

    // Act
    scheduleInstance.isExpired();

    // Assert
    verify(expires).getTime();
  }

  /**
   * Method under test: {@link ScheduleInstance#isExpired()}
   */
  @Test
  void testIsExpired2() {
    // Arrange
    java.sql.Date expires = mock(java.sql.Date.class);
    when(expires.getTime()).thenReturn(Long.MAX_VALUE);

    ScheduleInstance scheduleInstance = new ScheduleInstance();
    scheduleInstance.setExpires(expires);

    // Act
    boolean actualIsExpiredResult = scheduleInstance.isExpired();

    // Assert
    verify(expires).getTime();
    assertFalse(actualIsExpiredResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ScheduleInstance#ScheduleInstance(String, Date)}
   *   <li>{@link ScheduleInstance#setExpires(Date)}
   *   <li>{@link ScheduleInstance#setId(String)}
   *   <li>{@link ScheduleInstance#setSchedule(String)}
   *   <li>{@link ScheduleInstance#getExpires()}
   *   <li>{@link ScheduleInstance#getId()}
   *   <li>{@link ScheduleInstance#getSchedule()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    ScheduleInstance actualScheduleInstance = new ScheduleInstance("Name",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    Date expires = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualScheduleInstance.setExpires(expires);
    actualScheduleInstance.setId("42");
    actualScheduleInstance.setSchedule("Schedule");
    Date actualExpires = actualScheduleInstance.getExpires();
    String actualId = actualScheduleInstance.getId();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Schedule", actualScheduleInstance.getSchedule());
    assertSame(expires, actualExpires);
  }
}
