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

package org.finos.legend.depot.store.model.generations;

import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestStoredFileGeneration
{
    private static final String GROUP_ID = "examples.metadata";
    private static final String ARTIFACT_ID = "test-artifact";
    private static final String VERSION_ID = "1.0.0";
    private static final String PATH = "/examples/metadata/test";
    private static final String TYPE = "java";

    @Test
    public void canCreateStoredFileGeneration()
    {
        DepotGeneration depotGeneration = new DepotGeneration("/test/path.java", "some content");
        StoredFileGeneration stored = new StoredFileGeneration(GROUP_ID, ARTIFACT_ID, VERSION_ID, PATH, TYPE, depotGeneration);

        Assertions.assertNotNull(stored);
        Assertions.assertEquals(GROUP_ID, stored.getGroupId());
        Assertions.assertEquals(ARTIFACT_ID, stored.getArtifactId());
        Assertions.assertEquals(VERSION_ID, stored.getVersionId());
        Assertions.assertEquals(PATH, stored.getPath());
        Assertions.assertEquals(TYPE, stored.getType());
        Assertions.assertNotNull(stored.getFile());
        Assertions.assertEquals("some content", stored.getFile().getContent());
    }

    @Test
    public void canGetId()
    {
        DepotGeneration depotGeneration = new DepotGeneration("/test/path.java", "some content");
        StoredFileGeneration stored = new StoredFileGeneration(GROUP_ID, ARTIFACT_ID, VERSION_ID, PATH, TYPE, depotGeneration);

        Assertions.assertEquals("", stored.getId());
    }

    @Test
    public void canTestEquality()
    {
        DepotGeneration depotGeneration = new DepotGeneration("/test/path.java", "some content");
        StoredFileGeneration stored1 = new StoredFileGeneration(GROUP_ID, ARTIFACT_ID, VERSION_ID, PATH, TYPE, depotGeneration);
        StoredFileGeneration stored2 = new StoredFileGeneration(GROUP_ID, ARTIFACT_ID, VERSION_ID, PATH, TYPE, depotGeneration);

        Assertions.assertEquals(stored1, stored2);
        Assertions.assertEquals(stored1.hashCode(), stored2.hashCode());
    }

    @Test
    public void canTestInequality()
    {
        DepotGeneration depotGeneration1 = new DepotGeneration("/test/path.java", "some content");
        DepotGeneration depotGeneration2 = new DepotGeneration("/test/other.java", "other content");
        StoredFileGeneration stored1 = new StoredFileGeneration(GROUP_ID, ARTIFACT_ID, VERSION_ID, PATH, TYPE, depotGeneration1);
        StoredFileGeneration stored2 = new StoredFileGeneration(GROUP_ID, ARTIFACT_ID, VERSION_ID, PATH, "xml", depotGeneration2);

        Assertions.assertNotEquals(stored1, stored2);
    }

    @Test
    public void canCreateWithNullFileGeneration()
    {
        StoredFileGeneration stored = new StoredFileGeneration(GROUP_ID, ARTIFACT_ID, VERSION_ID, PATH, TYPE, null);

        Assertions.assertNotNull(stored);
        Assertions.assertNull(stored.getFile());
        Assertions.assertEquals(PATH, stored.getPath());
        Assertions.assertEquals(TYPE, stored.getType());
    }
}
