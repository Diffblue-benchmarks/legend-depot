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
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.finos.legend.depot.services.api.artifacts.reconciliation.VersionsReconciliationService;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.finos.legend.depot.services.artifacts.reconciliation.VersionsReconciliationServiceImpl.MISSING_REPO_VERSIONS;
import static org.finos.legend.depot.services.artifacts.reconciliation.VersionsReconciliationServiceImpl.MISSING_STORE_VERSIONS;
import static org.finos.legend.depot.services.artifacts.reconciliation.VersionsReconciliationServiceImpl.PROJECTS;
import static org.finos.legend.depot.services.artifacts.reconciliation.VersionsReconciliationServiceImpl.PROJECT_UPDATE_EXCEPTIONS;
import static org.finos.legend.depot.services.artifacts.reconciliation.VersionsReconciliationServiceImpl.REPO_EXCEPTIONS;
import static org.finos.legend.depot.services.artifacts.reconciliation.VersionsReconciliationServiceImpl.REPO_VERSIONS;
import static org.finos.legend.depot.services.artifacts.reconciliation.VersionsReconciliationServiceImpl.STORE_VERSIONS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;

public class TestVersionReconciliationSchedulesModule
{
    private final PrometheusConfiguration prometheusConfiguration = mock(PrometheusConfiguration.class);
    private final SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    private final VersionsReconciliationService versionsReconciliationService = mock(VersionsReconciliationService.class);
    private final PrometheusMetricsHandler metricsHandler = mock(PrometheusMetricsHandler.class);
    private final VersionReconciliationSchedulesModule module = new VersionReconciliationSchedulesModule();

    @Test
    public void testRegisterMetricsWhenEnabled()
    {
        when(prometheusConfiguration.isEnabled()).thenReturn(true);
        when(prometheusConfiguration.getMetricsHandler()).thenReturn(metricsHandler);

        boolean result = module.registerMetrics(prometheusConfiguration, schedulesFactory, versionsReconciliationService);

        Assertions.assertTrue(result);
        verify(metricsHandler).registerGauge(PROJECTS, PROJECTS);
        verify(metricsHandler).registerGauge(REPO_VERSIONS, REPO_VERSIONS);
        verify(metricsHandler).registerGauge(STORE_VERSIONS, STORE_VERSIONS);
        verify(metricsHandler).registerGauge(MISSING_REPO_VERSIONS, MISSING_REPO_VERSIONS);
        verify(metricsHandler).registerGauge(MISSING_STORE_VERSIONS, MISSING_STORE_VERSIONS);
        verify(metricsHandler).registerGauge(REPO_EXCEPTIONS, REPO_EXCEPTIONS);
        verify(metricsHandler).registerGauge(PROJECT_UPDATE_EXCEPTIONS, PROJECT_UPDATE_EXCEPTIONS);
        verify(schedulesFactory, times(2)).register(anyString(), anyLong(), anyLong(), any());
        verify(schedulesFactory).register(eq(VersionReconciliationSchedulesModule.REPOSITORY_METRICS_SCHEDULE), anyLong(), anyLong(), any());
        verify(schedulesFactory).register(eq(VersionReconciliationSchedulesModule.SYNC_PROJECT_LATEST_VERSIONS_SCHEDULE), anyLong(), anyLong(), any());
    }

    @Test
    public void testRegisterMetricsWhenDisabled()
    {
        when(prometheusConfiguration.isEnabled()).thenReturn(false);

        boolean result = module.registerMetrics(prometheusConfiguration, schedulesFactory, versionsReconciliationService);

        Assertions.assertTrue(result);
        verifyNoInteractions(schedulesFactory);
        verifyNoInteractions(metricsHandler);
    }
}
