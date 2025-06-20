package org.finos.legend.depot.store.resources.schedules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.security.Principal;
import javax.inject.Provider;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.schedules.SchedulesFactoryImpl;
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ManageSchedulesResourceDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ManageSchedulesResource#ManageSchedulesResource(AuthorisationProvider, Provider, SchedulesFactory, SchedulesStore, ScheduleInstancesStore)}
   *   <li>{@link ManageSchedulesResource#getResourceName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ManageSchedulesResource.<init>(AuthorisationProvider, Provider, SchedulesFactory, SchedulesStore, ScheduleInstancesStore)",
      "java.lang.String ManageSchedulesResource.getResourceName()"})
  void testGettersAndSetters() {
    // Arrange
    AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
    Provider<Principal> principalProvider = mock(Provider.class);

    // Act and Assert
    assertEquals(ManageSchedulesResource.SCHEDULES_RESOURCE,
        new ManageSchedulesResource(authorisationProvider, principalProvider,
            new SchedulesFactoryImpl(mock(SchedulesStore.class), mock(ScheduleInstancesStore.class), true),
            mock(SchedulesStore.class), mock(ScheduleInstancesStore.class)).getResourceName());
  }
}
