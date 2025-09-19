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
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInstance;
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
   * Test {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}.
   *
   * <p>Method under test: {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}
   */
  @Test
  @DisplayName("Test getSchedulerStatus(Boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ManageSchedulesResource.getSchedulerStatus(Boolean)"})
  void testGetSchedulerStatus() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    ScheduleInfo scheduleInfo = new ScheduleInfo("get schedule status");
    scheduleInfo.setDisabled(true);

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);
    Provider<Principal> principalProvider = mock(Provider.class);
    SchedulesFactoryImpl schedulesFactory =
        new SchedulesFactoryImpl(
            mock(SchedulesStore.class), mock(ScheduleInstancesStore.class), true);

    ManageSchedulesResource manageSchedulesResource =
        new ManageSchedulesResource(
            authorisationProvider,
            principalProvider,
            schedulesFactory,
            manageSchedulesService,
            mock(ScheduleInstancesStore.class));

    // Act
    List<ScheduleInfo> actualSchedulerStatus = manageSchedulesResource.getSchedulerStatus(true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(manageSchedulesService).getAll();
    assertEquals(scheduleInfoList, actualSchedulerStatus);
  }

  /**
   * Test {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}
   */
  @Test
  @DisplayName("Test getSchedulerStatus(Boolean); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ManageSchedulesResource.getSchedulerStatus(Boolean)"})
  void testGetSchedulerStatus_thenReturnEmpty() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("get schedule status"));

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);
    Provider<Principal> principalProvider = mock(Provider.class);
    SchedulesFactoryImpl schedulesFactory =
        new SchedulesFactoryImpl(
            mock(SchedulesStore.class), mock(ScheduleInstancesStore.class), true);

    ManageSchedulesResource manageSchedulesResource =
        new ManageSchedulesResource(
            authorisationProvider,
            principalProvider,
            schedulesFactory,
            manageSchedulesService,
            mock(ScheduleInstancesStore.class));

    // Act
    List<ScheduleInfo> actualSchedulerStatus = manageSchedulesResource.getSchedulerStatus(true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(manageSchedulesService).getAll();
    assertTrue(actualSchedulerStatus.isEmpty());
  }

  /**
   * Test {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}
   */
  @Test
  @DisplayName("Test getSchedulerStatus(Boolean); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ManageSchedulesResource.getSchedulerStatus(Boolean)"})
  void testGetSchedulerStatus_thenReturnEmpty2() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("get schedule status"));
    scheduleInfoList.add(new ScheduleInfo("get schedule status"));

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);
    Provider<Principal> principalProvider = mock(Provider.class);
    SchedulesFactoryImpl schedulesFactory =
        new SchedulesFactoryImpl(
            mock(SchedulesStore.class), mock(ScheduleInstancesStore.class), true);

    ManageSchedulesResource manageSchedulesResource =
        new ManageSchedulesResource(
            authorisationProvider,
            principalProvider,
            schedulesFactory,
            manageSchedulesService,
            mock(ScheduleInstancesStore.class));

    // Act
    List<ScheduleInfo> actualSchedulerStatus = manageSchedulesResource.getSchedulerStatus(true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(manageSchedulesService).getAll();
    assertTrue(actualSchedulerStatus.isEmpty());
  }

  /**
   * Test {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}
   */
  @Test
  @DisplayName("Test getSchedulerStatus(Boolean); when 'null'; then return ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ManageSchedulesResource.getSchedulerStatus(Boolean)"})
  void testGetSchedulerStatus_whenNull_thenReturnArrayList() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("get schedule status"));

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);
    Provider<Principal> principalProvider = mock(Provider.class);
    SchedulesFactoryImpl schedulesFactory =
        new SchedulesFactoryImpl(
            mock(SchedulesStore.class), mock(ScheduleInstancesStore.class), true);

    ManageSchedulesResource manageSchedulesResource =
        new ManageSchedulesResource(
            authorisationProvider,
            principalProvider,
            schedulesFactory,
            manageSchedulesService,
            mock(ScheduleInstancesStore.class));

    // Act
    List<ScheduleInfo> actualSchedulerStatus = manageSchedulesResource.getSchedulerStatus(null);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(manageSchedulesService).getAll();
    assertEquals(scheduleInfoList, actualSchedulerStatus);
  }

  /**
   * Test {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ManageSchedulesResource#getSchedulerStatus(Boolean)}
   */
  @Test
  @DisplayName("Test getSchedulerStatus(Boolean); when 'true'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ManageSchedulesResource.getSchedulerStatus(Boolean)"})
  void testGetSchedulerStatus_whenTrue_thenReturnEmpty() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.getAll()).thenReturn(new ArrayList<>());
    Provider<Principal> principalProvider = mock(Provider.class);
    SchedulesFactoryImpl schedulesFactory =
        new SchedulesFactoryImpl(
            mock(SchedulesStore.class), mock(ScheduleInstancesStore.class), true);

    ManageSchedulesResource manageSchedulesResource =
        new ManageSchedulesResource(
            authorisationProvider,
            principalProvider,
            schedulesFactory,
            manageSchedulesService,
            mock(ScheduleInstancesStore.class));

    // Act
    List<ScheduleInfo> actualSchedulerStatus = manageSchedulesResource.getSchedulerStatus(true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(manageSchedulesService).getAll();
    assertTrue(actualSchedulerStatus.isEmpty());
  }

  /**
   * Test {@link ManageSchedulesResource#getSchedulerInstances()}.
   *
   * <p>Method under test: {@link ManageSchedulesResource#getSchedulerInstances()}
   */
  @Test
  @DisplayName("Test getSchedulerInstances()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ManageSchedulesResource.getSchedulerInstances()"})
  void testGetSchedulerInstances() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    ScheduleInstancesStore scheduleInstancesStore = mock(ScheduleInstancesStore.class);
    when(scheduleInstancesStore.getAll()).thenReturn(new ArrayList<>());
    Provider<Principal> principalProvider = mock(Provider.class);
    SchedulesFactoryImpl schedulesFactory =
        new SchedulesFactoryImpl(
            mock(SchedulesStore.class), mock(ScheduleInstancesStore.class), true);

    ManageSchedulesResource manageSchedulesResource =
        new ManageSchedulesResource(
            authorisationProvider,
            principalProvider,
            schedulesFactory,
            mock(SchedulesStore.class),
            scheduleInstancesStore);

    // Act
    List<ScheduleInstance> actualSchedulerInstances =
        manageSchedulesResource.getSchedulerInstances();

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(scheduleInstancesStore).getAll();
    assertTrue(actualSchedulerInstances.isEmpty());
  }

  /**
   * Test {@link ManageSchedulesResource#forceScheduler(String, boolean)}.
   *
   * <ul>
   *   <li>Then StatusInfo return {@link Status}.
   * </ul>
   *
   * <p>Method under test: {@link ManageSchedulesResource#forceScheduler(String, boolean)}
   */
  @Test
  @DisplayName("Test forceScheduler(String, boolean); then StatusInfo return Status")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response ManageSchedulesResource.forceScheduler(String, boolean)"})
  void testForceScheduler_thenStatusInfoReturnStatus() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
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
    Response actualForceSchedulerResult =
        manageSchedulesResource.forceScheduler("Schedule Name", true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(manageSchedulesService).get("Schedule Name");
    StatusType statusInfo = actualForceSchedulerResult.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    assertTrue(actualForceSchedulerResult instanceof OutboundJaxrsResponse);
    assertNull(actualForceSchedulerResult.getEntity());
    assertNull(actualForceSchedulerResult.getLocation());
    assertNull(actualForceSchedulerResult.getDate());
    assertNull(actualForceSchedulerResult.getLastModified());
    assertNull(actualForceSchedulerResult.getLanguage());
    assertNull(actualForceSchedulerResult.getEntityTag());
    assertNull(actualForceSchedulerResult.getMediaType());
    assertEquals(-1, actualForceSchedulerResult.getLength());
    assertEquals(204, actualForceSchedulerResult.getStatus());
    assertEquals(Status.NO_CONTENT, statusInfo);
    assertTrue(actualForceSchedulerResult.getCookies().isEmpty());
    MultivaluedMap<String, Object> headers = actualForceSchedulerResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualForceSchedulerResult.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualForceSchedulerResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(allowedMethods, actualForceSchedulerResult.getLinks());
    assertSame(headers, actualForceSchedulerResult.getMetadata());
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
        new SchedulesFactoryImpl(manageSchedulesService, mock(ScheduleInstancesStore.class), false);

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

  /**
   * Test {@link ManageSchedulesResource#deleteSchedules()}.
   *
   * <ul>
   *   <li>Then StatusInfo return {@link Status}.
   * </ul>
   *
   * <p>Method under test: {@link ManageSchedulesResource#deleteSchedules()}
   */
  @Test
  @DisplayName("Test deleteSchedules(); then StatusInfo return Status")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response ManageSchedulesResource.deleteSchedules()"})
  void testDeleteSchedules_thenStatusInfoReturnStatus() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.getAll()).thenReturn(new ArrayList<>());
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
    Response actualDeleteSchedulesResult = manageSchedulesResource.deleteSchedules();

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(manageSchedulesService).getAll();
    StatusType statusInfo = actualDeleteSchedulesResult.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    assertTrue(actualDeleteSchedulesResult instanceof OutboundJaxrsResponse);
    assertNull(actualDeleteSchedulesResult.getEntity());
    assertNull(actualDeleteSchedulesResult.getLocation());
    assertNull(actualDeleteSchedulesResult.getDate());
    assertNull(actualDeleteSchedulesResult.getLastModified());
    assertNull(actualDeleteSchedulesResult.getLanguage());
    assertNull(actualDeleteSchedulesResult.getEntityTag());
    assertNull(actualDeleteSchedulesResult.getMediaType());
    assertEquals(-1, actualDeleteSchedulesResult.getLength());
    assertEquals(204, actualDeleteSchedulesResult.getStatus());
    assertEquals(Status.NO_CONTENT, statusInfo);
    assertTrue(actualDeleteSchedulesResult.getCookies().isEmpty());
    MultivaluedMap<String, Object> headers = actualDeleteSchedulesResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualDeleteSchedulesResult.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualDeleteSchedulesResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(allowedMethods, actualDeleteSchedulesResult.getLinks());
    assertSame(headers, actualDeleteSchedulesResult.getMetadata());
  }

  /**
   * Test {@link ManageSchedulesResource#toggleScheduler(String, boolean)} with {@code
   * scheduleName}, {@code toggle}.
   *
   * <ul>
   *   <li>Then StatusInfo return {@link Status}.
   * </ul>
   *
   * <p>Method under test: {@link ManageSchedulesResource#toggleScheduler(String, boolean)}
   */
  @Test
  @DisplayName(
      "Test toggleScheduler(String, boolean) with 'scheduleName', 'toggle'; then StatusInfo return Status")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response ManageSchedulesResource.toggleScheduler(String, boolean)"})
  void testToggleSchedulerWithScheduleNameToggle_thenStatusInfoReturnStatus() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
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
    Response actualToggleSchedulerResult =
        manageSchedulesResource.toggleScheduler("Schedule Name", true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Schedule Name");
    StatusType statusInfo = actualToggleSchedulerResult.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    assertTrue(actualToggleSchedulerResult instanceof OutboundJaxrsResponse);
    assertNull(actualToggleSchedulerResult.getEntity());
    assertNull(actualToggleSchedulerResult.getLocation());
    assertNull(actualToggleSchedulerResult.getDate());
    assertNull(actualToggleSchedulerResult.getLastModified());
    assertNull(actualToggleSchedulerResult.getLanguage());
    assertNull(actualToggleSchedulerResult.getEntityTag());
    assertNull(actualToggleSchedulerResult.getMediaType());
    assertEquals(-1, actualToggleSchedulerResult.getLength());
    assertEquals(204, actualToggleSchedulerResult.getStatus());
    assertEquals(Status.NO_CONTENT, statusInfo);
    assertTrue(actualToggleSchedulerResult.getCookies().isEmpty());
    MultivaluedMap<String, Object> headers = actualToggleSchedulerResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualToggleSchedulerResult.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualToggleSchedulerResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(allowedMethods, actualToggleSchedulerResult.getLinks());
    assertSame(headers, actualToggleSchedulerResult.getMetadata());
  }

  /**
   * Test {@link ManageSchedulesResource#toggleScheduler(boolean)} with {@code toggle}.
   *
   * <ul>
   *   <li>Then StatusInfo return {@link Status}.
   * </ul>
   *
   * <p>Method under test: {@link ManageSchedulesResource#toggleScheduler(boolean)}
   */
  @Test
  @DisplayName("Test toggleScheduler(boolean) with 'toggle'; then StatusInfo return Status")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Response ManageSchedulesResource.toggleScheduler(boolean)"})
  void testToggleSchedulerWithToggle_thenStatusInfoReturnStatus() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    doNothing()
        .when(authorisationProvider)
        .authorise(Mockito.<Provider<Principal>>any(), Mockito.<String>any());

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.getAll()).thenReturn(new ArrayList<>());
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
    Response actualToggleSchedulerResult = manageSchedulesResource.toggleScheduler(true);

    // Assert
    verify(authorisationProvider).authorise(isA(Provider.class), eq("Schedules"));
    verify(manageSchedulesService).getAll();
    StatusType statusInfo = actualToggleSchedulerResult.getStatusInfo();
    assertTrue(statusInfo instanceof Status);
    assertTrue(actualToggleSchedulerResult instanceof OutboundJaxrsResponse);
    assertNull(actualToggleSchedulerResult.getEntity());
    assertNull(actualToggleSchedulerResult.getLocation());
    assertNull(actualToggleSchedulerResult.getDate());
    assertNull(actualToggleSchedulerResult.getLastModified());
    assertNull(actualToggleSchedulerResult.getLanguage());
    assertNull(actualToggleSchedulerResult.getEntityTag());
    assertNull(actualToggleSchedulerResult.getMediaType());
    assertEquals(-1, actualToggleSchedulerResult.getLength());
    assertEquals(204, actualToggleSchedulerResult.getStatus());
    assertEquals(Status.NO_CONTENT, statusInfo);
    assertTrue(actualToggleSchedulerResult.getCookies().isEmpty());
    MultivaluedMap<String, Object> headers = actualToggleSchedulerResult.getHeaders();
    assertTrue(headers.isEmpty());
    assertTrue(actualToggleSchedulerResult.getStringHeaders().isEmpty());
    Set<String> allowedMethods = actualToggleSchedulerResult.getAllowedMethods();
    assertTrue(allowedMethods.isEmpty());
    assertSame(allowedMethods, actualToggleSchedulerResult.getLinks());
    assertSame(headers, actualToggleSchedulerResult.getMetadata());
  }
}
