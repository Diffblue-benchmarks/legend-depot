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

import org.eclipse.collections.api.block.function.Function0;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.domain.project.dependencies.VersionDependencyReport;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProjectDependencyGraphWalkerContextTest
{
    @Test
    public void canCreateContext()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        Assertions.assertNotNull(context);
        Assertions.assertNotNull(context.getProjectToVersions());
        Assertions.assertNotNull(context.getProjectVersionToDependencyMap());
    }

    @Test
    public void canGetProjectToVersions()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        Map<ProjectDependencyGraphWalkerContext.DependencyProject, Set<ProjectVersion>> projectToVersions = context.getProjectToVersions();
        Assertions.assertNotNull(projectToVersions);
        Assertions.assertTrue(projectToVersions.isEmpty());
    }

    @Test
    public void canAddVersionToProject()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        ProjectVersion version = new ProjectVersion("org.example", "test-artifact", "1.0.0");
        context.addVersionToProject("org.example", "test-artifact", version);
        Map<ProjectDependencyGraphWalkerContext.DependencyProject, Set<ProjectVersion>> projectToVersions = context.getProjectToVersions();
        Assertions.assertEquals(1, projectToVersions.size());
    }

    @Test
    public void canGetProjectVersionToDependencyMap()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        Assertions.assertNotNull(context.getProjectVersionToDependencyMap());
        Assertions.assertTrue(context.getProjectVersionToDependencyMap().isEmpty());
    }

    @Test
    public void canGetProjectDataPutIfAbsent()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        StoreProjectVersionData projectData = new StoreProjectVersionData("org.example", "test-artifact", "1.0.0");
        Function0<StoreProjectVersionData> supplier = () -> projectData;
        StoreProjectVersionData result = context.getProjectDataPutIfAbsent("org.example", "test-artifact", "1.0.0", supplier);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("org.example", result.getGroupId());
        Assertions.assertEquals("test-artifact", result.getArtifactId());
        Assertions.assertEquals("1.0.0", result.getVersionId());
    }

    @Test
    public void canGetProjectData()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        StoreProjectVersionData projectData = new StoreProjectVersionData("org.example", "test-artifact", "1.0.0");
        Function0<StoreProjectVersionData> supplier = () -> projectData;
        context.getProjectDataPutIfAbsent("org.example", "test-artifact", "1.0.0", supplier);
        StoreProjectVersionData result = context.getProjectData("org.example", "test-artifact", "1.0.0");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("org.example", result.getGroupId());
    }

    @Test
    public void canGetProjectDataDependenciesNonTransitive()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        ProjectVersion pv1 = new ProjectVersion("org.example", "test-artifact", "1.0.0");
        ProjectVersion dep1 = new ProjectVersion("org.example", "dependency1", "1.0.0");
        ProjectVersionData versionData = new ProjectVersionData();
        versionData.setDependencies(Arrays.asList(dep1));
        StoreProjectVersionData projectData = new StoreProjectVersionData("org.example", "test-artifact", "1.0.0", false, versionData);
        Function0<StoreProjectVersionData> supplier = () -> projectData;
        context.getProjectDataPutIfAbsent("org.example", "test-artifact", "1.0.0", supplier);
        List<ProjectVersion> projectVersions = new ArrayList<>();
        projectVersions.add(pv1);
        Set<ProjectVersion> dependencies = context.getProjectDataDependencies(projectVersions, false);
        Assertions.assertNotNull(dependencies);
        Assertions.assertEquals(1, dependencies.size());
    }

    @Test
    public void canGetProjectDataDependenciesTransitive()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        ProjectVersion pv1 = new ProjectVersion("org.example", "test-artifact", "1.0.0");
        ProjectVersion dep1 = new ProjectVersion("org.example", "dependency1", "1.0.0");
        ProjectVersion dep2 = new ProjectVersion("org.example", "dependency2", "1.0.0");
        VersionDependencyReport transitiveDeps = new VersionDependencyReport();
        transitiveDeps.setTransitiveDependencies(Arrays.asList(dep1, dep2));
        StoreProjectVersionData projectData = new StoreProjectVersionData("org.example", "test-artifact", "1.0.0");
        projectData.setTransitiveDependenciesReport(transitiveDeps);
        Function0<StoreProjectVersionData> supplier = () -> projectData;
        context.getProjectDataPutIfAbsent("org.example", "test-artifact", "1.0.0", supplier);
        List<ProjectVersion> projectVersions = new ArrayList<>();
        projectVersions.add(pv1);
        Set<ProjectVersion> dependencies = context.getProjectDataDependencies(projectVersions, true);
        Assertions.assertNotNull(dependencies);
        Assertions.assertEquals(2, dependencies.size());
    }
}
