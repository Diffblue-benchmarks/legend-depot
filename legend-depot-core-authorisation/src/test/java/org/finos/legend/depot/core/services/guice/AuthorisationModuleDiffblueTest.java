package org.finos.legend.depot.core.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.core.services.authorisation.BasicAuthorisationProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AuthorisationModuleDiffblueTest {
  /**
   * Test new {@link AuthorisationModule} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link AuthorisationModule}
   */
  @Test
  @DisplayName("Test new AuthorisationModule (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AuthorisationModule.<init>()"})
  void testNewAuthorisationModule() {
    // Arrange, Act and Assert
    assertTrue((new AuthorisationModule()).getAuthorisationProvider() instanceof BasicAuthorisationProvider);
  }
}
