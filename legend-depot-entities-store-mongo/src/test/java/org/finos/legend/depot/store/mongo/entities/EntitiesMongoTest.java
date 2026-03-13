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

package org.finos.legend.depot.store.mongo.entities;

import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import com.mongodb.client.MongoClients;
import org.finos.legend.depot.store.mongo.core.BaseMongo;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;

public class EntitiesMongoTest
{
    private static MongoServer server;
    private static EntitiesMongo entitiesMongo;

    @BeforeAll
    public static void setup()
    {
        server = new MongoServer(new MemoryBackend());
        InetSocketAddress addr = server.bind();
        String connectionString = "mongodb://" + addr.getHostString() + ":" + addr.getPort();
        MongoDatabase database = MongoClients.create(connectionString).getDatabase("entities-test");
        entitiesMongo = new EntitiesMongo(database);
    }

    @AfterAll
    public static void teardown()
    {
        if (server != null)
        {
            server.shutdown();
        }
    }

    @Test
    public void testGetAllEntitiesForNonExistentVersion()
    {
        List<Entity> result = entitiesMongo.getAllEntities("org.finos", "nonexistent", "9.9.9");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetEntityByPathNotFound()
    {
        Optional<Entity> found = entitiesMongo.getEntity("org.finos", "legend-depot", "1.0.0", "model::NonExistent");
        Assertions.assertFalse(found.isPresent());
    }

    @Test
    public void testDeleteNonExistentVersion()
    {
        long deleted = entitiesMongo.delete("org.finos", "nonexistent", "9.9.9");
        Assertions.assertEquals(0, deleted);
    }

    @Test
    public void testDeleteNonExistentGA()
    {
        long deleted = entitiesMongo.delete("org.finos", "nonexistent");
        Assertions.assertEquals(0, deleted);
    }

    @Test
    public void testFindEntitiesByClassifierEmpty()
    {
        List<Entity> found = entitiesMongo.findEntitiesByClassifier("org.finos", "legend-depot", "1.0.0", "meta::pure::metamodel::type::Class");
        Assertions.assertTrue(found.isEmpty());
    }

    @Test
    public void testBuildIndexes()
    {
        Assertions.assertFalse(EntitiesMongo.buildIndexes().isEmpty());
        Assertions.assertEquals(4, EntitiesMongo.buildIndexes().size());
    }

    @Test
    public void testGetEntitiesByPackageEmpty()
    {
        List<Entity> result = entitiesMongo.getEntitiesByPackage("org.finos", "legend-depot", "1.0.0", "model", null, true);
        Assertions.assertTrue(result.isEmpty());
    }
}
