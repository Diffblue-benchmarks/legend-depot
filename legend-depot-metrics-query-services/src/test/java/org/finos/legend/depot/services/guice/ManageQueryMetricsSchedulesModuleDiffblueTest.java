package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsService;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.metrics.query.QueryMetricsServiceImpl;
import org.finos.legend.depot.store.mongo.metrics.query.QueryMetricsMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ManageQueryMetricsSchedulesModuleDiffblueTest {
  /**
   * Test {@link ManageQueryMetricsSchedulesModule#scheduleMetricsConsolidation(SchedulesFactory, QueryMetricsService)}.
   * <p>
   * Method under test: {@link ManageQueryMetricsSchedulesModule#scheduleMetricsConsolidation(SchedulesFactory, QueryMetricsService)}
   */
  @Test
  @DisplayName("Test scheduleMetricsConsolidation(SchedulesFactory, QueryMetricsService)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "boolean ManageQueryMetricsSchedulesModule.scheduleMetricsConsolidation(SchedulesFactory, QueryMetricsService)"})
  void testScheduleMetricsConsolidation() {
    // Arrange
    ManageQueryMetricsSchedulesModule manageQueryMetricsSchedulesModule = new ManageQueryMetricsSchedulesModule();
    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing().when(schedulesFactory)
        .registerSingleInstance(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    boolean actualScheduleMetricsConsolidationResult = manageQueryMetricsSchedulesModule.scheduleMetricsConsolidation(
        schedulesFactory,
        new QueryMetricsServiceImpl(new QueryMetricsMongo(new MongoDatabaseImpl("Name", codecRegistry, readPreference,
            writeConcern, true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
            mock(OperationExecutor.class)))));

    // Assert
    verify(schedulesFactory).registerSingleInstance(eq("consolidate-query-metrics"), eq(6000L), eq(21600000L),
        isA(Supplier.class));
    assertTrue(actualScheduleMetricsConsolidationResult);
  }
}
