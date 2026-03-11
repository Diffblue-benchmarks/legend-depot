package org.finos.legend.depot.domain.project.dependencies;

import org.eclipse.collections.api.factory.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class TestProjectDependencyConflict
{

    @Test
    public void canCreateConflictAndGetVersions()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        Set<String> versions = Sets.mutable.with("1.0.0", "2.0.0");

        report.addConflict("org.example", "my-artifact", versions);

        Assertions.assertEquals(1, report.getConflicts().size());
        ProjectDependencyReport.ProjectDependencyConflict conflict = report.getConflicts().get(0);
        Assertions.assertEquals("org.example", conflict.getGroupId());
        Assertions.assertEquals("my-artifact", conflict.getArtifactId());
        Assertions.assertEquals(versions, conflict.getVersions());
    }
}
