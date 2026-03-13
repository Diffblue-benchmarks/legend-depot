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
import com.google.inject.PrivateModule;
import com.mongodb.client.MongoDatabase;
import org.finos.legend.depot.store.api.admin.artifacts.ArtifactsFilesStore;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Named;

public class ArtifactsStoreMongoModuleTest extends TestStoreMongo
{
    @Test
    public void canCreateModule()
    {
        ArtifactsStoreMongoModule module = new ArtifactsStoreMongoModule();
        Assertions.assertNotNull(module);
    }

    @Test
    public void canInstantiateModuleMultipleTimes()
    {
        ArtifactsStoreMongoModule module1 = new ArtifactsStoreMongoModule();
        ArtifactsStoreMongoModule module2 = new ArtifactsStoreMongoModule();

        Assertions.assertNotNull(module1);
        Assertions.assertNotNull(module2);
        Assertions.assertFalse(module1 == module2);
    }

    @Test
    public void moduleExtendsPrivateModule()
    {
        ArtifactsStoreMongoModule module = new ArtifactsStoreMongoModule();
        Assertions.assertTrue(module instanceof PrivateModule);
    }

    @Test
    public void canRegisterIndexes()
    {
        ArtifactsStoreMongoModule module = new ArtifactsStoreMongoModule();
        MongoAdminStore adminStore = new MongoAdminStore(mongoProvider);

        boolean result = module.registerIndexes(adminStore);

        Assertions.assertTrue(result);
    }

    @Test
    public void registerIndexesDoesNotThrowException()
    {
        ArtifactsStoreMongoModule module = new ArtifactsStoreMongoModule();
        MongoAdminStore adminStore = new MongoAdminStore(mongoProvider);

        Assertions.assertDoesNotThrow(() -> module.registerIndexes(adminStore));
    }
}
