package org.finos.legend.depot.store.mongo.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.model.IndexModel;
import java.util.List;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ManageCoreDataStoreMongoModuleDiffblueTest {
  /**
   * Test {@link ManageCoreDataStoreMongoModule#registerIndexes(MongoAdminStore)}.
   *
   * <ul>
   *   <li>Then calls {@link MongoAdminStore#registerIndexes(String, List)}.
   * </ul>
   *
   * <p>Method under test: {@link ManageCoreDataStoreMongoModule#registerIndexes(MongoAdminStore)}
   */
  @Test
  @DisplayName("Test registerIndexes(MongoAdminStore); then calls registerIndexes(String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ManageCoreDataStoreMongoModule.registerIndexes(MongoAdminStore)"})
  void testRegisterIndexes_thenCallsRegisterIndexes() {
    // Arrange
    ManageCoreDataStoreMongoModule manageCoreDataStoreMongoModule =
        new ManageCoreDataStoreMongoModule();

    MongoAdminStore adminStore = mock(MongoAdminStore.class);
    doNothing()
        .when(adminStore)
        .registerIndexes(Mockito.<String>any(), Mockito.<List<IndexModel>>any());

    // Act
    boolean actualRegisterIndexesResult =
        manageCoreDataStoreMongoModule.registerIndexes(adminStore);

    // Assert
    verify(adminStore, atLeast(1))
        .registerIndexes(Mockito.<String>any(), Mockito.<List<IndexModel>>any());
    assertTrue(actualRegisterIndexesResult);
  }

  /**
   * Test {@link ManageCoreDataStoreMongoModule#registerIndexes(MongoAdminStore)}.
   *
   * <ul>
   *   <li>When {@link MongoAdminStore#MongoAdminStore(MongoDatabase)} with mongoDatabase is {@code
   *       null}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ManageCoreDataStoreMongoModule#registerIndexes(MongoAdminStore)}
   */
  @Test
  @DisplayName(
      "Test registerIndexes(MongoAdminStore); when MongoAdminStore(MongoDatabase) with mongoDatabase is 'null'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ManageCoreDataStoreMongoModule.registerIndexes(MongoAdminStore)"})
  void testRegisterIndexes_whenMongoAdminStoreWithMongoDatabaseIsNull_thenReturnTrue() {
    // Arrange
    ManageCoreDataStoreMongoModule manageCoreDataStoreMongoModule =
        new ManageCoreDataStoreMongoModule();

    // Act
    boolean actualRegisterIndexesResult =
        manageCoreDataStoreMongoModule.registerIndexes(new MongoAdminStore(null));

    // Assert
    assertTrue(actualRegisterIndexesResult);
  }
}
