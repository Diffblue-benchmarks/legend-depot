package org.finos.legend.depot.core.server.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LegendDepotServerExceptionDiffblueTest {
  /**
   * Method under test: {@link LegendDepotServerException#getStatus()}
   */
  @Test
  void testGetStatus() {
    // Arrange, Act and Assert
    assertEquals(Response.Status.INTERNAL_SERVER_ERROR,
        (new LegendDepotServerException("An error occurred")).getStatus());
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validateNonNull(Object, String)}
   */
  @Test
  void testValidateNonNull() {
    // Arrange, Act and Assert
    assertEquals("Arg", LegendDepotServerException.validateNonNull("Arg", "An error occurred"));
    assertEquals("Arg", LegendDepotServerException.validateNonNull("Arg", "An error occurred", Response.Status.OK));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, String)}
   */
  @Test
  void testValidate() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(true);

    // Act
    Object actualValidateResult = LegendDepotServerException.validate("Arg", predicate, "An error occurred");

    // Assert
    verify(predicate).test(isA(Object.class));
    assertEquals("Arg", actualValidateResult);
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, String)}
   */
  @Test
  void testValidate2() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, "An error occurred"));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, String)}
   */
  @Test
  void testValidate3() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenThrow(new LegendDepotServerException("An error occurred"));

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, "An error occurred"));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, String, Response.Status)}
   */
  @Test
  void testValidate4() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(true);

    // Act
    Object actualValidateResult = LegendDepotServerException.validate("Arg", predicate, "An error occurred",
        Response.Status.OK);

    // Assert
    verify(predicate).test(isA(Object.class));
    assertEquals("Arg", actualValidateResult);
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, String, Response.Status)}
   */
  @Test
  void testValidate5() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, "An error occurred", Response.Status.OK));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, String, Response.Status)}
   */
  @Test
  void testValidate6() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenThrow(new LegendDepotServerException("An error occurred"));

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, "An error occurred", Response.Status.OK));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, String, Response.Status)}
   */
  @Test
  void testValidate7() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, "An error occurred", null));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, Function)}
   */
  @Test
  void testValidate8() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(true);

    // Act
    Object actualValidateResult = LegendDepotServerException.validate("Arg", predicate, mock(Function.class));

    // Assert
    verify(predicate).test(isA(Object.class));
    assertEquals("Arg", actualValidateResult);
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, Function)}
   */
  @Test
  void testValidate9() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);
    Function<Object, String> messageFn = mock(Function.class);
    when(messageFn.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, messageFn));
    verify(messageFn).apply(isA(Object.class));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, Function)}
   */
  @Test
  void testValidate10() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);
    Function<Object, String> messageFn = mock(Function.class);
    when(messageFn.apply(Mockito.<Object>any())).thenThrow(new LegendDepotServerException("An error occurred"));

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, messageFn));
    verify(messageFn).apply(isA(Object.class));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, Function, Response.Status)}
   */
  @Test
  void testValidate11() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(true);

    // Act
    Object actualValidateResult = LegendDepotServerException.validate("Arg", predicate, mock(Function.class),
        Response.Status.OK);

    // Assert
    verify(predicate).test(isA(Object.class));
    assertEquals("Arg", actualValidateResult);
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, Function, Response.Status)}
   */
  @Test
  void testValidate12() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);
    Function<Object, String> messageFn = mock(Function.class);
    when(messageFn.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, messageFn, Response.Status.OK));
    verify(messageFn).apply(isA(Object.class));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, Function, Response.Status)}
   */
  @Test
  void testValidate13() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);
    Function<Object, String> messageFn = mock(Function.class);
    when(messageFn.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, messageFn, null));
    verify(messageFn).apply(isA(Object.class));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, Function, Response.Status)}
   */
  @Test
  void testValidate14() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);
    Function<Object, String> messageFn = mock(Function.class);
    when(messageFn.apply(Mockito.<Object>any())).thenThrow(new LegendDepotServerException("An error occurred"));

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, messageFn, Response.Status.OK));
    verify(messageFn).apply(isA(Object.class));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, Function, Response.Status)}
   */
  @Test
  void testValidate15() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, (Function<? super Object, String>) null, null));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#validate(Object, Predicate, Function, Response.Status)}
   */
  @Test
  void testValidate16() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenThrow(new LegendDepotServerException("An error occurred"));

    // Act and Assert
    assertThrows(LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, (Function<? super Object, String>) null, null));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#LegendDepotServerException(String)}
   */
  @Test
  void testNewLegendDepotServerException() {
    // Arrange and Act
    LegendDepotServerException actualLegendDepotServerException = new LegendDepotServerException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertNull(actualLegendDepotServerException.getCause());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Response.Status.INTERNAL_SERVER_ERROR, actualLegendDepotServerException.getStatus());
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#LegendDepotServerException(String, Throwable)}
   */
  @Test
  void testNewLegendDepotServerException2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    LegendDepotServerException actualLegendDepotServerException = new LegendDepotServerException("An error occurred",
        cause);

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Response.Status.INTERNAL_SERVER_ERROR, actualLegendDepotServerException.getStatus());
    assertSame(cause, actualLegendDepotServerException.getCause());
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#LegendDepotServerException(String, Response.Status)}
   */
  @Test
  void testNewLegendDepotServerException3() {
    // Arrange and Act
    LegendDepotServerException actualLegendDepotServerException = new LegendDepotServerException("An error occurred",
        Response.Status.OK);

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertNull(actualLegendDepotServerException.getCause());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Response.Status.OK, actualLegendDepotServerException.getStatus());
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#LegendDepotServerException(String, Response.Status)}
   */
  @Test
  void testNewLegendDepotServerException4() {
    // Arrange and Act
    LegendDepotServerException actualLegendDepotServerException = new LegendDepotServerException("An error occurred",
        (Response.Status) null);

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertNull(actualLegendDepotServerException.getCause());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Response.Status.INTERNAL_SERVER_ERROR, actualLegendDepotServerException.getStatus());
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#LegendDepotServerException(String, Response.Status, Throwable)}
   */
  @Test
  void testNewLegendDepotServerException5() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    LegendDepotServerException actualLegendDepotServerException = new LegendDepotServerException("An error occurred",
        Response.Status.OK, cause);

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Response.Status.OK, actualLegendDepotServerException.getStatus());
    assertSame(cause, actualLegendDepotServerException.getCause());
  }

  /**
   * Method under test:
   * {@link LegendDepotServerException#LegendDepotServerException(String, Response.Status, Throwable)}
   */
  @Test
  void testNewLegendDepotServerException6() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    LegendDepotServerException actualLegendDepotServerException = new LegendDepotServerException("An error occurred",
        null, cause);

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Response.Status.INTERNAL_SERVER_ERROR, actualLegendDepotServerException.getStatus());
    assertSame(cause, actualLegendDepotServerException.getCause());
  }
}
