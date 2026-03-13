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

package org.finos.legend.depot.store.mongo.admin.migrations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.CoreDataMongoStoreTests;
import org.finos.legend.depot.store.mongo.core.BaseMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.finos.legend.depot.domain.version.VersionValidator.BRANCH_SNAPSHOT;
import static org.finos.legend.depot.store.mongo.core.BaseMongo.buildDocument;
import static org.finos.legend.depot.store.mongo.core.BaseMongo.convert;

public class TestProjectToProjectVersionMigration extends CoreDataMongoStoreTests
{
    private ProjectToProjectVersionMigration migration;

    @BeforeEach
    public void setupTestData()
    {
        migration = new ProjectToProjectVersionMigration(mongoProvider);
        setUpProjectDataFromFile(this.getClass().getClassLoader().getResource("data/projectVersionMigration/projectsData.json"));
        Assertions.assertEquals(3, mongoProvider.getCollection("project-configurations").countDocuments());
    }

    protected static List<Document> readProjectDataConfigsFile(URL fileName)
    {
        try
        {
            InputStream stream = fileName.openStream();
            String jsonInput = new java.util.Scanner(stream).useDelimiter("\\A").next();
            List<Document> projects = new ObjectMapper().readValue(jsonInput, new TypeReference<List<Document>>() {});
            Assertions.assertNotNull(projects, "testing file" + fileName.getFile());
            return projects;
        }
        catch (Exception e)
        {
            Assertions.fail("an error has occurred loading test project metadata" + e.getMessage());
        }
        return null;
    }

    private void setUpProjectDataFromFile(URL projectConfigFile)
    {
        try
        {
            Assertions.assertNotNull(getMongoProjects());
            readProjectDataConfigsFile(projectConfigFile).forEach(project ->
            {
                try
                {
                    getMongoProjects().insertOne(Document.parse(new ObjectMapper().writeValueAsString(project)));
                }
                catch (JsonProcessingException e)
                {
                    Assertions.fail("an error has occurred loading test project " + e.getMessage());
                }
            });
        }
        catch (Exception e)
        {
            Assertions.fail("an error has occurred loading test project metadata" + e.getMessage());
        }
    }

    @Test
    public void canCreateMigration()
    {
        Assertions.assertNotNull(migration);
    }

    @Test
    public void canMigrateProjectToProjectVersions()
    {
        Assertions.assertEquals(0, mongoProvider.getCollection("versions").countDocuments());
        migration.migrationToProjectVersions();
        Assertions.assertEquals(7, mongoProvider.getCollection("versions").countDocuments());
        StoreProjectVersionData result = convert(new ObjectMapper(), mongoProvider.getCollection("versions").find().first(), StoreProjectVersionData.class);
        Assertions.assertEquals("examples.metadata", result.getGroupId());
        Assertions.assertEquals("test", result.getArtifactId());
        Assertions.assertEquals(BRANCH_SNAPSHOT("master"), result.getVersionId());
    }

    @Test
    public void canCleanUpProjectData()
    {
        migration.cleanUpProjectData();
        Assertions.assertEquals(3, mongoProvider.getCollection("project-configurations").countDocuments());
        StoreProjectData result = convert(new ObjectMapper(), mongoProvider.getCollection("project-configurations").find().first(), StoreProjectData.class);
        Assertions.assertEquals("PROD-A", result.getProjectId());
        Assertions.assertEquals("examples.metadata", result.getGroupId());
        Assertions.assertEquals("test", result.getArtifactId());
    }

    @Test
    public void canAddLatestVersionToProjectData()
    {
        Assertions.assertEquals(0, mongoProvider.getCollection("versions").countDocuments());
        migration.migrationToProjectVersions();
        Assertions.assertEquals(7, mongoProvider.getCollection("versions").countDocuments());
        migration.cleanUpProjectData();
        Assertions.assertEquals(3, mongoProvider.getCollection("project-configurations").countDocuments());
        migration.addLatestVersionToProjectData();
        Assertions.assertEquals(3, mongoProvider.getCollection("project-configurations").countDocuments());
        List<StoreProjectData> result = new ArrayList<>();
        mongoProvider.getCollection("project-configurations").find().forEach((Consumer<Document>) doc -> result.add(convert(new ObjectMapper(), doc, StoreProjectData.class)));
        Assertions.assertEquals("2.3.1", result.get(0).getLatestVersion());
        Assertions.assertEquals("1.0.0", result.get(1).getLatestVersion());
        Assertions.assertEquals("2.0.1", result.get(2).getLatestVersion());
    }

    @Test
    public void canHandleProjectWithNoVersions()
    {
        mongoProvider.getCollection("project-configurations").drop();
        mongoProvider.getCollection("project-configurations").insertOne(buildDocument(new StoreProjectData("PROD-A", "examples.project", "metadata")));
        mongoProvider.getCollection("versions").insertOne(BaseMongo.buildDocument(new StoreProjectVersionData("examples.project", "metadata", "master-snapshot")));

        migration.addLatestVersionToProjectData();
        StoreProjectData result = convert(new ObjectMapper(), mongoProvider.getCollection("project-configurations").find().first(), StoreProjectData.class);

        Assertions.assertNull(result.getLatestVersion());
    }

    @Test
    public void canMigrateMultipleProjects()
    {
        migration.migrationToProjectVersions();
        long versionCount = mongoProvider.getCollection("versions").countDocuments();
        Assertions.assertTrue(versionCount > 0);

        List<StoreProjectVersionData> results = new ArrayList<>();
        mongoProvider.getCollection("versions").find().forEach((Consumer<Document>) doc ->
            results.add(convert(new ObjectMapper(), doc, StoreProjectVersionData.class)));

        Assertions.assertFalse(results.isEmpty());
        for (StoreProjectVersionData result : results)
        {
            Assertions.assertNotNull(result.getGroupId());
            Assertions.assertNotNull(result.getArtifactId());
            Assertions.assertNotNull(result.getVersionId());
        }
    }

    @Test
    public void canCleanUpMultipleProjects()
    {
        long beforeCount = mongoProvider.getCollection("project-configurations").countDocuments();
        migration.cleanUpProjectData();
        long afterCount = mongoProvider.getCollection("project-configurations").countDocuments();

        Assertions.assertEquals(beforeCount, afterCount);
    }

    @Test
    public void canAddLatestVersionToMultipleProjects()
    {
        migration.migrationToProjectVersions();
        migration.cleanUpProjectData();
        migration.addLatestVersionToProjectData();

        List<StoreProjectData> results = new ArrayList<>();
        mongoProvider.getCollection("project-configurations").find().forEach((Consumer<Document>) doc ->
            results.add(convert(new ObjectMapper(), doc, StoreProjectData.class)));

        Assertions.assertFalse(results.isEmpty());
    }

    @Test
    public void canHandleExceptionDuringMigration()
    {
        Document invalidDocument = new Document();
        invalidDocument.append(BaseMongo.GROUP_ID, "examples.metadata");
        invalidDocument.append(BaseMongo.ARTIFACT_ID, "invalid-project");
        invalidDocument.append("versions", "not-a-list");

        mongoProvider.getCollection("project-configurations").insertOne(invalidDocument);

        long beforeCount = mongoProvider.getCollection("versions").countDocuments();
        migration.migrationToProjectVersions();
        long afterCount = mongoProvider.getCollection("versions").countDocuments();

        Assertions.assertTrue(afterCount >= beforeCount);
    }
}
