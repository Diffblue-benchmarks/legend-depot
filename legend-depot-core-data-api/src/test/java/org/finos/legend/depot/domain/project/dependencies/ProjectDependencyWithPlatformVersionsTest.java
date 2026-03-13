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

public class ProjectDependencyWithPlatformVersionsTest
{

    @Test
    public void canCreateProjectDependencyWithPlatformVersions()
    {

        ProjectVersion dependency = new ProjectVersion("test.group", "test-artifact", "1.0.0");
        List<Property> platformsVersion = Arrays.asList(
                new Property("platform1", "version1"),
                new Property("platform2", "version2")
        );

        ProjectDependencyWithPlatformVersions projectDependency = new ProjectDependencyWithPlatformVersions(
                "example.group",
                "example-artifact",
                "2.0.0",
                dependency,
                platformsVersion
        );

        Assertions.assertNotNull(projectDependency);
        Assertions.assertEquals("example.group", projectDependency.getGroupId());
        Assertions.assertEquals("example-artifact", projectDependency.getArtifactId());
        Assertions.assertEquals("2.0.0", projectDependency.getVersionId());

    }

    @Test
    public void canGetPlatformsVersion()
    {

        ProjectVersion dependency = new ProjectVersion("test.group", "test-artifact", "1.0.0");
        List<Property> platformsVersion = Arrays.asList(
                new Property("platform1", "version1"),
                new Property("platform2", "version2")
        );

        ProjectDependencyWithPlatformVersions projectDependency = new ProjectDependencyWithPlatformVersions(
                "example.group",
                "example-artifact",
                "2.0.0",
                dependency,
                platformsVersion
        );

        List<Property> result = projectDependency.getPlatformsVersion();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("platform1", result.get(0).getPropertyName());
        Assertions.assertEquals("version1", result.get(0).getValue());

    }

    @Test
    public void canGetDependency()
    {

        ProjectVersion dependency = new ProjectVersion("test.group", "test-artifact", "1.0.0");
        List<Property> platformsVersion = Collections.emptyList();

        ProjectDependencyWithPlatformVersions projectDependency = new ProjectDependencyWithPlatformVersions(
                "example.group",
                "example-artifact",
                "2.0.0",
                dependency,
                platformsVersion
        );

        ProjectVersion result = projectDependency.getDependency();

        Assertions.assertNotNull(result);
        Assertions.assertEquals("test.group", result.getGroupId());
        Assertions.assertEquals("test-artifact", result.getArtifactId());
        Assertions.assertEquals("1.0.0", result.getVersionId());

    }

    @Test
    public void testEquals()
    {

        ProjectVersion dependency1 = new ProjectVersion("test.group", "test-artifact", "1.0.0");
        ProjectVersion dependency2 = new ProjectVersion("test.group", "test-artifact", "1.0.0");
        ProjectVersion dependency3 = new ProjectVersion("other.group", "other-artifact", "2.0.0");
        List<Property> platformsVersion1 = Arrays.asList(new Property("platform1", "version1"));
        List<Property> platformsVersion2 = Arrays.asList(new Property("platform1", "version1"));
        List<Property> platformsVersion3 = Arrays.asList(new Property("platform2", "version2"));

        ProjectDependencyWithPlatformVersions projectDependency1 = new ProjectDependencyWithPlatformVersions(
                "example.group",
                "example-artifact",
                "2.0.0",
                dependency1,
                platformsVersion1
        );

        ProjectDependencyWithPlatformVersions projectDependency2 = new ProjectDependencyWithPlatformVersions(
                "example.group",
                "example-artifact",
                "2.0.0",
                dependency2,
                platformsVersion2
        );

        ProjectDependencyWithPlatformVersions projectDependency3 = new ProjectDependencyWithPlatformVersions(
                "example.group",
                "example-artifact",
                "2.0.0",
                dependency3,
                platformsVersion3
        );

        Assertions.assertTrue(projectDependency1.equals(projectDependency2));
        Assertions.assertFalse(projectDependency1.equals(projectDependency3));
        Assertions.assertFalse(projectDependency1.equals(null));

    }

    @Test
    public void testHashCode()
    {

        ProjectVersion dependency1 = new ProjectVersion("test.group", "test-artifact", "1.0.0");
        ProjectVersion dependency2 = new ProjectVersion("test.group", "test-artifact", "1.0.0");
        List<Property> platformsVersion1 = Arrays.asList(new Property("platform1", "version1"));
        List<Property> platformsVersion2 = Arrays.asList(new Property("platform1", "version1"));

        ProjectDependencyWithPlatformVersions projectDependency1 = new ProjectDependencyWithPlatformVersions(
                "example.group",
                "example-artifact",
                "2.0.0",
                dependency1,
                platformsVersion1
        );

        ProjectDependencyWithPlatformVersions projectDependency2 = new ProjectDependencyWithPlatformVersions(
                "example.group",
                "example-artifact",
                "2.0.0",
                dependency2,
                platformsVersion2
        );

        Assertions.assertEquals(projectDependency1.hashCode(), projectDependency2.hashCode());

    }
}
