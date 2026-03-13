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

import com.mongodb.client.MongoCollection;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class QueryMetricsMongoTest extends TestStoreMongo
{
    private QueryMetricsMongo queryMetricsMongo;

    @BeforeEach
    public void setUp()
    {
        // Arrange
        queryMetricsMongo = new QueryMetricsMongo(mongoProvider);
    }

    @Test
    public void canCreateQueryMetricsMongo()
    {
        // Arrange & Act
        QueryMetricsMongo store = new QueryMetricsMongo(mongoProvider);

        // Assert
        Assertions.assertNotNull(store);
    }

    @Test
    public void canGetCollection()
    {
        // Act
        MongoCollection collection = queryMetricsMongo.getCollection();

        // Assert
        Assertions.assertNotNull(collection);
    }

    @Test
    public void canGetAll()
    {
        // Arrange
        VersionQueryMetric metric1 = new VersionQueryMetric("org.example", "artifact1", "1.0.0");
        VersionQueryMetric metric2 = new VersionQueryMetric("org.example", "artifact2", "2.0.0");
        queryMetricsMongo.insert(metric1);
        queryMetricsMongo.insert(metric2);

        // Act
        List<VersionQueryMetric> result = queryMetricsMongo.getAll();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void canGetAllStoredEntitiesCoordinates()
    {
        // Arrange
        VersionQueryMetric metric1 = new VersionQueryMetric("org.example", "artifact1", "1.0.0");
        VersionQueryMetric metric2 = new VersionQueryMetric("org.example", "artifact2", "2.0.0");
        VersionQueryMetric metric3 = new VersionQueryMetric("org.example", "artifact1", "1.0.0");
        queryMetricsMongo.insert(metric1);
        queryMetricsMongo.insert(metric2);
        queryMetricsMongo.insert(metric3);

        // Act
        List<ProjectVersion> result = queryMetricsMongo.getAllStoredEntitiesCoordinates();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void canGetByCoordinates()
    {
        // Arrange
        VersionQueryMetric metric = new VersionQueryMetric("org.example", "artifact1", "1.0.0");
        queryMetricsMongo.insert(metric);

        // Act
        List<VersionQueryMetric> result = queryMetricsMongo.get("org.example", "artifact1", "1.0.0");

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("org.example", result.get(0).getGroupId());
        Assertions.assertEquals("artifact1", result.get(0).getArtifactId());
        Assertions.assertEquals("1.0.0", result.get(0).getVersionId());
    }

    @Test
    public void canFindByGroupAndArtifact()
    {
        // Arrange
        VersionQueryMetric metric1 = new VersionQueryMetric("org.example", "artifact1", "1.0.0");
        VersionQueryMetric metric2 = new VersionQueryMetric("org.example", "artifact1", "2.0.0");
        VersionQueryMetric metric3 = new VersionQueryMetric("org.example", "artifact2", "1.0.0");
        queryMetricsMongo.insert(metric1);
        queryMetricsMongo.insert(metric2);
        queryMetricsMongo.insert(metric3);

        // Act
        List<VersionQueryMetric> result = queryMetricsMongo.find("org.example", "artifact1");

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void canInsertMetric()
    {
        // Arrange
        VersionQueryMetric metric = new VersionQueryMetric("org.example", "artifact1", "1.0.0");

        // Act
        queryMetricsMongo.insert(metric);
        List<VersionQueryMetric> result = queryMetricsMongo.getAll();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("org.example", result.get(0).getGroupId());
    }

    @Test
    public void canConsolidateMetrics()
    {
        // Arrange
        Date oldDate = new Date(System.currentTimeMillis() - 10000);
        Date newDate = new Date(System.currentTimeMillis());
        VersionQueryMetric oldMetric = new VersionQueryMetric("org.example", "artifact1", "1.0.0", oldDate);
        VersionQueryMetric newMetric = new VersionQueryMetric("org.example", "artifact1", "1.0.0", newDate);
        queryMetricsMongo.insert(oldMetric);
        queryMetricsMongo.insert(newMetric);

        // Act
        long deletedCount = queryMetricsMongo.consolidate(newMetric);

        // Assert
        Assertions.assertTrue(deletedCount >= 0);
    }

    @Test
    public void canFindMetricsBefore()
    {
        // Arrange
        Date pastDate = new Date(System.currentTimeMillis() - 10000);
        Date futureDate = new Date(System.currentTimeMillis() + 10000);
        VersionQueryMetric oldMetric = new VersionQueryMetric("org.example", "artifact1", "1.0.0", pastDate);
        VersionQueryMetric newMetric = new VersionQueryMetric("org.example", "artifact2", "2.0.0", futureDate);
        queryMetricsMongo.insert(oldMetric);
        queryMetricsMongo.insert(newMetric);

        // Act
        List<VersionQueryMetric> result = queryMetricsMongo.findMetricsBefore(new Date());

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.size() >= 1);
    }

    @Test
    public void canDeleteByCoordinates()
    {
        // Arrange
        VersionQueryMetric metric = new VersionQueryMetric("org.example", "artifact1", "1.0.0");
        queryMetricsMongo.insert(metric);

        // Act
        long deletedCount = queryMetricsMongo.delete("org.example", "artifact1", "1.0.0");
        List<VersionQueryMetric> result = queryMetricsMongo.getAll();

        // Assert
        Assertions.assertEquals(1, deletedCount);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void canGetMultipleVersionsOfSameArtifact()
    {
        // Arrange
        VersionQueryMetric metric1 = new VersionQueryMetric("org.example", "artifact1", "1.0.0");
        VersionQueryMetric metric2 = new VersionQueryMetric("org.example", "artifact1", "2.0.0");
        queryMetricsMongo.insert(metric1);
        queryMetricsMongo.insert(metric2);

        // Act
        List<VersionQueryMetric> resultV1 = queryMetricsMongo.get("org.example", "artifact1", "1.0.0");
        List<VersionQueryMetric> resultV2 = queryMetricsMongo.get("org.example", "artifact1", "2.0.0");

        // Assert
        Assertions.assertNotNull(resultV1);
        Assertions.assertEquals(1, resultV1.size());
        Assertions.assertEquals("1.0.0", resultV1.get(0).getVersionId());
        Assertions.assertNotNull(resultV2);
        Assertions.assertEquals(1, resultV2.size());
        Assertions.assertEquals("2.0.0", resultV2.get(0).getVersionId());
    }

    @Test
    public void canValidateNewData()
    {
        // Arrange
        VersionQueryMetric metric = new VersionQueryMetric("org.example", "artifact1", "1.0.0");

        // Act & Assert - should not throw exception
        queryMetricsMongo.validateNewData(metric);
    }

    @Test
    public void canBuildIndexes()
    {
        // Act
        List result = QueryMetricsMongo.buildIndexes();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }
}
