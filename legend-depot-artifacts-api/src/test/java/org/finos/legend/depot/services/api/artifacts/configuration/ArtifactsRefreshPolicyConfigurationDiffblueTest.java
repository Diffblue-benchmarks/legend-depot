package org.finos.legend.depot.services.api.artifacts.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class ArtifactsRefreshPolicyConfigurationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ArtifactsRefreshPolicyConfiguration#getIncludeProjectPropertiesConfiguration()}
   *   <li>
   * {@link ArtifactsRefreshPolicyConfiguration#getVersionsUpdateIntervalInMillis()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<String> properties = new ArrayList<>();
    ArtifactsRefreshPolicyConfiguration artifactsRefreshPolicyConfiguration = new ArtifactsRefreshPolicyConfiguration(
        42L, new IncludeProjectPropertiesConfiguration(properties, new ArrayList<>()));

    // Act
    IncludeProjectPropertiesConfiguration actualIncludeProjectPropertiesConfiguration = artifactsRefreshPolicyConfiguration
        .getIncludeProjectPropertiesConfiguration();

    // Assert
    assertEquals(42L, artifactsRefreshPolicyConfiguration.getVersionsUpdateIntervalInMillis());
    assertSame(artifactsRefreshPolicyConfiguration.includeProjectPropertiesConfiguration,
        actualIncludeProjectPropertiesConfiguration);
  }

  /**
   * Method under test:
   * {@link ArtifactsRefreshPolicyConfiguration#ArtifactsRefreshPolicyConfiguration(Long, IncludeProjectPropertiesConfiguration)}
   */
  @Test
  void testNewArtifactsRefreshPolicyConfiguration() {
    // Arrange
    ArrayList<String> properties = new ArrayList<>();
    IncludeProjectPropertiesConfiguration includeProjectPropertiesConfiguration = new IncludeProjectPropertiesConfiguration(
        properties, new ArrayList<>());

    // Act
    ArtifactsRefreshPolicyConfiguration actualArtifactsRefreshPolicyConfiguration = new ArtifactsRefreshPolicyConfiguration(
        42L, includeProjectPropertiesConfiguration);

    // Assert
    assertEquals(42L, actualArtifactsRefreshPolicyConfiguration.getVersionsUpdateIntervalInMillis());
    assertSame(includeProjectPropertiesConfiguration,
        actualArtifactsRefreshPolicyConfiguration.getIncludeProjectPropertiesConfiguration());
  }

  /**
   * Method under test:
   * {@link ArtifactsRefreshPolicyConfiguration#ArtifactsRefreshPolicyConfiguration(Long, IncludeProjectPropertiesConfiguration)}
   */
  @Test
  void testNewArtifactsRefreshPolicyConfiguration2() {
    // Arrange
    ArrayList<String> properties = new ArrayList<>();
    IncludeProjectPropertiesConfiguration includeProjectPropertiesConfiguration = new IncludeProjectPropertiesConfiguration(
        properties, new ArrayList<>());

    // Act
    ArtifactsRefreshPolicyConfiguration actualArtifactsRefreshPolicyConfiguration = new ArtifactsRefreshPolicyConfiguration(
        null, includeProjectPropertiesConfiguration);

    // Assert
    assertEquals(7200000L, actualArtifactsRefreshPolicyConfiguration.getVersionsUpdateIntervalInMillis());
    assertSame(includeProjectPropertiesConfiguration,
        actualArtifactsRefreshPolicyConfiguration.getIncludeProjectPropertiesConfiguration());
  }
}
