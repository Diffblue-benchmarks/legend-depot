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
import org.finos.legend.depot.store.api.generations.FileGenerations;
import org.finos.legend.depot.store.api.generations.UpdateFileGenerations;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.finos.legend.depot.store.mongo.generations.FileGenerationsMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestManageGenerationsStoreMongoModule extends TestStoreMongo
{
    @Test
    public void canCreateInjectorWithModule()
    {
        Injector injector = Guice.createInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(MongoDatabase.class).annotatedWith(Names.named("mongoDatabase")).toInstance(mongoProvider);
                bind(MongoAdminStore.class).toInstance(new MongoAdminStore(mongoProvider));
                install(new ManageGenerationsStoreMongoModule());
            }
        });

        FileGenerations fileGenerations = injector.getInstance(FileGenerations.class);
        Assertions.assertNotNull(fileGenerations);
        Assertions.assertTrue(fileGenerations instanceof FileGenerationsMongo);

        UpdateFileGenerations updateFileGenerations = injector.getInstance(UpdateFileGenerations.class);
        Assertions.assertNotNull(updateFileGenerations);
        Assertions.assertTrue(updateFileGenerations instanceof FileGenerationsMongo);
    }

    @Test
    public void canRegisterGenerationsIndexes()
    {
        MongoAdminStore adminStore = new MongoAdminStore(mongoProvider);
        ManageGenerationsStoreMongoModule module = new ManageGenerationsStoreMongoModule();

        boolean result = module.registerGenerationsIndexes(adminStore);

        Assertions.assertTrue(result);
    }
}
