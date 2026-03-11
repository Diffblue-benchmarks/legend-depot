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
import static org.junit.jupiter.api.Assertions.assertNull;

class TestArtifactFile
{
    @Test
    void canCreateDefaultInstance()
    {
        ArtifactFile file = new ArtifactFile();

        assertNull(file.getPath());
        assertNull(file.getCheckSum());
        assertNull(file.getId());
    }

    @Test
    void canCreateInstanceWithArguments()
    {
        ArtifactFile file = new ArtifactFile("/some/path", "abc123");

        assertEquals("/some/path", file.getPath());
        assertEquals("abc123", file.getCheckSum());
    }

    @Test
    void canSetAndGetCheckSum()
    {
        ArtifactFile file = new ArtifactFile();
        ArtifactFile result = file.setCheckSum("checksum1");

        assertEquals("checksum1", file.getCheckSum());
        assertEquals(file, result);
    }

    @Test
    void canSetAndGetPath()
    {
        ArtifactFile file = new ArtifactFile();
        ArtifactFile result = file.setPath("/new/path");

        assertEquals("/new/path", file.getPath());
        assertEquals(file, result);
    }

    @Test
    void testGetIdReturnsNull()
    {
        ArtifactFile file = new ArtifactFile("/path", "checksum");

        assertNull(file.getId());
    }
}
