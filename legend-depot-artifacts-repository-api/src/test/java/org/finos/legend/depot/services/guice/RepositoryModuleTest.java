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

package org.finos.legend.depot.services.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryProviderConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RepositoryModuleTest
{
    @Test
    public void testGetArtifactRepositoryWithNullConfiguration()
    {
        RepositoryModule module = new RepositoryModule();
        ArtifactRepository result = module.getArtifactRepository(null);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result instanceof VoidArtifactRepositoryProvider);
    }

    @Test
    public void testGetArtifactRepositoryWithConfigurationReturningNull()
    {
        RepositoryModule module = new RepositoryModule();
        ArtifactRepositoryProviderConfiguration configuration = Mockito.mock(ArtifactRepositoryProviderConfiguration.class);
        Mockito.when(configuration.initialiseArtifactRepositoryProvider()).thenReturn(null);

        ArtifactRepository result = module.getArtifactRepository(configuration);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result instanceof VoidArtifactRepositoryProvider);
    }

    @Test
    public void testGetArtifactRepositoryWithValidConfiguration()
    {
        RepositoryModule module = new RepositoryModule();
        ArtifactRepositoryProviderConfiguration configuration = Mockito.mock(ArtifactRepositoryProviderConfiguration.class);
        ArtifactRepository mockRepo = Mockito.mock(ArtifactRepository.class);
        Mockito.when(configuration.initialiseArtifactRepositoryProvider()).thenReturn(mockRepo);
        Mockito.when(configuration.getName()).thenReturn("test-provider");

        ArtifactRepository result = module.getArtifactRepository(configuration);
        Assertions.assertSame(mockRepo, result);
    }
}
