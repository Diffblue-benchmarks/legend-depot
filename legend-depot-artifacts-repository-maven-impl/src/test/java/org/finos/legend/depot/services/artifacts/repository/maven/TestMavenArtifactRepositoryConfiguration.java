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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMavenArtifactRepositoryConfiguration
{
    @Test
    public void canCreateConfiguration()
    {
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration("/path/to/settings.xml");

        Assertions.assertNotNull(config);
        Assertions.assertEquals("MavenArtifactRepositoryConfiguration", config.getName());
        Assertions.assertEquals("/path/to/settings.xml", config.getSettingsLocation());
    }

    @Test
    public void canGetSettingsLocation()
    {
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration("test-settings.xml");

        Assertions.assertEquals("test-settings.xml", config.getSettingsLocation());
    }

    @Test
    public void canInitialiseArtifactRepositoryProvider()
    {
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration("non-existent-settings.xml");

        Assertions.assertThrows(RuntimeException.class, () -> config.initialiseArtifactRepositoryProvider());
    }

    @Test
    public void canGetToString()
    {
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration("/path/to/settings.xml");

        String result = config.toString();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.contains("MavenArtifactRepositoryConfiguration"));
        Assertions.assertTrue(result.contains("/path/to/settings.xml"));
    }
}
