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

package org.finos.legend.depot.services.api.artifacts.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class TestArtifactRepositoryException
{
    @Test
    void canCreateExceptionWithThrowableCause()
    {
        RuntimeException cause = new RuntimeException("root cause");
        ArtifactRepositoryException exception = new ArtifactRepositoryException(cause);

        assertNotNull(exception);
        assertSame(cause, exception.getCause());
        assertEquals("java.lang.RuntimeException: root cause", exception.getMessage());
    }

    @Test
    void canCreateExceptionWithStringMessage()
    {
        String message = "something went wrong";
        ArtifactRepositoryException exception = new ArtifactRepositoryException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }
}
