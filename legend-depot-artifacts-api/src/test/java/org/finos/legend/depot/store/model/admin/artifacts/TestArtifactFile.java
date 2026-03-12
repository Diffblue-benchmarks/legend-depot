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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArtifactFile
{
    @Test
    public void testDefaultConstructor()
    {
        ArtifactFile file = new ArtifactFile();
        Assertions.assertNull(file.getPath());
        Assertions.assertNull(file.getCheckSum());
        Assertions.assertNull(file.getId());
    }

    @Test
    public void testConstructorWithPathAndCheckSum()
    {
        ArtifactFile file = new ArtifactFile("/path/to/file.jar", "abc123");
        Assertions.assertEquals("/path/to/file.jar", file.getPath());
        Assertions.assertEquals("abc123", file.getCheckSum());
    }

    @Test
    public void testFluentSetters()
    {
        ArtifactFile file = new ArtifactFile()
                .setPath("/new/path.jar")
                .setCheckSum("def456");
        Assertions.assertEquals("/new/path.jar", file.getPath());
        Assertions.assertEquals("def456", file.getCheckSum());
    }

    @Test
    public void testGetIdReturnsNull()
    {
        ArtifactFile file = new ArtifactFile("/path", "checksum");
        Assertions.assertNull(file.getId());
    }
}
