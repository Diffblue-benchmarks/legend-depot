package org.finos.legend.depot.store.mongo.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MongoConfigurationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MongoConfiguration#MongoConfiguration(String, String, boolean)}
   *   <li>{@link MongoConfiguration#getDatabase()}
   *   <li>{@link MongoConfiguration#getUrl()}
   *   <li>{@link MongoConfiguration#isTracingEnabled()}
   * </ul>
   */
  @Test
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
