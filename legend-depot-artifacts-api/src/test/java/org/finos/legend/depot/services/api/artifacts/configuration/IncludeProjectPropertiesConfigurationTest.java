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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IncludeProjectPropertiesConfigurationTest
{
    @Test
    public void canConstructWithPropertiesAndManifestProperties()
    {
        // Arrange
        List<String> properties = Arrays.asList("prop1", "prop2");
        List<String> manifestProperties = Arrays.asList("manifest1", "manifest2");

        // Act
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(properties, manifestProperties);

        // Assert
        assertNotNull(config);
        assertNotNull(config.getProperties());
        assertNotNull(config.getManifestProperties());
    }

    @Test
    public void canConstructWithEmptyLists()
    {
        // Arrange
        List<String> properties = Collections.emptyList();
        List<String> manifestProperties = Collections.emptyList();

        // Act
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(properties, manifestProperties);

        // Assert
        assertNotNull(config);
        assertNotNull(config.getProperties());
        assertNotNull(config.getManifestProperties());
    }

    @Test
    public void canConstructWithNullValues()
    {
        // Arrange
        List<String> properties = null;
        List<String> manifestProperties = null;

        // Act
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(properties, manifestProperties);

        // Assert
        assertNotNull(config);
        assertNull(config.getProperties());
        assertNull(config.getManifestProperties());
    }

    @Test
    public void canGetProperties()
    {
        // Arrange
        List<String> properties = Arrays.asList("prop1", "prop2", "prop3");
        List<String> manifestProperties = Arrays.asList("manifest1");
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(properties, manifestProperties);

        // Act
        List<String> result = config.getProperties();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("prop1", result.get(0));
        assertEquals("prop2", result.get(1));
        assertEquals("prop3", result.get(2));
    }

    @Test
    public void canGetPropertiesReturnsEmptyList()
    {
        // Arrange
        List<String> properties = Collections.emptyList();
        List<String> manifestProperties = Arrays.asList("manifest1");
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(properties, manifestProperties);

        // Act
        List<String> result = config.getProperties();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void canGetManifestProperties()
    {
        // Arrange
        List<String> properties = Arrays.asList("prop1");
        List<String> manifestProperties = Arrays.asList("manifest1", "manifest2", "manifest3");
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(properties, manifestProperties);

        // Act
        List<String> result = config.getManifestProperties();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("manifest1", result.get(0));
        assertEquals("manifest2", result.get(1));
        assertEquals("manifest3", result.get(2));
    }

    @Test
    public void canGetManifestPropertiesReturnsEmptyList()
    {
        // Arrange
        List<String> properties = Arrays.asList("prop1");
        List<String> manifestProperties = Collections.emptyList();
        IncludeProjectPropertiesConfiguration config = new IncludeProjectPropertiesConfiguration(properties, manifestProperties);

        // Act
        List<String> result = config.getManifestProperties();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
