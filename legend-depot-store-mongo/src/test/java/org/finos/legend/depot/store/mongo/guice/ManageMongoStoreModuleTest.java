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

import com.mongodb.client.MongoDatabase;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManageMongoStoreModuleTest extends TestStoreMongo
{
    @Test
    public void canBuildMongoAdminStore()
    {
        ManageMongoStoreModule module = new ManageMongoStoreModule();
        MongoDatabase database = getMongoDatabase();

        MongoAdminStore adminStore = module.buildMongoAdminStore(database);

        assertNotNull(adminStore);
        assertEquals("test-db", adminStore.getName());
    }
}
