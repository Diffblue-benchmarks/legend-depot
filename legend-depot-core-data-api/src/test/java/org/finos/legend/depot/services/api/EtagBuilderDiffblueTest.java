package org.finos.legend.depot.services.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EtagBuilderDiffblueTest {
  /**
   * Test {@link EtagBuilder#create()}.
   * <p>
   * Method under test: {@link EtagBuilder#create()}
   */
  @Test
  @DisplayName("Test create()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"EtagBuilder EtagBuilder.create()"})
  void testCreate() {
    // Arrange, Act and Assert
    assertEquals("", EtagBuilder.create().build());
  }

  /**
   * Test {@link EtagBuilder#withGAV(String, String, String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then create build is {@code 424242}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EtagBuilder#withGAV(String, String, String)}
   */
  @Test
  @DisplayName("Test withGAV(String, String, String); when '42'; then create build is '424242'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"EtagBuilder EtagBuilder.withGAV(String, String, String)"})
  void testWithGAV_when42_thenCreateBuildIs424242() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();

    // Act
    EtagBuilder actualWithGAVResult = createResult.withGAV("42", "42", "42");

    // Assert
    assertEquals("424242", createResult.build());
    assertSame(createResult, actualWithGAVResult);
  }

  /**
   * Test {@link EtagBuilder#withGAV(String, String, String)}.
   * <ul>
   *   <li>When {@code -SNAPSHOT}.</li>
   *   <li>Then create build is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EtagBuilder#withGAV(String, String, String)}
   */
  @Test
  @DisplayName("Test withGAV(String, String, String); when '-SNAPSHOT'; then create build is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"EtagBuilder EtagBuilder.withGAV(String, String, String)"})
  void testWithGAV_whenSnapshot_thenCreateBuildIsNull() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();

    // Act
    EtagBuilder actualWithGAVResult = createResult.withGAV("42", "42", "-SNAPSHOT");

    // Assert
    assertNull(createResult.build());
    assertSame(createResult, actualWithGAVResult);
  }

  /**
   * Test {@link EtagBuilder#withProtocolVersion(String)}.
   * <ul>
   *   <li>When {@code 1.0.2}.</li>
   *   <li>Then create build is {@code 1.0.2}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EtagBuilder#withProtocolVersion(String)}
   */
  @Test
  @DisplayName("Test withProtocolVersion(String); when '1.0.2'; then create build is '1.0.2'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"EtagBuilder EtagBuilder.withProtocolVersion(String)"})
  void testWithProtocolVersion_when102_thenCreateBuildIs102() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();

    // Act
    EtagBuilder actualWithProtocolVersionResult = createResult.withProtocolVersion("1.0.2");

    // Assert
    assertEquals("1.0.2", createResult.build());
    assertSame(createResult, actualWithProtocolVersionResult);
  }

  /**
   * Test {@link EtagBuilder#withProtocolVersion(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then create build is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EtagBuilder#withProtocolVersion(String)}
   */
  @Test
  @DisplayName("Test withProtocolVersion(String); when 'null'; then create build is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"EtagBuilder EtagBuilder.withProtocolVersion(String)"})
  void testWithProtocolVersion_whenNull_thenCreateBuildIsNull() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();

    // Act
    EtagBuilder actualWithProtocolVersionResult = createResult.withProtocolVersion(null);

    // Assert
    assertNull(createResult.build());
    assertSame(createResult, actualWithProtocolVersionResult);
  }

  /**
   * Test {@link EtagBuilder#withProtocolVersion(String)}.
   * <ul>
   *   <li>When {@code vX_X_X}.</li>
   *   <li>Then create build is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EtagBuilder#withProtocolVersion(String)}
   */
  @Test
  @DisplayName("Test withProtocolVersion(String); when 'vX_X_X'; then create build is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"EtagBuilder EtagBuilder.withProtocolVersion(String)"})
  void testWithProtocolVersion_whenVXXX_thenCreateBuildIsNull() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();

    // Act
    EtagBuilder actualWithProtocolVersionResult = createResult.withProtocolVersion("vX_X_X");

    // Assert
    assertNull(createResult.build());
    assertSame(createResult, actualWithProtocolVersionResult);
  }

  /**
   * Test {@link EtagBuilder#build()}.
   * <ul>
   *   <li>Given create withProtocolVersion {@code vX_X_X}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EtagBuilder#build()}
   */
  @Test
  @DisplayName("Test build(); given create withProtocolVersion 'vX_X_X'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String EtagBuilder.build()"})
  void testBuild_givenCreateWithProtocolVersionVXXX_thenReturnNull() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();
    createResult.withProtocolVersion("vX_X_X");

    // Act and Assert
    assertNull(createResult.build());
  }

  /**
   * Test {@link EtagBuilder#build()}.
   * <ul>
   *   <li>Given create.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link EtagBuilder#build()}
   */
  @Test
  @DisplayName("Test build(); given create; then return empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String EtagBuilder.build()"})
  void testBuild_givenCreate_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", EtagBuilder.create().build());
  }

  /**
   * Test {@link EtagBuilder#build()}.
   * <ul>
   *   <li>Then return {@code 424242}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EtagBuilder#build()}
   */
  @Test
  @DisplayName("Test build(); then return '424242'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String EtagBuilder.build()"})
  void testBuild_thenReturn424242() {
    // Arrange
    EtagBuilder createResult = EtagBuilder.create();
    createResult.withGAV("42", "42", "42");

    // Act and Assert
    assertEquals("424242", createResult.build());
  }
}
