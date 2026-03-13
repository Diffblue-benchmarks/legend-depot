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

package org.finos.legend.depot.store.api.projects;

import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class UpdateProjectsVersionsTest
{
    private UpdateProjectsVersions store;
    private StoreProjectVersionData testProjectVersionData;

    @BeforeEach
    public void setUp()
    {
        testProjectVersionData = new StoreProjectVersionData("com.example", "myartifact", "1.0.0");

        store = new UpdateProjectsVersions()
        {
            @Override
            public StoreProjectVersionData createOrUpdate(StoreProjectVersionData projectVersionData)
            {
                return projectVersionData;
            }

            @Override
            public long delete(String groupId, String artifactId)
            {
                return 5L;
            }

            @Override
            public long delete(String groupId, String artifactId, String versionId)
            {
                return 1L;
            }

            @Override
            public List<StoreProjectVersionData> getAll()
            {
                return null;
            }

            @Override
            public List<StoreProjectVersionData> findByUpdatedDate(long updatedFrom, long updatedTo)
            {
                return null;
            }

            @Override
            public List<StoreProjectVersionData> find(String groupId, String artifactId)
            {
                return null;
            }

            @Override
            public Optional<StoreProjectVersionData> find(String groupId, String artifactId, String versionId)
            {
                return Optional.empty();
            }

            @Override
            public long getVersionCount(String groupId, String artifactId)
            {
                return 0;
            }

            @Override
            public List<StoreProjectVersionData> findVersion(Boolean excluded)
            {
                return null;
            }
        };
    }

    @Test
    public void canCreateOrUpdate()
    {
        StoreProjectVersionData result = store.createOrUpdate(testProjectVersionData);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("com.example", result.getGroupId());
        Assertions.assertEquals("myartifact", result.getArtifactId());
        Assertions.assertEquals("1.0.0", result.getVersionId());
    }

    @Test
    public void canDeleteByGroupAndArtifact()
    {
        long result = store.delete("com.example", "myartifact");

        Assertions.assertEquals(5L, result);
    }

    @Test
    public void canDeleteByGroupArtifactAndVersion()
    {
        long result = store.delete("com.example", "myartifact", "1.0.0");

        Assertions.assertEquals(1L, result);
    }
}
