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

package org.finos.legend.depot.store.mongo.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.CoreDataMongoStoreTests;
import org.finos.legend.depot.store.mongo.projects.ProjectsMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsVersionsMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static org.finos.legend.depot.store.mongo.core.BaseMongo.convert;

public class CoreDataMigrationsTest extends CoreDataMongoStoreTests
{
    private CoreDataMigrations coreDataMigrations;

    @BeforeEach
    public void setup()
    {
        coreDataMigrations = new CoreDataMigrations(mongoProvider);
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

    private void setUpProjectVersionDataFromFile(URL projectConfigFile)
    {
        try
        {
            readProjectDataConfigsFile(projectConfigFile).forEach(project ->
            {
                try
                {
                    getMongoProjectVersions().insertOne(Document.parse(new ObjectMapper().writeValueAsString(project)));
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
    public void canCreateCoreDataMigrations()
    {
        CoreDataMigrations migrations = new CoreDataMigrations(mongoProvider);
        Assertions.assertNotNull(migrations);
    }

    @Test
    public void canMigrateToProjectVersions()
    {
        setUpProjectDataFromFile(this.getClass().getClassLoader().getResource("data/projectVersionMigration/projectsData.json"));
        Assertions.assertEquals(3, mongoProvider.getCollection(ProjectsMongo.COLLECTION).countDocuments());
        Assertions.assertEquals(0, mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).countDocuments());

        coreDataMigrations.migrationToProjectVersions();

        Assertions.assertEquals(7, mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).countDocuments());
        StoreProjectVersionData result = convert(new ObjectMapper(), mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).find().first(), StoreProjectVersionData.class);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getGroupId());
        Assertions.assertNotNull(result.getArtifactId());
    }

    @Test
    public void canCleanUpProjectData()
    {
        setUpProjectDataFromFile(this.getClass().getClassLoader().getResource("data/projectVersionMigration/projectsData.json"));
        Assertions.assertEquals(3, mongoProvider.getCollection(ProjectsMongo.COLLECTION).countDocuments());

        coreDataMigrations.cleanUpProjectData();

        Assertions.assertEquals(3, mongoProvider.getCollection(ProjectsMongo.COLLECTION).countDocuments());
        StoreProjectData result = convert(new ObjectMapper(), mongoProvider.getCollection(ProjectsMongo.COLLECTION).find().first(), StoreProjectData.class);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getProjectId());
    }

    @Test
    public void canCalculateTransitiveDependenciesForAllProjectVersions()
    {
        setUpProjectVersionDataFromFile(this.getClass().getClassLoader().getResource("data/projectsVersions1.json"));
        Assertions.assertEquals(9, mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).countDocuments());

        coreDataMigrations.calculateTransitiveDependenciesForAllProjectVersions();

        StoreProjectVersionData result = convert(new ObjectMapper(), mongoProvider.getCollection("versionsTemp").find().first(), StoreProjectVersionData.class);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getTransitiveDependenciesReport());
    }

    @Test
    public void canAddTransitiveDependenciesToVersionData()
    {
        setUpProjectVersionDataFromFile(this.getClass().getClassLoader().getResource("data/projectsVersions1.json"));
        Assertions.assertEquals(9, mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).countDocuments());

        coreDataMigrations.calculateTransitiveDependenciesForAllProjectVersions();
        coreDataMigrations.addTransitiveDependenciesToVersionData();

        StoreProjectVersionData result = convert(new ObjectMapper(), mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).find().first(), StoreProjectVersionData.class);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getTransitiveDependenciesReport());
    }

    @Test
    public void canAddLatestVersionToProjectData()
    {
        setUpProjectDataFromFile(this.getClass().getClassLoader().getResource("data/projectVersionMigration/projectsData.json"));
        coreDataMigrations.migrationToProjectVersions();
        coreDataMigrations.cleanUpProjectData();
        Assertions.assertEquals(3, mongoProvider.getCollection(ProjectsMongo.COLLECTION).countDocuments());

        coreDataMigrations.addLatestVersionToProjectData();

        Assertions.assertEquals(3, mongoProvider.getCollection(ProjectsMongo.COLLECTION).countDocuments());
        StoreProjectData result = convert(new ObjectMapper(), mongoProvider.getCollection(ProjectsMongo.COLLECTION).find().first(), StoreProjectData.class);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getLatestVersion());
    }
}
