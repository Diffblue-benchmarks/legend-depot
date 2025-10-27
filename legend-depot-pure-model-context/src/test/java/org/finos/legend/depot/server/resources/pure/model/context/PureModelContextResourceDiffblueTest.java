package org.finos.legend.depot.server.resources.pure.model.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.services.api.pure.model.context.PureModelContextService;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.glassfish.jersey.message.internal.AcceptableMediaType;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PureModelContextResourceDiffblueTest {
  @InjectMocks
  private PureModelContextResource pureModelContextResource;

  @Mock
  private PureModelContextService pureModelContextService;

  /**
   * Method under test:
   * {@link PureModelContextResource#getPureModelContextData(String, String, String, String, boolean, boolean, Request)}
   */
  @Test
  void testGetPureModelContextData() throws MissingResourceException {
    // Arrange
    PureModelContextData newPureModelContextDataResult = PureModelContextData.newPureModelContextData();
    when(pureModelContextService.getPureModelContextData(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any(), anyBoolean(), anyBoolean()))
            .thenReturn(newPureModelContextDataResult);

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData("42", "42", "42", "1.0.2",
        true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(eq("42"), eq("42"), eq("42"), eq("1.0.2"), eq(true),
        eq(true));
    MultivaluedMap<String, Object> headers = actualPureModelContextData.getHeaders();
    assertEquals(2, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualPureModelContextData.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualPureModelContextData).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
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
    EntityTag entityTag = actualPureModelContextData.getEntityTag();
    assertEquals("4242421.0.2", entityTag.getValue());
    MultivaluedMap<String, String> stringHeaders = actualPureModelContextData.getStringHeaders();
    assertEquals(2, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("ETag");
    assertEquals(1, getResult5.size());
    assertEquals("\"4242421.0.2\"", getResult5.get(0));
    List<String> getResult6 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult6.size());
    assertEquals("no-transform, must-revalidate", getResult6.get(0));
    assertNull(actualPureModelContextData.getLocation());
    assertNull(context.getLocation());
    assertNull(actualPureModelContextData.getDate());
    assertNull(actualPureModelContextData.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualPureModelContextData.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualPureModelContextData.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualPureModelContextData.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    List<Object> getResult7 = headers.get("ETag");
    assertEquals(1, getResult7.size());
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualPureModelContextData.getStatus());
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
    Map<String, NewCookie> cookies = actualPureModelContextData.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualPureModelContextData.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isMustRevalidate());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<PureModelContextData> expectedEntityClass = PureModelContextData.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualPureModelContextData.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityTag, getResult7.get(0));
    assertSame(entityTag, context.getEntityTag());
    assertSame(entityClass, context.getEntityType());
    assertSame(newPureModelContextDataResult, actualPureModelContextData.getEntity());
    assertSame(newPureModelContextDataResult, context.getEntity());
    assertSame(headers, actualPureModelContextData.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link PureModelContextResource#getPureModelContextData(String, String, String, String, boolean, boolean, Request)}
   */
  @Test
  void testGetPureModelContextData2() throws MissingResourceException {
    // Arrange
    PureModelContextData newPureModelContextDataResult = PureModelContextData.newPureModelContextData();
    when(pureModelContextService.getPureModelContextData(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any(), anyBoolean(), anyBoolean()))
            .thenReturn(newPureModelContextDataResult);

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData("42", "42", "-SNAPSHOT",
        "1.0.2", true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(eq("42"), eq("42"), eq("-SNAPSHOT"), eq("1.0.2"), eq(true),
        eq(true));
    MultivaluedMap<String, Object> headers = actualPureModelContextData.getHeaders();
    assertEquals(1, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualPureModelContextData.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualPureModelContextData).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
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
    MultivaluedMap<String, String> stringHeaders = actualPureModelContextData.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult5.size());
    assertEquals("no-cache, no-store, no-transform", getResult5.get(0));
    assertNull(actualPureModelContextData.getLocation());
    assertNull(context.getLocation());
    assertNull(actualPureModelContextData.getDate());
    assertNull(actualPureModelContextData.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualPureModelContextData.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualPureModelContextData.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualPureModelContextData.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualPureModelContextData.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualPureModelContextData.getStatus());
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
    Map<String, NewCookie> cookies = actualPureModelContextData.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualPureModelContextData.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isNoCache());
    assertTrue(((CacheControl) getResult2).isNoStore());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<PureModelContextData> expectedEntityClass = PureModelContextData.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualPureModelContextData.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityClass, context.getEntityType());
    assertSame(newPureModelContextDataResult, actualPureModelContextData.getEntity());
    assertSame(newPureModelContextDataResult, context.getEntity());
    assertSame(headers, actualPureModelContextData.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link PureModelContextResource#getPureModelContextData(String, String, String, String, boolean, boolean, Request)}
   */
  @Test
  void testGetPureModelContextData3() throws MissingResourceException {
    // Arrange
    PureModelContextData newPureModelContextDataResult = PureModelContextData.newPureModelContextData();
    when(pureModelContextService.getPureModelContextData(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any(), anyBoolean(), anyBoolean()))
            .thenReturn(newPureModelContextDataResult);

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData("42", "42", "42", "vX_X_X",
        true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(eq("42"), eq("42"), eq("42"), eq("vX_X_X"), eq(true),
        eq(true));
    MultivaluedMap<String, Object> headers = actualPureModelContextData.getHeaders();
    assertEquals(1, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualPureModelContextData.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualPureModelContextData).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
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
    MultivaluedMap<String, String> stringHeaders = actualPureModelContextData.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult5.size());
    assertEquals("no-cache, no-store, no-transform", getResult5.get(0));
    assertNull(actualPureModelContextData.getLocation());
    assertNull(context.getLocation());
    assertNull(actualPureModelContextData.getDate());
    assertNull(actualPureModelContextData.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualPureModelContextData.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualPureModelContextData.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualPureModelContextData.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualPureModelContextData.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualPureModelContextData.getStatus());
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
    Map<String, NewCookie> cookies = actualPureModelContextData.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualPureModelContextData.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isNoCache());
    assertTrue(((CacheControl) getResult2).isNoStore());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<PureModelContextData> expectedEntityClass = PureModelContextData.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualPureModelContextData.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityClass, context.getEntityType());
    assertSame(newPureModelContextDataResult, actualPureModelContextData.getEntity());
    assertSame(newPureModelContextDataResult, context.getEntity());
    assertSame(headers, actualPureModelContextData.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link PureModelContextResource#getPureModelContextData(List, String, boolean, boolean, Request)}
   */
  @Test
  void testGetPureModelContextData4() throws MissingResourceException {
    // Arrange
    PureModelContextData newPureModelContextDataResult = PureModelContextData.newPureModelContextData();
    when(pureModelContextService.getPureModelContextData(Mockito.<List<ProjectVersion>>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(newPureModelContextDataResult);

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData(new ArrayList<>(), "1.0.2",
        true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(isA(List.class), eq("1.0.2"), eq(true), eq(true));
    MultivaluedMap<String, Object> headers = actualPureModelContextData.getHeaders();
    assertEquals(1, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualPureModelContextData.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualPureModelContextData).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
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
    MultivaluedMap<String, String> stringHeaders = actualPureModelContextData.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult5.size());
    assertEquals("no-cache, no-store, no-transform", getResult5.get(0));
    assertNull(actualPureModelContextData.getLocation());
    assertNull(context.getLocation());
    assertNull(actualPureModelContextData.getDate());
    assertNull(actualPureModelContextData.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualPureModelContextData.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualPureModelContextData.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualPureModelContextData.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualPureModelContextData.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualPureModelContextData.getStatus());
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
    Map<String, NewCookie> cookies = actualPureModelContextData.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualPureModelContextData.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isNoCache());
    assertTrue(((CacheControl) getResult2).isNoStore());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<PureModelContextData> expectedEntityClass = PureModelContextData.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualPureModelContextData.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityClass, context.getEntityType());
    assertSame(newPureModelContextDataResult, actualPureModelContextData.getEntity());
    assertSame(newPureModelContextDataResult, context.getEntity());
    assertSame(headers, actualPureModelContextData.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link PureModelContextResource#getPureModelContextData(List, String, boolean, boolean, Request)}
   */
  @Test
  void testGetPureModelContextData5() throws MissingResourceException {
    // Arrange
    PureModelContextData newPureModelContextDataResult = PureModelContextData.newPureModelContextData();
    when(pureModelContextService.getPureModelContextData(Mockito.<List<ProjectVersion>>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(newPureModelContextDataResult);

    ArrayList<ProjectVersion> projectDependencies = new ArrayList<>();
    projectDependencies.add(new ProjectVersion("42", "42", "42"));

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData(projectDependencies, "1.0.2",
        true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(isA(List.class), eq("1.0.2"), eq(true), eq(true));
    MultivaluedMap<String, Object> headers = actualPureModelContextData.getHeaders();
    assertEquals(1, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualPureModelContextData.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualPureModelContextData).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
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
    MultivaluedMap<String, String> stringHeaders = actualPureModelContextData.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult5.size());
    assertEquals("no-cache, no-store, no-transform", getResult5.get(0));
    assertNull(actualPureModelContextData.getLocation());
    assertNull(context.getLocation());
    assertNull(actualPureModelContextData.getDate());
    assertNull(actualPureModelContextData.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualPureModelContextData.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualPureModelContextData.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualPureModelContextData.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualPureModelContextData.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualPureModelContextData.getStatus());
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
    Map<String, NewCookie> cookies = actualPureModelContextData.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualPureModelContextData.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isNoCache());
    assertTrue(((CacheControl) getResult2).isNoStore());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<PureModelContextData> expectedEntityClass = PureModelContextData.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualPureModelContextData.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityClass, context.getEntityType());
    assertSame(newPureModelContextDataResult, actualPureModelContextData.getEntity());
    assertSame(newPureModelContextDataResult, context.getEntity());
    assertSame(headers, actualPureModelContextData.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link PureModelContextResource#getPureModelContextData(List, String, boolean, boolean, Request)}
   */
  @Test
  void testGetPureModelContextData6() throws MissingResourceException {
    // Arrange
    PureModelContextData newPureModelContextDataResult = PureModelContextData.newPureModelContextData();
    when(pureModelContextService.getPureModelContextData(Mockito.<List<ProjectVersion>>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(newPureModelContextDataResult);

    ArrayList<ProjectVersion> projectDependencies = new ArrayList<>();
    projectDependencies.add(new ProjectVersion("42", "42", "42"));
    projectDependencies.add(new ProjectVersion("42", "42", "42"));

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData(projectDependencies, "1.0.2",
        true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(isA(List.class), eq("1.0.2"), eq(true), eq(true));
    MultivaluedMap<String, Object> headers = actualPureModelContextData.getHeaders();
    assertEquals(1, headers.size());
    List<Object> getResult = headers.get("Cache-Control");
    assertEquals(1, getResult.size());
    Object getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof CacheControl);
    Response.StatusType statusInfo = actualPureModelContextData.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualPureModelContextData).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult3 = acceptableMediaTypes.get(0);
    assertTrue(getResult3 instanceof AcceptableMediaType);
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
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
    MultivaluedMap<String, String> stringHeaders = actualPureModelContextData.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    List<String> getResult5 = stringHeaders.get("Cache-Control");
    assertEquals(1, getResult5.size());
    assertEquals("no-cache, no-store, no-transform", getResult5.get(0));
    assertNull(actualPureModelContextData.getLocation());
    assertNull(context.getLocation());
    assertNull(actualPureModelContextData.getDate());
    assertNull(actualPureModelContextData.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualPureModelContextData.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualPureModelContextData.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualPureModelContextData.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, ((CacheControl) getResult2).getMaxAge());
    assertEquals(-1, ((CacheControl) getResult2).getSMaxAge());
    assertEquals(-1, actualPureModelContextData.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult3).getQuality());
    assertEquals(200, actualPureModelContextData.getStatus());
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
    Map<String, NewCookie> cookies = actualPureModelContextData.getCookies();
    assertTrue(cookies.isEmpty());
    Set<String> allowedMethods = actualPureModelContextData.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(((CacheControl) getResult2).isNoCache());
    assertTrue(((CacheControl) getResult2).isNoStore());
    assertTrue(((CacheControl) getResult2).isNoTransform());
    assertTrue(getResult3.isWildcardSubtype());
    assertTrue(getResult3.isWildcardType());
    assertTrue(context.hasEntity());
    Class<PureModelContextData> expectedEntityClass = PureModelContextData.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertEquals(stringHeaders, context.getStringHeaders());
    assertSame(allowedMethods, getResult4.getExtensionKeys());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult4.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualPureModelContextData.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityClass, context.getEntityType());
    assertSame(newPureModelContextDataResult, actualPureModelContextData.getEntity());
    assertSame(newPureModelContextDataResult, context.getEntity());
    assertSame(headers, actualPureModelContextData.getMetadata());
    assertSame(headers, context.getHeaders());
  }
}
