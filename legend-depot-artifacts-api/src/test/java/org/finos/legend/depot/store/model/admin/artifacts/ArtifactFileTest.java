// Copyright 2021 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.depot.store.model.admin.artifacts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArtifactFileTest
{
    @Test
    public void testDefaultConstructor()
    {
        ArtifactFile artifactFile = new ArtifactFile();

        Assertions.assertNull(artifactFile.getCheckSum());
        Assertions.assertNull(artifactFile.getPath());
    }

    @Test
    public void testParameterizedConstructor()
    {
        ArtifactFile artifactFile = new ArtifactFile("some/path", "abc123");

        Assertions.assertEquals("some/path", artifactFile.getPath());
        Assertions.assertEquals("abc123", artifactFile.getCheckSum());
    }

    @Test
    public void testGetCheckSum()
    {
        ArtifactFile artifactFile = new ArtifactFile("path", "checksum");

        Assertions.assertEquals("checksum", artifactFile.getCheckSum());
    }

    @Test
    public void testSetCheckSum()
    {
        ArtifactFile artifactFile = new ArtifactFile();
        ArtifactFile result = artifactFile.setCheckSum("newCheckSum");

        Assertions.assertEquals("newCheckSum", artifactFile.getCheckSum());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(artifactFile, result);
    }

    @Test
    public void testGetPath()
    {
        ArtifactFile artifactFile = new ArtifactFile("myPath", "sum");

        Assertions.assertEquals("myPath", artifactFile.getPath());
    }

    @Test
    public void testSetPath()
    {
        ArtifactFile artifactFile = new ArtifactFile();
        ArtifactFile result = artifactFile.setPath("newPath");

        Assertions.assertEquals("newPath", artifactFile.getPath());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(artifactFile, result);
    }

    @Test
    public void testGetId()
    {
        ArtifactFile artifactFile = new ArtifactFile("path", "checksum");

        Assertions.assertNull(artifactFile.getId());
    }
}
