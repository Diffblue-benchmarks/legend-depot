package org.finos.legend.depot.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import ch.qos.logback.access.ViewStatusMessagesServlet;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
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
import org.finos.legend.depot.core.services.guice.MonitoringModule;
import org.finos.legend.depot.server.configuration.DepotServerConfiguration;
import org.finos.legend.depot.server.guice.DepotServerModule;
import org.finos.legend.depot.server.resources.guice.CoreDataResourcesModule;
import org.finos.legend.depot.server.resources.guice.EntitiesResourcesModule;
import org.finos.legend.depot.services.guice.CoreDataServicesModule;
import org.finos.legend.depot.services.guice.QueryMetricsModule;
import org.finos.legend.depot.services.guice.QueryMetricsSchedulesModule;
import org.finos.legend.depot.services.guice.SchedulesModule;
import org.finos.legend.depot.store.mongo.guice.CoreDataStoreMongoModule;
import org.finos.legend.depot.store.mongo.guice.QueryMetricsMongoStoreModule;
import org.finos.legend.depot.store.mongo.guice.SchedulesStoreMongoModule;
import org.glassfish.hk2.utilities.general.internal.MessageInterpolatorImpl;
import org.glassfish.jersey.server.validation.internal.InjectingConstraintValidatorFactory;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.resolver.DefaultTraversableResolver;
import org.hibernate.validator.internal.metadata.BeanMetaDataManager;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.spi.time.TimeProvider;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LegendDepotServerDiffblueTest {
  /**
   * Test new {@link LegendDepotServer} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link LegendDepotServer}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotServer.<init>()"})
  public void testNewLegendDepotServer() {
    // Arrange and Act
    LegendDepotServer actualLegendDepotServer = new LegendDepotServer();

    // Assert
    List<Module> serverModules = actualLegendDepotServer.getServerModules();
    assertEquals(22, serverModules.size());
    assertTrue(serverModules.get(0) instanceof ServerInfoModule);
    assertTrue(serverModules.get(21) instanceof MonitoringModule);
    assertTrue(serverModules.get(1) instanceof DepotServerModule);
    assertTrue(serverModules.get(2) instanceof CoreDataResourcesModule);
    assertTrue(serverModules.get(20) instanceof QueryMetricsSchedulesModule);
    assertTrue(serverModules.get(19) instanceof QueryMetricsMongoStoreModule);
    assertEquals("LegendDepotServer", actualLegendDepotServer.getName());
    Class<DepotServerConfiguration> expectedConfigurationClass = DepotServerConfiguration.class;
    assertEquals(expectedConfigurationClass, actualLegendDepotServer.getConfigurationClass());
  }

  /**
   * Test {@link LegendDepotServer#getServerModules()}.
   *
   * <p>Method under test: {@link LegendDepotServer#getServerModules()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List LegendDepotServer.getServerModules()"})
  public void testGetServerModules() {
    // Arrange and Act
    List<Module> actualServerModules = new LegendDepotServer().getServerModules();

    // Assert
    assertEquals(22, actualServerModules.size());
    assertTrue(actualServerModules.get(0) instanceof ServerInfoModule);
    assertTrue(actualServerModules.get(21) instanceof MonitoringModule);
    assertTrue(actualServerModules.get(1) instanceof DepotServerModule);
    assertTrue(actualServerModules.get(2) instanceof CoreDataResourcesModule);
    assertTrue(actualServerModules.get(5) instanceof EntitiesResourcesModule);
    assertTrue(actualServerModules.get(3) instanceof CoreDataServicesModule);
    assertTrue(actualServerModules.get(18) instanceof QueryMetricsModule);
    assertTrue(actualServerModules.get(20) instanceof QueryMetricsSchedulesModule);
    assertTrue(actualServerModules.get(Short.SIZE) instanceof SchedulesModule);
    assertTrue(actualServerModules.get(4) instanceof CoreDataStoreMongoModule);
    assertTrue(actualServerModules.get(19) instanceof QueryMetricsMongoStoreModule);
    assertTrue(actualServerModules.get(17) instanceof SchedulesStoreMongoModule);
  }

  /**
   * Test {@link LegendDepotServer#registerJacksonJsonProvider(JerseyEnvironment)}.
   *
   * <p>Method under test: {@link LegendDepotServer#registerJacksonJsonProvider(JerseyEnvironment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotServer.registerJacksonJsonProvider(JerseyEnvironment)"})
  public void testRegisterJacksonJsonProvider() {
    // Arrange
    LegendDepotServer legendDepotServer = new LegendDepotServer();
    JerseyContainerHolder holder = new JerseyContainerHolder(new ViewStatusMessagesServlet());
    JerseyEnvironment jerseyEnvironment =
        new JerseyEnvironment(holder, new DropwizardResourceConfig());

    // Act
    legendDepotServer.registerJacksonJsonProvider(jerseyEnvironment);

    // Assert
    assertEquals(5, jerseyEnvironment.getResourceConfig().getInstances().size());
  }

  /**
   * Test {@link LegendDepotServer#registerJacksonJsonProvider(JerseyEnvironment)}.
   *
   * <p>Method under test: {@link LegendDepotServer#registerJacksonJsonProvider(JerseyEnvironment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotServer.registerJacksonJsonProvider(JerseyEnvironment)"})
  public void testRegisterJacksonJsonProvider2() {
    // Arrange
    LegendDepotServer legendDepotServer = new LegendDepotServer();
    JerseyContainerHolder holder = new JerseyContainerHolder(new ViewStatusMessagesServlet());
    JerseyEnvironment jerseyEnvironment =
        new JerseyEnvironment(holder, new DropwizardResourceConfig(new MetricRegistry()));

    // Act
    legendDepotServer.registerJacksonJsonProvider(jerseyEnvironment);

    // Assert
    assertEquals(6, jerseyEnvironment.getResourceConfig().getInstances().size());
  }

  /**
   * Test {@link LegendDepotServer#initialiseCors(Environment)}.
   *
   * <p>Method under test: {@link LegendDepotServer#initialiseCors(Environment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotServer.initialiseCors(Environment)"})
  public void testInitialiseCors() {
    // Arrange
    LegendDepotServer legendDepotServer = new LegendDepotServer();
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
    legendDepotServer.initialiseCors(environment);

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
   * Test {@link LegendDepotServer#initialiseCors(Environment)}.
   *
   * <p>Method under test: {@link LegendDepotServer#initialiseCors(Environment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegendDepotServer.initialiseCors(Environment)"})
  public void testInitialiseCors2() {
    // Arrange
    LegendDepotServer legendDepotServer = new LegendDepotServer();
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
    legendDepotServer.initialiseCors(environment);

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
