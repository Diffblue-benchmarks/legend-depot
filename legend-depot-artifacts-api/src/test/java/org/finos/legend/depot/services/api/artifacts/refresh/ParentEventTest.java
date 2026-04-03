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

package org.finos.legend.depot.services.api.artifacts.refresh;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParentEventTest
{
    @Test
    public void testBuildReturnsParentEventIdWhenNotNull()
    {
        String result = ParentEvent.build("com.example", "my-artifact", "1.0.0", "existingEventId");
        Assertions.assertEquals("existingEventId", result);
    }

    @Test
    public void testBuildReturnsConcatenatedStringWhenParentEventIdIsNull()
    {
        String result = ParentEvent.build("com.example", "my-artifact", "1.0.0", null);
        Assertions.assertEquals("com.example_my-artifact_1.0.0", result);
    }
}
