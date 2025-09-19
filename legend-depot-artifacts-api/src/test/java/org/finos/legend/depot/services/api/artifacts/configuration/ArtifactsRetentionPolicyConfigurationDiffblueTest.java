package org.finos.legend.depot.services.api.artifacts.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ArtifactsRetentionPolicyConfigurationDiffblueTest {
  /**
   * Test {@link
   * ArtifactsRetentionPolicyConfiguration#ArtifactsRetentionPolicyConfiguration(Integer, Integer,
   * Integer)}.
   *
   * <ul>
   *   <li>Then return TtlForSnapshots is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactsRetentionPolicyConfiguration#ArtifactsRetentionPolicyConfiguration(Integer, Integer,
   * Integer)}
   */
  @Test
  @DisplayName(
      "Test new ArtifactsRetentionPolicyConfiguration(Integer, Integer, Integer); then return TtlForSnapshots is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ArtifactsRetentionPolicyConfiguration.<init>(Integer, Integer, Integer)"
  })
  void testNewArtifactsRetentionPolicyConfiguration_thenReturnTtlForSnapshotsIsOne() {
    // Arrange and Act
    ArtifactsRetentionPolicyConfiguration actualArtifactsRetentionPolicyConfiguration =
        new ArtifactsRetentionPolicyConfiguration(3, 1, 1);

    // Assert
    assertEquals(1, actualArtifactsRetentionPolicyConfiguration.getTtlForSnapshots());
    assertEquals(1, actualArtifactsRetentionPolicyConfiguration.getTtlForVersions());
    assertEquals(3, actualArtifactsRetentionPolicyConfiguration.getMaximumSnapshotsAllowed());
  }

  /**
   * Test {@link
   * ArtifactsRetentionPolicyConfiguration#ArtifactsRetentionPolicyConfiguration(Integer, Integer,
   * Integer)}.
   *
   * <ul>
   *   <li>Then return TtlForSnapshots is thirty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactsRetentionPolicyConfiguration#ArtifactsRetentionPolicyConfiguration(Integer, Integer,
   * Integer)}
   */
  @Test
  @DisplayName(
      "Test new ArtifactsRetentionPolicyConfiguration(Integer, Integer, Integer); then return TtlForSnapshots is thirty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ArtifactsRetentionPolicyConfiguration.<init>(Integer, Integer, Integer)"
  })
  void testNewArtifactsRetentionPolicyConfiguration_thenReturnTtlForSnapshotsIsThirty() {
    // Arrange and Act
    ArtifactsRetentionPolicyConfiguration actualArtifactsRetentionPolicyConfiguration =
        new ArtifactsRetentionPolicyConfiguration(null, null, null);

    // Assert
    assertEquals(30, actualArtifactsRetentionPolicyConfiguration.getTtlForSnapshots());
    assertEquals(365, actualArtifactsRetentionPolicyConfiguration.getTtlForVersions());
    assertEquals(5, actualArtifactsRetentionPolicyConfiguration.getMaximumSnapshotsAllowed());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ArtifactsRetentionPolicyConfiguration#getMaximumSnapshotsAllowed()}
   *   <li>{@link ArtifactsRetentionPolicyConfiguration#getTtlForSnapshots()}
   *   <li>{@link ArtifactsRetentionPolicyConfiguration#getTtlForVersions()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ArtifactsRetentionPolicyConfiguration.getMaximumSnapshotsAllowed()",
    "int ArtifactsRetentionPolicyConfiguration.getTtlForSnapshots()",
    "int ArtifactsRetentionPolicyConfiguration.getTtlForVersions()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArtifactsRetentionPolicyConfiguration artifactsRetentionPolicyConfiguration =
        new ArtifactsRetentionPolicyConfiguration(3, 1, 1);

    // Act
    int actualMaximumSnapshotsAllowed =
        artifactsRetentionPolicyConfiguration.getMaximumSnapshotsAllowed();
    int actualTtlForSnapshots = artifactsRetentionPolicyConfiguration.getTtlForSnapshots();

    // Assert
    assertEquals(1, actualTtlForSnapshots);
    assertEquals(1, artifactsRetentionPolicyConfiguration.getTtlForVersions());
    assertEquals(3, actualMaximumSnapshotsAllowed);
  }
}
