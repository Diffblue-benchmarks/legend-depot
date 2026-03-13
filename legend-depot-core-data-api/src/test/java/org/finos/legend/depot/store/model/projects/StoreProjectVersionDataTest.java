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

import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.domain.project.dependencies.VersionDependencyReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Date;

public class StoreProjectVersionDataTest
{
    @Test
    public void canCreateWithDefaultConstructor()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        Assertions.assertNotNull(data);
    }

    @Test
    public void canCreateWithGavParameters()
    {
        String groupId = "org.finos.legend";
        String artifactId = "legend-depot";
        String versionId = "1.0.0";

        StoreProjectVersionData data = new StoreProjectVersionData(groupId, artifactId, versionId);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(groupId, data.getGroupId());
        Assertions.assertEquals(artifactId, data.getArtifactId());
        Assertions.assertEquals(versionId, data.getVersionId());
        Assertions.assertNotNull(data.getCreated());
    }

    @Test
    public void canCreateWithAllParameters()
    {
        String groupId = "org.finos.legend";
        String artifactId = "legend-depot";
        String versionId = "1.0.0";
        boolean evicted = true;
        ProjectVersionData versionData = new ProjectVersionData();

        StoreProjectVersionData data = new StoreProjectVersionData(groupId, artifactId, versionId, evicted, versionData);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(groupId, data.getGroupId());
        Assertions.assertEquals(artifactId, data.getArtifactId());
        Assertions.assertEquals(versionId, data.getVersionId());
        Assertions.assertTrue(data.isEvicted());
        Assertions.assertEquals(versionData, data.getVersionData());
        Assertions.assertNotNull(data.getCreated());
    }

    @Test
    public void canSetAndGetCreated()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        Date created = new Date();

        data.setCreated(created);

        Assertions.assertEquals(created, data.getCreated());
    }

    @Test
    public void canSetAndGetEvicted()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();

        data.setEvicted(true);
        Assertions.assertTrue(data.isEvicted());

        data.setEvicted(false);
        Assertions.assertFalse(data.isEvicted());
    }

    @Test
    public void canSetAndGetVersionData()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        ProjectVersionData versionData = new ProjectVersionData();

        data.setVersionData(versionData);

        Assertions.assertEquals(versionData, data.getVersionData());
    }

    @Test
    public void canSetAndGetTransitiveDependenciesReport()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        VersionDependencyReport report = new VersionDependencyReport();

        data.setTransitiveDependenciesReport(report);

        Assertions.assertEquals(report, data.getTransitiveDependenciesReport());
    }

    @Test
    public void canSetAndGetUpdated()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        Date updated = new Date();

        data.setUpdated(updated);

        Assertions.assertEquals(updated, data.getUpdated());
    }

    @Test
    public void canGetId()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();

        String id = data.getId();

        Assertions.assertNotNull(id);
        Assertions.assertEquals("", id);
    }

    @Test
    public void testEquals()
    {
        StoreProjectVersionData data1 = new StoreProjectVersionData();
        data1.setVersionId("1.0.0");
        Date sameDate = new Date();
        data1.setCreated(sameDate);
        data1.setUpdated(sameDate);
        ProjectVersionData versionData = new ProjectVersionData();
        data1.setVersionData(versionData);
        VersionDependencyReport report = new VersionDependencyReport();
        data1.setTransitiveDependenciesReport(report);

        StoreProjectVersionData data2 = new StoreProjectVersionData();
        data2.setVersionId("1.0.0");
        data2.setCreated(sameDate);
        data2.setUpdated(sameDate);
        data2.setVersionData(versionData);
        data2.setTransitiveDependenciesReport(report);

        StoreProjectVersionData data3 = new StoreProjectVersionData();
        data3.setVersionId("2.0.0");
        data3.setCreated(sameDate);
        data3.setUpdated(sameDate);
        data3.setVersionData(versionData);
        data3.setTransitiveDependenciesReport(report);

        Assertions.assertEquals(data1, data2);
        Assertions.assertNotEquals(data1, data3);
        Assertions.assertNotEquals(data1, null);
        Assertions.assertNotEquals(data1, new Object());
    }

    @Test
    public void testHashCode()
    {
        StoreProjectVersionData data1 = new StoreProjectVersionData();
        data1.setVersionId("1.0.0");
        Date sameDate = new Date();
        data1.setCreated(sameDate);
        data1.setUpdated(sameDate);
        ProjectVersionData versionData = new ProjectVersionData();
        data1.setVersionData(versionData);
        VersionDependencyReport report = new VersionDependencyReport();
        data1.setTransitiveDependenciesReport(report);

        StoreProjectVersionData data2 = new StoreProjectVersionData();
        data2.setVersionId("1.0.0");
        data2.setCreated(sameDate);
        data2.setUpdated(sameDate);
        data2.setVersionData(versionData);
        data2.setTransitiveDependenciesReport(report);

        StoreProjectVersionData data3 = new StoreProjectVersionData();
        data3.setVersionId("2.0.0");
        data3.setCreated(sameDate);
        data3.setUpdated(sameDate);
        data3.setVersionData(versionData);
        data3.setTransitiveDependenciesReport(report);

        Assertions.assertEquals(data1.hashCode(), data2.hashCode());
        Assertions.assertNotEquals(data1.hashCode(), data3.hashCode());
    }
}
