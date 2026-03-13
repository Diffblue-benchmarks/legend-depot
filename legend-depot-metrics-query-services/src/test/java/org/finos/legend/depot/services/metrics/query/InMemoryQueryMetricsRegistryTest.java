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
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

public class InMemoryQueryMetricsRegistryTest
{
    @Test
    public void testFindFirstOnEmptyRegistry()
    {
        InMemoryQueryMetricsRegistry registry = new InMemoryQueryMetricsRegistry();
        Optional<VersionQueryMetric> result = registry.findFirst();
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testRecordAndFindFirst()
    {
        InMemoryQueryMetricsRegistry registry = new InMemoryQueryMetricsRegistry();
        Date now = new Date();
        registry.record("org.finos", "legend-depot", "1.0.0", now);

        Optional<VersionQueryMetric> result = registry.findFirst();
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("org.finos", result.get().getGroupId());
        Assertions.assertEquals("legend-depot", result.get().getArtifactId());
        Assertions.assertEquals("1.0.0", result.get().getVersionId());
        Assertions.assertEquals(now, result.get().getLastQueryTime());
    }

    @Test
    public void testFindFirstRemovesItem()
    {
        InMemoryQueryMetricsRegistry registry = new InMemoryQueryMetricsRegistry();
        registry.record("org.finos", "legend-depot", "1.0.0", new Date());

        Optional<VersionQueryMetric> first = registry.findFirst();
        Assertions.assertTrue(first.isPresent());

        Optional<VersionQueryMetric> second = registry.findFirst();
        Assertions.assertFalse(second.isPresent());
    }

    @Test
    public void testFifoOrder()
    {
        InMemoryQueryMetricsRegistry registry = new InMemoryQueryMetricsRegistry();
        registry.record("org.finos", "legend-depot", "1.0.0", new Date());
        registry.record("org.finos", "legend-engine", "2.0.0", new Date());

        Optional<VersionQueryMetric> first = registry.findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertEquals("legend-depot", first.get().getArtifactId());

        Optional<VersionQueryMetric> second = registry.findFirst();
        Assertions.assertTrue(second.isPresent());
        Assertions.assertEquals("legend-engine", second.get().getArtifactId());
    }

    @Test
    public void testMultipleRecords()
    {
        InMemoryQueryMetricsRegistry registry = new InMemoryQueryMetricsRegistry();
        for (int i = 0; i < 10; i++)
        {
            registry.record("org.finos", "artifact-" + i, "1.0.0", new Date());
        }

        for (int i = 0; i < 10; i++)
        {
            Optional<VersionQueryMetric> result = registry.findFirst();
            Assertions.assertTrue(result.isPresent());
            Assertions.assertEquals("artifact-" + i, result.get().getArtifactId());
        }

        Assertions.assertFalse(registry.findFirst().isPresent());
    }
}
