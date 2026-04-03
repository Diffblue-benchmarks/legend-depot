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

package org.finos.legend.depot.core.server.resources.info;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.finos.legend.depot.core.server.info.InfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InfoResourceTest
{
    @Test
    public void testConstructorInitializesResource()
    {
        InfoService infoService = new InfoService();
        InfoResource resource = new InfoResource(infoService, null);
        Assertions.assertNotNull(resource);
    }

    @Test
    public void testGetServerInfoReturnsServerInfo()
    {
        InfoService infoService = new InfoService();
        InfoResource resource = new InfoResource(infoService, null);

        InfoService.ServerInfo serverInfo = resource.getServerInfo();

        Assertions.assertNotNull(serverInfo);
    }

    @Test
    public void testGetServerInfoReturnsServerTimeZone()
    {
        InfoService infoService = new InfoService();
        InfoResource resource = new InfoResource(infoService, null);

        InfoService.ServerInfo serverInfo = resource.getServerInfo();

        Assertions.assertNotNull(serverInfo.getServerTimeZone());
    }

    @Test
    public void testGetServerConfigReturnsJsonString() throws JsonProcessingException
    {
        InfoService infoService = new InfoService();
        InfoResource resource = new InfoResource(infoService, null);

        String config = resource.getServerConfig();

        Assertions.assertNotNull(config);
    }

    @Test
    public void testGetServerInfoReturnsPlatformInfo()
    {
        InfoService infoService = new InfoService();
        InfoResource resource = new InfoResource(infoService, null);

        InfoService.ServerInfo serverInfo = resource.getServerInfo();

        Assertions.assertNotNull(serverInfo.getPlatform());
    }
}
