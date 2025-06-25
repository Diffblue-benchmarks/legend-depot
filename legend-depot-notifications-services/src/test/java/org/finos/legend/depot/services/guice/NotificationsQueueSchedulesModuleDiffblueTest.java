package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.finos.legend.depot.services.api.notifications.NotificationHandler;
import org.finos.legend.depot.services.api.notifications.queue.QueueManagerConfiguration;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.notifications.NotificationsQueueManager;
import org.finos.legend.depot.store.mongo.notifications.NotificationsMongo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Disabled("Failed pitest")
class NotificationsQueueSchedulesModuleDiffblueTest {
  /**
   * Test {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)}.
   * <ul>
   *   <li>When {@link QueueManagerConfiguration} {@link QueueManagerConfiguration#getNumberOfQueueWorkers()} return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)}
   */
  @Test
  @DisplayName("Test initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager); when QueueManagerConfiguration getNumberOfQueueWorkers() return zero")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "boolean NotificationsQueueSchedulesModule.initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)"})
  void testInitQueue_whenQueueManagerConfigurationGetNumberOfQueueWorkersReturnZero() {
    // Arrange
    NotificationsQueueSchedulesModule notificationsQueueSchedulesModule = new NotificationsQueueSchedulesModule();
    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    QueueManagerConfiguration config = mock(QueueManagerConfiguration.class);
    when(config.getNumberOfQueueWorkers()).thenReturn(0L);
    doNothing().when(config).setNumberOfQueueWorkers(anyLong());
    doNothing().when(config).setQueueDelay(anyLong());
    doNothing().when(config).setQueueInterval(anyLong());
    config.setNumberOfQueueWorkers(1L);
    config.setQueueDelay(1L);
    config.setQueueInterval(42L);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    NotificationsMongo notifications = new NotificationsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> notificationsQueueSchedulesModule.initQueue(schedulesFactory,
        config, new NotificationsQueueManager(notifications, new VoidQueue(), mock(NotificationHandler.class))));
    verify(config).getNumberOfQueueWorkers();
    verify(config).setNumberOfQueueWorkers(eq(1L));
    verify(config).setQueueDelay(eq(1L));
    verify(config).setQueueInterval(eq(42L));
  }

  /**
   * Test {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)}.
   * <ul>
   *   <li>When {@link SchedulesFactory} {@link SchedulesFactory#register(String, long, long, Supplier)} does nothing.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)}
   */
  @Test
  @DisplayName("Test initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager); when SchedulesFactory register(String, long, long, Supplier) does nothing; then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "boolean NotificationsQueueSchedulesModule.initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)"})
  void testInitQueue_whenSchedulesFactoryRegisterDoesNothing_thenReturnTrue() {
    // Arrange
    NotificationsQueueSchedulesModule notificationsQueueSchedulesModule = new NotificationsQueueSchedulesModule();
    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing().when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());

    QueueManagerConfiguration config = new QueueManagerConfiguration();
    config.setNumberOfQueueWorkers(1L);
    config.setQueueDelay(1L);
    config.setQueueInterval(42L);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    NotificationsMongo notifications = new NotificationsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act
    boolean actualInitQueueResult = notificationsQueueSchedulesModule.initQueue(schedulesFactory, config,
        new NotificationsQueueManager(notifications, new VoidQueue(), mock(NotificationHandler.class)));

    // Assert
    verify(schedulesFactory).register(eq("queue-observer_1"), eq(1L), eq(42L), isA(Supplier.class));
    assertTrue(actualInitQueueResult);
  }

  /**
   * Test {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)}.
   * <ul>
   *   <li>When {@link SchedulesFactory} {@link SchedulesFactory#register(String, long, long, Supplier)} throw {@link IllegalArgumentException#IllegalArgumentException(String)} with {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)}
   */
  @Test
  @DisplayName("Test initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager); when SchedulesFactory register(String, long, long, Supplier) throw IllegalArgumentException(String) with 'foo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "boolean NotificationsQueueSchedulesModule.initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)"})
  void testInitQueue_whenSchedulesFactoryRegisterThrowIllegalArgumentExceptionWithFoo() {
    // Arrange
    NotificationsQueueSchedulesModule notificationsQueueSchedulesModule = new NotificationsQueueSchedulesModule();
    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doThrow(new IllegalArgumentException("foo")).when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());

    QueueManagerConfiguration config = new QueueManagerConfiguration();
    config.setQueueDelay(1L);
    config.setQueueInterval(42L);
    config.setNumberOfQueueWorkers(1L);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    NotificationsMongo notifications = new NotificationsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> notificationsQueueSchedulesModule.initQueue(schedulesFactory,
        config, new NotificationsQueueManager(notifications, new VoidQueue(), mock(NotificationHandler.class))));
    verify(schedulesFactory).register(eq("queue-observer_1"), eq(1L), eq(42L), isA(Supplier.class));
  }
}
