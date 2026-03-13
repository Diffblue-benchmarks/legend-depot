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

package org.finos.legend.depot.store.model.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

public class EntityDefinitionTest
{
    @Test
    public void canCreateEntityDefinitionWithParameters()
    {
        String path = "test::path::Entity";
        String classifierPath = "meta::pure::metamodel::type::Class";
        Map<String, Object> content = new HashMap<>();
        content.put("package", "test::path");
        content.put("name", "Entity");

        EntityDefinition entityDefinition = new EntityDefinition(path, classifierPath, content);

        Assertions.assertNotNull(entityDefinition);
        Assertions.assertEquals(path, entityDefinition.getPath());
        Assertions.assertEquals(classifierPath, entityDefinition.getClassifierPath());
        Assertions.assertEquals(content, entityDefinition.getContent());
    }

    @Test
    public void canGetPath()
    {
        String path = "test::path::Entity";
        EntityDefinition entityDefinition = new EntityDefinition(path, "meta::pure::metamodel::type::Class", new HashMap<>());

        String retrievedPath = entityDefinition.getPath();

        Assertions.assertEquals(path, retrievedPath);
    }

    @Test
    public void canGetClassifierPath()
    {
        String classifierPath = "meta::pure::metamodel::type::Class";
        EntityDefinition entityDefinition = new EntityDefinition("test::path::Entity", classifierPath, new HashMap<>());

        String retrievedClassifierPath = entityDefinition.getClassifierPath();

        Assertions.assertEquals(classifierPath, retrievedClassifierPath);
    }

    @Test
    public void canSetClassifierPath()
    {
        String originalClassifierPath = "meta::pure::metamodel::type::Class";
        String newClassifierPath = "meta::pure::metamodel::type::Enumeration";
        EntityDefinition entityDefinition = new EntityDefinition("test::path::Entity", originalClassifierPath, new HashMap<>());

        entityDefinition.setClassifierPath(newClassifierPath);

        Assertions.assertEquals(newClassifierPath, entityDefinition.getClassifierPath());
    }

    @Test
    public void canGetContent()
    {
        Map<String, Object> content = new HashMap<>();
        content.put("package", "test::path");
        content.put("name", "Entity");
        EntityDefinition entityDefinition = new EntityDefinition("test::path::Entity", "meta::pure::metamodel::type::Class", content);

        Map<String, ?> retrievedContent = entityDefinition.getContent();

        Assertions.assertEquals(content, retrievedContent);
    }

    @Test
    public void testEquals()
    {
        Map<String, Object> content1 = new HashMap<>();
        content1.put("package", "test::path");
        Map<String, Object> content2 = new HashMap<>();
        content2.put("package", "test::path");
        Map<String, Object> content3 = new HashMap<>();
        content3.put("package", "different::path");

        EntityDefinition entityDefinition1 = new EntityDefinition("test::path::Entity", "meta::pure::metamodel::type::Class", content1);
        EntityDefinition entityDefinition2 = new EntityDefinition("test::path::Entity", "meta::pure::metamodel::type::Class", content2);
        EntityDefinition entityDefinition3 = new EntityDefinition("different::path::Entity", "meta::pure::metamodel::type::Class", content3);

        Assertions.assertEquals(entityDefinition1, entityDefinition2);
        Assertions.assertNotEquals(entityDefinition1, entityDefinition3);
        Assertions.assertNotEquals(entityDefinition1, null);
        Assertions.assertNotEquals(entityDefinition1, new Object());
    }

    @Test
    public void testHashCode()
    {
        Map<String, Object> content1 = new HashMap<>();
        content1.put("package", "test::path");
        Map<String, Object> content2 = new HashMap<>();
        content2.put("package", "test::path");
        Map<String, Object> content3 = new HashMap<>();
        content3.put("package", "different::path");

        EntityDefinition entityDefinition1 = new EntityDefinition("test::path::Entity", "meta::pure::metamodel::type::Class", content1);
        EntityDefinition entityDefinition2 = new EntityDefinition("test::path::Entity", "meta::pure::metamodel::type::Class", content2);
        EntityDefinition entityDefinition3 = new EntityDefinition("different::path::Entity", "meta::pure::metamodel::type::Class", content3);

        Assertions.assertEquals(entityDefinition1.hashCode(), entityDefinition2.hashCode());
        Assertions.assertNotEquals(entityDefinition1.hashCode(), entityDefinition3.hashCode());
    }
}
