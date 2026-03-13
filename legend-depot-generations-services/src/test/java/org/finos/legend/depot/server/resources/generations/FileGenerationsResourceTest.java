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

import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FileGenerationsResourceTest
{
    private FileGenerationsService generationsService;
    private FileGenerationsResource resource;
    private Request request;

    @BeforeEach
    public void setUp()
    {
        generationsService = mock(FileGenerationsService.class);
        resource = new FileGenerationsResource(generationsService);
        request = mock(Request.class);
    }

    @Test
    public void canConstructFileGenerationsResource()
    {
        FileGenerationsService service = mock(FileGenerationsService.class);
        FileGenerationsResource testResource = new FileGenerationsResource(service);
        Assertions.assertNotNull(testResource);
    }

    @Test
    public void canGetFileGenerations()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        List<DepotGeneration> expectedGenerations = Arrays.asList(
                new DepotGeneration("/path/to/file1.java", "content1"),
                new DepotGeneration("/path/to/file2.java", "content2")
        );

        when(generationsService.getFileGenerations(groupId, artifactId, versionId)).thenReturn(expectedGenerations);

        Response response = resource.getFileGenerations(groupId, artifactId, versionId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(generationsService).getFileGenerations(groupId, artifactId, versionId);
    }

    @Test
    public void canGetFileGenerationsByElementPath()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String elementPath = "example::test::Element";
        List<DepotGeneration> expectedGenerations = Collections.singletonList(
                new DepotGeneration("/path/to/element.java", "element content")
        );

        when(generationsService.getFileGenerationsByElementPath(groupId, artifactId, versionId, elementPath))
                .thenReturn(expectedGenerations);

        Response response = resource.getFileGenerationsByElementPath(groupId, artifactId, versionId, elementPath, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(generationsService).getFileGenerationsByElementPath(groupId, artifactId, versionId, elementPath);
    }

    @Test
    public void canGetFileGenerationsByFilePath()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String filePath = "/path/to/generated.java";
        DepotGeneration generation = new DepotGeneration(filePath, "file content");
        Optional<DepotGeneration> expectedGeneration = Optional.of(generation);

        when(generationsService.getFileGenerationsByFilePath(groupId, artifactId, versionId, filePath))
                .thenReturn(expectedGeneration);

        Response response = resource.getFileGenerationsByFilePath(groupId, artifactId, versionId, filePath, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(generationsService).getFileGenerationsByFilePath(groupId, artifactId, versionId, filePath);
    }

    @Test
    public void canGetFileGenerationContentByFilePath()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String filePath = "/path/to/generated.java";
        Optional<String> expectedContent = Optional.of("file content");

        when(generationsService.getFileGenerationContentByFilePath(groupId, artifactId, versionId, filePath))
                .thenReturn(expectedContent);

        Response response = resource.getFileGenerationContentByFilePath(groupId, artifactId, versionId, filePath, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(generationsService).getFileGenerationContentByFilePath(groupId, artifactId, versionId, filePath);
    }

    @Test
    public void canGetFileGenerationsByType()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String type = "avro";
        String elementPath = "example::test::Element";
        DepotGeneration generation = new DepotGeneration("/path/to/file.avro", "avro content");
        List<StoredFileGeneration> expectedGenerations = Collections.singletonList(
                new StoredFileGeneration(groupId, artifactId, versionId, elementPath, type, generation)
        );

        when(generationsService.findByTypeAndElementPath(groupId, artifactId, versionId, type, elementPath))
                .thenReturn(expectedGenerations);

        Response response = resource.getFileGenerations(groupId, artifactId, versionId, type, elementPath, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(generationsService).findByTypeAndElementPath(groupId, artifactId, versionId, type, elementPath);
    }

    @Test
    public void canGetEmptyFileGenerations()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";

        when(generationsService.getFileGenerations(groupId, artifactId, versionId)).thenReturn(Collections.emptyList());

        Response response = resource.getFileGenerations(groupId, artifactId, versionId, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        List<DepotGeneration> entityList = (List<DepotGeneration>) response.getEntity();
        Assertions.assertNotNull(entityList);
        Assertions.assertEquals(0, entityList.size());
    }

    @Test
    public void canGetEmptyOptionalForFileGenerationsByFilePath()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String filePath = "/path/to/nonexistent.java";

        when(generationsService.getFileGenerationsByFilePath(groupId, artifactId, versionId, filePath))
                .thenReturn(Optional.empty());

        Response response = resource.getFileGenerationsByFilePath(groupId, artifactId, versionId, filePath, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
