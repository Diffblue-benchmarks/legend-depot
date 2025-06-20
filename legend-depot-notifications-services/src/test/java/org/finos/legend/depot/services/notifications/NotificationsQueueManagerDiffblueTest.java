package org.finos.legend.depot.services.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
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
import java.util.ArrayList;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
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
   * <ul>
   *   <li>Given {@link NotificationsMongo#NotificationsMongo(MongoDatabase)} with databaseProvider is {@code null}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueManager#handle()}
   */
  @Test
  @DisplayName("Test handle(); given NotificationsMongo(MongoDatabase) with databaseProvider is 'null'; then return zero")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"int NotificationsQueueManager.handle()"})
  void testHandle_givenNotificationsMongoWithDatabaseProviderIsNull_thenReturnZero() {
    // Arrange
    NotificationsMongo notifications = new NotificationsMongo(null);

    // Act and Assert
    assertEquals(0,
        new NotificationsQueueManager(notifications, new VoidQueue(), mock(NotificationHandler.class)).handle());
  }

  /**
   * Test {@link NotificationsQueueManager#handle()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueManager#handle()}
   */
  @Test
  @DisplayName("Test handle(); then return zero")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"int NotificationsQueueManager.handle()"})
  void testHandle_thenReturnZero() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    NotificationsMongo notifications = new NotificationsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertEquals(0,
        new NotificationsQueueManager(notifications, new VoidQueue(), mock(NotificationHandler.class)).handle());
  }

  /**
   * Test {@link NotificationsQueueManager#notify(String, String, String, String)} with {@code String}, {@code String}, {@code String}, {@code String}.
   * <p>
   * Method under test: {@link NotificationsQueueManager#notify(String, String, String, String)}
   */
  @Test
  @DisplayName("Test notify(String, String, String, String) with 'String', 'String', 'String', 'String'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String NotificationsQueueManager.notify(String, String, String, String)"})
  void testNotifyWithStringStringStringString() {
    // Arrange
    NotificationHandler eventHandler = mock(NotificationHandler.class);
    when(eventHandler.validate(Mockito.<MetadataNotification>any()))
        .thenThrow(new IllegalArgumentException(NotificationsQueueManager.NOTIFICATIONS_COUNTER));
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    NotificationsMongo notifications = new NotificationsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> new NotificationsQueueManager(notifications, new VoidQueue(), eventHandler).notify("myproject", "42",
            "42", "42"));
    verify(eventHandler).validate(isA(MetadataNotification.class));
  }

  /**
   * Test {@link NotificationsQueueManager#notify(String, String, String, String)} with {@code String}, {@code String}, {@code String}, {@code String}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link NotificationsQueueManager#NOTIFICATIONS_COUNTER}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueManager#notify(String, String, String, String)}
   */
  @Test
  @DisplayName("Test notify(String, String, String, String) with 'String', 'String', 'String', 'String'; given ArrayList() add NOTIFICATIONS_COUNTER")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String NotificationsQueueManager.notify(String, String, String, String)"})
  void testNotifyWithStringStringStringString_givenArrayListAddNotifications_counter() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add(NotificationsQueueManager.NOTIFICATIONS_COUNTER);
    NotificationHandler eventHandler = mock(NotificationHandler.class);
    when(eventHandler.validate(Mockito.<MetadataNotification>any())).thenReturn(stringList);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    NotificationsMongo notifications = new NotificationsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> new NotificationsQueueManager(notifications, new VoidQueue(), eventHandler).notify("myproject", "42",
            "42", "42"));
    verify(eventHandler).validate(isA(MetadataNotification.class));
  }

  /**
   * Test {@link NotificationsQueueManager#notify(String, String, String, String)} with {@code String}, {@code String}, {@code String}, {@code String}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsQueueManager#notify(String, String, String, String)}
   */
  @Test
  @DisplayName("Test notify(String, String, String, String) with 'String', 'String', 'String', 'String'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String NotificationsQueueManager.notify(String, String, String, String)"})
  void testNotifyWithStringStringStringString_thenReturnNull() {
    // Arrange
    NotificationHandler eventHandler = mock(NotificationHandler.class);
    when(eventHandler.validate(Mockito.<MetadataNotification>any())).thenReturn(new ArrayList<>());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    NotificationsMongo notifications = new NotificationsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));

    // Act
    String actualNotifyResult = new NotificationsQueueManager(notifications, new VoidQueue(), eventHandler)
        .notify("myproject", "42", "42", "42");

    // Assert
    verify(eventHandler).validate(isA(MetadataNotification.class));
    assertNull(actualNotifyResult);
  }
}
