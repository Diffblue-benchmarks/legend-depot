package org.finos.legend.depot.services.api.metrics.query;

import org.finos.legend.depot.store.model.metrics.query.VersionQueryMetric;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TestVoidQueryMetricsRegistry
{
    private final VoidQueryMetricsRegistry registry = new VoidQueryMetricsRegistry();

    @Test
    void canRecordWithoutError()
    {
        registry.record("org.finos.legend", "my-artifact", "1.0.0", new Date());
    }

    @Test
    void canFindFirstReturnsEmpty()
    {
        Optional<VersionQueryMetric> result = registry.findFirst();

        assertFalse(result.isPresent());
    }
}
