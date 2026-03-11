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

import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryProviderConfiguration;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactDependency;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.jboss.shrinkwrap.resolver.api.ResolutionException;
import org.jboss.shrinkwrap.resolver.api.maven.PackagingType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class TestMavenArtifactRepositoryCoverage
{
    private static final String GROUP_ID = "examples.metadata";
    private final TestMavenArtifactsRepository repository = new TestMavenArtifactsRepository();

    @Test
    public void constructorThrowsForNonMavenConfiguration()
    {
        ArtifactRepositoryProviderConfiguration badConfig = new ArtifactRepositoryProviderConfiguration("bad")
        {
            @Override
            public ArtifactRepository initialiseArtifactRepositoryProvider()
            {
                return null;
            }
        };
        Assertions.assertThrows(IllegalArgumentException.class, () -> new MavenArtifactRepository(badConfig));
    }

    @Test
    public void constructorThrowsForInvalidSettingsFile()
    {
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration("/nonexistent/settings.xml");
        Assertions.assertThrows(RuntimeException.class, () -> new MavenArtifactRepository(config));
    }

    @Test
    public void gavCoordinatesWithType()
    {
        String result = repository.gavCoordinates("com.example", "my-artifact", PackagingType.JAR, "1.0.0");
        Assertions.assertEquals("com.example:my-artifact:jar:1.0.0", result);
    }

    @Test
    public void gavCoordinatesWithNullType()
    {
        String result = repository.gavCoordinates("com.example", "my-artifact", null, "1.0.0");
        Assertions.assertEquals("com.example:my-artifact:1.0.0", result);
    }

    @Test
    public void gavCoordinatesThreeArg()
    {
        String result = repository.gavCoordinates("com.example", "my-artifact", "2.0.0");
        Assertions.assertEquals("com.example:my-artifact:2.0.0", result);
    }

    @Test
    public void gavCoordinatesWithPomType()
    {
        String result = repository.gavCoordinates("com.example", "my-artifact", PackagingType.POM, "1.0.0");
        Assertions.assertEquals("com.example:my-artifact:pom:1.0.0", result);
    }

    @Test
    public void areValidCoordinatesReturnsTrueForValid()
    {
        Assertions.assertTrue(repository.areValidCoordinates("com.example", "my-artifact"));
    }

    @Test
    public void areValidCoordinatesReturnsFalseForNullGroup()
    {
        Assertions.assertFalse(repository.areValidCoordinates(null, "my-artifact"));
    }

    @Test
    public void areValidCoordinatesReturnsFalseForNullArtifact()
    {
        Assertions.assertFalse(repository.areValidCoordinates("com.example", null));
    }

    @Test
    public void areValidCoordinatesReturnsFalseForGroupWithColon()
    {
        Assertions.assertFalse(repository.areValidCoordinates("com:example", "my-artifact"));
    }

    @Test
    public void areValidCoordinatesReturnsFalseForArtifactWithColon()
    {
        Assertions.assertFalse(repository.areValidCoordinates("com.example", "my:artifact"));
    }

    @Test
    public void canGetJarFile()
    {
        File jarFile = repository.getJarFile(GROUP_ID, "test-entities", "1.0.0");
        Assertions.assertNotNull(jarFile);
        Assertions.assertEquals("test-entities-1.0.0.jar", jarFile.getName());
    }

    @Test
    public void getJarFileReturnsNullForNonexistent()
    {
        File jarFile = repository.getJarFile(GROUP_ID, "nonexistent-artifact", "1.0.0");
        Assertions.assertNull(jarFile);
    }

    @Test
    public void canFindDependenciesFiles()
    {
        List<File> files = repository.findDependenciesFiles(ArtifactType.ENTITIES, GROUP_ID, "test", "1.0.0");
        Assertions.assertNotNull(files);
        Assertions.assertFalse(files.isEmpty());
    }

    @Test
    public void getPOMResolvesExistingPom()
    {
        org.apache.maven.model.Model model = repository.getPOM(GROUP_ID, "test", "1.0.0");
        Assertions.assertNotNull(model);
        Assertions.assertEquals("test", model.getArtifactId());
        Assertions.assertEquals("1.0.0", model.getVersion());
    }

    @Test
    public void getModulesFromPOMWithNoModules()
    {
        List<String> modules = repository.getModulesFromPOM(ArtifactType.ENTITIES, GROUP_ID, "test-entities", "1.0.0");
        Assertions.assertNotNull(modules);
        Assertions.assertEquals(1, modules.size());
        Assertions.assertEquals("test-entities", modules.get(0));
    }

    @Test
    public void findDependenciesWithPluginDependenciesFallback()
    {
        Set<ArtifactDependency> dependencies = repository.findDependencies(GROUP_ID, "test", "1.0.0");
        Assertions.assertNotNull(dependencies);
    }

    @Test
    public void getPOMReturnsEmptyModelWhenResolutionExceptionAndLocalFileNotFound()
    {
        TestMavenArtifactsRepository repoWithException = new TestMavenArtifactsRepository()
        {
            @Override
            protected URL[] resolvePOMFromRepository(String group, String artifact, String version)
            {
                throw new ResolutionException("test resolution failure");
            }
        };
        org.apache.maven.model.Model model = repoWithException.getPOM(GROUP_ID, "nonexistent", "9.9.9");
        Assertions.assertNotNull(model);
        Assertions.assertNull(model.getArtifactId());
    }

    @Test
    public void getPOMReturnsEmptyModelWhenPomResolvesToNull()
    {
        TestMavenArtifactsRepository repoReturningNull = new TestMavenArtifactsRepository()
        {
            @Override
            protected URL[] resolvePOMFromRepository(String group, String artifact, String version)
            {
                return null;
            }
        };
        org.apache.maven.model.Model model = repoReturningNull.getPOM(GROUP_ID, "nonexistent", "9.9.9");
        Assertions.assertNotNull(model);
        Assertions.assertNull(model.getArtifactId());
    }

    @Test
    public void getPOMReturnsEmptyModelWhenPomResolvesToEmptyArray()
    {
        TestMavenArtifactsRepository repoReturningEmpty = new TestMavenArtifactsRepository()
        {
            @Override
            protected URL[] resolvePOMFromRepository(String group, String artifact, String version)
            {
                return new URL[0];
            }
        };
        org.apache.maven.model.Model model = repoReturningEmpty.getPOM(GROUP_ID, "nonexistent", "9.9.9");
        Assertions.assertNotNull(model);
        Assertions.assertNull(model.getArtifactId());
    }
}
