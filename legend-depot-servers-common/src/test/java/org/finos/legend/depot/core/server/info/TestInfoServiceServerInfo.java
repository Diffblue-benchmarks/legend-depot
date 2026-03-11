//  Copyright 2023 Goldman Sachs
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

package org.finos.legend.depot.core.server.info;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestInfoServiceServerInfo
{
    @Test
    void canCreateInfoServiceAndGetServerInfo()
    {
        InfoService infoService = new InfoService();
        InfoService.ServerInfo serverInfo = infoService.getServerInfo();

        assertNotNull(serverInfo);
    }

    @Test
    void canGetHostName()
    {
        InfoService infoService = new InfoService();
        InfoService.ServerInfo serverInfo = infoService.getServerInfo();

        assertNotNull(serverInfo.getHostName());
    }

    @Test
    void canGetPlatform()
    {
        InfoService infoService = new InfoService();
        InfoService.ServerInfo serverInfo = infoService.getServerInfo();

        assertNotNull(serverInfo.getPlatform());
    }

    @Test
    void canGetServerTimeZone()
    {
        InfoService infoService = new InfoService();
        InfoService.ServerInfo serverInfo = infoService.getServerInfo();

        String expectedTimeZone = ZoneId.systemDefault().getId();
        assertNotNull(serverInfo.getServerTimeZone());
        assertEquals(expectedTimeZone, serverInfo.getServerTimeZone());
    }

    @Test
    void canGetPlatformVersionWhenNoVersionFile()
    {
        InfoService infoService = new InfoService();
        InfoService.ServerPlatformInfo platform = infoService.getServerInfo().getPlatform();

        assertNotNull(platform);
        assertEquals(null, platform.getVersion());
        assertEquals(null, platform.getBuildTime());
        assertEquals(null, platform.getBuildRevision());
    }
}
