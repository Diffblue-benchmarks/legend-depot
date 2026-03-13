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
    @Test
    public void testDefaultVersionsUpdateInterval()
    {
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(null, null);
        Assertions.assertEquals(2 * 60 * 60 * 1000L, config.getVersionsUpdateIntervalInMillis());
        Assertions.assertNull(config.getIncludeProjectPropertiesConfiguration());
    }

    @Test
    public void testCustomVersionsUpdateInterval()
    {
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(30000L, null);
        Assertions.assertEquals(30000L, config.getVersionsUpdateIntervalInMillis());
    }

    @Test
    public void testWithIncludeProjectPropertiesConfiguration()
    {
        IncludeProjectPropertiesConfiguration propsConfig = new IncludeProjectPropertiesConfiguration(
                Arrays.asList("prop1", "prop2"),
                Arrays.asList("manifest1"));
        ArtifactsRefreshPolicyConfiguration config = new ArtifactsRefreshPolicyConfiguration(null, propsConfig);

        Assertions.assertNotNull(config.getIncludeProjectPropertiesConfiguration());
        Assertions.assertEquals(2, config.getIncludeProjectPropertiesConfiguration().getProperties().size());
        Assertions.assertEquals(1, config.getIncludeProjectPropertiesConfiguration().getManifestProperties().size());
    }
}
