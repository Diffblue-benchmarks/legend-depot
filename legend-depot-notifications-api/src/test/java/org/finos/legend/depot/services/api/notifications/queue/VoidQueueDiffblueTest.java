package org.finos.legend.depot.services.api.notifications.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.sql.Date;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.junit.jupiter.api.Test;

class VoidQueueDiffblueTest {
  /**
   * Method under test: {@link VoidQueue#getAll()}
   */
  @Test
  void testGetAll() {
    // Arrange, Act and Assert
    assertTrue((new VoidQueue()).getAll().isEmpty());
  }

  /**
   * Method under test: {@link VoidQueue#pullAll()}
   */
  @Test
  void testPullAll() {
    // Arrange, Act and Assert
    assertTrue((new VoidQueue()).pullAll().isEmpty());
  }

  /**
   * Method under test: {@link VoidQueue#getFirstInQueue()}
   */
  @Test
  void testGetFirstInQueue() {
    // Arrange, Act and Assert
    assertFalse((new VoidQueue()).getFirstInQueue().isPresent());
  }

  /**
   * Method under test: {@link VoidQueue#get(String)}
   */
  @Test
  void testGet() {
    // Arrange, Act and Assert
    assertFalse((new VoidQueue()).get("42").isPresent());
  }

  /**
   * Method under test: {@link VoidQueue#push(MetadataNotification)}
   */
  @Test
  void testPush() {
    // Arrange
    VoidQueue voidQueue = new VoidQueue();

    // Act and Assert
    assertNull(voidQueue.push(new MetadataNotification("myproject", "42", "42", "42")));
  }

  /**
   * Method under test: {@link VoidQueue#push(MetadataNotification)}
   */
  @Test
  void testPush2() {
    // Arrange
    VoidQueue voidQueue = new VoidQueue();

    MetadataNotification metadataEvent = new MetadataNotification("myproject", "42", "42", "42");
    metadataEvent.setCompleted(mock(Date.class));

    // Act and Assert
    assertNull(voidQueue.push(metadataEvent));
  }

  /**
   * Method under test: {@link VoidQueue#deleteAll()}
   */
  @Test
  void testDeleteAll() {
    // Arrange, Act and Assert
    assertEquals(0L, (new VoidQueue()).deleteAll());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link VoidQueue}
   *   <li>{@link VoidQueue#size()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(0L, (new VoidQueue()).size());
  }
}
