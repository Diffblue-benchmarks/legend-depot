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

package org.finos.legend.depot.domain.project.dependencies;

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

public class TestProjectDependencyWithPlatformVersions
{
    @Test
    public void testConstructorAndGetters()
    {
        ProjectVersion dep = new ProjectVersion("org.finos", "dep-artifact", "2.0.0");
        Property prop = new Property("platform.version", "1.5.0");

        ProjectDependencyWithPlatformVersions pdwp = new ProjectDependencyWithPlatformVersions(
                "org.finos", "artifact", "1.0.0", dep, Arrays.asList(prop));

        Assertions.assertEquals("org.finos", pdwp.getGroupId());
        Assertions.assertEquals("artifact", pdwp.getArtifactId());
        Assertions.assertEquals("1.0.0", pdwp.getVersionId());
        Assertions.assertEquals(dep, pdwp.getDependency());
        Assertions.assertEquals(1, pdwp.getPlatformsVersion().size());
        Assertions.assertEquals("platform.version", pdwp.getPlatformsVersion().get(0).getPropertyName());
    }

    @Test
    public void testEquality()
    {
        ProjectVersion dep = new ProjectVersion("org.finos", "dep", "1.0.0");
        ProjectDependencyWithPlatformVersions a = new ProjectDependencyWithPlatformVersions(
                "org.finos", "artifact", "1.0.0", dep, Collections.emptyList());
        ProjectDependencyWithPlatformVersions b = new ProjectDependencyWithPlatformVersions(
                "org.finos", "artifact", "1.0.0", dep, Collections.emptyList());
        Assertions.assertEquals(a, b);
    }

    @Test
    public void testNotEqualDifferentDependency()
    {
        ProjectVersion dep1 = new ProjectVersion("org.finos", "dep-a", "1.0.0");
        ProjectVersion dep2 = new ProjectVersion("org.finos", "dep-b", "1.0.0");

        ProjectDependencyWithPlatformVersions a = new ProjectDependencyWithPlatformVersions(
                "org.finos", "artifact", "1.0.0", dep1, Collections.emptyList());
        ProjectDependencyWithPlatformVersions b = new ProjectDependencyWithPlatformVersions(
                "org.finos", "artifact", "1.0.0", dep2, Collections.emptyList());
        Assertions.assertNotEquals(a, b);
    }
}
