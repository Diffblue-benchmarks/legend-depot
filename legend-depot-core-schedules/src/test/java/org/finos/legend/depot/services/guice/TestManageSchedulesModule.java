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

package org.finos.legend.depot.services.guice;

import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.store.api.admin.schedules.ScheduleInstancesStore;
import org.finos.legend.depot.store.api.admin.schedules.SchedulesStore;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInfo;
import org.finos.legend.depot.store.model.admin.schedules.ScheduleInstance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestManageSchedulesModule
{
    @Test
    public void canGetFactory()
    {
        ManageSchedulesModule module = new ManageSchedulesModule();

        SchedulesFactory factory = module.getFactory(new TestSchedulesStore(), new TestInstancesStore());

        Assertions.assertNotNull(factory);
    }

    @Test
    public void initialiseHouseKeeperReturnsTrue()
    {
        ManageSchedulesModule module = new ManageSchedulesModule();

        boolean result = module.initialiseHouseKeeper();

        Assertions.assertTrue(result);
    }

    private static class TestSchedulesStore implements SchedulesStore
    {
        Map<String, ScheduleInfo> schedules = new HashMap<>();

        @Override
        public Optional<ScheduleInfo> get(String name)
        {
            return schedules.containsKey(name) ? Optional.of(schedules.get(name)) : Optional.empty();
        }

        @Override
        public List<ScheduleInfo> getAll()
        {
            return schedules.values().stream().collect(Collectors.toList());
        }

        @Override
        public ScheduleInfo createOrUpdate(ScheduleInfo scheduleInfo)
        {
            schedules.put(scheduleInfo.name, scheduleInfo);
            return scheduleInfo;
        }

        @Override
        public void delete(String name)
        {
            schedules.remove(name);
        }
    }

    private static class TestInstancesStore implements ScheduleInstancesStore
    {
        List<ScheduleInstance> instances = new ArrayList<>();
        int ids = 1;

        @Override
        public void insert(ScheduleInstance instance)
        {
            instance.setId(String.valueOf(ids));
            ids++;
            instances.add(instance);
        }

        @Override
        public long delete(long l)
        {
            List<ScheduleInstance> toDeletedInstances = instances.stream().filter(i -> i.isExpired()).collect(Collectors.toList());
            instances.removeAll(toDeletedInstances);
            return toDeletedInstances.size();
        }

        @Override
        public List<ScheduleInstance> find(String scheduleName)
        {
            return instances.stream().filter(i -> i.getSchedule().equals(scheduleName)).collect(Collectors.toList());
        }

        @Override
        public List<ScheduleInstance> getAll()
        {
            return instances;
        }
    }
}
