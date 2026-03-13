//  Copyright 2022 Goldman Sachs
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

package org.finos.legend.depot.services.pure.model.context.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.finos.legend.depot.services.api.entities.EntitiesService;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.services.api.pure.model.context.PureModelContextService;
import org.finos.legend.depot.services.pure.model.context.PureModelContextServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class TestPureModelContextModule
{
    @Test
    public void canCreateInjectorWithModule()
    {
        EntitiesService mockEntitiesService = mock(EntitiesService.class);
        ProjectsService mockProjectsService = mock(ProjectsService.class);

        Injector injector = Guice.createInjector(
                new PureModelContextModule(),
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(EntitiesService.class).toInstance(mockEntitiesService);
                        bind(ProjectsService.class).toInstance(mockProjectsService);
                    }
                }
        );

        PureModelContextService service = injector.getInstance(PureModelContextService.class);
        Assertions.assertNotNull(service);
        Assertions.assertTrue(service instanceof PureModelContextServiceImpl);
    }

    @Test
    public void canGetServiceFromInjector()
    {
        EntitiesService mockEntitiesService = mock(EntitiesService.class);
        ProjectsService mockProjectsService = mock(ProjectsService.class);

        Injector injector = Guice.createInjector(
                new PureModelContextModule(),
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(EntitiesService.class).toInstance(mockEntitiesService);
                        bind(ProjectsService.class).toInstance(mockProjectsService);
                    }
                }
        );

        PureModelContextService service = injector.getInstance(PureModelContextService.class);
        Assertions.assertNotNull(service);
    }
}
