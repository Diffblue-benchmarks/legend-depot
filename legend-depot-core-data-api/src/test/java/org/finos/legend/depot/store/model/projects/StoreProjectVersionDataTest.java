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

package org.finos.legend.depot.store.model.projects;

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.domain.project.dependencies.VersionDependencyReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class StoreProjectVersionDataTest
{
    @Test
    public void testDefaultConstructor()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        Assertions.assertFalse(data.isEvicted());
        Assertions.assertNotNull(data.getVersionData());
        Assertions.assertNotNull(data.getTransitiveDependenciesReport());
    }

    @Test
    public void testThreeArgConstructor()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("org.finos", data.getGroupId());
        Assertions.assertEquals("legend-depot", data.getArtifactId());
        Assertions.assertEquals("1.0.0", data.getVersionId());
        Assertions.assertFalse(data.isEvicted());
        Assertions.assertNotNull(data.getCreated());
    }

    @Test
    public void testFiveArgConstructor()
    {
        ProjectVersionData versionData = new ProjectVersionData();
        versionData.setDeprecated(true);
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0", true, versionData);

        Assertions.assertTrue(data.isEvicted());
        Assertions.assertTrue(data.getVersionData().isDeprecated());
        Assertions.assertNotNull(data.getCreated());
    }

    @Test
    public void testEvicted()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        Assertions.assertFalse(data.isEvicted());
        data.setEvicted(true);
        Assertions.assertTrue(data.isEvicted());
    }

    @Test
    public void testSetVersionData()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        ProjectVersionData versionData = new ProjectVersionData();
        versionData.addDependency(new ProjectVersion("org.finos", "legend-sdlc", "1.0.0"));
        data.setVersionData(versionData);

        Assertions.assertEquals(1, data.getVersionData().getDependencies().size());
    }

    @Test
    public void testSetTransitiveDependenciesReport()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        VersionDependencyReport report = new VersionDependencyReport(
                new ArrayList<>(Arrays.asList(new ProjectVersion("org.finos", "legend-sdlc", "1.0.0"))),
                true);
        data.setTransitiveDependenciesReport(report);

        Assertions.assertTrue(data.getTransitiveDependenciesReport().isValid());
        Assertions.assertEquals(1, data.getTransitiveDependenciesReport().getTransitiveDependencies().size());
    }

    @Test
    public void testSetUpdated()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        Assertions.assertNull(data.getUpdated());
        Date now = new Date();
        data.setUpdated(now);
        Assertions.assertEquals(now, data.getUpdated());
    }

    @Test
    public void testGetId()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("", data.getId());
    }
}
