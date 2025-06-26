package org.finos.legend.depot.store.mongo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import com.mongodb.operation.WriteOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.eclipse.collections.api.tuple.Pair;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Disabled("Failed pitest")
class AbstractEntitiesMongoDiffblueTest {
  /**
   * Test {@link AbstractEntitiesMongo#getEntityPathFilter(String, String, String, String)}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getEntityPathFilter(String, String, String, String)}
   */
  @Test
  @DisplayName("Test getEntityPathFilter(String, String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson AbstractEntitiesMongo.getEntityPathFilter(String, String, String, String)"})
  void testGetEntityPathFilter() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    entitiesMongo.getEntityPathFilter("42", "42", "42", "Path");

    // Assert that nothing has changed
    assertSame(databaseProvider, entitiesMongo.getDatabase());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getEntityPathFilter(String, String, String, String)}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getEntityPathFilter(String, String, String, String)}
   */
  @Test
  @DisplayName("Test getEntityPathFilter(String, String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson AbstractEntitiesMongo.getEntityPathFilter(String, String, String, String)"})
  void testGetEntityPathFilter2() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    Bson actualEntityPathFilter = entitiesMongo.getEntityPathFilter("42", "42", "42", "Path");
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry2 = mock(CodecRegistry.class);
    when(codecRegistry2.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult = actualEntityPathFilter.toBsonDocument(forNameResult, codecRegistry2);

    // Assert
    verify(codecRegistry2, atLeast(1)).get(isA(Class.class));
    MongoDatabase database = entitiesMongo.getDatabase();
    assertTrue(database instanceof MongoDatabaseImpl);
    assertEquals(4, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonString);
    assertTrue(actualToBsonDocumentResult.get("entityAttributes.path") instanceof BsonString);
    BsonValue getResult2 = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult2 instanceof BsonString);
    BsonValue getResult3 = actualToBsonDocumentResult.get("versionId");
    assertTrue(getResult3 instanceof BsonString);
    assertEquals(getResult3, getResult);
    assertEquals(getResult3, getResult2);
    assertSame(databaseProvider, database);
  }

  /**
   * Test {@link AbstractEntitiesMongo#getArtifactAndVersionVersionedFilter(String, String, String)}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getArtifactAndVersionVersionedFilter(String, String, String)}
   */
  @Test
  @DisplayName("Test getArtifactAndVersionVersionedFilter(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson AbstractEntitiesMongo.getArtifactAndVersionVersionedFilter(String, String, String)"})
  void testGetArtifactAndVersionVersionedFilter() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    entitiesMongo.getArtifactAndVersionVersionedFilter("42", "42", "42");

    // Assert that nothing has changed
    assertSame(databaseProvider, entitiesMongo.getDatabase());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getArtifactAndVersionVersionedFilter(String, String, String)}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getArtifactAndVersionVersionedFilter(String, String, String)}
   */
  @Test
  @DisplayName("Test getArtifactAndVersionVersionedFilter(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson AbstractEntitiesMongo.getArtifactAndVersionVersionedFilter(String, String, String)"})
  void testGetArtifactAndVersionVersionedFilter2() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    Bson actualArtifactAndVersionVersionedFilter = entitiesMongo.getArtifactAndVersionVersionedFilter("42", "42", "42");
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry2 = mock(CodecRegistry.class);
    when(codecRegistry2.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult = actualArtifactAndVersionVersionedFilter.toBsonDocument(forNameResult,
        codecRegistry2);

    // Assert
    verify(codecRegistry2, atLeast(1)).get(isA(Class.class));
    MongoDatabase database = entitiesMongo.getDatabase();
    assertTrue(database instanceof MongoDatabaseImpl);
    assertEquals(3, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonString);
    BsonValue getResult2 = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult2 instanceof BsonString);
    BsonValue getResult3 = actualToBsonDocumentResult.get("versionId");
    assertTrue(getResult3 instanceof BsonString);
    assertEquals(getResult3, getResult);
    assertEquals(getResult3, getResult2);
    assertSame(databaseProvider, database);
  }

  /**
   * Test {@link AbstractEntitiesMongo#getArtifactVersionedFilter(String, String)}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getArtifactVersionedFilter(String, String)}
   */
  @Test
  @DisplayName("Test getArtifactVersionedFilter(String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson AbstractEntitiesMongo.getArtifactVersionedFilter(String, String)"})
  void testGetArtifactVersionedFilter() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    entitiesMongo.getArtifactVersionedFilter("42", "42");

    // Assert that nothing has changed
    assertSame(databaseProvider, entitiesMongo.getDatabase());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getArtifactVersionedFilter(String, String)}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getArtifactVersionedFilter(String, String)}
   */
  @Test
  @DisplayName("Test getArtifactVersionedFilter(String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson AbstractEntitiesMongo.getArtifactVersionedFilter(String, String)"})
  void testGetArtifactVersionedFilter2() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    Bson actualArtifactVersionedFilter = entitiesMongo.getArtifactVersionedFilter("42", "42");
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry2 = mock(CodecRegistry.class);
    when(codecRegistry2.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult = actualArtifactVersionedFilter.toBsonDocument(forNameResult,
        codecRegistry2);

    // Assert
    verify(codecRegistry2, atLeast(1)).get(isA(Class.class));
    MongoDatabase database = entitiesMongo.getDatabase();
    assertTrue(database instanceof MongoDatabaseImpl);
    assertEquals(2, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonString);
    BsonValue getResult2 = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult2 instanceof BsonString);
    assertEquals(getResult2, getResult);
    assertSame(databaseProvider, database);
  }

  /**
   * Test {@link AbstractEntitiesMongo#getEntity(String, String, String, String)}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getEntity(String, String, String, String)}
   */
  @Test
  @DisplayName("Test getEntity(String, String, String, String); given FindIterable forEach(Consumer) does nothing; then return not Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional AbstractEntitiesMongo.getEntity(String, String, String, String)"})
  void testGetEntity_givenFindIterableForEachDoesNothing_thenReturnNotPresent() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    Optional<Entity> actualEntity = entitiesMongo.getEntity("42", "42", "42", "Path");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertFalse(actualEntity.isPresent());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getEntityFromDependencies(Set, List)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getEntityFromDependencies(Set, List)}
   */
  @Test
  @DisplayName("Test getEntityFromDependencies(Set, List); when HashSet(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getEntityFromDependencies(Set, List)"})
  void testGetEntityFromDependencies_whenHashSet_thenReturnEmpty() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    HashSet<ProjectVersion> dependencies = new HashSet<>();

    // Act and Assert
    assertTrue(entitiesMongo.getEntityFromDependencies(dependencies, new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getStoredEntities(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getStoredEntities(String, String, String)}
   */
  @Test
  @DisplayName("Test getStoredEntities(String, String, String) with 'groupId', 'artifactId', 'versionId'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getStoredEntities(String, String, String)"})
  void testGetStoredEntitiesWithGroupIdArtifactIdVersionId_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<StoredEntity> actualStoredEntities = entitiesMongo.getStoredEntities("42", "42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualStoredEntities.isEmpty());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getStoredEntities(String, String)} with {@code groupId}, {@code artifactId}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getStoredEntities(String, String)}
   */
  @Test
  @DisplayName("Test getStoredEntities(String, String) with 'groupId', 'artifactId'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getStoredEntities(String, String)"})
  void testGetStoredEntitiesWithGroupIdArtifactId_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<StoredEntity> actualStoredEntities = entitiesMongo.getStoredEntities("42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualStoredEntities.isEmpty());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getAllEntities(String, String, String)}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getAllEntities(String, String, String)}
   */
  @Test
  @DisplayName("Test getAllEntities(String, String, String); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getAllEntities(String, String, String)"})
  void testGetAllEntities_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<Entity> actualAllEntities = entitiesMongo.getAllEntities("42", "42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAllEntities.isEmpty());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}
   */
  @Test
  @DisplayName("Test getEntitiesByPackage(String, String, String, String, Set, boolean); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getEntitiesByPackage(String, String, String, String, Set, boolean)"})
  void testGetEntitiesByPackage_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<Entity> actualEntitiesByPackage = entitiesMongo.getEntitiesByPackage("42", "42", "42", "java.text",
        new HashSet<>(), true);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualEntitiesByPackage.isEmpty());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}.
   * <ul>
   *   <li>Given {@code groupId}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code groupId}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}
   */
  @Test
  @DisplayName("Test getEntitiesByPackage(String, String, String, String, Set, boolean); given 'groupId'; when HashSet() add 'groupId'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getEntitiesByPackage(String, String, String, String, Set, boolean)"})
  void testGetEntitiesByPackage_givenGroupId_whenHashSetAddGroupId_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    HashSet<String> classifierPaths = new HashSet<>();
    classifierPaths.add("groupId");
    classifierPaths.add("versionId");

    // Act
    List<Entity> actualEntitiesByPackage = entitiesMongo.getEntitiesByPackage("42", "42", "42", "java.text",
        classifierPaths, true);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualEntitiesByPackage.isEmpty());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}.
   * <ul>
   *   <li>Given {@code versionId}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code versionId}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}
   */
  @Test
  @DisplayName("Test getEntitiesByPackage(String, String, String, String, Set, boolean); given 'versionId'; when HashSet() add 'versionId'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getEntitiesByPackage(String, String, String, String, Set, boolean)"})
  void testGetEntitiesByPackage_givenVersionId_whenHashSetAddVersionId_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    HashSet<String> classifierPaths = new HashSet<>();
    classifierPaths.add("versionId");

    // Act
    List<Entity> actualEntitiesByPackage = entitiesMongo.getEntitiesByPackage("42", "42", "42", "java.text",
        classifierPaths, true);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualEntitiesByPackage.isEmpty());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}
   */
  @Test
  @DisplayName("Test getEntitiesByPackage(String, String, String, String, Set, boolean); when empty string; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getEntitiesByPackage(String, String, String, String, Set, boolean)"})
  void testGetEntitiesByPackage_whenEmptyString_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<Entity> actualEntitiesByPackage = entitiesMongo.getEntitiesByPackage("42", "42", "42", "", new HashSet<>(),
        true);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualEntitiesByPackage.isEmpty());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}
   */
  @Test
  @DisplayName("Test getEntitiesByPackage(String, String, String, String, Set, boolean); when 'false'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getEntitiesByPackage(String, String, String, String, Set, boolean)"})
  void testGetEntitiesByPackage_whenFalse_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<Entity> actualEntitiesByPackage = entitiesMongo.getEntitiesByPackage("42", "42", "42", "java.text",
        new HashSet<>(), false);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualEntitiesByPackage.isEmpty());
  }

  /**
   * Test {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getEntitiesByPackage(String, String, String, String, Set, boolean)}
   */
  @Test
  @DisplayName("Test getEntitiesByPackage(String, String, String, String, Set, boolean); when 'null'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getEntitiesByPackage(String, String, String, String, Set, boolean)"})
  void testGetEntitiesByPackage_whenNull_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<Entity> actualEntitiesByPackage = entitiesMongo.getEntitiesByPackage("42", "42", "42", null, new HashSet<>(),
        true);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualEntitiesByPackage.isEmpty());
  }

  /**
   * Test {@link AbstractEntitiesMongo#findReleasedEntitiesByClassifier(String, String)} with {@code classifier}, {@code search}.
   * <ul>
   *   <li>Then calls {@link MongoCollection#find(Bson)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#findReleasedEntitiesByClassifier(String, String)}
   */
  @Test
  @DisplayName("Test findReleasedEntitiesByClassifier(String, String) with 'classifier', 'search'; then calls find(Bson)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FindIterable AbstractEntitiesMongo.findReleasedEntitiesByClassifier(String, String)"})
  void testFindReleasedEntitiesByClassifierWithClassifierSearch_thenCallsFind() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(mock(FindIterable.class));
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    entitiesMongo.findReleasedEntitiesByClassifier("Classifier", "Search");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link AbstractEntitiesMongo#findReleasedEntitiesByClassifier(String, String)} with {@code classifier}, {@code search}.
   * <ul>
   *   <li>Then calls {@link MongoCollection#find(Bson)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#findReleasedEntitiesByClassifier(String, String)}
   */
  @Test
  @DisplayName("Test findReleasedEntitiesByClassifier(String, String) with 'classifier', 'search'; then calls find(Bson)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FindIterable AbstractEntitiesMongo.findReleasedEntitiesByClassifier(String, String)"})
  void testFindReleasedEntitiesByClassifierWithClassifierSearch_thenCallsFind2() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(mock(FindIterable.class));
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    entitiesMongo.findReleasedEntitiesByClassifier("Classifier", null);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link AbstractEntitiesMongo#findReleasedEntitiesByClassifier(String)} with {@code classifier}.
   * <ul>
   *   <li>Then calls {@link MongoCollection#find(Bson)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#findReleasedEntitiesByClassifier(String)}
   */
  @Test
  @DisplayName("Test findReleasedEntitiesByClassifier(String) with 'classifier'; then calls find(Bson)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FindIterable AbstractEntitiesMongo.findReleasedEntitiesByClassifier(String)"})
  void testFindReleasedEntitiesByClassifierWithClassifier_thenCallsFind() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(mock(FindIterable.class));
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    entitiesMongo.findReleasedEntitiesByClassifier("Classifier");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link AbstractEntitiesMongo#findLatestEntitiesByClassifier(String, String)} with {@code classifier}, {@code search}.
   * <ul>
   *   <li>Then calls {@link MongoCollection#find(Bson)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#findLatestEntitiesByClassifier(String, String)}
   */
  @Test
  @DisplayName("Test findLatestEntitiesByClassifier(String, String) with 'classifier', 'search'; then calls find(Bson)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FindIterable AbstractEntitiesMongo.findLatestEntitiesByClassifier(String, String)"})
  void testFindLatestEntitiesByClassifierWithClassifierSearch_thenCallsFind() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(mock(FindIterable.class));
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    entitiesMongo.findLatestEntitiesByClassifier("Classifier", "Search");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link AbstractEntitiesMongo#findLatestEntitiesByClassifier(String, String)} with {@code classifier}, {@code search}.
   * <ul>
   *   <li>Then calls {@link MongoCollection#find(Bson)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#findLatestEntitiesByClassifier(String, String)}
   */
  @Test
  @DisplayName("Test findLatestEntitiesByClassifier(String, String) with 'classifier', 'search'; then calls find(Bson)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FindIterable AbstractEntitiesMongo.findLatestEntitiesByClassifier(String, String)"})
  void testFindLatestEntitiesByClassifierWithClassifierSearch_thenCallsFind2() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(mock(FindIterable.class));
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    entitiesMongo.findLatestEntitiesByClassifier("Classifier", null);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link AbstractEntitiesMongo#findLatestEntitiesByClassifier(String)} with {@code classifier}.
   * <ul>
   *   <li>Then calls {@link MongoCollection#find(Bson)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#findLatestEntitiesByClassifier(String)}
   */
  @Test
  @DisplayName("Test findLatestEntitiesByClassifier(String) with 'classifier'; then calls find(Bson)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FindIterable AbstractEntitiesMongo.findLatestEntitiesByClassifier(String)"})
  void testFindLatestEntitiesByClassifierWithClassifier_thenCallsFind() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(mock(FindIterable.class));
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    entitiesMongo.findLatestEntitiesByClassifier("Classifier");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link AbstractEntitiesMongo#findEntitiesByClassifierAndVersions(String, List)} with {@code classifier}, {@code projectVersions}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#findEntitiesByClassifierAndVersions(String, List)}
   */
  @Test
  @DisplayName("Test findEntitiesByClassifierAndVersions(String, List) with 'classifier', 'projectVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FindIterable AbstractEntitiesMongo.findEntitiesByClassifierAndVersions(String, List)"})
  void testFindEntitiesByClassifierAndVersionsWithClassifierProjectVersions() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(mock(FindIterable.class));
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    entitiesMongo.findEntitiesByClassifierAndVersions("Classifier", new ArrayList<>());

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link AbstractEntitiesMongo#findEntitiesByClassifierAndVersions(String, List)} with {@code classifier}, {@code projectVersions}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#findEntitiesByClassifierAndVersions(String, List)}
   */
  @Test
  @DisplayName("Test findEntitiesByClassifierAndVersions(String, List) with 'classifier', 'projectVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FindIterable AbstractEntitiesMongo.findEntitiesByClassifierAndVersions(String, List)"})
  void testFindEntitiesByClassifierAndVersionsWithClassifierProjectVersions2() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(mock(FindIterable.class));
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    entitiesMongo.findEntitiesByClassifierAndVersions("Classifier", projectVersions);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link AbstractEntitiesMongo#findEntitiesByClassifierAndVersions(String, List)} with {@code classifier}, {@code projectVersions}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#findEntitiesByClassifierAndVersions(String, List)}
   */
  @Test
  @DisplayName("Test findEntitiesByClassifierAndVersions(String, List) with 'classifier', 'projectVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FindIterable AbstractEntitiesMongo.findEntitiesByClassifierAndVersions(String, List)"})
  void testFindEntitiesByClassifierAndVersionsWithClassifierProjectVersions3() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(mock(FindIterable.class));
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    entitiesMongo.findEntitiesByClassifierAndVersions("Classifier", projectVersions);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link AbstractEntitiesMongo#delete(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#delete(String, String, String)}
   */
  @Test
  @DisplayName("Test delete(String, String, String) with 'groupId', 'artifactId', 'versionId'; then return three")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long AbstractEntitiesMongo.delete(String, String, String)"})
  void testDeleteWithGroupIdArtifactIdVersionId_thenReturnThree() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BulkWriteResult bulkWriteResult = mock(BulkWriteResult.class);
    when(bulkWriteResult.getDeletedCount()).thenReturn(3);
    when(bulkWriteResult.wasAcknowledged()).thenReturn(true);
    OperationExecutor executor = mock(OperationExecutor.class);
    when(executor.execute(Mockito.<WriteOperation<BulkWriteResult>>any(), Mockito.<ReadConcern>any(),
        Mockito.<ClientSession>any())).thenReturn(bulkWriteResult);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, executor));

    // Act
    long actualDeleteResult = entitiesMongo.delete("42", "42", "42");

    // Assert
    verify(bulkWriteResult).getDeletedCount();
    verify(bulkWriteResult).wasAcknowledged();
    verify(executor).execute(isA(WriteOperation.class), isA(ReadConcern.class), isNull());
    verify(codecRegistry, atLeast(1)).get(isA(Class.class));
    assertEquals(3L, actualDeleteResult);
  }

  /**
   * Test {@link AbstractEntitiesMongo#delete(String, String)} with {@code groupId}, {@code artifactId}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#delete(String, String)}
   */
  @Test
  @DisplayName("Test delete(String, String) with 'groupId', 'artifactId'; then return three")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long AbstractEntitiesMongo.delete(String, String)"})
  void testDeleteWithGroupIdArtifactId_thenReturnThree() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BulkWriteResult bulkWriteResult = mock(BulkWriteResult.class);
    when(bulkWriteResult.getDeletedCount()).thenReturn(3);
    when(bulkWriteResult.wasAcknowledged()).thenReturn(true);
    OperationExecutor executor = mock(OperationExecutor.class);
    when(executor.execute(Mockito.<WriteOperation<BulkWriteResult>>any(), Mockito.<ReadConcern>any(),
        Mockito.<ClientSession>any())).thenReturn(bulkWriteResult);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, executor));

    // Act
    long actualDeleteResult = entitiesMongo.delete("42", "42");

    // Assert
    verify(bulkWriteResult).getDeletedCount();
    verify(bulkWriteResult).wasAcknowledged();
    verify(executor).execute(isA(WriteOperation.class), isA(ReadConcern.class), isNull());
    verify(codecRegistry, atLeast(1)).get(isA(Class.class));
    assertEquals(3L, actualDeleteResult);
  }

  /**
   * Test {@link AbstractEntitiesMongo#getStoredEntitiesCoordinates()}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getStoredEntitiesCoordinates()}
   */
  @Test
  @DisplayName("Test getStoredEntitiesCoordinates()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getStoredEntitiesCoordinates()"})
  void testGetStoredEntitiesCoordinates() {
    // Arrange
    AggregateIterable<Document> aggregateIterable = mock(AggregateIterable.class);
    doThrow(new IllegalStateException("$groupId")).when(aggregateIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.aggregate(Mockito.<List<Bson>>any())).thenReturn(aggregateIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> entitiesMongo.getStoredEntitiesCoordinates());
    verify(mongoCollection).aggregate(isA(List.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(aggregateIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link AbstractEntitiesMongo#getStoredEntitiesCoordinates()}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getStoredEntitiesCoordinates()}
   */
  @Test
  @DisplayName("Test getStoredEntitiesCoordinates()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getStoredEntitiesCoordinates()"})
  void testGetStoredEntitiesCoordinates2() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.aggregate(Mockito.<List<Bson>>any())).thenThrow(new IllegalStateException("$groupId"));
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    Class<StoredEntity> documentClass = StoredEntity.class;
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider, documentClass);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> entitiesMongo.getStoredEntitiesCoordinates());
    verify(mongoCollection).aggregate(isA(List.class));
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link AbstractEntitiesMongo#getStoredEntitiesCoordinates()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#getStoredEntitiesCoordinates()}
   */
  @Test
  @DisplayName("Test getStoredEntitiesCoordinates(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AbstractEntitiesMongo.getStoredEntitiesCoordinates()"})
  void testGetStoredEntitiesCoordinates_thenReturnEmpty() {
    // Arrange
    AggregateIterable<Document> aggregateIterable = mock(AggregateIterable.class);
    doNothing().when(aggregateIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.aggregate(Mockito.<List<Bson>>any())).thenReturn(aggregateIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<Pair<String, String>> actualStoredEntitiesCoordinates = entitiesMongo.getStoredEntitiesCoordinates();

    // Assert
    verify(mongoCollection).aggregate(isA(List.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(aggregateIterable).forEach(isA(Consumer.class));
    assertTrue(actualStoredEntitiesCoordinates.isEmpty());
  }

  /**
   * Test {@link AbstractEntitiesMongo#serializeEntity(Entity)}.
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#serializeEntity(Entity)}
   */
  @Test
  @DisplayName("Test serializeEntity(Entity)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AbstractEntitiesMongo.serializeEntity(Entity)"})
  void testSerializeEntity() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("42", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertEquals("{\"path\":\"Path\",\"classifierPath\":\"Classifier Path\",\"content\":{}}",
        entitiesMongo.serializeEntity(new EntityDefinition("Path", "Classifier Path", new HashMap<>())));
  }

  /**
   * Test {@link AbstractEntitiesMongo#serializeEntity(Entity)}.
   * <ul>
   *   <li>Then return {@code {"path":"Path","classifierPath":"Classifier Path","content":{}}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#serializeEntity(Entity)}
   */
  @Test
  @DisplayName("Test serializeEntity(Entity); then return '{\"path\":\"Path\",\"classifierPath\":\"Classifier Path\",\"content\":{}}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AbstractEntitiesMongo.serializeEntity(Entity)"})
  void testSerializeEntity_thenReturnPathPathClassifierPathClassifierPathContent() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertEquals("{\"path\":\"Path\",\"classifierPath\":\"Classifier Path\",\"content\":{}}",
        entitiesMongo.serializeEntity(new EntityDefinition("Path", "Classifier Path", new HashMap<>())));
  }

  /**
   * Test {@link AbstractEntitiesMongo#serializeEntity(Entity)}.
   * <ul>
   *   <li>Then return {@code {"path":"Path","classifierPath":"Classifier Path","content":{"foo":"42"}}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#serializeEntity(Entity)}
   */
  @Test
  @DisplayName("Test serializeEntity(Entity); then return '{\"path\":\"Path\",\"classifierPath\":\"Classifier Path\",\"content\":{\"foo\":\"42\"}}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AbstractEntitiesMongo.serializeEntity(Entity)"})
  void testSerializeEntity_thenReturnPathPathClassifierPathClassifierPathContentFoo42() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    HashMap<String, Object> content = new HashMap<>();
    content.put("foo", "42");

    // Act and Assert
    assertEquals("{\"path\":\"Path\",\"classifierPath\":\"Classifier Path\",\"content\":{\"foo\":\"42\"}}",
        entitiesMongo.serializeEntity(new EntityDefinition("Path", "Classifier Path", content)));
  }

  /**
   * Test {@link AbstractEntitiesMongo#serializeEntity(Entity)}.
   * <ul>
   *   <li>Then return {@code {"path":"Path","classifierPath":"Classifier Path","content":{"foo":42}}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#serializeEntity(Entity)}
   */
  @Test
  @DisplayName("Test serializeEntity(Entity); then return '{\"path\":\"Path\",\"classifierPath\":\"Classifier Path\",\"content\":{\"foo\":42}}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AbstractEntitiesMongo.serializeEntity(Entity)"})
  void testSerializeEntity_thenReturnPathPathClassifierPathClassifierPathContentFoo422() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    HashMap<String, Object> content = new HashMap<>();
    content.put("foo", 42);

    // Act and Assert
    assertEquals("{\"path\":\"Path\",\"classifierPath\":\"Classifier Path\",\"content\":{\"foo\":42}}",
        entitiesMongo.serializeEntity(new EntityDefinition("Path", "Classifier Path", content)));
  }

  /**
   * Test {@link AbstractEntitiesMongo#serializeEntity(Entity)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#serializeEntity(Entity)}
   */
  @Test
  @DisplayName("Test serializeEntity(Entity); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AbstractEntitiesMongo.serializeEntity(Entity)"})
  void testSerializeEntity_whenNull_thenReturnNull() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertEquals("null", entitiesMongo.serializeEntity(null));
  }

  /**
   * Test {@link AbstractEntitiesMongo#buildEntityAttributes(Entity)}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntitiesMongo#buildEntityAttributes(Entity)}
   */
  @Test
  @DisplayName("Test buildEntityAttributes(Entity); then return size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map AbstractEntitiesMongo.buildEntityAttributes(Entity)"})
  void testBuildEntityAttributes_thenReturnSizeIsTwo() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act
    Map<String, ?> actualBuildEntityAttributesResult = entitiesMongo
        .buildEntityAttributes(new EntityDefinition("Path", "Classifier Path", null));

    // Assert
    assertEquals(2, actualBuildEntityAttributesResult.size());
    assertEquals("Classifier Path", actualBuildEntityAttributesResult.get("classifierPath"));
    assertEquals("Path", actualBuildEntityAttributesResult.get("path"));
  }
}
