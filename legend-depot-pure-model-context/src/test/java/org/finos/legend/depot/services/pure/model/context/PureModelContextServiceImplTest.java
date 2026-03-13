//  Copyright 2022 Goldman Sachs
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

package org.finos.legend.depot.services.pure.model.context;

import org.finos.legend.depot.services.api.entities.EntitiesService;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.engine.protocol.pure.PureClientVersions;
import org.finos.legend.engine.protocol.pure.v1.model.context.AlloySDLC;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

public class PureModelContextServiceImplTest
{
    private EntitiesService mockEntitiesService;
    private ProjectsService mockProjectsService;
    private PureModelContextServiceImpl service;

    @BeforeEach
    public void setup()
    {
        mockEntitiesService = Mockito.mock(EntitiesService.class);
        mockProjectsService = Mockito.mock(ProjectsService.class);
        service = new PureModelContextServiceImpl(mockEntitiesService, mockProjectsService);
    }

    @Test
    public void testResolveAndValidateClientVersionWithNull()
    {
        String result = service.resolveAndValidateClientVersion(null);
        Assertions.assertEquals(PureClientVersions.production, result);
    }

    @Test
    public void testResolveAndValidateClientVersionWithValidVersion()
    {
        if (!PureClientVersions.versions.isEmpty())
        {
            String validVersion = PureClientVersions.versions.get(0);
            String result = service.resolveAndValidateClientVersion(validVersion);
            Assertions.assertEquals(validVersion, result);
        }
    }

    @Test
    public void testResolveAndValidateClientVersionWithInvalidVersion()
    {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.resolveAndValidateClientVersion("v_invalid_999"));
    }

    @Test
    public void testBuildAlloySDLC()
    {
        AlloySDLC sdlc = service.buildAlloySDLC("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("org.finos:legend-depot", sdlc.project);
        Assertions.assertEquals("1.0.0", sdlc.baseVersion);
    }

    @Test
    public void testBuildPureModelContextDataWithEmptyEntities()
    {
        PureModelContextData pmcd = service.buildPureModelContextData(
                Stream.empty(), "org.finos", "legend-depot", "1.0.0",
                PureClientVersions.production, true);
        Assertions.assertNotNull(pmcd);
    }

    @Test
    public void testGetPureModelContextDataNonTransitive()
    {
        when(mockProjectsService.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockEntitiesService.getEntities("org.finos", "legend-depot", "1.0.0")).thenReturn(Collections.emptyList());

        PureModelContextData result = service.getPureModelContextData("org.finos", "legend-depot", "1.0.0", null, false, true);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testCombinePureModelContextData()
    {
        PureModelContextData pmcd1 = PureModelContextData.newBuilder().build();
        PureModelContextData pmcd2 = PureModelContextData.newBuilder().build();

        PureModelContextData combined = service.combinePureModelContextData(pmcd1, pmcd2);
        Assertions.assertNotNull(combined);
    }

    @Test
    public void testCombinePureModelContextDataWithNullChildThrows()
    {
        PureModelContextData pmcd1 = PureModelContextData.newBuilder().build();
        Assertions.assertThrows(NullPointerException.class,
                () -> service.combinePureModelContextData(pmcd1, null));
    }
}
