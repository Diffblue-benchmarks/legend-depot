package org.finos.legend.depot.core.services.authorisation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BasicAuthorisationProviderDiffblueTest {
  /**
   * Test {@link BasicAuthorisationProvider#BasicAuthorisationProvider()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link BasicAuthorisationProvider#BasicAuthorisationProvider()}
   */
  @Test
  @DisplayName("Test new BasicAuthorisationProvider(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BasicAuthorisationProvider.<init>()"})
  void testNewBasicAuthorisationProvider_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> new BasicAuthorisationProvider());
  }
}
