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
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtifactsExtractorTest
{
    private static class TestArtifactsExtractor implements ArtifactsExtractor<String>
    {
        private final ArtifactType type;
        private final String fileExtension;

        public TestArtifactsExtractor(ArtifactType type, String fileExtension)
        {
            this.type = type;
            this.fileExtension = fileExtension;
        }

        @Override
        public ArtifactType getType()
        {
            return type;
        }

        @Override
        public boolean matchesArtifactType(File file)
        {
            return file != null && file.getName().endsWith(fileExtension);
        }

        @Override
        public List<String> extractArtifactsForType(Stream<File> files)
        {
            return files.map(File::getName).collect(Collectors.toList());
        }
    }

    @Test
    public void canGetType()
    {
        // Arrange
        TestArtifactsExtractor extractor = new TestArtifactsExtractor(ArtifactType.ENTITIES, ".json");

        // Act
        ArtifactType result = extractor.getType();

        // Assert
        assertNotNull(result);
        assertEquals(ArtifactType.ENTITIES, result);
    }

    @Test
    public void canGetTypeForVersionedEntities()
    {
        // Arrange
        TestArtifactsExtractor extractor = new TestArtifactsExtractor(ArtifactType.VERSIONED_ENTITIES, ".xml");

        // Act
        ArtifactType result = extractor.getType();

        // Assert
        assertNotNull(result);
        assertEquals(ArtifactType.VERSIONED_ENTITIES, result);
    }

    @Test
    public void canMatchesArtifactTypeReturnsTrueForMatchingFile()
    {
        // Arrange
        TestArtifactsExtractor extractor = new TestArtifactsExtractor(ArtifactType.ENTITIES, ".json");
        File file = new File("test.json");

        // Act
        boolean result = extractor.matchesArtifactType(file);

        // Assert
        assertTrue(result);
    }

    @Test
    public void canMatchesArtifactTypeReturnsFalseForNonMatchingFile()
    {
        // Arrange
        TestArtifactsExtractor extractor = new TestArtifactsExtractor(ArtifactType.ENTITIES, ".json");
        File file = new File("test.xml");

        // Act
        boolean result = extractor.matchesArtifactType(file);

        // Assert
        assertFalse(result);
    }

    @Test
    public void canMatchesArtifactTypeReturnsFalseForNullFile()
    {
        // Arrange
        TestArtifactsExtractor extractor = new TestArtifactsExtractor(ArtifactType.ENTITIES, ".json");

        // Act
        boolean result = extractor.matchesArtifactType(null);

        // Assert
        assertFalse(result);
    }

    @Test
    public void canExtractArtifactsFiltersMatchingFiles()
    {
        // Arrange
        TestArtifactsExtractor extractor = new TestArtifactsExtractor(ArtifactType.ENTITIES, ".json");
        List<File> files = Arrays.asList(
                new File("test1.json"),
                new File("test2.xml"),
                new File("test3.json")
        );

        // Act
        List<String> result = extractor.extractArtifacts(files);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("test1.json", result.get(0));
        assertEquals("test3.json", result.get(1));
    }

    @Test
    public void canExtractArtifactsReturnsEmptyListWhenNoMatchingFiles()
    {
        // Arrange
        TestArtifactsExtractor extractor = new TestArtifactsExtractor(ArtifactType.ENTITIES, ".json");
        List<File> files = Arrays.asList(
                new File("test1.xml"),
                new File("test2.txt")
        );

        // Act
        List<String> result = extractor.extractArtifacts(files);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void canExtractArtifactsHandlesEmptyList()
    {
        // Arrange
        TestArtifactsExtractor extractor = new TestArtifactsExtractor(ArtifactType.ENTITIES, ".json");
        List<File> files = Collections.emptyList();

        // Act
        List<String> result = extractor.extractArtifacts(files);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void canExtractArtifactsForType()
    {
        // Arrange
        TestArtifactsExtractor extractor = new TestArtifactsExtractor(ArtifactType.FILE_GENERATIONS, ".gen");
        Stream<File> files = Arrays.asList(
                new File("file1.gen"),
                new File("file2.gen")
        ).stream();

        // Act
        List<String> result = extractor.extractArtifactsForType(files);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("file1.gen", result.get(0));
        assertEquals("file2.gen", result.get(1));
    }

    @Test
    public void canExtractArtifactsForTypeWithEmptyStream()
    {
        // Arrange
        TestArtifactsExtractor extractor = new TestArtifactsExtractor(ArtifactType.FILE_GENERATIONS, ".gen");
        Stream<File> files = Stream.empty();

        // Act
        List<String> result = extractor.extractArtifactsForType(files);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
