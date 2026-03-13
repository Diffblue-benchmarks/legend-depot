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
    public void canCreateStoredFileGeneration()
    {
        DepotGeneration fileGeneration = new DepotGeneration("test/path", "test content");
        StoredFileGeneration storedFileGeneration = new StoredFileGeneration(
                "test.group",
                "test.artifact",
                "1.0.0",
                "test/path",
                "avro",
                fileGeneration
        );

        Assertions.assertNotNull(storedFileGeneration);
        Assertions.assertEquals("test/path", storedFileGeneration.getPath());
        Assertions.assertEquals("avro", storedFileGeneration.getType());
        Assertions.assertEquals(fileGeneration, storedFileGeneration.getFile());
    }

    @Test
    public void canGetId()
    {
        DepotGeneration fileGeneration = new DepotGeneration("test/path", "test content");
        StoredFileGeneration storedFileGeneration = new StoredFileGeneration(
                "test.group",
                "test.artifact",
                "1.0.0",
                "test/path",
                "avro",
                fileGeneration
        );

        String id = storedFileGeneration.getId();
        Assertions.assertNotNull(id);
        Assertions.assertEquals("", id);
    }

    @Test
    public void canGetPath()
    {
        DepotGeneration fileGeneration = new DepotGeneration("test/path", "test content");
        StoredFileGeneration storedFileGeneration = new StoredFileGeneration(
                "test.group",
                "test.artifact",
                "1.0.0",
                "test/path/file.avro",
                "avro",
                fileGeneration
        );

        String path = storedFileGeneration.getPath();
        Assertions.assertEquals("test/path/file.avro", path);
    }

    @Test
    public void canGetType()
    {
        DepotGeneration fileGeneration = new DepotGeneration("test/path", "test content");
        StoredFileGeneration storedFileGeneration = new StoredFileGeneration(
                "test.group",
                "test.artifact",
                "1.0.0",
                "test/path",
                "protobuf",
                fileGeneration
        );

        String type = storedFileGeneration.getType();
        Assertions.assertEquals("protobuf", type);
    }

    @Test
    public void canGetFile()
    {
        DepotGeneration fileGeneration = new DepotGeneration("test/path", "test content");
        StoredFileGeneration storedFileGeneration = new StoredFileGeneration(
                "test.group",
                "test.artifact",
                "1.0.0",
                "test/path",
                "avro",
                fileGeneration
        );

        DepotGeneration file = storedFileGeneration.getFile();
        Assertions.assertNotNull(file);
        Assertions.assertEquals(fileGeneration, file);
        Assertions.assertEquals("test content", file.getContent());
    }

    @Test
    public void testEquals()
    {
        DepotGeneration fileGeneration1 = new DepotGeneration("test/path", "test content");
        StoredFileGeneration storedFileGeneration1 = new StoredFileGeneration(
                "test.group",
                "test.artifact",
                "1.0.0",
                "test/path",
                "avro",
                fileGeneration1
        );

        DepotGeneration fileGeneration2 = new DepotGeneration("test/path", "test content");
        StoredFileGeneration storedFileGeneration2 = new StoredFileGeneration(
                "test.group",
                "test.artifact",
                "1.0.0",
                "test/path",
                "avro",
                fileGeneration2
        );

        Assertions.assertTrue(storedFileGeneration1.equals(storedFileGeneration2));
        Assertions.assertTrue(storedFileGeneration1.equals(storedFileGeneration1));
    }

    @Test
    public void testNotEquals()
    {
        DepotGeneration fileGeneration1 = new DepotGeneration("test/path", "test content");
        StoredFileGeneration storedFileGeneration1 = new StoredFileGeneration(
                "test.group",
                "test.artifact",
                "1.0.0",
                "test/path",
                "avro",
                fileGeneration1
        );

        DepotGeneration fileGeneration2 = new DepotGeneration("different/path", "different content");
        StoredFileGeneration storedFileGeneration2 = new StoredFileGeneration(
                "different.group",
                "different.artifact",
                "2.0.0",
                "different/path",
                "protobuf",
                fileGeneration2
        );

        Assertions.assertFalse(storedFileGeneration1.equals(storedFileGeneration2));
        Assertions.assertFalse(storedFileGeneration1.equals(null));
        Assertions.assertFalse(storedFileGeneration1.equals("not a StoredFileGeneration"));
    }

    @Test
    public void testHashCode()
    {
        DepotGeneration fileGeneration1 = new DepotGeneration("test/path", "test content");
        StoredFileGeneration storedFileGeneration1 = new StoredFileGeneration(
                "test.group",
                "test.artifact",
                "1.0.0",
                "test/path",
                "avro",
                fileGeneration1
        );

        DepotGeneration fileGeneration2 = new DepotGeneration("test/path", "test content");
        StoredFileGeneration storedFileGeneration2 = new StoredFileGeneration(
                "test.group",
                "test.artifact",
                "1.0.0",
                "test/path",
                "avro",
                fileGeneration2
        );

        int hashCode1 = storedFileGeneration1.hashCode();
        int hashCode2 = storedFileGeneration2.hashCode();

        Assertions.assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testHashCodeDifferentObjects()
    {
        DepotGeneration fileGeneration1 = new DepotGeneration("test/path", "test content");
        StoredFileGeneration storedFileGeneration1 = new StoredFileGeneration(
                "test.group",
                "test.artifact",
                "1.0.0",
                "test/path",
                "avro",
                fileGeneration1
        );

        DepotGeneration fileGeneration2 = new DepotGeneration("different/path", "different content");
        StoredFileGeneration storedFileGeneration2 = new StoredFileGeneration(
                "different.group",
                "different.artifact",
                "2.0.0",
                "different/path",
                "protobuf",
                fileGeneration2
        );

        int hashCode1 = storedFileGeneration1.hashCode();
        int hashCode2 = storedFileGeneration2.hashCode();

        Assertions.assertNotEquals(hashCode1, hashCode2);
    }

}
