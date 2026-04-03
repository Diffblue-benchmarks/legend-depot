// Copyright 2024 Goldman Sachs
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

package org.finos.legend.depot.core.server;

import org.finos.legend.depot.core.server.info.InfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InfoServiceTest
{
    @Test
    public void testInfoServiceConstructorCreatesServerInfo()
    {
        InfoService infoService = new InfoService();
        Assertions.assertNotNull(infoService);
    }

    @Test
    public void testGetServerInfoNotNull()
    {
        InfoService infoService = new InfoService();
        InfoService.ServerInfo serverInfo = infoService.getServerInfo();
        Assertions.assertNotNull(serverInfo);
    }

    @Test
    public void testGetServerInfoHostName()
    {
        InfoService infoService = new InfoService();
        InfoService.ServerInfo serverInfo = infoService.getServerInfo();
        Assertions.assertNotNull(serverInfo.getHostName());
    }

    @Test
    public void testGetServerInfoPlatformNotNull()
    {
        InfoService infoService = new InfoService();
        InfoService.ServerInfo serverInfo = infoService.getServerInfo();
        Assertions.assertNotNull(serverInfo.getPlatform());
    }

    @Test
    public void testGetServerInfoServerTimeZoneNotNull()
    {
        InfoService infoService = new InfoService();
        InfoService.ServerInfo serverInfo = infoService.getServerInfo();
        Assertions.assertNotNull(serverInfo.getServerTimeZone());
    }
}
