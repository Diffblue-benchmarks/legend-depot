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

public class ProjectValidatorTest
{
    @Test
    public void testValidProjectId()
    {
        Assertions.assertTrue(ProjectValidator.isValidProjectId("PROD-1"));
        Assertions.assertTrue(ProjectValidator.isValidProjectId("PROD-123"));
        Assertions.assertTrue(ProjectValidator.isValidProjectId("PROD-99999"));
    }

    @Test
    public void testInvalidProjectId()
    {
        Assertions.assertFalse(ProjectValidator.isValidProjectId(null));
        Assertions.assertFalse(ProjectValidator.isValidProjectId(""));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("PROD-"));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("PROD"));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("prod-123"));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("TEST-123"));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("PROD-abc"));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("PROD-123-extra"));
    }

    @Test
    public void testIsValidWithValidProject()
    {
        StoreProjectData projectData = new StoreProjectData("PROD-123", "org.finos.legend", "legend-depot");
        Assertions.assertTrue(ProjectValidator.isValid(projectData));
    }

    @Test
    public void testIsValidWithInvalidProjectId()
    {
        StoreProjectData projectData = new StoreProjectData("INVALID", "org.finos.legend", "legend-depot");
        Assertions.assertFalse(ProjectValidator.isValid(projectData));
    }

    @Test
    public void testIsValidWithInvalidGroupId()
    {
        StoreProjectData projectData = new StoreProjectData("PROD-123", "123invalid", "legend-depot");
        Assertions.assertFalse(ProjectValidator.isValid(projectData));
    }

    @Test
    public void testIsValidWithInvalidArtifactId()
    {
        StoreProjectData projectData = new StoreProjectData("PROD-123", "org.finos.legend", "Legend-Depot");
        Assertions.assertFalse(ProjectValidator.isValid(projectData));
    }
}
