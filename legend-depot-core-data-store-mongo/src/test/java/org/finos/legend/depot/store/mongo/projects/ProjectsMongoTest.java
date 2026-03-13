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

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;
import org.bson.conversions.Bson;
import org.finos.legend.depot.store.StoreException;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class ProjectsMongoTest extends TestStoreMongo
{
    private ProjectsMongo projectsMongo;

    @BeforeEach
    public void setUp()
    {
        projectsMongo = new ProjectsMongo(mongoProvider);
    }

    @Test
    public void canCreateProjectsMongo()
    {
        ProjectsMongo instance = new ProjectsMongo(mongoProvider);
        Assertions.assertNotNull(instance);
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = ProjectsMongo.buildIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertEquals(1, indexes.size());
    }

    @Test
    public void canGetCollection()
    {
        MongoCollection collection = projectsMongo.getCollection();
        Assertions.assertNotNull(collection);
    }

    @Test
    public void canGetAll()
    {
        StoreProjectData project1 = new StoreProjectData("PROD-1", "org.example", "artifact1");
        StoreProjectData project2 = new StoreProjectData("PROD-2", "org.example", "artifact2");

        projectsMongo.createOrUpdate(project1);
        projectsMongo.createOrUpdate(project2);

        List<StoreProjectData> allProjects = projectsMongo.getAll();
        Assertions.assertNotNull(allProjects);
        Assertions.assertEquals(2, allProjects.size());
    }

    @Test
    public void canFindByProjectId()
    {
        StoreProjectData project = new StoreProjectData("PROD-123", "org.example", "test-artifact");
        projectsMongo.createOrUpdate(project);

        List<StoreProjectData> results = projectsMongo.findByProjectId("PROD-123");
        Assertions.assertNotNull(results);
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals("PROD-123", results.get(0).getProjectId());
    }

    @Test
    public void canFindByGroupIdAndArtifactId()
    {
        StoreProjectData project = new StoreProjectData("PROD-456", "org.finos", "legend-depot");
        projectsMongo.createOrUpdate(project);

        Optional<StoreProjectData> result = projectsMongo.find("org.finos", "legend-depot");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("PROD-456", result.get().getProjectId());
    }

    @Test
    public void canDeleteByGroupIdAndArtifactId()
    {
        StoreProjectData project = new StoreProjectData("PROD-789", "org.delete", "delete-artifact");
        projectsMongo.createOrUpdate(project);

        long deletedCount = projectsMongo.delete("org.delete", "delete-artifact");
        Assertions.assertEquals(1, deletedCount);

        Optional<StoreProjectData> result = projectsMongo.find("org.delete", "delete-artifact");
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void validateNewDataThrowsExceptionForInvalidProject()
    {
        StoreProjectData invalidProject = new StoreProjectData("invalid", "org.example", "artifact");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            projectsMongo.createOrUpdate(invalidProject);
        });
    }

    @Test
    public void validateNewDataThrowsExceptionForInvalidGroupId()
    {
        StoreProjectData invalidProject = new StoreProjectData("PROD-1", null, "artifact");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            projectsMongo.createOrUpdate(invalidProject);
        });
    }

    @Test
    public void validateNewDataThrowsExceptionForInvalidArtifactId()
    {
        StoreProjectData invalidProject = new StoreProjectData("PROD-1", "org.example", null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            projectsMongo.createOrUpdate(invalidProject);
        });
    }

    @Test
    public void validateNewDataThrowsExceptionForDuplicateCoordinatesWithDifferentProjectId()
    {
        StoreProjectData project1 = new StoreProjectData("PROD-100", "org.duplicate", "duplicate-artifact");
        projectsMongo.createOrUpdate(project1);

        StoreProjectData project2 = new StoreProjectData("PROD-200", "org.duplicate", "duplicate-artifact");

        Assertions.assertThrows(StoreException.class, () -> {
            projectsMongo.createOrUpdate(project2);
        });
    }

    @Test
    public void validateNewDataAllowsUpdateWithSameProjectId()
    {
        StoreProjectData project = new StoreProjectData("PROD-300", "org.same", "same-artifact");
        projectsMongo.createOrUpdate(project);

        StoreProjectData updatedProject = new StoreProjectData("PROD-300", "org.same", "same-artifact");
        updatedProject.setLatestVersion("1.0.0");

        projectsMongo.createOrUpdate(updatedProject);

        Optional<StoreProjectData> result = projectsMongo.find("org.same", "same-artifact");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("1.0.0", result.get().getLatestVersion());
    }
}
