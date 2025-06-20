package org.finos.legend.depot.store.mongo.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import com.mongodb.client.model.IndexModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationsMongoDiffblueTest {
  @Mock
  private MongoDatabase mongoDatabase;

  @InjectMocks
  private NotificationsMongo notificationsMongo;

  /**
   * Test {@link NotificationsMongo#NotificationsMongo(MongoDatabase)}.
   * <p>
   * Method under test: {@link NotificationsMongo#NotificationsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new NotificationsMongo(MongoDatabase)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NotificationsMongo.<init>(MongoDatabase)"})
  void testNewNotificationsMongo() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    // Act and Assert
    assertSame(databaseProvider, new NotificationsMongo(databaseProvider).getDatabase());
  }

  /**
   * Test {@link NotificationsMongo#NotificationsMongo(MongoDatabase)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Database is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsMongo#NotificationsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new NotificationsMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NotificationsMongo.<init>(MongoDatabase)"})
  void testNewNotificationsMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new NotificationsMongo(null).getDatabase());
  }

  /**
   * Test {@link NotificationsMongo#getCollection()}.
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoCollection NotificationsMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mock(MongoCollection.class));

    // Act
    new NotificationsMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection(eq("notifications"));
  }

  /**
   * Test {@link NotificationsMongo#buildIndexes()}.
   * <p>
   * Method under test: {@link NotificationsMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List NotificationsMongo.buildIndexes()"})
  void testBuildIndexes() {
    // Arrange and Act
    List<IndexModel> actualBuildIndexesResult = NotificationsMongo.buildIndexes();

    // Assert
    assertEquals(5, actualBuildIndexesResult.size());
    Bson keys = actualBuildIndexesResult.get(0).getKeys();
    assertTrue(keys instanceof Map);
    Bson keys2 = actualBuildIndexesResult.get(1).getKeys();
    assertTrue(keys2 instanceof Map);
    Bson keys3 = actualBuildIndexesResult.get(2).getKeys();
    assertTrue(keys3 instanceof Map);
    Bson keys4 = actualBuildIndexesResult.get(3).getKeys();
    assertTrue(keys4 instanceof Map);
    Bson keys5 = actualBuildIndexesResult.get(4).getKeys();
    assertTrue(keys5 instanceof Map);
    assertEquals(1, ((Map<String, BsonInt32>) keys).size());
    assertEquals(1, ((Map<String, BsonInt32>) keys2).size());
    assertEquals(1, ((Map<String, BsonInt32>) keys3).size());
    assertEquals(1, ((Map<String, BsonInt32>) keys5).size());
    assertEquals(3, ((Map<String, BsonInt32>) keys4).size());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("parentEventId"));
    assertTrue(((Map<String, BsonInt32>) keys2).containsKey("status"));
    assertTrue(((Map<String, BsonInt32>) keys3).containsKey("updated"));
    assertTrue(((Map<String, BsonInt32>) keys4).containsKey("artifactId"));
    assertTrue(((Map<String, BsonInt32>) keys4).containsKey("groupId"));
    assertTrue(((Map<String, BsonInt32>) keys4).containsKey("versionId"));
    assertTrue(((Map<String, BsonInt32>) keys5).containsKey("eventId"));
  }

  /**
   * Test {@link NotificationsMongo#getKeyFilter(MetadataNotification)} with {@code MetadataNotification}.
   * <p>
   * Method under test: {@link NotificationsMongo#getKeyFilter(MetadataNotification)}
   */
  @Test
  @DisplayName("Test getKeyFilter(MetadataNotification) with 'MetadataNotification'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson NotificationsMongo.getKeyFilter(MetadataNotification)"})
  void testGetKeyFilterWithMetadataNotification() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    NotificationsMongo notificationsMongo = new NotificationsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act
    Bson actualKeyFilter = notificationsMongo.getKeyFilter(new MetadataNotification("myproject", "42", "42", "42"));
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
   * Test {@link NotificationsMongo#getAll()}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsMongo#getAll()}
   */
  @Test
  @DisplayName("Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List NotificationsMongo.getAll()"})
  void testGetAll_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<MetadataNotification> actualAll = new NotificationsMongo(databaseProvider).getAll();

    // Assert
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection(eq("notifications"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link NotificationsMongo#get(String)}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsMongo#get(String)}
   */
  @Test
  @DisplayName("Test get(String); given FindIterable forEach(Consumer) does nothing; then return not Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional NotificationsMongo.get(String)"})
  void testGet_givenFindIterableForEachDoesNothing_thenReturnNotPresent() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    Optional<MetadataNotification> actualGetResult = new NotificationsMongo(databaseProvider).get("42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("notifications"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertFalse(actualGetResult.isPresent());
  }

  /**
   * Test {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)} with {@code groupId}, {@code artifactId}, {@code version}, {@code eventId}, {@code parentEventId}, {@code success}, {@code fromDate}, {@code toDate}.
   * <p>
   * Method under test: {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime) with 'groupId', 'artifactId', 'version', 'eventId', 'parentEventId', 'success', 'fromDate', 'toDate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List NotificationsMongo.find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)"})
  void testFindWithGroupIdArtifactIdVersionEventIdParentEventIdSuccessFromDateToDate() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.sort(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    LocalDateTime fromDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    List<MetadataNotification> actualFindResult = notificationsMongo.find("42", "42", "1.0.2", "42", "42", true,
        fromDate, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(findIterable2).sort(isA(Bson.class));
    verify(mongoCollection).find(isA(Bson.class));
    verify(mongoDatabase).getCollection(eq("notifications"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)} with {@code groupId}, {@code artifactId}, {@code version}, {@code eventId}, {@code parentEventId}, {@code success}, {@code fromDate}, {@code toDate}.
   * <p>
   * Method under test: {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime) with 'groupId', 'artifactId', 'version', 'eventId', 'parentEventId', 'success', 'fromDate', 'toDate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List NotificationsMongo.find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)"})
  void testFindWithGroupIdArtifactIdVersionEventIdParentEventIdSuccessFromDateToDate2() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.sort(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    LocalDateTime fromDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    List<MetadataNotification> actualFindResult = notificationsMongo.find(null, "42", "1.0.2", "42", "42", true,
        fromDate, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(findIterable2).sort(isA(Bson.class));
    verify(mongoCollection).find(isA(Bson.class));
    verify(mongoDatabase).getCollection(eq("notifications"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)} with {@code groupId}, {@code artifactId}, {@code version}, {@code eventId}, {@code parentEventId}, {@code success}, {@code fromDate}, {@code toDate}.
   * <p>
   * Method under test: {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime) with 'groupId', 'artifactId', 'version', 'eventId', 'parentEventId', 'success', 'fromDate', 'toDate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List NotificationsMongo.find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)"})
  void testFindWithGroupIdArtifactIdVersionEventIdParentEventIdSuccessFromDateToDate3() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.sort(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    LocalDateTime fromDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    List<MetadataNotification> actualFindResult = notificationsMongo.find("42", null, "1.0.2", "42", "42", true,
        fromDate, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(findIterable2).sort(isA(Bson.class));
    verify(mongoCollection).find(isA(Bson.class));
    verify(mongoDatabase).getCollection(eq("notifications"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)} with {@code groupId}, {@code artifactId}, {@code version}, {@code eventId}, {@code parentEventId}, {@code success}, {@code fromDate}, {@code toDate}.
   * <p>
   * Method under test: {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime) with 'groupId', 'artifactId', 'version', 'eventId', 'parentEventId', 'success', 'fromDate', 'toDate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List NotificationsMongo.find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)"})
  void testFindWithGroupIdArtifactIdVersionEventIdParentEventIdSuccessFromDateToDate4() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.sort(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    LocalDateTime fromDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    List<MetadataNotification> actualFindResult = notificationsMongo.find("42", "42", null, "42", "42", true, fromDate,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(findIterable2).sort(isA(Bson.class));
    verify(mongoCollection).find(isA(Bson.class));
    verify(mongoDatabase).getCollection(eq("notifications"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)} with {@code groupId}, {@code artifactId}, {@code version}, {@code eventId}, {@code parentEventId}, {@code success}, {@code fromDate}, {@code toDate}.
   * <p>
   * Method under test: {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime) with 'groupId', 'artifactId', 'version', 'eventId', 'parentEventId', 'success', 'fromDate', 'toDate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List NotificationsMongo.find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)"})
  void testFindWithGroupIdArtifactIdVersionEventIdParentEventIdSuccessFromDateToDate5() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.sort(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    LocalDateTime fromDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    List<MetadataNotification> actualFindResult = notificationsMongo.find("42", "42", "1.0.2", null, "42", true,
        fromDate, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(findIterable2).sort(isA(Bson.class));
    verify(mongoCollection).find(isA(Bson.class));
    verify(mongoDatabase).getCollection(eq("notifications"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)} with {@code groupId}, {@code artifactId}, {@code version}, {@code eventId}, {@code parentEventId}, {@code success}, {@code fromDate}, {@code toDate}.
   * <p>
   * Method under test: {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime) with 'groupId', 'artifactId', 'version', 'eventId', 'parentEventId', 'success', 'fromDate', 'toDate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List NotificationsMongo.find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)"})
  void testFindWithGroupIdArtifactIdVersionEventIdParentEventIdSuccessFromDateToDate6() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.sort(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    LocalDateTime fromDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    List<MetadataNotification> actualFindResult = notificationsMongo.find("42", "42", "1.0.2", "42", null, true,
        fromDate, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(findIterable2).sort(isA(Bson.class));
    verify(mongoCollection).find(isA(Bson.class));
    verify(mongoDatabase).getCollection(eq("notifications"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)} with {@code groupId}, {@code artifactId}, {@code version}, {@code eventId}, {@code parentEventId}, {@code success}, {@code fromDate}, {@code toDate}.
   * <p>
   * Method under test: {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime) with 'groupId', 'artifactId', 'version', 'eventId', 'parentEventId', 'success', 'fromDate', 'toDate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List NotificationsMongo.find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)"})
  void testFindWithGroupIdArtifactIdVersionEventIdParentEventIdSuccessFromDateToDate7() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.sort(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    LocalDateTime fromDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    List<MetadataNotification> actualFindResult = notificationsMongo.find("42", "42", "1.0.2", "42", "42", false,
        fromDate, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(findIterable2).sort(isA(Bson.class));
    verify(mongoCollection).find(isA(Bson.class));
    verify(mongoDatabase).getCollection(eq("notifications"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)} with {@code groupId}, {@code artifactId}, {@code version}, {@code eventId}, {@code parentEventId}, {@code success}, {@code fromDate}, {@code toDate}.
   * <p>
   * Method under test: {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime) with 'groupId', 'artifactId', 'version', 'eventId', 'parentEventId', 'success', 'fromDate', 'toDate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List NotificationsMongo.find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)"})
  void testFindWithGroupIdArtifactIdVersionEventIdParentEventIdSuccessFromDateToDate8() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.sort(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    LocalDateTime fromDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    List<MetadataNotification> actualFindResult = notificationsMongo.find("42", "42", "1.0.2", "42", "42", null,
        fromDate, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(findIterable2).sort(isA(Bson.class));
    verify(mongoCollection).find(isA(Bson.class));
    verify(mongoDatabase).getCollection(eq("notifications"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)} with {@code groupId}, {@code artifactId}, {@code version}, {@code eventId}, {@code parentEventId}, {@code success}, {@code fromDate}, {@code toDate}.
   * <p>
   * Method under test: {@link NotificationsMongo#find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime) with 'groupId', 'artifactId', 'version', 'eventId', 'parentEventId', 'success', 'fromDate', 'toDate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List NotificationsMongo.find(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)"})
  void testFindWithGroupIdArtifactIdVersionEventIdParentEventIdSuccessFromDateToDate9() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.sort(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<MetadataNotification> actualFindResult = notificationsMongo.find(null, null, null, null, null, null, null,
        null);

    // Assert
    verify(findIterable2).sort(isA(Bson.class));
    verify(mongoCollection).find(isA(Bson.class));
    verify(mongoDatabase).getCollection(eq("notifications"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }
}
