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

public class VersionMismatchTest
{
    @Test
    public void testConstructorWithoutErrors()
    {
        VersionMismatch mismatch = new VersionMismatch("PROD-1", "org.finos", "legend-depot",
                Arrays.asList("1.0.0", "2.0.0"),
                Arrays.asList("3.0.0"));
        Assertions.assertEquals("PROD-1", mismatch.projectId);
        Assertions.assertEquals("org.finos", mismatch.groupId);
        Assertions.assertEquals("legend-depot", mismatch.artifactId);
        Assertions.assertEquals(2, mismatch.versionsNotInStore.size());
        Assertions.assertEquals(1, mismatch.versionsNotInRepository.size());
        Assertions.assertTrue(mismatch.errors.isEmpty());
    }

    @Test
    public void testConstructorWithErrors()
    {
        VersionMismatch mismatch = new VersionMismatch("PROD-1", "org.finos", "legend-depot",
                Collections.singletonList("1.0.0"),
                Collections.emptyList(),
                Arrays.asList("error1", "error2"));
        Assertions.assertEquals(2, mismatch.errors.size());
        Assertions.assertEquals("error1", mismatch.errors.get(0));
    }

    @Test
    public void testEqualsIgnoresErrors()
    {
        VersionMismatch mismatch1 = new VersionMismatch("PROD-1", "org.finos", "legend-depot",
                Arrays.asList("1.0.0"),
                Collections.emptyList(),
                Arrays.asList("error1"));
        VersionMismatch mismatch2 = new VersionMismatch("PROD-1", "org.finos", "legend-depot",
                Arrays.asList("1.0.0"),
                Collections.emptyList(),
                Arrays.asList("different-error"));
        Assertions.assertEquals(mismatch1, mismatch2);
    }

    @Test
    public void testNotEquals()
    {
        VersionMismatch mismatch1 = new VersionMismatch("PROD-1", "org.finos", "legend-depot",
                Arrays.asList("1.0.0"),
                Collections.emptyList());
        VersionMismatch mismatch2 = new VersionMismatch("PROD-2", "org.finos", "legend-depot",
                Arrays.asList("1.0.0"),
                Collections.emptyList());
        Assertions.assertNotEquals(mismatch1, mismatch2);
    }

    @Test
    public void testEmptyLists()
    {
        VersionMismatch mismatch = new VersionMismatch("PROD-1", "org.finos", "legend-depot",
                Collections.emptyList(),
                Collections.emptyList());
        Assertions.assertTrue(mismatch.versionsNotInStore.isEmpty());
        Assertions.assertTrue(mismatch.versionsNotInRepository.isEmpty());
    }
}
