package org.finos.legend.depot.core.services.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.prometheus.client.Counter;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.collections.impl.map.mutable.ConcurrentHashMap;
import org.junit.jupiter.api.Test;

class DepotPrometheusMetricsHandlerDiffblueTest {
  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#DepotPrometheusMetricsHandler(String)}
   */
  @Test
  void testNewDepotPrometheusMetricsHandler() {
    // Arrange and Act
    DepotPrometheusMetricsHandler actualDepotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("Prefix");

    // Assert
    assertTrue(actualDepotPrometheusMetricsHandler.allCounters.toList().isEmpty());
    ConcurrentHashMap<String, Counter> stringCounterMap = actualDepotPrometheusMetricsHandler.allCounters;
    assertEquals(stringCounterMap, actualDepotPrometheusMetricsHandler.allErrorCounters);
    assertEquals(stringCounterMap, actualDepotPrometheusMetricsHandler.allGauges);
    assertEquals(stringCounterMap, actualDepotPrometheusMetricsHandler.allHistograms);
    assertEquals(stringCounterMap, actualDepotPrometheusMetricsHandler.allSummaries);
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#observeHistogram(String, long, long, String[])}
   */
  @Test
  void testObserveHistogram() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Prefix")).observeHistogram("Name", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler(" ")).observeHistogram("Name", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram("Name", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Prefix")).observeHistogram(" ", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class, () -> (new DepotPrometheusMetricsHandler("Prefix"))
        .observeHistogram("Please register the histogram first if you need labels", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Prefix")).observeHistogram(null, 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler(" ")).observeHistogram(" ", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler(" ")).observeHistogram("_", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler(" ")).observeHistogram("\\s", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class, () -> (new DepotPrometheusMetricsHandler(" "))
        .observeHistogram("Please register the histogram first if you need labels", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler(" ")).observeHistogram(null, 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class, () -> (new DepotPrometheusMetricsHandler(" "))
        .observeHistogram("(Ljava/lang/Object;)Ljava/lang/Object;", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler(" ")).observeHistogram("42", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class, () -> (new DepotPrometheusMetricsHandler(" "))
        .observeHistogram("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler(" ")).observeHistogram("", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class, () -> (new DepotPrometheusMetricsHandler(" "))
        .observeHistogram("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram(" ", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram("_", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram("\\s", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram("Please register the histogram first if you need labels", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram(null, 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram("(Ljava/lang/Object;)Ljava/lang/Object;", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram("42", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram("", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("_")).observeHistogram(" ", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("\\s")).observeHistogram(" ", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler(null)).observeHistogram(" ", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("(Ljava/lang/Object;)Ljava/lang/Object;")).observeHistogram(" ", 1L,
            1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("42")).observeHistogram(" ", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;")).observeHistogram(" ", 1L, 1L,
                "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("")).observeHistogram(" ", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;"))
            .observeHistogram(" ", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class, () -> (new DepotPrometheusMetricsHandler("_"))
        .observeHistogram("Please register the histogram first if you need labels", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class, () -> (new DepotPrometheusMetricsHandler("\\s"))
        .observeHistogram("Please register the histogram first if you need labels", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class, () -> (new DepotPrometheusMetricsHandler(null))
        .observeHistogram("Please register the histogram first if you need labels", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("(Ljava/lang/Object;)Ljava/lang/Object;"))
            .observeHistogram("Please register the histogram first if you need labels", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class, () -> (new DepotPrometheusMetricsHandler("42"))
        .observeHistogram("Please register the histogram first if you need labels", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler(
            "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;"))
                .observeHistogram("Please register the histogram first if you need labels", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class, () -> (new DepotPrometheusMetricsHandler(""))
        .observeHistogram("Please register the histogram first if you need labels", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;"))
            .observeHistogram("Please register the histogram first if you need labels", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class, () -> (new DepotPrometheusMetricsHandler(" "))
        .observeHistogram("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Histogram;", 1L, 1L, "42"));
    assertThrows(UnsupportedOperationException.class,
        () -> (new DepotPrometheusMetricsHandler("Please register the histogram first if you need labels"))
            .observeHistogram("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Histogram;", 1L, 1L, "42"));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("Prefix");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge2() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge3() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("_");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge4() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("\\s");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge5() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge6() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(null);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge7() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/Object;)Ljava/lang/Object;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge8() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("42");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge9() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge10() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge11() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge12() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("Prefix");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge13() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("Prefix");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("_", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge14() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("Prefix");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge15() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("Prefix");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(null, 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge16() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("Prefix");

    ArrayList<String> labelValues = new ArrayList<>();
    labelValues.add(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, labelValues));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge17() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("Prefix");

    ArrayList<String> labelValues = new ArrayList<>();
    labelValues.add("_");
    labelValues.add(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, labelValues));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge18() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Histogram;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("Gauge Name", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge19() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge20() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("_", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge21() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("\\s", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge22() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge23() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(null, 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge24() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("(Ljava/lang/Object;)Ljava/lang/Object;", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge25() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("42", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge26() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge27() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge28() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge29() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("_");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge30() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("_");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge31() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("\\s");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge32() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("\\s");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge33() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge34() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("_", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge35() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("\\s", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge36() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge37() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(null, 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge38() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("(Ljava/lang/Object;)Ljava/lang/Object;", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge39() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("42", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge40() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge41() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge("", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge42() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "Please register the gauge first if you need labels");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge43() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(null);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge44() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(null);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge45() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/Object;)Ljava/lang/Object;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge46() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/Object;)Ljava/lang/Object;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge47() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("42");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge48() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("42");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge49() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge50() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Counter;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge51() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge52() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler("");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge53() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge54() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Gauge;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge55() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Histogram;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> depotPrometheusMetricsHandler.setGauge(" ", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge56() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(
        "(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Histogram;");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("Please register the gauge first if you need labels", 10.0d, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DepotPrometheusMetricsHandler#setGauge(String, double, List)}
   */
  @Test
  void testSetGauge57() {
    // Arrange
    DepotPrometheusMetricsHandler depotPrometheusMetricsHandler = new DepotPrometheusMetricsHandler(" ");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> depotPrometheusMetricsHandler
        .setGauge("(Ljava/lang/String;Ljava/lang/String;)Lio/prometheus/client/Histogram;", 10.0d, new ArrayList<>()));
  }
}
