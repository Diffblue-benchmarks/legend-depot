package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.services.schedules.SchedulesFactoryImpl;
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SchedulesModuleDiffblueTest {
  /**
   * Test {@link SchedulesModule#getFactory(SchedulesStore, ScheduleInstancesStore)}.
   * <ul>
   *   <li>Given {@link ManageSchedulesModule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesModule#getFactory(SchedulesStore, ScheduleInstancesStore)}
   */
  @Test
  @DisplayName("Test getFactory(SchedulesStore, ScheduleInstancesStore); given ManageSchedulesModule (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.finos.legend.depot.services.api.schedules.SchedulesFactory SchedulesModule.getFactory(SchedulesStore, ScheduleInstancesStore)"})
  void testGetFactory_givenManageSchedulesModule() {
    // Arrange, Act and Assert
    assertTrue(new ManageSchedulesModule().getFactory(mock(SchedulesStore.class),
        mock(ScheduleInstancesStore.class)) instanceof SchedulesFactoryImpl);
  }

  /**
   * Test {@link SchedulesModule#getFactory(SchedulesStore, ScheduleInstancesStore)}.
   * <ul>
   *   <li>Given {@link SchedulesModule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesModule#getFactory(SchedulesStore, ScheduleInstancesStore)}
   */
  @Test
  @DisplayName("Test getFactory(SchedulesStore, ScheduleInstancesStore); given SchedulesModule (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.finos.legend.depot.services.api.schedules.SchedulesFactory SchedulesModule.getFactory(SchedulesStore, ScheduleInstancesStore)"})
  void testGetFactory_givenSchedulesModule() {
    // Arrange, Act and Assert
    assertTrue(new SchedulesModule().getFactory(mock(SchedulesStore.class),
        mock(ScheduleInstancesStore.class)) instanceof SchedulesFactoryImpl);
  }

  /**
   * Test {@link SchedulesModule#initialiseHouseKeeper()}.
   * <ul>
   *   <li>Given {@link ManageSchedulesModule} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesModule#initialiseHouseKeeper()}
   */
  @Test
  @DisplayName("Test initialiseHouseKeeper(); given ManageSchedulesModule (default constructor); then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SchedulesModule.initialiseHouseKeeper()"})
  void testInitialiseHouseKeeper_givenManageSchedulesModule_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new ManageSchedulesModule().initialiseHouseKeeper());
  }

  /**
   * Test {@link SchedulesModule#initialiseHouseKeeper()}.
   * <ul>
   *   <li>Given {@link SchedulesModule} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SchedulesModule#initialiseHouseKeeper()}
   */
  @Test
  @DisplayName("Test initialiseHouseKeeper(); given SchedulesModule (default constructor); then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SchedulesModule.initialiseHouseKeeper()"})
  void testInitialiseHouseKeeper_givenSchedulesModule_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new SchedulesModule().initialiseHouseKeeper());
  }
}
