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

import org.finos.legend.depot.services.api.entities.ManageEntitiesService;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.services.entities.ManageEntitiesServiceImpl;
import org.finos.legend.depot.services.generations.impl.ManageFileGenerationsServiceImpl;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.store.api.admin.artifacts.ArtifactsFilesStore;
import org.finos.legend.depot.store.api.entities.UpdateEntities;
import org.finos.legend.depot.store.api.generations.UpdateFileGenerations;
import org.finos.legend.depot.store.api.projects.UpdateProjects;
import org.finos.legend.depot.store.api.projects.UpdateProjectsVersions;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryException;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.finos.legend.depot.services.artifacts.handlers.entities.EntitiesHandlerImpl;
import org.finos.legend.depot.services.artifacts.handlers.entities.EntityProvider;
import org.finos.legend.depot.services.artifacts.handlers.generations.FileGenerationHandlerImpl;
import org.finos.legend.depot.services.artifacts.handlers.generations.FileGenerationsProvider;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.artifacts.ArtifactsFilesMongo;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.artifacts.handlers.ProjectArtifactHandlerFactory;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.EntityArtifactsProvider;
import org.finos.legend.depot.services.api.artifacts.handlers.generations.FileGenerationsArtifactsProvider;
import org.finos.legend.depot.store.mongo.notifications.queue.NotificationsQueueMongo;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.finos.legend.depot.domain.version.VersionValidator.BRANCH_SNAPSHOT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestArtifactsRefreshServiceWithMocks extends TestStoreMongo
{
    public static final String TEST_GROUP_ID = "examples.metadata";
    public static final String TEST_ARTIFACT_ID = "test";
    public static final String TEST_DEPENDENCIES_ARTIFACT_ID = "test-dependencies";
    public static final String PROJECT_A = "PROD-1";
    public static final String PROJECT_B = "PROD-2";

    protected ArtifactsFilesStore artifacts = new ArtifactsFilesMongo(mongoProvider);
    protected List<String> properties = Arrays.asList("[a-zA-Z0-9]+.version");

    protected EntityArtifactsProvider entitiesProvider = new EntityProvider();
    protected FileGenerationsArtifactsProvider fileGenerationsProvider = new FileGenerationsProvider();

    protected ArtifactRepository repository = mock(ArtifactRepository.class);
    protected UpdateProjects mongoProjects = mock(UpdateProjects.class);
    protected UpdateProjectsVersions mongoProjectsVersions = mock(UpdateProjectsVersions.class);
    private final QueryMetricsRegistry metrics = mock(QueryMetricsRegistry.class);
    protected Queue queue = new NotificationsQueueMongo(mongoProvider);
    protected ManageProjectsService projectsService = new ManageProjectsServiceImpl(mongoProjectsVersions,mongoProjects, metrics, queue, new ProjectsConfiguration("master"));
    protected UpdateEntities mongoEntities = mock(UpdateEntities.class);
    protected ManageEntitiesService entitiesService = new ManageEntitiesServiceImpl(mongoEntities,projectsService);
    protected UpdateFileGenerations mongoGenerations = mock(UpdateFileGenerations.class);

    protected ArtifactsRefreshServiceImpl artifactsRefreshService = new ArtifactsRefreshServiceImpl(projectsService, repository,queue);


    @BeforeEach
    public void setUpData() throws ArtifactRepositoryException
    {
        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.ENTITIES, new EntitiesHandlerImpl(entitiesService, entitiesProvider));
        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.FILE_GENERATIONS, new FileGenerationHandlerImpl(repository, fileGenerationsProvider, new ManageFileGenerationsServiceImpl(mongoGenerations, projectsService)));

        List<StoreProjectData> projects = Arrays.asList(new StoreProjectData(PROJECT_B, TEST_GROUP_ID, TEST_DEPENDENCIES_ARTIFACT_ID),
                new StoreProjectData(PROJECT_A, TEST_GROUP_ID, TEST_ARTIFACT_ID),
                new StoreProjectData("C", TEST_GROUP_ID, "C"));
        when(mongoProjects.getAll()).thenReturn(projects);
        when(mongoProjects.find(TEST_GROUP_ID,TEST_ARTIFACT_ID)).thenReturn(Optional.of(new StoreProjectData(PROJECT_A, TEST_GROUP_ID, TEST_ARTIFACT_ID)));
        when(mongoProjects.find(TEST_GROUP_ID,TEST_DEPENDENCIES_ARTIFACT_ID)).thenReturn(Optional.of(new StoreProjectData(PROJECT_B,TEST_GROUP_ID, TEST_DEPENDENCIES_ARTIFACT_ID)));
        when(mongoProjectsVersions.find(TEST_GROUP_ID, TEST_ARTIFACT_ID,BRANCH_SNAPSHOT("master"))).thenReturn(Optional.of(new StoreProjectVersionData(TEST_GROUP_ID, TEST_ARTIFACT_ID, BRANCH_SNAPSHOT("master"))));
        when(mongoProjectsVersions.find(TEST_GROUP_ID, TEST_DEPENDENCIES_ARTIFACT_ID,BRANCH_SNAPSHOT("master"))).thenReturn(Optional.of(new StoreProjectVersionData(TEST_GROUP_ID, TEST_DEPENDENCIES_ARTIFACT_ID, BRANCH_SNAPSHOT("master"))));
        when(repository.findVersions(TEST_GROUP_ID,TEST_ARTIFACT_ID)).thenReturn(Arrays.asList(VersionId.parseVersionId("1.0.0"), VersionId.parseVersionId("2.0.0")));
        when(repository.findVersions(TEST_GROUP_ID,TEST_DEPENDENCIES_ARTIFACT_ID)).thenReturn(Arrays.asList(VersionId.parseVersionId("1.0.0")));
        when(repository.findVersions(TEST_GROUP_ID,"c")).thenReturn(Arrays.asList(VersionId.parseVersionId("1.0.0")));
    }

    @AfterEach
    public void afterTest()
    {
        Assertions.assertTrue(queue.getAll().isEmpty(), "should not have events in queue");
    }


    @Test
    public void canCalculateCandidateVersionsToUpdate()
    {
        List<VersionId> repoVersions = Arrays.asList(VersionId.parseVersionId("1.0.0"),VersionId.parseVersionId("2.0.0"));
        List<String> versions = Arrays.asList("1.0.0");
        List<VersionId> candidates = artifactsRefreshService.calculateCandidateVersions(repoVersions,versions);
        Assertions.assertEquals("2.0.0",candidates.get(0).toVersionIdString());
    }

    @Test
    public void canInstantiateServiceWithDependencies()
    {
        Assertions.assertNotNull(artifactsRefreshService);
    }

    @Test
    public void canRefreshAllVersionsForAllProjects() throws ArtifactRepositoryException
    {
        when(mongoProjects.find(TEST_GROUP_ID, "C")).thenReturn(Optional.of(new StoreProjectData("C", TEST_GROUP_ID, "C")));
        when(repository.areValidCoordinates(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(true);
        when(repository.areValidCoordinates(TEST_GROUP_ID, TEST_DEPENDENCIES_ARTIFACT_ID)).thenReturn(true);
        when(repository.areValidCoordinates(TEST_GROUP_ID, "C")).thenReturn(true);

        org.finos.legend.depot.domain.notifications.MetadataNotificationResponse response = artifactsRefreshService.refreshAllVersionsForAllProjects(true, true, true, "parent-1");

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());

        queue.deleteAll();
    }

    @Test
    public void canRefreshDefaultSnapshotsForAllProjects()
    {
        org.finos.legend.depot.domain.notifications.MetadataNotificationResponse response = artifactsRefreshService.refreshDefaultSnapshotsForAllProjects(true, true, "parent-2");

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());

        queue.deleteAll();
    }

    @Test
    public void canRefreshAllVersionsForProjectById()
    {
        when(repository.areValidCoordinates(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(true);
        when(mongoProjectsVersions.find(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(Arrays.asList());

        org.finos.legend.depot.domain.notifications.MetadataNotificationResponse response = artifactsRefreshService.refreshAllVersionsForProject(TEST_GROUP_ID, TEST_ARTIFACT_ID, true, true, true, "parent-3");

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());

        queue.deleteAll();
    }

    @Test
    public void canRefreshVersionForProject()
    {
        org.finos.legend.depot.domain.notifications.MetadataNotificationResponse response = artifactsRefreshService.refreshVersionForProject(TEST_GROUP_ID, TEST_ARTIFACT_ID, "1.0.0", true, true, "parent-4");

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());

        queue.deleteAll();
    }

    @Test
    public void canHandleInvalidCoordinatesWhenRefreshingAllVersions()
    {
        when(repository.areValidCoordinates(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(false);

        StoreProjectData projectData = new StoreProjectData(PROJECT_A, TEST_GROUP_ID, TEST_ARTIFACT_ID);
        org.finos.legend.depot.domain.notifications.MetadataNotificationResponse response = artifactsRefreshService.refreshAllVersionsForProject(TEST_GROUP_ID, TEST_ARTIFACT_ID, true, false, false, "parent-5");

        Assertions.assertNotNull(response);

        queue.deleteAll();
    }

    @Test
    public void canHandleRepositoryExceptionWhenFindingVersions() throws ArtifactRepositoryException
    {
        when(repository.areValidCoordinates(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(true);
        when(repository.findVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenThrow(new ArtifactRepositoryException("Repository error"));

        org.finos.legend.depot.domain.notifications.MetadataNotificationResponse response = artifactsRefreshService.refreshAllVersionsForProject(TEST_GROUP_ID, TEST_ARTIFACT_ID, true, true, false, "parent-6");

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.hasErrors());

        queue.deleteAll();
    }

    @Test
    public void canRefreshAllVersionsWhenStoreVersionsExist() throws ArtifactRepositoryException
    {
        when(repository.areValidCoordinates(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(true);
        when(mongoProjectsVersions.find(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(Arrays.asList(
            new StoreProjectVersionData(TEST_GROUP_ID, TEST_ARTIFACT_ID, "1.0.0")
        ));

        org.finos.legend.depot.domain.notifications.MetadataNotificationResponse response = artifactsRefreshService.refreshAllVersionsForProject(TEST_GROUP_ID, TEST_ARTIFACT_ID, true, false, false, "parent-7");

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());

        queue.deleteAll();
    }

    @Test
    public void canHandleMissingProjectCoordinates()
    {
        when(mongoProjects.find("unknown.group", "unknown-artifact")).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () ->
            artifactsRefreshService.refreshVersionForProject("unknown.group", "unknown-artifact", "1.0.0", true, true, "parent-8")
        );
    }

    @Test
    public void canRefreshWithEmptyRepoVersions() throws ArtifactRepositoryException
    {
        when(repository.areValidCoordinates(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(true);
        when(repository.findVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(Arrays.asList());

        org.finos.legend.depot.domain.notifications.MetadataNotificationResponse response = artifactsRefreshService.refreshAllVersionsForProject(TEST_GROUP_ID, TEST_ARTIFACT_ID, true, true, false, "parent-9");

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());

        queue.deleteAll();
    }

    @Test
    public void canRefreshWithNullRepoVersions() throws ArtifactRepositoryException
    {
        when(repository.areValidCoordinates(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(true);
        when(repository.findVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(null);

        org.finos.legend.depot.domain.notifications.MetadataNotificationResponse response = artifactsRefreshService.refreshAllVersionsForProject(TEST_GROUP_ID, TEST_ARTIFACT_ID, true, true, false, "parent-10");

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());

        queue.deleteAll();
    }

    @Test
    public void canHandleEvictedSnapshotVersion()
    {
        StoreProjectVersionData evictedVersion = new StoreProjectVersionData(TEST_GROUP_ID, TEST_ARTIFACT_ID, BRANCH_SNAPSHOT("master"));
        evictedVersion.setEvicted(true);
        when(mongoProjectsVersions.find(TEST_GROUP_ID, TEST_ARTIFACT_ID, BRANCH_SNAPSHOT("master"))).thenReturn(Optional.of(evictedVersion));

        org.finos.legend.depot.domain.notifications.MetadataNotificationResponse response = artifactsRefreshService.refreshAllVersionsForProject(TEST_GROUP_ID, TEST_ARTIFACT_ID, true, true, true, "parent-11");

        Assertions.assertNotNull(response);

        queue.deleteAll();
    }

    @Test
    public void canRefreshWhenSnapshotVersionNotPresent()
    {
        when(mongoProjectsVersions.find(TEST_GROUP_ID, TEST_ARTIFACT_ID, BRANCH_SNAPSHOT("master"))).thenReturn(Optional.empty());

        org.finos.legend.depot.domain.notifications.MetadataNotificationResponse response = artifactsRefreshService.refreshAllVersionsForProject(TEST_GROUP_ID, TEST_ARTIFACT_ID, true, true, true, "parent-12");

        Assertions.assertNotNull(response);

        queue.deleteAll();
    }
}
