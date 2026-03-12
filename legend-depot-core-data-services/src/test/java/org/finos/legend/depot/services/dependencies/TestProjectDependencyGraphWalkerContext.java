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
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class TestProjectDependencyGraphWalkerContext
{
    @Test
    public void testAddVersionToProject()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        ProjectVersion pv = new ProjectVersion("org.finos", "artifact", "1.0.0");
        context.addVersionToProject("org.finos", "artifact", pv);

        Assertions.assertEquals(1, context.getProjectToVersions().size());
    }

    @Test
    public void testAddMultipleVersionsToSameProject()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        ProjectVersion v1 = new ProjectVersion("org.finos", "artifact", "1.0.0");
        ProjectVersion v2 = new ProjectVersion("org.finos", "artifact", "2.0.0");

        context.addVersionToProject("org.finos", "artifact", v1);
        context.addVersionToProject("org.finos", "artifact", v2);

        Assertions.assertEquals(1, context.getProjectToVersions().size());
        Set<ProjectVersion> versions = context.getProjectToVersions().values().iterator().next();
        Assertions.assertEquals(2, versions.size());
    }

    @Test
    public void testGetProjectDataPutIfAbsent()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");

        StoreProjectVersionData result = context.getProjectDataPutIfAbsent("org.finos", "artifact", "1.0.0", () -> data);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(data, result);
    }

    @Test
    public void testGetProjectDataPutIfAbsentReturnsCachedValue()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        StoreProjectVersionData first = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");
        StoreProjectVersionData second = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");

        context.getProjectDataPutIfAbsent("org.finos", "artifact", "1.0.0", () -> first);
        StoreProjectVersionData result = context.getProjectDataPutIfAbsent("org.finos", "artifact", "1.0.0", () -> second);

        Assertions.assertSame(first, result);
    }

    @Test
    public void testGetProjectDataReturnsNullForMissing()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        Assertions.assertNull(context.getProjectData("org.finos", "artifact", "1.0.0"));
    }

    @Test
    public void testGetProjectDataReturnsStoredValue()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");
        context.getProjectDataPutIfAbsent("org.finos", "artifact", "1.0.0", () -> data);

        StoreProjectVersionData result = context.getProjectData("org.finos", "artifact", "1.0.0");
        Assertions.assertNotNull(result);
    }

    @Test
    public void testGetProjectDataDependenciesNonTransitive()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();

        ProjectVersion dep = new ProjectVersion("org.finos", "dep", "1.0.0");
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");
        data.getVersionData().addDependency(dep);

        ProjectVersion key = new ProjectVersion("org.finos", "artifact", "1.0.0");
        context.getProjectDataPutIfAbsent("org.finos", "artifact", "1.0.0", () -> data);

        Set<ProjectVersion> result = context.getProjectDataDependencies(Collections.singletonList(key), false);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.contains(dep));
    }

    @Test
    public void testGetProjectDataDependenciesTransitive()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();

        ProjectVersion transitiveDep = new ProjectVersion("org.finos", "transitive", "1.0.0");
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");
        data.getTransitiveDependenciesReport().setTransitiveDependencies(Arrays.asList(transitiveDep));

        ProjectVersion key = new ProjectVersion("org.finos", "artifact", "1.0.0");
        context.getProjectDataPutIfAbsent("org.finos", "artifact", "1.0.0", () -> data);

        Set<ProjectVersion> result = context.getProjectDataDependencies(Collections.singletonList(key), true);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.contains(transitiveDep));
    }

    @Test
    public void testGetProjectVersionToDependencyMap()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        Assertions.assertNotNull(context.getProjectVersionToDependencyMap());
        Assertions.assertTrue(context.getProjectVersionToDependencyMap().isEmpty());
    }
}
