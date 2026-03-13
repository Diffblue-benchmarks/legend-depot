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

package org.finos.legend.depot.services.dependencies;

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class DependencyUtilTest
{
    private final DependencyUtil dependencyUtil = new DependencyUtil();

    @Test
    public void testOverrideWithEmptyDependencies()
    {
        List<ProjectVersion> dependencies = new ArrayList<>();
        List<ProjectVersion> overrides = new ArrayList<>();
        overrides.add(new ProjectVersion("org.finos", "legend-sdlc", "2.0.0"));

        List<ProjectVersion> result = dependencyUtil.overrideWith(
                dependencies,
                overrides,
                (deps, flag) -> new HashSet<>(deps));

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testOverrideWithEmptyOverrides()
    {
        List<ProjectVersion> dependencies = new ArrayList<>();
        dependencies.add(new ProjectVersion("org.finos", "legend-sdlc", "1.0.0"));
        List<ProjectVersion> overrides = new ArrayList<>();

        List<ProjectVersion> result = dependencyUtil.overrideWith(
                dependencies,
                overrides,
                (deps, flag) -> new HashSet<>(deps));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("1.0.0", result.get(0).getVersionId());
    }

    @Test
    public void testOverrideWithMatchingDependency()
    {
        List<ProjectVersion> dependencies = new ArrayList<>(Arrays.asList(
                new ProjectVersion("org.finos", "legend-sdlc", "1.0.0"),
                new ProjectVersion("org.finos", "legend-engine", "3.0.0")
        ));
        List<ProjectVersion> overrides = new ArrayList<>();
        overrides.add(new ProjectVersion("org.finos", "legend-sdlc", "2.0.0"));

        List<ProjectVersion> result = dependencyUtil.overrideWith(
                dependencies,
                overrides,
                (deps, flag) -> new HashSet<>(deps));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("legend-engine", result.get(0).getArtifactId());
    }

    @Test
    public void testOverrideWithNoMatchingDependency()
    {
        List<ProjectVersion> dependencies = new ArrayList<>();
        dependencies.add(new ProjectVersion("org.finos", "legend-engine", "3.0.0"));
        List<ProjectVersion> overrides = new ArrayList<>();
        overrides.add(new ProjectVersion("org.finos", "legend-sdlc", "2.0.0"));

        List<ProjectVersion> result = dependencyUtil.overrideWith(
                dependencies,
                overrides,
                (deps, flag) -> new HashSet<>(deps));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("legend-engine", result.get(0).getArtifactId());
    }

    @Test
    public void testOverrideRemovesBothOverriddenAndDeletedDeps()
    {
        List<ProjectVersion> dependencies = new ArrayList<>(Arrays.asList(
                new ProjectVersion("org.finos", "legend-sdlc", "1.0.0"),
                new ProjectVersion("org.finos", "legend-sdlc", "1.5.0"),
                new ProjectVersion("org.finos", "legend-engine", "3.0.0")
        ));
        List<ProjectVersion> overrides = new ArrayList<>();
        overrides.add(new ProjectVersion("org.finos", "legend-sdlc", "2.0.0"));

        List<ProjectVersion> result = dependencyUtil.overrideWith(
                dependencies,
                overrides,
                (deps, flag) -> new HashSet<>(deps));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("legend-engine", result.get(0).getArtifactId());
    }
}
