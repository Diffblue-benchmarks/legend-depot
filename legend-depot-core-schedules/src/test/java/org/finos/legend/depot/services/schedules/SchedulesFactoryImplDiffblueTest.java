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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SchedulesFactoryImplDiffblueTest {
  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
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
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule2() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
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
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule3() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
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
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule4() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
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
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule5() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
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
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule6() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("", 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq(""));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule7() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("", 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq(""));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule8() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    scheduleInfo.setFrequency(1L);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("", 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq(""));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule9() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setId("42");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("Name", 1L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule10() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setId("42");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule(
        "org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo", 1L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule11() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setName("Name");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("java.util.TimerTask", 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerExternalTriggerSchedule(String, long, Supplier)}
   */
  @Test
  void testRegisterExternalTriggerSchedule12() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setId("42");
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerExternalTriggerSchedule("Name", 1L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
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
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance2() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1000L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance3() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setName("Name");
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
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
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance4() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo("Name"));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo", 1L,
        42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance5() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Name", 1L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance6() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("42", 1L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance7() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("42", 1L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance8() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setExternalTrigger(true);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("42", 1L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance9() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("42", 6000L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance10() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("42", 1L, 1L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance11() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    scheduleInfo.setExternalTrigger(true);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("42", 1L, 42L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance12() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    scheduleInfo.setExternalTrigger(true);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("42", 1L, 4L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance13() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    scheduleInfo.setExternalTrigger(true);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("42", 1L, 3L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance14() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    scheduleInfo.setExternalTrigger(true);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Schedule {} not in store", 1L, 4L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule {} not in store"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance15() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    scheduleInfo.setExternalTrigger(false);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("42", 1L, 3L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance16() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    scheduleInfo.setExternalTrigger(true);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), false);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("42", 1L, 3L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("42"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#registerSingleInstance(String, long, long, Supplier)}
   */
  @Test
  void testRegisterSingleInstance17() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    scheduleInfo.setExternalTrigger(true);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService,
        mock(ScheduleInstancesStore.class), true);
    Supplier<Object> function = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.registerSingleInstance("Starting schedule {} ", 1L, 4L, function);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Starting schedule {} "));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(function, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  void testRegister() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
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
   * Method under test:
   * {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  void testRegister2() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 1000L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, schedulesFactoryImpl.tasksRegistry.toList().size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  void testRegister3() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setId("42");
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
   * Method under test:
   * {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  void testRegister4() {
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
    assertSame(task, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  void testRegister5() {
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
   * Method under test:
   * {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  void testRegister6() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
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
    assertSame(task, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  void testRegister7() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setFrequency(1L);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
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
    assertSame(task, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  void testRegister8() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(null);
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 6000L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  void testRegister9() {
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
    assertSame(task, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  void testRegister10() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, false);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 42L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#register(String, long, long, Supplier)}
   */
  @Test
  void testRegister11() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    SchedulesFactoryImpl schedulesFactoryImpl = new SchedulesFactoryImpl(manageSchedulesService, null, true);
    Supplier<Object> task = mock(Supplier.class);

    // Act
    schedulesFactoryImpl.register("Name", 1L, 2L, task);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Name"));
    MutableList<Supplier<Object>> toListResult = schedulesFactoryImpl.functions.toList();
    assertEquals(1, toListResult.size());
    assertSame(task, toListResult.get(0));
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  void testCanExecute() {
    // Arrange
    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    boolean actualCanExecuteResult = (new SchedulesFactoryImpl(null, instancesStore, true)).canExecute("Name");

    // Assert
    verify(instancesStore).find(eq("Name"));
    assertTrue(actualCanExecuteResult);
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  void testCanExecute2() {
    // Arrange
    ScheduleInstance scheduleInstance = new ScheduleInstance();
    scheduleInstance.setExpires(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    ArrayList<ScheduleInstance> scheduleInstanceList = new ArrayList<>();
    scheduleInstanceList.add(scheduleInstance);
    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(scheduleInstanceList);

    // Act
    (new SchedulesFactoryImpl(null, instancesStore, true)).canExecute("Name");

    // Assert
    verify(instancesStore).find(eq("Name"));
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  void testCanExecute3() {
    // Arrange
    ScheduleInstance scheduleInstance = mock(ScheduleInstance.class);
    when(scheduleInstance.isExpired()).thenReturn(false);

    ArrayList<ScheduleInstance> scheduleInstanceList = new ArrayList<>();
    scheduleInstanceList.add(scheduleInstance);
    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(scheduleInstanceList);

    // Act
    boolean actualCanExecuteResult = (new SchedulesFactoryImpl(null, instancesStore, true)).canExecute("Name");

    // Assert
    verify(instancesStore).find(eq("Name"));
    verify(scheduleInstance).isExpired();
    assertFalse(actualCanExecuteResult);
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#canExecute(String)}
   */
  @Test
  void testCanExecute4() {
    // Arrange
    ArrayList<ScheduleInstance> scheduleInstanceList = new ArrayList<>();
    scheduleInstanceList.add(new ScheduleInstance("Name",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
    scheduleInstanceList.add(mock(ScheduleInstance.class));
    MockInstancesStore instancesStore = mock(MockInstancesStore.class);
    when(instancesStore.find(Mockito.<String>any())).thenReturn(scheduleInstanceList);

    // Act
    boolean actualCanExecuteResult = (new SchedulesFactoryImpl(null, instancesStore, true)).canExecute("Name");

    // Assert
    verify(instancesStore).find(eq("Name"));
    assertFalse(actualCanExecuteResult);
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#deRegister(String)}
   */
  @Test
  void testDeRegister() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    doNothing().when(manageSchedulesService).delete(Mockito.<String>any());

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).deRegister("Name");

    // Assert
    verify(manageSchedulesService).delete(eq("Name"));
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#deRegisterAll()}
   */
  @Test
  void testDeRegisterAll() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.getAll()).thenReturn(new ArrayList<>());

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).deRegisterAll();

    // Assert
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#deRegisterAll()}
   */
  @Test
  void testDeRegisterAll2() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    doNothing().when(manageSchedulesService).delete(Mockito.<String>any());
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).deRegisterAll();

    // Assert
    verify(manageSchedulesService).delete(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#deRegisterAll()}
   */
  @Test
  void testDeRegisterAll3() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    scheduleInfoList.add(new ScheduleInfo());
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    doNothing().when(manageSchedulesService).delete(Mockito.<String>any());
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).deRegisterAll();

    // Assert
    verify(manageSchedulesService, atLeast(1)).delete(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  void testTrigger() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).trigger("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  void testTrigger2() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).trigger("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  void testTrigger3() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).trigger("No function to execute {}", true);

    // Assert
    verify(manageSchedulesService).get(eq("No function to execute {}"));
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  void testTrigger4() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).trigger("", true);

    // Assert
    verify(manageSchedulesService).get(eq(""));
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  void testTrigger5() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).trigger("Schedule Name", false);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  void testTrigger6() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).trigger("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  void testTrigger7() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, mock(ScheduleInstancesStore.class), true))
        .trigger("No function to execute {}", true);

    // Assert
    verify(manageSchedulesService).get(eq("No function to execute {}"));
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  void testTrigger8() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    when(scheduleInfo.isDisabled()).thenReturn(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).trigger("Schedule Name", false);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
    verify(scheduleInfo).isDisabled();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  void testTrigger9() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    when(scheduleInfo.isDisabled()).thenReturn(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, mock(MockInstancesStore.class), true))
        .trigger("Schedule {} is disabled and force run flag is false", false);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule {} is disabled and force run flag is false"));
    verify(scheduleInfo).isDisabled();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  void testTrigger10() {
    // Arrange
    ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
    when(scheduleInfo.isDisabled()).thenReturn(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, mock(MockInstancesStore.class), true))
        .trigger("Found {} schedule: disabled {}, singleInstance {}", false);

    // Assert
    verify(manageSchedulesService).get(eq("Found {} schedule: disabled {}, singleInstance {}"));
    verify(scheduleInfo).isDisabled();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#trigger(String, boolean)}
   */
  @Test
  void testTrigger11() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true))
        .trigger("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl", false);

    // Assert
    verify(manageSchedulesService).get(eq("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl"));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  void testToggleDisable() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  void testToggleDisable2() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  void testToggleDisable3() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  void testToggleDisable4() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true))
        .toggleDisable("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("org.finos.legend.depot.services.schedules.SchedulesFactoryImpl"));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  void testToggleDisable5() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  void testToggleDisable6() {
    // Arrange
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(mock(ScheduleInfo.class));
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  void testToggleDisable7() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    ScheduleInfo scheduleInfo2 = new ScheduleInfo();
    scheduleInfo2.setName("No function to execute {}");
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  void testToggleDisable8() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    ScheduleInfo scheduleInfo2 = new ScheduleInfo();
    scheduleInfo2.setName("42");
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  void testToggleDisable9() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(false);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    ScheduleInfo scheduleInfo2 = new ScheduleInfo();
    scheduleInfo2.setName("42");
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  void testToggleDisable10() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(false);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    ScheduleInfo scheduleInfo2 = new ScheduleInfo();
    scheduleInfo2.setName("42");
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, false)).toggleDisable("Schedule Name", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("Schedule Name"));
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#toggleDisable(String, boolean)}
   */
  @Test
  void testToggleDisable11() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);

    ScheduleInfo scheduleInfo2 = new ScheduleInfo();
    scheduleInfo2.setName("");
    MockScheduleStore manageSchedulesService = mock(MockScheduleStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo2);
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisable("java.util.TimerTask", true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(eq("java.util.TimerTask"));
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll() {
    // Arrange
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.getAll()).thenReturn(new ArrayList<>());

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert that nothing has changed
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll2() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll3() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll4() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    scheduleInfoList.add(new ScheduleInfo());
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll5() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());

    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(scheduleInfo);
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll6() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());

    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setId("42");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll7() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    scheduleInfoList.add(new ScheduleInfo("Name"));
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll8() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(mock(ScheduleInfo.class));

    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setId("42");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll9() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll10() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo());
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll11() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setFrequency(4L);

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo());
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll12() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    scheduleInfoList.add(new ScheduleInfo("Name"));
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll13() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setSingleInstance(true);

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo());
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    Optional<ScheduleInfo> emptyResult = Optional.empty();
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(emptyResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll14() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setFrequency(Long.MAX_VALUE);

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo());
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll15() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo("Name");
    scheduleInfo.setName("Deleted {} expired schedule runs");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    scheduleInfoList.add(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(mock(ScheduleInfo.class));
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll16() {
    // Arrange
    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());

    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setName("No function to execute {}");
    scheduleInfo.setId("42");
    Optional<ScheduleInfo> ofResult = Optional.of(scheduleInfo);
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll17() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setFrequency(4L);

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    scheduleInfoList.add(new ScheduleInfo());
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo());
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(isNull());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test: {@link SchedulesFactoryImpl#toggleDisableAll(boolean)}
   */
  @Test
  void testToggleDisableAll18() {
    // Arrange
    ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setFrequency(4L);

    ScheduleInfo scheduleInfo2 = new ScheduleInfo();
    scheduleInfo2.setName("No function to execute {}");

    ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
    scheduleInfoList.add(new ScheduleInfo());
    scheduleInfoList.add(scheduleInfo2);
    scheduleInfoList.add(scheduleInfo);
    scheduleInfoList.add(new ScheduleInfo());
    SchedulesStore manageSchedulesService = mock(SchedulesStore.class);
    when(manageSchedulesService.createOrUpdate(Mockito.<ScheduleInfo>any())).thenReturn(new ScheduleInfo());
    Optional<ScheduleInfo> ofResult = Optional.of(new ScheduleInfo());
    when(manageSchedulesService.get(Mockito.<String>any())).thenReturn(ofResult);
    when(manageSchedulesService.getAll()).thenReturn(scheduleInfoList);

    // Act
    (new SchedulesFactoryImpl(manageSchedulesService, null, true)).toggleDisableAll(true);

    // Assert
    verify(manageSchedulesService, atLeast(1)).createOrUpdate(isA(ScheduleInfo.class));
    verify(manageSchedulesService, atLeast(1)).get(Mockito.<String>any());
    verify(manageSchedulesService).getAll();
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore, boolean)}
   */
  @Test
  void testNewSchedulesFactoryImpl() {
    // Arrange and Act
    SchedulesFactoryImpl actualSchedulesFactoryImpl = new SchedulesFactoryImpl(null, null, true);

    // Assert
    assertNull(actualSchedulesFactoryImpl.instancesStore);
    assertNull(actualSchedulesFactoryImpl.schedulesStore);
    assertTrue(actualSchedulesFactoryImpl.functions.toList().isEmpty());
    assertEquals(actualSchedulesFactoryImpl.functions, actualSchedulesFactoryImpl.tasksRegistry);
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore, boolean)}
   */
  @Test
  void testNewSchedulesFactoryImpl2() {
    // Arrange and Act
    SchedulesFactoryImpl actualSchedulesFactoryImpl = new SchedulesFactoryImpl(null, null, false);

    // Assert
    assertNull(actualSchedulesFactoryImpl.instancesStore);
    assertNull(actualSchedulesFactoryImpl.schedulesStore);
    assertTrue(actualSchedulesFactoryImpl.functions.toList().isEmpty());
    assertEquals(actualSchedulesFactoryImpl.functions, actualSchedulesFactoryImpl.tasksRegistry);
  }

  /**
   * Method under test:
   * {@link SchedulesFactoryImpl#SchedulesFactoryImpl(SchedulesStore, ScheduleInstancesStore, boolean)}
   */
  @Test
  void testNewSchedulesFactoryImpl3() {
    // Arrange and Act
    SchedulesFactoryImpl actualSchedulesFactoryImpl = new SchedulesFactoryImpl(mock(MockScheduleStore.class), null,
        true);

    // Assert
    assertNull(actualSchedulesFactoryImpl.instancesStore);
    assertTrue(actualSchedulesFactoryImpl.functions.toList().isEmpty());
    assertEquals(actualSchedulesFactoryImpl.functions, actualSchedulesFactoryImpl.tasksRegistry);
  }
}
