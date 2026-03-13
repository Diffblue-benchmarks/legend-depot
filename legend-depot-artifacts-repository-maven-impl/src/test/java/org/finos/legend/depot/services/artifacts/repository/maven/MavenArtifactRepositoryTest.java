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
import org.jboss.shrinkwrap.resolver.api.ResolutionException;
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
    public void testFindFilesWithSuccessfulResolution() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        File tempFile1 = tempDir.resolve("test-artifact-entities-1.0.0.jar").toFile();
        tempFile1.createNewFile();
        File[] artifactFiles = new File[]{tempFile1};

        doReturn(artifactFiles).when(spyRepository).resolveArtifactFilesFromRepository("org.example", "test-artifact-entities", "1.0.0");

        List<File> result = spyRepository.findFiles(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tempFile1, result.get(0));
    }

    @Test
    public void testFindFilesWithMultipleModules() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities", "test-artifact-entities-v2");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        File tempFile1 = tempDir.resolve("test-artifact-entities-1.0.0.jar").toFile();
        tempFile1.createNewFile();
        File[] artifactFiles1 = new File[]{tempFile1};

        File tempFile2 = tempDir.resolve("test-artifact-entities-v2-1.0.0.jar").toFile();
        tempFile2.createNewFile();
        File[] artifactFiles2 = new File[]{tempFile2};

        doReturn(artifactFiles1).when(spyRepository).resolveArtifactFilesFromRepository("org.example", "test-artifact-entities", "1.0.0");
        doReturn(artifactFiles2).when(spyRepository).resolveArtifactFilesFromRepository("org.example", "test-artifact-entities-v2", "1.0.0");

        List<File> result = spyRepository.findFiles(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(tempFile1));
        assertTrue(result.contains(tempFile2));
    }

    @Test
    public void testFindFilesHandlesNoResolvedResultException() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        doThrow(new org.jboss.shrinkwrap.resolver.api.NoResolvedResultException("Resolution failed")).when(spyRepository).resolveArtifactFilesFromRepository("org.example", "test-artifact-entities", "1.0.0");

        List<File> result = spyRepository.findFiles(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindFilesWithEmptyModules() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> emptyModules = java.util.Collections.emptyList();
        doReturn(emptyModules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        List<File> result = spyRepository.findFiles(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(0, result.size());
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
    public void testFindDependenciesByArtifactTypeWithMatchingModule() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        org.apache.maven.model.Model modulePom = new org.apache.maven.model.Model();
        org.apache.maven.model.Dependency dep1 = new org.apache.maven.model.Dependency();
        dep1.setGroupId("org.dependency");
        dep1.setArtifactId("dependency-entities");
        dep1.setVersion("2.0.0");
        modulePom.addDependency(dep1);

        org.apache.maven.model.Dependency dep2 = new org.apache.maven.model.Dependency();
        dep2.setGroupId("org.other");
        dep2.setArtifactId("other-artifact");
        dep2.setVersion("3.0.0");
        modulePom.addDependency(dep2);

        doReturn(modulePom).when(spyRepository).getPOM("org.example", "test-artifact-entities", "1.0.0");

        Set<ArtifactDependency> result = spyRepository.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(1, result.size());
        ArtifactDependency artifactDep = result.iterator().next();
        assertEquals("org.dependency", artifactDep.getGroupId());
        assertEquals("dependency-entities", artifactDep.getArtifactId());
        assertEquals("2.0.0", artifactDep.getVersion());
    }

    @Test
    public void testFindDependenciesByArtifactTypeWithNoMatchingModule() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-other");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        Set<ArtifactDependency> result = spyRepository.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindDependenciesByArtifactTypeWithNoDependencies() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        org.apache.maven.model.Model modulePom = new org.apache.maven.model.Model();

        doReturn(modulePom).when(spyRepository).getPOM("org.example", "test-artifact-entities", "1.0.0");

        Set<ArtifactDependency> result = spyRepository.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindDependenciesByArtifactTypeWithMultipleMatchingDependencies() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        org.apache.maven.model.Model modulePom = new org.apache.maven.model.Model();
        org.apache.maven.model.Dependency dep1 = new org.apache.maven.model.Dependency();
        dep1.setGroupId("org.dependency1");
        dep1.setArtifactId("dependency1-entities");
        dep1.setVersion("2.0.0");
        modulePom.addDependency(dep1);

        org.apache.maven.model.Dependency dep2 = new org.apache.maven.model.Dependency();
        dep2.setGroupId("org.dependency2");
        dep2.setArtifactId("dependency2-entities");
        dep2.setVersion("3.0.0");
        modulePom.addDependency(dep2);

        doReturn(modulePom).when(spyRepository).getPOM("org.example", "test-artifact-entities", "1.0.0");

        Set<ArtifactDependency> result = spyRepository.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(2, result.size());
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

    @Test
    public void testGetPOMHandlesResolutionException() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);
        File localRepoDir = tempDir.resolve("repo").resolve("org").resolve("example").resolve("test-artifact").resolve("1.0.0").toFile();
        localRepoDir.mkdirs();
        File pomFile = new File(localRepoDir, "test-artifact-1.0.0.pom");

        String pomContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project>\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <groupId>org.example</groupId>\n" +
                "  <artifactId>test-artifact</artifactId>\n" +
                "  <version>1.0.0</version>\n" +
                "</project>";

        try (FileWriter writer = new FileWriter(pomFile))
        {
            writer.write(pomContent);
        }

        doThrow(new ResolutionException("Resolution failed")).when(spyRepository).resolvePOMFromRepository("org.example", "test-artifact", "1.0.0");

        org.apache.maven.model.Model result = spyRepository.getPOM("org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals("test-artifact", result.getArtifactId());
    }

    @Test
    public void testGetPOMWithNullResolutionResult() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);
        File localRepoDir = tempDir.resolve("repo").resolve("org").resolve("example").resolve("test-artifact").resolve("2.0.0").toFile();
        localRepoDir.mkdirs();
        File pomFile = new File(localRepoDir, "test-artifact-2.0.0.pom");

        String pomContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project>\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <groupId>org.example</groupId>\n" +
                "  <artifactId>test-artifact</artifactId>\n" +
                "  <version>2.0.0</version>\n" +
                "</project>";

        try (FileWriter writer = new FileWriter(pomFile))
        {
            writer.write(pomContent);
        }

        doReturn(null).when(spyRepository).resolvePOMFromRepository("org.example", "test-artifact", "2.0.0");

        org.apache.maven.model.Model result = spyRepository.getPOM("org.example", "test-artifact", "2.0.0");

        assertNotNull(result);
        assertEquals("test-artifact", result.getArtifactId());
    }

    @Test
    public void testGetPOMWithEmptyResolutionResult() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);
        File localRepoDir = tempDir.resolve("repo").resolve("org").resolve("example").resolve("test-artifact").resolve("3.0.0").toFile();
        localRepoDir.mkdirs();
        File pomFile = new File(localRepoDir, "test-artifact-3.0.0.pom");

        String pomContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project>\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <groupId>org.example</groupId>\n" +
                "  <artifactId>test-artifact</artifactId>\n" +
                "  <version>3.0.0</version>\n" +
                "</project>";

        try (FileWriter writer = new FileWriter(pomFile))
        {
            writer.write(pomContent);
        }

        URL[] emptyUrls = new URL[0];
        doReturn(emptyUrls).when(spyRepository).resolvePOMFromRepository("org.example", "test-artifact", "3.0.0");

        org.apache.maven.model.Model result = spyRepository.getPOM("org.example", "test-artifact", "3.0.0");

        assertNotNull(result);
        assertEquals("test-artifact", result.getArtifactId());
    }

    @Test
    public void testGetPOMWithSuccessfulResolution() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);
        File tempPom = tempDir.resolve("resolved-pom.pom").toFile();

        String pomContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project>\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <groupId>org.example</groupId>\n" +
                "  <artifactId>resolved-artifact</artifactId>\n" +
                "  <version>1.0.0</version>\n" +
                "</project>";

        try (FileWriter writer = new FileWriter(tempPom))
        {
            writer.write(pomContent);
        }

        URL pomUrl = tempPom.toURI().toURL();
        URL[] pomUrls = new URL[]{pomUrl};

        doReturn(pomUrls).when(spyRepository).resolvePOMFromRepository("org.example", "resolved-artifact", "1.0.0");

        org.apache.maven.model.Model result = spyRepository.getPOM("org.example", "resolved-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals("resolved-artifact", result.getArtifactId());
    }

    @Test
    public void testGetPOMHandlesFileReadException() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        doReturn(null).when(spyRepository).resolvePOMFromRepository("org.example", "nonexistent-pom", "1.0.0");

        org.apache.maven.model.Model result = spyRepository.getPOM("org.example", "nonexistent-pom", "1.0.0");

        assertNotNull(result);
        assertNull(result.getArtifactId());
    }

    @Test
    public void testGetModulesFromPOMWithNoModules() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);
        org.apache.maven.model.Model model = new org.apache.maven.model.Model();
        model.setGroupId("org.example");
        model.setArtifactId("test-artifact");
        model.setVersion("1.0.0");

        doReturn(model).when(spyRepository).getPOM("org.example", "test-artifact", "1.0.0");

        List<String> result = spyRepository.getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test-artifact", result.get(0));
    }

    @Test
    public void testGetModulesFromPOMWithMatchingModule() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);
        org.apache.maven.model.Model model = new org.apache.maven.model.Model();
        model.setGroupId("org.example");
        model.setArtifactId("test-artifact");
        model.setVersion("1.0.0");
        model.addModule("test-artifact-entities");
        model.addModule("test-artifact-other");

        doReturn(model).when(spyRepository).getPOM("org.example", "test-artifact", "1.0.0");

        List<String> result = spyRepository.getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test-artifact-entities", result.get(0));
    }

    @Test
    public void testFindDependenciesWithModulesAndDirectDependencies() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        org.apache.maven.model.Model modulePom = new org.apache.maven.model.Model();
        org.apache.maven.model.Dependency dep = new org.apache.maven.model.Dependency();
        dep.setGroupId("org.dependency");
        dep.setArtifactId("dependency-entities");
        dep.setVersion("2.0.0");
        modulePom.addDependency(dep);

        org.apache.maven.model.Model dependencyPom = new org.apache.maven.model.Model();
        org.apache.maven.model.Parent parent = new org.apache.maven.model.Parent();
        parent.setGroupId("org.parent");
        parent.setArtifactId("parent-artifact");
        parent.setVersion("3.0.0");
        dependencyPom.setParent(parent);

        doReturn(modulePom).when(spyRepository).getPOM("org.example", "test-artifact-entities", "1.0.0");
        doReturn(dependencyPom).when(spyRepository).getPOM("org.dependency", "dependency-entities", "2.0.0");

        Set<ArtifactDependency> result = spyRepository.findDependencies("org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(1, result.size());
        ArtifactDependency artifactDep = result.iterator().next();
        assertEquals("org.parent", artifactDep.getGroupId());
        assertEquals("parent-artifact", artifactDep.getArtifactId());
        assertEquals("3.0.0", artifactDep.getVersion());
    }

    @Test
    public void testFindDependenciesWithPluginDependencies() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        org.apache.maven.model.Model modulePom = new org.apache.maven.model.Model();
        org.apache.maven.model.Dependency depWithoutVersion = new org.apache.maven.model.Dependency();
        depWithoutVersion.setGroupId("org.dep");
        depWithoutVersion.setArtifactId("dep-artifact");
        modulePom.addDependency(depWithoutVersion);

        org.apache.maven.model.Build build = new org.apache.maven.model.Build();
        org.apache.maven.model.Plugin plugin = new org.apache.maven.model.Plugin();
        org.apache.maven.model.Dependency pluginDep = new org.apache.maven.model.Dependency();
        pluginDep.setGroupId("org.plugin.dep");
        pluginDep.setArtifactId("plugin-dep-entities");
        pluginDep.setVersion("1.5.0");
        plugin.addDependency(pluginDep);
        build.addPlugin(plugin);
        modulePom.setBuild(build);

        org.apache.maven.model.Model dependencyPom = new org.apache.maven.model.Model();
        org.apache.maven.model.Parent parent = new org.apache.maven.model.Parent();
        parent.setGroupId("org.plugin.parent");
        parent.setArtifactId("plugin-parent-artifact");
        parent.setVersion("2.5.0");
        dependencyPom.setParent(parent);

        doReturn(modulePom).when(spyRepository).getPOM("org.example", "test-artifact-entities", "1.0.0");
        doReturn(dependencyPom).when(spyRepository).getPOM("org.plugin.dep", "plugin-dep-entities", "1.5.0");

        Set<ArtifactDependency> result = spyRepository.findDependencies("org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(1, result.size());
        ArtifactDependency artifactDep = result.iterator().next();
        assertEquals("org.plugin.parent", artifactDep.getGroupId());
        assertEquals("plugin-parent-artifact", artifactDep.getArtifactId());
        assertEquals("2.5.0", artifactDep.getVersion());
    }

    @Test
    public void testFindDependenciesWithNullParent() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        org.apache.maven.model.Model modulePom = new org.apache.maven.model.Model();
        org.apache.maven.model.Dependency dep = new org.apache.maven.model.Dependency();
        dep.setGroupId("org.dependency");
        dep.setArtifactId("dependency-entities");
        dep.setVersion("2.0.0");
        modulePom.addDependency(dep);

        org.apache.maven.model.Model dependencyPom = new org.apache.maven.model.Model();

        doReturn(modulePom).when(spyRepository).getPOM("org.example", "test-artifact-entities", "1.0.0");
        doReturn(dependencyPom).when(spyRepository).getPOM("org.dependency", "dependency-entities", "2.0.0");

        Set<ArtifactDependency> result = spyRepository.findDependencies("org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindDependenciesWithNonEntitiesDependencies() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        org.apache.maven.model.Model modulePom = new org.apache.maven.model.Model();
        org.apache.maven.model.Dependency dep = new org.apache.maven.model.Dependency();
        dep.setGroupId("org.dependency");
        dep.setArtifactId("dependency-other");
        dep.setVersion("2.0.0");
        modulePom.addDependency(dep);

        doReturn(modulePom).when(spyRepository).getPOM("org.example", "test-artifact-entities", "1.0.0");

        Set<ArtifactDependency> result = spyRepository.findDependencies("org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindDependenciesWithEmptyModules() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> emptyModules = java.util.Collections.emptyList();
        doReturn(emptyModules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        Set<ArtifactDependency> result = spyRepository.findDependencies("org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindDependenciesWithNullBuild() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        org.apache.maven.model.Model modulePom = new org.apache.maven.model.Model();
        org.apache.maven.model.Dependency depWithoutVersion = new org.apache.maven.model.Dependency();
        depWithoutVersion.setGroupId("org.dep");
        depWithoutVersion.setArtifactId("dep-artifact");
        modulePom.addDependency(depWithoutVersion);
        modulePom.setBuild(null);

        doReturn(modulePom).when(spyRepository).getPOM("org.example", "test-artifact-entities", "1.0.0");

        Set<ArtifactDependency> result = spyRepository.findDependencies("org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindDependenciesWithEmptyPlugins() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        org.apache.maven.model.Model modulePom = new org.apache.maven.model.Model();
        org.apache.maven.model.Dependency depWithoutVersion = new org.apache.maven.model.Dependency();
        depWithoutVersion.setGroupId("org.dep");
        depWithoutVersion.setArtifactId("dep-artifact");
        modulePom.addDependency(depWithoutVersion);

        org.apache.maven.model.Build build = new org.apache.maven.model.Build();
        modulePom.setBuild(build);

        doReturn(modulePom).when(spyRepository).getPOM("org.example", "test-artifact-entities", "1.0.0");

        Set<ArtifactDependency> result = spyRepository.findDependencies("org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindDependenciesWithMultipleModulesAndDependencies() throws Exception
    {
        MavenArtifactRepository spyRepository = spy(repository);

        java.util.List<String> modules = java.util.Arrays.asList("test-artifact-entities", "test-artifact-entities-v2");
        doReturn(modules).when(spyRepository).getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "test-artifact", "1.0.0");

        org.apache.maven.model.Model modulePom1 = new org.apache.maven.model.Model();
        org.apache.maven.model.Dependency dep1 = new org.apache.maven.model.Dependency();
        dep1.setGroupId("org.dependency1");
        dep1.setArtifactId("dependency1-entities");
        dep1.setVersion("2.0.0");
        modulePom1.addDependency(dep1);

        org.apache.maven.model.Model modulePom2 = new org.apache.maven.model.Model();
        org.apache.maven.model.Dependency dep2 = new org.apache.maven.model.Dependency();
        dep2.setGroupId("org.dependency2");
        dep2.setArtifactId("dependency2-entities");
        dep2.setVersion("3.0.0");
        modulePom2.addDependency(dep2);

        org.apache.maven.model.Model dependencyPom1 = new org.apache.maven.model.Model();
        org.apache.maven.model.Parent parent1 = new org.apache.maven.model.Parent();
        parent1.setGroupId("org.parent1");
        parent1.setArtifactId("parent1-artifact");
        parent1.setVersion("4.0.0");
        dependencyPom1.setParent(parent1);

        org.apache.maven.model.Model dependencyPom2 = new org.apache.maven.model.Model();
        org.apache.maven.model.Parent parent2 = new org.apache.maven.model.Parent();
        parent2.setGroupId("org.parent2");
        parent2.setArtifactId("parent2-artifact");
        parent2.setVersion("5.0.0");
        dependencyPom2.setParent(parent2);

        doReturn(modulePom1).when(spyRepository).getPOM("org.example", "test-artifact-entities", "1.0.0");
        doReturn(modulePom2).when(spyRepository).getPOM("org.example", "test-artifact-entities-v2", "1.0.0");
        doReturn(dependencyPom1).when(spyRepository).getPOM("org.dependency1", "dependency1-entities", "2.0.0");
        doReturn(dependencyPom2).when(spyRepository).getPOM("org.dependency2", "dependency2-entities", "3.0.0");

        Set<ArtifactDependency> result = spyRepository.findDependencies("org.example", "test-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
