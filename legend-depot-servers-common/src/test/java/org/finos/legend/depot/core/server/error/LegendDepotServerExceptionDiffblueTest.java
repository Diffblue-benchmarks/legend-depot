package org.finos.legend.depot.core.server.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LegendDepotServerExceptionDiffblueTest {
  /**
   * Test {@link LegendDepotServerException#LegendDepotServerException(String)}.
   *
   * <p>Method under test: {@link LegendDepotServerException#LegendDepotServerException(String)}
   */
  @Test
  @DisplayName("Test new LegendDepotServerException(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotServerException.<init>(String)"})
  void testNewLegendDepotServerException() {
    // Arrange and Act
    LegendDepotServerException actualLegendDepotServerException =
        new LegendDepotServerException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertNull(actualLegendDepotServerException.getCause());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Status.INTERNAL_SERVER_ERROR, actualLegendDepotServerException.getStatus());
  }

  /**
   * Test {@link LegendDepotServerException#LegendDepotServerException(String, Throwable)}.
   *
   * <p>Method under test: {@link LegendDepotServerException#LegendDepotServerException(String,
   * Throwable)}
   */
  @Test
  @DisplayName("Test new LegendDepotServerException(String, Throwable)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotServerException.<init>(String, Throwable)"})
  void testNewLegendDepotServerException2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    LegendDepotServerException actualLegendDepotServerException =
        new LegendDepotServerException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Status.INTERNAL_SERVER_ERROR, actualLegendDepotServerException.getStatus());
    assertSame(cause, actualLegendDepotServerException.getCause());
  }

  /**
   * Test {@link LegendDepotServerException#LegendDepotServerException(String, Status)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Status is {@code INTERNAL_SERVER_ERROR}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#LegendDepotServerException(String,
   * Status)}
   */
  @Test
  @DisplayName(
      "Test new LegendDepotServerException(String, Status); when 'null'; then return Status is 'INTERNAL_SERVER_ERROR'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotServerException.<init>(String, Status)"})
  void testNewLegendDepotServerException_whenNull_thenReturnStatusIsInternalServerError() {
    // Arrange and Act
    LegendDepotServerException actualLegendDepotServerException =
        new LegendDepotServerException("An error occurred", (Status) null);

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertNull(actualLegendDepotServerException.getCause());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Status.INTERNAL_SERVER_ERROR, actualLegendDepotServerException.getStatus());
  }

  /**
   * Test {@link LegendDepotServerException#LegendDepotServerException(String, Status, Throwable)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Status is {@code INTERNAL_SERVER_ERROR}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#LegendDepotServerException(String,
   * Status, Throwable)}
   */
  @Test
  @DisplayName(
      "Test new LegendDepotServerException(String, Status, Throwable); when 'null'; then return Status is 'INTERNAL_SERVER_ERROR'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotServerException.<init>(String, Status, Throwable)"})
  void testNewLegendDepotServerException_whenNull_thenReturnStatusIsInternalServerError2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    LegendDepotServerException actualLegendDepotServerException =
        new LegendDepotServerException("An error occurred", null, cause);

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Status.INTERNAL_SERVER_ERROR, actualLegendDepotServerException.getStatus());
    assertSame(cause, actualLegendDepotServerException.getCause());
  }

  /**
   * Test {@link LegendDepotServerException#LegendDepotServerException(String, Status)}.
   *
   * <ul>
   *   <li>When {@code OK}.
   *   <li>Then return Status is {@code OK}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#LegendDepotServerException(String,
   * Status)}
   */
  @Test
  @DisplayName(
      "Test new LegendDepotServerException(String, Status); when 'OK'; then return Status is 'OK'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotServerException.<init>(String, Status)"})
  void testNewLegendDepotServerException_whenOk_thenReturnStatusIsOk() {
    // Arrange and Act
    LegendDepotServerException actualLegendDepotServerException =
        new LegendDepotServerException("An error occurred", Status.OK);

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertNull(actualLegendDepotServerException.getCause());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Status.OK, actualLegendDepotServerException.getStatus());
  }

  /**
   * Test {@link LegendDepotServerException#LegendDepotServerException(String, Status, Throwable)}.
   *
   * <ul>
   *   <li>When {@code OK}.
   *   <li>Then return Status is {@code OK}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#LegendDepotServerException(String,
   * Status, Throwable)}
   */
  @Test
  @DisplayName(
      "Test new LegendDepotServerException(String, Status, Throwable); when 'OK'; then return Status is 'OK'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotServerException.<init>(String, Status, Throwable)"})
  void testNewLegendDepotServerException_whenOk_thenReturnStatusIsOk2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    LegendDepotServerException actualLegendDepotServerException =
        new LegendDepotServerException("An error occurred", Status.OK, cause);

    // Assert
    assertEquals("An error occurred", actualLegendDepotServerException.getLocalizedMessage());
    assertEquals("An error occurred", actualLegendDepotServerException.getMessage());
    assertEquals(0, actualLegendDepotServerException.getSuppressed().length);
    assertEquals(Status.OK, actualLegendDepotServerException.getStatus());
    assertSame(cause, actualLegendDepotServerException.getCause());
  }

  /**
   * Test {@link LegendDepotServerException#getStatus()}.
   *
   * <p>Method under test: {@link LegendDepotServerException#getStatus()}
   */
  @Test
  @DisplayName("Test getStatus()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Status LegendDepotServerException.getStatus()"})
  void testGetStatus() {
    // Arrange, Act and Assert
    assertEquals(
        Status.INTERNAL_SERVER_ERROR,
        new LegendDepotServerException("An error occurred").getStatus());
  }

  /**
   * Test {@link LegendDepotServerException#validateNonNull(Object, String)} with {@code arg},
   * {@code message}.
   *
   * <p>Method under test: {@link LegendDepotServerException#validateNonNull(Object, String)}
   */
  @Test
  @DisplayName("Test validateNonNull(Object, String) with 'arg', 'message'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LegendDepotServerException.validateNonNull(Object, String)"})
  void testValidateNonNullWithArgMessage() {
    // Arrange, Act and Assert
    assertEquals("Arg", LegendDepotServerException.validateNonNull("Arg", "An error occurred"));
  }

  /**
   * Test {@link LegendDepotServerException#validateNonNull(Object, String, Status)} with {@code
   * arg}, {@code message}, {@code httpStatus}.
   *
   * <p>Method under test: {@link LegendDepotServerException#validateNonNull(Object, String,
   * Status)}
   */
  @Test
  @DisplayName("Test validateNonNull(Object, String, Status) with 'arg', 'message', 'httpStatus'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LegendDepotServerException.validateNonNull(Object, String, Status)"})
  void testValidateNonNullWithArgMessageHttpStatus() {
    // Arrange, Act and Assert
    assertEquals(
        "Arg", LegendDepotServerException.validateNonNull("Arg", "An error occurred", Status.OK));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, String)} with {@code arg},
   * {@code predicate}, {@code message}.
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, String)}
   */
  @Test
  @DisplayName("Test validate(Object, Predicate, String) with 'arg', 'predicate', 'message'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LegendDepotServerException.validate(Object, Predicate, String)"})
  void testValidateWithArgPredicateMessage() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any()))
        .thenThrow(new LegendDepotServerException("An error occurred"));

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, "An error occurred"));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, Function)} with {@code arg},
   * {@code predicate}, {@code messageFn}.
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, Function)}
   */
  @Test
  @DisplayName("Test validate(Object, Predicate, Function) with 'arg', 'predicate', 'messageFn'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LegendDepotServerException.validate(Object, Predicate, Function)"})
  void testValidateWithArgPredicateMessageFn() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any()))
        .thenThrow(new LegendDepotServerException("An error occurred"));

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, mock(Function.class)));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, Function)} with {@code arg},
   * {@code predicate}, {@code messageFn}.
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, Function)}
   */
  @Test
  @DisplayName("Test validate(Object, Predicate, Function) with 'arg', 'predicate', 'messageFn'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LegendDepotServerException.validate(Object, Predicate, Function)"})
  void testValidateWithArgPredicateMessageFn2() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    Function<Object, String> messageFn = mock(Function.class);
    when(messageFn.apply(Mockito.<Object>any()))
        .thenThrow(new LegendDepotServerException("An error occurred"));

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, messageFn));
    verify(messageFn).apply(isA(Object.class));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, Function, Status)} with
   * {@code arg}, {@code predicate}, {@code messageFn}, {@code httpStatus}.
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, Function,
   * Status)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, Function, Status) with 'arg', 'predicate', 'messageFn', 'httpStatus'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LegendDepotServerException.validate(Object, Predicate, Function, Status)"
  })
  void testValidateWithArgPredicateMessageFnHttpStatus() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any()))
        .thenThrow(new LegendDepotServerException("An error occurred"));

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () ->
            LegendDepotServerException.validate("Arg", predicate, mock(Function.class), Status.OK));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, Function, Status)} with
   * {@code arg}, {@code predicate}, {@code messageFn}, {@code httpStatus}.
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, Function,
   * Status)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, Function, Status) with 'arg', 'predicate', 'messageFn', 'httpStatus'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LegendDepotServerException.validate(Object, Predicate, Function, Status)"
  })
  void testValidateWithArgPredicateMessageFnHttpStatus2() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    Function<Object, String> messageFn = mock(Function.class);
    when(messageFn.apply(Mockito.<Object>any()))
        .thenThrow(new LegendDepotServerException("An error occurred"));

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, messageFn, Status.OK));
    verify(messageFn).apply(isA(Object.class));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, Function, Status)} with
   * {@code arg}, {@code predicate}, {@code messageFn}, {@code httpStatus}.
   *
   * <ul>
   *   <li>Given {@code Apply}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, Function,
   * Status)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, Function, Status) with 'arg', 'predicate', 'messageFn', 'httpStatus'; given 'Apply'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LegendDepotServerException.validate(Object, Predicate, Function, Status)"
  })
  void testValidateWithArgPredicateMessageFnHttpStatus_givenApply() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    Function<Object, String> messageFn = mock(Function.class);
    when(messageFn.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, messageFn, Status.OK));
    verify(messageFn).apply(isA(Object.class));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, Function, Status)} with
   * {@code arg}, {@code predicate}, {@code messageFn}, {@code httpStatus}.
   *
   * <ul>
   *   <li>Given {@code Apply}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, Function,
   * Status)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, Function, Status) with 'arg', 'predicate', 'messageFn', 'httpStatus'; given 'Apply'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LegendDepotServerException.validate(Object, Predicate, Function, Status)"
  })
  void testValidateWithArgPredicateMessageFnHttpStatus_givenApply_whenNull() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    Function<Object, String> messageFn = mock(Function.class);
    when(messageFn.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, messageFn, null));
    verify(messageFn).apply(isA(Object.class));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, Function, Status)} with
   * {@code arg}, {@code predicate}, {@code messageFn}, {@code httpStatus}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code Arg}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, Function,
   * Status)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, Function, Status) with 'arg', 'predicate', 'messageFn', 'httpStatus'; given 'true'; then return 'Arg'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LegendDepotServerException.validate(Object, Predicate, Function, Status)"
  })
  void testValidateWithArgPredicateMessageFnHttpStatus_givenTrue_thenReturnArg() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(true);

    // Act
    Object actualValidateResult =
        LegendDepotServerException.validate("Arg", predicate, mock(Function.class), Status.OK);

    // Assert
    verify(predicate).test(isA(Object.class));
    assertEquals("Arg", actualValidateResult);
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, Function)} with {@code arg},
   * {@code predicate}, {@code messageFn}.
   *
   * <ul>
   *   <li>Given {@code Apply}.
   *   <li>When {@link Function} {@link Function#apply(Object)} return {@code Apply}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, Function)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, Function) with 'arg', 'predicate', 'messageFn'; given 'Apply'; when Function apply(Object) return 'Apply'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LegendDepotServerException.validate(Object, Predicate, Function)"})
  void testValidateWithArgPredicateMessageFn_givenApply_whenFunctionApplyReturnApply() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    Function<Object, String> messageFn = mock(Function.class);
    when(messageFn.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, messageFn));
    verify(messageFn).apply(isA(Object.class));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, Function)} with {@code arg},
   * {@code predicate}, {@code messageFn}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code Arg}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, Function)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, Function) with 'arg', 'predicate', 'messageFn'; given 'true'; then return 'Arg'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LegendDepotServerException.validate(Object, Predicate, Function)"})
  void testValidateWithArgPredicateMessageFn_givenTrue_thenReturnArg() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(true);

    // Act
    Object actualValidateResult =
        LegendDepotServerException.validate("Arg", predicate, mock(Function.class));

    // Assert
    verify(predicate).test(isA(Object.class));
    assertEquals("Arg", actualValidateResult);
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, String, Status)} with {@code
   * arg}, {@code predicate}, {@code message}, {@code httpStatus}.
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, String,
   * Status)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, String, Status) with 'arg', 'predicate', 'message', 'httpStatus'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LegendDepotServerException.validate(Object, Predicate, String, Status)"
  })
  void testValidateWithArgPredicateMessageHttpStatus() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any()))
        .thenThrow(new LegendDepotServerException("An error occurred"));

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () ->
            LegendDepotServerException.validate("Arg", predicate, "An error occurred", Status.OK));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, String, Status)} with {@code
   * arg}, {@code predicate}, {@code message}, {@code httpStatus}.
   *
   * <ul>
   *   <li>Given {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, String,
   * Status)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, String, Status) with 'arg', 'predicate', 'message', 'httpStatus'; given 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LegendDepotServerException.validate(Object, Predicate, String, Status)"
  })
  void testValidateWithArgPredicateMessageHttpStatus_givenFalse() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () ->
            LegendDepotServerException.validate("Arg", predicate, "An error occurred", Status.OK));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, String, Status)} with {@code
   * arg}, {@code predicate}, {@code message}, {@code httpStatus}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, String,
   * Status)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, String, Status) with 'arg', 'predicate', 'message', 'httpStatus'; given 'false'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LegendDepotServerException.validate(Object, Predicate, String, Status)"
  })
  void testValidateWithArgPredicateMessageHttpStatus_givenFalse_whenNull() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, "An error occurred", null));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, String, Status)} with {@code
   * arg}, {@code predicate}, {@code message}, {@code httpStatus}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code Arg}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, String,
   * Status)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, String, Status) with 'arg', 'predicate', 'message', 'httpStatus'; given 'true'; then return 'Arg'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LegendDepotServerException.validate(Object, Predicate, String, Status)"
  })
  void testValidateWithArgPredicateMessageHttpStatus_givenTrue_thenReturnArg() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(true);

    // Act
    Object actualValidateResult =
        LegendDepotServerException.validate("Arg", predicate, "An error occurred", Status.OK);

    // Assert
    verify(predicate).test(isA(Object.class));
    assertEquals("Arg", actualValidateResult);
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, String)} with {@code arg},
   * {@code predicate}, {@code message}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>When {@link Predicate} {@link Predicate#test(Object)} return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, String)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, String) with 'arg', 'predicate', 'message'; given 'false'; when Predicate test(Object) return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LegendDepotServerException.validate(Object, Predicate, String)"})
  void testValidateWithArgPredicateMessage_givenFalse_whenPredicateTestReturnFalse() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(false);

    // Act and Assert
    assertThrows(
        LegendDepotServerException.class,
        () -> LegendDepotServerException.validate("Arg", predicate, "An error occurred"));
    verify(predicate).test(isA(Object.class));
  }

  /**
   * Test {@link LegendDepotServerException#validate(Object, Predicate, String)} with {@code arg},
   * {@code predicate}, {@code message}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code Arg}.
   * </ul>
   *
   * <p>Method under test: {@link LegendDepotServerException#validate(Object, Predicate, String)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, Predicate, String) with 'arg', 'predicate', 'message'; given 'true'; then return 'Arg'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LegendDepotServerException.validate(Object, Predicate, String)"})
  void testValidateWithArgPredicateMessage_givenTrue_thenReturnArg() {
    // Arrange
    Predicate<Object> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<Object>any())).thenReturn(true);

    // Act
    Object actualValidateResult =
        LegendDepotServerException.validate("Arg", predicate, "An error occurred");

    // Assert
    verify(predicate).test(isA(Object.class));
    assertEquals("Arg", actualValidateResult);
  }
}
