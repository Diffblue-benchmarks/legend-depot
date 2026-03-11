package org.finos.legend.depot.store.mongo.core;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestAbstractMongoConnectionFactory
{
    private static MongoServer server;
    private static String mongoUrl;

    @BeforeAll
    public static void setUp()
    {
        server = new MongoServer(new MemoryBackend());
        mongoUrl = "mongodb://" + server.bind().getHostName() + ":" + server.getLocalAddress().getPort();
    }

    @AfterAll
    public static void tearDown()
    {
        if (server != null)
        {
            server.shutdown();
        }
    }

    private static class TestableConnectionFactory extends AbstractMongoConnectionFactory
    {
        public TestableConnectionFactory(String applicationName, MongoConfiguration mongoConfiguration)
        {
            super(applicationName, mongoConfiguration);
            client = new MongoClient(buildMongoURI());
        }
    }

    @Test
    public void constructorWithValidConfiguration()
    {
        MongoConfiguration config = new MongoConfiguration("test-db", mongoUrl, false);
        TestableConnectionFactory factory = new TestableConnectionFactory("test-app", config);

        Assertions.assertEquals(mongoUrl, factory.getMongoURI());
        Assertions.assertEquals("test-app", factory.getApplicationName());
        Assertions.assertNotNull(factory.getDatabase());
        Assertions.assertNotNull(factory.getClient());
    }

    @Test
    public void constructorWithNullConfigurationThrowsException()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new TestableConnectionFactory("test-app", null));
    }

    @Test
    public void constructorWithNullDatabaseThrowsException()
    {
        MongoConfiguration config = new MongoConfiguration(null, mongoUrl, false);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new TestableConnectionFactory("test-app", config));
    }

    @Test
    public void constructorWithEmptyDatabaseThrowsException()
    {
        MongoConfiguration config = new MongoConfiguration("", mongoUrl, false);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new TestableConnectionFactory("test-app", config));
    }

    @Test
    public void constructorWithNullUrlThrowsException()
    {
        MongoConfiguration config = new MongoConfiguration("test-db", null, false);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new TestableConnectionFactory("test-app", config));
    }

    @Test
    public void constructorWithEmptyUrlThrowsException()
    {
        MongoConfiguration config = new MongoConfiguration("test-db", "", false);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new TestableConnectionFactory("test-app", config));
    }

    @Test
    public void getDatabaseReturnsCorrectDatabase()
    {
        MongoConfiguration config = new MongoConfiguration("test-db", mongoUrl, false);
        TestableConnectionFactory factory = new TestableConnectionFactory("test-app", config);

        MongoDatabase database = factory.getDatabase();
        Assertions.assertNotNull(database);
        Assertions.assertEquals("test-db", database.getName());
    }

    @Test
    public void getClientReturnsNonNullClient()
    {
        MongoConfiguration config = new MongoConfiguration("test-db", mongoUrl, false);
        TestableConnectionFactory factory = new TestableConnectionFactory("test-app", config);

        MongoClient client = factory.getClient();
        Assertions.assertNotNull(client);
    }
}
