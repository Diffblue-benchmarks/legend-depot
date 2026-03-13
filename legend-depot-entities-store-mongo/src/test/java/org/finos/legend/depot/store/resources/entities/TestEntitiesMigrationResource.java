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

package org.finos.legend.depot.store.resources.entities;

import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.migrations.MongoEntitiesMigrations;
import org.finos.legend.depot.store.mongo.entities.test.EntitiesMongoTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import javax.ws.rs.core.Response;
import java.security.Principal;

public class TestEntitiesMigrationResource extends TestStoreMongo
{
    private MongoEntitiesMigrations mongoEntitiesMigrations;
    private EntitiesMigrationResource resource;

    @BeforeEach
    public void setUp()
    {
        mongoEntitiesMigrations = new MongoEntitiesMigrations(mongoProvider);

        AuthorisationProvider authorisationProvider = new AuthorisationProvider()
        {
            @Override
            public void authorise(Provider<Principal> principalProvider, String resourceName)
            {
            }
        };

        Provider<Principal> principalProvider = new Provider<Principal>()
        {
            @Override
            public Principal get()
            {
                return new Principal()
                {
                    @Override
                    public String getName()
                    {
                        return "test-user";
                    }
                };
            }
        };

        resource = new EntitiesMigrationResource(mongoEntitiesMigrations, authorisationProvider, principalProvider);

        new EntitiesMongoTestUtils(mongoProvider).loadEntities(this.getClass().getClassLoader().getResource("data/versioned-entities-deletion.json"));
    }

    @Test
    public void canMigrateEntitiesToStoredEntityData()
    {
        Assertions.assertEquals(3, mongoProvider.getCollection("entities").countDocuments());

        Response response = resource.migrateEntitiesToStoredEntityData();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(3, mongoProvider.getCollection("entities").countDocuments());
    }

    @Test
    public void canDeleteVersionedEntities()
    {
        Document versionedEntity1 = new Document()
                .append("groupId", "test.group")
                .append("artifactId", "test-artifact")
                .append("versionId", "1.0.0")
                .append("versionedEntity", true)
                .append("entityAttributes", new Document()
                        .append("path", "test::path::Entity1")
                        .append("classifierPath", "meta::pure::metamodel::type::Class"));

        Document versionedEntity2 = new Document()
                .append("groupId", "test.group")
                .append("artifactId", "test-artifact")
                .append("versionId", "1.0.0")
                .append("versionedEntity", true)
                .append("entityAttributes", new Document()
                        .append("path", "test::path::Entity2")
                        .append("classifierPath", "meta::pure::metamodel::type::Class"));

        Document nonVersionedEntity = new Document()
                .append("groupId", "test.group")
                .append("artifactId", "test-artifact")
                .append("versionId", "1.0.0")
                .append("versionedEntity", false)
                .append("entityAttributes", new Document()
                        .append("path", "test::path::Entity3")
                        .append("classifierPath", "meta::pure::metamodel::type::Class"));

        mongoProvider.getCollection("entities").insertOne(versionedEntity1);
        mongoProvider.getCollection("entities").insertOne(versionedEntity2);
        mongoProvider.getCollection("entities").insertOne(nonVersionedEntity);

        Assertions.assertEquals(6, mongoProvider.getCollection("entities").countDocuments());

        Response response = resource.deleteVersionedEntities();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertNotNull(response.getEntity());

        DeleteResult deleteResult = (DeleteResult) response.getEntity();
        Assertions.assertEquals(2, deleteResult.getDeletedCount());
        Assertions.assertEquals(4, mongoProvider.getCollection("entities").countDocuments());
    }
}
