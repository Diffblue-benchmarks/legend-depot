package org.finos.legend.depot.store.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class StoredEntityDiffblueTest {
  /**
   * Method under test: {@link StoredEntity#getEntityAttributes()}
   */
  @Test
  void testGetEntityAttributes() {
    // Arrange, Act and Assert
    assertNull((new StoredEntityData("42", "42", "42")).getEntityAttributes());
  }

  /**
   * Method under test: {@link StoredEntity#getEntityAttributes()}
   */
  @Test
  void testGetEntityAttributes2() {
    // Arrange
    HashMap<String, Object> content = new HashMap<>();
    content.computeIfPresent("foo", mock(BiFunction.class));
    EntityDefinition entity = new EntityDefinition("Path", "Classifier Path", content);

    HashMap<String, Object> entityAttributes = new HashMap<>();

    // Act
    Map<String, ?> actualEntityAttributes = (new StoredEntityData("42", "42", "42", entity, entityAttributes))
        .getEntityAttributes();

    // Assert
    assertTrue(actualEntityAttributes.isEmpty());
    assertSame(entityAttributes, actualEntityAttributes);
  }

  /**
   * Method under test: {@link StoredEntity#getId()}
   */
  @Test
  void testGetId() {
    // Arrange, Act and Assert
    assertEquals("", (new StoredEntityReference("42", "42", "42")).getId());
  }

  /**
   * Method under test: {@link StoredEntity#getId()}
   */
  @Test
  void testGetId2() {
    // Arrange
    HashMap<String, Object> entityAttributes = new HashMap<>();
    entityAttributes.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertEquals("", (new StoredEntityReference("42", "42", "42", "Reference", entityAttributes)).getId());
  }
}
