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

public class StoredFileGenerationTest
{
    @Test
    public void testConstructor()
    {
        DepotGeneration gen = new DepotGeneration("/output/file.txt", "generated content");
        StoredFileGeneration stored = new StoredFileGeneration("org.finos", "legend-depot", "1.0.0", "/my/element", "avro", gen);

        Assertions.assertEquals("org.finos", stored.getGroupId());
        Assertions.assertEquals("legend-depot", stored.getArtifactId());
        Assertions.assertEquals("1.0.0", stored.getVersionId());
        Assertions.assertEquals("/my/element", stored.getPath());
        Assertions.assertEquals("avro", stored.getType());
        Assertions.assertEquals(gen, stored.getFile());
    }

    @Test
    public void testGetId()
    {
        DepotGeneration gen = new DepotGeneration("/output/file.txt", "content");
        StoredFileGeneration stored = new StoredFileGeneration("org.finos", "legend-depot", "1.0.0", "/path", "java", gen);
        Assertions.assertEquals("", stored.getId());
    }

    @Test
    public void testEquals()
    {
        DepotGeneration gen = new DepotGeneration("/output/file.txt", "content");
        StoredFileGeneration stored1 = new StoredFileGeneration("org.finos", "legend-depot", "1.0.0", "/path", "java", gen);
        StoredFileGeneration stored2 = new StoredFileGeneration("org.finos", "legend-depot", "1.0.0", "/path", "java", gen);
        Assertions.assertEquals(stored1, stored2);
    }

    @Test
    public void testNotEquals()
    {
        DepotGeneration gen = new DepotGeneration("/output/file.txt", "content");
        StoredFileGeneration stored1 = new StoredFileGeneration("org.finos", "legend-depot", "1.0.0", "/path1", "java", gen);
        StoredFileGeneration stored2 = new StoredFileGeneration("org.finos", "legend-depot", "1.0.0", "/path2", "java", gen);
        Assertions.assertNotEquals(stored1, stored2);
    }
}
