package org.finos.legend.depot.store.resources.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.security.Principal;
import javax.inject.Provider;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ManageProjectsResourceDiffblueTest {
  /**
   * Test {@link ManageProjectsResource#deleteProject(String, String)}.
   * <ul>
   *   <li>Given {@link ManageProjectsServiceImpl} {@link ManageProjectsServiceImpl#delete(String, String)} return one.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageProjectsResource#deleteProject(String, String)}
   */
  @Test
  @DisplayName("Test deleteProject(String, String); given ManageProjectsServiceImpl delete(String, String) return one; then return one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long ManageProjectsResource.deleteProject(String, String)"})
  void testDeleteProject_givenManageProjectsServiceImplDeleteReturnOne_thenReturnOne() {
    // Arrange
    ManageProjectsServiceImpl projectApi = mock(ManageProjectsServiceImpl.class);
    when(projectApi.delete(Mockito.<String>any(), Mockito.<String>any())).thenReturn(1L);
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    // Act
    long actualDeleteProjectResult = new ManageProjectsResource(projectApi, authorisationProvider, mock(Provider.class))
        .deleteProject("42", "42");

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Projects"));
    verify(projectApi).delete(eq("42"), eq("42"));
    assertEquals(1L, actualDeleteProjectResult);
  }
}
