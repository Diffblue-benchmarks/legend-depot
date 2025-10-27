package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ManageSchedulesModuleDiffblueTest {
  /**
   * Method under test: {@link ManageSchedulesModule#initialiseHouseKeeper()}
   */
  @Test
  void testInitialiseHouseKeeper() {
    // Arrange, Act and Assert
    assertTrue((new ManageSchedulesModule()).initialiseHouseKeeper());
  }
}
