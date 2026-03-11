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

package org.finos.legend.depot.core.server.filters;

import ch.qos.logback.access.spi.IAccessEvent;
import ch.qos.logback.access.spi.ServerAdapter;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestHealthcheckFilterFactory
{
    private final HealthcheckFilterFactory factory = new HealthcheckFilterFactory();

    @Test
    void canBuildFilter()
    {
        Filter<IAccessEvent> filter = factory.build();
        assertNotNull(filter);
    }

    @Test
    void canReturnNeutralForNonHealthcheckUri()
    {
        Filter<IAccessEvent> filter = factory.build();
        IAccessEvent event = createEvent("/api/projects");

        FilterReply reply = filter.decide(event);

        assertEquals(FilterReply.NEUTRAL, reply);
    }

    @Test
    void canReturnNeutralForHealthcheckUriWhenCountIsLow()
    {
        Filter<IAccessEvent> filter = factory.build();
        IAccessEvent event = createEvent("/admin/healthcheck");

        FilterReply reply = filter.decide(event);

        assertEquals(FilterReply.NEUTRAL, reply);
    }

    @Test
    void canDenyHealthcheckUriAfterCountExceedsTen()
    {
        Filter<IAccessEvent> filter = factory.build();
        IAccessEvent healthcheckEvent = createEvent("/admin/healthcheck");

        for (int i = 0; i < 11; i++)
        {
            assertEquals(FilterReply.NEUTRAL, filter.decide(healthcheckEvent));
        }

        assertEquals(FilterReply.DENY, filter.decide(healthcheckEvent));
    }

    @Test
    void canReturnNeutralForNonHealthcheckUriEvenAfterCountExceedsTen()
    {
        Filter<IAccessEvent> filter = factory.build();
        IAccessEvent healthcheckEvent = createEvent("/admin/healthcheck");
        IAccessEvent otherEvent = createEvent("/api/projects");

        for (int i = 0; i < 11; i++)
        {
            filter.decide(healthcheckEvent);
        }

        assertEquals(FilterReply.NEUTRAL, filter.decide(otherEvent));
    }

    @Test
    void canCountNonHealthcheckRequestsTowardThreshold()
    {
        Filter<IAccessEvent> filter = factory.build();
        IAccessEvent otherEvent = createEvent("/api/projects");
        IAccessEvent healthcheckEvent = createEvent("/admin/healthcheck");

        for (int i = 0; i < 11; i++)
        {
            filter.decide(otherEvent);
        }

        assertEquals(FilterReply.DENY, filter.decide(healthcheckEvent));
    }

    private static IAccessEvent createEvent(String requestUri)
    {
        return new IAccessEvent()
        {
            @Override
            public String getRequestURI()
            {
                return requestUri;
            }

            @Override
            public HttpServletRequest getRequest() { return null; }
            @Override
            public HttpServletResponse getResponse() { return null; }
            @Override
            public long getTimeStamp() { return 0; }
            @Override
            public long getElapsedTime() { return 0; }
            @Override
            public long getElapsedSeconds() { return 0; }
            @Override
            public String getRequestURL() { return null; }
            @Override
            public String getRemoteHost() { return null; }
            @Override
            public String getRemoteUser() { return null; }
            @Override
            public String getProtocol() { return null; }
            @Override
            public String getMethod() { return null; }
            @Override
            public String getServerName() { return null; }
            @Override
            public String getSessionID() { return null; }
            @Override
            public void setThreadName(String name) { }
            @Override
            public String getThreadName() { return null; }
            @Override
            public String getQueryString() { return null; }
            @Override
            public String getRemoteAddr() { return null; }
            @Override
            public String getRequestHeader(String name) { return null; }
            @Override
            public Enumeration<String> getRequestHeaderNames() { return null; }
            @Override
            public Map<String, String> getRequestHeaderMap() { return null; }
            @Override
            public Map<String, String[]> getRequestParameterMap() { return null; }
            @Override
            public String getAttribute(String name) { return null; }
            @Override
            public String[] getRequestParameter(String name) { return null; }
            @Override
            public String getCookie(String name) { return null; }
            @Override
            public long getContentLength() { return 0; }
            @Override
            public int getStatusCode() { return 0; }
            @Override
            public String getRequestContent() { return null; }
            @Override
            public String getResponseContent() { return null; }
            @Override
            public int getLocalPort() { return 0; }
            @Override
            public ServerAdapter getServerAdapter() { return null; }
            @Override
            public String getResponseHeader(String name) { return null; }
            @Override
            public Map<String, String> getResponseHeaderMap() { return null; }
            @Override
            public List<String> getResponseHeaderNameList() { return null; }
            @Override
            public void prepareForDeferredProcessing() { }
        };
    }
}
