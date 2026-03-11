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

import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRetentionPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.handlers.ProjectArtifactHandlerFactory;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.EntitiesArtifactsHandler;
import org.finos.legend.depot.services.api.artifacts.handlers.generations.FileGenerationsArtifactsHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
