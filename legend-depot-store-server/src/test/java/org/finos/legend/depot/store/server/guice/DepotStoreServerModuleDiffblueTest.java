package org.finos.legend.depot.store.server.guice;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.BindingBuilder;
import com.google.inject.spi.Element;
import io.dropwizard.logging.DefaultLoggingFactory;
import io.dropwizard.metrics.MetricsFactory;
import io.dropwizard.server.DefaultServerFactory;
import java.util.ArrayList;
import java.util.List;
import org.finos.legend.depot.core.server.error.configuration.ExceptionMapperConfiguration;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.TracerProvider;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRefreshPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRetentionPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.configuration.IncludeProjectPropertiesConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryConfiguration;
import org.finos.legend.depot.services.api.notifications.queue.QueueManagerConfiguration;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.store.StorageConfiguration;
import org.finos.legend.depot.store.server.configuration.DepotStoreServerConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class DepotStoreServerModuleDiffblueTest {
  /**
   * Test {@link DepotStoreServerModule#configure(Binder)}.
   *
   * <ul>
   *   <li>Given {@link OpenTracingConfiguration} (default constructor) Enabled is {@code true}.
   *   <li>Then calls {@link Binder#addError(String, Object[])}.
   * </ul>
   *
   * <p>Method under test: {@link DepotStoreServerModule#configure(Binder)}
   */
  @Test
  @DisplayName(
      "Test configure(Binder); given OpenTracingConfiguration (default constructor) Enabled is 'true'; then calls addError(String, Object[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DepotStoreServerModule.configure(Binder)"})
  void testConfigure_givenOpenTracingConfigurationEnabledIsTrue_thenCallsAddError() {
    // Arrange
    OpenTracingConfiguration openTracingConfiguration = new OpenTracingConfiguration();
    openTracingConfiguration.setEnabled(true);
    openTracingConfiguration.setOpenTracingUri("Open Tracing Uri");
    openTracingConfiguration.setServiceName("Service Name");
    openTracingConfiguration.setTracerProvider(mock(TracerProvider.class));

    QueueManagerConfiguration queueManagerConfiguration = new QueueManagerConfiguration();
    queueManagerConfiguration.setNumberOfQueueWorkers(1L);
    queueManagerConfiguration.setQueueDelay(1L);
    queueManagerConfiguration.setQueueInterval(42L);

    DepotStoreServerConfiguration depotStoreServerConfiguration =
        new DepotStoreServerConfiguration();
    depotStoreServerConfiguration.setArtifactRepositoryProviderConfiguration(
        new VoidArtifactRepositoryConfiguration());
    ArrayList<String> properties = new ArrayList<>();
    IncludeProjectPropertiesConfiguration includeProjectPropertiesConfiguration =
        new IncludeProjectPropertiesConfiguration(properties, new ArrayList<>());
    ArtifactsRefreshPolicyConfiguration artifactsRefreshPolicyConfiguration =
        new ArtifactsRefreshPolicyConfiguration(42L, includeProjectPropertiesConfiguration);
    depotStoreServerConfiguration.setArtifactsRefreshPolicyConfiguration(
        artifactsRefreshPolicyConfiguration);
    depotStoreServerConfiguration.setExceptionMapperConfiguration(
        new ExceptionMapperConfiguration());
    depotStoreServerConfiguration.setLoggingFactory(new DefaultLoggingFactory());
    depotStoreServerConfiguration.setMetricsFactory(new MetricsFactory());
    depotStoreServerConfiguration.setOpenTracingConfiguration(openTracingConfiguration);
    depotStoreServerConfiguration.setPrometheusConfiguration(new PrometheusConfiguration());
    depotStoreServerConfiguration.setQueueManagerConfiguration(queueManagerConfiguration);
    depotStoreServerConfiguration.setRetentionPolicyConfiguration(
        new ArtifactsRetentionPolicyConfiguration(3, 1, 1));
    depotStoreServerConfiguration.setServerFactory(new DefaultServerFactory());
    depotStoreServerConfiguration.setStorage(new ArrayList<>());

    DepotStoreServerModule depotStoreServerModule = new DepotStoreServerModule();
    depotStoreServerModule.setConfiguration(depotStoreServerConfiguration);

    Binder binder = mock(Binder.class);
    doNothing().when(binder).addError(Mockito.<String>any(), (Object[]) Mockito.any());
    ArrayList<Element> elements = new ArrayList<>();
    Class<ProjectsConfiguration> type = ProjectsConfiguration.class;
    Key<ProjectsConfiguration> key = Key.get(type);

    BindingBuilder<ProjectsConfiguration> bindingBuilder =
        new BindingBuilder<>(binder, elements, "Source", key);

    Binder binder2 = mock(Binder.class);
    Binder binder3 = mock(Binder.class);
    ArrayList<Element> elements2 = new ArrayList<>();
    Class<Object> type2 = Object.class;
    Key<Object> key2 = Key.get(type2);

    BindingBuilder<Object> bindingBuilder2 =
        new BindingBuilder<>(binder3, elements2, "Source", key2);
    when(binder2.bind(Mockito.<TypeLiteral<Object>>any())).thenReturn(bindingBuilder2);
    Binder binder4 = mock(Binder.class);
    ArrayList<Element> elements3 = new ArrayList<>();
    Class<List> forNameResult = List.class;
    Key<List<StorageConfiguration>> key3 =
        Key.get((Class<List<StorageConfiguration>>) (Class) forNameResult);

    BindingBuilder<List<StorageConfiguration>> bindingBuilder3 =
        new BindingBuilder<>(binder4, elements3, "Source", key3);
    when(binder2.bind(Mockito.<TypeLiteral<List<StorageConfiguration>>>any()))
        .thenReturn(bindingBuilder3);
    when(binder2.bind(Mockito.<Class<ProjectsConfiguration>>any())).thenReturn(bindingBuilder);

    // Act
    depotStoreServerModule.configure(binder2);

    // Assert
    verify(binder, atLeast(1))
        .addError(eq("Implementation is set more than once."), (Object[]) Mockito.any());
    verify(binder2, atLeast(1)).bind(isA(TypeLiteral.class));
    verify(binder2, atLeast(1)).bind(Mockito.<Class<Object>>any());
  }
}
