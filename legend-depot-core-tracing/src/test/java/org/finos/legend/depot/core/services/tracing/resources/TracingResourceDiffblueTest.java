package org.finos.legend.depot.core.services.tracing.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.function.Supplier;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.message.internal.AcceptableMediaType;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.glassfish.jersey.server.ContainerRequest;
import org.junit.jupiter.api.Test;

class TracingResourceDiffblueTest {
  /**
   * Method under test: {@link TracingResource#handle(String, String, Supplier)}
   */
  @Test
  void testHandle() {
    // Arrange
    TracingResource tracingResource = new TracingResource();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Object actualHandleResult = tracingResource.handle("Resource APIMetric Name", "Label", supplier);

    // Assert
    verify(supplier).get();
    assertEquals("Get", actualHandleResult);
  }

  /**
   * Method under test:
   * {@link TracingResource#handle(String, String, Supplier, Request, Supplier)}
   */
  @Test
  void testHandle2() throws MissingResourceException {
    // Arrange
    TracingResource tracingResource = new TracingResource();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    URI baseUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URI requestUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    ContainerRequest request = new ContainerRequest(baseUri, requestUri, "https://example.org/example", null,
        new MapPropertiesDelegate());

    Supplier<String> etagSupplier = mock(Supplier.class);
    when(etagSupplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResult = tracingResource.handle("Resource APIMetric Name", "Label", supplier, request,
        etagSupplier);

    // Assert
    verify(supplier).get();
    verify(etagSupplier).get();
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(2, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualHandleResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult4 = acceptableLanguages.get(0);
    assertEquals("", getResult4.getCountry());
    assertEquals("", getResult4.getDisplayCountry());
    assertEquals("", getResult4.getDisplayScript());
    assertEquals("", getResult4.getDisplayVariant());
    assertEquals("", getResult4.getISO3Country());
    assertEquals("", getResult4.getScript());
    assertEquals("", getResult4.getVariant());
    assertEquals("*", getResult4.getDisplayLanguage());
    assertEquals("*", getResult4.getDisplayName());
    assertEquals("*", getResult4.getLanguage());
    assertEquals("*", getResult3.getSubtype());
    assertEquals("*", getResult3.getType());
    EntityTag entityTag = actualHandleResult.getEntityTag();
    assertEquals("Get", entityTag.getValue());
    assertEquals("Get", actualHandleResult.getEntity());
    assertEquals("Get", context.getEntity());
    MultivaluedMap<String, String> stringHeaders = actualHandleResult.getStringHeaders();
    assertEquals(2, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("ETag");
    assertEquals(1, getResult5.size());
    assertEquals("\"Get\"", getResult5.get(0));
    List<String> getResult6 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult6.size());
    assertEquals("no-transform, must-revalidate", getResult6.get(0));
    assertNull(actualHandleResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualHandleResult.getDate());
    assertNull(actualHandleResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualHandleResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualHandleResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualHandleResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    List<Object> getResult7 = headers.get("ETag");
    assertEquals(1, getResult7.size());
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualHandleResult.getStatus());
    assertEquals(Response.Status.OK, statusInfo);
    assertFalse(getResult4.hasExtensions());
    assertFalse(((CacheControl) getResult2).isNoCache());
    assertFalse(((CacheControl) getResult2).isNoStore());
    assertFalse(((CacheControl) getResult2).isPrivate());
    assertFalse(((CacheControl) getResult2).isProxyRevalidate());
    assertFalse(entityTag.isWeak());
    assertFalse(context.isCommitted());
    assertTrue(((CacheControl) getResult2).getNoCacheFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getPrivateFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getCacheExtension().isEmpty());
    assertTrue(getResult3.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualHandleResult.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualHandleResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isMustRevalidate());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<String> expectedEntityClass = String.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualHandleResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityTag, getResult7.get(0));
    assertSame(entityTag, context.getEntityTag());
    assertSame(entityClass, context.getEntityType());
    assertSame(headers, actualHandleResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link TracingResource#handle(String, String, Supplier, Request, Supplier)}
   */
  @Test
  void testHandle3() throws MissingResourceException {
    // Arrange
    TracingResource tracingResource = new TracingResource();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    Supplier<String> etagSupplier = mock(Supplier.class);
    when(etagSupplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResult = tracingResource.handle("Resource APIMetric Name", "Label", supplier, null,
        etagSupplier);

    // Assert
    verify(supplier).get();
    verify(etagSupplier).get();
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(2, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualHandleResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult4 = acceptableLanguages.get(0);
    assertEquals("", getResult4.getCountry());
    assertEquals("", getResult4.getDisplayCountry());
    assertEquals("", getResult4.getDisplayScript());
    assertEquals("", getResult4.getDisplayVariant());
    assertEquals("", getResult4.getISO3Country());
    assertEquals("", getResult4.getScript());
    assertEquals("", getResult4.getVariant());
    assertEquals("*", getResult4.getDisplayLanguage());
    assertEquals("*", getResult4.getDisplayName());
    assertEquals("*", getResult4.getLanguage());
    assertEquals("*", getResult3.getSubtype());
    assertEquals("*", getResult3.getType());
    EntityTag entityTag = actualHandleResult.getEntityTag();
    assertEquals("Get", entityTag.getValue());
    assertEquals("Get", actualHandleResult.getEntity());
    assertEquals("Get", context.getEntity());
    MultivaluedMap<String, String> stringHeaders = actualHandleResult.getStringHeaders();
    assertEquals(2, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("ETag");
    assertEquals(1, getResult5.size());
    assertEquals("\"Get\"", getResult5.get(0));
    List<String> getResult6 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult6.size());
    assertEquals("no-transform, must-revalidate", getResult6.get(0));
    assertNull(actualHandleResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualHandleResult.getDate());
    assertNull(actualHandleResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualHandleResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualHandleResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualHandleResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    List<Object> getResult7 = headers.get("ETag");
    assertEquals(1, getResult7.size());
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualHandleResult.getStatus());
    assertEquals(Response.Status.OK, statusInfo);
    assertFalse(getResult4.hasExtensions());
    assertFalse(((CacheControl) getResult2).isNoCache());
    assertFalse(((CacheControl) getResult2).isNoStore());
    assertFalse(((CacheControl) getResult2).isPrivate());
    assertFalse(((CacheControl) getResult2).isProxyRevalidate());
    assertFalse(entityTag.isWeak());
    assertFalse(context.isCommitted());
    assertTrue(((CacheControl) getResult2).getNoCacheFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getPrivateFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getCacheExtension().isEmpty());
    assertTrue(getResult3.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualHandleResult.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualHandleResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isMustRevalidate());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<String> expectedEntityClass = String.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualHandleResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityTag, getResult7.get(0));
    assertSame(entityTag, context.getEntityTag());
    assertSame(entityClass, context.getEntityType());
    assertSame(headers, actualHandleResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link TracingResource#handle(String, String, Supplier, Request, Supplier)}
   */
  @Test
  void testHandle4() throws MissingResourceException {
    // Arrange
    TracingResource tracingResource = new TracingResource();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    URI baseUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URI requestUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    ContainerRequest request = new ContainerRequest(baseUri, requestUri, "https://example.org/example", null,
        new MapPropertiesDelegate());

    Supplier<String> etagSupplier = mock(Supplier.class);
    when(etagSupplier.get()).thenReturn(null);

    // Act
    Response actualHandleResult = tracingResource.handle("Resource APIMetric Name", "Label", supplier, request,
        etagSupplier);

    // Assert
    verify(supplier).get();
    verify(etagSupplier).get();
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(1, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualHandleResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult4 = acceptableLanguages.get(0);
    assertEquals("", getResult4.getCountry());
    assertEquals("", getResult4.getDisplayCountry());
    assertEquals("", getResult4.getDisplayScript());
    assertEquals("", getResult4.getDisplayVariant());
    assertEquals("", getResult4.getISO3Country());
    assertEquals("", getResult4.getScript());
    assertEquals("", getResult4.getVariant());
    assertEquals("*", getResult4.getDisplayLanguage());
    assertEquals("*", getResult4.getDisplayName());
    assertEquals("*", getResult4.getLanguage());
    assertEquals("*", getResult3.getSubtype());
    assertEquals("*", getResult3.getType());
    assertEquals("Get", actualHandleResult.getEntity());
    assertEquals("Get", context.getEntity());
    MultivaluedMap<String, String> stringHeaders = actualHandleResult.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult5.size());
    assertEquals("no-cache, no-store, no-transform", getResult5.get(0));
    assertNull(actualHandleResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualHandleResult.getDate());
    assertNull(actualHandleResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualHandleResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualHandleResult.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualHandleResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualHandleResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualHandleResult.getStatus());
    assertEquals(Response.Status.OK, statusInfo);
    assertFalse(getResult4.hasExtensions());
    assertFalse(((CacheControl) getResult2).isMustRevalidate());
    assertFalse(((CacheControl) getResult2).isPrivate());
    assertFalse(((CacheControl) getResult2).isProxyRevalidate());
    assertFalse(context.isCommitted());
    assertTrue(((CacheControl) getResult2).getNoCacheFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getPrivateFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getCacheExtension().isEmpty());
    assertTrue(getResult3.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualHandleResult.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualHandleResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isNoCache());
    assertTrue(((CacheControl) getResult2).isNoStore());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<String> expectedEntityClass = String.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualHandleResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityClass, context.getEntityType());
    assertSame(headers, actualHandleResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test: {@link TracingResource#handle(String, Supplier)}
   */
  @Test
  void testHandle5() {
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
   * Method under test:
   * {@link TracingResource#handle(String, Supplier, Request, Supplier)}
   */
  @Test
  void testHandle6() throws MissingResourceException {
    // Arrange
    TracingResource tracingResource = new TracingResource();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    URI baseUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URI requestUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    ContainerRequest request = new ContainerRequest(baseUri, requestUri, "https://example.org/example", null,
        new MapPropertiesDelegate());

    Supplier<String> entityTagSupplier = mock(Supplier.class);
    when(entityTagSupplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResult = tracingResource.handle("Label", supplier, request, entityTagSupplier);

    // Assert
    verify(supplier).get();
    verify(entityTagSupplier).get();
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(2, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualHandleResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult4 = acceptableLanguages.get(0);
    assertEquals("", getResult4.getCountry());
    assertEquals("", getResult4.getDisplayCountry());
    assertEquals("", getResult4.getDisplayScript());
    assertEquals("", getResult4.getDisplayVariant());
    assertEquals("", getResult4.getISO3Country());
    assertEquals("", getResult4.getScript());
    assertEquals("", getResult4.getVariant());
    assertEquals("*", getResult4.getDisplayLanguage());
    assertEquals("*", getResult4.getDisplayName());
    assertEquals("*", getResult4.getLanguage());
    assertEquals("*", getResult3.getSubtype());
    assertEquals("*", getResult3.getType());
    EntityTag entityTag = actualHandleResult.getEntityTag();
    assertEquals("Get", entityTag.getValue());
    assertEquals("Get", actualHandleResult.getEntity());
    assertEquals("Get", context.getEntity());
    MultivaluedMap<String, String> stringHeaders = actualHandleResult.getStringHeaders();
    assertEquals(2, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("ETag");
    assertEquals(1, getResult5.size());
    assertEquals("\"Get\"", getResult5.get(0));
    List<String> getResult6 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult6.size());
    assertEquals("no-transform, must-revalidate", getResult6.get(0));
    assertNull(actualHandleResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualHandleResult.getDate());
    assertNull(actualHandleResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualHandleResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualHandleResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualHandleResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    List<Object> getResult7 = headers.get("ETag");
    assertEquals(1, getResult7.size());
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualHandleResult.getStatus());
    assertEquals(Response.Status.OK, statusInfo);
    assertFalse(getResult4.hasExtensions());
    assertFalse(((CacheControl) getResult2).isNoCache());
    assertFalse(((CacheControl) getResult2).isNoStore());
    assertFalse(((CacheControl) getResult2).isPrivate());
    assertFalse(((CacheControl) getResult2).isProxyRevalidate());
    assertFalse(entityTag.isWeak());
    assertFalse(context.isCommitted());
    assertTrue(((CacheControl) getResult2).getNoCacheFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getPrivateFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getCacheExtension().isEmpty());
    assertTrue(getResult3.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualHandleResult.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualHandleResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isMustRevalidate());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<String> expectedEntityClass = String.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualHandleResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityTag, getResult7.get(0));
    assertSame(entityTag, context.getEntityTag());
    assertSame(entityClass, context.getEntityType());
    assertSame(headers, actualHandleResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link TracingResource#handle(String, Supplier, Request, Supplier)}
   */
  @Test
  void testHandle7() throws MissingResourceException {
    // Arrange
    TracingResource tracingResource = new TracingResource();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    Supplier<String> entityTagSupplier = mock(Supplier.class);
    when(entityTagSupplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResult = tracingResource.handle("Label", supplier, null, entityTagSupplier);

    // Assert
    verify(supplier).get();
    verify(entityTagSupplier).get();
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(2, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualHandleResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult4 = acceptableLanguages.get(0);
    assertEquals("", getResult4.getCountry());
    assertEquals("", getResult4.getDisplayCountry());
    assertEquals("", getResult4.getDisplayScript());
    assertEquals("", getResult4.getDisplayVariant());
    assertEquals("", getResult4.getISO3Country());
    assertEquals("", getResult4.getScript());
    assertEquals("", getResult4.getVariant());
    assertEquals("*", getResult4.getDisplayLanguage());
    assertEquals("*", getResult4.getDisplayName());
    assertEquals("*", getResult4.getLanguage());
    assertEquals("*", getResult3.getSubtype());
    assertEquals("*", getResult3.getType());
    EntityTag entityTag = actualHandleResult.getEntityTag();
    assertEquals("Get", entityTag.getValue());
    assertEquals("Get", actualHandleResult.getEntity());
    assertEquals("Get", context.getEntity());
    MultivaluedMap<String, String> stringHeaders = actualHandleResult.getStringHeaders();
    assertEquals(2, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("ETag");
    assertEquals(1, getResult5.size());
    assertEquals("\"Get\"", getResult5.get(0));
    List<String> getResult6 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult6.size());
    assertEquals("no-transform, must-revalidate", getResult6.get(0));
    assertNull(actualHandleResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualHandleResult.getDate());
    assertNull(actualHandleResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualHandleResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualHandleResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualHandleResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    List<Object> getResult7 = headers.get("ETag");
    assertEquals(1, getResult7.size());
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualHandleResult.getStatus());
    assertEquals(Response.Status.OK, statusInfo);
    assertFalse(getResult4.hasExtensions());
    assertFalse(((CacheControl) getResult2).isNoCache());
    assertFalse(((CacheControl) getResult2).isNoStore());
    assertFalse(((CacheControl) getResult2).isPrivate());
    assertFalse(((CacheControl) getResult2).isProxyRevalidate());
    assertFalse(entityTag.isWeak());
    assertFalse(context.isCommitted());
    assertTrue(((CacheControl) getResult2).getNoCacheFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getPrivateFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getCacheExtension().isEmpty());
    assertTrue(getResult3.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualHandleResult.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualHandleResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isMustRevalidate());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<String> expectedEntityClass = String.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualHandleResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityTag, getResult7.get(0));
    assertSame(entityTag, context.getEntityTag());
    assertSame(entityClass, context.getEntityType());
    assertSame(headers, actualHandleResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link TracingResource#handle(String, Supplier, Request, Supplier)}
   */
  @Test
  void testHandle8() throws MissingResourceException {
    // Arrange
    TracingResource tracingResource = new TracingResource();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");
    URI baseUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URI requestUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    ContainerRequest request = new ContainerRequest(baseUri, requestUri, "https://example.org/example", null,
        new MapPropertiesDelegate());

    Supplier<String> entityTagSupplier = mock(Supplier.class);
    when(entityTagSupplier.get()).thenReturn(null);

    // Act
    Response actualHandleResult = tracingResource.handle("Label", supplier, request, entityTagSupplier);

    // Assert
    verify(supplier).get();
    verify(entityTagSupplier).get();
    MultivaluedMap<String, Object> headers = actualHandleResult.getHeaders();
    assertEquals(1, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualHandleResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualHandleResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult4 = acceptableLanguages.get(0);
    assertEquals("", getResult4.getCountry());
    assertEquals("", getResult4.getDisplayCountry());
    assertEquals("", getResult4.getDisplayScript());
    assertEquals("", getResult4.getDisplayVariant());
    assertEquals("", getResult4.getISO3Country());
    assertEquals("", getResult4.getScript());
    assertEquals("", getResult4.getVariant());
    assertEquals("*", getResult4.getDisplayLanguage());
    assertEquals("*", getResult4.getDisplayName());
    assertEquals("*", getResult4.getLanguage());
    assertEquals("*", getResult3.getSubtype());
    assertEquals("*", getResult3.getType());
    assertEquals("Get", actualHandleResult.getEntity());
    assertEquals("Get", context.getEntity());
    MultivaluedMap<String, String> stringHeaders = actualHandleResult.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult5.size());
    assertEquals("no-cache, no-store, no-transform", getResult5.get(0));
    assertNull(actualHandleResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualHandleResult.getDate());
    assertNull(actualHandleResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualHandleResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualHandleResult.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualHandleResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualHandleResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualHandleResult.getStatus());
    assertEquals(Response.Status.OK, statusInfo);
    assertFalse(getResult4.hasExtensions());
    assertFalse(((CacheControl) getResult2).isMustRevalidate());
    assertFalse(((CacheControl) getResult2).isPrivate());
    assertFalse(((CacheControl) getResult2).isProxyRevalidate());
    assertFalse(context.isCommitted());
    assertTrue(((CacheControl) getResult2).getNoCacheFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getPrivateFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getCacheExtension().isEmpty());
    assertTrue(getResult3.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualHandleResult.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualHandleResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isNoCache());
    assertTrue(((CacheControl) getResult2).isNoStore());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<String> expectedEntityClass = String.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualHandleResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityClass, context.getEntityType());
    assertSame(headers, actualHandleResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link TracingResource#handleResponse(String, String, Supplier)}
   */
  @Test
  void testHandleResponse() throws MissingResourceException {
    // Arrange
    TracingResource tracingResource = new TracingResource();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResponseResult = tracingResource.handleResponse("Resource APIMetric Name", "Label", supplier);

    // Assert
    verify(supplier).get();
    MultivaluedMap<String, Object> headers = actualHandleResponseResult.getHeaders();
    assertEquals(1, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualHandleResponseResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResponseResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualHandleResponseResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult4 = acceptableLanguages.get(0);
    assertEquals("", getResult4.getCountry());
    assertEquals("", getResult4.getDisplayCountry());
    assertEquals("", getResult4.getDisplayScript());
    assertEquals("", getResult4.getDisplayVariant());
    assertEquals("", getResult4.getISO3Country());
    assertEquals("", getResult4.getScript());
    assertEquals("", getResult4.getVariant());
    assertEquals("*", getResult4.getDisplayLanguage());
    assertEquals("*", getResult4.getDisplayName());
    assertEquals("*", getResult4.getLanguage());
    assertEquals("*", getResult3.getSubtype());
    assertEquals("*", getResult3.getType());
    assertEquals("Get", actualHandleResponseResult.getEntity());
    assertEquals("Get", context.getEntity());
    MultivaluedMap<String, String> stringHeaders = actualHandleResponseResult.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult5.size());
    assertEquals("no-cache, no-store, no-transform", getResult5.get(0));
    assertNull(actualHandleResponseResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualHandleResponseResult.getDate());
    assertNull(actualHandleResponseResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualHandleResponseResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualHandleResponseResult.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualHandleResponseResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualHandleResponseResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualHandleResponseResult.getStatus());
    assertEquals(Response.Status.OK, statusInfo);
    assertFalse(getResult4.hasExtensions());
    assertFalse(((CacheControl) getResult2).isMustRevalidate());
    assertFalse(((CacheControl) getResult2).isPrivate());
    assertFalse(((CacheControl) getResult2).isProxyRevalidate());
    assertFalse(context.isCommitted());
    assertTrue(((CacheControl) getResult2).getNoCacheFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getPrivateFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getCacheExtension().isEmpty());
    assertTrue(getResult3.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualHandleResponseResult.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualHandleResponseResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isNoCache());
    assertTrue(((CacheControl) getResult2).isNoStore());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<String> expectedEntityClass = String.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualHandleResponseResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityClass, context.getEntityType());
    assertSame(headers, actualHandleResponseResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test: {@link TracingResource#handleResponse(String, Supplier)}
   */
  @Test
  void testHandleResponse2() throws MissingResourceException {
    // Arrange
    TracingResource tracingResource = new TracingResource();
    Supplier<Object> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Get");

    // Act
    Response actualHandleResponseResult = tracingResource.handleResponse("Label", supplier);

    // Assert
    verify(supplier).get();
    MultivaluedMap<String, Object> headers = actualHandleResponseResult.getHeaders();
    assertEquals(1, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualHandleResponseResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualHandleResponseResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualHandleResponseResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult4 = acceptableLanguages.get(0);
    assertEquals("", getResult4.getCountry());
    assertEquals("", getResult4.getDisplayCountry());
    assertEquals("", getResult4.getDisplayScript());
    assertEquals("", getResult4.getDisplayVariant());
    assertEquals("", getResult4.getISO3Country());
    assertEquals("", getResult4.getScript());
    assertEquals("", getResult4.getVariant());
    assertEquals("*", getResult4.getDisplayLanguage());
    assertEquals("*", getResult4.getDisplayName());
    assertEquals("*", getResult4.getLanguage());
    assertEquals("*", getResult3.getSubtype());
    assertEquals("*", getResult3.getType());
    assertEquals("Get", actualHandleResponseResult.getEntity());
    assertEquals("Get", context.getEntity());
    MultivaluedMap<String, String> stringHeaders = actualHandleResponseResult.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult5.size());
    assertEquals("no-cache, no-store, no-transform", getResult5.get(0));
    assertNull(actualHandleResponseResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualHandleResponseResult.getDate());
    assertNull(actualHandleResponseResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualHandleResponseResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualHandleResponseResult.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualHandleResponseResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualHandleResponseResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualHandleResponseResult.getStatus());
    assertEquals(Response.Status.OK, statusInfo);
    assertFalse(getResult4.hasExtensions());
    assertFalse(((CacheControl) getResult2).isMustRevalidate());
    assertFalse(((CacheControl) getResult2).isPrivate());
    assertFalse(((CacheControl) getResult2).isProxyRevalidate());
    assertFalse(context.isCommitted());
    assertTrue(((CacheControl) getResult2).getNoCacheFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getPrivateFields().isEmpty());
    assertTrue(((CacheControl) getResult2).getCacheExtension().isEmpty());
    assertTrue(getResult3.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualHandleResponseResult.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualHandleResponseResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isNoCache());
    assertTrue(((CacheControl) getResult2).isNoStore());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<String> expectedEntityClass = String.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualHandleResponseResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityClass, context.getEntityType());
    assertSame(headers, actualHandleResponseResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }
}
