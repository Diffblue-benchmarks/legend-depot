package org.finos.legend.depot.store.mongo.notifications.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.bson.BsonDocument;
import org.bson.BsonNull;
import org.bson.BsonString;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationKeyFilterDiffblueTest {
  /**
   * Test {@link NotificationKeyFilter#getFilter(MetadataNotification)}.
   *
   * <ul>
   *   <li>Then toBsonDocument {@link Object} and {@link CodecRegistry} {@code artifactId} return
   *       {@link BsonString}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationKeyFilter#getFilter(MetadataNotification)}
   */
  @Test
  @DisplayName(
      "Test getFilter(MetadataNotification); then toBsonDocument Object and CodecRegistry 'artifactId' return BsonString")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson NotificationKeyFilter.getFilter(MetadataNotification)"})
  void testGetFilter_thenToBsonDocumentObjectAndCodecRegistryArtifactIdReturnBsonString() {
    // Arrange
    MetadataNotification notification = new MetadataNotification("myproject", "42", "42", "42");
    notification.setEventId(null);

    // Act
    Bson actualFilter = NotificationKeyFilter.getFilter(notification);
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult =
        actualFilter.toBsonDocument(forNameResult, codecRegistry);

    // Assert
    verify(codecRegistry, atLeast(1)).get(isA(Class.class));
    assertEquals(3, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonString);
    BsonValue getResult2 = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult2 instanceof BsonString);
    BsonValue getResult3 = actualToBsonDocumentResult.get("versionId");
    assertTrue(getResult3 instanceof BsonString);
    assertEquals("42", ((BsonString) getResult2).getValue());
    assertEquals(BsonType.STRING, getResult2.getBsonType());
    assertFalse(getResult2.isNull());
    assertTrue(getResult2.isString());
    assertEquals(getResult2, getResult);
    assertEquals(getResult2, getResult3);
  }

  /**
   * Test {@link NotificationKeyFilter#getFilter(MetadataNotification)}.
   *
   * <ul>
   *   <li>Then toBsonDocument {@link Object} and {@link CodecRegistry} {@code groupId} return
   *       {@link BsonNull}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationKeyFilter#getFilter(MetadataNotification)}
   */
  @Test
  @DisplayName(
      "Test getFilter(MetadataNotification); then toBsonDocument Object and CodecRegistry 'groupId' return BsonNull")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Bson NotificationKeyFilter.getFilter(MetadataNotification)"})
  void testGetFilter_thenToBsonDocumentObjectAndCodecRegistryGroupIdReturnBsonNull() {
    // Arrange and Act
    Bson actualFilter = NotificationKeyFilter.getFilter(new MetadataNotification());
    Class<Object> forNameResult = Object.class;
    BsonDocument actualToBsonDocumentResult =
        actualFilter.toBsonDocument(forNameResult, mock(CodecRegistry.class));

    // Assert
    assertEquals(3, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult instanceof BsonNull);
    assertEquals(BsonType.NULL, getResult.getBsonType());
    assertFalse(getResult.isString());
    assertTrue(getResult.isNull());
    assertSame(getResult, actualToBsonDocumentResult.get("artifactId"));
    assertSame(getResult, actualToBsonDocumentResult.get("versionId"));
  }
}
