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

package org.finos.legend.depot.services.api.artifacts.handlers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestArtifactLoadingException
{
    @Test
    void canCreateExceptionWithMessage()
    {
        String message = "test error message";
        ArtifactLoadingException exception = new ArtifactLoadingException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void canBeThrown()
    {
        String message = "loading failed";
        ArtifactLoadingException thrown = assertThrows(ArtifactLoadingException.class, () ->
        {
            throw new ArtifactLoadingException(message);
        });

        assertEquals(message, thrown.getMessage());
    }

    @Test
    void isRuntimeException()
    {
        ArtifactLoadingException exception = new ArtifactLoadingException("error");

        assertTrue(exception instanceof RuntimeException);
    }
}
