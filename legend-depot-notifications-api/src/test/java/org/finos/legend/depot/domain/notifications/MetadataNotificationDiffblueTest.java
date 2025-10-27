package org.finos.legend.depot.domain.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class MetadataNotificationDiffblueTest {
  /**
   * Method under test: {@link MetadataNotification#getStatus()}
   */
  @Test
  void testGetStatus() {
    // Arrange, Act and Assert
    assertEquals(MetadataNotificationStatus.SUCCESS,
        (new MetadataNotification("myproject", "42", "42", "42")).getStatus());
  }

  /**
   * Method under test: {@link MetadataNotification#getStatus()}
   */
  @Test
  void testGetStatus2() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    metadataNotification.addError("An error occurred");

    // Act and Assert
    assertEquals(MetadataNotificationStatus.FAILED, metadataNotification.getStatus());
  }

  /**
   * Method under test: {@link MetadataNotification#complete()}
   */
  @Test
  void testComplete() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");

    // Act and Assert
    assertSame(metadataNotification, metadataNotification.complete());
  }

  /**
   * Method under test: {@link MetadataNotification#complete()}
   */
  @Test
  void testComplete2() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    metadataNotification.setCompleted(mock(java.sql.Date.class));

    // Act and Assert
    assertSame(metadataNotification, metadataNotification.complete());
  }

  /**
   * Method under test: {@link MetadataNotification#retriesExceeded()}
   */
  @Test
  void testRetriesExceeded() {
    // Arrange, Act and Assert
    assertFalse((new MetadataNotification("myproject", "42", "42", "42")).retriesExceeded());
    assertTrue((new MetadataNotification()).retriesExceeded());
  }

  /**
   * Method under test: {@link MetadataNotification#retriesExceeded()}
   */
  @Test
  void testRetriesExceeded2() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    metadataNotification.setCompleted(mock(java.sql.Date.class));

    // Act and Assert
    assertFalse(metadataNotification.retriesExceeded());
  }

  /**
   * Method under test: {@link MetadataNotification#getResponses()}
   */
  @Test
  void testGetResponses() {
    // Arrange, Act and Assert
    assertTrue((new MetadataNotification("myproject", "42", "42", "42")).getResponses().isEmpty());
  }

  /**
   * Method under test: {@link MetadataNotification#getResponses()}
   */
  @Test
  void testGetResponses2() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    metadataNotification.setResponses(null);

    // Act and Assert
    assertTrue(metadataNotification.getResponses().isEmpty());
  }

  /**
   * Method under test: {@link MetadataNotification#addError(String)}
   */
  @Test
  void testAddError() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");

    // Act
    MetadataNotification actualAddErrorResult = metadataNotification.addError("An error occurred");

    // Assert
    assertEquals(MetadataNotificationStatus.FAILED, metadataNotification.getStatus());
    assertSame(metadataNotification, actualAddErrorResult);
  }

  /**
   * Method under test: {@link MetadataNotification#addError(String)}
   */
  @Test
  void testAddError2() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    metadataNotification.setResponses(null);

    // Act
    MetadataNotification actualAddErrorResult = metadataNotification.addError("An error occurred");

    // Assert
    assertEquals(MetadataNotificationStatus.FAILED, metadataNotification.getStatus());
    assertSame(metadataNotification, actualAddErrorResult);
  }

  /**
   * Method under test:
   * {@link MetadataNotification#setResponse(MetadataNotificationResponse)}
   */
  @Test
  void testSetResponse() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    MetadataNotificationResponse response = new MetadataNotificationResponse();

    // Act
    metadataNotification.setResponse(response);

    // Assert
    Map<Integer, MetadataNotificationResponse> responses = metadataNotification.getResponses();
    assertEquals(1, responses.size());
    assertSame(response, responses.get(0));
    assertSame(response, metadataNotification.getCurrentResponse());
  }

  /**
   * Method under test:
   * {@link MetadataNotification#setResponse(MetadataNotificationResponse)}
   */
  @Test
  void testSetResponse2() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    metadataNotification.setResponses(null);
    MetadataNotificationResponse response = new MetadataNotificationResponse();

    // Act
    metadataNotification.setResponse(response);

    // Assert
    Map<Integer, MetadataNotificationResponse> responses = metadataNotification.getResponses();
    assertEquals(1, responses.size());
    assertSame(response, responses.get(0));
    assertSame(response, metadataNotification.getCurrentResponse());
  }

  /**
   * Method under test:
   * {@link MetadataNotification#combineResponse(MetadataNotificationResponse)}
   */
  @Test
  void testCombineResponse() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");

    // Act and Assert
    assertSame(metadataNotification, metadataNotification.combineResponse(new MetadataNotificationResponse()));
  }

  /**
   * Method under test:
   * {@link MetadataNotification#combineResponse(MetadataNotificationResponse)}
   */
  @Test
  void testCombineResponse2() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    metadataNotification.setResponses(new HashMap<>());

    // Act and Assert
    assertSame(metadataNotification, metadataNotification.combineResponse(null));
  }

  /**
   * Method under test:
   * {@link MetadataNotification#combineResponse(MetadataNotificationResponse)}
   */
  @Test
  void testCombineResponse3() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    metadataNotification.setResponses(null);

    // Act and Assert
    assertSame(metadataNotification, metadataNotification.combineResponse(new MetadataNotificationResponse()));
  }

  /**
   * Method under test:
   * {@link MetadataNotification#combineResponse(MetadataNotificationResponse)}
   */
  @Test
  void testCombineResponse4() {
    // Arrange
    HashMap<Integer, MetadataNotificationResponse> responses = new HashMap<>();
    responses.computeIfPresent(1, mock(BiFunction.class));

    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    metadataNotification.setResponses(responses);

    // Act and Assert
    assertSame(metadataNotification, metadataNotification.combineResponse(null));
  }

  /**
   * Method under test: {@link MetadataNotification#getCurrentResponse()}
   */
  @Test
  void testGetCurrentResponse() {
    // Arrange, Act and Assert
    assertNull((new MetadataNotification("myproject", "42", "42", "42")).getCurrentResponse());
  }

  /**
   * Method under test: {@link MetadataNotification#getCurrentResponse()}
   */
  @Test
  void testGetCurrentResponse2() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    metadataNotification.setResponses(null);

    // Act and Assert
    assertNull(metadataNotification.getCurrentResponse());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MetadataNotification#equals(Object)}
   *   <li>{@link MetadataNotification#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");
    MetadataNotification metadataNotification2 = new MetadataNotification("myproject", "42", "42", "42");

    // Act and Assert
    assertEquals(metadataNotification, metadataNotification2);
    int expectedHashCodeResult = metadataNotification.hashCode();
    assertEquals(expectedHashCodeResult, metadataNotification2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MetadataNotification#equals(Object)}
   *   <li>{@link MetadataNotification#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("myproject", "42", "42", "42");

    // Act and Assert
    assertEquals(metadataNotification, metadataNotification);
    int expectedHashCodeResult = metadataNotification.hashCode();
    assertEquals(expectedHashCodeResult, metadataNotification.hashCode());
  }

  /**
   * Method under test: {@link MetadataNotification#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MetadataNotification metadataNotification = new MetadataNotification("42", "42", "42", "42");

    // Act and Assert
    assertNotEquals(metadataNotification, new MetadataNotification("myproject", "42", "42", "42"));
  }

  /**
   * Method under test: {@link MetadataNotification#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MetadataNotification("myproject", "42", "42", "42"), null);
  }

  /**
   * Method under test: {@link MetadataNotification#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MetadataNotification("myproject", "42", "42", "42"), "Different type to MetadataNotification");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MetadataNotification#MetadataNotification()}
   *   <li>{@link MetadataNotification#setAttempt(int)}
   *   <li>{@link MetadataNotification#setCompleted(Date)}
   *   <li>{@link MetadataNotification#setCreated(Date)}
   *   <li>{@link MetadataNotification#setEventId(String)}
   *   <li>{@link MetadataNotification#setEventPriority(Priority)}
   *   <li>{@link MetadataNotification#setFullUpdate(boolean)}
   *   <li>{@link MetadataNotification#setId(String)}
   *   <li>{@link MetadataNotification#setMaxAttempts(int)}
   *   <li>{@link MetadataNotification#setParentEventId(String)}
   *   <li>{@link MetadataNotification#setProjectId(String)}
   *   <li>{@link MetadataNotification#setResponses(Map)}
   *   <li>{@link MetadataNotification#setTransitive(boolean)}
   *   <li>{@link MetadataNotification#setUpdated(Date)}
   *   <li>{@link MetadataNotification#increaseAttempts()}
   *   <li>{@link MetadataNotification#getAttempt()}
   *   <li>{@link MetadataNotification#getCompleted()}
   *   <li>{@link MetadataNotification#getCreated()}
   *   <li>{@link MetadataNotification#getEventId()}
   *   <li>{@link MetadataNotification#getEventPriority()}
   *   <li>{@link MetadataNotification#getId()}
   *   <li>{@link MetadataNotification#getMaxAttempts()}
   *   <li>{@link MetadataNotification#getParentEventId()}
   *   <li>{@link MetadataNotification#getProjectId()}
   *   <li>{@link MetadataNotification#getUpdated()}
   *   <li>{@link MetadataNotification#isFullUpdate()}
   *   <li>{@link MetadataNotification#isTransitive()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    MetadataNotification actualMetadataNotification = new MetadataNotification();
    MetadataNotification actualSetAttemptResult = actualMetadataNotification.setAttempt(1);
    Date completed = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualMetadataNotification.setCompleted(completed);
    Date created = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualMetadataNotification.setCreated(created);
    MetadataNotification actualSetEventIdResult = actualMetadataNotification.setEventId("Event ID");
    actualMetadataNotification.setEventPriority(Priority.HIGH);
    MetadataNotification actualSetFullUpdateResult = actualMetadataNotification.setFullUpdate(true);
    actualMetadataNotification.setId("42");
    actualMetadataNotification.setMaxAttempts(3);
    actualMetadataNotification.setParentEventId("42");
    MetadataNotification actualSetProjectIdResult = actualMetadataNotification.setProjectId("myproject");
    HashMap<Integer, MetadataNotificationResponse> responses = new HashMap<>();
    actualMetadataNotification.setResponses(responses);
    actualMetadataNotification.setTransitive(true);
    Date updated = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    MetadataNotification actualSetUpdatedResult = actualMetadataNotification.setUpdated(updated);
    MetadataNotification actualIncreaseAttemptsResult = actualMetadataNotification.increaseAttempts();
    int actualAttempt = actualMetadataNotification.getAttempt();
    Date actualCompleted = actualMetadataNotification.getCompleted();
    Date actualCreated = actualMetadataNotification.getCreated();
    String actualEventId = actualMetadataNotification.getEventId();
    Priority actualEventPriority = actualMetadataNotification.getEventPriority();
    String actualId = actualMetadataNotification.getId();
    int actualMaxAttempts = actualMetadataNotification.getMaxAttempts();
    String actualParentEventId = actualMetadataNotification.getParentEventId();
    String actualProjectId = actualMetadataNotification.getProjectId();
    Date actualUpdated = actualMetadataNotification.getUpdated();
    boolean actualIsFullUpdateResult = actualMetadataNotification.isFullUpdate();
    boolean actualIsTransitiveResult = actualMetadataNotification.isTransitive();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("42", actualParentEventId);
    assertEquals("Event ID", actualEventId);
    assertEquals("myproject", actualProjectId);
    assertEquals(2, actualAttempt);
    assertEquals(3, actualMaxAttempts);
    assertEquals(Priority.HIGH, actualEventPriority);
    Map<Integer, MetadataNotificationResponse> responses2 = actualMetadataNotification.getResponses();
    assertTrue(responses2.isEmpty());
    assertTrue(actualIsFullUpdateResult);
    assertTrue(actualIsTransitiveResult);
    assertSame(responses, responses2);
    assertSame(actualMetadataNotification, actualIncreaseAttemptsResult);
    assertSame(actualMetadataNotification, actualSetAttemptResult);
    assertSame(actualMetadataNotification, actualSetEventIdResult);
    assertSame(actualMetadataNotification, actualSetFullUpdateResult);
    assertSame(actualMetadataNotification, actualSetProjectIdResult);
    assertSame(actualMetadataNotification, actualSetUpdatedResult);
    assertSame(completed, actualCompleted);
    assertSame(created, actualCreated);
    assertSame(updated, actualUpdated);
  }

  /**
   * Method under test:
   * {@link MetadataNotification#MetadataNotification(String, String, String, String)}
   */
  @Test
  void testNewMetadataNotification() {
    // Arrange and Act
    MetadataNotification actualMetadataNotification = new MetadataNotification("myproject", "42", "42", "42");

    // Assert
    assertEquals("42", actualMetadataNotification.getArtifactId());
    assertEquals("42", actualMetadataNotification.getGroupId());
    assertEquals("42", actualMetadataNotification.getVersionId());
    assertEquals("myproject", actualMetadataNotification.getProjectId());
    assertNull(actualMetadataNotification.getEventId());
    assertNull(actualMetadataNotification.getId());
    assertNull(actualMetadataNotification.getParentEventId());
    assertNull(actualMetadataNotification.getCompleted());
    assertNull(actualMetadataNotification.getCreated());
    assertNull(actualMetadataNotification.getUpdated());
    assertNull(actualMetadataNotification.getCurrentResponse());
    assertEquals(0, actualMetadataNotification.getAttempt());
    assertEquals(2, actualMetadataNotification.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, actualMetadataNotification.getStatus());
    assertEquals(Priority.LOW, actualMetadataNotification.getEventPriority());
    assertFalse(actualMetadataNotification.isFullUpdate());
    assertFalse(actualMetadataNotification.isTransitive());
    assertTrue(actualMetadataNotification.getResponses().isEmpty());
  }

  /**
   * Method under test:
   * {@link MetadataNotification#MetadataNotification(String, String, String, String, Boolean, Boolean, String)}
   */
  @Test
  void testNewMetadataNotification2() {
    // Arrange and Act
    MetadataNotification actualMetadataNotification = new MetadataNotification("myproject", "42", "42", "42", true,
        true, "Parent Event");

    // Assert
    assertEquals("42", actualMetadataNotification.getArtifactId());
    assertEquals("42", actualMetadataNotification.getGroupId());
    assertEquals("42", actualMetadataNotification.getVersionId());
    assertEquals("Parent Event", actualMetadataNotification.getParentEventId());
    assertEquals("myproject", actualMetadataNotification.getProjectId());
    assertNull(actualMetadataNotification.getEventId());
    assertNull(actualMetadataNotification.getId());
    assertNull(actualMetadataNotification.getCompleted());
    assertNull(actualMetadataNotification.getCreated());
    assertNull(actualMetadataNotification.getUpdated());
    assertNull(actualMetadataNotification.getCurrentResponse());
    assertEquals(0, actualMetadataNotification.getAttempt());
    assertEquals(2, actualMetadataNotification.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, actualMetadataNotification.getStatus());
    assertEquals(Priority.LOW, actualMetadataNotification.getEventPriority());
    assertTrue(actualMetadataNotification.getResponses().isEmpty());
    assertTrue(actualMetadataNotification.isFullUpdate());
    assertTrue(actualMetadataNotification.isTransitive());
  }

  /**
   * Method under test:
   * {@link MetadataNotification#MetadataNotification(String, String, String, String, Boolean, Boolean, String)}
   */
  @Test
  void testNewMetadataNotification3() {
    // Arrange and Act
    MetadataNotification actualMetadataNotification = new MetadataNotification("myproject", "42", "42", "42", null,
        null, "Parent Event");

    // Assert
    assertEquals("42", actualMetadataNotification.getArtifactId());
    assertEquals("42", actualMetadataNotification.getGroupId());
    assertEquals("42", actualMetadataNotification.getVersionId());
    assertEquals("Parent Event", actualMetadataNotification.getParentEventId());
    assertEquals("myproject", actualMetadataNotification.getProjectId());
    assertNull(actualMetadataNotification.getEventId());
    assertNull(actualMetadataNotification.getId());
    assertNull(actualMetadataNotification.getCompleted());
    assertNull(actualMetadataNotification.getCreated());
    assertNull(actualMetadataNotification.getUpdated());
    assertNull(actualMetadataNotification.getCurrentResponse());
    assertEquals(0, actualMetadataNotification.getAttempt());
    assertEquals(2, actualMetadataNotification.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, actualMetadataNotification.getStatus());
    assertEquals(Priority.LOW, actualMetadataNotification.getEventPriority());
    assertFalse(actualMetadataNotification.isFullUpdate());
    assertFalse(actualMetadataNotification.isTransitive());
    assertTrue(actualMetadataNotification.getResponses().isEmpty());
  }

  /**
   * Method under test:
   * {@link MetadataNotification#MetadataNotification(String, String, String, String, Boolean, Boolean, String, Priority)}
   */
  @Test
  void testNewMetadataNotification4() {
    // Arrange and Act
    MetadataNotification actualMetadataNotification = new MetadataNotification("myproject", "42", "42", "42", true,
        true, "Parent Event", Priority.HIGH);

    // Assert
    assertEquals("42", actualMetadataNotification.getArtifactId());
    assertEquals("42", actualMetadataNotification.getGroupId());
    assertEquals("42", actualMetadataNotification.getVersionId());
    assertEquals("Parent Event", actualMetadataNotification.getParentEventId());
    assertEquals("myproject", actualMetadataNotification.getProjectId());
    assertNull(actualMetadataNotification.getEventId());
    assertNull(actualMetadataNotification.getId());
    assertNull(actualMetadataNotification.getCompleted());
    assertNull(actualMetadataNotification.getCreated());
    assertNull(actualMetadataNotification.getUpdated());
    assertNull(actualMetadataNotification.getCurrentResponse());
    assertEquals(0, actualMetadataNotification.getAttempt());
    assertEquals(2, actualMetadataNotification.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, actualMetadataNotification.getStatus());
    assertEquals(Priority.HIGH, actualMetadataNotification.getEventPriority());
    assertTrue(actualMetadataNotification.getResponses().isEmpty());
    assertTrue(actualMetadataNotification.isFullUpdate());
    assertTrue(actualMetadataNotification.isTransitive());
  }

  /**
   * Method under test:
   * {@link MetadataNotification#MetadataNotification(String, String, String, String, Boolean, Boolean, String, Priority)}
   */
  @Test
  void testNewMetadataNotification5() {
    // Arrange and Act
    MetadataNotification actualMetadataNotification = new MetadataNotification("myproject", "42", "42", "42", null,
        null, "Parent Event", Priority.HIGH);

    // Assert
    assertEquals("42", actualMetadataNotification.getArtifactId());
    assertEquals("42", actualMetadataNotification.getGroupId());
    assertEquals("42", actualMetadataNotification.getVersionId());
    assertEquals("Parent Event", actualMetadataNotification.getParentEventId());
    assertEquals("myproject", actualMetadataNotification.getProjectId());
    assertNull(actualMetadataNotification.getEventId());
    assertNull(actualMetadataNotification.getId());
    assertNull(actualMetadataNotification.getCompleted());
    assertNull(actualMetadataNotification.getCreated());
    assertNull(actualMetadataNotification.getUpdated());
    assertNull(actualMetadataNotification.getCurrentResponse());
    assertEquals(0, actualMetadataNotification.getAttempt());
    assertEquals(2, actualMetadataNotification.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, actualMetadataNotification.getStatus());
    assertEquals(Priority.HIGH, actualMetadataNotification.getEventPriority());
    assertFalse(actualMetadataNotification.isFullUpdate());
    assertFalse(actualMetadataNotification.isTransitive());
    assertTrue(actualMetadataNotification.getResponses().isEmpty());
  }

  /**
   * Method under test:
   * {@link MetadataNotification#MetadataNotification(String, String, String, String, String, String, Boolean, Boolean, Integer, Integer, Map, Date, Date, Date, Priority)}
   */
  @Test
  void testNewMetadataNotification6() {
    // Arrange
    HashMap<Integer, MetadataNotificationResponse> responses = new HashMap<>();
    Date created = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    Date updated = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    Date completed = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    MetadataNotification actualMetadataNotification = new MetadataNotification("myproject", "42", "42", "1.0.2", "42",
        "42", true, true, 1, 3, responses, created, updated, completed, Priority.HIGH);

    // Assert
    assertEquals("1.0.2", actualMetadataNotification.getVersionId());
    assertEquals("42", actualMetadataNotification.getArtifactId());
    assertEquals("42", actualMetadataNotification.getGroupId());
    assertEquals("42", actualMetadataNotification.getEventId());
    assertEquals("42", actualMetadataNotification.getParentEventId());
    assertEquals("myproject", actualMetadataNotification.getProjectId());
    assertNull(actualMetadataNotification.getId());
    assertNull(actualMetadataNotification.getCurrentResponse());
    assertEquals(1, actualMetadataNotification.getAttempt());
    assertEquals(3, actualMetadataNotification.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, actualMetadataNotification.getStatus());
    assertEquals(Priority.HIGH, actualMetadataNotification.getEventPriority());
    Map<Integer, MetadataNotificationResponse> responses2 = actualMetadataNotification.getResponses();
    assertTrue(responses2.isEmpty());
    assertTrue(actualMetadataNotification.isFullUpdate());
    assertTrue(actualMetadataNotification.isTransitive());
    assertSame(responses, responses2);
    assertSame(completed, actualMetadataNotification.getCompleted());
    assertSame(created, actualMetadataNotification.getCreated());
    assertSame(updated, actualMetadataNotification.getUpdated());
  }

  /**
   * Method under test:
   * {@link MetadataNotification#MetadataNotification(String, String, String, String, String, String, Boolean, Boolean, Integer, Integer, Map, Date, Date, Date, Priority)}
   */
  @Test
  void testNewMetadataNotification7() {
    // Arrange
    Date created = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    Date updated = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    Date completed = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    MetadataNotification actualMetadataNotification = new MetadataNotification("myproject", "42", "42", "1.0.2", "42",
        "42", null, null, null, null, null, created, updated, completed, Priority.HIGH);

    // Assert
    assertEquals("1.0.2", actualMetadataNotification.getVersionId());
    assertEquals("42", actualMetadataNotification.getArtifactId());
    assertEquals("42", actualMetadataNotification.getGroupId());
    assertEquals("42", actualMetadataNotification.getEventId());
    assertEquals("42", actualMetadataNotification.getParentEventId());
    assertEquals("myproject", actualMetadataNotification.getProjectId());
    assertNull(actualMetadataNotification.getId());
    assertNull(actualMetadataNotification.getCurrentResponse());
    assertEquals(0, actualMetadataNotification.getAttempt());
    assertEquals(2, actualMetadataNotification.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, actualMetadataNotification.getStatus());
    assertEquals(Priority.HIGH, actualMetadataNotification.getEventPriority());
    assertFalse(actualMetadataNotification.isFullUpdate());
    assertFalse(actualMetadataNotification.isTransitive());
    assertTrue(actualMetadataNotification.getResponses().isEmpty());
    assertSame(completed, actualMetadataNotification.getCompleted());
    assertSame(created, actualMetadataNotification.getCreated());
    assertSame(updated, actualMetadataNotification.getUpdated());
  }

  /**
   * Method under test:
   * {@link MetadataNotification#MetadataNotification(String, String, String, String, String, String, Boolean, Boolean, Integer, Integer, Map, java.util.Date, java.util.Date, java.util.Date, Priority)}
   */
  @Test
  void testNewMetadataNotification8() {
    // Arrange
    HashMap<Integer, MetadataNotificationResponse> responses = new HashMap<>();
    java.sql.Date created = mock(java.sql.Date.class);
    java.util.Date updated = java.util.Date
        .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    java.util.Date completed = java.util.Date
        .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    MetadataNotification actualMetadataNotification = new MetadataNotification("myproject", "42", "42", "1.0.2", "42",
        "42", true, true, 1, 3, responses, created, updated, completed, Priority.HIGH);

    // Assert
    assertEquals("1.0.2", actualMetadataNotification.getVersionId());
    assertEquals("42", actualMetadataNotification.getArtifactId());
    assertEquals("42", actualMetadataNotification.getGroupId());
    assertEquals("42", actualMetadataNotification.getEventId());
    assertEquals("42", actualMetadataNotification.getParentEventId());
    assertEquals("myproject", actualMetadataNotification.getProjectId());
    assertNull(actualMetadataNotification.getId());
    assertNull(actualMetadataNotification.getCurrentResponse());
    assertEquals(1, actualMetadataNotification.getAttempt());
    assertEquals(3, actualMetadataNotification.getMaxAttempts());
    assertEquals(MetadataNotificationStatus.SUCCESS, actualMetadataNotification.getStatus());
    assertEquals(Priority.HIGH, actualMetadataNotification.getEventPriority());
    Map<Integer, MetadataNotificationResponse> responses2 = actualMetadataNotification.getResponses();
    assertTrue(responses2.isEmpty());
    assertTrue(actualMetadataNotification.isFullUpdate());
    assertTrue(actualMetadataNotification.isTransitive());
    assertSame(responses, responses2);
    assertSame(completed, actualMetadataNotification.getCompleted());
    assertSame(updated, actualMetadataNotification.getUpdated());
    assertSame(created, actualMetadataNotification.getCreated());
  }
}
