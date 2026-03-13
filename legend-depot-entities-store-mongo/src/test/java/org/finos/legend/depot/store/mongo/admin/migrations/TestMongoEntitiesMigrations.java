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

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.bson.conversions.Bson;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.entities.test.EntitiesMongoTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static org.finos.legend.depot.store.mongo.core.BaseMongo.ARTIFACT_ID;
import static org.finos.legend.depot.store.mongo.core.BaseMongo.GROUP_ID;
import static org.finos.legend.depot.store.mongo.core.BaseMongo.VERSION_ID;

public class TestMongoEntitiesMigrations extends TestStoreMongo
{
    private MongoEntitiesMigrations mongoEntitiesMigrations;

    @BeforeEach
    public void setUp()
    {
        mongoEntitiesMigrations = new MongoEntitiesMigrations(mongoProvider);
        new EntitiesMongoTestUtils(mongoProvider).loadEntities(this.getClass().getClassLoader().getResource("data/versioned-entities-deletion.json"));
        Assertions.assertEquals(3, mongoProvider.getCollection("entities").countDocuments());
    }

    @Test
    public void canCreateMongoEntitiesMigrations()
    {
        MongoEntitiesMigrations migrations = new MongoEntitiesMigrations(mongoProvider);
        Assertions.assertNotNull(migrations);
        Assertions.assertEquals("test-db", migrations.getName());
    }

    @Test
    public void canDeleteVersionedEntities()
    {
        mongoProvider.getCollection("entities").updateOne(
                and(Filters.eq("entity.path", "examples::metadata::test::TestProfile"),
                        getArtifactAndVersionFilter("examples.metadata", "test", "2.2.0")),
                Updates.combine(Updates.set("versionedEntity", true)));

        DeleteResult result = mongoEntitiesMigrations.deleteVersionedEntities();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getDeletedCount());
        Assertions.assertEquals(2, mongoProvider.getCollection("entities").countDocuments());
    }

    @Test
    public void canDeleteMultipleVersionedEntities()
    {
        mongoProvider.getCollection("entities").updateMany(
                getArtifactAndVersionFilter("examples.metadata", "test", "2.2.0"),
                Updates.combine(Updates.set("versionedEntity", true)));

        DeleteResult result = mongoEntitiesMigrations.deleteVersionedEntities();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getDeletedCount());
        Assertions.assertEquals(0, mongoProvider.getCollection("entities").countDocuments());
    }

    @Test
    public void canCallMigrateEntitiesToStoredEntityData()
    {
        mongoEntitiesMigrations.migrateEntitiesToStoredEntityData();
        Assertions.assertEquals(3, mongoProvider.getCollection("entities").countDocuments());
    }

    private Bson getArtifactAndVersionFilter(String groupId, String artifactId, String versionId)
    {
        return and(eq(VERSION_ID, versionId),
                and(eq(GROUP_ID, groupId),
                        eq(ARTIFACT_ID, artifactId)));
    }
}
