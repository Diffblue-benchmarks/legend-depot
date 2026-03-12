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

package org.finos.legend.depot.services.api.artifacts.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

public class TestIncludeProjectPropertiesConfiguration
{
    @Test
    public void testConstructorWithBothLists()
    {
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(
                Arrays.asList("prop1", "prop2"),
                Arrays.asList("manifest1")
        );

        Assertions.assertEquals(2, config.getProperties().size());
        Assertions.assertEquals(1, config.getManifestProperties().size());
    }

    @Test
    public void testConstructorWithNulls()
    {
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(null, null);
        Assertions.assertNull(config.getProperties());
        Assertions.assertNull(config.getManifestProperties());
    }

    @Test
    public void testConstructorWithEmptyLists()
    {
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(
                Collections.emptyList(), Collections.emptyList());
        Assertions.assertTrue(config.getProperties().isEmpty());
        Assertions.assertTrue(config.getManifestProperties().isEmpty());
    }
}
