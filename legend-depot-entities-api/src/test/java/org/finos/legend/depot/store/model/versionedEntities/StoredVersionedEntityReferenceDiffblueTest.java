package org.finos.legend.depot.store.model.versionedEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StoredVersionedEntityReferenceDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredVersionedEntityReference#equals(Object)}
   *   <li>{@link StoredVersionedEntityReference#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StoredVersionedEntityReference storedVersionedEntityReference = new StoredVersionedEntityReference("42", "42",
        "42");
    StoredVersionedEntityReference storedVersionedEntityReference2 = new StoredVersionedEntityReference("42", "42",
        "42");

    // Act and Assert
    assertEquals(storedVersionedEntityReference, storedVersionedEntityReference2);
    int expectedHashCodeResult = storedVersionedEntityReference.hashCode();
    assertEquals(expectedHashCodeResult, storedVersionedEntityReference2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredVersionedEntityReference#equals(Object)}
   *   <li>{@link StoredVersionedEntityReference#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StoredVersionedEntityReference storedVersionedEntityReference = new StoredVersionedEntityReference("42", "42",
        "42");

    // Act and Assert
    assertEquals(storedVersionedEntityReference, storedVersionedEntityReference);
    int expectedHashCodeResult = storedVersionedEntityReference.hashCode();
    assertEquals(expectedHashCodeResult, storedVersionedEntityReference.hashCode());
  }

  /**
   * Method under test: {@link StoredVersionedEntityReference#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StoredVersionedEntityReference storedVersionedEntityReference = new StoredVersionedEntityReference("Group Id", "42",
        "42");

    // Act and Assert
    assertNotEquals(storedVersionedEntityReference, new StoredVersionedEntityReference("42", "42", "42"));
  }

  /**
   * Method under test: {@link StoredVersionedEntityReference#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredVersionedEntityReference("42", "42", "42"), null);
  }

  /**
   * Method under test: {@link StoredVersionedEntityReference#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredVersionedEntityReference("42", "42", "42"),
        "Different type to StoredVersionedEntityReference");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredVersionedEntityReference#StoredVersionedEntityReference(String, String, String)}
   *   <li>{@link StoredVersionedEntityReference#getReference()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    StoredVersionedEntityReference actualStoredVersionedEntityReference = new StoredVersionedEntityReference("42", "42",
        "42");
    String actualReference = actualStoredVersionedEntityReference.getReference();

    // Assert
    assertEquals("42", actualStoredVersionedEntityReference.getArtifactId());
    assertEquals("42", actualStoredVersionedEntityReference.getGroupId());
    assertEquals("42", actualStoredVersionedEntityReference.getVersionId());
    assertNull(actualReference);
    assertNull(actualStoredVersionedEntityReference.getEntityAttributes());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredVersionedEntityReference#StoredVersionedEntityReference(String, String, String, String, Map)}
   *   <li>{@link StoredVersionedEntityReference#getReference()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange
    HashMap<String, Object> entityAttributes = new HashMap<>();

    // Act
    StoredVersionedEntityReference actualStoredVersionedEntityReference = new StoredVersionedEntityReference("42", "42",
        "42", "Reference", entityAttributes);
    String actualReference = actualStoredVersionedEntityReference.getReference();

    // Assert
    assertEquals("42", actualStoredVersionedEntityReference.getArtifactId());
    assertEquals("42", actualStoredVersionedEntityReference.getGroupId());
    assertEquals("42", actualStoredVersionedEntityReference.getVersionId());
    assertEquals("Reference", actualReference);
    Map<String, ?> entityAttributes2 = actualStoredVersionedEntityReference.getEntityAttributes();
    assertTrue(entityAttributes2.isEmpty());
    assertSame(entityAttributes, entityAttributes2);
  }
}
