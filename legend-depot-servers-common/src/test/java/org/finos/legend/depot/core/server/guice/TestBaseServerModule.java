package org.finos.legend.depot.core.server.guice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.ServletModule;
import org.finos.legend.depot.core.server.ServerConfiguration;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestBaseServerModule
{
    private TestableServerModule module;
    private ServerConfiguration configuration;

    private static class TestableServerModule extends BaseServerModule<ServerConfiguration>
    {
        private ServerConfiguration config;

        void setConfig(ServerConfiguration config)
        {
            this.config = config;
        }

        @Override
        protected ServerConfiguration getConfiguration()
        {
            return config;
        }

        @Override
        public void configure(Binder binder)
        {
            super.configure(binder);
        }
    }

    @BeforeEach
    void setUp()
    {
        module = new TestableServerModule();
        configuration = mock(ServerConfiguration.class);

        when(configuration.getApplicationName()).thenReturn("test-app");
        when(configuration.getStorageConfiguration()).thenReturn(Collections.emptyList());
        when(configuration.getProjectsConfiguration()).thenReturn(null);
        when(configuration.getOpenTracingConfiguration()).thenReturn(null);
        when(configuration.getPrometheusConfiguration()).thenReturn(null);

        module.setConfig(configuration);
    }

    private Injector createInjector()
    {
        return Guice.createInjector(new ServletModule(), module);
    }

    @Test
    void testProvideUserReturnsPrincipal()
    {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Principal expectedPrincipal = mock(Principal.class);
        when(request.getUserPrincipal()).thenReturn(expectedPrincipal);

        Principal result = module.provideUser(request);

        assertSame(expectedPrincipal, result);
        verify(request).getUserPrincipal();
    }

    @Test
    void testGetApplicationName()
    {
        String result = module.getApplicationName(configuration);

        assertEquals("test-app", result);
    }

    @Test
    void testGetConfig()
    {
        ServerConfiguration result = module.getConfig(configuration);

        assertSame(configuration, result);
    }

    @Test
    void testConfigureBindsConfigurations()
    {
        Binder binder = mock(Binder.class, Mockito.RETURNS_DEEP_STUBS);

        module.configure(binder);

        verify(binder).bind(ProjectsConfiguration.class);
        verify(binder).bind(OpenTracingConfiguration.class);
        verify(binder).bind(PrometheusConfiguration.class);
    }

    @Test
    void testGetProjectsConfigDefaultWhenNull()
    {
        when(configuration.getProjectsConfiguration()).thenReturn(null);

        Injector injector = createInjector();
        ProjectsConfiguration result = injector.getInstance(ProjectsConfiguration.class);

        assertNotNull(result);
        assertEquals("master", result.getDefaultBranch());
    }

    @Test
    void testGetProjectsConfigWhenSet()
    {
        ProjectsConfiguration projectsConfig = new ProjectsConfiguration("develop");
        when(configuration.getProjectsConfiguration()).thenReturn(projectsConfig);

        Injector injector = createInjector();
        ProjectsConfiguration result = injector.getInstance(ProjectsConfiguration.class);

        assertSame(projectsConfig, result);
    }

    @Test
    void testGetTracingConfigDefaultWhenNull()
    {
        when(configuration.getOpenTracingConfiguration()).thenReturn(null);

        Injector injector = createInjector();
        OpenTracingConfiguration result = injector.getInstance(OpenTracingConfiguration.class);

        assertNotNull(result);
    }

    @Test
    void testGetTracingConfigWhenSet()
    {
        OpenTracingConfiguration tracingConfig = new OpenTracingConfiguration();
        when(configuration.getOpenTracingConfiguration()).thenReturn(tracingConfig);

        Injector injector = createInjector();
        OpenTracingConfiguration result = injector.getInstance(OpenTracingConfiguration.class);

        assertSame(tracingConfig, result);
    }

    @Test
    void testGetPrometheusConfigDefaultWhenNull()
    {
        when(configuration.getPrometheusConfiguration()).thenReturn(null);

        Injector injector = createInjector();
        PrometheusConfiguration result = injector.getInstance(PrometheusConfiguration.class);

        assertNotNull(result);
    }

    @Test
    void testGetPrometheusConfigWhenSet()
    {
        PrometheusConfiguration prometheusConfig = new PrometheusConfiguration();
        when(configuration.getPrometheusConfiguration()).thenReturn(prometheusConfig);

        Injector injector = createInjector();
        PrometheusConfiguration result = injector.getInstance(PrometheusConfiguration.class);

        assertSame(prometheusConfig, result);
    }
}
