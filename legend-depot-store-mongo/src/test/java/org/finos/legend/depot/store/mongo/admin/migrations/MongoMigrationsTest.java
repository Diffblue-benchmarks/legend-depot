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

package org.finos.legend.depot.store.mongo.admin.migrations;

import com.mongodb.client.MongoDatabase;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MongoMigrationsTest extends TestStoreMongo
{
    private static class TestMongoMigrations extends MongoMigrations
    {
        public TestMongoMigrations(MongoDatabase mongoDatabase)
        {
            super(mongoDatabase);
        }
    }

    @Test
    public void canConstructMongoMigrations()
    {
        MongoDatabase database = getMongoDatabase();
        TestMongoMigrations migrations = new TestMongoMigrations(database);

        assertNotNull(migrations);
        assertNotNull(migrations.mongoDatabase);
        assertEquals(database, migrations.mongoDatabase);
    }

    @Test
    public void canGetDatabaseName()
    {
        MongoDatabase database = getMongoDatabase();
        TestMongoMigrations migrations = new TestMongoMigrations(database);

        String name = migrations.getName();

        assertNotNull(name);
        assertEquals("test-db", name);
    }
}
