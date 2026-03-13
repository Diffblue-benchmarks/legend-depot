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

import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

public class InMemoryQueryMetricsRegistryTest
{
    private InMemoryQueryMetricsRegistry registry;

    @BeforeEach
    public void setUp()
    {
        registry = new InMemoryQueryMetricsRegistry();
    }

    @Test
    public void canCreateRegistry()
    {
        InMemoryQueryMetricsRegistry newRegistry = new InMemoryQueryMetricsRegistry();
        Assertions.assertNotNull(newRegistry);
        Optional<VersionQueryMetric> result = newRegistry.findFirst();
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void canRecordMetric()
    {
        String groupId = "org.finos.legend";
        String artifactId = "legend-depot";
        String versionId = "1.0.0";
        Date date = new Date();

        registry.record(groupId, artifactId, versionId, date);

        Optional<VersionQueryMetric> result = registry.findFirst();
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(groupId, result.get().getGroupId());
        Assertions.assertEquals(artifactId, result.get().getArtifactId());
        Assertions.assertEquals(versionId, result.get().getVersionId());
        Assertions.assertEquals(date, result.get().getLastQueryTime());
    }

    @Test
    public void canRecordMultipleMetrics()
    {
        Date date1 = new Date();
        Date date2 = new Date();

        registry.record("group1", "artifact1", "1.0.0", date1);
        registry.record("group2", "artifact2", "2.0.0", date2);

        Optional<VersionQueryMetric> first = registry.findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertEquals("group1", first.get().getGroupId());

        Optional<VersionQueryMetric> second = registry.findFirst();
        Assertions.assertTrue(second.isPresent());
        Assertions.assertEquals("group2", second.get().getGroupId());
    }

    @Test
    public void findFirstReturnsEmptyWhenNoMetrics()
    {
        Optional<VersionQueryMetric> result = registry.findFirst();
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void findFirstReturnsEmptyAfterAllMetricsRetrieved()
    {
        registry.record("group1", "artifact1", "1.0.0", new Date());

        Optional<VersionQueryMetric> first = registry.findFirst();
        Assertions.assertTrue(first.isPresent());

        Optional<VersionQueryMetric> second = registry.findFirst();
        Assertions.assertFalse(second.isPresent());
    }
}
