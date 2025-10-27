package org.finos.legend.depot.store.mongo.guice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManageMongoStoreModuleDiffblueTest {
  @InjectMocks
  private ManageMongoStoreModule manageMongoStoreModule;

  /**
   * Method under test:
   * {@link ManageMongoStoreModule#buildMongoAdminStore(MongoDatabase)}
   */
  @Test
  void testBuildMongoAdminStore() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act and Assert
    assertEquals("Name",
        manageMongoStoreModule
            .buildMongoAdminStore(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
                new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)))
            .getName());
  }
}
