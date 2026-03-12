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

import org.eclipse.collections.api.factory.Sets;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TestDependencyUtil
{
    private final DependencyUtil dependencyUtil = new DependencyUtil();

    @Test
    public void testOverrideWithEmptyOverrides()
    {
        List<ProjectVersion> deps = new ArrayList<>(Arrays.asList(
                new ProjectVersion("org.finos", "artifact-a", "1.0.0"),
                new ProjectVersion("org.finos", "artifact-b", "2.0.0")
        ));

        List<ProjectVersion> result = dependencyUtil.overrideWith(deps, Collections.emptyList(), (pv, transitive) -> Sets.mutable.empty());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testOverrideWithEmptyDependencies()
    {
        List<ProjectVersion> deps = new ArrayList<>();
        List<ProjectVersion> overrides = Arrays.asList(
                new ProjectVersion("org.finos", "artifact-a", "2.0.0")
        );

        List<ProjectVersion> result = dependencyUtil.overrideWith(deps, overrides, (pv, transitive) -> Sets.mutable.empty());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testOverrideReplacesMatchingDependency()
    {
        ProjectVersion original = new ProjectVersion("org.finos", "artifact-a", "1.0.0");
        ProjectVersion override = new ProjectVersion("org.finos", "artifact-a", "2.0.0");
        ProjectVersion unrelated = new ProjectVersion("org.finos", "artifact-b", "1.0.0");

        List<ProjectVersion> deps = new ArrayList<>(Arrays.asList(original, unrelated));
        List<ProjectVersion> overrides = Arrays.asList(override);

        List<ProjectVersion> result = dependencyUtil.overrideWith(deps, overrides, (pv, transitive) ->
        {
            Set<ProjectVersion> transitives = Sets.mutable.empty();
            return transitives;
        });

        Assertions.assertFalse(result.contains(original));
        Assertions.assertTrue(result.contains(unrelated));
    }
}
