package org.finos.legend.depot.server.resources.pure.model.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.finos.legend.depot.services.api.pure.model.context.PureModelContextService;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PureModelContextResourceDiffblueTest {
  /**
   * Test {@link PureModelContextResource#getPureModelContextData(String, String, String, String,
   * boolean, boolean, Request)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}, {@code request}.
   *
   * <p>Method under test: {@link PureModelContextResource#getPureModelContextData(String, String,
   * String, String, boolean, boolean, Request)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean, Request) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol', 'request'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Response PureModelContextResource.getPureModelContextData(String, String, String, String, boolean, boolean, Request)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocolRequest() {
    // Arrange
    PureModelContextService service = mock(PureModelContextService.class);
    when(service.getPureModelContextData(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            anyBoolean(),
            anyBoolean()))
        .thenReturn(PureModelContextData.newBuilder().build());

    // Act
    Response actualPureModelContextData =
        new PureModelContextResource(service)
            .getPureModelContextData("42", "42", "42", "1.0.2", true, true, null);

    // Assert
    verify(service).getPureModelContextData("42", "42", "42", "1.0.2", true, true);
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
   * Test {@link PureModelContextResource#getPureModelContextData(String, String, String, String,
   * boolean, boolean, Request)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}, {@code request}.
   *
   * <p>Method under test: {@link PureModelContextResource#getPureModelContextData(String, String,
   * String, String, boolean, boolean, Request)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean, Request) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol', 'request'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Response PureModelContextResource.getPureModelContextData(String, String, String, String, boolean, boolean, Request)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocolRequest2() {
    // Arrange
    PureModelContextService service = mock(PureModelContextService.class);
    when(service.getPureModelContextData(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            anyBoolean(),
            anyBoolean()))
        .thenReturn(PureModelContextData.newBuilder().build());

    // Act
    Response actualPureModelContextData =
        new PureModelContextResource(service)
            .getPureModelContextData("42", "42", "-SNAPSHOT", "1.0.2", true, true, null);

    // Assert
    verify(service).getPureModelContextData("42", "42", "-SNAPSHOT", "1.0.2", true, true);
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
  }

  /**
   * Test {@link PureModelContextResource#getPureModelContextData(String, String, String, String,
   * boolean, boolean, Request)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * clientVersion}, {@code transitive}, {@code convertToNewProtocol}, {@code request}.
   *
   * <p>Method under test: {@link PureModelContextResource#getPureModelContextData(String, String,
   * String, String, boolean, boolean, Request)}
   */
  @Test
  @DisplayName(
      "Test getPureModelContextData(String, String, String, String, boolean, boolean, Request) with 'groupId', 'artifactId', 'versionId', 'clientVersion', 'transitive', 'convertToNewProtocol', 'request'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Response PureModelContextResource.getPureModelContextData(String, String, String, String, boolean, boolean, Request)"
  })
  void
      testGetPureModelContextDataWithGroupIdArtifactIdVersionIdClientVersionTransitiveConvertToNewProtocolRequest3() {
    // Arrange
    PureModelContextService service = mock(PureModelContextService.class);
    when(service.getPureModelContextData(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            anyBoolean(),
            anyBoolean()))
        .thenReturn(PureModelContextData.newBuilder().build());

    // Act
    Response actualPureModelContextData =
        new PureModelContextResource(service)
            .getPureModelContextData("42", "42", "42", "vX_X_X", true, true, null);

    // Assert
    verify(service).getPureModelContextData("42", "42", "42", "vX_X_X", true, true);
    assertTrue(actualPureModelContextData instanceof OutboundJaxrsResponse);
  }
}
