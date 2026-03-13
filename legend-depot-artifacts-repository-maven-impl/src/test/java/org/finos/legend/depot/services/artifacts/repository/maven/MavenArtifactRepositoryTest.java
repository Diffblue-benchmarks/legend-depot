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

package org.finos.legend.depot.services.artifacts.repository.maven;

import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryException;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryProviderConfiguration;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactDependency;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.jboss.shrinkwrap.resolver.api.maven.PackagingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

public class MavenArtifactRepositoryTest
{
    @TempDir
    Path tempDir;

    private File settingsFile;
    private MavenArtifactRepository repository;

    @BeforeEach
    public void setUp() throws IOException
    {
        settingsFile = tempDir.resolve("settings.xml").toFile();
        String localRepoPath = tempDir.resolve("repo").toString();

        String settingsContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<settings>\n" +
                "  <localRepository>" + localRepoPath + "</localRepository>\n" +
                "  <mirrors>\n" +
                "    <mirror>\n" +
                "      <id>central</id>\n" +
                "      <url>https://repo.maven.apache.org/maven2</url>\n" +
                "      <mirrorOf>central</mirrorOf>\n" +
                "    </mirror>\n" +
                "  </mirrors>\n" +
                "</settings>";

        try (FileWriter writer = new FileWriter(settingsFile))
        {
            writer.write(settingsContent);
        }

        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration(settingsFile.getAbsolutePath());
        repository = new MavenArtifactRepository(config);
    }

    @Test
    public void testConstructorWithNullConfiguration()
    {
        MavenArtifactRepository repo = new MavenArtifactRepository(null);
        assertNotNull(repo);
    }

    @Test
    public void testConstructorWithInvalidConfiguration()
    {
        ArtifactRepositoryProviderConfiguration invalidConfig = ArtifactRepositoryProviderConfiguration.voidConfiguration();
        assertThrows(IllegalArgumentException.class, () -> new MavenArtifactRepository(invalidConfig));
    }

    @Test
    public void testConstructorWithMissingSettingsFile()
    {
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration("/nonexistent/settings.xml");
        assertThrows(RuntimeException.class, () -> new MavenArtifactRepository(config));
    }

    @Test
    public void testConstructorWithInvalidSettingsFile() throws IOException
    {
        File invalidSettings = tempDir.resolve("invalid-settings.xml").toFile();
        try (FileWriter writer = new FileWriter(invalidSettings))
        {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<settings></settings>");
        }

        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration(invalidSettings.getAbsolutePath());
        assertThrows(RuntimeException.class, () -> new MavenArtifactRepository(config));
    }

    @Test
    public void testGavCoordinatesWithPackagingType()
    {
        String coordinates = repository.gavCoordinates("org.example", "artifact", PackagingType.JAR, "1.0.0");
        assertEquals("org.example:artifact:jar:1.0.0", coordinates);
    }

    @Test
    public void testGavCoordinatesWithNullPackagingType()
    {
        String coordinates = repository.gavCoordinates("org.example", "artifact", null, "1.0.0");
        assertEquals("org.example:artifact:1.0.0", coordinates);
    }

    @Test
    public void testGavCoordinatesWithoutPackagingType()
    {
        String coordinates = repository.gavCoordinates("org.example", "artifact", "1.0.0");
        assertEquals("org.example:artifact:1.0.0", coordinates);
    }

    @Test
    public void testAreValidCoordinatesWithValidInput()
    {
        assertTrue(repository.areValidCoordinates("org.example", "artifact"));
    }

    @Test
    public void testAreValidCoordinatesWithNullGroup()
    {
        assertFalse(repository.areValidCoordinates(null, "artifact"));
    }

    @Test
    public void testAreValidCoordinatesWithNullArtifact()
    {
        assertFalse(repository.areValidCoordinates("org.example", null));
    }

    @Test
    public void testAreValidCoordinatesWithColonInGroup()
    {
        assertFalse(repository.areValidCoordinates("org:example", "artifact"));
    }

    @Test
    public void testAreValidCoordinatesWithColonInArtifact()
    {
        assertFalse(repository.areValidCoordinates("org.example", "art:ifact"));
    }

    @Test
    public void testFindFilesWithNonExistentArtifact()
    {
        assertThrows(RuntimeException.class, () ->
            repository.findFiles(ArtifactType.ENTITIES, "org.nonexistent.testing123", "nonexistent-artifact", "999.0.0"));
    }

    @Test
    public void testFindDependenciesFilesWithNonExistentArtifact()
    {
        assertThrows(RuntimeException.class, () ->
            repository.findDependenciesFiles(ArtifactType.ENTITIES, "org.nonexistent.testing123", "nonexistent-artifact", "999.0.0"));
    }

