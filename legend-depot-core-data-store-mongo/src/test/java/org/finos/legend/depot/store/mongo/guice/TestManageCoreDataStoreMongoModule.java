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

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestManageCoreDataStoreMongoModule extends TestStoreMongo
{
    private final MongoAdminStore adminStore = new MongoAdminStore(mongoProvider);
    private final ManageCoreDataStoreMongoModule module = new ManageCoreDataStoreMongoModule();

    @Test
    void canRegisterIndexes()
    {
        boolean result = module.registerIndexes(adminStore);

        assertTrue(result);
        assertTrue(adminStore.createIndexes().size() > 0);
    }
}
