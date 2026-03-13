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
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FileGenerationsServiceImplTest
{
    private FileGenerations mockFileGenerations;
    private ProjectsService mockProjects;
    private FileGenerationsServiceImpl service;

    @BeforeEach
    public void setup()
    {
        mockFileGenerations = Mockito.mock(FileGenerations.class);
        mockProjects = Mockito.mock(ProjectsService.class);
        service = new FileGenerationsServiceImpl(mockFileGenerations, mockProjects);
    }

    @Test
    public void testGetFileGenerations()
    {
        DepotGeneration gen = new DepotGeneration("/output/file.txt", "content");
        StoredFileGeneration stored = new StoredFileGeneration("org.finos", "legend-depot", "1.0.0", "/path", "avro", gen);
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "latest")).thenReturn("1.0.0");
        when(mockFileGenerations.find("org.finos", "legend-depot", "1.0.0")).thenReturn(Arrays.asList(stored));

        List<DepotGeneration> result = service.getFileGenerations("org.finos", "legend-depot", "latest");

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("content", result.get(0).getContent());
        verify(mockProjects).resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "latest");
    }

    @Test
    public void testGetFileGenerationsEmpty()
    {
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockFileGenerations.find("org.finos", "legend-depot", "1.0.0")).thenReturn(Collections.emptyList());

        List<DepotGeneration> result = service.getFileGenerations("org.finos", "legend-depot", "1.0.0");

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetFileGenerationsByElementPath()
    {
        DepotGeneration gen = new DepotGeneration("/output/file.avro", "avro content");
        StoredFileGeneration stored = new StoredFileGeneration("org.finos", "legend-depot", "1.0.0", "model::MyAvro", "avro", gen);
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockFileGenerations.findByElementPath("org.finos", "legend-depot", "1.0.0", "model::MyAvro")).thenReturn(Arrays.asList(stored));

        List<DepotGeneration> result = service.getFileGenerationsByElementPath("org.finos", "legend-depot", "1.0.0", "model::MyAvro");

        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testGetFileGenerationsByFilePath()
    {
        DepotGeneration gen = new DepotGeneration("/output/file.avro", "avro content");
        StoredFileGeneration stored = new StoredFileGeneration("org.finos", "legend-depot", "1.0.0", "/path", "avro", gen);
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockFileGenerations.findByFilePath("org.finos", "legend-depot", "1.0.0", "/output/file.avro")).thenReturn(Optional.of(stored));

        Optional<DepotGeneration> result = service.getFileGenerationsByFilePath("org.finos", "legend-depot", "1.0.0", "/output/file.avro");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("avro content", result.get().getContent());
    }

    @Test
    public void testGetFileGenerationsByFilePathNotFound()
    {
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockFileGenerations.findByFilePath("org.finos", "legend-depot", "1.0.0", "/missing")).thenReturn(Optional.empty());

        Optional<DepotGeneration> result = service.getFileGenerationsByFilePath("org.finos", "legend-depot", "1.0.0", "/missing");

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testFindByTypeAndElementPathWithNullElementPath()
    {
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockFileGenerations.findByType("org.finos", "legend-depot", "1.0.0", "avro")).thenReturn(Collections.emptyList());

        List<StoredFileGeneration> result = service.findByTypeAndElementPath("org.finos", "legend-depot", "1.0.0", "avro", null);

        Assertions.assertTrue(result.isEmpty());
        verify(mockFileGenerations).findByType("org.finos", "legend-depot", "1.0.0", "avro");
    }

    @Test
    public void testFindByTypeAndElementPathWithElementPath()
    {
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockFileGenerations.findByTypeAndElementPath("org.finos", "legend-depot", "1.0.0", "avro", "model::MyAvro")).thenReturn(Collections.emptyList());

        List<StoredFileGeneration> result = service.findByTypeAndElementPath("org.finos", "legend-depot", "1.0.0", "avro", "model::MyAvro");

        verify(mockFileGenerations).findByTypeAndElementPath("org.finos", "legend-depot", "1.0.0", "avro", "model::MyAvro");
    }
}
