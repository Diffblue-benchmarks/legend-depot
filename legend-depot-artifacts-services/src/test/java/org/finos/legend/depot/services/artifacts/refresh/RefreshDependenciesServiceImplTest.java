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

package org.finos.legend.depot.services.artifacts.refresh;

import org.finos.legend.depot.domain.artifacts.repository.ArtifactDependency;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.services.dependencies.DependencyUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

public class RefreshDependenciesServiceImplTest
{
    private ManageProjectsService mockProjects;
    private ArtifactRepository mockRepository;
    private RefreshDependenciesServiceImpl service;

    @BeforeEach
    public void setup()
    {
        mockProjects = Mockito.mock(ManageProjectsService.class);
        mockRepository = Mockito.mock(ArtifactRepository.class);
        service = new RefreshDependenciesServiceImpl(mockProjects, mockRepository, new DependencyUtil());
    }

    @Test
    public void testRetrieveDependenciesFromRepository()
    {
        Set<ArtifactDependency> deps = new HashSet<>();
        deps.add(new ArtifactDependency("org.finos", "legend-sdlc", "1.0.0"));
        deps.add(new ArtifactDependency("org.finos", "legend-engine", "2.0.0"));
        when(mockRepository.findDependencies("org.finos", "legend-depot", "1.0.0")).thenReturn(deps);

        List<ProjectVersion> result = service.retrieveDependenciesFromRepository("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testRetrieveDependenciesFromRepositoryEmpty()
    {
        when(mockRepository.findDependencies("org.finos", "legend-depot", "1.0.0")).thenReturn(Collections.emptySet());

        List<ProjectVersion> result = service.retrieveDependenciesFromRepository("org.finos", "legend-depot", "1.0.0");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testValidateDependenciesNoErrors()
    {
        List<ProjectVersion> deps = Arrays.asList(new ProjectVersion("org.finos", "legend-sdlc", "1.0.0"));
        List<String> errors = service.validateDependencies(deps, "2.0.0");
        Assertions.assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateDependenciesSnapshotInRelease()
    {
        List<ProjectVersion> deps = Arrays.asList(new ProjectVersion("org.finos", "legend-sdlc", "master-SNAPSHOT"));
        List<String> errors = service.validateDependencies(deps, "2.0.0");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertTrue(errors.get(0).contains("Snapshot dependency"));
    }

    @Test
    public void testValidateDependenciesSnapshotInSnapshotIsOk()
    {
        List<ProjectVersion> deps = Arrays.asList(new ProjectVersion("org.finos", "legend-sdlc", "master-SNAPSHOT"));
        List<String> errors = service.validateDependencies(deps, "master-SNAPSHOT");
        Assertions.assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateDependenciesMultipleErrors()
    {
        List<ProjectVersion> deps = Arrays.asList(
                new ProjectVersion("org.finos", "legend-sdlc", "master-SNAPSHOT"),
                new ProjectVersion("org.finos", "legend-engine", "feature-SNAPSHOT"));
        List<String> errors = service.validateDependencies(deps, "1.0.0");
        Assertions.assertEquals(2, errors.size());
    }
}
