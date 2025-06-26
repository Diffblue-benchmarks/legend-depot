package org.finos.legend.depot.store.mongo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import com.mongodb.client.model.IndexModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonNull;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.finos.legend.depot.domain.entity.DepotEntity;
import org.finos.legend.depot.domain.entity.DepotEntityOverview;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.depot.store.model.entities.StoredEntityData;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Disabled("Failed pitest")
class EntitiesMongoDiffblueTest {
  /**
   * Test {@link EntitiesMongo#EntitiesMongo(MongoDatabase)}.
   * <p>
   * Method under test: {@link EntitiesMongo#EntitiesMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new EntitiesMongo(MongoDatabase)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void EntitiesMongo.<init>(MongoDatabase)"})
  void testNewEntitiesMongo() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    // Act
    EntitiesMongo<StoredEntity> actualEntitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Assert
    assertSame(databaseProvider, actualEntitiesMongo.getDatabase());
  }

  /**
   * Test {@link EntitiesMongo#EntitiesMongo(MongoDatabase, Class)}.
   * <p>
   * Method under test: {@link EntitiesMongo#EntitiesMongo(MongoDatabase, Class)}
   */
  @Test
  @DisplayName("Test new EntitiesMongo(MongoDatabase, Class)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void EntitiesMongo.<init>(MongoDatabase, Class)"})
  void testNewEntitiesMongo2() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    Class<StoredEntity> documentClass = StoredEntity.class;

    // Act
    EntitiesMongo<StoredEntity> actualEntitiesMongo = new EntitiesMongo<>(databaseProvider, documentClass);

    // Assert
    assertSame(databaseProvider, actualEntitiesMongo.getDatabase());
  }

  /**
   * Test {@link EntitiesMongo#buildIndexes()}.
   * <p>
   * Method under test: {@link EntitiesMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.buildIndexes()"})
  void testBuildIndexes() {
    // Arrange and Act
    List<IndexModel> actualBuildIndexesResult = EntitiesMongo.buildIndexes();

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
   * Test {@link EntitiesMongo#getCollection()}.
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoCollection EntitiesMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mock(MongoCollection.class));
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    entitiesMongo.getCollection();

    // Assert
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link EntitiesMongo#getKeyFilter(StoredEntity)} with {@code StoredEntity}.
   * <p>
   * Method under test: {@link EntitiesMongo#getKeyFilter(StoredEntity)}
   */
  @Test
  @DisplayName("Test getKeyFilter(StoredEntity) with 'StoredEntity'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson EntitiesMongo.getKeyFilter(StoredEntity)"})
  void testGetKeyFilterWithStoredEntity() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    EntityDefinition entity = new EntityDefinition("Path", "Classifier Path", new HashMap<>());

    // Act
    Bson actualKeyFilter = entitiesMongo.getKeyFilter(new StoredEntityData("42", "42", "42", entity, new HashMap<>()));
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry2 = mock(CodecRegistry.class);
    when(codecRegistry2.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult = actualKeyFilter.toBsonDocument(forNameResult, codecRegistry2);

    // Assert
    verify(codecRegistry2, atLeast(1)).get(isA(Class.class));
    assertEquals(4, actualToBsonDocumentResult.size());
    assertTrue(actualToBsonDocumentResult.get("entityAttributes.path") instanceof BsonNull);
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonString);
    BsonValue getResult2 = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult2 instanceof BsonString);
    BsonValue getResult3 = actualToBsonDocumentResult.get("versionId");
    assertTrue(getResult3 instanceof BsonString);
    assertEquals(getResult3, getResult);
    assertEquals(getResult3, getResult2);
  }

  /**
   * Test {@link EntitiesMongo#createOrUpdate(String, String, String, List)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code entityDefinitions}.
   * <p>
   * Method under test: {@link EntitiesMongo#createOrUpdate(String, String, String, List)}
   */
  @Test
  @DisplayName("Test createOrUpdate(String, String, String, List) with 'groupId', 'artifactId', 'versionId', 'entityDefinitions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.createOrUpdate(String, String, String, List)"})
  void testCreateOrUpdateWithGroupIdArtifactIdVersionIdEntityDefinitions() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertTrue(entitiesMongo.createOrUpdate("42", "42", "42", new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#createOrUpdate(List)} with {@code versionedEntities}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#createOrUpdate(List)}
   */
  @Test
  @DisplayName("Test createOrUpdate(List) with 'versionedEntities'; when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.createOrUpdate(List)"})
  void testCreateOrUpdateWithVersionedEntities_whenArrayList_thenReturnEmpty() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertTrue(entitiesMongo.createOrUpdate(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findLatestClassifierEntities(String, String, Integer)} with {@code classifier}, {@code search}, {@code limit}.
   * <ul>
   *   <li>Then calls {@link FindIterable#limit(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findLatestClassifierEntities(String, String, Integer)}
   */
  @Test
  @DisplayName("Test findLatestClassifierEntities(String, String, Integer) with 'classifier', 'search', 'limit'; then calls limit(int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findLatestClassifierEntities(String, String, Integer)"})
  void testFindLatestClassifierEntitiesWithClassifierSearchLimit_thenCallsLimit() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.limit(anyInt())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindLatestClassifierEntitiesResult = entitiesMongo
        .findLatestClassifierEntities("Classifier", "Search", 1);

    // Assert
    verify(findIterable2).limit(eq(1));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindLatestClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findLatestClassifierEntities(String, String, Integer)} with {@code classifier}, {@code search}, {@code limit}.
   * <ul>
   *   <li>Then calls {@link FindIterable#limit(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findLatestClassifierEntities(String, String, Integer)}
   */
  @Test
  @DisplayName("Test findLatestClassifierEntities(String, String, Integer) with 'classifier', 'search', 'limit'; then calls limit(int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findLatestClassifierEntities(String, String, Integer)"})
  void testFindLatestClassifierEntitiesWithClassifierSearchLimit_thenCallsLimit2() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.limit(anyInt())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindLatestClassifierEntitiesResult = entitiesMongo
        .findLatestClassifierEntities("Classifier", null, 1);

    // Assert
    verify(findIterable2).limit(eq(1));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindLatestClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findLatestClassifierEntities(String, String, Integer)} with {@code classifier}, {@code search}, {@code limit}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findLatestClassifierEntities(String, String, Integer)}
   */
  @Test
  @DisplayName("Test findLatestClassifierEntities(String, String, Integer) with 'classifier', 'search', 'limit'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findLatestClassifierEntities(String, String, Integer)"})
  void testFindLatestClassifierEntitiesWithClassifierSearchLimit_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindLatestClassifierEntitiesResult = entitiesMongo
        .findLatestClassifierEntities("Classifier", "Search", null);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindLatestClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findLatestClassifierEntities(String)} with {@code classifier}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findLatestClassifierEntities(String)}
   */
  @Test
  @DisplayName("Test findLatestClassifierEntities(String) with 'classifier'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findLatestClassifierEntities(String)"})
  void testFindLatestClassifierEntitiesWithClassifier_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindLatestClassifierEntitiesResult = entitiesMongo
        .findLatestClassifierEntities("Classifier");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindLatestClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findReleasedClassifierEntities(String, String, Integer)} with {@code classifier}, {@code search}, {@code limit}.
   * <ul>
   *   <li>Then calls {@link FindIterable#limit(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findReleasedClassifierEntities(String, String, Integer)}
   */
  @Test
  @DisplayName("Test findReleasedClassifierEntities(String, String, Integer) with 'classifier', 'search', 'limit'; then calls limit(int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findReleasedClassifierEntities(String, String, Integer)"})
  void testFindReleasedClassifierEntitiesWithClassifierSearchLimit_thenCallsLimit() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.limit(anyInt())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindReleasedClassifierEntitiesResult = entitiesMongo
        .findReleasedClassifierEntities("Classifier", "Search", 1);

    // Assert
    verify(findIterable2).limit(eq(1));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindReleasedClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findReleasedClassifierEntities(String, String, Integer)} with {@code classifier}, {@code search}, {@code limit}.
   * <ul>
   *   <li>Then calls {@link FindIterable#limit(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findReleasedClassifierEntities(String, String, Integer)}
   */
  @Test
  @DisplayName("Test findReleasedClassifierEntities(String, String, Integer) with 'classifier', 'search', 'limit'; then calls limit(int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findReleasedClassifierEntities(String, String, Integer)"})
  void testFindReleasedClassifierEntitiesWithClassifierSearchLimit_thenCallsLimit2() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.limit(anyInt())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindReleasedClassifierEntitiesResult = entitiesMongo
        .findReleasedClassifierEntities("Classifier", null, 1);

    // Assert
    verify(findIterable2).limit(eq(1));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindReleasedClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findReleasedClassifierEntities(String, String, Integer)} with {@code classifier}, {@code search}, {@code limit}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findReleasedClassifierEntities(String, String, Integer)}
   */
  @Test
  @DisplayName("Test findReleasedClassifierEntities(String, String, Integer) with 'classifier', 'search', 'limit'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findReleasedClassifierEntities(String, String, Integer)"})
  void testFindReleasedClassifierEntitiesWithClassifierSearchLimit_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindReleasedClassifierEntitiesResult = entitiesMongo
        .findReleasedClassifierEntities("Classifier", "Search", null);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindReleasedClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findReleasedClassifierEntities(String)} with {@code classifier}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findReleasedClassifierEntities(String)}
   */
  @Test
  @DisplayName("Test findReleasedClassifierEntities(String) with 'classifier'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findReleasedClassifierEntities(String)"})
  void testFindReleasedClassifierEntitiesWithClassifier_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindReleasedClassifierEntitiesResult = entitiesMongo
        .findReleasedClassifierEntities("Classifier");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindReleasedClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List)} with {@code classifier}, {@code projectVersions}.
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List) with 'classifier', 'projectVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierEntitiesByVersions(String, List)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersions() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entitiesMongo
        .findClassifierEntitiesByVersions("Classifier", new ArrayList<>());

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List)} with {@code classifier}, {@code projectVersions}.
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List) with 'classifier', 'projectVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierEntitiesByVersions(String, List)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersions2() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entitiesMongo
        .findClassifierEntitiesByVersions("Classifier", projectVersions);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List)} with {@code classifier}, {@code projectVersions}.
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List) with 'classifier', 'projectVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierEntitiesByVersions(String, List)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersions3() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entitiesMongo
        .findClassifierEntitiesByVersions("Classifier", projectVersions);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)} with {@code classifier}, {@code projectVersions}, {@code search}, {@code limit}.
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List, String, Integer) with 'classifier', 'projectVersions', 'search', 'limit'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierEntitiesByVersions(String, List, String, Integer)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersionsSearchLimit() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.limit(anyInt())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entitiesMongo
        .findClassifierEntitiesByVersions("Classifier", new ArrayList<>(), "Search", 1);

    // Assert
    verify(findIterable2).limit(eq(1));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)} with {@code classifier}, {@code projectVersions}, {@code search}, {@code limit}.
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List, String, Integer) with 'classifier', 'projectVersions', 'search', 'limit'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierEntitiesByVersions(String, List, String, Integer)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersionsSearchLimit2() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.limit(anyInt())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entitiesMongo
        .findClassifierEntitiesByVersions("Classifier", projectVersions, "Search", 1);

    // Assert
    verify(findIterable2).limit(eq(1));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)} with {@code classifier}, {@code projectVersions}, {@code search}, {@code limit}.
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List, String, Integer) with 'classifier', 'projectVersions', 'search', 'limit'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierEntitiesByVersions(String, List, String, Integer)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersionsSearchLimit3() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.limit(anyInt())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entitiesMongo
        .findClassifierEntitiesByVersions("Classifier", projectVersions, "Search", 1);

    // Assert
    verify(findIterable2).limit(eq(1));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)} with {@code classifier}, {@code projectVersions}, {@code search}, {@code limit}.
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List, String, Integer) with 'classifier', 'projectVersions', 'search', 'limit'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierEntitiesByVersions(String, List, String, Integer)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersionsSearchLimit4() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    FindIterable<Document> findIterable2 = mock(FindIterable.class);
    when(findIterable2.limit(anyInt())).thenReturn(findIterable);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable2);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entitiesMongo
        .findClassifierEntitiesByVersions("Classifier", new ArrayList<>(), null, 1);

    // Assert
    verify(findIterable2).limit(eq(1));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)} with {@code classifier}, {@code projectVersions}, {@code search}, {@code limit}.
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List, String, Integer) with 'classifier', 'projectVersions', 'search', 'limit'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierEntitiesByVersions(String, List, String, Integer)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersionsSearchLimit5() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entitiesMongo
        .findClassifierEntitiesByVersions("Classifier", new ArrayList<>(), "Search", null);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)} with {@code classifier}, {@code projectVersions}, {@code search}, {@code limit}.
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierEntitiesByVersions(String, List, String, Integer)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List, String, Integer) with 'classifier', 'projectVersions', 'search', 'limit'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierEntitiesByVersions(String, List, String, Integer)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersionsSearchLimit6() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    when(findIterable.limit(anyInt())).thenThrow(new IllegalArgumentException("entityAttributes.classifierPath"));
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> entitiesMongo.findClassifierEntitiesByVersions("Classifier", null, null, 1));
    verify(findIterable).limit(eq(1));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
  }

  /**
   * Test {@link EntitiesMongo#findLatestClassifierSummaries(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findLatestClassifierSummaries(String)}
   */
  @Test
  @DisplayName("Test findLatestClassifierSummaries(String); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findLatestClassifierSummaries(String)"})
  void testFindLatestClassifierSummaries_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntityOverview> actualFindLatestClassifierSummariesResult = entitiesMongo
        .findLatestClassifierSummaries("Classifier");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindLatestClassifierSummariesResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findLatestClassifierSummaries(String)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findLatestClassifierSummaries(String)}
   */
  @Test
  @DisplayName("Test findLatestClassifierSummaries(String); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findLatestClassifierSummaries(String)"})
  void testFindLatestClassifierSummaries_thenThrowIllegalArgumentException() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doThrow(new IllegalArgumentException("entityAttributes.classifierPath")).when(findIterable)
        .forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> entitiesMongo.findLatestClassifierSummaries("Classifier"));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link EntitiesMongo#findReleasedClassifierSummaries(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findReleasedClassifierSummaries(String)}
   */
  @Test
  @DisplayName("Test findReleasedClassifierSummaries(String); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findReleasedClassifierSummaries(String)"})
  void testFindReleasedClassifierSummaries_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntityOverview> actualFindReleasedClassifierSummariesResult = entitiesMongo
        .findReleasedClassifierSummaries("Classifier");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindReleasedClassifierSummariesResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findReleasedClassifierSummaries(String)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findReleasedClassifierSummaries(String)}
   */
  @Test
  @DisplayName("Test findReleasedClassifierSummaries(String); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findReleasedClassifierSummaries(String)"})
  void testFindReleasedClassifierSummaries_thenThrowIllegalArgumentException() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doThrow(new IllegalArgumentException("entityAttributes.classifierPath")).when(findIterable)
        .forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> entitiesMongo.findReleasedClassifierSummaries("Classifier"));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link EntitiesMongo#findClassifierSummariesByVersions(String, List)}.
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierSummariesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierSummariesByVersions(String, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierSummariesByVersions(String, List)"})
  void testFindClassifierSummariesByVersions() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntityOverview> actualFindClassifierSummariesByVersionsResult = entitiesMongo
        .findClassifierSummariesByVersions("Classifier", projectVersions);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindClassifierSummariesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findClassifierSummariesByVersions(String, List)}.
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierSummariesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierSummariesByVersions(String, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierSummariesByVersions(String, List)"})
  void testFindClassifierSummariesByVersions2() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntityOverview> actualFindClassifierSummariesByVersionsResult = entitiesMongo
        .findClassifierSummariesByVersions("Classifier", projectVersions);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindClassifierSummariesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#findClassifierSummariesByVersions(String, List)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierSummariesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierSummariesByVersions(String, List); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierSummariesByVersions(String, List)"})
  void testFindClassifierSummariesByVersions_thenThrowIllegalArgumentException() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doThrow(new IllegalArgumentException("entityAttributes.classifierPath")).when(findIterable)
        .forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> entitiesMongo.findClassifierSummariesByVersions("Classifier", new ArrayList<>()));
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link EntitiesMongo#findClassifierSummariesByVersions(String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#findClassifierSummariesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierSummariesByVersions(String, List); when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.findClassifierSummariesByVersions(String, List)"})
  void testFindClassifierSummariesByVersions_whenArrayList_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(databaseProvider);

    // Act
    List<DepotEntityOverview> actualFindClassifierSummariesByVersionsResult = entitiesMongo
        .findClassifierSummariesByVersions("Classifier", new ArrayList<>());

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("entities"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindClassifierSummariesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#curateDepotEntityOverview(FindIterable)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#curateDepotEntityOverview(FindIterable)}
   */
  @Test
  @DisplayName("Test curateDepotEntityOverview(FindIterable); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.curateDepotEntityOverview(FindIterable)"})
  void testCurateDepotEntityOverview_thenThrowIllegalArgumentException() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    FindIterable query = mock(FindIterable.class);
    doThrow(new IllegalArgumentException("foo")).when(query).forEach(Mockito.<Consumer<Object>>any());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> entitiesMongo.curateDepotEntityOverview(query));
    verify(query).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link EntitiesMongo#curateDepotEntityOverview(FindIterable)}.
   * <ul>
   *   <li>When {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#curateDepotEntityOverview(FindIterable)}
   */
  @Test
  @DisplayName("Test curateDepotEntityOverview(FindIterable); when FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.curateDepotEntityOverview(FindIterable)"})
  void testCurateDepotEntityOverview_whenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    FindIterable query = mock(FindIterable.class);
    doNothing().when(query).forEach(Mockito.<Consumer<Object>>any());

    // Act
    List<DepotEntityOverview> actualCurateDepotEntityOverviewResult = entitiesMongo.curateDepotEntityOverview(query);

    // Assert
    verify(query).forEach(isA(Consumer.class));
    assertTrue(actualCurateDepotEntityOverviewResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#curateDepotEntity(FindIterable)}.
   * <ul>
   *   <li>When {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#curateDepotEntity(FindIterable)}
   */
  @Test
  @DisplayName("Test curateDepotEntity(FindIterable); when FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesMongo.curateDepotEntity(FindIterable)"})
  void testCurateDepotEntity_whenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    FindIterable query = mock(FindIterable.class);
    doNothing().when(query).forEach(Mockito.<Consumer<Object>>any());

    // Act
    List<DepotEntity> actualCurateDepotEntityResult = entitiesMongo.curateDepotEntity(query);

    // Assert
    verify(query).forEach(isA(Consumer.class));
    assertTrue(actualCurateDepotEntityResult.isEmpty());
  }

  /**
   * Test {@link EntitiesMongo#resolvedToEntityDefinition(StoredEntity)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#resolvedToEntityDefinition(StoredEntity)}
   */
  @Test
  @DisplayName("Test resolvedToEntityDefinition(StoredEntity); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.finos.legend.sdlc.domain.model.entity.Entity EntitiesMongo.resolvedToEntityDefinition(StoredEntity)"})
  void testResolvedToEntityDefinition_thenReturnNull() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertNull(entitiesMongo.resolvedToEntityDefinition(new StoredEntityData("42", "42", "42")));
  }

  /**
   * Test {@link EntitiesMongo#resolvedToEntityDefinition(StoredEntity)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesMongo#resolvedToEntityDefinition(StoredEntity)}
   */
  @Test
  @DisplayName("Test resolvedToEntityDefinition(StoredEntity); when 'null'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.finos.legend.sdlc.domain.model.entity.Entity EntitiesMongo.resolvedToEntityDefinition(StoredEntity)"})
  void testResolvedToEntityDefinition_whenNull_thenThrowIllegalStateException() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo<>(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> entitiesMongo.resolvedToEntityDefinition(null));
  }
}
