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

package org.finos.legend.depot.store.mongo.schedules;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;
import org.bson.conversions.Bson;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInstance;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class TestScheduleInstancesMongo extends TestStoreMongo
{
    private ScheduleInstancesMongo scheduleInstancesMongo = new ScheduleInstancesMongo(mongoProvider);

    @Test
    public void canCreateScheduleInstancesMongo()
    {
        ScheduleInstancesMongo store = new ScheduleInstancesMongo(mongoProvider);
        Assertions.assertNotNull(store);
    }

    @Test
    public void canGetCollection()
    {
        MongoCollection collection = scheduleInstancesMongo.getCollection();
        Assertions.assertNotNull(collection);
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = ScheduleInstancesMongo.buildIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertEquals(1, indexes.size());
    }

    @Test
    public void canGetKeyFilter()
    {
        ScheduleInstance instance = new ScheduleInstance("test-schedule", new Date());
        Bson filter = scheduleInstancesMongo.getKeyFilter(instance);
        Assertions.assertNotNull(filter);
    }

    @Test
    public void canValidateNewData()
    {
        ScheduleInstance instance = new ScheduleInstance("test-schedule", new Date());
        scheduleInstancesMongo.validateNewData(instance);
    }

    @Test
    public void canGetAll()
    {
        ScheduleInstance instance1 = new ScheduleInstance("schedule1", new Date(System.currentTimeMillis() + 10000));
        ScheduleInstance instance2 = new ScheduleInstance("schedule2", new Date(System.currentTimeMillis() + 20000));

        scheduleInstancesMongo.createOrUpdate(instance1);
        scheduleInstancesMongo.createOrUpdate(instance2);

        List<ScheduleInstance> allInstances = scheduleInstancesMongo.getAll();
        Assertions.assertNotNull(allInstances);
        Assertions.assertEquals(2, allInstances.size());
    }

    @Test
    public void canFindByScheduleName()
    {
        ScheduleInstance instance1 = new ScheduleInstance("schedule1", new Date(System.currentTimeMillis() + 10000));
        ScheduleInstance instance2 = new ScheduleInstance("schedule2", new Date(System.currentTimeMillis() + 20000));
        ScheduleInstance instance3 = new ScheduleInstance("schedule1", new Date(System.currentTimeMillis() + 30000));

        scheduleInstancesMongo.createOrUpdate(instance1);
        scheduleInstancesMongo.createOrUpdate(instance2);
        scheduleInstancesMongo.createOrUpdate(instance3);

        List<ScheduleInstance> foundInstances = scheduleInstancesMongo.find("schedule1");
        Assertions.assertNotNull(foundInstances);
        Assertions.assertEquals(1, foundInstances.size());
        Assertions.assertEquals("schedule1", foundInstances.get(0).getSchedule());
    }

    @Test
    public void canDeleteExpiredInstances()
    {
        long currentTime = System.currentTimeMillis();
        ScheduleInstance expiredInstance = new ScheduleInstance("expired-schedule", new Date(currentTime - 10000));
        ScheduleInstance activeInstance = new ScheduleInstance("active-schedule", new Date(currentTime + 10000));

        scheduleInstancesMongo.createOrUpdate(expiredInstance);
        scheduleInstancesMongo.createOrUpdate(activeInstance);

        List<ScheduleInstance> allBefore = scheduleInstancesMongo.getAll();
        Assertions.assertEquals(2, allBefore.size());

        long deletedCount = scheduleInstancesMongo.delete(currentTime);
        Assertions.assertTrue(deletedCount >= 1);

        List<ScheduleInstance> allAfter = scheduleInstancesMongo.getAll();
        Assertions.assertEquals(1, allAfter.size());
        Assertions.assertEquals("active-schedule", allAfter.get(0).getSchedule());
    }
}
