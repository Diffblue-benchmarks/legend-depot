package org.finos.legend.depot.core.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.core.services.authorisation.BasicAuthorisationProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AuthorisationModuleDiffblueTest {
  /**
   * Test {@link AuthorisationModule#getAuthorisationProvider()}.
   *
   * <ul>
   *   <li>Then return {@link BasicAuthorisationProvider}.
   * </ul>
   *
   * <p>Method under test: {@link AuthorisationModule#getAuthorisationProvider()}
   */
  @Test
  @DisplayName("Test getAuthorisationProvider(); then return BasicAuthorisationProvider")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider AuthorisationModule.getAuthorisationProvider()"
  })
  void testGetAuthorisationProvider_thenReturnBasicAuthorisationProvider() {
    // Arrange, Act and Assert
    assertTrue(
        new AuthorisationModule().getAuthorisationProvider() instanceof BasicAuthorisationProvider);
  }

  /**
   * Test new {@link AuthorisationModule} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link AuthorisationModule}
   */
  @Test
  @DisplayName("Test new AuthorisationModule (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuthorisationModule.<init>()"})
  void testNewAuthorisationModule() {
    // Arrange, Act and Assert
    assertTrue(
        new AuthorisationModule().getAuthorisationProvider() instanceof BasicAuthorisationProvider);
  }
}
