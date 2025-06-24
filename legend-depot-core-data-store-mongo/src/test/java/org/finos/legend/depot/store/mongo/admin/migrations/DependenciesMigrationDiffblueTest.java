package org.finos.legend.depot.store.mongo.admin.migrations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
class DependenciesMigrationDiffblueTest {
  /**
   * Test {@link DependenciesMigration#calculateTransitiveDependenciesForAllProjectVersions()}.
   * <p>
   * Method under test: {@link DependenciesMigration#calculateTransitiveDependenciesForAllProjectVersions()}
   */
  @Test
  @DisplayName("Test calculateTransitiveDependenciesForAllProjectVersions()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DependenciesMigration.calculateTransitiveDependenciesForAllProjectVersions()"})
  void testCalculateTransitiveDependenciesForAllProjectVersions() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);
    doNothing().when(mongoCollection).drop();
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    new DependenciesMigration(mongoDatabase).calculateTransitiveDependenciesForAllProjectVersions();

    // Assert
    verify(mongoCollection).drop();
    verify(mongoCollection).find();
    verify(mongoDatabase, atLeast(1)).getCollection(Mockito.<String>any());
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link DependenciesMigration#calculateTransitiveDependenciesForAllProjectVersions()}.
   * <p>
   * Method under test: {@link DependenciesMigration#calculateTransitiveDependenciesForAllProjectVersions()}
   */
  @Test
  @DisplayName("Test calculateTransitiveDependenciesForAllProjectVersions()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DependenciesMigration.calculateTransitiveDependenciesForAllProjectVersions()"})
  void testCalculateTransitiveDependenciesForAllProjectVersions2() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doThrow(new IllegalStateException("versions")).when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);
    doNothing().when(mongoCollection).drop();
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> new DependenciesMigration(mongoDatabase).calculateTransitiveDependenciesForAllProjectVersions());
    verify(mongoCollection).drop();
    verify(mongoCollection).find();
    verify(mongoDatabase, atLeast(1)).getCollection(Mockito.<String>any());
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link DependenciesMigration#addTransitiveDependenciesToVersionData()}.
   * <ul>
   *   <li>Then calls {@link Iterable#forEach(Consumer)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DependenciesMigration#addTransitiveDependenciesToVersionData()}
   */
  @Test
  @DisplayName("Test addTransitiveDependenciesToVersionData(); then calls forEach(Consumer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DependenciesMigration.addTransitiveDependenciesToVersionData()"})
  void testAddTransitiveDependenciesToVersionData_thenCallsForEach() {
    // Arrange
    FindIterable<Document> findIterable = mock(FindIterable.class);
    doNothing().when(findIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenReturn(findIterable);
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act
    new DependenciesMigration(mongoDatabase).addTransitiveDependenciesToVersionData();

    // Assert
    verify(mongoCollection).find();
    verify(mongoDatabase, atLeast(1)).getCollection(Mockito.<String>any());
    verify(findIterable).forEach(isA(Consumer.class));
  }

  /**
   * Test {@link DependenciesMigration#addTransitiveDependenciesToVersionData()}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DependenciesMigration#addTransitiveDependenciesToVersionData()}
   */
  @Test
  @DisplayName("Test addTransitiveDependenciesToVersionData(); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DependenciesMigration.addTransitiveDependenciesToVersionData()"})
  void testAddTransitiveDependenciesToVersionData_thenThrowIllegalStateException() {
    // Arrange
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(mongoCollection.find()).thenThrow(new IllegalStateException("versions"));
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.getCollection(Mockito.<String>any())).thenReturn(mongoCollection);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> new DependenciesMigration(mongoDatabase).addTransitiveDependenciesToVersionData());
    verify(mongoCollection).find();
    verify(mongoDatabase, atLeast(1)).getCollection(Mockito.<String>any());
  }
}
