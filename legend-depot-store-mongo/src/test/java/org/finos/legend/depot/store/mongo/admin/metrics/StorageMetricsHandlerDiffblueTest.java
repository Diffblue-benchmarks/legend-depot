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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler.CollectionStats;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler.DbStats;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler.StorageStats;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StorageMetricsHandlerDiffblueTest {
  @Mock
  private PrometheusMetricsHandler prometheusMetricsHandler;

  @InjectMocks
  private StorageMetricsHandler storageMetricsHandler;

  @Mock
  private MongoAdminStore mongoAdminStore;

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
   *   <li>Given {@link ArrayList#ArrayList()} add {@code foo}.</li>
   *   <li>Then return {@link StorageStats#dbStats} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  @DisplayName("Test reportMetrics(); given ArrayList() add 'foo'; then return dbStats is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object StorageMetricsHandler.reportMetrics()"})
  void testReportMetrics_givenArrayListAddFoo_thenReturnDbStatsIsNull() {
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
    assertTrue(actualReportMetricsResult instanceof StorageStats);
    assertNull(((StorageStats) actualReportMetricsResult).dbStats);
    assertTrue(((StorageStats) actualReportMetricsResult).collectionStats.isEmpty());
  }

  /**
   * Test {@link StorageMetricsHandler#reportMetrics()}.
   * <ul>
   *   <li>Given {@link Document#Document()} append {@code dbStats} and {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  @DisplayName("Test reportMetrics(); given Document() append 'dbStats' and 'Value'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object StorageMetricsHandler.reportMetrics()"})
  void testReportMetrics_givenDocumentAppendDbStatsAndValue() {
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
    assertTrue(actualReportMetricsResult instanceof StorageStats);
    DbStats dbStats = ((StorageStats) actualReportMetricsResult).dbStats;
    assertNull(dbStats.averageDocSize);
    assertNull(dbStats.indexes);
    assertNull(dbStats.objectCount);
    assertEquals(0.0d, dbStats.indexSize.doubleValue());
    assertEquals(0.0d, dbStats.storageSize.doubleValue());
    assertEquals(0.0d, dbStats.uncompressedDataSize.doubleValue());
  }

  /**
   * Test {@link StorageMetricsHandler#reportMetrics()}.
   * <ul>
   *   <li>Given {@link Document#Document()} append {@code null} and {@code Value}.</li>
   *   <li>Then return {@link StorageStats#dbStats} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  @DisplayName("Test reportMetrics(); given Document() append 'null' and 'Value'; then return dbStats is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object StorageMetricsHandler.reportMetrics()"})
  void testReportMetrics_givenDocumentAppendNullAndValue_thenReturnDbStatsIsNull() {
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
    assertTrue(actualReportMetricsResult instanceof StorageStats);
    assertNull(((StorageStats) actualReportMetricsResult).dbStats);
    assertTrue(((StorageStats) actualReportMetricsResult).collectionStats.isEmpty());
  }

  /**
   * Test {@link StorageMetricsHandler#reportMetrics()}.
   * <ul>
   *   <li>Given {@link Document#Document()} append {@code scale} and {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  @DisplayName("Test reportMetrics(); given Document() append 'scale' and 'Value'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object StorageMetricsHandler.reportMetrics()"})
  void testReportMetrics_givenDocumentAppendScaleAndValue() {
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
    assertTrue(actualReportMetricsResult instanceof StorageStats);
    DbStats dbStats = ((StorageStats) actualReportMetricsResult).dbStats;
    assertNull(dbStats.averageDocSize);
    assertNull(dbStats.indexes);
    assertNull(dbStats.objectCount);
    assertEquals(0.0d, dbStats.indexSize.doubleValue());
    assertEquals(0.0d, dbStats.storageSize.doubleValue());
    assertEquals(0.0d, dbStats.uncompressedDataSize.doubleValue());
  }

  /**
   * Test {@link StorageMetricsHandler#reportMetrics()}.
   * <ul>
   *   <li>Then return {@link StorageStats#dbStats} {@link DbStats#averageDocSize} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StorageMetricsHandler#reportMetrics()}
   */
  @Test
  @DisplayName("Test reportMetrics(); then return dbStats averageDocSize is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object StorageMetricsHandler.reportMetrics()"})
  void testReportMetrics_thenReturnDbStatsAverageDocSizeIsNull() {
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
    assertTrue(actualReportMetricsResult instanceof StorageStats);
    DbStats dbStats = ((StorageStats) actualReportMetricsResult).dbStats;
    assertNull(dbStats.averageDocSize);
    assertNull(dbStats.indexes);
    assertNull(dbStats.objectCount);
    assertEquals(0.0d, dbStats.indexSize.doubleValue());
    assertEquals(0.0d, dbStats.storageSize.doubleValue());
    assertEquals(0.0d, dbStats.uncompressedDataSize.doubleValue());
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
