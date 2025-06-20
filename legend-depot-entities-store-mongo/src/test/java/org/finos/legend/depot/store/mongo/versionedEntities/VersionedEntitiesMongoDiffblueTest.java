package org.finos.legend.depot.store.mongo.versionedEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import com.mongodb.client.model.IndexModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bson.BsonInt32;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.finos.legend.depot.store.model.versionedEntities.StoredVersionedEntity;
import org.finos.legend.depot.store.model.versionedEntities.StoredVersionedEntityData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VersionedEntitiesMongoDiffblueTest {
  @Mock
  private MongoDatabase mongoDatabase;

  @InjectMocks
  private VersionedEntitiesMongo versionedEntitiesMongo;

  /**
   * Test {@link VersionedEntitiesMongo#VersionedEntitiesMongo(MongoDatabase)}.
   * <p>
   * Method under test: {@link VersionedEntitiesMongo#VersionedEntitiesMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new VersionedEntitiesMongo(MongoDatabase)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void VersionedEntitiesMongo.<init>(MongoDatabase)"})
  void testNewVersionedEntitiesMongo() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    // Act and Assert
    assertSame(databaseProvider, new VersionedEntitiesMongo(databaseProvider).getDatabase());
  }

  /**
   * Test {@link VersionedEntitiesMongo#buildIndexes()}.
   * <p>
   * Method under test: {@link VersionedEntitiesMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Then calls {@link MongoDatabase#getCollection(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedEntitiesMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoCollection VersionedEntitiesMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mock(MongoCollection.class));

    // Act
    versionedEntitiesMongo.getCollection();

    // Assert
    verify(mongoDatabase).getCollection(eq("versioned-entities"));
  }

  /**
   * Test {@link VersionedEntitiesMongo#createOrUpdate(String, String, String, List)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code entityDefinitions}.
   * <p>
   * Method under test: {@link VersionedEntitiesMongo#createOrUpdate(String, String, String, List)}
   */
  @Test
  @DisplayName("Test createOrUpdate(String, String, String, List) with 'groupId', 'artifactId', 'versionId', 'entityDefinitions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List VersionedEntitiesMongo.createOrUpdate(String, String, String, List)"})
  void testCreateOrUpdateWithGroupIdArtifactIdVersionIdEntityDefinitions() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    VersionedEntitiesMongo versionedEntitiesMongo = new VersionedEntitiesMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertTrue(versionedEntitiesMongo.createOrUpdate("42", "42", "42", new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)} with {@code StoredVersionedEntity}.
   * <p>
   * Method under test: {@link VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)}
   */
  @Test
  @DisplayName("Test resolvedToEntityDefinition(StoredVersionedEntity) with 'StoredVersionedEntity'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.finos.legend.sdlc.domain.model.entity.Entity VersionedEntitiesMongo.resolvedToEntityDefinition(StoredVersionedEntity)"})
  void testResolvedToEntityDefinitionWithStoredVersionedEntity() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> new VersionedEntitiesMongo(
            new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
                new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)))
                    .resolvedToEntityDefinition(null));
  }

  /**
   * Test {@link VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)} with {@code StoredVersionedEntity}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedEntitiesMongo#resolvedToEntityDefinition(StoredVersionedEntity)}
   */
  @Test
  @DisplayName("Test resolvedToEntityDefinition(StoredVersionedEntity) with 'StoredVersionedEntity'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.finos.legend.sdlc.domain.model.entity.Entity VersionedEntitiesMongo.resolvedToEntityDefinition(StoredVersionedEntity)"})
  void testResolvedToEntityDefinitionWithStoredVersionedEntity_thenReturnNull() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    VersionedEntitiesMongo versionedEntitiesMongo = new VersionedEntitiesMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertNull(versionedEntitiesMongo.resolvedToEntityDefinition(new StoredVersionedEntityData("42", "42", "42")));
  }
}
