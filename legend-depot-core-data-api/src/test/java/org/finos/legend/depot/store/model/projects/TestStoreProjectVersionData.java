package org.finos.legend.depot.store.model.projects;

import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.domain.project.dependencies.VersionDependencyReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TestStoreProjectVersionData
{
    @Test
    public void canCreateWithNoArgConstructor()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();

        Assertions.assertNull(data.getGroupId());
        Assertions.assertNull(data.getVersionId());
        Assertions.assertFalse(data.isEvicted());
        Assertions.assertNotNull(data.getVersionData());
        Assertions.assertNotNull(data.getTransitiveDependenciesReport());
    }

    @Test
    public void canCreateWithGroupArtifactVersion()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "test-artifact", "1.0.0");

        Assertions.assertEquals("org.finos", data.getGroupId());
        Assertions.assertEquals("test-artifact", data.getArtifactId());
        Assertions.assertEquals("1.0.0", data.getVersionId());
        Assertions.assertNotNull(data.getCreated());
        Assertions.assertFalse(data.isEvicted());
    }

    @Test
    public void canCreateWithAllArguments()
    {
        ProjectVersionData versionData = new ProjectVersionData();
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "test-artifact", "2.0.0", true, versionData);

        Assertions.assertEquals("org.finos", data.getGroupId());
        Assertions.assertEquals("test-artifact", data.getArtifactId());
        Assertions.assertEquals("2.0.0", data.getVersionId());
        Assertions.assertTrue(data.isEvicted());
        Assertions.assertSame(versionData, data.getVersionData());
        Assertions.assertNotNull(data.getCreated());
    }

    @Test
    public void canSetAndGetCreated()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        Date created = new Date();
        data.setCreated(created);

        Assertions.assertSame(created, data.getCreated());
    }

    @Test
    public void canSetAndGetEvicted()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        Assertions.assertFalse(data.isEvicted());

        data.setEvicted(true);
        Assertions.assertTrue(data.isEvicted());
    }

    @Test
    public void canSetAndGetVersionData()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        ProjectVersionData versionData = new ProjectVersionData();
        data.setVersionData(versionData);

        Assertions.assertSame(versionData, data.getVersionData());
    }

    @Test
    public void canSetAndGetTransitiveDependenciesReport()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        VersionDependencyReport report = new VersionDependencyReport();
        data.setTransitiveDependenciesReport(report);

        Assertions.assertSame(report, data.getTransitiveDependenciesReport());
    }

    @Test
    public void canSetAndGetUpdated()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        Date updated = new Date();
        data.setUpdated(updated);

        Assertions.assertSame(updated, data.getUpdated());
    }

    @Test
    public void testGetIdReturnsEmptyString()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "test-artifact", "1.0.0");

        Assertions.assertEquals("", data.getId());
    }

    @Test
    public void testEqualsAndHashCode()
    {
        Date now = new Date();
        ProjectVersionData sharedVersionData = new ProjectVersionData();
        VersionDependencyReport sharedReport = new VersionDependencyReport();

        StoreProjectVersionData data1 = new StoreProjectVersionData("org.finos", "test-artifact", "1.0.0");
        data1.setCreated(now);
        data1.setVersionData(sharedVersionData);
        data1.setTransitiveDependenciesReport(sharedReport);

        StoreProjectVersionData data2 = new StoreProjectVersionData("org.finos", "test-artifact", "1.0.0");
        data2.setCreated(now);
        data2.setVersionData(sharedVersionData);
        data2.setTransitiveDependenciesReport(sharedReport);

        Assertions.assertEquals(data1, data2);
        Assertions.assertEquals(data1.hashCode(), data2.hashCode());

        StoreProjectVersionData data3 = new StoreProjectVersionData("org.other", "other-artifact", "2.0.0");
        data3.setCreated(now);
        data3.setVersionData(sharedVersionData);
        data3.setTransitiveDependenciesReport(sharedReport);

        Assertions.assertNotEquals(data1, data3);
    }
}
