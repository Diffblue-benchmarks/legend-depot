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
import org.eclipse.collections.api.block.function.Function3;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.domain.project.dependencies.VersionDependencyReport;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.CoreDataMongoStoreTests;
import org.finos.legend.depot.store.mongo.admin.CoreDataMigrations;
import org.finos.legend.depot.store.mongo.projects.ProjectsVersionsMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.finos.legend.depot.store.mongo.core.BaseMongo.convert;

public class TestDependenciesMigration extends CoreDataMongoStoreTests
{
    CoreDataMigrations mongoAdminStore = new CoreDataMigrations(mongoProvider);
    private static final String VERSIONS_COLLECTION = "versionsTemp";

    @BeforeEach
    public void setupTestData()
    {
        setUpProjectDataFromFile(this.getClass().getClassLoader().getResource("data/projectsVersions1.json"));
        Assertions.assertEquals(9, mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).countDocuments());
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
    public void testStoringTransitiveDependencies()
    {
        mongoAdminStore.calculateTransitiveDependenciesForAllProjectVersions();

        StoreProjectVersionData result = convert(new ObjectMapper(),mongoProvider.getCollection(VERSIONS_COLLECTION).find().first(), StoreProjectVersionData.class);
        Assertions.assertTrue(result.getTransitiveDependenciesReport().isValid());
        Assertions.assertEquals(4, result.getTransitiveDependenciesReport().getTransitiveDependencies().size());
        ProjectVersion pv1 = new ProjectVersion("examples.metadata", "test-dependencies", "2.0.0");
        ProjectVersion pv2 = new ProjectVersion("examples.metadata", "art101", "1.0.0");
        ProjectVersion pv3 = new ProjectVersion("examples.metadata", "art102", "1.0.0");
        ProjectVersion pv4 = new ProjectVersion("examples.metadata", "art103", "1.0.0");
        Assertions.assertEquals(result.getTransitiveDependenciesReport().getTransitiveDependencies(), Arrays.asList(pv1, pv2, pv3, pv4));

        //check for the excluded Dependency
        StoreProjectVersionData result1 = convert(new ObjectMapper(), mongoProvider.getCollection(VERSIONS_COLLECTION).find(Filters.and(Filters.eq("groupId", "examples.metadata"), Filters.eq("artifactId", "art104"))).first(), StoreProjectVersionData.class);
        Assertions.assertFalse(result1.getTransitiveDependenciesReport().isValid());

        //check for other dependencies
        StoreProjectVersionData result2 = convert(new ObjectMapper(), mongoProvider.getCollection(VERSIONS_COLLECTION).find(Filters.and(Filters.eq("groupId", "examples.metadata"), Filters.eq("artifactId", "test-dependencies"))).first(), StoreProjectVersionData.class);
        Assertions.assertTrue(result2.getTransitiveDependenciesReport().isValid());
        Assertions.assertEquals(result2.getTransitiveDependenciesReport().getTransitiveDependencies(), Arrays.asList(pv2, pv3, pv4));

        //check for dependency not present in store
        StoreProjectVersionData result3 = convert(new ObjectMapper(), mongoProvider.getCollection(VERSIONS_COLLECTION).find(Filters.and(Filters.eq("groupId", "examples.metadata"), Filters.eq("artifactId", "art108"))).first(), StoreProjectVersionData.class);
        Assertions.assertFalse(result3.getTransitiveDependenciesReport().isValid());
    }

    @Test
    public void testUpdatingVersionsCollection()
    {
        mongoAdminStore.calculateTransitiveDependenciesForAllProjectVersions();
        mongoAdminStore.addTransitiveDependenciesToVersionData();
        StoreProjectVersionData result = convert(new ObjectMapper(),mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).find().first(), StoreProjectVersionData.class);
        Assertions.assertTrue(result.getTransitiveDependenciesReport().isValid());
        Assertions.assertEquals(4, result.getTransitiveDependenciesReport().getTransitiveDependencies().size());
        ProjectVersion pv1 = new ProjectVersion("examples.metadata", "test-dependencies", "2.0.0");
        ProjectVersion pv2 = new ProjectVersion("examples.metadata", "art101", "1.0.0");
        ProjectVersion pv3 = new ProjectVersion("examples.metadata", "art102", "1.0.0");
        ProjectVersion pv4 = new ProjectVersion("examples.metadata", "art103", "1.0.0");
        Assertions.assertEquals(result.getTransitiveDependenciesReport().getTransitiveDependencies(), Arrays.asList(pv1, pv2, pv3, pv4));

