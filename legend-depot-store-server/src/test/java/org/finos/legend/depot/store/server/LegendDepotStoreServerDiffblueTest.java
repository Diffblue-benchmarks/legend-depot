package org.finos.legend.depot.store.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import ch.qos.logback.access.ViewStatusMessagesServlet;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.inject.Module;
import io.dropwizard.jersey.DropwizardResourceConfig;
import io.dropwizard.jersey.setup.JerseyContainerHolder;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jersey.validation.DropwizardConfiguredValidator;
import io.dropwizard.jersey.validation.JerseyParameterNameProvider;
import io.dropwizard.setup.Environment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javassist.Loader;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletHandler;
import org.finos.legend.depot.core.server.guice.ServerInfoModule;
import org.finos.legend.depot.core.services.guice.AuthorisationModule;
import org.finos.legend.depot.core.services.guice.MonitoringModule;
import org.finos.legend.depot.services.guice.ManageCoreDataServicesModule;
import org.finos.legend.depot.services.guice.VersionReconciliationSchedulesModule;
import org.finos.legend.depot.store.mongo.core.MongoClientModule;
import org.finos.legend.depot.store.mongo.guice.ManageCoreDataStoreMongoModule;
import org.finos.legend.depot.store.mongo.guice.ManageMongoStoreModule;
import org.finos.legend.depot.store.mongo.guice.ManageMongoStoreSchedulesModule;
import org.finos.legend.depot.store.mongo.guice.ManageQueryMetricsMongoStoreModule;
import org.finos.legend.depot.store.resources.guice.ManageCoreDataResourcesModule;
import org.finos.legend.depot.store.server.configuration.DepotStoreServerConfiguration;
import org.finos.legend.depot.store.server.guice.DepotStoreServerModule;
import org.glassfish.hk2.utilities.general.internal.MessageInterpolatorImpl;
import org.glassfish.jersey.server.validation.internal.InjectingConstraintValidatorFactory;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.resolver.DefaultTraversableResolver;
import org.hibernate.validator.internal.metadata.BeanMetaDataManager;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.spi.time.TimeProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class LegendDepotStoreServerDiffblueTest {
  /**
   * Test new {@link LegendDepotStoreServer} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link LegendDepotStoreServer}
   */
  @Test
  @DisplayName("Test new LegendDepotStoreServer (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotStoreServer.<init>()"})
  void testNewLegendDepotStoreServer() {
    // Arrange and Act
    LegendDepotStoreServer actualLegendDepotStoreServer = new LegendDepotStoreServer();

    // Assert
    List<Module> serverModules = actualLegendDepotStoreServer.getServerModules();
    assertEquals(36, serverModules.size());
    assertTrue(serverModules.get(0) instanceof ServerInfoModule);
    assertTrue(serverModules.get(33) instanceof MongoClientModule);
    assertTrue(serverModules.get(35) instanceof ManageMongoStoreModule);
    assertTrue(serverModules.get(34) instanceof ManageMongoStoreSchedulesModule);
    assertTrue(serverModules.get(2) instanceof ManageCoreDataResourcesModule);
    assertTrue(serverModules.get(1) instanceof DepotStoreServerModule);
    assertEquals("LegendDepotStoreServer", actualLegendDepotStoreServer.getName());
    Class<DepotStoreServerConfiguration> expectedConfigurationClass =
        DepotStoreServerConfiguration.class;
    assertEquals(expectedConfigurationClass, actualLegendDepotStoreServer.getConfigurationClass());
  }

  /**
   * Test {@link LegendDepotStoreServer#registerJacksonJsonProvider(JerseyEnvironment)}.
   *
   * <p>Method under test: {@link
   * LegendDepotStoreServer#registerJacksonJsonProvider(JerseyEnvironment)}
   */
  @Test
  @DisplayName("Test registerJacksonJsonProvider(JerseyEnvironment)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotStoreServer.registerJacksonJsonProvider(JerseyEnvironment)"})
  void testRegisterJacksonJsonProvider() {
    // Arrange
    LegendDepotStoreServer legendDepotStoreServer = new LegendDepotStoreServer();
    JerseyContainerHolder holder = new JerseyContainerHolder(new ViewStatusMessagesServlet());
    JerseyEnvironment jerseyEnvironment =
        new JerseyEnvironment(holder, new DropwizardResourceConfig());

    // Act
    legendDepotStoreServer.registerJacksonJsonProvider(jerseyEnvironment);

    // Assert
    assertEquals(10, jerseyEnvironment.getResourceConfig().getClasses().size());
  }

  /**
   * Test {@link LegendDepotStoreServer#getServerModules()}.
   *
   * <p>Method under test: {@link LegendDepotStoreServer#getServerModules()}
   */
  @Test
  @DisplayName("Test getServerModules()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List LegendDepotStoreServer.getServerModules()"})
  void testGetServerModules() {
    // Arrange and Act
    List<Module> actualServerModules = new LegendDepotStoreServer().getServerModules();

    // Assert
    assertEquals(36, actualServerModules.size());
    assertTrue(actualServerModules.get(0) instanceof ServerInfoModule);
    assertTrue(actualServerModules.get(31) instanceof AuthorisationModule);
    assertTrue(actualServerModules.get(Integer.SIZE) instanceof MonitoringModule);
    assertTrue(actualServerModules.get(3) instanceof ManageCoreDataServicesModule);
    assertTrue(actualServerModules.get(5) instanceof VersionReconciliationSchedulesModule);
    assertTrue(actualServerModules.get(33) instanceof MongoClientModule);
    assertTrue(actualServerModules.get(4) instanceof ManageCoreDataStoreMongoModule);
    assertTrue(actualServerModules.get(35) instanceof ManageMongoStoreModule);
    assertTrue(actualServerModules.get(34) instanceof ManageMongoStoreSchedulesModule);
    assertTrue(actualServerModules.get(30) instanceof ManageQueryMetricsMongoStoreModule);
    assertTrue(actualServerModules.get(2) instanceof ManageCoreDataResourcesModule);
    assertTrue(actualServerModules.get(1) instanceof DepotStoreServerModule);
  }

  /**
   * Test {@link LegendDepotStoreServer#initialiseCors(Environment)}.
   *
   * <p>Method under test: {@link LegendDepotStoreServer#initialiseCors(Environment)}
   */
  @Test
  @DisplayName("Test initialiseCors(Environment)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotStoreServer.initialiseCors(Environment)"})
  void testInitialiseCors() {
    // Arrange
    LegendDepotStoreServer legendDepotStoreServer = new LegendDepotStoreServer();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    InjectingConstraintValidatorFactory constraintValidatorFactory =
        new InjectingConstraintValidatorFactory();
    MessageInterpolatorImpl messageInterpolator = new MessageInterpolatorImpl();
    DefaultTraversableResolver traversableResolver = new DefaultTraversableResolver();
    BeanMetaDataManager beanMetaDataManager = mock(BeanMetaDataManager.class);
    JerseyParameterNameProvider parameterNameProvider = new JerseyParameterNameProvider();
    TimeProvider timeProvider = mock(TimeProvider.class);
    TypeResolutionHelper typeResolutionHelper = new TypeResolutionHelper();

    ValidatorImpl validator =
        new ValidatorImpl(
            constraintValidatorFactory,
            messageInterpolator,
            traversableResolver,
            beanMetaDataManager,
            parameterNameProvider,
            timeProvider,
            typeResolutionHelper,
            new ArrayList<>(),
            mock(ConstraintValidatorManager.class),
            true);
    DropwizardConfiguredValidator validator2 = new DropwizardConfiguredValidator(validator);
    MetricRegistry metricRegistry = new MetricRegistry();

    Environment environment =
        new Environment("Name", objectMapper, validator2, metricRegistry, new Loader());

    // Act
    legendDepotStoreServer.initialiseCors(environment);

    // Assert
    Handler handler = environment.getApplicationContext().getHandler();
    Collection<Object> beans = ((ServletHandler) handler).getBeans();
    assertEquals(2, beans.size());
    assertTrue(beans instanceof List);
    Object getResult = ((List<Object>) beans).get(0);
    assertTrue(getResult instanceof FilterHolder);
    assertTrue(handler instanceof ServletHandler);
    FilterMapping[] filterMappings = ((ServletHandler) handler).getFilterMappings();
    assertEquals(1, filterMappings.length);
    FilterHolder[] filters = ((ServletHandler) handler).getFilters();
    assertEquals(1, filters.length);
    assertSame(((List<Object>) beans).get(1), filterMappings[0]);
    assertSame(getResult, filters[0]);
    assertSame(handler, ((FilterHolder) getResult).getServletHandler());
  }

  /**
   * Test {@link LegendDepotStoreServer#initialiseCors(Environment)}.
   *
   * <p>Method under test: {@link LegendDepotStoreServer#initialiseCors(Environment)}
   */
  @Test
  @DisplayName("Test initialiseCors(Environment)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotStoreServer.initialiseCors(Environment)"})
  void testInitialiseCors2() {
    // Arrange
    LegendDepotStoreServer legendDepotStoreServer = new LegendDepotStoreServer();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    InjectingConstraintValidatorFactory constraintValidatorFactory =
        new InjectingConstraintValidatorFactory();
    MessageInterpolatorImpl messageInterpolator = new MessageInterpolatorImpl();
    DefaultTraversableResolver traversableResolver = new DefaultTraversableResolver();
    BeanMetaDataManager beanMetaDataManager = mock(BeanMetaDataManager.class);
    JerseyParameterNameProvider parameterNameProvider = new JerseyParameterNameProvider();
    TimeProvider timeProvider = mock(TimeProvider.class);
    TypeResolutionHelper typeResolutionHelper = new TypeResolutionHelper();

    ValidatorImpl validator =
        new ValidatorImpl(
            constraintValidatorFactory,
            messageInterpolator,
            traversableResolver,
            beanMetaDataManager,
            parameterNameProvider,
            timeProvider,
            typeResolutionHelper,
            new ArrayList<>(),
            mock(ConstraintValidatorManager.class),
            true);
    DropwizardConfiguredValidator validator2 = new DropwizardConfiguredValidator(validator);
    MetricRegistry metricRegistry = new MetricRegistry();
    Loader classLoader = new Loader();

    Environment environment =
        new Environment(
            "CORS",
            objectMapper,
            validator2,
            metricRegistry,
            classLoader,
            new HealthCheckRegistry());

    // Act
    legendDepotStoreServer.initialiseCors(environment);

    // Assert
    Handler handler = environment.getApplicationContext().getHandler();
    Collection<Object> beans = ((ServletHandler) handler).getBeans();
    assertEquals(2, beans.size());
    assertTrue(beans instanceof List);
    Object getResult = ((List<Object>) beans).get(0);
    assertTrue(getResult instanceof FilterHolder);
    assertTrue(handler instanceof ServletHandler);
    FilterMapping[] filterMappings = ((ServletHandler) handler).getFilterMappings();
    assertEquals(1, filterMappings.length);
    FilterHolder[] filters = ((ServletHandler) handler).getFilters();
    assertEquals(1, filters.length);
    assertSame(((List<Object>) beans).get(1), filterMappings[0]);
    assertSame(getResult, filters[0]);
    assertSame(handler, ((FilterHolder) getResult).getServletHandler());
  }
}
