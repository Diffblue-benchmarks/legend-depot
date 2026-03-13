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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArtifactRepositoryExceptionTest
{
    @Test
    public void canCreateExceptionWithMessage()
    {
        String message = "Repository error occurred";
        ArtifactRepositoryException exception = new ArtifactRepositoryException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    public void canCreateExceptionWithNullMessage()
    {
        ArtifactRepositoryException exception = new ArtifactRepositoryException((String) null);
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    public void canCreateExceptionWithEmptyMessage()
    {
        String message = "";
        ArtifactRepositoryException exception = new ArtifactRepositoryException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    public void canCreateExceptionWithCause()
    {
        Throwable cause = new RuntimeException("Root cause");
        ArtifactRepositoryException exception = new ArtifactRepositoryException(cause);
        Assertions.assertEquals(cause, exception.getCause());
    }

    @Test
    public void canCreateExceptionWithNullCause()
    {
        ArtifactRepositoryException exception = new ArtifactRepositoryException((Throwable) null);
        Assertions.assertNull(exception.getCause());
    }
}
