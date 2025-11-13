package org.finos.legend.depot.services.artifacts.handlers.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.classic.Logger;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.VersionedEntityArtifactsProvider;
import org.finos.legend.depot.services.api.versionedEntities.ManageVersionedEntitiesService;
import org.finos.legend.depot.services.versionedEntities.ManageVersionedEntitiesServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VersionedEntitiesHandlerImplDiffblueTest {
  /**
   * Test {@link
   * VersionedEntitiesHandlerImpl#VersionedEntitiesHandlerImpl(ManageVersionedEntitiesService,
   * VersionedEntityArtifactsProvider)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then LOGGER return {@link Logger}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VersionedEntitiesHandlerImpl#VersionedEntitiesHandlerImpl(ManageVersionedEntitiesService,
   * VersionedEntityArtifactsProvider)}
   */
  @Test
  @DisplayName(
      "Test new VersionedEntitiesHandlerImpl(ManageVersionedEntitiesService, VersionedEntityArtifactsProvider); when 'null'; then LOGGER return Logger")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VersionedEntitiesHandlerImpl.<init>(ManageVersionedEntitiesService, VersionedEntityArtifactsProvider)"
  })
  void testNewVersionedEntitiesHandlerImpl_whenNull_thenLoggerReturnLogger() {
    // Arrange and Act
    VersionedEntitiesHandlerImpl actualVersionedEntitiesHandlerImpl =
        new VersionedEntitiesHandlerImpl(null, new VersionedEntityProvider());

    // Assert
    org.slf4j.Logger lOGGER = actualVersionedEntitiesHandlerImpl.getLOGGER();
    assertTrue(lOGGER instanceof Logger);
    assertEquals(
        "org.finos.legend.depot.services.artifacts.handlers.entities.AbstractEntityRefreshHandlerImpl",
        lOGGER.getName());
    assertNull(((Logger) lOGGER).getLevel());
    assertNull(actualVersionedEntitiesHandlerImpl.getEntitiesApi());
    assertFalse(lOGGER.isDebugEnabled());
    assertFalse(lOGGER.isErrorEnabled());
    assertFalse(lOGGER.isInfoEnabled());
    assertFalse(lOGGER.isTraceEnabled());
    assertFalse(lOGGER.isWarnEnabled());
    assertTrue(((Logger) lOGGER).isAdditive());
  }

  /**
   * Test {@link VersionedEntitiesHandlerImpl#delete(String, String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ManageVersionedEntitiesServiceImpl#delete(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link VersionedEntitiesHandlerImpl#delete(String, String, String)}
   */
  @Test
  @DisplayName("Test delete(String, String, String); then calls delete(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionedEntitiesHandlerImpl.delete(String, String, String)"})
  void testDelete_thenCallsDelete() {
    // Arrange
    ManageVersionedEntitiesServiceImpl versionedEntitiesService =
        mock(ManageVersionedEntitiesServiceImpl.class);
    when(versionedEntitiesService.delete(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(1L);
    VersionedEntitiesHandlerImpl versionedEntitiesHandlerImpl =
        new VersionedEntitiesHandlerImpl(versionedEntitiesService, new VersionedEntityProvider());

    // Act
    versionedEntitiesHandlerImpl.delete("42", "42", "42");

    // Assert
    verify(versionedEntitiesService).delete("42", "42", "42");
  }
}
