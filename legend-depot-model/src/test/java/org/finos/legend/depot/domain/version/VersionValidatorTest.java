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

public class VersionValidatorTest
{
    @Test
    public void testValidReleaseVersion()
    {
        Assertions.assertTrue(VersionValidator.isValidReleaseVersion("1.0.0"));
        Assertions.assertTrue(VersionValidator.isValidReleaseVersion("0.0.0"));
        Assertions.assertTrue(VersionValidator.isValidReleaseVersion("10.20.30"));
        Assertions.assertTrue(VersionValidator.isValidReleaseVersion("2.65.1"));
    }

    @Test
    public void testInvalidReleaseVersion()
    {
        Assertions.assertFalse(VersionValidator.isValidReleaseVersion("1.0"));
        Assertions.assertFalse(VersionValidator.isValidReleaseVersion("abc"));
        Assertions.assertFalse(VersionValidator.isValidReleaseVersion("1.0.0-SNAPSHOT"));
        Assertions.assertFalse(VersionValidator.isValidReleaseVersion(""));
    }

    @Test
    public void testSnapshotVersion()
    {
        Assertions.assertTrue(VersionValidator.isSnapshotVersion("master-SNAPSHOT"));
        Assertions.assertTrue(VersionValidator.isSnapshotVersion("1.0.0-SNAPSHOT"));
        Assertions.assertTrue(VersionValidator.isSnapshotVersion("feature-branch-SNAPSHOT"));
        Assertions.assertFalse(VersionValidator.isSnapshotVersion("1.0.0"));
        Assertions.assertFalse(VersionValidator.isSnapshotVersion("SNAPSHOT"));
    }

    @Test
    public void testIsValid()
    {
        Assertions.assertTrue(VersionValidator.isValid("1.0.0"));
        Assertions.assertTrue(VersionValidator.isValid("master-SNAPSHOT"));
        Assertions.assertFalse(VersionValidator.isValid(null));
        Assertions.assertFalse(VersionValidator.isValid(""));
        Assertions.assertFalse(VersionValidator.isValid("invalid"));
    }

    @Test
    public void testVersionAlias()
    {
        Assertions.assertTrue(VersionValidator.isVersionAlias("latest"));
        Assertions.assertTrue(VersionValidator.isVersionAlias("head"));
        Assertions.assertTrue(VersionValidator.isVersionAlias("LATEST"));
        Assertions.assertTrue(VersionValidator.isVersionAlias("HEAD"));
        Assertions.assertFalse(VersionValidator.isVersionAlias("1.0.0"));
        Assertions.assertFalse(VersionValidator.isVersionAlias("unknown"));
    }

    @Test
    public void testBranchSnapshot()
    {
        Assertions.assertEquals("master-SNAPSHOT", VersionValidator.BRANCH_SNAPSHOT("master"));
        Assertions.assertEquals("feature-SNAPSHOT", VersionValidator.BRANCH_SNAPSHOT("feature"));
    }
}
