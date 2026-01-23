package org.finos.legend.depot.services.schedules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.eclipse.collections.api.list.MutableList;
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
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
    // Arrange
    MockScheduleStore manageSchedulesService = new MockScheduleStore();

    // Act
    SchedulesFactoryImpl actualSchedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Assert
    ScheduleInstancesStore scheduleInstancesStore = actualSchedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    SchedulesStore schedulesStore = actualSchedulesFactoryImpl.schedulesStore;
    assertTrue(schedulesStore instanceof MockScheduleStore);
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(actualSchedulesFactoryImpl.functions.toList().isEmpty());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
    assertTrue(schedulesStore.getAll().isEmpty());
    assertTrue(((MockScheduleStore) schedulesStore).schedules.isEmpty());
    assertEquals(manageSchedulesService.schedules, actualSchedulesFactoryImpl.tasksRegistry);
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
    // Arrange
    MockScheduleStore manageSchedulesService = new MockScheduleStore();

    // Act
    SchedulesFactoryImpl actualSchedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Assert
    ScheduleInstancesStore scheduleInstancesStore = actualSchedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    SchedulesStore schedulesStore = actualSchedulesFactoryImpl.schedulesStore;
    assertTrue(schedulesStore instanceof MockScheduleStore);
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(actualSchedulesFactoryImpl.functions.toList().isEmpty());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
    assertTrue(schedulesStore.getAll().isEmpty());
    assertTrue(((MockScheduleStore) schedulesStore).schedules.isEmpty());
    assertEquals(manageSchedulesService.schedules, actualSchedulesFactoryImpl.tasksRegistry);
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
    MockScheduleStore manageSchedulesService = new MockScheduleStore();
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("Name", 42L, function);

    // Assert
    SchedulesStore schedulesStore = schedulesFactoryImpl.schedulesStore;
    assertTrue(schedulesStore instanceof MockScheduleStore);
    List<ScheduleInfo> all = schedulesStore.getAll();
    assertEquals(1, all.size());
    ScheduleInfo getResult = all.get(0);
    assertEquals("Name", getResult.getName());
    assertNull(getResult.getSingleInstance());
    assertNull(getResult.getId());
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    Map<String, ScheduleInfo> stringScheduleInfoMap =
        ((MockScheduleStore) schedulesStore).schedules;
    assertEquals(1, stringScheduleInfoMap.size());
    assertEquals(42L, getResult.getFrequency().longValue());
    assertFalse(getResult.isDisabled());
    assertTrue(getResult.getExternalTrigger());
    assertSame(getResult, stringScheduleInfoMap.get("Name"));
    assertSame(manageSchedulesService.schedules, stringScheduleInfoMap);
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
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 6000L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 6000L, function);

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
  void testRegisterSingleInstance4() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 6000L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
  void testRegisterSingleInstance5() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, false);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 8L, 6000L, function);

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
  void testRegisterSingleInstance6() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance(
        "org.finos.legend.depot.services.schedules.SchedulesFactoryImpl", 1L, 1L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService)
        .get("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Disabled is {@code true}.
   *   <li>When four.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given ScheduleInfo() Disabled is 'true'; when four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenScheduleInfoDisabledIsTrue_whenFour() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setDisabled(true);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 4L, 6000L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Disabled is {@code true}.
   *   <li>When four.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given ScheduleInfo() Disabled is 'true'; when four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenScheduleInfoDisabledIsTrue_whenFour2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setDisabled(true);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 4L, 6000L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given ScheduleInfo() Id is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenScheduleInfoIdIs42() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    ScheduleInfo scheduleInfo2 = new ScheduleInfo();
    scheduleInfo2.setId("42");

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(scheduleInfo2);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 6000L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} SingleInstance is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given ScheduleInfo() SingleInstance is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenScheduleInfoSingleInstanceIsTrue() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 6000L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} SingleInstance is {@code true}.
   *   <li>When three.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given ScheduleInfo() SingleInstance is 'true'; when three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenScheduleInfoSingleInstanceIsTrue_whenThree() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 3L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} SingleInstance is {@code true}.
   *   <li>When two.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given ScheduleInfo() SingleInstance is 'true'; when two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenScheduleInfoSingleInstanceIsTrue_whenTwo() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 2L, 6000L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} SingleInstance is {@code true}.
   *   <li>When two.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given ScheduleInfo() SingleInstance is 'true'; when two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenScheduleInfoSingleInstanceIsTrue_whenTwo2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 2L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return empty.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given SchedulesStore get(String) return empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenSchedulesStoreGetReturnEmpty() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 1L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return empty.
   *   <li>When forty-two.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given SchedulesStore get(String) return empty; when forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenSchedulesStoreGetReturnEmpty_whenFortyTwo() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
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
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return empty.
   *   <li>When four.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given SchedulesStore get(String) return empty; when four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenSchedulesStoreGetReturnEmpty_whenFour() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 4L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return of {@link
   *       ScheduleInfo}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); given SchedulesStore get(String) return of ScheduleInfo")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_givenSchedulesStoreGetReturnOfScheduleInfo() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setDisabled(true);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 6000L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   *
   * <ul>
   *   <li>When eight.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName("Test registerSingleInstance(String, long, long, Supplier); when eight")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_whenEight() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 8L, 6000L, function);

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
   *   <li>When {@code No function to execute {}}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long,
   * Supplier)}
   */
  @Test
  @DisplayName(
      "Test registerSingleInstance(String, long, long, Supplier); when 'No function to execute {}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"
  })
  void testRegisterSingleInstance_whenNoFunctionToExecute() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("No function to execute {}", 1L, 1L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("No function to execute {}");
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
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
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("", 1L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setDisabled(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setExternalTrigger(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setDisabled(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("java.util.TimerTask", 1L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("java.util.TimerTask");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setExternalTrigger(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    ScheduleInfo scheduleInfo2 = new ScheduleInfo();
    scheduleInfo2.setName("Found {} schedule: disabled {}, singleInstance {}");

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(scheduleInfo2);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask15() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 6000L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask16() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 1000L, 1000L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask18() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, null, false);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("", 4L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("");
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
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask19() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 1000L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask20() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 6000L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask26() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 1L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask28() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 6000L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask29() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("No function to execute {}", 6000L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("No function to execute {}");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
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
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask30() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("Name", 6000L, 1L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Name");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <ul>
   *   <li>When four.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'; when four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask_whenFour() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("", 4L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <ul>
   *   <li>When four.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'; when four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask_whenFour2() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("", 4L, 6000L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <ul>
   *   <li>When four.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'; when four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask_whenFour3() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setDisabled(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any()))
        .thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("java.util.TimerTask", 4L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("java.util.TimerTask");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name},
   * {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   *
   * <ul>
   *   <li>When two.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName(
      "Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'; when two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask_whenTwo() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), false);

    // Act
    schedulesFactoryImpl.register("java.util.TimerTask", 2L, 42L, mock(Supplier.class));

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("java.util.TimerTask");
    ScheduleInstancesStore scheduleInstancesStore = schedulesFactoryImpl.instancesStore;
    assertTrue(scheduleInstancesStore instanceof MockInstancesStore);
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertEquals(1, ((MockInstancesStore) scheduleInstancesStore).ids);
    assertTrue(scheduleInstancesStore.getAll().isEmpty());
  }

  /**
   * Test {@link SchedulesFactoryImpl#canExecute(String)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  @DisplayName("Test canExecute(String); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SchedulesFactoryImpl.canExecute(String)"})
  void testCanExecute_thenReturnTrue() {
    // Arrange
    MockScheduleStore manageSchedulesService = new MockScheduleStore();
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act and Assert
    assertTrue(schedulesFactoryImpl.canExecute("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#deleteExpired()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#deleteExpired()}
   */
  @Test
  @DisplayName("Test deleteExpired(); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long SchedulesFactoryImpl.deleteExpired()"})
  void testDeleteExpired_thenReturnZero() {
    // Arrange
    MockScheduleStore manageSchedulesService = new MockScheduleStore();
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act and Assert
    assertEquals(0L, schedulesFactoryImpl.deleteExpired());
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link
   *       ScheduleInfo#ScheduleInfo()}.
   *   <li>When {@code false}.
   *   <li>Then calls {@link MockScheduleStore#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName(
      "Test trigger(String, boolean); given MockScheduleStore get(String) return of ScheduleInfo(); when 'false'; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnOfScheduleInfo_whenFalse_thenCallsGet() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

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
   *       ScheduleInfo#ScheduleInfo()}.
   *   <li>When {@code true}.
   *   <li>Then calls {@link MockScheduleStore#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName(
      "Test trigger(String, boolean); given MockScheduleStore get(String) return of ScheduleInfo(); when 'true'; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnOfScheduleInfo_whenTrue_thenCallsGet() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act
    schedulesFactoryImpl.trigger("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get("Schedule Name");
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Disabled is {@code true}.
   *   <li>When {@code false}.
   *   <li>Then calls {@link MockScheduleStore#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName(
      "Test trigger(String, boolean); given ScheduleInfo() Disabled is 'true'; when 'false'; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenScheduleInfoDisabledIsTrue_whenFalse_thenCallsGet() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setDisabled(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act
    schedulesFactoryImpl.trigger("Schedule Name", false);

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
        .thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    SchedulesFactoryImpl schedulesFactoryImpl =
        new SchedulesFactoryImpl(manageSchedulesService, new MockInstancesStore(), true);

    // Act
    schedulesFactoryImpl.toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get("Schedule Name");
  }
}
