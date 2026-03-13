//  Copyright 2023 Goldman Sachs
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

import org.eclipse.collections.api.block.function.Function2;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DependencyUtilTest
{
    @Test
    public void canOverrideWithEmptyLists()
    {
        DependencyUtil util = new DependencyUtil();
        List<ProjectVersion> dependencies = new ArrayList<>();
        List<ProjectVersion> overridingDependencies = new ArrayList<>();
        Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> function = (list, bool) -> new HashSet<>();

        List<ProjectVersion> result = util.overrideWith(dependencies, overridingDependencies, function);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void canOverrideWithMatchingDependencies()
    {
        DependencyUtil util = new DependencyUtil();
        List<ProjectVersion> dependencies = new ArrayList<>();
        dependencies.add(new ProjectVersion("com.example", "artifact1", "1.0.0"));
        dependencies.add(new ProjectVersion("com.example", "artifact2", "1.0.0"));

        List<ProjectVersion> overridingDependencies = new ArrayList<>();
        overridingDependencies.add(new ProjectVersion("com.example", "artifact1", "2.0.0"));

        Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> function = (list, bool) -> new HashSet<>(list);

        List<ProjectVersion> result = util.overrideWith(dependencies, overridingDependencies, function);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("artifact2", result.get(0).getArtifactId());
    }

    @Test
    public void canOverrideWithNonMatchingDependencies()
    {
        DependencyUtil util = new DependencyUtil();
        List<ProjectVersion> dependencies = new ArrayList<>();
        dependencies.add(new ProjectVersion("com.example", "artifact1", "1.0.0"));
        dependencies.add(new ProjectVersion("com.example", "artifact2", "1.0.0"));

        List<ProjectVersion> overridingDependencies = new ArrayList<>();
        overridingDependencies.add(new ProjectVersion("com.other", "artifact3", "1.0.0"));

        Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> function = (list, bool) -> new HashSet<>();

        List<ProjectVersion> result = util.overrideWith(dependencies, overridingDependencies, function);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void canOverrideWithMultipleVersionsSameArtifact()
    {
        DependencyUtil util = new DependencyUtil();
        List<ProjectVersion> dependencies = new ArrayList<>();
        dependencies.add(new ProjectVersion("com.example", "artifact1", "1.0.0"));
        dependencies.add(new ProjectVersion("com.example", "artifact1", "1.5.0"));
        dependencies.add(new ProjectVersion("com.example", "artifact2", "1.0.0"));

        List<ProjectVersion> overridingDependencies = new ArrayList<>();
        overridingDependencies.add(new ProjectVersion("com.example", "artifact1", "2.0.0"));

        Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> function = (list, bool) -> new HashSet<>(list);

        List<ProjectVersion> result = util.overrideWith(dependencies, overridingDependencies, function);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("artifact2", result.get(0).getArtifactId());
    }

    @Test
    public void canOverrideWithFunctionReturningEmptySet()
    {
        DependencyUtil util = new DependencyUtil();
        List<ProjectVersion> dependencies = new ArrayList<>();
        dependencies.add(new ProjectVersion("com.example", "artifact1", "1.0.0"));
        dependencies.add(new ProjectVersion("com.example", "artifact2", "1.0.0"));

        List<ProjectVersion> overridingDependencies = new ArrayList<>();
        overridingDependencies.add(new ProjectVersion("com.example", "artifact1", "2.0.0"));

        Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> function = (list, bool) -> Collections.emptySet();

        List<ProjectVersion> result = util.overrideWith(dependencies, overridingDependencies, function);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("artifact2", result.get(0).getArtifactId());
    }

    @Test
    public void canOverrideWithComplexScenario()
    {
        DependencyUtil util = new DependencyUtil();
        List<ProjectVersion> dependencies = new ArrayList<>();
        dependencies.add(new ProjectVersion("com.example", "artifact1", "1.0.0"));
        dependencies.add(new ProjectVersion("com.example", "artifact2", "1.0.0"));
        dependencies.add(new ProjectVersion("com.other", "artifact3", "1.0.0"));
        dependencies.add(new ProjectVersion("com.other", "artifact3", "1.5.0"));

        List<ProjectVersion> overridingDependencies = new ArrayList<>();
        overridingDependencies.add(new ProjectVersion("com.example", "artifact1", "2.0.0"));
        overridingDependencies.add(new ProjectVersion("com.other", "artifact3", "2.0.0"));

        Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> function = (list, bool) -> {
            Set<ProjectVersion> result = new HashSet<>();
            for (ProjectVersion pv : list)
            {
                result.add(pv);
            }
            return result;
        };

        List<ProjectVersion> result = util.overrideWith(dependencies, overridingDependencies, function);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("artifact2", result.get(0).getArtifactId());
        assertEquals("com.example", result.get(0).getGroupId());
    }

    @Test
    public void canOverrideWithSameOverridingDependency()
    {
        DependencyUtil util = new DependencyUtil();
        List<ProjectVersion> dependencies = new ArrayList<>();
        dependencies.add(new ProjectVersion("com.example", "artifact1", "1.0.0"));

        List<ProjectVersion> overridingDependencies = new ArrayList<>();
        overridingDependencies.add(new ProjectVersion("com.example", "artifact1", "1.0.0"));

        Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> function = (list, bool) -> new HashSet<>(list);

        List<ProjectVersion> result = util.overrideWith(dependencies, overridingDependencies, function);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1.0.0", result.get(0).getVersionId());
    }
}
