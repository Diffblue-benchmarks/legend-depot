//  Copyright 2022 Goldman Sachs
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

package org.finos.legend.depot.services.pure.model.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.finos.legend.depot.services.TestBaseServices;
import org.finos.legend.depot.services.api.entities.EntitiesService;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.api.pure.model.context.PureModelContextService;
import org.finos.legend.depot.services.entities.EntitiesServiceImpl;
import org.finos.legend.depot.services.metrics.query.InMemoryQueryMetricsRegistry;
import org.finos.legend.depot.services.projects.ProjectsServiceImpl;
import org.finos.legend.depot.store.api.entities.UpdateEntities;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.mongo.entities.EntitiesMongo;
import org.finos.legend.depot.store.mongo.entities.test.EntitiesMongoTestUtils;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.finos.legend.engine.shared.core.ObjectMapperFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.mockito.Mockito.mock;

public class TestEntityPackageableElementSerialization extends TestBaseServices
{
    public static final URL projects = TestPureModelContextService.class.getClassLoader().getResource("allProjectVersions.json");
    protected static final URL versionedEntities = TestPureModelContextService.class.getClassLoader().getResource("data/versioned-entities.json");
    public static final String TEST_GROUP_ID = "examples.metadata";
    public static final String CLIENT_VERSION = "vX_X_X";
    private final QueryMetricsRegistry metricsRegistry = new InMemoryQueryMetricsRegistry();
    private final Queue queue = mock(Queue.class);
    ProjectsService projectsService = new ProjectsServiceImpl(projectsVersionsStore, projectsStore, metricsRegistry, queue, new ProjectsConfiguration("master"));
    protected UpdateEntities<?> entitiesStore = new EntitiesMongo<>(mongoProvider);
    private final EntitiesMongoTestUtils entityUtils = new EntitiesMongoTestUtils(mongoProvider);
    private final PureModelContextService service = new PureModelContextServiceImpl(new EntitiesServiceImpl<>(entitiesStore, projectsService), projectsService);
    private final ObjectMapper objectMapper = ObjectMapperFactory.getNewStandardObjectMapperWithPureProtocolExtensionSupports().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @BeforeEach
    public void setupMetadata()
    {
        projectsStore.createOrUpdate(new StoreProjectData("PROD-1", "test.legend", "blank-prod", null, "2.0.0"));
        setUpProjectsVersionsFromFile(projects);
        entityUtils.loadEntities(versionedEntities);
    }

    @Test
    public void canSerializeEntityPackageableElementWithRawConverter() throws JsonProcessingException
    {
        PureModelContextData contextData = service.getPureModelContextData(TEST_GROUP_ID, "test", "2.2.0", CLIENT_VERSION, false, false);
        Assertions.assertNotNull(contextData);
        Assertions.assertFalse(contextData.getElements().isEmpty());

        String serialized = objectMapper.writeValueAsString(contextData);
        Assertions.assertNotNull(serialized);
        Assertions.assertTrue(serialized.contains("\"_type\":\"data\""));
        Assertions.assertTrue(serialized.contains("\"elements\""));
    }

    @Test
    public void canSerializeEntityPackageableElementAndDeserialize() throws JsonProcessingException
    {
        PureModelContextData contextData = service.getPureModelContextData(TEST_GROUP_ID, "test", "2.2.0", CLIENT_VERSION, false, false);
        Assertions.assertNotNull(contextData);

        String serialized = objectMapper.writeValueAsString(contextData);
        PureModelContextData deserialized = objectMapper.readValue(serialized, PureModelContextData.class);

        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals(contextData.getElements().size(), deserialized.getElements().size());
    }

    @Test
    public void canSerializeMultipleEntityPackageableElements() throws JsonProcessingException
    {
        PureModelContextData contextData = service.getPureModelContextData(TEST_GROUP_ID, "test", "2.2.0", CLIENT_VERSION, false, false);
        Assertions.assertNotNull(contextData);
        Assertions.assertTrue(contextData.getElements().size() > 1);

        String serialized = objectMapper.writeValueAsString(contextData);
        Assertions.assertNotNull(serialized);

        PureModelContextData deserialized = objectMapper.readValue(serialized, PureModelContextData.class);
        Assertions.assertEquals(contextData.getElements().size(), deserialized.getElements().size());
    }

    @Test
    public void canSerializeEntityPackageableElementWithBasicMapper() throws JsonProcessingException
    {
        ObjectMapper basicMapper = new ObjectMapper();
        PureModelContextData contextData = service.getPureModelContextData(TEST_GROUP_ID, "test", "2.2.0", CLIENT_VERSION, false, false);
        Assertions.assertNotNull(contextData);
        Assertions.assertFalse(contextData.getElements().isEmpty());

        String serialized = basicMapper.writeValueAsString(contextData.getElements());
        Assertions.assertNotNull(serialized);
        Assertions.assertTrue(serialized.contains("{"));
    }
}
