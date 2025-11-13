package org.finos.legend.depot.store.mongo.generations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.mongodb.client.model.IndexModel;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.finos.legend.depot.store.model.generations.StoredFileGeneration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FileGenerationsMongoDiffblueTest {
  /**
   * Test {@link FileGenerationsMongo#FileGenerationsMongo(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationsMongo#FileGenerationsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName(
      "Test new FileGenerationsMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FileGenerationsMongo.<init>(MongoDatabase)"})
  void testNewFileGenerationsMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new FileGenerationsMongo(null).getDatabase());
  }

  /**
   * Test {@link FileGenerationsMongo#getCollection()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationsMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoCollection FileGenerationsMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any()))
        .thenReturn(mock(MongoCollection.class));

    // Act
    new FileGenerationsMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection("file-generations");
  }

  /**
   * Test {@link FileGenerationsMongo#getAll()}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationsMongo#getAll()}
   */
  @Test
  @DisplayName(
      "Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List FileGenerationsMongo.getAll()"})
  void testGetAll_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoredFileGeneration> actualAll = new FileGenerationsMongo(databaseProvider).getAll();

    // Assert
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection("file-generations");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link FileGenerationsMongo#buildIndexes()}.
   *
   * <p>Method under test: {@link FileGenerationsMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List FileGenerationsMongo.buildIndexes()"})
  void testBuildIndexes() {
    // Arrange and Act
    List<IndexModel> actualBuildIndexesResult = FileGenerationsMongo.buildIndexes();

    // Assert
    assertEquals(2, actualBuildIndexesResult.size());
    Bson keys = actualBuildIndexesResult.get(0).getKeys();
    assertTrue(keys instanceof Map);
    Bson keys2 = actualBuildIndexesResult.get(1).getKeys();
    assertTrue(keys2 instanceof Map);
    assertEquals(4, ((Map<String, BsonInt32>) keys).size());
    assertEquals(4, ((Map<String, BsonInt32>) keys2).size());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("artifactId"));
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("file.path"));
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("groupId"));
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("versionId"));
    assertTrue(((Map<String, BsonInt32>) keys2).containsKey("artifactId"));
    assertTrue(((Map<String, BsonInt32>) keys2).containsKey("groupId"));
    assertTrue(((Map<String, BsonInt32>) keys2).containsKey("path"));
    assertTrue(((Map<String, BsonInt32>) keys2).containsKey("versionId"));
  }

  /**
   * Test {@link FileGenerationsMongo#getKeyFilter(StoredFileGeneration)} with {@code
   * StoredFileGeneration}.
   *
   * <p>Method under test: {@link FileGenerationsMongo#getKeyFilter(StoredFileGeneration)}
   */
  @Test
  @DisplayName("Test getKeyFilter(StoredFileGeneration) with 'StoredFileGeneration'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson FileGenerationsMongo.getKeyFilter(StoredFileGeneration)"})
  void testGetKeyFilterWithStoredFileGeneration() {
    // Arrange
    FileGenerationsMongo fileGenerationsMongo = new FileGenerationsMongo(null);
    StoredFileGeneration data =
        new StoredFileGeneration(
            "42",
            "42",
            "42",
            "Path",
            "Type",
            new DepotGeneration("Path", "Not all who wander are lost"));

    // Act
    Bson actualKeyFilter = fileGenerationsMongo.getKeyFilter(data);
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult =
        actualKeyFilter.toBsonDocument(forNameResult, codecRegistry);

    // Assert
    verify(codecRegistry, atLeast(1)).get(isA(Class.class));
    assertEquals(4, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonString);
    assertTrue(actualToBsonDocumentResult.get("file.path") instanceof BsonString);
    BsonValue getResult2 = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult2 instanceof BsonString);
    BsonValue getResult3 = actualToBsonDocumentResult.get("versionId");
    assertTrue(getResult3 instanceof BsonString);
    assertEquals(getResult3, getResult);
    assertEquals(getResult3, getResult2);
  }

  /**
   * Test {@link FileGenerationsMongo#find(String, String, String)} with {@code groupId}, {@code
   * artifactId}, {@code versionId}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationsMongo#find(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List FileGenerationsMongo.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoredFileGeneration> actualFindResult =
        new FileGenerationsMongo(databaseProvider).find("42", "42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("file-generations");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link FileGenerationsMongo#findByElementPath(String, String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationsMongo#findByElementPath(String, String, String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test findByElementPath(String, String, String, String); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List FileGenerationsMongo.findByElementPath(String, String, String, String)"})
  void testFindByElementPath_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoredFileGeneration> actualFindByElementPathResult =
        new FileGenerationsMongo(databaseProvider)
            .findByElementPath("42", "42", "42", "Generation Path");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("file-generations");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindByElementPathResult.isEmpty());
  }

  /**
   * Test {@link FileGenerationsMongo#findByFilePath(String, String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationsMongo#findByFilePath(String, String, String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test findByFilePath(String, String, String, String); given FindIterable forEach(Consumer) does nothing; then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional FileGenerationsMongo.findByFilePath(String, String, String, String)"
  })
  void testFindByFilePath_givenFindIterableForEachDoesNothing_thenReturnNotPresent() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    Optional<StoredFileGeneration> actualFindByFilePathResult =
        new FileGenerationsMongo(databaseProvider)
            .findByFilePath("42", "42", "42", "/directory/foo.txt");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("file-generations");
    verify(findIterable).forEach(isA(Consumer.class));
    assertFalse(actualFindByFilePathResult.isPresent());
  }

  /**
   * Test {@link FileGenerationsMongo#findByType(String, String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationsMongo#findByType(String, String, String, String)}
   */
  @Test
  @DisplayName(
      "Test findByType(String, String, String, String); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List FileGenerationsMongo.findByType(String, String, String, String)"})
  void testFindByType_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoredFileGeneration> actualFindByTypeResult =
        new FileGenerationsMongo(databaseProvider).findByType("42", "42", "42", "Type");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("file-generations");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindByTypeResult.isEmpty());
  }

  /**
   * Test {@link FileGenerationsMongo#findByTypeAndElementPath(String, String, String, String,
   * String)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationsMongo#findByTypeAndElementPath(String, String,
   * String, String, String)}
   */
  @Test
  @DisplayName(
      "Test findByTypeAndElementPath(String, String, String, String, String); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List FileGenerationsMongo.findByTypeAndElementPath(String, String, String, String, String)"
  })
  void testFindByTypeAndElementPath_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoredFileGeneration> actualFindByTypeAndElementPathResult =
        new FileGenerationsMongo(databaseProvider)
            .findByTypeAndElementPath("42", "42", "42", "Type", "Element Path");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("file-generations");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindByTypeAndElementPathResult.isEmpty());
  }
}
