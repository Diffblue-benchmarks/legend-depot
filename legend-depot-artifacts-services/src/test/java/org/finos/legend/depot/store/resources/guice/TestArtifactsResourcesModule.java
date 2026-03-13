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
import org.finos.legend.depot.services.api.artifacts.refresh.ArtifactsRefreshService;
import org.finos.legend.depot.services.api.artifacts.refresh.RefreshDependenciesService;
import org.finos.legend.depot.services.api.artifacts.reconciliation.VersionsReconciliationService;
import org.finos.legend.depot.store.resources.artifacts.ArtifactDependenciesRefreshResource;
import org.finos.legend.depot.store.resources.artifacts.ArtifactsPurgeResource;
import org.finos.legend.depot.store.resources.artifacts.ArtifactsRefreshResource;
import org.finos.legend.depot.store.resources.artifacts.VersionsReconciliationResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.security.Principal;

import static org.mockito.Mockito.mock;

public class TestArtifactsResourcesModule
{
    @Test
    public void canCreateInjectorWithModule()
    {
        ArtifactsPurgeService mockPurgeService = mock(ArtifactsPurgeService.class);
        ArtifactsRefreshService mockRefreshService = mock(ArtifactsRefreshService.class);
        VersionsReconciliationService mockVersionsReconciliationService = mock(VersionsReconciliationService.class);
        RefreshDependenciesService mockDependenciesService = mock(RefreshDependenciesService.class);
        AuthorisationProvider mockAuthProvider = mock(AuthorisationProvider.class);
        Provider<Principal> mockPrincipalProvider = mock(Provider.class);

        Injector injector = Guice.createInjector(
                new ArtifactsResourcesModule(),
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(ArtifactsPurgeService.class).toInstance(mockPurgeService);
                        bind(ArtifactsRefreshService.class).toInstance(mockRefreshService);
                        bind(VersionsReconciliationService.class).toInstance(mockVersionsReconciliationService);
                        bind(RefreshDependenciesService.class).toInstance(mockDependenciesService);
                        bind(AuthorisationProvider.class).toInstance(mockAuthProvider);
                        bind(Principal.class).annotatedWith(Names.named("requestPrincipal")).toProvider(mockPrincipalProvider);
                    }
                }
        );

        ArtifactsPurgeResource purgeResource = injector.getInstance(ArtifactsPurgeResource.class);
        Assertions.assertNotNull(purgeResource);

        ArtifactsRefreshResource refreshResource = injector.getInstance(ArtifactsRefreshResource.class);
        Assertions.assertNotNull(refreshResource);

        VersionsReconciliationResource reconciliationResource = injector.getInstance(VersionsReconciliationResource.class);
        Assertions.assertNotNull(reconciliationResource);

        ArtifactDependenciesRefreshResource dependenciesResource = injector.getInstance(ArtifactDependenciesRefreshResource.class);
        Assertions.assertNotNull(dependenciesResource);
    }

    @Test
    public void canGetArtifactsPurgeResourceFromInjector()
    {
        ArtifactsPurgeService mockPurgeService = mock(ArtifactsPurgeService.class);
        ArtifactsRefreshService mockRefreshService = mock(ArtifactsRefreshService.class);
        VersionsReconciliationService mockVersionsReconciliationService = mock(VersionsReconciliationService.class);
        RefreshDependenciesService mockDependenciesService = mock(RefreshDependenciesService.class);
        AuthorisationProvider mockAuthProvider = mock(AuthorisationProvider.class);
        Provider<Principal> mockPrincipalProvider = mock(Provider.class);

        Injector injector = Guice.createInjector(
                new ArtifactsResourcesModule(),
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(ArtifactsPurgeService.class).toInstance(mockPurgeService);
                        bind(ArtifactsRefreshService.class).toInstance(mockRefreshService);
                        bind(VersionsReconciliationService.class).toInstance(mockVersionsReconciliationService);
                        bind(RefreshDependenciesService.class).toInstance(mockDependenciesService);
                        bind(AuthorisationProvider.class).toInstance(mockAuthProvider);
                        bind(Principal.class).annotatedWith(Names.named("requestPrincipal")).toProvider(mockPrincipalProvider);
                    }
                }
        );

        ArtifactsPurgeResource resource = injector.getInstance(ArtifactsPurgeResource.class);
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetArtifactsRefreshResourceFromInjector()
    {
        ArtifactsPurgeService mockPurgeService = mock(ArtifactsPurgeService.class);
        ArtifactsRefreshService mockRefreshService = mock(ArtifactsRefreshService.class);
        VersionsReconciliationService mockVersionsReconciliationService = mock(VersionsReconciliationService.class);
        RefreshDependenciesService mockDependenciesService = mock(RefreshDependenciesService.class);
        AuthorisationProvider mockAuthProvider = mock(AuthorisationProvider.class);
        Provider<Principal> mockPrincipalProvider = mock(Provider.class);

        Injector injector = Guice.createInjector(
                new ArtifactsResourcesModule(),
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(ArtifactsPurgeService.class).toInstance(mockPurgeService);
                        bind(ArtifactsRefreshService.class).toInstance(mockRefreshService);
                        bind(VersionsReconciliationService.class).toInstance(mockVersionsReconciliationService);
                        bind(RefreshDependenciesService.class).toInstance(mockDependenciesService);
                        bind(AuthorisationProvider.class).toInstance(mockAuthProvider);
                        bind(Principal.class).annotatedWith(Names.named("requestPrincipal")).toProvider(mockPrincipalProvider);
                    }
                }
        );

        ArtifactsRefreshResource resource = injector.getInstance(ArtifactsRefreshResource.class);
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetVersionsReconciliationResourceFromInjector()
    {
        ArtifactsPurgeService mockPurgeService = mock(ArtifactsPurgeService.class);
        ArtifactsRefreshService mockRefreshService = mock(ArtifactsRefreshService.class);
        VersionsReconciliationService mockVersionsReconciliationService = mock(VersionsReconciliationService.class);
        RefreshDependenciesService mockDependenciesService = mock(RefreshDependenciesService.class);
        AuthorisationProvider mockAuthProvider = mock(AuthorisationProvider.class);
        Provider<Principal> mockPrincipalProvider = mock(Provider.class);

        Injector injector = Guice.createInjector(
                new ArtifactsResourcesModule(),
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(ArtifactsPurgeService.class).toInstance(mockPurgeService);
                        bind(ArtifactsRefreshService.class).toInstance(mockRefreshService);
                        bind(VersionsReconciliationService.class).toInstance(mockVersionsReconciliationService);
                        bind(RefreshDependenciesService.class).toInstance(mockDependenciesService);
                        bind(AuthorisationProvider.class).toInstance(mockAuthProvider);
                        bind(Principal.class).annotatedWith(Names.named("requestPrincipal")).toProvider(mockPrincipalProvider);
                    }
                }
        );

        VersionsReconciliationResource resource = injector.getInstance(VersionsReconciliationResource.class);
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetArtifactDependenciesRefreshResourceFromInjector()
    {
        ArtifactsPurgeService mockPurgeService = mock(ArtifactsPurgeService.class);
        ArtifactsRefreshService mockRefreshService = mock(ArtifactsRefreshService.class);
        VersionsReconciliationService mockVersionsReconciliationService = mock(VersionsReconciliationService.class);
        RefreshDependenciesService mockDependenciesService = mock(RefreshDependenciesService.class);
        AuthorisationProvider mockAuthProvider = mock(AuthorisationProvider.class);
        Provider<Principal> mockPrincipalProvider = mock(Provider.class);

        Injector injector = Guice.createInjector(
                new ArtifactsResourcesModule(),
                new AbstractModule()
                {
                    @Override
                    protected void configure()
                    {
                        bind(ArtifactsPurgeService.class).toInstance(mockPurgeService);
                        bind(ArtifactsRefreshService.class).toInstance(mockRefreshService);
                        bind(VersionsReconciliationService.class).toInstance(mockVersionsReconciliationService);
                        bind(RefreshDependenciesService.class).toInstance(mockDependenciesService);
                        bind(AuthorisationProvider.class).toInstance(mockAuthProvider);
                        bind(Principal.class).annotatedWith(Names.named("requestPrincipal")).toProvider(mockPrincipalProvider);
                    }
                }
        );

        ArtifactDependenciesRefreshResource resource = injector.getInstance(ArtifactDependenciesRefreshResource.class);
        Assertions.assertNotNull(resource);
    }
}
