package org.finos.legend.depot.core.services.tracing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import io.opentracing.Span;
import io.opentracing.Tracer;
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
   * <ul>
   *   <li>Given {@link TracerProvider} {@link TracerProvider#create(OpenTracingConfiguration)} return {@link Tracer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TracerFactory#configure(OpenTracingConfiguration)}
   */
  @Test
  @DisplayName("Test configure(OpenTracingConfiguration); given TracerProvider create(OpenTracingConfiguration) return Tracer")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"TracerFactory TracerFactory.configure(OpenTracingConfiguration)"})
  void testConfigure_givenTracerProviderCreateReturnTracer() {
    // Arrange
    TracerProvider tracerProvider = mock(TracerProvider.class);
    when(tracerProvider.create(Mockito.<OpenTracingConfiguration>any())).thenReturn(mock(Tracer.class));

    OpenTracingConfiguration openTracingConfiguration = new OpenTracingConfiguration();
    openTracingConfiguration.setEnabled(true);
    openTracingConfiguration.setOpenTracingUri("Open Tracing Uri");
    openTracingConfiguration.setServiceName("Service Name");
    openTracingConfiguration.setTracerProvider(tracerProvider);

    // Act
    TracerFactory.configure(openTracingConfiguration);

    // Assert
    verify(tracerProvider).create(isA(OpenTracingConfiguration.class));
  }

  /**
   * Test {@link TracerFactory#configure(OpenTracingConfiguration)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TracerFactory#configure(OpenTracingConfiguration)}
   */
  @Test
  @DisplayName("Test configure(OpenTracingConfiguration); then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"TracerFactory TracerFactory.configure(OpenTracingConfiguration)"})
  void testConfigure_thenThrowRuntimeException() {
    // Arrange
    TracerProvider tracerProvider = mock(TracerProvider.class);
    when(tracerProvider.create(Mockito.<OpenTracingConfiguration>any())).thenThrow(new RuntimeException("foo"));
    OpenTracingConfiguration openTracingConfiguration = mock(OpenTracingConfiguration.class);
    when(openTracingConfiguration.getTracerProvider()).thenReturn(tracerProvider);
    when(openTracingConfiguration.isEnabled()).thenReturn(true);
    doNothing().when(openTracingConfiguration).setEnabled(anyBoolean());
    doNothing().when(openTracingConfiguration).setOpenTracingUri(Mockito.<String>any());
    doNothing().when(openTracingConfiguration).setServiceName(Mockito.<String>any());
    doNothing().when(openTracingConfiguration).setTracerProvider(Mockito.<TracerProvider>any());
    openTracingConfiguration.setEnabled(true);
    openTracingConfiguration.setOpenTracingUri("Open Tracing Uri");
    openTracingConfiguration.setServiceName("Service Name");
    openTracingConfiguration.setTracerProvider(mock(TracerProvider.class));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> TracerFactory.configure(openTracingConfiguration));
    verify(openTracingConfiguration, atLeast(1)).getTracerProvider();
    verify(openTracingConfiguration).isEnabled();
    verify(openTracingConfiguration).setEnabled(eq(true));
    verify(openTracingConfiguration).setOpenTracingUri(eq("Open Tracing Uri"));
    verify(openTracingConfiguration).setServiceName(eq("Service Name"));
    verify(openTracingConfiguration).setTracerProvider(isA(TracerProvider.class));
    verify(tracerProvider).create(isA(OpenTracingConfiguration.class));
  }

  /**
   * Test {@link TracerFactory#addTags(Map, Span)} with {@code tags}, {@code span}.
   * <ul>
   *   <li>Given {@link Span}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.</li>
   *   <li>Then calls {@link Span#setTag(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TracerFactory#addTags(Map, Span)}
   */
  @Test
  @DisplayName("Test addTags(Map, Span) with 'tags', 'span'; given Span; when HashMap() '42' is '42'; then calls setTag(String, String)")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Span}.</li>
   *   <li>When {@link Span} {@link Span#setTag(String, String)} return {@link Span}.</li>
   *   <li>Then calls {@link Span#setTag(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TracerFactory#addTags(Map, Span)}
   */
  @Test
  @DisplayName("Test addTags(Map, Span) with 'tags', 'span'; given Span; when Span setTag(String, String) return Span; then calls setTag(String, String)")
  @Tag("MaintainedByDiffblue")
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
    verify(span).setTag(eq("foo"), eq("42"));
  }

  /**
   * Test {@link TracerFactory#executeWithTrace(String, Supplier, Map)} with {@code label}, {@code supplier}, {@code tags}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TracerFactory#executeWithTrace(String, Supplier, Map)}
   */
  @Test
  @DisplayName("Test executeWithTrace(String, Supplier, Map) with 'label', 'supplier', 'tags'; given 'foo'; when HashMap() 'foo' is 'foo'")
  @Tag("MaintainedByDiffblue")
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
   * Test {@link TracerFactory#executeWithTrace(String, Supplier, Map)} with {@code label}, {@code supplier}, {@code tags}.
   * <ul>
   *   <li>Given {@code Get}.</li>
   *   <li>Then return {@code Get}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TracerFactory#executeWithTrace(String, Supplier, Map)}
   */
  @Test
  @DisplayName("Test executeWithTrace(String, Supplier, Map) with 'label', 'supplier', 'tags'; given 'Get'; then return 'Get'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object TracerFactory.executeWithTrace(String, Supplier, Map)"})
  void testExecuteWithTraceWithLabelSupplierTags_givenGet_thenReturnGet() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Object actualExecuteWithTraceResult = getResult.executeWithTrace("Label", supplier, new HashMap<>());

    // Assert
    verify(supplier).get();
    assertEquals("Get", actualExecuteWithTraceResult);
  }

  /**
   * Test {@link TracerFactory#executeWithTrace(String, Supplier, Map)} with {@code label}, {@code supplier}, {@code tags}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TracerFactory#executeWithTrace(String, Supplier, Map)}
   */
  @Test
  @DisplayName("Test executeWithTrace(String, Supplier, Map) with 'label', 'supplier', 'tags'; then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object TracerFactory.executeWithTrace(String, Supplier, Map)"})
  void testExecuteWithTraceWithLabelSupplierTags_thenThrowRuntimeException() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> getResult.executeWithTrace("Label", supplier, new HashMap<>()));
    verify(supplier).get();
  }

  /**
   * Test {@link TracerFactory#executeWithTrace(String, Supplier)} with {@code label}, {@code supplier}.
   * <ul>
   *   <li>Given {@code Get}.</li>
   *   <li>Then return {@code Get}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TracerFactory#executeWithTrace(String, Supplier)}
   */
  @Test
  @DisplayName("Test executeWithTrace(String, Supplier) with 'label', 'supplier'; given 'Get'; then return 'Get'")
  @Tag("MaintainedByDiffblue")
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
   * Test {@link TracerFactory#executeWithTrace(String, Supplier)} with {@code label}, {@code supplier}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TracerFactory#executeWithTrace(String, Supplier)}
   */
  @Test
  @DisplayName("Test executeWithTrace(String, Supplier) with 'label', 'supplier'; then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object TracerFactory.executeWithTrace(String, Supplier)"})
  void testExecuteWithTraceWithLabelSupplier_thenThrowRuntimeException() {
    // Arrange
    TracerFactory getResult = TracerFactory.get();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> getResult.executeWithTrace("Label", supplier));
    verify(supplier).get();
  }
}
