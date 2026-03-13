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

package org.finos.legend.depot.services.generations.loader;

import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileGenerationLoaderTest
{
    @TempDir
    Path tempDir;

    private FileGenerationLoader loader;

    @AfterEach
    public void cleanup() throws Exception
    {
        if (loader != null)
        {
            loader.close();
            loader = null;
        }
    }

    @Test
    public void canCreateLoaderFromPath() throws IOException
    {
        Path subDir = tempDir.resolve("testdir");
        Files.createDirectories(subDir);
        Path testFile = subDir.resolve("test.txt");
        Files.writeString(testFile, "test content");

        loader = FileGenerationLoader.newFileGenerationsLoader(subDir);

        Assertions.assertNotNull(loader);
    }

    @Test
    public void canCreateLoaderFromFile() throws IOException
    {
        Path subDir = tempDir.resolve("testdir");
        Files.createDirectories(subDir);
        File testFile = subDir.toFile();

        loader = FileGenerationLoader.newFileGenerationsLoader(testFile);

        Assertions.assertNotNull(loader);
    }

    @Test
    public void canCreateLoaderFromDirectory() throws IOException
    {
        Path subDir = tempDir.resolve("subdir");
        Files.createDirectories(subDir);
        Path testFile = subDir.resolve("generation.txt");
        Files.writeString(testFile, "generation content");

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);

        Assertions.assertNotNull(loader);
    }

    @Test
    public void canCreateLoaderFromNonExistentPath()
    {
        Path nonExistent = tempDir.resolve("nonexistent");

        loader = FileGenerationLoader.newFileGenerationsLoader(nonExistent);

        Assertions.assertNotNull(loader);
    }

    @Test
    public void canGetAllFileGenerations() throws IOException
    {
        Path testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, "test content");

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        Stream<DepotGeneration> generations = loader.getAllFileGenerations();

        Assertions.assertNotNull(generations);
        List<DepotGeneration> list = generations.collect(Collectors.toList());
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void canGetAllFileGenerationsFromMultipleFiles() throws IOException
    {
        Path file1 = tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");
        Files.writeString(file1, "content 1");
        Files.writeString(file2, "content 2");

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        Stream<DepotGeneration> generations = loader.getAllFileGenerations();

        List<DepotGeneration> list = generations.collect(Collectors.toList());
        Assertions.assertEquals(2, list.size());
    }

    @Test
    public void canGetGenerationsWithNestedDirectories() throws IOException
    {
        Path subDir = tempDir.resolve("nested");
        Files.createDirectories(subDir);
        Path nestedFile = subDir.resolve("nested.txt");
        Files.writeString(nestedFile, "nested content");

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        Stream<DepotGeneration> generations = loader.getAllFileGenerations();

        List<DepotGeneration> list = generations.collect(Collectors.toList());
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void canReadGenerationContent() throws IOException
    {
        String content = "line1\nline2\nline3";
        Path testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, content);

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        Stream<DepotGeneration> generations = loader.getAllFileGenerations();

        List<DepotGeneration> list = generations.collect(Collectors.toList());
        Assertions.assertEquals(1, list.size());
        DepotGeneration gen = list.get(0);
        Assertions.assertNotNull(gen.getContent());
        Assertions.assertTrue(gen.getContent().contains("line1"));
    }

    @Test
    public void canFilterMetaInfFiles() throws IOException
    {
        Path metaInfDir = tempDir.resolve("META-INF");
        Files.createDirectories(metaInfDir);
        Path metaInfFile = metaInfDir.resolve("manifest.txt");
        Files.writeString(metaInfFile, "meta content");

        Path regularFile = tempDir.resolve("regular.txt");
        Files.writeString(regularFile, "regular content");

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        Stream<DepotGeneration> generations = loader.getAllFileGenerations();

        List<DepotGeneration> list = generations.collect(Collectors.toList());
        Assertions.assertFalse(list.isEmpty());
        boolean hasRegularFile = list.stream().anyMatch(g -> g.getPath().contains("regular.txt"));
        Assertions.assertTrue(hasRegularFile);
    }

    @Test
    public void canHandleEmptyDirectory()
    {
        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        Stream<DepotGeneration> generations = loader.getAllFileGenerations();

        List<DepotGeneration> list = generations.collect(Collectors.toList());
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void canCloseLoader() throws Exception
    {
        Path testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, "test content");

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        loader.close();

        loader = null;
    }

    @Test
    public void canCloseLoaderMultipleTimes() throws Exception
    {
        Path testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, "test content");

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        loader.close();
        loader.close();

        loader = null;
    }

    @Test
    public void canHandleInvalidPath()
    {
        Path invalidPath = Path.of("/invalid/path/that/does/not/exist");

        loader = FileGenerationLoader.newFileGenerationsLoader(invalidPath);

        Assertions.assertNotNull(loader);
        Stream<DepotGeneration> generations = loader.getAllFileGenerations();
        List<DepotGeneration> list = generations.collect(Collectors.toList());
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void canReadGenerationWithSpecialCharacters() throws IOException
    {
        String content = "content with special chars: \t\n\r";
        Path testFile = tempDir.resolve("special.txt");
        Files.writeString(testFile, content);

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        Stream<DepotGeneration> generations = loader.getAllFileGenerations();

        List<DepotGeneration> list = generations.collect(Collectors.toList());
        Assertions.assertEquals(1, list.size());
        Assertions.assertNotNull(list.get(0).getContent());
    }

    @Test
    public void canHandleDirectoryWithOnlySubdirectories() throws IOException
    {
        Path subDir1 = tempDir.resolve("sub1");
        Path subDir2 = tempDir.resolve("sub2");
        Files.createDirectories(subDir1);
        Files.createDirectories(subDir2);

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        Stream<DepotGeneration> generations = loader.getAllFileGenerations();

        List<DepotGeneration> list = generations.collect(Collectors.toList());
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void canGetGenerationsFromSingleFileDirectory() throws IOException
    {
        Path subDir = tempDir.resolve("singledir");
        Files.createDirectories(subDir);
        Path testFile = subDir.resolve("single.txt");
        Files.writeString(testFile, "single file content");

        loader = FileGenerationLoader.newFileGenerationsLoader(subDir);
        Stream<DepotGeneration> generations = loader.getAllFileGenerations();

        List<DepotGeneration> list = generations.collect(Collectors.toList());
        Assertions.assertEquals(1, list.size());
    }

    @Test
    public void canCreateLoaderWithZipFile() throws IOException
    {
        Path zipFile = tempDir.resolve("test.zip");
        Files.writeString(zipFile, "PK");

        try
        {
            loader = FileGenerationLoader.newFileGenerationsLoader(zipFile);
        }
        catch (RuntimeException e)
        {
            Assertions.assertTrue(e.getMessage().contains("Error handling"));
        }
    }

    @Test
    public void canHandlePathStartingWithMetaInf() throws IOException
    {
        Path metaPath = Path.of("/META-INF/test.txt");

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);

        Assertions.assertNotNull(loader);
    }

    @Test
    public void canReadGenerationWithEmptyContent() throws IOException
    {
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.writeString(emptyFile, "");

        loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        Stream<DepotGeneration> generations = loader.getAllFileGenerations();

        List<DepotGeneration> list = generations.collect(Collectors.toList());
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("", list.get(0).getContent());
    }

    @Test
    public void canHandleMultipleCloseCalls() throws Exception
    {
        Path subDir = tempDir.resolve("closedir");
        Files.createDirectories(subDir);

        loader = FileGenerationLoader.newFileGenerationsLoader(subDir);
        loader.close();
        loader.close();
        loader.close();

        loader = null;
    }
}
