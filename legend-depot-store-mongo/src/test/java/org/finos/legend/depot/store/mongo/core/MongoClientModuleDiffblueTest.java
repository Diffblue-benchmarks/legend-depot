package org.finos.legend.depot.store.mongo.core;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MongoClientModuleDiffblueTest {
  /**
   * Test {@link MongoClientModule#getMongoDatabase(ConnectionFactory)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MongoClientModule#getMongoDatabase(ConnectionFactory)}
   */
  @Test
  @DisplayName("Test getMongoDatabase(ConnectionFactory); given 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MongoDatabase MongoClientModule.getMongoDatabase(ConnectionFactory)"})
  void testGetMongoDatabase_givenNull_thenReturnNull() {
    // Arrange
    MongoClientModule mongoClientModule = new MongoClientModule();

    MongoNonTracingConnectionFactory connectionFactory =
        mock(MongoNonTracingConnectionFactory.class);
    when(connectionFactory.getDatabase()).thenReturn(null);

    // Act
    MongoDatabase actualMongoDatabase = mongoClientModule.getMongoDatabase(connectionFactory);

    // Assert
    verify(connectionFactory).getDatabase();
    assertNull(actualMongoDatabase);
  }
}
