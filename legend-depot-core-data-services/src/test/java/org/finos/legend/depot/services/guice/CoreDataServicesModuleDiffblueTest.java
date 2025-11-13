package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.collections.api.block.function.Function2;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.services.api.dependencies.DependencyOverride;
import org.finos.legend.depot.services.dependencies.DependencyUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CoreDataServicesModuleDiffblueTest {
  /**
   * Test {@link CoreDataServicesModule#initialiseDependencyCache()}.
   *
   * <p>Method under test: {@link CoreDataServicesModule#initialiseDependencyCache()}
   */
  @Test
  @DisplayName("Test initialiseDependencyCache()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"DependencyOverride CoreDataServicesModule.initialiseDependencyCache()"})
  void testInitialiseDependencyCache() {
    // Arrange and Act
    DependencyOverride actualInitialiseDependencyCacheResult =
        new CoreDataServicesModule().initialiseDependencyCache();
    ArrayList<ProjectVersion> projectVersionList = new ArrayList<>();
    List<ProjectVersion> actualOverrideWithResult =
        actualInitialiseDependencyCacheResult.overrideWith(
            projectVersionList, new ArrayList<>(), mock(Function2.class));

    // Assert
    assertTrue(actualInitialiseDependencyCacheResult instanceof DependencyUtil);
    assertTrue(actualOverrideWithResult.isEmpty());
  }
}
