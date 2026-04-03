// Copyright 2024 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.depot.core.server;

import org.finos.legend.depot.core.server.error.configuration.ExceptionMapperConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerConfigurationTest
{
    private static final Unsafe UNSAFE;

    static
    {
        try
        {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private ServerConfiguration newConfig()
    {
        try
        {
            return (ServerConfiguration) UNSAFE.allocateInstance(ServerConfiguration.class);
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetDeploymentReturnsNull()
    {
        ServerConfiguration config = newConfig();
        Assertions.assertNull(config.getDeployment());
    }

    @Test
    public void testGetDeploymentReturnsSetValue() throws Exception
    {
        ServerConfiguration config = newConfig();
        Field f = ServerConfiguration.class.getDeclaredField("deployment");
        f.setAccessible(true);
        f.set(config, "prod");
        Assertions.assertEquals("prod", config.getDeployment());
    }

    @Test
    public void testGetStorageConfigurationReturnsNull()
    {
        ServerConfiguration config = newConfig();
        Assertions.assertNull(config.getStorageConfiguration());
    }

    @Test
    public void testSetStorageAndGetStorageConfiguration()
    {
        ServerConfiguration config = newConfig();
        config.setStorage(Collections.emptyList());
        Assertions.assertNotNull(config.getStorageConfiguration());
    }

    @Test
    public void testGetProjectsConfigurationReturnsNull()
    {
        ServerConfiguration config = newConfig();
        Assertions.assertNull(config.getProjectsConfiguration());
    }

    @Test
    public void testGetOpenTracingConfigurationReturnsNull()
    {
        ServerConfiguration config = newConfig();
        Assertions.assertNull(config.getOpenTracingConfiguration());
    }

    @Test
    public void testSetAndGetOpenTracingConfiguration()
    {
        ServerConfiguration config = newConfig();
        config.setOpenTracingConfiguration(null);
        Assertions.assertNull(config.getOpenTracingConfiguration());
    }

    @Test
    public void testGetExceptionMapperConfigurationReturnsDefault()
    {
        ServerConfiguration config = newConfig();
        ExceptionMapperConfiguration result = config.getExceptionMapperConfiguration();
        Assertions.assertNotNull(result);
    }

    @Test
    public void testSetAndGetExceptionMapperConfiguration()
    {
        ServerConfiguration config = newConfig();
        ExceptionMapperConfiguration exConfig = new ExceptionMapperConfiguration();
        exConfig.setIncludeStackTrace(true);
        config.setExceptionMapperConfiguration(exConfig);
        Assertions.assertEquals(exConfig, config.getExceptionMapperConfiguration());
        Assertions.assertTrue(config.getExceptionMapperConfiguration().includeStackTrace());
    }

    @Test
    public void testGetSessionCookieReturnsNull()
    {
        ServerConfiguration config = newConfig();
        Assertions.assertNull(config.getSessionCookie());
    }

    @Test
    public void testGetSessionCookieReturnsSetValue() throws Exception
    {
        ServerConfiguration config = newConfig();
        Field f = ServerConfiguration.class.getDeclaredField("sessionCookie");
        f.setAccessible(true);
        f.set(config, "MYSESSION");
        Assertions.assertEquals("MYSESSION", config.getSessionCookie());
    }

    @Test
    public void testGetApplicationNameReturnsNull()
    {
        ServerConfiguration config = newConfig();
        Assertions.assertNull(config.getApplicationName());
    }

    @Test
    public void testGetApplicationNameReturnsSetValue() throws Exception
    {
        ServerConfiguration config = newConfig();
        Field f = ServerConfiguration.class.getDeclaredField("applicationName");
        f.setAccessible(true);
        f.set(config, "MyApp");
        Assertions.assertEquals("MyApp", config.getApplicationName());
    }

    @Test
    public void testGetSwaggerBundleConfigurationReturnsNull()
    {
        ServerConfiguration config = newConfig();
        Assertions.assertNull(config.getSwaggerBundleConfiguration());
    }

    @Test
    public void testGetPac4jConfigurationReturnsNull()
    {
        ServerConfiguration config = newConfig();
        Assertions.assertNull(config.getPac4jConfiguration());
    }

    @Test
    public void testGetFilterPrioritiesReturnsNull()
    {
        ServerConfiguration config = newConfig();
        Assertions.assertNull(config.getFilterPriorities());
    }

    @Test
    public void testGetFilterPrioritiesReturnsSetValue() throws Exception
    {
        ServerConfiguration config = newConfig();
        Map<String, Integer> priorities = new HashMap<>();
        priorities.put("filter1", 1);
        Field f = ServerConfiguration.class.getDeclaredField("filterPriorities");
        f.setAccessible(true);
        f.set(config, priorities);
        Assertions.assertEquals(priorities, config.getFilterPriorities());
    }

    @Test
    public void testGetPrometheusConfigurationReturnsNull()
    {
        ServerConfiguration config = newConfig();
        Assertions.assertNull(config.getPrometheusConfiguration());
    }

    @Test
    public void testSetAndGetPrometheusConfiguration()
    {
        ServerConfiguration config = newConfig();
        config.setPrometheusConfiguration(null);
        Assertions.assertNull(config.getPrometheusConfiguration());
    }

    @Test
    public void testGetUrlPatternReturnsNull()
    {
        ServerConfiguration config = newConfig();
        Assertions.assertNull(config.getUrlPattern());
    }

    @Test
    public void testGetUrlPatternReturnsSetValue() throws Exception
    {
        ServerConfiguration config = newConfig();
        Field f = ServerConfiguration.class.getDeclaredField("urlPattern");
        f.setAccessible(true);
        f.set(config, "/api/*");
        Assertions.assertEquals("/api/*", config.getUrlPattern());
    }
}
