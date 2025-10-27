package org.finos.legend.depot.core.server.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class ExtendedErrorMessageDiffblueTest {
  /**
   * Method under test:
   * {@link ExtendedErrorMessage#newExtendedErrorMessage(int, String, String, String, Instant)}
   */
  @Test
  void testNewExtendedErrorMessage() {
    // Arrange
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    ExtendedErrorMessage actualNewExtendedErrorMessageResult = ExtendedErrorMessage.newExtendedErrorMessage(1,
        "Not all who wander are lost", "Details", "Stack Trace", timestamp);

    // Assert
    assertEquals("Details", actualNewExtendedErrorMessageResult.getDetails());
    assertEquals("Not all who wander are lost", actualNewExtendedErrorMessageResult.getMessage());
    assertEquals("Stack Trace", actualNewExtendedErrorMessageResult.getStackTrace());
    assertEquals(1, actualNewExtendedErrorMessageResult.getCode().intValue());
    Instant expectedTimestamp = timestamp.EPOCH;
    assertSame(expectedTimestamp, actualNewExtendedErrorMessageResult.getTimestamp());
  }

  /**
   * Method under test:
   * {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String, boolean)}
   */
  @Test
  void testFromThrowable() {
    // Arrange and Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(new Throwable(), 1,
        "Not all who wander are lost", "Details", false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertEquals("Not all who wander are lost", actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(1, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Method under test:
   * {@link ExtendedErrorMessage#fromThrowable(Throwable, Response.Status, String, String, boolean)}
   */
  @Test
  void testFromThrowable2() {
    // Arrange and Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(new Throwable(),
        Response.Status.OK, "Not all who wander are lost", "Details", false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertEquals("Not all who wander are lost", actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(200, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Method under test:
   * {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)}
   */
  @Test
  void testFromThrowable3() {
    // Arrange and Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(new Throwable(), false);

    // Assert
    assertNull(actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(500, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Method under test:
   * {@link ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException, boolean)}
   */
  @Test
  void testFromLegendDepotServerException() {
    // Arrange and Act
    ExtendedErrorMessage actualFromLegendDepotServerExceptionResult = ExtendedErrorMessage
        .fromLegendDepotServerException(new LegendDepotServerException("An error occurred"), false);

    // Assert
    assertEquals("An error occurred", actualFromLegendDepotServerExceptionResult.getMessage());
    assertNull(actualFromLegendDepotServerExceptionResult.getDetails());
    assertNull(actualFromLegendDepotServerExceptionResult.getStackTrace());
    assertEquals(500, actualFromLegendDepotServerExceptionResult.getCode().intValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExtendedErrorMessage#getStackTrace()}
   *   <li>{@link ExtendedErrorMessage#getTimestamp()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ExtendedErrorMessage newExtendedErrorMessageResult = ExtendedErrorMessage.newExtendedErrorMessage(1,
        "Not all who wander are lost", "Details", "Stack Trace",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    String actualStackTrace = newExtendedErrorMessageResult.getStackTrace();
    Instant actualTimestamp = newExtendedErrorMessageResult.getTimestamp();

    // Assert
    assertEquals("Stack Trace", actualStackTrace);
    assertSame(actualTimestamp.EPOCH, actualTimestamp);
  }
}
