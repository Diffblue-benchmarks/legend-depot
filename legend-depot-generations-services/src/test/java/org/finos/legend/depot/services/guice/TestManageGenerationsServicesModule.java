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
import org.finos.legend.depot.services.api.generations.ManageFileGenerationsService;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.store.api.generations.FileGenerations;
import org.finos.legend.depot.store.api.generations.UpdateFileGenerations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class TestManageGenerationsServicesModule
{
    @Test
    public void canCreateModuleBindings()
    {
        UpdateFileGenerations mockGenerations = mock(UpdateFileGenerations.class);
        ProjectsService mockProjectsService = mock(ProjectsService.class);

        Injector injector = Guice.createInjector(new ManageGenerationsServicesModule(), new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(FileGenerations.class).toInstance(mockGenerations);
                bind(UpdateFileGenerations.class).toInstance(mockGenerations);
                bind(ProjectsService.class).toInstance(mockProjectsService);
            }
        });

        Assertions.assertNotNull(injector.getInstance(FileGenerationsService.class));
        Assertions.assertNotNull(injector.getInstance(ManageFileGenerationsService.class));
    }
}
