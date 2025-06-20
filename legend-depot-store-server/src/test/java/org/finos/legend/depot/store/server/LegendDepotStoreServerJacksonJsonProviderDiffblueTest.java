package org.finos.legend.depot.store.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class LegendDepotStoreServerJacksonJsonProviderDiffblueTest {
  /**
   * Test new {@link LegendDepotStoreServerJacksonJsonProvider} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link LegendDepotStoreServerJacksonJsonProvider}
   */
  @Test
  @DisplayName("Test new LegendDepotStoreServerJacksonJsonProvider (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void LegendDepotStoreServerJacksonJsonProvider.<init>()"})
  void testNewLegendDepotStoreServerJacksonJsonProvider() {
    // Arrange, Act and Assert
    Version versionResult = new LegendDepotStoreServerJacksonJsonProvider().version();
    assertEquals("com.fasterxml.jackson.jaxrs", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.jaxrs/jackson-jaxrs-json-provider/2.10.5", versionResult.toFullString());
    assertEquals("jackson-jaxrs-json-provider", versionResult.getArtifactId());
    assertEquals(10, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(5, versionResult.getPatchLevel());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
  }

  /**
   * Test {@link LegendDepotStoreServerJacksonJsonProvider#getContext(Class)}.
   * <p>
   * Method under test: {@link LegendDepotStoreServerJacksonJsonProvider#getContext(Class)}
   */
  @Test
  @DisplayName("Test getContext(Class)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ObjectMapper LegendDepotStoreServerJacksonJsonProvider.getContext(Class)"})
  void testGetContext() {
    // Arrange
    LegendDepotStoreServerJacksonJsonProvider legendDepotStoreServerJacksonJsonProvider = new LegendDepotStoreServerJacksonJsonProvider();
    Class<Object> type = Object.class;

    // Act
    ObjectMapper actualContext = legendDepotStoreServerJacksonJsonProvider.getContext(type);

    // Assert
    JsonFactory factory = actualContext.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    assertTrue(actualContext.getDeserializationContext() instanceof DefaultDeserializationContext.Impl);
    assertTrue(actualContext.getVisibilityChecker() instanceof Std);
    assertTrue(actualContext.getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(actualContext.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(actualContext.getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(actualContext.getSerializerProvider() instanceof Impl);
    assertTrue(actualContext.getSerializerProviderInstance() instanceof Impl);
    assertTrue(actualContext.getDateFormat() instanceof SimpleDateFormat);
    assertNull(actualContext.getInjectableValues());
    assertNull(actualContext.getPropertyNamingStrategy());
    assertTrue(actualContext.getRegisteredModuleIds().isEmpty());
    assertSame(factory, actualContext.getJsonFactory());
  }
}