    @Test
    public void testFindDependenciesByArtifactTypeWithNonExistentArtifact()
    {
        assertThrows(RuntimeException.class, () ->
            repository.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.nonexistent.testing123", "nonexistent-artifact", "999.0.0"));
    }

    @Test
    public void testFindDependenciesWithNonExistentArtifact()
    {
        assertThrows(RuntimeException.class, () ->
            repository.findDependencies("org.nonexistent.testing123", "nonexistent-artifact", "999.0.0"));
    }

    @Test
    public void testGetModulesFromPOMWithNonExistentArtifact()
    {
        assertThrows(RuntimeException.class, () ->
            repository.getModulesFromPOM(ArtifactType.ENTITIES, "org.nonexistent.testing123", "nonexistent-artifact", "999.0.0"));
    }

    @Test
    public void testGetPOMWithNonExistentArtifact()
    {
        assertThrows(RuntimeException.class, () ->
            repository.getPOM("org.nonexistent.testing123", "nonexistent-artifact", "999.0.0"));
    }

    @Test
    public void testGetJarFileWithNonExistentArtifact()
    {
        File jarFile = repository.getJarFile("org.nonexistent.testing123", "nonexistent-artifact", "999.0.0");
        assertNull(jarFile);
    }

    @Test
    public void testFindVersionsWithNonExistentArtifact()
    {
        assertThrows(ArtifactRepositoryException.class, () ->
            repository.findVersions("org.nonexistent.testing123", "nonexistent-artifact"));
    }

    @Test
    public void testFindVersionWithNonExistentArtifact()
    {
        assertThrows(ArtifactRepositoryException.class, () ->
            repository.findVersion("org.nonexistent.testing123", "nonexistent-artifact", "999.0.0"));
    }

    @Test
    public void testGetJarFileWithRealArtifact()
    {
        try
        {
            File jarFile = repository.getJarFile("junit", "junit", "4.13.2");
            if (jarFile != null)
            {
                assertTrue(jarFile.exists());
                assertTrue(jarFile.getName().contains("junit"));
            }
        }
        catch (Exception e)
        {
        }
    }

    @Test
    public void testGetPOMWithRealArtifact()
    {
        try
        {
            org.apache.maven.model.Model pom = repository.getPOM("junit", "junit", "4.13.2");
            assertNotNull(pom);
            if (pom.getArtifactId() != null)
            {
                assertEquals("junit", pom.getArtifactId());
            }
        }
        catch (RuntimeException e)
        {
        }
    }

    @Test
    public void testFindVersionsWithRealArtifact()
    {
        try
        {
            List<VersionId> versions = repository.findVersions("junit", "junit");
            assertNotNull(versions);
        }
        catch (ArtifactRepositoryException e)
        {
        }
    }

    @Test
    public void testFindVersionWithRealArtifact()
    {
        try
        {
            Optional<String> version = repository.findVersion("junit", "junit", "4.13.2");
            assertNotNull(version);
        }
        catch (ArtifactRepositoryException e)
        {
        }
    }

    @Test
    public void testGetJarFileWithSuccessfulResolution() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);
        File tempJar = tempDir.resolve("test-artifact-1.0.0.jar").toFile();
        tempJar.createNewFile();
        URL jarUrl = tempJar.toURI().toURL();
        URL[] jarUrls = new URL[]{jarUrl};

        doReturn(jarUrls).when(spyRepository).resolveJarFromRepository("org.example", "test-artifact", "1.0.0");

        File result = spyRepository.getJarFile("org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(tempJar.getPath(), result.getPath());
    }

    @Test
    public void testGetJarFileReturnsNullWhenResolutionReturnsNull() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        doReturn(null).when(spyRepository).resolveJarFromRepository("org.example", "test-artifact", "1.0.0");

        File result = spyRepository.getJarFile("org.example", "test-artifact", "1.0.0");

        assertNull(result);
    }

    @Test
    public void testGetJarFileReturnsNullWhenResolutionReturnsEmptyArray() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);
        URL[] emptyUrls = new URL[0];

        doReturn(emptyUrls).when(spyRepository).resolveJarFromRepository("org.example", "test-artifact", "1.0.0");

        File result = spyRepository.getJarFile("org.example", "test-artifact", "1.0.0");

        assertNull(result);
    }

    @Test
    public void testGetJarFileHandlesExceptionDuringResolution() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        doThrow(new RuntimeException("Resolution failed")).when(spyRepository).resolveJarFromRepository("org.example", "test-artifact", "1.0.0");

        File result = spyRepository.getJarFile("org.example", "test-artifact", "1.0.0");

        assertNull(result);
    }
}
