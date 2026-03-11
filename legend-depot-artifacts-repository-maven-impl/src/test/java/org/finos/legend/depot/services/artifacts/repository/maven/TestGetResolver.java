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

import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.net.URL;

public class TestGetResolver
{
    @Test
    public void getResolverReturnsNonNullMavenResolverSystem() throws Exception
    {
        URL settingsUrl = TestGetResolver.class.getClassLoader().getResource("test-settings.xml");
        Assertions.assertNotNull(settingsUrl, "test-settings.xml must be on the classpath");

        String settingsPath = settingsUrl.getFile();
        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration(settingsPath);
        MavenArtifactRepository repository = new MavenArtifactRepository(config);

        Method method = MavenArtifactRepository.class.getDeclaredMethod("getResolver");
        method.setAccessible(true);

        Object result = method.invoke(repository);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(MavenResolverSystem.class, result);
    }
}
