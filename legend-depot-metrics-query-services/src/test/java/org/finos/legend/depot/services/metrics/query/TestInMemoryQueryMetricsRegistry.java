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

public class TestInMemoryQueryMetricsRegistry
{
    @Test
    public void testRecordAndFindFirst()
    {
        InMemoryQueryMetricsRegistry registry = new InMemoryQueryMetricsRegistry();
        registry.record("org.finos", "artifact", "1.0.0", new Date());

        Optional<VersionQueryMetric> result = registry.findFirst();
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("org.finos", result.get().getGroupId());
        Assertions.assertEquals("artifact", result.get().getArtifactId());
        Assertions.assertEquals("1.0.0", result.get().getVersionId());
    }

    @Test
    public void testFindFirstOnEmptyRegistryReturnsEmpty()
    {
        InMemoryQueryMetricsRegistry registry = new InMemoryQueryMetricsRegistry();
        Assertions.assertFalse(registry.findFirst().isPresent());
    }

    @Test
    public void testFindFirstConsumesElement()
    {
        InMemoryQueryMetricsRegistry registry = new InMemoryQueryMetricsRegistry();
        registry.record("org.finos", "artifact", "1.0.0", new Date());

        Assertions.assertTrue(registry.findFirst().isPresent());
        Assertions.assertFalse(registry.findFirst().isPresent());
    }

    @Test
    public void testMultipleRecordsAreFIFO()
    {
        InMemoryQueryMetricsRegistry registry = new InMemoryQueryMetricsRegistry();
        registry.record("org.finos", "first", "1.0.0", new Date());
        registry.record("org.finos", "second", "2.0.0", new Date());

        Optional<VersionQueryMetric> first = registry.findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertEquals("first", first.get().getArtifactId());

        Optional<VersionQueryMetric> second = registry.findFirst();
        Assertions.assertTrue(second.isPresent());
        Assertions.assertEquals("second", second.get().getArtifactId());
    }
}
