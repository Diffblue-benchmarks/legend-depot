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

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestIncludeProjectPropertiesConfiguration
{
    @Test
    void canCreateConfigurationWithPropertiesAndManifestProperties()
    {
        List<String> properties = Arrays.asList("prop1", "prop2");
        List<String> manifestProperties = Arrays.asList("manifest1", "manifest2");

        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(properties, manifestProperties);

        assertEquals(properties, config.getProperties());
        assertEquals(manifestProperties, config.getManifestProperties());
    }

    @Test
    void canCreateConfigurationWithNullValues()
    {
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(null, null);

        assertNull(config.getProperties());
        assertNull(config.getManifestProperties());
    }

    @Test
    void canCreateConfigurationWithEmptyLists()
    {
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(Collections.emptyList(), Collections.emptyList());

        assertEquals(Collections.emptyList(), config.getProperties());
        assertEquals(Collections.emptyList(), config.getManifestProperties());
    }
}
