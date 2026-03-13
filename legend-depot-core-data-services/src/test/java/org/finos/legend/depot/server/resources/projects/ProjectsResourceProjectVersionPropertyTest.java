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

package org.finos.legend.depot.server.resources.projects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProjectsResourceProjectVersionPropertyTest
{
    @Test
    public void canCreateWithDefaultConstructor()
    {
        ProjectsResource.ProjectVersionProperty property = new ProjectsResource.ProjectVersionProperty();
        Assertions.assertNotNull(property);
    }

    @Test
    public void canCreateWithParameterizedConstructor()
    {
        ProjectsResource.ProjectVersionProperty property = new ProjectsResource.ProjectVersionProperty("propName", "propValue", "versionId");
        Assertions.assertNotNull(property);
        Assertions.assertEquals("propName", property.getPropertyName());
        Assertions.assertEquals("propValue", property.getValue());
        Assertions.assertEquals("versionId", property.getProjectVersionId());
    }

    @Test
    public void canGetProjectVersionId()
    {
        ProjectsResource.ProjectVersionProperty property = new ProjectsResource.ProjectVersionProperty("propName", "propValue", "versionId");
        Assertions.assertEquals("versionId", property.getProjectVersionId());
    }

    @Test
    public void canGetPropertyName()
    {
        ProjectsResource.ProjectVersionProperty property = new ProjectsResource.ProjectVersionProperty("propName", "propValue", "versionId");
        Assertions.assertEquals("propName", property.getPropertyName());
    }

    @Test
    public void canGetValue()
    {
        ProjectsResource.ProjectVersionProperty property = new ProjectsResource.ProjectVersionProperty("propName", "propValue", "versionId");
        Assertions.assertEquals("propValue", property.getValue());
    }

    @Test
    public void testEquals()
    {
        ProjectsResource.ProjectVersionProperty property1 = new ProjectsResource.ProjectVersionProperty("propName", "propValue", "versionId");
        ProjectsResource.ProjectVersionProperty property2 = new ProjectsResource.ProjectVersionProperty("propName", "propValue", "versionId");
        ProjectsResource.ProjectVersionProperty property3 = new ProjectsResource.ProjectVersionProperty("different", "values", "here");

        Assertions.assertTrue(property1.equals(property1));
        Assertions.assertTrue(property1.equals(property2));
        Assertions.assertFalse(property1.equals(property3));
        Assertions.assertFalse(property1.equals(null));
        Assertions.assertFalse(property1.equals("not a ProjectVersionProperty"));
    }

    @Test
    public void testHashCode()
    {
        ProjectsResource.ProjectVersionProperty property1 = new ProjectsResource.ProjectVersionProperty("propName", "propValue", "versionId");
        ProjectsResource.ProjectVersionProperty property2 = new ProjectsResource.ProjectVersionProperty("propName", "propValue", "versionId");

        Assertions.assertEquals(property1.hashCode(), property2.hashCode());
    }
}
