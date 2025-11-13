package org.finos.legend.depot.store.mongo.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProjectsMongoDiffblueTest {
  /**
   * Test {@link ProjectsMongo#ProjectsMongo(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#ProjectsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new ProjectsMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProjectsMongo.<init>(MongoDatabase)"})
  void testNewProjectsMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new ProjectsMongo(null).getDatabase());
  }

  /**
   * Test {@link ProjectsMongo#ProjectsMongo(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#ProjectsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new ProjectsMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProjectsMongo.<init>(MongoDatabase)"})
  void testNewProjectsMongo_whenNull_thenReturnDatabaseIsNull2() {
    // Arrange, Act and Assert
    assertNull(new ProjectsMongo(null).getDatabase());
  }

  /**
   * Test {@link ProjectsMongo#buildIndexes()}.
   *
   * <p>Method under test: {@link ProjectsMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsMongo.buildIndexes()"})
  void testBuildIndexes() {
    // Arrange and Act
    List<IndexModel> actualBuildIndexesResult = ProjectsMongo.buildIndexes();

    // Assert
    assertEquals(1, actualBuildIndexesResult.size());
    IndexModel getResult = actualBuildIndexesResult.get(0);
    Bson keys = getResult.getKeys();
    assertTrue(keys instanceof Map);
    IndexOptions options = getResult.getOptions();
    assertEquals("groupId-artifactId", options.getName());
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
    assertTrue(options.isUnique());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("groupId"));
    BsonInt32 expectedGetResult = ((Map<String, BsonInt32>) keys).get("groupId");
    assertSame(expectedGetResult, ((Map<String, BsonInt32>) keys).get("artifactId"));
  }

  /**
   * Test {@link ProjectsMongo#getKeyFilter(StoreProjectData)} with {@code StoreProjectData}.
   *
   * <p>Method under test: {@link ProjectsMongo#getKeyFilter(StoreProjectData)}
   */
  @Test
  @DisplayName("Test getKeyFilter(StoreProjectData) with 'StoreProjectData'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson ProjectsMongo.getKeyFilter(StoreProjectData)"})
  void testGetKeyFilterWithStoreProjectData() {
    // Arrange
    ProjectsMongo projectsMongo = new ProjectsMongo(null);

    // Act
    Bson actualKeyFilter = projectsMongo.getKeyFilter(new StoreProjectData());
    Class<Object> forNameResult = Object.class;
    BsonDocument actualToBsonDocumentResult =
        actualKeyFilter.toBsonDocument(forNameResult, mock(CodecRegistry.class));

    // Assert
    assertEquals(2, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult instanceof BsonNull);
    assertEquals(BsonType.NULL, getResult.getBsonType());
    assertFalse(getResult.isString());
    assertTrue(getResult.isNull());
    assertSame(getResult, actualToBsonDocumentResult.get("artifactId"));
  }

  /**
   * Test {@link ProjectsMongo#getKeyFilter(StoreProjectData)} with {@code StoreProjectData}.
   *
   * <p>Method under test: {@link ProjectsMongo#getKeyFilter(StoreProjectData)}
   */
  @Test
  @DisplayName("Test getKeyFilter(StoreProjectData) with 'StoreProjectData'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson ProjectsMongo.getKeyFilter(StoreProjectData)"})
  void testGetKeyFilterWithStoreProjectData2() {
    // Arrange
    ProjectsMongo projectsMongo = new ProjectsMongo(null);
    StoreProjectData data = new StoreProjectData("myproject", "42", "42");

    // Act
    Bson actualKeyFilter = projectsMongo.getKeyFilter(data);
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult =
        actualKeyFilter.toBsonDocument(forNameResult, codecRegistry);

    // Assert
    verify(codecRegistry, atLeast(1)).get(isA(Class.class));
    assertEquals(2, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonString);
    BsonValue getResult2 = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult2 instanceof BsonString);
    assertEquals("42", ((BsonString) getResult2).getValue());
    assertEquals(BsonType.STRING, getResult2.getBsonType());
    assertFalse(getResult2.isNull());
    assertTrue(getResult2.isString());
    assertEquals(getResult2, getResult);
  }

  /**
   * Test {@link ProjectsMongo#getCollection()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoCollection ProjectsMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any()))
        .thenReturn(mock(MongoCollection.class));

    // Act
    new ProjectsMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection("project-configurations");
  }

  /**
   * Test {@link ProjectsMongo#validateNewData(StoreProjectData)} with {@code StoreProjectData}.
   *
   * <p>Method under test: {@link ProjectsMongo#validateNewData(StoreProjectData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectData) with 'StoreProjectData'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProjectsMongo.validateNewData(StoreProjectData)"})
  void testValidateNewDataWithStoreProjectData() {
    // Arrange
    ProjectsMongo projectsMongo = new ProjectsMongo(null);
    StoreProjectData data = new StoreProjectData("myproject", "42", "42");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsMongo.validateNewData(data));
  }

  /**
   * Test {@link ProjectsMongo#validateNewData(StoreProjectData)} with {@code StoreProjectData}.
   *
   * <p>Method under test: {@link ProjectsMongo#validateNewData(StoreProjectData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectData) with 'StoreProjectData'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProjectsMongo.validateNewData(StoreProjectData)"})
  void testValidateNewDataWithStoreProjectData2() {
    // Arrange
    ProjectsMongo projectsMongo = new ProjectsMongo(null);
    StoreProjectData data = new StoreProjectData("PROD-9", "42", "42");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsMongo.validateNewData(data));
  }

  /**
   * Test {@link ProjectsMongo#validateNewData(StoreProjectData)} with {@code StoreProjectData}.
   *
   * <p>Method under test: {@link ProjectsMongo#validateNewData(StoreProjectData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectData) with 'StoreProjectData'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProjectsMongo.validateNewData(StoreProjectData)"})
  void testValidateNewDataWithStoreProjectData3() {
    // Arrange
    ProjectsMongo projectsMongo = new ProjectsMongo(null);
    StoreProjectData data = new StoreProjectData("PROD-9", "", "42");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsMongo.validateNewData(data));
  }

  /**
   * Test {@link ProjectsMongo#validateNewData(StoreProjectData)} with {@code StoreProjectData}.
   *
   * <ul>
   *   <li>When {@link StoreProjectData#StoreProjectData()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#validateNewData(StoreProjectData)}
   */
  @Test
  @DisplayName(
      "Test validateNewData(StoreProjectData) with 'StoreProjectData'; when StoreProjectData()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProjectsMongo.validateNewData(StoreProjectData)"})
  void testValidateNewDataWithStoreProjectData_whenStoreProjectData() {
    // Arrange
    ProjectsMongo projectsMongo = new ProjectsMongo(null);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> projectsMongo.validateNewData(new StoreProjectData()));
  }

  /**
   * Test {@link ProjectsMongo#getAll()}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#getAll()}
   */
  @Test
  @DisplayName(
      "Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsMongo.getAll()"})
  void testGetAll_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoreProjectData> actualAll = new ProjectsMongo(databaseProvider).getAll();

    // Assert
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection("project-configurations");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link ProjectsMongo#getAll()}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#getAll()}
   */
  @Test
  @DisplayName(
      "Test getAll(); given FindIterable forEach(Consumer) throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsMongo.getAll()"})
  void testGetAll_givenFindIterableForEachThrowIllegalArgumentException() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doThrow(new IllegalArgumentException())
        .when(findIterable)
        .forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> new ProjectsMongo(databaseProvider).getAll());
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection("project-configurations");
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link ProjectsMongo#getAll()}.
   *
   * <ul>
   *   <li>Given {@link MongoCollection} {@link MongoCollection#find()} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#getAll()}
   */
  @Test
  @DisplayName("Test getAll(); given MongoCollection find() throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsMongo.getAll()"})
  void testGetAll_givenMongoCollectionFindThrowIllegalArgumentException() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenThrow(new IllegalArgumentException());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> new ProjectsMongo(databaseProvider).getAll());
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection("project-configurations");
  }

  /**
   * Test {@link ProjectsMongo#findByProjectId(String)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#findByProjectId(String)}
   */
  @Test
  @DisplayName(
      "Test findByProjectId(String); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsMongo.findByProjectId(String)"})
  void testFindByProjectId_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoreProjectData> actualFindByProjectIdResult =
        new ProjectsMongo(databaseProvider).findByProjectId("myproject");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("project-configurations");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindByProjectIdResult.isEmpty());
  }

  /**
   * Test {@link ProjectsMongo#findByProjectId(String)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#findByProjectId(String)}
   */
  @Test
  @DisplayName(
      "Test findByProjectId(String); given FindIterable forEach(Consumer) throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsMongo.findByProjectId(String)"})
  void testFindByProjectId_givenFindIterableForEachThrowIllegalArgumentException() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doThrow(new IllegalArgumentException())
        .when(findIterable)
        .forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new ProjectsMongo(databaseProvider).findByProjectId("myproject"));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("project-configurations");
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link ProjectsMongo#findByProjectId(String)}.
   *
   * <ul>
   *   <li>Given {@link MongoCollection} {@link MongoCollection#find(Bson)} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#findByProjectId(String)}
   */
  @Test
  @DisplayName(
      "Test findByProjectId(String); given MongoCollection find(Bson) throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsMongo.findByProjectId(String)"})
  void testFindByProjectId_givenMongoCollectionFindThrowIllegalArgumentException() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenThrow(new IllegalArgumentException());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new ProjectsMongo(databaseProvider).findByProjectId("myproject"));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("project-configurations");
  }

  /**
   * Test {@link ProjectsMongo#find(String, String)} with {@code groupId}, {@code artifactId}.
   *
   * <p>Method under test: {@link ProjectsMongo#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ProjectsMongo.find(String, String)"})
  void testFindWithGroupIdArtifactId() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenThrow(new IllegalArgumentException());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> new ProjectsMongo(databaseProvider).find("42", "42"));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("project-configurations");
  }

  /**
   * Test {@link ProjectsMongo#find(String, String)} with {@code groupId}, {@code artifactId}.
   *
   * <p>Method under test: {@link ProjectsMongo#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ProjectsMongo.find(String, String)"})
  void testFindWithGroupIdArtifactId2() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doThrow(new IllegalArgumentException())
        .when(findIterable)
        .forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> new ProjectsMongo(databaseProvider).find("42", "42"));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("project-configurations");
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link ProjectsMongo#find(String, String)} with {@code groupId}, {@code artifactId}.
   *
   * <ul>
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'; then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ProjectsMongo.find(String, String)"})
  void testFindWithGroupIdArtifactId_thenReturnNotPresent() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    Optional<StoreProjectData> actualFindResult =
        new ProjectsMongo(databaseProvider).find("42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("project-configurations");
    verify(findIterable).forEach(isA(Consumer.class));
    assertFalse(actualFindResult.isPresent());
  }

  /**
   * Test {@link ProjectsMongo#delete(String, String)} with {@code groupId}, {@code artifactId}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsMongo#delete(String, String)}
   */
  @Test
  @DisplayName(
      "Test delete(String, String) with 'groupId', 'artifactId'; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long ProjectsMongo.delete(String, String)"})
  void testDeleteWithGroupIdArtifactId_thenThrowIllegalArgumentException() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.deleteMany(Mockito.<Bson>any())).thenThrow(new IllegalArgumentException());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new ProjectsMongo(databaseProvider).delete("42", "42"));
    verify(mongoCollection).deleteMany(isA(Bson.class));
    verify(databaseProvider).getCollection("project-configurations");
  }
}
