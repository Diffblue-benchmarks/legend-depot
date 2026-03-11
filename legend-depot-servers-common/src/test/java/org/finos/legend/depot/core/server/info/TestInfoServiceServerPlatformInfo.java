package org.finos.legend.depot.core.server.info;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestInfoServiceServerPlatformInfo
{
    @Test
    void testNoArgConstructorViaInfoService()
    {
        InfoService infoService = new InfoService();

        InfoService.ServerPlatformInfo platform = infoService.getServerInfo().getPlatform();

        assertNotNull(platform);
        assertNull(platform.getVersion());
        assertNull(platform.getBuildTime());
        assertNull(platform.getBuildRevision());
    }

    @Test
    void testThreeArgConstructorViaReflection() throws Exception
    {
        Constructor<InfoService.ServerPlatformInfo> constructor =
                InfoService.ServerPlatformInfo.class.getDeclaredConstructor(String.class, String.class, String.class);
        constructor.setAccessible(true);

        InfoService.ServerPlatformInfo platform = constructor.newInstance("1.0.0", "2024-01-01", "abc123");

        assertEquals("1.0.0", platform.getVersion());
        assertEquals("2024-01-01", platform.getBuildTime());
        assertEquals("abc123", platform.getBuildRevision());
    }
}
