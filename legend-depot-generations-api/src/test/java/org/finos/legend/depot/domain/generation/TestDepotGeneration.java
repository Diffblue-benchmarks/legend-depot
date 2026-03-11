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
    public void canGetPath()
    {
        DepotGeneration generation = new DepotGeneration("/test/path", "some content");
        Assertions.assertEquals("/test/path", generation.getPath());
    }

    @Test
    public void canTestEquality()
    {
        DepotGeneration generation1 = new DepotGeneration("/test/path", "some content");
        DepotGeneration generation2 = new DepotGeneration("/test/path", "some content");
        DepotGeneration generation3 = new DepotGeneration("/other/path", "other content");

        Assertions.assertEquals(generation1, generation2);
        Assertions.assertNotEquals(generation1, generation3);
        Assertions.assertNotEquals(generation1, null);
        Assertions.assertNotEquals(generation1, "a string");
    }

    @Test
    public void canComputeHashCode()
    {
        DepotGeneration generation1 = new DepotGeneration("/test/path", "some content");
        DepotGeneration generation2 = new DepotGeneration("/test/path", "some content");
        DepotGeneration generation3 = new DepotGeneration("/other/path", "other content");

        Assertions.assertEquals(generation1.hashCode(), generation2.hashCode());
        Assertions.assertNotEquals(generation1.hashCode(), generation3.hashCode());
    }
}
