package org.finos.legend.depot.store.mongo.core;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MongoNonTracingConnectionFactoryDiffblueTest {
  /**
   * Test {@link MongoNonTracingConnectionFactory#MongoNonTracingConnectionFactory(String,
   * MongoConfiguration)}.
   *
   * <p>Method under test: {@link
   * MongoNonTracingConnectionFactory#MongoNonTracingConnectionFactory(String, MongoConfiguration)}
   */
  @Test
  @DisplayName("Test new MongoNonTracingConnectionFactory(String, MongoConfiguration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MongoNonTracingConnectionFactory.<init>(String, MongoConfiguration)"})
  void testNewMongoNonTracingConnectionFactory() {
    // Arrange
    MongoConfiguration mongoConfiguration = new MongoConfiguration("", "", true);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new MongoNonTracingConnectionFactory("Application Name", mongoConfiguration));
  }

  /**
   * Test {@link MongoNonTracingConnectionFactory#MongoNonTracingConnectionFactory(String,
   * MongoConfiguration)}.
   *
   * <p>Method under test: {@link
   * MongoNonTracingConnectionFactory#MongoNonTracingConnectionFactory(String, MongoConfiguration)}
   */
  @Test
  @DisplayName("Test new MongoNonTracingConnectionFactory(String, MongoConfiguration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MongoNonTracingConnectionFactory.<init>(String, MongoConfiguration)"})
  void testNewMongoNonTracingConnectionFactory2() {
    // Arrange
    MongoConfiguration mongoConfiguration = new MongoConfiguration(null, "", true);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new MongoNonTracingConnectionFactory("Application Name", mongoConfiguration));
  }

  /**
   * Test {@link MongoNonTracingConnectionFactory#MongoNonTracingConnectionFactory(String,
   * MongoConfiguration)}.
   *
   * <p>Method under test: {@link
   * MongoNonTracingConnectionFactory#MongoNonTracingConnectionFactory(String, MongoConfiguration)}
   */
  @Test
  @DisplayName("Test new MongoNonTracingConnectionFactory(String, MongoConfiguration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MongoNonTracingConnectionFactory.<init>(String, MongoConfiguration)"})
  void testNewMongoNonTracingConnectionFactory3() {
    // Arrange
    MongoConfiguration mongoConfiguration = new MongoConfiguration("Database", "", true);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new MongoNonTracingConnectionFactory("Application Name", mongoConfiguration));
  }

  /**
   * Test {@link MongoNonTracingConnectionFactory#MongoNonTracingConnectionFactory(String,
   * MongoConfiguration)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MongoNonTracingConnectionFactory#MongoNonTracingConnectionFactory(String, MongoConfiguration)}
   */
  @Test
  @DisplayName("Test new MongoNonTracingConnectionFactory(String, MongoConfiguration); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MongoNonTracingConnectionFactory.<init>(String, MongoConfiguration)"})
  void testNewMongoNonTracingConnectionFactory_whenNull() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new MongoNonTracingConnectionFactory("Application Name", null));
  }
}
