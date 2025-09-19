package org.finos.legend.depot.core.services.tracing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import io.opentracing.Span;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.TracerProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TracerFactoryDiffblueTest {
  /**
   * Test {@link TracerFactory#configure(OpenTracingConfiguration)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link TracerFactory#configure(OpenTracingConfiguration)}
   */
  @Test
  @DisplayName("Test configure(OpenTracingConfiguration); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TracerFactory TracerFactory.configure(OpenTracingConfiguration)"})
  void testConfigure_thenThrowRuntimeException() {
    // Arrange
    TracerProvider tracerProvider = mock(TracerProvider.class);
    when(tracerProvider.create(Mockito.<OpenTracingConfiguration>any()))
        .thenThrow(new RuntimeException());

    OpenTracingConfiguration openTracingConfiguration = new OpenTracingConfiguration();
    openTracingConfiguration.setEnabled(true);
    openTracingConfiguration.setOpenTracingUri("Open Tracing Uri");
    openTracingConfiguration.setServiceName("Service Name");
    openTracingConfiguration.setTracerProvider(tracerProvider);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> TracerFactory.configure(openTracingConfiguration));
    verify(tracerProvider).create(isA(OpenTracingConfiguration.class));
  }

  /**
   * Test {@link TracerFactory#addTags(Map, Span)} with {@code tags}, {@code span}.
   *
   * <ul>
   *   <li>Given {@link Span}.
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.
   *   <li>Then calls {@link Span#setTag(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link TracerFactory#addTags(Map, Span)}
   */
  @Test
  @DisplayName(
      "Test addTags(Map, Span) with 'tags', 'span'; given Span; when HashMap() '42' is '42'; then calls setTag(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TracerFactory.addTags(Map, Span)"})
  void testAddTagsWithTagsSpan_givenSpan_whenHashMap42Is42_thenCallsSetTag() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();

    HashMap<String, Object> tags = new HashMap<>();
    tags.put("42", "42");
    tags.put("foo", "42");

    Span span = mock(Span.class);
    when(span.setTag(Mockito.<String>any(), Mockito.<String>any())).thenReturn(mock(Span.class));

    // Act
    getResult.addTags(tags, span);

    // Assert
    verify(span, atLeast(1)).setTag(Mockito.<String>any(), eq("42"));
  }

  /**
   * Test {@link TracerFactory#addTags(Map, Span)} with {@code tags}, {@code span}.
   *
   * <ul>
   *   <li>Given {@link Span}.
   *   <li>When {@link Span} {@link Span#setTag(String, String)} return {@link Span}.
   *   <li>Then calls {@link Span#setTag(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link TracerFactory#addTags(Map, Span)}
   */
  @Test
  @DisplayName(
      "Test addTags(Map, Span) with 'tags', 'span'; given Span; when Span setTag(String, String) return Span; then calls setTag(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TracerFactory.addTags(Map, Span)"})
  void testAddTagsWithTagsSpan_givenSpan_whenSpanSetTagReturnSpan_thenCallsSetTag() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();

    HashMap<String, Object> tags = new HashMap<>();
    tags.put("foo", "42");

    Span span = mock(Span.class);
    when(span.setTag(Mockito.<String>any(), Mockito.<String>any())).thenReturn(mock(Span.class));

    // Act
    getResult.addTags(tags, span);

    // Assert
    verify(span).setTag("foo", "42");
  }

  /**
   * Test {@link TracerFactory#executeWithTrace(String, Supplier, Map)} with {@code label}, {@code
   * supplier}, {@code tags}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TracerFactory#executeWithTrace(String, Supplier, Map)}
   */
  @Test
  @DisplayName(
      "Test executeWithTrace(String, Supplier, Map) with 'label', 'supplier', 'tags'; given '42'; when HashMap() '42' is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TracerFactory.executeWithTrace(String, Supplier, Map)"})
  void testExecuteWithTraceWithLabelSupplierTags_given42_whenHashMap42Is42() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    HashMap<String, String> tags = new HashMap<>();
    tags.put("42", "42");
    tags.put("foo", "foo");

    // Act
    Object actualExecuteWithTraceResult = getResult.executeWithTrace("Label", supplier, tags);

    // Assert
    verify(supplier).get();
    assertEquals("Get", actualExecuteWithTraceResult);
  }

  /**
   * Test {@link TracerFactory#executeWithTrace(String, Supplier, Map)} with {@code label}, {@code
   * supplier}, {@code tags}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link TracerFactory#executeWithTrace(String, Supplier, Map)}
   */
  @Test
  @DisplayName(
      "Test executeWithTrace(String, Supplier, Map) with 'label', 'supplier', 'tags'; given 'foo'; when HashMap() 'foo' is 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TracerFactory.executeWithTrace(String, Supplier, Map)"})
  void testExecuteWithTraceWithLabelSupplierTags_givenFoo_whenHashMapFooIsFoo() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    HashMap<String, String> tags = new HashMap<>();
    tags.put("foo", "foo");

    // Act
    Object actualExecuteWithTraceResult = getResult.executeWithTrace("Label", supplier, tags);

    // Assert
    verify(supplier).get();
    assertEquals("Get", actualExecuteWithTraceResult);
  }

  /**
   * Test {@link TracerFactory#executeWithTrace(String, Supplier, Map)} with {@code label}, {@code
   * supplier}, {@code tags}.
   *
   * <ul>
   *   <li>Given {@code Get}.
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return {@code Get}.
   * </ul>
   *
   * <p>Method under test: {@link TracerFactory#executeWithTrace(String, Supplier, Map)}
   */
  @Test
  @DisplayName(
      "Test executeWithTrace(String, Supplier, Map) with 'label', 'supplier', 'tags'; given 'Get'; when HashMap(); then return 'Get'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TracerFactory.executeWithTrace(String, Supplier, Map)"})
  void testExecuteWithTraceWithLabelSupplierTags_givenGet_whenHashMap_thenReturnGet() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Object actualExecuteWithTraceResult =
        getResult.executeWithTrace("Label", supplier, new HashMap<>());

    // Assert
    verify(supplier).get();
    assertEquals("Get", actualExecuteWithTraceResult);
  }

  /**
   * Test {@link TracerFactory#executeWithTrace(String, Supplier, Map)} with {@code label}, {@code
   * supplier}, {@code tags}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link TracerFactory#executeWithTrace(String, Supplier, Map)}
   */
  @Test
  @DisplayName(
      "Test executeWithTrace(String, Supplier, Map) with 'label', 'supplier', 'tags'; then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TracerFactory.executeWithTrace(String, Supplier, Map)"})
  void testExecuteWithTraceWithLabelSupplierTags_thenThrowRuntimeException() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> getResult.executeWithTrace("Label", supplier, new HashMap<>()));
    verify(supplier).get();
  }

  /**
   * Test {@link TracerFactory#executeWithTrace(String, Supplier)} with {@code label}, {@code
   * supplier}.
   *
   * <ul>
   *   <li>Given {@link TracerFactory#get()} addTags {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link TracerFactory#executeWithTrace(String, Supplier)}
   */
  @Test
  @DisplayName(
      "Test executeWithTrace(String, Supplier) with 'label', 'supplier'; given get() addTags HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TracerFactory.executeWithTrace(String, Supplier)"})
  void testExecuteWithTraceWithLabelSupplier_givenGetAddTagsHashMap() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();
    getResult.addTags(new HashMap<>());

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> getResult.executeWithTrace("Label", supplier));
    verify(supplier).get();
  }

  /**
   * Test {@link TracerFactory#executeWithTrace(String, Supplier)} with {@code label}, {@code
   * supplier}.
   *
   * <ul>
   *   <li>Given {@code Get}.
   *   <li>Then return {@code Get}.
   * </ul>
   *
   * <p>Method under test: {@link TracerFactory#executeWithTrace(String, Supplier)}
   */
  @Test
  @DisplayName(
      "Test executeWithTrace(String, Supplier) with 'label', 'supplier'; given 'Get'; then return 'Get'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TracerFactory.executeWithTrace(String, Supplier)"})
  void testExecuteWithTraceWithLabelSupplier_givenGet_thenReturnGet() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Object actualExecuteWithTraceResult = getResult.executeWithTrace("Label", supplier);

    // Assert
    verify(supplier).get();
    assertEquals("Get", actualExecuteWithTraceResult);
  }

  /**
   * Test {@link TracerFactory#executeWithTrace(String, Supplier)} with {@code label}, {@code
   * supplier}.
   *
   * <ul>
   *   <li>Given {@link TracerFactory#get()}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link TracerFactory#executeWithTrace(String, Supplier)}
   */
  @Test
  @DisplayName(
      "Test executeWithTrace(String, Supplier) with 'label', 'supplier'; given get(); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TracerFactory.executeWithTrace(String, Supplier)"})
  void testExecuteWithTraceWithLabelSupplier_givenGet_thenThrowRuntimeException() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> getResult.executeWithTrace("Label", supplier));
    verify(supplier).get();
  }
}
