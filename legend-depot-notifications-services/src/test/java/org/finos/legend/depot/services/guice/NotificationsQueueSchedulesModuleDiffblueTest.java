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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.function.Supplier;
import org.finos.legend.depot.services.api.notifications.NotificationHandler;
import org.finos.legend.depot.services.api.notifications.queue.QueueManagerConfiguration;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.notifications.NotificationsQueueManager;
import org.finos.legend.depot.store.mongo.notifications.NotificationsMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationsQueueSchedulesModuleDiffblueTest {
  /**
   * Test {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory,
   * QueueManagerConfiguration, NotificationsQueueManager)}.
   *
   * <ul>
   *   <li>Given {@link IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory,
   * QueueManagerConfiguration, NotificationsQueueManager)}
   */
  @Test
  @DisplayName(
      "Test initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager); given IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NotificationsQueueSchedulesModule.initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)"
  })
  void testInitQueue_givenIllegalArgumentException() {
    // Arrange
    NotificationsQueueSchedulesModule notificationsQueueSchedulesModule =
        new NotificationsQueueSchedulesModule();

    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doThrow(new IllegalArgumentException())
        .when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());

    QueueManagerConfiguration config = new QueueManagerConfiguration();
    config.setNumberOfQueueWorkers(1L);
    config.setQueueDelay(1L);
    config.setQueueInterval(42L);
    NotificationsMongo notifications = new NotificationsMongo(null);
    NotificationsQueueManager notificationsManager =
        new NotificationsQueueManager(
            notifications, new VoidQueue(), mock(NotificationHandler.class));

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            notificationsQueueSchedulesModule.initQueue(
                schedulesFactory, config, notificationsManager));
    verify(schedulesFactory).register(eq("queue-observer_1"), eq(1L), eq(42L), isA(Supplier.class));
  }

  /**
   * Test {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory,
   * QueueManagerConfiguration, NotificationsQueueManager)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory,
   * QueueManagerConfiguration, NotificationsQueueManager)}
   */
  @Test
  @DisplayName(
      "Test initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NotificationsQueueSchedulesModule.initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)"
  })
  void testInitQueue_givenMinusOne() {
    // Arrange
    NotificationsQueueSchedulesModule notificationsQueueSchedulesModule =
        new NotificationsQueueSchedulesModule();
    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);

    QueueManagerConfiguration config = new QueueManagerConfiguration();
    config.setNumberOfQueueWorkers(-1L);
    config.setQueueDelay(1L);
    config.setQueueInterval(42L);
    NotificationsMongo notifications = new NotificationsMongo(null);
    NotificationsQueueManager notificationsManager =
        new NotificationsQueueManager(
            notifications, new VoidQueue(), mock(NotificationHandler.class));

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            notificationsQueueSchedulesModule.initQueue(
                schedulesFactory, config, notificationsManager));
  }

  /**
   * Test {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory,
   * QueueManagerConfiguration, NotificationsQueueManager)}.
   *
   * <ul>
   *   <li>When {@link SchedulesFactory} {@link SchedulesFactory#register(String, long, long,
   *       Supplier)} does nothing.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueSchedulesModule#initQueue(SchedulesFactory,
   * QueueManagerConfiguration, NotificationsQueueManager)}
   */
  @Test
  @DisplayName(
      "Test initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager); when SchedulesFactory register(String, long, long, Supplier) does nothing; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NotificationsQueueSchedulesModule.initQueue(SchedulesFactory, QueueManagerConfiguration, NotificationsQueueManager)"
  })
  void testInitQueue_whenSchedulesFactoryRegisterDoesNothing_thenReturnTrue() {
    // Arrange
    NotificationsQueueSchedulesModule notificationsQueueSchedulesModule =
        new NotificationsQueueSchedulesModule();

    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing()
        .when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());

    QueueManagerConfiguration config = new QueueManagerConfiguration();
    config.setNumberOfQueueWorkers(1L);
    config.setQueueDelay(1L);
    config.setQueueInterval(42L);
    NotificationsMongo notifications = new NotificationsMongo(null);
    NotificationsQueueManager notificationsManager =
        new NotificationsQueueManager(
            notifications, new VoidQueue(), mock(NotificationHandler.class));

    // Act
    boolean actualInitQueueResult =
        notificationsQueueSchedulesModule.initQueue(schedulesFactory, config, notificationsManager);

    // Assert
    verify(schedulesFactory).register(eq("queue-observer_1"), eq(1L), eq(42L), isA(Supplier.class));
    assertTrue(actualInitQueueResult);
  }
}
