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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DepotEntityOverviewTest
{
    @Test
    public void canCreateDepotEntityOverviewWithParameters()
    {
        String groupId = "org.finos.legend";
        String artifactId = "legend-depot";
        String versionId = "1.0.0";
        String path = "test::path::Entity";
        String classifierPath = "meta::pure::metamodel::type::Class";

        DepotEntityOverview entityOverview = new DepotEntityOverview(groupId, artifactId, versionId, path, classifierPath);

        Assertions.assertNotNull(entityOverview);
        Assertions.assertEquals(groupId, entityOverview.getGroupId());
        Assertions.assertEquals(artifactId, entityOverview.getArtifactId());
        Assertions.assertEquals(versionId, entityOverview.getVersionId());
        Assertions.assertEquals(path, entityOverview.getPath());
        Assertions.assertEquals(classifierPath, entityOverview.getClassifierPath());
    }

    @Test
    public void canGetPath()
    {
        String path = "test::path::Entity";
        DepotEntityOverview entityOverview = new DepotEntityOverview("org.finos.legend", "legend-depot", "1.0.0", path, "meta::pure::metamodel::type::Class");

        String retrievedPath = entityOverview.getPath();

        Assertions.assertEquals(path, retrievedPath);
    }

    @Test
    public void canGetClassifierPath()
    {
        String classifierPath = "meta::pure::metamodel::type::Class";
        DepotEntityOverview entityOverview = new DepotEntityOverview("org.finos.legend", "legend-depot", "1.0.0", "test::path::Entity", classifierPath);

        String retrievedClassifierPath = entityOverview.getClassifierPath();

        Assertions.assertEquals(classifierPath, retrievedClassifierPath);
    }

    @Test
    public void testEquals()
    {
        DepotEntityOverview entityOverview1 = new DepotEntityOverview("org.finos.legend", "legend-depot", "1.0.0", "test::path::Entity", "meta::pure::metamodel::type::Class");
        DepotEntityOverview entityOverview2 = new DepotEntityOverview("org.finos.legend", "legend-depot", "1.0.0", "test::path::Entity", "meta::pure::metamodel::type::Class");
        DepotEntityOverview entityOverview3 = new DepotEntityOverview("org.finos.legend", "legend-depot", "2.0.0", "test::path::Entity", "meta::pure::metamodel::type::Class");

        Assertions.assertEquals(entityOverview1, entityOverview2);
        Assertions.assertNotEquals(entityOverview1, entityOverview3);
        Assertions.assertNotEquals(entityOverview1, null);
        Assertions.assertNotEquals(entityOverview1, new Object());
    }

    @Test
    public void testHashCode()
    {
        DepotEntityOverview entityOverview1 = new DepotEntityOverview("org.finos.legend", "legend-depot", "1.0.0", "test::path::Entity", "meta::pure::metamodel::type::Class");
        DepotEntityOverview entityOverview2 = new DepotEntityOverview("org.finos.legend", "legend-depot", "1.0.0", "test::path::Entity", "meta::pure::metamodel::type::Class");
        DepotEntityOverview entityOverview3 = new DepotEntityOverview("org.finos.legend", "legend-depot", "2.0.0", "test::path::Entity", "meta::pure::metamodel::type::Class");

        Assertions.assertEquals(entityOverview1.hashCode(), entityOverview2.hashCode());
        Assertions.assertNotEquals(entityOverview1.hashCode(), entityOverview3.hashCode());
    }
}
