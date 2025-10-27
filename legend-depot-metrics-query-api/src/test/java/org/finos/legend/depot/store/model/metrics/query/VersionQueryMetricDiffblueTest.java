package org.finos.legend.depot.store.model.metrics.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.Test;

class VersionQueryMetricDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionQueryMetric#VersionQueryMetric()}
   *   <li>{@link VersionQueryMetric#setLastQueryTime(Date)}
   *   <li>{@link VersionQueryMetric#getArtifactId()}
   *   <li>{@link VersionQueryMetric#getGroupId()}
   *   <li>{@link VersionQueryMetric#getId()}
   *   <li>{@link VersionQueryMetric#getLastQueryTime()}
   *   <li>{@link VersionQueryMetric#getVersionId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    VersionQueryMetric actualVersionQueryMetric = new VersionQueryMetric();
    Date time = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualVersionQueryMetric.setLastQueryTime(time);
    actualVersionQueryMetric.getArtifactId();
    actualVersionQueryMetric.getGroupId();
    String actualId = actualVersionQueryMetric.getId();
    Date actualLastQueryTime = actualVersionQueryMetric.getLastQueryTime();
    actualVersionQueryMetric.getVersionId();

    // Assert that nothing has changed
    assertEquals("", actualId);
    assertSame(time, actualLastQueryTime);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionQueryMetric#VersionQueryMetric(String, String, String)}
   *   <li>{@link VersionQueryMetric#setLastQueryTime(Date)}
   *   <li>{@link VersionQueryMetric#getArtifactId()}
   *   <li>{@link VersionQueryMetric#getGroupId()}
   *   <li>{@link VersionQueryMetric#getId()}
   *   <li>{@link VersionQueryMetric#getLastQueryTime()}
   *   <li>{@link VersionQueryMetric#getVersionId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    VersionQueryMetric actualVersionQueryMetric = new VersionQueryMetric("42", "42", "42");
    Date time = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualVersionQueryMetric.setLastQueryTime(time);
    String actualArtifactId = actualVersionQueryMetric.getArtifactId();
    String actualGroupId = actualVersionQueryMetric.getGroupId();
    String actualId = actualVersionQueryMetric.getId();
    Date actualLastQueryTime = actualVersionQueryMetric.getLastQueryTime();

    // Assert that nothing has changed
    assertEquals("", actualId);
    assertEquals("42", actualArtifactId);
    assertEquals("42", actualGroupId);
    assertEquals("42", actualVersionQueryMetric.getVersionId());
    assertSame(time, actualLastQueryTime);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link VersionQueryMetric#VersionQueryMetric(String, String, String, Date)}
   *   <li>{@link VersionQueryMetric#setLastQueryTime(Date)}
   *   <li>{@link VersionQueryMetric#getArtifactId()}
   *   <li>{@link VersionQueryMetric#getGroupId()}
   *   <li>{@link VersionQueryMetric#getId()}
   *   <li>{@link VersionQueryMetric#getLastQueryTime()}
   *   <li>{@link VersionQueryMetric#getVersionId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters3() {
    // Arrange and Act
    VersionQueryMetric actualVersionQueryMetric = new VersionQueryMetric("42", "42", "42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    Date time = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualVersionQueryMetric.setLastQueryTime(time);
    String actualArtifactId = actualVersionQueryMetric.getArtifactId();
    String actualGroupId = actualVersionQueryMetric.getGroupId();
    String actualId = actualVersionQueryMetric.getId();
    Date actualLastQueryTime = actualVersionQueryMetric.getLastQueryTime();

    // Assert that nothing has changed
    assertEquals("", actualId);
    assertEquals("42", actualArtifactId);
    assertEquals("42", actualGroupId);
    assertEquals("42", actualVersionQueryMetric.getVersionId());
    assertSame(time, actualLastQueryTime);
  }
}
