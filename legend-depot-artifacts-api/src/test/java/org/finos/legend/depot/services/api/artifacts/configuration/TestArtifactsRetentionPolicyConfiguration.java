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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestArtifactsRetentionPolicyConfiguration
{
    @Test
    void canCreateWithAllNullsAndGetDefaults()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(null, null, null);

        assertEquals(5, config.getMaximumSnapshotsAllowed());
        assertEquals(365, config.getTtlForVersions());
        assertEquals(30, config.getTtlForSnapshots());
    }

    @Test
    void canCreateWithCustomValues()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(10, 180, 60);

        assertEquals(10, config.getMaximumSnapshotsAllowed());
        assertEquals(180, config.getTtlForVersions());
        assertEquals(60, config.getTtlForSnapshots());
    }

    @Test
    void canCreateWithPartialNulls()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(null, 200, null);

        assertEquals(5, config.getMaximumSnapshotsAllowed());
        assertEquals(200, config.getTtlForVersions());
        assertEquals(30, config.getTtlForSnapshots());
    }
}
