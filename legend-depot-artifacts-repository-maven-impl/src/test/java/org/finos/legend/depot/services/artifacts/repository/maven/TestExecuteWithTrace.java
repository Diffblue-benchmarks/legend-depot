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

package org.finos.legend.depot.services.artifacts.repository.maven;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.function.Supplier;

public class TestExecuteWithTrace
{
    private final MavenArtifactRepository repository = new MavenArtifactRepository(null);

    @Test
    public void executeWithTraceReturnsSupplierResult() throws Exception
    {
        Method method = MavenArtifactRepository.class.getDeclaredMethod(
                "executeWithTrace", String.class, String.class, String.class, String.class, Supplier.class);
        method.setAccessible(true);

        Supplier<Object> supplier = () -> "testResult";
        Object result = method.invoke(repository, "testLabel", "com.example", "my-artifact", "1.0.0", supplier);

        Assertions.assertEquals("testResult", result);
    }

    @Test
    public void executeWithTracePassesThroughSupplierException() throws Exception
    {
        Method method = MavenArtifactRepository.class.getDeclaredMethod(
                "executeWithTrace", String.class, String.class, String.class, String.class, Supplier.class);
        method.setAccessible(true);

        Supplier<Object> supplier = () ->
        {
            throw new RuntimeException("test error");
        };

        Exception thrown = Assertions.assertThrows(Exception.class,
                () -> method.invoke(repository, "errorLabel", "com.example", "my-artifact", "1.0.0", supplier));
        Assertions.assertTrue(thrown.getCause().getMessage().contains("test error"));
    }
}
