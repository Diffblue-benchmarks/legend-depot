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

public class ArtifactsRefreshPolicyConfigurationTest
{
    private static final long ONE_HOUR = 60 * 60 * 1000L;

    @Test
    public void testConstructorWithNullVersionsUpdateInterval()
    {
        IncludeProjectPropertiesConfiguration props = new IncludeProjectPropertiesConfiguration(null, null);
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(null, props);

        Assertions.assertEquals(2 * ONE_HOUR, config.getVersionsUpdateIntervalInMillis());
        Assertions.assertEquals(props, config.getIncludeProjectPropertiesConfiguration());
    }

    @Test
    public void testConstructorWithProvidedVersionsUpdateInterval()
    {
        long interval = 5000L;
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(interval, null);

        Assertions.assertEquals(interval, config.getVersionsUpdateIntervalInMillis());
        Assertions.assertNull(config.getIncludeProjectPropertiesConfiguration());
    }

    @Test
    public void testGetVersionsUpdateIntervalInMillis()
    {
        long interval = 12345L;
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(interval, null);

        Assertions.assertEquals(interval, config.getVersionsUpdateIntervalInMillis());
    }

    @Test
    public void testGetIncludeProjectPropertiesConfiguration()
    {
        IncludeProjectPropertiesConfiguration props = new IncludeProjectPropertiesConfiguration(
                Arrays.asList("prop1", "prop2"),
                Arrays.asList("manifest1"));
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(null, props);

        Assertions.assertNotNull(config.getIncludeProjectPropertiesConfiguration());
        Assertions.assertEquals(props, config.getIncludeProjectPropertiesConfiguration());
    }
}
