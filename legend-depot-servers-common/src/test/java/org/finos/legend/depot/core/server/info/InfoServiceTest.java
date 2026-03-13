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

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InfoServiceTest
{
    @Test
    public void testServerPlatformInfoConstructorWithParameters() throws Exception
    {
        Constructor<InfoService.ServerPlatformInfo> constructor = InfoService.ServerPlatformInfo.class.getDeclaredConstructor(String.class, String.class, String.class);
        constructor.setAccessible(true);

        InfoService.ServerPlatformInfo platformInfo = constructor.newInstance("1.0.0", "2024-01-01", "abc123");

        assertNotNull(platformInfo);
        assertEquals("1.0.0", platformInfo.getVersion());
        assertEquals("2024-01-01", platformInfo.getBuildTime());
        assertEquals("abc123", platformInfo.getBuildRevision());
    }

    @Test
    public void testServerPlatformInfoDefaultConstructor() throws Exception
    {
        Constructor<InfoService.ServerPlatformInfo> constructor = InfoService.ServerPlatformInfo.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InfoService.ServerPlatformInfo platformInfo = constructor.newInstance();

        assertNotNull(platformInfo);
        assertNull(platformInfo.getVersion());
        assertNull(platformInfo.getBuildTime());
        assertNull(platformInfo.getBuildRevision());
    }

    @Test
    public void testServerPlatformInfoGetVersion() throws Exception
    {
        Constructor<InfoService.ServerPlatformInfo> constructor = InfoService.ServerPlatformInfo.class.getDeclaredConstructor(String.class, String.class, String.class);
        constructor.setAccessible(true);

        InfoService.ServerPlatformInfo platformInfo = constructor.newInstance("2.5.0", null, null);

        assertEquals("2.5.0", platformInfo.getVersion());
    }

    @Test
    public void testServerPlatformInfoGetBuildTime() throws Exception
    {
        Constructor<InfoService.ServerPlatformInfo> constructor = InfoService.ServerPlatformInfo.class.getDeclaredConstructor(String.class, String.class, String.class);
        constructor.setAccessible(true);

        InfoService.ServerPlatformInfo platformInfo = constructor.newInstance(null, "2024-03-13", null);

        assertEquals("2024-03-13", platformInfo.getBuildTime());
    }

    @Test
    public void testServerPlatformInfoGetBuildRevision() throws Exception
    {
        Constructor<InfoService.ServerPlatformInfo> constructor = InfoService.ServerPlatformInfo.class.getDeclaredConstructor(String.class, String.class, String.class);
        constructor.setAccessible(true);

        InfoService.ServerPlatformInfo platformInfo = constructor.newInstance(null, null, "def456");

        assertEquals("def456", platformInfo.getBuildRevision());
    }
}
