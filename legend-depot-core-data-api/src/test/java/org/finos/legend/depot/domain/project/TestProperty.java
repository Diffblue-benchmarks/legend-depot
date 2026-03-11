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

package org.finos.legend.depot.domain.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestProperty
{
    @Test
    public void canCreateDefaultProperty()
    {
        Property property = new Property();
        Assertions.assertNull(property.getPropertyName());
        Assertions.assertNull(property.getValue());
    }

    @Test
    public void canCreatePropertyWithValues()
    {
        Property property = new Property("name", "value");
        Assertions.assertEquals("name", property.getPropertyName());
        Assertions.assertEquals("value", property.getValue());
    }

    @Test
    public void testEqualsWithSameValues()
    {
        Property property1 = new Property("name", "value");
        Property property2 = new Property("name", "value");
        Assertions.assertEquals(property1, property2);
    }

    @Test
    public void testEqualsWithDifferentValues()
    {
        Property property1 = new Property("name1", "value1");
        Property property2 = new Property("name2", "value2");
        Assertions.assertNotEquals(property1, property2);
    }

    @Test
    public void testHashCodeConsistency()
    {
        Property property1 = new Property("name", "value");
        Property property2 = new Property("name", "value");
        Assertions.assertEquals(property1.hashCode(), property2.hashCode());
    }

    @Test
    public void testHashCodeDiffers()
    {
        Property property1 = new Property("name1", "value1");
        Property property2 = new Property("name2", "value2");
        Assertions.assertNotEquals(property1.hashCode(), property2.hashCode());
    }
}
