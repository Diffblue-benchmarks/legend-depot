package org.finos.legend.depot.store.model.versionedEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.junit.jupiter.api.Test;

class StoredVersionedEntityDataDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredVersionedEntityData#equals(Object)}
   *   <li>{@link StoredVersionedEntityData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StoredVersionedEntityData storedVersionedEntityData = new StoredVersionedEntityData("42", "42", "42");
    StoredVersionedEntityData storedVersionedEntityData2 = new StoredVersionedEntityData("42", "42", "42");

    // Act and Assert
    assertEquals(storedVersionedEntityData, storedVersionedEntityData2);
    int expectedHashCodeResult = storedVersionedEntityData.hashCode();
    assertEquals(expectedHashCodeResult, storedVersionedEntityData2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredVersionedEntityData#equals(Object)}
   *   <li>{@link StoredVersionedEntityData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StoredVersionedEntityData storedVersionedEntityData = new StoredVersionedEntityData("42", "42", "42");

    // Act and Assert
    assertEquals(storedVersionedEntityData, storedVersionedEntityData);
    int expectedHashCodeResult = storedVersionedEntityData.hashCode();
    assertEquals(expectedHashCodeResult, storedVersionedEntityData.hashCode());
  }

  /**
   * Method under test: {@link StoredVersionedEntityData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StoredVersionedEntityData storedVersionedEntityData = new StoredVersionedEntityData("Group Id", "42", "42");

    // Act and Assert
    assertNotEquals(storedVersionedEntityData, new StoredVersionedEntityData("42", "42", "42"));
  }

  /**
   * Method under test: {@link StoredVersionedEntityData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredVersionedEntityData("42", "42", "42"), null);
  }

  /**
   * Method under test: {@link StoredVersionedEntityData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredVersionedEntityData("42", "42", "42"), "Different type to StoredVersionedEntityData");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredVersionedEntityData#StoredVersionedEntityData(String, String, String)}
   *   <li>{@link StoredVersionedEntityData#getEntity()}
   *   <li>{@link StoredVersionedEntityData#getId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    StoredVersionedEntityData actualStoredVersionedEntityData = new StoredVersionedEntityData("42", "42", "42");
    EntityDefinition actualEntity = actualStoredVersionedEntityData.getEntity();

    // Assert
    assertEquals("", actualStoredVersionedEntityData.getId());
    assertEquals("42", actualStoredVersionedEntityData.getArtifactId());
    assertEquals("42", actualStoredVersionedEntityData.getGroupId());
    assertEquals("42", actualStoredVersionedEntityData.getVersionId());
    assertNull(actualStoredVersionedEntityData.getEntityAttributes());
    assertNull(actualEntity);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredVersionedEntityData#StoredVersionedEntityData(String, String, String, EntityDefinition, Map)}
   *   <li>{@link StoredVersionedEntityData#getEntity()}
   *   <li>{@link StoredVersionedEntityData#getId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange
    EntityDefinition entity = new EntityDefinition("Path", "Classifier Path", new HashMap<>());

    HashMap<String, Object> entityAttributes = new HashMap<>();

    // Act
    StoredVersionedEntityData actualStoredVersionedEntityData = new StoredVersionedEntityData("42", "42", "42", entity,
        entityAttributes);
    EntityDefinition actualEntity = actualStoredVersionedEntityData.getEntity();

    // Assert
    assertEquals("", actualStoredVersionedEntityData.getId());
    assertEquals("42", actualStoredVersionedEntityData.getArtifactId());
    assertEquals("42", actualStoredVersionedEntityData.getGroupId());
    assertEquals("42", actualStoredVersionedEntityData.getVersionId());
    Map<String, ?> entityAttributes2 = actualStoredVersionedEntityData.getEntityAttributes();
    assertTrue(entityAttributes2.isEmpty());
    assertSame(entityAttributes, entityAttributes2);
    assertSame(entity, actualEntity);
  }
}
