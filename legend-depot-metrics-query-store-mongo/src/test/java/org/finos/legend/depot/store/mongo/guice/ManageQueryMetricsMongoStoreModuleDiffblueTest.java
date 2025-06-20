package org.finos.legend.depot.store.mongo.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ManageQueryMetricsMongoStoreModuleDiffblueTest {
  /**
   * Test {@link ManageQueryMetricsMongoStoreModule#registerIndexes(MongoAdminStore)}.
   * <ul>
   *   <li>When {@link WriteConcern#WriteConcern(int)} with w is one.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageQueryMetricsMongoStoreModule#registerIndexes(MongoAdminStore)}
   */
  @Test
  @DisplayName("Test registerIndexes(MongoAdminStore); when WriteConcern(int) with w is one; then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ManageQueryMetricsMongoStoreModule.registerIndexes(MongoAdminStore)"})
  void testRegisterIndexes_whenWriteConcernWithWIsOne_thenReturnTrue() {
    // Arrange
    ManageQueryMetricsMongoStoreModule manageQueryMetricsMongoStoreModule = new ManageQueryMetricsMongoStoreModule();
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act and Assert
    assertTrue(manageQueryMetricsMongoStoreModule.registerIndexes(
        new MongoAdminStore(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)))));
  }
}
