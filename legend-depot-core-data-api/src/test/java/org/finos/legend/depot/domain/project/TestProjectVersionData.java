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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestProjectVersionData
{

    @Test
    public void testDefaultConstructor()
    {
        ProjectVersionData data = new ProjectVersionData();

        Assertions.assertNotNull(data.getDependencies());
        Assertions.assertTrue(data.getDependencies().isEmpty());
        Assertions.assertNotNull(data.getProperties());
        Assertions.assertTrue(data.getProperties().isEmpty());
        Assertions.assertNull(data.getManifestProperties());
        Assertions.assertFalse(data.isDeprecated());
        Assertions.assertFalse(data.isExcluded());
        Assertions.assertNull(data.getExclusionReason());
    }

    @Test
    public void testTwoArgConstructor()
    {
        List<ProjectVersion> deps = Arrays.asList(new ProjectVersion("org.test", "art1", "1.0.0"));
        List<Property> props = Arrays.asList(new Property("key", "value"));

        ProjectVersionData data = new ProjectVersionData(deps, props);

        Assertions.assertEquals(1, data.getDependencies().size());
        Assertions.assertEquals(1, data.getProperties().size());
        Assertions.assertFalse(data.isDeprecated());
        Assertions.assertFalse(data.isExcluded());
    }

    @Test
    public void testFourArgConstructor()
    {
        List<ProjectVersion> deps = Arrays.asList(new ProjectVersion("org.test", "art1", "1.0.0"));
        List<Property> props = Arrays.asList(new Property("key", "value"));

        ProjectVersionData data = new ProjectVersionData(deps, props, true, true);

        Assertions.assertEquals(1, data.getDependencies().size());
        Assertions.assertEquals(1, data.getProperties().size());
        Assertions.assertTrue(data.isDeprecated());
        Assertions.assertTrue(data.isExcluded());
    }

    @Test
    public void testSetAndGetDependencies()
    {
        ProjectVersionData data = new ProjectVersionData();
        List<ProjectVersion> deps = Arrays.asList(new ProjectVersion("org.test", "art1", "1.0.0"));

        data.setDependencies(deps);

        Assertions.assertEquals(1, data.getDependencies().size());
        Assertions.assertEquals("org.test", data.getDependencies().get(0).getGroupId());
    }

    @Test
    public void testAddDependencies()
    {
        ProjectVersionData data = new ProjectVersionData();
        List<ProjectVersion> deps = Arrays.asList(
                new ProjectVersion("org.test", "art1", "1.0.0"),
                new ProjectVersion("org.test", "art2", "2.0.0")
        );

        data.addDependencies(deps);

        Assertions.assertEquals(2, data.getDependencies().size());
    }

    @Test
    public void testAddDependencyNoDuplicate()
    {
        ProjectVersionData data = new ProjectVersionData();
        ProjectVersion dep = new ProjectVersion("org.test", "art1", "1.0.0");

        data.addDependency(dep);
        Assertions.assertEquals(1, data.getDependencies().size());

        data.addDependency(dep);
        Assertions.assertEquals(1, data.getDependencies().size());

        data.addDependency(new ProjectVersion("org.test", "art2", "2.0.0"));
        Assertions.assertEquals(2, data.getDependencies().size());
    }

    @Test
    public void testSetAndGetProperties()
    {
        ProjectVersionData data = new ProjectVersionData();
        List<Property> props = Arrays.asList(new Property("key", "value"));

        data.setProperties(props);

        Assertions.assertEquals(1, data.getProperties().size());
        Assertions.assertEquals("key", data.getProperties().get(0).getPropertyName());
    }

    @Test
    public void testAddProperties()
    {
        ProjectVersionData data = new ProjectVersionData();
        List<Property> props = Arrays.asList(
                new Property("key1", "val1"),
                new Property("key2", "val2")
        );

        data.addProperties(props);
        Assertions.assertEquals(2, data.getProperties().size());

        data.addProperties(props);
        Assertions.assertEquals(2, data.getProperties().size());
    }

    @Test
    public void testSetAndGetManifestProperties()
    {
        ProjectVersionData data = new ProjectVersionData();
        Map<String, String> manifest = new HashMap<>();
        manifest.put("Built-By", "test");

        data.setManifestProperties(manifest);

        Assertions.assertNotNull(data.getManifestProperties());
        Assertions.assertEquals("test", data.getManifestProperties().get("Built-By"));
    }

    @Test
    public void testSetAndGetDeprecated()
    {
        ProjectVersionData data = new ProjectVersionData();
        Assertions.assertFalse(data.isDeprecated());

        data.setDeprecated(true);
        Assertions.assertTrue(data.isDeprecated());
    }

    @Test
    public void testSetAndGetExcluded()
    {
        ProjectVersionData data = new ProjectVersionData();
        Assertions.assertFalse(data.isExcluded());

        data.setExcluded(true);
        Assertions.assertTrue(data.isExcluded());
    }

    @Test
    public void testSetAndGetExclusionReason()
    {
        ProjectVersionData data = new ProjectVersionData();
        Assertions.assertNull(data.getExclusionReason());

        data.setExclusionReason("no longer supported");
        Assertions.assertEquals("no longer supported", data.getExclusionReason());
    }
}
