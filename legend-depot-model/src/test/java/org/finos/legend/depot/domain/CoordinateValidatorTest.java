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

package org.finos.legend.depot.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoordinateValidatorTest
{

    @Test
    public void canValidateValidArtifactId()
    {

        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("valid-artifact"));
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("artifact"));
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("my-artifact-id"));
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("a1"));
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("test_artifact"));

    }

    @Test
    public void canValidateInvalidArtifactId()
    {

        Assertions.assertFalse(CoordinateValidator.isValidArtifactId(null));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId(""));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("Invalid"));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("invalid-"));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("-invalid"));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("1invalid"));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("invalid.artifact"));

    }

    @Test
    public void canValidateValidGroupId()
    {

        Assertions.assertTrue(CoordinateValidator.isValidGroupId("com.example"));
        Assertions.assertTrue(CoordinateValidator.isValidGroupId("org.finos.legend"));
        Assertions.assertTrue(CoordinateValidator.isValidGroupId("com.company.project"));
        Assertions.assertTrue(CoordinateValidator.isValidGroupId("valid"));

    }

    @Test
    public void canValidateInvalidGroupId()
    {

        Assertions.assertFalse(CoordinateValidator.isValidGroupId(null));
        Assertions.assertFalse(CoordinateValidator.isValidGroupId(""));
        Assertions.assertFalse(CoordinateValidator.isValidGroupId("1invalid"));
        Assertions.assertFalse(CoordinateValidator.isValidGroupId("invalid-name"));
        Assertions.assertFalse(CoordinateValidator.isValidGroupId("com.example."));

    }
}
