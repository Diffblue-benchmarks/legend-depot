package org.finos.legend.depot.services.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

class EtagBuilderDiffblueTest {
  /**
   * Method under test: {@link EtagBuilder#create()}
   */
  @Test
  void testCreate() {
    // Arrange, Act and Assert
    assertEquals("", EtagBuilder.create().build());
  }

  /**
   * Method under test: {@link EtagBuilder#withGAV(String, String, String)}
   */
  @Test
  void testWithGAV() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();

    // Act
    EtagBuilder actualWithGAVResult = createResult.withGAV("42", "42", "42");

    // Assert
    assertEquals("424242", actualWithGAVResult.build());
    assertEquals("424242", createResult.build());
    assertSame(createResult, actualWithGAVResult);
  }

  /**
   * Method under test: {@link EtagBuilder#withGAV(String, String, String)}
   */
  @Test
  void testWithGAV2() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();

    // Act
    EtagBuilder actualWithGAVResult = createResult.withGAV("42", "42", "-SNAPSHOT");

    // Assert
    assertNull(actualWithGAVResult.build());
    assertNull(createResult.build());
    assertSame(createResult, actualWithGAVResult);
  }

  /**
   * Method under test: {@link EtagBuilder#withProtocolVersion(String)}
   */
  @Test
  void testWithProtocolVersion() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();

    // Act
    EtagBuilder actualWithProtocolVersionResult = createResult.withProtocolVersion("1.0.2");

    // Assert
    assertEquals("1.0.2", actualWithProtocolVersionResult.build());
    assertEquals("1.0.2", createResult.build());
    assertSame(createResult, actualWithProtocolVersionResult);
  }

  /**
   * Method under test: {@link EtagBuilder#withProtocolVersion(String)}
   */
  @Test
  void testWithProtocolVersion2() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();

    // Act
    EtagBuilder actualWithProtocolVersionResult = createResult.withProtocolVersion(null);

    // Assert
    assertNull(actualWithProtocolVersionResult.build());
    assertNull(createResult.build());
    assertSame(createResult, actualWithProtocolVersionResult);
  }

  /**
   * Method under test: {@link EtagBuilder#withProtocolVersion(String)}
   */
  @Test
  void testWithProtocolVersion3() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();

    // Act
    EtagBuilder actualWithProtocolVersionResult = createResult.withProtocolVersion("vX_X_X");

    // Assert
    assertNull(actualWithProtocolVersionResult.build());
    assertNull(createResult.build());
    assertSame(createResult, actualWithProtocolVersionResult);
  }

  /**
   * Method under test: {@link EtagBuilder#build()}
   */
  @Test
  void testBuild() {
    // Arrange, Act and Assert
    assertEquals("", EtagBuilder.create().build());
  }

  /**
   * Method under test: {@link EtagBuilder#build()}
   */
  @Test
  void testBuild2() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();
    createResult.withGAV("42", "42", "42");

    // Act and Assert
    assertEquals("424242", createResult.build());
  }

  /**
   * Method under test: {@link EtagBuilder#build()}
   */
  @Test
  void testBuild3() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();
    createResult.withProtocolVersion("vX_X_X");

    // Act and Assert
    assertNull(createResult.build());
  }
}
