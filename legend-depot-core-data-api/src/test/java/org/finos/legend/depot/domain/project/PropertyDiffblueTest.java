package org.finos.legend.depot.domain.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class PropertyDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return PropertyName is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Property#Property()}
   *   <li>{@link Property#getPropertyName()}
   *   <li>{@link Property#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; then return PropertyName is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Property.<init>()", "void Property.<init>(String, String)",
      "String Property.getPropertyName()", "String Property.getValue()"})
  void testGettersAndSetters_thenReturnPropertyNameIsNull() {
    // Arrange and Act
    Property actualProperty = new Property();
    String actualPropertyName = actualProperty.getPropertyName();

    // Assert
    assertNull(actualPropertyName);
    assertNull(actualProperty.getValue());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Property Name}.</li>
   *   <li>Then return Value is {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Property#Property(String, String)}
   *   <li>{@link Property#getPropertyName()}
   *   <li>{@link Property#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'Property Name'; then return Value is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Property.<init>()", "void Property.<init>(String, String)",
      "String Property.getPropertyName()", "String Property.getValue()"})
  void testGettersAndSetters_whenPropertyName_thenReturnValueIs42() {
    // Arrange and Act
    Property actualProperty = new Property("Property Name", "42");
    String actualPropertyName = actualProperty.getPropertyName();

    // Assert
    assertEquals("42", actualProperty.getValue());
    assertEquals("Property Name", actualPropertyName);
  }

  /**
   * Test {@link Property#equals(Object)}, and {@link Property#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Property#equals(Object)}
   *   <li>{@link Property#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Property.equals(Object)", "int Property.hashCode()"})
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
   * Test {@link Property#equals(Object)}, and {@link Property#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Property#equals(Object)}
   *   <li>{@link Property#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Property.equals(Object)", "int Property.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Property property = new Property("Property Name", "42");

    // Act and Assert
    assertEquals(property, property);
    int expectedHashCodeResult = property.hashCode();
    assertEquals(expectedHashCodeResult, property.hashCode());
  }

  /**
   * Test {@link Property#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Property#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Property.equals(Object)", "int Property.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Property property = new Property("42", "42");

    // Act and Assert
    assertNotEquals(property, new Property("Property Name", "42"));
  }

  /**
   * Test {@link Property#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Property#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Property.equals(Object)", "int Property.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Property("Property Name", "42"), null);
  }

  /**
   * Test {@link Property#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Property#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Property.equals(Object)", "int Property.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Property("Property Name", "42"), "Different type to Property");
  }
}
