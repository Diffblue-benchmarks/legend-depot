package org.finos.legend.depot.store.mongo.schedules;

import com.mongodb.client.model.IndexModel;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInstance;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestScheduleInstancesMongo extends TestStoreMongo
{
    private ScheduleInstancesMongo store;

    @BeforeEach
    public void setUp()
    {
        store = new ScheduleInstancesMongo(mongoProvider);
    }

    @Test
    public void canCreateInstance()
    {
        assertNotNull(store);
        assertNotNull(store.getAll());
        assertTrue(store.getAll().isEmpty());
    }

    @Test
    public void canInsertAndGetAll()
    {
        Date future = new Date(System.currentTimeMillis() + 100000);
        ScheduleInstance instance1 = new ScheduleInstance("schedule-1", future);
        ScheduleInstance instance2 = new ScheduleInstance("schedule-2", future);

        store.insert(instance1);
        store.insert(instance2);

        List<ScheduleInstance> all = store.getAll();
        assertEquals(2, all.size());
    }

    @Test
    public void canFindByScheduleName()
    {
        Date future = new Date(System.currentTimeMillis() + 100000);
        store.insert(new ScheduleInstance("schedule-1", future));
        store.insert(new ScheduleInstance("schedule-2", future));

        List<ScheduleInstance> found = store.find("schedule-1");
        assertEquals(1, found.size());
        assertEquals("schedule-1", found.get(0).getSchedule());

        List<ScheduleInstance> notFound = store.find("nonexistent");
        assertTrue(notFound.isEmpty());
    }

    @Test
    public void canDeleteByExpiry()
    {
        long now = System.currentTimeMillis();
        store.insert(new ScheduleInstance("expired-schedule", new Date(now - 100000)));
        store.insert(new ScheduleInstance("active-schedule", new Date(now + 100000)));

        assertEquals(2, store.getAll().size());

        long deleted = store.delete(now);
        assertEquals(1, deleted);

        List<ScheduleInstance> remaining = store.getAll();
        assertEquals(1, remaining.size());
        assertEquals("active-schedule", remaining.get(0).getSchedule());
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = ScheduleInstancesMongo.buildIndexes();
        assertNotNull(indexes);
        assertFalse(indexes.isEmpty());
        assertEquals(1, indexes.size());
    }
}
