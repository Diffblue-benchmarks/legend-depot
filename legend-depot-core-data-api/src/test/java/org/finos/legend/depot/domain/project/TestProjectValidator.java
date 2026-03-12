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

import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestProjectValidator
{
    @Test
    public void testValidProjectId()
    {
        Assertions.assertTrue(ProjectValidator.isValidProjectId("PROD-1"));
        Assertions.assertTrue(ProjectValidator.isValidProjectId("PROD-12345"));
    }

    @Test
    public void testInvalidProjectIdNull()
    {
        Assertions.assertFalse(ProjectValidator.isValidProjectId(null));
    }

    @Test
    public void testInvalidProjectIdWrongPrefix()
    {
        Assertions.assertFalse(ProjectValidator.isValidProjectId("TEST-1"));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("prod-1"));
    }

    @Test
    public void testInvalidProjectIdNoNumber()
    {
        Assertions.assertFalse(ProjectValidator.isValidProjectId("PROD-"));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("PROD-abc"));
    }

    @Test
    public void testInvalidProjectIdEmpty()
    {
        Assertions.assertFalse(ProjectValidator.isValidProjectId(""));
    }

    @Test
    public void testValidProject()
    {
        StoreProjectData project = new StoreProjectData("PROD-123", "org.finos", "my-artifact");
        Assertions.assertTrue(ProjectValidator.isValid(project));
    }

    @Test
    public void testInvalidProjectBadProjectId()
    {
        StoreProjectData project = new StoreProjectData("INVALID", "org.finos", "my-artifact");
        Assertions.assertFalse(ProjectValidator.isValid(project));
    }

    @Test
    public void testInvalidProjectBadGroupId()
    {
        StoreProjectData project = new StoreProjectData("PROD-123", "invalid group", "my-artifact");
        Assertions.assertFalse(ProjectValidator.isValid(project));
    }

    @Test
    public void testInvalidProjectBadArtifactId()
    {
        StoreProjectData project = new StoreProjectData("PROD-123", "org.finos", "InvalidArtifact");
        Assertions.assertFalse(ProjectValidator.isValid(project));
    }
}
