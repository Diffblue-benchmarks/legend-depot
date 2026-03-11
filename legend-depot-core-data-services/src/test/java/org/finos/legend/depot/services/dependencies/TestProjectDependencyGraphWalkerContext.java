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

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TestProjectDependencyGraphWalkerContext
{
    @Test
    public void canGetProjectDataDependenciesNonTransitive()
    {
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();

        ProjectVersion dep1 = new ProjectVersion("examples.metadata", "dep-one", "1.0.0");
        ProjectVersion dep2 = new ProjectVersion("examples.metadata", "dep-two", "2.0.0");

        ProjectVersionData versionData = new ProjectVersionData();
        versionData.setDependencies(Arrays.asList(dep1, dep2));

        StoreProjectVersionData storeData = new StoreProjectVersionData("examples.metadata", "test-project", "1.0.0");
        storeData.setVersionData(versionData);

        ProjectVersion projectVersion = new ProjectVersion("examples.metadata", "test-project", "1.0.0");
        context.getProjectDataPutIfAbsent(projectVersion.getGroupId(), projectVersion.getArtifactId(), projectVersion.getVersionId(), () -> storeData);

        List<ProjectVersion> pvList = Collections.singletonList(projectVersion);
        Set<ProjectVersion> result = context.getProjectDataDependencies(pvList, false);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(dep1));
        Assertions.assertTrue(result.contains(dep2));
    }
}
