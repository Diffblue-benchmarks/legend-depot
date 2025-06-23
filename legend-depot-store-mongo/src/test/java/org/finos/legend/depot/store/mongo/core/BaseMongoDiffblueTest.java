package org.finos.legend.depot.store.mongo.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.bson.BsonInt32;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.finos.legend.depot.store.StoreException;
import org.finos.legend.depot.store.model.HasIdentifier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BaseMongoDiffblueTest {
  /**
   * Test {@link BaseMongo#buildDocument(HasIdentifier)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HasIdentifier} {@link HasIdentifier#getId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseMongo#buildDocument(HasIdentifier)}
   */
  @Test
  @DisplayName("Test buildDocument(HasIdentifier); given '42'; when HasIdentifier getId() return '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Document BaseMongo.buildDocument(HasIdentifier)"})
  void testBuildDocument_given42_whenHasIdentifierGetIdReturn42() {
    // Arrange
    HasIdentifier hasIdentifier = mock(HasIdentifier.class);
    when(hasIdentifier.getId()).thenReturn("42");

    // Act and Assert
    assertThrows(StoreException.class, () -> BaseMongo.buildDocument(hasIdentifier));
  }

  /**
   * Test {@link BaseMongo#buildDocument(HasIdentifier)}.
   * <ul>
   *   <li>Given {@link StoreException#StoreException(String)} with error is {@code An error occurredError}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseMongo#buildDocument(HasIdentifier)}
   */
  @Test
  @DisplayName("Test buildDocument(HasIdentifier); given StoreException(String) with error is 'An error occurredError'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Document BaseMongo.buildDocument(HasIdentifier)"})
  void testBuildDocument_givenStoreExceptionWithErrorIsAnErrorOccurredError() {
    // Arrange
    HasIdentifier hasIdentifier = mock(HasIdentifier.class);
    when(hasIdentifier.getId()).thenThrow(new StoreException("An error occurredError"));

    // Act and Assert
    assertThrows(StoreException.class, () -> BaseMongo.buildDocument(hasIdentifier));
  }

  /**
   * Test {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}.
   * <p>
   * Method under test: {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}
   */
  @Test
  @DisplayName("Test createIndexesIfAbsent(MongoDatabase, String, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BaseMongo.createIndexesIfAbsent(MongoDatabase, String, List)"})
  void testCreateIndexesIfAbsent() {
    // Arrange
    ListIndexesIterable<Document> listIndexesIterable = mock(ListIndexesIterable.class);
    doNothing().when(listIndexesIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.createIndexes(Mockito.<List<IndexModel>>any())).thenReturn(new ArrayList<>());
    when(mongoCollection.listIndexes()).thenReturn(listIndexesIterable);
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    ArrayList<IndexModel> candidateIndexes = new ArrayList<>();
    candidateIndexes.add(BaseMongo.buildIndex("Index Name", new IndexOptions(), "Field Names"));

    // Act
    List<String> actualCreateIndexesIfAbsentResult = BaseMongo.createIndexesIfAbsent(mongoDatabase, "Collection",
        candidateIndexes);

    // Assert
    verify(mongoCollection).createIndexes(isA(List.class));
    verify(mongoCollection).listIndexes();
    verify(mongoDatabase).getCollection(eq("Collection"));
    verify(listIndexesIterable).forEach(isA(Consumer.class));
    assertTrue(actualCreateIndexesIfAbsentResult.isEmpty());
  }

  /**
   * Test {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}.
   * <p>
   * Method under test: {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}
   */
  @Test
  @DisplayName("Test createIndexesIfAbsent(MongoDatabase, String, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BaseMongo.createIndexesIfAbsent(MongoDatabase, String, List)"})
  void testCreateIndexesIfAbsent2() {
    // Arrange
    ListIndexesIterable<Document> listIndexesIterable = mock(ListIndexesIterable.class);
    doNothing().when(listIndexesIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.createIndexes(Mockito.<List<IndexModel>>any())).thenReturn(new ArrayList<>());
    when(mongoCollection.listIndexes()).thenReturn(listIndexesIterable);
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    ArrayList<IndexModel> candidateIndexes = new ArrayList<>();
    candidateIndexes.add(BaseMongo.buildIndex("Index Name", new IndexOptions(), "Field Names"));
    candidateIndexes.add(BaseMongo.buildIndex("Index Name", new IndexOptions(), "Field Names"));

    // Act
    List<String> actualCreateIndexesIfAbsentResult = BaseMongo.createIndexesIfAbsent(mongoDatabase, "Collection",
        candidateIndexes);

    // Assert
    verify(mongoCollection).createIndexes(isA(List.class));
    verify(mongoCollection).listIndexes();
    verify(mongoDatabase).getCollection(eq("Collection"));
    verify(listIndexesIterable).forEach(isA(Consumer.class));
    assertTrue(actualCreateIndexesIfAbsentResult.isEmpty());
  }

  /**
   * Test {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}.
   * <ul>
   *   <li>Then calls {@link IndexModel#getOptions()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}
   */
  @Test
  @DisplayName("Test createIndexesIfAbsent(MongoDatabase, String, List); then calls getOptions()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BaseMongo.createIndexesIfAbsent(MongoDatabase, String, List)"})
  void testCreateIndexesIfAbsent_thenCallsGetOptions() {
    // Arrange
    ListIndexesIterable<Document> listIndexesIterable = mock(ListIndexesIterable.class);
    doNothing().when(listIndexesIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.createIndexes(Mockito.<List<IndexModel>>any())).thenReturn(new ArrayList<>());
    when(mongoCollection.listIndexes()).thenReturn(listIndexesIterable);
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    IndexModel indexModel = mock(IndexModel.class);
    when(indexModel.getOptions()).thenReturn(new IndexOptions());

    ArrayList<IndexModel> candidateIndexes = new ArrayList<>();
    candidateIndexes.add(indexModel);

    // Act
    List<String> actualCreateIndexesIfAbsentResult = BaseMongo.createIndexesIfAbsent(mongoDatabase, "Collection",
        candidateIndexes);

    // Assert
    verify(mongoCollection).createIndexes(isA(List.class));
    verify(mongoCollection).listIndexes();
    verify(mongoDatabase).getCollection(eq("Collection"));
    verify(indexModel).getOptions();
    verify(listIndexesIterable).forEach(isA(Consumer.class));
    assertTrue(actualCreateIndexesIfAbsentResult.isEmpty());
  }

  /**
   * Test {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}
   */
  @Test
  @DisplayName("Test createIndexesIfAbsent(MongoDatabase, String, List); when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BaseMongo.createIndexesIfAbsent(MongoDatabase, String, List)"})
  void testCreateIndexesIfAbsent_whenArrayList_thenReturnEmpty() {
    // Arrange
    ListIndexesIterable<Document> listIndexesIterable = mock(ListIndexesIterable.class);
    doNothing().when(listIndexesIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.listIndexes()).thenReturn(listIndexesIterable);
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<String> actualCreateIndexesIfAbsentResult = BaseMongo.createIndexesIfAbsent(mongoDatabase, "Collection",
        new ArrayList<>());

    // Assert
    verify(mongoCollection).listIndexes();
    verify(mongoDatabase).getCollection(eq("Collection"));
    verify(listIndexesIterable).forEach(isA(Consumer.class));
    assertTrue(actualCreateIndexesIfAbsentResult.isEmpty());
  }

  /**
   * Test {@link BaseMongo#buildIndex(String, String[])} with {@code indexName}, {@code fieldNames}.
   * <p>
   * Method under test: {@link BaseMongo#buildIndex(String, String[])}
   */
  @Test
  @DisplayName("Test buildIndex(String, String[]) with 'indexName', 'fieldNames'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"IndexModel BaseMongo.buildIndex(String, String[])"})
  void testBuildIndexWithIndexNameFieldNames() {
    // Arrange and Act
    IndexModel actualBuildIndexResult = BaseMongo.buildIndex("Index Name", "Field Names");

    // Assert
    Bson keys = actualBuildIndexResult.getKeys();
    assertTrue(keys instanceof Map);
    IndexOptions options = actualBuildIndexResult.getOptions();
    assertEquals("Index Name", options.getName());
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
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("Field Names"));
  }

  /**
   * Test {@link BaseMongo#buildIndex(String, IndexOptions, String[])} with {@code indexName}, {@code indexOptions}, {@code fieldNames}.
   * <ul>
   *   <li>Then Keys return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseMongo#buildIndex(String, IndexOptions, String[])}
   */
  @Disabled("Failed pitest")
  @Test
  @DisplayName("Test buildIndex(String, IndexOptions, String[]) with 'indexName', 'indexOptions', 'fieldNames'; then Keys return Map")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"IndexModel BaseMongo.buildIndex(String, IndexOptions, String[])"})
  void testBuildIndexWithIndexNameIndexOptionsFieldNames_thenKeysReturnMap() {
    // Arrange
    IndexOptions indexOptions = new IndexOptions();

    // Act
    IndexModel actualBuildIndexResult = BaseMongo.buildIndex("Index Name", indexOptions, "Field Names");

    // Assert
    Bson keys = actualBuildIndexResult.getKeys();
    assertTrue(keys instanceof Map);
    assertEquals("Index Name", indexOptions.getName());
    assertEquals(1, ((Map<String, BsonInt32>) keys).size());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("Field Names"));
    assertSame(indexOptions, actualBuildIndexResult.getOptions());
  }

  /**
   * Test {@link BaseMongo#buildIndex(String, boolean, String[])} with {@code indexName}, {@code isUnique}, {@code fieldNames}.
   * <p>
   * Method under test: {@link BaseMongo#buildIndex(String, boolean, String[])}
   */
  @Test
  @DisplayName("Test buildIndex(String, boolean, String[]) with 'indexName', 'isUnique', 'fieldNames'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"IndexModel BaseMongo.buildIndex(String, boolean, String[])"})
  void testBuildIndexWithIndexNameIsUniqueFieldNames() {
    // Arrange and Act
    IndexModel actualBuildIndexResult = BaseMongo.buildIndex("Index Name", true, "Field Names");

    // Assert
    Bson keys = actualBuildIndexResult.getKeys();
    assertTrue(keys instanceof Map);
    IndexOptions options = actualBuildIndexResult.getOptions();
    assertEquals("Index Name", options.getName());
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
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("Field Names"));
  }
}
