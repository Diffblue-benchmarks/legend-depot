package org.finos.legend.depot.store.mongo.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
@Disabled("Failed pitest")
class MongoAdminStoreDiffblueTest {
  /**
   * Test {@link MongoAdminStore#MongoAdminStore(MongoDatabase)}.
   * <p>
   * Method under test: {@link MongoAdminStore#MongoAdminStore(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new MongoAdminStore(MongoDatabase)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MongoAdminStore.<init>(MongoDatabase)"})
  void testNewMongoAdminStore() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ReadConcern readConcern = new ReadConcern(ReadConcernLevel.LOCAL);

    // Act
    MongoAdminStore actualMongoAdminStore = new MongoAdminStore(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true, readConcern,
            UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Assert
    MongoDatabase mongoDatabase = actualMongoAdminStore.mongoDatabase;
    assertTrue(mongoDatabase instanceof MongoDatabaseImpl);
    assertEquals("Name", mongoDatabase.getName());
    assertEquals("Name", actualMongoAdminStore.getName());
    assertSame(readConcern, mongoDatabase.getReadConcern());
    assertSame(writeConcern, mongoDatabase.getWriteConcern());
  }

  /**
   * Test {@link MongoAdminStore#deleteCollection(String)}.
   * <ul>
   *   <li>Given {@link OperationExecutor} {@link OperationExecutor#execute(WriteOperation, ReadConcern, ClientSession)} return {@code null}.</li>
   *   <li>Then calls {@link OperationExecutor#execute(WriteOperation, ReadConcern, ClientSession)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoAdminStore#deleteCollection(String)}
   */
  @Test
  @DisplayName("Test deleteCollection(String); given OperationExecutor execute(WriteOperation, ReadConcern, ClientSession) return 'null'; then calls execute(WriteOperation, ReadConcern, ClientSession)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MongoAdminStore.deleteCollection(String)"})
  void testDeleteCollection_givenOperationExecutorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    OperationExecutor executor = mock(OperationExecutor.class);
    when(
        executor.execute(Mockito.<WriteOperation<Void>>any(), Mockito.<ReadConcern>any(), Mockito.<ClientSession>any()))
            .thenReturn(null);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    new MongoAdminStore(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
        new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, executor)).deleteCollection("42");

    // Assert
    verify(executor).execute(isA(WriteOperation.class), isA(ReadConcern.class), isNull());
  }

  /**
   * Test {@link MongoAdminStore#getAllCollections()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoAdminStore#getAllCollections()}
   */
  @Test
  @DisplayName("Test getAllCollections(); then return Empty")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link ListCollectionsIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoAdminStore#getAllIndexes()}
   */
  @Test
  @DisplayName("Test getAllIndexes(); given ListCollectionsIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map MongoAdminStore.getAllIndexes()"})
  void testGetAllIndexes_givenListCollectionsIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    ListCollectionsIterable<Document> listCollectionsIterable = mock(ListCollectionsIterable.class);
    doNothing().when(listCollectionsIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.listCollections()).thenReturn(listCollectionsIterable);

    // Act
    Map<String, List<Document>> actualAllIndexes = new MongoAdminStore(mongoDatabase).getAllIndexes();

    // Assert
    verify(mongoDatabase).listCollections();
    verify(listCollectionsIterable).forEach(isA(Consumer.class));
    assertTrue(actualAllIndexes.isEmpty());
  }

  /**
   * Test {@link MongoAdminStore#deleteIndex(String, String)}.
   * <ul>
   *   <li>Given {@link OperationExecutor} {@link OperationExecutor#execute(WriteOperation, ReadConcern, ClientSession)} return {@code null}.</li>
   *   <li>Then calls {@link OperationExecutor#execute(WriteOperation, ReadConcern, ClientSession)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoAdminStore#deleteIndex(String, String)}
   */
  @Test
  @DisplayName("Test deleteIndex(String, String); given OperationExecutor execute(WriteOperation, ReadConcern, ClientSession) return 'null'; then calls execute(WriteOperation, ReadConcern, ClientSession)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MongoAdminStore.deleteIndex(String, String)"})
  void testDeleteIndex_givenOperationExecutorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    OperationExecutor executor = mock(OperationExecutor.class);
    when(
        executor.execute(Mockito.<WriteOperation<Void>>any(), Mockito.<ReadConcern>any(), Mockito.<ClientSession>any()))
            .thenReturn(null);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    new MongoAdminStore(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
        new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, executor)).deleteIndex("42",
            "Index Name");

    // Assert
    verify(executor).execute(isA(WriteOperation.class), isA(ReadConcern.class), isNull());
  }

  /**
   * Test {@link MongoAdminStore#createIndexes()}.
   * <p>
   * Method under test: {@link MongoAdminStore#createIndexes()}
   */
  @Test
  @DisplayName("Test createIndexes()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MongoAdminStore.createIndexes()"})
  void testCreateIndexes() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act and Assert
    assertTrue(new MongoAdminStore(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true,
        true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)))
            .createIndexes()
            .isEmpty());
  }

  /**
   * Test {@link MongoAdminStore#runCommand(Document)}.
   * <ul>
   *   <li>Given {@link MongoDatabaseImpl} {@link MongoDatabaseImpl#runCommand(Bson)} return {@link Document#Document()}.</li>
   *   <li>Then return {@link Document#Document()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoAdminStore#runCommand(Document)}
   */
  @Test
  @DisplayName("Test runCommand(Document); given MongoDatabaseImpl runCommand(Bson) return Document(); then return Document()")
  @Tag("MaintainedByDiffblue")
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
   * Test {@link MongoAdminStore#runPipeline(String, List)} with {@code collectionName}, {@code pipeline}.
   * <ul>
   *   <li>Given {@link Document#Document()}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link Document#Document()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoAdminStore#runPipeline(String, List)}
   */
  @Test
  @DisplayName("Test runPipeline(String, List) with 'collectionName', 'pipeline'; given Document(); when ArrayList() add Document()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MongoAdminStore.runPipeline(String, List)"})
  void testRunPipelineWithCollectionNamePipeline_givenDocument_whenArrayListAddDocument() {
    // Arrange
    AggregateIterable<Document> aggregateIterable = mock(AggregateIterable.class);
    doNothing().when(aggregateIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.aggregate(Mockito.<List<Bson>>any())).thenReturn(aggregateIterable);
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    MongoAdminStore mongoAdminStore = new MongoAdminStore(mongoDatabase);

    ArrayList<Document> pipeline = new ArrayList<>();
    pipeline.add(new Document());

    // Act
    List<Document> actualRunPipelineResult = mongoAdminStore.runPipeline("Collection Name", pipeline);

    // Assert
    verify(mongoCollection).aggregate(isA(List.class));
    verify(mongoDatabase).getCollection(eq("Collection Name"));
    verify(aggregateIterable).forEach(isA(Consumer.class));
    assertTrue(actualRunPipelineResult.isEmpty());
  }

  /**
   * Test {@link MongoAdminStore#runPipeline(String, List)} with {@code collectionName}, {@code pipeline}.
   * <ul>
   *   <li>Given {@link Document#Document()}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link Document#Document()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoAdminStore#runPipeline(String, List)}
   */
  @Test
  @DisplayName("Test runPipeline(String, List) with 'collectionName', 'pipeline'; given Document(); when ArrayList() add Document()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MongoAdminStore.runPipeline(String, List)"})
  void testRunPipelineWithCollectionNamePipeline_givenDocument_whenArrayListAddDocument2() {
    // Arrange
    AggregateIterable<Document> aggregateIterable = mock(AggregateIterable.class);
    doNothing().when(aggregateIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.aggregate(Mockito.<List<Bson>>any())).thenReturn(aggregateIterable);
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    MongoAdminStore mongoAdminStore = new MongoAdminStore(mongoDatabase);

    ArrayList<Document> pipeline = new ArrayList<>();
    pipeline.add(new Document());
    pipeline.add(new Document());

    // Act
    List<Document> actualRunPipelineResult = mongoAdminStore.runPipeline("Collection Name", pipeline);

    // Assert
    verify(mongoCollection).aggregate(isA(List.class));
    verify(mongoDatabase).getCollection(eq("Collection Name"));
    verify(aggregateIterable).forEach(isA(Consumer.class));
    assertTrue(actualRunPipelineResult.isEmpty());
  }

  /**
   * Test {@link MongoAdminStore#runPipeline(String, List)} with {@code collectionName}, {@code pipeline}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoAdminStore#runPipeline(String, List)}
   */
  @Test
  @DisplayName("Test runPipeline(String, List) with 'collectionName', 'pipeline'; when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MongoAdminStore.runPipeline(String, List)"})
  void testRunPipelineWithCollectionNamePipeline_whenArrayList_thenReturnEmpty() {
    // Arrange
    AggregateIterable<Document> aggregateIterable = mock(AggregateIterable.class);
    doNothing().when(aggregateIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.aggregate(Mockito.<List<Bson>>any())).thenReturn(aggregateIterable);
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    MongoAdminStore mongoAdminStore = new MongoAdminStore(mongoDatabase);

    // Act
    List<Document> actualRunPipelineResult = mongoAdminStore.runPipeline("Collection Name", new ArrayList<>());

    // Assert
    verify(mongoCollection).aggregate(isA(List.class));
    verify(mongoDatabase).getCollection(eq("Collection Name"));
    verify(aggregateIterable).forEach(isA(Consumer.class));
    assertTrue(actualRunPipelineResult.isEmpty());
  }

  /**
   * Test {@link MongoAdminStore#getName()}.
   * <ul>
   *   <li>Given {@link WriteConcern#WriteConcern(int)} with w is one.</li>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoAdminStore#getName()}
   */
  @Test
  @DisplayName("Test getName(); given WriteConcern(int) with w is one; then return 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String MongoAdminStore.getName()"})
  void testGetName_givenWriteConcernWithWIsOne_thenReturnName() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act and Assert
    assertEquals("Name",
        new MongoAdminStore(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)))
                .getName());
  }
}
