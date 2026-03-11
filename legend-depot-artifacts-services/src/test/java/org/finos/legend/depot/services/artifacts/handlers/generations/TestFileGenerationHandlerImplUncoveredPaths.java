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

import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.domain.notifications.MetadataNotificationResponse;
import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.services.api.generations.ManageFileGenerationsService;
import org.finos.legend.depot.services.generations.impl.ManageFileGenerationsServiceImpl;
import org.finos.legend.depot.services.projects.ProjectsServiceImpl;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.store.api.projects.UpdateProjects;
import org.finos.legend.depot.store.api.projects.UpdateProjectsVersions;
import org.finos.legend.depot.services.api.artifacts.handlers.generations.FileGenerationsArtifactsProvider;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.generations.FileGenerationsMongo;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


public class TestFileGenerationHandlerImplUncoveredPaths extends TestStoreMongo
{

    public static final String PRODUCT_A = "PROD-23992";
    protected static final String TEST_GROUP_ID = "examples.metadata";
    public static final String TEST_ARTIFACT_ID = "test";
    private final UpdateProjects projects = mock(UpdateProjects.class);
    private final UpdateProjectsVersions projectsVersions = mock(UpdateProjectsVersions.class);
    private final QueryMetricsRegistry metrics = mock(QueryMetricsRegistry.class);
    private final Queue queue = mock(Queue.class);
    private final ArtifactRepository repository = mock(ArtifactRepository.class);
    private final FileGenerationsArtifactsProvider provider = mock(FileGenerationsArtifactsProvider.class);
    private final ManageFileGenerationsService generations = new ManageFileGenerationsServiceImpl(new FileGenerationsMongo(mongoProvider), new ProjectsServiceImpl(projectsVersions, projects, metrics, queue, new ProjectsConfiguration("master")));
    private final FileGenerationHandlerImpl handler = spy(new FileGenerationHandlerImpl(repository, provider, generations));

    @BeforeEach
    public void setup()
    {
        when(projects.find(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(Optional.of(new StoreProjectData(PRODUCT_A, TEST_GROUP_ID, TEST_ARTIFACT_ID)));
        when(projectsVersions.find(TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.0.0")).thenReturn(Optional.of(new StoreProjectVersionData(TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.0.0")));
    }

    @Test
    public void canHandleGeneratedFileWithNoMatchingEntityPath()
    {
        List<Entity> projectEntities = new ArrayList<>(Arrays.asList(
                new EntityDefinition("examples::metadata::myEntity", "", Collections.emptyMap())
        ));
        List<DepotGeneration> generatedFiles = new ArrayList<>(Arrays.asList(
                new DepotGeneration("/no/matching/path/file.json", "content")
        ));
        List<File> files = Collections.emptyList();
        when(this.provider.extractArtifacts(files)).thenReturn(generatedFiles);
        doReturn(projectEntities).when(this.handler).getAllNonVersionedEntities(TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.0.0");

        MetadataNotificationResponse response = this.handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.0.0", files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());
        Assertions.assertEquals(0, generations.getAll().size());
    }

    @Test
    public void canHandleGeneratedFileWithUnknownType()
    {
        List<Entity> projectEntities = new ArrayList<>(Arrays.asList(
                new EntityDefinition("examples::metadata::myEntity", "", Collections.emptyMap())
        ));
        List<DepotGeneration> generatedFiles = new ArrayList<>(Arrays.asList(
                new DepotGeneration("/examples/metadata/myEntity/file.ext", "content")
        ));
        List<File> files = Collections.emptyList();
        when(this.provider.extractArtifacts(files)).thenReturn(generatedFiles);
        doReturn(projectEntities).when(this.handler).getAllNonVersionedEntities(TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.0.0");

        MetadataNotificationResponse response = this.handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.0.0", files);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.hasErrors());
        Assertions.assertTrue(response.getErrors().get(0).contains("Generation type for file"));
    }

    @Test
    public void canHandleExceptionDuringRefresh()
    {
        List<File> files = Collections.emptyList();
        when(this.provider.extractArtifacts(files)).thenThrow(new RuntimeException("test error"));
        doReturn(Collections.emptyList()).when(this.handler).getAllNonVersionedEntities(TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.0.0");

        MetadataNotificationResponse response = this.handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.0.0", files);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.hasErrors());
        Assertions.assertTrue(response.getErrors().get(0).contains("Error processing generations update"));
        Assertions.assertTrue(response.getErrors().get(0).contains("test error"));
    }
}
