package org.finos.legend.depot.services.artifacts.repository.maven;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MavenArtifactRepositoryConfigurationDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MavenArtifactRepositoryConfiguration#MavenArtifactRepositoryConfiguration(String)}
   *   <li>{@link MavenArtifactRepositoryConfiguration#toString()}
   *   <li>{@link MavenArtifactRepositoryConfiguration#getSettingsLocation()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MavenArtifactRepositoryConfiguration.<init>(String)",
    "String MavenArtifactRepositoryConfiguration.getSettingsLocation()",
    "String MavenArtifactRepositoryConfiguration.toString()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    MavenArtifactRepositoryConfiguration actualMavenArtifactRepositoryConfiguration =
        new MavenArtifactRepositoryConfiguration("Settings Location");
    String actualToStringResult = actualMavenArtifactRepositoryConfiguration.toString();
    String actualSettingsLocation =
        actualMavenArtifactRepositoryConfiguration.getSettingsLocation();

    // Assert
    assertEquals(
        "MavenArtifactRepositoryConfiguration",
        actualMavenArtifactRepositoryConfiguration.getName());
    assertEquals(
        "MavenArtifactRepositoryConfiguration{name='MavenArtifactRepositoryConfiguration'settings='Settings"
            + " Location'}",
        actualToStringResult);
    assertEquals("Settings Location", actualSettingsLocation);
  }
}
