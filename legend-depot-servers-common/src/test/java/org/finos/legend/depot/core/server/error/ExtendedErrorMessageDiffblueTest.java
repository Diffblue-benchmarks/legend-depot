package org.finos.legend.depot.core.server.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
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
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, boolean) with 't', 'includeStackTrace'; given 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, boolean)"})
  void testFromThrowableWithTIncludeStackTrace_givenNull() {
    // Arrange
    Throwable t = new Throwable((String) null);
    t.initCause(null);

    // Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(t, false);

    // Assert
    assertNull(actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(500, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)} with {@code t}, {@code includeStackTrace}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, boolean) with 't', 'includeStackTrace'; given Throwable()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, boolean)"})
  void testFromThrowableWithTIncludeStackTrace_givenThrowable() {
    // Arrange
    Throwable t = new Throwable((String) null);
    t.initCause(new Throwable());

    // Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(t, false);

    // Assert
    assertNull(actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(500, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)} with {@code t}, {@code includeStackTrace}.
   * <ul>
   *   <li>Then return Message is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, boolean) with 't', 'includeStackTrace'; then return Message is 'foo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, boolean)"})
  void testFromThrowableWithTIncludeStackTrace_thenReturnMessageIsFoo() {
    // Arrange
    Throwable throwable = new Throwable("foo");
    throwable.initCause(null);

    Throwable throwable2 = new Throwable((String) null);
    throwable2.initCause(throwable);

    Throwable t = new Throwable((String) null);
    t.initCause(throwable2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(t, false);

    // Assert
    assertEquals("foo", actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getDetails());
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
    // Arrange
    Throwable throwable = new Throwable("foo");
    throwable.initCause(null);

    Throwable throwable2 = new Throwable((String) null);
    throwable2.initCause(throwable);

    Throwable t = new Throwable((String) null);
    t.initCause(throwable2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(t, 1, null, "Details", false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertEquals("foo", actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(1, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String, boolean)} with {@code t}, {@code statusCode}, {@code message}, {@code details}, {@code includeStrackTrace}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, int, String, String, boolean) with 't', 'statusCode', 'message', 'details', 'includeStrackTrace'; given 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, int, String, String, boolean)"})
  void testFromThrowableWithTStatusCodeMessageDetailsIncludeStrackTrace_givenNull() {
    // Arrange
    Throwable t = new Throwable((String) null);
    t.initCause(null);

    // Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(t, 1, null, "Details", false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(1, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String, boolean)} with {@code t}, {@code statusCode}, {@code message}, {@code details}, {@code includeStrackTrace}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, int, String, String, boolean) with 't', 'statusCode', 'message', 'details', 'includeStrackTrace'; given Throwable()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, int, String, String, boolean)"})
  void testFromThrowableWithTStatusCodeMessageDetailsIncludeStrackTrace_givenThrowable() {
    // Arrange
    Throwable t = new Throwable((String) null);
    t.initCause(new Throwable());

    // Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(t, 1, null, "Details", false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getMessage());
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
    // Arrange
    Throwable throwable = new Throwable("foo");
    throwable.initCause(null);

    Throwable throwable2 = new Throwable((String) null);
    throwable2.initCause(throwable);

    Throwable t = new Throwable((String) null);
    t.initCause(throwable2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(t, null, null, "Details",
        false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertEquals("foo", actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(500, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String, String, boolean)} with {@code t}, {@code status}, {@code message}, {@code details}, {@code includeStrackTrace}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String, String, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, Status, String, String, boolean) with 't', 'status', 'message', 'details', 'includeStrackTrace'; given 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, Status, String, String, boolean)"})
  void testFromThrowableWithTStatusMessageDetailsIncludeStrackTrace_givenNull() {
    // Arrange
    Throwable t = new Throwable((String) null);
    t.initCause(null);

    // Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(t, null, null, "Details",
        false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(500, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String, String, boolean)} with {@code t}, {@code status}, {@code message}, {@code details}, {@code includeStrackTrace}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String, String, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, Status, String, String, boolean) with 't', 'status', 'message', 'details', 'includeStrackTrace'; given Throwable()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, Status, String, String, boolean)"})
  void testFromThrowableWithTStatusMessageDetailsIncludeStrackTrace_givenThrowable() {
    // Arrange
    Throwable t = new Throwable((String) null);
    t.initCause(new Throwable());

    // Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(t, null, null, "Details",
        false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(500, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException, boolean)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException, boolean)}
   */
  @Test
  @DisplayName("Test fromLegendDepotServerException(LegendDepotServerException, boolean); given 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ExtendedErrorMessage ExtendedErrorMessage.fromLegendDepotServerException(LegendDepotServerException, boolean)"})
  void testFromLegendDepotServerException_givenNull() {
    // Arrange
    LegendDepotServerException e = new LegendDepotServerException(null);
    e.initCause(null);

    // Act
    ExtendedErrorMessage actualFromLegendDepotServerExceptionResult = ExtendedErrorMessage
        .fromLegendDepotServerException(e, false);

    // Assert
    assertNull(actualFromLegendDepotServerExceptionResult.getDetails());
    assertNull(actualFromLegendDepotServerExceptionResult.getMessage());
    assertNull(actualFromLegendDepotServerExceptionResult.getStackTrace());
    assertEquals(500, actualFromLegendDepotServerExceptionResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException, boolean)}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException, boolean)}
   */
  @Test
  @DisplayName("Test fromLegendDepotServerException(LegendDepotServerException, boolean); given Throwable()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ExtendedErrorMessage ExtendedErrorMessage.fromLegendDepotServerException(LegendDepotServerException, boolean)"})
  void testFromLegendDepotServerException_givenThrowable() {
    // Arrange
    LegendDepotServerException e = new LegendDepotServerException(null);
    e.initCause(new Throwable());

    // Act
    ExtendedErrorMessage actualFromLegendDepotServerExceptionResult = ExtendedErrorMessage
        .fromLegendDepotServerException(e, false);

    // Assert
    assertNull(actualFromLegendDepotServerExceptionResult.getDetails());
    assertNull(actualFromLegendDepotServerExceptionResult.getMessage());
    assertNull(actualFromLegendDepotServerExceptionResult.getStackTrace());
    assertEquals(500, actualFromLegendDepotServerExceptionResult.getCode().intValue());
  }
}
