package org.finos.legend.depot.store.model.versionedEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StoredVersionedEntityStringDataDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredVersionedEntityStringData#equals(Object)}
   *   <li>{@link StoredVersionedEntityStringData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StoredVersionedEntityStringData storedVersionedEntityStringData = new StoredVersionedEntityStringData("42", "42",
        "42");
    StoredVersionedEntityStringData storedVersionedEntityStringData2 = new StoredVersionedEntityStringData("42", "42",
        "42");

    // Act and Assert
    assertEquals(storedVersionedEntityStringData, storedVersionedEntityStringData2);
    int expectedHashCodeResult = storedVersionedEntityStringData.hashCode();
    assertEquals(expectedHashCodeResult, storedVersionedEntityStringData2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredVersionedEntityStringData#equals(Object)}
   *   <li>{@link StoredVersionedEntityStringData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StoredVersionedEntityStringData storedVersionedEntityStringData = new StoredVersionedEntityStringData("42", "42",
        "42");

    // Act and Assert
    assertEquals(storedVersionedEntityStringData, storedVersionedEntityStringData);
    int expectedHashCodeResult = storedVersionedEntityStringData.hashCode();
    assertEquals(expectedHashCodeResult, storedVersionedEntityStringData.hashCode());
  }

  /**
   * Method under test: {@link StoredVersionedEntityStringData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StoredVersionedEntityStringData storedVersionedEntityStringData = new StoredVersionedEntityStringData("Group Id",
        "42", "42");

    // Act and Assert
    assertNotEquals(storedVersionedEntityStringData, new StoredVersionedEntityStringData("42", "42", "42"));
  }

  /**
   * Method under test: {@link StoredVersionedEntityStringData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredVersionedEntityStringData("42", "42", "42"), null);
  }

  /**
   * Method under test: {@link StoredVersionedEntityStringData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredVersionedEntityStringData("42", "42", "42"),
        "Different type to StoredVersionedEntityStringData");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredVersionedEntityStringData#StoredVersionedEntityStringData(String, String, String)}
   *   <li>{@link StoredVersionedEntityStringData#getData()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    StoredVersionedEntityStringData actualStoredVersionedEntityStringData = new StoredVersionedEntityStringData("42",
        "42", "42");
    String actualData = actualStoredVersionedEntityStringData.getData();

    // Assert
    assertEquals("42", actualStoredVersionedEntityStringData.getArtifactId());
    assertEquals("42", actualStoredVersionedEntityStringData.getGroupId());
    assertEquals("42", actualStoredVersionedEntityStringData.getVersionId());
    assertNull(actualData);
    assertNull(actualStoredVersionedEntityStringData.getEntityAttributes());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredVersionedEntityStringData#StoredVersionedEntityStringData(String, String, String, String, Map)}
   *   <li>{@link StoredVersionedEntityStringData#getData()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange
    HashMap<String, Object> entityAttributes = new HashMap<>();

    // Act
    StoredVersionedEntityStringData actualStoredVersionedEntityStringData = new StoredVersionedEntityStringData("42",
        "42", "42", "Data", entityAttributes);
    String actualData = actualStoredVersionedEntityStringData.getData();

    // Assert
    assertEquals("42", actualStoredVersionedEntityStringData.getArtifactId());
    assertEquals("42", actualStoredVersionedEntityStringData.getGroupId());
    assertEquals("42", actualStoredVersionedEntityStringData.getVersionId());
    assertEquals("Data", actualData);
    Map<String, ?> entityAttributes2 = actualStoredVersionedEntityStringData.getEntityAttributes();
    assertTrue(entityAttributes2.isEmpty());
    assertSame(entityAttributes, entityAttributes2);
  }
}
