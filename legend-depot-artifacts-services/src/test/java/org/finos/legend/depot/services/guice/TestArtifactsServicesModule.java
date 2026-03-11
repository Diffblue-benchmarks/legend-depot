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

import com.google.inject.spi.Element;
import com.google.inject.spi.Elements;
import com.google.inject.spi.LinkedKeyBinding;
import com.google.inject.spi.PrivateElements;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRetentionPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.handlers.ProjectArtifactHandlerFactory;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.EntitiesArtifactsHandler;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.EntityArtifactsProvider;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.VersionedEntitiesArtifactsHandler;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.VersionedEntityArtifactsProvider;
import org.finos.legend.depot.services.api.artifacts.handlers.generations.FileGenerationsArtifactsHandler;
import org.finos.legend.depot.services.api.artifacts.handlers.generations.FileGenerationsArtifactsProvider;
import org.finos.legend.depot.services.artifacts.handlers.entities.EntitiesHandlerImpl;
import org.finos.legend.depot.services.artifacts.handlers.entities.EntityProvider;
import org.finos.legend.depot.services.artifacts.handlers.entities.VersionedEntitiesHandlerImpl;
import org.finos.legend.depot.services.artifacts.handlers.entities.VersionedEntityProvider;
import org.finos.legend.depot.services.artifacts.handlers.generations.FileGenerationHandlerImpl;
import org.finos.legend.depot.services.artifacts.handlers.generations.FileGenerationsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestArtifactsServicesModule
{
    private final ArtifactsServicesModule module = new ArtifactsServicesModule();

    @Test
    public void canRegisterEntityHandler()
    {
        EntitiesArtifactsHandler handler = mock(EntitiesArtifactsHandler.class);
        boolean result = module.registerEntityHandler(handler);
        Assertions.assertTrue(result);
        Assertions.assertNotNull(ProjectArtifactHandlerFactory.getArtifactHandler(ArtifactType.ENTITIES));
    }

    @Test
    public void canRegisterFileGenerationHandler()
    {
        FileGenerationsArtifactsHandler handler = mock(FileGenerationsArtifactsHandler.class);
        boolean result = module.registerFileGenerationHandler(handler);
        Assertions.assertTrue(result);
        Assertions.assertNotNull(ProjectArtifactHandlerFactory.getArtifactHandler(ArtifactType.FILE_GENERATIONS));
    }

    @Test
    public void canRegisterMetrics()
    {
        PrometheusMetricsHandler metricsHandler = mock(PrometheusMetricsHandler.class);
        boolean result = module.registerMetrics(metricsHandler);
        Assertions.assertTrue(result);
        verify(metricsHandler).registerCounter(
                org.finos.legend.depot.services.artifacts.refresh.ProjectVersionRefreshHandler.VERSION_REFRESH_COUNTER,
                org.finos.legend.depot.services.artifacts.refresh.ProjectVersionRefreshHandler.TOTAL_NUMBER_OF_VERSIONS_REFRESH);
        verify(metricsHandler).registerHistogram(
                org.finos.legend.depot.services.artifacts.refresh.ProjectVersionRefreshHandler.VERSION_REFRESH_DURATION,
                org.finos.legend.depot.services.artifacts.refresh.ProjectVersionRefreshHandler.VERSION_REFRESH_DURATION_HELP);
    }

    @Test
    public void canGetNoOfSnapshotVersionsToRetain()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(10, null, null);
        int result = module.getNoOfSnapshotVersionsToRetain(config);
        Assertions.assertEquals(10, result);
    }

    @Test
    public void canGetNoOfSnapshotVersionsToRetainWithDefaults()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(null, null, null);
        int result = module.getNoOfSnapshotVersionsToRetain(config);
        Assertions.assertEquals(5, result);
    }

    @Test
    public void canConfigureHandlers()
    {
        List<Element> elements = Elements.getElements(module);
        List<Element> privateElements = elements.stream()
                .filter(e -> e instanceof PrivateElements)
                .flatMap(e -> ((PrivateElements) e).getElements().stream())
                .collect(Collectors.toList());

        Map<Class<?>, Class<?>> linkedBindings = privateElements.stream()
                .filter(e -> e instanceof LinkedKeyBinding)
                .map(e -> (LinkedKeyBinding<?>) e)
                .collect(Collectors.toMap(
                        b -> b.getKey().getTypeLiteral().getRawType(),
                        b -> b.getLinkedKey().getTypeLiteral().getRawType()
                ));

        Assertions.assertEquals(EntityProvider.class, linkedBindings.get(EntityArtifactsProvider.class));
        Assertions.assertEquals(VersionedEntityProvider.class, linkedBindings.get(VersionedEntityArtifactsProvider.class));
        Assertions.assertEquals(FileGenerationsProvider.class, linkedBindings.get(FileGenerationsArtifactsProvider.class));
        Assertions.assertEquals(EntitiesHandlerImpl.class, linkedBindings.get(EntitiesArtifactsHandler.class));
        Assertions.assertEquals(VersionedEntitiesHandlerImpl.class, linkedBindings.get(VersionedEntitiesArtifactsHandler.class));
        Assertions.assertEquals(FileGenerationHandlerImpl.class, linkedBindings.get(FileGenerationsArtifactsHandler.class));
    }
}
