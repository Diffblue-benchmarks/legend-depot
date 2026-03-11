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

package org.finos.legend.depot.store.mongo.metrics.query;

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.finos.legend.depot.domain.DatesHandler.toDate;

public class TestQueryMetricsMongo extends TestStoreMongo
{
    private QueryMetricsMongo metricsStore = new QueryMetricsMongo(mongoProvider);

    @BeforeEach
    public void setup()
    {
        metricsStore.getCollection().drop();
    }

    @Test
    public void canCreateInstanceAndGetCollection()
    {
        Assertions.assertNotNull(metricsStore);
        Assertions.assertNotNull(metricsStore.getCollection());
    }

    @Test
    public void canGetAllWhenEmpty()
    {
        List<VersionQueryMetric> all = metricsStore.getAll();
        Assertions.assertNotNull(all);
        Assertions.assertTrue(all.isEmpty());
    }

    @Test
    public void canInsertAndGetAll()
    {
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "1.0.0"));
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "2.0.0"));

        List<VersionQueryMetric> all = metricsStore.getAll();
        Assertions.assertEquals(2, all.size());
    }

    @Test
    public void canGetByGroupArtifactVersion()
    {
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "1.0.0"));
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "2.0.0"));
        metricsStore.insert(new VersionQueryMetric("group2", "art2", "1.0.0"));

        List<VersionQueryMetric> result = metricsStore.get("group1", "art1", "1.0.0");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("group1", result.get(0).getGroupId());
        Assertions.assertEquals("art1", result.get(0).getArtifactId());
        Assertions.assertEquals("1.0.0", result.get(0).getVersionId());
    }

    @Test
    public void canFindByGroupAndArtifact()
    {
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "1.0.0"));
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "2.0.0"));
        metricsStore.insert(new VersionQueryMetric("group2", "art2", "1.0.0"));

        List<VersionQueryMetric> result = metricsStore.find("group1", "art1");
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void canGetAllStoredEntitiesCoordinates()
    {
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "1.0.0"));
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "1.0.0"));
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "2.0.0"));
        metricsStore.insert(new VersionQueryMetric("group2", "art2", "1.0.0"));

        List<ProjectVersion> coordinates = metricsStore.getAllStoredEntitiesCoordinates();
        Assertions.assertEquals(3, coordinates.size());
    }

    @Test
    public void canConsolidate() throws InterruptedException
    {
        Date earlier = toDate(LocalDateTime.parse("2023-03-21T14:02:49", DateTimeFormatter.ISO_DATE_TIME));
        Date later = toDate(LocalDateTime.parse("2023-03-22T14:02:49", DateTimeFormatter.ISO_DATE_TIME));

        metricsStore.insert(new VersionQueryMetric("group1", "art1", "1.0.0", earlier));
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "1.0.0", later));

        long deleted = metricsStore.consolidate(new VersionQueryMetric("group1", "art1", "1.0.0", later));
        Assertions.assertEquals(1, deleted);

        List<VersionQueryMetric> remaining = metricsStore.getAll();
        Assertions.assertEquals(1, remaining.size());
        Assertions.assertEquals(later, remaining.get(0).getLastQueryTime());
    }

    @Test
    public void canFindMetricsBefore()
    {
        Date early = toDate(LocalDateTime.parse("2023-03-20T14:02:49", DateTimeFormatter.ISO_DATE_TIME));
        Date late = toDate(LocalDateTime.parse("2023-03-25T14:02:49", DateTimeFormatter.ISO_DATE_TIME));

        metricsStore.insert(new VersionQueryMetric("group1", "art1", "1.0.0", early));
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "2.0.0", late));

        Date cutoff = toDate(LocalDateTime.parse("2023-03-22T14:02:49", DateTimeFormatter.ISO_DATE_TIME));
        List<VersionQueryMetric> before = metricsStore.findMetricsBefore(cutoff);
        Assertions.assertEquals(1, before.size());
        Assertions.assertEquals("1.0.0", before.get(0).getVersionId());
    }

    @Test
    public void canDeleteByCoordinates()
    {
        metricsStore.insert(new VersionQueryMetric("group1", "art1", "1.0.0"));
        metricsStore.insert(new VersionQueryMetric("group2", "art2", "1.0.0"));

        long result = metricsStore.delete("group1", "art1", "1.0.0");
        Assertions.assertEquals(1, result);

        List<VersionQueryMetric> all = metricsStore.getAll();
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals("group2", all.get(0).getGroupId());
    }

    @Test
    public void canBuildIndexes()
    {
        Assertions.assertFalse(QueryMetricsMongo.buildIndexes().isEmpty());
    }

    @Test
    public void canValidateNewData()
    {
        VersionQueryMetric metric = new VersionQueryMetric("group1", "art1", "1.0.0");
        Assertions.assertDoesNotThrow(() -> metricsStore.insert(metric));
    }
}
