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

public class ArtifactsRetentionPolicyConfigurationTest
{
    @Test
    public void testDefaultValues()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(null, null, null);
        Assertions.assertEquals(5, config.getMaximumSnapshotsAllowed());
        Assertions.assertEquals(365, config.getTtlForVersions());
        Assertions.assertEquals(30, config.getTtlForSnapshots());
    }

    @Test
    public void testCustomValues()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(10, 180, 7);
        Assertions.assertEquals(10, config.getMaximumSnapshotsAllowed());
        Assertions.assertEquals(180, config.getTtlForVersions());
        Assertions.assertEquals(7, config.getTtlForSnapshots());
    }

    @Test
    public void testPartialCustomValues()
    {
        ArtifactsRetentionPolicyConfiguration config = new ArtifactsRetentionPolicyConfiguration(3, null, null);
        Assertions.assertEquals(3, config.getMaximumSnapshotsAllowed());
        Assertions.assertEquals(365, config.getTtlForVersions());
        Assertions.assertEquals(30, config.getTtlForSnapshots());
    }
}
