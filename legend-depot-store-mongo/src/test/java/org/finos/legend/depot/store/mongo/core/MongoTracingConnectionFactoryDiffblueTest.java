package org.finos.legend.depot.store.mongo.core;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import io.opentracing.Tracer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MongoTracingConnectionFactoryDiffblueTest {
  /**
   * Test {@link MongoTracingConnectionFactory#MongoTracingConnectionFactory(String,
   * MongoConfiguration, Tracer)}.
   *
   * <p>Method under test: {@link
   * MongoTracingConnectionFactory#MongoTracingConnectionFactory(String, MongoConfiguration,
   * Tracer)}
   */
  @Test
  @DisplayName("Test new MongoTracingConnectionFactory(String, MongoConfiguration, Tracer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MongoTracingConnectionFactory.<init>(String, MongoConfiguration, Tracer)"
  })
  void testNewMongoTracingConnectionFactory() {
    // Arrange
    MongoConfiguration mongoConfiguration =
        new MongoConfiguration("", "https://example.org/example", true);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new MongoTracingConnectionFactory(
                "Application Name", mongoConfiguration, mock(Tracer.class)));
  }

  /**
   * Test {@link MongoTracingConnectionFactory#MongoTracingConnectionFactory(String,
   * MongoConfiguration, Tracer)}.
   *
   * <p>Method under test: {@link
   * MongoTracingConnectionFactory#MongoTracingConnectionFactory(String, MongoConfiguration,
   * Tracer)}
   */
  @Test
  @DisplayName("Test new MongoTracingConnectionFactory(String, MongoConfiguration, Tracer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MongoTracingConnectionFactory.<init>(String, MongoConfiguration, Tracer)"
  })
  void testNewMongoTracingConnectionFactory2() {
    // Arrange
    MongoConfiguration mongoConfiguration = new MongoConfiguration("Database", "", true);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new MongoTracingConnectionFactory(
                "Application Name", mongoConfiguration, mock(Tracer.class)));
  }

  /**
   * Test {@link MongoTracingConnectionFactory#MongoTracingConnectionFactory(String,
   * MongoConfiguration, Tracer)}.
   *
   * <ul>
   *   <li>When {@link MongoConfiguration}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MongoTracingConnectionFactory#MongoTracingConnectionFactory(String, MongoConfiguration,
   * Tracer)}
   */
  @Test
  @DisplayName(
      "Test new MongoTracingConnectionFactory(String, MongoConfiguration, Tracer); when MongoConfiguration")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MongoTracingConnectionFactory.<init>(String, MongoConfiguration, Tracer)"
  })
  void testNewMongoTracingConnectionFactory_whenMongoConfiguration() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new MongoTracingConnectionFactory(
                "Application Name", mock(MongoConfiguration.class), mock(Tracer.class)));
  }

  /**
   * Test {@link MongoTracingConnectionFactory#MongoTracingConnectionFactory(String,
   * MongoConfiguration, Tracer)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MongoTracingConnectionFactory#MongoTracingConnectionFactory(String, MongoConfiguration,
   * Tracer)}
   */
  @Test
  @DisplayName(
      "Test new MongoTracingConnectionFactory(String, MongoConfiguration, Tracer); when 'null'; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MongoTracingConnectionFactory.<init>(String, MongoConfiguration, Tracer)"
  })
  void testNewMongoTracingConnectionFactory_whenNull_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new MongoTracingConnectionFactory("Application Name", null, mock(Tracer.class)));
  }
}
