package org.finos.legend.depot.services.api.artifacts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestArtifactRepositoryProviderConfiguration
{
    @Test
    void canCreateVoidConfiguration()
    {
        ArtifactRepositoryProviderConfiguration config = ArtifactRepositoryProviderConfiguration.voidConfiguration();

        assertNotNull(config);
        assertEquals("void configuration", config.getName());
    }

    @Test
    void canGetName()
    {
        ArtifactRepositoryProviderConfiguration config = new VoidArtifactRepositoryConfiguration();

        assertEquals("void configuration", config.getName());
    }

    @Test
    void canConfigureObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectMapper result = ArtifactRepositoryProviderConfiguration.configureObjectMapper(objectMapper);

        assertNotNull(result);
        assertEquals(objectMapper, result);
    }

    @Test
    void testVoidConfigurationInitialisesNull()
    {
        ArtifactRepositoryProviderConfiguration config = ArtifactRepositoryProviderConfiguration.voidConfiguration();

        assertNull(config.initialiseArtifactRepositoryProvider());
    }
}
