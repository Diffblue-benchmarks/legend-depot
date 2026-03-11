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

import com.google.inject.PrivateModule;
import org.finos.legend.depot.store.mongo.admin.migrations.MongoEntitiesMigrations;
import org.finos.legend.depot.store.resources.entities.EntitiesMigrationResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class TestEntitiesMigrationsStoreMongoModule
{
    @Test
    public void canCreateModule()
    {
        EntitiesMigrationsStoreMongoModule module = new EntitiesMigrationsStoreMongoModule();
        Assertions.assertNotNull(module);
        Assertions.assertTrue(module instanceof PrivateModule);
    }

    @Test
    public void configureMethodExists() throws Exception
    {
        Method configureMethod = EntitiesMigrationsStoreMongoModule.class.getDeclaredMethod("configure");
        Assertions.assertNotNull(configureMethod);
        Assertions.assertEquals(void.class, configureMethod.getReturnType());
        Assertions.assertEquals(0, configureMethod.getParameterCount());
    }

    @Test
    public void moduleReferencesExpectedClasses() throws Exception
    {
        ClassLoader cl = EntitiesMigrationsStoreMongoModule.class.getClassLoader();
        Class<?> migrationsClass = cl.loadClass(MongoEntitiesMigrations.class.getName());
        Class<?> resourceClass = cl.loadClass(EntitiesMigrationResource.class.getName());

        Assertions.assertNotNull(migrationsClass);
        Assertions.assertNotNull(resourceClass);
        Assertions.assertEquals("MongoEntitiesMigrations", migrationsClass.getSimpleName());
        Assertions.assertEquals("EntitiesMigrationResource", resourceClass.getSimpleName());
    }
}
