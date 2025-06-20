package org.finos.legend.depot.store.mongo.notifications.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.domain.notifications.MetadataNotificationStatus;
import org.finos.legend.depot.domain.notifications.Priority;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationKeyFilterDiffblueTest {
  /**
   * Test {@link NotificationKeyFilter#getFilter(MetadataNotification)}.
   * <p>
   * Method under test: {@link NotificationKeyFilter#getFilter(MetadataNotification)}
   */
  @Test
  @DisplayName("Test getFilter(MetadataNotification)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson NotificationKeyFilter.getFilter(MetadataNotification)"})
  void testGetFilter() {
    // Arrange
    MetadataNotification notification = new MetadataNotification("myproject", "42", "42", "42");

    // Act
    NotificationKeyFilter.getFilter(notification);

    // Assert that nothing has changed
    assertEquals("42", notification.getArtifactId());
    assertEquals("42", notification.getGroupId());
    assertEquals("42", notification.getVersionId());
    assertEquals("myproject", notification.getProjectId());
    assertEquals(0, notification.getAttempt());
    assertEquals(2, notification.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, notification.getStatus());
    assertEquals(Priority.LOW, notification.getEventPriority());
    assertFalse(notification.isFullUpdate());
    assertFalse(notification.isTransitive());
    assertTrue(notification.getResponses().isEmpty());
  }

  /**
   * Test {@link NotificationKeyFilter#getFilter(MetadataNotification)}.
   * <ul>
   *   <li>Then return toBsonDocument {@link Object} and {@link CodecRegistry} size is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationKeyFilter#getFilter(MetadataNotification)}
   */
  @Test
  @DisplayName("Test getFilter(MetadataNotification); then return toBsonDocument Object and CodecRegistry size is three")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Bson NotificationKeyFilter.getFilter(MetadataNotification)"})
  void testGetFilter_thenReturnToBsonDocumentObjectAndCodecRegistrySizeIsThree() {
    // Arrange
    MetadataNotification notification = new MetadataNotification("myproject", "42", "42", "42");

    // Act
    Bson actualFilter = NotificationKeyFilter.getFilter(notification);
    Class<Object> forNameResult = Object.class;
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    when(codecRegistry.get(Mockito.<Class<String>>any())).thenReturn(new StringCodec());
    BsonDocument actualToBsonDocumentResult = actualFilter.toBsonDocument(forNameResult, codecRegistry);

    // Assert
    verify(codecRegistry, atLeast(1)).get(isA(Class.class));
    assertEquals(3, actualToBsonDocumentResult.size());
    BsonValue getResult = actualToBsonDocumentResult.get("artifactId");
    assertTrue(getResult instanceof BsonString);
    BsonValue getResult2 = actualToBsonDocumentResult.get("groupId");
    assertTrue(getResult2 instanceof BsonString);
    BsonValue getResult3 = actualToBsonDocumentResult.get("versionId");
    assertTrue(getResult3 instanceof BsonString);
    assertEquals("42", notification.getArtifactId());
    assertEquals("42", notification.getGroupId());
    assertEquals("42", notification.getVersionId());
    assertEquals("myproject", notification.getProjectId());
    assertNull(notification.getEventId());
    assertNull(notification.getId());
    assertNull(notification.getParentEventId());
    assertNull(notification.getCompleted());
    assertNull(notification.getCreated());
    assertNull(notification.getUpdated());
    assertNull(notification.getCurrentResponse());
    assertEquals(0, notification.getAttempt());
    assertEquals(2, notification.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, notification.getStatus());
    assertEquals(Priority.LOW, notification.getEventPriority());
    assertFalse(notification.isFullUpdate());
    assertFalse(notification.isTransitive());
    assertTrue(notification.getResponses().isEmpty());
    assertEquals(getResult2, getResult);
    assertEquals(getResult2, getResult3);
  }
}
