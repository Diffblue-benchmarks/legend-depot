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

public class TestCoordinateValidator
{
    @Test
    public void testIsValidArtifactId()
    {
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("legend-depot-model"));
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("abc"));
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("my-artifact"));
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("a123"));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId(null));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId(""));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("123abc"));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("ABC"));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("-abc"));
    }

    @Test
    public void testIsValidGroupId()
    {
        Assertions.assertTrue(CoordinateValidator.isValidGroupId("org.finos.legend"));
        Assertions.assertTrue(CoordinateValidator.isValidGroupId("com.example"));
        Assertions.assertTrue(CoordinateValidator.isValidGroupId("mygroup"));
        Assertions.assertFalse(CoordinateValidator.isValidGroupId(null));
        Assertions.assertFalse(CoordinateValidator.isValidGroupId(""));
        Assertions.assertFalse(CoordinateValidator.isValidGroupId("123.invalid"));
    }
}
