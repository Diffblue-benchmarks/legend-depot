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
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProjectsVersionsMongoDiffblueTest {
  /**
   * Test {@link ProjectsVersionsMongo#ProjectsVersionsMongo(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#ProjectsVersionsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName(
      "Test new ProjectsVersionsMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProjectsVersionsMongo.<init>(MongoDatabase)"})
  void testNewProjectsVersionsMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new ProjectsVersionsMongo(null).getDatabase());
  }

  /**
   * Test {@link ProjectsVersionsMongo#buildIndexes()}.
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.buildIndexes()"})
  void testBuildIndexes() {
    // Arrange and Act
    List<IndexModel> actualBuildIndexesResult = ProjectsVersionsMongo.buildIndexes();

    // Assert
    assertEquals(1, actualBuildIndexesResult.size());
    Bson keys = actualBuildIndexesResult.get(0).getKeys();
    assertTrue(keys instanceof Map);
    assertEquals(3, ((Map<String, BsonInt32>) keys).size());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("artifactId"));
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("groupId"));
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("versionId"));
  }

  /**
   * Test {@link ProjectsVersionsMongo#getAll()}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#getAll()}
   */
  @Test
  @DisplayName(
      "Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.getAll()"})
  void testGetAll_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoreProjectVersionData> actualAll = new ProjectsVersionsMongo(databaseProvider).getAll();

    // Assert
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection("versions");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link ProjectsVersionsMongo#getAll()}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#getAll()}
   */
  @Test
  @DisplayName(
      "Test getAll(); given FindIterable forEach(Consumer) throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.getAll()"})
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
        IllegalArgumentException.class, () -> new ProjectsVersionsMongo(databaseProvider).getAll());
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection("versions");
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link ProjectsVersionsMongo#getAll()}.
   *
   * <ul>
   *   <li>Given {@link MongoCollection} {@link MongoCollection#find()} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#getAll()}
   */
  @Test
  @DisplayName("Test getAll(); given MongoCollection find() throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.getAll()"})
  void testGetAll_givenMongoCollectionFindThrowIllegalArgumentException() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenThrow(new IllegalArgumentException());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> new ProjectsVersionsMongo(databaseProvider).getAll());
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection("versions");
  }

  /**
   * Test {@link ProjectsVersionsMongo#findByUpdatedDate(long, long)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#findByUpdatedDate(long, long)}
   */
  @Test
  @DisplayName(
      "Test findByUpdatedDate(long, long); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.findByUpdatedDate(long, long)"})
  void testFindByUpdatedDate_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoreProjectVersionData> actualFindByUpdatedDateResult =
        new ProjectsVersionsMongo(databaseProvider).findByUpdatedDate(1L, 1L);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindByUpdatedDateResult.isEmpty());
  }

  /**
   * Test {@link ProjectsVersionsMongo#findByUpdatedDate(long, long)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#findByUpdatedDate(long, long)}
   */
  @Test
  @DisplayName(
      "Test findByUpdatedDate(long, long); given FindIterable forEach(Consumer) throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.findByUpdatedDate(long, long)"})
  void testFindByUpdatedDate_givenFindIterableForEachThrowIllegalArgumentException() {
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
        () -> new ProjectsVersionsMongo(databaseProvider).findByUpdatedDate(1L, 1L));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link ProjectsVersionsMongo#findByUpdatedDate(long, long)}.
   *
   * <ul>
   *   <li>Given {@link MongoCollection} {@link MongoCollection#find(Bson)} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#findByUpdatedDate(long, long)}
   */
  @Test
  @DisplayName(
      "Test findByUpdatedDate(long, long); given MongoCollection find(Bson) throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.findByUpdatedDate(long, long)"})
  void testFindByUpdatedDate_givenMongoCollectionFindThrowIllegalArgumentException() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenThrow(new IllegalArgumentException());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new ProjectsVersionsMongo(databaseProvider).findByUpdatedDate(1L, 1L));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String)} with {@code groupId}, {@code
   * artifactId}.
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.find(String, String)"})
  void testFindWithGroupIdArtifactId() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenThrow(new IllegalArgumentException());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new ProjectsVersionsMongo(databaseProvider).find("42", "42"));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String)} with {@code groupId}, {@code
   * artifactId}.
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.find(String, String)"})
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
        IllegalArgumentException.class,
        () -> new ProjectsVersionsMongo(databaseProvider).find("42", "42"));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String, String)} with {@code groupId}, {@code
   * artifactId}, {@code versionId}.
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#find(String, String, String)}
   */
  @Test
  @DisplayName("Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ProjectsVersionsMongo.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenThrow(new IllegalArgumentException());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new ProjectsVersionsMongo(databaseProvider).find("42", "42", "42"));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String, String)} with {@code groupId}, {@code
   * artifactId}, {@code versionId}.
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#find(String, String, String)}
   */
  @Test
  @DisplayName("Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ProjectsVersionsMongo.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId2() {
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
        () -> new ProjectsVersionsMongo(databaseProvider).find("42", "42", "42"));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String, String)} with {@code groupId}, {@code
   * artifactId}, {@code versionId}.
   *
   * <ul>
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#find(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'; then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ProjectsVersionsMongo.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId_thenReturnNotPresent() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    Optional<StoreProjectVersionData> actualFindResult =
        new ProjectsVersionsMongo(databaseProvider).find("42", "42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
    verify(findIterable).forEach(isA(Consumer.class));
    assertFalse(actualFindResult.isPresent());
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String, String)} with {@code groupId}, {@code
   * artifactId}, {@code versionId}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#find(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'; when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ProjectsVersionsMongo.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId_whenEmptyString() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new ProjectsVersionsMongo(mock(MongoDatabaseImpl.class)).find("42", "42", ""));
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String, String)} with {@code groupId}, {@code
   * artifactId}, {@code versionId}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#find(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ProjectsVersionsMongo.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId_whenNull() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new ProjectsVersionsMongo(mock(MongoDatabaseImpl.class)).find("42", "42", null));
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String)} with {@code groupId}, {@code
   * artifactId}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.find(String, String)"})
  void testFindWithGroupIdArtifactId_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoreProjectVersionData> actualFindResult =
        new ProjectsVersionsMongo(databaseProvider).find("42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link ProjectsVersionsMongo#findVersion(Boolean)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#findVersion(Boolean)}
   */
  @Test
  @DisplayName(
      "Test findVersion(Boolean); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.findVersion(Boolean)"})
  void testFindVersion_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoreProjectVersionData> actualFindVersionResult =
        new ProjectsVersionsMongo(databaseProvider).findVersion(true);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindVersionResult.isEmpty());
  }

  /**
   * Test {@link ProjectsVersionsMongo#findVersion(Boolean)}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#findVersion(Boolean)}
   */
  @Test
  @DisplayName(
      "Test findVersion(Boolean); given FindIterable forEach(Consumer) throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.findVersion(Boolean)"})
  void testFindVersion_givenFindIterableForEachThrowIllegalArgumentException() {
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
        () -> new ProjectsVersionsMongo(databaseProvider).findVersion(true));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link ProjectsVersionsMongo#findVersion(Boolean)}.
   *
   * <ul>
   *   <li>Given {@link MongoCollection} {@link MongoCollection#find(Bson)} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#findVersion(Boolean)}
   */
  @Test
  @DisplayName(
      "Test findVersion(Boolean); given MongoCollection find(Bson) throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ProjectsVersionsMongo.findVersion(Boolean)"})
  void testFindVersion_givenMongoCollectionFindThrowIllegalArgumentException() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenThrow(new IllegalArgumentException());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new ProjectsVersionsMongo(databaseProvider).findVersion(true));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
  }

  /**
   * Test {@link ProjectsVersionsMongo#getVersionCount(String, String)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#getVersionCount(String, String)}
   */
  @Test
  @DisplayName("Test getVersionCount(String, String); then return three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long ProjectsVersionsMongo.getVersionCount(String, String)"})
  void testGetVersionCount_thenReturnThree() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.countDocuments(Mockito.<Bson>any())).thenReturn(3L);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    long actualVersionCount =
        new ProjectsVersionsMongo(databaseProvider).getVersionCount("42", "42");

    // Assert
    verify(mongoCollection).countDocuments(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
    assertEquals(3L, actualVersionCount);
  }

  /**
   * Test {@link ProjectsVersionsMongo#delete(String, String, String)} with {@code groupId}, {@code
   * artifactId}, {@code versionId}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#delete(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test delete(String, String, String) with 'groupId', 'artifactId', 'versionId'; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long ProjectsVersionsMongo.delete(String, String, String)"})
  void testDeleteWithGroupIdArtifactIdVersionId_thenThrowIllegalArgumentException() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.deleteMany(Mockito.<Bson>any())).thenThrow(new IllegalArgumentException());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new ProjectsVersionsMongo(databaseProvider).delete("42", "42", "42"));
    verify(mongoCollection).deleteMany(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
  }

  /**
   * Test {@link ProjectsVersionsMongo#delete(String, String)} with {@code groupId}, {@code
   * artifactId}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#delete(String, String)}
   */
  @Test
  @DisplayName(
      "Test delete(String, String) with 'groupId', 'artifactId'; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long ProjectsVersionsMongo.delete(String, String)"})
  void testDeleteWithGroupIdArtifactId_thenThrowIllegalArgumentException() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.deleteMany(Mockito.<Bson>any())).thenThrow(new IllegalArgumentException());

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new ProjectsVersionsMongo(databaseProvider).delete("42", "42"));
    verify(mongoCollection).deleteMany(isA(Bson.class));
    verify(databaseProvider).getCollection("versions");
  }

  /**
   * Test {@link ProjectsVersionsMongo#getCollection()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoCollection ProjectsVersionsMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any()))
        .thenReturn(mock(MongoCollection.class));

    // Act
    new ProjectsVersionsMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection("versions");
  }

  /**
   * Test {@link ProjectsVersionsMongo#getKeyFilter(StoreProjectVersionData)} with {@code
   * StoreProjectVersionData}.
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#getKeyFilter(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test getKeyFilter(StoreProjectVersionData) with 'StoreProjectVersionData'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson ProjectsVersionsMongo.getKeyFilter(StoreProjectVersionData)"})
  void testGetKeyFilterWithStoreProjectVersionData() {
    // Arrange
    ProjectsVersionsMongo projectsVersionsMongo = new ProjectsVersionsMongo(null);

    // Act
    Bson actualKeyFilter = projectsVersionsMongo.getKeyFilter(new StoreProjectVersionData());
    Class<Object> forNameResult = Object.class;
    BsonDocument actualToBsonDocumentResult =
        actualKeyFilter.toBsonDocument(forNameResult, mock(CodecRegistry.class));

    // Assert
    assertEquals(3, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("versionId");
    assertTrue(getResult instanceof BsonNull);
    assertEquals(BsonType.NULL, getResult.getBsonType());
    assertFalse(getResult.isString());
    assertTrue(getResult.isNull());
    assertSame(getResult, actualToBsonDocumentResult.get("artifactId"));
    assertSame(getResult, actualToBsonDocumentResult.get("groupId"));
  }

  /**
   * Test {@link ProjectsVersionsMongo#getKeyFilter(StoreProjectVersionData)} with {@code
   * StoreProjectVersionData}.
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#getKeyFilter(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test getKeyFilter(StoreProjectVersionData) with 'StoreProjectVersionData'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson ProjectsVersionsMongo.getKeyFilter(StoreProjectVersionData)"})
  void testGetKeyFilterWithStoreProjectVersionData2() {
    // Arrange
    ProjectsVersionsMongo projectsVersionsMongo = new ProjectsVersionsMongo(null);
    StoreProjectVersionData data = new StoreProjectVersionData("42", "42", "42");

    // Act
    Bson actualKeyFilter = projectsVersionsMongo.getKeyFilter(data);
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
    assertEquals("42", ((BsonString) getResult3).getValue());
    assertEquals(BsonType.STRING, getResult3.getBsonType());
    assertFalse(getResult3.isNull());
    assertTrue(getResult3.isString());
    assertEquals(getResult3, getResult);
    assertEquals(getResult3, getResult2);
  }

  /**
   * Test {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)} with {@code
   * StoreProjectVersionData}.
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectVersionData) with 'StoreProjectVersionData'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProjectsVersionsMongo.validateNewData(StoreProjectVersionData)"})
  void testValidateNewDataWithStoreProjectVersionData() {
    // Arrange
    ProjectsVersionsMongo projectsVersionsMongo = new ProjectsVersionsMongo(null);
    StoreProjectVersionData data = new StoreProjectVersionData("42", "42", "42");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsVersionsMongo.validateNewData(data));
  }

  /**
   * Test {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)} with {@code
   * StoreProjectVersionData}.
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectVersionData) with 'StoreProjectVersionData'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProjectsVersionsMongo.validateNewData(StoreProjectVersionData)"})
  void testValidateNewDataWithStoreProjectVersionData2() {
    // Arrange
    ProjectsVersionsMongo projectsVersionsMongo = new ProjectsVersionsMongo(null);
    StoreProjectVersionData data = new StoreProjectVersionData("", "42", "42");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsVersionsMongo.validateNewData(data));
  }

  /**
   * Test {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)} with {@code
   * StoreProjectVersionData}.
   *
   * <ul>
   *   <li>When {@link StoreProjectVersionData#StoreProjectVersionData()}.
   * </ul>
   *
   * <p>Method under test: {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)}
   */
  @Test
  @DisplayName(
      "Test validateNewData(StoreProjectVersionData) with 'StoreProjectVersionData'; when StoreProjectVersionData()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProjectsVersionsMongo.validateNewData(StoreProjectVersionData)"})
  void testValidateNewDataWithStoreProjectVersionData_whenStoreProjectVersionData() {
    // Arrange
    ProjectsVersionsMongo projectsVersionsMongo = new ProjectsVersionsMongo(null);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> projectsVersionsMongo.validateNewData(new StoreProjectVersionData()));
  }
}
