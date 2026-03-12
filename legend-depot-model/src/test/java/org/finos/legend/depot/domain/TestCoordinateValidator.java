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
    public void validArtifactIdWithSimpleName()
    {
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("test"));
    }

    @Test
    public void validArtifactIdWithHyphens()
    {
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("my-artifact"));
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("my-multi-part-artifact"));
    }

    @Test
    public void validArtifactIdWithDigits()
    {
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("test123"));
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("my-artifact2"));
    }

    @Test
    public void validArtifactIdWithUnderscores()
    {
        Assertions.assertTrue(CoordinateValidator.isValidArtifactId("my_artifact"));
    }

    @Test
    public void invalidArtifactIdNull()
    {
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId(null));
    }

    @Test
    public void invalidArtifactIdEmpty()
    {
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId(""));
    }

    @Test
    public void invalidArtifactIdWithUpperCase()
    {
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("MyArtifact"));
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("singleWordNoDots"));
    }

    @Test
    public void invalidArtifactIdStartingWithDigit()
    {
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("1artifact"));
    }

    @Test
    public void invalidArtifactIdStartingWithHyphen()
    {
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("-artifact"));
    }

    @Test
    public void invalidArtifactIdWithSpaces()
    {
        Assertions.assertFalse(CoordinateValidator.isValidArtifactId("my artifact"));
    }

    @Test
    public void validGroupIdWithDots()
    {
        Assertions.assertTrue(CoordinateValidator.isValidGroupId("org.finos.legend"));
        Assertions.assertTrue(CoordinateValidator.isValidGroupId("com.example"));
    }

    @Test
    public void validGroupIdSingleWord()
    {
        Assertions.assertTrue(CoordinateValidator.isValidGroupId("example"));
    }

    @Test
    public void invalidGroupIdNull()
    {
        Assertions.assertFalse(CoordinateValidator.isValidGroupId(null));
    }

    @Test
    public void invalidGroupIdEmpty()
    {
        Assertions.assertFalse(CoordinateValidator.isValidGroupId(""));
    }

    @Test
    public void invalidGroupIdWithSpaces()
    {
        Assertions.assertFalse(CoordinateValidator.isValidGroupId("org finos"));
    }
}
