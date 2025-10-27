package org.finos.legend.depot.store.model.generations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.junit.jupiter.api.Test;

class StoredFileGenerationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredFileGeneration#equals(Object)}
   *   <li>{@link StoredFileGeneration#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StoredFileGeneration storedFileGeneration = new StoredFileGeneration("42", "42", "42", "Path", "Type",
        new DepotGeneration("Path", "Not all who wander are lost"));
    StoredFileGeneration storedFileGeneration2 = new StoredFileGeneration("42", "42", "42", "Path", "Type",
        new DepotGeneration("Path", "Not all who wander are lost"));

    // Act and Assert
    assertEquals(storedFileGeneration, storedFileGeneration2);
    int expectedHashCodeResult = storedFileGeneration.hashCode();
    assertEquals(expectedHashCodeResult, storedFileGeneration2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredFileGeneration#equals(Object)}
   *   <li>{@link StoredFileGeneration#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StoredFileGeneration storedFileGeneration = new StoredFileGeneration("42", "42", "42", "Path", "Type",
        new DepotGeneration("Path", "Not all who wander are lost"));

    // Act and Assert
    assertEquals(storedFileGeneration, storedFileGeneration);
    int expectedHashCodeResult = storedFileGeneration.hashCode();
    assertEquals(expectedHashCodeResult, storedFileGeneration.hashCode());
  }

  /**
   * Method under test: {@link StoredFileGeneration#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StoredFileGeneration storedFileGeneration = new StoredFileGeneration("Group Id", "42", "42", "Path", "Type",
        new DepotGeneration("Path", "Not all who wander are lost"));

    // Act and Assert
    assertNotEquals(storedFileGeneration, new StoredFileGeneration("42", "42", "42", "Path", "Type",
        new DepotGeneration("Path", "Not all who wander are lost")));
  }

  /**
   * Method under test: {@link StoredFileGeneration#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    StoredFileGeneration storedFileGeneration = new StoredFileGeneration("42", "42", "42", "Path", "Type",
        mock(DepotGeneration.class));

    // Act and Assert
    assertNotEquals(storedFileGeneration, new StoredFileGeneration("42", "42", "42", "Path", "Type",
        new DepotGeneration("Path", "Not all who wander are lost")));
  }

  /**
   * Method under test: {@link StoredFileGeneration#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredFileGeneration("42", "42", "42", "Path", "Type",
        new DepotGeneration("Path", "Not all who wander are lost")), null);
  }

  /**
   * Method under test: {@link StoredFileGeneration#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredFileGeneration("42", "42", "42", "Path", "Type",
        new DepotGeneration("Path", "Not all who wander are lost")), "Different type to StoredFileGeneration");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredFileGeneration#StoredFileGeneration(String, String, String, String, String, DepotGeneration)}
   *   <li>{@link StoredFileGeneration#getFile()}
   *   <li>{@link StoredFileGeneration#getId()}
   *   <li>{@link StoredFileGeneration#getPath()}
   *   <li>{@link StoredFileGeneration#getType()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    DepotGeneration fileGeneration = new DepotGeneration("Path", "Not all who wander are lost");

    // Act
    StoredFileGeneration actualStoredFileGeneration = new StoredFileGeneration("42", "42", "42", "Path", "Type",
        fileGeneration);
    DepotGeneration actualFile = actualStoredFileGeneration.getFile();
    String actualId = actualStoredFileGeneration.getId();
    String actualPath = actualStoredFileGeneration.getPath();
    String actualType = actualStoredFileGeneration.getType();

    // Assert
    assertEquals("", actualId);
    assertEquals("42", actualStoredFileGeneration.getArtifactId());
    assertEquals("42", actualStoredFileGeneration.getGroupId());
    assertEquals("42", actualStoredFileGeneration.getVersionId());
    assertEquals("Path", actualPath);
    assertEquals("Type", actualType);
    assertSame(fileGeneration, actualFile);
  }
}
