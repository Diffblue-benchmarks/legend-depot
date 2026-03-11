package org.finos.legend.depot.core.server.guice;

import org.finos.legend.depot.core.server.info.InfoService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestServerInfoModule
{
    @Test
    void canInitInfo()
    {
        ServerInfoModule module = new ServerInfoModule();
        InfoService infoService = module.initInfo();
        assertNotNull(infoService);
        assertNotNull(infoService.getServerInfo());
    }
}
