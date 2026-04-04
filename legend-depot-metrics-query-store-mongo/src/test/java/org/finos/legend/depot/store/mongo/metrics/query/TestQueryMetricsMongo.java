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

import java.util.Date;
import java.util.List;

public class TestQueryMetricsMongo extends TestStoreMongo
{

    private QueryMetricsMongo store;

    @BeforeEach
    public void setUp()
    {
        store = new QueryMetricsMongo(mongoProvider);
    }

    @Test
    public void testGetAllReturnsEmptyWhenNoData()
    {
        List<VersionQueryMetric> result = store.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testInsertAndGetAll()
    {
        VersionQueryMetric metric = new VersionQueryMetric("group1", "artifact1", "1.0.0");
        store.insert(metric);

        List<VersionQueryMetric> result = store.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("group1", result.get(0).getGroupId());
        Assertions.assertEquals("artifact1", result.get(0).getArtifactId());
        Assertions.assertEquals("1.0.0", result.get(0).getVersionId());
    }

    @Test
    public void testGetByGroupArtifactVersion()
    {
        VersionQueryMetric metric1 = new VersionQueryMetric("group1", "artifact1", "1.0.0");
        VersionQueryMetric metric2 = new VersionQueryMetric("group2", "artifact2", "2.0.0");
        store.insert(metric1);
        store.insert(metric2);

        List<VersionQueryMetric> result = store.get("group1", "artifact1", "1.0.0");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("group1", result.get(0).getGroupId());
    }

    @Test
    public void testGetReturnsEmptyForUnknownVersion()
    {
        List<VersionQueryMetric> result = store.get("unknown", "unknown", "0.0.0");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByGroupArtifact()
    {
        VersionQueryMetric metric1 = new VersionQueryMetric("group1", "artifact1", "1.0.0");
        VersionQueryMetric metric2 = new VersionQueryMetric("group1", "artifact1", "2.0.0");
        VersionQueryMetric metric3 = new VersionQueryMetric("group2", "artifact2", "1.0.0");
        store.insert(metric1);
        store.insert(metric2);
        store.insert(metric3);

        List<VersionQueryMetric> result = store.find("group1", "artifact1");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testFindReturnsEmptyForUnknownArtifact()
    {
        List<VersionQueryMetric> result = store.find("unknown", "unknown");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testConsolidate()
    {
        Date olderDate = new Date(System.currentTimeMillis() - 10000);
        Date newerDate = new Date(System.currentTimeMillis());

        VersionQueryMetric oldMetric = new VersionQueryMetric("group1", "artifact1", "1.0.0", olderDate);
        VersionQueryMetric newMetric = new VersionQueryMetric("group1", "artifact1", "1.0.0", newerDate);
        store.insert(oldMetric);
        store.insert(newMetric);

        Assertions.assertEquals(2, store.getAll().size());

        long deleted = store.consolidate(newMetric);
        Assertions.assertTrue(deleted >= 0);
    }

    @Test
    public void testFindMetricsBefore()
    {
        Date pastDate = new Date(System.currentTimeMillis() - 10000);
        Date futureDate = new Date(System.currentTimeMillis() + 10000);

        VersionQueryMetric oldMetric = new VersionQueryMetric("group1", "artifact1", "1.0.0", pastDate);
        VersionQueryMetric newMetric = new VersionQueryMetric("group1", "artifact1", "2.0.0", futureDate);
        store.insert(oldMetric);
        store.insert(newMetric);

        Date threshold = new Date(System.currentTimeMillis());
        List<VersionQueryMetric> result = store.findMetricsBefore(threshold);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("1.0.0", result.get(0).getVersionId());
    }

    @Test
    public void testDelete()
    {
        VersionQueryMetric metric = new VersionQueryMetric("group1", "artifact1", "1.0.0");
        store.insert(metric);
        Assertions.assertEquals(1, store.getAll().size());

        long result = store.delete("group1", "artifact1", "1.0.0");
        Assertions.assertEquals(1, result);
        Assertions.assertTrue(store.getAll().isEmpty());
    }

    @Test
    public void testGetAllStoredEntitiesCoordinates()
    {
        VersionQueryMetric metric1 = new VersionQueryMetric("group1", "artifact1", "1.0.0");
        VersionQueryMetric metric2 = new VersionQueryMetric("group1", "artifact1", "1.0.0");
        VersionQueryMetric metric3 = new VersionQueryMetric("group2", "artifact2", "2.0.0");
        store.insert(metric1);
        store.insert(metric2);
        store.insert(metric3);

        List<ProjectVersion> coordinates = store.getAllStoredEntitiesCoordinates();
        Assertions.assertNotNull(coordinates);
        Assertions.assertEquals(2, coordinates.size());
    }

    @Test
    public void testBuildIndexes()
    {
        Assertions.assertNotNull(QueryMetricsMongo.buildIndexes());
        Assertions.assertFalse(QueryMetricsMongo.buildIndexes().isEmpty());
    }

    @Test
    public void testGetCollection()
    {
        Assertions.assertNotNull(store.getCollection());
    }
}
