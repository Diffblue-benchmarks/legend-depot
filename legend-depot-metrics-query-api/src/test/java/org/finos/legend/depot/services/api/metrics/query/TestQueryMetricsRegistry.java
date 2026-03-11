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

package org.finos.legend.depot.services.api.metrics.query;

import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestQueryMetricsRegistry
{
    @Test
    void canRecordWithDefaultDate()
    {
        String[] capturedArgs = new String[3];
        Date[] capturedDate = new Date[1];

        QueryMetricsRegistry registry = new QueryMetricsRegistry()
        {
            @Override
            public void record(String groupId, String artifactId, String versionId, Date date)
            {
                capturedArgs[0] = groupId;
                capturedArgs[1] = artifactId;
                capturedArgs[2] = versionId;
                capturedDate[0] = date;
            }

            @Override
            public Optional<VersionQueryMetric> findFirst()
            {
                return Optional.empty();
            }
        };

        Date before = new Date();
        registry.record("org.finos.test", "test-artifact", "1.0.0");
        Date after = new Date();

        assertEquals("org.finos.test", capturedArgs[0]);
        assertEquals("test-artifact", capturedArgs[1]);
        assertEquals("1.0.0", capturedArgs[2]);
        assertNotNull(capturedDate[0]);
        // The date created by the default method should be between before and after
        assert capturedDate[0].getTime() >= before.getTime();
        assert capturedDate[0].getTime() <= after.getTime();
    }
}
