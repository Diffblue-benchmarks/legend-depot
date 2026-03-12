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

public class TestArtifactsRefreshPolicyConfiguration
{
    @Test
    public void testDefaultIntervalIs2Hours()
    {
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(null, null);
        Assertions.assertEquals(2 * 60 * 60 * 1000L, config.getVersionsUpdateIntervalInMillis());
    }

    @Test
    public void testCustomInterval()
    {
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(5000L, null);
        Assertions.assertEquals(5000L, config.getVersionsUpdateIntervalInMillis());
    }

    @Test
    public void testNullPropertiesConfig()
    {
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(null, null);
        Assertions.assertNull(config.getIncludeProjectPropertiesConfiguration());
    }

    @Test
    public void testWithPropertiesConfig()
    {
        IncludeProjectPropertiesConfiguration propsConfig = new IncludeProjectPropertiesConfiguration(
                Arrays.asList("prop1"), Arrays.asList("manifest1"));
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(null, propsConfig);
        Assertions.assertNotNull(config.getIncludeProjectPropertiesConfiguration());
        Assertions.assertEquals(1, config.getIncludeProjectPropertiesConfiguration().getProperties().size());
    }
}
