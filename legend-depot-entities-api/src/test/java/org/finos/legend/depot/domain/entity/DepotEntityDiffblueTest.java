package org.finos.legend.depot.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Test;

class DepotEntityDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DepotEntity#equals(Object)}
   *   <li>{@link DepotEntity#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    DepotEntity depotEntity = new DepotEntity("42", "42", "42");
    DepotEntity depotEntity2 = new DepotEntity("42", "42", "42");

    // Act and Assert
    assertEquals(depotEntity, depotEntity2);
    int expectedHashCodeResult = depotEntity.hashCode();
    assertEquals(expectedHashCodeResult, depotEntity2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DepotEntity#equals(Object)}
   *   <li>{@link DepotEntity#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    DepotEntity depotEntity = new DepotEntity("42", "42", "42");

    // Act and Assert
    assertEquals(depotEntity, depotEntity);
    int expectedHashCodeResult = depotEntity.hashCode();
    assertEquals(expectedHashCodeResult, depotEntity.hashCode());
  }

  /**
   * Method under test: {@link DepotEntity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    DepotEntity depotEntity = new DepotEntity("Group Id", "42", "42");

    // Act and Assert
    assertNotEquals(depotEntity, new DepotEntity("42", "42", "42"));
  }

  /**
   * Method under test: {@link DepotEntity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    DepotEntity depotEntity = new DepotEntity("42", "42", "42", mock(Entity.class));

    // Act and Assert
    assertNotEquals(depotEntity, new DepotEntity("42", "42", "42"));
  }

  /**
   * Method under test: {@link DepotEntity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new DepotEntity("42", "42", "42"), null);
  }

  /**
   * Method under test: {@link DepotEntity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new DepotEntity("42", "42", "42"), "Different type to DepotEntity");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DepotEntity#DepotEntity()}
   *   <li>{@link DepotEntity#getEntity()}
   *   <li>{@link DepotEntity#isVersionedEntity()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    DepotEntity actualDepotEntity = new DepotEntity();
    Entity actualEntity = actualDepotEntity.getEntity();
    boolean actualIsVersionedEntityResult = actualDepotEntity.isVersionedEntity();

    // Assert
    assertNull(actualDepotEntity.getArtifactId());
    assertNull(actualDepotEntity.getGroupId());
    assertNull(actualDepotEntity.getVersionId());
    assertNull(actualEntity);
    assertFalse(actualIsVersionedEntityResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DepotEntity#DepotEntity(String, String, String)}
   *   <li>{@link DepotEntity#getEntity()}
   *   <li>{@link DepotEntity#isVersionedEntity()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    DepotEntity actualDepotEntity = new DepotEntity("42", "42", "42");
    Entity actualEntity = actualDepotEntity.getEntity();
    boolean actualIsVersionedEntityResult = actualDepotEntity.isVersionedEntity();

    // Assert
    assertEquals("42", actualDepotEntity.getArtifactId());
    assertEquals("42", actualDepotEntity.getGroupId());
    assertEquals("42", actualDepotEntity.getVersionId());
    assertNull(actualEntity);
    assertFalse(actualIsVersionedEntityResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DepotEntity#DepotEntity(String, String, String, Entity)}
   *   <li>{@link DepotEntity#getEntity()}
   *   <li>{@link DepotEntity#isVersionedEntity()}
   * </ul>
   */
  @Test
  void testGettersAndSetters3() {
    // Arrange
    EntityDefinition entity = new EntityDefinition("Path", "Classifier Path", new HashMap<>());

    // Act
    DepotEntity actualDepotEntity = new DepotEntity("42", "42", "42", entity);
    Entity actualEntity = actualDepotEntity.getEntity();
    boolean actualIsVersionedEntityResult = actualDepotEntity.isVersionedEntity();

    // Assert
    assertEquals("42", actualDepotEntity.getArtifactId());
    assertEquals("42", actualDepotEntity.getGroupId());
    assertEquals("42", actualDepotEntity.getVersionId());
    assertFalse(actualIsVersionedEntityResult);
    assertSame(entity, actualEntity);
  }
}
