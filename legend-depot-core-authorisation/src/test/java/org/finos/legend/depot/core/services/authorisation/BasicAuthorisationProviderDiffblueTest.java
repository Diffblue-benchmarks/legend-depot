package org.finos.legend.depot.core.services.authorisation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import javax.inject.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BasicAuthorisationProviderDiffblueTest {
  /**
   * Test {@link BasicAuthorisationProvider#authorise(Provider, String)}.
   *
   * <p>Method under test: {@link BasicAuthorisationProvider#authorise(Provider, String)}
   */
  @Test
  @DisplayName("Test authorise(Provider, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BasicAuthorisationProvider.authorise(Provider, String)"})
  void testAuthorise() {
    // Arrange, Act and Assert
    assertThrows(
        SecurityException.class,
        () -> new BasicAuthorisationProvider().authorise(mock(Provider.class), "Role"));
  }
}
