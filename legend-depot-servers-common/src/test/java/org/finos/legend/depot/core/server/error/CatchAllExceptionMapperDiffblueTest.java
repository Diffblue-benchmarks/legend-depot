package org.finos.legend.depot.core.server.error;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CatchAllExceptionMapperDiffblueTest {
  /**
   * Test {@link CatchAllExceptionMapper#CatchAllExceptionMapper()}.
   *
   * <ul>
   *   <li>Then return not {@link BaseExceptionMapper#includeStackTrace}.
   * </ul>
   *
   * <p>Method under test: {@link CatchAllExceptionMapper#CatchAllExceptionMapper()}
   */
  @Test
  @DisplayName("Test new CatchAllExceptionMapper(); then return not includeStackTrace")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CatchAllExceptionMapper.<init>()",
    "void CatchAllExceptionMapper.<init>(boolean)"
  })
  void testNewCatchAllExceptionMapper_thenReturnNotIncludeStackTrace() {
    // Arrange, Act and Assert
    assertFalse(new CatchAllExceptionMapper().includeStackTrace);
  }

  /**
   * Test {@link CatchAllExceptionMapper#CatchAllExceptionMapper(boolean)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@link BaseExceptionMapper#includeStackTrace}.
   * </ul>
   *
   * <p>Method under test: {@link CatchAllExceptionMapper#CatchAllExceptionMapper(boolean)}
   */
  @Test
  @DisplayName(
      "Test new CatchAllExceptionMapper(boolean); when 'true'; then return includeStackTrace")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CatchAllExceptionMapper.<init>()",
    "void CatchAllExceptionMapper.<init>(boolean)"
  })
  void testNewCatchAllExceptionMapper_whenTrue_thenReturnIncludeStackTrace() {
    // Arrange, Act and Assert
    assertTrue(new CatchAllExceptionMapper(true).includeStackTrace);
  }
}
