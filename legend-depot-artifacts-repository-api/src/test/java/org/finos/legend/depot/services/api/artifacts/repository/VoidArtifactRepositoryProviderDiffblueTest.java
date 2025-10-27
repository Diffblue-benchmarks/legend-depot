package org.finos.legend.depot.services.api.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.maven.model.Model;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.junit.jupiter.api.Test;

class VoidArtifactRepositoryProviderDiffblueTest {
  /**
   * Method under test:
   * {@link VoidArtifactRepositoryProvider#areValidCoordinates(String, String)}
   */
  @Test
  void testAreValidCoordinates() {
    // Arrange, Act and Assert
    assertFalse((new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration()))
        .areValidCoordinates("Group", "Artifact"));
  }

  /**
   * Method under test:
   * {@link VoidArtifactRepositoryProvider#getPOM(String, String, String)}
   */
  @Test
  void testGetPOM() {
    // Arrange and Act
    Model actualPOM = (new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration())).getPOM("Group",
        "Artifact", "1.0.2");

    // Assert
    assertEquals("UTF-8", actualPOM.getModelEncoding());
    assertEquals("[inherited]:null:jar:[inherited]", actualPOM.getId());
    assertEquals("jar", actualPOM.getPackaging());
    assertNull(actualPOM.getPomFile());
    assertNull(actualPOM.getProjectDirectory());
    assertNull(actualPOM.getReports());
    assertNull(actualPOM.getArtifactId());
    assertNull(actualPOM.getChildProjectUrlInheritAppendPath());
    assertNull(actualPOM.getDescription());
    assertNull(actualPOM.getGroupId());
    assertNull(actualPOM.getInceptionYear());
    assertNull(actualPOM.getModelVersion());
    assertNull(actualPOM.getName());
    assertNull(actualPOM.getUrl());
    assertNull(actualPOM.getVersion());
    assertNull(actualPOM.getBuild());
    assertNull(actualPOM.getCiManagement());
    assertNull(actualPOM.getDependencyManagement());
    assertNull(actualPOM.getDistributionManagement());
    assertNull(actualPOM.getIssueManagement());
    assertNull(actualPOM.getOrganization());
    assertNull(actualPOM.getParent());
    assertNull(actualPOM.getPrerequisites());
    assertNull(actualPOM.getReporting());
    assertNull(actualPOM.getScm());
    assertTrue(actualPOM.getContributors().isEmpty());
    assertTrue(actualPOM.getDevelopers().isEmpty());
    assertTrue(actualPOM.getLicenses().isEmpty());
    assertTrue(actualPOM.getMailingLists().isEmpty());
    assertTrue(actualPOM.getProfiles().isEmpty());
    assertTrue(actualPOM.getDependencies().isEmpty());
    assertTrue(actualPOM.getModules().isEmpty());
    assertTrue(actualPOM.getPluginRepositories().isEmpty());
    assertTrue(actualPOM.getRepositories().isEmpty());
    assertTrue(actualPOM.getProperties().isEmpty());
  }

  /**
   * Method under test:
   * {@link VoidArtifactRepositoryProvider#getJarFile(String, String, String)}
   */
  @Test
  void testGetJarFile() {
    // Arrange, Act and Assert
    assertNull((new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration())).getJarFile("Group",
        "Artifact", "1.0.2"));
  }

  /**
   * Method under test:
   * {@link VoidArtifactRepositoryProvider#getModulesFromPOM(ArtifactType, String, String, String)}
   */
  @Test
  void testGetModulesFromPOM() {
    // Arrange, Act and Assert
    assertTrue((new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration()))
        .getModulesFromPOM(ArtifactType.ENTITIES, "Group", "Artifact", "1.0.2")
        .isEmpty());
  }

  /**
   * Method under test:
   * {@link VoidArtifactRepositoryProvider#findVersions(String, String)}
   */
  @Test
  void testFindVersions() {
    // Arrange, Act and Assert
    assertTrue((new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration()))
        .findVersions("Group", "Artifact")
        .isEmpty());
  }

  /**
   * Method under test:
   * {@link VoidArtifactRepositoryProvider#findVersion(String, String, String)}
   */
  @Test
  void testFindVersion() throws ArtifactRepositoryException {
    // Arrange, Act and Assert
    assertFalse((new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration()))
        .findVersion("Group", "Artifact", "42")
        .isPresent());
  }

  /**
   * Method under test:
   * {@link VoidArtifactRepositoryProvider#findFiles(ArtifactType, String, String, String)}
   */
  @Test
  void testFindFiles() {
    // Arrange, Act and Assert
    assertTrue((new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration()))
        .findFiles(ArtifactType.ENTITIES, "Group", "Artifact", "1.0.2")
        .isEmpty());
  }

  /**
   * Method under test:
   * {@link VoidArtifactRepositoryProvider#findDependenciesFiles(ArtifactType, String, String, String)}
   */
  @Test
  void testFindDependenciesFiles() {
    // Arrange, Act and Assert
    assertTrue((new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration()))
        .findDependenciesFiles(ArtifactType.ENTITIES, "Group", "Artifact", "1.0.2")
        .isEmpty());
  }

  /**
   * Method under test:
   * {@link VoidArtifactRepositoryProvider#findDependenciesByArtifactType(ArtifactType, String, String, String)}
   */
  @Test
  void testFindDependenciesByArtifactType() {
    // Arrange, Act and Assert
    assertTrue((new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration()))
        .findDependenciesByArtifactType(ArtifactType.ENTITIES, "42", "42", "42")
        .isEmpty());
  }

  /**
   * Method under test:
   * {@link VoidArtifactRepositoryProvider#findDependencies(String, String, String)}
   */
  @Test
  void testFindDependencies() {
    // Arrange, Act and Assert
    assertTrue((new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration()))
        .findDependencies("42", "42", "42")
        .isEmpty());
  }
}
