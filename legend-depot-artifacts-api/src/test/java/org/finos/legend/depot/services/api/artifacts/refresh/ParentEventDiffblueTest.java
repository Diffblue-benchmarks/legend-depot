package org.finos.legend.depot.services.api.artifacts.refresh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ParentEventDiffblueTest {
  /**
   * Test {@link ParentEvent#build(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ParentEvent#build(String, String, String, String)}
   */
  @Test
  @DisplayName("Test build(String, String, String, String); when '42'; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ParentEvent.build(String, String, String, String)"})
  void testBuild_when42_thenReturn42() {
    // Arrange and Act
    String actualString = ParentEvent.build("42", "42", "42", "42");

    // Assert
    assertEquals("42", actualString);
  }

  /**
   * Test {@link ParentEvent#build(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code 42_42_42}.
   * </ul>
   *
   * <p>Method under test: {@link ParentEvent#build(String, String, String, String)}
   */
  @Test
  @DisplayName("Test build(String, String, String, String); when 'null'; then return '42_42_42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ParentEvent.build(String, String, String, String)"})
  void testBuild_whenNull_thenReturn424242() {
    // Arrange and Act
    String actualString = ParentEvent.build("42", "42", "42", null);

    // Assert
    assertEquals("42_42_42", actualString);
  }
}
