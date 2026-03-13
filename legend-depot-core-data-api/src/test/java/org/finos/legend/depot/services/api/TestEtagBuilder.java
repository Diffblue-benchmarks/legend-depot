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

package org.finos.legend.depot.services.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestEtagBuilder
{

    @Test
    public void canCreateEtagBuilder()
    {
        EtagBuilder builder = EtagBuilder.create();
        Assertions.assertNotNull(builder);
    }

    @Test
    public void canBuildEtagWithConstantGAV()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos.legend", "legend-depot", "1.0.0")
                .build();
        Assertions.assertEquals("org.finos.legendlegend-depot1.0.0", etag);
    }

    @Test
    public void canBuildEtagWithGAVAndProtocolVersion()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos.legend", "legend-depot", "2.3.1")
                .withProtocolVersion("v1_2_3")
                .build();
        Assertions.assertEquals("org.finos.legendlegend-depot2.3.1v1_2_3", etag);
    }

    @Test
    public void returnsNullForSnapshotVersion()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos.legend", "legend-depot", "1.0.0-SNAPSHOT")
                .build();
        Assertions.assertNull(etag);
    }

    @Test
    public void returnsNullForVersionAlias()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos.legend", "legend-depot", "latest")
                .build();
        Assertions.assertNull(etag);
    }

    @Test
    public void returnsNullForNullProtocolVersion()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos.legend", "legend-depot", "1.0.0")
                .withProtocolVersion(null)
                .build();
        Assertions.assertNull(etag);
    }

    @Test
    public void returnsNullForHeadProtocolVersion()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos.legend", "legend-depot", "1.0.0")
                .withProtocolVersion("vX_X_X")
                .build();
        Assertions.assertNull(etag);
    }

    @Test
    public void returnsNullForHeadProtocolVersionCaseInsensitive()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos.legend", "legend-depot", "1.0.0")
                .withProtocolVersion("VX_X_X")
                .build();
        Assertions.assertNull(etag);
    }

    @Test
    public void canBuildEtagWithMultipleParameters()
    {
        String etag = EtagBuilder.create()
                .withGAV("com.example", "test-artifact", "3.2.1")
                .withProtocolVersion("v2_0_0")
                .build();
        Assertions.assertEquals("com.exampletest-artifact3.2.1v2_0_0", etag);
    }

    @Test
    public void canBuildEmptyEtagWithNoParameters()
    {
        String etag = EtagBuilder.create()
                .build();
        Assertions.assertEquals("", etag);
    }

    @Test
    public void returnsNullWhenMixingConstantAndNonConstantParams()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos.legend", "legend-depot", "1.0.0")
                .withGAV("org.finos.legend", "legend-depot-other", "master-SNAPSHOT")
                .build();
        Assertions.assertNull(etag);
    }
}
