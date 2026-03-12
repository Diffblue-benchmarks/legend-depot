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
    public void testBuildWithConstantGAV()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos", "artifact", "1.0.0")
                .build();
        Assertions.assertNotNull(etag);
        Assertions.assertEquals("org.finosartifact1.0.0", etag);
    }

    @Test
    public void testBuildWithSnapshotVersionReturnsNull()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos", "artifact", "master-SNAPSHOT")
                .build();
        Assertions.assertNull(etag);
    }

    @Test
    public void testBuildWithVersionAliasLatestReturnsNull()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos", "artifact", "latest")
                .build();
        Assertions.assertNull(etag);
    }

    @Test
    public void testBuildWithVersionAliasHeadReturnsNull()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos", "artifact", "head")
                .build();
        Assertions.assertNull(etag);
    }

    @Test
    public void testBuildWithProtocolVersion()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos", "artifact", "1.0.0")
                .withProtocolVersion("v1_0_0")
                .build();
        Assertions.assertNotNull(etag);
        Assertions.assertEquals("org.finosartifact1.0.0v1_0_0", etag);
    }

    @Test
    public void testBuildWithHeadProtocolVersionReturnsNull()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos", "artifact", "1.0.0")
                .withProtocolVersion("vX_X_X")
                .build();
        Assertions.assertNull(etag);
    }

    @Test
    public void testBuildWithNullProtocolVersionReturnsNull()
    {
        String etag = EtagBuilder.create()
                .withGAV("org.finos", "artifact", "1.0.0")
                .withProtocolVersion(null)
                .build();
        Assertions.assertNull(etag);
    }

    @Test
    public void testBuildWithEmptyBuilder()
    {
        String etag = EtagBuilder.create().build();
        Assertions.assertNotNull(etag);
        Assertions.assertEquals("", etag);
    }
}
