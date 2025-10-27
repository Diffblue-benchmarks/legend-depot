package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SchedulesModuleDiffblueTest {
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
    assertTrue((new ManageSchedulesModule()).initialiseHouseKeeper());
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
    assertFalse((new SchedulesModule()).initialiseHouseKeeper());
  }
}
