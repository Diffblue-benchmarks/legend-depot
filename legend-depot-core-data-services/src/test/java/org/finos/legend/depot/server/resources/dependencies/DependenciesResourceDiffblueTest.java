package org.finos.legend.depot.server.resources.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyReport;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DependenciesResourceDiffblueTest {
  /**
   * Test {@link DependenciesResource#analyzeDependencyTree(List)}.
   *
   * <ul>
   *   <li>Then StatusInfo return {@link Status}.
   * </ul>
   *
   * <p>Method under test: {@link DependenciesResource#analyzeDependencyTree(List)}
   */
  @Test
  @DisplayName("Test analyzeDependencyTree(List); then StatusInfo return Status")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response DependenciesResource.analyzeDependencyTree(List)"})
  void testAnalyzeDependencyTree_thenStatusInfoReturnStatus() {
    // Arrange
    ManageProjectsServiceImpl projectApi = mock(ManageProjectsServiceImpl.class);
    ProjectDependencyReport projectDependencyReport = new ProjectDependencyReport();
    when(projectApi.getProjectDependencyReport(Mockito.<List<ProjectVersion>>any()))
        .thenReturn(projectDependencyReport);
    DependenciesResource dependenciesResource = new DependenciesResource(projectApi);

    // Act
    Response actualAnalyzeDependencyTreeResult =
        dependenciesResource.analyzeDependencyTree(new ArrayList<>());

    // Assert
    verify(projectApi).getProjectDependencyReport(isA(List.class));
    StatusType statusInfo = actualAnalyzeDependencyTreeResult.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    Object entity = actualAnalyzeDependencyTreeResult.getEntity();
    assertTrue(entity instanceof ProjectDependencyReport);
    assertTrue(actualAnalyzeDependencyTreeResult instanceof OutboundJaxrsResponse);
    assertNull(actualAnalyzeDependencyTreeResult.getLocation());
    assertNull(actualAnalyzeDependencyTreeResult.getDate());
    assertNull(actualAnalyzeDependencyTreeResult.getLastModified());
    assertNull(actualAnalyzeDependencyTreeResult.getLanguage());
    assertNull(actualAnalyzeDependencyTreeResult.getEntityTag());
    assertNull(actualAnalyzeDependencyTreeResult.getMediaType());
    assertEquals(-1, actualAnalyzeDependencyTreeResult.getLength());
    MultivaluedMap<String, Object> headers = actualAnalyzeDependencyTreeResult.getHeaders();
    assertEquals(1, headers.size());
    MultivaluedMap<String, String> stringHeaders =
        actualAnalyzeDependencyTreeResult.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    assertEquals(200, actualAnalyzeDependencyTreeResult.getStatus());
    assertEquals(Status.OK, statusInfo);
    assertTrue(headers.containsKey("Cache-Control"));
    assertTrue(stringHeaders.containsKey("Cache-Control"));
    assertTrue(actualAnalyzeDependencyTreeResult.getCookies().isEmpty());
    Set<String> allowedMethods = actualAnalyzeDependencyTreeResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(projectDependencyReport, entity);
    assertSame(allowedMethods, actualAnalyzeDependencyTreeResult.getLinks());
    assertSame(headers, actualAnalyzeDependencyTreeResult.getMetadata());
  }
}
