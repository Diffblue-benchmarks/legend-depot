package org.finos.legend.depot.services.notifications;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Optional;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.services.api.notifications.NotificationHandler;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.store.mongo.notifications.NotificationsMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationsQueueManagerDiffblueTest {
  /**
   * Test {@link NotificationsQueueManager#handle()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManager#handle()}
   */
  @Test
  @DisplayName("Test handle(); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int NotificationsQueueManager.handle()"})
  void testHandle_thenReturnZero() {
    // Arrange
    NotificationsMongo notifications = new NotificationsMongo(null);
    NotificationsQueueManager notificationsQueueManager =
        new NotificationsQueueManager(
            notifications, new VoidQueue(), mock(NotificationHandler.class));

    // Act and Assert
    assertEquals(0, notificationsQueueManager.handle());
  }

  /**
   * Test {@link NotificationsQueueManager#handle()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManager#handle()}
   */
  @Test
  @DisplayName("Test handle(); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int NotificationsQueueManager.handle()"})
  void testHandle_thenReturnZero2() {
    // Arrange
    NotificationsMongo notifications = new NotificationsMongo(null);
    NotificationsQueueManager notificationsQueueManager =
        new NotificationsQueueManager(
            notifications, new VoidQueue(), mock(NotificationHandler.class));

    // Act and Assert
    assertEquals(0, notificationsQueueManager.handle());
  }

  /**
   * Test {@link NotificationsQueueManager#handleEvent(MetadataNotification)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManager#handleEvent(MetadataNotification)}
   */
  @Test
  @DisplayName("Test handleEvent(MetadataNotification); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void NotificationsQueueManager.handleEvent(MetadataNotification)"})
  void testHandleEvent_thenThrowIllegalArgumentException() {
    // Arrange
    NotificationHandler eventHandler = mock(NotificationHandler.class);
    when(eventHandler.validate(Mockito.<MetadataNotification>any()))
        .thenThrow(new IllegalArgumentException());
    NotificationsMongo notifications = new NotificationsMongo(null);

    NotificationsQueueManager notificationsQueueManager =
        new NotificationsQueueManager(notifications, new VoidQueue(), eventHandler);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> notificationsQueueManager.handleEvent(new MetadataNotification()));
    verify(eventHandler).validate(isA(MetadataNotification.class));
  }

  /**
   * Test {@link NotificationsQueueManager#notify(String, String, String, String)} with {@code
   * String}, {@code String}, {@code String}, {@code String}.
   *
   * <p>Method under test: {@link NotificationsQueueManager#notify(String, String, String, String)}
   */
  @Test
  @DisplayName(
      "Test notify(String, String, String, String) with 'String', 'String', 'String', 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String NotificationsQueueManager.notify(String, String, String, String)"})
  void testNotifyWithStringStringStringString() {
    // Arrange
    NotificationHandler eventHandler = mock(NotificationHandler.class);
    when(eventHandler.validate(Mockito.<MetadataNotification>any()))
        .thenThrow(new IllegalArgumentException());
    NotificationsMongo notifications = new NotificationsMongo(null);

    NotificationsQueueManager notificationsQueueManager =
        new NotificationsQueueManager(notifications, new VoidQueue(), eventHandler);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> notificationsQueueManager.notify("myproject", "42", "42", "42"));
    verify(eventHandler).validate(isA(MetadataNotification.class));
  }

  /**
   * Test {@link NotificationsQueueManager#notify(String, String, String, String)} with {@code
   * String}, {@code String}, {@code String}, {@code String}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       NotificationsQueueManager#NOTIFICATIONS_COUNTER}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManager#notify(String, String, String, String)}
   */
  @Test
  @DisplayName(
      "Test notify(String, String, String, String) with 'String', 'String', 'String', 'String'; given ArrayList() add NOTIFICATIONS_COUNTER")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String NotificationsQueueManager.notify(String, String, String, String)"})
  void testNotifyWithStringStringStringString_givenArrayListAddNotifications_counter() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add(NotificationsQueueManager.NOTIFICATIONS_COUNTER);

    NotificationHandler eventHandler = mock(NotificationHandler.class);
    when(eventHandler.validate(Mockito.<MetadataNotification>any())).thenReturn(stringList);
    NotificationsMongo notifications = new NotificationsMongo(null);

    NotificationsQueueManager notificationsQueueManager =
        new NotificationsQueueManager(notifications, new VoidQueue(), eventHandler);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> notificationsQueueManager.notify("myproject", "42", "42", "42"));
    verify(eventHandler).validate(isA(MetadataNotification.class));
  }

  /**
   * Test {@link NotificationsQueueManager#notify(String, String, String, String)} with {@code
   * String}, {@code String}, {@code String}, {@code String}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManager#notify(String, String, String, String)}
   */
  @Test
  @DisplayName(
      "Test notify(String, String, String, String) with 'String', 'String', 'String', 'String'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String NotificationsQueueManager.notify(String, String, String, String)"})
  void testNotifyWithStringStringStringString_thenReturnNull() {
    // Arrange
    NotificationHandler eventHandler = mock(NotificationHandler.class);
    when(eventHandler.validate(Mockito.<MetadataNotification>any())).thenReturn(new ArrayList<>());
    NotificationsMongo notifications = new NotificationsMongo(null);

    NotificationsQueueManager notificationsQueueManager =
        new NotificationsQueueManager(notifications, new VoidQueue(), eventHandler);

    // Act
    String actualNotifyResult = notificationsQueueManager.notify("myproject", "42", "42", "42");

    // Assert
    verify(eventHandler).validate(isA(MetadataNotification.class));
    assertNull(actualNotifyResult);
  }

  /**
   * Test {@link NotificationsQueueManager#handleAll()}.
   *
   * <p>Method under test: {@link NotificationsQueueManager#handleAll()}
   */
  @Test
  @DisplayName("Test handleAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void NotificationsQueueManager.handleAll()"})
  void testHandleAll() {
    // Arrange
    MetadataNotification metadataNotification = mock(MetadataNotification.class);
    when(metadataNotification.retriesExceeded()).thenThrow(new IllegalArgumentException());
    when(metadataNotification.increaseAttempts()).thenThrow(new IllegalArgumentException());
    Optional<MetadataNotification> ofResult = Optional.of(metadataNotification);

    VoidQueue queue = mock(VoidQueue.class);
    when(queue.getFirstInQueue()).thenReturn(ofResult);

    NotificationHandler eventHandler = mock(NotificationHandler.class);
    when(eventHandler.validate(Mockito.<MetadataNotification>any())).thenReturn(new ArrayList<>());

    NotificationsQueueManager notificationsQueueManager =
        new NotificationsQueueManager(new NotificationsMongo(null), queue, eventHandler);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> notificationsQueueManager.handleAll());
    verify(metadataNotification).increaseAttempts();
    verify(metadataNotification).retriesExceeded();
    verify(eventHandler).validate(isA(MetadataNotification.class));
    verify(queue).getFirstInQueue();
  }

  /**
   * Test {@link NotificationsQueueManager#handleAll()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       NotificationsQueueManager#NOTIFICATIONS_COUNTER}.
   *   <li>Then calls {@link MetadataNotification#getEventId()}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManager#handleAll()}
   */
  @Test
  @DisplayName(
      "Test handleAll(); given ArrayList() add NOTIFICATIONS_COUNTER; then calls getEventId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void NotificationsQueueManager.handleAll()"})
  void testHandleAll_givenArrayListAddNotifications_counter_thenCallsGetEventId() {
    // Arrange
    MetadataNotification metadataNotification = mock(MetadataNotification.class);
    when(metadataNotification.getEventId()).thenThrow(new IllegalArgumentException());
    Optional<MetadataNotification> ofResult = Optional.of(metadataNotification);

    VoidQueue queue = mock(VoidQueue.class);
    when(queue.getFirstInQueue()).thenReturn(ofResult);

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add(NotificationsQueueManager.NOTIFICATIONS_COUNTER);

    NotificationHandler eventHandler = mock(NotificationHandler.class);
    when(eventHandler.validate(Mockito.<MetadataNotification>any())).thenReturn(stringList);

    NotificationsQueueManager notificationsQueueManager =
        new NotificationsQueueManager(new NotificationsMongo(null), queue, eventHandler);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> notificationsQueueManager.handleAll());
    verify(metadataNotification).getEventId();
    verify(eventHandler).validate(isA(MetadataNotification.class));
    verify(queue).getFirstInQueue();
  }

  /**
   * Test {@link NotificationsQueueManager#handleAll()}.
   *
   * <ul>
   *   <li>Given {@link MetadataNotification} {@link MetadataNotification#retriesExceeded()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManager#handleAll()}
   */
  @Test
  @DisplayName("Test handleAll(); given MetadataNotification retriesExceeded() return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void NotificationsQueueManager.handleAll()"})
  void testHandleAll_givenMetadataNotificationRetriesExceededReturnFalse() {
    // Arrange
    MetadataNotification metadataNotification = mock(MetadataNotification.class);
    when(metadataNotification.retriesExceeded()).thenReturn(false);
    when(metadataNotification.getEventId()).thenThrow(new IllegalArgumentException());
    when(metadataNotification.increaseAttempts()).thenThrow(new IllegalArgumentException());
    Optional<MetadataNotification> ofResult = Optional.of(metadataNotification);

    VoidQueue queue = mock(VoidQueue.class);
    when(queue.getFirstInQueue()).thenReturn(ofResult);

    NotificationHandler eventHandler = mock(NotificationHandler.class);
    when(eventHandler.validate(Mockito.<MetadataNotification>any())).thenReturn(new ArrayList<>());

    NotificationsQueueManager notificationsQueueManager =
        new NotificationsQueueManager(new NotificationsMongo(null), queue, eventHandler);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> notificationsQueueManager.handleAll());
    verify(metadataNotification).getEventId();
    verify(metadataNotification).increaseAttempts();
    verify(metadataNotification).retriesExceeded();
    verify(eventHandler).validate(isA(MetadataNotification.class));
    verify(queue).getFirstInQueue();
  }

  /**
   * Test {@link NotificationsQueueManager#handleAll()}.
   *
   * <ul>
   *   <li>Given {@link MetadataNotification} {@link MetadataNotification#retriesExceeded()} return
   *       {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManager#handleAll()}
   */
  @Test
  @DisplayName("Test handleAll(); given MetadataNotification retriesExceeded() return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void NotificationsQueueManager.handleAll()"})
  void testHandleAll_givenMetadataNotificationRetriesExceededReturnTrue() {
    // Arrange
    MetadataNotification metadataNotification = mock(MetadataNotification.class);
    when(metadataNotification.retriesExceeded()).thenReturn(true);
    when(metadataNotification.getEventId()).thenThrow(new IllegalArgumentException());
    when(metadataNotification.increaseAttempts()).thenThrow(new IllegalArgumentException());
    Optional<MetadataNotification> ofResult = Optional.of(metadataNotification);

    VoidQueue queue = mock(VoidQueue.class);
    when(queue.getFirstInQueue()).thenReturn(ofResult);

    NotificationHandler eventHandler = mock(NotificationHandler.class);
    when(eventHandler.validate(Mockito.<MetadataNotification>any())).thenReturn(new ArrayList<>());

    NotificationsQueueManager notificationsQueueManager =
        new NotificationsQueueManager(new NotificationsMongo(null), queue, eventHandler);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> notificationsQueueManager.handleAll());
    verify(metadataNotification).getEventId();
    verify(metadataNotification).increaseAttempts();
    verify(metadataNotification).retriesExceeded();
    verify(eventHandler).validate(isA(MetadataNotification.class));
    verify(queue).getFirstInQueue();
  }

  /**
   * Test {@link NotificationsQueueManager#handleAll()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsQueueManager#handleAll()}
   */
  @Test
  @DisplayName("Test handleAll(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void NotificationsQueueManager.handleAll()"})
  void testHandleAll_thenDoesNotThrow() {
    // Arrange
    NotificationsMongo notifications = new NotificationsMongo(null);
    NotificationsQueueManager notificationsQueueManager =
        new NotificationsQueueManager(
            notifications, new VoidQueue(), mock(NotificationHandler.class));

    // Act and Assert
    assertDoesNotThrow(() -> notificationsQueueManager.handleAll());
  }
}
