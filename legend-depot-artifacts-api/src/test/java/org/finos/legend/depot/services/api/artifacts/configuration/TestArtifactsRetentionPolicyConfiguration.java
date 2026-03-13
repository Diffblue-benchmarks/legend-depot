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

package org.finos.legend.depot.services.api.artifacts.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArtifactsRetentionPolicyConfiguration
{
    @Test
    public void testConstructorWithAllParameters()
    {
        Integer maxSnapshots = 10;
        Integer ttlVersions = 180;
        Integer ttlSnapshots = 60;

        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(
                maxSnapshots,
                ttlVersions,
                ttlSnapshots
        );

        Assertions.assertEquals(10, config.getMaximumSnapshotsAllowed());
        Assertions.assertEquals(180, config.getTtlForVersions());
        Assertions.assertEquals(60, config.getTtlForSnapshots());
    }

    @Test
    public void testConstructorWithNullParameters()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(
                null,
                null,
                null
        );

        Assertions.assertEquals(5, config.getMaximumSnapshotsAllowed());
        Assertions.assertEquals(365, config.getTtlForVersions());
        Assertions.assertEquals(30, config.getTtlForSnapshots());
    }

    @Test
    public void testConstructorWithMixedNullParameters()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(
                15,
                null,
                45
        );

        Assertions.assertEquals(15, config.getMaximumSnapshotsAllowed());
        Assertions.assertEquals(365, config.getTtlForVersions());
        Assertions.assertEquals(45, config.getTtlForSnapshots());
    }

    @Test
    public void testGetMaximumSnapshotsAllowed()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(
                20,
                null,
                null
        );

        Assertions.assertEquals(20, config.getMaximumSnapshotsAllowed());
    }

    @Test
    public void testGetTtlForVersions()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(
                null,
                200,
                null
        );

        Assertions.assertEquals(200, config.getTtlForVersions());
    }

    @Test
    public void testGetTtlForSnapshots()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(
                null,
                null,
                90
        );

        Assertions.assertEquals(90, config.getTtlForSnapshots());
    }
}
