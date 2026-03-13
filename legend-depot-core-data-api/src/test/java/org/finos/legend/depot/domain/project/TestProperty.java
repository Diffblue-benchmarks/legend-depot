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
    public void canCreatePropertyWithNoArgConstructor()
    {
        Property property = new Property();
        Assertions.assertNotNull(property);
    }

    @Test
    public void canCreatePropertyWithArguments()
    {
        Property property = new Property("testProperty", "testValue");
        Assertions.assertNotNull(property);
    }

    @Test
    public void canGetPropertyName()
    {
        Property property = new Property("testProperty", "testValue");
        Assertions.assertEquals("testProperty", property.getPropertyName());
    }

    @Test
    public void canGetValue()
    {
        Property property = new Property("testProperty", "testValue");
        Assertions.assertEquals("testValue", property.getValue());
    }

    @Test
    public void testEquals()
    {
        Property property1 = new Property("name", "value");
        Property property2 = new Property("name", "value");
        Property property3 = new Property("different", "value");

        Assertions.assertTrue(property1.equals(property2));
        Assertions.assertFalse(property1.equals(property3));
        Assertions.assertFalse(property1.equals(null));
        Assertions.assertTrue(property1.equals(property1));
    }

    @Test
    public void testHashCode()
    {
        Property property1 = new Property("name", "value");
        Property property2 = new Property("name", "value");
        Property property3 = new Property("different", "value");

        Assertions.assertEquals(property1.hashCode(), property2.hashCode());
        Assertions.assertNotEquals(property1.hashCode(), property3.hashCode());
    }
}
