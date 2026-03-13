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

import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRefreshPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRetentionPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.purge.ArtifactsPurgeService;
import org.finos.legend.depot.services.api.artifacts.refresh.ArtifactsRefreshService;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestArtifactsSchedulesModule
{
    private SchedulesFactory schedulesFactory;
    private ArtifactsRefreshService artifactsRefreshService;
    private ArtifactsPurgeService artifactsPurgeService;
    private ArtifactsRefreshPolicyConfiguration refreshPolicyConfiguration;
    private ArtifactsRetentionPolicyConfiguration retentionPolicyConfiguration;
    private ArtifactsSchedulesModule module;

    @BeforeEach
    public void setup()
    {
        schedulesFactory = mock(SchedulesFactory.class);
        artifactsRefreshService = mock(ArtifactsRefreshService.class);
        artifactsPurgeService = mock(ArtifactsPurgeService.class);
        refreshPolicyConfiguration = mock(ArtifactsRefreshPolicyConfiguration.class);
        retentionPolicyConfiguration = mock(ArtifactsRetentionPolicyConfiguration.class);
        module = new ArtifactsSchedulesModule();
    }

    @Test
    public void canCallConfigure()
    {
        module.configure();
    }

    @Test
    public void canInitVersions()
    {
        long updateInterval = 7200000L;
        when(refreshPolicyConfiguration.getVersionsUpdateIntervalInMillis()).thenReturn(updateInterval);

        boolean result = module.initVersions(schedulesFactory, artifactsRefreshService, refreshPolicyConfiguration);

        Assertions.assertTrue(result);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> intervalCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Supplier> supplierCaptor = ArgumentCaptor.forClass(Supplier.class);
        verify(schedulesFactory).registerExternalTriggerSchedule(nameCaptor.capture(), intervalCaptor.capture(), supplierCaptor.capture());
        Assertions.assertEquals("REFRESH_ALL_VERSION_ARTIFACTS_SCHEDULE", nameCaptor.getValue());
        Assertions.assertEquals(updateInterval, intervalCaptor.getValue());
        Assertions.assertNotNull(supplierCaptor.getValue());

        supplierCaptor.getValue().get();
        verify(artifactsRefreshService).refreshAllVersionsForAllProjects(false, false, false, "REFRESH_ALL_VERSION_ARTIFACTS_SCHEDULE");
    }

    @Test
    public void canScheduleEvictionOfProjectVersions()
    {
        int ttlForVersions = 365;
        int ttlForSnapshots = 30;
        when(retentionPolicyConfiguration.getTtlForVersions()).thenReturn(ttlForVersions);
        when(retentionPolicyConfiguration.getTtlForSnapshots()).thenReturn(ttlForSnapshots);

        boolean result = module.scheduleEvictionOfProjectVersions(schedulesFactory, artifactsPurgeService, retentionPolicyConfiguration);

        Assertions.assertTrue(result);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> delayCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> intervalCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Supplier> supplierCaptor = ArgumentCaptor.forClass(Supplier.class);
        verify(schedulesFactory).registerSingleInstance(nameCaptor.capture(), delayCaptor.capture(), intervalCaptor.capture(), supplierCaptor.capture());
        Assertions.assertEquals("evict-LRU-project-versions", nameCaptor.getValue());
        Assertions.assertEquals(SchedulesFactory.MINUTE, delayCaptor.getValue());
        Assertions.assertEquals(24 * SchedulesFactory.HOUR, intervalCaptor.getValue());
        Assertions.assertNotNull(supplierCaptor.getValue());

        Object lambdaResult = supplierCaptor.getValue().get();
        Assertions.assertEquals(true, lambdaResult);
        verify(artifactsPurgeService).evictLeastRecentlyUsed(ttlForVersions, ttlForSnapshots);
    }

    @Test
    public void canScheduleDeprecationOfProjectVersions()
    {
        boolean result = module.scheduleDeprecationOfProjectVersions(schedulesFactory, artifactsPurgeService);

        Assertions.assertTrue(result);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> delayCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> intervalCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Supplier> supplierCaptor = ArgumentCaptor.forClass(Supplier.class);
        verify(schedulesFactory).registerSingleInstance(nameCaptor.capture(), delayCaptor.capture(), intervalCaptor.capture(), supplierCaptor.capture());
        Assertions.assertEquals("deprecate-versions-notInRepository", nameCaptor.getValue());
        Assertions.assertEquals(SchedulesFactory.MINUTE, delayCaptor.getValue());
        Assertions.assertEquals(48 * SchedulesFactory.HOUR, intervalCaptor.getValue());
        Assertions.assertNotNull(supplierCaptor.getValue());

        Object lambdaResult = supplierCaptor.getValue().get();
        Assertions.assertEquals(true, lambdaResult);
        verify(artifactsPurgeService).deprecateVersionsNotInRepository();
    }
}
