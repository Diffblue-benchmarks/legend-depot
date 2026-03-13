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

public class VersionMismatchTest
{

    @Test
    public void canCreateVersionMismatchWithAllParameters()
    {

        List<String> versionsNotInStore = Arrays.asList("1.0.0", "1.1.0");
        List<String> versionsNotInRepo = Arrays.asList("2.0.0", "2.1.0");
        List<String> errors = Arrays.asList("error1", "error2");

        VersionMismatch versionMismatch = new VersionMismatch(
                "example.project",
                "example.group",
                "example-artifact",
                versionsNotInStore,
                versionsNotInRepo,
                errors
        );

        Assertions.assertNotNull(versionMismatch);
        Assertions.assertEquals("example.project", versionMismatch.projectId);
        Assertions.assertEquals("example.group", versionMismatch.groupId);
        Assertions.assertEquals("example-artifact", versionMismatch.artifactId);
        Assertions.assertEquals(2, versionMismatch.versionsNotInStore.size());
        Assertions.assertEquals(2, versionMismatch.versionsNotInRepository.size());
        Assertions.assertEquals(2, versionMismatch.errors.size());

    }

    @Test
    public void canCreateVersionMismatchWithoutErrors()
    {

        List<String> versionsNotInStore = Arrays.asList("1.0.0");
        List<String> versionsNotInRepo = Arrays.asList("2.0.0");

        VersionMismatch versionMismatch = new VersionMismatch(
                "example.project",
                "example.group",
                "example-artifact",
                versionsNotInStore,
                versionsNotInRepo
        );

        Assertions.assertNotNull(versionMismatch);
        Assertions.assertEquals("example.project", versionMismatch.projectId);
        Assertions.assertEquals("example.group", versionMismatch.groupId);
        Assertions.assertEquals("example-artifact", versionMismatch.artifactId);
        Assertions.assertEquals(1, versionMismatch.versionsNotInStore.size());
        Assertions.assertEquals(1, versionMismatch.versionsNotInRepository.size());
        Assertions.assertEquals(0, versionMismatch.errors.size());

    }

    @Test
    public void testEquals()
    {

        List<String> versionsNotInStore1 = Arrays.asList("1.0.0", "1.1.0");
        List<String> versionsNotInRepo1 = Arrays.asList("2.0.0", "2.1.0");
        List<String> errors1 = Arrays.asList("error1");

        VersionMismatch versionMismatch1 = new VersionMismatch(
                "example.project",
                "example.group",
                "example-artifact",
                versionsNotInStore1,
                versionsNotInRepo1,
                errors1
        );

        VersionMismatch versionMismatch2 = new VersionMismatch(
                "example.project",
                "example.group",
                "example-artifact",
                Arrays.asList("1.0.0", "1.1.0"),
                Arrays.asList("2.0.0", "2.1.0"),
                Arrays.asList("error1")
        );

        VersionMismatch versionMismatch3 = new VersionMismatch(
                "different.project",
                "example.group",
                "example-artifact",
                versionsNotInStore1,
                versionsNotInRepo1,
                errors1
        );

        Assertions.assertTrue(versionMismatch1.equals(versionMismatch2));
        Assertions.assertFalse(versionMismatch1.equals(versionMismatch3));
        Assertions.assertFalse(versionMismatch1.equals(null));

    }

    @Test
    public void testHashCode()
    {

        List<String> versionsNotInStore = Arrays.asList("1.0.0", "1.1.0");
        List<String> versionsNotInRepo = Arrays.asList("2.0.0", "2.1.0");
        List<String> errors = Arrays.asList("error1");

        VersionMismatch versionMismatch1 = new VersionMismatch(
                "example.project",
                "example.group",
                "example-artifact",
                versionsNotInStore,
                versionsNotInRepo,
                errors
        );

        VersionMismatch versionMismatch2 = new VersionMismatch(
                "example.project",
                "example.group",
                "example-artifact",
                Arrays.asList("1.0.0", "1.1.0"),
                Arrays.asList("2.0.0", "2.1.0"),
                Arrays.asList("error1")
        );

        Assertions.assertEquals(versionMismatch1.hashCode(), versionMismatch2.hashCode());

    }
}
