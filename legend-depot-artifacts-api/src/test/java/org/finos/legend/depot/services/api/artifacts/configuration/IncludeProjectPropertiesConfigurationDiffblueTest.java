package org.finos.legend.depot.services.api.artifacts.configuration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class IncludeProjectPropertiesConfigurationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link IncludeProjectPropertiesConfiguration#IncludeProjectPropertiesConfiguration(List, List)}
   *   <li>{@link IncludeProjectPropertiesConfiguration#getManifestProperties()}
   *   <li>{@link IncludeProjectPropertiesConfiguration#getProperties()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<String> properties = new ArrayList<>();
    ArrayList<String> manifestProperties = new ArrayList<>();

    // Act
    IncludeProjectPropertiesConfiguration actualIncludeProjectPropertiesConfiguration = new IncludeProjectPropertiesConfiguration(
        properties, manifestProperties);
    List<String> actualManifestProperties = actualIncludeProjectPropertiesConfiguration.getManifestProperties();
    List<String> actualProperties = actualIncludeProjectPropertiesConfiguration.getProperties();

    // Assert
    assertTrue(actualManifestProperties.isEmpty());
    assertTrue(actualProperties.isEmpty());
    assertSame(manifestProperties, actualManifestProperties);
    assertSame(properties, actualProperties);
  }
}
