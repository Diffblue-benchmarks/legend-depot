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

package org.finos.legend.depot.store.mongo.guice;

import com.google.inject.Binding;
import com.google.inject.PrivateModule;
import com.google.inject.spi.Element;
import com.google.inject.spi.Elements;
import com.google.inject.spi.PrivateElements;
import org.finos.legend.depot.store.api.metrics.query.QueryMetrics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class TestQueryMetricsMongoStoreModule
{
    @Test
    public void canCreateModule()
    {
        QueryMetricsMongoStoreModule module = new QueryMetricsMongoStoreModule();
        Assertions.assertNotNull(module);
        Assertions.assertTrue(module instanceof PrivateModule);
    }

    @Test
    public void configureBindsQueryMetricsAndExposesIt()
    {
        QueryMetricsMongoStoreModule module = new QueryMetricsMongoStoreModule();
        List<Element> elements = Elements.getElements(module);

        List<PrivateElements> privateElements = elements.stream()
                .filter(e -> e instanceof PrivateElements)
                .map(e -> (PrivateElements) e)
                .collect(Collectors.toList());

        Assertions.assertFalse(privateElements.isEmpty(), "Module should contain private elements");

        PrivateElements pe = privateElements.get(0);

        List<String> exposedKeys = pe.getExposedKeys().stream()
                .map(k -> k.getTypeLiteral().getRawType().getName())
                .collect(Collectors.toList());
        Assertions.assertTrue(exposedKeys.contains(QueryMetrics.class.getName()),
                "QueryMetrics should be exposed");

        boolean hasQueryMetricsBinding = pe.getElements().stream()
                .filter(e -> e instanceof Binding)
                .map(e -> (Binding<?>) e)
                .anyMatch(b -> b.getKey().getTypeLiteral().getRawType().equals(QueryMetrics.class));
        Assertions.assertTrue(hasQueryMetricsBinding, "QueryMetrics should be bound");
    }
}
