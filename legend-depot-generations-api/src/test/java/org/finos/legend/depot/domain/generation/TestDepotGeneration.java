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

package org.finos.legend.depot.domain.generation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDepotGeneration
{

    @Test
    public void canCreateDepotGeneration()
    {
        String path = "test/path/file.txt";
        String content = "test content";

        DepotGeneration generation = new DepotGeneration(path, content);

        Assertions.assertNotNull(generation);
        Assertions.assertEquals(path, generation.getPath());
        Assertions.assertEquals(content, generation.getContent());
    }

    @Test
    public void canGetContent()
    {
        String expectedContent = "sample content";
        DepotGeneration generation = new DepotGeneration("path", expectedContent);

        String actualContent = generation.getContent();

        Assertions.assertEquals(expectedContent, actualContent);
    }

    @Test
    public void canGetPath()
    {
        String expectedPath = "sample/path.txt";
        DepotGeneration generation = new DepotGeneration(expectedPath, "content");

        String actualPath = generation.getPath();

        Assertions.assertEquals(expectedPath, actualPath);
    }

    @Test
    public void canTestEquality()
    {
        DepotGeneration generation1 = new DepotGeneration("path", "content");
        DepotGeneration generation2 = new DepotGeneration("path", "content");
        DepotGeneration generation3 = new DepotGeneration("different", "content");

        Assertions.assertTrue(generation1.equals(generation2));
        Assertions.assertFalse(generation1.equals(generation3));
        Assertions.assertFalse(generation1.equals(null));
    }

    @Test
    public void canTestHashCode()
    {
        DepotGeneration generation1 = new DepotGeneration("path", "content");
        DepotGeneration generation2 = new DepotGeneration("path", "content");
        DepotGeneration generation3 = new DepotGeneration("different", "content");

        Assertions.assertEquals(generation1.hashCode(), generation2.hashCode());
        Assertions.assertNotEquals(generation1.hashCode(), generation3.hashCode());
    }

}
