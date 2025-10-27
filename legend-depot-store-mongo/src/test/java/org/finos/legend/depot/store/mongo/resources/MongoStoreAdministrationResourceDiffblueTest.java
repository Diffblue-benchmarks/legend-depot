package org.finos.legend.depot.store.mongo.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MongoStoreAdministrationResourceDiffblueTest {
  @InjectMocks
  private MongoStoreAdministrationResource mongoStoreAdministrationResource;

  @Mock
  private StorageMetricsHandler storageMetricsHandler;

  @Mock
  private MongoAdminStore mongoAdminStore;

  /**
   * Test {@link MongoStoreAdministrationResource#getIndexed()}.
   * <p>
   * Method under test: {@link MongoStoreAdministrationResource#getIndexed()}
   */
  @Test
  @DisplayName("Test getIndexed()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map MongoStoreAdministrationResource.getIndexed()"})
  void testGetIndexed() {
    // Arrange
    when(mongoAdminStore.getAllIndexes()).thenReturn(new HashMap<>());

    // Act
    Map<String, List<Document>> actualIndexed = mongoStoreAdministrationResource.getIndexed();

    // Assert
    verify(mongoAdminStore).getAllIndexes();
    assertTrue(actualIndexed.isEmpty());
  }

  /**
   * Test {@link MongoStoreAdministrationResource#getCollectionStats()}.
   * <p>
   * Method under test: {@link MongoStoreAdministrationResource#getCollectionStats()}
   */
  @Test
  @DisplayName("Test getCollectionStats()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object MongoStoreAdministrationResource.getCollectionStats()"})
  void testGetCollectionStats() {
    // Arrange
    when(storageMetricsHandler.reportMetrics()).thenReturn("Report Metrics");

    // Act
    Object actualCollectionStats = mongoStoreAdministrationResource.getCollectionStats();

    // Assert
    verify(storageMetricsHandler).reportMetrics();
    assertEquals("Report Metrics", actualCollectionStats);
  }
}
