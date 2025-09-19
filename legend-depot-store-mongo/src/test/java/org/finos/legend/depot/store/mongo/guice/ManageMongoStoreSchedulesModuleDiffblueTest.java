package org.finos.legend.depot.store.mongo.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.function.Supplier;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ManageMongoStoreSchedulesModuleDiffblueTest {
  /**
   * Test {@link ManageMongoStoreSchedulesModule#scheduleStorageMetrics(SchedulesFactory,
   * StorageMetricsHandler)}.
   *
   * <ul>
   *   <li>Then calls {@link StorageMetricsHandler#init()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ManageMongoStoreSchedulesModule#scheduleStorageMetrics(SchedulesFactory,
   * StorageMetricsHandler)}
   */
  @Test
  @DisplayName(
      "Test scheduleStorageMetrics(SchedulesFactory, StorageMetricsHandler); then calls init()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ManageMongoStoreSchedulesModule.scheduleStorageMetrics(SchedulesFactory, StorageMetricsHandler)"
  })
  void testScheduleStorageMetrics_thenCallsInit() {
    // Arrange
    ManageMongoStoreSchedulesModule manageMongoStoreSchedulesModule =
        new ManageMongoStoreSchedulesModule();

    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing()
        .when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());

    StorageMetricsHandler storageMetrics = mock(StorageMetricsHandler.class);
    doNothing().when(storageMetrics).init();

    // Act
    boolean actualScheduleStorageMetricsResult =
        manageMongoStoreSchedulesModule.scheduleStorageMetrics(schedulesFactory, storageMetrics);

    // Assert
    verify(schedulesFactory)
        .register(eq("storage-metrics"), eq(30000L), eq(30000L), isA(Supplier.class));
    verify(storageMetrics).init();
    assertTrue(actualScheduleStorageMetricsResult);
  }

  /**
   * Test {@link ManageMongoStoreSchedulesModule#scheduleStorageMetrics(SchedulesFactory,
   * StorageMetricsHandler)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ManageMongoStoreSchedulesModule#scheduleStorageMetrics(SchedulesFactory,
   * StorageMetricsHandler)}
   */
  @Test
  @DisplayName(
      "Test scheduleStorageMetrics(SchedulesFactory, StorageMetricsHandler); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ManageMongoStoreSchedulesModule.scheduleStorageMetrics(SchedulesFactory, StorageMetricsHandler)"
  })
  void testScheduleStorageMetrics_thenReturnTrue() {
    // Arrange
    ManageMongoStoreSchedulesModule manageMongoStoreSchedulesModule =
        new ManageMongoStoreSchedulesModule();

    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing()
        .when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());
    MongoAdminStore adminStore = new MongoAdminStore(null);
    StorageMetricsHandler storageMetrics =
        new StorageMetricsHandler(adminStore, new VoidPrometheusMetricsHandler());

    // Act
    boolean actualScheduleStorageMetricsResult =
        manageMongoStoreSchedulesModule.scheduleStorageMetrics(schedulesFactory, storageMetrics);

    // Assert
    verify(schedulesFactory)
        .register(eq("storage-metrics"), eq(30000L), eq(30000L), isA(Supplier.class));
    assertTrue(actualScheduleStorageMetricsResult);
  }
}
