package org.finos.legend.depot.core.server.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExtendedErrorMessageDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExtendedErrorMessage#getStackTrace()}
   *   <li>{@link ExtendedErrorMessage#getTimestamp()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ExtendedErrorMessage.getStackTrace()", "Instant ExtendedErrorMessage.getTimestamp()"})
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

  /**
   * Test {@link ExtendedErrorMessage#newExtendedErrorMessage(int, String, String, String, Instant)}.
   * <p>
   * Method under test: {@link ExtendedErrorMessage#newExtendedErrorMessage(int, String, String, String, Instant)}
   */
  @Test
  @DisplayName("Test newExtendedErrorMessage(int, String, String, String, Instant)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ExtendedErrorMessage ExtendedErrorMessage.newExtendedErrorMessage(int, String, String, String, Instant)"})
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
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)} with {@code t}, {@code includeStackTrace}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return StackTrace is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, boolean) with 't', 'includeStackTrace'; when 'false'; then return StackTrace is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, boolean)"})
  void testFromThrowableWithTIncludeStackTrace_whenFalse_thenReturnStackTraceIsNull() {
    // Arrange and Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(new Throwable(), false);

    // Assert
    assertNull(actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(500, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String, boolean)} with {@code t}, {@code statusCode}, {@code message}, {@code details}, {@code includeStrackTrace}.
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, int, String, String, boolean) with 't', 'statusCode', 'message', 'details', 'includeStrackTrace'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, int, String, String, boolean)"})
  void testFromThrowableWithTStatusCodeMessageDetailsIncludeStrackTrace() {
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
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String, String, boolean)} with {@code t}, {@code status}, {@code message}, {@code details}, {@code includeStrackTrace}.
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String, String, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, Status, String, String, boolean) with 't', 'status', 'message', 'details', 'includeStrackTrace'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, Status, String, String, boolean)"})
  void testFromThrowableWithTStatusMessageDetailsIncludeStrackTrace() {
    // Arrange and Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(new Throwable(), Status.OK,
        "Not all who wander are lost", "Details", false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertEquals("Not all who wander are lost", actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(200, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return StackTrace is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException, boolean)}
   */
  @Test
  @DisplayName("Test fromLegendDepotServerException(LegendDepotServerException, boolean); when 'false'; then return StackTrace is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ExtendedErrorMessage ExtendedErrorMessage.fromLegendDepotServerException(LegendDepotServerException, boolean)"})
  void testFromLegendDepotServerException_whenFalse_thenReturnStackTraceIsNull() {
    // Arrange and Act
    ExtendedErrorMessage actualFromLegendDepotServerExceptionResult = ExtendedErrorMessage
        .fromLegendDepotServerException(new LegendDepotServerException("An error occurred"), false);

    // Assert
    assertEquals("An error occurred", actualFromLegendDepotServerExceptionResult.getMessage());
    assertNull(actualFromLegendDepotServerExceptionResult.getDetails());
    assertNull(actualFromLegendDepotServerExceptionResult.getStackTrace());
    assertEquals(500, actualFromLegendDepotServerExceptionResult.getCode().intValue());
  }
}
