package org.finos.legend.depot.store.mongo.metrics.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.model.IndexModel;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.StoreException;
import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class QueryMetricsMongoDiffblueTest {
  /**
   * Test {@link QueryMetricsMongo#QueryMetricsMongo(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsMongo#QueryMetricsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName(
      "Test new QueryMetricsMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsMongo.<init>(MongoDatabase)"})
  void testNewQueryMetricsMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new QueryMetricsMongo(null).getDatabase());
  }

  /**
   * Test {@link QueryMetricsMongo#QueryMetricsMongo(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsMongo#QueryMetricsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName(
      "Test new QueryMetricsMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsMongo.<init>(MongoDatabase)"})
  void testNewQueryMetricsMongo_whenNull_thenReturnDatabaseIsNull2() {
    // Arrange, Act and Assert
    assertNull(new QueryMetricsMongo(null).getDatabase());
  }

  /**
   * Test {@link QueryMetricsMongo#getCollection()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoCollection QueryMetricsMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any()))
        .thenReturn(mock(MongoCollection.class));

    // Act
    new QueryMetricsMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection("query-metrics");
  }

  /**
   * Test {@link QueryMetricsMongo#getAll()}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsMongo#getAll()}
   */
  @Test
  @DisplayName(
      "Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsMongo.getAll()"})
  void testGetAll_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<VersionQueryMetric> actualAll = new QueryMetricsMongo(databaseProvider).getAll();

    // Assert
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection("query-metrics");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link QueryMetricsMongo#getAllStoredEntitiesCoordinates()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsMongo#getAllStoredEntitiesCoordinates()}
   */
  @Test
  @DisplayName("Test getAllStoredEntitiesCoordinates(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsMongo.getAllStoredEntitiesCoordinates()"})
  void testGetAllStoredEntitiesCoordinates_thenReturnEmpty() {
    // Arrange
    AggregateIterable<Document> aggregateIterable = mock(AggregateIterable.class);
    doNothing().when(aggregateIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.aggregate(Mockito.<List<Bson>>any())).thenReturn(aggregateIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<ProjectVersion> actualAllStoredEntitiesCoordinates =
        new QueryMetricsMongo(databaseProvider).getAllStoredEntitiesCoordinates();

    // Assert
    verify(mongoCollection).aggregate(isA(List.class));
    verify(databaseProvider).getCollection("query-metrics");
    verify(aggregateIterable).forEach(isA(Consumer.class));
    assertTrue(actualAllStoredEntitiesCoordinates.isEmpty());
  }

  /**
   * Test {@link QueryMetricsMongo#get(String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsMongo#get(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test get(String, String, String); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsMongo.get(String, String, String)"})
  void testGet_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<VersionQueryMetric> actualGetResult =
        new QueryMetricsMongo(databaseProvider).get("42", "42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("query-metrics");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualGetResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsMongo#find(String, String)} with {@code groupId}, {@code artifactId}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsMongo#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsMongo.find(String, String)"})
  void testFindWithGroupIdArtifactId_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<VersionQueryMetric> actualFindResult =
        new QueryMetricsMongo(databaseProvider).find("42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("query-metrics");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsMongo#insert(VersionQueryMetric)} with {@code VersionQueryMetric}.
   *
   * <p>Method under test: {@link QueryMetricsMongo#insert(VersionQueryMetric)}
   */
  @Test
  @DisplayName("Test insert(VersionQueryMetric) with 'VersionQueryMetric'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsMongo.insert(VersionQueryMetric)"})
  void testInsertWithVersionQueryMetric() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    doNothing().when(mongoCollection).insertOne(Mockito.<Document>any());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(databaseProvider);
    VersionQueryMetric metric = new VersionQueryMetric("42", "42", "42");

    // Act
    queryMetricsMongo.insert(metric);

    // Assert
    verify(mongoCollection).insertOne(isA(Document.class));
    verify(databaseProvider).getCollection("query-metrics");
  }

  /**
   * Test {@link QueryMetricsMongo#insert(VersionQueryMetric)} with {@code VersionQueryMetric}.
   *
   * <p>Method under test: {@link QueryMetricsMongo#insert(VersionQueryMetric)}
   */
  @Test
  @DisplayName("Test insert(VersionQueryMetric) with 'VersionQueryMetric'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsMongo.insert(VersionQueryMetric)"})
  void testInsertWithVersionQueryMetric2() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    doNothing().when(mongoCollection).insertOne(Mockito.<Document>any());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(databaseProvider);
    Date lastQueryTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    VersionQueryMetric metric = new VersionQueryMetric("42", "42", "42", lastQueryTime);

    // Act
    queryMetricsMongo.insert(metric);

    // Assert
    verify(mongoCollection).insertOne(isA(Document.class));
    verify(databaseProvider).getCollection("query-metrics");
  }

  /**
   * Test {@link QueryMetricsMongo#insert(VersionQueryMetric)} with {@code VersionQueryMetric}.
   *
   * <p>Method under test: {@link QueryMetricsMongo#insert(VersionQueryMetric)}
   */
  @Test
  @DisplayName("Test insert(VersionQueryMetric) with 'VersionQueryMetric'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsMongo.insert(VersionQueryMetric)"})
  void testInsertWithVersionQueryMetric3() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any()))
        .thenReturn(mock(MongoCollection.class));
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(databaseProvider);

    VersionQueryMetric metric = mock(VersionQueryMetric.class);
    when(metric.getArtifactId()).thenReturn("42");
    when(metric.getGroupId()).thenReturn("42");
    when(metric.getVersionId()).thenReturn("42");
    when(metric.getLastQueryTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act and Assert
    assertThrows(StoreException.class, () -> queryMetricsMongo.insert(metric));
    verify(databaseProvider).getCollection("query-metrics");
    verify(metric).getArtifactId();
    verify(metric).getGroupId();
    verify(metric).getLastQueryTime();
    verify(metric).getVersionId();
  }

  /**
   * Test {@link QueryMetricsMongo#insert(VersionQueryMetric)} with {@code VersionQueryMetric}.
   *
   * <p>Method under test: {@link QueryMetricsMongo#insert(VersionQueryMetric)}
   */
  @Test
  @DisplayName("Test insert(VersionQueryMetric) with 'VersionQueryMetric'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsMongo.insert(VersionQueryMetric)"})
  void testInsertWithVersionQueryMetric4() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any()))
        .thenReturn(mock(MongoCollection.class));
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(databaseProvider);

    VersionQueryMetric metric = mock(VersionQueryMetric.class);
    when(metric.getArtifactId()).thenReturn("42");
    when(metric.getGroupId()).thenReturn("42");
    when(metric.getVersionId()).thenReturn("42");

    LocalDate ofEpochDayResult = LocalDate.ofEpochDay(4L);
    when(metric.getLastQueryTime())
        .thenReturn(Date.from(ofEpochDayResult.atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act and Assert
    assertThrows(StoreException.class, () -> queryMetricsMongo.insert(metric));
    verify(databaseProvider).getCollection("query-metrics");
    verify(metric).getArtifactId();
    verify(metric).getGroupId();
    verify(metric).getLastQueryTime();
    verify(metric).getVersionId();
  }

  /**
   * Test {@link QueryMetricsMongo#insert(VersionQueryMetric)} with {@code VersionQueryMetric}.
   *
   * <ul>
   *   <li>Given from now atStartOfDay atZone {@link ZoneOffset#UTC} toInstant.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsMongo#insert(VersionQueryMetric)}
   */
  @Test
  @DisplayName(
      "Test insert(VersionQueryMetric) with 'VersionQueryMetric'; given from now atStartOfDay atZone UTC toInstant")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsMongo.insert(VersionQueryMetric)"})
  void testInsertWithVersionQueryMetric_givenFromNowAtStartOfDayAtZoneUtcToInstant() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any()))
        .thenReturn(mock(MongoCollection.class));
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(databaseProvider);

    VersionQueryMetric metric = mock(VersionQueryMetric.class);
    when(metric.getArtifactId()).thenReturn("42");
    when(metric.getGroupId()).thenReturn("42");
    when(metric.getVersionId()).thenReturn("42");
    when(metric.getLastQueryTime())
        .thenReturn(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act and Assert
    assertThrows(StoreException.class, () -> queryMetricsMongo.insert(metric));
    verify(databaseProvider).getCollection("query-metrics");
    verify(metric).getArtifactId();
    verify(metric).getGroupId();
    verify(metric).getLastQueryTime();
    verify(metric).getVersionId();
  }

  /**
   * Test {@link QueryMetricsMongo#insert(VersionQueryMetric)} with {@code VersionQueryMetric}.
   *
   * <ul>
   *   <li>When {@link VersionQueryMetric#VersionQueryMetric()}.
   *   <li>Then calls {@link MongoCollection#insertOne(Object)}.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsMongo#insert(VersionQueryMetric)}
   */
  @Test
  @DisplayName(
      "Test insert(VersionQueryMetric) with 'VersionQueryMetric'; when VersionQueryMetric(); then calls insertOne(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsMongo.insert(VersionQueryMetric)"})
  void testInsertWithVersionQueryMetric_whenVersionQueryMetric_thenCallsInsertOne() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    doNothing().when(mongoCollection).insertOne(Mockito.<Document>any());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(databaseProvider);

    // Act
    queryMetricsMongo.insert(new VersionQueryMetric());

    // Assert
    verify(mongoCollection).insertOne(isA(Document.class));
    verify(databaseProvider).getCollection("query-metrics");
  }

  /**
   * Test {@link QueryMetricsMongo#findMetricsBefore(Date)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsMongo#findMetricsBefore(Date)}
   */
  @Test
  @DisplayName(
      "Test findMetricsBefore(Date); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsMongo.findMetricsBefore(Date)"})
  void testFindMetricsBefore_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(databaseProvider);

    // Act
    List<VersionQueryMetric> actualFindMetricsBeforeResult =
        queryMetricsMongo.findMetricsBefore(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("query-metrics");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindMetricsBeforeResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsMongo#getKeyFilter(VersionQueryMetric)} with {@code data}.
   *
   * <p>Method under test: {@link QueryMetricsMongo#getKeyFilter(VersionQueryMetric)}
   */
  @Test
  @DisplayName("Test getKeyFilter(VersionQueryMetric) with 'data'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson QueryMetricsMongo.getKeyFilter(VersionQueryMetric)"})
  void testGetKeyFilterWithData() {
    // Arrange
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(null);

    // Act
    Bson actualKeyFilter = queryMetricsMongo.getKeyFilter(new VersionQueryMetric());
    Class<Object> forNameResult = Object.class;
    BsonDocument actualToBsonDocumentResult =
        actualKeyFilter.toBsonDocument(forNameResult, mock(CodecRegistry.class));

    // Assert
    assertEquals(3, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonNull);
    assertEquals(BsonType.NULL, getResult.getBsonType());
    assertFalse(getResult.isString());
    assertTrue(getResult.isNull());
    assertSame(getResult, actualToBsonDocumentResult.get("groupId"));
    assertSame(getResult, actualToBsonDocumentResult.get("versionId"));
  }

  /**
   * Test {@link QueryMetricsMongo#getKeyFilter(VersionQueryMetric)} with {@code data}.
   *
   * <p>Method under test: {@link QueryMetricsMongo#getKeyFilter(VersionQueryMetric)}
   */
  @Test
  @DisplayName("Test getKeyFilter(VersionQueryMetric) with 'data'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson QueryMetricsMongo.getKeyFilter(VersionQueryMetric)"})
  void testGetKeyFilterWithData2() {
    // Arrange
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(null);
    VersionQueryMetric data = new VersionQueryMetric("42", "42", "42");

    // Act
    Bson actualKeyFilter = queryMetricsMongo.getKeyFilter(data);
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
    assertEquals("42", ((BsonString) getResult).getValue());
    assertEquals(BsonType.STRING, getResult.getBsonType());
    assertFalse(getResult.isNull());
    assertTrue(getResult.isString());
    assertEquals(getResult, getResult2);
    assertEquals(getResult, getResult3);
  }

  /**
   * Test {@link QueryMetricsMongo#getKeyFilter(String, String, String)} with {@code groupId},
   * {@code artifactId}, {@code versionId}.
   *
   * <p>Method under test: {@link QueryMetricsMongo#getKeyFilter(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test getKeyFilter(String, String, String) with 'groupId', 'artifactId', 'versionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson QueryMetricsMongo.getKeyFilter(String, String, String)"})
  void testGetKeyFilterWithGroupIdArtifactIdVersionId() {
    // Arrange and Act
    Bson actualKeyFilter = new QueryMetricsMongo(null).getKeyFilter("42", "42", "42");
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
    assertEquals(getResult, getResult2);
    assertEquals(getResult, getResult3);
  }

  /**
   * Test {@link QueryMetricsMongo#buildIndexes()}.
   *
   * <p>Method under test: {@link QueryMetricsMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsMongo.buildIndexes()"})
  void testBuildIndexes() {
    // Arrange and Act
    List<IndexModel> actualBuildIndexesResult = QueryMetricsMongo.buildIndexes();

    // Assert
    assertEquals(1, actualBuildIndexesResult.size());
    Bson keys = actualBuildIndexesResult.get(0).getKeys();
    assertTrue(keys instanceof Map);
    assertEquals(3, ((Map<String, BsonInt32>) keys).size());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("artifactId"));
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("groupId"));
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("versionId"));
  }
}
