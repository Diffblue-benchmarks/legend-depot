package org.finos.legend.depot.services.api.notifications.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class VoidQueueDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link VoidQueue}
   *   <li>{@link VoidQueue#size()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void VoidQueue.<init>()", "long VoidQueue.size()"})
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(0L, new VoidQueue().size());
  }

  /**
   * Test {@link VoidQueue#getAll()}.
   * <p>
   * Method under test: {@link VoidQueue#getAll()}
   */
  @Test
  @DisplayName("Test getAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.List VoidQueue.getAll()"})
  void testGetAll() {
    // Arrange, Act and Assert
    assertTrue(new VoidQueue().getAll().isEmpty());
  }

  /**
   * Test {@link VoidQueue#pullAll()}.
   * <p>
   * Method under test: {@link VoidQueue#pullAll()}
   */
  @Test
  @DisplayName("Test pullAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.List VoidQueue.pullAll()"})
  void testPullAll() {
    // Arrange, Act and Assert
    assertTrue(new VoidQueue().pullAll().isEmpty());
  }

  /**
   * Test {@link VoidQueue#getFirstInQueue()}.
   * <p>
   * Method under test: {@link VoidQueue#getFirstInQueue()}
   */
  @Test
  @DisplayName("Test getFirstInQueue()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Optional VoidQueue.getFirstInQueue()"})
  void testGetFirstInQueue() {
    // Arrange, Act and Assert
    assertFalse(new VoidQueue().getFirstInQueue().isPresent());
  }

  /**
   * Test {@link VoidQueue#get(String)}.
   * <p>
   * Method under test: {@link VoidQueue#get(String)}
   */
  @Test
  @DisplayName("Test get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Optional VoidQueue.get(String)"})
  void testGet() {
    // Arrange, Act and Assert
    assertFalse(new VoidQueue().get("42").isPresent());
  }

  /**
   * Test {@link VoidQueue#push(MetadataNotification)}.
   * <p>
   * Method under test: {@link VoidQueue#push(MetadataNotification)}
   */
  @Test
  @DisplayName("Test push(MetadataNotification)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String VoidQueue.push(MetadataNotification)"})
  void testPush() {
    // Arrange
    VoidQueue voidQueue = new VoidQueue();

    // Act and Assert
    assertNull(voidQueue.push(new MetadataNotification("myproject", "42", "42", "42")));
  }

  /**
   * Test {@link VoidQueue#deleteAll()}.
   * <p>
   * Method under test: {@link VoidQueue#deleteAll()}
   */
  @Test
  @DisplayName("Test deleteAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long VoidQueue.deleteAll()"})
  void testDeleteAll() {
    // Arrange, Act and Assert
    assertEquals(0L, new VoidQueue().deleteAll());
  }
}
