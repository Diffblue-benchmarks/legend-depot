package org.finos.legend.depot.services.metrics.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.finos.legend.depot.store.mongo.metrics.query.QueryMetricsMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class QueryMetricsServiceImplDiffblueTest {
  /**
   * Test {@link QueryMetricsServiceImpl#getSummary(String, String, String)}.
   * <ul>
   *   <li>Given {@link QueryMetricsMongo} {@link QueryMetricsMongo#get(String, String, String)} return {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#getSummary(String, String, String)}
   */
  @Test
  @DisplayName("Test getSummary(String, String, String); given QueryMetricsMongo get(String, String, String) return ArrayList(); then return not Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional QueryMetricsServiceImpl.getSummary(String, String, String)"})
  void testGetSummary_givenQueryMetricsMongoGetReturnArrayList_thenReturnNotPresent() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    Optional<VersionQueryMetric> actualSummary = new QueryMetricsServiceImpl(metricsStore).getSummary("42", "42", "42");

    // Assert
    verify(metricsStore).get(eq("42"), eq("42"), eq("42"));
    assertFalse(actualSummary.isPresent());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getSummary(String, String, String)}.
   * <ul>
   *   <li>Then return Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#getSummary(String, String, String)}
   */
  @Test
  @DisplayName("Test getSummary(String, String, String); then return Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional QueryMetricsServiceImpl.getSummary(String, String, String)"})
  void testGetSummary_thenReturnPresent() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    VersionQueryMetric versionQueryMetric = new VersionQueryMetric("42", "42", "42");

    versionQueryMetricList.add(versionQueryMetric);
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);

    // Act
    Optional<VersionQueryMetric> actualSummary = new QueryMetricsServiceImpl(metricsStore).getSummary("42", "42", "42");

    // Assert
    verify(metricsStore).get(eq("42"), eq("42"), eq("42"));
    assertTrue(actualSummary.isPresent());
    assertSame(versionQueryMetric, actualSummary.get());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}.
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}
   */
  @Test
  @DisplayName("Test getSummaryByProjectVersion(); then return ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getSummaryByProjectVersion()"})
  void testGetSummaryByProjectVersion_thenReturnArrayList() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion("42", "42", "42"));

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    List<VersionQueryMetric> actualSummaryByProjectVersion = new QueryMetricsServiceImpl(metricsStore)
        .getSummaryByProjectVersion();

    // Assert
    verify(metricsStore).get(eq("42"), eq("42"), eq("42"));
    verify(metricsStore).getAllStoredEntitiesCoordinates();
    assertEquals(versionQueryMetricList, actualSummaryByProjectVersion);
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}
   */
  @Test
  @DisplayName("Test getSummaryByProjectVersion(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getSummaryByProjectVersion()"})
  void testGetSummaryByProjectVersion_thenReturnEmpty() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(new ArrayList<>());

    // Act
    List<VersionQueryMetric> actualSummaryByProjectVersion = new QueryMetricsServiceImpl(metricsStore)
        .getSummaryByProjectVersion();

    // Assert
    verify(metricsStore).getAllStoredEntitiesCoordinates();
    assertTrue(actualSummaryByProjectVersion.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}
   */
  @Test
  @DisplayName("Test getSummaryByProjectVersion(); then return size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getSummaryByProjectVersion()"})
  void testGetSummaryByProjectVersion_thenReturnSizeIsTwo() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion("42", "42", "42"));
    projectVersionList.add(new ProjectVersion("42", "42", "42"));

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    VersionQueryMetric versionQueryMetric = new VersionQueryMetric("42", "42", "42");

    versionQueryMetricList.add(versionQueryMetric);
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    List<VersionQueryMetric> actualSummaryByProjectVersion = new QueryMetricsServiceImpl(metricsStore)
        .getSummaryByProjectVersion();

    // Assert
    verify(metricsStore, atLeast(1)).get(eq("42"), eq("42"), eq("42"));
    verify(metricsStore).getAllStoredEntitiesCoordinates();
    assertEquals(2, actualSummaryByProjectVersion.size());
    assertSame(versionQueryMetric, actualSummaryByProjectVersion.get(0));
    assertSame(versionQueryMetric, actualSummaryByProjectVersion.get(1));
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findMetricsForProjectCoordinates(String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#findMetricsForProjectCoordinates(String, String)}
   */
  @Test
  @DisplayName("Test findMetricsForProjectCoordinates(String, String); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.findMetricsForProjectCoordinates(String, String)"})
  void testFindMetricsForProjectCoordinates_thenReturnEmpty() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<VersionQueryMetric> actualFindMetricsForProjectCoordinatesResult = new QueryMetricsServiceImpl(metricsStore)
        .findMetricsForProjectCoordinates("42", "42");

    // Assert
    verify(metricsStore).find(eq("42"), eq("42"));
    assertTrue(actualFindMetricsForProjectCoordinatesResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findReleasedVersionMetricsBefore(Date)}.
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#findReleasedVersionMetricsBefore(Date)}
   */
  @Test
  @DisplayName("Test findReleasedVersionMetricsBefore(Date)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.findReleasedVersionMetricsBefore(Date)"})
  void testFindReleasedVersionMetricsBefore() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    VersionQueryMetric versionQueryMetric = new VersionQueryMetric("42", "42", "42");

    versionQueryMetricList.add(versionQueryMetric);
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.findMetricsBefore(Mockito.<Date>any())).thenReturn(versionQueryMetricList);
    QueryMetricsServiceImpl queryMetricsServiceImpl = new QueryMetricsServiceImpl(metricsStore);

    // Act
    List<VersionQueryMetric> actualFindReleasedVersionMetricsBeforeResult = queryMetricsServiceImpl
        .findReleasedVersionMetricsBefore(
            Date.from(LocalDate.ofEpochDay(1L).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(metricsStore).findMetricsBefore(isA(Date.class));
    assertEquals(2, actualFindReleasedVersionMetricsBeforeResult.size());
    assertSame(versionQueryMetric, actualFindReleasedVersionMetricsBeforeResult.get(1));
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findReleasedVersionMetricsBefore(Date)}.
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#findReleasedVersionMetricsBefore(Date)}
   */
  @Test
  @DisplayName("Test findReleasedVersionMetricsBefore(Date); then return ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.findReleasedVersionMetricsBefore(Date)"})
  void testFindReleasedVersionMetricsBefore_thenReturnArrayList() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.findMetricsBefore(Mockito.<Date>any())).thenReturn(versionQueryMetricList);
    QueryMetricsServiceImpl queryMetricsServiceImpl = new QueryMetricsServiceImpl(metricsStore);

    // Act
    List<VersionQueryMetric> actualFindReleasedVersionMetricsBeforeResult = queryMetricsServiceImpl
        .findReleasedVersionMetricsBefore(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(metricsStore).findMetricsBefore(isA(Date.class));
    assertEquals(versionQueryMetricList, actualFindReleasedVersionMetricsBeforeResult);
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findReleasedVersionMetricsBefore(Date)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#findReleasedVersionMetricsBefore(Date)}
   */
  @Test
  @DisplayName("Test findReleasedVersionMetricsBefore(Date); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.findReleasedVersionMetricsBefore(Date)"})
  void testFindReleasedVersionMetricsBefore_thenReturnEmpty() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.findMetricsBefore(Mockito.<Date>any())).thenReturn(new ArrayList<>());
    QueryMetricsServiceImpl queryMetricsServiceImpl = new QueryMetricsServiceImpl(metricsStore);

    // Act
    List<VersionQueryMetric> actualFindReleasedVersionMetricsBeforeResult = queryMetricsServiceImpl
        .findReleasedVersionMetricsBefore(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(metricsStore).findMetricsBefore(isA(Date.class));
    assertTrue(actualFindReleasedVersionMetricsBeforeResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findReleasedVersionMetricsBefore(Date)}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#findReleasedVersionMetricsBefore(Date)}
   */
  @Test
  @DisplayName("Test findReleasedVersionMetricsBefore(Date); then return size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.findReleasedVersionMetricsBefore(Date)"})
  void testFindReleasedVersionMetricsBefore_thenReturnSizeIsTwo() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    VersionQueryMetric versionQueryMetric = new VersionQueryMetric("42", "42", "42");

    versionQueryMetricList.add(versionQueryMetric);
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.findMetricsBefore(Mockito.<Date>any())).thenReturn(versionQueryMetricList);
    QueryMetricsServiceImpl queryMetricsServiceImpl = new QueryMetricsServiceImpl(metricsStore);

    // Act
    List<VersionQueryMetric> actualFindReleasedVersionMetricsBeforeResult = queryMetricsServiceImpl
        .findReleasedVersionMetricsBefore(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(metricsStore).findMetricsBefore(isA(Date.class));
    assertEquals(2, actualFindReleasedVersionMetricsBeforeResult.size());
    assertSame(versionQueryMetric, actualFindReleasedVersionMetricsBeforeResult.get(1));
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findSnapshotVersionMetricsBefore(Date)}.
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#findSnapshotVersionMetricsBefore(Date)}
   */
  @Test
  @DisplayName("Test findSnapshotVersionMetricsBefore(Date)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.findSnapshotVersionMetricsBefore(Date)"})
  void testFindSnapshotVersionMetricsBefore() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.findMetricsBefore(Mockito.<Date>any())).thenReturn(versionQueryMetricList);
    QueryMetricsServiceImpl queryMetricsServiceImpl = new QueryMetricsServiceImpl(metricsStore);

    // Act
    List<VersionQueryMetric> actualFindSnapshotVersionMetricsBeforeResult = queryMetricsServiceImpl
        .findSnapshotVersionMetricsBefore(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(metricsStore).findMetricsBefore(isA(Date.class));
    assertTrue(actualFindSnapshotVersionMetricsBeforeResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findSnapshotVersionMetricsBefore(Date)}.
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#findSnapshotVersionMetricsBefore(Date)}
   */
  @Test
  @DisplayName("Test findSnapshotVersionMetricsBefore(Date)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.findSnapshotVersionMetricsBefore(Date)"})
  void testFindSnapshotVersionMetricsBefore2() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.findMetricsBefore(Mockito.<Date>any())).thenReturn(versionQueryMetricList);
    QueryMetricsServiceImpl queryMetricsServiceImpl = new QueryMetricsServiceImpl(metricsStore);

    // Act
    List<VersionQueryMetric> actualFindSnapshotVersionMetricsBeforeResult = queryMetricsServiceImpl
        .findSnapshotVersionMetricsBefore(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(metricsStore).findMetricsBefore(isA(Date.class));
    assertTrue(actualFindSnapshotVersionMetricsBeforeResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findSnapshotVersionMetricsBefore(Date)}.
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#findSnapshotVersionMetricsBefore(Date)}
   */
  @Test
  @DisplayName("Test findSnapshotVersionMetricsBefore(Date)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.findSnapshotVersionMetricsBefore(Date)"})
  void testFindSnapshotVersionMetricsBefore3() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.findMetricsBefore(Mockito.<Date>any())).thenReturn(versionQueryMetricList);
    QueryMetricsServiceImpl queryMetricsServiceImpl = new QueryMetricsServiceImpl(metricsStore);

    // Act
    List<VersionQueryMetric> actualFindSnapshotVersionMetricsBeforeResult = queryMetricsServiceImpl
        .findSnapshotVersionMetricsBefore(
            Date.from(LocalDate.ofEpochDay(1L).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(metricsStore).findMetricsBefore(isA(Date.class));
    assertTrue(actualFindSnapshotVersionMetricsBeforeResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findSnapshotVersionMetricsBefore(Date)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#findSnapshotVersionMetricsBefore(Date)}
   */
  @Test
  @DisplayName("Test findSnapshotVersionMetricsBefore(Date); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.findSnapshotVersionMetricsBefore(Date)"})
  void testFindSnapshotVersionMetricsBefore_thenReturnEmpty() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.findMetricsBefore(Mockito.<Date>any())).thenReturn(new ArrayList<>());
    QueryMetricsServiceImpl queryMetricsServiceImpl = new QueryMetricsServiceImpl(metricsStore);

    // Act
    List<VersionQueryMetric> actualFindSnapshotVersionMetricsBeforeResult = queryMetricsServiceImpl
        .findSnapshotVersionMetricsBefore(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(metricsStore).findMetricsBefore(isA(Date.class));
    assertTrue(actualFindSnapshotVersionMetricsBeforeResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#delete(String, String, String)}.
   * <ul>
   *   <li>Given {@link QueryMetricsMongo} {@link QueryMetricsMongo#delete(String, String, String)} return one.</li>
   *   <li>Then calls {@link QueryMetricsMongo#delete(String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#delete(String, String, String)}
   */
  @Test
  @DisplayName("Test delete(String, String, String); given QueryMetricsMongo delete(String, String, String) return one; then calls delete(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void QueryMetricsServiceImpl.delete(String, String, String)"})
  void testDelete_givenQueryMetricsMongoDeleteReturnOne_thenCallsDelete() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.delete(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(1L);

    // Act
    new QueryMetricsServiceImpl(metricsStore).delete("42", "42", "42");

    // Assert
    verify(metricsStore).delete(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}.
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}
   */
  @Test
  @DisplayName("Test getStaleMetrics(int, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getStaleMetrics(int, int)"})
  void testGetStaleMetrics() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAll()).thenReturn(versionQueryMetricList);

    // Act
    List<VersionQueryMetric> actualStaleMetrics = new QueryMetricsServiceImpl(metricsStore).getStaleMetrics(1, 1);

    // Assert
    verify(metricsStore).getAll();
    assertTrue(actualStaleMetrics.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}.
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}
   */
  @Test
  @DisplayName("Test getStaleMetrics(int, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getStaleMetrics(int, int)"})
  void testGetStaleMetrics2() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAll()).thenReturn(versionQueryMetricList);

    // Act
    List<VersionQueryMetric> actualStaleMetrics = new QueryMetricsServiceImpl(metricsStore).getStaleMetrics(1, 1);

    // Assert
    verify(metricsStore).getAll();
    assertTrue(actualStaleMetrics.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}.
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}
   */
  @Test
  @DisplayName("Test getStaleMetrics(int, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getStaleMetrics(int, int)"})
  void testGetStaleMetrics3() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAll()).thenReturn(versionQueryMetricList);

    // Act
    new QueryMetricsServiceImpl(metricsStore).getStaleMetrics(1, 1);

    // Assert
    verify(metricsStore).getAll();
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}.
   * <ul>
   *   <li>Given {@link java.sql.Date} {@link Date#before(Date)} return {@code false}.</li>
   *   <li>Then calls {@link Date#before(Date)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}
   */
  @Test
  @DisplayName("Test getStaleMetrics(int, int); given Date before(Date) return 'false'; then calls before(Date)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getStaleMetrics(int, int)"})
  void testGetStaleMetrics_givenDateBeforeReturnFalse_thenCallsBefore() {
    // Arrange
    java.sql.Date date = mock(java.sql.Date.class);
    when(date.before(Mockito.<Date>any())).thenReturn(false);
    VersionQueryMetric versionQueryMetric = mock(VersionQueryMetric.class);
    when(versionQueryMetric.getLastQueryTime()).thenReturn(date);
    when(versionQueryMetric.getVersionId()).thenReturn("-SNAPSHOT");

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(versionQueryMetric);
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAll()).thenReturn(versionQueryMetricList);

    // Act
    List<VersionQueryMetric> actualStaleMetrics = new QueryMetricsServiceImpl(metricsStore).getStaleMetrics(1, 1);

    // Assert
    verify(date, atLeast(1)).before(Mockito.<Date>any());
    verify(versionQueryMetric, atLeast(1)).getLastQueryTime();
    verify(versionQueryMetric).getVersionId();
    verify(metricsStore).getAll();
    assertTrue(actualStaleMetrics.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}.
   * <ul>
   *   <li>Given {@link java.sql.Date} {@link Date#before(Date)} return {@code true}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}
   */
  @Test
  @DisplayName("Test getStaleMetrics(int, int); given Date before(Date) return 'true'; then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getStaleMetrics(int, int)"})
  void testGetStaleMetrics_givenDateBeforeReturnTrue_thenReturnSizeIsOne() {
    // Arrange
    java.sql.Date date = mock(java.sql.Date.class);
    when(date.before(Mockito.<Date>any())).thenReturn(true);
    VersionQueryMetric versionQueryMetric = mock(VersionQueryMetric.class);
    when(versionQueryMetric.getLastQueryTime()).thenReturn(date);
    when(versionQueryMetric.getVersionId()).thenReturn("-SNAPSHOT");

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(versionQueryMetric);
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAll()).thenReturn(versionQueryMetricList);

    // Act
    List<VersionQueryMetric> actualStaleMetrics = new QueryMetricsServiceImpl(metricsStore).getStaleMetrics(1, 1);

    // Assert
    verify(date).before(isA(Date.class));
    verify(versionQueryMetric).getLastQueryTime();
    verify(versionQueryMetric).getVersionId();
    verify(metricsStore).getAll();
    assertEquals(1, actualStaleMetrics.size());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}.
   * <ul>
   *   <li>Given {@link QueryMetricsMongo} {@link QueryMetricsMongo#getAll()} return {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}
   */
  @Test
  @DisplayName("Test getStaleMetrics(int, int); given QueryMetricsMongo getAll() return ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getStaleMetrics(int, int)"})
  void testGetStaleMetrics_givenQueryMetricsMongoGetAllReturnArrayList_thenReturnEmpty() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAll()).thenReturn(new ArrayList<>());

    // Act
    List<VersionQueryMetric> actualStaleMetrics = new QueryMetricsServiceImpl(metricsStore).getStaleMetrics(1, 1);

    // Assert
    verify(metricsStore).getAll();
    assertTrue(actualStaleMetrics.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#consolidateMetrics()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then calls {@link QueryMetricsMongo#get(String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#consolidateMetrics()}
   */
  @Test
  @DisplayName("Test consolidateMetrics(); given ArrayList() add 'null'; then calls get(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void QueryMetricsServiceImpl.consolidateMetrics()"})
  void testConsolidateMetrics_givenArrayListAddNull_thenCallsGet() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion("42", "42", "42"));

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(null);
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    new QueryMetricsServiceImpl(metricsStore).consolidateMetrics();

    // Assert
    verify(metricsStore).get(eq("42"), eq("42"), eq("42"));
    verify(metricsStore).getAllStoredEntitiesCoordinates();
  }

  /**
   * Test {@link QueryMetricsServiceImpl#consolidateMetrics()}.
   * <ul>
   *   <li>Then calls {@link QueryMetricsMongo#consolidate(VersionQueryMetric)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#consolidateMetrics()}
   */
  @Test
  @DisplayName("Test consolidateMetrics(); then calls consolidate(VersionQueryMetric)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void QueryMetricsServiceImpl.consolidateMetrics()"})
  void testConsolidateMetrics_thenCallsConsolidate() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion("42", "42", "42"));

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.consolidate(Mockito.<VersionQueryMetric>any())).thenReturn(1L);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    new QueryMetricsServiceImpl(metricsStore).consolidateMetrics();

    // Assert
    verify(metricsStore).consolidate(isA(VersionQueryMetric.class));
    verify(metricsStore).get(eq("42"), eq("42"), eq("42"));
    verify(metricsStore).getAllStoredEntitiesCoordinates();
  }

  /**
   * Test {@link QueryMetricsServiceImpl#consolidateMetrics()}.
   * <ul>
   *   <li>Then calls {@link QueryMetricsMongo#consolidate(VersionQueryMetric)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#consolidateMetrics()}
   */
  @Test
  @DisplayName("Test consolidateMetrics(); then calls consolidate(VersionQueryMetric)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void QueryMetricsServiceImpl.consolidateMetrics()"})
  void testConsolidateMetrics_thenCallsConsolidate2() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion("42", "42", "42"));
    projectVersionList.add(new ProjectVersion("42", "42", "42"));

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric("42", "42", "42"));
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.consolidate(Mockito.<VersionQueryMetric>any())).thenReturn(1L);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    new QueryMetricsServiceImpl(metricsStore).consolidateMetrics();

    // Assert
    verify(metricsStore, atLeast(1)).consolidate(isA(VersionQueryMetric.class));
    verify(metricsStore, atLeast(1)).get(eq("42"), eq("42"), eq("42"));
    verify(metricsStore).getAllStoredEntitiesCoordinates();
  }

  /**
   * Test {@link QueryMetricsServiceImpl#consolidateMetrics()}.
   * <ul>
   *   <li>Then calls {@link QueryMetricsMongo#get(String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#consolidateMetrics()}
   */
  @Test
  @DisplayName("Test consolidateMetrics(); then calls get(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void QueryMetricsServiceImpl.consolidateMetrics()"})
  void testConsolidateMetrics_thenCallsGet() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion("42", "42", "42"));
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    new QueryMetricsServiceImpl(metricsStore).consolidateMetrics();

    // Assert
    verify(metricsStore).get(eq("42"), eq("42"), eq("42"));
    verify(metricsStore).getAllStoredEntitiesCoordinates();
  }

  /**
   * Test {@link QueryMetricsServiceImpl#consolidateMetrics()}.
   * <ul>
   *   <li>Then calls {@link QueryMetricsMongo#getAllStoredEntitiesCoordinates()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryMetricsServiceImpl#consolidateMetrics()}
   */
  @Test
  @DisplayName("Test consolidateMetrics(); then calls getAllStoredEntitiesCoordinates()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void QueryMetricsServiceImpl.consolidateMetrics()"})
  void testConsolidateMetrics_thenCallsGetAllStoredEntitiesCoordinates() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(new ArrayList<>());

    // Act
    new QueryMetricsServiceImpl(metricsStore).consolidateMetrics();

    // Assert
    verify(metricsStore).getAllStoredEntitiesCoordinates();
  }
}
