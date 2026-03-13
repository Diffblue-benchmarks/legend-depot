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

package org.finos.legend.depot.store.mongo.guice;

import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManageCoreDataStoreMongoModuleTest extends TestStoreMongo
{
    @Test
    public void canInstantiateModule()
    {
        ManageCoreDataStoreMongoModule module = new ManageCoreDataStoreMongoModule();

        assertNotNull(module);
    }

    @Test
    public void testRegisterIndexes()
    {
        // Setup
        ManageCoreDataStoreMongoModule module = new ManageCoreDataStoreMongoModule();
        MongoAdminStore adminStore = new MongoAdminStore(getMongoDatabase());

        // Execute
        boolean result = module.registerIndexes(adminStore);

        // Verify
        assertTrue(result);
    }

    @Test
    public void canCallRegisterIndexesMultipleTimes()
    {
        // Setup
        ManageCoreDataStoreMongoModule module = new ManageCoreDataStoreMongoModule();
        MongoAdminStore adminStore = new MongoAdminStore(getMongoDatabase());

        // Execute
        boolean result1 = module.registerIndexes(adminStore);
        boolean result2 = module.registerIndexes(adminStore);

        // Verify
        assertTrue(result1);
        assertTrue(result2);
    }
}
