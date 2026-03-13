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
import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.domain.project.dependencies.VersionDependencyReport;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class ProjectDependencyGraphWalkerContextTest
{
    @Test
    public void testGetProjectDataPutIfAbsent()
    {
        ProjectDependencyGraphWalkerContext ctx = new ProjectDependencyGraphWalkerContext();
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");

        StoreProjectVersionData result = ctx.getProjectDataPutIfAbsent("org.finos", "legend-depot", "1.0.0", () -> data);
        Assertions.assertSame(data, result);
    }

    @Test
    public void testGetProjectDataPutIfAbsentReturnsCached()
    {
        ProjectDependencyGraphWalkerContext ctx = new ProjectDependencyGraphWalkerContext();
        StoreProjectVersionData data1 = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        StoreProjectVersionData data2 = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");

        ctx.getProjectDataPutIfAbsent("org.finos", "legend-depot", "1.0.0", () -> data1);
        StoreProjectVersionData result = ctx.getProjectDataPutIfAbsent("org.finos", "legend-depot", "1.0.0", () -> data2);
        Assertions.assertSame(data1, result);
    }

    @Test
    public void testGetProjectData()
    {
        ProjectDependencyGraphWalkerContext ctx = new ProjectDependencyGraphWalkerContext();
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        ctx.getProjectDataPutIfAbsent("org.finos", "legend-depot", "1.0.0", () -> data);

        Assertions.assertSame(data, ctx.getProjectData("org.finos", "legend-depot", "1.0.0"));
    }

    @Test
    public void testGetProjectDataNotFound()
    {
        ProjectDependencyGraphWalkerContext ctx = new ProjectDependencyGraphWalkerContext();
        Assertions.assertNull(ctx.getProjectData("org.finos", "missing", "1.0.0"));
    }

    @Test
    public void testAddVersionToProject()
    {
        ProjectDependencyGraphWalkerContext ctx = new ProjectDependencyGraphWalkerContext();
        ProjectVersion v1 = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        ProjectVersion v2 = new ProjectVersion("org.finos", "legend-depot", "2.0.0");

        ctx.addVersionToProject("org.finos", "legend-depot", v1);
        ctx.addVersionToProject("org.finos", "legend-depot", v2);

        Assertions.assertEquals(1, ctx.getProjectToVersions().size());
        Set<ProjectVersion> versions = ctx.getProjectToVersions().values().iterator().next();
        Assertions.assertEquals(2, versions.size());
    }

    @Test
    public void testGetProjectDataDependenciesDirect()
    {
        ProjectDependencyGraphWalkerContext ctx = new ProjectDependencyGraphWalkerContext();
        ProjectVersion pv = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        ProjectVersion dep = new ProjectVersion("org.finos", "legend-sdlc", "2.0.0");
        data.getVersionData().addDependency(dep);
        data.setTransitiveDependenciesReport(new VersionDependencyReport(Collections.emptyList(), true));
        ctx.getProjectDataPutIfAbsent("org.finos", "legend-depot", "1.0.0", () -> data);

        Set<ProjectVersion> deps = ctx.getProjectDataDependencies(Arrays.asList(pv), false);
        Assertions.assertEquals(1, deps.size());
        Assertions.assertTrue(deps.contains(dep));
    }

    @Test
    public void testGetProjectDataDependenciesTransitive()
    {
        ProjectDependencyGraphWalkerContext ctx = new ProjectDependencyGraphWalkerContext();
        ProjectVersion pv = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        ProjectVersion transDep = new ProjectVersion("org.finos", "legend-engine", "3.0.0");
        data.setTransitiveDependenciesReport(new VersionDependencyReport(Arrays.asList(transDep), true));
        ctx.getProjectDataPutIfAbsent("org.finos", "legend-depot", "1.0.0", () -> data);

        Set<ProjectVersion> deps = ctx.getProjectDataDependencies(Arrays.asList(pv), true);
        Assertions.assertEquals(1, deps.size());
        Assertions.assertTrue(deps.contains(transDep));
    }

    @Test
    public void testProjectVersionToDependencyMap()
    {
        ProjectDependencyGraphWalkerContext ctx = new ProjectDependencyGraphWalkerContext();
        ProjectVersion pv = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        ProjectVersion dep = new ProjectVersion("org.finos", "legend-sdlc", "2.0.0");
        ctx.getProjectVersionToDependencyMap().putIfAbsent(pv, Arrays.asList(dep));

        Assertions.assertEquals(1, ctx.getProjectVersionToDependencyMap().get(pv).size());
    }
}
