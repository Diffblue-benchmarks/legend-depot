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

public class TestCoreDataStoreMigrationsResource extends CoreDataMongoStoreTests
{
    private CoreDataMigrations coreDataMigrations;
    private CoreDataStoreMigrationsResource resource;

    @BeforeEach
    public void setUp()
    {
        coreDataMigrations = new CoreDataMigrations(mongoProvider);
        AuthorisationProvider authProvider = (principalProvider, role) -> {};
        Provider<Principal> principalProvider = () -> null;
        resource = new CoreDataStoreMigrationsResource(coreDataMigrations, authProvider, principalProvider);
    }

    @Test
    public void testGetResourceName()
    {
        Assertions.assertEquals(CoreDataStoreMigrationsResource.STORE_ADMINISTRATION_RESOURCE, resource.getResourceName());
    }

    @Test
    public void testMigrationToProjectVersionData()
    {
        Response response = resource.migrationToProjectVersionData();
        Assertions.assertNotNull(response);
    }

    @Test
    public void testCleanupProjectData()
    {
        Response response = resource.cleanupProjectData();
        Assertions.assertNotNull(response);
    }

    @Test
    public void testStoreTransitiveDependenciesForVersions()
    {
        Response response = resource.storeTransitiveDependenciesForVersions();
        Assertions.assertNotNull(response);
    }

    @Test
    public void testAddTransitiveDependenciesToVersionData()
    {
        Response response = resource.addTransitiveDependenciesToVersionData();
        Assertions.assertNotNull(response);
    }

    @Test
    public void testAddLatestVersionToProjectData()
    {
        Response response = resource.addLatestVersionToProjectData();
        Assertions.assertNotNull(response);
    }
}
