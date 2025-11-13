package org.finos.legend.depot.store.mongo.admin.migrations;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.result.DeleteResult;
import java.util.List;
import java.util.function.Consumer;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MongoEntitiesMigrationsDiffblueTest {
  /**
   * Test {@link MongoEntitiesMigrations#deleteVersionedEntities()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MongoEntitiesMigrations#deleteVersionedEntities()}
   */
  @Test
  @DisplayName("Test deleteVersionedEntities(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"DeleteResult MongoEntitiesMigrations.deleteVersionedEntities()"})
  void testDeleteVersionedEntities_thenReturnNull() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.deleteMany(Mockito.<Bson>any())).thenReturn(null);

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    DeleteResult actualDeleteVersionedEntitiesResult =
        new MongoEntitiesMigrations(mongoDatabase).deleteVersionedEntities();

    // Assert
    verify(mongoCollection).deleteMany(isA(Bson.class));
    verify(mongoDatabase).getCollection("entities");
    assertNull(actualDeleteVersionedEntitiesResult);
  }

  /**
   * Test {@link MongoEntitiesMigrations#migrateEntitiesToStoredEntityData()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoCollection#aggregate(List)}.
   * </ul>
   *
   * <p>Method under test: {@link MongoEntitiesMigrations#migrateEntitiesToStoredEntityData()}
   */
  @Test
  @DisplayName("Test migrateEntitiesToStoredEntityData(); then calls aggregate(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MongoEntitiesMigrations.migrateEntitiesToStoredEntityData()"})
  void testMigrateEntitiesToStoredEntityData_thenCallsAggregate() {
    // Arrange
    AggregateIterable<Document> aggregateIterable = mock(AggregateIterable.class);
    doNothing().when(aggregateIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.aggregate(Mockito.<List<Bson>>any())).thenReturn(aggregateIterable);

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    new MongoEntitiesMigrations(mongoDatabase).migrateEntitiesToStoredEntityData();

    // Assert
    verify(mongoCollection).aggregate(isA(List.class));
    verify(mongoDatabase, atLeast(1)).getCollection("entities");
    verify(aggregateIterable).forEach(isA(Consumer.class));
  }
}
