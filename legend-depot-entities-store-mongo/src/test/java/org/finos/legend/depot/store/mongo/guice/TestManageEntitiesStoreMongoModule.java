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

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.mongodb.client.MongoDatabase;
import org.finos.legend.depot.store.api.entities.UpdateEntities;
import org.finos.legend.depot.store.api.versionedEntities.UpdateVersionedEntities;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.finos.legend.depot.store.mongo.entities.EntitiesMongo;
import org.finos.legend.depot.store.mongo.versionedEntities.VersionedEntitiesMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestManageEntitiesStoreMongoModule extends TestStoreMongo
{
    @Test
    public void canRegisterEntityIndexes()
    {
        MongoAdminStore adminStore = new MongoAdminStore(mongoProvider);
        ManageEntitiesStoreMongoModule module = new ManageEntitiesStoreMongoModule();

        boolean result = module.registerGenerationsIndexes(adminStore);

        Assertions.assertTrue(result);
        adminStore.createIndexes();

        Assertions.assertNotNull(adminStore.getAllIndexes());
    }

    @Test
    public void registerEntityIndexesRegistersForBothCollections()
    {
        MongoAdminStore adminStore = new MongoAdminStore(mongoProvider);
        ManageEntitiesStoreMongoModule module = new ManageEntitiesStoreMongoModule();

        boolean result = module.registerGenerationsIndexes(adminStore);

        Assertions.assertTrue(result);

        adminStore.createIndexes();

        Assertions.assertNotNull(EntitiesMongo.buildIndexes());
        Assertions.assertFalse(EntitiesMongo.buildIndexes().isEmpty());
        Assertions.assertNotNull(VersionedEntitiesMongo.buildIndexes());
        Assertions.assertFalse(VersionedEntitiesMongo.buildIndexes().isEmpty());
    }
}
