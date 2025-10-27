package org.finos.legend.depot.core.services.authorisation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import java.security.Principal;
import javax.inject.Provider;
import org.junit.jupiter.api.Test;

class BasicAuthorisationProviderDiffblueTest {
  /**
   * Method under test:
   * {@link BasicAuthorisationProvider#authorise(Provider, String)}
   */
  @Test
  void testAuthorise() {
    // Arrange, Act and Assert
    assertThrows(SecurityException.class,
        () -> (new BasicAuthorisationProvider()).authorise(mock(Provider.class), "Role"));
  }
}
