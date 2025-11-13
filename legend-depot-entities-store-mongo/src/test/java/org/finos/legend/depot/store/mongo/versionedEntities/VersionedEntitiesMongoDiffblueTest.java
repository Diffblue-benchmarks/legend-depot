package org.finos.legend.depot.store.mongo.versionedEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.model.IndexModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.BsonInt32;
import org.bson.conversions.Bson;
import org.finos.legend.depot.store.model.versionedEntities.StoredVersionedEntity;
import org.finos.legend.depot.store.model.versionedEntities.StoredVersionedEntityData;
import org.finos.legend.depot.store.model.versionedEntities.StoredVersionedEntityStringData;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VersionedEntitiesMongoDiffblueTest {
  /**
   * Test {@link VersionedEntitiesMongo#VersionedEntitiesMongo(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Database is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VersionedEntitiesMongo#VersionedEntitiesMongo(MongoDatabase)}
   */
  @Test
  @DisplayName(
      "Test new VersionedEntitiesMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionedEntitiesMongo.<init>(MongoDatabase)"})
  void testNewVersionedEntitiesMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new VersionedEntitiesMongo(null).getDatabase());
  }

  /**
   * Test {@link VersionedEntitiesMongo#buildIndexes()}.
   *
   * <p>Method under test: {@link VersionedEntitiesMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionedEntitiesMongo.buildIndexes()"})
  void testBuildIndexes() {
    // Arrange and Act
    List<IndexModel> actualBuildIndexesResult = VersionedEntitiesMongo.buildIndexes();

    // Assert
    assertEquals(4, actualBuildIndexesResult.size());
    Bson keys = actualBuildIndexesResult.get(0).getKeys();
    assertTrue(keys instanceof Map);
    Bson keys2 = actualBuildIndexesResult.get(1).getKeys();
    assertTrue(keys2 instanceof Map);
    Bson keys3 = actualBuildIndexesResult.get(2).getKeys();
    assertTrue(keys3 instanceof Map);
    Bson keys4 = actualBuildIndexesResult.get(3).getKeys();
    assertTrue(keys4 instanceof Map);
    assertEquals(1, ((Map<String, BsonInt32>) keys4).size());
    assertEquals(3, ((Map<String, BsonInt32>) keys).size());
    assertEquals(4, ((Map<String, BsonInt32>) keys2).size());
    assertEquals(4, ((Map<String, BsonInt32>) keys3).size());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("artifactId"));
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("groupId"));
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("versionId"));
    assertTrue(((Map<String, BsonInt32>) keys2).containsKey("artifactId"));
    assertTrue(((Map<String, BsonInt32>) keys2).containsKey("entityAttributes.path"));
    assertTrue(((Map<String, BsonInt32>) keys2).containsKey("groupId"));
    assertTrue(((Map<String, BsonInt32>) keys2).containsKey("versionId"));
    assertTrue(((Map<String, BsonInt32>) keys3).containsKey("artifactId"));
    assertTrue(((Map<String, BsonInt32>) keys3).containsKey("entityAttributes.package"));
    assertTrue(((Map<String, BsonInt32>) keys3).containsKey("groupId"));
    assertTrue(((Map<String, BsonInt32>) keys3).containsKey("versionId"));
    assertTrue(((Map<String, BsonInt32>) keys4).containsKey("entityAttributes.classifierPath"));
  }

  /**
   * Test {@link VersionedEntitiesMongo#getCollection()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.
   * </ul>
   *
   * <p>Method under test: {@link VersionedEntitiesMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoCollection VersionedEntitiesMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any()))
        .thenReturn(mock(MongoCollection.class));

    // Act
    new VersionedEntitiesMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection("versioned-entities");
  }

  /**
   * Test {@link VersionedEntitiesMongo#createOrUpdate(String, String, String, List)} with {@code
   * groupId}, {@code artifactId}, {@code versionId}, {@code entityDefinitions}.
   *
   * <p>Method under test: {@link VersionedEntitiesMongo#createOrUpdate(String, String, String,
   * List)}
   */
  @Test
  @DisplayName(
      "Test createOrUpdate(String, String, String, List) with 'groupId', 'artifactId', 'versionId', 'entityDefinitions'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionedEntitiesMongo.createOrUpdate(String, String, String, List)"})
  void testCreateOrUpdateWithGroupIdArtifactIdVersionIdEntityDefinitions() {
    // Arrange
    VersionedEntitiesMongo versionedEntitiesMongo = new VersionedEntitiesMongo(null);

    // Act and Assert
    assertTrue(
        versionedEntitiesMongo.createOrUpdate("42", "42", "42", new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)} with
   * {@code StoredVersionedEntity}.
   *
   * <p>Method under test: {@link
   * VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)}
   */
  @Test
  @DisplayName(
      "Test resolvedToEntityDefinition(StoredVersionedEntity) with 'StoredVersionedEntity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Entity VersionedEntitiesMongo.resolvedToEntityDefinition(StoredVersionedEntity)"
  })
  void testResolvedToEntityDefinitionWithStoredVersionedEntity() {
    // Arrange
    VersionedEntitiesMongo versionedEntitiesMongo = new VersionedEntitiesMongo(null);
    StoredVersionedEntityStringData storedEntity =
        new StoredVersionedEntityStringData("42", "42", "42", "Data", new HashMap<>());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> versionedEntitiesMongo.resolvedToEntityDefinition(storedEntity));
  }

  /**
   * Test {@link VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)} with
   * {@code StoredVersionedEntity}.
   *
   * <p>Method under test: {@link
   * VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)}
   */
  @Test
  @DisplayName(
      "Test resolvedToEntityDefinition(StoredVersionedEntity) with 'StoredVersionedEntity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Entity VersionedEntitiesMongo.resolvedToEntityDefinition(StoredVersionedEntity)"
  })
  void testResolvedToEntityDefinitionWithStoredVersionedEntity2() {
    // Arrange
    VersionedEntitiesMongo versionedEntitiesMongo = new VersionedEntitiesMongo(null);
    StoredVersionedEntityStringData storedEntity =
        new StoredVersionedEntityStringData("42", "42", "42", "42", new HashMap<>());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> versionedEntitiesMongo.resolvedToEntityDefinition(storedEntity));
  }

  /**
   * Test {@link VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)} with
   * {@code StoredVersionedEntity}.
   *
   * <p>Method under test: {@link
   * VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)}
   */
  @Test
  @DisplayName(
      "Test resolvedToEntityDefinition(StoredVersionedEntity) with 'StoredVersionedEntity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Entity VersionedEntitiesMongo.resolvedToEntityDefinition(StoredVersionedEntity)"
  })
  void testResolvedToEntityDefinitionWithStoredVersionedEntity3() {
    // Arrange
    VersionedEntitiesMongo versionedEntitiesMongo = new VersionedEntitiesMongo(null);
    StoredVersionedEntityStringData storedEntity =
        new StoredVersionedEntityStringData("42", "42", "42", "", new HashMap<>());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> versionedEntitiesMongo.resolvedToEntityDefinition(storedEntity));
  }

  /**
   * Test {@link VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)} with
   * {@code StoredVersionedEntity}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)}
   */
  @Test
  @DisplayName(
      "Test resolvedToEntityDefinition(StoredVersionedEntity) with 'StoredVersionedEntity'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Entity VersionedEntitiesMongo.resolvedToEntityDefinition(StoredVersionedEntity)"
  })
  void testResolvedToEntityDefinitionWithStoredVersionedEntity_thenReturnNull() {
    // Arrange
    VersionedEntitiesMongo versionedEntitiesMongo = new VersionedEntitiesMongo(null);
    StoredVersionedEntityData storedEntity = new StoredVersionedEntityData("42", "42", "42");

    // Act
    Entity actualResolvedToEntityDefinitionResult =
        versionedEntitiesMongo.resolvedToEntityDefinition(storedEntity);

    // Assert
    assertNull(actualResolvedToEntityDefinitionResult);
  }

  /**
   * Test {@link VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)} with
   * {@code StoredVersionedEntity}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)}
   */
  @Test
  @DisplayName(
      "Test resolvedToEntityDefinition(StoredVersionedEntity) with 'StoredVersionedEntity'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Entity VersionedEntitiesMongo.resolvedToEntityDefinition(StoredVersionedEntity)"
  })
  void testResolvedToEntityDefinitionWithStoredVersionedEntity_whenNull() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> new VersionedEntitiesMongo(null).resolvedToEntityDefinition(null));
  }
}
