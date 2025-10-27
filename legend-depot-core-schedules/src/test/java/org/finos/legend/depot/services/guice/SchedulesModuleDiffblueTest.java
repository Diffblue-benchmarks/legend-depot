package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.finos.legend.depot.services.schedules.SchedulesFactoryImpl;
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.junit.jupiter.api.Test;

class SchedulesModuleDiffblueTest {
  /**
   * Method under test:
   * {@link SchedulesModule#getFactory(SchedulesStore, ScheduleInstancesStore)}
   */
  @Test
  void testGetFactory() {
    // Arrange, Act and Assert
    assertTrue((new SchedulesModule()).getFactory(mock(SchedulesStore.class),
        mock(ScheduleInstancesStore.class)) instanceof SchedulesFactoryImpl);
    assertTrue((new ManageSchedulesModule()).getFactory(mock(SchedulesStore.class),
        mock(ScheduleInstancesStore.class)) instanceof SchedulesFactoryImpl);
  }

  /**
   * Method under test: {@link SchedulesModule#initialiseHouseKeeper()}
   */
  @Test
  void testInitialiseHouseKeeper() {
    // Arrange, Act and Assert
    assertFalse((new SchedulesModule()).initialiseHouseKeeper());
    assertTrue((new ManageSchedulesModule()).initialiseHouseKeeper());
  }
}
