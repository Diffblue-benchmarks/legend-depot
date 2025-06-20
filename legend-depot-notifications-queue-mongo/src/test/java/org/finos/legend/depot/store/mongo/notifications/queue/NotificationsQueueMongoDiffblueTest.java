package org.finos.legend.depot.store.mongo.notifications.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import com.mongodb.client.model.FindOneAndDeleteOptions;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.operation.WriteOperation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.BsonBooleanCodec;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.domain.notifications.MetadataNotificationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationsQueueMongoDiffblueTest {
  /**
   * Test {@link NotificationsQueueMongo#NotificationsQueueMongo(MongoDatabase)}.
   * <p>
   * Method under test: {@link NotificationsQueueMongo#NotificationsQueueMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new NotificationsQueueMongo(MongoDatabase)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NotificationsQueueMongo.<init>(MongoDatabase)"})
  void testNewNotificationsQueueMongo() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    // Act and Assert
    assertSame(databaseProvider, new NotificationsQueueMongo(databaseProvider).getDatabase());
  }

  /**
   * Test {@link NotificationsQueueMongo#NotificationsQueueMongo(MongoDatabase)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Database is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueMongo#NotificationsQueueMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new NotificationsQueueMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NotificationsQueueMongo.<init>(MongoDatabase)"})
  void testNewNotificationsQueueMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new NotificationsQueueMongo(null).getDatabase());
  }

  /**
   * Test {@link NotificationsQueueMongo#buildIndexes()}.
   * <p>
   * Method under test: {@link NotificationsQueueMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List NotificationsQueueMongo.buildIndexes()"})
  void testBuildIndexes() {
    // Arrange and Act
    List<IndexModel> actualBuildIndexesResult = NotificationsQueueMongo.buildIndexes();

    // Assert
    assertEquals(1, actualBuildIndexesResult.size());
    IndexModel getResult = actualBuildIndexesResult.get(0);
    Bson keys = getResult.getKeys();
    assertTrue(keys instanceof Map);
    IndexOptions options = getResult.getOptions();
    assertEquals("eventPriority-created", options.getName());
    assertNull(options.getCollation());
    assertNull(options.getBucketSize());
    assertNull(options.getMax());
    assertNull(options.getMin());
    assertNull(options.getBits());
    assertNull(options.getSphereVersion());
    assertNull(options.getTextVersion());
    assertNull(options.getVersion());
    assertNull(options.getDefaultLanguage());
    assertNull(options.getLanguageOverride());
    assertNull(options.getPartialFilterExpression());
    assertNull(options.getStorageEngine());
    assertNull(options.getWeights());
    assertNull(options.getWildcardProjection());
    assertEquals(2, ((Map<String, BsonInt32>) keys).size());
    assertFalse(options.isBackground());
    assertFalse(options.isSparse());
    assertFalse(options.isUnique());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("eventPriority"));
    BsonInt32 expectedGetResult = ((Map<String, BsonInt32>) keys).get("eventPriority");
    assertSame(expectedGetResult, ((Map<String, BsonInt32>) keys).get("created"));
  }

  /**
   * Test {@link NotificationsQueueMongo#getCollection()}.
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoCollection NotificationsQueueMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mock(MongoCollection.class));

    // Act
    new NotificationsQueueMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection(eq("notifications-queue"));
  }

  /**
   * Test {@link NotificationsQueueMongo#getKeyFilter(MetadataNotification)} with {@code MetadataNotification}.
   * <p>
   * Method under test: {@link NotificationsQueueMongo#getKeyFilter(MetadataNotification)}
   */
  @Test
  @DisplayName("Test getKeyFilter(MetadataNotification) with 'MetadataNotification'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson NotificationsQueueMongo.getKeyFilter(MetadataNotification)"})
  void testGetKeyFilterWithMetadataNotification() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    NotificationsQueueMongo notificationsQueueMongo = new NotificationsQueueMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act
    Bson actualKeyFilter = notificationsQueueMongo
        .getKeyFilter(new MetadataNotification("myproject", "42", "42", "42"));
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry2 = mock(CodecRegistry.class);
    when(codecRegistry2.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult = actualKeyFilter.toBsonDocument(forNameResult, codecRegistry2);

    // Assert
    verify(codecRegistry2, atLeast(1)).get(isA(Class.class));
    assertEquals(3, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonString);
    BsonValue getResult2 = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult2 instanceof BsonString);
    BsonValue getResult3 = actualToBsonDocumentResult.get("versionId");
    assertTrue(getResult3 instanceof BsonString);
    assertEquals(getResult2, getResult);
    assertEquals(getResult2, getResult3);
  }

  /**
   * Test {@link NotificationsQueueMongo#size()}.
   * <ul>
   *   <li>Given {@link MongoCollection} {@link MongoCollection#countDocuments()} return three.</li>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueMongo#size()}
   */
  @Test
  @DisplayName("Test size(); given MongoCollection countDocuments() return three; then return three")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long NotificationsQueueMongo.size()"})
  void testSize_givenMongoCollectionCountDocumentsReturnThree_thenReturnThree() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.countDocuments()).thenReturn(3L);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    long actualSizeResult = new NotificationsQueueMongo(databaseProvider).size();

    // Assert
    verify(mongoCollection).countDocuments();
    verify(databaseProvider).getCollection(eq("notifications-queue"));
    assertEquals(3L, actualSizeResult);
  }

  /**
   * Test {@link NotificationsQueueMongo#push(MetadataNotification)}.
   * <ul>
   *   <li>Given {@link Document#Document()} append {@code groupId} and {@code Value}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueMongo#push(MetadataNotification)}
   */
  @Test
  @DisplayName("Test push(MetadataNotification); given Document() append 'groupId' and 'Value'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String NotificationsQueueMongo.push(MetadataNotification)"})
  void testPush_givenDocumentAppendGroupIdAndValue_thenReturnNull() {
    // Arrange
    Document document = new Document();
    document.append("groupId", "Value");
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.findOneAndReplace(Mockito.<Bson>any(), Mockito.<Document>any(),
        Mockito.<FindOneAndReplaceOptions>any())).thenReturn(document);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    NotificationsQueueMongo notificationsQueueMongo = new NotificationsQueueMongo(databaseProvider);

    // Act
    String actualPushResult = notificationsQueueMongo.push(new MetadataNotification("myproject", "42", "42", "42"));

    // Assert
    verify(mongoCollection, atLeast(1)).findOneAndReplace(Mockito.<Bson>any(), Mockito.<Document>any(),
        isA(FindOneAndReplaceOptions.class));
    verify(databaseProvider, atLeast(1)).getCollection(eq("notifications-queue"));
    assertNull(actualPushResult);
  }

  /**
   * Test {@link NotificationsQueueMongo#push(MetadataNotification)}.
   * <ul>
   *   <li>Given {@link MongoCollection} {@link MongoCollection#findOneAndReplace(Bson, Object, FindOneAndReplaceOptions)} return {@link Document#Document()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueMongo#push(MetadataNotification)}
   */
  @Test
  @DisplayName("Test push(MetadataNotification); given MongoCollection findOneAndReplace(Bson, Object, FindOneAndReplaceOptions) return Document(); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String NotificationsQueueMongo.push(MetadataNotification)"})
  void testPush_givenMongoCollectionFindOneAndReplaceReturnDocument_thenReturnNull() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.findOneAndReplace(Mockito.<Bson>any(), Mockito.<Document>any(),
        Mockito.<FindOneAndReplaceOptions>any())).thenReturn(new Document());
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    NotificationsQueueMongo notificationsQueueMongo = new NotificationsQueueMongo(databaseProvider);

    // Act
    String actualPushResult = notificationsQueueMongo.push(new MetadataNotification("myproject", "42", "42", "42"));

    // Assert
    verify(mongoCollection, atLeast(1)).findOneAndReplace(Mockito.<Bson>any(), Mockito.<Document>any(),
        isA(FindOneAndReplaceOptions.class));
    verify(databaseProvider, atLeast(1)).getCollection(eq("notifications-queue"));
    assertNull(actualPushResult);
  }

  /**
   * Test {@link NotificationsQueueMongo#pullAll()}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueMongo#pullAll()}
   */
  @Test
  @DisplayName("Test pullAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List NotificationsQueueMongo.pullAll()"})
  void testPullAll_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<MetadataNotification> actualPullAllResult = new NotificationsQueueMongo(databaseProvider).pullAll();

    // Assert
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection(eq("notifications-queue"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualPullAllResult.isEmpty());
  }

  /**
   * Test {@link NotificationsQueueMongo#getFirstInQueue()}.
   * <ul>
   *   <li>Given {@link Document#Document()} append {@link NotificationsQueueMongo#COLLECTION} and {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueMongo#getFirstInQueue()}
   */
  @Test
  @DisplayName("Test getFirstInQueue(); given Document() append COLLECTION and 'Value'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional NotificationsQueueMongo.getFirstInQueue()"})
  void testGetFirstInQueue_givenDocumentAppendCollectionAndValue() {
    // Arrange
    Document document = new Document();
    document.append(NotificationsQueueMongo.COLLECTION, "Value");
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.findOneAndDelete(Mockito.<Bson>any(), Mockito.<FindOneAndDeleteOptions>any()))
        .thenReturn(document);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    Optional<MetadataNotification> actualFirstInQueue = new NotificationsQueueMongo(databaseProvider).getFirstInQueue();

    // Assert
    verify(mongoCollection).findOneAndDelete(isA(Bson.class), isA(FindOneAndDeleteOptions.class));
    verify(databaseProvider).getCollection(eq("notifications-queue"));
    MetadataNotification getResult = actualFirstInQueue.get();
    assertNull(getResult.getArtifactId());
    assertNull(getResult.getGroupId());
    assertNull(getResult.getVersionId());
    assertNull(getResult.getEventId());
    assertNull(getResult.getId());
    assertNull(getResult.getParentEventId());
    assertNull(getResult.getProjectId());
    assertNull(getResult.getCompleted());
    assertNull(getResult.getCreated());
    assertNull(getResult.getUpdated());
    assertNull(getResult.getCurrentResponse());
    assertNull(getResult.getEventPriority());
    assertEquals(0, getResult.getAttempt());
    assertEquals(2, getResult.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, getResult.getStatus());
    assertFalse(getResult.isFullUpdate());
    assertFalse(getResult.isTransitive());
    assertTrue(getResult.getResponses().isEmpty());
    assertTrue(actualFirstInQueue.isPresent());
  }

  /**
   * Test {@link NotificationsQueueMongo#getFirstInQueue()}.
   * <ul>
   *   <li>Then return {@link Optional#get()} ArtifactId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueMongo#getFirstInQueue()}
   */
  @Test
  @DisplayName("Test getFirstInQueue(); then return get() ArtifactId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional NotificationsQueueMongo.getFirstInQueue()"})
  void testGetFirstInQueue_thenReturnGetArtifactIdIsNull() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.findOneAndDelete(Mockito.<Bson>any(), Mockito.<FindOneAndDeleteOptions>any()))
        .thenReturn(new Document());
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    Optional<MetadataNotification> actualFirstInQueue = new NotificationsQueueMongo(databaseProvider).getFirstInQueue();

    // Assert
    verify(mongoCollection).findOneAndDelete(isA(Bson.class), isA(FindOneAndDeleteOptions.class));
    verify(databaseProvider).getCollection(eq("notifications-queue"));
    MetadataNotification getResult = actualFirstInQueue.get();
    assertNull(getResult.getArtifactId());
    assertNull(getResult.getGroupId());
    assertNull(getResult.getVersionId());
    assertNull(getResult.getEventId());
    assertNull(getResult.getId());
    assertNull(getResult.getParentEventId());
    assertNull(getResult.getProjectId());
    assertNull(getResult.getCompleted());
    assertNull(getResult.getCreated());
    assertNull(getResult.getUpdated());
    assertNull(getResult.getCurrentResponse());
    assertNull(getResult.getEventPriority());
    assertEquals(0, getResult.getAttempt());
    assertEquals(2, getResult.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, getResult.getStatus());
    assertFalse(getResult.isFullUpdate());
    assertFalse(getResult.isTransitive());
    assertTrue(getResult.getResponses().isEmpty());
    assertTrue(actualFirstInQueue.isPresent());
  }

  /**
   * Test {@link NotificationsQueueMongo#getAll()}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueMongo#getAll()}
   */
  @Test
  @DisplayName("Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List NotificationsQueueMongo.getAll()"})
  void testGetAll_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<MetadataNotification> actualAll = new NotificationsQueueMongo(databaseProvider).getAll();

    // Assert
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection(eq("notifications-queue"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link NotificationsQueueMongo#deleteAll()}.
   * <ul>
   *   <li>Given {@link DeleteResult} {@link DeleteResult#getDeletedCount()} return three.</li>
   *   <li>Then calls {@link MongoCollection#deleteMany(Bson)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueMongo#deleteAll()}
   */
  @Test
  @DisplayName("Test deleteAll(); given DeleteResult getDeletedCount() return three; then calls deleteMany(Bson)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long NotificationsQueueMongo.deleteAll()"})
  void testDeleteAll_givenDeleteResultGetDeletedCountReturnThree_thenCallsDeleteMany() {
    // Arrange
    DeleteResult deleteResult = mock(DeleteResult.class);
    when(deleteResult.getDeletedCount()).thenReturn(3L);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.deleteMany(Mockito.<Bson>any())).thenReturn(deleteResult);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    long actualDeleteAllResult = new NotificationsQueueMongo(databaseProvider).deleteAll();

    // Assert
    verify(mongoCollection).deleteMany(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("notifications-queue"));
    verify(deleteResult).getDeletedCount();
    assertEquals(3L, actualDeleteAllResult);
  }

  /**
   * Test {@link NotificationsQueueMongo#deleteAll()}.
   * <ul>
   *   <li>Then calls {@link BulkWriteResult#getDeletedCount()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueMongo#deleteAll()}
   */
  @Test
  @DisplayName("Test deleteAll(); then calls getDeletedCount()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long NotificationsQueueMongo.deleteAll()"})
  void testDeleteAll_thenCallsGetDeletedCount() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<BsonBoolean>>any())).thenReturn(new BsonBooleanCodec());
    BulkWriteResult bulkWriteResult = mock(BulkWriteResult.class);
    when(bulkWriteResult.getDeletedCount()).thenReturn(3);
    when(bulkWriteResult.wasAcknowledged()).thenReturn(true);
    OperationExecutor executor = mock(OperationExecutor.class);
    when(executor.execute(Mockito.<WriteOperation<BulkWriteResult>>any(), Mockito.<ReadConcern>any(),
        Mockito.<ClientSession>any())).thenReturn(bulkWriteResult);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    long actualDeleteAllResult = new NotificationsQueueMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, executor)).deleteAll();

    // Assert
    verify(bulkWriteResult).getDeletedCount();
    verify(bulkWriteResult).wasAcknowledged();
    verify(executor).execute(isA(WriteOperation.class), isA(ReadConcern.class), isNull());
    verify(codecRegistry).get(isA(Class.class));
    assertEquals(3L, actualDeleteAllResult);
  }
}
