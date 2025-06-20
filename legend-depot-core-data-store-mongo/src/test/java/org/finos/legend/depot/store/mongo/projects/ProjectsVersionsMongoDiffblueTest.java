package org.finos.legend.depot.store.mongo.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import org.bson.UuidRepresentation;
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
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#ProjectsVersionsMongo(MongoDatabase)}
   */
  @Test
  @DisplayName("Test new ProjectsVersionsMongo(MongoDatabase)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsVersionsMongo.<init>(MongoDatabase)"})
  void testNewProjectsVersionsMongo() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl databaseProvider = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    // Act and Assert
    assertSame(databaseProvider, new ProjectsVersionsMongo(databaseProvider).getDatabase());
  }

  /**
   * Test {@link ProjectsVersionsMongo#buildIndexes()}.
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#buildIndexes()}
   */
  @Test
  @DisplayName("Test buildIndexes()")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#getAll()}
   */
  @Test
  @DisplayName("Test getAll(); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
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
    verify(databaseProvider).getCollection(eq("versions"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link ProjectsVersionsMongo#findByUpdatedDate(long, long)}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#findByUpdatedDate(long, long)}
   */
  @Test
  @DisplayName("Test findByUpdatedDate(long, long); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
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
    List<StoreProjectVersionData> actualFindByUpdatedDateResult = new ProjectsVersionsMongo(databaseProvider)
        .findByUpdatedDate(1L, 1L);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("versions"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindByUpdatedDateResult.isEmpty());
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <ul>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#find(String, String, String)}
   */
  @Test
  @DisplayName("Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'; then return not Present")
  @Tag("MaintainedByDiffblue")
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
    Optional<StoreProjectVersionData> actualFindResult = new ProjectsVersionsMongo(databaseProvider).find("42", "42",
        "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("versions"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertFalse(actualFindResult.isPresent());
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#find(String, String, String)}
   */
  @Test
  @DisplayName("Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ProjectsVersionsMongo.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> new ProjectsVersionsMongo(mock(MongoDatabaseImpl.class)).find("42", "42", null));
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#find(String, String, String)}
   */
  @Test
  @DisplayName("Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ProjectsVersionsMongo.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId_thenThrowIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> new ProjectsVersionsMongo(mock(MongoDatabaseImpl.class)).find("42", "42", ""));
  }

  /**
   * Test {@link ProjectsVersionsMongo#find(String, String)} with {@code groupId}, {@code artifactId}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'; then return Empty")
  @Tag("MaintainedByDiffblue")
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
    List<StoreProjectVersionData> actualFindResult = new ProjectsVersionsMongo(databaseProvider).find("42", "42");

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("versions"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link ProjectsVersionsMongo#findVersion(Boolean)}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#findVersion(Boolean)}
   */
  @Test
  @DisplayName("Test findVersion(Boolean); given FindIterable forEach(Consumer) does nothing; then return Empty")
  @Tag("MaintainedByDiffblue")
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
    List<StoreProjectVersionData> actualFindVersionResult = new ProjectsVersionsMongo(databaseProvider)
        .findVersion(true);

    // Assert
    verify(mongoCollection).find(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("versions"));
    verify(findIterable).forEach(isA(Consumer.class));
    assertTrue(actualFindVersionResult.isEmpty());
  }

  /**
   * Test {@link ProjectsVersionsMongo#getVersionCount(String, String)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#getVersionCount(String, String)}
   */
  @Test
  @DisplayName("Test getVersionCount(String, String); then return three")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long ProjectsVersionsMongo.getVersionCount(String, String)"})
  void testGetVersionCount_thenReturnThree() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.countDocuments(Mockito.<Bson>any())).thenReturn(3L);
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    long actualVersionCount = new ProjectsVersionsMongo(databaseProvider).getVersionCount("42", "42");

    // Assert
    verify(mongoCollection).countDocuments(isA(Bson.class));
    verify(databaseProvider).getCollection(eq("versions"));
    assertEquals(3L, actualVersionCount);
  }

  /**
   * Test {@link ProjectsVersionsMongo#getCollection()}.
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#getCollection(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#getCollection()}
   */
  @Test
  @DisplayName("Test getCollection(); then calls getCollection(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoCollection ProjectsVersionsMongo.getCollection()"})
  void testGetCollection_thenCallsGetCollection() {
    // Arrange
    MongoDatabaseImpl databaseProvider = mock(MongoDatabaseImpl.class);
    when(databaseProvider.getCollection(Mockito.<String>any())).thenReturn(mock(MongoCollection.class));

    // Act
    new ProjectsVersionsMongo(databaseProvider).getCollection();

    // Assert
    verify(databaseProvider).getCollection(eq("versions"));
  }

  /**
   * Test {@link ProjectsVersionsMongo#getKeyFilter(StoreProjectVersionData)} with {@code StoreProjectVersionData}.
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#getKeyFilter(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test getKeyFilter(StoreProjectVersionData) with 'StoreProjectVersionData'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson ProjectsVersionsMongo.getKeyFilter(StoreProjectVersionData)"})
  void testGetKeyFilterWithStoreProjectVersionData() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsVersionsMongo projectsVersionsMongo = new ProjectsVersionsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act
    Bson actualKeyFilter = projectsVersionsMongo.getKeyFilter(new StoreProjectVersionData());
    Class<Object> forNameResult = Object.class;
    BsonDocument actualToBsonDocumentResult = actualKeyFilter.toBsonDocument(forNameResult, mock(CodecRegistry.class));

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
   * Test {@link ProjectsVersionsMongo#getKeyFilter(StoreProjectVersionData)} with {@code StoreProjectVersionData}.
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#getKeyFilter(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test getKeyFilter(StoreProjectVersionData) with 'StoreProjectVersionData'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson ProjectsVersionsMongo.getKeyFilter(StoreProjectVersionData)"})
  void testGetKeyFilterWithStoreProjectVersionData2() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsVersionsMongo projectsVersionsMongo = new ProjectsVersionsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act
    Bson actualKeyFilter = projectsVersionsMongo.getKeyFilter(new StoreProjectVersionData("42", "42", "42"));
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry2 = mock(CodecRegistry.class);
    when(codecRegistry2.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult = actualKeyFilter.toBsonDocument(forNameResult, codecRegistry2);

    // Assert
    verify(codecRegistry2, atLeast(1)).get(isA(Class.class));
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
   * Test {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)} with {@code StoreProjectVersionData}.
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectVersionData) with 'StoreProjectVersionData'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsVersionsMongo.validateNewData(StoreProjectVersionData)"})
  void testValidateNewDataWithStoreProjectVersionData() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsVersionsMongo projectsVersionsMongo = new ProjectsVersionsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsVersionsMongo.validateNewData(new StoreProjectVersionData("42", "42", "42")));
  }

  /**
   * Test {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)} with {@code StoreProjectVersionData}.
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectVersionData) with 'StoreProjectVersionData'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsVersionsMongo.validateNewData(StoreProjectVersionData)"})
  void testValidateNewDataWithStoreProjectVersionData2() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsVersionsMongo projectsVersionsMongo = new ProjectsVersionsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsVersionsMongo.validateNewData(new StoreProjectVersionData("", "42", "42")));
  }

  /**
   * Test {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)} with {@code StoreProjectVersionData}.
   * <ul>
   *   <li>When {@link StoreProjectVersionData#StoreProjectVersionData()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsVersionsMongo#validateNewData(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test validateNewData(StoreProjectVersionData) with 'StoreProjectVersionData'; when StoreProjectVersionData()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsVersionsMongo.validateNewData(StoreProjectVersionData)"})
  void testValidateNewDataWithStoreProjectVersionData_whenStoreProjectVersionData() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsVersionsMongo projectsVersionsMongo = new ProjectsVersionsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsVersionsMongo.validateNewData(new StoreProjectVersionData()));
  }
}
