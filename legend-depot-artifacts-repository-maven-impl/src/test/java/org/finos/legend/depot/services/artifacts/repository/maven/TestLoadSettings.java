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
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class TestLoadSettings
{
    @TempDir
    Path tempDir;

    private File createSettingsFile(String content) throws IOException
    {
        File settingsFile = tempDir.resolve("settings.xml").toFile();
        try (FileWriter writer = new FileWriter(settingsFile))
        {
            writer.write(content);
        }
        return settingsFile;
    }

    @Test
    public void constructorSucceedsWithValidSettingsFile() throws IOException
    {
        File settingsFile = createSettingsFile(
                "<settings>" +
                "  <localRepository>" + tempDir.resolve("local-repo").toString() + "</localRepository>" +
                "</settings>"
        );

        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration(settingsFile.getAbsolutePath());
        MavenArtifactRepository repository = new MavenArtifactRepository(config);
        Assertions.assertNotNull(repository);
    }

    @Test
    public void constructorThrowsWhenLocalRepositoryIsNull() throws IOException
    {
        File settingsFile = createSettingsFile(
                "<settings></settings>"
        );

        MavenArtifactRepositoryConfiguration config = new MavenArtifactRepositoryConfiguration(settingsFile.getAbsolutePath());
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> new MavenArtifactRepository(config));
        Assertions.assertTrue(exception.getMessage().contains("valid local repository"));
    }
}
