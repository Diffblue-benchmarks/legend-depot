//  Copyright 2021 Goldman Sachs
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

package org.finos.legend.depot.core.server;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Module;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.MutableServletContextHandler;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.AdminEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.finos.legend.depot.core.server.error.configuration.ExceptionMapperConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.Servlet;
import javax.servlet.ServletRegistration;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.eclipse.jetty.util.component.LifeCycle;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestBaseServer
{
    private TestableBaseServer server;

    private static class TestableBaseServer extends BaseServer<ServerConfiguration>
    {
        @Override
        protected List<Module> getServerModules()
        {
            return Collections.emptyList();
        }

        @Override
        protected void registerJacksonJsonProvider(JerseyEnvironment jerseyEnvironment)
        {
        }

        @Override
        protected void initialiseCors(Environment environment)
        {
        }

        public boolean callIsEnvironmentVariableSubstitutionStrict()
        {
            return isEnvironmentVariableSubstitutionStrict();
        }

        public void callConfigureObjectMapper(Bootstrap<ServerConfiguration> bootstrap)
        {
            configureObjectMapper(bootstrap);
        }
    }

    @BeforeEach
    void setUp()
    {
        server = new TestableBaseServer();
    }

    @Test
    void canCreateBaseServer()
    {
        assertNotNull(server);
    }

    @Test
    void testServerStartedConstant()
    {
        assertEquals("server started", BaseServer.SERVER_STARTED);
    }

    @Test
    void testIsEnvironmentVariableSubstitutionStrict()
    {
        assertTrue(server.callIsEnvironmentVariableSubstitutionStrict());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testConfigureObjectMapper()
    {
        Bootstrap<ServerConfiguration> bootstrap = mock(Bootstrap.class);
        ObjectMapper objectMapper = new ObjectMapper();
        when(bootstrap.getObjectMapper()).thenReturn(objectMapper);

        server.callConfigureObjectMapper(bootstrap);

        verify(bootstrap, times(3)).getObjectMapper();
    }

    @Test
    void testRunRegistersComponents()
    {
        ServerConfiguration configuration = mock(ServerConfiguration.class);
        Environment environment = mock(Environment.class);
        JerseyEnvironment jersey = mock(JerseyEnvironment.class);
        LifecycleEnvironment lifecycle = mock(LifecycleEnvironment.class);
        HealthCheckRegistry healthChecks = mock(HealthCheckRegistry.class);
        AdminEnvironment admin = mock(AdminEnvironment.class);
        ServletEnvironment servlets = mock(ServletEnvironment.class);
        ServletRegistration.Dynamic dynamic = mock(ServletRegistration.Dynamic.class);
        MetricRegistry metricRegistry = new MetricRegistry();

        when(environment.jersey()).thenReturn(jersey);
        when(environment.lifecycle()).thenReturn(lifecycle);
        when(environment.healthChecks()).thenReturn(healthChecks);
        when(environment.servlets()).thenReturn(servlets);
        when(environment.admin()).thenReturn(admin);
        when(environment.metrics()).thenReturn(metricRegistry);
        when(environment.getApplicationContext()).thenReturn(mock(MutableServletContextHandler.class));

        when(admin.addServlet(anyString(), any(Servlet.class))).thenReturn(dynamic);
        when(dynamic.addMapping(anyString())).thenReturn(null);

        when(configuration.getSessionCookie()).thenReturn(null);
        when(configuration.getFilterPriorities()).thenReturn(null);
        when(configuration.getUrlPattern()).thenReturn(null);
        when(configuration.getApplicationName()).thenReturn("test-app");
        when(configuration.getExceptionMapperConfiguration()).thenReturn(new ExceptionMapperConfiguration());

        server.run(configuration, environment);

        verify(jersey).register(any(Class.class));
        verify(healthChecks).register(eq("HealthCheck"), any());
        verify(lifecycle).addLifeCycleListener(any());
    }

    @Test
    void testRunWithSessionCookie()
    {
        ServerConfiguration configuration = mock(ServerConfiguration.class);
        Environment environment = mock(Environment.class);
        JerseyEnvironment jersey = mock(JerseyEnvironment.class);
        LifecycleEnvironment lifecycle = mock(LifecycleEnvironment.class);
        HealthCheckRegistry healthChecks = mock(HealthCheckRegistry.class);
        AdminEnvironment admin = mock(AdminEnvironment.class);
        ServletEnvironment servlets = mock(ServletEnvironment.class);
        ServletRegistration.Dynamic dynamic = mock(ServletRegistration.Dynamic.class);
        MetricRegistry metricRegistry = new MetricRegistry();

        when(environment.jersey()).thenReturn(jersey);
        when(environment.lifecycle()).thenReturn(lifecycle);
        when(environment.healthChecks()).thenReturn(healthChecks);
        when(environment.servlets()).thenReturn(servlets);
        when(environment.admin()).thenReturn(admin);
        when(environment.metrics()).thenReturn(metricRegistry);
        when(environment.getApplicationContext()).thenReturn(mock(MutableServletContextHandler.class));

        when(admin.addServlet(anyString(), any(Servlet.class))).thenReturn(dynamic);
        when(dynamic.addMapping(anyString())).thenReturn(null);

        when(configuration.getSessionCookie()).thenReturn("MY_SESSION");
        when(configuration.getFilterPriorities()).thenReturn(null);
        when(configuration.getUrlPattern()).thenReturn("/api/*");
        when(configuration.getApplicationName()).thenReturn("test-app");
        when(configuration.getExceptionMapperConfiguration()).thenReturn(new ExceptionMapperConfiguration());

        server.run(configuration, environment);

        verify(jersey).setUrlPattern("/api/*");
    }

    @Test
    void testInitialisePrometheusMetrics()
    {
        ServerConfiguration configuration = mock(ServerConfiguration.class);
        Environment environment = mock(Environment.class);
        JerseyEnvironment jersey = mock(JerseyEnvironment.class);
        LifecycleEnvironment lifecycle = mock(LifecycleEnvironment.class);
        HealthCheckRegistry healthChecks = mock(HealthCheckRegistry.class);
        AdminEnvironment admin = mock(AdminEnvironment.class);
        ServletEnvironment servlets = mock(ServletEnvironment.class);
        ServletRegistration.Dynamic dynamic = mock(ServletRegistration.Dynamic.class);
        MetricRegistry metricRegistry = new MetricRegistry();

        when(environment.jersey()).thenReturn(jersey);
        when(environment.lifecycle()).thenReturn(lifecycle);
        when(environment.healthChecks()).thenReturn(healthChecks);
        when(environment.servlets()).thenReturn(servlets);
        when(environment.admin()).thenReturn(admin);
        when(environment.metrics()).thenReturn(metricRegistry);
        when(environment.getApplicationContext()).thenReturn(mock(MutableServletContextHandler.class));

        when(admin.addServlet(anyString(), any(Servlet.class))).thenReturn(dynamic);
        when(dynamic.addMapping(anyString())).thenReturn(null);

        when(configuration.getSessionCookie()).thenReturn(null);
        when(configuration.getFilterPriorities()).thenReturn(null);
        when(configuration.getUrlPattern()).thenReturn(null);
        when(configuration.getApplicationName()).thenReturn("test-app");
        when(configuration.getExceptionMapperConfiguration()).thenReturn(new ExceptionMapperConfiguration());

        server.run(configuration, environment);

        verify(admin).addServlet(eq("prometheus"), any(Servlet.class));
        verify(dynamic).addMapping("/prometheus");
    }

    private LifeCycle.Listener captureLifeCycleListener()
    {
        ServerConfiguration configuration = mock(ServerConfiguration.class);
        Environment environment = mock(Environment.class);
        JerseyEnvironment jersey = mock(JerseyEnvironment.class);
        LifecycleEnvironment lifecycle = mock(LifecycleEnvironment.class);
        HealthCheckRegistry healthChecks = mock(HealthCheckRegistry.class);
        AdminEnvironment admin = mock(AdminEnvironment.class);
        ServletEnvironment servlets = mock(ServletEnvironment.class);
        ServletRegistration.Dynamic dynamic = mock(ServletRegistration.Dynamic.class);
        MetricRegistry metricRegistry = new MetricRegistry();

        when(environment.jersey()).thenReturn(jersey);
        when(environment.lifecycle()).thenReturn(lifecycle);
        when(environment.healthChecks()).thenReturn(healthChecks);
        when(environment.servlets()).thenReturn(servlets);
        when(environment.admin()).thenReturn(admin);
        when(environment.metrics()).thenReturn(metricRegistry);
        when(environment.getApplicationContext()).thenReturn(mock(MutableServletContextHandler.class));

        when(admin.addServlet(anyString(), any(Servlet.class))).thenReturn(dynamic);
        when(dynamic.addMapping(anyString())).thenReturn(null);

        when(configuration.getSessionCookie()).thenReturn(null);
        when(configuration.getFilterPriorities()).thenReturn(null);
        when(configuration.getUrlPattern()).thenReturn(null);
        when(configuration.getApplicationName()).thenReturn("test-app");
        when(configuration.getExceptionMapperConfiguration()).thenReturn(new ExceptionMapperConfiguration());

        server.run(configuration, environment);

        ArgumentCaptor<LifeCycle.Listener> captor = ArgumentCaptor.forClass(LifeCycle.Listener.class);
        verify(lifecycle).addLifeCycleListener(captor.capture());
        return captor.getValue();
    }

    @Test
    void testLifeCycleListenerStarting()
    {
        LifeCycle.Listener listener = captureLifeCycleListener();
        LifeCycle event = mock(LifeCycle.class);

        assertDoesNotThrow(() -> listener.lifeCycleStarting(event));
    }

    @Test
    void testLifeCycleListenerStarted()
    {
        LifeCycle.Listener listener = captureLifeCycleListener();
        LifeCycle event = mock(LifeCycle.class);

        assertDoesNotThrow(() -> listener.lifeCycleStarted(event));
    }

    @Test
    void testLifeCycleListenerFailure()
    {
        LifeCycle.Listener listener = captureLifeCycleListener();
        LifeCycle event = mock(LifeCycle.class);
        Throwable cause = new RuntimeException("test failure");

        assertDoesNotThrow(() -> listener.lifeCycleFailure(event, cause));
    }

    @Test
    void testLifeCycleListenerStopping()
    {
        LifeCycle.Listener listener = captureLifeCycleListener();
        LifeCycle event = mock(LifeCycle.class);

        assertDoesNotThrow(() -> listener.lifeCycleStopping(event));
    }

    @Test
    void testLifeCycleListenerStopped()
    {
        LifeCycle.Listener listener = captureLifeCycleListener();
        LifeCycle event = mock(LifeCycle.class);

        assertDoesNotThrow(() -> listener.lifeCycleStopped(event));
    }

    @Test
    void testInitializeAddsBundlesAndConfiguresBootstrap()
    {
        Bootstrap<ServerConfiguration> bootstrap = new Bootstrap<>(server);

        assertDoesNotThrow(() -> server.initialize(bootstrap));

        assertNotNull(bootstrap.getConfigurationSourceProvider());
        assertNotNull(bootstrap.getObjectMapper());
    }
}
