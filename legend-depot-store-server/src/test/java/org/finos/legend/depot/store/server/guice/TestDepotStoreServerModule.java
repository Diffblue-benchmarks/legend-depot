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

package org.finos.legend.depot.store.server.guice;

import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRefreshPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRetentionPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.configuration.IncludeProjectPropertiesConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryProviderConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryConfiguration;
import org.finos.legend.depot.services.api.notifications.queue.QueueManagerConfiguration;
import org.finos.legend.depot.store.server.configuration.DepotStoreServerConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class TestDepotStoreServerModule
{
    private DepotStoreServerModule module;
    private DepotStoreServerConfiguration configuration;

    @BeforeEach
    public void setUp() throws Exception
    {
        module = new DepotStoreServerModule();
        configuration = new DepotStoreServerConfiguration();
        injectConfiguration(module, configuration);
    }

    private void injectConfiguration(DepotStoreServerModule target, DepotStoreServerConfiguration config) throws Exception
    {
        Class<?> clazz = target.getClass();
        while (clazz != null)
        {
            for (Method m : clazz.getDeclaredMethods())
            {
                if (m.getName().equals("setConfiguration") && m.getParameterCount() == 1)
                {
                    m.setAccessible(true);
                    m.invoke(target, config);
                    return;
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private Object invokePrivateMethod(String methodName) throws Exception
    {
        Method method = DepotStoreServerModule.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(module);
    }

    @Test
    public void testGetQueueManagerConfigurationReturnsDefaultWhenNotSet() throws Exception
    {
        QueueManagerConfiguration result = (QueueManagerConfiguration) invokePrivateMethod("getQueueManagerConfiguration");

        Assertions.assertNotNull(result);
    }

    @Test
    public void testGetQueueManagerConfigurationReturnsExistingWhenSet() throws Exception
    {
        QueueManagerConfiguration queueConfig = new QueueManagerConfiguration();
        configuration.setQueueManagerConfiguration(queueConfig);

        QueueManagerConfiguration result = (QueueManagerConfiguration) invokePrivateMethod("getQueueManagerConfiguration");

        Assertions.assertSame(queueConfig, result);
    }

    @Test
    public void testGetRefreshPolicyConfigurationReturnsSetConfiguration() throws Exception
    {
        ArtifactsRefreshPolicyConfiguration refreshConfig = new ArtifactsRefreshPolicyConfiguration(null, null);
        configuration.setArtifactsRefreshPolicyConfiguration(refreshConfig);

        ArtifactsRefreshPolicyConfiguration result = (ArtifactsRefreshPolicyConfiguration) invokePrivateMethod("getRefreshPolicyConfiguration");

        Assertions.assertSame(refreshConfig, result);
    }

    @Test
    public void testGetIncludePropertiesConfigurationReturnsConfiguredValue() throws Exception
    {
        IncludeProjectPropertiesConfiguration includeConfig = new IncludeProjectPropertiesConfiguration(null, null);
        ArtifactsRefreshPolicyConfiguration refreshConfig = new ArtifactsRefreshPolicyConfiguration(null, includeConfig);
        configuration.setArtifactsRefreshPolicyConfiguration(refreshConfig);

        IncludeProjectPropertiesConfiguration result = (IncludeProjectPropertiesConfiguration) invokePrivateMethod("getIncludePropertiesConfiguration");

        Assertions.assertSame(includeConfig, result);
    }

    @Test
    public void testGetRetentionPolicyConfigurationReturnsSetConfiguration() throws Exception
    {
        ArtifactsRetentionPolicyConfiguration retentionConfig = new ArtifactsRetentionPolicyConfiguration(null, null, null);
        configuration.setRetentionPolicyConfiguration(retentionConfig);

        ArtifactsRetentionPolicyConfiguration result = (ArtifactsRetentionPolicyConfiguration) invokePrivateMethod("getRetentionPolicyConfiguration");

        Assertions.assertSame(retentionConfig, result);
    }

    @Test
    public void testGetArtifactRepositoryConfigurationReturnsVoidWhenNull() throws Exception
    {
        ArtifactRepositoryProviderConfiguration result = (ArtifactRepositoryProviderConfiguration) invokePrivateMethod("getArtifactRepositoryConfiguration");

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(VoidArtifactRepositoryConfiguration.class, result);
    }

    @Test
    public void testGetArtifactRepositoryConfigurationReturnsExistingWhenSet() throws Exception
    {
        VoidArtifactRepositoryConfiguration repoConfig = new VoidArtifactRepositoryConfiguration();
        configuration.setArtifactRepositoryProviderConfiguration(repoConfig);

        ArtifactRepositoryProviderConfiguration result = (ArtifactRepositoryProviderConfiguration) invokePrivateMethod("getArtifactRepositoryConfiguration");

        Assertions.assertSame(repoConfig, result);
    }
}
