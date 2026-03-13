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

package org.finos.legend.depot.services.artifacts.reconciliation;

import org.finos.legend.depot.domain.version.VersionMismatch;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryException;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

public class VersionsReconciliationServiceImplTest
{
    private ArtifactRepository mockRepository;
    private ManageProjectsService mockProjects;
    private VersionsReconciliationServiceImpl service;

    @BeforeEach
    public void setup()
    {
        mockRepository = Mockito.mock(ArtifactRepository.class);
        mockProjects = Mockito.mock(ManageProjectsService.class);
        service = new VersionsReconciliationServiceImpl(mockRepository, mockProjects);
    }

    @Test
    public void testFindVersionsMismatchesNoProjects()
    {
        when(mockProjects.getAllProjectCoordinates()).thenReturn(Collections.emptyList());

        List<VersionMismatch> result = service.findVersionsMismatches();
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testFindVersionsMismatchesAllInSync() throws ArtifactRepositoryException
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        when(mockProjects.getAllProjectCoordinates()).thenReturn(Arrays.asList(project));

        StoreProjectVersionData v1 = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        when(mockProjects.find("org.finos", "legend-depot")).thenReturn(Arrays.asList(v1));
        when(mockRepository.findVersions("org.finos", "legend-depot")).thenReturn(Arrays.asList(VersionId.parseVersionId("1.0.0")));

        List<VersionMismatch> result = service.findVersionsMismatches();
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testFindVersionsMismatchesMissingInStore() throws ArtifactRepositoryException
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        when(mockProjects.getAllProjectCoordinates()).thenReturn(Arrays.asList(project));
        when(mockProjects.find("org.finos", "legend-depot")).thenReturn(Collections.emptyList());
        when(mockRepository.findVersions("org.finos", "legend-depot")).thenReturn(Arrays.asList(VersionId.parseVersionId("1.0.0")));

        List<VersionMismatch> result = service.findVersionsMismatches();
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0).versionsNotInStore.contains("1.0.0"));
    }

    @Test
    public void testFindVersionsMismatchesMissingInRepo() throws ArtifactRepositoryException
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        when(mockProjects.getAllProjectCoordinates()).thenReturn(Arrays.asList(project));

        StoreProjectVersionData v1 = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        when(mockProjects.find("org.finos", "legend-depot")).thenReturn(Arrays.asList(v1));
        when(mockRepository.findVersions("org.finos", "legend-depot")).thenReturn(Collections.emptyList());

        List<VersionMismatch> result = service.findVersionsMismatches();
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0).versionsNotInRepository.contains("1.0.0"));
    }

    @Test
    public void testFindVersionsMismatchesHandlesExceptions()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        when(mockProjects.getAllProjectCoordinates()).thenReturn(Arrays.asList(project));
        when(mockProjects.find("org.finos", "legend-depot")).thenThrow(new RuntimeException("db error"));

        List<VersionMismatch> result = service.findVersionsMismatches();
        Assertions.assertEquals(1, result.size());
        Assertions.assertFalse(result.get(0).errors.isEmpty());
    }

    @Test
    public void testSyncLatestProjectVersionsNoProjects()
    {
        when(mockProjects.getAllProjectCoordinates()).thenReturn(Collections.emptyList());

        List<StoreProjectData> result = service.syncLatestProjectVersions();
        Assertions.assertTrue(result.isEmpty());
    }
}
