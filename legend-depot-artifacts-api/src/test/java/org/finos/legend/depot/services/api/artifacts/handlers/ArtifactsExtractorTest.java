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

public class ArtifactsExtractorTest
{
    private static class TestExtractor implements ArtifactsExtractor<String>
    {
        private final ArtifactType type;
        private final String extension;

        TestExtractor(ArtifactType type, String extension)
        {
            this.type = type;
            this.extension = extension;
        }

        @Override
        public ArtifactType getType()
        {
            return type;
        }

        @Override
        public boolean matchesArtifactType(File file)
        {
            return file.getName().endsWith(extension);
        }

        @Override
        public List<String> extractArtifactsForType(Stream<File> files)
        {
            return files.map(File::getName).collect(Collectors.toList());
        }
    }

    @Test
    public void testGetType()
    {
        TestExtractor extractor = new TestExtractor(ArtifactType.ENTITIES, ".json");
        Assertions.assertEquals(ArtifactType.ENTITIES, extractor.getType());
    }

    @Test
    public void testMatchesArtifactTypeTrue()
    {
        TestExtractor extractor = new TestExtractor(ArtifactType.ENTITIES, ".json");
        File file = new File("model.json");
        Assertions.assertTrue(extractor.matchesArtifactType(file));
    }

    @Test
    public void testMatchesArtifactTypeFalse()
    {
        TestExtractor extractor = new TestExtractor(ArtifactType.ENTITIES, ".json");
        File file = new File("model.xml");
        Assertions.assertFalse(extractor.matchesArtifactType(file));
    }

    @Test
    public void testExtractArtifactsFiltersAndDelegates()
    {
        TestExtractor extractor = new TestExtractor(ArtifactType.ENTITIES, ".json");
        File jsonFile = new File("a.json");
        File xmlFile = new File("b.xml");
        List<String> result = extractor.extractArtifacts(Arrays.asList(jsonFile, xmlFile));
        Assertions.assertEquals(Collections.singletonList("a.json"), result);
    }

    @Test
    public void testExtractArtifactsEmptyList()
    {
        TestExtractor extractor = new TestExtractor(ArtifactType.ENTITIES, ".json");
        List<String> result = extractor.extractArtifacts(Collections.emptyList());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testExtractArtifactsForType()
    {
        TestExtractor extractor = new TestExtractor(ArtifactType.FILE_GENERATIONS, ".json");
        File f1 = new File("x.json");
        File f2 = new File("y.json");
        List<String> result = extractor.extractArtifactsForType(Stream.of(f1, f2));
        Assertions.assertEquals(Arrays.asList("x.json", "y.json"), result);
    }
}
