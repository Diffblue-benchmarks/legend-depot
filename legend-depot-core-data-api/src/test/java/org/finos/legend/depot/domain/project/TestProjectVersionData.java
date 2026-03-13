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
    public void canCreateProjectVersionDataWithNoArgConstructor()
    {
        ProjectVersionData data = new ProjectVersionData();
        Assertions.assertNotNull(data);
    }

    @Test
    public void canCreateProjectVersionDataWithDependenciesAndProperties()
    {
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("group1", "artifact1", "1.0.0"),
                new ProjectVersion("group2", "artifact2", "2.0.0")
        );
        List<Property> properties = Arrays.asList(
                new Property("prop1", "value1"),
                new Property("prop2", "value2")
        );

        ProjectVersionData data = new ProjectVersionData(dependencies, properties);
        Assertions.assertNotNull(data);
        Assertions.assertEquals(2, data.getDependencies().size());
        Assertions.assertEquals(2, data.getProperties().size());
    }

    @Test
    public void canCreateProjectVersionDataWithAllArguments()
    {
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("group1", "artifact1", "1.0.0")
        );
        List<Property> properties = Arrays.asList(
                new Property("prop1", "value1")
        );

        ProjectVersionData data = new ProjectVersionData(dependencies, properties, true, true);
        Assertions.assertNotNull(data);
        Assertions.assertEquals(1, data.getDependencies().size());
        Assertions.assertEquals(1, data.getProperties().size());
        Assertions.assertTrue(data.isDeprecated());
        Assertions.assertTrue(data.isExcluded());
    }

    @Test
    public void canGetDependencies()
    {
        ProjectVersionData data = new ProjectVersionData();
        List<ProjectVersion> dependencies = data.getDependencies();
        Assertions.assertNotNull(dependencies);
    }

    @Test
    public void canAddDependencies()
    {
        ProjectVersionData data = new ProjectVersionData();
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("group1", "artifact1", "1.0.0"),
                new ProjectVersion("group2", "artifact2", "2.0.0")
        );

        data.addDependencies(dependencies);
        Assertions.assertEquals(2, data.getDependencies().size());
    }

    @Test
    public void canAddDependency()
    {
        ProjectVersionData data = new ProjectVersionData();
        ProjectVersion dependency = new ProjectVersion("group1", "artifact1", "1.0.0");

        data.addDependency(dependency);
        Assertions.assertEquals(1, data.getDependencies().size());
    }

    @Test
    public void cannotAddDuplicateDependency()
    {
        ProjectVersionData data = new ProjectVersionData();
        ProjectVersion dependency = new ProjectVersion("group1", "artifact1", "1.0.0");

        data.addDependency(dependency);
        data.addDependency(dependency);
        Assertions.assertEquals(1, data.getDependencies().size());
    }

    @Test
    public void canSetDependencies()
    {
        ProjectVersionData data = new ProjectVersionData();
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("group1", "artifact1", "1.0.0")
        );

        data.setDependencies(dependencies);
        Assertions.assertEquals(1, data.getDependencies().size());
    }

    @Test
    public void canGetProperties()
    {
        ProjectVersionData data = new ProjectVersionData();
        List<Property> properties = data.getProperties();
        Assertions.assertNotNull(properties);
    }

    @Test
    public void canSetProperties()
    {
        ProjectVersionData data = new ProjectVersionData();
        List<Property> properties = Arrays.asList(
                new Property("prop1", "value1")
        );

        data.setProperties(properties);
        Assertions.assertEquals(1, data.getProperties().size());
    }

    @Test
    public void canAddProperties()
    {
        ProjectVersionData data = new ProjectVersionData();
        List<Property> properties = Arrays.asList(
                new Property("prop1", "value1"),
                new Property("prop2", "value2")
        );

        data.addProperties(properties);
        Assertions.assertEquals(2, data.getProperties().size());
    }

    @Test
    public void cannotAddDuplicateProperties()
    {
        ProjectVersionData data = new ProjectVersionData();
        Property property = new Property("prop1", "value1");
        List<Property> properties = Arrays.asList(property);

        data.addProperties(properties);
        data.addProperties(properties);
        Assertions.assertEquals(1, data.getProperties().size());
    }

    @Test
    public void canGetManifestProperties()
    {
        ProjectVersionData data = new ProjectVersionData();
        Map<String, String> manifestProperties = data.getManifestProperties();
        Assertions.assertNull(manifestProperties);
    }

    @Test
    public void canSetManifestProperties()
    {
        ProjectVersionData data = new ProjectVersionData();
        Map<String, String> manifestProperties = new HashMap<>();
        manifestProperties.put("key1", "value1");
        manifestProperties.put("key2", "value2");

        data.setManifestProperties(manifestProperties);
        Assertions.assertNotNull(data.getManifestProperties());
        Assertions.assertEquals(2, data.getManifestProperties().size());
    }

    @Test
    public void canGetDeprecated()
    {
        ProjectVersionData data = new ProjectVersionData();
        boolean deprecated = data.isDeprecated();
        Assertions.assertFalse(deprecated);
    }

    @Test
    public void canSetDeprecated()
    {
        ProjectVersionData data = new ProjectVersionData();
        data.setDeprecated(true);
        Assertions.assertTrue(data.isDeprecated());
    }

    @Test
    public void canGetExcluded()
    {
        ProjectVersionData data = new ProjectVersionData();
        boolean excluded = data.isExcluded();
        Assertions.assertFalse(excluded);
    }

    @Test
    public void canSetExcluded()
    {
        ProjectVersionData data = new ProjectVersionData();
        data.setExcluded(true);
        Assertions.assertTrue(data.isExcluded());
    }

    @Test
    public void canGetExclusionReason()
    {
        ProjectVersionData data = new ProjectVersionData();
        String exclusionReason = data.getExclusionReason();
        Assertions.assertNull(exclusionReason);
    }

    @Test
    public void canSetExclusionReason()
    {
        ProjectVersionData data = new ProjectVersionData();
        data.setExclusionReason("test reason");
        Assertions.assertEquals("test reason", data.getExclusionReason());
    }
}
