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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManageSchedulesStoreMongoModuleTest extends TestStoreMongo
{
    @Test
    public void testRegisterIndexes()
    {
        // Setup
        ManageSchedulesStoreMongoModule module = new ManageSchedulesStoreMongoModule();
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
        ManageSchedulesStoreMongoModule module = new ManageSchedulesStoreMongoModule();
        MongoAdminStore adminStore = new MongoAdminStore(getMongoDatabase());

        // Execute
        boolean result1 = module.registerIndexes(adminStore);
        boolean result2 = module.registerIndexes(adminStore);

        // Verify
        assertTrue(result1);
        assertTrue(result2);
    }
}
