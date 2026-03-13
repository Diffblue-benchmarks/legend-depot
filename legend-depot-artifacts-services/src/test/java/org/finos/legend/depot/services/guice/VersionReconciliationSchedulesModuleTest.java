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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.finos.legend.depot.services.guice.VersionReconciliationSchedulesModule.REPOSITORY_METRICS_SCHEDULE;
import static org.finos.legend.depot.services.guice.VersionReconciliationSchedulesModule.SYNC_PROJECT_LATEST_VERSIONS_SCHEDULE;
import org.mockito.ArgumentCaptor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VersionReconciliationSchedulesModuleTest
{
    private static final String PROJECTS = "projects";
    private static final String REPO_VERSIONS = "repo_versions";
    private static final String STORE_VERSIONS = "store_versions";
    private static final String MISSING_REPO_VERSIONS = "missing_repo_versions";
    private static final String MISSING_STORE_VERSIONS = "missing_store_versions";
    private static final String REPO_EXCEPTIONS = "repo_exceptions";
    private static final String PROJECT_UPDATE_EXCEPTIONS = "project_update_exceptions";

    private VersionReconciliationSchedulesModule module;
    private PrometheusConfiguration prometheusConfiguration;
    private PrometheusMetricsHandler metricsHandler;
    private SchedulesFactory schedulesFactory;
    private VersionsReconciliationService versionsReconciliationService;

    @BeforeEach
    public void setUp()
    {
        module = new VersionReconciliationSchedulesModule();
        prometheusConfiguration = mock(PrometheusConfiguration.class);
        metricsHandler = mock(PrometheusMetricsHandler.class);
        schedulesFactory = mock(SchedulesFactory.class);
        versionsReconciliationService = mock(VersionsReconciliationService.class);
    }

    @Test
    public void canRegisterMetricsWhenPrometheusEnabled()
    {
        when(prometheusConfiguration.isEnabled()).thenReturn(true);
        when(prometheusConfiguration.getMetricsHandler()).thenReturn(metricsHandler);

        boolean result = module.registerMetrics(prometheusConfiguration, schedulesFactory, versionsReconciliationService);

        Assertions.assertTrue(result);
        verify(prometheusConfiguration).isEnabled();
        verify(prometheusConfiguration).getMetricsHandler();
        verify(metricsHandler).registerGauge(PROJECTS, PROJECTS);
        verify(metricsHandler).registerGauge(REPO_VERSIONS, REPO_VERSIONS);
        verify(metricsHandler).registerGauge(STORE_VERSIONS, STORE_VERSIONS);
        verify(metricsHandler).registerGauge(MISSING_REPO_VERSIONS, MISSING_REPO_VERSIONS);
        verify(metricsHandler).registerGauge(MISSING_STORE_VERSIONS, MISSING_STORE_VERSIONS);
        verify(metricsHandler).registerGauge(REPO_EXCEPTIONS, REPO_EXCEPTIONS);
        verify(metricsHandler).registerGauge(PROJECT_UPDATE_EXCEPTIONS, PROJECT_UPDATE_EXCEPTIONS);
    }

    @Test
    public void canRegisterSchedulesWhenPrometheusEnabled()
    {
        when(prometheusConfiguration.isEnabled()).thenReturn(true);
        when(prometheusConfiguration.getMetricsHandler()).thenReturn(metricsHandler);

        boolean result = module.registerMetrics(prometheusConfiguration, schedulesFactory, versionsReconciliationService);

        Assertions.assertTrue(result);
        verify(schedulesFactory).register(eq(REPOSITORY_METRICS_SCHEDULE), eq(5 * SchedulesFactory.MINUTE), eq(5 * SchedulesFactory.MINUTE), any());
        verify(schedulesFactory).register(eq(SYNC_PROJECT_LATEST_VERSIONS_SCHEDULE), eq(5 * SchedulesFactory.MINUTE), eq(5 * SchedulesFactory.MINUTE), any());
    }

    @Test
    public void canHandlePrometheusDisabled()
    {
        when(prometheusConfiguration.isEnabled()).thenReturn(false);

        boolean result = module.registerMetrics(prometheusConfiguration, schedulesFactory, versionsReconciliationService);

        Assertions.assertTrue(result);
        verify(prometheusConfiguration).isEnabled();
        verify(prometheusConfiguration, never()).getMetricsHandler();
        verify(metricsHandler, never()).registerGauge(PROJECTS, PROJECTS);
        verify(metricsHandler, never()).registerGauge(REPO_VERSIONS, REPO_VERSIONS);
        verify(metricsHandler, never()).registerGauge(STORE_VERSIONS, STORE_VERSIONS);
        verify(metricsHandler, never()).registerGauge(MISSING_REPO_VERSIONS, MISSING_REPO_VERSIONS);
        verify(metricsHandler, never()).registerGauge(MISSING_STORE_VERSIONS, MISSING_STORE_VERSIONS);
        verify(metricsHandler, never()).registerGauge(REPO_EXCEPTIONS, REPO_EXCEPTIONS);
        verify(metricsHandler, never()).registerGauge(PROJECT_UPDATE_EXCEPTIONS, PROJECT_UPDATE_EXCEPTIONS);
        verify(schedulesFactory, never()).register(eq(REPOSITORY_METRICS_SCHEDULE), eq(5 * SchedulesFactory.MINUTE), eq(5 * SchedulesFactory.MINUTE), any());
        verify(schedulesFactory, never()).register(eq(SYNC_PROJECT_LATEST_VERSIONS_SCHEDULE), eq(5 * SchedulesFactory.MINUTE), eq(5 * SchedulesFactory.MINUTE), any());
    }

    @Test
    public void canAlwaysReturnTrueRegardlessOfPrometheusState()
    {
        when(prometheusConfiguration.isEnabled()).thenReturn(true);
        when(prometheusConfiguration.getMetricsHandler()).thenReturn(metricsHandler);

        boolean resultEnabled = module.registerMetrics(prometheusConfiguration, schedulesFactory, versionsReconciliationService);

        when(prometheusConfiguration.isEnabled()).thenReturn(false);

        boolean resultDisabled = module.registerMetrics(prometheusConfiguration, schedulesFactory, versionsReconciliationService);

        Assertions.assertTrue(resultEnabled);
        Assertions.assertTrue(resultDisabled);
    }
}
