package org.finos.legend.depot.store.mongo.core;

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
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.bson.BsonType;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BaseMongoDiffblueTest {
  /**
   * Method under test:
   * {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}
   */
  @Test
  void testCreateIndexesIfAbsent() {
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
   * Method under test:
   * {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}
   */
  @Test
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
   * Method under test:
   * {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}
   */
  @Test
  void testCreateIndexesIfAbsent3() {
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
   * Method under test:
   * {@link BaseMongo#createIndexesIfAbsent(MongoDatabase, String, List)}
   */
  @Test
  void testCreateIndexesIfAbsent4() {
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
   * Method under test: {@link BaseMongo#convert(ObjectMapper, Document, Class)}
   */
  @Test
  void testConvert() {
    // Arrange
    ObjectMapper mapper = new ObjectMapper();
    Document document = new Document();
    Class<Object> clazz = Object.class;

    // Act
    Object actualConvertResult = BaseMongo.convert(mapper, document, clazz);

    // Assert
    assertTrue(actualConvertResult instanceof Map);
    assertTrue(((Map<Object, Object>) actualConvertResult).isEmpty());
  }

  /**
   * Method under test: {@link BaseMongo#convert(ObjectMapper, Document, Class)}
   */
  @Test
  void testConvert2() {
    // Arrange
    ObjectMapper mapper = new ObjectMapper();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    mapper.addMixIn(target, mixinSource);
    Document document = new Document();
    Class<Object> clazz = Object.class;

    // Act
    Object actualConvertResult = BaseMongo.convert(mapper, document, clazz);

    // Assert
    assertTrue(actualConvertResult instanceof Map);
    assertTrue(((Map<Object, Object>) actualConvertResult).isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseMongo#buildIndex(String, IndexOptions, String[])}
   */
  @Test
  void testBuildIndex() {
    // Arrange
    IndexOptions indexOptions = new IndexOptions();

    // Act
    IndexModel actualBuildIndexResult = BaseMongo.buildIndex("Index Name", indexOptions, "Field Names");

    // Assert
    Bson keys = actualBuildIndexResult.getKeys();
    assertTrue(keys instanceof Map);
    assertEquals("Index Name", indexOptions.getName());
    assertEquals(1, ((Map<String, BsonInt32>) keys).size());
    BsonInt32 getResult = ((Map<String, BsonInt32>) keys).get("Field Names");
    assertEquals(1, getResult.getValue());
    assertEquals(BsonType.INT32, getResult.getBsonType());
    assertFalse(getResult.isArray());
    assertFalse(getResult.isBinary());
    assertFalse(getResult.isBoolean());
    assertFalse(getResult.isDBPointer());
    assertFalse(getResult.isDateTime());
    assertFalse(getResult.isDecimal128());
    assertFalse(getResult.isDocument());
    assertFalse(getResult.isDouble());
    assertFalse(getResult.isInt64());
    assertFalse(getResult.isJavaScript());
    assertFalse(getResult.isJavaScriptWithScope());
    assertFalse(getResult.isNull());
    assertFalse(getResult.isObjectId());
    assertFalse(getResult.isRegularExpression());
    assertFalse(getResult.isString());
    assertFalse(getResult.isSymbol());
    assertFalse(getResult.isTimestamp());
    assertTrue(getResult.isInt32());
    assertTrue(getResult.isNumber());
    assertSame(indexOptions, actualBuildIndexResult.getOptions());
  }

  /**
   * Method under test:
   * {@link BaseMongo#buildIndex(String, IndexOptions, String[])}
   */
  @Test
  void testBuildIndex2() {
    // Arrange
    IndexOptions indexOptions = new IndexOptions();
    indexOptions.weights(mock(Bson.class));

    // Act
    IndexModel actualBuildIndexResult = BaseMongo.buildIndex("Index Name", indexOptions, "Field Names");

    // Assert
    Bson keys = actualBuildIndexResult.getKeys();
    assertTrue(keys instanceof Map);
    assertEquals("Index Name", indexOptions.getName());
    assertEquals(1, ((Map<String, BsonInt32>) keys).size());
    BsonInt32 getResult = ((Map<String, BsonInt32>) keys).get("Field Names");
    assertEquals(1, getResult.getValue());
    assertEquals(BsonType.INT32, getResult.getBsonType());
    assertFalse(getResult.isArray());
    assertFalse(getResult.isBinary());
    assertFalse(getResult.isBoolean());
    assertFalse(getResult.isDBPointer());
    assertFalse(getResult.isDateTime());
    assertFalse(getResult.isDecimal128());
    assertFalse(getResult.isDocument());
    assertFalse(getResult.isDouble());
    assertFalse(getResult.isInt64());
    assertFalse(getResult.isJavaScript());
    assertFalse(getResult.isJavaScriptWithScope());
    assertFalse(getResult.isNull());
    assertFalse(getResult.isObjectId());
    assertFalse(getResult.isRegularExpression());
    assertFalse(getResult.isString());
    assertFalse(getResult.isSymbol());
    assertFalse(getResult.isTimestamp());
    assertTrue(getResult.isInt32());
    assertTrue(getResult.isNumber());
    assertSame(indexOptions, actualBuildIndexResult.getOptions());
  }

  /**
   * Method under test: {@link BaseMongo#buildIndex(String, boolean, String[])}
   */
  @Test
  void testBuildIndex3() {
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
    BsonInt32 getResult = ((Map<String, BsonInt32>) keys).get("Field Names");
    assertEquals(1, getResult.getValue());
    assertEquals(BsonType.INT32, getResult.getBsonType());
    assertFalse(options.isBackground());
    assertFalse(options.isSparse());
    assertFalse(getResult.isArray());
    assertFalse(getResult.isBinary());
    assertFalse(getResult.isBoolean());
    assertFalse(getResult.isDBPointer());
    assertFalse(getResult.isDateTime());
    assertFalse(getResult.isDecimal128());
    assertFalse(getResult.isDocument());
    assertFalse(getResult.isDouble());
    assertFalse(getResult.isInt64());
    assertFalse(getResult.isJavaScript());
    assertFalse(getResult.isJavaScriptWithScope());
    assertFalse(getResult.isNull());
    assertFalse(getResult.isObjectId());
    assertFalse(getResult.isRegularExpression());
    assertFalse(getResult.isString());
    assertFalse(getResult.isSymbol());
    assertFalse(getResult.isTimestamp());
    assertTrue(options.isUnique());
    assertTrue(getResult.isInt32());
    assertTrue(getResult.isNumber());
  }

  /**
   * Method under test: {@link BaseMongo#buildIndex(String, String[])}
   */
  @Test
  void testBuildIndex4() {
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
    BsonInt32 getResult = ((Map<String, BsonInt32>) keys).get("Field Names");
    assertEquals(1, getResult.getValue());
    assertEquals(BsonType.INT32, getResult.getBsonType());
    assertFalse(options.isBackground());
    assertFalse(options.isSparse());
    assertFalse(options.isUnique());
    assertFalse(getResult.isArray());
    assertFalse(getResult.isBinary());
    assertFalse(getResult.isBoolean());
    assertFalse(getResult.isDBPointer());
    assertFalse(getResult.isDateTime());
    assertFalse(getResult.isDecimal128());
    assertFalse(getResult.isDocument());
    assertFalse(getResult.isDouble());
    assertFalse(getResult.isInt64());
    assertFalse(getResult.isJavaScript());
    assertFalse(getResult.isJavaScriptWithScope());
    assertFalse(getResult.isNull());
    assertFalse(getResult.isObjectId());
    assertFalse(getResult.isRegularExpression());
    assertFalse(getResult.isString());
    assertFalse(getResult.isSymbol());
    assertFalse(getResult.isTimestamp());
    assertTrue(getResult.isInt32());
    assertTrue(getResult.isNumber());
  }
}
