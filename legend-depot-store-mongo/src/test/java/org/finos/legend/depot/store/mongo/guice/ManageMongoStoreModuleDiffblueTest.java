package org.finos.legend.depot.store.mongo.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ManageMongoStoreModuleDiffblueTest {
  /**
   * Test {@link ManageMongoStoreModule#buildMongoAdminStore(MongoDatabase)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return createIndexes Empty.
   * </ul>
   *
   * <p>Method under test: {@link ManageMongoStoreModule#buildMongoAdminStore(MongoDatabase)}
   */
  @Test
  @DisplayName(
      "Test buildMongoAdminStore(MongoDatabase); when 'null'; then return createIndexes Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.finos.legend.depot.store.mongo.admin.MongoAdminStore ManageMongoStoreModule.buildMongoAdminStore(MongoDatabase)"
  })
  void testBuildMongoAdminStore_whenNull_thenReturnCreateIndexesEmpty() {
    // Arrange, Act and Assert
    assertTrue(new ManageMongoStoreModule().buildMongoAdminStore(null).createIndexes().isEmpty());
  }
}
