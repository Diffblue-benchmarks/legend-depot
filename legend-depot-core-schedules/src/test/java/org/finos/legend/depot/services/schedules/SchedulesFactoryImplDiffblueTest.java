package org.finos.legend.depot.services.schedules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;
import org.eclipse.collections.api.list.MutableList;
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
   * Test {@link SchedulesFactoryImpl#SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore, boolean)}
   */
  @Test
  @DisplayName("Test new SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore, boolean); when 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.<init>(SchedulesStore, ScheduleInstancesStore, boolean)"})
  void testNewSchedulesFactoryImpl_whenFalse() {
    // Arrange and Act
    SchedulesFactoryImpl actualSchedulesFactoryImpl = new SchedulesFactoryImpl(null, null, false);

    // Assert
    assertNull(actualSchedulesFactoryImpl.instancesStore);
    assertNull(actualSchedulesFactoryImpl.schedulesStore);
    assertTrue(actualSchedulesFactoryImpl.functions.toList().isEmpty());
    assertEquals(actualSchedulesFactoryImpl.functions, actualSchedulesFactoryImpl.tasksRegistry);
  }

  /**
   * Test {@link SchedulesFactoryImpl#SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore, boolean)}.
   * <ul>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore, boolean)}
   */
  @Test
  @DisplayName("Test new SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore, boolean); when 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.<init>(SchedulesStore, ScheduleInstancesStore, boolean)"})
  void testNewSchedulesFactoryImpl_whenTrue() {
    // Arrange and Act
    SchedulesFactoryImpl actualSchedulesFactoryImpl = new SchedulesFactoryImpl(null, null, true);

    // Assert
    assertNull(actualSchedulesFactoryImpl.instancesStore);
    assertNull(actualSchedulesFactoryImpl.schedulesStore);
    assertTrue(actualSchedulesFactoryImpl.functions.toList().isEmpty());
    assertEquals(actualSchedulesFactoryImpl.functions, actualSchedulesFactoryImpl.tasksRegistry);
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  @DisplayName("Test registerExternalTriggerSchedule(String, long, Supplier)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.registerExternalTriggerSchedule(String, long, Supplier)"})
  void testRegisterExternalTriggerSchedule() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("Name", 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  @DisplayName("Test registerExternalTriggerSchedule(String, long, Supplier)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.registerExternalTriggerSchedule(String, long, Supplier)"})
  void testRegisterExternalTriggerSchedule2() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo(""));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("Name", 1000L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  @DisplayName("Test registerExternalTriggerSchedule(String, long, Supplier)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.registerExternalTriggerSchedule(String, long, Supplier)"})
  void testRegisterExternalTriggerSchedule3() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("Name", Long.MAX_VALUE, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  @DisplayName("Test registerExternalTriggerSchedule(String, long, Supplier)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.registerExternalTriggerSchedule(String, long, Supplier)"})
  void testRegisterExternalTriggerSchedule4() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("42", 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Disabled is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  @DisplayName("Test registerExternalTriggerSchedule(String, long, Supplier); given ScheduleInfo(String) with 'Name' Disabled is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.registerExternalTriggerSchedule(String, long, Supplier)"})
  void testRegisterExternalTriggerSchedule_givenScheduleInfoWithNameDisabledIsTrue() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setDisabled(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("Name", 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test registerSingleInstance(String, long, long, Supplier)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"})
  void testRegisterSingleInstance() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test registerSingleInstance(String, long, long, Supplier)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.registerSingleInstance(String, long, long, Supplier)"})
  void testRegisterSingleInstance2() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("42"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(MockInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Name");
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask3() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("NameName"));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask4() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setFrequency(1L);
    scheduleInfo.setName("Name");
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask5() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("42"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 6000L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask6() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo(""));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("42", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask7() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask8() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask9() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, false);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask10() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setDisabled(true);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 6000L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask11() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("No function to execute {}");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask12() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Schedule {} disabled, skipping");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#register(String, long, long, Supplier)} with {@code name}, {@code delayStartInMilliseconds}, {@code intervalInMilliseconds}, {@code task}.
   * <ul>
   *   <li>When {@code 6000}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  @DisplayName("Test register(String, long, long, Supplier) with 'name', 'delayStartInMilliseconds', 'intervalInMilliseconds', 'task'; when '6000'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.register(String, long, long, Supplier)"})
  void testRegisterWithNameDelayStartInMillisecondsIntervalInMillisecondsTask_when6000() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 6000L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Test {@link SchedulesFactoryImpl#canExecute(String)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  @DisplayName("Test canExecute(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SchedulesFactoryImpl.canExecute(String)"})
  void testCanExecute() {
    // Arrange
    ScheduleInstance scheduleInstance = new ScheduleInstance();
    scheduleInstance.setExpires(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    ArrayList<ScheduleInstance> scheduleInstanceList = new ArrayList<>();
    scheduleInstanceList.add(scheduleInstance);
    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(scheduleInstanceList);

    // Act
    new SchedulesFactoryImpl(null, instancesStore, true).canExecute("Name");

    // Assert
    verify(instancesStore).find(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#canExecute(String)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  @DisplayName("Test canExecute(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SchedulesFactoryImpl.canExecute(String)"})
  void testCanExecute2() {
    // Arrange
    ScheduleInstance scheduleInstance = mock(ScheduleInstance.class);
    when(scheduleInstance.isExpired()).thenReturn(false);

    ArrayList<ScheduleInstance> scheduleInstanceList = new ArrayList<>();
    scheduleInstanceList.add(new ScheduleInstance("Name",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
    scheduleInstanceList.add(scheduleInstance);
    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(scheduleInstanceList);

    // Act
    boolean actualCanExecuteResult = new SchedulesFactoryImpl(null, instancesStore, true).canExecute("Name");

    // Assert
    verify(instancesStore).find(eq("Name"));
    assertFalse(actualCanExecuteResult);
  }

  /**
   * Test {@link SchedulesFactoryImpl#canExecute(String)}.
   * <ul>
   *   <li>Given {@link MockInstancesStore} {@link MockInstancesStore#find(String)} return {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  @DisplayName("Test canExecute(String); given MockInstancesStore find(String) return ArrayList(); then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SchedulesFactoryImpl.canExecute(String)"})
  void testCanExecute_givenMockInstancesStoreFindReturnArrayList_thenReturnTrue() {
    // Arrange
    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    boolean actualCanExecuteResult = new SchedulesFactoryImpl(null, instancesStore, true).canExecute("Name");

    // Assert
    verify(instancesStore).find(eq("Name"));
    assertTrue(actualCanExecuteResult);
  }

  /**
   * Test {@link SchedulesFactoryImpl#canExecute(String)}.
   * <ul>
   *   <li>Given {@link ScheduleInstance} {@link ScheduleInstance#isExpired()} return {@code false}.</li>
   *   <li>Then calls {@link ScheduleInstance#isExpired()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  @DisplayName("Test canExecute(String); given ScheduleInstance isExpired() return 'false'; then calls isExpired()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SchedulesFactoryImpl.canExecute(String)"})
  void testCanExecute_givenScheduleInstanceIsExpiredReturnFalse_thenCallsIsExpired() {
    // Arrange
    ScheduleInstance scheduleInstance = mock(ScheduleInstance.class);
    when(scheduleInstance.isExpired()).thenReturn(false);

    ArrayList<ScheduleInstance> scheduleInstanceList = new ArrayList<>();
    scheduleInstanceList.add(scheduleInstance);
    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(scheduleInstanceList);

    // Act
    boolean actualCanExecuteResult = new SchedulesFactoryImpl(null, instancesStore, true).canExecute("Name");

    // Assert
    verify(instancesStore).find(eq("Name"));
    verify(scheduleInstance).isExpired();
    assertFalse(actualCanExecuteResult);
  }

  /**
   * Test {@link SchedulesFactoryImpl#deRegister(String)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#delete(String)} does nothing.</li>
   *   <li>When {@code NameName}.</li>
   *   <li>Then calls {@link SchedulesStore#delete(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#deRegister(String)}
   */
  @Test
  @DisplayName("Test deRegister(String); given SchedulesStore delete(String) does nothing; when 'NameName'; then calls delete(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.deRegister(String)"})
  void testDeRegister_givenSchedulesStoreDeleteDoesNothing_whenNameName_thenCallsDelete() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    doNothing().when(manageSchedulesService).delete(Mockito.<String>any());

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).deRegister("NameName");

    // Assert
    verify(manageSchedulesService).delete(eq("NameName"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#deRegister(String)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#delete(String)} does nothing.</li>
   *   <li>When {@code Name}.</li>
   *   <li>Then calls {@link SchedulesStore#delete(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#deRegister(String)}
   */
  @Test
  @DisplayName("Test deRegister(String); given SchedulesStore delete(String) does nothing; when 'Name'; then calls delete(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.deRegister(String)"})
  void testDeRegister_givenSchedulesStoreDeleteDoesNothing_whenName_thenCallsDelete() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    doNothing().when(manageSchedulesService).delete(Mockito.<String>any());

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).deRegister("Name");

    // Assert
    verify(manageSchedulesService).delete(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#deRegisterAll()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code De-registering schedule {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#deRegisterAll()}
   */
  @Test
  @DisplayName("Test deRegisterAll(); given ArrayList() add ScheduleInfo(String) with name is 'De-registering schedule {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.deRegisterAll()"})
  void testDeRegisterAll_givenArrayListAddScheduleInfoWithNameIsDeRegisteringSchedule() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("De-registering schedule {}"));
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    doNothing().when(manageSchedulesService).delete(Mockito.<String>any());
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).deRegisterAll();

    // Assert
    verify(manageSchedulesService, atLeast(1)).delete(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#deRegisterAll()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   *   <li>Then calls {@link MockScheduleStore#delete(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#deRegisterAll()}
   */
  @Test
  @DisplayName("Test deRegisterAll(); given ArrayList() add ScheduleInfo(String) with 'Name'; then calls delete(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.deRegisterAll()"})
  void testDeRegisterAll_givenArrayListAddScheduleInfoWithName_thenCallsDelete() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    doNothing().when(manageSchedulesService).delete(Mockito.<String>any());
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).deRegisterAll();

    // Assert
    verify(manageSchedulesService).delete(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#deRegisterAll()}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#getAll()} return {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link MockScheduleStore#getAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#deRegisterAll()}
   */
  @Test
  @DisplayName("Test deRegisterAll(); given MockScheduleStore getAll() return ArrayList(); then calls getAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.deRegisterAll()"})
  void testDeRegisterAll_givenMockScheduleStoreGetAllReturnArrayList_thenCallsGetAll() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.getAll()).thenReturn(new ArrayList<>());

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).deRegisterAll();

    // Assert
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("No function to execute {}Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger2() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("No function to execute {}42"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger3() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("No function to execute {}"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("Schedule Name", false);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger4() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    when(scheduleInfo.isDisabled()).thenReturn(false);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, mock(ScheduleInstancesStore.class), true).trigger("Schedule Name",
        false);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).isDisabled();
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger5() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    when(scheduleInfo.isDisabled()).thenReturn(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, mock(MockInstancesStore.class), false).trigger("Schedule Name",
        false);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).isDisabled();
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger6() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    when(scheduleInfo.isDisabled()).thenReturn(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, mock(MockInstancesStore.class), true)
        .trigger("No function to execute {}", false);

    // Assert
    verify(manageSchedulesService).get(eq("No function to execute {}"));
    verify(scheduleInfo).isDisabled();
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger7() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).trigger("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.</li>
   *   <li>When empty string.</li>
   *   <li>Then calls {@link MockScheduleStore#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); given MockScheduleStore get(String) return empty; when empty string; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnEmpty_whenEmptyString_thenCallsGet() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("", false);

    // Assert
    verify(manageSchedulesService).get(eq(""));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.</li>
   *   <li>When {@code java.util.TimerTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); given MockScheduleStore get(String) return empty; when 'java.util.TimerTask'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnEmpty_whenJavaUtilTimerTask() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("java.util.TimerTask", false);

    // Assert
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.</li>
   *   <li>When {@code No function to execute {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); given MockScheduleStore get(String) return empty; when 'No function to execute {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnEmpty_whenNoFunctionToExecute() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("No function to execute {}", true);

    // Assert
    verify(manageSchedulesService).get(eq("No function to execute {}"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.</li>
   *   <li>When {@code Schedule Name}.</li>
   *   <li>Then calls {@link MockScheduleStore#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); given MockScheduleStore get(String) return empty; when 'Schedule Name'; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnEmpty_whenScheduleName_thenCallsGet() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("Schedule Name", false);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.</li>
   *   <li>When {@code true}.</li>
   *   <li>Then calls {@link MockScheduleStore#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); given MockScheduleStore get(String) return empty; when 'true'; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnEmpty_whenTrue_thenCallsGet() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); given MockScheduleStore get(String) return of ScheduleInfo(String) with 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnOfScheduleInfoWithName() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("No function to execute {}", true);

    // Assert
    verify(manageSchedulesService).get(eq("No function to execute {}"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); given MockScheduleStore get(String) return of ScheduleInfo(String) with 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnOfScheduleInfoWithName2() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("Schedule Name", false);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); given MockScheduleStore get(String) return of ScheduleInfo(String) with 'Name'; when 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenMockScheduleStoreGetReturnOfScheduleInfoWithName_whenTrue() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} {@link ScheduleInfo#isDisabled()} return {@code false}.</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); given ScheduleInfo isDisabled() return 'false'; when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenScheduleInfoIsDisabledReturnFalse_whenEmptyString() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    when(scheduleInfo.isDisabled()).thenReturn(false);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("", false);

    // Assert
    verify(manageSchedulesService).get(eq(""));
    verify(scheduleInfo).isDisabled();
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} {@link ScheduleInfo#isDisabled()} return {@code true}.</li>
   *   <li>Then calls {@link ScheduleInfo#isDisabled()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); given ScheduleInfo isDisabled() return 'true'; then calls isDisabled()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_givenScheduleInfoIsDisabledReturnTrue_thenCallsIsDisabled() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    when(scheduleInfo.isDisabled()).thenReturn(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).trigger("Schedule Name", false);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).isDisabled();
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>When {@code Deleted {} expired schedule runs}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); when 'Deleted {} expired schedule runs'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_whenDeletedExpiredScheduleRuns() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    when(scheduleInfo.isDisabled()).thenReturn(false);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, mock(ScheduleInstancesStore.class), true)
        .trigger("Deleted {} expired schedule runs", false);

    // Assert
    verify(manageSchedulesService).get(eq("Deleted {} expired schedule runs"));
    verify(scheduleInfo).isDisabled();
  }

  /**
   * Test {@link SchedulesFactoryImpl#trigger(String, boolean)}.
   * <ul>
   *   <li>When {@code SchedulesFactoryImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  @DisplayName("Test trigger(String, boolean); when 'org.finos.legend.depot.services.schedules.SchedulesFactoryImpl'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.trigger(String, boolean)"})
  void testTrigger_whenOrgFinosLegendDepotServicesSchedulesSchedulesFactoryImpl() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    when(scheduleInfo.isDisabled()).thenReturn(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, mock(MockInstancesStore.class), true)
        .trigger("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl", false);

    // Assert
    verify(manageSchedulesService).get(eq("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl"));
    verify(scheduleInfo).isDisabled();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo(""));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable2() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("NameName"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable3() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("No function to execute {}");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable4() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Starting schedule {} ");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable5() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Found {} schedule: disabled {}, singleInstance {}");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable6() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Starting schedule {} ");
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable7() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Schedule {} completed");
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable8() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Deleted {} expired schedule runs");
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable9() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Found {} schedule: disabled {}, singleInstance {}");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Disabled is {@code true}.</li>
   *   <li>When {@code java.util.TimerTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Disabled is 'true'; when 'java.util.TimerTask'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoDisabledIsTrue_whenJavaUtilTimerTask() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setDisabled(true);
    scheduleInfo.setName(null);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Disabled is {@code true}.</li>
   *   <li>When {@code java.util.TimerTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Disabled is 'true'; when 'java.util.TimerTask'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoDisabledIsTrue_whenJavaUtilTimerTask2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setDisabled(true);
    scheduleInfo.setExternalTrigger(true);
    scheduleInfo.setName(null);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Disabled is {@code true}.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Disabled is 'true'; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoDisabledIsTrue_whenScheduleName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setDisabled(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} ExternalTrigger is {@code true}.</li>
   *   <li>When {@code java.util.TimerTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() ExternalTrigger is 'true'; when 'java.util.TimerTask'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoExternalTriggerIsTrue_whenJavaUtilTimerTask() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setExternalTrigger(true);
    scheduleInfo.setName(null);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} ExternalTrigger is {@code true}.</li>
   *   <li>When {@code java.util.TimerTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() ExternalTrigger is 'true'; when 'java.util.TimerTask'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoExternalTriggerIsTrue_whenJavaUtilTimerTask2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setExternalTrigger(true);
    scheduleInfo.setDisabled(true);
    scheduleInfo.setName(null);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Frequency is one.</li>
   *   <li>When {@code java.util.TimerTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Frequency is one; when 'java.util.TimerTask'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoFrequencyIsOne_whenJavaUtilTimerTask() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setFrequency(1L);
    scheduleInfo.setExternalTrigger(true);
    scheduleInfo.setName(null);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Frequency is one.</li>
   *   <li>When {@code java.util.TimerTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Frequency is one; when 'java.util.TimerTask'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoFrequencyIsOne_whenJavaUtilTimerTask2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setFrequency(1L);
    scheduleInfo.setId("42");
    scheduleInfo.setDisabled(true);
    scheduleInfo.setExternalTrigger(true);
    scheduleInfo.setName(null);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Id is {@code 42}.</li>
   *   <li>When {@code java.util.TimerTask}.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Id is '42'; when 'java.util.TimerTask'; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoIdIs42_whenJavaUtilTimerTask_thenCallsSetName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setId("42");
    scheduleInfo.setDisabled(true);
    scheduleInfo.setExternalTrigger(true);
    scheduleInfo.setName(null);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Name is {@code 42}.</li>
   *   <li>When {@code Schedule Name}.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo Name is '42'; when 'Schedule Name'; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIs42_whenScheduleName_thenCallsSetName() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Name is {@code Deleted {} expired schedule runs}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo Name is 'Deleted {} expired schedule runs'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsDeletedExpiredScheduleRuns() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Deleted {} expired schedule runs");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Deleted {} expired schedule runs"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Name is {@code Deleted {} expired schedule runs}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Name is 'Deleted {} expired schedule runs'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsDeletedExpiredScheduleRuns2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Name is {@code Deleted {} expired schedule runs}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo Name is 'Deleted {} expired schedule runs'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsDeletedExpiredScheduleRuns3() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).setName(eq("Deleted {} expired schedule runs"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Name is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo Name is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsEmptyString() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false)
        .toggleDisable("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl", true);

    // Assert
    verify(manageSchedulesService).get(eq("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl"));
    verify(scheduleInfo).setName(eq(""));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Name is {@code Found {} schedule: disabled {}, singleInstance {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo Name is 'Found {} schedule: disabled {}, singleInstance {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsFoundScheduleDisabledSingleInstance() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Found {} schedule: disabled {}, singleInstance {}");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Found {} schedule: disabled {}, singleInstance {}"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Name is {@code Found {} schedule: disabled {}, singleInstance {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Name is 'Found {} schedule: disabled {}, singleInstance {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsFoundScheduleDisabledSingleInstance2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setName("Found {} schedule: disabled {}, singleInstance {}");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Name is {@code Found {} schedule: disabled {}, singleInstance {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Name is 'Found {} schedule: disabled {}, singleInstance {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsFoundScheduleDisabledSingleInstance3() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setName("Found {} schedule: disabled {}, singleInstance {}");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true)
        .toggleDisable("Found {} schedule: disabled {}, singleInstance {}", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Found {} schedule: disabled {}, singleInstance {}"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Name is {@code Found {} schedule: disabled {}, singleInstance {}Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Name is 'Found {} schedule: disabled {}, singleInstance {}Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsFoundScheduleDisabledSingleInstanceName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setName("Found {} schedule: disabled {}, singleInstance {}Name");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Name is {@code Name}.</li>
   *   <li>When {@code Schedule Name}.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo Name is 'Name'; when 'Schedule Name'; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsName_whenScheduleName_thenCallsSetName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Name");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Name is {@code No function to execute {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Name is 'No function to execute {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsNoFunctionToExecute() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setName("No function to execute {}");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Name is {@code No function to execute {}}.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo Name is 'No function to execute {}'; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsNoFunctionToExecute_whenScheduleName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("No function to execute {}");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("No function to execute {}"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Name is {@code null}.</li>
   *   <li>When {@code java.util.TimerTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Name is 'null'; when 'java.util.TimerTask'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsNull_whenJavaUtilTimerTask() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setName(null);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Name is {@code Schedule {} completed}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Name is 'Schedule {} completed'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsScheduleCompleted() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setName("Schedule {} completed");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false)
        .toggleDisable("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl"));
    verify(scheduleInfo2).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo()} Name is {@code Schedule {} completed}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo() Name is 'Schedule {} completed'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsScheduleCompleted2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setName("Schedule {} completed");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false)
        .toggleDisable("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl"));
    verify(scheduleInfo2).setName(eq(""));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Name is {@code Schedule {} completed}.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo Name is 'Schedule {} completed'; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoNameIsScheduleCompleted_whenScheduleName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Schedule {} completed");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Schedule {} completed"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} {@link ScheduleInfo#setFrequency(Long)} does nothing.</li>
   *   <li>Then calls {@link ScheduleInfo#setFrequency(Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo setFrequency(Long) does nothing; then calls setFrequency(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoSetFrequencyDoesNothing_thenCallsSetFrequency() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setFrequency(Mockito.<Long>any());
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).setFrequency(eq(1L));
    verify(scheduleInfo2).setName(eq("42"));
    verify(scheduleInfo).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} ExternalTrigger is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with 'Name' ExternalTrigger is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameExternalTriggerIsTrue() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setExternalTrigger(true);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Frequency is one.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with 'Name' Frequency is one; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameFrequencyIsOne_whenScheduleName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42} Disabled is {@code true}.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with name is '42' Disabled is 'true'; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameIs42DisabledIsTrue_whenScheduleName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setDisabled(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42} Frequency is one.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with name is '42' Frequency is one; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameIs42FrequencyIsOne_whenScheduleName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42} Frequency is zero.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with name is '42' Frequency is zero; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameIs42FrequencyIsZero_whenScheduleName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(0L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42} Name is {@code Deleted {} expired schedule runs}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with name is '42' Name is 'Deleted {} expired schedule runs'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameIs42NameIsDeletedExpiredScheduleRuns() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42} Name is {@code null}.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with name is '42' Name is 'null'; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameIs42NameIsNull_whenScheduleName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName(null);
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42} Name is {@code Starting schedule {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with name is '42' Name is 'Starting schedule {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameIs42NameIsStartingSchedule() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42} Name is {@code Starting schedule {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with name is '42' Name is 'Starting schedule {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameIs42NameIsStartingSchedule2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42} Name is {@code Starting schedule {}}.</li>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with name is '42' Name is 'Starting schedule {}'; when '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameIs42NameIsStartingSchedule_when42() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("42", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with name is empty string Name is {@code Starting schedule {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with name is empty string Name is 'Starting schedule {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameIsEmptyStringNameIsStartingSchedule() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code Deleted {} expired schedule runs}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with 'Name' Name is 'Deleted {} expired schedule runs'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameNameIsDeletedExpiredScheduleRuns() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Deleted {} expired schedule runs");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code Name}.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with 'Name' Name is 'Name'; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameNameIsName_whenScheduleName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Name");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code No function to execute {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with 'Name' Name is 'No function to execute {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameNameIsNoFunctionToExecute() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("No function to execute {}");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("42"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code Starting schedule {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with 'Name' Name is 'Starting schedule {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameNameIsStartingSchedule() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("42"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code Starting schedule {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with 'Name' Name is 'Starting schedule {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithNameNameIsStartingSchedule2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   *   <li>When {@code Schedule Name}.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given ScheduleInfo(String) with 'Name'; when 'Schedule Name'; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenScheduleInfoWithName_whenScheduleName_thenCallsSetName() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#createOrUpdate(ScheduleInfo)} return {@code null}.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore createOrUpdate(ScheduleInfo) return 'null'; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreCreateOrUpdateReturnNull_whenScheduleName() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#createOrUpdate(ScheduleInfo)} return {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore createOrUpdate(ScheduleInfo) return ScheduleInfo(String) with 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreCreateOrUpdateReturnScheduleInfoWithName() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#createOrUpdate(ScheduleInfo)} return {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore createOrUpdate(ScheduleInfo) return ScheduleInfo(String) with 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreCreateOrUpdateReturnScheduleInfoWithName2() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("42"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return empty.</li>
   *   <li>When {@code Schedule {} disabled, skipping}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return empty; when 'Schedule {} disabled, skipping'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnEmpty_whenScheduleDisabledSkipping() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule {} disabled, skipping", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule {} disabled, skipping"));
    verify(scheduleInfo).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return empty.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return empty; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnEmpty_whenScheduleName() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return empty.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return empty; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnEmpty_whenScheduleName2() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return empty.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return empty; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnEmpty_whenScheduleName3() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return empty.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return empty; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnEmpty_whenScheduleName4() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return empty.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return empty; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnEmpty_whenScheduleName5() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return empty.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return empty; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnEmpty_whenScheduleName6() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return empty.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return empty; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnEmpty_whenScheduleName7() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return of ScheduleInfo(String) with 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnOfScheduleInfoWithName() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return of ScheduleInfo(String) with name is '42Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnOfScheduleInfoWithNameIs42Name() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("42Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return of {@link ScheduleInfo}.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return of ScheduleInfo; when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnOfScheduleInfo_whenScheduleName() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>Given {@link SchedulesStore} {@link SchedulesStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo()}.</li>
   *   <li>When {@code Schedule Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); given SchedulesStore get(String) return of ScheduleInfo(); when 'Schedule Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_givenSchedulesStoreGetReturnOfScheduleInfo_whenScheduleName2() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_whenEmptyString() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq(""));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>When {@code Found {} schedule: disabled {}, singleInstance {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); when 'Found {} schedule: disabled {}, singleInstance {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_whenFoundScheduleDisabledSingleInstance() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false)
        .toggleDisable("Found {} schedule: disabled {}, singleInstance {}", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Found {} schedule: disabled {}, singleInstance {}"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>When {@code java.util.TimerTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); when 'java.util.TimerTask'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_whenJavaUtilTimerTask() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    verify(scheduleInfo).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>When {@code No function to execute {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); when 'No function to execute {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_whenNoFunctionToExecute() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("No function to execute {}", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("No function to execute {}"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>When {@code SchedulesFactoryImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); when 'org.finos.legend.depot.services.schedules.SchedulesFactoryImpl'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_whenOrgFinosLegendDepotServicesSchedulesSchedulesFactoryImpl() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false)
        .toggleDisable("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>When {@code SchedulesFactoryImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); when 'org.finos.legend.depot.services.schedules.SchedulesFactoryImpl'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_whenOrgFinosLegendDepotServicesSchedulesSchedulesFactoryImpl2() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false)
        .toggleDisable("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl"));
    verify(scheduleInfo).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>When {@code Schedule {} completed}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); when 'Schedule {} completed'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_whenScheduleCompleted() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule {} completed", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule {} completed"));
    verify(scheduleInfo).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>When {@code Schedule {} disabled, skipping}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); when 'Schedule {} disabled, skipping'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_whenScheduleDisabledSkipping() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule {} disabled, skipping", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule {} disabled, skipping"));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>When {@code Schedule {} disabled, skipping}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); when 'Schedule {} disabled, skipping'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_whenScheduleDisabledSkipping2() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName(null);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Schedule {} disabled, skipping", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule {} disabled, skipping"));
    verify(scheduleInfo).setName(isNull());
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}.
   * <ul>
   *   <li>When {@code Starting schedule {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  @DisplayName("Test toggleDisable(String, boolean); when 'Starting schedule {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisable(String, boolean)"})
  void testToggleDisable_whenStartingSchedule() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("42");
    scheduleInfo.setName("Starting schedule {} ");
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Starting schedule {} ");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisable("Starting schedule {} ", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Starting schedule {} "));
    verify(scheduleInfo2).setName(eq("Starting schedule {} "));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll2() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll3() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name42"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll4() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo(""));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll5() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, false).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll6() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));

    ArrayList<ScheduleInfo> scheduleInfoList2 = new ArrayList<>();
    scheduleInfoList2.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList2.addAll(new ArrayList<>());
    scheduleInfoList2.addAll(scheduleInfoList);
    scheduleInfoList2.add(scheduleInfo);
    scheduleInfoList2.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList2);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll7() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ScheduleInfo scheduleInfo2 = new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}");
    scheduleInfo2.setId("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll8() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ScheduleInfo scheduleInfo2 = new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}");
    scheduleInfo2.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll9() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ScheduleInfo scheduleInfo2 = new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}");
    scheduleInfo2.setDisabled(true);

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll10() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ScheduleInfo scheduleInfo2 = new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}");
    scheduleInfo2.setSingleInstance(true);

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll11() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ScheduleInfo scheduleInfo2 = new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}");
    scheduleInfo2.setFrequency(1L);

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll12() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Found {} schedule: disabled {}, singleInstance {}");
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.add(scheduleInfo);
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo2).setName(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll13() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("No function to execute {}");
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Deleted {} expired schedule runs"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.add(scheduleInfo);
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo2).setName(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll14() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("");
    scheduleInfo.setName("No function to execute {}");
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.add(scheduleInfo);
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo2).setName(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ArrayList() add ScheduleInfo(String) with name is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenArrayListAddScheduleInfoWithNameIs42() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("No function to execute {}");
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("42"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.add(scheduleInfo);
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo2).setName(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ArrayList() add ScheduleInfo(String) with name is '42Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenArrayListAddScheduleInfoWithNameIs42Name() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("42Name"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ArrayList() add ScheduleInfo(String) with name is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenArrayListAddScheduleInfoWithNameIs422() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("No function to execute {}");
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.add(new ScheduleInfo("42"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.add(scheduleInfo);
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo2).setName(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code Name42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ArrayList() add ScheduleInfo(String) with name is 'Name42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenArrayListAddScheduleInfoWithNameIsName42() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name42"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name42"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code Name42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ArrayList() add ScheduleInfo(String) with name is 'Name42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenArrayListAddScheduleInfoWithNameIsName422() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name42"));
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code No function to execute {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ArrayList() add ScheduleInfo(String) with name is 'No function to execute {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenArrayListAddScheduleInfoWithNameIsNoFunctionToExecute() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("No function to execute {}"));
    scheduleInfoList.add(new ScheduleInfo("Name"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code Schedule {} completed}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ArrayList() add ScheduleInfo(String) with name is 'Schedule {} completed'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenArrayListAddScheduleInfoWithNameIsScheduleCompleted() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));

    ArrayList<ScheduleInfo> scheduleInfoList2 = new ArrayList<>();
    scheduleInfoList2.add(new ScheduleInfo("Schedule {} completed"));
    scheduleInfoList2.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList2.addAll(new ArrayList<>());
    scheduleInfoList2.addAll(scheduleInfoList);
    scheduleInfoList2.add(scheduleInfo);
    scheduleInfoList2.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList2);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo()}.</li>
   *   <li>Then calls {@link ScheduleInfo#setFrequency(Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ArrayList() add ScheduleInfo(); then calls setFrequency(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenArrayListAddScheduleInfo_thenCallsSetFrequency() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo());
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScheduleInfo#ScheduleInfo()}.</li>
   *   <li>Then calls {@link ScheduleInfo#setFrequency(Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ArrayList() add ScheduleInfo(); then calls setFrequency(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenArrayListAddScheduleInfo_thenCallsSetFrequency2() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));

    ArrayList<ScheduleInfo> scheduleInfoList2 = new ArrayList<>();
    scheduleInfoList2.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList2.addAll(new ArrayList<>());
    scheduleInfoList2.addAll(scheduleInfoList);
    scheduleInfoList2.add(scheduleInfo);
    scheduleInfoList2.add(new ScheduleInfo());
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList2);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#createOrUpdate(ScheduleInfo)} return {@link ScheduleInfo#ScheduleInfo()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore createOrUpdate(ScheduleInfo) return ScheduleInfo()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreCreateOrUpdateReturnScheduleInfo() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.</li>
   *   <li>Then calls {@link MockScheduleStore#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return empty; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnEmpty_thenCallsGet() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.</li>
   *   <li>Then calls {@link MockScheduleStore#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return empty; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnEmpty_thenCallsGet2() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.</li>
   *   <li>Then calls {@link MockScheduleStore#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return empty; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnEmpty_thenCallsGet3() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return empty; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnEmpty_thenCallsSetName() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return empty; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnEmpty_thenCallsSetName2() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo).setName(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return empty.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return empty; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnEmpty_thenCallsSetName3() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo).setName(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return of ScheduleInfo()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnOfScheduleInfo() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return of ScheduleInfo()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnOfScheduleInfo2() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return of ScheduleInfo()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnOfScheduleInfo3() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return of ScheduleInfo(String) with 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnOfScheduleInfoWithName() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return of ScheduleInfo(String) with 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnOfScheduleInfoWithName2() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(mock(ScheduleInfo.class));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(isNull());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return of ScheduleInfo(String) with 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnOfScheduleInfoWithName3() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return of ScheduleInfo(String) with name is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnOfScheduleInfoWithNameIs42() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("42"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link MockScheduleStore} {@link MockScheduleStore#get(String)} return of {@link ScheduleInfo#ScheduleInfo(String)} with name is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given MockScheduleStore get(String) return of ScheduleInfo(String) with name is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenMockScheduleStoreGetReturnOfScheduleInfoWithNameIs422() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(mock(ScheduleInfo.class));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("42"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Frequency is four.</li>
   *   <li>Then calls {@link ScheduleInfo#setFrequency(Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo Frequency is four; then calls setFrequency(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoFrequencyIsFour_thenCallsSetFrequency() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Frequency is zero.</li>
   *   <li>Then calls {@link ScheduleInfo#setFrequency(Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo Frequency is zero; then calls setFrequency(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoFrequencyIsZero_thenCallsSetFrequency() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(0L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(0L));
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Name is empty string.</li>
   *   <li>Then calls {@link ScheduleInfo#setFrequency(Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo Name is empty string; then calls setFrequency(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoNameIsEmptyString_thenCallsSetFrequency() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq(""));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Name is {@code Name}.</li>
   *   <li>Then calls {@link ScheduleInfo#setFrequency(Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo Name is 'Name'; then calls setFrequency(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoNameIsName_thenCallsSetFrequency() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo} Name is {@code Name}.</li>
   *   <li>Then calls {@link ScheduleInfo#setFrequency(Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo Name is 'Name'; then calls setFrequency(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoNameIsName_thenCallsSetFrequency2() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(mock(ScheduleInfo.class));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setFrequency(Mockito.<Long>any());
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setFrequency(eq(4L));
    verify(scheduleInfo).setName(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} ExternalTrigger is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' ExternalTrigger is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameExternalTriggerIsTrue() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setExternalTrigger(true);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.add(scheduleInfo);
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Frequency is four.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Frequency is four")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameFrequencyIsFour() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));

    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setFrequency(4L);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Frequency is four.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Frequency is four; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameFrequencyIsFour_thenCallsSetName() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));

    ScheduleInfo scheduleInfo2 = new ScheduleInfo("Name");
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Frequency is four.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Frequency is four; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameFrequencyIsFour_thenCallsSetName2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setFrequency(4L);
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.add(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Frequency is four.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Frequency is four; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameFrequencyIsFour_thenCallsSetName3() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));

    ScheduleInfo scheduleInfo2 = new ScheduleInfo("Name");
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Frequency is four.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Frequency is four; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameFrequencyIsFour_thenCallsSetName4() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(mock(ScheduleInfo.class));

    ScheduleInfo scheduleInfo2 = new ScheduleInfo("Name");
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(isNull());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Frequency is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Frequency is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameFrequencyIsOne() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));

    ScheduleInfo scheduleInfo2 = new ScheduleInfo("Name");
    scheduleInfo2.setFrequency(1L);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Id is {@code 42}.</li>
   *   <li>Then calls {@link MockScheduleStore#createOrUpdate(ScheduleInfo)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Id is '42'; then calls createOrUpdate(ScheduleInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameIdIs42_thenCallsCreateOrUpdate() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));

    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setId("42");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Id is {@code 42}.</li>
   *   <li>Then calls {@link ScheduleInfo#setName(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Id is '42'; then calls setName(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameIdIs42_thenCallsSetName() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setId("42");
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.add(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code 42}.</li>
   *   <li>Then calls {@link MockScheduleStore#createOrUpdate(ScheduleInfo)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Name is '42'; then calls createOrUpdate(ScheduleInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameNameIs42_thenCallsCreateOrUpdate() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code 42}.</li>
   *   <li>Then calls {@link MockScheduleStore#createOrUpdate(ScheduleInfo)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Name is '42'; then calls createOrUpdate(ScheduleInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameNameIs42_thenCallsCreateOrUpdate2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code 42}.</li>
   *   <li>Then calls {@link MockScheduleStore#createOrUpdate(ScheduleInfo)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Name is '42'; then calls createOrUpdate(ScheduleInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameNameIs42_thenCallsCreateOrUpdate3() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code Name}.</li>
   *   <li>Then calls {@link MockScheduleStore#createOrUpdate(ScheduleInfo)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Name is 'Name'; then calls createOrUpdate(ScheduleInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameNameIsName_thenCallsCreateOrUpdate() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code Name}.</li>
   *   <li>Then calls {@link ScheduleInfo#setFrequency(Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Name is 'Name'; then calls setFrequency(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameNameIsName_thenCallsSetFrequency() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Name");
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.add(scheduleInfo);
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo2).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code No function to execute {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Name is 'No function to execute {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameNameIsNoFunctionToExecute() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("No function to execute {}");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code No function to execute {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Name is 'No function to execute {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameNameIsNoFunctionToExecute2() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo).setName(Mockito.<String>any());
    scheduleInfo.setName("42");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo("Name"));

    ScheduleInfo scheduleInfo2 = new ScheduleInfo("Name");
    scheduleInfo2.setName("No function to execute {}");
    scheduleInfo2.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo2);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo).setName(eq("42"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} Name is {@code No function to execute {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' Name is 'No function to execute {}'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameNameIsNoFunctionToExecute3() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("No function to execute {}");
    ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo2).setName(Mockito.<String>any());
    scheduleInfo2.setName("Name");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Found {} schedule: disabled {}, singleInstance {}"));
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.addAll(new ArrayList<>());
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.add(scheduleInfo);
    ScheduleInfo scheduleInfo3 = mock(ScheduleInfo.class);
    doNothing().when(scheduleInfo3).setFrequency(Mockito.<Long>any());
    scheduleInfo3.setFrequency(4L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo3);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
    verify(scheduleInfo3).setFrequency(eq(4L));
    verify(scheduleInfo2).setName(eq("Name"));
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} SingleInstance is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' SingleInstance is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameSingleInstanceIsTrue() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));

    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setSingleInstance(true);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name} SingleInstance is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name' SingleInstance is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithNameSingleInstanceIsTrue2() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setSingleInstance(true);

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Given {@link ScheduleInfo#ScheduleInfo(String)} with {@code Name}.</li>
   *   <li>Then calls {@link MockScheduleStore#createOrUpdate(ScheduleInfo)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); given ScheduleInfo(String) with 'Name'; then calls createOrUpdate(ScheduleInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_givenScheduleInfoWithName_thenCallsCreateOrUpdate() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo("Name"));
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo("Name"));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    verify(manageSchedulesService).getAll();
  }

  /**
   * Test {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}.
   * <ul>
   *   <li>Then calls {@link MockScheduleStore#getAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  @DisplayName("Test toggleDisableAll(boolean); then calls getAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SchedulesFactoryImpl.toggleDisableAll(boolean)"})
  void testToggleDisableAll_thenCallsGetAll() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.getAll()).thenReturn(new ArrayList<>());

    // Act
    new SchedulesFactoryImpl(manageSchedulesService, null, true).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).getAll();
  }
}
