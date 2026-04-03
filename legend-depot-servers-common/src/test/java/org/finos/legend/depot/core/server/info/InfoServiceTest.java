// Copyright 2023 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.depot.core.server.info;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InfoServiceTest
{
    @Test
    public void testGetServerInfo()
    {
        InfoService infoService = new InfoService();
        InfoService.ServerInfo serverInfo = infoService.getServerInfo();
        assertNotNull(serverInfo);
        assertNotNull(serverInfo.getServerTimeZone());
    }

    @Test
    public void testServerPlatformInfoWithArgs() throws Exception
    {
        Constructor<InfoService.ServerPlatformInfo> ctor = InfoService.ServerPlatformInfo.class
                .getDeclaredConstructor(String.class, String.class, String.class);
        ctor.setAccessible(true);
        InfoService.ServerPlatformInfo info = ctor.newInstance("1.0.0", "2023-01-01T00:00:00Z", "abc123");

        assertEquals("1.0.0", info.getVersion());
        assertEquals("2023-01-01T00:00:00Z", info.getBuildTime());
        assertEquals("abc123", info.getBuildRevision());
    }

    @Test
    public void testServerPlatformInfoNoArgs() throws Exception
    {
        Constructor<InfoService.ServerPlatformInfo> ctor = InfoService.ServerPlatformInfo.class
                .getDeclaredConstructor();
        ctor.setAccessible(true);
        InfoService.ServerPlatformInfo info = ctor.newInstance();

        assertNull(info.getVersion());
        assertNull(info.getBuildTime());
        assertNull(info.getBuildRevision());
    }
}
