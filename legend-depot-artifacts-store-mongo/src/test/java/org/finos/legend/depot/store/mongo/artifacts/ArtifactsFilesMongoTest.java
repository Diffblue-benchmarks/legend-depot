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

package org.finos.legend.depot.store.mongo.artifacts;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;
import org.bson.conversions.Bson;
import org.finos.legend.depot.store.model.admin.artifacts.ArtifactFile;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtifactsFilesMongoTest extends TestStoreMongo
{
    @Test
    public void canCreateArtifactsFilesMongo()
    {
        ArtifactsFilesMongo artifactsFilesMongo = new ArtifactsFilesMongo(getMongoDatabase());

        assertNotNull(artifactsFilesMongo);
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = ArtifactsFilesMongo.buildIndexes();

        assertNotNull(indexes);
        assertEquals(1, indexes.size());
        assertEquals("path", indexes.get(0).getOptions().getName());
        assertTrue(indexes.get(0).getOptions().isUnique());
    }

    @Test
    public void canGetCollection()
    {
        ArtifactsFilesMongo artifactsFilesMongo = new ArtifactsFilesMongo(getMongoDatabase());

        MongoCollection collection = artifactsFilesMongo.getCollection();

        assertNotNull(collection);
        assertEquals(ArtifactsFilesMongo.COLLECTION, collection.getNamespace().getCollectionName());
    }

    @Test
    public void canGetKeyFilter()
    {
        ArtifactsFilesMongo artifactsFilesMongo = new ArtifactsFilesMongo(getMongoDatabase());
        ArtifactFile artifactFile = new ArtifactFile("test/path/file.jar", "checksum123");

        Bson filter = artifactsFilesMongo.getKeyFilter(artifactFile);

        assertNotNull(filter);
    }

    @Test
    public void canValidateNewData()
    {
        ArtifactsFilesMongo artifactsFilesMongo = new ArtifactsFilesMongo(getMongoDatabase());
        ArtifactFile artifactFile = new ArtifactFile("test/path/file.jar", "checksum123");

        artifactsFilesMongo.validateNewData(artifactFile);
    }

    @Test
    public void canFindByPath()
    {
        ArtifactsFilesMongo artifactsFilesMongo = new ArtifactsFilesMongo(getMongoDatabase());
        ArtifactFile artifactFile = new ArtifactFile("test/path/file.jar", "checksum123");
        artifactsFilesMongo.createOrUpdate(artifactFile);

        Optional<ArtifactFile> result = artifactsFilesMongo.find("test/path/file.jar");

        assertTrue(result.isPresent());
        assertEquals("test/path/file.jar", result.get().getPath());
        assertEquals("checksum123", result.get().getCheckSum());
    }

    @Test
    public void canFindNonExistentPath()
    {
        ArtifactsFilesMongo artifactsFilesMongo = new ArtifactsFilesMongo(getMongoDatabase());

        Optional<ArtifactFile> result = artifactsFilesMongo.find("non/existent/path.jar");

        assertTrue(result.isEmpty());
    }
}
