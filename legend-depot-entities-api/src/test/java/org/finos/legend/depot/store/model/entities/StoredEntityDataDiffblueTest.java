package org.finos.legend.depot.store.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StoredEntityDataDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredEntityData#equals(Object)}
   *   <li>{@link StoredEntityData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StoredEntityData storedEntityData = new StoredEntityData("42", "42", "42");
    StoredEntityData storedEntityData2 = new StoredEntityData("42", "42", "42");

    // Act and Assert
    assertEquals(storedEntityData, storedEntityData2);
    int expectedHashCodeResult = storedEntityData.hashCode();
    assertEquals(expectedHashCodeResult, storedEntityData2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredEntityData#equals(Object)}
   *   <li>{@link StoredEntityData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StoredEntityData storedEntityData = new StoredEntityData("42", "42", "42");

    // Act and Assert
    assertEquals(storedEntityData, storedEntityData);
    int expectedHashCodeResult = storedEntityData.hashCode();
    assertEquals(expectedHashCodeResult, storedEntityData.hashCode());
  }

  /**
   * Method under test: {@link StoredEntityData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StoredEntityData storedEntityData = new StoredEntityData("Group Id", "42", "42");

    // Act and Assert
    assertNotEquals(storedEntityData, new StoredEntityData("42", "42", "42"));
  }

  /**
   * Method under test: {@link StoredEntityData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredEntityData("42", "42", "42"), null);
  }

  /**
   * Method under test: {@link StoredEntityData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredEntityData("42", "42", "42"), "Different type to StoredEntityData");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredEntityData#StoredEntityData(String, String, String)}
   *   <li>{@link StoredEntityData#getEntity()}
   *   <li>{@link StoredEntityData#getId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    StoredEntityData actualStoredEntityData = new StoredEntityData("42", "42", "42");
    EntityDefinition actualEntity = actualStoredEntityData.getEntity();

    // Assert
    assertEquals("", actualStoredEntityData.getId());
    assertEquals("42", actualStoredEntityData.getArtifactId());
    assertEquals("42", actualStoredEntityData.getGroupId());
    assertEquals("42", actualStoredEntityData.getVersionId());
    assertNull(actualStoredEntityData.getEntityAttributes());
    assertNull(actualEntity);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredEntityData#StoredEntityData(String, String, String, EntityDefinition, Map)}
   *   <li>{@link StoredEntityData#getEntity()}
   *   <li>{@link StoredEntityData#getId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange
    EntityDefinition entity = new EntityDefinition("Path", "Classifier Path", new HashMap<>());

    HashMap<String, Object> entityAttributes = new HashMap<>();

    // Act
    StoredEntityData actualStoredEntityData = new StoredEntityData("42", "42", "42", entity, entityAttributes);
    EntityDefinition actualEntity = actualStoredEntityData.getEntity();

    // Assert
    assertEquals("", actualStoredEntityData.getId());
    assertEquals("42", actualStoredEntityData.getArtifactId());
    assertEquals("42", actualStoredEntityData.getGroupId());
    assertEquals("42", actualStoredEntityData.getVersionId());
    Map<String, ?> entityAttributes2 = actualStoredEntityData.getEntityAttributes();
    assertTrue(entityAttributes2.isEmpty());
    assertSame(entityAttributes, entityAttributes2);
    assertSame(entity, actualEntity);
  }
}
