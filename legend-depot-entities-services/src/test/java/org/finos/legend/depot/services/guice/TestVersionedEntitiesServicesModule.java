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

package org.finos.legend.depot.services.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.services.api.versionedEntities.VersionedEntitiesService;
import org.finos.legend.depot.services.versionedEntities.VersionedEntitiesServiceImpl;
import org.finos.legend.depot.store.api.versionedEntities.VersionedEntities;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class TestVersionedEntitiesServicesModule extends TestStoreMongo
{
    @Test
    public void canCreateModuleBindings()
    {
        Injector injector = Guice.createInjector(
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(VersionedEntities.class).toInstance(mock(VersionedEntities.class));
                        bind(ProjectsService.class).toInstance(mock(ProjectsService.class));
                    }
                },
                new VersionedEntitiesServicesModule()
        );

        VersionedEntitiesService service = injector.getInstance(VersionedEntitiesService.class);
        Assertions.assertNotNull(service);
        Assertions.assertTrue(service instanceof VersionedEntitiesServiceImpl);
    }
}
