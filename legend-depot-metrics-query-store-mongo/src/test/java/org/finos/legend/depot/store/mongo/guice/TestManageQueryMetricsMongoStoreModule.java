package org.finos.legend.depot.store.mongo.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.mongodb.client.MongoDatabase;
import org.finos.legend.depot.store.api.metrics.query.QueryMetrics;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.admin.MongoAdminStore;
import org.finos.legend.depot.store.mongo.metrics.query.QueryMetricsMongo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestManageQueryMetricsMongoStoreModule extends TestStoreMongo
{
    @Test
    public void canConfigure()
    {
        ManageQueryMetricsMongoStoreModule module = new ManageQueryMetricsMongoStoreModule();

        Injector injector = Guice.createInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(MongoDatabase.class).annotatedWith(Names.named("mongoDatabase")).toInstance(mongoProvider);
                bind(MongoAdminStore.class).toInstance(new MongoAdminStore(mongoProvider));
            }
        }, module);

        QueryMetrics queryMetrics = injector.getInstance(QueryMetrics.class);
        assertNotNull(queryMetrics);
        assertTrue(queryMetrics instanceof QueryMetricsMongo);
    }

    @Test
    public void canRegisterIndexes()
    {
        MongoAdminStore adminStore = new MongoAdminStore(mongoProvider);

        ManageQueryMetricsMongoStoreModule module = new ManageQueryMetricsMongoStoreModule();
        boolean result = module.registerIndexes(adminStore);

        assertTrue(result);
    }
}
