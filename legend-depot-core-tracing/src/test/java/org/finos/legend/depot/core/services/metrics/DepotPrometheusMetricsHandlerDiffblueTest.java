package org.finos.legend.depot.core.services.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import io.prometheus.client.Counter;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.map.mutable.ConcurrentHashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DepotPrometheusMetricsHandlerDiffblueTest {
  /**
   * Test {@link DepotPrometheusMetricsHandler#DepotPrometheusMetricsHandler(String)}.
   *
   * <p>Method under test: {@link
   * DepotPrometheusMetricsHandler#DepotPrometheusMetricsHandler(String)}
   */
  @Test
  @DisplayName("Test new DepotPrometheusMetricsHandler(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.<init>(String)"})
  void testNewDepotPrometheusMetricsHandler() {
    // Arrange and Act
    DepotPrometheusMetricsHandler actualDepotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Prefix");

    // Assert
    MutableList<Counter> toListResult = actualDepotPrometheusMetricsHandler.allCounters.toList();
    assertTrue(toListResult.isEmpty());
    ConcurrentHashMap<String, Counter> stringCounterMap =
        actualDepotPrometheusMetricsHandler.allCounters;
    assertEquals(stringCounterMap, actualDepotPrometheusMetricsHandler.allGauges);
    assertEquals(stringCounterMap, actualDepotPrometheusMetricsHandler.allHistograms);
    assertEquals(stringCounterMap, actualDepotPrometheusMetricsHandler.allSummaries);
    assertEquals(toListResult, actualDepotPrometheusMetricsHandler.allErrorCounters.toList());
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler("Prefix").observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues2() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler("Prefix")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues3() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler(" ").observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues4() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler("_").observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues5() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler("\\s").observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues6() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "Please register the histogram first if you need labels")
                .observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues7() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler(null).observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues8() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler("(Ljava/lang/Object;)Ljava/lang/Object;")
                .observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues9() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler("42").observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues10() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;")
                .observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues11() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler("").observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues12() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;")
                .observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues13() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(" ")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues14() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler("_")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues15() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler("\\s")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues16() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "Please register the histogram first if you need labels")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues17() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(null)
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues18() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler("(Ljava/lang/Object;)Ljava/lang/Object;")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues19() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler("42")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues20() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues21() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler("")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues22() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues23() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Histogram;")
                .observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues24() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Histogram;")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues25() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(" ")
                .observeHistogram("(Ljava/lang/Object;)Ljava/lang/Object;", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues26() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler(" ").observeHistogram("42", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues27() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(" ")
                .observeHistogram(
                    "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;",
                    1L,
                    1L,
                    "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues28() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "Please register the histogram first if you need labels")
                .observeHistogram("(Ljava/lang/Object;)Ljava/lang/Object;", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues29() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "Please register the histogram first if you need labels")
                .observeHistogram("42", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues30() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "Please register the histogram first if you need labels")
                .observeHistogram(
                    "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;",
                    1L,
                    1L,
                    "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues31() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Summary;")
                .observeHistogram(" ", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues32() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Summary;")
                .observeHistogram(
                    "Please register the histogram first if you need labels", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenEmptyString() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler(" ").observeHistogram("", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenEmptyString2() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "Please register the histogram first if you need labels")
                .observeHistogram("", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenName() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler("Prefix").observeHistogram("Name", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenName2() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler(" ").observeHistogram("Name", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenName3() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "Please register the histogram first if you need labels")
                .observeHistogram("Name", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenNull() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler("Prefix").observeHistogram(null, 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenNull2() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler(" ").observeHistogram(null, 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenNull3() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "Please register the histogram first if you need labels")
                .observeHistogram(null, 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code \s}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when '\\s'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenS() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler(" ").observeHistogram("\\s", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code \s}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when '\\s'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenS2() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "Please register the histogram first if you need labels")
                .observeHistogram("\\s", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code _}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when '_'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenUnderscore() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new DepotPrometheusMetricsHandler(" ").observeHistogram("_", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])} with
   * {@code name}, {@code start}, {@code end}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code _}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test observeHistogram(String, long, long, String[]) with 'name', 'start', 'end', 'labelValues'; when '_'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DepotPrometheusMetricsHandler.observeHistogram(String, long, long, String[])"
  })
  void testObserveHistogramWithNameStartEndLabelValues_whenUnderscore2() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new DepotPrometheusMetricsHandler(
                    "Please register the histogram first if you need labels")
                .observeHistogram("_", 1L, 1L, "42"));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Prefix");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues2() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues3() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("_");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues4() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("\\s");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues5() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues6() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(null);

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues7() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("(Ljava/lang/Object;)Ljava/lang/Object;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues8() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("42");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues9() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues10() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues11() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues12() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Histogram;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues13() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Prefix");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues14() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Prefix");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues15() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues16() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("_");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues17() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("\\s");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues18() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues19() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(null);

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues20() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("(Ljava/lang/Object;)Ljava/lang/Object;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues21() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("42");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues22() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues23() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues24() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues25() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues26() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("_");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues27() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("\\s");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues28() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues29() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(null);

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues30() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("(Ljava/lang/Object;)Ljava/lang/Object;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues31() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("42");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues32() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues33() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues34() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues35() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Histogram;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues36() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Histogram;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues37() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Summary;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues38() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;",
                10.0d,
                new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues39() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;",
                10.0d,
                new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues40() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Summary;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName("Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues41() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Summary;");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>Given space.
   *   <li>When {@link ArrayList#ArrayList()} add space.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; given space; when ArrayList() add space")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_givenSpace_whenArrayListAddSpace() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Prefix");

    ArrayList<String> labelValues = new ArrayList<>();
    labelValues.add(" ");
    labelValues.add("\"TestObjectForArrayListAddMethod\"");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, labelValues));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>Given {@code "TestObjectForArrayListAddMethod"}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; given '\"TestObjectForArrayListAddMethod\"'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_givenTestObjectForArrayListAddMethod() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Prefix");

    ArrayList<String> labelValues = new ArrayList<>();
    labelValues.add("\"TestObjectForArrayListAddMethod\"");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, labelValues));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_when42() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("42", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_when422() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("42", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenEmptyString() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenEmptyString2() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code (Ljava/lang/Object;)Ljava/lang/Object;}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when '(Ljava/lang/Object;)Ljava/lang/Object;'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenLjavaLangObjectLjavaLangObject() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "(Ljava/lang/Object;)Ljava/lang/Object;", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code (Ljava/lang/Object;)Ljava/lang/Object;}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when '(Ljava/lang/Object;)Ljava/lang/Object;'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenLjavaLangObjectLjavaLangObject2() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            depotPrometheusMetricsHandler.setGauge(
                "(Ljava/lang/Object;)Ljava/lang/Object;", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenNull() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Prefix");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(null, 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenNull2() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(null, 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenNull3() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(null, 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code \s}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when '\\s'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenS() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("\\s", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code \s}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when '\\s'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenS2() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("\\s", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code _}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when '_'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenUnderscore() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Prefix");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("_", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code _}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when '_'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenUnderscore2() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("_", 10.0d, new ArrayList<>()));
  }

  /**
   * Test {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)} with {@code
   * gaugeName}, {@code value}, {@code labelValues}.
   *
   * <ul>
   *   <li>When {@code _}.
   * </ul>
   *
   * <p>Method under test: {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  @DisplayName(
      "Test setGauge(String, double, List) with 'gaugeName', 'value', 'labelValues'; when '_'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotPrometheusMetricsHandler.setGauge(String, double, List)"})
  void testSetGaugeWithGaugeNameValueLabelValues_whenUnderscore3() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler =
        new DepotPrometheusMetricsHandler("Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("_", 10.0d, new ArrayList<>()));
  }
}
