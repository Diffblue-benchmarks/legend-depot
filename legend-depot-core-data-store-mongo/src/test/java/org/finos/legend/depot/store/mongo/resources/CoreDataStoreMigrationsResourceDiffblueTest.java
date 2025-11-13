package org.finos.legend.depot.store.mongo.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.internal.MongoDatabaseImpl;
import java.security.Principal;
import java.util.Set;
import java.util.function.Consumer;
import javax.inject.Provider;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import org.bson.Document;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.store.mongo.admin.CoreDataMigrations;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CoreDataStoreMigrationsResourceDiffblueTest {
  /**
   * Test {@link CoreDataStoreMigrationsResource#addLatestVersionToProjectData()}.
   *
   * <ul>
   *   <li>Then StatusInfo return {@link Status}.
   * </ul>
   *
   * <p>Method under test: {@link CoreDataStoreMigrationsResource#addLatestVersionToProjectData()}
   */
  @Test
  @DisplayName("Test addLatestVersionToProjectData(); then StatusInfo return Status")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response CoreDataStoreMigrationsResource.addLatestVersionToProjectData()"})
  void testAddLatestVersionToProjectData_thenStatusInfoReturnStatus() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    CoreDataMigrations mongoMigrations = new CoreDataMigrations(mongoDatabase);

    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    CoreDataStoreMigrationsResource coreDataStoreMigrationsResource =
        new CoreDataStoreMigrationsResource(
            mongoMigrations, authorisationProvider, mock(Provider.class));

    // Act
    Response actualAddLatestVersionToProjectDataResult =
        coreDataStoreMigrationsResource.addLatestVersionToProjectData();

    // Assert
    verify(mongoCollection).find();
    verify(mongoDatabase, atLeast(1)).getCollection(Mockito.<String>any());
    verify(findIterable).forEach(isA(Consumer.class));
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Store Administration"));
    StatusType statusInfo = actualAddLatestVersionToProjectDataResult.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    assertTrue(actualAddLatestVersionToProjectDataResult instanceof OutboundJaxrsResponse);
    assertNull(actualAddLatestVersionToProjectDataResult.getEntity());
    assertNull(actualAddLatestVersionToProjectDataResult.getLocation());
    assertNull(actualAddLatestVersionToProjectDataResult.getDate());
    assertNull(actualAddLatestVersionToProjectDataResult.getLastModified());
    assertNull(actualAddLatestVersionToProjectDataResult.getLanguage());
    assertNull(actualAddLatestVersionToProjectDataResult.getEntityTag());
    assertNull(actualAddLatestVersionToProjectDataResult.getMediaType());
    assertEquals(-1, actualAddLatestVersionToProjectDataResult.getLength());
    assertEquals(204, actualAddLatestVersionToProjectDataResult.getStatus());
    assertEquals(Status.NO_CONTENT, statusInfo);
    assertTrue(actualAddLatestVersionToProjectDataResult.getCookies().isEmpty());
    MultivaluedMap<String, Object> headers = actualAddLatestVersionToProjectDataResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualAddLatestVersionToProjectDataResult.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualAddLatestVersionToProjectDataResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(allowedMethods, actualAddLatestVersionToProjectDataResult.getLinks());
    assertSame(headers, actualAddLatestVersionToProjectDataResult.getMetadata());
  }
}
