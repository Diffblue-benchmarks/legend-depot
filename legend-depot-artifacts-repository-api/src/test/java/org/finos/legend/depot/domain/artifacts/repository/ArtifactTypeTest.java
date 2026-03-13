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

package org.finos.legend.depot.domain.artifacts.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArtifactTypeTest
{
    @Test
    public void testEntitiesModuleName()
    {
        ArtifactType type = ArtifactType.ENTITIES;
        Assertions.assertEquals("entities", type.getModuleName());
    }

    @Test
    public void testVersionedEntitiesModuleName()
    {
        ArtifactType type = ArtifactType.VERSIONED_ENTITIES;
        Assertions.assertEquals("versioned-entities", type.getModuleName());
    }

    @Test
    public void testFileGenerationsModuleName()
    {
        ArtifactType type = ArtifactType.FILE_GENERATIONS;
        Assertions.assertEquals("file-generation", type.getModuleName());
    }

    @Test
    public void testEnumValues()
    {
        ArtifactType[] values = ArtifactType.values();
        Assertions.assertEquals(3, values.length);
        Assertions.assertEquals(ArtifactType.ENTITIES, values[0]);
        Assertions.assertEquals(ArtifactType.VERSIONED_ENTITIES, values[1]);
        Assertions.assertEquals(ArtifactType.FILE_GENERATIONS, values[2]);
    }
}
