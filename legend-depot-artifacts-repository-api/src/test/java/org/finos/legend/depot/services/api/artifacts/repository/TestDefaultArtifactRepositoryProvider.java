package org.finos.legend.depot.services.api.artifacts.repository;

import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestDefaultArtifactRepositoryProvider
{
    private final DefaultArtifactRepositoryProvider provider = new DefaultArtifactRepositoryProvider() {};

    @Test
    void testAreValidCoordinatesThrowsUnsupportedOperationException()
    {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> provider.areValidCoordinates("org.example", "artifact"));
        assertEquals("method not supported", exception.getMessage());
    }

    @Test
    void testGetPOMThrowsUnsupportedOperationException()
    {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> provider.getPOM("org.example", "artifact", "1.0.0"));
        assertEquals("method not supported", exception.getMessage());
    }

    @Test
    void testGetJarFileThrowsUnsupportedOperationException()
    {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> provider.getJarFile("org.example", "artifact", "1.0.0"));
        assertEquals("method not supported", exception.getMessage());
    }

    @Test
    void testGetModulesFromPOMThrowsUnsupportedOperationException()
    {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> provider.getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "artifact", "1.0.0"));
        assertEquals("method not supported", exception.getMessage());
    }

    @Test
    void testFindVersionsThrowsUnsupportedOperationException()
    {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> provider.findVersions("org.example", "artifact"));
        assertEquals("method not supported", exception.getMessage());
    }

    @Test
    void testFindVersionThrowsUnsupportedOperationException()
    {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> provider.findVersion("org.example", "artifact", "1.0.0"));
        assertEquals("method not supported", exception.getMessage());
    }

    @Test
    void testFindFilesThrowsUnsupportedOperationException()
    {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> provider.findFiles(ArtifactType.ENTITIES, "org.example", "artifact", "1.0.0"));
        assertEquals("method not supported", exception.getMessage());
    }

    @Test
    void testFindDependenciesFilesThrowsUnsupportedOperationException()
    {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> provider.findDependenciesFiles(ArtifactType.ENTITIES, "org.example", "artifact", "1.0.0"));
        assertEquals("method not supported", exception.getMessage());
    }

    @Test
    void testFindDependenciesByArtifactTypeThrowsUnsupportedOperationException()
    {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> provider.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.example", "artifact", "1.0.0"));
        assertEquals("method not supported", exception.getMessage());
    }

    @Test
    void testFindDependenciesThrowsUnsupportedOperationException()
    {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> provider.findDependencies("org.example", "artifact", "1.0.0"));
        assertEquals("method not supported", exception.getMessage());
    }
}
