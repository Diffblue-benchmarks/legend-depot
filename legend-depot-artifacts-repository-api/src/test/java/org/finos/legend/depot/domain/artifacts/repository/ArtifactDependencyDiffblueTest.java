package org.finos.legend.depot.domain.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ArtifactDependencyDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ArtifactDependency#ArtifactDependency(String, String, String)}
   *   <li>{@link ArtifactDependency#getArtifactId()}
   *   <li>{@link ArtifactDependency#getGroupId()}
   *   <li>{@link ArtifactDependency#getVersion()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ArtifactDependency.<init>(String, String, String)",
      "String ArtifactDependency.getArtifactId()", "String ArtifactDependency.getGroupId()",
      "String ArtifactDependency.getVersion()"})
  void testGettersAndSetters() {
    // Arrange and Act
    ArtifactDependency actualArtifactDependency = new ArtifactDependency("42", "42", "1.0.2");
    String actualArtifactId = actualArtifactDependency.getArtifactId();
    String actualGroupId = actualArtifactDependency.getGroupId();

    // Assert
    assertEquals("1.0.2", actualArtifactDependency.getVersion());
    assertEquals("42", actualArtifactId);
    assertEquals("42", actualGroupId);
  }

  /**
   * Test {@link ArtifactDependency#equals(Object)}, and {@link ArtifactDependency#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ArtifactDependency#equals(Object)}
   *   <li>{@link ArtifactDependency#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ArtifactDependency.equals(Object)", "int ArtifactDependency.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ArtifactDependency artifactDependency = new ArtifactDependency("42", "42", "1.0.2");
    ArtifactDependency artifactDependency2 = new ArtifactDependency("42", "42", "1.0.2");

    // Act and Assert
    assertEquals(artifactDependency, artifactDependency2);
    int expectedHashCodeResult = artifactDependency.hashCode();
    assertEquals(expectedHashCodeResult, artifactDependency2.hashCode());
  }

  /**
   * Test {@link ArtifactDependency#equals(Object)}, and {@link ArtifactDependency#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ArtifactDependency#equals(Object)}
   *   <li>{@link ArtifactDependency#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ArtifactDependency.equals(Object)", "int ArtifactDependency.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ArtifactDependency artifactDependency = new ArtifactDependency("42", "42", "1.0.2");

    // Act and Assert
    assertEquals(artifactDependency, artifactDependency);
    int expectedHashCodeResult = artifactDependency.hashCode();
    assertEquals(expectedHashCodeResult, artifactDependency.hashCode());
  }

  /**
   * Test {@link ArtifactDependency#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArtifactDependency#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ArtifactDependency.equals(Object)", "int ArtifactDependency.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArtifactDependency artifactDependency = new ArtifactDependency("Group Id", "42", "1.0.2");

    // Act and Assert
    assertNotEquals(artifactDependency, new ArtifactDependency("42", "42", "1.0.2"));
  }

  /**
   * Test {@link ArtifactDependency#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArtifactDependency#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ArtifactDependency.equals(Object)", "int ArtifactDependency.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ArtifactDependency("42", "42", "1.0.2"), null);
  }

  /**
   * Test {@link ArtifactDependency#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArtifactDependency#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ArtifactDependency.equals(Object)", "int ArtifactDependency.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ArtifactDependency("42", "42", "1.0.2"), "Different type to ArtifactDependency");
  }
}
