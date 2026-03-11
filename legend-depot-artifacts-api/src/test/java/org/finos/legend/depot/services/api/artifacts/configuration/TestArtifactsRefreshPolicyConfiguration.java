package org.finos.legend.depot.services.api.artifacts.configuration;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestArtifactsRefreshPolicyConfiguration
{
    @Test
    void canCreateWithNullVersionsUpdateInterval()
    {
        IncludeProjectPropertiesConfiguration propsConfig =
                new IncludeProjectPropertiesConfiguration(Arrays.asList("prop1"), Arrays.asList("manifest1"));

        ArtifactsRefreshPolicyConfiguration config =
                new ArtifactsRefreshPolicyConfiguration(null, propsConfig);

        long twoHoursInMillis = 2 * 60 * 60 * 1000L;
        assertEquals(twoHoursInMillis, config.getVersionsUpdateIntervalInMillis());
        assertEquals(propsConfig, config.getIncludeProjectPropertiesConfiguration());
    }

    @Test
    void canCreateWithCustomVersionsUpdateInterval()
    {
        long customInterval = 5000L;
        ArtifactsRefreshPolicyConfiguration config =
                new ArtifactsRefreshPolicyConfiguration(customInterval, null);

        assertEquals(customInterval, config.getVersionsUpdateIntervalInMillis());
        assertNull(config.getIncludeProjectPropertiesConfiguration());
    }

    @Test
    void canCreateWithAllParameters()
    {
        long customInterval = 10000L;
        IncludeProjectPropertiesConfiguration propsConfig =
                new IncludeProjectPropertiesConfiguration(Arrays.asList("a", "b"), Arrays.asList("c"));

        ArtifactsRefreshPolicyConfiguration config =
                new ArtifactsRefreshPolicyConfiguration(customInterval, propsConfig);

        assertEquals(customInterval, config.getVersionsUpdateIntervalInMillis());
        assertEquals(propsConfig, config.getIncludeProjectPropertiesConfiguration());
    }
}
