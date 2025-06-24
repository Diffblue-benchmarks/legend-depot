package org.finos.legend.depot.store.mongo.admin.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.internal.MongoDatabaseImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler.CollectionStats;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler.DbStats;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler.StorageStats;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Disabled("Failed pitest")
@ExtendWith(MockitoExtension.class)
class StorageMetricsHandlerDiffblueTest {
  @Mock
  private PrometheusMetricsHandler prometheusMetricsHandler;

  @InjectMocks
  private StorageMetricsHandler storageMetricsHandler;

  /**
   * Test CollectionStats new {@link CollectionStats} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link CollectionStats}
   */
  @Test
  @DisplayName("Test CollectionStats new CollectionStats (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CollectionStats.<init>()"})
  void testCollectionStatsNewCollectionStats() {
    // Arrange and Act
    CollectionStats actualCollectionStats = new CollectionStats();

    // Assert
    assertEquals(0.0d, actualCollectionStats.averageDocSize.doubleValue());
    assertEquals(0.0d, actualCollectionStats.indexSize.doubleValue());
    assertEquals(0.0d, actualCollectionStats.storageSize.doubleValue());
  }

  /**
   * Test DbStats new {@link DbStats} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link DbStats}
   */
  @Test
  @DisplayName("Test DbStats new DbStats (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DbStats.<init>()"})
  void testDbStatsNewDbStats() {
    // Arrange and Act
    DbStats actualDbStats = new DbStats();

    // Assert
    assertEquals(0.0d, actualDbStats.indexSize.doubleValue());
    assertEquals(0.0d, actualDbStats.storageSize.doubleValue());
    assertEquals(0.0d, actualDbStats.uncompressedDataSize.doubleValue());
  }

  /**
   * Test {@link StorageMetricsHandler#init()}.
   * <p>
   * Method under test: {@link StorageMetricsHandler#init()}
   */
  @Test
  @DisplayName("Test init()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StorageMetricsHandler.init()"})
  void testInit() {
    // Arrange
    doNothing().when(prometheusMetricsHandler).registerGauge(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(prometheusMetricsHandler)
        .registerGauge(Mockito.<String>any(), Mockito.<String>any(), Mockito.<List<String>>any());

    // Act
    storageMetricsHandler.init();

    // Assert
    verify(prometheusMetricsHandler, atLeast(1)).registerGauge(Mockito.<String>any(), Mockito.<String>any());
    verify(prometheusMetricsHandler, atLeast(1)).registerGauge(Mockito.<String>any(), Mockito.<String>any(),
        isA(List.class));
  }

  /**
   * Test {@link StorageMetricsHandler#reportMetrics()}.
   * <ul>
   *   <li>Given {@link Document#Document()} append {@code dbStats} and {@code Value}.</li>
   *   <li>Then calls {@link MongoDatabaseImpl#listCollections()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  @DisplayName("Test reportMetrics(); given Document() append 'dbStats' and 'Value'; then calls listCollections()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object StorageMetricsHandler.reportMetrics()"})
  void testReportMetrics_givenDocumentAppendDbStatsAndValue_thenCallsListCollections() {
    // Arrange
    ListCollectionsIterable<Document> listCollectionsIterable = mock(ListCollectionsIterable.class);
    doNothing().when(listCollectionsIterable).forEach(Mockito.<Consumer<Document>>any());

    Document document = new Document();
    document.append("dbStats", "Value");
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.listCollections()).thenReturn(listCollectionsIterable);
    when(mongoDatabase.runCommand(Mockito.<Bson>any())).thenReturn(document);
    MongoAdminStore adminStore = new MongoAdminStore(mongoDatabase);

    // Act
    Object actualReportMetricsResult = new StorageMetricsHandler(adminStore, new VoidPrometheusMetricsHandler())
        .reportMetrics();

    // Assert
    verify(mongoDatabase).listCollections();
    verify(mongoDatabase).runCommand(isA(Bson.class));
    verify(listCollectionsIterable).forEach(isA(Consumer.class));
    assertTrue(actualReportMetricsResult instanceof StorageStats);
    DbStats dbStats = ((StorageStats) actualReportMetricsResult).dbStats;
    assertNull(dbStats.averageDocSize);
    assertNull(dbStats.indexes);
    assertNull(dbStats.objectCount);
    assertEquals(0.0d, dbStats.indexSize.doubleValue());
    assertEquals(0.0d, dbStats.storageSize.doubleValue());
    assertEquals(0.0d, dbStats.uncompressedDataSize.doubleValue());
    assertTrue(((StorageStats) actualReportMetricsResult).collectionStats.isEmpty());
  }

  /**
   * Test {@link StorageMetricsHandler#reportMetrics()}.
   * <ul>
   *   <li>Then calls {@link MongoAdminStore#getAllCollections()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  @DisplayName("Test reportMetrics(); then calls getAllCollections()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object StorageMetricsHandler.reportMetrics()"})
  void testReportMetrics_thenCallsGetAllCollections() {
    // Arrange
    MongoAdminStore adminStore = mock(MongoAdminStore.class);
    when(adminStore.getAllCollections()).thenReturn(new ArrayList<>());
    when(adminStore.runCommand(Mockito.<Document>any())).thenReturn(new Document());

    // Act
    Object actualReportMetricsResult = new StorageMetricsHandler(adminStore, new VoidPrometheusMetricsHandler())
        .reportMetrics();

    // Assert
    verify(adminStore).getAllCollections();
    verify(adminStore).runCommand(isA(Document.class));
    assertTrue(actualReportMetricsResult instanceof StorageStats);
    DbStats dbStats = ((StorageStats) actualReportMetricsResult).dbStats;
    assertNull(dbStats.averageDocSize);
    assertNull(dbStats.indexes);
    assertNull(dbStats.objectCount);
    assertEquals(0.0d, dbStats.indexSize.doubleValue());
    assertEquals(0.0d, dbStats.storageSize.doubleValue());
    assertEquals(0.0d, dbStats.uncompressedDataSize.doubleValue());
    assertTrue(((StorageStats) actualReportMetricsResult).collectionStats.isEmpty());
  }

  /**
   * Test {@link StorageMetricsHandler#reportMetrics()}.
   * <ul>
   *   <li>Then calls {@link MongoDatabaseImpl#listCollections()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  @DisplayName("Test reportMetrics(); then calls listCollections()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object StorageMetricsHandler.reportMetrics()"})
  void testReportMetrics_thenCallsListCollections() {
    // Arrange
    ListCollectionsIterable<Document> listCollectionsIterable = mock(ListCollectionsIterable.class);
    doNothing().when(listCollectionsIterable).forEach(Mockito.<Consumer<Document>>any());
    MongoDatabaseImpl mongoDatabase = mock(MongoDatabaseImpl.class);
    when(mongoDatabase.listCollections()).thenReturn(listCollectionsIterable);
    when(mongoDatabase.runCommand(Mockito.<Bson>any())).thenReturn(new Document());
    MongoAdminStore adminStore = new MongoAdminStore(mongoDatabase);

    // Act
    Object actualReportMetricsResult = new StorageMetricsHandler(adminStore, new VoidPrometheusMetricsHandler())
        .reportMetrics();

    // Assert
    verify(mongoDatabase).listCollections();
    verify(mongoDatabase).runCommand(isA(Bson.class));
    verify(listCollectionsIterable).forEach(isA(Consumer.class));
    assertTrue(actualReportMetricsResult instanceof StorageStats);
    DbStats dbStats = ((StorageStats) actualReportMetricsResult).dbStats;
    assertNull(dbStats.averageDocSize);
    assertNull(dbStats.indexes);
    assertNull(dbStats.objectCount);
    assertEquals(0.0d, dbStats.indexSize.doubleValue());
    assertEquals(0.0d, dbStats.storageSize.doubleValue());
    assertEquals(0.0d, dbStats.uncompressedDataSize.doubleValue());
    assertTrue(((StorageStats) actualReportMetricsResult).collectionStats.isEmpty());
  }

  /**
   * Test StorageStats new {@link StorageStats} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link StorageStats}
   */
  @Test
  @DisplayName("Test StorageStats new StorageStats (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StorageStats.<init>()"})
  void testStorageStatsNewStorageStats() {
    // Arrange and Act
    StorageStats actualStorageStats = new StorageStats();

    // Assert
    assertNull(actualStorageStats.collectionStats);
    assertNull(actualStorageStats.dbStats);
  }
}
