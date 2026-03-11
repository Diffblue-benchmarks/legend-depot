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
    public void validProjectId()
    {
        Assertions.assertTrue(ProjectValidator.isValidProjectId("PROD-1"));
        Assertions.assertTrue(ProjectValidator.isValidProjectId("PROD-12345"));
        Assertions.assertFalse(ProjectValidator.isValidProjectId(null));
        Assertions.assertFalse(ProjectValidator.isValidProjectId(""));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("PROD-"));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("PROD"));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("prod-1"));
        Assertions.assertFalse(ProjectValidator.isValidProjectId("OTHER-123"));
    }

    @Test
    public void isValidProject()
    {
        StoreProjectData validProject = new StoreProjectData("PROD-1", "examples.test", "test-artifact", null, null);
        Assertions.assertTrue(ProjectValidator.isValid(validProject));

        StoreProjectData invalidProjectId = new StoreProjectData("INVALID", "examples.test", "test-artifact", null, null);
        Assertions.assertFalse(ProjectValidator.isValid(invalidProjectId));

        StoreProjectData invalidGroupId = new StoreProjectData("PROD-1", "", "test-artifact", null, null);
        Assertions.assertFalse(ProjectValidator.isValid(invalidGroupId));

        StoreProjectData invalidArtifactId = new StoreProjectData("PROD-1", "examples.test", "", null, null);
        Assertions.assertFalse(ProjectValidator.isValid(invalidArtifactId));
    }
}
