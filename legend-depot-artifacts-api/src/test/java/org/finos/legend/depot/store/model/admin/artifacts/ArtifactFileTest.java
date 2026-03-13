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

package org.finos.legend.depot.store.model.admin.artifacts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ArtifactFileTest
{
    @Test
    public void canCreateArtifactFileWithDefaultConstructor()
    {
        ArtifactFile artifactFile = new ArtifactFile();

        assertNotNull(artifactFile);
    }

    @Test
    public void canCreateArtifactFileWithParameterizedConstructor()
    {
        String path = "/path/to/artifact";
        String checkSum = "abc123";

        ArtifactFile artifactFile = new ArtifactFile(path, checkSum);

        assertNotNull(artifactFile);
        assertEquals(path, artifactFile.getPath());
        assertEquals(checkSum, artifactFile.getCheckSum());
    }

    @Test
    public void canGetCheckSum()
    {
        String expectedCheckSum = "xyz789";
        ArtifactFile artifactFile = new ArtifactFile("/some/path", expectedCheckSum);

        String actualCheckSum = artifactFile.getCheckSum();

        assertEquals(expectedCheckSum, actualCheckSum);
    }

    @Test
    public void canSetCheckSum()
    {
        String newCheckSum = "def456";
        ArtifactFile artifactFile = new ArtifactFile();

        ArtifactFile result = artifactFile.setCheckSum(newCheckSum);

        assertEquals(newCheckSum, artifactFile.getCheckSum());
        assertEquals(artifactFile, result);
    }

    @Test
    public void canGetPath()
    {
        String expectedPath = "/expected/path";
        ArtifactFile artifactFile = new ArtifactFile(expectedPath, "checksum");

        String actualPath = artifactFile.getPath();

        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void canSetPath()
    {
        String newPath = "/new/path";
        ArtifactFile artifactFile = new ArtifactFile();

        ArtifactFile result = artifactFile.setPath(newPath);

        assertEquals(newPath, artifactFile.getPath());
        assertEquals(artifactFile, result);
    }

    @Test
    public void canGetId()
    {
        ArtifactFile artifactFile = new ArtifactFile("/path", "checksum");

        String id = artifactFile.getId();

        assertNull(id);
    }
}
