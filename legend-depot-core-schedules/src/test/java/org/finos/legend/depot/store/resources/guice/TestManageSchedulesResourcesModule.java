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

package org.finos.legend.depot.store.resources.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.finos.legend.depot.core.services.api.authorisation.AuthorisationProvider;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.finos.legend.depot.store.resources.schedules.ManageSchedulesResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Provider;
import java.security.Principal;

public class TestManageSchedulesResourcesModule
{
    @Test
    public void configureBindsAndExposesManageSchedulesResource()
    {
        Injector injector = Guice.createInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(AuthorisationProvider.class).toInstance(Mockito.mock(AuthorisationProvider.class));
                bind(Principal.class).annotatedWith(Names.named("requestPrincipal")).toProvider(() -> Mockito.mock(Principal.class));
                bind(SchedulesFactory.class).toInstance(Mockito.mock(SchedulesFactory.class));
                bind(SchedulesStore.class).toInstance(Mockito.mock(SchedulesStore.class));
                bind(ScheduleInstancesStore.class).toInstance(Mockito.mock(ScheduleInstancesStore.class));
                install(new ManageSchedulesResourcesModule());
            }
        });

        ManageSchedulesResource resource = injector.getInstance(ManageSchedulesResource.class);
        Assertions.assertNotNull(resource);
    }
}
