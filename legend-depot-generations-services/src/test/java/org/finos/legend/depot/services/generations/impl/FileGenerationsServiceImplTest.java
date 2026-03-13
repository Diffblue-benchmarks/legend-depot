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

package org.finos.legend.depot.services.generations.impl;

import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.store.api.generations.FileGenerations;
import org.finos.legend.depot.store.model.generations.StoredFileGeneration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FileGenerationsServiceImplTest
{
    private FileGenerations fileGenerations;
    private ProjectsService projectsService;
    private FileGenerationsServiceImpl service;

    @BeforeEach
    public void setup()
    {
        fileGenerations = mock(FileGenerations.class);
        projectsService = mock(ProjectsService.class);
        service = new FileGenerationsServiceImpl(fileGenerations, projectsService);
    }

    @Test
    public void canConstructService()
    {
        Assertions.assertNotNull(service);
    }

    @Test
    public void canGetFileGenerations()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String resolvedVersion = "1.0.0";

        DepotGeneration generation = new DepotGeneration("/path/to/file.txt", "content");
        StoredFileGeneration stored = new StoredFileGeneration(groupId, artifactId, resolvedVersion, "element", "type", generation);
        List<StoredFileGeneration> storedList = Arrays.asList(stored);

        when(projectsService.resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId)).thenReturn(resolvedVersion);
        when(fileGenerations.find(groupId, artifactId, resolvedVersion)).thenReturn(storedList);

        List<DepotGeneration> result = service.getFileGenerations(groupId, artifactId, versionId);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("/path/to/file.txt", result.get(0).getPath());
        Assertions.assertEquals("content", result.get(0).getContent());
        verify(projectsService).resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId);
        verify(fileGenerations).find(groupId, artifactId, resolvedVersion);
    }

    @Test
    public void canGetFileGenerationsByElementPath()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String resolvedVersion = "1.0.0";
        String elementPath = "example::element::path";

        DepotGeneration generation = new DepotGeneration("/path/to/file.txt", "content");
        StoredFileGeneration stored = new StoredFileGeneration(groupId, artifactId, resolvedVersion, elementPath, "type", generation);
        List<StoredFileGeneration> storedList = Arrays.asList(stored);

        when(projectsService.resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId)).thenReturn(resolvedVersion);
        when(fileGenerations.findByElementPath(groupId, artifactId, resolvedVersion, elementPath)).thenReturn(storedList);

        List<DepotGeneration> result = service.getFileGenerationsByElementPath(groupId, artifactId, versionId, elementPath);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("/path/to/file.txt", result.get(0).getPath());
        Assertions.assertEquals("content", result.get(0).getContent());
        verify(projectsService).resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId);
        verify(fileGenerations).findByElementPath(groupId, artifactId, resolvedVersion, elementPath);
    }

    @Test
    public void canGetFileGenerationsByFilePath()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String resolvedVersion = "1.0.0";
        String filePath = "/path/to/file.txt";

        DepotGeneration generation = new DepotGeneration(filePath, "content");
        StoredFileGeneration stored = new StoredFileGeneration(groupId, artifactId, resolvedVersion, "element", "type", generation);

        when(projectsService.resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId)).thenReturn(resolvedVersion);
        when(fileGenerations.findByFilePath(groupId, artifactId, resolvedVersion, filePath)).thenReturn(Optional.of(stored));

        Optional<DepotGeneration> result = service.getFileGenerationsByFilePath(groupId, artifactId, versionId, filePath);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(filePath, result.get().getPath());
        Assertions.assertEquals("content", result.get().getContent());
        verify(projectsService).resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId);
        verify(fileGenerations).findByFilePath(groupId, artifactId, resolvedVersion, filePath);
    }

    @Test
    public void canGetFileGenerationsByFilePathWhenNotFound()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String resolvedVersion = "1.0.0";
        String filePath = "/path/to/missing.txt";

        when(projectsService.resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId)).thenReturn(resolvedVersion);
        when(fileGenerations.findByFilePath(groupId, artifactId, resolvedVersion, filePath)).thenReturn(Optional.empty());

        Optional<DepotGeneration> result = service.getFileGenerationsByFilePath(groupId, artifactId, versionId, filePath);

        Assertions.assertFalse(result.isPresent());
        verify(projectsService).resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId);
        verify(fileGenerations).findByFilePath(groupId, artifactId, resolvedVersion, filePath);
    }

    @Test
    public void canFindByTypeAndElementPath()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String resolvedVersion = "1.0.0";
        String type = "avro";
        String elementPath = "example::element::path";

        DepotGeneration generation = new DepotGeneration("/path/to/file.avro", "content");
        StoredFileGeneration stored = new StoredFileGeneration(groupId, artifactId, resolvedVersion, elementPath, type, generation);
        List<StoredFileGeneration> storedList = Arrays.asList(stored);

        when(projectsService.resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId)).thenReturn(resolvedVersion);
        when(fileGenerations.findByTypeAndElementPath(groupId, artifactId, resolvedVersion, type, elementPath)).thenReturn(storedList);

        List<StoredFileGeneration> result = service.findByTypeAndElementPath(groupId, artifactId, versionId, type, elementPath);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("/path/to/file.avro", result.get(0).getFile().getPath());
        verify(projectsService).resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId);
        verify(fileGenerations).findByTypeAndElementPath(groupId, artifactId, resolvedVersion, type, elementPath);
    }

    @Test
    public void canFindByTypeWhenElementPathIsNull()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String resolvedVersion = "1.0.0";
        String type = "avro";

        DepotGeneration generation = new DepotGeneration("/path/to/file.avro", "content");
        StoredFileGeneration stored = new StoredFileGeneration(groupId, artifactId, resolvedVersion, "element", type, generation);
        List<StoredFileGeneration> storedList = Arrays.asList(stored);

        when(projectsService.resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId)).thenReturn(resolvedVersion);
        when(fileGenerations.findByType(groupId, artifactId, resolvedVersion, type)).thenReturn(storedList);

        List<StoredFileGeneration> result = service.findByTypeAndElementPath(groupId, artifactId, versionId, type, null);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("/path/to/file.avro", result.get(0).getFile().getPath());
        verify(projectsService).resolveAliasesAndCheckVersionExists(groupId, artifactId, versionId);
        verify(fileGenerations).findByType(groupId, artifactId, resolvedVersion, type);
    }
}
