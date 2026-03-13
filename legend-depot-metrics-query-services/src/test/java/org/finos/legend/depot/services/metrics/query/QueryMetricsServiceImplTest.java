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

package org.finos.legend.depot.services.metrics.query;

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.store.api.metrics.query.QueryMetrics;
import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class QueryMetricsServiceImplTest
{
    private TestQueryMetrics metricsStore;
    private QueryMetricsServiceImpl service;

    private static class TestQueryMetrics implements QueryMetrics
    {
        private List<VersionQueryMetric> metrics = new ArrayList<>();
        private List<ProjectVersion> coordinates = new ArrayList<>();

        @Override
        public List<VersionQueryMetric> get(String groupId, String artifactId, String versionId)
        {
            return metrics.stream()
                    .filter(m -> m.getGroupId().equals(groupId) && m.getArtifactId().equals(artifactId) && m.getVersionId().equals(versionId))
                    .collect(Collectors.toList());
        }

        @Override
        public List<VersionQueryMetric> find(String groupId, String artifactId)
        {
            return metrics.stream()
                    .filter(m -> m.getGroupId().equals(groupId) && m.getArtifactId().equals(artifactId))
                    .collect(Collectors.toList());
        }

        @Override
        public List<VersionQueryMetric> getAll()
        {
            return new ArrayList<>(metrics);
        }

        @Override
        public void insert(VersionQueryMetric versionQueryMetric)
        {
            metrics.add(versionQueryMetric);
        }

        @Override
        public long consolidate(VersionQueryMetric metric)
        {
            return 5L;
        }

        @Override
        public List<ProjectVersion> getAllStoredEntitiesCoordinates()
        {
            return new ArrayList<>(coordinates);
        }

        @Override
        public List<VersionQueryMetric> findMetricsBefore(Date date)
        {
            return metrics.stream()
                    .filter(m -> m.getLastQueryTime().before(date))
                    .collect(Collectors.toList());
        }

        @Override
        public long delete(String groupId, String artifactId, String versionId)
        {
            long count = metrics.stream()
                    .filter(m -> m.getGroupId().equals(groupId) && m.getArtifactId().equals(artifactId) && m.getVersionId().equals(versionId))
                    .count();
            metrics.removeIf(m -> m.getGroupId().equals(groupId) && m.getArtifactId().equals(artifactId) && m.getVersionId().equals(versionId));
            return count;
        }

        public void addMetric(VersionQueryMetric metric)
        {
            metrics.add(metric);
        }

        public void addCoordinate(ProjectVersion pv)
        {
            coordinates.add(pv);
        }
    }

    @BeforeEach
    public void setup()
    {
        metricsStore = new TestQueryMetrics();
        service = new QueryMetricsServiceImpl(metricsStore);
    }

    @Test
    public void testConstructor()
    {
        QueryMetricsServiceImpl newService = new QueryMetricsServiceImpl(metricsStore);
        Assertions.assertNotNull(newService);
    }

    @Test
    public void testGetSummaryWithMultipleMetrics()
    {
        Date oldDate = new Date(System.currentTimeMillis() - 100000);
        Date newDate = new Date(System.currentTimeMillis());

        VersionQueryMetric metric1 = new VersionQueryMetric("group1", "artifact1", "1.0.0", oldDate);
        VersionQueryMetric metric2 = new VersionQueryMetric("group1", "artifact1", "1.0.0", newDate);

        metricsStore.addMetric(metric1);
        metricsStore.addMetric(metric2);

        Optional<VersionQueryMetric> result = service.getSummary("group1", "artifact1", "1.0.0");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(newDate, result.get().getLastQueryTime());
    }

    @Test
    public void testGetSummaryWithEmptyResult()
    {
        Optional<VersionQueryMetric> result = service.getSummary("group1", "artifact1", "1.0.0");

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testGetSummaryByProjectVersion()
    {
        ProjectVersion pv1 = new ProjectVersion("group1", "artifact1", "1.0.0");
        ProjectVersion pv2 = new ProjectVersion("group2", "artifact2", "2.0.0");

        VersionQueryMetric metric1 = new VersionQueryMetric("group1", "artifact1", "1.0.0");
        VersionQueryMetric metric2 = new VersionQueryMetric("group2", "artifact2", "2.0.0");

        metricsStore.addCoordinate(pv1);
        metricsStore.addCoordinate(pv2);
        metricsStore.addMetric(metric1);
        metricsStore.addMetric(metric2);

        List<VersionQueryMetric> result = service.getSummaryByProjectVersion();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testFindMetricsForProjectCoordinates()
    {
        VersionQueryMetric metric1 = new VersionQueryMetric("group1", "artifact1", "1.0.0");
        metricsStore.addMetric(metric1);

        List<VersionQueryMetric> result = service.findMetricsForProjectCoordinates("group1", "artifact1");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testFindReleasedVersionMetricsBefore()
    {
        Date oldDate = new Date(System.currentTimeMillis() - 100000);
        Date cutoffDate = new Date();
        VersionQueryMetric releaseMetric = new VersionQueryMetric("group1", "artifact1", "1.0.0", oldDate);
        VersionQueryMetric snapshotMetric = new VersionQueryMetric("group2", "artifact2", "2.0.0-SNAPSHOT", oldDate);

        metricsStore.addMetric(releaseMetric);
        metricsStore.addMetric(snapshotMetric);

        List<VersionQueryMetric> result = service.findReleasedVersionMetricsBefore(cutoffDate);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("1.0.0", result.get(0).getVersionId());
    }

    @Test
    public void testFindSnapshotVersionMetricsBefore()
    {
        Date oldDate = new Date(System.currentTimeMillis() - 100000);
        Date cutoffDate = new Date();
        VersionQueryMetric releaseMetric = new VersionQueryMetric("group1", "artifact1", "1.0.0", oldDate);
        VersionQueryMetric snapshotMetric = new VersionQueryMetric("group2", "artifact2", "2.0.0-SNAPSHOT", oldDate);

        metricsStore.addMetric(releaseMetric);
        metricsStore.addMetric(snapshotMetric);

        List<VersionQueryMetric> result = service.findSnapshotVersionMetricsBefore(cutoffDate);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("2.0.0-SNAPSHOT", result.get(0).getVersionId());
    }

    @Test
    public void testPersist()
    {
        InMemoryQueryMetricsRegistry registry = new InMemoryQueryMetricsRegistry();
        registry.record("group1", "artifact1", "1.0.0");
        registry.record("group2", "artifact2", "2.0.0");

        service.persist(registry);

        List<VersionQueryMetric> allMetrics = metricsStore.getAll();
        Assertions.assertEquals(2, allMetrics.size());
    }

    @Test
    public void testDelete()
    {
        VersionQueryMetric metric = new VersionQueryMetric("group1", "artifact1", "1.0.0");
        metricsStore.addMetric(metric);

        service.delete("group1", "artifact1", "1.0.0");

        List<VersionQueryMetric> remaining = metricsStore.get("group1", "artifact1", "1.0.0");
        Assertions.assertEquals(0, remaining.size());
    }

    @Test
    public void testGetStaleMetrics()
    {
        Date oldDate = new Date(System.currentTimeMillis() - (100L * 24 * 60 * 60 * 1000));
        Date recentDate = new Date();

        VersionQueryMetric oldRelease = new VersionQueryMetric("group1", "artifact1", "1.0.0", oldDate);
        VersionQueryMetric recentRelease = new VersionQueryMetric("group2", "artifact2", "2.0.0", recentDate);
        VersionQueryMetric oldSnapshot = new VersionQueryMetric("group3", "artifact3", "3.0.0-SNAPSHOT", oldDate);

        metricsStore.addMetric(oldRelease);
        metricsStore.addMetric(recentRelease);
        metricsStore.addMetric(oldSnapshot);

        List<VersionQueryMetric> result = service.getStaleMetrics(30, 7);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testConsolidateMetrics()
    {
        ProjectVersion pv1 = new ProjectVersion("group1", "artifact1", "1.0.0");
        VersionQueryMetric metric1 = new VersionQueryMetric("group1", "artifact1", "1.0.0");

        metricsStore.addCoordinate(pv1);
        metricsStore.addMetric(metric1);

        service.consolidateMetrics();

        Assertions.assertNotNull(metricsStore.getAll());
    }

    @Test
    public void testConsolidateMetricsWithException()
    {
        TestQueryMetrics failingStore = new TestQueryMetrics()
        {
            @Override
            public List<VersionQueryMetric> get(String groupId, String artifactId, String versionId)
            {
                throw new RuntimeException("Test exception");
            }
        };

        QueryMetricsServiceImpl failingService = new QueryMetricsServiceImpl(failingStore);
        ProjectVersion pv1 = new ProjectVersion("group1", "artifact1", "1.0.0");
        failingStore.addCoordinate(pv1);

        failingService.consolidateMetrics();

        Assertions.assertEquals(1, failingStore.getAllStoredEntitiesCoordinates().size());
    }
}
