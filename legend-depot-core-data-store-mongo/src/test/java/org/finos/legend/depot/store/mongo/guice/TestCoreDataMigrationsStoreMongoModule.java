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
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.CoreDataMigrations;
import org.finos.legend.depot.store.mongo.resources.CoreDataStoreMigrationsResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.Principal;

public class TestCoreDataMigrationsStoreMongoModule extends TestStoreMongo
{
    @Test
    public void canCreateInjectorWithModule()
    {
        Injector injector = Guice.createInjector(
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(MongoDatabase.class).annotatedWith(Names.named("mongoDatabase")).toInstance(mongoProvider);
                        bind(AuthorisationProvider.class).toInstance((principalProvider, role) -> { });
                        bind(Principal.class).annotatedWith(Names.named("requestPrincipal")).toInstance(() -> "testUser");
                    }
                },
                new CoreDataMigrationsStoreMongoModule()
        );

        CoreDataMigrations migrations = injector.getInstance(CoreDataMigrations.class);
        Assertions.assertNotNull(migrations);
    }

    @Test
    public void canGetExposedBindings()
    {
        Injector injector = Guice.createInjector(
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(MongoDatabase.class).annotatedWith(Names.named("mongoDatabase")).toInstance(mongoProvider);
                        bind(AuthorisationProvider.class).toInstance((principalProvider, role) -> { });
                        bind(Principal.class).annotatedWith(Names.named("requestPrincipal")).toInstance(() -> "testUser");
                    }
                },
                new CoreDataMigrationsStoreMongoModule()
        );

        CoreDataMigrations migrations = injector.getInstance(CoreDataMigrations.class);
        CoreDataStoreMigrationsResource resource = injector.getInstance(CoreDataStoreMigrationsResource.class);
        Assertions.assertNotNull(migrations);
        Assertions.assertNotNull(resource);
    }
}
