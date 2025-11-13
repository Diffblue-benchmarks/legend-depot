package org.finos.legend.depot.services.metrics.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link VersionQueryMetric#VersionQueryMetric()}.
   *   <li>Then return Present.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#getSummary(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test getSummary(String, String, String); given ArrayList() add VersionQueryMetric(); then return Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional QueryMetricsServiceImpl.getSummary(String, String, String)"})
  void testGetSummary_givenArrayListAddVersionQueryMetric_thenReturnPresent() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    VersionQueryMetric versionQueryMetric = new VersionQueryMetric();
    versionQueryMetricList.add(versionQueryMetric);

    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);

    // Act
    Optional<VersionQueryMetric> actualSummary =
        new QueryMetricsServiceImpl(metricsStore).getSummary("42", "42", "42");

    // Assert
    verify(metricsStore).get("42", "42", "42");
    assertTrue(actualSummary.isPresent());
    assertSame(versionQueryMetric, actualSummary.get());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getSummary(String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link QueryMetricsMongo} {@link QueryMetricsMongo#get(String, String, String)}
   *       return {@link ArrayList#ArrayList()}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#getSummary(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test getSummary(String, String, String); given QueryMetricsMongo get(String, String, String) return ArrayList(); then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional QueryMetricsServiceImpl.getSummary(String, String, String)"})
  void testGetSummary_givenQueryMetricsMongoGetReturnArrayList_thenReturnNotPresent() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    Optional<VersionQueryMetric> actualSummary =
        new QueryMetricsServiceImpl(metricsStore).getSummary("42", "42", "42");

    // Assert
    verify(metricsStore).get("42", "42", "42");
    assertFalse(actualSummary.isPresent());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}.
   *
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}
   */
  @Test
  @DisplayName("Test getSummaryByProjectVersion(); then return ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getSummaryByProjectVersion()"})
  void testGetSummaryByProjectVersion_thenReturnArrayList() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion());

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric());

    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    List<VersionQueryMetric> actualSummaryByProjectVersion =
        new QueryMetricsServiceImpl(metricsStore).getSummaryByProjectVersion();

    // Assert
    verify(metricsStore).get(null, null, null);
    verify(metricsStore).getAllStoredEntitiesCoordinates();
    assertEquals(versionQueryMetricList, actualSummaryByProjectVersion);
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}
   */
  @Test
  @DisplayName("Test getSummaryByProjectVersion(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getSummaryByProjectVersion()"})
  void testGetSummaryByProjectVersion_thenReturnEmpty() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(new ArrayList<>());

    // Act
    List<VersionQueryMetric> actualSummaryByProjectVersion =
        new QueryMetricsServiceImpl(metricsStore).getSummaryByProjectVersion();

    // Assert
    verify(metricsStore).getAllStoredEntitiesCoordinates();
    assertTrue(actualSummaryByProjectVersion.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}.
   *
   * <ul>
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#getSummaryByProjectVersion()}
   */
  @Test
  @DisplayName("Test getSummaryByProjectVersion(); then return size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getSummaryByProjectVersion()"})
  void testGetSummaryByProjectVersion_thenReturnSizeIsTwo() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion());
    projectVersionList.add(new ProjectVersion());

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    VersionQueryMetric versionQueryMetric = new VersionQueryMetric();
    versionQueryMetricList.add(versionQueryMetric);

    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    List<VersionQueryMetric> actualSummaryByProjectVersion =
        new QueryMetricsServiceImpl(metricsStore).getSummaryByProjectVersion();

    // Assert
    verify(metricsStore, atLeast(1)).get(null, null, null);
    verify(metricsStore).getAllStoredEntitiesCoordinates();
    assertEquals(2, actualSummaryByProjectVersion.size());
    assertSame(versionQueryMetric, actualSummaryByProjectVersion.get(0));
    assertSame(versionQueryMetric, actualSummaryByProjectVersion.get(1));
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findMetricsForProjectCoordinates(String, String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#findMetricsForProjectCoordinates(String,
   * String)}
   */
  @Test
  @DisplayName("Test findMetricsForProjectCoordinates(String, String); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List QueryMetricsServiceImpl.findMetricsForProjectCoordinates(String, String)"
  })
  void testFindMetricsForProjectCoordinates_thenReturnEmpty() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<VersionQueryMetric> actualFindMetricsForProjectCoordinatesResult =
        new QueryMetricsServiceImpl(metricsStore).findMetricsForProjectCoordinates("42", "42");

    // Assert
    verify(metricsStore).find("42", "42");
    assertTrue(actualFindMetricsForProjectCoordinatesResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findReleasedVersionMetricsBefore(Date)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#findReleasedVersionMetricsBefore(Date)}
   */
  @Test
  @DisplayName("Test findReleasedVersionMetricsBefore(Date); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsServiceImpl.findReleasedVersionMetricsBefore(Date)"})
  void testFindReleasedVersionMetricsBefore_thenReturnEmpty() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.findMetricsBefore(Mockito.<Date>any())).thenReturn(new ArrayList<>());
    QueryMetricsServiceImpl queryMetricsServiceImpl = new QueryMetricsServiceImpl(metricsStore);

    // Act
    List<VersionQueryMetric> actualFindReleasedVersionMetricsBeforeResult =
        queryMetricsServiceImpl.findReleasedVersionMetricsBefore(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(metricsStore).findMetricsBefore(isA(Date.class));
    assertTrue(actualFindReleasedVersionMetricsBeforeResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#findSnapshotVersionMetricsBefore(Date)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#findSnapshotVersionMetricsBefore(Date)}
   */
  @Test
  @DisplayName("Test findSnapshotVersionMetricsBefore(Date); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsServiceImpl.findSnapshotVersionMetricsBefore(Date)"})
  void testFindSnapshotVersionMetricsBefore_thenReturnEmpty() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.findMetricsBefore(Mockito.<Date>any())).thenReturn(new ArrayList<>());
    QueryMetricsServiceImpl queryMetricsServiceImpl = new QueryMetricsServiceImpl(metricsStore);

    // Act
    List<VersionQueryMetric> actualFindSnapshotVersionMetricsBeforeResult =
        queryMetricsServiceImpl.findSnapshotVersionMetricsBefore(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(metricsStore).findMetricsBefore(isA(Date.class));
    assertTrue(actualFindSnapshotVersionMetricsBeforeResult.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#delete(String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link QueryMetricsMongo} {@link QueryMetricsMongo#delete(String, String, String)}
   *       return one.
   *   <li>Then calls {@link QueryMetricsMongo#delete(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#delete(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test delete(String, String, String); given QueryMetricsMongo delete(String, String, String) return one; then calls delete(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsServiceImpl.delete(String, String, String)"})
  void testDelete_givenQueryMetricsMongoDeleteReturnOne_thenCallsDelete() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.delete(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(1L);

    // Act
    new QueryMetricsServiceImpl(metricsStore).delete("42", "42", "42");

    // Assert
    verify(metricsStore).delete("42", "42", "42");
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}.
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}
   */
  @Test
  @DisplayName("Test getStaleMetrics(int, int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getStaleMetrics(int, int)"})
  void testGetStaleMetrics() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    Date lastQueryTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    VersionQueryMetric versionQueryMetric = new VersionQueryMetric("42", "42", "42", lastQueryTime);
    versionQueryMetricList.add(versionQueryMetric);

    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAll()).thenReturn(versionQueryMetricList);

    // Act
    new QueryMetricsServiceImpl(metricsStore).getStaleMetrics(1, 1);

    // Assert
    verify(metricsStore).getAll();
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}.
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}
   */
  @Test
  @DisplayName("Test getStaleMetrics(int, int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getStaleMetrics(int, int)"})
  void testGetStaleMetrics2() {
    // Arrange
    VersionQueryMetric versionQueryMetric = mock(VersionQueryMetric.class);
    when(versionQueryMetric.getLastQueryTime())
        .thenReturn(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(versionQueryMetric.getVersionId()).thenReturn("-SNAPSHOT");

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(versionQueryMetric);

    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAll()).thenReturn(versionQueryMetricList);

    // Act
    new QueryMetricsServiceImpl(metricsStore).getStaleMetrics(1, 1);

    // Assert
    verify(versionQueryMetric, atLeast(1)).getLastQueryTime();
    verify(versionQueryMetric).getVersionId();
    verify(metricsStore).getAll();
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}.
   *
   * <ul>
   *   <li>Given {@link QueryMetricsMongo} {@link QueryMetricsMongo#getAll()} return {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}
   */
  @Test
  @DisplayName(
      "Test getStaleMetrics(int, int); given QueryMetricsMongo getAll() return ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getStaleMetrics(int, int)"})
  void testGetStaleMetrics_givenQueryMetricsMongoGetAllReturnArrayList_thenReturnEmpty() {
    // Arrange
    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAll()).thenReturn(new ArrayList<>());

    // Act
    List<VersionQueryMetric> actualStaleMetrics =
        new QueryMetricsServiceImpl(metricsStore).getStaleMetrics(1, 1);

    // Assert
    verify(metricsStore).getAll();
    assertTrue(actualStaleMetrics.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#getStaleMetrics(int, int)}
   */
  @Test
  @DisplayName("Test getStaleMetrics(int, int); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List QueryMetricsServiceImpl.getStaleMetrics(int, int)"})
  void testGetStaleMetrics_thenReturnEmpty() {
    // Arrange
    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    VersionQueryMetric versionQueryMetric = new VersionQueryMetric("42", "42", "42");
    versionQueryMetricList.add(versionQueryMetric);

    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.getAll()).thenReturn(versionQueryMetricList);

    // Act
    List<VersionQueryMetric> actualStaleMetrics =
        new QueryMetricsServiceImpl(metricsStore).getStaleMetrics(1, 1);

    // Assert
    verify(metricsStore).getAll();
    assertTrue(actualStaleMetrics.isEmpty());
  }

  /**
   * Test {@link QueryMetricsServiceImpl#consolidateMetrics()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ProjectVersion#ProjectVersion()}.
   *   <li>Then calls {@link QueryMetricsMongo#get(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#consolidateMetrics()}
   */
  @Test
  @DisplayName(
      "Test consolidateMetrics(); given ArrayList() add ProjectVersion(); then calls get(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsServiceImpl.consolidateMetrics()"})
  void testConsolidateMetrics_givenArrayListAddProjectVersion_thenCallsGet() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion());

    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    new QueryMetricsServiceImpl(metricsStore).consolidateMetrics();

    // Assert
    verify(metricsStore).get(null, null, null);
    verify(metricsStore).getAllStoredEntitiesCoordinates();
  }

  /**
   * Test {@link QueryMetricsServiceImpl#consolidateMetrics()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ProjectVersion#ProjectVersion()}.
   *   <li>Then calls {@link QueryMetricsMongo#get(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#consolidateMetrics()}
   */
  @Test
  @DisplayName(
      "Test consolidateMetrics(); given ArrayList() add ProjectVersion(); then calls get(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsServiceImpl.consolidateMetrics()"})
  void testConsolidateMetrics_givenArrayListAddProjectVersion_thenCallsGet2() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion());

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric());
    versionQueryMetricList.add(new VersionQueryMetric());

    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    new QueryMetricsServiceImpl(metricsStore).consolidateMetrics();

    // Assert
    verify(metricsStore).get(null, null, null);
    verify(metricsStore).getAllStoredEntitiesCoordinates();
  }

  /**
   * Test {@link QueryMetricsServiceImpl#consolidateMetrics()}.
   *
   * <ul>
   *   <li>Then calls {@link QueryMetricsMongo#consolidate(VersionQueryMetric)}.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#consolidateMetrics()}
   */
  @Test
  @DisplayName("Test consolidateMetrics(); then calls consolidate(VersionQueryMetric)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsServiceImpl.consolidateMetrics()"})
  void testConsolidateMetrics_thenCallsConsolidate() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion());

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric());

    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.consolidate(Mockito.<VersionQueryMetric>any())).thenReturn(1L);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    new QueryMetricsServiceImpl(metricsStore).consolidateMetrics();

    // Assert
    verify(metricsStore).consolidate(isA(VersionQueryMetric.class));
    verify(metricsStore).get(null, null, null);
    verify(metricsStore).getAllStoredEntitiesCoordinates();
  }

  /**
   * Test {@link QueryMetricsServiceImpl#consolidateMetrics()}.
   *
   * <ul>
   *   <li>Then calls {@link QueryMetricsMongo#consolidate(VersionQueryMetric)}.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#consolidateMetrics()}
   */
  @Test
  @DisplayName("Test consolidateMetrics(); then calls consolidate(VersionQueryMetric)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsServiceImpl.consolidateMetrics()"})
  void testConsolidateMetrics_thenCallsConsolidate2() {
    // Arrange
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    projectVersionList.add(new ProjectVersion());
    projectVersionList.add(new ProjectVersion());

    ArrayList<VersionQueryMetric> versionQueryMetricList = new ArrayList<>();
    versionQueryMetricList.add(new VersionQueryMetric());

    QueryMetricsMongo metricsStore = mock(QueryMetricsMongo.class);
    when(metricsStore.consolidate(Mockito.<VersionQueryMetric>any())).thenReturn(1L);
    when(metricsStore.get(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(versionQueryMetricList);
    when(metricsStore.getAllStoredEntitiesCoordinates()).thenReturn(projectVersionList);

    // Act
    new QueryMetricsServiceImpl(metricsStore).consolidateMetrics();

    // Assert
    verify(metricsStore, atLeast(1)).consolidate(isA(VersionQueryMetric.class));
    verify(metricsStore, atLeast(1)).get(null, null, null);
    verify(metricsStore).getAllStoredEntitiesCoordinates();
  }

  /**
   * Test {@link QueryMetricsServiceImpl#consolidateMetrics()}.
   *
   * <ul>
   *   <li>Then calls {@link QueryMetricsMongo#getAllStoredEntitiesCoordinates()}.
   * </ul>
   *
   * <p>Method under test: {@link QueryMetricsServiceImpl#consolidateMetrics()}
   */
  @Test
  @DisplayName("Test consolidateMetrics(); then calls getAllStoredEntitiesCoordinates()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
