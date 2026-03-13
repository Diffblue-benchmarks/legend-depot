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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtifactDependencyTest
{
    @Test
    public void testConstructorAndGetters()
    {
        ArtifactDependency dependency = new ArtifactDependency("com.example", "my-artifact", "1.0.0");

        assertNotNull(dependency);
        assertEquals("com.example", dependency.getGroupId());
        assertEquals("my-artifact", dependency.getArtifactId());
        assertEquals("1.0.0", dependency.getVersion());
    }

    @Test
    public void testGetGroupId()
    {
        ArtifactDependency dependency = new ArtifactDependency("org.test", "test-artifact", "2.0.0");

        assertEquals("org.test", dependency.getGroupId());
    }

    @Test
    public void testGetArtifactId()
    {
        ArtifactDependency dependency = new ArtifactDependency("org.test", "test-artifact", "2.0.0");

        assertEquals("test-artifact", dependency.getArtifactId());
    }

    @Test
    public void testGetVersion()
    {
        ArtifactDependency dependency = new ArtifactDependency("org.test", "test-artifact", "2.0.0");

        assertEquals("2.0.0", dependency.getVersion());
    }

    @Test
    public void testEquals()
    {
        ArtifactDependency dependency1 = new ArtifactDependency("com.example", "my-artifact", "1.0.0");
        ArtifactDependency dependency2 = new ArtifactDependency("com.example", "my-artifact", "1.0.0");
        ArtifactDependency dependency3 = new ArtifactDependency("com.example", "other-artifact", "1.0.0");

        assertTrue(dependency1.equals(dependency2));
        assertFalse(dependency1.equals(dependency3));
        assertFalse(dependency1.equals(null));
        assertTrue(dependency1.equals(dependency1));
    }

    @Test
    public void testHashCode()
    {
        ArtifactDependency dependency1 = new ArtifactDependency("com.example", "my-artifact", "1.0.0");
        ArtifactDependency dependency2 = new ArtifactDependency("com.example", "my-artifact", "1.0.0");
        ArtifactDependency dependency3 = new ArtifactDependency("com.example", "other-artifact", "1.0.0");

        assertEquals(dependency1.hashCode(), dependency2.hashCode());
        assertNotEquals(dependency1.hashCode(), dependency3.hashCode());
    }
}
