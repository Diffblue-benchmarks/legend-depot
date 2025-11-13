package org.finos.legend.depot.store.resources.artifacts;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.security.Principal;
import javax.inject.Provider;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.services.artifacts.refresh.RefreshDependenciesServiceImpl;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ArtifactDependenciesRefreshResourceDiffblueTest {
  /**
   * Test {@link ArtifactDependenciesRefreshResource#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <ul>
   *   <li>Then return {@link StoreProjectVersionData#StoreProjectVersionData()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactDependenciesRefreshResource#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test updateTransitiveDependencies(String, String, String); then return StoreProjectVersionData()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData ArtifactDependenciesRefreshResource.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies_thenReturnStoreProjectVersionData() {
    // Arrange
    RefreshDependenciesServiceImpl refreshDependenciesService =
        mock(RefreshDependenciesServiceImpl.class);
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData();
    when(refreshDependenciesService.updateTransitiveDependencies(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionData);

    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    ArtifactDependenciesRefreshResource artifactDependenciesRefreshResource =
        new ArtifactDependenciesRefreshResource(
            refreshDependenciesService, authorisationProvider, mock(Provider.class));

    // Act
    StoreProjectVersionData actualUpdateTransitiveDependenciesResult =
        artifactDependenciesRefreshResource.updateTransitiveDependencies("42", "42", "42");

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("ArtifactsRefresh"));
    verify(refreshDependenciesService).updateTransitiveDependencies("42", "42", "42");
    assertSame(storeProjectVersionData, actualUpdateTransitiveDependenciesResult);
  }
}
