package org.finos.legend.depot.services.api.artifacts.configuration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class IncludeProjectPropertiesConfigurationDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link IncludeProjectPropertiesConfiguration#IncludeProjectPropertiesConfiguration(List,
   *       List)}
   *   <li>{@link IncludeProjectPropertiesConfiguration#getManifestProperties()}
   *   <li>{@link IncludeProjectPropertiesConfiguration#getProperties()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IncludeProjectPropertiesConfiguration.<init>(List, List)",
    "List IncludeProjectPropertiesConfiguration.getManifestProperties()",
    "List IncludeProjectPropertiesConfiguration.getProperties()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArrayList<String> properties = new ArrayList<>();
    ArrayList<String> manifestProperties = new ArrayList<>();

    // Act
    IncludeProjectPropertiesConfiguration actualIncludeProjectPropertiesConfiguration =
        new IncludeProjectPropertiesConfiguration(properties, manifestProperties);
    List<String> actualManifestProperties =
        actualIncludeProjectPropertiesConfiguration.getManifestProperties();
    List<String> actualProperties = actualIncludeProjectPropertiesConfiguration.getProperties();

    // Assert
    assertTrue(actualManifestProperties.isEmpty());
    assertTrue(actualProperties.isEmpty());
    assertSame(manifestProperties, actualManifestProperties);
    assertSame(properties, actualProperties);
  }
}
