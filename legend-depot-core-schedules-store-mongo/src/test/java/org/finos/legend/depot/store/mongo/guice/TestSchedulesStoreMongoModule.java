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
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.finos.legend.depot.store.mongo.schedules.ScheduleInstancesMongo;
import org.finos.legend.depot.store.mongo.schedules.SchedulesMongo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSchedulesStoreMongoModule
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
    public void canCreateInjectorWithModule()
    {
        MongoDatabase db = this.mongoDatabase;
        Injector injector = Guice.createInjector(
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(MongoDatabase.class).annotatedWith(Names.named("mongoDatabase")).toInstance(db);
                    }
                },
                new SchedulesStoreMongoModule()
        );

        assertNotNull(injector);
    }

    @Test
    public void canResolveSchedulesStore()
    {
        MongoDatabase db = this.mongoDatabase;
        Injector injector = Guice.createInjector(
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(MongoDatabase.class).annotatedWith(Names.named("mongoDatabase")).toInstance(db);
                    }
                },
                new SchedulesStoreMongoModule()
        );

        SchedulesStore schedulesStore = injector.getInstance(SchedulesStore.class);
        assertNotNull(schedulesStore);
        assertTrue(schedulesStore instanceof SchedulesMongo);
    }

    @Test
    public void canResolveScheduleInstancesStore()
    {
        MongoDatabase db = this.mongoDatabase;
        Injector injector = Guice.createInjector(
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(MongoDatabase.class).annotatedWith(Names.named("mongoDatabase")).toInstance(db);
                    }
                },
                new SchedulesStoreMongoModule()
        );

        ScheduleInstancesStore instancesStore = injector.getInstance(ScheduleInstancesStore.class);
        assertNotNull(instancesStore);
        assertTrue(instancesStore instanceof ScheduleInstancesMongo);
    }
}
