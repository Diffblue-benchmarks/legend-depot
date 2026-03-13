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

package org.finos.legend.depot.domain.version;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.finos.legend.depot.domain.version.VersionValidator.BRANCH_SNAPSHOT;

public class TestVersionValidator
{
    @Test
    public void testVersionsAreCorrect()
    {
        Assertions.assertTrue(VersionValidator.isValid("1.1.1"));
        Assertions.assertTrue(VersionValidator.isValid(BRANCH_SNAPSHOT("master")));
        Assertions.assertFalse(VersionValidator.isValid("jkwhfkjasf-jhdfjks"));
        Assertions.assertTrue(VersionValidator.isValid("my-SNAPSHOT"));
    }

    @Test
    public void testVersionAliasRecognizesValidAliases()
    {
        Assertions.assertTrue(VersionValidator.isVersionAlias("latest"));
        Assertions.assertTrue(VersionValidator.isVersionAlias("head"));
        Assertions.assertTrue(VersionValidator.isVersionAlias("LATEST"));
        Assertions.assertTrue(VersionValidator.isVersionAlias("HEAD"));
        Assertions.assertTrue(VersionValidator.isVersionAlias("Latest"));
        Assertions.assertTrue(VersionValidator.isVersionAlias("Head"));
    }

    @Test
    public void testVersionAliasRejectsInvalidAliases()
    {
        Assertions.assertFalse(VersionValidator.isVersionAlias("invalid"));
        Assertions.assertFalse(VersionValidator.isVersionAlias("master"));
        Assertions.assertFalse(VersionValidator.isVersionAlias("1.0.0"));
        Assertions.assertFalse(VersionValidator.isVersionAlias(""));
        Assertions.assertFalse(VersionValidator.isVersionAlias("master-SNAPSHOT"));
    }

}
