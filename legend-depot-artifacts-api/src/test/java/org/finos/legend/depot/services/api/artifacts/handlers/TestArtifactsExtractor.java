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

import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TestArtifactsExtractor
{
    private static class TestExtractor implements ArtifactsExtractor<String>
    {
        @Override
        public ArtifactType getType()
        {
            return ArtifactType.ENTITIES;
        }

        @Override
        public boolean matchesArtifactType(File file)
        {
            return file.getName().endsWith(".json");
        }

        @Override
        public List<String> extractArtifactsForType(Stream<File> files)
        {
            return files.map(File::getName).collect(Collectors.toList());
        }
    }

    private final TestExtractor extractor = new TestExtractor();

    @Test
    void canExtractArtifactsFilteringByType()
    {
        List<File> files = Arrays.asList(
                new File("entity1.json"),
                new File("readme.txt"),
                new File("entity2.json")
        );

        List<String> result = extractor.extractArtifacts(files);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("entity1.json", result.get(0));
        Assertions.assertEquals("entity2.json", result.get(1));
    }

    @Test
    void canExtractArtifactsWithEmptyList()
    {
        List<String> result = extractor.extractArtifacts(Collections.emptyList());

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void canExtractArtifactsWhenNoFilesMatch()
    {
        List<File> files = Arrays.asList(
                new File("readme.txt"),
                new File("config.xml")
        );

        List<String> result = extractor.extractArtifacts(files);

        Assertions.assertTrue(result.isEmpty());
    }
}
