package org.finos.legend.depot.core.services.tracing.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.glassfish.jersey.server.ContainerRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TracingResourceDiffblueTest {
  /**
   * Test {@link TracingResource#handle(String, Supplier)} with {@code label}, {@code supplier}.
   *
   * <p>Method under test: {@link TracingResource#handle(String, Supplier)}
   */
  @Test
  @DisplayName("Test handle(String, Supplier) with 'label', 'supplier'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TracingResource.handle(String, Supplier)"})
  void testHandleWithLabelSupplier() {
    // Arrange
    TracingResource tracingResource = new TracingResource();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Object actualHandleResult = tracingResource.handle("Label", supplier);

    // Assert
    verify(supplier).get();
    assertEquals("Get", actualHandleResult);
  }

  /**
   * Test {@link TracingResource#handle(String, Supplier, Request, Supplier)} with {@code label},
   * {@code supplier}, {@code request}, {@code entityTagSupplier}.
   *
   * <p>Method under test: {@link TracingResource#handle(String, Supplier, Request, Supplier)}
   */
  @Test
  @DisplayName(
      "Test handle(String, Supplier, Request, Supplier) with 'label', 'supplier', 'request', 'entityTagSupplier'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response TracingResource.handle(String, Supplier, Request, Supplier)"})
  void testHandleWithLabelSupplierRequestEntityTagSupplier() {
    // Arrange
    TracingResource tracingResource = new TracingResource();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    URI baseUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URI requestUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    ContainerRequest request =
        new ContainerRequest(
            baseUri, requestUri, "https://example.org/example", null, new MapPropertiesDelegate());

    Supplier<String> entityTagSupplier = mock(Supplier.class);
    when(entityTagSupplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResult =
        tracingResource.handle("Label", supplier, request, entityTagSupplier);

    // Assert
    verify(supplier).get();
    verify(entityTagSupplier).get();
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(2, headers.size());
    MultivaluedMap<String, String> stringHeaders = actualHandleResult.getStringHeaders();
    assertEquals(2, stringHeaders.size());
    assertTrue(headers.containsKey("Cache-Control"));
    assertTrue(headers.containsKey("ETag"));
    assertTrue(stringHeaders.containsKey("Cache-Control"));
    assertTrue(stringHeaders.containsKey("ETag"));
    assertSame(headers, actualHandleResult.getMetadata());
  }

  /**
   * Test {@link TracingResource#handle(String, Supplier, Request, Supplier)} with {@code label},
   * {@code supplier}, {@code request}, {@code entityTagSupplier}.
   *
   * <p>Method under test: {@link TracingResource#handle(String, Supplier, Request, Supplier)}
   */
  @Test
  @DisplayName(
      "Test handle(String, Supplier, Request, Supplier) with 'label', 'supplier', 'request', 'entityTagSupplier'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response TracingResource.handle(String, Supplier, Request, Supplier)"})
  void testHandleWithLabelSupplierRequestEntityTagSupplier2() {
    // Arrange
    TracingResource tracingResource = new TracingResource();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    Supplier<String> entityTagSupplier = mock(Supplier.class);
    when(entityTagSupplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResult =
        tracingResource.handle("Label", supplier, null, entityTagSupplier);

    // Assert
    verify(supplier).get();
    verify(entityTagSupplier).get();
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(2, headers.size());
    assertEquals(1, headers.get("ETag").size());
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResult).getContext();
    assertEquals(1, context.getAcceptableLanguages().size());
    assertTrue(headers.containsKey("Cache-Control"));
    assertSame(headers, actualHandleResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Test {@link TracingResource#handle(String, Supplier, Request, Supplier)} with {@code label},
   * {@code supplier}, {@code request}, {@code entityTagSupplier}.
   *
   * <ul>
   *   <li>Then return Headers size is one.
   * </ul>
   *
   * <p>Method under test: {@link TracingResource#handle(String, Supplier, Request, Supplier)}
   */
  @Test
  @DisplayName(
      "Test handle(String, Supplier, Request, Supplier) with 'label', 'supplier', 'request', 'entityTagSupplier'; then return Headers size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response TracingResource.handle(String, Supplier, Request, Supplier)"})
  void testHandleWithLabelSupplierRequestEntityTagSupplier_thenReturnHeadersSizeIsOne() {
    // Arrange
    TracingResource tracingResource = new TracingResource();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    URI baseUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URI requestUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    ContainerRequest request =
        new ContainerRequest(
            baseUri, requestUri, "https://example.org/example", null, new MapPropertiesDelegate());

    Supplier<String> entityTagSupplier = mock(Supplier.class);
    when(entityTagSupplier.get()).thenReturn(null);

    // Act
    Response actualHandleResult =
        tracingResource.handle("Label", supplier, request, entityTagSupplier);

    // Assert
    verify(supplier).get();
    verify(entityTagSupplier).get();
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(1, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    MultivaluedMap<String, String> stringHeaders = actualHandleResult.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    List<String> getResult3 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult3.size());
    assertEquals("no-cache, no-store, no-transform", getResult3.get(0));
    assertNull(actualHandleResult.getEntityTag());
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResult).getContext();
    assertNull(context.getEntityTag());
    MultivaluedMap<String, String> stringHeaders2 = context.getStringHeaders();
    assertEquals(1, stringHeaders2.size());
    assertFalse(((CacheControl) getResult2).isMustRevalidate());
    assertTrue(stringHeaders2.containsKey("Cache-Control"));
    assertTrue(((CacheControl) getResult2).isNoCache());
    assertTrue(((CacheControl) getResult2).isNoStore());
  }

  /**
   * Test {@link TracingResource#handle(String, String, Supplier)} with {@code
   * resourceAPIMetricName}, {@code label}, {@code supplier}.
   *
   * <p>Method under test: {@link TracingResource#handle(String, String, Supplier)}
   */
  @Test
  @DisplayName(
      "Test handle(String, String, Supplier) with 'resourceAPIMetricName', 'label', 'supplier'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TracingResource.handle(String, String, Supplier)"})
  void testHandleWithResourceAPIMetricNameLabelSupplier() {
    // Arrange
    TracingResource tracingResource = new TracingResource();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Object actualHandleResult =
        tracingResource.handle("Resource APIMetric Name", "Label", supplier);

    // Assert
    verify(supplier).get();
    assertEquals("Get", actualHandleResult);
  }

  /**
   * Test {@link TracingResource#handle(String, String, Supplier, Request, Supplier)} with {@code
   * resourceAPIMetricName}, {@code label}, {@code supplier}, {@code request}, {@code etagSupplier}.
   *
   * <p>Method under test: {@link TracingResource#handle(String, String, Supplier, Request,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test handle(String, String, Supplier, Request, Supplier) with 'resourceAPIMetricName', 'label', 'supplier', 'request', 'etagSupplier'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Response TracingResource.handle(String, String, Supplier, Request, Supplier)"
  })
  void testHandleWithResourceAPIMetricNameLabelSupplierRequestEtagSupplier() {
    // Arrange
    TracingResource tracingResource = new TracingResource();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    URI baseUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URI requestUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    ContainerRequest request =
        new ContainerRequest(
            baseUri, requestUri, "https://example.org/example", null, new MapPropertiesDelegate());

    Supplier<String> etagSupplier = mock(Supplier.class);
    when(etagSupplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResult =
        tracingResource.handle("Resource APIMetric Name", "Label", supplier, request, etagSupplier);

    // Assert
    verify(supplier).get();
    verify(etagSupplier).get();
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(2, headers.size());
    MultivaluedMap<String, String> stringHeaders = actualHandleResult.getStringHeaders();
    assertEquals(2, stringHeaders.size());
    assertTrue(headers.containsKey("Cache-Control"));
    assertTrue(headers.containsKey("ETag"));
    assertTrue(stringHeaders.containsKey("Cache-Control"));
    assertTrue(stringHeaders.containsKey("ETag"));
    assertSame(headers, actualHandleResult.getMetadata());
  }

  /**
   * Test {@link TracingResource#handle(String, String, Supplier, Request, Supplier)} with {@code
   * resourceAPIMetricName}, {@code label}, {@code supplier}, {@code request}, {@code etagSupplier}.
   *
   * <p>Method under test: {@link TracingResource#handle(String, String, Supplier, Request,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test handle(String, String, Supplier, Request, Supplier) with 'resourceAPIMetricName', 'label', 'supplier', 'request', 'etagSupplier'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Response TracingResource.handle(String, String, Supplier, Request, Supplier)"
  })
  void testHandleWithResourceAPIMetricNameLabelSupplierRequestEtagSupplier2() {
    // Arrange
    TracingResource tracingResource = new TracingResource();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    URI baseUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URI requestUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    ContainerRequest request =
        new ContainerRequest(
            baseUri, requestUri, "https://example.org/example", null, new MapPropertiesDelegate());

    Supplier<String> etagSupplier = mock(Supplier.class);
    when(etagSupplier.get()).thenReturn(null);

    // Act
    Response actualHandleResult =
        tracingResource.handle("Resource APIMetric Name", "Label", supplier, request, etagSupplier);

    // Assert
    verify(supplier).get();
    verify(etagSupplier).get();
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(1, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    MultivaluedMap<String, String> stringHeaders = actualHandleResult.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    List<String> getResult3 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult3.size());
    assertEquals("no-cache, no-store, no-transform", getResult3.get(0));
    assertNull(actualHandleResult.getEntityTag());
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResult).getContext();
    assertNull(context.getEntityTag());
    MultivaluedMap<String, String> stringHeaders2 = context.getStringHeaders();
    assertEquals(1, stringHeaders2.size());
    assertFalse(((CacheControl) getResult2).isMustRevalidate());
    assertTrue(stringHeaders2.containsKey("Cache-Control"));
    assertTrue(((CacheControl) getResult2).isNoCache());
    assertTrue(((CacheControl) getResult2).isNoStore());
  }

  /**
   * Test {@link TracingResource#handle(String, String, Supplier, Request, Supplier)} with {@code
   * resourceAPIMetricName}, {@code label}, {@code supplier}, {@code request}, {@code etagSupplier}.
   *
   * <p>Method under test: {@link TracingResource#handle(String, String, Supplier, Request,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test handle(String, String, Supplier, Request, Supplier) with 'resourceAPIMetricName', 'label', 'supplier', 'request', 'etagSupplier'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Response TracingResource.handle(String, String, Supplier, Request, Supplier)"
  })
  void testHandleWithResourceAPIMetricNameLabelSupplierRequestEtagSupplier3() {
    // Arrange
    TracingResource tracingResource = new TracingResource();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    Supplier<String> etagSupplier = mock(Supplier.class);
    when(etagSupplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResult =
        tracingResource.handle("Resource APIMetric Name", "Label", supplier, null, etagSupplier);

    // Assert
    verify(supplier).get();
    verify(etagSupplier).get();
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(2, headers.size());
    assertEquals(1, headers.get("ETag").size());
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResult).getContext();
    assertEquals(1, context.getAcceptableLanguages().size());
    assertTrue(headers.containsKey("Cache-Control"));
    assertSame(headers, actualHandleResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Test {@link TracingResource#handleResponse(String, Supplier)} with {@code label}, {@code
   * supplier}.
   *
   * <p>Method under test: {@link TracingResource#handleResponse(String, Supplier)}
   */
  @Test
  @DisplayName("Test handleResponse(String, Supplier) with 'label', 'supplier'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response TracingResource.handleResponse(String, Supplier)"})
  void testHandleResponseWithLabelSupplier() {
    // Arrange
    TracingResource tracingResource = new TracingResource();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResponseResult = tracingResource.handleResponse("Label", supplier);

    // Assert
    verify(supplier).get();
    StatusType statusInfo = actualHandleResponseResult.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    assertTrue(actualHandleResponseResult instanceof OutboundJaxrsResponse);
    assertEquals("Get", actualHandleResponseResult.getEntity());
    assertNull(actualHandleResponseResult.getLocation());
    assertNull(actualHandleResponseResult.getDate());
    assertNull(actualHandleResponseResult.getLastModified());
    assertNull(actualHandleResponseResult.getLanguage());
    assertNull(actualHandleResponseResult.getEntityTag());
    assertNull(actualHandleResponseResult.getMediaType());
    assertEquals(-1, actualHandleResponseResult.getLength());
    MultivaluedMap<String, Object> headers = actualHandleResponseResult.getHeaders();
    assertEquals(1, headers.size());
    MultivaluedMap<String, String> stringHeaders = actualHandleResponseResult.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    assertEquals(200, actualHandleResponseResult.getStatus());
    assertEquals(Status.OK, statusInfo);
    assertTrue(headers.containsKey("Cache-Control"));
    assertTrue(stringHeaders.containsKey("Cache-Control"));
    assertTrue(actualHandleResponseResult.getCookies().isEmpty());
    Set<String> allowedMethods = actualHandleResponseResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(allowedMethods, actualHandleResponseResult.getLinks());
    assertSame(headers, actualHandleResponseResult.getMetadata());
  }

  /**
   * Test {@link TracingResource#handleResponse(String, String, Supplier)} with {@code
   * resourceAPIMetricName}, {@code label}, {@code supplier}.
   *
   * <p>Method under test: {@link TracingResource#handleResponse(String, String, Supplier)}
   */
  @Test
  @DisplayName(
      "Test handleResponse(String, String, Supplier) with 'resourceAPIMetricName', 'label', 'supplier'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response TracingResource.handleResponse(String, String, Supplier)"})
  void testHandleResponseWithResourceAPIMetricNameLabelSupplier() {
    // Arrange
    TracingResource tracingResource = new TracingResource();

    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResponseResult =
        tracingResource.handleResponse("Resource APIMetric Name", "Label", supplier);

    // Assert
    verify(supplier).get();
    StatusType statusInfo = actualHandleResponseResult.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    assertTrue(actualHandleResponseResult instanceof OutboundJaxrsResponse);
    assertEquals("Get", actualHandleResponseResult.getEntity());
    assertNull(actualHandleResponseResult.getLocation());
    assertNull(actualHandleResponseResult.getDate());
    assertNull(actualHandleResponseResult.getLastModified());
    assertNull(actualHandleResponseResult.getLanguage());
    assertNull(actualHandleResponseResult.getEntityTag());
    assertNull(actualHandleResponseResult.getMediaType());
    assertEquals(-1, actualHandleResponseResult.getLength());
    MultivaluedMap<String, Object> headers = actualHandleResponseResult.getHeaders();
    assertEquals(1, headers.size());
    MultivaluedMap<String, String> stringHeaders = actualHandleResponseResult.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    assertEquals(200, actualHandleResponseResult.getStatus());
    assertEquals(Status.OK, statusInfo);
    assertTrue(headers.containsKey("Cache-Control"));
    assertTrue(stringHeaders.containsKey("Cache-Control"));
    assertTrue(actualHandleResponseResult.getCookies().isEmpty());
    Set<String> allowedMethods = actualHandleResponseResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(allowedMethods, actualHandleResponseResult.getLinks());
    assertSame(headers, actualHandleResponseResult.getMetadata());
  }
}
