package org.finos.legend.depot.store.mongo.admin;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.internal.MongoDatabaseImpl;
import java.util.function.Consumer;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CoreDataMigrationsDiffblueTest {
  /**
   * Test {@link CoreDataMigrations#migrationToProjectVersions()}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then calls {@link MongoCollection#drop()}.
   * </ul>
   *
   * <p>Method under test: {@link CoreDataMigrations#migrationToProjectVersions()}
   */
  @Test
  @DisplayName(
      "Test migrationToProjectVersions(); given FindIterable forEach(Consumer) does nothing; then calls drop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CoreDataMigrations.migrationToProjectVersions()"})
  void testMigrationToProjectVersions_givenFindIterableForEachDoesNothing_thenCallsDrop() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);
    doNothing().when(mongoCollection).drop();

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    new CoreDataMigrations(mongoDatabase).migrationToProjectVersions();

    // Assert
    verify(mongoCollection).drop();
    verify(mongoCollection).find();
    verify(mongoDatabase, atLeast(1)).getCollection(Mockito.<String>any());
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link CoreDataMigrations#cleanUpProjectData()}.
   *
   * <ul>
   *   <li>Given {@link FindIterable} {@link FindIterable#forEach(Consumer)} does nothing.
   *   <li>Then calls {@link MongoCollection#find()}.
   * </ul>
   *
   * <p>Method under test: {@link CoreDataMigrations#cleanUpProjectData()}
   */
  @Test
  @DisplayName(
      "Test cleanUpProjectData(); given FindIterable forEach(Consumer) does nothing; then calls find()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CoreDataMigrations.cleanUpProjectData()"})
  void testCleanUpProjectData_givenFindIterableForEachDoesNothing_thenCallsFind() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    new CoreDataMigrations(mongoDatabase).cleanUpProjectData();

    // Assert
    verify(mongoCollection).find();
    verify(mongoDatabase).getCollection("project-configurations");
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link CoreDataMigrations#calculateTransitiveDependenciesForAllProjectVersions()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoCollection#drop()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * CoreDataMigrations#calculateTransitiveDependenciesForAllProjectVersions()}
   */
  @Test
  @DisplayName("Test calculateTransitiveDependenciesForAllProjectVersions(); then calls drop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CoreDataMigrations.calculateTransitiveDependenciesForAllProjectVersions()"
  })
  void testCalculateTransitiveDependenciesForAllProjectVersions_thenCallsDrop() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);
    doNothing().when(mongoCollection).drop();

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    new CoreDataMigrations(mongoDatabase).calculateTransitiveDependenciesForAllProjectVersions();

    // Assert
    verify(mongoCollection).drop();
    verify(mongoCollection).find();
    verify(mongoDatabase, atLeast(1)).getCollection(Mockito.<String>any());
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link CoreDataMigrations#addTransitiveDependenciesToVersionData()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoCollection#find()}.
   * </ul>
   *
   * <p>Method under test: {@link CoreDataMigrations#addTransitiveDependenciesToVersionData()}
   */
  @Test
  @DisplayName("Test addTransitiveDependenciesToVersionData(); then calls find()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CoreDataMigrations.addTransitiveDependenciesToVersionData()"})
  void testAddTransitiveDependenciesToVersionData_thenCallsFind() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    new CoreDataMigrations(mongoDatabase).addTransitiveDependenciesToVersionData();

    // Assert
    verify(mongoCollection).find();
    verify(mongoDatabase, atLeast(1)).getCollection(Mockito.<String>any());
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link CoreDataMigrations#addLatestVersionToProjectData()}.
   *
   * <ul>
   *   <li>Then calls {@link MongoCollection#find()}.
   * </ul>
   *
   * <p>Method under test: {@link CoreDataMigrations#addLatestVersionToProjectData()}
   */
  @Test
  @DisplayName("Test addLatestVersionToProjectData(); then calls find()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CoreDataMigrations.addLatestVersionToProjectData()"})
  void testAddLatestVersionToProjectData_thenCallsFind() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());

    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);

    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    new CoreDataMigrations(mongoDatabase).addLatestVersionToProjectData();

    // Assert
    verify(mongoCollection).find();
    verify(mongoDatabase, atLeast(1)).getCollection(Mockito.<String>any());
    verify(findIterable).forEach(isA(Consumer.class));
  }
}
