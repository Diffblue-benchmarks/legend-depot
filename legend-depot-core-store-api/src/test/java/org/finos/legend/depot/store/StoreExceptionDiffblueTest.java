package org.finos.legend.depot.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class StoreExceptionDiffblueTest {
  /**
   * Method under test: {@link StoreException#StoreException(String)}
   */
  @Test
  void testNewStoreException() {
    // Arrange and Act
    StoreException actualStoreException = new StoreException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualStoreException.getMessage());
    assertNull(actualStoreException.getCause());
    assertEquals(0, actualStoreException.getSuppressed().length);
  }
}
