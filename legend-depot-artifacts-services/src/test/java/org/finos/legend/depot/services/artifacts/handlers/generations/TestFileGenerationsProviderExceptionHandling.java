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

package org.finos.legend.depot.services.artifacts.handlers.generations;

import org.finos.legend.depot.services.api.artifacts.handlers.ArtifactLoadingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.stream.Stream;

public class TestFileGenerationsProviderExceptionHandling
{
    private final FileGenerationsProvider provider = new FileGenerationsProvider();

    @Test
    public void testExtractArtifactsForTypeThrowsArtifactLoadingExceptionForInvalidFile(@TempDir Path tempDir) throws Exception
    {
        File invalidFile = tempDir.resolve("invalid-file-generations.jar").toFile();
        try (FileOutputStream fos = new FileOutputStream(invalidFile))
        {
            fos.write("not a valid jar".getBytes());
        }

        Assertions.assertThrows(ArtifactLoadingException.class, () ->
                provider.extractArtifactsForType(Stream.of(invalidFile)));
    }

    @Test
    public void testExtractArtifactsForTypeWithValidAndInvalidFiles(@TempDir Path tempDir) throws Exception
    {
        File invalidFile = tempDir.resolve("bad-file-generations.jar").toFile();
        try (FileOutputStream fos = new FileOutputStream(invalidFile))
        {
            fos.write("invalid content".getBytes());
        }

        Assertions.assertThrows(ArtifactLoadingException.class, () ->
                provider.extractArtifacts(Collections.singletonList(invalidFile)));
    }
}
