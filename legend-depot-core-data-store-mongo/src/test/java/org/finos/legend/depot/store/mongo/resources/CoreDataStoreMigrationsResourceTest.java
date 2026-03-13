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

package org.finos.legend.depot.store.mongo.resources;

import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.store.mongo.CoreDataMongoStoreTests;
import org.finos.legend.depot.store.mongo.admin.CoreDataMigrations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import javax.ws.rs.core.Response;
import java.security.Principal;

public class CoreDataStoreMigrationsResourceTest extends CoreDataMongoStoreTests
{
    private CoreDataMigrations coreDataMigrations;
    private AuthorisationProvider authorisationProvider;
    private Provider<Principal> principalProvider;
    private CoreDataStoreMigrationsResource resource;

    @BeforeEach
    public void setup()
    {
        coreDataMigrations = new CoreDataMigrations(mongoProvider);

        authorisationProvider = new AuthorisationProvider()
        {
            @Override
            public void authorise(Provider<Principal> principalProvider, String role)
            {
            }
        };

        principalProvider = new Provider<Principal>()
        {
            @Override
            public Principal get()
            {
                return new Principal()
                {
                    @Override
                    public String getName()
                    {
                        return "testUser";
                    }
                };
            }
        };

        resource = new CoreDataStoreMigrationsResource(coreDataMigrations, authorisationProvider, principalProvider);
    }

    @Test
    public void canCreateCoreDataStoreMigrationsResource()
    {
        CoreDataStoreMigrationsResource testResource = new CoreDataStoreMigrationsResource(coreDataMigrations, authorisationProvider, principalProvider);
        Assertions.assertNotNull(testResource);
    }

    @Test
    public void canGetResourceName()
    {
        String resourceName = resource.getResourceName();
        Assertions.assertNotNull(resourceName);
        Assertions.assertEquals(CoreDataStoreMigrationsResource.STORE_ADMINISTRATION_RESOURCE, resourceName);
    }

    @Test
    public void canMigrationToProjectVersionData()
    {
        Response response = resource.migrationToProjectVersionData();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void canCleanupProjectData()
    {
        Response response = resource.cleanupProjectData();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void canStoreTransitiveDependenciesForVersions()
    {
        Response response = resource.storeTransitiveDependenciesForVersions();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void canAddTransitiveDependenciesToVersionData()
    {
        Response response = resource.addTransitiveDependenciesToVersionData();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void canAddLatestVersionToProjectData()
    {
        Response response = resource.addLatestVersionToProjectData();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}
