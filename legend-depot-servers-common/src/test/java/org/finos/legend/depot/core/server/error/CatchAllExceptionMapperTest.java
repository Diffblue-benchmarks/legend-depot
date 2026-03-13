//  Copyright 2023 Goldman Sachs
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

package org.finos.legend.depot.core.server.error;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatchAllExceptionMapperTest
{
    @Test
    public void canConstructMapperWithStackTrace() throws Exception
    {
        // Arrange & Act
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(true);

        // Assert
        assertNotNull(mapper);
        Field field = BaseExceptionMapper.class.getDeclaredField("includeStackTrace");
        field.setAccessible(true);
        assertTrue((Boolean) field.get(mapper));
    }

    @Test
    public void canConstructMapperWithoutStackTrace() throws Exception
    {
        // Arrange & Act
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper(false);

        // Assert
        assertNotNull(mapper);
        Field field = BaseExceptionMapper.class.getDeclaredField("includeStackTrace");
        field.setAccessible(true);
        assertFalse((Boolean) field.get(mapper));
    }

    @Test
    public void canConstructMapperWithDefaultConstructor() throws Exception
    {
        // Arrange & Act
        CatchAllExceptionMapper mapper = new CatchAllExceptionMapper();

        // Assert
        assertNotNull(mapper);
        Field field = BaseExceptionMapper.class.getDeclaredField("includeStackTrace");
        field.setAccessible(true);
        assertFalse((Boolean) field.get(mapper));
    }
}

