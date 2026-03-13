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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

public class TestMavenArtifactRepositoryConfiguration
{
    @Test
    public void canCreateConfiguration()
    {
        String settingsLocation = "/path/to/settings.xml";
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration(settingsLocation);

        Assertions.assertNotNull(config);
        Assertions.assertEquals(settingsLocation, config.getSettingsLocation());
        Assertions.assertEquals("MavenArtifactRepositoryConfiguration", config.getName());
    }

    @Test
    public void canGetSettingsLocation()
    {
        String settingsLocation = "/custom/path/settings.xml";
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration(settingsLocation);

        Assertions.assertEquals(settingsLocation, config.getSettingsLocation());
    }

    @Test
    public void canInitialiseArtifactRepositoryProvider()
    {
        URL resource = this.getClass().getClassLoader().getResource("test-settings.xml");
        Assertions.assertNotNull(resource);
        String settingsLocation = new File(resource.getFile()).getAbsolutePath();
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration(settingsLocation);

        ArtifactRepository repository = config.initialiseArtifactRepositoryProvider();

        Assertions.assertNotNull(repository);
        Assertions.assertTrue(repository instanceof MavenArtifactRepository);
    }

    @Test
    public void canGetToString()
    {
        String settingsLocation = "/path/to/settings.xml";
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration(settingsLocation);

        String result = config.toString();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.contains("MavenArtifactRepositoryConfiguration"));
        Assertions.assertTrue(result.contains("MavenArtifactRepositoryConfiguration"));
        Assertions.assertTrue(result.contains(settingsLocation));
    }
}
