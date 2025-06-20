package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
   * Test {@link NotificationsSchedulesModule#notificationsCleanUp(SchedulesFactory, NotificationsService)}.
   * <ul>
   *   <li>When {@link SchedulesFactory} {@link SchedulesFactory#register(String, long, long, Supplier)} does nothing.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationsSchedulesModule#notificationsCleanUp(SchedulesFactory, NotificationsService)}
   */
  @Test
  @DisplayName("Test notificationsCleanUp(SchedulesFactory, NotificationsService); when SchedulesFactory register(String, long, long, Supplier) does nothing; then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "boolean NotificationsSchedulesModule.notificationsCleanUp(SchedulesFactory, NotificationsService)"})
  void testNotificationsCleanUp_whenSchedulesFactoryRegisterDoesNothing_thenReturnTrue() {
    // Arrange
    NotificationsSchedulesModule notificationsSchedulesModule = new NotificationsSchedulesModule();
    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing().when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    boolean actualNotificationsCleanUpResult = notificationsSchedulesModule.notificationsCleanUp(schedulesFactory,
        new NotificationsServiceImpl(new NotificationsMongo(new MongoDatabaseImpl("Name", codecRegistry, readPreference,
            writeConcern, true, true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
            mock(OperationExecutor.class)))));

    // Assert
    verify(schedulesFactory).register(eq("clean-notifications-schedule"), eq(6000L), eq(3600000L), isA(Supplier.class));
    assertTrue(actualNotificationsCleanUpResult);
  }
}
