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

package org.finos.legend.depot.server.resources.dependencies;

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.server.resources.projects.ProjectsResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDependenciesResource
{
    @Test
    public void testProjectVersionPlatformDependencyConstructor() throws Exception
    {
        Class<?> innerClass = getProjectVersionPlatformDependencyClass();
        Constructor<?> constructor = innerClass.getDeclaredConstructor(String.class, String.class, String.class, ProjectVersion.class, List.class);
        constructor.setAccessible(true);

        ProjectVersion dependency = new ProjectVersion("dep.group", "dep.artifact", "1.0.0");
        List<ProjectsResource.ProjectVersionProperty> platformVersions = new ArrayList<>();
        platformVersions.add(new ProjectsResource.ProjectVersionProperty("platform", "v1", "1.0.0"));

        Object instance = constructor.newInstance("com.example", "test-artifact", "2.0.0", dependency, platformVersions);

        Assertions.assertNotNull(instance);
    }

    @Test
    public void testGetPlatformsVersion() throws Exception
    {
        Class<?> innerClass = getProjectVersionPlatformDependencyClass();
        Constructor<?> constructor = innerClass.getDeclaredConstructor(String.class, String.class, String.class, ProjectVersion.class, List.class);
        constructor.setAccessible(true);

        ProjectVersion dependency = new ProjectVersion("dep.group", "dep.artifact", "1.0.0");
        List<ProjectsResource.ProjectVersionProperty> platformVersions = new ArrayList<>();
        ProjectsResource.ProjectVersionProperty prop = new ProjectsResource.ProjectVersionProperty("platform", "v1", "1.0.0");
        platformVersions.add(prop);

        Object instance = constructor.newInstance("com.example", "test-artifact", "2.0.0", dependency, platformVersions);

        Method getPlatformsVersionMethod = innerClass.getDeclaredMethod("getPlatformsVersion");
        getPlatformsVersionMethod.setAccessible(true);
        List<?> result = (List<?>) getPlatformsVersionMethod.invoke(instance);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(prop, result.get(0));
    }

    @Test
    public void testGetDependency() throws Exception
    {
        Class<?> innerClass = getProjectVersionPlatformDependencyClass();
        Constructor<?> constructor = innerClass.getDeclaredConstructor(String.class, String.class, String.class, ProjectVersion.class, List.class);
        constructor.setAccessible(true);

        ProjectVersion dependency = new ProjectVersion("dep.group", "dep.artifact", "1.0.0");
        List<ProjectsResource.ProjectVersionProperty> platformVersions = new ArrayList<>();

        Object instance = constructor.newInstance("com.example", "test-artifact", "2.0.0", dependency, platformVersions);

        Method getDependencyMethod = innerClass.getDeclaredMethod("getDependency");
        getDependencyMethod.setAccessible(true);
        ProjectVersion result = (ProjectVersion) getDependencyMethod.invoke(instance);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dependency, result);
    }

    @Test
    public void testEquals() throws Exception
    {
        Class<?> innerClass = getProjectVersionPlatformDependencyClass();
        Constructor<?> constructor = innerClass.getDeclaredConstructor(String.class, String.class, String.class, ProjectVersion.class, List.class);
        constructor.setAccessible(true);

        ProjectVersion dependency1 = new ProjectVersion("dep.group", "dep.artifact", "1.0.0");
        List<ProjectsResource.ProjectVersionProperty> platformVersions1 = new ArrayList<>();
        platformVersions1.add(new ProjectsResource.ProjectVersionProperty("platform", "v1", "1.0.0"));

        Object instance1 = constructor.newInstance("com.example", "test-artifact", "2.0.0", dependency1, platformVersions1);
        Object instance2 = constructor.newInstance("com.example", "test-artifact", "2.0.0", dependency1, platformVersions1);

        Method equalsMethod = innerClass.getDeclaredMethod("equals", Object.class);
        equalsMethod.setAccessible(true);
        boolean result = (boolean) equalsMethod.invoke(instance1, instance2);

        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsDifferentObjects() throws Exception
    {
        Class<?> innerClass = getProjectVersionPlatformDependencyClass();
        Constructor<?> constructor = innerClass.getDeclaredConstructor(String.class, String.class, String.class, ProjectVersion.class, List.class);
        constructor.setAccessible(true);

        ProjectVersion dependency1 = new ProjectVersion("dep.group", "dep.artifact", "1.0.0");
        ProjectVersion dependency2 = new ProjectVersion("dep.group", "dep.artifact", "2.0.0");
        List<ProjectsResource.ProjectVersionProperty> platformVersions = new ArrayList<>();

        Object instance1 = constructor.newInstance("com.example", "test-artifact", "2.0.0", dependency1, platformVersions);
        Object instance2 = constructor.newInstance("com.example", "test-artifact", "3.0.0", dependency2, platformVersions);

        Method equalsMethod = innerClass.getDeclaredMethod("equals", Object.class);
        equalsMethod.setAccessible(true);
        boolean result = (boolean) equalsMethod.invoke(instance1, instance2);

        Assertions.assertFalse(result);
    }

    @Test
    public void testHashCode() throws Exception
    {
        Class<?> innerClass = getProjectVersionPlatformDependencyClass();
        Constructor<?> constructor = innerClass.getDeclaredConstructor(String.class, String.class, String.class, ProjectVersion.class, List.class);
        constructor.setAccessible(true);

        ProjectVersion dependency = new ProjectVersion("dep.group", "dep.artifact", "1.0.0");
        List<ProjectsResource.ProjectVersionProperty> platformVersions = new ArrayList<>();
        platformVersions.add(new ProjectsResource.ProjectVersionProperty("platform", "v1", "1.0.0"));

        Object instance = constructor.newInstance("com.example", "test-artifact", "2.0.0", dependency, platformVersions);

        Method hashCodeMethod = innerClass.getDeclaredMethod("hashCode");
        hashCodeMethod.setAccessible(true);
        int result = (int) hashCodeMethod.invoke(instance);

        Assertions.assertNotEquals(0, result);
    }

    @Test
    public void testHashCodeConsistency() throws Exception
    {
        Class<?> innerClass = getProjectVersionPlatformDependencyClass();
        Constructor<?> constructor = innerClass.getDeclaredConstructor(String.class, String.class, String.class, ProjectVersion.class, List.class);
        constructor.setAccessible(true);

        ProjectVersion dependency = new ProjectVersion("dep.group", "dep.artifact", "1.0.0");
        List<ProjectsResource.ProjectVersionProperty> platformVersions = new ArrayList<>();

        Object instance1 = constructor.newInstance("com.example", "test-artifact", "2.0.0", dependency, platformVersions);
        Object instance2 = constructor.newInstance("com.example", "test-artifact", "2.0.0", dependency, platformVersions);

        Method hashCodeMethod = innerClass.getDeclaredMethod("hashCode");
        hashCodeMethod.setAccessible(true);
        int hashCode1 = (int) hashCodeMethod.invoke(instance1);
        int hashCode2 = (int) hashCodeMethod.invoke(instance2);

        Assertions.assertEquals(hashCode1, hashCode2);
    }

    private Class<?> getProjectVersionPlatformDependencyClass() throws ClassNotFoundException
    {
        Class<?>[] innerClasses = DependenciesResource.class.getDeclaredClasses();
        for (Class<?> innerClass : innerClasses)
        {
            if (innerClass.getSimpleName().equals("ProjectVersionPlatformDependency"))
            {
                return innerClass;
            }
        }
        throw new ClassNotFoundException("ProjectVersionPlatformDependency inner class not found");
    }
}
