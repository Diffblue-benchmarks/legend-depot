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

package org.finos.legend.depot.services.api.artifacts.refresh;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParentEventTest
{
    @Test
    public void canBuildWithParentEventId()
    {
        String groupId = "org.example";
        String artifactId = "my-artifact";
        String versionId = "1.0.0";
        String parentEventId = "existing-parent-event-id";

        String result = ParentEvent.build(groupId, artifactId, versionId, parentEventId);

        assertNotNull(result);
        assertEquals(parentEventId, result);
    }

    @Test
    public void canBuildWithoutParentEventId()
    {
        String groupId = "org.example";
        String artifactId = "my-artifact";
        String versionId = "1.0.0";

        String result = ParentEvent.build(groupId, artifactId, versionId, null);

        assertNotNull(result);
        assertEquals("org.example_my-artifact_1.0.0", result);
    }
}
