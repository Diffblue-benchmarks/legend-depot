package org.finos.legend.depot.store.mongo.schedules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
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
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
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
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SchedulesMongoDiffblueTest {
  /**
   * Test {@link SchedulesMongo#SchedulesMongo(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesMongo#SchedulesMongo(MongoDatabase)}
   */
  @Test
  @DisplayName(
      "Test new SchedulesMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesMongo.<init>(MongoDatabase)"})
  void testNewSchedulesMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new SchedulesMongo(null).getDatabase());
  }

  /**
   * Test {@link SchedulesMongo#getCollection()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoCollection SchedulesMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any()))
        .thenReturn(mock(MongoCollection.class));

    // Act
    new SchedulesMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection("schedules");
  }

  /**
   * Test {@link SchedulesMongo#getKeyFilter(ScheduleInfo)} with {@code ScheduleInfo}.
   *
   * <p>Method under test: {@link SchedulesMongo#getKeyFilter(ScheduleInfo)}
   */
  @Test
  @DisplayName("Test getKeyFilter(ScheduleInfo) with 'ScheduleInfo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson SchedulesMongo.getKeyFilter(ScheduleInfo)"})
  void testGetKeyFilterWithScheduleInfo() {
    // Arrange
    SchedulesMongo schedulesMongo = new SchedulesMongo(null);

    // Act
    Bson actualKeyFilter = schedulesMongo.getKeyFilter(new ScheduleInfo());
    Class<Object> forNameResult = Object.class;
    BsonDocument actualToBsonDocumentResult =
        actualKeyFilter.toBsonDocument(forNameResult, mock(CodecRegistry.class));

    // Assert
    assertEquals(1, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get(SchedulesMongo.NAME);
    assertTrue(getResult instanceof BsonNull);
    assertEquals(BsonType.NULL, getResult.getBsonType());
    assertFalse(getResult.isString());
    assertTrue(getResult.isNull());
  }

  /**
   * Test {@link SchedulesMongo#getKeyFilter(ScheduleInfo)} with {@code ScheduleInfo}.
   *
   * <p>Method under test: {@link SchedulesMongo#getKeyFilter(ScheduleInfo)}
   */
  @Test
  @DisplayName("Test getKeyFilter(ScheduleInfo) with 'ScheduleInfo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson SchedulesMongo.getKeyFilter(ScheduleInfo)"})
  void testGetKeyFilterWithScheduleInfo2() {
    // Arrange
    SchedulesMongo schedulesMongo = new SchedulesMongo(null);

    // Act
    Bson actualKeyFilter = schedulesMongo.getKeyFilter(new ScheduleInfo("Name"));
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult =
        actualKeyFilter.toBsonDocument(forNameResult, codecRegistry);

    // Assert
    verify(codecRegistry).get(isA(Class.class));
    assertEquals(1, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get(SchedulesMongo.NAME);
    assertTrue(getResult instanceof BsonString);
    assertEquals("Name", ((BsonString) getResult).getValue());
    assertEquals(BsonType.STRING, getResult.getBsonType());
    assertFalse(getResult.isNull());
    assertTrue(getResult.isString());
  }

  /**
   * Test {@link SchedulesMongo#get(String)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesMongo#get(String)}
   */
  @Test
  @DisplayName(
      "Test get(String); given FindIterable forEach(Consumer) does nothing; then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional SchedulesMongo.get(String)"})
  void testGet_givenFindIterableForEachDoesNothing_thenReturnNotPresent() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    Optional<ScheduleInfo> actualGetResult = new SchedulesMongo(databaseProvider).get("Name");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("schedules");
    verify(findIterable).forEach(isA(Consumer.class));
    assertFalse(actualGetResult.isPresent());
  }

  /**
   * Test {@link SchedulesMongo#getAll()}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesMongo#getAll()}
   */
  @Test
  @DisplayName(
      "Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SchedulesMongo.getAll()"})
  void testGetAll_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<ScheduleInfo> actualAll = new SchedulesMongo(databaseProvider).getAll();

    // Assert
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection("schedules");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link SchedulesMongo#buildIndexes()}.
   *
   * <p>Method under test: {@link SchedulesMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SchedulesMongo.buildIndexes()"})
  void testBuildIndexes() {
    // Arrange and Act
    List<IndexModel> actualBuildIndexesResult = SchedulesMongo.buildIndexes();

    // Assert
    assertEquals(1, actualBuildIndexesResult.size());
    IndexModel getResult = actualBuildIndexesResult.get(0);
    Bson keys = getResult.getKeys();
    assertTrue(keys instanceof Map);
    IndexOptions options = getResult.getOptions();
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
    assertEquals(1, ((Map<String, BsonInt32>) keys).size());
    assertFalse(options.isBackground());
    assertFalse(options.isSparse());
    assertFalse(options.isUnique());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey(SchedulesMongo.NAME));
    assertEquals(SchedulesMongo.NAME, options.getName());
  }
}
