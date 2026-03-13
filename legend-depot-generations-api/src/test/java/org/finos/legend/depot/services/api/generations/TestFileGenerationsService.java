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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestFileGenerationsService
{

    @Test
    public void canCallFindByType()
    {
        TestFileGenerationsServiceImpl service = new TestFileGenerationsServiceImpl();
        List<StoredFileGeneration> result = service.findByType("group", "artifact", "1.0.0", "testType");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("testType", service.lastCalledType);
        Assertions.assertNull(service.lastCalledElementPath);
    }

    @Test
    public void canCallGetFileGenerationContentByFilePath()
    {
        TestFileGenerationsServiceImpl service = new TestFileGenerationsServiceImpl();
        Optional<String> result = service.getFileGenerationContentByFilePath("group", "artifact", "1.0.0", "test/path");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("test content", result.get());
    }

    @Test
    public void canHandleEmptyOptionalInGetFileGenerationContentByFilePath()
    {
        TestFileGenerationsServiceImpl service = new TestFileGenerationsServiceImpl();
        service.shouldReturnEmpty = true;
        Optional<String> result = service.getFileGenerationContentByFilePath("group", "artifact", "1.0.0", "test/path");

        Assertions.assertFalse(result.isPresent());
    }

    private static class TestFileGenerationsServiceImpl implements FileGenerationsService
    {
        String lastCalledType;
        String lastCalledElementPath;
        boolean shouldReturnEmpty = false;

        @Override
        public List<DepotGeneration> getFileGenerations(String groupId, String artifactId, String versionId)
        {
            return new ArrayList<>();
        }

        @Override
        public List<DepotGeneration> getFileGenerationsByElementPath(String groupId, String artifactId, String versionId, String elementPath)
        {
            return new ArrayList<>();
        }

        @Override
        public Optional<DepotGeneration> getFileGenerationsByFilePath(String groupId, String artifactId, String versionsId, String filePath)
        {
            if (shouldReturnEmpty)
            {
                return Optional.empty();
            }
            return Optional.of(new DepotGeneration("test/path", "test content"));
        }

        @Override
        public List<StoredFileGeneration> findByTypeAndElementPath(String groupId, String artifactId, String versionId, String type, String elementPath)
        {
            this.lastCalledType = type;
            this.lastCalledElementPath = elementPath;
            StoredFileGeneration generation = new StoredFileGeneration(groupId, artifactId, versionId, "test/path", type, new DepotGeneration("test/path", "content"));
            return Arrays.asList(generation);
        }
    }
}
