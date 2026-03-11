package org.finos.legend.depot.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestDepotEntityOverview
{
    @Test
    void canCreateDepotEntityOverview()
    {
        DepotEntityOverview overview = new DepotEntityOverview("org.finos", "artifact1", "1.0.0", "org::test::MyClass", "meta::pure::metamodel::type::Class");

        assertEquals("org::test::MyClass", overview.getPath());
        assertEquals("meta::pure::metamodel::type::Class", overview.getClassifierPath());
        assertEquals("org.finos", overview.getGroupId());
        assertEquals("artifact1", overview.getArtifactId());
        assertEquals("1.0.0", overview.getVersionId());
    }

    @Test
    void canTestEquality()
    {
        DepotEntityOverview overview1 = new DepotEntityOverview("org.finos", "artifact1", "1.0.0", "org::test::MyClass", "meta::pure::metamodel::type::Class");
        DepotEntityOverview overview2 = new DepotEntityOverview("org.finos", "artifact1", "1.0.0", "org::test::MyClass", "meta::pure::metamodel::type::Class");
        DepotEntityOverview overview3 = new DepotEntityOverview("org.finos", "artifact2", "1.0.0", "org::test::MyClass", "meta::pure::metamodel::type::Class");

        assertEquals(overview1, overview2);
        assertNotEquals(overview1, overview3);
    }

    @Test
    void canTestHashCode()
    {
        DepotEntityOverview overview1 = new DepotEntityOverview("org.finos", "artifact1", "1.0.0", "org::test::MyClass", "meta::pure::metamodel::type::Class");
        DepotEntityOverview overview2 = new DepotEntityOverview("org.finos", "artifact1", "1.0.0", "org::test::MyClass", "meta::pure::metamodel::type::Class");

        assertNotNull(overview1.hashCode());
        assertEquals(overview1.hashCode(), overview2.hashCode());
    }
}
