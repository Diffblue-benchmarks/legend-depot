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

package org.finos.legend.depot.store.resources.guice;

import com.google.inject.PrivateModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestNotificationsResourcesModule
{
    @Test
    public void canInstantiateModule()
    {
        NotificationsResourcesModule module = new NotificationsResourcesModule();

        assertNotNull(module);
    }

    @Test
    public void moduleExtendsPrivateModule()
    {
        NotificationsResourcesModule module = new NotificationsResourcesModule();

        assertTrue(module instanceof PrivateModule);
    }
}
