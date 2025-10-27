package org.finos.legend.depot.domain.generation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class DepotGenerationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DepotGeneration#equals(Object)}
   *   <li>{@link DepotGeneration#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    DepotGeneration depotGeneration = new DepotGeneration("Path", "Not all who wander are lost");
    DepotGeneration depotGeneration2 = new DepotGeneration("Path", "Not all who wander are lost");

    // Act and Assert
    assertEquals(depotGeneration, depotGeneration2);
    int expectedHashCodeResult = depotGeneration.hashCode();
    assertEquals(expectedHashCodeResult, depotGeneration2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DepotGeneration#equals(Object)}
   *   <li>{@link DepotGeneration#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    DepotGeneration depotGeneration = new DepotGeneration("Path", "Not all who wander are lost");

    // Act and Assert
    assertEquals(depotGeneration, depotGeneration);
    int expectedHashCodeResult = depotGeneration.hashCode();
    assertEquals(expectedHashCodeResult, depotGeneration.hashCode());
  }

  /**
   * Method under test: {@link DepotGeneration#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    DepotGeneration depotGeneration = new DepotGeneration("42", "Not all who wander are lost");

    // Act and Assert
    assertNotEquals(depotGeneration, new DepotGeneration("Path", "Not all who wander are lost"));
  }

  /**
   * Method under test: {@link DepotGeneration#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new DepotGeneration("Path", "Not all who wander are lost"), null);
  }

  /**
   * Method under test: {@link DepotGeneration#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new DepotGeneration("Path", "Not all who wander are lost"), "Different type to DepotGeneration");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DepotGeneration#DepotGeneration(String, String)}
   *   <li>{@link DepotGeneration#getContent()}
   *   <li>{@link DepotGeneration#getPath()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    DepotGeneration actualDepotGeneration = new DepotGeneration("Path", "Not all who wander are lost");
    String actualContent = actualDepotGeneration.getContent();

    // Assert
    assertEquals("Not all who wander are lost", actualContent);
    assertEquals("Path", actualDepotGeneration.getPath());
  }
}
