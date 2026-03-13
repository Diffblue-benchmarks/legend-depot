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

package org.finos.legend.depot.services.artifacts.handlers.generations;

import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.finos.legend.depot.domain.notifications.MetadataNotificationResponse;
import org.finos.legend.depot.services.api.artifacts.handlers.generations.FileGenerationsArtifactsProvider;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.generations.ManageFileGenerationsService;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.artifacts.repository.maven.TestMavenArtifactsRepository;
import org.finos.legend.depot.services.generations.impl.ManageFileGenerationsServiceImpl;
import org.finos.legend.depot.services.projects.ProjectsServiceImpl;
import org.finos.legend.depot.store.api.projects.UpdateProjects;
import org.finos.legend.depot.store.api.projects.UpdateProjectsVersions;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.depot.store.model.generations.StoredFileGeneration;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.generations.FileGenerationsMongo;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.finos.legend.depot.domain.generation.DepotGeneration.GENERATION_CONFIGURATION;
import static org.finos.legend.depot.domain.version.VersionValidator.BRANCH_SNAPSHOT;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TestFileGenerationHandlerImpl extends TestStoreMongo
{
    public static final String PRODUCT_A = "PROD-23992";
    protected static final String TEST_GROUP_ID = "examples.metadata";
    public static final String TEST_ARTIFACT_ID = "test";
    public static final String TEST_VERSION = "2.0.0";

    private final ArtifactRepository repository = new TestMavenArtifactsRepository();
    private final FileGenerationsArtifactsProvider provider = mock(FileGenerationsArtifactsProvider.class);
    private final UpdateProjects projects = mock(UpdateProjects.class);
    private final UpdateProjectsVersions projectsVersions = mock(UpdateProjectsVersions.class);
    private final QueryMetricsRegistry metrics = mock(QueryMetricsRegistry.class);
    private final Queue queue = mock(Queue.class);
    private final ManageFileGenerationsService generations = new ManageFileGenerationsServiceImpl(new FileGenerationsMongo(mongoProvider), new ProjectsServiceImpl(projectsVersions, projects, metrics, queue, new ProjectsConfiguration("master")));
    private FileGenerationHandlerImpl handler;

    @BeforeEach
    public void setup()
    {
        when(projects.find(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(Optional.of(new StoreProjectData(PRODUCT_A, TEST_GROUP_ID, TEST_ARTIFACT_ID)));
        when(projectsVersions.find(TEST_GROUP_ID, TEST_ARTIFACT_ID, BRANCH_SNAPSHOT("master"))).thenReturn(Optional.of(new StoreProjectVersionData(TEST_GROUP_ID, TEST_ARTIFACT_ID, BRANCH_SNAPSHOT("master"))));
        when(projectsVersions.find(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION)).thenReturn(Optional.of(new StoreProjectVersionData(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION)));
        handler = spy(new FileGenerationHandlerImpl(repository, provider, generations));
    }

    @Test
    public void canConstructHandler()
    {
        FileGenerationHandlerImpl newHandler = new FileGenerationHandlerImpl(repository, provider, generations);
        Assertions.assertNotNull(newHandler);
    }

    @Test
    public void canRefreshProjectVersionArtifactsWithFileGenerationEntities()
    {
        List<File> files = repository.findFiles(ArtifactType.FILE_GENERATIONS, TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        Map<String, Object> entityContent = new HashMap<>();
        entityContent.put("type", "avrogen");
        entityContent.put("generationOutputPath", "examples/metadata/test");

        List<Entity> projectEntities = new ArrayList<>(Arrays.asList(
            new EntityDefinition("examples::metadata::TestFileGeneration", GENERATION_CONFIGURATION, entityContent)
        ));

        List<DepotGeneration> generatedFiles = new ArrayList<>(Arrays.asList(
            new DepotGeneration("/examples/metadata/test/TestFile.avro", "content1")
        ));

        when(provider.extractArtifacts(files)).thenReturn(generatedFiles);
        when(provider.getType()).thenReturn(ArtifactType.FILE_GENERATIONS);
        doReturn(projectEntities).when(handler).getAllNonVersionedEntities(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());
        List<StoredFileGeneration> storedGenerations = generations.getAll();
        Assertions.assertEquals(1, storedGenerations.size());
    }

    @Test
    public void canRefreshProjectVersionArtifactsForSnapshotVersion()
    {
        String snapshotVersion = BRANCH_SNAPSHOT("master");
        List<File> files = repository.findFiles(ArtifactType.FILE_GENERATIONS, TEST_GROUP_ID, TEST_ARTIFACT_ID, snapshotVersion);

        List<Entity> projectEntities = new ArrayList<>();
        List<DepotGeneration> generatedFiles = new ArrayList<>();

        when(provider.extractArtifacts(files)).thenReturn(generatedFiles);
        when(provider.getType()).thenReturn(ArtifactType.FILE_GENERATIONS);
        doReturn(projectEntities).when(handler).getAllNonVersionedEntities(TEST_GROUP_ID, TEST_ARTIFACT_ID, snapshotVersion);

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, snapshotVersion, files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());
        Assertions.assertTrue(response.getMessages().stream().anyMatch(msg -> msg.contains("removing prior")));
    }

    @Test
    public void canHandleGeneratedFilesWithoutEntityPath()
    {
        List<File> files = repository.findFiles(ArtifactType.FILE_GENERATIONS, TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        List<Entity> projectEntities = new ArrayList<>(Arrays.asList(
            new EntityDefinition("examples::metadata::TestEntity", "", Collections.emptyMap())
        ));

        List<DepotGeneration> generatedFiles = new ArrayList<>(Arrays.asList(
            new DepotGeneration("/unknown/path/TestFile.txt", "content")
        ));

        when(provider.extractArtifacts(files)).thenReturn(generatedFiles);
        when(provider.getType()).thenReturn(ArtifactType.FILE_GENERATIONS);
        doReturn(projectEntities).when(handler).getAllNonVersionedEntities(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());
    }

    @Test
    public void canHandleGeneratedFilesWithMatchingEntityPath()
    {
        List<File> files = repository.findFiles(ArtifactType.FILE_GENERATIONS, TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        List<Entity> projectEntities = new ArrayList<>(Arrays.asList(
            new EntityDefinition("examples::metadata::TestEntity", "", Collections.emptyMap())
        ));

        List<DepotGeneration> generatedFiles = new ArrayList<>(Arrays.asList(
            new DepotGeneration("/examples/metadata/TestEntity/java/TestFile.java", "content")
        ));

        when(provider.extractArtifacts(files)).thenReturn(generatedFiles);
        when(provider.getType()).thenReturn(ArtifactType.FILE_GENERATIONS);
        doReturn(projectEntities).when(handler).getAllNonVersionedEntities(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());
        List<StoredFileGeneration> storedGenerations = generations.getAll();
        Assertions.assertEquals(1, storedGenerations.size());
        Assertions.assertEquals("java", storedGenerations.get(0).getType());
    }

    @Test
    public void canHandleGeneratedFilesWithUnknownType()
    {
        List<File> files = repository.findFiles(ArtifactType.FILE_GENERATIONS, TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        List<Entity> projectEntities = new ArrayList<>(Arrays.asList(
            new EntityDefinition("examples::metadata::TestEntity", "", Collections.emptyMap())
        ));

        List<DepotGeneration> generatedFiles = new ArrayList<>(Arrays.asList(
            new DepotGeneration("/examples/metadata/TestEntity/file.txt", "content")
        ));

        when(provider.extractArtifacts(files)).thenReturn(generatedFiles);
        when(provider.getType()).thenReturn(ArtifactType.FILE_GENERATIONS);
        doReturn(projectEntities).when(handler).getAllNonVersionedEntities(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, files);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.hasErrors());
        Assertions.assertTrue(response.getErrors().stream().anyMatch(err -> err.contains("Generation type")));
    }

    @Test
    public void canHandleExceptionDuringRefresh()
    {
        List<File> files = repository.findFiles(ArtifactType.FILE_GENERATIONS, TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        when(provider.extractArtifacts(files)).thenThrow(new RuntimeException("Test exception"));

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, files);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.hasErrors());
        Assertions.assertTrue(response.getErrors().stream().anyMatch(err -> err.contains("Error processing generations")));
    }

    @Test
    public void canGetAllNonVersionedEntities()
    {
        List<Entity> entities = handler.getAllNonVersionedEntities(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        Assertions.assertNotNull(entities);
        Assertions.assertFalse(entities.isEmpty());
    }

    @Test
    public void canDeleteGenerations()
    {
        StoredFileGeneration generation = new StoredFileGeneration(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION, "examples::metadata::Test", "java", new DepotGeneration("/test/file.java", "content"));
        generations.createOrUpdate(Collections.singletonList(generation));

        Assertions.assertEquals(1, generations.getAll().size());

        handler.delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        Assertions.assertEquals(0, generations.getAll().size());
    }
}
