// Copyright 2021 Goldman Sachs
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

package org.finos.legend.depot.services.api.artifacts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArtifactRepositoryProviderConfigurationTest
{
    @Test
    public void testConstructorSetsName()
    {
        ArtifactRepositoryProviderConfiguration config = new VoidArtifactRepositoryConfiguration();
        Assertions.assertEquals("void configuration", config.getName());
    }

    @Test
    public void testGetNameReturnsName()
    {
        ArtifactRepositoryProviderConfiguration config = new VoidArtifactRepositoryConfiguration();
        Assertions.assertNotNull(config.getName());
        Assertions.assertEquals("void configuration", config.getName());
    }

    @Test
    public void testVoidConfigurationReturnsInstance()
    {
        ArtifactRepositoryProviderConfiguration config = ArtifactRepositoryProviderConfiguration.voidConfiguration();
        Assertions.assertNotNull(config);
        Assertions.assertTrue(config instanceof VoidArtifactRepositoryConfiguration);
    }

    @Test
    public void testVoidConfigurationName()
    {
        ArtifactRepositoryProviderConfiguration config = ArtifactRepositoryProviderConfiguration.voidConfiguration();
        Assertions.assertEquals("void configuration", config.getName());
    }

    @Test
    public void testConfigureObjectMapperReturnsObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper result = ArtifactRepositoryProviderConfiguration.configureObjectMapper(objectMapper);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testConfigureObjectMapperAddsMixin()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper result = ArtifactRepositoryProviderConfiguration.configureObjectMapper(objectMapper);
        Assertions.assertNotNull(result.findMixInClassFor(ArtifactRepositoryProviderConfiguration.class));
    }

    @Test
    public void testInitialiseArtifactRepositoryProviderReturnsNull()
    {
        ArtifactRepositoryProviderConfiguration config = ArtifactRepositoryProviderConfiguration.voidConfiguration();
        ArtifactRepository repository = config.initialiseArtifactRepositoryProvider();
        Assertions.assertNull(repository);
    }
}
