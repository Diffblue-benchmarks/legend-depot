package org.finos.legend.depot.store.mongo.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
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
import com.mongodb.client.model.IndexOptions;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProjectsMongoDiffblueTest {
  /**
   * Test {@link ProjectsMongo#ProjectsMongo(MongoDatabase)}.
   * <p>
   * Method under test: {@link ProjectsMongo#ProjectsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new ProjectsMongo(MongoDatabase)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsMongo.<init>(MongoDatabase)"})
  void testNewProjectsMongo() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    // Act and Assert
    assertSame(databaseProvider, new ProjectsMongo(databaseProvider).getDatabase());
  }

  /**
   * Test {@link ProjectsMongo#ProjectsMongo(MongoDatabase)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Database is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsMongo#ProjectsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new ProjectsMongo(MongoDatabase); when 'null'; then return Database is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsMongo.<init>(MongoDatabase)"})
  void testNewProjectsMongo_whenNull_thenReturnDatabaseIsNull() {
    // Arrange, Act and Assert
    assertNull(new ProjectsMongo(null).getDatabase());
  }

  /**
   * Test {@link ProjectsMongo#buildIndexes()}.
   * <p>
   * Method under test: {@link ProjectsMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsMongo.buildIndexes()"})
  void testBuildIndexes() {
    // Arrange and Act
    List<IndexModel> actualBuildIndexesResult = ProjectsMongo.buildIndexes();

    // Assert
    assertEquals(1, actualBuildIndexesResult.size());
    IndexModel getResult = actualBuildIndexesResult.get(0);
    Bson keys = getResult.getKeys();
    assertTrue(keys instanceof Map);
    IndexOptions options = getResult.getOptions();
    assertEquals("groupId-artifactId", options.getName());
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
    assertEquals(2, ((Map<String, BsonInt32>) keys).size());
    assertFalse(options.isBackground());
    assertFalse(options.isSparse());
    assertTrue(options.isUnique());
    assertTrue(((Map<String, BsonInt32>) keys).containsKey("groupId"));
    BsonInt32 expectedGetResult = ((Map<String, BsonInt32>) keys).get("groupId");
    assertSame(expectedGetResult, ((Map<String, BsonInt32>) keys).get("artifactId"));
  }

  /**
   * Test {@link ProjectsMongo#getKeyFilter(StoreProjectData)} with {@code StoreProjectData}.
   * <p>
   * Method under test: {@link ProjectsMongo#getKeyFilter(StoreProjectData)}
   */
  @Test
  @DisplayName("Test getKeyFilter(StoreProjectData) with 'StoreProjectData'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson ProjectsMongo.getKeyFilter(StoreProjectData)"})
  void testGetKeyFilterWithStoreProjectData() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsMongo projectsMongo = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act
    Bson actualKeyFilter = projectsMongo.getKeyFilter(new StoreProjectData("myproject", "42", "42"));
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry2 = mock(CodecRegistry.class);
    when(codecRegistry2.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult = actualKeyFilter.toBsonDocument(forNameResult, codecRegistry2);

    // Assert
    verify(codecRegistry2, atLeast(1)).get(isA(Class.class));
    assertEquals(2, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonString);
    BsonValue getResult2 = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult2 instanceof BsonString);
    assertEquals(getResult2, getResult);
  }

  /**
   * Test {@link ProjectsMongo#getCollection()}.
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoCollection ProjectsMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mock(MongoCollection.class));

    // Act
    new ProjectsMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection(eq("project-configurations"));
  }

  /**
   * Test {@link ProjectsMongo#validateNewData(StoreProjectData)} with {@code StoreProjectData}.
   * <p>
   * Method under test: {@link ProjectsMongo#validateNewData(StoreProjectData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectData) with 'StoreProjectData'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsMongo.validateNewData(StoreProjectData)"})
  void testValidateNewDataWithStoreProjectData() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsMongo projectsMongo = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsMongo.validateNewData(new StoreProjectData("myproject", "42", "42")));
  }

  /**
   * Test {@link ProjectsMongo#validateNewData(StoreProjectData)} with {@code StoreProjectData}.
   * <p>
   * Method under test: {@link ProjectsMongo#validateNewData(StoreProjectData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectData) with 'StoreProjectData'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsMongo.validateNewData(StoreProjectData)"})
  void testValidateNewDataWithStoreProjectData2() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsMongo projectsMongo = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsMongo.validateNewData(new StoreProjectData("PROD-9", "42", "42")));
  }

  /**
   * Test {@link ProjectsMongo#validateNewData(StoreProjectData)} with {@code StoreProjectData}.
   * <p>
   * Method under test: {@link ProjectsMongo#validateNewData(StoreProjectData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectData) with 'StoreProjectData'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsMongo.validateNewData(StoreProjectData)"})
  void testValidateNewDataWithStoreProjectData3() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsMongo projectsMongo = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsMongo.validateNewData(new StoreProjectData("PROD-9", "", "42")));
  }

  /**
   * Test {@link ProjectsMongo#validateNewData(StoreProjectData)} with {@code StoreProjectData}.
   * <ul>
   *   <li>When {@link StoreProjectData#StoreProjectData()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsMongo#validateNewData(StoreProjectData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectData) with 'StoreProjectData'; when StoreProjectData()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsMongo.validateNewData(StoreProjectData)"})
  void testValidateNewDataWithStoreProjectData_whenStoreProjectData() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsMongo projectsMongo = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsMongo.validateNewData(new StoreProjectData()));
  }

  /**
   * Test {@link ProjectsMongo#getAll()}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsMongo#getAll()}
   */
  @Test
  @DisplayName("Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsMongo.getAll()"})
  void testGetAll_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoreProjectData> actualAll = new ProjectsMongo(databaseProvider).getAll();

    // Assert
    verify(mongoCollection).find();
    verify(databaseProvider).getCollection(eq("project-configurations"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link ProjectsMongo#findByProjectId(String)}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsMongo#findByProjectId(String)}
   */
  @Test
  @DisplayName("Test findByProjectId(String); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsMongo.findByProjectId(String)"})
  void testFindByProjectId_givenFindIterableForEachDoesNothing_thenReturnEmpty() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    List<StoreProjectData> actualFindByProjectIdResult = new ProjectsMongo(databaseProvider)
        .findByProjectId("myproject");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("project-configurations"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindByProjectIdResult.isEmpty());
  }

  /**
   * Test {@link ProjectsMongo#find(String, String)} with {@code groupId}, {@code artifactId}.
   * <ul>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsMongo#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'; then return not Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ProjectsMongo.find(String, String)"})
  void testFindWithGroupIdArtifactId_thenReturnNotPresent() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find(Mockito.<Bson>any())).thenReturn(findIterable);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    Optional<StoreProjectData> actualFindResult = new ProjectsMongo(databaseProvider).find("42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("project-configurations"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertFalse(actualFindResult.isPresent());
  }
}
