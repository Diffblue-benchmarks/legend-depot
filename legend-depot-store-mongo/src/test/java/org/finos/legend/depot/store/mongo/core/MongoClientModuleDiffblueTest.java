package org.finos.legend.depot.store.mongo.core;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.MongoClient;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import java.util.ArrayList;
import java.util.List;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.finos.legend.depot.store.StorageConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MongoClientModuleDiffblueTest {
  @InjectMocks
  private MongoClientModule mongoClientModule;

  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   * <p>
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName("Test getMongoConfiguration(List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration() {
    // Arrange
    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    MongoConfiguration mongoConfiguration = new MongoConfiguration("Database", "https://example.org/example", true);

    configurations.add(mongoConfiguration);

    // Act and Assert
    assertSame(mongoConfiguration, mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   * <p>
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName("Test getMongoConfiguration(List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration2() {
    // Arrange
    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    MongoConfiguration mongoConfiguration = new MongoConfiguration("Database", "https://example.org/example", true);

    configurations.add(mongoConfiguration);
    configurations.add(new MongoConfiguration("mongo configuration not provided", "https://example.org/example", true));

    // Act and Assert
    assertSame(mongoConfiguration, mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   * <p>
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName("Test getMongoConfiguration(List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration3() {
    // Arrange
    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    configurations.add(null);
    MongoConfiguration mongoConfiguration = new MongoConfiguration("mongo configuration not provided",
        "https://example.org/example", true);

    configurations.add(mongoConfiguration);

    // Act and Assert
    assertSame(mongoConfiguration, mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName("Test getMongoConfiguration(List); given 'null'; when ArrayList() add 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration_givenNull_whenArrayListAddNull() {
    // Arrange
    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    configurations.add(null);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   * <ul>
   *   <li>Given {@link StorageConfiguration}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName("Test getMongoConfiguration(List); given StorageConfiguration")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration_givenStorageConfiguration() {
    // Arrange
    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    configurations.add(mock(StorageConfiguration.class));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Test {@link MongoClientModule#getMongoConfiguration(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  @DisplayName("Test getMongoConfiguration(List); when ArrayList(); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoConfiguration MongoClientModule.getMongoConfiguration(List)"})
  void testGetMongoConfiguration_whenArrayList_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> mongoClientModule.getMongoConfiguration(new ArrayList<>()));
  }

  /**
   * Test {@link MongoClientModule#getMongoDatabase(ConnectionFactory)}.
   * <p>
   * Method under test: {@link MongoClientModule#getMongoDatabase(ConnectionFactory)}
   */
  @Test
  @DisplayName("Test getMongoDatabase(ConnectionFactory)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoDatabase MongoClientModule.getMongoDatabase(ConnectionFactory)"})
  void testGetMongoDatabase() {
    // Arrange
    MongoNonTracingConnectionFactory connectionFactory = mock(MongoNonTracingConnectionFactory.class);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoDatabaseImpl mongoDatabaseImpl = new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern,
        true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
        mock(OperationExecutor.class));

    when(connectionFactory.getDatabase()).thenReturn(mongoDatabaseImpl);

    // Act
    MongoDatabase actualMongoDatabase = mongoClientModule.getMongoDatabase(connectionFactory);

    // Assert
    verify(connectionFactory).getDatabase();
    assertSame(mongoDatabaseImpl, actualMongoDatabase);
  }

  /**
   * Test {@link MongoClientModule#getMongoClient(ConnectionFactory)}.
   * <ul>
   *   <li>Given {@link MongoClient#MongoClient()}.</li>
   *   <li>Then return {@link MongoClient#MongoClient()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MongoClientModule#getMongoClient(ConnectionFactory)}
   */
  @Test
  @DisplayName("Test getMongoClient(ConnectionFactory); given MongoClient(); then return MongoClient()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MongoClient MongoClientModule.getMongoClient(ConnectionFactory)"})
  void testGetMongoClient_givenMongoClient_thenReturnMongoClient() {
    // Arrange
    MongoNonTracingConnectionFactory connectionFactory = mock(MongoNonTracingConnectionFactory.class);
    MongoClient mongoClient = new MongoClient();
    when(connectionFactory.getClient()).thenReturn(mongoClient);

    // Act
    MongoClient actualMongoClient = mongoClientModule.getMongoClient(connectionFactory);

    // Assert
    verify(connectionFactory).getClient();
    assertSame(mongoClient, actualMongoClient);
  }
}
