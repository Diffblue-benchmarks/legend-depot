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

package org.finos.legend.depot.services.artifacts.purge;

import org.finos.legend.depot.domain.notifications.MetadataNotificationResponse;
import org.finos.legend.depot.services.api.artifacts.reconciliation.VersionsReconciliationService;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsService;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestArtifactsPurgeServiceWithMocks
{
    public static final String TEST_GROUP_ID = "examples.metadata";
    public static final String TEST_ARTIFACT_ID = "test";

    private final ManageProjectsService projects = mock(ManageProjectsService.class);
    private final VersionsReconciliationService versionsMismatchService = mock(VersionsReconciliationService.class);
    private final QueryMetricsService metricsService = mock(QueryMetricsService.class);
    private final ProjectsConfiguration projectsConfiguration = new ProjectsConfiguration("master");

    private ArtifactsPurgeServiceImpl purgeService;

    @BeforeEach
    public void setUp()
    {
        purgeService = new ArtifactsPurgeServiceImpl(projects, versionsMismatchService, metricsService, projectsConfiguration);
    }

    @Test
    public void testGetQueryMetricsService()
    {
        Assertions.assertNotNull(purgeService.getQueryMetricsService());
        Assertions.assertEquals(metricsService, purgeService.getQueryMetricsService());
    }

    @Test
    public void testDeleteSnapshotVersionsThrowsWhenProjectNotFound()
    {
        when(projects.findCoordinates(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                purgeService.deleteSnapshotVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID, Arrays.asList("test-SNAPSHOT")));
    }

    @Test
    public void testEvictOldestProjectVersionsCatchesException()
    {
        doNothing().when(projects).checkExists(TEST_GROUP_ID, TEST_ARTIFACT_ID);
        when(projects.getVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID)).thenReturn(Arrays.asList("1.0.0", "2.0.0"));
        when(projects.find(TEST_GROUP_ID, TEST_ARTIFACT_ID, "1.0.0")).thenReturn(Optional.empty());

        MetadataNotificationResponse response = purgeService.evictOldestProjectVersions(TEST_GROUP_ID, TEST_ARTIFACT_ID, 1);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getErrors().isEmpty());
    }

    @Test
    public void testEvictLeastRecentlyUsedCatchesException()
    {
        when(metricsService.findSnapshotVersionMetricsBefore(any())).thenThrow(new RuntimeException("metrics error"));

        MetadataNotificationResponse response = purgeService.evictLeastRecentlyUsed(365, 30);
        Assertions.assertNotNull(response);
    }

    @Test
    public void testEvictVersionsNotUsedCatchesException()
    {
        when(projects.getAllProjectCoordinates()).thenThrow(new RuntimeException("projects error"));

        MetadataNotificationResponse response = purgeService.evictVersionsNotUsed();
        Assertions.assertNotNull(response);
    }
}
