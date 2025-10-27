package org.finos.legend.depot.store.mongo.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import java.util.function.Supplier;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.finos.legend.depot.store.mongo.admin.metrics.StorageMetricsHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManageMongoStoreSchedulesModuleDiffblueTest {
  @InjectMocks
  private ManageMongoStoreSchedulesModule manageMongoStoreSchedulesModule;

  /**
   * Method under test:
   * {@link ManageMongoStoreSchedulesModule#scheduleStorageMetrics(SchedulesFactory, StorageMetricsHandler)}
   */
  @Test
  void testScheduleStorageMetrics() {
    // Arrange
    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing().when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    MongoAdminStore adminStore = new MongoAdminStore(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act
    boolean actualScheduleStorageMetricsResult = manageMongoStoreSchedulesModule.scheduleStorageMetrics(
        schedulesFactory, new StorageMetricsHandler(adminStore, new VoidPrometheusMetricsHandler()));

    // Assert
    verify(schedulesFactory).register(eq("storage-metrics"), eq(30000L), eq(30000L), isA(Supplier.class));
    assertTrue(actualScheduleStorageMetricsResult);
  }

  /**
   * Method under test:
   * {@link ManageMongoStoreSchedulesModule#scheduleStorageMetrics(SchedulesFactory, StorageMetricsHandler)}
   */
  @Test
  void testScheduleStorageMetrics2() {
    // Arrange
    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing().when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());
    StorageMetricsHandler storageMetrics = mock(StorageMetricsHandler.class);
    doNothing().when(storageMetrics).init();

    // Act
    boolean actualScheduleStorageMetricsResult = manageMongoStoreSchedulesModule
        .scheduleStorageMetrics(schedulesFactory, storageMetrics);

    // Assert
    verify(schedulesFactory).register(eq("storage-metrics"), eq(30000L), eq(30000L), isA(Supplier.class));
    verify(storageMetrics).init();
    assertTrue(actualScheduleStorageMetricsResult);
  }
}
