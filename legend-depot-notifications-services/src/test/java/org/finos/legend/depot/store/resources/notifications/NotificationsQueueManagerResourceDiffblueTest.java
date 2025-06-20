package org.finos.legend.depot.store.resources.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.security.Principal;
import javax.inject.Provider;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.services.api.notifications.NotificationHandler;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.notifications.NotificationsQueueManager;
import org.finos.legend.depot.store.mongo.notifications.NotificationsMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationsQueueManagerResourceDiffblueTest {
  /**
   * Test {@link NotificationsQueueManagerResource#getAllEventsInQueueCount()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueManagerResource#getAllEventsInQueueCount()}
   */
  @Test
  @DisplayName("Test getAllEventsInQueueCount(); then return zero")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long NotificationsQueueManagerResource.getAllEventsInQueueCount()"})
  void testGetAllEventsInQueueCount_thenReturnZero() {
    // Arrange
    NotificationsMongo notifications = new NotificationsMongo(null);
    NotificationsQueueManager notificationsManager = new NotificationsQueueManager(notifications, new VoidQueue(),
        mock(NotificationHandler.class));

    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    Provider<Principal> principalProvider = mock(Provider.class);

    // Act and Assert
    assertEquals(0L, new NotificationsQueueManagerResource(notificationsManager, authorisationProvider,
        principalProvider, new VoidQueue()).getAllEventsInQueueCount());
  }

  /**
   * Test {@link NotificationsQueueManagerResource#purgeQueue()}.
   * <ul>
   *   <li>Given {@link AuthorisationProvider} {@link AuthorisationProvider#authorise(Provider, String)} does nothing.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueManagerResource#purgeQueue()}
   */
  @Test
  @DisplayName("Test purgeQueue(); given AuthorisationProvider authorise(Provider, String) does nothing; then return zero")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long NotificationsQueueManagerResource.purgeQueue()"})
  void testPurgeQueue_givenAuthorisationProviderAuthoriseDoesNothing_thenReturnZero() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing().when(authorisationProvider).authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());
    NotificationsMongo notifications = new NotificationsMongo(null);
    NotificationsQueueManager notificationsManager = new NotificationsQueueManager(notifications, new VoidQueue(),
        mock(NotificationHandler.class));

    Provider<Principal> principalProvider = mock(Provider.class);

    // Act
    long actualPurgeQueueResult = new NotificationsQueueManagerResource(notificationsManager, authorisationProvider,
        principalProvider, new VoidQueue()).purgeQueue();

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Notifications"));
    assertEquals(0L, actualPurgeQueueResult);
  }
}
