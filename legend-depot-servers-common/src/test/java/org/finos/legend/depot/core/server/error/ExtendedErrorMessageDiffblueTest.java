package org.finos.legend.depot.core.server.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ExtendedErrorMessage#getStackTrace()}
   *   <li>{@link ExtendedErrorMessage#getTimestamp()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ExtendedErrorMessage.getStackTrace()",
    "Instant ExtendedErrorMessage.getTimestamp()"
  })
  void testGettersAndSetters() {
    // Arrange
    ExtendedErrorMessage newExtendedErrorMessageResult =
        ExtendedErrorMessage.newExtendedErrorMessage(
            1,
            "Not all who wander are lost",
            "Details",
            "Stack Trace",
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    String actualStackTrace = newExtendedErrorMessageResult.getStackTrace();

    // Assert
    assertEquals("Stack Trace", actualStackTrace);
    assertSame(Instant.EPOCH, newExtendedErrorMessageResult.getTimestamp());
  }

  /**
   * Test {@link ExtendedErrorMessage#newExtendedErrorMessage(int, String, String, String,
   * Instant)}.
   *
   * <p>Method under test: {@link ExtendedErrorMessage#newExtendedErrorMessage(int, String, String,
   * String, Instant)}
   */
  @Test
  @DisplayName("Test newExtendedErrorMessage(int, String, String, String, Instant)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExtendedErrorMessage ExtendedErrorMessage.newExtendedErrorMessage(int, String, String, String, Instant)"
  })
  void testNewExtendedErrorMessage() {
    // Arrange and Act
    ExtendedErrorMessage actualNewExtendedErrorMessageResult =
        ExtendedErrorMessage.newExtendedErrorMessage(
            1,
            "Not all who wander are lost",
            "Details",
            "Stack Trace",
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    assertEquals("Details", actualNewExtendedErrorMessageResult.getDetails());
    assertEquals("Not all who wander are lost", actualNewExtendedErrorMessageResult.getMessage());
    assertEquals("Stack Trace", actualNewExtendedErrorMessageResult.getStackTrace());
    assertEquals(1, actualNewExtendedErrorMessageResult.getCode().intValue());
    assertSame(Instant.EPOCH, actualNewExtendedErrorMessageResult.getTimestamp());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)} with {@code t}, {@code
   * includeStackTrace}.
   *
   * <p>Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, boolean) with 't', 'includeStackTrace'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, boolean)"})
  void testFromThrowableWithTIncludeStackTrace() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException("An error occurred");
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException("An error occurred");
    runtimeException2.initCause(runtimeException);

    RuntimeException t = new RuntimeException("An error occurred");
    t.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(t, false);

    // Assert
    assertEquals("An error occurred", actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(500, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)} with {@code t}, {@code
   * includeStackTrace}.
   *
   * <p>Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, boolean)}
   */
  @Test
  @DisplayName("Test fromThrowable(Throwable, boolean) with 't', 'includeStackTrace'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, boolean)"})
  void testFromThrowableWithTIncludeStackTrace2() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException("An error occurred");
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException("An error occurred");
    runtimeException2.initCause(runtimeException);

    RuntimeException t = new RuntimeException((String) null);
    t.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult = ExtendedErrorMessage.fromThrowable(t, false);

    // Assert
    assertEquals("An error occurred", actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(500, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String, boolean)} with
   * {@code t}, {@code statusCode}, {@code message}, {@code details}, {@code includeStrackTrace}.
   *
   * <p>Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test fromThrowable(Throwable, int, String, String, boolean) with 't', 'statusCode', 'message', 'details', 'includeStrackTrace'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, int, String, String, boolean)"
  })
  void testFromThrowableWithTStatusCodeMessageDetailsIncludeStrackTrace() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException("An error occurred");
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException("An error occurred");
    runtimeException2.initCause(runtimeException);

    RuntimeException t = new RuntimeException("An error occurred");
    t.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult =
        ExtendedErrorMessage.fromThrowable(t, 1, "Not all who wander are lost", "Details", false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertEquals("Not all who wander are lost", actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(1, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String, boolean)} with
   * {@code t}, {@code statusCode}, {@code message}, {@code details}, {@code includeStrackTrace}.
   *
   * <p>Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test fromThrowable(Throwable, int, String, String, boolean) with 't', 'statusCode', 'message', 'details', 'includeStrackTrace'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, int, String, String, boolean)"
  })
  void testFromThrowableWithTStatusCodeMessageDetailsIncludeStrackTrace2() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException("An error occurred");
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException("An error occurred");
    runtimeException2.initCause(runtimeException);

    RuntimeException t = new RuntimeException("An error occurred");
    t.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult =
        ExtendedErrorMessage.fromThrowable(t, 1, null, "Details", false);

    // Assert
    assertEquals("An error occurred", actualFromThrowableResult.getMessage());
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(1, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String, boolean)} with
   * {@code t}, {@code statusCode}, {@code message}, {@code details}, {@code includeStrackTrace}.
   *
   * <p>Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test fromThrowable(Throwable, int, String, String, boolean) with 't', 'statusCode', 'message', 'details', 'includeStrackTrace'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, int, String, String, boolean)"
  })
  void testFromThrowableWithTStatusCodeMessageDetailsIncludeStrackTrace3() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException("An error occurred");
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException("An error occurred");
    runtimeException2.initCause(runtimeException);

    RuntimeException t = new RuntimeException((String) null);
    t.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult =
        ExtendedErrorMessage.fromThrowable(t, 1, null, "Details", false);

    // Assert
    assertEquals("An error occurred", actualFromThrowableResult.getMessage());
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(1, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String, boolean)} with
   * {@code t}, {@code statusCode}, {@code message}, {@code details}, {@code includeStrackTrace}.
   *
   * <p>Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, int, String, String,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test fromThrowable(Throwable, int, String, String, boolean) with 't', 'statusCode', 'message', 'details', 'includeStrackTrace'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, int, String, String, boolean)"
  })
  void testFromThrowableWithTStatusCodeMessageDetailsIncludeStrackTrace4() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException((String) null);
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException((String) null);
    runtimeException2.initCause(runtimeException);

    RuntimeException t = new RuntimeException((String) null);
    t.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult =
        ExtendedErrorMessage.fromThrowable(t, 1, null, "Details", false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(1, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String, String, boolean)}
   * with {@code t}, {@code status}, {@code message}, {@code details}, {@code includeStrackTrace}.
   *
   * <p>Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String,
   * String, boolean)}
   */
  @Test
  @DisplayName(
      "Test fromThrowable(Throwable, Status, String, String, boolean) with 't', 'status', 'message', 'details', 'includeStrackTrace'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, Status, String, String, boolean)"
  })
  void testFromThrowableWithTStatusMessageDetailsIncludeStrackTrace() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException("An error occurred");
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException("An error occurred");
    runtimeException2.initCause(runtimeException);

    RuntimeException t = new RuntimeException("An error occurred");
    t.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult =
        ExtendedErrorMessage.fromThrowable(
            t, Status.OK, "Not all who wander are lost", "Details", false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertEquals("Not all who wander are lost", actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(200, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String, String, boolean)}
   * with {@code t}, {@code status}, {@code message}, {@code details}, {@code includeStrackTrace}.
   *
   * <p>Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String,
   * String, boolean)}
   */
  @Test
  @DisplayName(
      "Test fromThrowable(Throwable, Status, String, String, boolean) with 't', 'status', 'message', 'details', 'includeStrackTrace'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, Status, String, String, boolean)"
  })
  void testFromThrowableWithTStatusMessageDetailsIncludeStrackTrace2() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException("An error occurred");
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException("An error occurred");
    runtimeException2.initCause(runtimeException);

    RuntimeException t = new RuntimeException("An error occurred");
    t.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult =
        ExtendedErrorMessage.fromThrowable(t, Status.OK, null, "Details", false);

    // Assert
    assertEquals("An error occurred", actualFromThrowableResult.getMessage());
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(200, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String, String, boolean)}
   * with {@code t}, {@code status}, {@code message}, {@code details}, {@code includeStrackTrace}.
   *
   * <p>Method under test: {@link ExtendedErrorMessage#fromThrowable(Throwable, Status, String,
   * String, boolean)}
   */
  @Test
  @DisplayName(
      "Test fromThrowable(Throwable, Status, String, String, boolean) with 't', 'status', 'message', 'details', 'includeStrackTrace'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExtendedErrorMessage ExtendedErrorMessage.fromThrowable(Throwable, Status, String, String, boolean)"
  })
  void testFromThrowableWithTStatusMessageDetailsIncludeStrackTrace3() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException("An error occurred");
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException("An error occurred");
    runtimeException2.initCause(runtimeException);

    RuntimeException t = new RuntimeException("An error occurred");
    t.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromThrowableResult =
        ExtendedErrorMessage.fromThrowable(
            t, null, "Not all who wander are lost", "Details", false);

    // Assert
    assertEquals("Details", actualFromThrowableResult.getDetails());
    assertEquals("Not all who wander are lost", actualFromThrowableResult.getMessage());
    assertNull(actualFromThrowableResult.getStackTrace());
    assertEquals(500, actualFromThrowableResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException,
   * boolean)}.
   *
   * <p>Method under test: {@link
   * ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException, boolean)}
   */
  @Test
  @DisplayName("Test fromLegendDepotServerException(LegendDepotServerException, boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExtendedErrorMessage ExtendedErrorMessage.fromLegendDepotServerException(LegendDepotServerException, boolean)"
  })
  void testFromLegendDepotServerException() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException("An error occurred");
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException("An error occurred");
    runtimeException2.initCause(runtimeException);

    LegendDepotServerException e = new LegendDepotServerException("An error occurred");
    e.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromLegendDepotServerExceptionResult =
        ExtendedErrorMessage.fromLegendDepotServerException(e, false);

    // Assert
    assertEquals("An error occurred", actualFromLegendDepotServerExceptionResult.getMessage());
    assertNull(actualFromLegendDepotServerExceptionResult.getDetails());
    assertNull(actualFromLegendDepotServerExceptionResult.getStackTrace());
    assertEquals(500, actualFromLegendDepotServerExceptionResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException,
   * boolean)}.
   *
   * <p>Method under test: {@link
   * ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException, boolean)}
   */
  @Test
  @DisplayName("Test fromLegendDepotServerException(LegendDepotServerException, boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExtendedErrorMessage ExtendedErrorMessage.fromLegendDepotServerException(LegendDepotServerException, boolean)"
  })
  void testFromLegendDepotServerException2() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException("An error occurred");
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException("An error occurred");
    runtimeException2.initCause(runtimeException);

    LegendDepotServerException e = new LegendDepotServerException(null);
    e.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromLegendDepotServerExceptionResult =
        ExtendedErrorMessage.fromLegendDepotServerException(e, false);

    // Assert
    assertEquals("An error occurred", actualFromLegendDepotServerExceptionResult.getMessage());
    assertNull(actualFromLegendDepotServerExceptionResult.getDetails());
    assertNull(actualFromLegendDepotServerExceptionResult.getStackTrace());
    assertEquals(500, actualFromLegendDepotServerExceptionResult.getCode().intValue());
  }

  /**
   * Test {@link ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException,
   * boolean)}.
   *
   * <ul>
   *   <li>Then return Message is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExtendedErrorMessage#fromLegendDepotServerException(LegendDepotServerException, boolean)}
   */
  @Test
  @DisplayName(
      "Test fromLegendDepotServerException(LegendDepotServerException, boolean); then return Message is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExtendedErrorMessage ExtendedErrorMessage.fromLegendDepotServerException(LegendDepotServerException, boolean)"
  })
  void testFromLegendDepotServerException_thenReturnMessageIsNull() {
    // Arrange
    RuntimeException runtimeException = new RuntimeException((String) null);
    runtimeException.initCause(new Throwable());

    RuntimeException runtimeException2 = new RuntimeException((String) null);
    runtimeException2.initCause(runtimeException);

    LegendDepotServerException e = new LegendDepotServerException(null);
    e.initCause(runtimeException2);

    // Act
    ExtendedErrorMessage actualFromLegendDepotServerExceptionResult =
        ExtendedErrorMessage.fromLegendDepotServerException(e, false);

    // Assert
    assertNull(actualFromLegendDepotServerExceptionResult.getDetails());
    assertNull(actualFromLegendDepotServerExceptionResult.getMessage());
    assertNull(actualFromLegendDepotServerExceptionResult.getStackTrace());
    assertEquals(500, actualFromLegendDepotServerExceptionResult.getCode().intValue());
  }
}
