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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class TestDirectoryEntityFileSearch
{
    @Test
    public void canCloseDirectoryBasedLoader() throws Exception
    {
        Path tempDir = Files.createTempDirectory("gen-test");
        FileGenerationLoader loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        loader.close();
    }

    @Test
    public void canLoadGenerationsFromDirectory() throws IOException
    {
        Path tempDir = Files.createTempDirectory("gen-test");
        Path subDir = tempDir.resolve("myGen");
        Files.createDirectories(subDir);
        Files.write(subDir.resolve("output.txt"), "hello world".getBytes());

        FileGenerationLoader loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        List<DepotGeneration> generations = loader.getAllFileGenerations().collect(Collectors.toList());
        Assertions.assertNotNull(generations);
        Assertions.assertFalse(generations.isEmpty());
    }

    @Test
    public void canLoadEmptyDirectory() throws Exception
    {
        Path tempDir = Files.createTempDirectory("gen-test-empty");

        FileGenerationLoader loader = FileGenerationLoader.newFileGenerationsLoader(tempDir);
        List<DepotGeneration> generations = loader.getAllFileGenerations().collect(Collectors.toList());
        Assertions.assertNotNull(generations);
        Assertions.assertTrue(generations.isEmpty());
        loader.close();
    }
}
