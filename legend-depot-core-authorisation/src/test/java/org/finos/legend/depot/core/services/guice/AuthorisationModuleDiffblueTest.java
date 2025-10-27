package org.finos.legend.depot.core.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.finos.legend.depot.core.services.authorisation.BasicAuthorisationProvider;
import org.junit.jupiter.api.Test;

class AuthorisationModuleDiffblueTest {
  /**
   * Method under test: default or parameterless constructor of
   * {@link AuthorisationModule}
   */
  @Test
  void testNewAuthorisationModule() {
    // Arrange, Act and Assert
    assertTrue((new AuthorisationModule()).getAuthorisationProvider() instanceof BasicAuthorisationProvider);
  }
}
