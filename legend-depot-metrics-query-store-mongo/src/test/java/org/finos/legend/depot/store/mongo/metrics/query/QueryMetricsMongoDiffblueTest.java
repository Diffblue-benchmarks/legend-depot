package org.finos.legend.depot.store.mongo.metrics.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import com.mongodb.client.model.IndexModel;
import com.mongodb.operation.WriteOperation;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class QueryMetricsMongoDiffblueTest {
  /**
   * Test {@link QueryMetricsMongo#QueryMetricsMongo(MongoDatabase)}.
   * <p>
   * Method under test: {@link QueryMetricsMongo#QueryMetricsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new QueryMetricsMongo(MongoDatabase)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void QueryMetricsMongo.<init>(MongoDatabase)"})
  void testNewQueryMetricsMongo() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    // Act and Assert
    assertSame(databaseProvider, new QueryMetricsMongo(databaseProvider).getDatabase());
  }

  /**
   * Test {@link QueryMetricsMongo#QueryMetricsMongo(MongoDatabase)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Database is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsMongo#QueryMetricsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new QueryMetricsMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void QueryMetricsMongo.<init>(MongoDatabase)"})
  void testNewQueryMetricsMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new QueryMetricsMongo(null).getDatabase());
  }

  /**
   * Test {@link QueryMetricsMongo#getCollection()}.
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoCollection QueryMetricsMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mock(MongoCollection.class));

    // Act
    new QueryMetricsMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection(eq("query-metrics"));
  }

  /**
   * Test {@link QueryMetricsMongo#getAll()}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsMongo#getAll()}
   */
  @Test
  @DisplayName("Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
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
    verify(databaseProvider).getCollection(eq("query-metrics"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link QueryMetricsMongo#getAllStoredEntitiesCoordinates()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsMongo#getAllStoredEntitiesCoordinates()}
   */
  @Test
  @DisplayName("Test getAllStoredEntitiesCoordinates(); then return Empty")
  @Tag("MaintainedByDiffblue")
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
    List<ProjectVersion> actualAllStoredEntitiesCoordinates = new QueryMetricsMongo(databaseProvider)
        .getAllStoredEntitiesCoordinates();

    // Assert
    verify(mongoCollection).aggregate(isA(List.class));
    verify(databaseProvider).getCollection(eq("query-metrics"));
    verify(aggregateIterable).forEach(isA(Consumer.class));
    assertTrue(actualAllStoredEntitiesCoordinates.isEmpty());
  }

  /**
   * Test {@link QueryMetricsMongo#get(String, String, String)}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsMongo#get(String, String, String)}
   */
  @Test
  @DisplayName("Test get(String, String, String); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
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
    List<VersionQueryMetric> actualGetResult = new QueryMetricsMongo(databaseProvider).get("42", "42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("query-metrics"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualGetResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsMongo#find(String, String)} with {@code groupId}, {@code artifactId}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsMongo#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'; then return Empty")
  @Tag("MaintainedByDiffblue")
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
    List<VersionQueryMetric> actualFindResult = new QueryMetricsMongo(databaseProvider).find("42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("query-metrics"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsMongo#insert(VersionQueryMetric)} with {@code VersionQueryMetric}.
   * <p>
   * Method under test: {@link QueryMetricsMongo#insert(VersionQueryMetric)}
   */
  @Test
  @DisplayName("Test insert(VersionQueryMetric) with 'VersionQueryMetric'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void QueryMetricsMongo.insert(VersionQueryMetric)"})
  void testInsertWithVersionQueryMetric() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    doNothing().when(mongoCollection).insertOne(Mockito.<Document>any());
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(databaseProvider);

    // Act
    queryMetricsMongo.insert(new VersionQueryMetric("42", "42", "42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));

    // Assert
    verify(mongoCollection).insertOne(isA(Document.class));
    verify(databaseProvider).getCollection(eq("query-metrics"));
  }

  /**
   * Test {@link QueryMetricsMongo#insert(VersionQueryMetric)} with {@code VersionQueryMetric}.
   * <ul>
   *   <li>Then calls {@link OperationExecutor#execute(WriteOperation, ReadConcern, ClientSession)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsMongo#insert(VersionQueryMetric)}
   */
  @Test
  @DisplayName("Test insert(VersionQueryMetric) with 'VersionQueryMetric'; then calls execute(WriteOperation, ReadConcern, ClientSession)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void QueryMetricsMongo.insert(VersionQueryMetric)"})
  void testInsertWithVersionQueryMetric_thenCallsExecute() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<Document>>any())).thenReturn(new DocumentCodec());
    OperationExecutor executor = mock(OperationExecutor.class);
    when(executor.execute(Mockito.<WriteOperation<BulkWriteResult>>any(), Mockito.<ReadConcern>any(),
        Mockito.<ClientSession>any())).thenReturn(null);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, executor));

    // Act
    queryMetricsMongo.insert(new VersionQueryMetric("42", "42", "42"));

    // Assert
    verify(executor).execute(isA(WriteOperation.class), isA(ReadConcern.class), isNull());
    verify(codecRegistry, atLeast(1)).get(isA(Class.class));
  }

  /**
   * Test {@link QueryMetricsMongo#insert(VersionQueryMetric)} with {@code VersionQueryMetric}.
   * <ul>
   *   <li>Then calls {@link MongoCollection#insertOne(Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsMongo#insert(VersionQueryMetric)}
   */
  @Test
  @DisplayName("Test insert(VersionQueryMetric) with 'VersionQueryMetric'; then calls insertOne(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void QueryMetricsMongo.insert(VersionQueryMetric)"})
  void testInsertWithVersionQueryMetric_thenCallsInsertOne() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    doNothing().when(mongoCollection).insertOne(Mockito.<Document>any());
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(databaseProvider);

    // Act
    queryMetricsMongo.insert(new VersionQueryMetric("42", "42", "42"));

    // Assert
    verify(mongoCollection).insertOne(isA(Document.class));
    verify(databaseProvider).getCollection(eq("query-metrics"));
  }

  /**
   * Test {@link QueryMetricsMongo#insert(VersionQueryMetric)} with {@code VersionQueryMetric}.
   * <ul>
   *   <li>When {@link VersionQueryMetric#VersionQueryMetric()}.</li>
   *   <li>Then calls {@link MongoCollection#insertOne(Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsMongo#insert(VersionQueryMetric)}
   */
  @Test
  @DisplayName("Test insert(VersionQueryMetric) with 'VersionQueryMetric'; when VersionQueryMetric(); then calls insertOne(Object)")
  @Tag("MaintainedByDiffblue")
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
    verify(databaseProvider).getCollection(eq("query-metrics"));
  }

  /**
   * Test {@link QueryMetricsMongo#findMetricsBefore(Date)}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsMongo#findMetricsBefore(Date)}
   */
  @Test
  @DisplayName("Test findMetricsBefore(Date); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
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
    List<VersionQueryMetric> actualFindMetricsBeforeResult = queryMetricsMongo
        .findMetricsBefore(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("query-metrics"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindMetricsBeforeResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsMongo#getKeyFilter(VersionQueryMetric)} with {@code data}.
   * <p>
   * Method under test: {@link QueryMetricsMongo#getKeyFilter(VersionQueryMetric)}
   */
  @Test
  @DisplayName("Test getKeyFilter(VersionQueryMetric) with 'data'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson QueryMetricsMongo.getKeyFilter(VersionQueryMetric)"})
  void testGetKeyFilterWithData() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    QueryMetricsMongo queryMetricsMongo = new QueryMetricsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act
    Bson actualKeyFilter = queryMetricsMongo.getKeyFilter(new VersionQueryMetric("42", "42", "42"));
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
    assertEquals(getResult, getResult2);
    assertEquals(getResult, getResult3);
  }

  /**
   * Test {@link QueryMetricsMongo#getKeyFilter(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <p>
   * Method under test: {@link QueryMetricsMongo#getKeyFilter(String, String, String)}
   */
  @Test
  @DisplayName("Test getKeyFilter(String, String, String) with 'groupId', 'artifactId', 'versionId'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson QueryMetricsMongo.getKeyFilter(String, String, String)"})
  void testGetKeyFilterWithGroupIdArtifactIdVersionId() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    Bson actualKeyFilter = new QueryMetricsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)))
                .getKeyFilter("42", "42", "42");
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
    assertEquals(getResult, getResult2);
    assertEquals(getResult, getResult3);
  }

  /**
   * Test {@link QueryMetricsMongo#buildIndexes()}.
   * <p>
   * Method under test: {@link QueryMetricsMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("MaintainedByDiffblue")
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
