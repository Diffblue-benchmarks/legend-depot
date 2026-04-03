//  Copyright 2024 Goldman Sachs
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

package org.finos.legend.depot.core.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import org.finos.legend.depot.core.server.ServerConfiguration;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.store.StorageConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TestBaseServerModule
{
    private static class TestServerConfiguration extends ServerConfiguration
    {
        private final String appName;
        private final List<StorageConfiguration> storages;
        private final ProjectsConfiguration projectsConfig;
        private final OpenTracingConfiguration tracingConfig;
        private final PrometheusConfiguration prometheusConfig;

        TestServerConfiguration(String appName, List<StorageConfiguration> storages,
                                 ProjectsConfiguration projectsConfig,
                                 OpenTracingConfiguration tracingConfig,
                                 PrometheusConfiguration prometheusConfig)
        {
            this.appName = appName;
            this.storages = storages;
            this.projectsConfig = projectsConfig;
            this.tracingConfig = tracingConfig;
            this.prometheusConfig = prometheusConfig;
        }

        @Override
        public String getApplicationName()
        {
            return appName;
        }

        @Override
        public List<StorageConfiguration> getStorageConfiguration()
        {
            return storages;
        }

        @Override
        public ProjectsConfiguration getProjectsConfiguration()
        {
            return projectsConfig;
        }

        @Override
        public OpenTracingConfiguration getOpenTracingConfiguration()
        {
            return tracingConfig;
        }

        @Override
        public PrometheusConfiguration getPrometheusConfiguration()
        {
            return prometheusConfig;
        }
    }

    private static class TestServerModule extends BaseServerModule<TestServerConfiguration>
    {
    }

    private TestServerModule createModule(TestServerConfiguration config)
    {
        TestServerModule module = new TestServerModule();
        module.setConfiguration(config);
        return module;
    }

    private Injector createInjector(TestServerConfiguration config)
    {
        TestServerModule module = new TestServerModule()
        {
            @Override
            public void configure(com.google.inject.Binder binder)
            {
                super.configure(binder);
                binder.bind(TestServerConfiguration.class).toInstance(getConfiguration());
            }
        };
        module.setConfiguration(config);
        return Guice.createInjector(new ServletModule(), module);
    }

    private static HttpServletRequest createStubRequest(Principal principal)
    {
        return new HttpServletRequest()
        {
            @Override public Principal getUserPrincipal() { return principal; }
            @Override public String getAuthType() { return null; }
            @Override public Cookie[] getCookies() { return null; }
            @Override public long getDateHeader(String name) { return 0; }
            @Override public String getHeader(String name) { return null; }
            @Override public Enumeration<String> getHeaders(String name) { return null; }
            @Override public Enumeration<String> getHeaderNames() { return null; }
            @Override public int getIntHeader(String name) { return 0; }
            @Override public String getMethod() { return null; }
            @Override public String getPathInfo() { return null; }
            @Override public String getPathTranslated() { return null; }
            @Override public String getContextPath() { return null; }
            @Override public String getQueryString() { return null; }
            @Override public String getRemoteUser() { return null; }
            @Override public boolean isUserInRole(String role) { return false; }
            @Override public String getRequestedSessionId() { return null; }
            @Override public String getRequestURI() { return null; }
            @Override public StringBuffer getRequestURL() { return null; }
            @Override public String getServletPath() { return null; }
            @Override public HttpSession getSession(boolean create) { return null; }
            @Override public HttpSession getSession() { return null; }
            @Override public String changeSessionId() { return null; }
            @Override public boolean isRequestedSessionIdValid() { return false; }
            @Override public boolean isRequestedSessionIdFromCookie() { return false; }
            @Override public boolean isRequestedSessionIdFromURL() { return false; }
            @Override public boolean isRequestedSessionIdFromUrl() { return false; }
            @Override public boolean authenticate(HttpServletResponse response) { return false; }
            @Override public void login(String username, String password) {}
            @Override public void logout() {}
            @Override public Collection<Part> getParts() { return null; }
            @Override public Part getPart(String name) { return null; }
            @Override public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) { return null; }
            @Override public Object getAttribute(String name) { return null; }
            @Override public Enumeration<String> getAttributeNames() { return null; }
            @Override public String getCharacterEncoding() { return null; }
            @Override public void setCharacterEncoding(String env) {}
            @Override public int getContentLength() { return 0; }
            @Override public long getContentLengthLong() { return 0; }
            @Override public String getContentType() { return null; }
            @Override public ServletInputStream getInputStream() { return null; }
            @Override public String getParameter(String name) { return null; }
            @Override public Enumeration<String> getParameterNames() { return null; }
            @Override public String[] getParameterValues(String name) { return null; }
            @Override public Map<String, String[]> getParameterMap() { return null; }
            @Override public String getProtocol() { return null; }
            @Override public String getScheme() { return null; }
            @Override public String getServerName() { return null; }
            @Override public int getServerPort() { return 0; }
            @Override public BufferedReader getReader() { return null; }
            @Override public String getRemoteAddr() { return null; }
            @Override public String getRemoteHost() { return null; }
            @Override public void setAttribute(String name, Object o) {}
            @Override public void removeAttribute(String name) {}
            @Override public Locale getLocale() { return null; }
            @Override public Enumeration<Locale> getLocales() { return null; }
            @Override public boolean isSecure() { return false; }
            @Override public RequestDispatcher getRequestDispatcher(String path) { return null; }
            @Override public String getRealPath(String path) { return null; }
            @Override public int getRemotePort() { return 0; }
            @Override public String getLocalName() { return null; }
            @Override public String getLocalAddr() { return null; }
            @Override public int getLocalPort() { return 0; }
            @Override public ServletContext getServletContext() { return null; }
            @Override public AsyncContext startAsync() { return null; }
            @Override public AsyncContext startAsync(ServletRequest req, ServletResponse res) { return null; }
            @Override public boolean isAsyncStarted() { return false; }
            @Override public boolean isAsyncSupported() { return false; }
            @Override public AsyncContext getAsyncContext() { return null; }
            @Override public DispatcherType getDispatcherType() { return null; }
        };
    }

    @Test
    public void testProvideUser_returnsUserPrincipal()
    {
        TestServerModule module = createModule(new TestServerConfiguration("app", Collections.emptyList(), null, null, null));
        Principal expected = () -> "testUser";
        HttpServletRequest req = createStubRequest(expected);

        Principal result = module.provideUser(req);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testProvideUser_returnsNullWhenNoPrincipal()
    {
        TestServerModule module = createModule(new TestServerConfiguration("app", Collections.emptyList(), null, null, null));
        HttpServletRequest req = createStubRequest(null);

        Principal result = module.provideUser(req);

        Assertions.assertNull(result);
    }

    @Test
    public void testGetApplicationName_returnsConfigurationApplicationName()
    {
        TestServerConfiguration config = new TestServerConfiguration("my-app", Collections.emptyList(), null, null, null);
        TestServerModule module = createModule(config);

        String result = module.getApplicationName(config);

        Assertions.assertEquals("my-app", result);
    }

    @Test
    public void testGetConfig_returnsConfiguration()
    {
        TestServerConfiguration config = new TestServerConfiguration("app", Collections.emptyList(), null, null, null);
        TestServerModule module = createModule(config);

        ServerConfiguration result = module.getConfig(config);

        Assertions.assertEquals(config, result);
    }

    @Test
    public void testGetProjectsConfig_defaultsToMasterBranchWhenNull() throws Exception
    {
        TestServerConfiguration config = new TestServerConfiguration("app", Collections.emptyList(), null, null, null);
        TestServerModule module = createModule(config);

        Method method = BaseServerModule.class.getDeclaredMethod("getProjectsConfig");
        method.setAccessible(true);
        ProjectsConfiguration result = (ProjectsConfiguration) method.invoke(module);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("master", result.getDefaultBranch());
    }

    @Test
    public void testGetProjectsConfig_returnsConfiguredProjectsConfiguration() throws Exception
    {
        ProjectsConfiguration projectsConfig = new ProjectsConfiguration("develop");
        TestServerConfiguration config = new TestServerConfiguration("app", Collections.emptyList(), projectsConfig, null, null);
        TestServerModule module = createModule(config);

        Method method = BaseServerModule.class.getDeclaredMethod("getProjectsConfig");
        method.setAccessible(true);
        ProjectsConfiguration result = (ProjectsConfiguration) method.invoke(module);

        Assertions.assertEquals("develop", result.getDefaultBranch());
    }

    @Test
    public void testGetStorageConfig_returnsStorageList() throws Exception
    {
        List<StorageConfiguration> storages = Collections.emptyList();
        TestServerConfiguration config = new TestServerConfiguration("app", storages, null, null, null);
        TestServerModule module = createModule(config);

        Method method = BaseServerModule.class.getDeclaredMethod("getStorageConfig");
        method.setAccessible(true);
        List<StorageConfiguration> result = (List<StorageConfiguration>) method.invoke(module);

        Assertions.assertEquals(storages, result);
    }

    @Test
    public void testGetTracingConfig_returnsDefaultWhenNull() throws Exception
    {
        TestServerConfiguration config = new TestServerConfiguration("app", Collections.emptyList(), null, null, null);
        TestServerModule module = createModule(config);

        Method method = BaseServerModule.class.getDeclaredMethod("getTracingConfig");
        method.setAccessible(true);
        OpenTracingConfiguration result = (OpenTracingConfiguration) method.invoke(module);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testGetTracingConfig_returnsConfiguredTracingConfiguration() throws Exception
    {
        OpenTracingConfiguration tracingConfig = new OpenTracingConfiguration();
        tracingConfig.setEnabled(true);
        TestServerConfiguration config = new TestServerConfiguration("app", Collections.emptyList(), null, tracingConfig, null);
        TestServerModule module = createModule(config);

        Method method = BaseServerModule.class.getDeclaredMethod("getTracingConfig");
        method.setAccessible(true);
        OpenTracingConfiguration result = (OpenTracingConfiguration) method.invoke(module);

        Assertions.assertTrue(result.isEnabled());
    }

    @Test
    public void testGetPrometheusConfig_returnsDefaultWhenNull() throws Exception
    {
        TestServerConfiguration config = new TestServerConfiguration("app", Collections.emptyList(), null, null, null);
        TestServerModule module = createModule(config);

        Method method = BaseServerModule.class.getDeclaredMethod("getPrometheusConfig");
        method.setAccessible(true);
        PrometheusConfiguration result = (PrometheusConfiguration) method.invoke(module);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testGetPrometheusConfig_returnsConfiguredPrometheusConfiguration() throws Exception
    {
        PrometheusConfiguration prometheusConfig = new PrometheusConfiguration();
        prometheusConfig.setEnabled(true);
        TestServerConfiguration config = new TestServerConfiguration("app", Collections.emptyList(), null, null, prometheusConfig);
        TestServerModule module = createModule(config);

        Method method = BaseServerModule.class.getDeclaredMethod("getPrometheusConfig");
        method.setAccessible(true);
        PrometheusConfiguration result = (PrometheusConfiguration) method.invoke(module);

        Assertions.assertTrue(result.isEnabled());
    }

    @Test
    public void testConfigure_bindsAllExpectedTypes()
    {
        ProjectsConfiguration projectsConfig = new ProjectsConfiguration("main");
        OpenTracingConfiguration tracingConfig = new OpenTracingConfiguration();
        PrometheusConfiguration prometheusConfig = new PrometheusConfiguration();
        TestServerConfiguration config = new TestServerConfiguration("app", Collections.emptyList(), projectsConfig, tracingConfig, prometheusConfig);

        Injector injector = createInjector(config);

        ProjectsConfiguration boundProjects = injector.getInstance(ProjectsConfiguration.class);
        Assertions.assertEquals("main", boundProjects.getDefaultBranch());

        OpenTracingConfiguration boundTracing = injector.getInstance(OpenTracingConfiguration.class);
        Assertions.assertNotNull(boundTracing);

        PrometheusConfiguration boundPrometheus = injector.getInstance(PrometheusConfiguration.class);
        Assertions.assertNotNull(boundPrometheus);

        List<StorageConfiguration> boundStorages = injector.getInstance(Key.get(new TypeLiteral<List<StorageConfiguration>>() {}));
        Assertions.assertNotNull(boundStorages);

        String appName = injector.getInstance(Key.get(String.class, Names.named("applicationName")));
        Assertions.assertEquals("app", appName);
    }
}
