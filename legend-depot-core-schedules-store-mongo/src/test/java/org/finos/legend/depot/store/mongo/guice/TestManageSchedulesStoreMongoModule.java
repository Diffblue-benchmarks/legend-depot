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
import org.finos.legend.depot.store.mongo.schedules.ScheduleInstancesMongo;
import org.finos.legend.depot.store.mongo.schedules.SchedulesMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestManageSchedulesStoreMongoModule extends TestStoreMongo
{
    @Test
    public void testRegisterIndexesReturnsTrue()
    {
        MongoAdminStore adminStore = new MongoAdminStore(getMongoDatabase());
        ManageSchedulesStoreMongoModule module = new ManageSchedulesStoreMongoModule();

        boolean result = module.registerIndexes(adminStore);

        Assertions.assertTrue(result);
    }

    @Test
    public void testRegisterIndexesRegistersSchedulesCollection()
    {
        MongoAdminStore adminStore = new MongoAdminStore(getMongoDatabase());
        ManageSchedulesStoreMongoModule module = new ManageSchedulesStoreMongoModule();

        module.registerIndexes(adminStore);

        java.util.List<String> createdIndexes = adminStore.createIndexes();
        Assertions.assertNotNull(createdIndexes);
    }

    @Test
    public void testRegisterIndexesCreatesIndexesForBothCollections()
    {
        MongoAdminStore adminStore = new MongoAdminStore(getMongoDatabase());
        ManageSchedulesStoreMongoModule module = new ManageSchedulesStoreMongoModule();

        module.registerIndexes(adminStore);
        adminStore.createIndexes();

        java.util.Map<String, java.util.List<org.bson.Document>> allIndexes = adminStore.getAllIndexes();
        Assertions.assertTrue(allIndexes.containsKey(SchedulesMongo.COLLECTION));
        Assertions.assertTrue(allIndexes.containsKey(ScheduleInstancesMongo.COLLECTION));
    }
}
