package org.finos.legend.depot.domain.project.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.Test;

class VersionDependencyReportDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionDependencyReport#equals(Object)}
   *   <li>{@link VersionDependencyReport#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    VersionDependencyReport versionDependencyReport = new VersionDependencyReport();
    VersionDependencyReport versionDependencyReport2 = new VersionDependencyReport();

    // Act and Assert
    assertEquals(versionDependencyReport, versionDependencyReport2);
    int expectedHashCodeResult = versionDependencyReport.hashCode();
    assertEquals(expectedHashCodeResult, versionDependencyReport2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionDependencyReport#equals(Object)}
   *   <li>{@link VersionDependencyReport#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    VersionDependencyReport versionDependencyReport = new VersionDependencyReport();

    // Act and Assert
    assertEquals(versionDependencyReport, versionDependencyReport);
    int expectedHashCodeResult = versionDependencyReport.hashCode();
    assertEquals(expectedHashCodeResult, versionDependencyReport.hashCode());
  }

  /**
   * Method under test: {@link VersionDependencyReport#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new VersionDependencyReport(), 4);
  }

  /**
   * Method under test: {@link VersionDependencyReport#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new VersionDependencyReport(), null);
  }

  /**
   * Method under test: {@link VersionDependencyReport#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new VersionDependencyReport(), "Different type to VersionDependencyReport");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionDependencyReport#VersionDependencyReport()}
   *   <li>{@link VersionDependencyReport#setTransitiveDependencies(List)}
   *   <li>{@link VersionDependencyReport#setValid(boolean)}
   *   <li>{@link VersionDependencyReport#getTransitiveDependencies()}
   *   <li>{@link VersionDependencyReport#isValid()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    VersionDependencyReport actualVersionDependencyReport = new VersionDependencyReport();
    ArrayList<ProjectVersion> transitiveDependencies = new ArrayList<>();
    actualVersionDependencyReport.setTransitiveDependencies(transitiveDependencies);
    actualVersionDependencyReport.setValid(true);
    List<ProjectVersion> actualTransitiveDependencies = actualVersionDependencyReport.getTransitiveDependencies();
    boolean actualIsValidResult = actualVersionDependencyReport.isValid();

    // Assert that nothing has changed
    assertTrue(actualTransitiveDependencies.isEmpty());
    assertTrue(actualIsValidResult);
    assertSame(transitiveDependencies, actualTransitiveDependencies);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionDependencyReport#VersionDependencyReport(List, boolean)}
   *   <li>{@link VersionDependencyReport#setTransitiveDependencies(List)}
   *   <li>{@link VersionDependencyReport#setValid(boolean)}
   *   <li>{@link VersionDependencyReport#getTransitiveDependencies()}
   *   <li>{@link VersionDependencyReport#isValid()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    VersionDependencyReport actualVersionDependencyReport = new VersionDependencyReport(new ArrayList<>(), true);
    ArrayList<ProjectVersion> transitiveDependencies = new ArrayList<>();
    actualVersionDependencyReport.setTransitiveDependencies(transitiveDependencies);
    actualVersionDependencyReport.setValid(true);
    List<ProjectVersion> actualTransitiveDependencies = actualVersionDependencyReport.getTransitiveDependencies();
    boolean actualIsValidResult = actualVersionDependencyReport.isValid();

    // Assert that nothing has changed
    assertTrue(actualTransitiveDependencies.isEmpty());
    assertTrue(actualIsValidResult);
    assertSame(transitiveDependencies, actualTransitiveDependencies);
  }
}
