package org.finos.legend.depot.store.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StoredEntityDiffblueTest {
  /**
   * Test {@link StoredEntity#getEntityAttributes()}.
   * <p>
   * Method under test: {@link StoredEntity#getEntityAttributes()}
   */
  @Test
  @DisplayName("Test getEntityAttributes()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Map StoredEntity.getEntityAttributes()"})
  void testGetEntityAttributes() {
    // Arrange, Act and Assert
    assertNull((new StoredEntityData("42", "42", "42")).getEntityAttributes());
  }

  /**
   * Test {@link StoredEntity#getId()}.
   * <p>
   * Method under test: {@link StoredEntity#getId()}
   */
  @Test
  @DisplayName("Test getId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String StoredEntity.getId()"})
  void testGetId() {
    // Arrange, Act and Assert
    assertEquals("", (new StoredEntityReference("42", "42", "42")).getId());
  }
}
