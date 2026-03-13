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

package org.finos.legend.depot.services.dependencies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestProjectDependencyGraphWalkerContext
{
    @Test
    public void canCreateDependencyProject()
    {
        ProjectDependencyGraphWalkerContext.DependencyProject project = new ProjectDependencyGraphWalkerContext.DependencyProject("com.example", "test-artifact");

        Assertions.assertNotNull(project);
        Assertions.assertEquals("com.example", project.getGroupId());
        Assertions.assertEquals("test-artifact", project.getArtifactId());
    }

    @Test
    public void canCompareDependencyProjects()
    {
        ProjectDependencyGraphWalkerContext.DependencyProject project1 = new ProjectDependencyGraphWalkerContext.DependencyProject("com.example", "test-artifact");
        ProjectDependencyGraphWalkerContext.DependencyProject project2 = new ProjectDependencyGraphWalkerContext.DependencyProject("com.example", "test-artifact");
        ProjectDependencyGraphWalkerContext.DependencyProject project3 = new ProjectDependencyGraphWalkerContext.DependencyProject("com.example", "different-artifact");

        Assertions.assertEquals(project1, project2);
        Assertions.assertNotEquals(project1, project3);
        Assertions.assertEquals(project1.hashCode(), project2.hashCode());
    }
}
