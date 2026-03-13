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

package org.finos.legend.depot.server.resources.dependencies;

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.Property;
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyReport;
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyWithPlatformVersions;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DependenciesResourceTest
{
    private ProjectsService projectsService;
    private DependenciesResource resource;

    @BeforeEach
    public void setup()
    {
        projectsService = mock(ProjectsService.class);
        resource = new DependenciesResource(projectsService);
    }

    @Test
    public void testConstructor()
    {
        ProjectsService mockService = mock(ProjectsService.class);
        DependenciesResource testResource = new DependenciesResource(mockService);
        assertNotNull(testResource);
    }

    @Test
    public void canGetProjectDependencies()
    {
        Set<ProjectVersion> dependencies = new HashSet<>();
        dependencies.add(new ProjectVersion("com.example", "artifact1", "1.0.0"));

        when(projectsService.getDependencies(anyString(), anyString(), anyString(), anyBoolean()))
            .thenReturn(dependencies);

        Request request = mock(Request.class);
        Response response = resource.getProjectDependencies("com.test", "test-artifact", "1.0.0", false, request);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(projectsService).getDependencies("com.test", "test-artifact", "1.0.0", false);
    }

    @Test
    public void canGetProjectDependenciesTransitive()
    {
        Set<ProjectVersion> dependencies = new HashSet<>();
        dependencies.add(new ProjectVersion("com.example", "artifact1", "1.0.0"));
        dependencies.add(new ProjectVersion("com.example", "artifact2", "2.0.0"));

        when(projectsService.getDependencies(anyString(), anyString(), anyString(), anyBoolean()))
            .thenReturn(dependencies);

        Request request = mock(Request.class);
        Response response = resource.getProjectDependencies("com.test", "test-artifact", "1.0.0", true, request);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(projectsService).getDependencies("com.test", "test-artifact", "1.0.0", true);
    }

    @Test
    public void canAnalyzeDependencyTree()
    {
        List<ProjectVersion> projectVersions = new ArrayList<>();
        projectVersions.add(new ProjectVersion("com.example", "artifact1", "1.0.0"));

        ProjectDependencyReport report = mock(ProjectDependencyReport.class);
        when(projectsService.getProjectDependencyReport(anyList())).thenReturn(report);

        Response response = resource.analyzeDependencyTree(projectVersions);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(projectsService).getProjectDependencyReport(projectVersions);
    }

    @Test
    public void canAnalyzeDependencyTreeWithEmptyList()
    {
        List<ProjectVersion> projectVersions = new ArrayList<>();

        ProjectDependencyReport report = mock(ProjectDependencyReport.class);
        when(projectsService.getProjectDependencyReport(anyList())).thenReturn(report);

        Response response = resource.analyzeDependencyTree(projectVersions);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(projectsService).getProjectDependencyReport(projectVersions);
    }

    @Test
    public void canGetDependantProjects()
    {
        List<ProjectDependencyWithPlatformVersions> dependants = new ArrayList<>();
        ProjectDependencyWithPlatformVersions dep = new ProjectDependencyWithPlatformVersions(
            "com.test", "test-artifact", "2.0.0",
            new ProjectVersion("com.example", "artifact1", "1.0.0"),
            new ArrayList<>()
        );
        dependants.add(dep);

        when(projectsService.getDependantProjects(anyString(), anyString(), anyString(), anyBoolean()))
            .thenReturn(dependants);

        Response response = resource.getDependantProjects("com.example", "artifact1", "1.0.0", false);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(projectsService).getDependantProjects("com.example", "artifact1", "1.0.0", false);
    }

    @Test
    public void canGetDependantProjectsLatestOnly()
    {
        List<ProjectDependencyWithPlatformVersions> dependants = new ArrayList<>();
        ProjectDependencyWithPlatformVersions dep = new ProjectDependencyWithPlatformVersions(
            "com.test", "test-artifact", "2.0.0",
            new ProjectVersion("com.example", "artifact1", "1.0.0"),
            new ArrayList<>()
        );
        dependants.add(dep);

        when(projectsService.getDependantProjects(anyString(), anyString(), anyString(), anyBoolean()))
            .thenReturn(dependants);

        Response response = resource.getDependantProjects("com.example", "artifact1", "1.0.0", true);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(projectsService).getDependantProjects("com.example", "artifact1", "1.0.0", true);
    }

    @Test
    public void canGetDependantProjectsWithEmptyResult()
    {
        List<ProjectDependencyWithPlatformVersions> dependants = new ArrayList<>();

        when(projectsService.getDependantProjects(anyString(), anyString(), anyString(), anyBoolean()))
            .thenReturn(dependants);

        Response response = resource.getDependantProjects("com.example", "artifact1", "1.0.0", false);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(projectsService).getDependantProjects("com.example", "artifact1", "1.0.0", false);
    }

    @Test
    public void canGetDependantProjectsWithPlatformVersions()
    {
        List<ProjectDependencyWithPlatformVersions> dependants = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("platform", "java-11"));

        ProjectDependencyWithPlatformVersions dep = new ProjectDependencyWithPlatformVersions(
            "com.test", "test-artifact", "2.0.0",
            new ProjectVersion("com.example", "artifact1", "1.0.0"),
            properties
        );
        dependants.add(dep);

        when(projectsService.getDependantProjects(anyString(), anyString(), anyString(), anyBoolean()))
            .thenReturn(dependants);

        Response response = resource.getDependantProjects("com.example", "artifact1", "1.0.0", false);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(projectsService).getDependantProjects("com.example", "artifact1", "1.0.0", false);
    }
}
