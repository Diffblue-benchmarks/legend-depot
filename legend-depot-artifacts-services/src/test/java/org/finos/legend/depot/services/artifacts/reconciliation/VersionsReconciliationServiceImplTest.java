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

import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.domain.version.VersionMismatch;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryException;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VersionsReconciliationServiceImplTest
{
    private ArtifactRepository mockRepository;
    private ManageProjectsService mockProjectsService;
    private VersionsReconciliationServiceImpl service;

    @BeforeEach
    public void setUp()
    {
        mockRepository = mock(ArtifactRepository.class);
        mockProjectsService = mock(ManageProjectsService.class);
        service = new VersionsReconciliationServiceImpl(mockRepository, mockProjectsService);
    }

    @Test
    public void canConstructVersionsReconciliationService()
    {
        VersionsReconciliationServiceImpl newService = new VersionsReconciliationServiceImpl(mockRepository, mockProjectsService);
        assertNotNull(newService);
    }

    @Test
    public void canFindVersionsMismatchesWithNoMismatches() throws ArtifactRepositoryException
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Collections.singletonList(version1));

        VersionId versionId = VersionId.parseVersionId("1.0.0");
        when(mockRepository.findVersions("test.group", "test-artifact")).thenReturn(Collections.singletonList(versionId));

        List<VersionMismatch> mismatches = service.findVersionsMismatches();

        assertNotNull(mismatches);
        assertTrue(mismatches.isEmpty());
    }

    @Test
    public void canFindVersionsMismatchesWithVersionsNotInStore() throws ArtifactRepositoryException
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Collections.singletonList(version1));

        VersionId versionId1 = VersionId.parseVersionId("1.0.0");
        VersionId versionId2 = VersionId.parseVersionId("2.0.0");
        when(mockRepository.findVersions("test.group", "test-artifact")).thenReturn(Arrays.asList(versionId1, versionId2));

        List<VersionMismatch> mismatches = service.findVersionsMismatches();

        assertNotNull(mismatches);
        assertEquals(1, mismatches.size());
        VersionMismatch mismatch = mismatches.get(0);
        assertEquals("test-project", mismatch.projectId);
        assertEquals("test.group", mismatch.groupId);
        assertEquals("test-artifact", mismatch.artifactId);
        assertEquals(1, mismatch.versionsNotInStore.size());
        assertTrue(mismatch.versionsNotInStore.contains("2.0.0"));
        assertTrue(mismatch.versionsNotInRepository.isEmpty());
    }

    @Test
    public void canFindVersionsMismatchesWithVersionsNotInRepository() throws ArtifactRepositoryException
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "2.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        StoreProjectVersionData version2 = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Arrays.asList(version1, version2));

        VersionId versionId1 = VersionId.parseVersionId("1.0.0");
        when(mockRepository.findVersions("test.group", "test-artifact")).thenReturn(Collections.singletonList(versionId1));

        List<VersionMismatch> mismatches = service.findVersionsMismatches();

        assertNotNull(mismatches);
        assertEquals(1, mismatches.size());
        VersionMismatch mismatch = mismatches.get(0);
        assertEquals("test-project", mismatch.projectId);
        assertEquals("test.group", mismatch.groupId);
        assertEquals("test-artifact", mismatch.artifactId);
        assertTrue(mismatch.versionsNotInStore.isEmpty());
        assertEquals(1, mismatch.versionsNotInRepository.size());
        assertTrue(mismatch.versionsNotInRepository.contains("2.0.0"));
    }

    @Test
    public void canFindVersionsMismatchesWithBothTypesOfMismatches() throws ArtifactRepositoryException
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "2.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        StoreProjectVersionData version2 = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Arrays.asList(version1, version2));

        VersionId versionId1 = VersionId.parseVersionId("1.0.0");
        VersionId versionId3 = VersionId.parseVersionId("3.0.0");
        when(mockRepository.findVersions("test.group", "test-artifact")).thenReturn(Arrays.asList(versionId1, versionId3));

        List<VersionMismatch> mismatches = service.findVersionsMismatches();

        assertNotNull(mismatches);
        assertEquals(1, mismatches.size());
        VersionMismatch mismatch = mismatches.get(0);
        assertEquals("test-project", mismatch.projectId);
        assertEquals(1, mismatch.versionsNotInStore.size());
        assertTrue(mismatch.versionsNotInStore.contains("3.0.0"));
        assertEquals(1, mismatch.versionsNotInRepository.size());
        assertTrue(mismatch.versionsNotInRepository.contains("2.0.0"));
    }

    @Test
    public void canFindVersionsMismatchesIgnoresSnapshotVersions() throws ArtifactRepositoryException
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        StoreProjectVersionData snapshotVersion = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0-SNAPSHOT");
        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Arrays.asList(version1, snapshotVersion));

        VersionId versionId1 = VersionId.parseVersionId("1.0.0");
        when(mockRepository.findVersions("test.group", "test-artifact")).thenReturn(Collections.singletonList(versionId1));

        List<VersionMismatch> mismatches = service.findVersionsMismatches();

        assertNotNull(mismatches);
        assertTrue(mismatches.isEmpty());
    }

    @Test
    public void canFindVersionsMismatchesCountsEvictedAndExcludedVersions() throws ArtifactRepositoryException
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");

        StoreProjectVersionData evictedVersion = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        evictedVersion.setEvicted(true);

        ProjectVersionData excludedVersionData = new ProjectVersionData();
        excludedVersionData.setExcluded(true);
        StoreProjectVersionData excludedVersion = new StoreProjectVersionData("test.group", "test-artifact", "3.0.0", false, excludedVersionData);

        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Arrays.asList(version1, evictedVersion, excludedVersion));

        VersionId versionId1 = VersionId.parseVersionId("1.0.0");
        VersionId versionId2 = VersionId.parseVersionId("2.0.0");
        VersionId versionId3 = VersionId.parseVersionId("3.0.0");
        when(mockRepository.findVersions("test.group", "test-artifact")).thenReturn(Arrays.asList(versionId1, versionId2, versionId3));

        List<VersionMismatch> mismatches = service.findVersionsMismatches();

        assertNotNull(mismatches);
        assertTrue(mismatches.isEmpty());
    }

    @Test
    public void canFindVersionsMismatchesHandlesRepositoryException() throws ArtifactRepositoryException
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Collections.singletonList(version1));

        when(mockRepository.findVersions("test.group", "test-artifact")).thenThrow(new ArtifactRepositoryException("Repository unavailable"));

        List<VersionMismatch> mismatches = service.findVersionsMismatches();

        assertNotNull(mismatches);
        assertEquals(1, mismatches.size());
        VersionMismatch mismatch = mismatches.get(0);
        assertEquals("test-project", mismatch.projectId);
        assertEquals("test.group", mismatch.groupId);
        assertEquals("test-artifact", mismatch.artifactId);
        assertTrue(mismatch.versionsNotInStore.isEmpty());
        assertTrue(mismatch.versionsNotInRepository.isEmpty());
        assertFalse(mismatch.errors.isEmpty());
    }

    @Test
    public void canFindVersionsMismatchesWithMultipleProjects() throws ArtifactRepositoryException
    {
        StoreProjectData project1 = new StoreProjectData("project-1", "test.group", "artifact-1", "master", "1.0.0");
        StoreProjectData project2 = new StoreProjectData("project-2", "test.group", "artifact-2", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Arrays.asList(project1, project2));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "artifact-1", "1.0.0");
        when(mockProjectsService.find("test.group", "artifact-1")).thenReturn(Collections.singletonList(version1));

        StoreProjectVersionData version2 = new StoreProjectVersionData("test.group", "artifact-2", "1.0.0");
        when(mockProjectsService.find("test.group", "artifact-2")).thenReturn(Collections.singletonList(version2));

        VersionId versionId1 = VersionId.parseVersionId("1.0.0");
        VersionId versionId2 = VersionId.parseVersionId("2.0.0");
        when(mockRepository.findVersions("test.group", "artifact-1")).thenReturn(Collections.singletonList(versionId1));
        when(mockRepository.findVersions("test.group", "artifact-2")).thenReturn(Arrays.asList(versionId1, versionId2));

        List<VersionMismatch> mismatches = service.findVersionsMismatches();

        assertNotNull(mismatches);
        assertEquals(1, mismatches.size());
        assertEquals("project-2", mismatches.get(0).projectId);
    }

    @Test
    public void canSyncLatestProjectVersionsWithNoUpdatesNeeded()
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Collections.singletonList(version1));

        List<StoreProjectData> updatedProjects = service.syncLatestProjectVersions();

        assertNotNull(updatedProjects);
        assertTrue(updatedProjects.isEmpty());
    }

    @Test
    public void canSyncLatestProjectVersionsWithUpdate()
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        StoreProjectVersionData version2 = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Arrays.asList(version1, version2));

        List<StoreProjectData> updatedProjects = service.syncLatestProjectVersions();

        assertNotNull(updatedProjects);
        assertEquals(1, updatedProjects.size());
        assertEquals("test-project", updatedProjects.get(0).getProjectId());
        assertEquals("2.0.0", updatedProjects.get(0).getLatestVersion());
    }

    @Test
    public void canSyncLatestProjectVersionsExcludesEvictedVersions()
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");

        StoreProjectVersionData evictedVersion = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        evictedVersion.setEvicted(true);

        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Arrays.asList(version1, evictedVersion));

        List<StoreProjectData> updatedProjects = service.syncLatestProjectVersions();

        assertNotNull(updatedProjects);
        assertTrue(updatedProjects.isEmpty());
    }

    @Test
    public void canSyncLatestProjectVersionsExcludesExcludedVersions()
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");

        ProjectVersionData excludedVersionData = new ProjectVersionData();
        excludedVersionData.setExcluded(true);
        StoreProjectVersionData excludedVersion = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0", false, excludedVersionData);

        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Arrays.asList(version1, excludedVersion));

        List<StoreProjectData> updatedProjects = service.syncLatestProjectVersions();

        assertNotNull(updatedProjects);
        assertTrue(updatedProjects.isEmpty());
    }

    @Test
    public void canSyncLatestProjectVersionsExcludesDeprecatedVersions()
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");

        ProjectVersionData deprecatedVersionData = new ProjectVersionData();
        deprecatedVersionData.setDeprecated(true);
        StoreProjectVersionData deprecatedVersion = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0", false, deprecatedVersionData);

        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Arrays.asList(version1, deprecatedVersion));

        List<StoreProjectData> updatedProjects = service.syncLatestProjectVersions();

        assertNotNull(updatedProjects);
        assertTrue(updatedProjects.isEmpty());
    }

    @Test
    public void canSyncLatestProjectVersionsExcludesSnapshotVersions()
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        StoreProjectVersionData snapshotVersion = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0-SNAPSHOT");

        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Arrays.asList(version1, snapshotVersion));

        List<StoreProjectData> updatedProjects = service.syncLatestProjectVersions();

        assertNotNull(updatedProjects);
        assertTrue(updatedProjects.isEmpty());
    }

    @Test
    public void canSyncLatestProjectVersionsHandlesEmptyVersionList()
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Collections.emptyList());

        List<StoreProjectData> updatedProjects = service.syncLatestProjectVersions();

        assertNotNull(updatedProjects);
        assertTrue(updatedProjects.isEmpty());
    }

    @Test
    public void canSyncLatestProjectVersionsHandlesException()
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        when(mockProjectsService.find("test.group", "test-artifact")).thenThrow(new RuntimeException("Service unavailable"));

        List<StoreProjectData> updatedProjects = service.syncLatestProjectVersions();

        assertNotNull(updatedProjects);
        assertTrue(updatedProjects.isEmpty());
    }

    @Test
    public void canSyncLatestProjectVersionsWithMultipleProjects()
    {
        StoreProjectData project1 = new StoreProjectData("project-1", "test.group", "artifact-1", "master", "1.0.0");
        StoreProjectData project2 = new StoreProjectData("project-2", "test.group", "artifact-2", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Arrays.asList(project1, project2));

        StoreProjectVersionData version1_1 = new StoreProjectVersionData("test.group", "artifact-1", "1.0.0");
        StoreProjectVersionData version1_2 = new StoreProjectVersionData("test.group", "artifact-1", "2.0.0");
        when(mockProjectsService.find("test.group", "artifact-1")).thenReturn(Arrays.asList(version1_1, version1_2));

        StoreProjectVersionData version2_1 = new StoreProjectVersionData("test.group", "artifact-2", "1.0.0");
        when(mockProjectsService.find("test.group", "artifact-2")).thenReturn(Collections.singletonList(version2_1));

        List<StoreProjectData> updatedProjects = service.syncLatestProjectVersions();

        assertNotNull(updatedProjects);
        assertEquals(1, updatedProjects.size());
        assertEquals("project-1", updatedProjects.get(0).getProjectId());
        assertEquals("2.0.0", updatedProjects.get(0).getLatestVersion());
    }

    @Test
    public void canSyncLatestProjectVersionsSelectsMaxVersion()
    {
        StoreProjectData project = new StoreProjectData("test-project", "test.group", "test-artifact", "master", "1.0.0");
        when(mockProjectsService.getAllProjectCoordinates()).thenReturn(Collections.singletonList(project));

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        StoreProjectVersionData version2 = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        StoreProjectVersionData version3 = new StoreProjectVersionData("test.group", "test-artifact", "3.5.0");
        when(mockProjectsService.find("test.group", "test-artifact")).thenReturn(Arrays.asList(version1, version2, version3));

        List<StoreProjectData> updatedProjects = service.syncLatestProjectVersions();

        assertNotNull(updatedProjects);
        assertEquals(1, updatedProjects.size());
        assertEquals("3.5.0", updatedProjects.get(0).getLatestVersion());
    }
}
