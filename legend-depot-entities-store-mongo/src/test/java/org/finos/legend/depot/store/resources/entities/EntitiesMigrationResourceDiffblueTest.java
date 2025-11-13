package org.finos.legend.depot.store.resources.entities;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.internal.MongoDatabaseImpl;
import java.security.Principal;
import javax.inject.Provider;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.store.mongo.admin.migrations.MongoEntitiesMigrations;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EntitiesMigrationResourceDiffblueTest {
  /**
   * Test {@link EntitiesMigrationResource#deleteVersionedEntities()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoCollection#deleteMany(Bson)}.
   * </ul>
   *
   * <p>Method under test: {@link EntitiesMigrationResource#deleteVersionedEntities()}
   */
  @Test
  @DisplayName("Test deleteVersionedEntities(); then calls deleteMany(Bson)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response EntitiesMigrationResource.deleteVersionedEntities()"})
  void testDeleteVersionedEntities_thenCallsDeleteMany() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.deleteMany(Mockito.<Bson>any())).thenReturn(null);

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    MongoEntitiesMigrations mongoMigrations = new MongoEntitiesMigrations(mongoDatabase);

    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    EntitiesMigrationResource entitiesMigrationResource =
        new EntitiesMigrationResource(mongoMigrations, authorisationProvider, mock(Provider.class));

    // Act
    Response actualDeleteVersionedEntitiesResult =
        entitiesMigrationResource.deleteVersionedEntities();

    // Assert
    verify(mongoCollection).deleteMany(isA(Bson.class));
    verify(mongoDatabase).getCollection("entities");
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Store Administration"));
    assertTrue(actualDeleteVersionedEntitiesResult instanceof OutboundJaxrsResponse);
    MultivaluedMap<String, Object> headers = actualDeleteVersionedEntitiesResult.getHeaders();
    assertSame(headers, actualDeleteVersionedEntitiesResult.getMetadata());
    assertSame(
        headers,
        ((OutboundJaxrsResponse) actualDeleteVersionedEntitiesResult).getContext().getHeaders());
  }

  /**
   * Test {@link EntitiesMigrationResource#deleteVersionedEntities()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoEntitiesMigrations#deleteVersionedEntities()}.
   * </ul>
   *
   * <p>Method under test: {@link EntitiesMigrationResource#deleteVersionedEntities()}
   */
  @Test
  @DisplayName("Test deleteVersionedEntities(); then calls deleteVersionedEntities()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response EntitiesMigrationResource.deleteVersionedEntities()"})
  void testDeleteVersionedEntities_thenCallsDeleteVersionedEntities() {
    // Arrange
    MongoEntitiesMigrations mongoMigrations = mock(MongoEntitiesMigrations.class);
    when(mongoMigrations.deleteVersionedEntities()).thenReturn(null);

    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    EntitiesMigrationResource entitiesMigrationResource =
        new EntitiesMigrationResource(mongoMigrations, authorisationProvider, mock(Provider.class));

    // Act
    Response actualDeleteVersionedEntitiesResult =
        entitiesMigrationResource.deleteVersionedEntities();

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Store Administration"));
    verify(mongoMigrations).deleteVersionedEntities();
    assertTrue(actualDeleteVersionedEntitiesResult instanceof OutboundJaxrsResponse);
    MultivaluedMap<String, Object> headers = actualDeleteVersionedEntitiesResult.getHeaders();
    assertSame(headers, actualDeleteVersionedEntitiesResult.getMetadata());
    assertSame(
        headers,
        ((OutboundJaxrsResponse) actualDeleteVersionedEntitiesResult).getContext().getHeaders());
  }
}