        //check for the excluded Dependency
        StoreProjectVersionData result1 = convert(new ObjectMapper(), mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).find(Filters.and(Filters.eq("groupId", "examples.metadata"), Filters.eq("artifactId", "art104"))).first(), StoreProjectVersionData.class);
        Assertions.assertFalse(result1.getTransitiveDependenciesReport().isValid());

        //check for other dependencies
        StoreProjectVersionData result2 = convert(new ObjectMapper(), mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).find(Filters.and(Filters.eq("groupId", "examples.metadata"), Filters.eq("artifactId", "test-dependencies"))).first(), StoreProjectVersionData.class);
        Assertions.assertTrue(result2.getTransitiveDependenciesReport().isValid());
        Assertions.assertEquals(result2.getTransitiveDependenciesReport().getTransitiveDependencies(), Arrays.asList(pv2, pv3, pv4));

        //check for dependency not present in store
        StoreProjectVersionData result3 = convert(new ObjectMapper(), mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).find(Filters.and(Filters.eq("groupId", "examples.metadata"), Filters.eq("artifactId", "art108"))).first(), StoreProjectVersionData.class);
        Assertions.assertFalse(result3.getTransitiveDependenciesReport().isValid());
    }

    @Test
    public void testCalculateTransitiveDependenciesWithExcludedDependency() throws Exception
    {
        DependenciesMigration migration = new DependenciesMigration(mongoProvider);

        Method method = DependenciesMigration.class.getDeclaredMethod("calculateTransitiveDependencies", ProjectVersion.class, Function3.class);
        method.setAccessible(true);

        ProjectVersion pv = new ProjectVersion("examples.metadata", "excluded-art", "1.0.0");
        ProjectVersionData pvd = new ProjectVersionData();
        pvd.setExcluded(true);
        StoreProjectVersionData spvd = new StoreProjectVersionData("examples.metadata", "excluded-art", "1.0.0");
        spvd.setVersionData(pvd);

        Function3<String, String, String, StoreProjectVersionData> provider = (group, artifact, version) -> spvd;

        VersionDependencyReport report = (VersionDependencyReport) method.invoke(migration, pv, provider);
        Assertions.assertFalse(report.isValid());
        Assertions.assertTrue(report.getTransitiveDependencies().isEmpty());
    }

    @Test
    public void testCalculateTransitiveDependenciesWithGenericException() throws Exception
    {
        DependenciesMigration migration = new DependenciesMigration(mongoProvider);

        Method method = DependenciesMigration.class.getDeclaredMethod("calculateTransitiveDependencies", ProjectVersion.class, Function3.class);
        method.setAccessible(true);

        ProjectVersion pv = new ProjectVersion("examples.metadata", "error-art", "1.0.0");
        Function3<String, String, String, StoreProjectVersionData> provider = (group, artifact, version) ->
        {
            throw new RuntimeException("unexpected error");
        };

        try
        {
            method.invoke(migration, pv, provider);
            Assertions.fail("Expected InvocationTargetException wrapping IllegalStateException");
        }
        catch (InvocationTargetException e)
        {
            Assertions.assertTrue(e.getCause() instanceof IllegalStateException);
            Assertions.assertTrue(e.getCause().getMessage().contains("unexpected error"));
        }
    }

    @Test
    public void testCalculateTransitiveDependenciesWithExcludedTransitiveDependency() throws Exception
    {
        DependenciesMigration migration = new DependenciesMigration(mongoProvider);

        Method method = DependenciesMigration.class.getDeclaredMethod("calculateTransitiveDependencies", ProjectVersion.class, Function3.class);
        method.setAccessible(true);

        ProjectVersionData excludedData = new ProjectVersionData();
        excludedData.setExcluded(true);
        StoreProjectVersionData excludedVersion = new StoreProjectVersionData("examples.metadata", "dep-excluded", "1.0.0");
        excludedVersion.setVersionData(excludedData);

        ProjectVersionData parentData = new ProjectVersionData();
        parentData.setDependencies(Collections.singletonList(new ProjectVersion("examples.metadata", "dep-excluded", "1.0.0")));
        StoreProjectVersionData parentVersion = new StoreProjectVersionData("examples.metadata", "parent-art", "1.0.0");
        parentVersion.setVersionData(parentData);

        Function3<String, String, String, StoreProjectVersionData> provider = (group, artifact, version) ->
        {
            if ("dep-excluded".equals(artifact))
            {
                return excludedVersion;
            }
            return parentVersion;
        };

        ProjectVersion pv = new ProjectVersion("examples.metadata", "parent-art", "1.0.0");
        VersionDependencyReport report = (VersionDependencyReport) method.invoke(migration, pv, provider);
        Assertions.assertFalse(report.isValid());
        Assertions.assertTrue(report.getTransitiveDependencies().isEmpty());
    }
}
