package org.finos.legend.depot.store.mongo.notifications.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.model.FindOneAndDeleteOptions;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.result.DeleteResult;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonNull;
import org.bson.BsonString;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.Document;
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
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueMongo#NotificationsQueueMongo(MongoDatabase)}
   */
  @Test
  @DisplayName(
      "Test new NotificationsQueueMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void NotificationsQueueMongo.<init>(MongoDatabase)"})
  void testNewNotificationsQueueMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new NotificationsQueueMongo(null).getDatabase());
  }

  /**
   * Test {@link NotificationsQueueMongo#NotificationsQueueMongo(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueMongo#NotificationsQueueMongo(MongoDatabase)}
   */
  @Test
  @DisplayName(
      "Test new NotificationsQueueMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void NotificationsQueueMongo.<init>(MongoDatabase)"})
  void testNewNotificationsQueueMongo_whenNull_thenReturnDatabaseIsNull2() {
    // Arrange, Act and Assert
    assertNull(new NotificationsQueueMongo(null).getDatabase());
  }

  /**
   * Test {@link NotificationsQueueMongo#buildIndexes()}.
   *
   * <p>Method under test: {@link NotificationsQueueMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoCollection NotificationsQueueMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any()))
        .thenReturn(mock(MongoCollection.class));

    // Act
    new NotificationsQueueMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection("notifications-queue");
  }

  /**
   * Test {@link NotificationsQueueMongo#getKeyFilter(MetadataNotification)} with {@code
   * MetadataNotification}.
   *
   * <p>Method under test: {@link NotificationsQueueMongo#getKeyFilter(MetadataNotification)}
   */
  @Test
  @DisplayName("Test getKeyFilter(MetadataNotification) with 'MetadataNotification'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson NotificationsQueueMongo.getKeyFilter(MetadataNotification)"})
  void testGetKeyFilterWithMetadataNotification() {
    // Arrange
    NotificationsQueueMongo notificationsQueueMongo = new NotificationsQueueMongo(null);

    // Act
    Bson actualKeyFilter = notificationsQueueMongo.getKeyFilter(new MetadataNotification());
    Class<Object> forNameResult = Object.class;
    BsonDocument actualToBsonDocumentResult =
        actualKeyFilter.toBsonDocument(forNameResult, mock(CodecRegistry.class));

    // Assert
    assertEquals(3, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult instanceof BsonNull);
    assertEquals(BsonType.NULL, getResult.getBsonType());
    assertFalse(getResult.isString());
    assertTrue(getResult.isNull());
    assertSame(getResult, actualToBsonDocumentResult.get("artifactId"));
    assertSame(getResult, actualToBsonDocumentResult.get("versionId"));
  }

  /**
   * Test {@link NotificationsQueueMongo#getKeyFilter(MetadataNotification)} with {@code
   * MetadataNotification}.
   *
   * <p>Method under test: {@link NotificationsQueueMongo#getKeyFilter(MetadataNotification)}
   */
  @Test
  @DisplayName("Test getKeyFilter(MetadataNotification) with 'MetadataNotification'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson NotificationsQueueMongo.getKeyFilter(MetadataNotification)"})
  void testGetKeyFilterWithMetadataNotification2() {
    // Arrange
    NotificationsQueueMongo notificationsQueueMongo = new NotificationsQueueMongo(null);
    MetadataNotification event = new MetadataNotification("myproject", "42", "42", "42");

    // Act
    Bson actualKeyFilter = notificationsQueueMongo.getKeyFilter(event);
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult =
        actualKeyFilter.toBsonDocument(forNameResult, codecRegistry);

    // Assert
    verify(codecRegistry, atLeast(1)).get(isA(Class.class));
    assertEquals(3, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonString);
    BsonValue getResult2 = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult2 instanceof BsonString);
    BsonValue getResult3 = actualToBsonDocumentResult.get("versionId");
    assertTrue(getResult3 instanceof BsonString);
    assertEquals("42", ((BsonString) getResult2).getValue());
    assertEquals(BsonType.STRING, getResult2.getBsonType());
    assertFalse(getResult2.isNull());
    assertTrue(getResult2.isString());
    assertEquals(getResult2, getResult);
    assertEquals(getResult2, getResult3);
  }

  /**
   * Test {@link NotificationsQueueMongo#size()}.
   *
   * <ul>
   *   <li>Given {@link MongoCollection} {@link MongoCollection#countDocuments()} return three.
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueMongo#size()}
   */
  @Test
  @DisplayName(
      "Test size(); given MongoCollection countDocuments() return three; then return three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
    verify(databaseProvider).getCollection("notifications-queue");
    assertEquals(3L, actualSizeResult);
  }

  /**
   * Test {@link NotificationsQueueMongo#push(MetadataNotification)}.
   *
   * <ul>
   *   <li>Given {@link Document#Document()} append {@code groupId} and {@code Value}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueMongo#push(MetadataNotification)}
   */
  @Test
  @DisplayName(
      "Test push(MetadataNotification); given Document() append 'groupId' and 'Value'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String NotificationsQueueMongo.push(MetadataNotification)"})
  void testPush_givenDocumentAppendGroupIdAndValue_thenReturnNull() {
    // Arrange
    Document document = new Document();
    document.append("groupId", "Value");

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.findOneAndReplace(
            Mockito.<Bson>any(), Mockito.<Document>any(), Mockito.<FindOneAndReplaceOptions>any()))
        .thenReturn(document);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    NotificationsQueueMongo notificationsQueueMongo = new NotificationsQueueMongo(databaseProvider);

    // Act
    String actualPushResult = notificationsQueueMongo.push(new MetadataNotification());

    // Assert
    verify(mongoCollection, atLeast(1))
        .findOneAndReplace(
            Mockito.<Bson>any(), Mockito.<Document>any(), isA(FindOneAndReplaceOptions.class));
    verify(databaseProvider, atLeast(1)).getCollection("notifications-queue");
    assertNull(actualPushResult);
  }

  /**
   * Test {@link NotificationsQueueMongo#push(MetadataNotification)}.
   *
   * <ul>
   *   <li>Given {@link MongoCollection} {@link MongoCollection#findOneAndReplace(Bson, Object,
   *       FindOneAndReplaceOptions)} return {@link Document#Document()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueMongo#push(MetadataNotification)}
   */
  @Test
  @DisplayName(
      "Test push(MetadataNotification); given MongoCollection findOneAndReplace(Bson, Object, FindOneAndReplaceOptions) return Document(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String NotificationsQueueMongo.push(MetadataNotification)"})
  void testPush_givenMongoCollectionFindOneAndReplaceReturnDocument_thenReturnNull() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.findOneAndReplace(
            Mockito.<Bson>any(), Mockito.<Document>any(), Mockito.<FindOneAndReplaceOptions>any()))
        .thenReturn(new Document());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    NotificationsQueueMongo notificationsQueueMongo = new NotificationsQueueMongo(databaseProvider);

    // Act
    String actualPushResult = notificationsQueueMongo.push(new MetadataNotification());

    // Assert
    verify(mongoCollection, atLeast(1))
        .findOneAndReplace(
            isA(Bson.class), Mockito.<Document>any(), isA(FindOneAndReplaceOptions.class));
    verify(databaseProvider, atLeast(1)).getCollection("notifications-queue");
    assertNull(actualPushResult);
  }

  /**
   * Test {@link NotificationsQueueMongo#pullAll()}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueMongo#pullAll()}
   */
  @Test
  @DisplayName(
      "Test pullAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
    List<MetadataNotification> actualPullAllResult =
        new NotificationsQueueMongo(databaseProvider).pullAll();

    // Assert
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection("notifications-queue");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualPullAllResult.isEmpty());
  }

  /**
   * Test {@link NotificationsQueueMongo#getFirstInQueue()}.
   *
   * <ul>
   *   <li>Given {@link Document#Document()} append {@link NotificationsQueueMongo#COLLECTION} and
   *       {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueMongo#getFirstInQueue()}
   */
  @Test
  @DisplayName("Test getFirstInQueue(); given Document() append COLLECTION and 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional NotificationsQueueMongo.getFirstInQueue()"})
  void testGetFirstInQueue_givenDocumentAppendCollectionAndValue() {
    // Arrange
    Document document = new Document();
    document.append(NotificationsQueueMongo.COLLECTION, "Value");

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.findOneAndDelete(
            Mockito.<Bson>any(), Mockito.<FindOneAndDeleteOptions>any()))
        .thenReturn(document);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    Optional<MetadataNotification> actualFirstInQueue =
        new NotificationsQueueMongo(databaseProvider).getFirstInQueue();

    // Assert
    verify(mongoCollection).findOneAndDelete(isA(Bson.class), isA(FindOneAndDeleteOptions.class));
    verify(databaseProvider).getCollection("notifications-queue");
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
   *
   * <ul>
   *   <li>Then return {@link Optional#get()} ArtifactId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueMongo#getFirstInQueue()}
   */
  @Test
  @DisplayName("Test getFirstInQueue(); then return get() ArtifactId is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional NotificationsQueueMongo.getFirstInQueue()"})
  void testGetFirstInQueue_thenReturnGetArtifactIdIsNull() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.findOneAndDelete(
            Mockito.<Bson>any(), Mockito.<FindOneAndDeleteOptions>any()))
        .thenReturn(new Document());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    Optional<MetadataNotification> actualFirstInQueue =
        new NotificationsQueueMongo(databaseProvider).getFirstInQueue();

    // Assert
    verify(mongoCollection).findOneAndDelete(isA(Bson.class), isA(FindOneAndDeleteOptions.class));
    verify(databaseProvider).getCollection("notifications-queue");
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
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueMongo#getAll()}
   */
  @Test
  @DisplayName(
      "Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
    verify(databaseProvider).getCollection("notifications-queue");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link NotificationsQueueMongo#deleteAll()}.
   *
   * <ul>
   *   <li>Given {@link DeleteResult} {@link DeleteResult#getDeletedCount()} return three.
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueMongo#deleteAll()}
   */
  @Test
  @DisplayName(
      "Test deleteAll(); given DeleteResult getDeletedCount() return three; then return three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long NotificationsQueueMongo.deleteAll()"})
  void testDeleteAll_givenDeleteResultGetDeletedCountReturnThree_thenReturnThree() {
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
    verify(databaseProvider).getCollection("notifications-queue");
    verify(deleteResult).getDeletedCount();
    assertEquals(3L, actualDeleteAllResult);
  }
}
