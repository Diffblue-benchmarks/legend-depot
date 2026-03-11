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

package org.finos.legend.depot.services.api.generations;

import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.finos.legend.depot.store.model.generations.StoredFileGeneration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TestFileGenerationsServiceDefaultMethods
{
    private final FileGenerationsService service = new FileGenerationsService()
    {
        @Override
        public List<DepotGeneration> getFileGenerations(String groupId, String artifactId, String versionId)
        {
            return Collections.emptyList();
        }

        @Override
        public List<DepotGeneration> getFileGenerationsByElementPath(String groupId, String artifactId, String versionId, String elementPath)
        {
            return Collections.emptyList();
        }

        @Override
        public Optional<DepotGeneration> getFileGenerationsByFilePath(String groupId, String artifactId, String versionsId, String filePath)
        {
            if ("/test/path.json".equals(filePath))
            {
                return Optional.of(new DepotGeneration("/test/path.json", "test-content"));
            }
            return Optional.empty();
        }

        @Override
        public List<StoredFileGeneration> findByTypeAndElementPath(String groupId, String artifactId, String versionId, String type, String elementPath)
        {
            if (elementPath == null && "avro".equals(type))
            {
                return Arrays.asList(
                        new StoredFileGeneration(groupId, artifactId, versionId, "elementPath1", "avro", new DepotGeneration("/gen/file1.avro", "content1")),
                        new StoredFileGeneration(groupId, artifactId, versionId, "elementPath2", "avro", new DepotGeneration("/gen/file2.avro", "content2"))
                );
            }
            return Collections.emptyList();
        }
    };

    @Test
    public void canFindByTypeDelegatesToFindByTypeAndElementPath()
    {
        List<StoredFileGeneration> results = service.findByType("org.test", "artifact", "1.0.0", "avro");
        Assertions.assertNotNull(results);
        Assertions.assertEquals(2, results.size());
    }

    @Test
    public void canGetFileGenerationContentByFilePath()
    {
        Optional<String> content = service.getFileGenerationContentByFilePath("org.test", "artifact", "1.0.0", "/test/path.json");
        Assertions.assertTrue(content.isPresent());
        Assertions.assertEquals("test-content", content.get());
    }

    @Test
    public void canGetFileGenerationContentByFilePathReturnsEmptyForUnknownPath()
    {
        Optional<String> content = service.getFileGenerationContentByFilePath("org.test", "artifact", "1.0.0", "/unknown/path.json");
        Assertions.assertFalse(content.isPresent());
    }
}
