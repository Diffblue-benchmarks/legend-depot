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

package org.finos.legend.depot.domain.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectVersionDataTest
{
    @Test
    public void testDefaultConstructor()
    {
        ProjectVersionData data = new ProjectVersionData();
        Assertions.assertTrue(data.getDependencies().isEmpty());
        Assertions.assertTrue(data.getProperties().isEmpty());
        Assertions.assertNull(data.getManifestProperties());
        Assertions.assertFalse(data.isDeprecated());
        Assertions.assertFalse(data.isExcluded());
        Assertions.assertNull(data.getExclusionReason());
    }

    @Test
    public void testAddDependency()
    {
        ProjectVersionData data = new ProjectVersionData();
        ProjectVersion dep = new ProjectVersion("org.finos", "legend-sdlc", "1.0.0");
        data.addDependency(dep);
        Assertions.assertEquals(1, data.getDependencies().size());
        Assertions.assertEquals(dep, data.getDependencies().get(0));
    }

    @Test
    public void testAddDuplicateDependency()
    {
        ProjectVersionData data = new ProjectVersionData();
        ProjectVersion dep = new ProjectVersion("org.finos", "legend-sdlc", "1.0.0");
        data.addDependency(dep);
        data.addDependency(dep);
        Assertions.assertEquals(1, data.getDependencies().size());
    }

    @Test
    public void testAddDependencies()
    {
        ProjectVersionData data = new ProjectVersionData();
        ProjectVersion dep1 = new ProjectVersion("org.finos", "legend-sdlc", "1.0.0");
        ProjectVersion dep2 = new ProjectVersion("org.finos", "legend-engine", "2.0.0");
        data.addDependencies(Arrays.asList(dep1, dep2));
        Assertions.assertEquals(2, data.getDependencies().size());
    }

    @Test
    public void testAddPropertiesNoDuplicates()
    {
        ProjectVersionData data = new ProjectVersionData();
        Property prop1 = new Property("key1", "value1");
        Property prop2 = new Property("key2", "value2");
        data.addProperties(Arrays.asList(prop1, prop2));
        Assertions.assertEquals(2, data.getProperties().size());

        data.addProperties(Collections.singletonList(prop1));
        Assertions.assertEquals(2, data.getProperties().size());
    }

    @Test
    public void testSetManifestProperties()
    {
        ProjectVersionData data = new ProjectVersionData();
        Map<String, String> manifest = new HashMap<>();
        manifest.put("Build-Version", "1.0.0");
        data.setManifestProperties(manifest);
        Assertions.assertEquals("1.0.0", data.getManifestProperties().get("Build-Version"));
    }

    @Test
    public void testExclusionFields()
    {
        ProjectVersionData data = new ProjectVersionData();
        data.setExcluded(true);
        data.setExclusionReason("Security vulnerability");
        Assertions.assertTrue(data.isExcluded());
        Assertions.assertEquals("Security vulnerability", data.getExclusionReason());
    }

    @Test
    public void testDeprecatedField()
    {
        ProjectVersionData data = new ProjectVersionData();
        data.setDeprecated(true);
        Assertions.assertTrue(data.isDeprecated());
    }

    @Test
    public void testConstructorWithDependenciesAndProperties()
    {
        List<ProjectVersion> deps = new ArrayList<>();
        deps.add(new ProjectVersion("org.finos", "legend-sdlc", "1.0.0"));
        List<Property> props = new ArrayList<>();
        props.add(new Property("key", "value"));

        ProjectVersionData data = new ProjectVersionData(deps, props);
        Assertions.assertEquals(1, data.getDependencies().size());
        Assertions.assertEquals(1, data.getProperties().size());
        Assertions.assertFalse(data.isDeprecated());
        Assertions.assertFalse(data.isExcluded());
    }

    @Test
    public void testConstructorWithAllFields()
    {
        List<ProjectVersion> deps = new ArrayList<>();
        List<Property> props = new ArrayList<>();

        ProjectVersionData data = new ProjectVersionData(deps, props, true, true);
        Assertions.assertTrue(data.isDeprecated());
        Assertions.assertTrue(data.isExcluded());
    }
}
