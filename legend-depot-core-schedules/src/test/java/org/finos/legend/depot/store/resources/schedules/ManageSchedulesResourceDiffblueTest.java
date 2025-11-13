package org.finos.legend.depot.store.resources.schedules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.security.Principal;
import java.util.Set;
import javax.inject.Provider;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.schedules.SchedulesFactoryImpl;
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ManageSchedulesResourceDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ManageSchedulesResource#ManageSchedulesResource(AuthorisationProvider, Provider,
   *       SchedulesFactory, SchedulesStore, ScheduleInstancesStore)}
   *   <li>{@link ManageSchedulesResource#getResourceName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ManageSchedulesResource.<init>(AuthorisationProvider, Provider, SchedulesFactory, SchedulesStore, ScheduleInstancesStore)",
    "String ManageSchedulesResource.getResourceName()"
  })
  void testGettersAndSetters() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    Provider<Principal> principalProvider = mock(Provider.class);
    SchedulesFactoryImpl schedulesFactory =
        new SchedulesFactoryImpl(
            mock(SchedulesStore.class), mock(ScheduleInstancesStore.class), true);

    // Act
    ManageSchedulesResource actualManageSchedulesResource =
        new ManageSchedulesResource(
            authorisationProvider,
            principalProvider,
            schedulesFactory,
            mock(SchedulesStore.class),
            mock(ScheduleInstancesStore.class));

    // Assert
    assertEquals(
        ManageSchedulesResource.SCHEDULES_RESOURCE,
        actualManageSchedulesResource.getResourceName());
  }

  /**
   * Test {@link ManageSchedulesResource#deleteScheduler(String)}.
   *
   * <ul>
   *   <li>Then StatusInfo return {@link Status}.
   * </ul>
   *
   * <p>Method under test: {@link ManageSchedulesResource#deleteScheduler(String)}
   */
  @Test
  @DisplayName("Test deleteScheduler(String); then StatusInfo return Status")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response ManageSchedulesResource.deleteScheduler(String)"})
  void testDeleteScheduler_thenStatusInfoReturnStatus() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    doNothing().when(manageSchedulesService).delete(Mockito.<String>any());
    SchedulesFactoryImpl schedulesFactory =
        new SchedulesFactoryImpl(manageSchedulesService, mock(ScheduleInstancesStore.class), true);

    ManageSchedulesResource manageSchedulesResource =
        new ManageSchedulesResource(
            authorisationProvider,
            mock(Provider.class),
            schedulesFactory,
            mock(SchedulesStore.class),
            mock(ScheduleInstancesStore.class));

    // Act
    Response actualDeleteSchedulerResult = manageSchedulesResource.deleteScheduler("Schedule Name");

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(manageSchedulesService).delete("Schedule Name");
    StatusType statusInfo = actualDeleteSchedulerResult.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    assertTrue(actualDeleteSchedulerResult instanceof OutboundJaxrsResponse);
    assertNull(actualDeleteSchedulerResult.getEntity());
    assertNull(actualDeleteSchedulerResult.getLocation());
    assertNull(actualDeleteSchedulerResult.getDate());
    assertNull(actualDeleteSchedulerResult.getLastModified());
    assertNull(actualDeleteSchedulerResult.getLanguage());
    assertNull(actualDeleteSchedulerResult.getEntityTag());
    assertNull(actualDeleteSchedulerResult.getMediaType());
    assertEquals(-1, actualDeleteSchedulerResult.getLength());
    assertEquals(204, actualDeleteSchedulerResult.getStatus());
    assertEquals(Status.NO_CONTENT, statusInfo);
    assertTrue(actualDeleteSchedulerResult.getCookies().isEmpty());
    MultivaluedMap<String, Object> headers = actualDeleteSchedulerResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualDeleteSchedulerResult.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualDeleteSchedulerResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(allowedMethods, actualDeleteSchedulerResult.getLinks());
    assertSame(headers, actualDeleteSchedulerResult.getMetadata());
  }
}
