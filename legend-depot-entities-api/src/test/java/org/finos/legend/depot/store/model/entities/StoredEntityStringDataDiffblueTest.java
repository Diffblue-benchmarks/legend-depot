package org.finos.legend.depot.store.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StoredEntityStringDataDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredEntityStringData#equals(Object)}
   *   <li>{@link StoredEntityStringData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StoredEntityStringData storedEntityStringData = new StoredEntityStringData("42", "42", "42");
    StoredEntityStringData storedEntityStringData2 = new StoredEntityStringData("42", "42", "42");

    // Act and Assert
    assertEquals(storedEntityStringData, storedEntityStringData2);
    int expectedHashCodeResult = storedEntityStringData.hashCode();
    assertEquals(expectedHashCodeResult, storedEntityStringData2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredEntityStringData#equals(Object)}
   *   <li>{@link StoredEntityStringData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StoredEntityStringData storedEntityStringData = new StoredEntityStringData("42", "42", "42");

    // Act and Assert
    assertEquals(storedEntityStringData, storedEntityStringData);
    int expectedHashCodeResult = storedEntityStringData.hashCode();
    assertEquals(expectedHashCodeResult, storedEntityStringData.hashCode());
  }

  /**
   * Method under test: {@link StoredEntityStringData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StoredEntityStringData storedEntityStringData = new StoredEntityStringData("Group Id", "42", "42");

    // Act and Assert
    assertNotEquals(storedEntityStringData, new StoredEntityStringData("42", "42", "42"));
  }

  /**
   * Method under test: {@link StoredEntityStringData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredEntityStringData("42", "42", "42"), null);
  }

  /**
   * Method under test: {@link StoredEntityStringData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredEntityStringData("42", "42", "42"), "Different type to StoredEntityStringData");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredEntityStringData#StoredEntityStringData(String, String, String)}
   *   <li>{@link StoredEntityStringData#getData()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    StoredEntityStringData actualStoredEntityStringData = new StoredEntityStringData("42", "42", "42");
    String actualData = actualStoredEntityStringData.getData();

    // Assert
    assertEquals("42", actualStoredEntityStringData.getArtifactId());
    assertEquals("42", actualStoredEntityStringData.getGroupId());
    assertEquals("42", actualStoredEntityStringData.getVersionId());
    assertNull(actualData);
    assertNull(actualStoredEntityStringData.getEntityAttributes());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredEntityStringData#StoredEntityStringData(String, String, String, String, Map)}
   *   <li>{@link StoredEntityStringData#getData()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange
    HashMap<String, Object> entityAttributes = new HashMap<>();

    // Act
    StoredEntityStringData actualStoredEntityStringData = new StoredEntityStringData("42", "42", "42", "Data",
        entityAttributes);
    String actualData = actualStoredEntityStringData.getData();

    // Assert
    assertEquals("42", actualStoredEntityStringData.getArtifactId());
    assertEquals("42", actualStoredEntityStringData.getGroupId());
    assertEquals("42", actualStoredEntityStringData.getVersionId());
    assertEquals("Data", actualData);
    Map<String, ?> entityAttributes2 = actualStoredEntityStringData.getEntityAttributes();
    assertTrue(entityAttributes2.isEmpty());
    assertSame(entityAttributes, entityAttributes2);
  }
}
