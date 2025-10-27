package org.finos.legend.depot.store.mongo.admin.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StorageMetricsHandlerDiffblueTest {
  @Mock
  private MongoAdminStore mongoAdminStore;

  @Mock
  private PrometheusMetricsHandler prometheusMetricsHandler;

  @InjectMocks
  private StorageMetricsHandler storageMetricsHandler;

  /**
   * Method under test: default or parameterless constructor of
   * {@link StorageMetricsHandler.CollectionStats}
   */
  @Test
  void testCollectionStatsNewCollectionStats() {
    // Arrange and Act
    StorageMetricsHandler.CollectionStats actualCollectionStats = new StorageMetricsHandler.CollectionStats();

    // Assert
    assertEquals(0.0d, actualCollectionStats.averageDocSize.doubleValue());
    assertEquals(0.0d, actualCollectionStats.indexSize.doubleValue());
    assertEquals(0.0d, actualCollectionStats.storageSize.doubleValue());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link StorageMetricsHandler.DbStats}
   */
  @Test
  void testDbStatsNewDbStats() {
    // Arrange and Act
    StorageMetricsHandler.DbStats actualDbStats = new StorageMetricsHandler.DbStats();

    // Assert
    assertEquals(0.0d, actualDbStats.indexSize.doubleValue());
    assertEquals(0.0d, actualDbStats.storageSize.doubleValue());
    assertEquals(0.0d, actualDbStats.uncompressedDataSize.doubleValue());
  }

  /**
   * Method under test: {@link StorageMetricsHandler#init()}
   */
  @Test
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
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  void testReportMetrics() {
    // Arrange
    when(mongoAdminStore.getAllCollections()).thenReturn(new ArrayList<>());
    when(mongoAdminStore.runCommand(Mockito.<Document>any())).thenReturn(new Document());
    doNothing().when(prometheusMetricsHandler).setGauge(Mockito.<String>any(), anyDouble());

    // Act
    Object actualReportMetricsResult = storageMetricsHandler.reportMetrics();

    // Assert
    verify(prometheusMetricsHandler, atLeast(1)).setGauge(Mockito.<String>any(), eq(0.0d));
    verify(mongoAdminStore).getAllCollections();
    verify(mongoAdminStore).runCommand(isA(Document.class));
    assertTrue(actualReportMetricsResult instanceof StorageMetricsHandler.StorageStats);
    StorageMetricsHandler.DbStats dbStats = ((StorageMetricsHandler.StorageStats) actualReportMetricsResult).dbStats;
    assertNull(dbStats.averageDocSize);
    assertNull(dbStats.indexes);
    assertNull(dbStats.objectCount);
    assertEquals(0.0d, dbStats.indexSize.doubleValue());
    assertEquals(0.0d, dbStats.storageSize.doubleValue());
    assertEquals(0.0d, dbStats.uncompressedDataSize.doubleValue());
    assertTrue(((StorageMetricsHandler.StorageStats) actualReportMetricsResult).collectionStats.isEmpty());
  }

  /**
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  void testReportMetrics2() {
    // Arrange
    Document document = new Document();
    document.append("dbStats", "Value");
    when(mongoAdminStore.getAllCollections()).thenReturn(new ArrayList<>());
    when(mongoAdminStore.runCommand(Mockito.<Document>any())).thenReturn(document);
    doNothing().when(prometheusMetricsHandler).setGauge(Mockito.<String>any(), anyDouble());

    // Act
    Object actualReportMetricsResult = storageMetricsHandler.reportMetrics();

    // Assert
    verify(prometheusMetricsHandler, atLeast(1)).setGauge(Mockito.<String>any(), eq(0.0d));
    verify(mongoAdminStore).getAllCollections();
    verify(mongoAdminStore).runCommand(isA(Document.class));
    assertTrue(actualReportMetricsResult instanceof StorageMetricsHandler.StorageStats);
    StorageMetricsHandler.DbStats dbStats = ((StorageMetricsHandler.StorageStats) actualReportMetricsResult).dbStats;
    assertNull(dbStats.averageDocSize);
    assertNull(dbStats.indexes);
    assertNull(dbStats.objectCount);
    assertEquals(0.0d, dbStats.indexSize.doubleValue());
    assertEquals(0.0d, dbStats.storageSize.doubleValue());
    assertEquals(0.0d, dbStats.uncompressedDataSize.doubleValue());
    assertTrue(((StorageMetricsHandler.StorageStats) actualReportMetricsResult).collectionStats.isEmpty());
  }

  /**
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  void testReportMetrics3() {
    // Arrange
    Document document = new Document();
    document.append("scale", "Value");
    document.append("dbStats", 42);
    when(mongoAdminStore.getAllCollections()).thenReturn(new ArrayList<>());
    when(mongoAdminStore.runCommand(Mockito.<Document>any())).thenReturn(document);
    doNothing().when(prometheusMetricsHandler).setGauge(Mockito.<String>any(), anyDouble());

    // Act
    Object actualReportMetricsResult = storageMetricsHandler.reportMetrics();

    // Assert
    verify(prometheusMetricsHandler, atLeast(1)).setGauge(Mockito.<String>any(), eq(0.0d));
    verify(mongoAdminStore).getAllCollections();
    verify(mongoAdminStore).runCommand(isA(Document.class));
    assertTrue(actualReportMetricsResult instanceof StorageMetricsHandler.StorageStats);
    StorageMetricsHandler.DbStats dbStats = ((StorageMetricsHandler.StorageStats) actualReportMetricsResult).dbStats;
    assertNull(dbStats.averageDocSize);
    assertNull(dbStats.indexes);
    assertNull(dbStats.objectCount);
    assertEquals(0.0d, dbStats.indexSize.doubleValue());
    assertEquals(0.0d, dbStats.storageSize.doubleValue());
    assertEquals(0.0d, dbStats.uncompressedDataSize.doubleValue());
    assertTrue(((StorageMetricsHandler.StorageStats) actualReportMetricsResult).collectionStats.isEmpty());
  }

  /**
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  void testReportMetrics4() {
    // Arrange
    Document document = new Document();
    document.append(null, "Value");
    document.append("dbStats", 42);
    when(mongoAdminStore.getName()).thenReturn("Name");
    when(mongoAdminStore.getAllCollections()).thenReturn(new ArrayList<>());
    when(mongoAdminStore.runCommand(Mockito.<Document>any())).thenReturn(document);

    // Act
    Object actualReportMetricsResult = storageMetricsHandler.reportMetrics();

    // Assert
    verify(mongoAdminStore).getAllCollections();
    verify(mongoAdminStore).getName();
    verify(mongoAdminStore).runCommand(isA(Document.class));
    assertTrue(actualReportMetricsResult instanceof StorageMetricsHandler.StorageStats);
    assertNull(((StorageMetricsHandler.StorageStats) actualReportMetricsResult).dbStats);
    assertTrue(((StorageMetricsHandler.StorageStats) actualReportMetricsResult).collectionStats.isEmpty());
  }

  /**
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  void testReportMetrics5() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");

    Document document = new Document();
    document.append(null, "Value");
    document.append("dbStats", 42);
    when(mongoAdminStore.getName()).thenReturn("Name");
    when(mongoAdminStore.getAllCollections()).thenReturn(stringList);
    when(mongoAdminStore.runCommand(Mockito.<Document>any())).thenReturn(document);

    // Act
    Object actualReportMetricsResult = storageMetricsHandler.reportMetrics();

    // Assert
    verify(mongoAdminStore).getAllCollections();
    verify(mongoAdminStore).getName();
    verify(mongoAdminStore, atLeast(1)).runCommand(Mockito.<Document>any());
    assertTrue(actualReportMetricsResult instanceof StorageMetricsHandler.StorageStats);
    assertNull(((StorageMetricsHandler.StorageStats) actualReportMetricsResult).dbStats);
    assertTrue(((StorageMetricsHandler.StorageStats) actualReportMetricsResult).collectionStats.isEmpty());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link StorageMetricsHandler.StorageStats}
   */
  @Test
  void testStorageStatsNewStorageStats() {
    // Arrange and Act
    StorageMetricsHandler.StorageStats actualStorageStats = new StorageMetricsHandler.StorageStats();

    // Assert
    assertNull(actualStorageStats.collectionStats);
    assertNull(actualStorageStats.dbStats);
  }
}
