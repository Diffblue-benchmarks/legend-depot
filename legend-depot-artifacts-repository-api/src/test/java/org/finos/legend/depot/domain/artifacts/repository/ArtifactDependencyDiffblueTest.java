package org.finos.legend.depot.domain.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class ArtifactDependencyDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ArtifactDependency#equals(Object)}
   *   <li>{@link ArtifactDependency#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link ArtifactDependency#equals(Object)}
   *   <li>{@link ArtifactDependency#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ArtifactDependency artifactDependency = new ArtifactDependency("42", "42", "1.0.2");

    // Act and Assert
    assertEquals(artifactDependency, artifactDependency);
    int expectedHashCodeResult = artifactDependency.hashCode();
    assertEquals(expectedHashCodeResult, artifactDependency.hashCode());
  }

  /**
   * Method under test: {@link ArtifactDependency#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArtifactDependency artifactDependency = new ArtifactDependency("Group Id", "42", "1.0.2");

    // Act and Assert
    assertNotEquals(artifactDependency, new ArtifactDependency("42", "42", "1.0.2"));
  }

  /**
   * Method under test: {@link ArtifactDependency#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ArtifactDependency("42", "42", "1.0.2"), null);
  }

  /**
   * Method under test: {@link ArtifactDependency#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ArtifactDependency("42", "42", "1.0.2"), "Different type to ArtifactDependency");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ArtifactDependency#ArtifactDependency(String, String, String)}
   *   <li>{@link ArtifactDependency#getArtifactId()}
   *   <li>{@link ArtifactDependency#getGroupId()}
   *   <li>{@link ArtifactDependency#getVersion()}
   * </ul>
   */
  @Test
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
}
