package org.finos.legend.depot.server.configuration;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import io.dropwizard.logging.DefaultLoggingFactory;
import io.dropwizard.server.DefaultServerFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DepotServerConfigurationDiffblueTest {
  /**
   * Test new {@link DepotServerConfiguration} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link DepotServerConfiguration}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotServerConfiguration.<init>()"})
  public void testNewDepotServerConfiguration() {
    // Arrange and Act
    DepotServerConfiguration actualDepotServerConfiguration = new DepotServerConfiguration();

    // Assert
    assertTrue(actualDepotServerConfiguration.getLoggingFactory() instanceof DefaultLoggingFactory);
    assertTrue(actualDepotServerConfiguration.getServerFactory() instanceof DefaultServerFactory);
    assertNull(actualDepotServerConfiguration.getSwaggerBundleConfiguration());
    assertNull(actualDepotServerConfiguration.getApplicationName());
    assertNull(actualDepotServerConfiguration.getDeployment());
    assertNull(actualDepotServerConfiguration.getSessionCookie());
    assertNull(actualDepotServerConfiguration.getUrlPattern());
    assertNull(actualDepotServerConfiguration.getStorageConfiguration());
    assertNull(actualDepotServerConfiguration.getFilterPriorities());
    assertNull(actualDepotServerConfiguration.getPrometheusConfiguration());
    assertNull(actualDepotServerConfiguration.getOpenTracingConfiguration());
    assertNull(actualDepotServerConfiguration.getProjectsConfiguration());
    assertNull(actualDepotServerConfiguration.getPac4jConfiguration());
  }
}
