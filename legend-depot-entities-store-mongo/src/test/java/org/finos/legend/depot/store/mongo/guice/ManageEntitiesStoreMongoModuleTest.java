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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManageEntitiesStoreMongoModuleTest extends TestStoreMongo
{
    @Test
    public void canCreateModule()
    {
        ManageEntitiesStoreMongoModule module = new ManageEntitiesStoreMongoModule();
        Assertions.assertNotNull(module);
    }

    @Test
    public void canInstantiateModuleMultipleTimes()
    {
        ManageEntitiesStoreMongoModule module1 = new ManageEntitiesStoreMongoModule();
        ManageEntitiesStoreMongoModule module2 = new ManageEntitiesStoreMongoModule();

        Assertions.assertNotNull(module1);
        Assertions.assertNotNull(module2);
        Assertions.assertFalse(module1 == module2);
    }

    @Test
    public void moduleExtendsCorrectBaseClass()
    {
        ManageEntitiesStoreMongoModule module = new ManageEntitiesStoreMongoModule();
        Assertions.assertTrue(module instanceof EntitiesStoreMongoModule);
    }

    @Test
    public void canRegisterGenerationsIndexes()
    {
        ManageEntitiesStoreMongoModule module = new ManageEntitiesStoreMongoModule();
        MongoAdminStore adminStore = new MongoAdminStore(mongoProvider);

        boolean result = module.registerGenerationsIndexes(adminStore);

        Assertions.assertTrue(result);
    }

    @Test
    public void registerGenerationsIndexesDoesNotThrowException()
    {
        ManageEntitiesStoreMongoModule module = new ManageEntitiesStoreMongoModule();
        MongoAdminStore adminStore = new MongoAdminStore(mongoProvider);

        Assertions.assertDoesNotThrow(() -> module.registerGenerationsIndexes(adminStore));
    }
}
