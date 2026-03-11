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
import org.finos.legend.depot.services.api.generations.FileGenerationsService;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.services.generations.impl.FileGenerationsServiceImpl;
import org.finos.legend.depot.store.api.generations.FileGenerations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class TestGenerationsServicesModule
{
    @Test
    public void canCreateModuleBindings()
    {
        Injector injector = Guice.createInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(FileGenerations.class).toInstance(mock(FileGenerations.class));
                bind(ProjectsService.class).toInstance(mock(ProjectsService.class));
                install(new GenerationsServicesModule());
            }
        });

        FileGenerationsService service = injector.getInstance(FileGenerationsService.class);
        Assertions.assertNotNull(service);
        Assertions.assertTrue(service instanceof FileGenerationsServiceImpl);
    }
}
