// Copyright 2021 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.depot.services.api.artifacts.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArtifactRepositoryExceptionTest
{
    @Test
    public void testConstructorWithThrowable()
    {
        Throwable cause = new RuntimeException("root cause");
        ArtifactRepositoryException exception = new ArtifactRepositoryException(cause);
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(cause, exception.getCause());
    }

    @Test
    public void testConstructorWithMessage()
    {
        String message = "test error message";
        ArtifactRepositoryException exception = new ArtifactRepositoryException(message);
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(message, exception.getMessage());
    }
}
