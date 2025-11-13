package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import java.util.function.Supplier;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsService;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.metrics.query.InMemoryQueryMetricsRegistry;
import org.finos.legend.depot.services.metrics.query.QueryMetricsServiceImpl;
import org.finos.legend.depot.store.mongo.metrics.query.QueryMetricsMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class QueryMetricsSchedulesModuleDiffblueTest {
  /**
   * Test {@link QueryMetricsSchedulesModule#scheduleMetricsPersistence(SchedulesFactory,
   * QueryMetricsRegistry, QueryMetricsService)}.
   *
   * <p>Method under test: {@link
   * QueryMetricsSchedulesModule#scheduleMetricsPersistence(SchedulesFactory, QueryMetricsRegistry,
   * QueryMetricsService)}
   */
  @Test
  @DisplayName(
      "Test scheduleMetricsPersistence(SchedulesFactory, QueryMetricsRegistry, QueryMetricsService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean QueryMetricsSchedulesModule.scheduleMetricsPersistence(SchedulesFactory, QueryMetricsRegistry, QueryMetricsService)"
  })
  void testScheduleMetricsPersistence() {
    // Arrange
    QueryMetricsSchedulesModule queryMetricsSchedulesModule = new QueryMetricsSchedulesModule();

    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing()
        .when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());
    InMemoryQueryMetricsRegistry metricsRegistry = new InMemoryQueryMetricsRegistry();
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern();

    MongoDatabaseImpl databaseProvider =
        new MongoDatabaseImpl(
            "Name",
            codecRegistry,
            readPreference,
            writeConcern,
            true,
            true,
            new ReadConcern(ReadConcernLevel.LOCAL),
            UuidRepresentation.UNSPECIFIED,
            mock(OperationExecutor.class));

    // Act
    boolean actualScheduleMetricsPersistenceResult =
        queryMetricsSchedulesModule.scheduleMetricsPersistence(
            schedulesFactory,
            metricsRegistry,
            new QueryMetricsServiceImpl(new QueryMetricsMongo(databaseProvider)));

    // Assert
    verify(schedulesFactory)
        .register(eq("persist-query-metrics"), eq(6000L), eq(30000L), isA(Supplier.class));
    assertTrue(actualScheduleMetricsPersistenceResult);
  }
}
