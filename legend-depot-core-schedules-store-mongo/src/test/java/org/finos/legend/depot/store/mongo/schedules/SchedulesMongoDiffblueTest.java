package org.finos.legend.depot.store.mongo.schedules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
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
import com.mongodb.client.model.IndexOptions;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.UuidRepresentation;
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
   * <p>
   * Method under test: {@link SchedulesMongo#SchedulesMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new SchedulesMongo(MongoDatabase)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesMongo.<init>(MongoDatabase)"})
  void testNewSchedulesMongo() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    // Act and Assert
    assertSame(databaseProvider, new SchedulesMongo(databaseProvider).getDatabase());
  }

  /**
   * Test {@link SchedulesMongo#getCollection()}.
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoCollection SchedulesMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mock(MongoCollection.class));

    // Act
    new SchedulesMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection(eq("schedules"));
  }

  /**
   * Test {@link SchedulesMongo#getKeyFilter(ScheduleInfo)} with {@code ScheduleInfo}.
   * <p>
   * Method under test: {@link SchedulesMongo#getKeyFilter(ScheduleInfo)}
   */
  @Test
  @DisplayName("Test getKeyFilter(ScheduleInfo) with 'ScheduleInfo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson SchedulesMongo.getKeyFilter(ScheduleInfo)"})
  void testGetKeyFilterWithScheduleInfo() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    SchedulesMongo schedulesMongo = new SchedulesMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act
    Bson actualKeyFilter = schedulesMongo.getKeyFilter(new ScheduleInfo("Name"));
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry2 = mock(CodecRegistry.class);
    when(codecRegistry2.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult = actualKeyFilter.toBsonDocument(forNameResult, codecRegistry2);

    // Assert
    verify(codecRegistry2).get(isA(Class.class));
    assertEquals(1, actualToBsonDocumentResult.size());
    assertTrue(actualToBsonDocumentResult.get(SchedulesMongo.NAME) instanceof BsonString);
  }

  /**
   * Test {@link SchedulesMongo#get(String)}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesMongo#get(String)}
   */
  @Test
  @DisplayName("Test get(String); given FindIterable forEach(Consumer) does nothing; then return not Present")
  @Tag("MaintainedByDiffblue")
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
    verify(databaseProvider).getCollection(eq("schedules"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertFalse(actualGetResult.isPresent());
  }

  /**
   * Test {@link SchedulesMongo#getAll()}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesMongo#getAll()}
   */
  @Test
  @DisplayName("Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
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
    verify(databaseProvider).getCollection(eq("schedules"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link SchedulesMongo#buildIndexes()}.
   * <p>
   * Method under test: {@link SchedulesMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("MaintainedByDiffblue")
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
