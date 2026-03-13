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

public class DepotGenerationTest
{
    @Test
    public void testConstructor()
    {
        DepotGeneration gen = new DepotGeneration("/path/to/file.java", "public class Foo {}");
        Assertions.assertEquals("/path/to/file.java", gen.getPath());
        Assertions.assertEquals("public class Foo {}", gen.getContent());
    }

    @Test
    public void testEquals()
    {
        DepotGeneration gen1 = new DepotGeneration("/path/to/file.java", "content1");
        DepotGeneration gen2 = new DepotGeneration("/path/to/file.java", "content1");
        Assertions.assertEquals(gen1, gen2);
    }

    @Test
    public void testNotEquals()
    {
        DepotGeneration gen1 = new DepotGeneration("/path/to/file.java", "content1");
        DepotGeneration gen2 = new DepotGeneration("/path/to/other.java", "content2");
        Assertions.assertNotEquals(gen1, gen2);
    }

    @Test
    public void testHashCode()
    {
        DepotGeneration gen1 = new DepotGeneration("/path/to/file.java", "content");
        DepotGeneration gen2 = new DepotGeneration("/path/to/file.java", "content");
        Assertions.assertEquals(gen1.hashCode(), gen2.hashCode());
    }

    @Test
    public void testGenerationConfigurationConstant()
    {
        Assertions.assertEquals("meta::pure::generation::metamodel::GenerationConfiguration", DepotGeneration.GENERATION_CONFIGURATION);
    }
}
