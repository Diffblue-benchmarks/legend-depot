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
import org.finos.legend.depot.services.api.artifacts.purge.ArtifactsPurgeService;
import org.finos.legend.depot.services.api.artifacts.reconciliation.VersionsReconciliationService;
import org.finos.legend.depot.services.api.artifacts.refresh.ArtifactsRefreshService;
import org.finos.legend.depot.services.api.artifacts.refresh.RefreshDependenciesService;
import org.finos.legend.depot.store.resources.artifacts.ArtifactDependenciesRefreshResource;
import org.finos.legend.depot.store.resources.artifacts.ArtifactsPurgeResource;
import org.finos.legend.depot.store.resources.artifacts.ArtifactsRefreshResource;
import org.finos.legend.depot.store.resources.artifacts.VersionsReconciliationResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.Principal;

import static org.mockito.Mockito.mock;

public class TestArtifactsResourcesModule
{
    @Test
    public void canCreateModuleAndExposeBindings()
    {
        ArtifactsPurgeService purgeService = mock(ArtifactsPurgeService.class);
        ArtifactsRefreshService refreshService = mock(ArtifactsRefreshService.class);
        VersionsReconciliationService reconciliationService = mock(VersionsReconciliationService.class);
        RefreshDependenciesService dependenciesService = mock(RefreshDependenciesService.class);
        AuthorisationProvider authorisationProvider = mock(AuthorisationProvider.class);
        Injector injector = Guice.createInjector(
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(ArtifactsPurgeService.class).toInstance(purgeService);
                        bind(ArtifactsRefreshService.class).toInstance(refreshService);
                        bind(VersionsReconciliationService.class).toInstance(reconciliationService);
                        bind(RefreshDependenciesService.class).toInstance(dependenciesService);
                        bind(AuthorisationProvider.class).toInstance(authorisationProvider);
                        bind(Principal.class).annotatedWith(Names.named("requestPrincipal")).toInstance(mock(Principal.class));
                    }
                },
                new ArtifactsResourcesModule()
        );

        Assertions.assertNotNull(injector.getInstance(ArtifactsPurgeResource.class));
        Assertions.assertNotNull(injector.getInstance(ArtifactsRefreshResource.class));
        Assertions.assertNotNull(injector.getInstance(VersionsReconciliationResource.class));
        Assertions.assertNotNull(injector.getInstance(ArtifactDependenciesRefreshResource.class));
    }
}
