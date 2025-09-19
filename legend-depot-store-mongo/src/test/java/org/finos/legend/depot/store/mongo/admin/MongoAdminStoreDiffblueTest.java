package org.finos.legend.depot.store.mongo.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
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
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.ClientSession;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import com.mongodb.operation.WriteOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MongoAdminStoreDiffblueTest {
  /**
   * Test {@link MongoAdminStore#MongoAdminStore(MongoDatabase)}.
   *
   * <p>Method under test: {@link MongoAdminStore#MongoAdminStore(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new MongoAdminStore(MongoDatabase)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MongoAdminStore.<init>(MongoDatabase)"})
  void testNewMongoAdminStore() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ReadConcern readConcern = new ReadConcern(ReadConcernLevel.LOCAL);

    MongoDatabaseImpl mongoDatabase =
        new MongoDatabaseImpl(
            "Name",
            codecRegistry,
            readPreference,
            writeConcern,
            true,
            true,
            readConcern,
            UuidRepresentation.UNSPECIFIED,
            mock(OperationExecutor.class));

    // Act
    MongoAdminStore actualMongoAdminStore = new MongoAdminStore(mongoDatabase);

    // Assert
    MongoDatabase mongoDatabase2 = actualMongoAdminStore.mongoDatabase;
    assertTrue(mongoDatabase2 instanceof MongoDatabaseImpl);
    assertEquals("Name", mongoDatabase2.getName());
    assertEquals("Name", actualMongoAdminStore.getName());
    assertSame(readConcern, mongoDatabase2.getReadConcern());
    assertSame(writeConcern, mongoDatabase2.getWriteConcern());
  }

  /**
   * Test {@link MongoAdminStore#deleteCollection(String)}.
   *
   * <ul>
   *   <li>Given {@link OperationExecutor} {@link OperationExecutor#execute(WriteOperation,
   *       ReadConcern, ClientSession)} return {@code null}.
   *   <li>Then calls {@link OperationExecutor#execute(WriteOperation, ReadConcern, ClientSession)}.
   * </ul>
   *
   * <p>Method under test: {@link MongoAdminStore#deleteCollection(String)}
   */
  @Test
  @DisplayName(
      "Test deleteCollection(String); given OperationExecutor execute(WriteOperation, ReadConcern, ClientSession) return 'null'; then calls execute(WriteOperation, ReadConcern, ClientSession)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MongoAdminStore.deleteCollection(String)"})
  void testDeleteCollection_givenOperationExecutorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    OperationExecutor executor = mock(OperationExecutor.class);
    when(executor.execute(
            Mockito.<WriteOperation<Void>>any(),
            Mockito.<ReadConcern>any(),
            Mockito.<ClientSession>any()))
        .thenReturn(null);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

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
    new MongoAdminStore(mongoDatabase).deleteCollection("42");

    // Assert
    verify(executor).execute(isA(WriteOperation.class), isA(ReadConcern.class), isNull());
  }

  /**
   * Test {@link MongoAdminStore#getAllCollections()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link MongoAdminStore#getAllCollections()}
   */
  @Test
  @DisplayName("Test getAllCollections(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MongoAdminStore.getAllCollections()"})
  void testGetAllCollections_thenReturnEmpty() {
    // Arrange
    ListCollectionsIterable<Document> listCollectionsIterable = mock(ListCollectionsIterable.class);
    doNothing().when(listCollectionsIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.listCollections()).thenReturn(listCollectionsIterable);

    // Act
    List<String> actualAllCollections = new MongoAdminStore(mongoDatabase).getAllCollections();

    // Assert
    verify(mongoDatabase).listCollections();
    verify(listCollectionsIterable).forEach(isA(Consumer.class));
    assertTrue(actualAllCollections.isEmpty());
  }

  /**
   * Test {@link MongoAdminStore#getAllIndexes()}.
   *
   * <ul>
   *   <li>Given {@link ListCollectionsIterable} {@link ListCollectionsIterable#forEach(Consumer)}
   *       does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link MongoAdminStore#getAllIndexes()}
   */
  @Test
  @DisplayName(
      "Test getAllIndexes(); given ListCollectionsIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map MongoAdminStore.getAllIndexes()"})
  void testGetAllIndexes_givenListCollectionsIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    ListCollectionsIterable<Document> listCollectionsIterable = mock(ListCollectionsIterable.class);
    doNothing().when(listCollectionsIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.listCollections()).thenReturn(listCollectionsIterable);

    // Act
    Map<String, List<Document>> actualAllIndexes =
        new MongoAdminStore(mongoDatabase).getAllIndexes();

    // Assert
    verify(mongoDatabase).listCollections();
    verify(listCollectionsIterable).forEach(isA(Consumer.class));
    assertTrue(actualAllIndexes.isEmpty());
  }

  /**
   * Test {@link MongoAdminStore#deleteIndex(String, String)}.
   *
   * <ul>
   *   <li>Given {@link OperationExecutor} {@link OperationExecutor#execute(WriteOperation,
   *       ReadConcern, ClientSession)} return {@code null}.
   *   <li>Then calls {@link OperationExecutor#execute(WriteOperation, ReadConcern, ClientSession)}.
   * </ul>
   *
   * <p>Method under test: {@link MongoAdminStore#deleteIndex(String, String)}
   */
  @Test
  @DisplayName(
      "Test deleteIndex(String, String); given OperationExecutor execute(WriteOperation, ReadConcern, ClientSession) return 'null'; then calls execute(WriteOperation, ReadConcern, ClientSession)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MongoAdminStore.deleteIndex(String, String)"})
  void testDeleteIndex_givenOperationExecutorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    OperationExecutor executor = mock(OperationExecutor.class);
    when(executor.execute(
            Mockito.<WriteOperation<Void>>any(),
            Mockito.<ReadConcern>any(),
            Mockito.<ClientSession>any()))
        .thenReturn(null);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

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
    new MongoAdminStore(mongoDatabase).deleteIndex("42", "Index Name");

    // Assert
    verify(executor).execute(isA(WriteOperation.class), isA(ReadConcern.class), isNull());
  }

  /**
   * Test {@link MongoAdminStore#createIndexes()}.
   *
   * <p>Method under test: {@link MongoAdminStore#createIndexes()}
   */
  @Test
  @DisplayName("Test createIndexes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MongoAdminStore.createIndexes()"})
  void testCreateIndexes() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

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
            mock(OperationExecutor.class));

    // Act and Assert
    assertTrue(new MongoAdminStore(mongoDatabase).createIndexes().isEmpty());
  }

  /**
   * Test {@link MongoAdminStore#runCommand(Document)}.
   *
   * <ul>
   *   <li>Given {@link MongoDatabaseImpl} {@link MongoDatabaseImpl#runCommand(Bson)} return {@link
   *       Document#Document()}.
   *   <li>Then return {@link Document#Document()}.
   * </ul>
   *
   * <p>Method under test: {@link MongoAdminStore#runCommand(Document)}
   */
  @Test
  @DisplayName(
      "Test runCommand(Document); given MongoDatabaseImpl runCommand(Bson) return Document(); then return Document()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Document MongoAdminStore.runCommand(Document)"})
  void testRunCommand_givenMongoDatabaseImplRunCommandReturnDocument_thenReturnDocument() {
    // Arrange
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    Document document = new Document();
    when(mongoDatabase.runCommand(Mockito.<Bson>any())).thenReturn(document);
    MongoAdminStore mongoAdminStore = new MongoAdminStore(mongoDatabase);

    // Act
    Document actualRunCommandResult = mongoAdminStore.runCommand(new Document());

    // Assert
    verify(mongoDatabase).runCommand(isA(Bson.class));
    assertSame(document, actualRunCommandResult);
  }

  /**
   * Test {@link MongoAdminStore#runPipeline(String, List)} with {@code collectionName}, {@code
   * pipeline}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link MongoAdminStore#runPipeline(String, List)}
   */
  @Test
  @DisplayName(
      "Test runPipeline(String, List) with 'collectionName', 'pipeline'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MongoAdminStore.runPipeline(String, List)"})
  void testRunPipelineWithCollectionNamePipeline_thenReturnEmpty() {
    // Arrange
    AggregateIterable<Document> aggregateIterable = mock(AggregateIterable.class);
    doNothing().when(aggregateIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.aggregate(Mockito.<List<Bson>>any())).thenReturn(aggregateIterable);

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    MongoAdminStore mongoAdminStore = new MongoAdminStore(mongoDatabase);

    // Act
    List<Document> actualRunPipelineResult =
        mongoAdminStore.runPipeline("Collection Name", new ArrayList<>());

    // Assert
    verify(mongoCollection).aggregate(isA(List.class));
    verify(mongoDatabase).getCollection("Collection Name");
    verify(aggregateIterable).forEach(isA(Consumer.class));
    assertTrue(actualRunPipelineResult.isEmpty());
  }

  /**
   * Test {@link MongoAdminStore#getName()}.
   *
   * <ul>
   *   <li>Given {@link WriteConcern#WriteConcern(int)} with w is one.
   *   <li>Then return {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link MongoAdminStore#getName()}
   */
  @Test
  @DisplayName("Test getName(); given WriteConcern(int) with w is one; then return 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MongoAdminStore.getName()"})
  void testGetName_givenWriteConcernWithWIsOne_thenReturnName() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

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
            mock(OperationExecutor.class));

    // Act and Assert
    assertEquals("Name", new MongoAdminStore(mongoDatabase).getName());
  }
}
