package org.finos.legend.depot.server.resources.pure.model.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.services.api.pure.model.context.PureModelContextService;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PureModelContextResourceDiffblueTest {
  @InjectMocks
  private PureModelContextResource pureModelContextResource;

  @Mock
  private PureModelContextService pureModelContextService;

  /**
   * Test {@link PureModelContextResource#getPureModelContextData(String, String, String, String, boolean, boolean, Request)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code clientVersion}, {@code transitive}, {@code convertToNewProtocol}, {@code request}.
   * <p>
   * Method under test: {@link PureModelContextResource#getPureModelContextData(String, String, String, String, boolean, boolean, Request)}
   */
  @Test
  @DisplayName("Test getPureModelContextData(String, String, String, String, boolean, boolean, Request) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol', 'request'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "Response PureModelContextResource.getPureModelContextData(String, String, String, String, boolean, boolean, Request)"})
  void testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocolRequest() {
    // Arrange
    PureModelContextData buildResult = PureModelContextData.newBuilder().build();
    when(pureModelContextService.getPureModelContextData(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any(), anyBoolean(), anyBoolean())).thenReturn(buildResult);

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData("42", "42", "42", "1.0.2",
        true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(eq("42"), eq("42"), eq("42"), eq("1.0.2"), eq(true),
        eq(true));
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
    MultivaluedMap<String, Object> headers = actualPureModelContextData.getHeaders();
    assertEquals(2, headers.size());
    MultivaluedMap<String, String> stringHeaders = actualPureModelContextData.getStringHeaders();
    assertEquals(2, stringHeaders.size());
    assertTrue(headers.containsKey("Cache-Control"));
    assertTrue(headers.containsKey("ETag"));
    assertTrue(stringHeaders.containsKey("Cache-Control"));
    assertTrue(stringHeaders.containsKey("ETag"));
    assertSame(headers, actualPureModelContextData.getMetadata());
  }

  /**
   * Test {@link PureModelContextResource#getPureModelContextData(String, String, String, String, boolean, boolean, Request)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code clientVersion}, {@code transitive}, {@code convertToNewProtocol}, {@code request}.
   * <p>
   * Method under test: {@link PureModelContextResource#getPureModelContextData(String, String, String, String, boolean, boolean, Request)}
   */
  @Test
  @DisplayName("Test getPureModelContextData(String, String, String, String, boolean, boolean, Request) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol', 'request'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "Response PureModelContextResource.getPureModelContextData(String, String, String, String, boolean, boolean, Request)"})
  void testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocolRequest2() {
    // Arrange
    PureModelContextData buildResult = PureModelContextData.newBuilder().build();
    when(pureModelContextService.getPureModelContextData(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any(), anyBoolean(), anyBoolean())).thenReturn(buildResult);

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData("42", "42", "-SNAPSHOT",
        "1.0.2", true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(eq("42"), eq("42"), eq("-SNAPSHOT"), eq("1.0.2"), eq(true),
        eq(true));
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
  }

  /**
   * Test {@link PureModelContextResource#getPureModelContextData(String, String, String, String, boolean, boolean, Request)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code clientVersion}, {@code transitive}, {@code convertToNewProtocol}, {@code request}.
   * <p>
   * Method under test: {@link PureModelContextResource#getPureModelContextData(String, String, String, String, boolean, boolean, Request)}
   */
  @Test
  @DisplayName("Test getPureModelContextData(String, String, String, String, boolean, boolean, Request) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol', 'request'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "Response PureModelContextResource.getPureModelContextData(String, String, String, String, boolean, boolean, Request)"})
  void testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocolRequest3() {
    // Arrange
    PureModelContextData buildResult = PureModelContextData.newBuilder().build();
    when(pureModelContextService.getPureModelContextData(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any(), anyBoolean(), anyBoolean())).thenReturn(buildResult);

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData("42", "42", "42", "vX_X_X",
        true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(eq("42"), eq("42"), eq("42"), eq("vX_X_X"), eq(true),
        eq(true));
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
  }

  /**
   * Test {@link PureModelContextResource#getPureModelContextData(List, String, boolean, boolean, Request)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code convertToNewProtocol}, {@code request}.
   * <p>
   * Method under test: {@link PureModelContextResource#getPureModelContextData(List, String, boolean, boolean, Request)}
   */
  @Test
  @DisplayName("Test getPureModelContextData(List, String, boolean, boolean, Request) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol', 'request'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "Response PureModelContextResource.getPureModelContextData(List, String, boolean, boolean, Request)"})
  void testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocolRequest() {
    // Arrange
    PureModelContextData buildResult = PureModelContextData.newBuilder().build();
    when(pureModelContextService.getPureModelContextData(Mockito.<List<ProjectVersion>>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(buildResult);

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData(new ArrayList<>(), "1.0.2",
        true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(isA(List.class), eq("1.0.2"), eq(true), eq(true));
    StatusType statusInfo = actualPureModelContextData.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    assertTrue(actualPureModelContextData.getEntity() instanceof PureModelContextData);
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
    assertNull(actualPureModelContextData.getLocation());
    assertNull(actualPureModelContextData.getDate());
    assertNull(actualPureModelContextData.getLastModified());
    assertNull(actualPureModelContextData.getLanguage());
    assertNull(actualPureModelContextData.getEntityTag());
    assertNull(actualPureModelContextData.getMediaType());
    assertEquals(-1, actualPureModelContextData.getLength());
    MultivaluedMap<String, Object> headers = actualPureModelContextData.getHeaders();
    assertEquals(1, headers.size());
    MultivaluedMap<String, String> stringHeaders = actualPureModelContextData.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    assertEquals(200, actualPureModelContextData.getStatus());
    assertEquals(Status.OK, statusInfo);
    assertTrue(headers.containsKey("Cache-Control"));
    assertTrue(stringHeaders.containsKey("Cache-Control"));
    assertTrue(actualPureModelContextData.getCookies().isEmpty());
    Set<String> allowedMethods = actualPureModelContextData.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(allowedMethods, actualPureModelContextData.getLinks());
    assertSame(headers, actualPureModelContextData.getMetadata());
  }

  /**
   * Test {@link PureModelContextResource#getPureModelContextData(List, String, boolean, boolean, Request)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code convertToNewProtocol}, {@code request}.
   * <p>
   * Method under test: {@link PureModelContextResource#getPureModelContextData(List, String, boolean, boolean, Request)}
   */
  @Test
  @DisplayName("Test getPureModelContextData(List, String, boolean, boolean, Request) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol', 'request'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "Response PureModelContextResource.getPureModelContextData(List, String, boolean, boolean, Request)"})
  void testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocolRequest2() {
    // Arrange
    PureModelContextData buildResult = PureModelContextData.newBuilder().build();
    when(pureModelContextService.getPureModelContextData(Mockito.<List<ProjectVersion>>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(buildResult);

    ArrayList<ProjectVersion> projectDependencies = new ArrayList<>();
    projectDependencies.add(new ProjectVersion("42", "42", "42"));

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData(projectDependencies, "1.0.2",
        true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(isA(List.class), eq("1.0.2"), eq(true), eq(true));
    StatusType statusInfo = actualPureModelContextData.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    assertTrue(actualPureModelContextData.getEntity() instanceof PureModelContextData);
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
    assertNull(actualPureModelContextData.getLocation());
    assertNull(actualPureModelContextData.getDate());
    assertNull(actualPureModelContextData.getLastModified());
    assertNull(actualPureModelContextData.getLanguage());
    assertNull(actualPureModelContextData.getEntityTag());
    assertNull(actualPureModelContextData.getMediaType());
    assertEquals(-1, actualPureModelContextData.getLength());
    MultivaluedMap<String, Object> headers = actualPureModelContextData.getHeaders();
    assertEquals(1, headers.size());
    MultivaluedMap<String, String> stringHeaders = actualPureModelContextData.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    assertEquals(200, actualPureModelContextData.getStatus());
    assertEquals(Status.OK, statusInfo);
    assertTrue(headers.containsKey("Cache-Control"));
    assertTrue(stringHeaders.containsKey("Cache-Control"));
    assertTrue(actualPureModelContextData.getCookies().isEmpty());
    Set<String> allowedMethods = actualPureModelContextData.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(allowedMethods, actualPureModelContextData.getLinks());
    assertSame(headers, actualPureModelContextData.getMetadata());
  }

  /**
   * Test {@link PureModelContextResource#getPureModelContextData(List, String, boolean, boolean, Request)} with {@code projectDependencies}, {@code clientVersion}, {@code transitive}, {@code convertToNewProtocol}, {@code request}.
   * <p>
   * Method under test: {@link PureModelContextResource#getPureModelContextData(List, String, boolean, boolean, Request)}
   */
  @Test
  @DisplayName("Test getPureModelContextData(List, String, boolean, boolean, Request) with 'projectDependencies', 'clientVersion', 'transitive', 'convertToNewProtocol', 'request'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "Response PureModelContextResource.getPureModelContextData(List, String, boolean, boolean, Request)"})
  void testGetPureModelContextDataWithProjectDependenciesClientVersionTransitiveConvertToNewProtocolRequest3() {
    // Arrange
    PureModelContextData buildResult = PureModelContextData.newBuilder().build();
    when(pureModelContextService.getPureModelContextData(Mockito.<List<ProjectVersion>>any(), Mockito.<String>any(),
        anyBoolean(), anyBoolean())).thenReturn(buildResult);

    ArrayList<ProjectVersion> projectDependencies = new ArrayList<>();
    projectDependencies.add(new ProjectVersion("42", "42", "42"));
    projectDependencies.add(new ProjectVersion("42", "42", "42"));

    // Act
    Response actualPureModelContextData = pureModelContextResource.getPureModelContextData(projectDependencies, "1.0.2",
        true, true, null);

    // Assert
    verify(pureModelContextService).getPureModelContextData(isA(List.class), eq("1.0.2"), eq(true), eq(true));
    StatusType statusInfo = actualPureModelContextData.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    assertTrue(actualPureModelContextData.getEntity() instanceof PureModelContextData);
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
    assertNull(actualPureModelContextData.getLocation());
    assertNull(actualPureModelContextData.getDate());
    assertNull(actualPureModelContextData.getLastModified());
    assertNull(actualPureModelContextData.getLanguage());
    assertNull(actualPureModelContextData.getEntityTag());
    assertNull(actualPureModelContextData.getMediaType());
    assertEquals(-1, actualPureModelContextData.getLength());
    MultivaluedMap<String, Object> headers = actualPureModelContextData.getHeaders();
    assertEquals(1, headers.size());
    MultivaluedMap<String, String> stringHeaders = actualPureModelContextData.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    assertEquals(200, actualPureModelContextData.getStatus());
    assertEquals(Status.OK, statusInfo);
    assertTrue(headers.containsKey("Cache-Control"));
    assertTrue(stringHeaders.containsKey("Cache-Control"));
    assertTrue(actualPureModelContextData.getCookies().isEmpty());
    Set<String> allowedMethods = actualPureModelContextData.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(allowedMethods, actualPureModelContextData.getLinks());
    assertSame(headers, actualPureModelContextData.getMetadata());
  }
}
