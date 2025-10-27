package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ManageSchedulesModuleDiffblueTest {
  /**
   * Test {@link ManageSchedulesModule#initialiseHouseKeeper()}.
   * <p>
   * Method under test: {@link ManageSchedulesModule#initialiseHouseKeeper()}
   */
  @Test
  @DisplayName("Test initialiseHouseKeeper()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ManageSchedulesModule.initialiseHouseKeeper()"})
  void testInitialiseHouseKeeper() {
    // Arrange, Act and Assert
    assertTrue((new ManageSchedulesModule()).initialiseHouseKeeper());
  }
}
