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
import org.finos.legend.depot.services.api.artifacts.refresh.ParentEvent;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;

public class TestArtifactsSchedulesModule
{
    private ArtifactsSchedulesModule module;
    private SchedulesFactory schedulesFactory;
    private ArtifactsRefreshService artifactsRefreshService;
    private ArtifactsPurgeService artifactsPurgeService;

    @BeforeEach
    public void setUp()
    {
        module = new ArtifactsSchedulesModule();
        schedulesFactory = mock(SchedulesFactory.class);
        artifactsRefreshService = mock(ArtifactsRefreshService.class);
        artifactsPurgeService = mock(ArtifactsPurgeService.class);
    }

    @Test
    public void testInitVersionsRegistersScheduleAndReturnsTrue()
    {
        ArtifactsRefreshPolicyConfiguration configuration = new ArtifactsRefreshPolicyConfiguration(5000L, null);

        boolean result = module.initVersions(schedulesFactory, artifactsRefreshService, configuration);

        Assertions.assertTrue(result);
        verify(schedulesFactory).registerExternalTriggerSchedule(
                eq(ParentEvent.REFRESH_ALL_VERSION_ARTIFACTS_SCHEDULE.name()),
                eq(5000L),
                any(Supplier.class));
    }

    @Test
    public void testScheduleEvictionOfProjectVersionsRegistersScheduleAndReturnsTrue()
    {
        ArtifactsRetentionPolicyConfiguration retentionConfig = new ArtifactsRetentionPolicyConfiguration(null, null, null);

        boolean result = module.scheduleEvictionOfProjectVersions(schedulesFactory, artifactsPurgeService, retentionConfig);

        Assertions.assertTrue(result);
        verify(schedulesFactory).registerSingleInstance(
                eq("evict-LRU-project-versions"),
                eq(SchedulesFactory.MINUTE),
                eq(24 * SchedulesFactory.HOUR),
                any(Supplier.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testScheduleEvictionOfProjectVersionsInvokesEvictLeastRecentlyUsed()
    {
        ArtifactsRetentionPolicyConfiguration retentionConfig = new ArtifactsRetentionPolicyConfiguration(null, 100, 50);
        ArgumentCaptor<Supplier<Object>> supplierCaptor = ArgumentCaptor.forClass(Supplier.class);

        module.scheduleEvictionOfProjectVersions(schedulesFactory, artifactsPurgeService, retentionConfig);

        verify(schedulesFactory).registerSingleInstance(
                eq("evict-LRU-project-versions"),
                anyLong(),
                anyLong(),
                supplierCaptor.capture());

        Object taskResult = supplierCaptor.getValue().get();
        Assertions.assertEquals(true, taskResult);
        verify(artifactsPurgeService).evictLeastRecentlyUsed(100, 50);
    }

    @Test
    public void testScheduleDeprecationOfProjectVersionsRegistersScheduleAndReturnsTrue()
    {
        boolean result = module.scheduleDeprecationOfProjectVersions(schedulesFactory, artifactsPurgeService);

        Assertions.assertTrue(result);
        verify(schedulesFactory).registerSingleInstance(
                eq("deprecate-versions-notInRepository"),
                eq(SchedulesFactory.MINUTE),
                eq(48 * SchedulesFactory.HOUR),
                any(Supplier.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testScheduleDeprecationOfProjectVersionsInvokesDeprecateVersionsNotInRepository()
    {
        ArgumentCaptor<Supplier<Object>> supplierCaptor = ArgumentCaptor.forClass(Supplier.class);

        module.scheduleDeprecationOfProjectVersions(schedulesFactory, artifactsPurgeService);

        verify(schedulesFactory).registerSingleInstance(
                eq("deprecate-versions-notInRepository"),
                anyLong(),
                anyLong(),
                supplierCaptor.capture());

        Object taskResult = supplierCaptor.getValue().get();
        Assertions.assertEquals(true, taskResult);
        verify(artifactsPurgeService).deprecateVersionsNotInRepository();
    }
}
