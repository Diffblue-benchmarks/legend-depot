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
import org.finos.legend.depot.store.mongo.core.BaseMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsVersionsMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
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
    public void testConstructor()
    {
        DependenciesMigration migration = new DependenciesMigration(mongoProvider);
        Assertions.assertNotNull(migration);
    }

    @Test
    public void testCalculateTransitiveDependenciesDirectly()
    {
        DependenciesMigration migration = new DependenciesMigration(mongoProvider);
        migration.calculateTransitiveDependenciesForAllProjectVersions();
        Assertions.assertEquals(9, mongoProvider.getCollection(VERSIONS_COLLECTION).countDocuments());
    }

    @Test
    public void testAddTransitiveDependenciesDirectly()
    {
        DependenciesMigration migration = new DependenciesMigration(mongoProvider);
        migration.calculateTransitiveDependenciesForAllProjectVersions();
        migration.addTransitiveDependenciesToVersionData();
        StoreProjectVersionData result = convert(new ObjectMapper(), mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).find().first(), StoreProjectVersionData.class);
        Assertions.assertNotNull(result.getTransitiveDependenciesReport());
    }

    @Test
    public void testWithExcludedDependency()
    {
        mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).drop();

        StoreProjectVersionData excludedProject = new StoreProjectVersionData("test.group", "excluded-artifact", "1.0.0");
        ProjectVersionData versionData = new ProjectVersionData();
        versionData.setExcluded(true);
        excludedProject.setVersionData(versionData);

        getMongoProjectVersions().insertOne(BaseMongo.buildDocument(excludedProject));

        DependenciesMigration migration = new DependenciesMigration(mongoProvider);
        migration.calculateTransitiveDependenciesForAllProjectVersions();

        StoreProjectVersionData result = convert(new ObjectMapper(), mongoProvider.getCollection(VERSIONS_COLLECTION).find().first(), StoreProjectVersionData.class);
        Assertions.assertFalse(result.getTransitiveDependenciesReport().isValid());
    }

    @Test
    public void testWithEmptyDependenciesList()
    {
        mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).drop();

        StoreProjectVersionData projectWithNoDeps = new StoreProjectVersionData("test.group", "no-deps-artifact", "1.0.0");
        ProjectVersionData versionData = new ProjectVersionData();
        versionData.setDependencies(Collections.emptyList());
        projectWithNoDeps.setVersionData(versionData);

        getMongoProjectVersions().insertOne(BaseMongo.buildDocument(projectWithNoDeps));

        DependenciesMigration migration = new DependenciesMigration(mongoProvider);
        migration.calculateTransitiveDependenciesForAllProjectVersions();

        Assertions.assertEquals(1, mongoProvider.getCollection(VERSIONS_COLLECTION).countDocuments());
    }

    @Test
    public void testMissingDependencyInStore()
    {
        mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).drop();

        StoreProjectVersionData projectWithMissingDep = new StoreProjectVersionData("test.group", "main-artifact", "1.0.0");
        ProjectVersionData versionData = new ProjectVersionData();
        ProjectVersion missingDep = new ProjectVersion("test.group", "missing-artifact", "1.0.0");
        versionData.setDependencies(Arrays.asList(missingDep));
        projectWithMissingDep.setVersionData(versionData);

        getMongoProjectVersions().insertOne(BaseMongo.buildDocument(projectWithMissingDep));

        DependenciesMigration migration = new DependenciesMigration(mongoProvider);
        migration.calculateTransitiveDependenciesForAllProjectVersions();

        StoreProjectVersionData result = convert(new ObjectMapper(), mongoProvider.getCollection(VERSIONS_COLLECTION).find(Filters.eq("artifactId", "main-artifact")).first(), StoreProjectVersionData.class);
        Assertions.assertFalse(result.getTransitiveDependenciesReport().isValid());
    }

    @Test
    public void testComplexDependencyChain()
    {
        mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).drop();

        StoreProjectVersionData leaf = new StoreProjectVersionData("test.group", "leaf", "1.0.0");
        leaf.setVersionData(new ProjectVersionData());

        StoreProjectVersionData middle = new StoreProjectVersionData("test.group", "middle", "1.0.0");
        ProjectVersionData middleData = new ProjectVersionData();
        middleData.setDependencies(Arrays.asList(new ProjectVersion("test.group", "leaf", "1.0.0")));
        middle.setVersionData(middleData);

        StoreProjectVersionData root = new StoreProjectVersionData("test.group", "root", "1.0.0");
        ProjectVersionData rootData = new ProjectVersionData();
        rootData.setDependencies(Arrays.asList(new ProjectVersion("test.group", "middle", "1.0.0")));
        root.setVersionData(rootData);

        getMongoProjectVersions().insertOne(BaseMongo.buildDocument(leaf));
        getMongoProjectVersions().insertOne(BaseMongo.buildDocument(middle));
        getMongoProjectVersions().insertOne(BaseMongo.buildDocument(root));

        DependenciesMigration migration = new DependenciesMigration(mongoProvider);
        migration.calculateTransitiveDependenciesForAllProjectVersions();

        StoreProjectVersionData rootResult = convert(new ObjectMapper(), mongoProvider.getCollection(VERSIONS_COLLECTION).find(Filters.eq("artifactId", "root")).first(), StoreProjectVersionData.class);
        Assertions.assertTrue(rootResult.getTransitiveDependenciesReport().isValid());
        Assertions.assertEquals(2, rootResult.getTransitiveDependenciesReport().getTransitiveDependencies().size());
    }

    @Test
    public void testDependencyOnExcludedProject()
    {
        mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).drop();

        StoreProjectVersionData excludedProject = new StoreProjectVersionData("test.group", "excluded-dep", "1.0.0");
        ProjectVersionData excludedData = new ProjectVersionData();
        excludedData.setExcluded(true);
        excludedProject.setVersionData(excludedData);

        StoreProjectVersionData projectDependingOnExcluded = new StoreProjectVersionData("test.group", "depends-on-excluded", "1.0.0");
        ProjectVersionData dependingData = new ProjectVersionData();
        dependingData.setDependencies(Arrays.asList(new ProjectVersion("test.group", "excluded-dep", "1.0.0")));
        projectDependingOnExcluded.setVersionData(dependingData);

        getMongoProjectVersions().insertOne(BaseMongo.buildDocument(excludedProject));
        getMongoProjectVersions().insertOne(BaseMongo.buildDocument(projectDependingOnExcluded));

        DependenciesMigration migration = new DependenciesMigration(mongoProvider);
        migration.calculateTransitiveDependenciesForAllProjectVersions();

        StoreProjectVersionData result = convert(new ObjectMapper(), mongoProvider.getCollection(VERSIONS_COLLECTION).find(Filters.eq("artifactId", "depends-on-excluded")).first(), StoreProjectVersionData.class);
        Assertions.assertFalse(result.getTransitiveDependenciesReport().isValid());
    }

    @Test
    public void testDependencyProviderThrowsException()
    {
        mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).drop();

        StoreProjectVersionData leaf = new StoreProjectVersionData("test.group", "leaf", "1.0.0");
        leaf.setVersionData(new ProjectVersionData());

        StoreProjectVersionData problematicMiddle = new StoreProjectVersionData("test.group", "problematic", "1.0.0");
        ProjectVersionData problematicData = new ProjectVersionData();
        problematicData.setDependencies(Arrays.asList(new ProjectVersion("test.group", "leaf", "1.0.0")));
        problematicMiddle.setVersionData(problematicData);

        StoreProjectVersionData root = new StoreProjectVersionData("test.group", "root", "1.0.0");
        ProjectVersionData rootData = new ProjectVersionData();
        rootData.setDependencies(Arrays.asList(new ProjectVersion("test.group", "problematic", "1.0.0")));
        root.setVersionData(rootData);

        getMongoProjectVersions().insertOne(BaseMongo.buildDocument(leaf));
        getMongoProjectVersions().insertOne(BaseMongo.buildDocument(root));

        DependenciesMigration migration = new DependenciesMigration(mongoProvider);
        migration.calculateTransitiveDependenciesForAllProjectVersions();

        StoreProjectVersionData result = convert(new ObjectMapper(), mongoProvider.getCollection(VERSIONS_COLLECTION).find(Filters.eq("artifactId", "root")).first(), StoreProjectVersionData.class);
        Assertions.assertFalse(result.getTransitiveDependenciesReport().isValid());
    }

    @Test
    public void testGenericExceptionInCalculateTransitiveDependencies() throws Exception
    {
        DependenciesMigration migration = new DependenciesMigration(mongoProvider);
        ProjectVersion projectVersion = new ProjectVersion("test.group", "test-artifact", "1.0.0");

        Function3<String, String, String, StoreProjectVersionData> throwingProvider = (group, artifact, version) -> {
            throw new RuntimeException("Simulated unexpected error");
        };

        Method method = DependenciesMigration.class.getDeclaredMethod("calculateTransitiveDependencies", ProjectVersion.class, Function3.class);
        method.setAccessible(true);

        try
        {
            method.invoke(migration, projectVersion, throwingProvider);
            Assertions.fail("Expected IllegalStateException to be thrown");
        }
        catch (Exception e)
        {
            Throwable cause = e.getCause();
            Assertions.assertNotNull(cause);
            Assertions.assertTrue(cause instanceof IllegalStateException);
            Assertions.assertTrue(cause.getMessage().contains("Error finding transitive dependencies with message"));
            Assertions.assertTrue(cause.getMessage().contains("Simulated unexpected error"));
        }
    }

    @Test
    public void testCalculateTransitiveDependenciesWithExcludedDependency() throws Exception
    {
        DependenciesMigration migration = new DependenciesMigration(mongoProvider);
        ProjectVersion projectVersion = new ProjectVersion("test.group", "test-artifact", "1.0.0");

        StoreProjectVersionData excludedData = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        ProjectVersionData versionData = new ProjectVersionData();
        versionData.setExcluded(true);
        excludedData.setVersionData(versionData);

        Function3<String, String, String, StoreProjectVersionData> excludedProvider = (group, artifact, version) -> excludedData;

        Method method = DependenciesMigration.class.getDeclaredMethod("calculateTransitiveDependencies", ProjectVersion.class, Function3.class);
        method.setAccessible(true);

        VersionDependencyReport result = (VersionDependencyReport) method.invoke(migration, projectVersion, excludedProvider);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isValid());
        Assertions.assertTrue(result.getTransitiveDependencies().isEmpty());
    }
}
