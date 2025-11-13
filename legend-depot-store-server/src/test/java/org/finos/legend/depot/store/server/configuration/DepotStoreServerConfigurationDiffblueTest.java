package org.finos.legend.depot.store.server.configuration;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import io.dropwizard.logging.DefaultLoggingFactory;
import io.dropwizard.server.DefaultServerFactory;
import java.util.ArrayList;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRefreshPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRetentionPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.configuration.IncludeProjectPropertiesConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryProviderConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryConfiguration;
import org.finos.legend.depot.services.api.notifications.queue.QueueManagerConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DepotStoreServerConfigurationDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       DepotStoreServerConfiguration#setArtifactRepositoryProviderConfiguration(ArtifactRepositoryProviderConfiguration)}
   *   <li>{@link
   *       DepotStoreServerConfiguration#setArtifactsRefreshPolicyConfiguration(ArtifactsRefreshPolicyConfiguration)}
   *   <li>{@link
   *       DepotStoreServerConfiguration#setQueueManagerConfiguration(QueueManagerConfiguration)}
   *   <li>{@link
   *       DepotStoreServerConfiguration#setRetentionPolicyConfiguration(ArtifactsRetentionPolicyConfiguration)}
   *   <li>{@link DepotStoreServerConfiguration#getArtifactRepositoryProviderConfiguration()}
   *   <li>{@link DepotStoreServerConfiguration#getArtifactsRefreshPolicyConfiguration()}
   *   <li>{@link DepotStoreServerConfiguration#getQueueManagerConfiguration()}
   *   <li>{@link DepotStoreServerConfiguration#getRetentionPolicyConfiguration()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ArtifactRepositoryProviderConfiguration DepotStoreServerConfiguration.getArtifactRepositoryProviderConfiguration()",
    "ArtifactsRefreshPolicyConfiguration DepotStoreServerConfiguration.getArtifactsRefreshPolicyConfiguration()",
    "QueueManagerConfiguration DepotStoreServerConfiguration.getQueueManagerConfiguration()",
    "ArtifactsRetentionPolicyConfiguration DepotStoreServerConfiguration.getRetentionPolicyConfiguration()",
    "void DepotStoreServerConfiguration.setArtifactRepositoryProviderConfiguration(ArtifactRepositoryProviderConfiguration)",
    "void DepotStoreServerConfiguration.setArtifactsRefreshPolicyConfiguration(ArtifactsRefreshPolicyConfiguration)",
    "void DepotStoreServerConfiguration.setQueueManagerConfiguration(QueueManagerConfiguration)",
    "void DepotStoreServerConfiguration.setRetentionPolicyConfiguration(ArtifactsRetentionPolicyConfiguration)"
  })
  void testGettersAndSetters() {
    // Arrange
    DepotStoreServerConfiguration depotStoreServerConfiguration =
        new DepotStoreServerConfiguration();
    VoidArtifactRepositoryConfiguration artifactRepositoryProviderConfiguration =
        new VoidArtifactRepositoryConfiguration();

    // Act
    depotStoreServerConfiguration.setArtifactRepositoryProviderConfiguration(
        artifactRepositoryProviderConfiguration);
    ArrayList<String> properties = new ArrayList<>();
    IncludeProjectPropertiesConfiguration includeProjectPropertiesConfiguration =
        new IncludeProjectPropertiesConfiguration(properties, new ArrayList<>());
    ArtifactsRefreshPolicyConfiguration artifactsRefreshPolicyConfiguration =
        new ArtifactsRefreshPolicyConfiguration(42L, includeProjectPropertiesConfiguration);
    depotStoreServerConfiguration.setArtifactsRefreshPolicyConfiguration(
        artifactsRefreshPolicyConfiguration);
    QueueManagerConfiguration queueManagerConfiguration = new QueueManagerConfiguration();
    queueManagerConfiguration.setNumberOfQueueWorkers(1L);
    queueManagerConfiguration.setQueueDelay(1L);
    queueManagerConfiguration.setQueueInterval(42L);
    depotStoreServerConfiguration.setQueueManagerConfiguration(queueManagerConfiguration);
    ArtifactsRetentionPolicyConfiguration artifactsRetentionPolicyConfiguration =
        new ArtifactsRetentionPolicyConfiguration(3, 1, 1);
    depotStoreServerConfiguration.setRetentionPolicyConfiguration(
        artifactsRetentionPolicyConfiguration);
    ArtifactRepositoryProviderConfiguration actualArtifactRepositoryProviderConfiguration =
        depotStoreServerConfiguration.getArtifactRepositoryProviderConfiguration();
    ArtifactsRefreshPolicyConfiguration actualArtifactsRefreshPolicyConfiguration =
        depotStoreServerConfiguration.getArtifactsRefreshPolicyConfiguration();
    QueueManagerConfiguration actualQueueManagerConfiguration =
        depotStoreServerConfiguration.getQueueManagerConfiguration();

    // Assert
    assertTrue(
        actualArtifactRepositoryProviderConfiguration
            instanceof VoidArtifactRepositoryConfiguration);
    assertSame(artifactsRefreshPolicyConfiguration, actualArtifactsRefreshPolicyConfiguration);
    assertSame(
        artifactsRetentionPolicyConfiguration,
        depotStoreServerConfiguration.getRetentionPolicyConfiguration());
    assertSame(
        artifactRepositoryProviderConfiguration, actualArtifactRepositoryProviderConfiguration);
    assertSame(queueManagerConfiguration, actualQueueManagerConfiguration);
  }

  /**
   * Test new {@link DepotStoreServerConfiguration} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * DepotStoreServerConfiguration}
   */
  @Test
  @DisplayName("Test new DepotStoreServerConfiguration (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotStoreServerConfiguration.<init>()"})
  void testNewDepotStoreServerConfiguration() {
    // Arrange and Act
    DepotStoreServerConfiguration actualDepotStoreServerConfiguration =
        new DepotStoreServerConfiguration();

    // Assert
    assertTrue(
        actualDepotStoreServerConfiguration.getLoggingFactory() instanceof DefaultLoggingFactory);
    assertTrue(
        actualDepotStoreServerConfiguration.getServerFactory() instanceof DefaultServerFactory);
    assertNull(actualDepotStoreServerConfiguration.getSwaggerBundleConfiguration());
    assertNull(actualDepotStoreServerConfiguration.getApplicationName());
    assertNull(actualDepotStoreServerConfiguration.getDeployment());
    assertNull(actualDepotStoreServerConfiguration.getSessionCookie());
    assertNull(actualDepotStoreServerConfiguration.getUrlPattern());
    assertNull(actualDepotStoreServerConfiguration.getStorageConfiguration());
    assertNull(actualDepotStoreServerConfiguration.getFilterPriorities());
    assertNull(actualDepotStoreServerConfiguration.getPrometheusConfiguration());
    assertNull(actualDepotStoreServerConfiguration.getOpenTracingConfiguration());
    assertNull(actualDepotStoreServerConfiguration.getArtifactsRefreshPolicyConfiguration());
    assertNull(actualDepotStoreServerConfiguration.getRetentionPolicyConfiguration());
    assertNull(actualDepotStoreServerConfiguration.getArtifactRepositoryProviderConfiguration());
    assertNull(actualDepotStoreServerConfiguration.getQueueManagerConfiguration());
    assertNull(actualDepotStoreServerConfiguration.getProjectsConfiguration());
    assertNull(actualDepotStoreServerConfiguration.getPac4jConfiguration());
  }
}
