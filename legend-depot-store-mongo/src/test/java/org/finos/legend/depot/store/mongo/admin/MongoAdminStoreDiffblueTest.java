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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MongoAdminStoreDiffblueTest {
  /**
   * Method under test: {@link MongoAdminStore#deleteCollection(String)}
   */
  @Test
  void testDeleteCollection() {
    // Arrange
    OperationExecutor executor = mock(OperationExecutor.class);
    when(
        executor.execute(Mockito.<WriteOperation<Void>>any(), Mockito.<ReadConcern>any(), Mockito.<ClientSession>any()))
            .thenReturn(null);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    (new MongoAdminStore(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
        new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, executor))).deleteCollection("42");

    // Assert
    verify(executor).execute(isA(WriteOperation.class), isA(ReadConcern.class), (ClientSession) isNull());
  }

  /**
   * Method under test: {@link MongoAdminStore#getAllCollections()}
   */
  @Test
  void testGetAllCollections() {
    // Arrange
    ListCollectionsIterable<Document> listCollectionsIterable = mock(ListCollectionsIterable.class);
    doNothing().when(listCollectionsIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.listCollections()).thenReturn(listCollectionsIterable);

    // Act
    List<String> actualAllCollections = (new MongoAdminStore(mongoDatabase)).getAllCollections();

    // Assert
    verify(mongoDatabase).listCollections();
    verify(listCollectionsIterable).forEach(isA(Consumer.class));
    assertTrue(actualAllCollections.isEmpty());
  }

  /**
   * Method under test: {@link MongoAdminStore#getAllIndexes()}
   */
  @Test
  void testGetAllIndexes() {
    // Arrange
    ListCollectionsIterable<Document> listCollectionsIterable = mock(ListCollectionsIterable.class);
    doNothing().when(listCollectionsIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.listCollections()).thenReturn(listCollectionsIterable);

    // Act
    Map<String, List<Document>> actualAllIndexes = (new MongoAdminStore(mongoDatabase)).getAllIndexes();

    // Assert
    verify(mongoDatabase).listCollections();
    verify(listCollectionsIterable).forEach(isA(Consumer.class));
    assertTrue(actualAllIndexes.isEmpty());
  }

  /**
   * Method under test: {@link MongoAdminStore#deleteIndex(String, String)}
   */
  @Test
  void testDeleteIndex() {
    // Arrange
    OperationExecutor executor = mock(OperationExecutor.class);
    when(
        executor.execute(Mockito.<WriteOperation<Void>>any(), Mockito.<ReadConcern>any(), Mockito.<ClientSession>any()))
            .thenReturn(null);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    (new MongoAdminStore(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
        new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, executor))).deleteIndex("42",
            "Index Name");

    // Assert
    verify(executor).execute(isA(WriteOperation.class), isA(ReadConcern.class), (ClientSession) isNull());
  }

  /**
   * Method under test: {@link MongoAdminStore#createIndexes()}
   */
  @Test
  void testCreateIndexes() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act and Assert
    assertTrue((new MongoAdminStore(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true,
        true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class))))
            .createIndexes()
            .isEmpty());
  }

  /**
   * Method under test: {@link MongoAdminStore#runCommand(Document)}
   */
  @Test
  void testRunCommand() {
    // Arrange
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    Document document = new Document();
    when(mongoDatabase.runCommand(Mockito.<Bson>any())).thenReturn(document);
    MongoAdminStore mongoAdminStore = new MongoAdminStore(mongoDatabase);

    // Act
    Document actualRunCommandResult = mongoAdminStore.runCommand(new Document());

    // Assert
    verify(mongoDatabase).runCommand(isA(Bson.class));
    assertTrue(actualRunCommandResult.isEmpty());
    assertSame(document, actualRunCommandResult);
  }

  /**
   * Method under test: {@link MongoAdminStore#runPipeline(String, String)}
   */
  @Test
  void testRunPipeline() throws JsonProcessingException {
    // Arrange
    AggregateIterable<Document> aggregateIterable = mock(AggregateIterable.class);
    doNothing().when(aggregateIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.aggregate(Mockito.<List<Bson>>any())).thenReturn(aggregateIterable);
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    MongoAdminStore mongoAdminStore = new MongoAdminStore(mongoDatabase);

    ObjectMapper objectMapper = new ObjectMapper();
    String collectionName = objectMapper.writeValueAsString(new ArrayList<>());

    ObjectMapper objectMapper2 = new ObjectMapper();

    // Act
    List<Document> actualRunPipelineResult = mongoAdminStore.runPipeline(collectionName,
        objectMapper2.writeValueAsString(new ArrayList<>()));

    // Assert
    verify(mongoCollection).aggregate(isA(List.class));
    verify(mongoDatabase).getCollection(eq("[]"));
    verify(aggregateIterable).forEach(isA(Consumer.class));
    assertTrue(actualRunPipelineResult.isEmpty());
  }

  /**
   * Method under test: {@link MongoAdminStore#runPipeline(String, List)}
   */
  @Test
  void testRunPipeline2() {
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
   * Method under test: {@link MongoAdminStore#runPipeline(String, List)}
   */
  @Test
  void testRunPipeline3() {
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
   * Method under test: {@link MongoAdminStore#runPipeline(String, List)}
   */
  @Test
  void testRunPipeline4() {
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
   * Method under test: {@link MongoAdminStore#getName()}
   */
  @Test
  void testGetName() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act and Assert
    assertEquals("Name",
        (new MongoAdminStore(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class))))
                .getName());
  }

  /**
   * Method under test: {@link MongoAdminStore#MongoAdminStore(MongoDatabase)}
   */
  @Test
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
}
