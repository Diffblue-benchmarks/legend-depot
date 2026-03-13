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

package org.finos.legend.depot.store.mongo.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoConfigurationTest
{
    @Test
    public void canCreateMongoConfiguration()
    {
        String database = "test-database";
        String url = "mongodb://localhost:27017";
        boolean tracing = true;

        MongoConfiguration config = new MongoConfiguration(database, url, tracing);

        assertNotNull(config);
        assertEquals(database, config.database);
        assertEquals(url, config.url);
        assertEquals(tracing, config.tracing);
    }

    @Test
    public void canGetDatabase()
    {
        String database = "my-database";
        MongoConfiguration config = new MongoConfiguration(database, "mongodb://localhost:27017", false);

        assertEquals(database, config.getDatabase());
    }

    @Test
    public void canGetUrl()
    {
        String url = "mongodb://localhost:27017";
        MongoConfiguration config = new MongoConfiguration("test-db", url, false);

        assertEquals(url, config.getUrl());
    }

    @Test
    public void canGetTracingEnabled()
    {
        MongoConfiguration configWithTracing = new MongoConfiguration("test-db", "mongodb://localhost:27017", true);
        assertTrue(configWithTracing.isTracingEnabled());

        MongoConfiguration configWithoutTracing = new MongoConfiguration("test-db", "mongodb://localhost:27017", false);
        assertFalse(configWithoutTracing.isTracingEnabled());
    }
}
