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
import java.util.List;

public class TestProjectDependencyWithPlatformVersions
{
    @Test
    public void canCreateInstanceAndGetFields()
    {
        ProjectVersion dependency = new ProjectVersion("org.finos.test", "dep-artifact", "1.0.0");
        List<Property> platforms = Arrays.asList(new Property("java", "11"), new Property("legend", "2.0"));

        ProjectDependencyWithPlatformVersions instance = new ProjectDependencyWithPlatformVersions(
                "org.finos.legend", "test-artifact", "2.0.0", dependency, platforms);

        Assertions.assertEquals(dependency, instance.getDependency());
        Assertions.assertEquals(platforms, instance.getPlatformsVersion());
        Assertions.assertEquals("org.finos.legend", instance.getGroupId());
        Assertions.assertEquals("test-artifact", instance.getArtifactId());
        Assertions.assertEquals("2.0.0", instance.getVersionId());
    }

    @Test
    public void canCreateWithEmptyPlatforms()
    {
        ProjectVersion dependency = new ProjectVersion("org.finos.test", "dep-artifact", "1.0.0");

        ProjectDependencyWithPlatformVersions instance = new ProjectDependencyWithPlatformVersions(
                "org.finos.legend", "test-artifact", "2.0.0", dependency, Collections.emptyList());

        Assertions.assertNotNull(instance.getPlatformsVersion());
        Assertions.assertTrue(instance.getPlatformsVersion().isEmpty());
        Assertions.assertEquals(dependency, instance.getDependency());
    }

    @Test
    public void testEqualsAndHashCode()
    {
        ProjectVersion dependency = new ProjectVersion("org.finos.test", "dep-artifact", "1.0.0");
        List<Property> platforms = Arrays.asList(new Property("java", "11"));

        ProjectDependencyWithPlatformVersions instance1 = new ProjectDependencyWithPlatformVersions(
                "org.finos.legend", "test-artifact", "2.0.0", dependency, platforms);
        ProjectDependencyWithPlatformVersions instance2 = new ProjectDependencyWithPlatformVersions(
                "org.finos.legend", "test-artifact", "2.0.0", dependency, platforms);

        Assertions.assertEquals(instance1, instance2);
        Assertions.assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    public void testNotEquals()
    {
        ProjectVersion dependency1 = new ProjectVersion("org.finos.test", "dep-artifact", "1.0.0");
        ProjectVersion dependency2 = new ProjectVersion("org.finos.test", "dep-artifact", "2.0.0");
        List<Property> platforms = Arrays.asList(new Property("java", "11"));

        ProjectDependencyWithPlatformVersions instance1 = new ProjectDependencyWithPlatformVersions(
                "org.finos.legend", "test-artifact", "2.0.0", dependency1, platforms);
        ProjectDependencyWithPlatformVersions instance2 = new ProjectDependencyWithPlatformVersions(
                "org.finos.legend", "test-artifact", "2.0.0", dependency2, platforms);

        Assertions.assertNotEquals(instance1, instance2);
    }
}
