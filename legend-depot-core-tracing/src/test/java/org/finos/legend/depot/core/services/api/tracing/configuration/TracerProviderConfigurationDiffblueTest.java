package org.finos.legend.depot.core.services.api.tracing.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TracerProviderConfigurationDiffblueTest {
  /**
   * Test {@link TracerProviderConfiguration#configureObjectMapper(ObjectMapper)}.
   * <p>
   * Method under test: {@link TracerProviderConfiguration#configureObjectMapper(ObjectMapper)}
   */
  @Test
  @DisplayName("Test configureObjectMapper(ObjectMapper)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ObjectMapper TracerProviderConfiguration.configureObjectMapper(ObjectMapper)"})
  void testConfigureObjectMapper() {
    // Arrange and Act
    ObjectMapper actualConfigureObjectMapperResult = TracerProviderConfiguration
        .configureObjectMapper(JsonMapper.builder().findAndAddModules().build());

    // Assert
    assertTrue(
        actualConfigureObjectMapperResult.getDeserializationContext() instanceof DefaultDeserializationContext.Impl);
    assertTrue(actualConfigureObjectMapperResult.getVisibilityChecker() instanceof Std);
    assertTrue(actualConfigureObjectMapperResult instanceof JsonMapper);
    assertTrue(actualConfigureObjectMapperResult.getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(actualConfigureObjectMapperResult.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(actualConfigureObjectMapperResult.getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(actualConfigureObjectMapperResult.getSerializerProvider() instanceof Impl);
    assertTrue(actualConfigureObjectMapperResult.getSerializerProviderInstance() instanceof Impl);
    assertTrue(actualConfigureObjectMapperResult.getDateFormat() instanceof StdDateFormat);
    assertNull(actualConfigureObjectMapperResult.getInjectableValues());
    assertNull(actualConfigureObjectMapperResult.getPropertyNamingStrategy());
    assertEquals(7, actualConfigureObjectMapperResult.getRegisteredModuleIds().size());
  }
}
