package org.finos.legend.depot.store.mongo.artifacts;

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
import org.finos.legend.depot.store.model.admin.artifacts.ArtifactFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ArtifactsFilesMongoDiffblueTest {
  /**
   * Test {@link ArtifactsFilesMongo#ArtifactsFilesMongo(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsFilesMongo#ArtifactsFilesMongo(MongoDatabase)}
   */
  @Test
  @DisplayName(
      "Test new ArtifactsFilesMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ArtifactsFilesMongo.<init>(MongoDatabase)"})
  void testNewArtifactsFilesMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new ArtifactsFilesMongo(null).getDatabase());
  }

  /**
   * Test {@link ArtifactsFilesMongo#ArtifactsFilesMongo(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsFilesMongo#ArtifactsFilesMongo(MongoDatabase)}
   */
  @Test
  @DisplayName(
      "Test new ArtifactsFilesMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ArtifactsFilesMongo.<init>(MongoDatabase)"})
  void testNewArtifactsFilesMongo_whenNull_thenReturnDatabaseIsNull2() {
    // Arrange, Act and Assert
    assertNull(new ArtifactsFilesMongo(null).getDatabase());
  }

  /**
   * Test {@link ArtifactsFilesMongo#buildIndexes()}.
   *
   * <p>Method under test: {@link ArtifactsFilesMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ArtifactsFilesMongo.buildIndexes()"})
  void testBuildIndexes() {
    // Arrange and Act
    List<IndexModel> actualBuildIndexesResult = ArtifactsFilesMongo.buildIndexes();

    // Assert
    assertEquals(1, actualBuildIndexesResult.size());
    IndexModel getResult = actualBuildIndexesResult.get(0);
    Bson keys = getResult.getKeys();
    assertTrue(keys instanceof Map);
    IndexOptions options = getResult.getOptions();
    assertEquals("path", options.getName());
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
    assertTrue(options.isUnique());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("path"));
  }

  /**
   * Test {@link ArtifactsFilesMongo#getCollection()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsFilesMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoCollection ArtifactsFilesMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any()))
        .thenReturn(mock(MongoCollection.class));

    // Act
    new ArtifactsFilesMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection("artifacts-files");
  }

  /**
   * Test {@link ArtifactsFilesMongo#getKeyFilter(ArtifactFile)} with {@code ArtifactFile}.
   *
   * <p>Method under test: {@link ArtifactsFilesMongo#getKeyFilter(ArtifactFile)}
   */
  @Test
  @DisplayName("Test getKeyFilter(ArtifactFile) with 'ArtifactFile'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson ArtifactsFilesMongo.getKeyFilter(ArtifactFile)"})
  void testGetKeyFilterWithArtifactFile() {
    // Arrange
    ArtifactsFilesMongo artifactsFilesMongo = new ArtifactsFilesMongo(null);

    // Act
    Bson actualKeyFilter = artifactsFilesMongo.getKeyFilter(new ArtifactFile());
    Class<Object> forNameResult = Object.class;
    BsonDocument actualToBsonDocumentResult =
        actualKeyFilter.toBsonDocument(forNameResult, mock(CodecRegistry.class));

    // Assert
    assertEquals(1, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("path");
    assertTrue(getResult instanceof BsonNull);
    assertEquals(BsonType.NULL, getResult.getBsonType());
    assertFalse(getResult.isString());
    assertTrue(getResult.isNull());
  }

  /**
   * Test {@link ArtifactsFilesMongo#getKeyFilter(ArtifactFile)} with {@code ArtifactFile}.
   *
   * <p>Method under test: {@link ArtifactsFilesMongo#getKeyFilter(ArtifactFile)}
   */
  @Test
  @DisplayName("Test getKeyFilter(ArtifactFile) with 'ArtifactFile'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson ArtifactsFilesMongo.getKeyFilter(ArtifactFile)"})
  void testGetKeyFilterWithArtifactFile2() {
    // Arrange
    ArtifactsFilesMongo artifactsFilesMongo = new ArtifactsFilesMongo(null);

    // Act
    Bson actualKeyFilter = artifactsFilesMongo.getKeyFilter(new ArtifactFile("Path", "Check Sum"));
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult =
        actualKeyFilter.toBsonDocument(forNameResult, codecRegistry);

    // Assert
    verify(codecRegistry).get(isA(Class.class));
    assertEquals(1, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("path");
    assertTrue(getResult instanceof BsonString);
    assertEquals("Path", ((BsonString) getResult).getValue());
    assertEquals(BsonType.STRING, getResult.getBsonType());
    assertFalse(getResult.isNull());
    assertTrue(getResult.isString());
  }

  /**
   * Test {@link ArtifactsFilesMongo#find(String)} with {@code path}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsFilesMongo#find(String)}
   */
  @Test
  @DisplayName(
      "Test find(String) with 'path'; given FindIterable forEach(Consumer) does nothing; then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ArtifactsFilesMongo.find(String)"})
  void testFindWithPath_givenFindIterableForEachDoesNothing_thenReturnNotPresent() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);

    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    Optional<ArtifactFile> actualFindResult =
        new ArtifactsFilesMongo(databaseProvider).find("Path");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection("artifacts-files");
    verify(findIterable).forEach(isA(Consumer.class));
    assertFalse(actualFindResult.isPresent());
  }
}
