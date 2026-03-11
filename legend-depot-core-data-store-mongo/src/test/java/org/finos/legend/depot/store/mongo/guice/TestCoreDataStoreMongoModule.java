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

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.finos.legend.depot.store.api.projects.Projects;
import org.finos.legend.depot.store.api.projects.ProjectsVersions;
import org.finos.legend.depot.store.mongo.projects.ProjectsMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsVersionsMongo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCoreDataStoreMongoModule
{
    private MongoServer server;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    @BeforeEach
    public void setUp()
    {
        server = new MongoServer(new MemoryBackend());
        mongoClient = new MongoClient(new ServerAddress(server.bind()));
        mongoDatabase = mongoClient.getDatabase("test-db");
    }

    @AfterEach
    public void tearDown()
    {
        mongoDatabase.drop();
        mongoClient.close();
        server.shutdown();
    }

    @Test
    public void canConfigureModuleBindings()
    {
        Injector injector = Guice.createInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(MongoDatabase.class).annotatedWith(Names.named("mongoDatabase")).toInstance(mongoDatabase);
                install(new CoreDataStoreMongoModule());
            }
        });

        Projects projects = injector.getInstance(Projects.class);
        Assertions.assertNotNull(projects);
        Assertions.assertTrue(projects instanceof ProjectsMongo);

        ProjectsVersions projectsVersions = injector.getInstance(ProjectsVersions.class);
        Assertions.assertNotNull(projectsVersions);
        Assertions.assertTrue(projectsVersions instanceof ProjectsVersionsMongo);
    }
}
