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

package org.finos.legend.depot.store.api.admin.artifacts;

import org.finos.legend.depot.store.model.admin.artifacts.ArtifactFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ArtifactsFilesStoreTest
{
    private static final ArtifactsFilesStore STORE = new ArtifactsFilesStore()
    {
        private final Map<String, ArtifactFile> storage = new HashMap<>();

        @Override
        public ArtifactFile createOrUpdate(ArtifactFile detail)
        {
            storage.put(detail.getPath(), detail);
            return detail;
        }

        @Override
        public Optional<ArtifactFile> find(String path)
        {
            return Optional.ofNullable(storage.get(path));
        }
    };

    @Test
    public void testCreateOrUpdate()
    {
        ArtifactFile file = new ArtifactFile("/some/path", "abc123");
        ArtifactFile result = STORE.createOrUpdate(file);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("/some/path", result.getPath());
        Assertions.assertEquals("abc123", result.getCheckSum());
    }

    @Test
    public void testFindReturnsValueWhenPresent()
    {
        ArtifactFile file = new ArtifactFile("/another/path", "def456");
        STORE.createOrUpdate(file);
        Optional<ArtifactFile> result = STORE.find("/another/path");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("def456", result.get().getCheckSum());
    }

    @Test
    public void testFindReturnsEmptyWhenAbsent()
    {
        Optional<ArtifactFile> result = STORE.find("/nonexistent/path");
        Assertions.assertFalse(result.isPresent());
    }
}
