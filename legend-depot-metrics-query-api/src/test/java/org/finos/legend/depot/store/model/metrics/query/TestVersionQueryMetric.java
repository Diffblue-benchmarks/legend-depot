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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TestVersionQueryMetric
{
    @Test
    public void testDefaultConstructor()
    {
        VersionQueryMetric metric = new VersionQueryMetric();
        Assertions.assertNull(metric.getGroupId());
        Assertions.assertNull(metric.getArtifactId());
        Assertions.assertNull(metric.getVersionId());
        Assertions.assertNull(metric.getLastQueryTime());
    }

    @Test
    public void testThreeArgConstructor()
    {
        VersionQueryMetric metric = new VersionQueryMetric("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("org.finos", metric.getGroupId());
        Assertions.assertEquals("artifact", metric.getArtifactId());
        Assertions.assertEquals("1.0.0", metric.getVersionId());
        Assertions.assertNotNull(metric.getLastQueryTime());
    }

    @Test
    public void testFourArgConstructor()
    {
        Date queryTime = new Date(1000L);
        VersionQueryMetric metric = new VersionQueryMetric("org.finos", "artifact", "2.0.0", queryTime);
        Assertions.assertEquals("org.finos", metric.getGroupId());
        Assertions.assertEquals("artifact", metric.getArtifactId());
        Assertions.assertEquals("2.0.0", metric.getVersionId());
        Assertions.assertEquals(queryTime, metric.getLastQueryTime());
    }

    @Test
    public void testGetIdReturnsEmptyString()
    {
        VersionQueryMetric metric = new VersionQueryMetric("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("", metric.getId());
    }
}
