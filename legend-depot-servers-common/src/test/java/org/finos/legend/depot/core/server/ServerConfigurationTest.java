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

import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ServerConfigurationTest
{
    @Test
    public void canGetPrometheusConfiguration() throws Exception
    {
        ServerConfiguration config = createServerConfigurationInstance();
        PrometheusConfiguration prometheusConfig = new PrometheusConfiguration();

        Field field = ServerConfiguration.class.getDeclaredField("prometheusConfiguration");
        field.setAccessible(true);
        field.set(config, prometheusConfig);

        assertEquals(prometheusConfig, config.getPrometheusConfiguration());
    }

    @Test
    public void canGetNullPrometheusConfiguration() throws Exception
    {
        ServerConfiguration config = createServerConfigurationInstance();

        assertNull(config.getPrometheusConfiguration());
    }

    private ServerConfiguration createServerConfigurationInstance() throws Exception
    {
        Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
        Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Object unsafe = unsafeField.get(null);
        java.lang.reflect.Method allocateInstance = unsafeClass.getMethod("allocateInstance", Class.class);
        return (ServerConfiguration) allocateInstance.invoke(unsafe, ServerConfiguration.class);
    }
}
