package org.finos.legend.depot.store.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StoredEntityReferenceDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredEntityReference#equals(Object)}
   *   <li>{@link StoredEntityReference#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StoredEntityReference storedEntityReference = new StoredEntityReference("42", "42", "42");
    StoredEntityReference storedEntityReference2 = new StoredEntityReference("42", "42", "42");

    // Act and Assert
    assertEquals(storedEntityReference, storedEntityReference2);
    int expectedHashCodeResult = storedEntityReference.hashCode();
    assertEquals(expectedHashCodeResult, storedEntityReference2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StoredEntityReference#equals(Object)}
   *   <li>{@link StoredEntityReference#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StoredEntityReference storedEntityReference = new StoredEntityReference("42", "42", "42");

    // Act and Assert
    assertEquals(storedEntityReference, storedEntityReference);
    int expectedHashCodeResult = storedEntityReference.hashCode();
    assertEquals(expectedHashCodeResult, storedEntityReference.hashCode());
  }

  /**
   * Method under test: {@link StoredEntityReference#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StoredEntityReference storedEntityReference = new StoredEntityReference("Group Id", "42", "42");

    // Act and Assert
    assertNotEquals(storedEntityReference, new StoredEntityReference("42", "42", "42"));
  }

  /**
   * Method under test: {@link StoredEntityReference#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredEntityReference("42", "42", "42"), null);
  }

  /**
   * Method under test: {@link StoredEntityReference#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StoredEntityReference("42", "42", "42"), "Different type to StoredEntityReference");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredEntityReference#StoredEntityReference(String, String, String)}
   *   <li>{@link StoredEntityReference#getReference()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    StoredEntityReference actualStoredEntityReference = new StoredEntityReference("42", "42", "42");
    String actualReference = actualStoredEntityReference.getReference();

    // Assert
    assertEquals("42", actualStoredEntityReference.getArtifactId());
    assertEquals("42", actualStoredEntityReference.getGroupId());
    assertEquals("42", actualStoredEntityReference.getVersionId());
    assertNull(actualReference);
    assertNull(actualStoredEntityReference.getEntityAttributes());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link StoredEntityReference#StoredEntityReference(String, String, String, String, Map)}
   *   <li>{@link StoredEntityReference#getReference()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange
    HashMap<String, Object> entityAttributes = new HashMap<>();

    // Act
    StoredEntityReference actualStoredEntityReference = new StoredEntityReference("42", "42", "42", "Reference",
        entityAttributes);
    String actualReference = actualStoredEntityReference.getReference();

    // Assert
    assertEquals("42", actualStoredEntityReference.getArtifactId());
    assertEquals("42", actualStoredEntityReference.getGroupId());
    assertEquals("42", actualStoredEntityReference.getVersionId());
    assertEquals("Reference", actualReference);
    Map<String, ?> entityAttributes2 = actualStoredEntityReference.getEntityAttributes();
    assertTrue(entityAttributes2.isEmpty());
    assertSame(entityAttributes, entityAttributes2);
  }
}
