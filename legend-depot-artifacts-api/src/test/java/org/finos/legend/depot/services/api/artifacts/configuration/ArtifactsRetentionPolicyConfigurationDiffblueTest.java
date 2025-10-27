package org.finos.legend.depot.services.api.artifacts.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ArtifactsRetentionPolicyConfigurationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ArtifactsRetentionPolicyConfiguration#getMaximumSnapshotsAllowed()}
   *   <li>{@link ArtifactsRetentionPolicyConfiguration#getTtlForSnapshots()}
   *   <li>{@link ArtifactsRetentionPolicyConfiguration#getTtlForVersions()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArtifactsRetentionPolicyConfiguration artifactsRetentionPolicyConfiguration = new ArtifactsRetentionPolicyConfiguration(
        3, 1, 1);

    // Act
    int actualMaximumSnapshotsAllowed = artifactsRetentionPolicyConfiguration.getMaximumSnapshotsAllowed();
    int actualTtlForSnapshots = artifactsRetentionPolicyConfiguration.getTtlForSnapshots();

    // Assert
    assertEquals(1, actualTtlForSnapshots);
    assertEquals(1, artifactsRetentionPolicyConfiguration.getTtlForVersions());
    assertEquals(3, actualMaximumSnapshotsAllowed);
  }

  /**
   * Method under test:
   * {@link ArtifactsRetentionPolicyConfiguration#ArtifactsRetentionPolicyConfiguration(Integer, Integer, Integer)}
   */
  @Test
  void testNewArtifactsRetentionPolicyConfiguration() {
    // Arrange and Act
    ArtifactsRetentionPolicyConfiguration actualArtifactsRetentionPolicyConfiguration = new ArtifactsRetentionPolicyConfiguration(
        3, 1, 1);

    // Assert
    assertEquals(1, actualArtifactsRetentionPolicyConfiguration.getTtlForSnapshots());
    assertEquals(1, actualArtifactsRetentionPolicyConfiguration.getTtlForVersions());
    assertEquals(3, actualArtifactsRetentionPolicyConfiguration.getMaximumSnapshotsAllowed());
  }

  /**
   * Method under test:
   * {@link ArtifactsRetentionPolicyConfiguration#ArtifactsRetentionPolicyConfiguration(Integer, Integer, Integer)}
   */
  @Test
  void testNewArtifactsRetentionPolicyConfiguration2() {
    // Arrange and Act
    ArtifactsRetentionPolicyConfiguration actualArtifactsRetentionPolicyConfiguration = new ArtifactsRetentionPolicyConfiguration(
        null, null, null);

    // Assert
    assertEquals(30, actualArtifactsRetentionPolicyConfiguration.getTtlForSnapshots());
    assertEquals(365, actualArtifactsRetentionPolicyConfiguration.getTtlForVersions());
    assertEquals(5, actualArtifactsRetentionPolicyConfiguration.getMaximumSnapshotsAllowed());
  }
}
