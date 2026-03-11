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

package org.finos.legend.depot.server.resources.generations;

import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.finos.legend.depot.services.api.generations.FileGenerationsService;
import org.finos.legend.depot.store.model.generations.StoredFileGeneration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class TestFileGenerationsResource
{
    private FileGenerationsService generationsService;
    private FileGenerationsResource resource;

    @BeforeEach
    public void setUp()
    {
        generationsService = mock(FileGenerationsService.class);
        resource = new FileGenerationsResource(generationsService);
    }

    @Test
    public void canCreateResource()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetFileGenerations()
    {
        List<DepotGeneration> generations = Collections.singletonList(new DepotGeneration("/path/file.txt", "content"));
        when(generationsService.getFileGenerations("group.test", "test", "1.0.0"))
                .thenReturn(generations);

        Response response = resource.getFileGenerations("group.test", "test", "1.0.0", null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        verify(generationsService).getFileGenerations("group.test", "test", "1.0.0");
    }

    @Test
    public void canGetFileGenerationsByElementPath()
    {
        List<DepotGeneration> generations = Collections.singletonList(new DepotGeneration("/path/file.txt", "content"));
        when(generationsService.getFileGenerationsByElementPath("group.test", "test", "1.0.0", "examples::avrogen"))
                .thenReturn(generations);

        Response response = resource.getFileGenerationsByElementPath("group.test", "test", "1.0.0", "examples::avrogen", null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        verify(generationsService).getFileGenerationsByElementPath("group.test", "test", "1.0.0", "examples::avrogen");
    }

    @Test
    public void canGetFileGenerationsByFilePath()
    {
        Optional<DepotGeneration> generation = Optional.of(new DepotGeneration("/path/file.txt", "content"));
        when(generationsService.getFileGenerationsByFilePath("group.test", "test", "1.0.0", "/path/file.txt"))
                .thenReturn(generation);

        Response response = resource.getFileGenerationsByFilePath("group.test", "test", "1.0.0", "/path/file.txt", null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        verify(generationsService).getFileGenerationsByFilePath("group.test", "test", "1.0.0", "/path/file.txt");
    }

    @Test
    public void canGetFileGenerationContentByFilePath()
    {
        when(generationsService.getFileGenerationContentByFilePath("group.test", "test", "1.0.0", "/path/file.txt"))
                .thenReturn(Optional.of("file content"));

        Response response = resource.getFileGenerationContentByFilePath("group.test", "test", "1.0.0", "/path/file.txt", null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        verify(generationsService).getFileGenerationContentByFilePath("group.test", "test", "1.0.0", "/path/file.txt");
    }

    @Test
    public void canGetFileGenerationsByType()
    {
        List<StoredFileGeneration> storedGenerations = Collections.emptyList();
        when(generationsService.findByTypeAndElementPath("group.test", "test", "1.0.0", "avro", "examples::avrogen"))
                .thenReturn(storedGenerations);

        Response response = resource.getFileGenerations("group.test", "test", "1.0.0", "avro", "examples::avrogen", null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        verify(generationsService).findByTypeAndElementPath("group.test", "test", "1.0.0", "avro", "examples::avrogen");
    }
}
