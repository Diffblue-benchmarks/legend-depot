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
    public void testConstructorAndGetters()
    {
        DepotGeneration gen = new DepotGeneration("/path/to/file.json", "{\"key\":\"value\"}");
        Assertions.assertEquals("/path/to/file.json", gen.getPath());
        Assertions.assertEquals("{\"key\":\"value\"}", gen.getContent());
    }

    @Test
    public void testEquality()
    {
        DepotGeneration a = new DepotGeneration("/path", "content");
        DepotGeneration b = new DepotGeneration("/path", "content");
        Assertions.assertEquals(a, b);
        Assertions.assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testNotEqual()
    {
        DepotGeneration a = new DepotGeneration("/path1", "content");
        DepotGeneration b = new DepotGeneration("/path2", "content");
        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void testGenerationConfigurationConstant()
    {
        Assertions.assertEquals("meta::pure::generation::metamodel::GenerationConfiguration", DepotGeneration.GENERATION_CONFIGURATION);
    }

    @Test
    public void testNullContent()
    {
        DepotGeneration gen = new DepotGeneration("/path", null);
        Assertions.assertNull(gen.getContent());
    }
}
