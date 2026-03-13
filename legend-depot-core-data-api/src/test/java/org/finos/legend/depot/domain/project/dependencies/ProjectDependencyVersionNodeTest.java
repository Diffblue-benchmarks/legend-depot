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

package org.finos.legend.depot.domain.project.dependencies;

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Set;

public class ProjectDependencyVersionNodeTest
{
    @Test
    public void canCreateProjectDependencyVersionNode()
    {
        String groupId = "org.finos.legend";
        String artifactId = "legend-depot";
        String versionId = "1.0.0";

        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode(groupId, artifactId, versionId);

        Assertions.assertNotNull(node);
        Assertions.assertEquals(groupId, node.getGroupId());
        Assertions.assertEquals(artifactId, node.getArtifactId());
        Assertions.assertEquals(versionId, node.getVersionId());
        Assertions.assertNotNull(node.getForwardEdges());
        Assertions.assertNotNull(node.getBackEdges());
        Assertions.assertTrue(node.getForwardEdges().isEmpty());
        Assertions.assertTrue(node.getBackEdges().isEmpty());
    }

    @Test
    public void canBuildFromProjectVersion()
    {
        String groupId = "org.finos.legend";
        String artifactId = "legend-depot";
        String versionId = "1.0.0";
        ProjectVersion projectVersion = new ProjectVersion(groupId, artifactId, versionId);

        ProjectDependencyVersionNode node = ProjectDependencyVersionNode.buildFromProjectVersion(projectVersion);

        Assertions.assertNotNull(node);
        Assertions.assertEquals(groupId, node.getGroupId());
        Assertions.assertEquals(artifactId, node.getArtifactId());
        Assertions.assertEquals(versionId, node.getVersionId());
    }

    @Test
    public void canGetBackEdges()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos.legend", "legend-depot", "1.0.0");

        Set<String> backEdges = node.getBackEdges();

        Assertions.assertNotNull(backEdges);
        Assertions.assertTrue(backEdges.isEmpty());
    }

    @Test
    public void canGetForwardEdges()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos.legend", "legend-depot", "1.0.0");

        Set<String> forwardEdges = node.getForwardEdges();

        Assertions.assertNotNull(forwardEdges);
        Assertions.assertTrue(forwardEdges.isEmpty());
    }

    @Test
    public void canGetAndSetProjectId()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos.legend", "legend-depot", "1.0.0");
        String projectId = "test-project-id";

        node.setProjectId(projectId);

        Assertions.assertEquals(projectId, node.getProjectId());
    }

    @Test
    public void canGetGav()
    {
        String groupId = "org.finos.legend";
        String artifactId = "legend-depot";
        String versionId = "1.0.0";
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode(groupId, artifactId, versionId);

        String gav = node.getGav();

        Assertions.assertEquals("org.finos.legend:legend-depot:1.0.0", gav);
    }

    @Test
    public void canGetCoordinates()
    {
        String groupId = "org.finos.legend";
        String artifactId = "legend-depot";
        String versionId = "1.0.0";
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode(groupId, artifactId, versionId);

        String coordinates = node.getCoordinates();

        Assertions.assertEquals("org.finos.legend:legend-depot", coordinates);
    }

    @Test
    public void canGetId()
    {
        String groupId = "org.finos.legend";
        String artifactId = "legend-depot";
        String versionId = "1.0.0";
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode(groupId, artifactId, versionId);

        String id = node.getId();

        Assertions.assertEquals("org.finos.legend:legend-depot:1.0.0", id);
    }

    @Test
    public void testProjectIdDefaultValue()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos.legend", "legend-depot", "1.0.0");

        String projectId = node.getProjectId();

        Assertions.assertNull(projectId);
    }
}
