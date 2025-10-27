package org.finos.legend.depot.store.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import com.fasterxml.jackson.core.Version;
import org.junit.jupiter.api.Test;

class LegendDepotStoreServerJacksonJsonProviderDiffblueTest {
  /**
   * Method under test: default or parameterless constructor of
   * {@link LegendDepotStoreServerJacksonJsonProvider}
   */
  @Test
  void testNewLegendDepotStoreServerJacksonJsonProvider() {
    // Arrange, Act and Assert
    Version versionResult = (new LegendDepotStoreServerJacksonJsonProvider()).version();
    assertEquals("com.fasterxml.jackson.jaxrs", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.jaxrs/jackson-jaxrs-json-provider/2.10.5", versionResult.toFullString());
    assertEquals("jackson-jaxrs-json-provider", versionResult.getArtifactId());
    assertEquals(10, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(5, versionResult.getPatchLevel());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
  }
}
