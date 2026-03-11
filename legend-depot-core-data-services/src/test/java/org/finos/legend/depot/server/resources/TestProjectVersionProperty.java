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

package org.finos.legend.depot.server.resources;

import org.finos.legend.depot.server.resources.projects.ProjectsResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestProjectVersionProperty
{
    @Test
    public void canCreateWithNoArgConstructor()
    {
        ProjectsResource.ProjectVersionProperty property = new ProjectsResource.ProjectVersionProperty();
        Assertions.assertNull(property.getPropertyName());
        Assertions.assertNull(property.getValue());
        Assertions.assertNull(property.getProjectVersionId());
    }

    @Test
    public void canCreateWithAllArgsConstructor()
    {
        ProjectsResource.ProjectVersionProperty property = new ProjectsResource.ProjectVersionProperty("prop1", "val1", "ver1");
        Assertions.assertEquals("prop1", property.getPropertyName());
        Assertions.assertEquals("val1", property.getValue());
        Assertions.assertEquals("ver1", property.getProjectVersionId());
    }

    @Test
    public void canGetProjectVersionId()
    {
        ProjectsResource.ProjectVersionProperty property = new ProjectsResource.ProjectVersionProperty("name", "value", "1.0.0");
        Assertions.assertEquals("1.0.0", property.getProjectVersionId());
    }

    @Test
    public void canGetPropertyName()
    {
        ProjectsResource.ProjectVersionProperty property = new ProjectsResource.ProjectVersionProperty("myProp", "myVal", "2.0.0");
        Assertions.assertEquals("myProp", property.getPropertyName());
    }

    @Test
    public void canGetValue()
    {
        ProjectsResource.ProjectVersionProperty property = new ProjectsResource.ProjectVersionProperty("myProp", "myVal", "2.0.0");
        Assertions.assertEquals("myVal", property.getValue());
    }

    @Test
    public void testEqualsWithSameValues()
    {
        ProjectsResource.ProjectVersionProperty property1 = new ProjectsResource.ProjectVersionProperty("prop", "val", "ver");
        ProjectsResource.ProjectVersionProperty property2 = new ProjectsResource.ProjectVersionProperty("prop", "val", "ver");
        Assertions.assertEquals(property1, property2);
    }

    @Test
    public void testEqualsWithDifferentValues()
    {
        ProjectsResource.ProjectVersionProperty property1 = new ProjectsResource.ProjectVersionProperty("prop1", "val1", "ver1");
        ProjectsResource.ProjectVersionProperty property2 = new ProjectsResource.ProjectVersionProperty("prop2", "val2", "ver2");
        Assertions.assertNotEquals(property1, property2);
    }

    @Test
    public void testHashCodeConsistency()
    {
        ProjectsResource.ProjectVersionProperty property1 = new ProjectsResource.ProjectVersionProperty("prop", "val", "ver");
        ProjectsResource.ProjectVersionProperty property2 = new ProjectsResource.ProjectVersionProperty("prop", "val", "ver");
        Assertions.assertEquals(property1.hashCode(), property2.hashCode());
    }

    @Test
    public void testHashCodeDiffers()
    {
        ProjectsResource.ProjectVersionProperty property1 = new ProjectsResource.ProjectVersionProperty("prop1", "val1", "ver1");
        ProjectsResource.ProjectVersionProperty property2 = new ProjectsResource.ProjectVersionProperty("prop2", "val2", "ver2");
        Assertions.assertNotEquals(property1.hashCode(), property2.hashCode());
    }
}
