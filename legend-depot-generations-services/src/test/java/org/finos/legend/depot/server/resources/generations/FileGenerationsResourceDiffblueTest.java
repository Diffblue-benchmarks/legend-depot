package org.finos.legend.depot.server.resources.generations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Optional;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.finos.legend.depot.services.api.generations.FileGenerationsService;
import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.glassfish.jersey.server.ContainerRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FileGenerationsResourceDiffblueTest {
  /**
   * Test {@link FileGenerationsResource#getFileGenerationContentByFilePath(String, String, String,
   * String, Request)}.
   *
   * <ul>
   *   <li>Then return Headers size is two.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationsResource#getFileGenerationContentByFilePath(String,
   * String, String, String, Request)}
   */
  @Test
  @DisplayName(
      "Test getFileGenerationContentByFilePath(String, String, String, String, Request); then return Headers size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Response FileGenerationsResource.getFileGenerationContentByFilePath(String, String, String, String, Request)"
  })
  void testGetFileGenerationContentByFilePath_thenReturnHeadersSizeIsTwo() {
    // Arrange
    FileGenerationsService generationsService = mock(FileGenerationsService.class);
    Optional<String> ofResult = Optional.of("42");
    when(generationsService.getFileGenerationContentByFilePath(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any()))
        .thenReturn(ofResult);
    FileGenerationsResource fileGenerationsResource =
        new FileGenerationsResource(generationsService);
    URI baseUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URI requestUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    ContainerRequest request =
        new ContainerRequest(
            baseUri, requestUri, "https://example.org/example", null, new MapPropertiesDelegate());

    // Act
    Response actualFileGenerationContentByFilePath =
        fileGenerationsResource.getFileGenerationContentByFilePath(
            "42", "42", "42", "/directory/foo.txt", request);

    // Assert
    verify(generationsService)
        .getFileGenerationContentByFilePath("42", "42", "42", "/directory/foo.txt");
    assertTrue(actualFileGenerationContentByFilePath instanceof OutboundJaxrsResponse);
    MultivaluedMap<String, Object> headers = actualFileGenerationContentByFilePath.getHeaders();
    assertEquals(2, headers.size());
    MultivaluedMap<String, String> stringHeaders =
        actualFileGenerationContentByFilePath.getStringHeaders();
    assertEquals(2, stringHeaders.size());
    assertTrue(headers.containsKey("Cache-Control"));
    assertTrue(headers.containsKey("ETag"));
    assertTrue(stringHeaders.containsKey("Cache-Control"));
    assertTrue(stringHeaders.containsKey("ETag"));
    assertSame(headers, actualFileGenerationContentByFilePath.getMetadata());
  }

  /**
   * Test {@link FileGenerationsResource#getFileGenerationContentByFilePath(String, String, String,
   * String, Request)}.
   *
   * <ul>
   *   <li>When {@code -SNAPSHOT}.
   *   <li>Then return EntityTag is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationsResource#getFileGenerationContentByFilePath(String,
   * String, String, String, Request)}
   */
  @Test
  @DisplayName(
      "Test getFileGenerationContentByFilePath(String, String, String, String, Request); when '-SNAPSHOT'; then return EntityTag is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Response FileGenerationsResource.getFileGenerationContentByFilePath(String, String, String, String, Request)"
  })
  void testGetFileGenerationContentByFilePath_whenSnapshot_thenReturnEntityTagIsNull() {
    // Arrange
    FileGenerationsService generationsService = mock(FileGenerationsService.class);
    Optional<String> ofResult = Optional.of("42");
    when(generationsService.getFileGenerationContentByFilePath(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any()))
        .thenReturn(ofResult);
    FileGenerationsResource fileGenerationsResource =
        new FileGenerationsResource(generationsService);
    URI baseUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URI requestUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    ContainerRequest request =
        new ContainerRequest(
            baseUri, requestUri, "https://example.org/example", null, new MapPropertiesDelegate());

    // Act
    Response actualFileGenerationContentByFilePath =
        fileGenerationsResource.getFileGenerationContentByFilePath(
            "42", "42", "-SNAPSHOT", "/directory/foo.txt", request);

    // Assert
    verify(generationsService)
        .getFileGenerationContentByFilePath("42", "42", "-SNAPSHOT", "/directory/foo.txt");
    assertTrue(actualFileGenerationContentByFilePath instanceof OutboundJaxrsResponse);
    assertNull(actualFileGenerationContentByFilePath.getEntityTag());
    OutboundMessageContext context =
        ((OutboundJaxrsResponse) actualFileGenerationContentByFilePath).getContext();
    assertNull(context.getEntityTag());
    MultivaluedMap<String, Object> headers = actualFileGenerationContentByFilePath.getHeaders();
    assertEquals(1, headers.size());
    assertEquals(1, headers.get("Cache-Control").size());
    MultivaluedMap<String, String> stringHeaders =
        actualFileGenerationContentByFilePath.getStringHeaders();
    assertEquals(1, stringHeaders.size());
    assertEquals(1, stringHeaders.get("Cache-Control").size());
    assertEquals(1, context.getAcceptableLanguages().size());
    MultivaluedMap<String, String> stringHeaders2 = context.getStringHeaders();
    assertEquals(1, stringHeaders2.size());
    assertTrue(stringHeaders2.containsKey("Cache-Control"));
    assertSame(headers, actualFileGenerationContentByFilePath.getMetadata());
    assertSame(headers, context.getHeaders());
  }
}
