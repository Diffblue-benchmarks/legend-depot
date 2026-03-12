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

import java.util.Arrays;
import java.util.Collections;

public class TestVersionMismatch
{
    @Test
    public void testConstructorWithAllFields()
    {
        VersionMismatch mismatch = new VersionMismatch("PROD-1", "org.finos", "artifact",
                Arrays.asList("1.0.0", "2.0.0"),
                Arrays.asList("3.0.0"),
                Arrays.asList("error1"));

        Assertions.assertEquals("PROD-1", mismatch.projectId);
        Assertions.assertEquals("org.finos", mismatch.groupId);
        Assertions.assertEquals("artifact", mismatch.artifactId);
        Assertions.assertEquals(2, mismatch.versionsNotInStore.size());
        Assertions.assertEquals(1, mismatch.versionsNotInRepository.size());
        Assertions.assertEquals(1, mismatch.errors.size());
    }

    @Test
    public void testConstructorWithoutErrors()
    {
        VersionMismatch mismatch = new VersionMismatch("PROD-1", "org.finos", "artifact",
                Arrays.asList("1.0.0"),
                Collections.emptyList());

        Assertions.assertEquals(1, mismatch.versionsNotInStore.size());
        Assertions.assertTrue(mismatch.versionsNotInRepository.isEmpty());
        Assertions.assertTrue(mismatch.errors.isEmpty());
    }

    @Test
    public void testEqualityExcludesErrors()
    {
        VersionMismatch a = new VersionMismatch("PROD-1", "org.finos", "artifact",
                Arrays.asList("1.0.0"), Collections.emptyList(), Arrays.asList("error1"));
        VersionMismatch b = new VersionMismatch("PROD-1", "org.finos", "artifact",
                Arrays.asList("1.0.0"), Collections.emptyList(), Arrays.asList("different-error"));

        Assertions.assertEquals(a, b);
    }

    @Test
    public void testNotEqualForDifferentVersions()
    {
        VersionMismatch a = new VersionMismatch("PROD-1", "org.finos", "artifact",
                Arrays.asList("1.0.0"), Collections.emptyList());
        VersionMismatch b = new VersionMismatch("PROD-1", "org.finos", "artifact",
                Arrays.asList("2.0.0"), Collections.emptyList());

        Assertions.assertNotEquals(a, b);
    }
}
