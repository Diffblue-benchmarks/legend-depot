package org.finos.legend.depot.core.server.resources.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.finos.legend.depot.core.server.info.InfoService;
import org.finos.legend.depot.core.server.info.InfoService.ServerInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InfoResourceDiffblueTest {
  /**
   * Test {@link InfoResource#getServerInfo()}.
   *
   * <ul>
   *   <li>Given {@link InfoService} {@link InfoService#getServerInfo()} return {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link InfoResource#getServerInfo()}
   */
  @Test
  @DisplayName(
      "Test getServerInfo(); given InfoService getServerInfo() return 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ServerInfo InfoResource.getServerInfo()"})
  void testGetServerInfo_givenInfoServiceGetServerInfoReturnNull_thenReturnNull() {
    // Arrange
    InfoService infoService = mock(InfoService.class);
    when(infoService.getServerInfo()).thenReturn(null);
    InfoResource infoResource = new InfoResource(infoService, null);

    // Act
    ServerInfo actualServerInfo = infoResource.getServerInfo();

    // Assert
    verify(infoService).getServerInfo();
    assertNull(actualServerInfo);
  }

  /**
   * Test {@link InfoResource#getServerConfig()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link InfoResource#getServerConfig()}
   */
  @Test
  @DisplayName("Test getServerConfig(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String InfoResource.getServerConfig()"})
  void testGetServerConfig_thenReturnNull() throws JsonProcessingException {
    // Arrange
    InfoResource infoResource = new InfoResource(null, null);

    // Act and Assert
    assertEquals("null", infoResource.getServerConfig());
  }
}
