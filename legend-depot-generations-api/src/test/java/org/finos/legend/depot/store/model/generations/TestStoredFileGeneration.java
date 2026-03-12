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

package org.finos.legend.depot.store.model.generations;

import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestStoredFileGeneration
{
    @Test
    public void testConstructorAndGetters()
    {
        DepotGeneration gen = new DepotGeneration("/file.json", "content");
        StoredFileGeneration stored = new StoredFileGeneration("org.finos", "artifact", "1.0.0", "my::gen::path", "avro", gen);

        Assertions.assertEquals("org.finos", stored.getGroupId());
        Assertions.assertEquals("artifact", stored.getArtifactId());
        Assertions.assertEquals("1.0.0", stored.getVersionId());
        Assertions.assertEquals("my::gen::path", stored.getPath());
        Assertions.assertEquals("avro", stored.getType());
        Assertions.assertEquals(gen, stored.getFile());
    }

    @Test
    public void testGetIdReturnsEmptyString()
    {
        DepotGeneration gen = new DepotGeneration("/file.json", "content");
        StoredFileGeneration stored = new StoredFileGeneration("org.finos", "artifact", "1.0.0", "path", "type", gen);
        Assertions.assertEquals("", stored.getId());
    }

    @Test
    public void testEquality()
    {
        DepotGeneration gen = new DepotGeneration("/file.json", "content");
        StoredFileGeneration a = new StoredFileGeneration("org.finos", "artifact", "1.0.0", "path", "type", gen);
        StoredFileGeneration b = new StoredFileGeneration("org.finos", "artifact", "1.0.0", "path", "type", gen);
        Assertions.assertEquals(a, b);
    }

    @Test
    public void testNotEqualDifferentPath()
    {
        DepotGeneration gen = new DepotGeneration("/file.json", "content");
        StoredFileGeneration a = new StoredFileGeneration("org.finos", "artifact", "1.0.0", "path1", "type", gen);
        StoredFileGeneration b = new StoredFileGeneration("org.finos", "artifact", "1.0.0", "path2", "type", gen);
        Assertions.assertNotEquals(a, b);
    }
}
