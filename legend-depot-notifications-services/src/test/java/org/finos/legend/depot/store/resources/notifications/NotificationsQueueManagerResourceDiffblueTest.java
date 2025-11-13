package org.finos.legend.depot.store.resources.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManagerResource#getAllEventsInQueueCount()}
   */
  @Test
  @DisplayName("Test getAllEventsInQueueCount(); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long NotificationsQueueManagerResource.getAllEventsInQueueCount()"})
  void testGetAllEventsInQueueCount_thenReturnZero() {
    // Arrange
    NotificationsMongo notifications = new NotificationsMongo(null);
    NotificationsQueueManager notificationsManager =
        new NotificationsQueueManager(
            notifications, new VoidQueue(), mock(NotificationHandler.class));
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    Provider<Principal> principalProvider = mock(Provider.class);

    NotificationsQueueManagerResource notificationsQueueManagerResource =
        new NotificationsQueueManagerResource(
            notificationsManager, authorisationProvider, principalProvider, new VoidQueue());

    // Act and Assert
    assertEquals(0L, notificationsQueueManagerResource.getAllEventsInQueueCount());
  }

  /**
   * Test {@link NotificationsQueueManagerResource#geEventsInQueue(String)}.
   *
   * <ul>
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManagerResource#geEventsInQueue(String)}
   */
  @Test
  @DisplayName("Test geEventsInQueue(String); then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Optional NotificationsQueueManagerResource.geEventsInQueue(String)"
  })
  void testGeEventsInQueue_thenReturnNotPresent() {
    // Arrange
    NotificationsMongo notifications = new NotificationsMongo(null);
    NotificationsQueueManager notificationsManager =
        new NotificationsQueueManager(
            notifications, new VoidQueue(), mock(NotificationHandler.class));
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    Provider<Principal> principalProvider = mock(Provider.class);

    NotificationsQueueManagerResource notificationsQueueManagerResource =
        new NotificationsQueueManagerResource(
            notificationsManager, authorisationProvider, principalProvider, new VoidQueue());

    // Act and Assert
    assertFalse(notificationsQueueManagerResource.geEventsInQueue("42").isPresent());
  }

  /**
   * Test {@link NotificationsQueueManagerResource#purgeQueue()}.
   *
   * <ul>
   *   <li>Given {@link AuthorisationProvider} {@link AuthorisationProvider#authorise(Provider,
   *       String)} does nothing.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManagerResource#purgeQueue()}
   */
  @Test
  @DisplayName(
      "Test purgeQueue(); given AuthorisationProvider authorise(Provider, String) does nothing; then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long NotificationsQueueManagerResource.purgeQueue()"})
  void testPurgeQueue_givenAuthorisationProviderAuthoriseDoesNothing_thenReturnZero() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());
    NotificationsMongo notifications = new NotificationsMongo(null);
    NotificationsQueueManager notificationsManager =
        new NotificationsQueueManager(
            notifications, new VoidQueue(), mock(NotificationHandler.class));
    Provider<Principal> principalProvider = mock(Provider.class);

    NotificationsQueueManagerResource notificationsQueueManagerResource =
        new NotificationsQueueManagerResource(
            notificationsManager, authorisationProvider, principalProvider, new VoidQueue());

    // Act
    long actualPurgeQueueResult = notificationsQueueManagerResource.purgeQueue();

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Notifications"));
    assertEquals(0L, actualPurgeQueueResult);
  }
}
