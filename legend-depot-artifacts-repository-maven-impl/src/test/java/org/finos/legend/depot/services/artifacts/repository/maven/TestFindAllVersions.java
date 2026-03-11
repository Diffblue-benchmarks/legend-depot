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
import org.jboss.shrinkwrap.resolver.api.VersionResolutionException;
import org.jboss.shrinkwrap.resolver.api.maven.PackagingType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFindAllVersions
{
    @Test
    public void findVersionsThrowsArtifactRepositoryExceptionOnResolverFailure()
    {
        MavenArtifactRepository repo = new MavenArtifactRepository(null);
        Assertions.assertThrows(ArtifactRepositoryException.class,
                () -> repo.findVersions("com.example", "my-artifact"));
    }

    @Test
    public void findVersionThrowsArtifactRepositoryExceptionOnResolverFailure()
    {
        MavenArtifactRepository repo = new MavenArtifactRepository(null);
        Assertions.assertThrows(ArtifactRepositoryException.class,
                () -> repo.findVersion("com.example", "my-artifact", "1.0.0"));
    }

    @Test
    public void findAllVersionsHandlesVersionResolutionException()
    {
        MavenArtifactRepository repo = new MavenArtifactRepository(null)
        {
            @Override
            protected String gavCoordinates(String group, String artifact, PackagingType type, String version)
            {
                throw new VersionResolutionException("test version resolution error");
            }
        };
        Assertions.assertThrows(ArtifactRepositoryException.class,
                () -> repo.findVersions("com.example", "my-artifact"));
    }
}
