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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MongoConfigurationTest
{
    @Test
    public void testConstructorAndGetters()
    {
        MongoConfiguration config = new MongoConfiguration("testdb", "mongodb://localhost:27017", true);
        Assertions.assertEquals("testdb", config.getDatabase());
        Assertions.assertEquals("mongodb://localhost:27017", config.getUrl());
        Assertions.assertTrue(config.isTracingEnabled());
    }

    @Test
    public void testTracingDisabled()
    {
        MongoConfiguration config = new MongoConfiguration("testdb", "mongodb://localhost:27017", false);
        Assertions.assertFalse(config.isTracingEnabled());
    }

    @Test
    public void testDifferentDatabaseNames()
    {
        MongoConfiguration config1 = new MongoConfiguration("depot-dev", "mongodb://host1:27017", false);
        MongoConfiguration config2 = new MongoConfiguration("depot-prod", "mongodb://host2:27017", true);

        Assertions.assertEquals("depot-dev", config1.getDatabase());
        Assertions.assertEquals("depot-prod", config2.getDatabase());
        Assertions.assertEquals("mongodb://host1:27017", config1.getUrl());
        Assertions.assertEquals("mongodb://host2:27017", config2.getUrl());
    }
}
