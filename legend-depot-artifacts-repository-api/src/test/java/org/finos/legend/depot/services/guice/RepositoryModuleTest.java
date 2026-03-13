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

import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryProviderConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepositoryModuleTest
{
    @Test
    public void canGetArtifactRepositoryWithNullConfiguration()
    {
        RepositoryModule module = new RepositoryModule();
        ArtifactRepository repository = module.getArtifactRepository(null);
        Assertions.assertNotNull(repository);
        Assertions.assertTrue(repository instanceof VoidArtifactRepositoryProvider);
    }

    @Test
    public void canGetArtifactRepositoryWhenConfigurationReturnsNull()
    {
        RepositoryModule module = new RepositoryModule();
        ArtifactRepositoryProviderConfiguration mockConfig = mock(ArtifactRepositoryProviderConfiguration.class);
        when(mockConfig.initialiseArtifactRepositoryProvider()).thenReturn(null);
        when(mockConfig.getName()).thenReturn("test-config");

        ArtifactRepository repository = module.getArtifactRepository(mockConfig);
        Assertions.assertNotNull(repository);
        Assertions.assertTrue(repository instanceof VoidArtifactRepositoryProvider);
    }

    @Test
    public void canGetArtifactRepositoryFromValidConfiguration()
    {
        RepositoryModule module = new RepositoryModule();
        ArtifactRepositoryProviderConfiguration mockConfig = mock(ArtifactRepositoryProviderConfiguration.class);
        ArtifactRepository mockRepository = mock(ArtifactRepository.class);
        when(mockConfig.initialiseArtifactRepositoryProvider()).thenReturn(mockRepository);
        when(mockConfig.getName()).thenReturn("test-config");

        ArtifactRepository repository = module.getArtifactRepository(mockConfig);
        Assertions.assertNotNull(repository);
        Assertions.assertEquals(mockRepository, repository);
    }

    @Test
    public void canGetVoidRepositoryWhenConfigurationReturnsNullProvider()
    {
        RepositoryModule module = new RepositoryModule();
        ArtifactRepositoryProviderConfiguration mockConfig = mock(ArtifactRepositoryProviderConfiguration.class);
        when(mockConfig.initialiseArtifactRepositoryProvider()).thenReturn(null);

        ArtifactRepository repository = module.getArtifactRepository(mockConfig);
        Assertions.assertNotNull(repository);
        Assertions.assertTrue(repository instanceof VoidArtifactRepositoryProvider);
    }
}
