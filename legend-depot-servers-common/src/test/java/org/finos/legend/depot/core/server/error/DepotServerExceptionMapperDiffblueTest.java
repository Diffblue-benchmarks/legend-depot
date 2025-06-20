package org.finos.legend.depot.core.server.error;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DepotServerExceptionMapperDiffblueTest {
  /**
   * Test {@link DepotServerExceptionMapper#DepotServerExceptionMapper()}.
   * <ul>
   *   <li>Then return not {@link BaseExceptionMapper#includeStackTrace}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DepotServerExceptionMapper#DepotServerExceptionMapper()}
   */
  @Test
  @DisplayName("Test new DepotServerExceptionMapper(); then return not includeStackTrace")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DepotServerExceptionMapper.<init>()", "void DepotServerExceptionMapper.<init>(boolean)"})
  void testNewDepotServerExceptionMapper_thenReturnNotIncludeStackTrace() {
    // Arrange, Act and Assert
    assertFalse(new DepotServerExceptionMapper().includeStackTrace);
  }

  /**
   * Test {@link DepotServerExceptionMapper#DepotServerExceptionMapper(boolean)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return {@link BaseExceptionMapper#includeStackTrace}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DepotServerExceptionMapper#DepotServerExceptionMapper(boolean)}
   */
  @Test
  @DisplayName("Test new DepotServerExceptionMapper(boolean); when 'true'; then return includeStackTrace")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DepotServerExceptionMapper.<init>()", "void DepotServerExceptionMapper.<init>(boolean)"})
  void testNewDepotServerExceptionMapper_whenTrue_thenReturnIncludeStackTrace() {
    // Arrange, Act and Assert
    assertTrue(new DepotServerExceptionMapper(true).includeStackTrace);
  }
}
