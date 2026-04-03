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

package org.finos.legend.depot.domain.artifacts.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArtifactDependencyTest
{
    @Test
    public void testConstructorAndGetters()
    {
        ArtifactDependency dep = new ArtifactDependency("com.example", "my-artifact", "1.0.0");

        Assertions.assertEquals("com.example", dep.getGroupId());
        Assertions.assertEquals("my-artifact", dep.getArtifactId());
        Assertions.assertEquals("1.0.0", dep.getVersion());
    }

    @Test
    public void testGetGroupId()
    {
        ArtifactDependency dep = new ArtifactDependency("org.finos", "artifact", "2.0.0");

        Assertions.assertEquals("org.finos", dep.getGroupId());
    }

    @Test
    public void testGetArtifactId()
    {
        ArtifactDependency dep = new ArtifactDependency("org.finos", "my-lib", "2.0.0");

        Assertions.assertEquals("my-lib", dep.getArtifactId());
    }

    @Test
    public void testGetVersion()
    {
        ArtifactDependency dep = new ArtifactDependency("org.finos", "artifact", "3.1.4");

        Assertions.assertEquals("3.1.4", dep.getVersion());
    }

    @Test
    public void testEqualsWhenSameValues()
    {
        ArtifactDependency dep1 = new ArtifactDependency("com.example", "artifact", "1.0.0");
        ArtifactDependency dep2 = new ArtifactDependency("com.example", "artifact", "1.0.0");

        Assertions.assertTrue(dep1.equals(dep2));
    }

    @Test
    public void testEqualsWhenDifferentValues()
    {
        ArtifactDependency dep1 = new ArtifactDependency("com.example", "artifact", "1.0.0");
        ArtifactDependency dep2 = new ArtifactDependency("com.example", "artifact", "2.0.0");

        Assertions.assertFalse(dep1.equals(dep2));
    }

    @Test
    public void testEqualsWithNull()
    {
        ArtifactDependency dep = new ArtifactDependency("com.example", "artifact", "1.0.0");

        Assertions.assertFalse(dep.equals(null));
    }

    @Test
    public void testHashCodeConsistency()
    {
        ArtifactDependency dep1 = new ArtifactDependency("com.example", "artifact", "1.0.0");
        ArtifactDependency dep2 = new ArtifactDependency("com.example", "artifact", "1.0.0");

        Assertions.assertEquals(dep1.hashCode(), dep2.hashCode());
    }

    @Test
    public void testHashCodeDifference()
    {
        ArtifactDependency dep1 = new ArtifactDependency("com.example", "artifact", "1.0.0");
        ArtifactDependency dep2 = new ArtifactDependency("com.other", "artifact", "1.0.0");

        Assertions.assertNotEquals(dep1.hashCode(), dep2.hashCode());
    }
}
