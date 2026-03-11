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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestArtifactDependency
{
    @Test
    void canCreateArtifactDependency()
    {
        ArtifactDependency dependency = new ArtifactDependency("org.finos", "legend-depot", "1.0.0");

        assertEquals("org.finos", dependency.getGroupId());
        assertEquals("legend-depot", dependency.getArtifactId());
        assertEquals("1.0.0", dependency.getVersion());
    }

    @Test
    void testEqualsSameValues()
    {
        ArtifactDependency dep1 = new ArtifactDependency("org.finos", "legend-depot", "1.0.0");
        ArtifactDependency dep2 = new ArtifactDependency("org.finos", "legend-depot", "1.0.0");

        assertEquals(dep1, dep2);
    }

    @Test
    void testEqualsDifferentValues()
    {
        ArtifactDependency dep1 = new ArtifactDependency("org.finos", "legend-depot", "1.0.0");
        ArtifactDependency dep2 = new ArtifactDependency("org.finos", "legend-depot", "2.0.0");

        assertNotEquals(dep1, dep2);
    }

    @Test
    void testHashCodeConsistentWithEquals()
    {
        ArtifactDependency dep1 = new ArtifactDependency("org.finos", "legend-depot", "1.0.0");
        ArtifactDependency dep2 = new ArtifactDependency("org.finos", "legend-depot", "1.0.0");

        assertEquals(dep1.hashCode(), dep2.hashCode());
    }

    @Test
    void testHashCodeDifferentForDifferentValues()
    {
        ArtifactDependency dep1 = new ArtifactDependency("org.finos", "legend-depot", "1.0.0");
        ArtifactDependency dep2 = new ArtifactDependency("com.example", "other-artifact", "3.0.0");

        assertNotEquals(dep1.hashCode(), dep2.hashCode());
    }
}
