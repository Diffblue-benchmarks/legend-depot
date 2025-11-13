package org.finos.legend.depot.services.artifacts.repository.maven;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryProviderConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryConfiguration;
import org.jboss.shrinkwrap.resolver.api.maven.PackagingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MavenArtifactRepositoryDiffblueTest {
  /**
   * Test {@link
   * MavenArtifactRepository#MavenArtifactRepository(ArtifactRepositoryProviderConfiguration)}.
   *
   * <ul>
   *   <li>Then return areValidCoordinates {@code Group} and {@code Artifact}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MavenArtifactRepository#MavenArtifactRepository(ArtifactRepositoryProviderConfiguration)}
   */
  @Test
  @DisplayName(
      "Test new MavenArtifactRepository(ArtifactRepositoryProviderConfiguration); then return areValidCoordinates 'Group' and 'Artifact'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MavenArtifactRepository.<init>(ArtifactRepositoryProviderConfiguration)"
  })
  void testNewMavenArtifactRepository_thenReturnAreValidCoordinatesGroupAndArtifact() {
    // Arrange, Act and Assert
    assertTrue(new MavenArtifactRepository(null).areValidCoordinates("Group", "Artifact"));
  }

  /**
   * Test {@link
   * MavenArtifactRepository#MavenArtifactRepository(ArtifactRepositoryProviderConfiguration)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MavenArtifactRepository#MavenArtifactRepository(ArtifactRepositoryProviderConfiguration)}
   */
  @Test
  @DisplayName(
      "Test new MavenArtifactRepository(ArtifactRepositoryProviderConfiguration); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MavenArtifactRepository.<init>(ArtifactRepositoryProviderConfiguration)"
  })
  void testNewMavenArtifactRepository_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new MavenArtifactRepository(new VoidArtifactRepositoryConfiguration()));
  }

  /**
   * Test {@link
   * MavenArtifactRepository#MavenArtifactRepository(ArtifactRepositoryProviderConfiguration)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MavenArtifactRepository#MavenArtifactRepository(ArtifactRepositoryProviderConfiguration)}
   */
  @Test
  @DisplayName(
      "Test new MavenArtifactRepository(ArtifactRepositoryProviderConfiguration); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MavenArtifactRepository.<init>(ArtifactRepositoryProviderConfiguration)"
  })
  void testNewMavenArtifactRepository_thenThrowRuntimeException() {
    // Arrange, Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            new MavenArtifactRepository(
                new MavenArtifactRepositoryConfiguration("Settings Location")));
  }

  /**
   * Test {@link MavenArtifactRepository#gavCoordinates(String, String, PackagingType, String)} with
   * {@code group}, {@code artifact}, {@code type}, {@code version}.
   *
   * <p>Method under test: {@link MavenArtifactRepository#gavCoordinates(String, String,
   * PackagingType, String)}
   */
  @Test
  @DisplayName(
      "Test gavCoordinates(String, String, PackagingType, String) with 'group', 'artifact', 'type', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String MavenArtifactRepository.gavCoordinates(String, String, PackagingType, String)"
  })
  void testGavCoordinatesWithGroupArtifactTypeVersion() throws IllegalArgumentException {
    // Arrange
    MavenArtifactRepository mavenArtifactRepository = new MavenArtifactRepository(null);

    // Act and Assert
    assertEquals(
        "Group:Artifact:Type Name:1.0.2",
        mavenArtifactRepository.gavCoordinates(
            "Group", "Artifact", PackagingType.of("Type Name"), "1.0.2"));
  }

  /**
   * Test {@link MavenArtifactRepository#gavCoordinates(String, String, PackagingType, String)} with
   * {@code group}, {@code artifact}, {@code type}, {@code version}.
   *
   * <ul>
   *   <li>Then return {@code Group:Artifact:1.0.2}.
   * </ul>
   *
   * <p>Method under test: {@link MavenArtifactRepository#gavCoordinates(String, String,
   * PackagingType, String)}
   */
  @Test
  @DisplayName(
      "Test gavCoordinates(String, String, PackagingType, String) with 'group', 'artifact', 'type', 'version'; then return 'Group:Artifact:1.0.2'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String MavenArtifactRepository.gavCoordinates(String, String, PackagingType, String)"
  })
  void testGavCoordinatesWithGroupArtifactTypeVersion_thenReturnGroupArtifact102() {
    // Arrange, Act and Assert
    assertEquals(
        "Group:Artifact:1.0.2",
        new MavenArtifactRepository(null).gavCoordinates("Group", "Artifact", null, "1.0.2"));
  }

  /**
   * Test {@link MavenArtifactRepository#gavCoordinates(String, String, String)} with {@code group},
   * {@code artifact}, {@code version}.
   *
   * <ul>
   *   <li>Then return {@code Group:Artifact:1.0.2}.
   * </ul>
   *
   * <p>Method under test: {@link MavenArtifactRepository#gavCoordinates(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test gavCoordinates(String, String, String) with 'group', 'artifact', 'version'; then return 'Group:Artifact:1.0.2'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MavenArtifactRepository.gavCoordinates(String, String, String)"})
  void testGavCoordinatesWithGroupArtifactVersion_thenReturnGroupArtifact102() {
    // Arrange, Act and Assert
    assertEquals(
        "Group:Artifact:1.0.2",
        new MavenArtifactRepository(null).gavCoordinates("Group", "Artifact", "1.0.2"));
  }

  /**
   * Test {@link MavenArtifactRepository#areValidCoordinates(String, String)}.
   *
   * <ul>
   *   <li>When {@code Artifact}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link MavenArtifactRepository#areValidCoordinates(String, String)}
   */
  @Test
  @DisplayName("Test areValidCoordinates(String, String); when 'Artifact'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MavenArtifactRepository.areValidCoordinates(String, String)"})
  void testAreValidCoordinates_whenArtifact_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new MavenArtifactRepository(null).areValidCoordinates(":", "Artifact"));
  }

  /**
   * Test {@link MavenArtifactRepository#areValidCoordinates(String, String)}.
   *
   * <ul>
   *   <li>When {@code Artifact}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link MavenArtifactRepository#areValidCoordinates(String, String)}
   */
  @Test
  @DisplayName("Test areValidCoordinates(String, String); when 'Artifact'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MavenArtifactRepository.areValidCoordinates(String, String)"})
  void testAreValidCoordinates_whenArtifact_thenReturnFalse2() {
    // Arrange, Act and Assert
    assertFalse(new MavenArtifactRepository(null).areValidCoordinates(null, "Artifact"));
  }

  /**
   * Test {@link MavenArtifactRepository#areValidCoordinates(String, String)}.
   *
   * <ul>
   *   <li>When {@code Group}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link MavenArtifactRepository#areValidCoordinates(String, String)}
   */
  @Test
  @DisplayName("Test areValidCoordinates(String, String); when 'Group'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MavenArtifactRepository.areValidCoordinates(String, String)"})
  void testAreValidCoordinates_whenGroup_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new MavenArtifactRepository(null).areValidCoordinates("Group", ":"));
  }

  /**
   * Test {@link MavenArtifactRepository#areValidCoordinates(String, String)}.
   *
   * <ul>
   *   <li>When {@code Group}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link MavenArtifactRepository#areValidCoordinates(String, String)}
   */
  @Test
  @DisplayName("Test areValidCoordinates(String, String); when 'Group'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MavenArtifactRepository.areValidCoordinates(String, String)"})
  void testAreValidCoordinates_whenGroup_thenReturnFalse2() {
    // Arrange, Act and Assert
    assertFalse(new MavenArtifactRepository(null).areValidCoordinates("Group", null));
  }

  /**
   * Test {@link MavenArtifactRepository#areValidCoordinates(String, String)}.
   *
   * <ul>
   *   <li>When {@code Group}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link MavenArtifactRepository#areValidCoordinates(String, String)}
   */
  @Test
  @DisplayName("Test areValidCoordinates(String, String); when 'Group'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MavenArtifactRepository.areValidCoordinates(String, String)"})
  void testAreValidCoordinates_whenGroup_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new MavenArtifactRepository(null).areValidCoordinates("Group", "Artifact"));
  }

  /**
   * Test {@link MavenArtifactRepository#getJarFile(String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link
   *       MavenArtifactRepository#MavenArtifactRepository(ArtifactRepositoryProviderConfiguration)}
   *       with configuration is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MavenArtifactRepository#getJarFile(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test getJarFile(String, String, String); given MavenArtifactRepository(ArtifactRepositoryProviderConfiguration) with configuration is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.io.File MavenArtifactRepository.getJarFile(String, String, String)"})
  void testGetJarFile_givenMavenArtifactRepositoryWithConfigurationIsNull() {
    // Arrange, Act and Assert
    assertNull(new MavenArtifactRepository(null).getJarFile("Group", "Artifact", "1.0.2"));
  }

  /**
   * Test {@link MavenArtifactRepository#getJarFile(String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link TestMavenArtifactsRepository} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MavenArtifactRepository#getJarFile(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test getJarFile(String, String, String); given TestMavenArtifactsRepository (default constructor); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.io.File MavenArtifactRepository.getJarFile(String, String, String)"})
  void testGetJarFile_givenTestMavenArtifactsRepository_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new TestMavenArtifactsRepository().getJarFile("Group", "Artifact", "1.0.2"));
  }
}
