package org.finos.legend.depot.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestStorageConfiguration
{
    private static class TestableStorageConfiguration extends StorageConfiguration
    {
        TestableStorageConfiguration()
        {
            super();
        }
    }

    @Test
    public void canInstantiateSubclass()
    {
        TestableStorageConfiguration config = new TestableStorageConfiguration();
        Assertions.assertNotNull(config);
    }

    @Test
    public void canConfigureObjectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper result = StorageConfiguration.configureObjectMapper(mapper);

        Assertions.assertNotNull(result);
        Assertions.assertSame(mapper, result);
    }
}
