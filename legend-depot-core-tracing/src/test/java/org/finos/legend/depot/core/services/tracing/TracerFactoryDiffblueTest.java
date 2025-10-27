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
import io.opentracing.Span;
import io.opentracing.Tracer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.TracerProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TracerFactoryDiffblueTest {
  @Mock
  private Tracer tracer;

  @InjectMocks
  private TracerFactory tracerFactory;

  /**
   * Method under test: {@link TracerFactory#configure(OpenTracingConfiguration)}
   */
  @Test
  void testConfigure() {
    // Arrange
    TracerProvider tracerProvider = mock(TracerProvider.class);
    when(tracerProvider.create(Mockito.<OpenTracingConfiguration>any())).thenReturn(tracer);

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
   * Method under test: {@link TracerFactory#configure(OpenTracingConfiguration)}
   */
  @Test
  void testConfigure2() {
    // Arrange
    OpenTracingConfiguration openTracingConfiguration = mock(OpenTracingConfiguration.class);
    when(openTracingConfiguration.isEnabled()).thenReturn(false);
    doNothing().when(openTracingConfiguration).setEnabled(anyBoolean());
    doNothing().when(openTracingConfiguration).setOpenTracingUri(Mockito.<String>any());
    doNothing().when(openTracingConfiguration).setServiceName(Mockito.<String>any());
    doNothing().when(openTracingConfiguration).setTracerProvider(Mockito.<TracerProvider>any());
    openTracingConfiguration.setEnabled(true);
    openTracingConfiguration.setOpenTracingUri("Open Tracing Uri");
    openTracingConfiguration.setServiceName("Service Name");
    openTracingConfiguration.setTracerProvider(mock(TracerProvider.class));

    // Act
    TracerFactory.configure(openTracingConfiguration);

    // Assert
    verify(openTracingConfiguration).isEnabled();
    verify(openTracingConfiguration).setEnabled(eq(true));
    verify(openTracingConfiguration).setOpenTracingUri(eq("Open Tracing Uri"));
    verify(openTracingConfiguration).setServiceName(eq("Service Name"));
    verify(openTracingConfiguration).setTracerProvider(isA(TracerProvider.class));
  }

  /**
   * Method under test: {@link TracerFactory#addTags(Map, Span)}
   */
  @Test
  void testAddTags() {
    // Arrange
    HashMap<String, Object> tags = new HashMap<>();
    tags.put("foo", "42");
    Span span = mock(Span.class);
    when(span.setTag(Mockito.<String>any(), Mockito.<String>any())).thenReturn(mock(Span.class));

    // Act
    tracerFactory.addTags(tags, span);

    // Assert
    verify(span).setTag(eq("foo"), eq("42"));
  }

  /**
   * Method under test: {@link TracerFactory#addTags(Map, Span)}
   */
  @Test
  void testAddTags2() {
    // Arrange
    HashMap<String, Object> tags = new HashMap<>();
    tags.put("42", "42");
    tags.put("foo", "42");
    Span span = mock(Span.class);
    when(span.setTag(Mockito.<String>any(), Mockito.<String>any())).thenReturn(mock(Span.class));

    // Act
    tracerFactory.addTags(tags, span);

    // Assert
    verify(span, atLeast(1)).setTag(Mockito.<String>any(), eq("42"));
  }

  /**
   * Method under test: {@link TracerFactory#executeWithTrace(String, Supplier)}
   */
  @Test
  void testExecuteWithTrace() {
    // Arrange
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Object actualExecuteWithTraceResult = tracerFactory.executeWithTrace("Label", supplier);

    // Assert
    verify(supplier).get();
    assertEquals("Get", actualExecuteWithTraceResult);
  }

  /**
   * Method under test: {@link TracerFactory#executeWithTrace(String, Supplier)}
   */
  @Test
  void testExecuteWithTrace2() {
    // Arrange
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> tracerFactory.executeWithTrace("Label", supplier));
    verify(supplier).get();
  }

  /**
   * Method under test:
   * {@link TracerFactory#executeWithTrace(String, Supplier, Map)}
   */
  @Test
  void testExecuteWithTrace3() {
    // Arrange
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Object actualExecuteWithTraceResult = tracerFactory.executeWithTrace("Label", supplier, new HashMap<>());

    // Assert
    verify(supplier).get();
    assertEquals("Get", actualExecuteWithTraceResult);
  }

  /**
   * Method under test:
   * {@link TracerFactory#executeWithTrace(String, Supplier, Map)}
   */
  @Test
  void testExecuteWithTrace4() {
    // Arrange
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    HashMap<String, String> tags = new HashMap<>();
    tags.put("foo", "foo");

    // Act
    Object actualExecuteWithTraceResult = tracerFactory.executeWithTrace("Label", supplier, tags);

    // Assert
    verify(supplier).get();
    assertEquals("Get", actualExecuteWithTraceResult);
  }

  /**
   * Method under test:
   * {@link TracerFactory#executeWithTrace(String, Supplier, Map)}
   */
  @Test
  void testExecuteWithTrace5() {
    // Arrange
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> tracerFactory.executeWithTrace("Label", supplier, new HashMap<>()));
    verify(supplier).get();
  }
}
