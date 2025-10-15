package org.finos.legend.depot.services.schedules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.map.mutable.SynchronizedMutableMap;
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SchedulesFactoryImplDiffblueTest {
  /**
   * Test {@link SchedulesFactoryImpl#SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore,
   * boolean)}.
   *
   * <ul>
   *   <li>When {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#SchedulesFactoryImpl(SchedulesStore,
   * ScheduleInstancesStore, boolean)}
   */
  @Test
  @DisplayName(
      "Test new SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore, boolean); when 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.<init>(SchedulesStore, ScheduleInstancesStore, boolean)"
  })
  void testNewSchedulesFactoryImpl_whenFalse() {
    // Arrange and Act
    SchedulesFactoryImpl actualSchedulesFactoryImpl = new SchedulesFactoryImpl(null, null, false);

    // Assert
    assertNull(actualSchedulesFactoryImpl.instancesStore);
    assertNull(actualSchedulesFactoryImpl.schedulesStore);
    assertTrue(actualSchedulesFactoryImpl.functions.toList().isEmpty());
    SynchronizedMutableMap<String, Supplier<Object>> expectedStringTimerTaskMap =
        actualSchedulesFactoryImpl.functions;
    assertEquals(expectedStringTimerTaskMap, actualSchedulesFactoryImpl.tasksRegistry);
  }

  /**
   * Test {@link SchedulesFactoryImpl#SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore,
   * boolean)}.
   *
   * <ul>
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#SchedulesFactoryImpl(SchedulesStore,
   * ScheduleInstancesStore, boolean)}
   */
  @Test
  @DisplayName(
      "Test new SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore, boolean); when 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.<init>(SchedulesStore, ScheduleInstancesStore, boolean)"
  })
  void testNewSchedulesFactoryImpl_whenTrue() {
    // Arrange and Act
    SchedulesFactoryImpl actualSchedulesFactoryImpl = new SchedulesFactoryImpl(null, null, true);

    // Assert
    assertNull(actualSchedulesFactoryImpl.instancesStore);
    assertNull(actualSchedulesFactoryImpl.schedulesStore);
    assertTrue(actualSchedulesFactoryImpl.functions.toList().isEmpty());
    SynchronizedMutableMap<String, Supplier<Object>> expectedStringTimerTaskMap =
        actualSchedulesFactoryImpl.functions;
    assertEquals(expectedStringTimerTaskMap, actualSchedulesFactoryImpl.tasksRegistry);
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long,
   * Supplier)}
   */
  @Test
  @DisplayName("Test registerExternalTriggerSchedule(String, long, Supplier)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerExternalTriggerSchedule(String, long, Supplier)"
  })
  void testRegisterExternalTriggerSchedule() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("Name", 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerExternalTriggerSchedule(String, long, Supplier); given MockScheduleStore get(String) return empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerExternalTriggerSchedule(String, long, Supplier)"
  })
  void testRegisterExternalTriggerSchedule_givenMockScheduleStoreGetReturnEmpty() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("", 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Id is {@code 42}.
   *   <li>When four.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerExternalTriggerSchedule(String, long, Supplier); given ScheduleInfo(String) with 'Name' Id is '42'; when four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerExternalTriggerSchedule(String, long, Supplier)"
  })
  void testRegisterExternalTriggerSchedule_givenScheduleInfoWithNameIdIs42_whenFour() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setId("42");

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("", 4L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Id is {@code 42}.
   *   <li>When minus one.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerExternalTriggerSchedule(String, long, Supplier); given ScheduleInfo(String) with 'Name' Id is '42'; when minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerExternalTriggerSchedule(String, long, Supplier)"
  })
  void testRegisterExternalTriggerSchedule_givenScheduleInfoWithNameIdIs42_whenMinusOne() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setId("42");

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("", -1L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long,
   * Supplier)}
   */
  @Test
  @DisplayName("Test registerExternalTriggerSchedule(String, long, Supplier); when '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerExternalTriggerSchedule(String, long, Supplier)"
  })
  void testRegisterExternalTriggerSchedule_when42() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("42", 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("42");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}.
   *
   * <ul>
   *   <li>When four.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long,
   * Supplier)}
   */
  @Test
  @DisplayName("Test registerExternalTriggerSchedule(String, long, Supplier); when four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerExternalTriggerSchedule(String, long, Supplier)"
  })
  void testRegisterExternalTriggerSchedule_whenFour() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("", 4L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName("Test registerSingleInstance(String, long, long, Supplier)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName("Test registerSingleInstance(String, long, long, Supplier)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance2() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(mock(ScheduleInfo.class));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} SingleInstance is
   *       {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given ScheduleInfo(String) with 'Name' SingleInstance is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenScheduleInfoWithNameSingleInstanceIsTrue() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>When {@link Long#MAX_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName("Test registerSingleInstance(String, long, long, Supplier); when MAX_VALUE")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_whenMax_value() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setExternalTrigger(true);
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 4L, Long.MAX_VALUE, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask2() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(mock(ScheduleInfo.class));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask3() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register(
        "org.finos.legend.depot.services.schedules.SchedulesFactoryImpl", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService)
        .get("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask4() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setDisabled(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask5() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setExternalTrigger(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask6() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask7() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("NameName"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask8() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(mock(ScheduleInfo.class));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask9() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name42"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <ul>
   *   <li>When {@code 6000}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'; when '6000'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask_when6000() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 6000L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#canExecute(String)}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  @DisplayName("Test canExecute(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SchedulesFactoryImpl.canExecute(String)"})
  void testCanExecute() {
    // Arrange
    ScheduleInstance scheduleInstance = new ScheduleInstance();
    scheduleInstance.setExpires(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    ArrayList<ScheduleInstance> scheduleInstanceList = new ArrayList<>();
    scheduleInstanceList.add(scheduleInstance);

    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(scheduleInstanceList);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(null, instancesStore, true);

    // Act
    schedulesFactoryImpl.canExecute("Name");

    // Assert
    verify(instancesStore).find("Name");
  }

  /**
   * Test {@link SchedulesFactoryImpl#canExecute(String)}.
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  @DisplayName("Test canExecute(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SchedulesFactoryImpl.canExecute(String)"})
  void testCanExecute2() {
    // Arrange
    ScheduleInstance scheduleInstance = mock(ScheduleInstance.class);
    when(scheduleInstance.isExpired()).thenReturn(false);

    ArrayList<ScheduleInstance> scheduleInstanceList = new ArrayList<>();
    Date expires =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    ScheduleInstance scheduleInstance2 = new ScheduleInstance("Name", expires);
    scheduleInstanceList.add(scheduleInstance2);
    scheduleInstanceList.add(scheduleInstance);

    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(scheduleInstanceList);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(null, instancesStore, true);

    // Act
    boolean actualCanExecuteResult = schedulesFactoryImpl.canExecute("Name");

    // Assert
    verify(instancesStore).find("Name");
    assertFalse(actualCanExecuteResult);
  }

  /**
   * Test {@link SchedulesFactoryImpl#canExecute(String)}.
   *
   * <ul>
   *   <li>Given {@link MockInstancesStore} {@link MockInstancesStore#find(String)} return {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  @DisplayName(
      "Test canExecute(String); given MockInstancesStore find(String) return ArrayList(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SchedulesFactoryImpl.canExecute(String)"})
  void testCanExecute_givenMockInstancesStoreFindReturnArrayList_thenReturnTrue() {
    // Arrange
    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(new ArrayList<>());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(null, instancesStore, true);

    // Act
    boolean actualCanExecuteResult = schedulesFactoryImpl.canExecute("Name");

    // Assert
    verify(instancesStore).find("Name");
    assertTrue(actualCanExecuteResult);
  }

  /**
   * Test {@link SchedulesFactoryImpl#canExecute(String)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInstance} {@link ScheduleInstance#isExpired()} return {@code false}.
   *   <li>Then calls {@link ScheduleInstance#isExpired()}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  @DisplayName(
      "Test canExecute(String); given ScheduleInstance isExpired() return 'false'; then calls isExpired()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SchedulesFactoryImpl.canExecute(String)"})
  void testCanExecute_givenScheduleInstanceIsExpiredReturnFalse_thenCallsIsExpired() {
    // Arrange
    ScheduleInstance scheduleInstance = mock(ScheduleInstance.class);
    when(scheduleInstance.isExpired()).thenReturn(false);

    ArrayList<ScheduleInstance> scheduleInstanceList = new ArrayList<>();
    scheduleInstanceList.add(scheduleInstance);

    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(scheduleInstanceList);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(null, instancesStore, true);

    // Act
    boolean actualCanExecuteResult = schedulesFactoryImpl.canExecute("Name");

    // Assert
    verify(instancesStore).find("Name");
    verify(scheduleInstance).isExpired();
    assertFalse(actualCanExecuteResult);
  }

  /**
   * Test {@link SchedulesFactoryImpl#deRegister(String)}.
   *
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#delete(String)} does nothing.
   *   <li>Then calls {@link SchedulesStore#delete(String)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#deRegister(String)}
   */
  @Test
  @DisplayName(
      "Test deRegister(String); given SchedulesStore delete(String) does nothing; then calls delete(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.deRegister(String)"})
  void testDeRegister_givenSchedulesStoreDeleteDoesNothing_thenCallsDelete() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    doNothing().when(manageSchedulesService).delete(Mockito.<String>any());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.deRegister("Name");

    // Assert
    verify(manageSchedulesService).delete("Name");
  }

  /**
   * Test {@link SchedulesFactoryImpl#deRegisterAll()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo(String)} with
   *       name is {@code De-registering schedule {}}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#deRegisterAll()}
   */
  @Test
  @DisplayName(
      "Test deRegisterAll(); given ArrayList() add ScheduleInfo(String) with name is 'De-registering schedule {}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.deRegisterAll()"})
  void testDeRegisterAll_givenArrayListAddScheduleInfoWithNameIsDeRegisteringSchedule() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("De-registering schedule {}"));
    scheduleInfoList.add(new ScheduleInfo("Name"));

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    doNothing().when(manageSchedulesService).delete(Mockito.<String>any());
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.deRegisterAll();

    // Assert
    verify(manageSchedulesService, atLeast(1)).delete(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#deRegisterAll()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo(String)} with
   *       {@code Name}.
   *   <li>Then calls {@link MockScheduleStore#delete(String)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#deRegisterAll()}
   */
  @Test
  @DisplayName(
      "Test deRegisterAll(); given ArrayList() add ScheduleInfo(String) with 'Name'; then calls delete(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.deRegisterAll()"})
  void testDeRegisterAll_givenArrayListAddScheduleInfoWithName_thenCallsDelete() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    doNothing().when(manageSchedulesService).delete(Mockito.<String>any());
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.deRegisterAll();

    // Assert
    verify(manageSchedulesService).delete("Name");
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#deRegisterAll()}.
   *
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#getAll()} return {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link MockScheduleStore#getAll()}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#deRegisterAll()}
   */
  @Test
  @DisplayName(
      "Test deRegisterAll(); given MockScheduleStore getAll() return ArrayList(); then calls getAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.deRegisterAll()"})
  void testDeRegisterAll_givenMockScheduleStoreGetAllReturnArrayList_thenCallsGetAll() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.getAll()).thenReturn(new ArrayList<>());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.deRegisterAll();

    // Assert
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.
   *   <li>When {@code true}.
   *   <li>Then calls {@link MockScheduleStore#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName(
      "Test trigger(String, boolean); given MockScheduleStore get(String) return empty; when 'true'; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnEmpty_whenTrue_thenCallsGet() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.trigger("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get("Schedule Name");
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link
   *       ScheduleInfo#ScheduleInfo(String)} with {@code Name}.
   *   <li>When {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName(
      "Test trigger(String, boolean); given MockScheduleStore get(String) return of ScheduleInfo(String) with 'Name'; when 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnOfScheduleInfoWithName_whenFalse() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.trigger("Schedule Name", false);

    // Assert
    verify(manageSchedulesService).get("Schedule Name");
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link
   *       ScheduleInfo#ScheduleInfo(String)} with {@code Name}.
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName(
      "Test trigger(String, boolean); given MockScheduleStore get(String) return of ScheduleInfo(String) with 'Name'; when 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnOfScheduleInfoWithName_whenTrue() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.trigger("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get("Schedule Name");
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Disabled is {@code
   *       true}.
   *   <li>When {@code false}.
   *   <li>Then calls {@link MockScheduleStore#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName(
      "Test trigger(String, boolean); given ScheduleInfo(String) with 'Name' Disabled is 'true'; when 'false'; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenScheduleInfoWithNameDisabledIsTrue_whenFalse_thenCallsGet() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setDisabled(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.trigger("Schedule Name", false);

    // Assert
    verify(manageSchedulesService).get("Schedule Name");
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.
   *   <li>Then calls {@link MockScheduleStore#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName(
      "Test toggleDisable(String, boolean); given MockScheduleStore get(String) return empty; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenMockScheduleStoreGetReturnEmpty_thenCallsGet() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get("Schedule Name");
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link MockScheduleStore#createOrUpdate(ScheduleInfo)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); then calls createOrUpdate(ScheduleInfo)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_thenCallsCreateOrUpdate() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Schedule Name");
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   *
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#getAll()} return {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link MockScheduleStore#getAll()}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName(
      "Test toggleDisableAll(boolean); given MockScheduleStore getAll() return ArrayList(); then calls getAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetAllReturnArrayList_thenCallsGetAll() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.getAll()).thenReturn(new ArrayList<>());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   *
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.
   *   <li>Then calls {@link MockScheduleStore#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName(
      "Test toggleDisableAll(boolean); given MockScheduleStore get(String) return empty; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnEmpty_thenCallsGet() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).get("Name");
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link MockScheduleStore#createOrUpdate(ScheduleInfo)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); then calls createOrUpdate(ScheduleInfo)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_thenCallsCreateOrUpdate() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link MockScheduleStore#createOrUpdate(ScheduleInfo)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); then calls createOrUpdate(ScheduleInfo)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_thenCallsCreateOrUpdate2() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    scheduleInfoList.add(new ScheduleInfo("Name"));

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);

    // Act
    schedulesFactoryImpl.toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get("Name");
    verify(manageSchedulesService).getAll();
  }
}
