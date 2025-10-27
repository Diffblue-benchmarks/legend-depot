package org.finos.legend.depot.services.api.artifacts.refresh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ParentEventDiffblueTest {
  /**
   * Method under test: {@link ParentEvent#build(String, String, String, String)}
   */
  @Test
  void testBuild() {
    // Arrange, Act and Assert
    assertEquals("42", ParentEvent.build("42", "42", "42", "42"));
    assertEquals("42_42_42", ParentEvent.build("42", "42", "42", null));
  }
}
