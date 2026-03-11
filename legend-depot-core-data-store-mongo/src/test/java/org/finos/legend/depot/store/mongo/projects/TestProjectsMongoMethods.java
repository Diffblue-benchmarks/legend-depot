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

package org.finos.legend.depot.store.mongo.projects;

import com.mongodb.client.model.IndexModel;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.mongo.CoreDataMongoStoreTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class TestProjectsMongoMethods extends CoreDataMongoStoreTests
{
    private ProjectsMongo projectsAPI = new ProjectsMongo(mongoProvider);

    @BeforeEach
    public void setUpTestData()
    {
        setUpProjectsFromFile(this.getClass().getClassLoader().getResource("data/projects.json"));
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = ProjectsMongo.buildIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertEquals(1, indexes.size());
        Assertions.assertEquals("groupId-artifactId", indexes.get(0).getOptions().getName());
    }

    @Test
    public void canDeleteByCoordinates()
    {
        Optional<StoreProjectData> project = projectsAPI.find("examples.metadata", "test");
        Assertions.assertTrue(project.isPresent());

        long deleted = projectsAPI.delete("examples.metadata", "test");
        Assertions.assertEquals(1, deleted);

        Optional<StoreProjectData> afterDelete = projectsAPI.find("examples.metadata", "test");
        Assertions.assertFalse(afterDelete.isPresent());
    }

    @Test
    public void canDeleteNonExistentProject()
    {
        long deleted = projectsAPI.delete("non.existent", "artifact");
        Assertions.assertEquals(0, deleted);
    }
}
