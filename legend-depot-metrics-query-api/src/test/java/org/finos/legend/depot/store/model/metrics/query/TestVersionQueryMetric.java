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

package org.finos.legend.depot.store.model.metrics.query;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestVersionQueryMetric
{
    @Test
    void canCreateWithNoArgConstructor()
    {
        VersionQueryMetric metric = new VersionQueryMetric();
        assertNull(metric.getGroupId());
        assertNull(metric.getArtifactId());
        assertNull(metric.getVersionId());
        assertNull(metric.getLastQueryTime());
        assertEquals("", metric.getId());
    }

    @Test
    void canCreateWithThreeArgConstructor()
    {
        VersionQueryMetric metric = new VersionQueryMetric("org.finos", "depot", "1.0.0");
        assertEquals("org.finos", metric.getGroupId());
        assertEquals("depot", metric.getArtifactId());
        assertEquals("1.0.0", metric.getVersionId());
        assertNotNull(metric.getLastQueryTime());
    }

    @Test
    void canCreateWithFourArgConstructor()
    {
        Date queryTime = new Date(1000L);
        VersionQueryMetric metric = new VersionQueryMetric("org.finos", "depot", "2.0.0", queryTime);
        assertEquals("org.finos", metric.getGroupId());
        assertEquals("depot", metric.getArtifactId());
        assertEquals("2.0.0", metric.getVersionId());
        assertEquals(queryTime, metric.getLastQueryTime());
    }

    @Test
    void canGetId()
    {
        VersionQueryMetric metric = new VersionQueryMetric("org.finos", "depot", "1.0.0");
        assertEquals("", metric.getId());
    }
}
