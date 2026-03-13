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
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectValidatorTest
{
    @Test
    public void cannotInstantiatePrivateConstructor() throws Exception
    {
        Constructor<ProjectValidator> constructor = ProjectValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void canValidateValidProjectData()
    {
        StoreProjectData projectData = new StoreProjectData("PROD-123", "org.example", "my-artifact");

        boolean result = ProjectValidator.isValid(projectData);

        assertTrue(result);
    }

    @Test
    public void canDetectInvalidProjectId()
    {
        StoreProjectData projectData = new StoreProjectData("invalid-id", "org.example", "my-artifact");

        boolean result = ProjectValidator.isValid(projectData);

        assertFalse(result);
    }

    @Test
    public void canDetectInvalidGroupId()
    {
        StoreProjectData projectData = new StoreProjectData("PROD-123", "", "my-artifact");

        boolean result = ProjectValidator.isValid(projectData);

        assertFalse(result);
    }

    @Test
    public void canDetectInvalidArtifactId()
    {
        StoreProjectData projectData = new StoreProjectData("PROD-123", "org.example", "");

        boolean result = ProjectValidator.isValid(projectData);

        assertFalse(result);
    }

    @Test
    public void canValidateValidProjectId()
    {
        boolean result = ProjectValidator.isValidProjectId("PROD-123");

        assertTrue(result);
    }

    @Test
    public void canValidateMultiDigitProjectId()
    {
        boolean result = ProjectValidator.isValidProjectId("PROD-9876543210");

        assertTrue(result);
    }

    @Test
    public void canDetectNullProjectId()
    {
        boolean result = ProjectValidator.isValidProjectId(null);

        assertFalse(result);
    }

    @Test
    public void canDetectProjectIdWithoutPrefix()
    {
        boolean result = ProjectValidator.isValidProjectId("123");

        assertFalse(result);
    }

    @Test
    public void canDetectProjectIdWithoutNumber()
    {
        boolean result = ProjectValidator.isValidProjectId("PROD-");

        assertFalse(result);
    }

    @Test
    public void canDetectProjectIdWithInvalidPrefix()
    {
        boolean result = ProjectValidator.isValidProjectId("INVALID-123");

        assertFalse(result);
    }

    @Test
    public void canDetectProjectIdWithNonNumericSuffix()
    {
        boolean result = ProjectValidator.isValidProjectId("PROD-ABC");

        assertFalse(result);
    }

    @Test
    public void canDetectEmptyProjectId()
    {
        boolean result = ProjectValidator.isValidProjectId("");

        assertFalse(result);
    }
}
