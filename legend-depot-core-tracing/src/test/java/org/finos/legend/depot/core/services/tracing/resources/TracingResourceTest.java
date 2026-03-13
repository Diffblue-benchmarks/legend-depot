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

package org.finos.legend.depot.core.services.tracing.resources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

public class TracingResourceTest
{
    private static class TestableTracingResource extends TracingResource
    {
        public <T> T testHandle(String resourceAPIMetricName, String label, Supplier<T> supplier)
        {
            return handle(resourceAPIMetricName, label, supplier);
        }
    }

    @Test
    public void canHandleSuccessfulExecution()
    {
        TestableTracingResource resource = new TestableTracingResource();
        String expectedResult = "test-result";

        String result = resource.testHandle("test-metric", "test-label", () -> expectedResult);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void canHandleSuccessfulExecutionWithDifferentTypes()
    {
        TestableTracingResource resource = new TestableTracingResource();
        Integer expectedValue = 42;

        Integer result = resource.testHandle("test-metric", "test-label", () -> expectedValue);

        Assertions.assertEquals(expectedValue, result);
    }

    @Test
    public void canHandleSuccessfulExecutionWithNullResult()
    {
        TestableTracingResource resource = new TestableTracingResource();

        Object result = resource.testHandle("test-metric", "test-label", () -> null);

        Assertions.assertNull(result);
    }

    @Test
    public void canHandleRuntimeException()
    {
        TestableTracingResource resource = new TestableTracingResource();
        RuntimeException expectedException = new RuntimeException("Test exception");

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            resource.testHandle("test-metric", "test-label", () -> {
                throw expectedException;
            });
        });

        Assertions.assertTrue(thrown.getMessage().contains("Test exception"));
    }

    @Test
    public void canHandleRuntimeExceptionWithNullMessage()
    {
        TestableTracingResource resource = new TestableTracingResource();
        RuntimeException expectedException = new RuntimeException();

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            resource.testHandle("test-metric", "test-label", () -> {
                throw expectedException;
            });
        });

        Assertions.assertNotNull(thrown);
    }

    @Test
    public void canHandleIllegalArgumentException()
    {
        TestableTracingResource resource = new TestableTracingResource();

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            resource.testHandle("test-metric", "test-label", () -> {
                throw new IllegalArgumentException("Invalid argument");
            });
        });

        Assertions.assertTrue(thrown.getMessage().contains("Invalid argument"));
        Assertions.assertTrue(thrown.getCause() instanceof IllegalArgumentException);
    }

    @Test
    public void canHandleNullPointerException()
    {
        TestableTracingResource resource = new TestableTracingResource();

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            resource.testHandle("test-metric", "test-label", () -> {
                throw new NullPointerException("Null value encountered");
            });
        });

        Assertions.assertTrue(thrown.getMessage().contains("Null value encountered"));
        Assertions.assertTrue(thrown.getCause() instanceof NullPointerException);
    }

    @Test
    public void canHandleMultipleSuccessfulCalls()
    {
        TestableTracingResource resource = new TestableTracingResource();

        String result1 = resource.testHandle("metric1", "label1", () -> "result1");
        String result2 = resource.testHandle("metric2", "label2", () -> "result2");
        String result3 = resource.testHandle("metric3", "label3", () -> "result3");

        Assertions.assertEquals("result1", result1);
        Assertions.assertEquals("result2", result2);
        Assertions.assertEquals("result3", result3);
    }

    @Test
    public void canHandleWithDifferentMetricNames()
    {
        TestableTracingResource resource = new TestableTracingResource();
        String expectedResult = "success";

        String result1 = resource.testHandle("api.users.get", "Get users", () -> expectedResult);
        String result2 = resource.testHandle("api.products.list", "List products", () -> expectedResult);

        Assertions.assertEquals(expectedResult, result1);
        Assertions.assertEquals(expectedResult, result2);
    }

    @Test
    public void canHandleWithSpecialCharactersInLabels()
    {
        TestableTracingResource resource = new TestableTracingResource();
        String expectedResult = "test";

        String result = resource.testHandle("test-metric", "Test: with special chars (and parens)", () -> expectedResult);

        Assertions.assertEquals(expectedResult, result);
    }
}
