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

import org.finos.legend.depot.domain.notifications.MetadataNotificationResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestArtifactsRefreshService
{
    @Test
    void testRefreshVersionForProjectDefaultDelegatesToFullOverload()
    {
        MetadataNotificationResponse expectedResponse = new MetadataNotificationResponse().addMessage("done");
        boolean[] capturedFullUpdate = {true};
        String[] capturedGroupId = {null};
        String[] capturedArtifactId = {null};
        String[] capturedVersionId = {null};
        boolean[] capturedTransitive = {false};
        String[] capturedParentEventId = {null};

        ArtifactsRefreshService service = new ArtifactsRefreshService()
        {
            @Override
            public MetadataNotificationResponse refreshVersionForProject(String groupId, String artifactId, String versionId, boolean fullUpdate, boolean transitive, String parentEventId)
            {
                capturedGroupId[0] = groupId;
                capturedArtifactId[0] = artifactId;
                capturedVersionId[0] = versionId;
                capturedFullUpdate[0] = fullUpdate;
                capturedTransitive[0] = transitive;
                capturedParentEventId[0] = parentEventId;
                return expectedResponse;
            }

            @Override
            public MetadataNotificationResponse refreshAllVersionsForProject(String groupId, String artifactId, boolean fullUpdate, boolean allVersions, boolean transitive, String parentEventId)
            {
                return null;
            }

            @Override
            public MetadataNotificationResponse refreshAllVersionsForAllProjects(boolean fullUpdate, boolean allVersions, boolean transitive, String parentEventId)
            {
                return null;
            }

            @Override
            public MetadataNotificationResponse refreshDefaultSnapshotsForAllProjects(boolean fullUpdate, boolean transitive, String parentEventId)
            {
                return null;
            }
        };

        MetadataNotificationResponse result = service.refreshVersionForProject("org.finos", "my-artifact", "1.0.0", true, "event-123");

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        assertEquals("org.finos", capturedGroupId[0]);
        assertEquals("my-artifact", capturedArtifactId[0]);
        assertEquals("1.0.0", capturedVersionId[0]);
        assertFalse(capturedFullUpdate[0]);
        assertEquals(true, capturedTransitive[0]);
        assertEquals("event-123", capturedParentEventId[0]);
    }
}
