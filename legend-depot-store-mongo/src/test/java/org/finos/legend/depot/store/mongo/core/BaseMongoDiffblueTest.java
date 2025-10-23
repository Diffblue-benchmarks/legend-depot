package org.finos.legend.depot.store.mongo.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import java.util.Map;
import org.bson.BsonInt32;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BaseMongoDiffblueTest {
  /**
   * Test {@link BaseMongo#buildIndex(String, String[])} with {@code indexName}, {@code fieldNames}.
   *
   * <p>Method under test: {@link BaseMongo#buildIndex(String, String[])}
   */
  @Test
  @DisplayName("Test buildIndex(String, String[]) with 'indexName', 'fieldNames'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
   * Test {@link BaseMongo#buildIndex(String, IndexOptions, String[])} with {@code indexName},
   * {@code indexOptions}, {@code fieldNames}.
   *
   * <ul>
   *   <li>Then Keys return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link BaseMongo#buildIndex(String, IndexOptions, String[])}
   */
  @Test
  @DisplayName(
      "Test buildIndex(String, IndexOptions, String[]) with 'indexName', 'indexOptions', 'fieldNames'; then Keys return Map")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"IndexModel BaseMongo.buildIndex(String, IndexOptions, String[])"})
  void testBuildIndexWithIndexNameIndexOptionsFieldNames_thenKeysReturnMap() {
    // Arrange
    IndexOptions indexOptions = new IndexOptions();

    // Act
    IndexModel actualBuildIndexResult =
        BaseMongo.buildIndex("Index Name", indexOptions, "Field Names");

    // Assert
    Bson keys = actualBuildIndexResult.getKeys();
    assertTrue(keys instanceof Map);
    assertEquals("Index Name", indexOptions.getName());
    assertEquals(1, ((Map<String, BsonInt32>) keys).size());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("Field Names"));
    assertSame(indexOptions, actualBuildIndexResult.getOptions());
  }

  /**
   * Test {@link BaseMongo#buildIndex(String, boolean, String[])} with {@code indexName}, {@code
   * isUnique}, {@code fieldNames}.
   *
   * <p>Method under test: {@link BaseMongo#buildIndex(String, boolean, String[])}
   */
  @Test
  @DisplayName(
      "Test buildIndex(String, boolean, String[]) with 'indexName', 'isUnique', 'fieldNames'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
