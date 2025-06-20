package org.finos.legend.depot.services.api.artifacts.refresh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ParentEventDiffblueTest {
  /**
   * Test {@link ParentEvent#build(String, String, String, String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ParentEvent#build(String, String, String, String)}
   */
  @Test
  @DisplayName("Test build(String, String, String, String); when '42'; then return '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ParentEvent.build(String, String, String, String)"})
  void testBuild_when42_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", ParentEvent.build("42", "42", "42", "42"));
  }

  /**
   * Test {@link ParentEvent#build(String, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code 42_42_42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ParentEvent#build(String, String, String, String)}
   */
  @Test
  @DisplayName("Test build(String, String, String, String); when 'null'; then return '42_42_42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ParentEvent.build(String, String, String, String)"})
  void testBuild_whenNull_thenReturn424242() {
    // Arrange, Act and Assert
    assertEquals("42_42_42", ParentEvent.build("42", "42", "42", null));
  }
}
