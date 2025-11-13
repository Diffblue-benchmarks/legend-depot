package org.finos.legend.depot.services.metrics.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InMemoryQueryMetricsRegistryDiffblueTest {
  /**
   * Test {@link InMemoryQueryMetricsRegistry#record(String, String, String, Date)} with {@code
   * groupId}, {@code artifactId}, {@code versionId}, {@code date}.
   *
   * <p>Method under test: {@link InMemoryQueryMetricsRegistry#record(String, String, String, Date)}
   */
  @Test
  @DisplayName(
      "Test record(String, String, String, Date) with 'groupId', 'artifactId', 'versionId', 'date'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InMemoryQueryMetricsRegistry.record(String, String, String, Date)"})
  void testRecordWithGroupIdArtifactIdVersionIdDate() {
    // Arrange
    InMemoryQueryMetricsRegistry inMemoryQueryMetricsRegistry = new InMemoryQueryMetricsRegistry();
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    inMemoryQueryMetricsRegistry.record("42", "42", "42", date);

    // Assert
    VersionQueryMetric getResult = inMemoryQueryMetricsRegistry.findFirst().get();
    assertEquals("", getResult.getId());
    assertEquals("42", getResult.getArtifactId());
    assertEquals("42", getResult.getGroupId());
    assertEquals("42", getResult.getVersionId());
    assertSame(date, getResult.getLastQueryTime());
  }

  /**
   * Test {@link InMemoryQueryMetricsRegistry#findFirst()}.
   *
   * <p>Method under test: {@link InMemoryQueryMetricsRegistry#findFirst()}
   */
  @Test
  @DisplayName("Test findFirst()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional InMemoryQueryMetricsRegistry.findFirst()"})
  void testFindFirst() {
    // Arrange, Act and Assert
    assertFalse(new InMemoryQueryMetricsRegistry().findFirst().isPresent());
  }
}
