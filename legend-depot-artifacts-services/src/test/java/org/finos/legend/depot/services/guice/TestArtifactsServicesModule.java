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
import com.google.inject.CreationException;
import com.google.inject.Guice;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRetentionPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.handlers.ProjectArtifactHandlerFactory;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.EntitiesArtifactsHandler;
import org.finos.legend.depot.services.api.artifacts.handlers.generations.FileGenerationsArtifactsHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestArtifactsServicesModule
{
    private ArtifactsServicesModule module;

    @BeforeEach
    public void setUp()
    {
        module = new ArtifactsServicesModule();
    }

    @Test
    public void canCreateModule()
    {
        Assertions.assertNotNull(module);
    }

    @Test
    public void canInstallModule()
    {
        try
        {
            Guice.createInjector(module, new AbstractModule()
            {
                @Override
                protected void configure()
                {
                }
            });
        }
        catch (CreationException e)
        {
        }

        Assertions.assertNotNull(module);
    }

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
        verify(metricsHandler, times(1)).registerCounter(anyString(), anyString());
        verify(metricsHandler, times(1)).registerHistogram(anyString(), anyString());
    }

    @Test
    public void canGetNoOfSnapshotVersionsToRetain()
    {
        ArtifactsRetentionPolicyConfiguration config = mock(ArtifactsRetentionPolicyConfiguration.class);
        when(config.getMaximumSnapshotsAllowed()).thenReturn(5);

        int result = module.getNoOfSnapshotVersionsToRetain(config);

        Assertions.assertEquals(5, result);
        verify(config, times(1)).getMaximumSnapshotsAllowed();
    }
}
