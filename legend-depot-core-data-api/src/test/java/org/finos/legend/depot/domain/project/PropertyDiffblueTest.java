package org.finos.legend.depot.domain.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class PropertyDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Property#equals(Object)}
   *   <li>{@link Property#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Property property = new Property("Property Name", "42");
    Property property2 = new Property("Property Name", "42");

    // Act and Assert
    assertEquals(property, property2);
    int expectedHashCodeResult = property.hashCode();
    assertEquals(expectedHashCodeResult, property2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Property#equals(Object)}
   *   <li>{@link Property#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Property property = new Property("Property Name", "42");

    // Act and Assert
    assertEquals(property, property);
    int expectedHashCodeResult = property.hashCode();
    assertEquals(expectedHashCodeResult, property.hashCode());
  }

  /**
   * Method under test: {@link Property#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Property property = new Property("42", "42");

    // Act and Assert
    assertNotEquals(property, new Property("Property Name", "42"));
  }

  /**
   * Method under test: {@link Property#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Property("Property Name", "42"), null);
  }

  /**
   * Method under test: {@link Property#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Property("Property Name", "42"), "Different type to Property");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Property#Property()}
   *   <li>{@link Property#getPropertyName()}
   *   <li>{@link Property#getValue()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    Property actualProperty = new Property();
    String actualPropertyName = actualProperty.getPropertyName();

    // Assert
    assertNull(actualPropertyName);
    assertNull(actualProperty.getValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Property#Property(String, String)}
   *   <li>{@link Property#getPropertyName()}
   *   <li>{@link Property#getValue()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    Property actualProperty = new Property("Property Name", "42");
    String actualPropertyName = actualProperty.getPropertyName();

    // Assert
    assertEquals("42", actualProperty.getValue());
    assertEquals("Property Name", actualPropertyName);
  }
}
