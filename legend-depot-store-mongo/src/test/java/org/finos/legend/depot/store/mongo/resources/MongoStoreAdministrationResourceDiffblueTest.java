package org.finos.legend.depot.store.mongo.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import javax.inject.Provider;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.bson.Document;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler;
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
class MongoStoreAdministrationResourceDiffblueTest {
  @Mock
  private AuthorisationProvider authorisationProvider;

  @Mock
  private MongoAdminStore mongoAdminStore;

  @InjectMocks
  private MongoStoreAdministrationResource mongoStoreAdministrationResource;

  @Mock
  private Provider<Principal> provider;

  @Mock
  private StorageMetricsHandler storageMetricsHandler;

  /**
   * Method under test: {@link MongoStoreAdministrationResource#getIndexed()}
   */
  @Test
  void testGetIndexed() {
    // Arrange
    HashMap<String, List<Document>> stringListMap = new HashMap<>();
    when(mongoAdminStore.getAllIndexes()).thenReturn(stringListMap);

    // Act
    Map<String, List<Document>> actualIndexed = mongoStoreAdministrationResource.getIndexed();

    // Assert
    verify(mongoAdminStore).getAllIndexes();
    assertTrue(actualIndexed.isEmpty());
    assertSame(stringListMap, actualIndexed);
  }

