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
import java.util.List;

public class TestVersionMismatch
{
    @Test
    public void canCreateWithAllParameters()
    {
        List<String> notInStore = Arrays.asList("1.0.0", "2.0.0");
        List<String> notInRepo = Arrays.asList("3.0.0");
        List<String> errors = Arrays.asList("some error");

        VersionMismatch mismatch = new VersionMismatch("PROD-1", "org.finos", "artifact", notInStore, notInRepo, errors);

        Assertions.assertEquals("PROD-1", mismatch.projectId);
        Assertions.assertEquals("org.finos", mismatch.groupId);
        Assertions.assertEquals("artifact", mismatch.artifactId);
        Assertions.assertEquals(notInStore, mismatch.versionsNotInStore);
        Assertions.assertEquals(notInRepo, mismatch.versionsNotInRepository);
        Assertions.assertEquals(errors, mismatch.errors);
    }

    @Test
    public void canCreateWithoutErrors()
    {
        List<String> notInStore = Arrays.asList("1.0.0");
        List<String> notInRepo = Arrays.asList("2.0.0");

        VersionMismatch mismatch = new VersionMismatch("PROD-2", "org.test", "test-artifact", notInStore, notInRepo);

        Assertions.assertEquals("PROD-2", mismatch.projectId);
        Assertions.assertEquals("org.test", mismatch.groupId);
        Assertions.assertEquals("test-artifact", mismatch.artifactId);
        Assertions.assertEquals(notInStore, mismatch.versionsNotInStore);
        Assertions.assertEquals(notInRepo, mismatch.versionsNotInRepository);
        Assertions.assertTrue(mismatch.errors.isEmpty());
    }

    @Test
    public void testEqualsSameValues()
    {
        VersionMismatch a = new VersionMismatch("PROD-1", "org.finos", "artifact", Arrays.asList("1.0.0"), Arrays.asList("2.0.0"), Collections.emptyList());
        VersionMismatch b = new VersionMismatch("PROD-1", "org.finos", "artifact", Arrays.asList("1.0.0"), Arrays.asList("2.0.0"), Collections.emptyList());

        Assertions.assertEquals(a, b);
    }

    @Test
    public void testEqualsDifferentValues()
    {
        VersionMismatch a = new VersionMismatch("PROD-1", "org.finos", "artifact", Arrays.asList("1.0.0"), Arrays.asList("2.0.0"));
        VersionMismatch b = new VersionMismatch("PROD-2", "org.finos", "artifact", Arrays.asList("1.0.0"), Arrays.asList("2.0.0"));

        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void testHashCodeConsistentWithEquals()
    {
        VersionMismatch a = new VersionMismatch("PROD-1", "org.finos", "artifact", Arrays.asList("1.0.0"), Arrays.asList("2.0.0"));
        VersionMismatch b = new VersionMismatch("PROD-1", "org.finos", "artifact", Arrays.asList("1.0.0"), Arrays.asList("2.0.0"));

        Assertions.assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testEqualsIgnoresErrors()
    {
        VersionMismatch a = new VersionMismatch("PROD-1", "org.finos", "artifact", Arrays.asList("1.0.0"), Arrays.asList("2.0.0"), Arrays.asList("error1"));
        VersionMismatch b = new VersionMismatch("PROD-1", "org.finos", "artifact", Arrays.asList("1.0.0"), Arrays.asList("2.0.0"), Arrays.asList("error2"));

        Assertions.assertEquals(a, b);
    }
}
