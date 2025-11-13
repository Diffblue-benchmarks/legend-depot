package org.finos.legend.depot.store.mongo.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
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

class ManageGenerationsStoreMongoModuleDiffblueTest {
  /**
   * Test {@link ManageGenerationsStoreMongoModule#registerGenerationsIndexes(MongoAdminStore)}.
   *
   * <ul>
   *   <li>Then calls {@link MongoAdminStore#registerIndexes(String, List)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ManageGenerationsStoreMongoModule#registerGenerationsIndexes(MongoAdminStore)}
   */
  @Test
  @DisplayName(
      "Test registerGenerationsIndexes(MongoAdminStore); then calls registerIndexes(String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ManageGenerationsStoreMongoModule.registerGenerationsIndexes(MongoAdminStore)"
  })
  void testRegisterGenerationsIndexes_thenCallsRegisterIndexes() {
    // Arrange
    ManageGenerationsStoreMongoModule manageGenerationsStoreMongoModule =
        new ManageGenerationsStoreMongoModule();

    MongoAdminStore adminStore = mock(MongoAdminStore.class);
    doNothing()
        .when(adminStore)
        .registerIndexes(Mockito.<String>any(), Mockito.<List<IndexModel>>any());

    // Act
    boolean actualRegisterGenerationsIndexesResult =
        manageGenerationsStoreMongoModule.registerGenerationsIndexes(adminStore);

    // Assert
    verify(adminStore).registerIndexes(eq("file-generations"), isA(List.class));
    assertTrue(actualRegisterGenerationsIndexesResult);
  }

  /**
   * Test {@link ManageGenerationsStoreMongoModule#registerGenerationsIndexes(MongoAdminStore)}.
   *
   * <ul>
   *   <li>When {@link MongoAdminStore#MongoAdminStore(MongoDatabase)} with mongoDatabase is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ManageGenerationsStoreMongoModule#registerGenerationsIndexes(MongoAdminStore)}
   */
  @Test
  @DisplayName(
      "Test registerGenerationsIndexes(MongoAdminStore); when MongoAdminStore(MongoDatabase) with mongoDatabase is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ManageGenerationsStoreMongoModule.registerGenerationsIndexes(MongoAdminStore)"
  })
  void testRegisterGenerationsIndexes_whenMongoAdminStoreWithMongoDatabaseIsNull() {
    // Arrange
    ManageGenerationsStoreMongoModule manageGenerationsStoreMongoModule =
        new ManageGenerationsStoreMongoModule();

    // Act
    boolean actualRegisterGenerationsIndexesResult =
        manageGenerationsStoreMongoModule.registerGenerationsIndexes(new MongoAdminStore(null));

    // Assert
    assertTrue(actualRegisterGenerationsIndexesResult);
  }
}
