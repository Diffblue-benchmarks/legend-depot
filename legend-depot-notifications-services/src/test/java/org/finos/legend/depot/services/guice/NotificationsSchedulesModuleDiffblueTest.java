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
import java.util.function.Supplier;
import org.finos.legend.depot.services.api.notifications.NotificationsService;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.notifications.NotificationsServiceImpl;
import org.finos.legend.depot.store.mongo.notifications.NotificationsMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationsSchedulesModuleDiffblueTest {
  /**
   * Test {@link NotificationsSchedulesModule#notificationsCleanUp(SchedulesFactory,
   * NotificationsService)}.
   *
   * <ul>
   *   <li>When {@link SchedulesFactory} {@link SchedulesFactory#register(String, long, long,
   *       Supplier)} does nothing.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * NotificationsSchedulesModule#notificationsCleanUp(SchedulesFactory, NotificationsService)}
   */
  @Test
  @DisplayName(
      "Test notificationsCleanUp(SchedulesFactory, NotificationsService); when SchedulesFactory register(String, long, long, Supplier) does nothing; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NotificationsSchedulesModule.notificationsCleanUp(SchedulesFactory, NotificationsService)"
  })
  void testNotificationsCleanUp_whenSchedulesFactoryRegisterDoesNothing_thenReturnTrue() {
    // Arrange
    NotificationsSchedulesModule notificationsSchedulesModule = new NotificationsSchedulesModule();

    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing()
        .when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());

    // Act
    boolean actualNotificationsCleanUpResult =
        notificationsSchedulesModule.notificationsCleanUp(
            schedulesFactory, new NotificationsServiceImpl(new NotificationsMongo(null)));

    // Assert
    verify(schedulesFactory)
        .register(eq("clean-notifications-schedule"), eq(6000L), eq(3600000L), isA(Supplier.class));
    assertTrue(actualNotificationsCleanUpResult);
  }
}