  /**
   * Method under test:
   * {@link MongoStoreAdministrationResource#createIndexesIfAbsent()}
   */
  @Test
  void testCreateIndexesIfAbsent() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    when(mongoAdminStore.createIndexes()).thenReturn(stringList);
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    // Act
    List<String> actualCreateIndexesIfAbsentResult = mongoStoreAdministrationResource.createIndexesIfAbsent();

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Store Administration"));
    verify(mongoAdminStore).createIndexes();
    assertTrue(actualCreateIndexesIfAbsentResult.isEmpty());
    assertSame(stringList, actualCreateIndexesIfAbsentResult);
  }

  /**
   * Method under test:
   * {@link MongoStoreAdministrationResource#remove(String, String)}
   */
  @Test
  void testRemove() {
    // Arrange
    doNothing().when(mongoAdminStore).deleteIndex(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    // Act
    boolean actualRemoveResult = mongoStoreAdministrationResource.remove("Index", "Collection");

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Store Administration"));
    verify(mongoAdminStore).deleteIndex(eq("Collection"), eq("Index"));
    assertTrue(actualRemoveResult);
  }

  /**
   * Method under test:
   * {@link MongoStoreAdministrationResource#getCollectionStats()}
   */
  @Test
  void testGetCollectionStats() {
    // Arrange
    when(storageMetricsHandler.reportMetrics()).thenReturn("Report Metrics");

    // Act
    Object actualCollectionStats = mongoStoreAdministrationResource.getCollectionStats();

    // Assert
    verify(storageMetricsHandler).reportMetrics();
    assertEquals("Report Metrics", actualCollectionStats);
  }

  /**
   * Method under test: {@link MongoStoreAdministrationResource#getCollections()}
   */
  @Test
  void testGetCollections() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    when(mongoAdminStore.getAllCollections()).thenReturn(stringList);
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    // Act
    List<String> actualCollections = mongoStoreAdministrationResource.getCollections();

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Store Administration"));
    verify(mongoAdminStore).getAllCollections();
    assertTrue(actualCollections.isEmpty());
    assertSame(stringList, actualCollections);
  }

  /**
   * Method under test:
   * {@link MongoStoreAdministrationResource#deleteCollections(String)}
   */
  @Test
  void testDeleteCollections() throws MissingResourceException {
    // Arrange
    doNothing().when(mongoAdminStore).deleteCollection(Mockito.<String>any());
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    // Act
    Response actualDeleteCollectionsResult = mongoStoreAdministrationResource.deleteCollections("42");

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Store Administration"));
    verify(mongoAdminStore).deleteCollection(eq("42"));
    Response.StatusType statusInfo = actualDeleteCollectionsResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualDeleteCollectionsResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult = acceptableMediaTypes.get(0);
    assertTrue(getResult instanceof AcceptableMediaType);
    assertTrue(actualDeleteCollectionsResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult2 = acceptableLanguages.get(0);
    assertEquals("", getResult2.getCountry());
    assertEquals("", getResult2.getDisplayCountry());
    assertEquals("", getResult2.getDisplayScript());
    assertEquals("", getResult2.getDisplayVariant());
    assertEquals("", getResult2.getISO3Country());
    assertEquals("", getResult2.getScript());
    assertEquals("", getResult2.getVariant());
    assertEquals("*", getResult2.getDisplayLanguage());
    assertEquals("*", getResult2.getDisplayName());
    assertEquals("*", getResult2.getLanguage());
    assertEquals("*", getResult.getSubtype());
    assertEquals("*", getResult.getType());
    assertNull(context.getEntityClass());
    assertNull(actualDeleteCollectionsResult.getEntity());
    assertNull(context.getEntity());
    assertNull(context.getEntityType());
    assertNull(actualDeleteCollectionsResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualDeleteCollectionsResult.getDate());
    assertNull(actualDeleteCollectionsResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualDeleteCollectionsResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualDeleteCollectionsResult.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualDeleteCollectionsResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, actualDeleteCollectionsResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult).getQuality());
    assertEquals(204, actualDeleteCollectionsResult.getStatus());
    assertEquals(Response.Status.NO_CONTENT, statusInfo);
    assertFalse(getResult2.hasExtensions());
    assertFalse(context.hasEntity());
    assertFalse(context.isCommitted());
    assertTrue(getResult.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualDeleteCollectionsResult.getCookies();
    assertTrue(cookies.isEmpty());
    MultivaluedMap<String, Object> headers = actualDeleteCollectionsResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualDeleteCollectionsResult.getStringHeaders().isEmpty());
    assertTrue(context.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualDeleteCollectionsResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(getResult.isWildcardSubtype());
    assertTrue(getResult.isWildcardType());
    assertSame(allowedMethods, getResult2.getExtensionKeys());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualDeleteCollectionsResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(headers, actualDeleteCollectionsResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }

  /**
   * Method under test:
   * {@link MongoStoreAdministrationResource#runPipeline(String, String)}
   */
  @Test
  void testRunPipeline() throws JsonProcessingException, MissingResourceException {
    // Arrange
    ArrayList<Document> documentList = new ArrayList<>();
    when(mongoAdminStore.runPipeline(Mockito.<String>any(), Mockito.<String>any())).thenReturn(documentList);
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    // Act
    Response actualRunPipelineResult = mongoStoreAdministrationResource.runPipeline("Collection Mame", "Json Pipeline");

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Store Administration"));
    verify(mongoAdminStore).runPipeline(eq("Collection Mame"), eq("Json Pipeline"));
    Object entity = actualRunPipelineResult.getEntity();
    assertTrue(entity instanceof List);
    Response.StatusType statusInfo = actualRunPipelineResult.getStatusInfo();
    assertTrue(statusInfo instanceof Response.Status);
    OutboundMessageContext context = ((OutboundJaxrsResponse) actualRunPipelineResult).getContext();
    List<MediaType> acceptableMediaTypes = context.getAcceptableMediaTypes();
    assertEquals(1, acceptableMediaTypes.size());
    MediaType getResult = acceptableMediaTypes.get(0);
    assertTrue(getResult instanceof AcceptableMediaType);
    assertTrue(actualRunPipelineResult instanceof OutboundJaxrsResponse);
    List<Locale> acceptableLanguages = context.getAcceptableLanguages();
    assertEquals(1, acceptableLanguages.size());
    Locale getResult2 = acceptableLanguages.get(0);
    assertEquals("", getResult2.getCountry());
    assertEquals("", getResult2.getDisplayCountry());
    assertEquals("", getResult2.getDisplayScript());
    assertEquals("", getResult2.getDisplayVariant());
    assertEquals("", getResult2.getISO3Country());
    assertEquals("", getResult2.getScript());
    assertEquals("", getResult2.getVariant());
    assertEquals("*", getResult2.getDisplayLanguage());
    assertEquals("*", getResult2.getDisplayName());
    assertEquals("*", getResult2.getLanguage());
    assertEquals("*", getResult.getSubtype());
    assertEquals("*", getResult.getType());
    assertNull(actualRunPipelineResult.getLocation());
    assertNull(context.getLocation());
    assertNull(actualRunPipelineResult.getDate());
    assertNull(actualRunPipelineResult.getLastModified());
    assertNull(context.getDate());
    assertNull(context.getLastModified());
    assertNull(actualRunPipelineResult.getLanguage());
    assertNull(context.getLanguage());
    assertNull(actualRunPipelineResult.getEntityTag());
    assertNull(context.getEntityTag());
    assertNull(actualRunPipelineResult.getMediaType());
    assertNull(context.getMediaType());
    assertEquals(-1, actualRunPipelineResult.getLength());
    assertEquals(-1, context.getLength());
    assertEquals(-1L, context.getLengthLong());
    assertEquals(0, context.getEntityAnnotations().length);
    assertEquals(1000, ((AcceptableMediaType) getResult).getQuality());
    assertEquals(200, actualRunPipelineResult.getStatus());
    assertEquals(Response.Status.OK, statusInfo);
    assertFalse(getResult2.hasExtensions());
    assertFalse(context.isCommitted());
    assertTrue(((List<Object>) entity).isEmpty());
    assertTrue(getResult.getParameters().isEmpty());
    Map<String, NewCookie> cookies = actualRunPipelineResult.getCookies();
    assertTrue(cookies.isEmpty());
    MultivaluedMap<String, Object> headers = actualRunPipelineResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualRunPipelineResult.getStringHeaders().isEmpty());
    assertTrue(context.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualRunPipelineResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertTrue(getResult.isWildcardSubtype());
    assertTrue(getResult.isWildcardType());
    assertTrue(context.hasEntity());
    Class<ArrayList> expectedEntityClass = ArrayList.class;
    Class<?> entityClass = context.getEntityClass();
    assertEquals(expectedEntityClass, entityClass);
    assertSame(documentList, entity);
    assertSame(documentList, context.getEntity());
    assertSame(allowedMethods, getResult2.getExtensionKeys());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleAttributes());
    assertSame(allowedMethods, getResult2.getUnicodeLocaleKeys());
    assertSame(allowedMethods, actualRunPipelineResult.getLinks());
    assertSame(allowedMethods, context.getAllowedMethods());
    assertSame(allowedMethods, context.getLinks());
    assertSame(cookies, context.getRequestCookies());
    assertSame(cookies, context.getResponseCookies());
    assertSame(entityClass, context.getEntityType());
    assertSame(headers, actualRunPipelineResult.getMetadata());
    assertSame(headers, context.getHeaders());
  }
}
