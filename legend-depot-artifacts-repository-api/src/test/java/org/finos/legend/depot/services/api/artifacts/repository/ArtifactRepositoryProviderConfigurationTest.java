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

package org.finos.legend.depot.services.api.artifacts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ArtifactRepositoryProviderConfigurationTest
{
    @Test
    public void testConstructorAndGetName()
    {
        TestArtifactRepositoryConfiguration config = new TestArtifactRepositoryConfiguration("test-config");

        assertNotNull(config);
        assertEquals("test-config", config.getName());
    }

    @Test
    public void testVoidConfiguration()
    {
        ArtifactRepositoryProviderConfiguration config = ArtifactRepositoryProviderConfiguration.voidConfiguration();

        assertNotNull(config);
        assertEquals("void configuration", config.getName());
        assertNull(config.initialiseArtifactRepositoryProvider());
    }

    @Test
    public void testConfigureObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper configuredMapper = ArtifactRepositoryProviderConfiguration.configureObjectMapper(objectMapper);

        assertNotNull(configuredMapper);
        assertEquals(objectMapper, configuredMapper);
    }

    @Test
    public void testInitialiseArtifactRepositoryProvider()
    {
        TestArtifactRepositoryConfiguration config = new TestArtifactRepositoryConfiguration("test");
        ArtifactRepository repository = config.initialiseArtifactRepositoryProvider();

        assertNull(repository);
    }

    private static class TestArtifactRepositoryConfiguration extends ArtifactRepositoryProviderConfiguration
    {
        protected TestArtifactRepositoryConfiguration(String name)
        {
            super(name);
        }

        @Override
        public ArtifactRepository initialiseArtifactRepositoryProvider()
        {
            return null;
        }
    }
}
