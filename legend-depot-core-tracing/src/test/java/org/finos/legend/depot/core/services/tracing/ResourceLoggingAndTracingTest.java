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

package org.finos.legend.depot.core.services.tracing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourceLoggingAndTracingTest
{
    @Test
    public void testConstantsAreNotNull()
    {
        Assertions.assertNotNull(ResourceLoggingAndTracing.GET_ALL_PROJECTS);
        Assertions.assertNotNull(ResourceLoggingAndTracing.GET_VERSION_ENTITIES);
        Assertions.assertNotNull(ResourceLoggingAndTracing.HANDLE_EVENTS_IN_QUEUE);
        Assertions.assertNotNull(ResourceLoggingAndTracing.DELETE_PROJECT);
        Assertions.assertNotNull(ResourceLoggingAndTracing.UPDATE_ALL_VERSIONS);
        Assertions.assertNotNull(ResourceLoggingAndTracing.GET_PROJECT_DEPENDENCIES);
    }

    @Test
    public void testConstantsAreNotEmpty()
    {
        Assertions.assertFalse(ResourceLoggingAndTracing.GET_ALL_PROJECTS.isEmpty());
        Assertions.assertFalse(ResourceLoggingAndTracing.GET_VERSION_ENTITIES.isEmpty());
        Assertions.assertFalse(ResourceLoggingAndTracing.HANDLE_EVENTS_IN_QUEUE.isEmpty());
    }

    @Test
    public void testConstantsHaveExpectedValues()
    {
        Assertions.assertEquals("get all projects", ResourceLoggingAndTracing.GET_ALL_PROJECTS);
        Assertions.assertEquals("handle queue events", ResourceLoggingAndTracing.HANDLE_EVENTS_IN_QUEUE);
        Assertions.assertEquals("get version entities", ResourceLoggingAndTracing.GET_VERSION_ENTITIES);
        Assertions.assertEquals("delete project", ResourceLoggingAndTracing.DELETE_PROJECT);
    }
}
