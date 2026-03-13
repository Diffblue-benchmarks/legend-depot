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

package org.finos.legend.depot.services.api.dependencies;

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

public class DependencyOverrideTest
{
    private static class TestDependencyOverride implements DependencyOverride
    {
        @Override
        public List<ProjectVersion> overrideWith(List<ProjectVersion> dependencies, List<ProjectVersion> overridingDependencies, Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> executableFunction)
        {
            return new ArrayList<>(dependencies);
        }
    }

    @Test
    public void canCallOverrideWithMethod()
    {
        DependencyOverride override = new TestDependencyOverride();
        List<ProjectVersion> dependencies = new ArrayList<>();
        dependencies.add(new ProjectVersion("com.example", "artifact1", "1.0.0"));
        List<ProjectVersion> overridingDependencies = new ArrayList<>();
        Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> function = (list, bool) -> new HashSet<>();

        List<ProjectVersion> result = override.overrideWith(dependencies, overridingDependencies, function);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void canCallOverrideWithEmptyLists()
    {
        DependencyOverride override = new TestDependencyOverride();
        List<ProjectVersion> dependencies = Collections.emptyList();
        List<ProjectVersion> overridingDependencies = Collections.emptyList();
        Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> function = (list, bool) -> Collections.emptySet();

        List<ProjectVersion> result = override.overrideWith(dependencies, overridingDependencies, function);

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
