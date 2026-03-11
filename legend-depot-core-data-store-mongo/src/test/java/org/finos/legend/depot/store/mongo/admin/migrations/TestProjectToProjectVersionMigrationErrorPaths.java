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

import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import org.finos.legend.depot.store.mongo.CoreDataMongoStoreTests;
import org.finos.legend.depot.store.mongo.projects.ProjectsMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsVersionsMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestProjectToProjectVersionMigrationErrorPaths extends CoreDataMongoStoreTests
{
    @Test
    public void testMigrationToProjectVersionsHandlesExceptionGracefully()
    {
        // Insert a project document with "versions" as a String (not a List),
        // causing ClassCastException at getList which is caught by the catch block
        Document invalidProject = new Document()
                .append("groupId", "examples.metadata")
                .append("artifactId", "test-invalid")
                .append("versions", "not-a-list");
        mongoProvider.getCollection(ProjectsMongo.COLLECTION).insertOne(invalidProject);

        ProjectToProjectVersionMigration migration = new ProjectToProjectVersionMigration(mongoProvider);
        // Should not throw - the catch block handles the exception
        migration.migrationToProjectVersions();

        // The exception occurs at getList before any insert, so versions collection stays empty
        Assertions.assertEquals(0, mongoProvider.getCollection(ProjectsVersionsMongo.COLLECTION).countDocuments());
    }

    @Test
    public void testCleanUpProjectDataHandlesExceptionGracefully()
    {
        // Create a unique index on "latestVersion" so that when two documents both
        // have their latestVersion unset (becoming null), the second update violates uniqueness
        mongoProvider.getCollection(ProjectsMongo.COLLECTION)
                .createIndex(new Document("latestVersion", 1), new IndexOptions().unique(true));

        Document project1 = new Document()
                .append("groupId", "examples.metadata")
                .append("artifactId", "test-one")
                .append("latestVersion", "1.0.0");
        Document project2 = new Document()
                .append("groupId", "examples.metadata")
                .append("artifactId", "test-two")
                .append("latestVersion", "2.0.0");
        mongoProvider.getCollection(ProjectsMongo.COLLECTION).insertOne(project1);
        mongoProvider.getCollection(ProjectsMongo.COLLECTION).insertOne(project2);

        ProjectToProjectVersionMigration migration = new ProjectToProjectVersionMigration(mongoProvider);
        // First document's unset succeeds, second fails with duplicate key on null latestVersion
        migration.cleanUpProjectData();

        // Both documents should still exist in the collection
        Assertions.assertEquals(2, mongoProvider.getCollection(ProjectsMongo.COLLECTION).countDocuments());
    }
}
