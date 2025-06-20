package org.finos.legend.depot.services.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.store.mongo.notifications.NotificationsMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationsServiceImplDiffblueTest {
  /**
   * Test {@link NotificationsServiceImpl#findProcessedEvents(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsServiceImpl#findProcessedEvents(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test findProcessedEvents(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List NotificationsServiceImpl.findProcessedEvents(String, String, String, String, String, Boolean, LocalDateTime, LocalDateTime)"})
  void testFindProcessedEvents_thenReturnEmpty() {
    // Arrange
    NotificationsMongo events = mock(NotificationsMongo.class);
    when(events.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
            .thenReturn(new ArrayList<>());
    NotificationsServiceImpl notificationsServiceImpl = new NotificationsServiceImpl(events);
    LocalDateTime from = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    List<MetadataNotification> actualFindProcessedEventsResult = notificationsServiceImpl.findProcessedEvents("Group",
        "Artifact", "1.0.2", "42", "42", true, from, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(events).find(eq("Group"), eq("Artifact"), eq("1.0.2"), eq("42"), eq("42"), eq(true),
        isA(LocalDateTime.class), isA(LocalDateTime.class));
    assertTrue(actualFindProcessedEventsResult.isEmpty());
  }

  /**
   * Test {@link NotificationsServiceImpl#getProcessedEvent(String)}.
   * <p>
   * Method under test: {@link NotificationsServiceImpl#getProcessedEvent(String)}
   */
  @Test
  @DisplayName("Test getProcessedEvent(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional NotificationsServiceImpl.getProcessedEvent(String)"})
  void testGetProcessedEvent() {
    // Arrange
    NotificationsMongo events = mock(NotificationsMongo.class);
    Optional<MetadataNotification> ofResult = Optional.of(new MetadataNotification("myproject", "42", "42", "42"));
    when(events.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    Optional<MetadataNotification> actualProcessedEvent = new NotificationsServiceImpl(events).getProcessedEvent("42");

    // Assert
    verify(events).get(eq("42"));
    assertSame(ofResult, actualProcessedEvent);
  }

  /**
   * Test {@link NotificationsServiceImpl#deleteOldNotifications(long)}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsServiceImpl#deleteOldNotifications(long)}
   */
  @Test
  @DisplayName("Test deleteOldNotifications(long); then return one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long NotificationsServiceImpl.deleteOldNotifications(long)"})
  void testDeleteOldNotifications_thenReturnOne() {
    // Arrange
    ArrayList<MetadataNotification> metadataNotificationList = new ArrayList<>();
    metadataNotificationList.add(new MetadataNotification("myproject", "42", "42", "42"));
    NotificationsMongo events = mock(NotificationsMongo.class);
    doNothing().when(events).delete(Mockito.<String>any());
    when(events.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
            .thenReturn(metadataNotificationList);

    // Act
    long actualDeleteOldNotificationsResult = new NotificationsServiceImpl(events).deleteOldNotifications(1L);

    // Assert
    verify(events).delete((String) isNull());
    verify(events).find(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(LocalDateTime.class));
    assertEquals(1L, actualDeleteOldNotificationsResult);
  }

  /**
   * Test {@link NotificationsServiceImpl#deleteOldNotifications(long)}.
   * <ul>
   *   <li>Then return two.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsServiceImpl#deleteOldNotifications(long)}
   */
  @Test
  @DisplayName("Test deleteOldNotifications(long); then return two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long NotificationsServiceImpl.deleteOldNotifications(long)"})
  void testDeleteOldNotifications_thenReturnTwo() {
    // Arrange
    ArrayList<MetadataNotification> metadataNotificationList = new ArrayList<>();
    metadataNotificationList.add(new MetadataNotification("myproject", "42", "42", "42"));
    metadataNotificationList.add(new MetadataNotification("myproject", "42", "42", "42"));
    NotificationsMongo events = mock(NotificationsMongo.class);
    doNothing().when(events).delete(Mockito.<String>any());
    when(events.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
            .thenReturn(metadataNotificationList);

    // Act
    long actualDeleteOldNotificationsResult = new NotificationsServiceImpl(events).deleteOldNotifications(1L);

    // Assert
    verify(events, atLeast(1)).delete((String) isNull());
    verify(events).find(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(LocalDateTime.class));
    assertEquals(2L, actualDeleteOldNotificationsResult);
  }

  /**
   * Test {@link NotificationsServiceImpl#deleteOldNotifications(long)}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsServiceImpl#deleteOldNotifications(long)}
   */
  @Test
  @DisplayName("Test deleteOldNotifications(long); then return zero")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long NotificationsServiceImpl.deleteOldNotifications(long)"})
  void testDeleteOldNotifications_thenReturnZero() {
    // Arrange
    NotificationsMongo events = mock(NotificationsMongo.class);
    when(events.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
            .thenReturn(new ArrayList<>());

    // Act
    long actualDeleteOldNotificationsResult = new NotificationsServiceImpl(events).deleteOldNotifications(1L);

    // Assert
    verify(events).find(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(LocalDateTime.class));
    assertEquals(0L, actualDeleteOldNotificationsResult);
  }
}
