package org.finos.legend.depot.store.mongo.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MongoConfigurationDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MongoConfiguration#MongoConfiguration(String, String, boolean)}
   *   <li>{@link MongoConfiguration#getDatabase()}
   *   <li>{@link MongoConfiguration#getUrl()}
   *   <li>{@link MongoConfiguration#isTracingEnabled()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MongoConfiguration.<init>(String, String, boolean)",
      "String MongoConfiguration.getDatabase()", "String MongoConfiguration.getUrl()",
      "boolean MongoConfiguration.isTracingEnabled()"})
  void testGettersAndSetters() {
    // Arrange and Act
    MongoConfiguration actualMongoConfiguration = new MongoConfiguration("Database", "https://example.org/example",
        true);
    String actualDatabase = actualMongoConfiguration.getDatabase();
    String actualUrl = actualMongoConfiguration.getUrl();

    // Assert
    assertEquals("Database", actualDatabase);
    assertEquals("https://example.org/example", actualUrl);
    assertTrue(actualMongoConfiguration.isTracingEnabled());
  }
}
