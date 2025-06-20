package org.finos.legend.depot.store.mongo.guice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ManageMongoStoreModuleDiffblueTest {
  /**
   * Test {@link ManageMongoStoreModule#buildMongoAdminStore(MongoDatabase)}.
   * <ul>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageMongoStoreModule#buildMongoAdminStore(MongoDatabase)}
   */
  @Test
  @DisplayName("Test buildMongoAdminStore(MongoDatabase); then return 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.finos.legend.depot.store.mongo.admin.MongoAdminStore ManageMongoStoreModule.buildMongoAdminStore(MongoDatabase)"})
  void testBuildMongoAdminStore_thenReturnName() {
    // Arrange
    ManageMongoStoreModule manageMongoStoreModule = new ManageMongoStoreModule();
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
