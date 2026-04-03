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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArtifactsRefreshServiceTest
{
    private static class TestArtifactsRefreshService implements ArtifactsRefreshService
    {
        boolean capturedFullUpdate;
        boolean capturedTransitive;
        String capturedGroupId;
        String capturedArtifactId;
        String capturedVersionId;
        String capturedParentEventId;

        @Override
        public MetadataNotificationResponse refreshVersionForProject(String groupId, String artifactId, String versionId, boolean fullUpdate, boolean transitive, String parentEventId)
        {
            this.capturedGroupId = groupId;
            this.capturedArtifactId = artifactId;
            this.capturedVersionId = versionId;
            this.capturedFullUpdate = fullUpdate;
            this.capturedTransitive = transitive;
            this.capturedParentEventId = parentEventId;
            return new MetadataNotificationResponse();
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
    public void testRefreshVersionForProjectDefaultMethodDelegatesToFullUpdateFalse()
    {
        TestArtifactsRefreshService service = new TestArtifactsRefreshService();
        MetadataNotificationResponse response = service.refreshVersionForProject("group1", "artifact1", "1.0.0", true, "event1");

        Assertions.assertNotNull(response);
        Assertions.assertEquals("group1", service.capturedGroupId);
        Assertions.assertEquals("artifact1", service.capturedArtifactId);
        Assertions.assertEquals("1.0.0", service.capturedVersionId);
        Assertions.assertFalse(service.capturedFullUpdate);
        Assertions.assertTrue(service.capturedTransitive);
        Assertions.assertEquals("event1", service.capturedParentEventId);
    }

    @Test
    public void testRefreshVersionForProjectWithFullUpdate()
    {
        TestArtifactsRefreshService service = new TestArtifactsRefreshService();
        MetadataNotificationResponse response = service.refreshVersionForProject("group1", "artifact1", "1.0.0", true, false, "event1");

        Assertions.assertNotNull(response);
        Assertions.assertEquals("group1", service.capturedGroupId);
        Assertions.assertEquals("artifact1", service.capturedArtifactId);
        Assertions.assertEquals("1.0.0", service.capturedVersionId);
        Assertions.assertTrue(service.capturedFullUpdate);
        Assertions.assertFalse(service.capturedTransitive);
        Assertions.assertEquals("event1", service.capturedParentEventId);
    }

    @Test
    public void testRefreshAllVersionsForProject()
    {
        TestArtifactsRefreshService service = new TestArtifactsRefreshService();
        MetadataNotificationResponse response = service.refreshAllVersionsForProject("group1", "artifact1", false, true, true, "event1");

        Assertions.assertNotNull(response);
    }

    @Test
    public void testRefreshAllVersionsForAllProjects()
    {
        TestArtifactsRefreshService service = new TestArtifactsRefreshService();
        MetadataNotificationResponse response = service.refreshAllVersionsForAllProjects(false, true, true, "event1");

        Assertions.assertNotNull(response);
    }

    @Test
    public void testRefreshDefaultSnapshotsForAllProjects()
    {
        TestArtifactsRefreshService service = new TestArtifactsRefreshService();
        MetadataNotificationResponse response = service.refreshDefaultSnapshotsForAllProjects(false, true, "event1");

        Assertions.assertNotNull(response);
    }
}
