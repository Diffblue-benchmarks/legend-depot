package org.finos.legend.depot.store.mongo.core;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.finos.legend.depot.store.StorageConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MongoClientModuleDiffblueTest {
  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   *
   * <p>Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName("Test getMongoConfiguration(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration() {
    // Arrange
    MongoClientModule mongoClientModule = new MongoClientModule();

    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    MongoConfiguration mongoConfiguration =
        new MongoConfiguration("Database", "https://example.org/example", true);
    configurations.add(mongoConfiguration);

    // Act and Assert
    assertSame(mongoConfiguration, mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   *
   * <p>Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName("Test getMongoConfiguration(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration2() {
    // Arrange
    MongoClientModule mongoClientModule = new MongoClientModule();

    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    MongoConfiguration mongoConfiguration =
        new MongoConfiguration("Database", "https://example.org/example", true);
    configurations.add(mongoConfiguration);
    MongoConfiguration mongoConfiguration2 =
        new MongoConfiguration(
            "mongo configuration not provided", "https://example.org/example", true);
    configurations.add(mongoConfiguration2);

    // Act and Assert
    assertSame(mongoConfiguration, mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   *
   * <p>Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName("Test getMongoConfiguration(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration3() {
    // Arrange
    MongoClientModule mongoClientModule = new MongoClientModule();

    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    configurations.add(null);
    MongoConfiguration mongoConfiguration =
        new MongoConfiguration(
            "mongo configuration not provided", "https://example.org/example", true);
    configurations.add(mongoConfiguration);

    // Act and Assert
    assertSame(mongoConfiguration, mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName(
      "Test getMongoConfiguration(List); given 'null'; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration_givenNull_thenThrowIllegalArgumentException() {
    // Arrange
    MongoClientModule mongoClientModule = new MongoClientModule();

    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    configurations.add(null);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   *
   * <ul>
   *   <li>Given {@link StorageConfiguration}.
   * </ul>
   *
   * <p>Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName("Test getMongoConfiguration(List); given StorageConfiguration")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration_givenStorageConfiguration() {
    // Arrange
    MongoClientModule mongoClientModule = new MongoClientModule();

    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    configurations.add(mock(StorageConfiguration.class));
    MongoConfiguration mongoConfiguration =
        new MongoConfiguration(
            "mongo configuration not provided", "https://example.org/example", true);
    configurations.add(mongoConfiguration);

    // Act and Assert
    assertSame(mongoConfiguration, mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName(
      "Test getMongoConfiguration(List); when ArrayList(); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration_whenArrayList_thenThrowIllegalArgumentException() {
    // Arrange
    MongoClientModule mongoClientModule = new MongoClientModule();

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> mongoClientModule.getMongoConfiguration(new ArrayList<>()));
  }

  /**
   * Test {@link MongoClientModule#getMongoDatabase(ConnectionFactory)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MongoClientModule#getMongoDatabase(ConnectionFactory)}
   */
  @Test
  @DisplayName("Test getMongoDatabase(ConnectionFactory); given 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoDatabase MongoClientModule.getMongoDatabase(ConnectionFactory)"})
  void testGetMongoDatabase_givenNull_thenReturnNull() {
    // Arrange
    MongoClientModule mongoClientModule = new MongoClientModule();

    MongoNonTracingConnectionFactory connectionFactory =
        mock(MongoNonTracingConnectionFactory.class);
    when(connectionFactory.getDatabase()).thenReturn(null);

    // Act
    MongoDatabase actualMongoDatabase = mongoClientModule.getMongoDatabase(connectionFactory);

    // Assert
    verify(connectionFactory).getDatabase();
    assertNull(actualMongoDatabase);
  }
}
