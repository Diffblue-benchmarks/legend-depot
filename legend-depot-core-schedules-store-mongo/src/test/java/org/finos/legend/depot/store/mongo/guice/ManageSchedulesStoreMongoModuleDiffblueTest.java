package org.finos.legend.depot.store.mongo.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import com.mongodb.client.model.IndexModel;
import java.util.List;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ManageSchedulesStoreMongoModuleDiffblueTest {
  /**
   * Test {@link ManageSchedulesStoreMongoModule#registerIndexes(MongoAdminStore)}.
   * <p>
   * Method under test: {@link ManageSchedulesStoreMongoModule#registerIndexes(MongoAdminStore)}
   */
  @Test
  @DisplayName("Test registerIndexes(MongoAdminStore)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ManageSchedulesStoreMongoModule.registerIndexes(MongoAdminStore)"})
  void testRegisterIndexes() {
    // Arrange
    ManageSchedulesStoreMongoModule manageSchedulesStoreMongoModule = new ManageSchedulesStoreMongoModule();
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act and Assert
    assertTrue(manageSchedulesStoreMongoModule.registerIndexes(
        new MongoAdminStore(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)))));
  }

  /**
   * Test {@link ManageSchedulesStoreMongoModule#registerIndexes(MongoAdminStore)}.
   * <ul>
   *   <li>Then calls {@link MongoAdminStore#registerIndexes(String, List)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageSchedulesStoreMongoModule#registerIndexes(MongoAdminStore)}
   */
  @Test
  @DisplayName("Test registerIndexes(MongoAdminStore); then calls registerIndexes(String, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ManageSchedulesStoreMongoModule.registerIndexes(MongoAdminStore)"})
  void testRegisterIndexes_thenCallsRegisterIndexes() {
    // Arrange
    ManageSchedulesStoreMongoModule manageSchedulesStoreMongoModule = new ManageSchedulesStoreMongoModule();
    MongoAdminStore adminStore = mock(MongoAdminStore.class);
    doNothing().when(adminStore).registerIndexes(Mockito.<String>any(), Mockito.<List<IndexModel>>any());

    // Act
    boolean actualRegisterIndexesResult = manageSchedulesStoreMongoModule.registerIndexes(adminStore);

    // Assert
    verify(adminStore, atLeast(1)).registerIndexes(Mockito.<String>any(), Mockito.<List<IndexModel>>any());
    assertTrue(actualRegisterIndexesResult);
  }
}
