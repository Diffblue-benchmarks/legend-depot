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

package org.finos.legend.depot.services.entities;

import org.finos.legend.depot.domain.entity.DepotEntity;
import org.finos.legend.depot.domain.entity.DepotEntityOverview;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.version.Scope;
import org.finos.legend.depot.services.TestBaseServices;
import org.finos.legend.depot.services.api.entities.EntityClassifierService;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.finos.legend.depot.store.api.entities.UpdateEntities;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.mongo.entities.EntitiesMongo;
import org.finos.legend.depot.store.mongo.entities.test.EntitiesMongoTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;

public class TestEntityClassifierService extends TestBaseServices
{
    private final QueryMetricsRegistry metrics = mock(QueryMetricsRegistry.class);
    private final Queue queue = mock(Queue.class);
    private EntitiesMongoTestUtils entityUtils = new EntitiesMongoTestUtils(mongoProvider);
    private ManageProjectsService projectsService = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metrics, queue, new ProjectsConfiguration("master"));
    protected UpdateEntities entitiesStore = new EntitiesMongo(mongoProvider);
    protected EntityClassifierService classifierService = new EntityClassifierServiceImpl(projectsService, entitiesStore);

    @BeforeEach
    public void setUpData()
    {
        super.setUpData();
        entityUtils.loadEntities("PROD-A", "2.3.1");
        entityUtils.loadEntities("PROD-B", "1.0.0");
        entityUtils.loadEntities("PROD-C", "2.0.1");
    }

    @Test
    public void canCreateEntityClassifierService()
    {
        EntityClassifierService service = new EntityClassifierServiceImpl(projectsService, entitiesStore);

        Assertions.assertNotNull(service);
    }

    @Test
    public void canFindClassifierEntitiesForSnapshotScope()
    {
        List<DepotEntity> entities = classifierService.findClassifierEntities("meta::pure::metamodel::type::Class", Scope.SNAPSHOT);

        Assertions.assertNotNull(entities);
    }

    @Test
    public void canFindClassifierEntitiesForReleasesScope()
    {
        List<DepotEntity> entities = classifierService.findClassifierEntities("meta::pure::metamodel::type::Class", Scope.RELEASES);

        Assertions.assertNotNull(entities);
        Assertions.assertFalse(entities.isEmpty());
    }

    @Test
    public void canFindClassifierEntitiesByVersions()
    {
        List<ProjectVersion> projectVersions = Arrays.asList(
                new ProjectVersion("examples.metadata", "test", "2.3.1"),
                new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0")
        );

        List<DepotEntity> entities = classifierService.findClassifierEntitiesByVersions("meta::pure::metamodel::type::Class", projectVersions);

        Assertions.assertNotNull(entities);
    }

    @Test
    public void canFindClassifierSummariesForSnapshotScope()
    {
        List<DepotEntityOverview> summaries = classifierService.findClassifierSummaries("meta::pure::metamodel::type::Class", Scope.SNAPSHOT);

        Assertions.assertNotNull(summaries);
    }

    @Test
    public void canFindClassifierSummariesForReleasesScope()
    {
        List<DepotEntityOverview> summaries = classifierService.findClassifierSummaries("meta::pure::metamodel::type::Class", Scope.RELEASES);

        Assertions.assertNotNull(summaries);
    }

    @Test
    public void canFindClassifierSummariesByVersions()
    {
        List<ProjectVersion> projectVersions = Arrays.asList(
                new ProjectVersion("examples.metadata", "test", "2.3.1"),
                new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0")
        );

        List<DepotEntityOverview> summaries = classifierService.findClassifierSummariesByVersions("meta::pure::metamodel::type::Class", projectVersions);

        Assertions.assertNotNull(summaries);
    }

    @Test
    public void canFindClassifierEntitiesWithSearchForSnapshotScope()
    {
        List<DepotEntity> entities = classifierService.findClassifierEntities("meta::pure::metamodel::type::Class", Scope.SNAPSHOT, "test", 10);

        Assertions.assertNotNull(entities);
    }

    @Test
    public void canFindClassifierEntitiesWithSearchForReleasesScope()
    {
        List<DepotEntity> entities = classifierService.findClassifierEntities("meta::pure::metamodel::type::Class", Scope.RELEASES, "test", 10);

        Assertions.assertNotNull(entities);
    }

    @Test
    public void canFindClassifierEntitiesByVersionsWithSearch()
    {
        List<ProjectVersion> projectVersions = Arrays.asList(
                new ProjectVersion("examples.metadata", "test", "2.3.1")
        );

        List<DepotEntity> entities = classifierService.findClassifierEntitiesByVersions("meta::pure::metamodel::type::Class", projectVersions, "test", 10);

        Assertions.assertNotNull(entities);
    }

    @Test
    public void canGetEntitiesByClassifierPathForSnapshotScope()
    {
        List<DepotEntity> entities = classifierService.getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", null, null, Scope.SNAPSHOT, true);

        Assertions.assertNotNull(entities);
    }

    @Test
    public void canGetEntitiesByClassifierPathForReleasesScope()
    {
        projectsStore.createOrUpdate(new StoreProjectData("PROD-10", "com.example", "project1", null, "1.0.0"));
        projectsStore.createOrUpdate(new StoreProjectData("PROD-11", "com.example", "project2", null, "1.0.0"));

        List<DepotEntity> entities = classifierService.getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", null, null, Scope.RELEASES, true);

        Assertions.assertNotNull(entities);
        Assertions.assertFalse(entities.isEmpty());
    }

    @Test
    public void canGetEntitiesByClassifierPathWithSearchAndLimit()
    {
        projectsStore.createOrUpdate(new StoreProjectData("PROD-12", "com.example", "project3", null, "1.0.0"));
        projectsStore.createOrUpdate(new StoreProjectData("PROD-13", "com.example", "project4", null, "1.0.0"));

        List<DepotEntity> entities = classifierService.getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", "test", 2, Scope.RELEASES, true);

        Assertions.assertNotNull(entities);
        Assertions.assertTrue(entities.size() <= 2);
    }

    @Test
    public void canGetEntitiesByClassifierPathWithEmptyResult()
    {
        List<DepotEntity> entities = classifierService.getEntitiesByClassifierPath("meta::nonexistent::Classifier", null, null, Scope.RELEASES, true);

        Assertions.assertNotNull(entities);
        Assertions.assertTrue(entities.isEmpty());
    }

    @Test
    public void canHandleMultiplePagesForClassifierPath()
    {
        projectsStore.createOrUpdate(new StoreProjectData("PROD-14", "com.example", "project5", null, "1.0.0"));
        projectsStore.createOrUpdate(new StoreProjectData("PROD-15", "com.example", "project6", null, "1.0.0"));

        List<DepotEntity> entities = classifierService.getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", null, 5, Scope.RELEASES, true);

        Assertions.assertNotNull(entities);
        Assertions.assertTrue(entities.size() <= 5);
    }
}
