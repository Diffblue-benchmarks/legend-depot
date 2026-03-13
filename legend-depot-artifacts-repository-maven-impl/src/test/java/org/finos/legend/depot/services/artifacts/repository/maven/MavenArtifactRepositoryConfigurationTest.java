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

public class MavenArtifactRepositoryConfigurationTest
{
    @Test
    public void testConstructorAndGetters()
    {
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration("/path/to/settings.xml");
        Assertions.assertEquals("/path/to/settings.xml", config.getSettingsLocation());
        Assertions.assertEquals("MavenArtifactRepositoryConfiguration", config.getName());
    }

    @Test
    public void testToString()
    {
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration("/settings.xml");
        String str = config.toString();
        Assertions.assertTrue(str.contains("MavenArtifactRepositoryConfiguration"));
        Assertions.assertTrue(str.contains("/settings.xml"));
    }

    @Test
    public void testDifferentSettingsLocations()
    {
        MavenArtifactRepositoryConfiguration config1 = new MavenArtifactRepositoryConfiguration("/home/user/.m2/settings.xml");
        MavenArtifactRepositoryConfiguration config2 = new MavenArtifactRepositoryConfiguration("/opt/maven/settings.xml");
        Assertions.assertNotEquals(config1.getSettingsLocation(), config2.getSettingsLocation());
    }
}
