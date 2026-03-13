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

package org.finos.legend.depot.store.api.generations;

import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.finos.legend.depot.store.model.generations.StoredFileGeneration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UpdateFileGenerationsTest
{
    private static class TestUpdateFileGenerations implements UpdateFileGenerations
    {
        private List<StoredFileGeneration> stored = new ArrayList<>();

        @Override
        public List<StoredFileGeneration> createOrUpdate(List<StoredFileGeneration> generation)
        {
            stored.addAll(generation);
            return generation;
        }

        @Override
        public long delete(String groupId, String artifactId, String versionId)
        {
            long count = stored.stream()
                    .filter(g -> g.getGroupId().equals(groupId) &&
                                 g.getArtifactId().equals(artifactId) &&
                                 g.getVersionId().equals(versionId))
                    .count();
            stored.removeIf(g -> g.getGroupId().equals(groupId) &&
                                 g.getArtifactId().equals(artifactId) &&
                                 g.getVersionId().equals(versionId));
            return count;
        }

        @Override
        public List<StoredFileGeneration> getAll()
        {
            return stored;
        }

        @Override
        public List<StoredFileGeneration> find(String groupId, String artifactId, String versionId)
        {
            return Collections.emptyList();
        }

        @Override
        public List<StoredFileGeneration> findByType(String groupId, String artifactId, String versionId, String type)
        {
            return Collections.emptyList();
        }

        @Override
        public List<StoredFileGeneration> findByElementPath(String groupId, String artifactId, String versionId, String elementPath)
        {
            return Collections.emptyList();
        }

        @Override
        public List<StoredFileGeneration> findByTypeAndElementPath(String groupId, String artifactId, String versionId, String type, String elementPath)
        {
            return Collections.emptyList();
        }

        @Override
        public Optional<StoredFileGeneration> findByFilePath(String groupId, String artifactId, String versionId, String filePath)
        {
            return Optional.empty();
        }
    }

    @Test
    public void testCreateOrUpdate()
    {
        TestUpdateFileGenerations updateFileGenerations = new TestUpdateFileGenerations();

        List<StoredFileGeneration> input = new ArrayList<>();
        StoredFileGeneration generation = new StoredFileGeneration("test.group", "test-artifact", "1.0.0",
                "/path/to/file", "avro", new DepotGeneration("/path/to/file", "test content"));
        input.add(generation);

        List<StoredFileGeneration> result = updateFileGenerations.createOrUpdate(input);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(generation, result.get(0));
    }

    @Test
    public void testCreateOrUpdateWithEmptyList()
    {
        TestUpdateFileGenerations updateFileGenerations = new TestUpdateFileGenerations();

        List<StoredFileGeneration> input = Collections.emptyList();

        List<StoredFileGeneration> result = updateFileGenerations.createOrUpdate(input);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testDelete()
    {
        TestUpdateFileGenerations updateFileGenerations = new TestUpdateFileGenerations();

        StoredFileGeneration generation = new StoredFileGeneration("test.group", "test-artifact", "1.0.0",
                "/path/to/file", "avro", new DepotGeneration("/path/to/file", "test content"));
        List<StoredFileGeneration> input = new ArrayList<>();
        input.add(generation);
        updateFileGenerations.createOrUpdate(input);

        long result = updateFileGenerations.delete("test.group", "test-artifact", "1.0.0");

        Assertions.assertEquals(1L, result);
    }

    @Test
    public void testDeleteWithNoMatches()
    {
        TestUpdateFileGenerations updateFileGenerations = new TestUpdateFileGenerations();

        long result = updateFileGenerations.delete("test.group", "test-artifact", "1.0.0");

        Assertions.assertEquals(0L, result);
    }
}
