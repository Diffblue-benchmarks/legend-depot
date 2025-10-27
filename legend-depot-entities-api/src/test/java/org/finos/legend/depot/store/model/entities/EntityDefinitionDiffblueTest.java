package org.finos.legend.depot.store.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class EntityDefinitionDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EntityDefinition#equals(Object)}
   *   <li>{@link EntityDefinition#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    EntityDefinition entityDefinition = new EntityDefinition("Path", "Classifier Path", new HashMap<>());
    EntityDefinition entityDefinition2 = new EntityDefinition("Path", "Classifier Path", new HashMap<>());

    // Act and Assert
    assertEquals(entityDefinition, entityDefinition2);
    int expectedHashCodeResult = entityDefinition.hashCode();
    assertEquals(expectedHashCodeResult, entityDefinition2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EntityDefinition#equals(Object)}
   *   <li>{@link EntityDefinition#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    HashMap<String, Object> content = new HashMap<>();
    content.computeIfPresent("foo", mock(BiFunction.class));
    EntityDefinition entityDefinition = new EntityDefinition("Path", "Classifier Path", content);
    EntityDefinition entityDefinition2 = new EntityDefinition("Path", "Classifier Path", new HashMap<>());

    // Act and Assert
    assertEquals(entityDefinition, entityDefinition2);
    int expectedHashCodeResult = entityDefinition.hashCode();
    assertEquals(expectedHashCodeResult, entityDefinition2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EntityDefinition#equals(Object)}
   *   <li>{@link EntityDefinition#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    EntityDefinition entityDefinition = new EntityDefinition("Path", "Classifier Path", new HashMap<>());

    // Act and Assert
    assertEquals(entityDefinition, entityDefinition);
    int expectedHashCodeResult = entityDefinition.hashCode();
    assertEquals(expectedHashCodeResult, entityDefinition.hashCode());
  }

  /**
   * Method under test: {@link EntityDefinition#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    EntityDefinition entityDefinition = new EntityDefinition("42", "Classifier Path", new HashMap<>());

    // Act and Assert
    assertNotEquals(entityDefinition, new EntityDefinition("Path", "Classifier Path", new HashMap<>()));
  }

  /**
   * Method under test: {@link EntityDefinition#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new EntityDefinition("Path", "Classifier Path", new HashMap<>()), null);
  }

  /**
   * Method under test: {@link EntityDefinition#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new EntityDefinition("Path", "Classifier Path", new HashMap<>()),
        "Different type to EntityDefinition");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EntityDefinition#EntityDefinition(String, String, Map)}
   *   <li>{@link EntityDefinition#setClassifierPath(String)}
   *   <li>{@link EntityDefinition#getClassifierPath()}
   *   <li>{@link EntityDefinition#getContent()}
   *   <li>{@link EntityDefinition#getPath()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    HashMap<String, Object> content = new HashMap<>();

    // Act
    EntityDefinition actualEntityDefinition = new EntityDefinition("Path", "Classifier Path", content);
    actualEntityDefinition.setClassifierPath("Path");
    String actualClassifierPath = actualEntityDefinition.getClassifierPath();
    Map<String, ?> actualContent = actualEntityDefinition.getContent();

    // Assert that nothing has changed
    assertEquals("Path", actualClassifierPath);
    assertEquals("Path", actualEntityDefinition.getPath());
    assertTrue(actualContent.isEmpty());
    assertSame(content, actualContent);
  }
}
