package org.finos.legend.depot.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.Impl;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LegendDepotServerJacksonJsonProviderDiffblueTest {
  /**
   * Test new {@link LegendDepotServerJacksonJsonProvider} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link LegendDepotServerJacksonJsonProvider}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LegendDepotServerJacksonJsonProvider.<init>()"})
  public void testNewLegendDepotServerJacksonJsonProvider() {
    // Arrange, Act and Assert
    Version versionResult = (new LegendDepotServerJacksonJsonProvider()).version();
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
   * Test {@link LegendDepotServerJacksonJsonProvider#getContext(Class)}.
   * <p>
   * Method under test: {@link LegendDepotServerJacksonJsonProvider#getContext(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectMapper LegendDepotServerJacksonJsonProvider.getContext(Class)"})
  public void testGetContext() {
    // Arrange
    LegendDepotServerJacksonJsonProvider legendDepotServerJacksonJsonProvider = new LegendDepotServerJacksonJsonProvider();
    Class<PureModelContextData> type = PureModelContextData.class;

    // Act
    ObjectMapper actualContext = legendDepotServerJacksonJsonProvider.getContext(type);

    // Assert
    JsonFactory factory = actualContext.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    assertTrue(actualContext.getDeserializationContext() instanceof Impl);
    assertTrue(actualContext.getSerializerProviderInstance() instanceof DefaultSerializerProvider.Impl);
    assertTrue(actualContext.getDateFormat() instanceof StdDateFormat);
    assertSame(factory, actualContext.getJsonFactory());
  }

  /**
   * Test {@link LegendDepotServerJacksonJsonProvider#getContext(Class)}.
   * <ul>
   *   <li>When {@code Object}.</li>
   *   <li>Then Factory return {@link MappingJsonFactory}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LegendDepotServerJacksonJsonProvider#getContext(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ObjectMapper LegendDepotServerJacksonJsonProvider.getContext(Class)"})
  public void testGetContext_whenJavaLangObject_thenFactoryReturnMappingJsonFactory() {
    // Arrange
    LegendDepotServerJacksonJsonProvider legendDepotServerJacksonJsonProvider = new LegendDepotServerJacksonJsonProvider();
    Class<Object> type = Object.class;

    // Act
    ObjectMapper actualContext = legendDepotServerJacksonJsonProvider.getContext(type);

    // Assert
    JsonFactory factory = actualContext.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    assertTrue(actualContext.getDeserializationContext() instanceof Impl);
    assertTrue(actualContext.getSerializerProviderInstance() instanceof DefaultSerializerProvider.Impl);
    assertTrue(actualContext.getDateFormat() instanceof StdDateFormat);
    assertSame(factory, actualContext.getJsonFactory());
  }
}
