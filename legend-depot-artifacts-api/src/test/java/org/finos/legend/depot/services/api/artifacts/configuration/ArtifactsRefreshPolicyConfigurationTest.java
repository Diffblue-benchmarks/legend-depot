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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ArtifactsRefreshPolicyConfigurationTest
{
    private static final long ONE_HOUR = 60 * 60 * 1000L;
    private static final long TWO_HOURS = 2 * ONE_HOUR;

    @Test
    public void canCreateConfigurationWithNullVersionsUpdateInterval()
    {
        IncludeProjectPropertiesConfiguration includeConfig = new IncludeProjectPropertiesConfiguration(
                Arrays.asList("prop1", "prop2"),
                Arrays.asList("manifest1", "manifest2")
        );

        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(null, includeConfig);

        assertEquals(TWO_HOURS, config.getVersionsUpdateIntervalInMillis());
        assertNotNull(config.getIncludeProjectPropertiesConfiguration());
        assertEquals(includeConfig, config.getIncludeProjectPropertiesConfiguration());
    }

    @Test
    public void canCreateConfigurationWithCustomVersionsUpdateInterval()
    {
        Long customInterval = 5 * ONE_HOUR;
        IncludeProjectPropertiesConfiguration includeConfig = new IncludeProjectPropertiesConfiguration(
                Arrays.asList("prop1"),
                Arrays.asList("manifest1")
        );

        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(customInterval, includeConfig);

        assertEquals(customInterval.longValue(), config.getVersionsUpdateIntervalInMillis());
        assertNotNull(config.getIncludeProjectPropertiesConfiguration());
    }

    @Test
    public void canCreateConfigurationWithNullIncludeProjectPropertiesConfiguration()
    {
        Long customInterval = 3 * ONE_HOUR;

        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(customInterval, null);

        assertEquals(customInterval.longValue(), config.getVersionsUpdateIntervalInMillis());
        assertNull(config.getIncludeProjectPropertiesConfiguration());
    }

    @Test
    public void canCreateConfigurationWithAllNullParameters()
    {
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(null, null);

        assertEquals(TWO_HOURS, config.getVersionsUpdateIntervalInMillis());
        assertNull(config.getIncludeProjectPropertiesConfiguration());
    }

    @Test
    public void canGetVersionsUpdateIntervalInMillis()
    {
        Long customInterval = 4 * ONE_HOUR;
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(customInterval, null);

        long result = config.getVersionsUpdateIntervalInMillis();

        assertEquals(customInterval.longValue(), result);
    }

    @Test
    public void canGetIncludeProjectPropertiesConfiguration()
    {
        IncludeProjectPropertiesConfiguration includeConfig = new IncludeProjectPropertiesConfiguration(
                Arrays.asList("testProp"),
                Arrays.asList("testManifest")
        );
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(null, includeConfig);

        IncludeProjectPropertiesConfiguration result = config.getIncludeProjectPropertiesConfiguration();

        assertEquals(includeConfig, result);
        assertNotNull(result);
    }
}
