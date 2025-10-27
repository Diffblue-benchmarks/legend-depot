package org.finos.legend.depot.services.api.artifacts.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ArtifactsRefreshPolicyConfigurationDiffblueTest {
  /**
   * Test {@link ArtifactsRefreshPolicyConfiguration#ArtifactsRefreshPolicyConfiguration(Long, IncludeProjectPropertiesConfiguration)}.
   * <p>
   * Method under test: {@link ArtifactsRefreshPolicyConfiguration#ArtifactsRefreshPolicyConfiguration(Long, IncludeProjectPropertiesConfiguration)}
   */
  @Test
  @DisplayName("Test new ArtifactsRefreshPolicyConfiguration(Long, IncludeProjectPropertiesConfiguration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ArtifactsRefreshPolicyConfiguration.<init>(Long, IncludeProjectPropertiesConfiguration)"})
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
   * Test {@link ArtifactsRefreshPolicyConfiguration#ArtifactsRefreshPolicyConfiguration(Long, IncludeProjectPropertiesConfiguration)}.
   * <p>
   * Method under test: {@link ArtifactsRefreshPolicyConfiguration#ArtifactsRefreshPolicyConfiguration(Long, IncludeProjectPropertiesConfiguration)}
   */
  @Test
  @DisplayName("Test new ArtifactsRefreshPolicyConfiguration(Long, IncludeProjectPropertiesConfiguration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ArtifactsRefreshPolicyConfiguration.<init>(Long, IncludeProjectPropertiesConfiguration)"})
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

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ArtifactsRefreshPolicyConfiguration#getIncludeProjectPropertiesConfiguration()}
   *   <li>{@link ArtifactsRefreshPolicyConfiguration#getVersionsUpdateIntervalInMillis()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "IncludeProjectPropertiesConfiguration ArtifactsRefreshPolicyConfiguration.getIncludeProjectPropertiesConfiguration()",
      "long ArtifactsRefreshPolicyConfiguration.getVersionsUpdateIntervalInMillis()"})
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
}
