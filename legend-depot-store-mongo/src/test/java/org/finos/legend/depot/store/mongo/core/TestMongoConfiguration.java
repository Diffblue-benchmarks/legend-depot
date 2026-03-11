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
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestMongoConfiguration
{
    @Test
    void canCreateConfigurationAndRetrieveProperties()
    {
        MongoConfiguration config = new MongoConfiguration("myDatabase", "mongodb://localhost:27017", true);

        assertEquals("myDatabase", config.getDatabase());
        assertEquals("mongodb://localhost:27017", config.getUrl());
        assertTrue(config.isTracingEnabled());
    }

    @Test
    void canCreateConfigurationWithTracingDisabled()
    {
        MongoConfiguration config = new MongoConfiguration("testDb", "mongodb://host:1234", false);

        assertEquals("testDb", config.getDatabase());
        assertEquals("mongodb://host:1234", config.getUrl());
        assertFalse(config.isTracingEnabled());
    }
}
