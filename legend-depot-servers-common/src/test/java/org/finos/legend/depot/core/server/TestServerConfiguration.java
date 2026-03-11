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

import org.finos.legend.depot.core.server.error.configuration.ExceptionMapperConfiguration;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.store.StorageConfiguration;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestServerConfiguration
{
    @Test
    void testGettersReturnNullByDefault()
    {
        ServerConfiguration config = new ServerConfiguration();

        assertNull(config.getDeployment());
        assertNull(config.getStorageConfiguration());
        assertNull(config.getProjectsConfiguration());
        assertNull(config.getOpenTracingConfiguration());
        assertNull(config.getSessionCookie());
        assertNull(config.getApplicationName());
        assertNull(config.getSwaggerBundleConfiguration());
        assertNull(config.getPac4jConfiguration());
        assertNull(config.getFilterPriorities());
        assertNull(config.getPrometheusConfiguration());
        assertNull(config.getUrlPattern());
    }

    @Test
    void testGetExceptionMapperConfigurationReturnsDefaultWhenNull()
    {
        ServerConfiguration config = new ServerConfiguration();

        ExceptionMapperConfiguration result = config.getExceptionMapperConfiguration();

        assertNotNull(result);
    }

    @Test
    void testSetAndGetStorage()
    {
        ServerConfiguration config = new ServerConfiguration();
        List<StorageConfiguration> storages = Collections.emptyList();

        config.setStorage(storages);

        assertNotNull(config.getStorageConfiguration());
        assertTrue(config.getStorageConfiguration().isEmpty());
    }

    @Test
    void testSetAndGetOpenTracingConfiguration()
    {
        ServerConfiguration config = new ServerConfiguration();
        OpenTracingConfiguration tracing = new OpenTracingConfiguration();

        config.setOpenTracingConfiguration(tracing);

        assertEquals(tracing, config.getOpenTracingConfiguration());
    }

    @Test
    void testSetAndGetExceptionMapperConfiguration()
    {
        ServerConfiguration config = new ServerConfiguration();
        ExceptionMapperConfiguration exConfig = new ExceptionMapperConfiguration();

        config.setExceptionMapperConfiguration(exConfig);

        assertEquals(exConfig, config.getExceptionMapperConfiguration());
    }

    @Test
    void testSetAndGetPrometheusConfiguration()
    {
        ServerConfiguration config = new ServerConfiguration();
        PrometheusConfiguration promConfig = new PrometheusConfiguration();

        config.setPrometheusConfiguration(promConfig);

        assertEquals(promConfig, config.getPrometheusConfiguration());
    }
}
