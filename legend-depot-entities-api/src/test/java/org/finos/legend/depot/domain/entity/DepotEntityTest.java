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

package org.finos.legend.depot.domain.entity;

import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class DepotEntityTest
{
    @Test
    public void testDefaultConstructor()
    {
        DepotEntity depotEntity = new DepotEntity();
        Assertions.assertNotNull(depotEntity);
        Assertions.assertNull(depotEntity.getEntity());
    }

    @Test
    public void testConstructorWithThreeParameters()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";

        DepotEntity depotEntity = new DepotEntity(groupId, artifactId, versionId);

        Assertions.assertNotNull(depotEntity);
        Assertions.assertEquals(groupId, depotEntity.getGroupId());
        Assertions.assertEquals(artifactId, depotEntity.getArtifactId());
        Assertions.assertEquals(versionId, depotEntity.getVersionId());
    }

    @Test
    public void testConstructorWithFourParameters()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        Entity entity = new TestEntity("test.path", "TestClassifier");

        DepotEntity depotEntity = new DepotEntity(groupId, artifactId, versionId, entity);

        Assertions.assertNotNull(depotEntity);
        Assertions.assertEquals(groupId, depotEntity.getGroupId());
        Assertions.assertEquals(artifactId, depotEntity.getArtifactId());
        Assertions.assertEquals(versionId, depotEntity.getVersionId());
        Assertions.assertEquals(entity, depotEntity.getEntity());
        Assertions.assertFalse(depotEntity.isVersionedEntity());
    }

    @Test
    public void testIsVersionedEntity()
    {
        DepotEntity depotEntity = new DepotEntity();
        Assertions.assertFalse(depotEntity.isVersionedEntity());
    }

    @Test
    public void testGetEntity()
    {
        Entity entity = new TestEntity("test.path", "TestClassifier");
        DepotEntity depotEntity = new DepotEntity("org.example", "test-artifact", "1.0.0", entity);

        Entity result = depotEntity.getEntity();

        Assertions.assertEquals(entity, result);
    }

    @Test
    public void testGetEntityWhenNull()
    {
        DepotEntity depotEntity = new DepotEntity("org.example", "test-artifact", "1.0.0");

        Entity result = depotEntity.getEntity();

        Assertions.assertNull(result);
    }

    @Test
    public void testEquals()
    {
        Entity entity = new TestEntity("test.path", "TestClassifier");
        DepotEntity depotEntity1 = new DepotEntity("org.example", "test-artifact", "1.0.0", entity);
        DepotEntity depotEntity2 = new DepotEntity("org.example", "test-artifact", "1.0.0", entity);

        Assertions.assertTrue(depotEntity1.equals(depotEntity2));
        Assertions.assertTrue(depotEntity2.equals(depotEntity1));
    }

    @Test
    public void testEqualsSameObject()
    {
        DepotEntity depotEntity = new DepotEntity("org.example", "test-artifact", "1.0.0");

        Assertions.assertTrue(depotEntity.equals(depotEntity));
    }

    @Test
    public void testNotEquals()
    {
        Entity entity1 = new TestEntity("test.path1", "TestClassifier");
        Entity entity2 = new TestEntity("test.path2", "TestClassifier");
        DepotEntity depotEntity1 = new DepotEntity("org.example", "test-artifact", "1.0.0", entity1);
        DepotEntity depotEntity2 = new DepotEntity("org.example", "test-artifact", "1.0.0", entity2);

        Assertions.assertFalse(depotEntity1.equals(depotEntity2));
    }

    @Test
    public void testEqualsWithNull()
    {
        DepotEntity depotEntity = new DepotEntity("org.example", "test-artifact", "1.0.0");

        Assertions.assertFalse(depotEntity.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass()
    {
        DepotEntity depotEntity = new DepotEntity("org.example", "test-artifact", "1.0.0");

        Assertions.assertFalse(depotEntity.equals("not a DepotEntity"));
    }

    @Test
    public void testHashCode()
    {
        Entity entity = new TestEntity("test.path", "TestClassifier");
        DepotEntity depotEntity1 = new DepotEntity("org.example", "test-artifact", "1.0.0", entity);
        DepotEntity depotEntity2 = new DepotEntity("org.example", "test-artifact", "1.0.0", entity);

        int hashCode1 = depotEntity1.hashCode();
        int hashCode2 = depotEntity2.hashCode();

        Assertions.assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testHashCodeDifferentObjects()
    {
        Entity entity1 = new TestEntity("test.path1", "TestClassifier");
        Entity entity2 = new TestEntity("test.path2", "TestClassifier");
        DepotEntity depotEntity1 = new DepotEntity("org.example", "test-artifact", "1.0.0", entity1);
        DepotEntity depotEntity2 = new DepotEntity("org.example", "test-artifact", "1.0.0", entity2);

        int hashCode1 = depotEntity1.hashCode();
        int hashCode2 = depotEntity2.hashCode();

        Assertions.assertNotEquals(hashCode1, hashCode2);
    }

    private static class TestEntity implements Entity
    {
        private final String path;
        private final String classifierPath;

        public TestEntity(String path, String classifierPath)
        {
            this.path = path;
            this.classifierPath = classifierPath;
        }

        @Override
        public String getPath()
        {
            return path;
        }

        @Override
        public String getClassifierPath()
        {
            return classifierPath;
        }

        @Override
        public Map<String, ?> getContent()
        {
            return null;
        }
    }
}
