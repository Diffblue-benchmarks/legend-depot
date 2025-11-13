package org.finos.legend.depot.store.mongo.admin.migrations;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import com.mongodb.operation.WriteOperation;
import java.util.List;
import java.util.function.Consumer;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.BooleanCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EntitiesMigrationDiffblueTest {
  /**
   * Test {@link EntitiesMigration#versionedEntitiesDeletion()}.
   *
   * <ul>
   *   <li>Then calls {@link BulkWriteResult#getDeletedCount()}.
   * </ul>
   *
   * <p>Method under test: {@link EntitiesMigration#versionedEntitiesDeletion()}
   */
  @Test
  @DisplayName("Test versionedEntitiesDeletion(); then calls getDeletedCount()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.mongodb.client.result.DeleteResult EntitiesMigration.versionedEntitiesDeletion()"
  })
  void testVersionedEntitiesDeletion_thenCallsGetDeletedCount() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<Boolean>>any())).thenReturn(new BooleanCodec());

    BulkWriteResult bulkWriteResult = mock(BulkWriteResult.class);
    when(bulkWriteResult.getDeletedCount()).thenReturn(3);
    when(bulkWriteResult.wasAcknowledged()).thenReturn(true);

    OperationExecutor executor = mock(OperationExecutor.class);
    when(executor.execute(
            Mockito.<WriteOperation<BulkWriteResult>>any(),
            Mockito.<ReadConcern>any(),
            Mockito.<ClientSession>any()))
        .thenReturn(bulkWriteResult);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern();

    MongoDatabaseImpl mongoDatabase =
        new MongoDatabaseImpl(
            "Name",
            codecRegistry,
            readPreference,
            writeConcern,
            true,
            true,
            new ReadConcern(ReadConcernLevel.LOCAL),
            UuidRepresentation.UNSPECIFIED,
            executor);

    // Act
    new EntitiesMigration(mongoDatabase).versionedEntitiesDeletion();

    // Assert
    verify(bulkWriteResult).getDeletedCount();
    verify(bulkWriteResult).wasAcknowledged();
    verify(executor).execute(isA(WriteOperation.class), isA(ReadConcern.class), isNull());
    verify(codecRegistry).get(isA(Class.class));
  }

  /**
   * Test {@link EntitiesMigration#entitiesToStoredEntityDataMigration()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoCollection#aggregate(List)}.
   * </ul>
   *
   * <p>Method under test: {@link EntitiesMigration#entitiesToStoredEntityDataMigration()}
   */
  @Test
  @DisplayName("Test entitiesToStoredEntityDataMigration(); then calls aggregate(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EntitiesMigration.entitiesToStoredEntityDataMigration()"})
  void testEntitiesToStoredEntityDataMigration_thenCallsAggregate() {
    // Arrange
    AggregateIterable<Document> aggregateIterable = mock(AggregateIterable.class);
    doNothing().when(aggregateIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.aggregate(Mockito.<List<Bson>>any())).thenReturn(aggregateIterable);

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    new EntitiesMigration(mongoDatabase).entitiesToStoredEntityDataMigration();

    // Assert
    verify(mongoCollection).aggregate(isA(List.class));
    verify(mongoDatabase, atLeast(1)).getCollection("entities");
    verify(aggregateIterable).forEach(isA(Consumer.class));
  }
}
