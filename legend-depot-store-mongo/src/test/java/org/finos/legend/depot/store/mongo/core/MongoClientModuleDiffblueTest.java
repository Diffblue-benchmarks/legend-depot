package org.finos.legend.depot.store.mongo.core;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MongoClientModuleDiffblueTest {
  @InjectMocks
  private MongoClientModule mongoClientModule;

  /**
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  void testGetMongoConfiguration() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> mongoClientModule.getMongoConfiguration(new ArrayList<>()));
  }

  /**
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  void testGetMongoConfiguration2() {
    // Arrange
    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    MongoConfiguration mongoConfiguration = new MongoConfiguration("mongo configuration not provided",
        "https://example.org/example", true);

    configurations.add(mongoConfiguration);

    // Act and Assert
    assertSame(mongoConfiguration, mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  void testGetMongoConfiguration3() {
    // Arrange
    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    MongoConfiguration mongoConfiguration = new MongoConfiguration("Database", "https://example.org/example", true);

    configurations.add(mongoConfiguration);
    configurations.add(new MongoConfiguration("mongo configuration not provided", "https://example.org/example", true));

    // Act and Assert
    assertSame(mongoConfiguration, mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  void testGetMongoConfiguration4() {
    // Arrange
    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    configurations.add(null);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  void testGetMongoConfiguration5() {
    // Arrange
    ArrayList<StorageConfiguration> configurations = new ArrayList<>();
    configurations.add(mock(StorageConfiguration.class));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> mongoClientModule.getMongoConfiguration(configurations));
  }

  /**
   * Method under test: {@link MongoClientModule#getMongoConfiguration(List)}
   */
  @Test
  void testGetMongoConfiguration6() {
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
   * Method under test:
   * {@link MongoClientModule#getMongoDatabase(ConnectionFactory)}
   */
  @Test
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
   * Method under test:
   * {@link MongoClientModule#getMongoClient(ConnectionFactory)}
   */
  @Test
  void testGetMongoClient() {
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
