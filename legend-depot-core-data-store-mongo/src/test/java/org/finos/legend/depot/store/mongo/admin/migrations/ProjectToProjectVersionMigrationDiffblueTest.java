package org.finos.legend.depot.store.mongo.admin.migrations;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.internal.MongoDatabaseImpl;
import java.util.function.Consumer;
import org.bson.Document;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Disabled("Failed pitest")
class ProjectToProjectVersionMigrationDiffblueTest {
  /**
   * Test {@link ProjectToProjectVersionMigration#migrationToProjectVersions()}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then calls {@link MongoCollection#drop()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectToProjectVersionMigration#migrationToProjectVersions()}
   */
  @Test
  @DisplayName("Test migrationToProjectVersions(); given FindIterable forEach(Consumer) does nothing; then calls drop()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectToProjectVersionMigration.migrationToProjectVersions()"})
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
    new ProjectToProjectVersionMigration(mongoDatabase).migrationToProjectVersions();

    // Assert
    verify(mongoCollection).drop();
    verify(mongoCollection).find();
    verify(mongoDatabase, atLeast(1)).getCollection(Mockito.<String>any());
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link ProjectToProjectVersionMigration#cleanUpProjectData()}.
   * <ul>
   *   <li>Given {@link FindIterable} {@link Iterable#forEach(Consumer)} does nothing.</li>
   *   <li>Then calls {@link MongoCollection#find()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectToProjectVersionMigration#cleanUpProjectData()}
   */
  @Test
  @DisplayName("Test cleanUpProjectData(); given FindIterable forEach(Consumer) does nothing; then calls find()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectToProjectVersionMigration.cleanUpProjectData()"})
  void testCleanUpProjectData_givenFindIterableForEachDoesNothing_thenCallsFind() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    new ProjectToProjectVersionMigration(mongoDatabase).cleanUpProjectData();

    // Assert
    verify(mongoCollection).find();
    verify(mongoDatabase).getCollection(eq("project-configurations"));
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link ProjectToProjectVersionMigration#addLatestVersionToProjectData()}.
   * <ul>
   *   <li>Then calls {@link MongoCollection#find()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectToProjectVersionMigration#addLatestVersionToProjectData()}
   */
  @Test
  @DisplayName("Test addLatestVersionToProjectData(); then calls find()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectToProjectVersionMigration.addLatestVersionToProjectData()"})
  void testAddLatestVersionToProjectData_thenCallsFind() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    new ProjectToProjectVersionMigration(mongoDatabase).addLatestVersionToProjectData();

    // Assert
    verify(mongoCollection).find();
    verify(mongoDatabase, atLeast(1)).getCollection(Mockito.<String>any());
    verify(findIterable).forEach(isA(Consumer.class));
  }
}
