package org.finos.legend.depot.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class DepotEntityOverviewDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DepotEntityOverview#equals(Object)}
   *   <li>{@link DepotEntityOverview#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    DepotEntityOverview depotEntityOverview = new DepotEntityOverview("42", "42", "42", "Path", "Classifier Path");
    DepotEntityOverview depotEntityOverview2 = new DepotEntityOverview("42", "42", "42", "Path", "Classifier Path");

    // Act and Assert
    assertEquals(depotEntityOverview, depotEntityOverview2);
    int expectedHashCodeResult = depotEntityOverview.hashCode();
    assertEquals(expectedHashCodeResult, depotEntityOverview2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DepotEntityOverview#equals(Object)}
   *   <li>{@link DepotEntityOverview#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    DepotEntityOverview depotEntityOverview = new DepotEntityOverview("42", "42", "42", "Path", "Classifier Path");

    // Act and Assert
    assertEquals(depotEntityOverview, depotEntityOverview);
    int expectedHashCodeResult = depotEntityOverview.hashCode();
    assertEquals(expectedHashCodeResult, depotEntityOverview.hashCode());
  }

  /**
   * Method under test: {@link DepotEntityOverview#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    DepotEntityOverview depotEntityOverview = new DepotEntityOverview("Group Id", "42", "42", "Path",
        "Classifier Path");

    // Act and Assert
    assertNotEquals(depotEntityOverview, new DepotEntityOverview("42", "42", "42", "Path", "Classifier Path"));
  }

  /**
   * Method under test: {@link DepotEntityOverview#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new DepotEntityOverview("42", "42", "42", "Path", "Classifier Path"), null);
  }

  /**
   * Method under test: {@link DepotEntityOverview#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new DepotEntityOverview("42", "42", "42", "Path", "Classifier Path"),
        "Different type to DepotEntityOverview");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link DepotEntityOverview#DepotEntityOverview(String, String, String, String, String)}
   *   <li>{@link DepotEntityOverview#getClassifierPath()}
   *   <li>{@link DepotEntityOverview#getPath()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    DepotEntityOverview actualDepotEntityOverview = new DepotEntityOverview("42", "42", "42", "Path",
        "Classifier Path");
    String actualClassifierPath = actualDepotEntityOverview.getClassifierPath();
    String actualPath = actualDepotEntityOverview.getPath();

    // Assert
    assertEquals("42", actualDepotEntityOverview.getArtifactId());
    assertEquals("42", actualDepotEntityOverview.getGroupId());
    assertEquals("42", actualDepotEntityOverview.getVersionId());
    assertEquals("Classifier Path", actualClassifierPath);
    assertEquals("Path", actualPath);
    assertNull(actualDepotEntityOverview.getEntity());
    assertFalse(actualDepotEntityOverview.isVersionedEntity());
  }
}
