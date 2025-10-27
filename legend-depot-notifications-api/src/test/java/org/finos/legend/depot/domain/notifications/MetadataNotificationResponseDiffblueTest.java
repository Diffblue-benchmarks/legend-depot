package org.finos.legend.depot.domain.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class MetadataNotificationResponseDiffblueTest {
  /**
   * Method under test: {@link MetadataNotificationResponse#getStatus()}
   */
  @Test
  void testGetStatus() {
    // Arrange, Act and Assert
    assertEquals(MetadataNotificationStatus.SUCCESS, (new MetadataNotificationResponse()).getStatus());
  }

  /**
   * Method under test: {@link MetadataNotificationResponse#getStatus()}
   */
  @Test
  void testGetStatus2() {
    // Arrange
    MetadataNotificationResponse metadataNotificationResponse = new MetadataNotificationResponse();
    metadataNotificationResponse.addError("Not all who wander are lost");

    // Act and Assert
    assertEquals(MetadataNotificationStatus.FAILED, metadataNotificationResponse.getStatus());
  }

  /**
   * Method under test: {@link MetadataNotificationResponse#addError(String)}
   */
  @Test
  void testAddError() {
    // Arrange
    MetadataNotificationResponse metadataNotificationResponse = new MetadataNotificationResponse();

    // Act
    MetadataNotificationResponse actualAddErrorResult = metadataNotificationResponse
        .addError("Not all who wander are lost");

    // Assert
    assertEquals(MetadataNotificationStatus.FAILED, metadataNotificationResponse.getStatus());
    assertTrue(metadataNotificationResponse.hasErrors());
    assertSame(metadataNotificationResponse, actualAddErrorResult);
  }

  /**
   * Method under test: {@link MetadataNotificationResponse#addMessage(String)}
   */
  @Test
  void testAddMessage() {
    // Arrange
    MetadataNotificationResponse metadataNotificationResponse = new MetadataNotificationResponse();

    // Act and Assert
    assertSame(metadataNotificationResponse, metadataNotificationResponse.addMessage("Not all who wander are lost"));
  }

  /**
   * Method under test: {@link MetadataNotificationResponse#addMessages(List)}
   */
  @Test
  void testAddMessages() {
    // Arrange
    MetadataNotificationResponse metadataNotificationResponse = new MetadataNotificationResponse();

    // Act and Assert
    assertSame(metadataNotificationResponse, metadataNotificationResponse.addMessages(new ArrayList<>()));
  }

  /**
   * Method under test: {@link MetadataNotificationResponse#addMessages(List)}
   */
  @Test
  void testAddMessages2() {
    // Arrange
    MetadataNotificationResponse metadataNotificationResponse = new MetadataNotificationResponse();

    ArrayList<String> messages = new ArrayList<>();
    messages.add("foo");

    // Act and Assert
    assertSame(metadataNotificationResponse, metadataNotificationResponse.addMessages(messages));
  }

  /**
   * Method under test: {@link MetadataNotificationResponse#addMessages(List)}
   */
  @Test
  void testAddMessages3() {
    // Arrange
    MetadataNotificationResponse metadataNotificationResponse = new MetadataNotificationResponse();

    ArrayList<String> messages = new ArrayList<>();
    messages.add("42");
    messages.add("foo");

    // Act and Assert
    assertSame(metadataNotificationResponse, metadataNotificationResponse.addMessages(messages));
  }

  /**
   * Method under test: {@link MetadataNotificationResponse#logError(String)}
   */
  @Test
  void testLogError() {
    // Arrange
    MetadataNotificationResponse metadataNotificationResponse = new MetadataNotificationResponse();

    // Act
    metadataNotificationResponse.logError("An error occurred");

    // Assert
    List<String> errors = metadataNotificationResponse.getErrors();
    assertEquals(1, errors.size());
    assertEquals("An error occurred", errors.get(0));
    assertEquals(MetadataNotificationStatus.FAILED, metadataNotificationResponse.getStatus());
    assertTrue(metadataNotificationResponse.hasErrors());
  }

  /**
   * Method under test: {@link MetadataNotificationResponse#hasErrors()}
   */
  @Test
  void testHasErrors() {
    // Arrange, Act and Assert
    assertFalse((new MetadataNotificationResponse()).hasErrors());
  }

  /**
   * Method under test: {@link MetadataNotificationResponse#hasErrors()}
   */
  @Test
  void testHasErrors2() {
    // Arrange
    MetadataNotificationResponse metadataNotificationResponse = new MetadataNotificationResponse();
    metadataNotificationResponse.addError("Not all who wander are lost");

    // Act and Assert
    assertTrue(metadataNotificationResponse.hasErrors());
  }

  /**
   * Method under test:
   * {@link MetadataNotificationResponse#combine(MetadataNotificationResponse)}
   */
  @Test
  void testCombine() {
    // Arrange
    MetadataNotificationResponse metadataNotificationResponse = new MetadataNotificationResponse();

    // Act and Assert
    assertSame(metadataNotificationResponse, metadataNotificationResponse.combine(new MetadataNotificationResponse()));
  }

  /**
   * Method under test:
   * {@link MetadataNotificationResponse#combine(MetadataNotificationResponse)}
   */
  @Test
  void testCombine2() {
    // Arrange
    MetadataNotificationResponse metadataNotificationResponse = new MetadataNotificationResponse();

    // Act and Assert
    assertSame(metadataNotificationResponse, metadataNotificationResponse.combine(null));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link MetadataNotificationResponse}
   *   <li>{@link MetadataNotificationResponse#toString()}
   *   <li>{@link MetadataNotificationResponse#getErrors()}
   *   <li>{@link MetadataNotificationResponse#getMessages()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    MetadataNotificationResponse actualMetadataNotificationResponse = new MetadataNotificationResponse();
    String actualToStringResult = actualMetadataNotificationResponse.toString();
    List<String> actualErrors = actualMetadataNotificationResponse.getErrors();
    List<String> actualMessages = actualMetadataNotificationResponse.getMessages();

    // Assert
    assertEquals("MetadataEventResponse{messages=[], errors=[]}", actualToStringResult);
    assertTrue(actualErrors.isEmpty());
    assertTrue(actualMessages.isEmpty());
  }
}
