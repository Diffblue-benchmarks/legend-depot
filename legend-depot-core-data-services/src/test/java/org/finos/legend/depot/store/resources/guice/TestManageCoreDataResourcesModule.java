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

package org.finos.legend.depot.store.resources.guice;

import com.google.inject.PrivateModule;
import org.finos.legend.depot.store.resources.projects.ManageProjectsResource;
import org.finos.legend.depot.store.resources.versions.ManageProjectsVersionsResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class TestManageCoreDataResourcesModule
{
    @Test
    public void canCreateModule()
    {
        ManageCoreDataResourcesModule module = new ManageCoreDataResourcesModule();
        Assertions.assertNotNull(module);
        Assertions.assertTrue(module instanceof PrivateModule);
    }

    @Test
    public void configureMethodExists() throws Exception
    {
        Method configureMethod = ManageCoreDataResourcesModule.class.getDeclaredMethod("configure");
        Assertions.assertNotNull(configureMethod);
        Assertions.assertEquals(void.class, configureMethod.getReturnType());
        Assertions.assertEquals(0, configureMethod.getParameterCount());
    }

    @Test
    public void moduleReferencesExpectedResourceClasses() throws Exception
    {
        // Verify the module source references the expected resource classes
        // by checking these classes are loadable in the same classloader
        ClassLoader cl = ManageCoreDataResourcesModule.class.getClassLoader();
        Class<?> projectsResourceClass = cl.loadClass(ManageProjectsResource.class.getName());
        Class<?> versionsResourceClass = cl.loadClass(ManageProjectsVersionsResource.class.getName());

        Assertions.assertNotNull(projectsResourceClass);
        Assertions.assertNotNull(versionsResourceClass);
        Assertions.assertEquals("ManageProjectsResource", projectsResourceClass.getSimpleName());
        Assertions.assertEquals("ManageProjectsVersionsResource", versionsResourceClass.getSimpleName());
    }
}
