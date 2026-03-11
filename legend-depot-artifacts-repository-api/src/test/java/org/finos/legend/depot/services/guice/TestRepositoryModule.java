package org.finos.legend.depot.services.guice;

import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryProviderConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestRepositoryModule
{
    @Test
    void canGetArtifactRepositoryWhenProviderReturnsNonNull()
    {
        RepositoryModule module = new RepositoryModule();
        ArtifactRepository mockRepo = mock(ArtifactRepository.class);
        ArtifactRepositoryProviderConfiguration configuration = mock(ArtifactRepositoryProviderConfiguration.class);
        when(configuration.initialiseArtifactRepositoryProvider()).thenReturn(mockRepo);
        when(configuration.getName()).thenReturn("test-provider");

        ArtifactRepository result = module.getArtifactRepository(configuration);

        assertNotNull(result);
        assertSame(mockRepo, result);
    }

    @Test
    void canGetVoidRepositoryWhenProviderReturnsNull()
    {
        RepositoryModule module = new RepositoryModule();
        ArtifactRepositoryProviderConfiguration configuration = mock(ArtifactRepositoryProviderConfiguration.class);
        when(configuration.initialiseArtifactRepositoryProvider()).thenReturn(null);

        ArtifactRepository result = module.getArtifactRepository(configuration);

        assertNotNull(result);
        assertTrue(result instanceof VoidArtifactRepositoryProvider);
    }

    @Test
    void canGetVoidRepositoryWhenConfigurationIsNull()
    {
        RepositoryModule module = new RepositoryModule();

        ArtifactRepository result = module.getArtifactRepository(null);

        assertNotNull(result);
        assertTrue(result instanceof VoidArtifactRepositoryProvider);
    }
}
