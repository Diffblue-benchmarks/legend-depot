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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtifactsRefreshServiceTest
{
    private static class TestArtifactsRefreshService implements ArtifactsRefreshService
    {
        private String capturedGroupId;
        private String capturedArtifactId;
        private String capturedVersionId;
        private boolean capturedFullUpdate;
        private boolean capturedTransitive;
        private String capturedParentEventId;

        @Override
        public MetadataNotificationResponse refreshVersionForProject(String groupId, String artifactId, String versionId, boolean fullUpdate, boolean transitive, String parentEventId)
        {
            this.capturedGroupId = groupId;
            this.capturedArtifactId = artifactId;
            this.capturedVersionId = versionId;
            this.capturedFullUpdate = fullUpdate;
            this.capturedTransitive = transitive;
            this.capturedParentEventId = parentEventId;

            MetadataNotificationResponse response = new MetadataNotificationResponse();
            response.addMessage("Refresh completed");
            return response;
        }

        @Override
        public MetadataNotificationResponse refreshAllVersionsForProject(String groupId, String artifactId, boolean fullUpdate, boolean allVersions, boolean transitive, String parentEventId)
        {
            return new MetadataNotificationResponse();
        }

        @Override
        public MetadataNotificationResponse refreshAllVersionsForAllProjects(boolean fullUpdate, boolean allVersions, boolean transitive, String parentEventId)
        {
            return new MetadataNotificationResponse();
        }

        @Override
        public MetadataNotificationResponse refreshDefaultSnapshotsForAllProjects(boolean fullUpdate, boolean transitive, String parentEventId)
        {
            return new MetadataNotificationResponse();
        }
    }

    @Test
    public void canCallDefaultRefreshVersionForProject()
    {
        TestArtifactsRefreshService service = new TestArtifactsRefreshService();

        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        boolean transitive = true;
        String parentEventId = "event-123";

        MetadataNotificationResponse result = service.refreshVersionForProject(groupId, artifactId, versionId, transitive, parentEventId);

        assertNotNull(result);
        assertEquals(groupId, service.capturedGroupId);
        assertEquals(artifactId, service.capturedArtifactId);
        assertEquals(versionId, service.capturedVersionId);
        assertFalse(service.capturedFullUpdate);
        assertTrue(service.capturedTransitive);
        assertEquals(parentEventId, service.capturedParentEventId);
    }
}
