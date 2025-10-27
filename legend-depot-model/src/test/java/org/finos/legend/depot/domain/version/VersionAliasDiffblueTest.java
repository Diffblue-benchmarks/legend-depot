package org.finos.legend.depot.domain.version;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class VersionAliasDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionAlias#getDescription()}
   *   <li>{@link VersionAlias#getName()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    VersionAlias valueOfResult = VersionAlias.valueOf("LATEST");

    // Act
    String actualDescription = valueOfResult.getDescription();

    // Assert
    assertEquals("latest", valueOfResult.getName());
    assertEquals(String.join("", "last ", System.getProperty("jdk.debug"), "d version"), actualDescription);
  }
}
