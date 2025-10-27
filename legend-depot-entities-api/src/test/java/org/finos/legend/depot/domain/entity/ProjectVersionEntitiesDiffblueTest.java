package org.finos.legend.depot.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Test;

class ProjectVersionEntitiesDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectVersionEntities#equals(Object)}
   *   <li>{@link ProjectVersionEntities#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProjectVersionEntities projectVersionEntities = new ProjectVersionEntities();
    ProjectVersionEntities projectVersionEntities2 = new ProjectVersionEntities();

    // Act and Assert
    assertEquals(projectVersionEntities, projectVersionEntities2);
    int expectedHashCodeResult = projectVersionEntities.hashCode();
    assertEquals(expectedHashCodeResult, projectVersionEntities2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectVersionEntities#equals(Object)}
   *   <li>{@link ProjectVersionEntities#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProjectVersionEntities projectVersionEntities = new ProjectVersionEntities();

    // Act and Assert
    assertEquals(projectVersionEntities, projectVersionEntities);
    int expectedHashCodeResult = projectVersionEntities.hashCode();
    assertEquals(expectedHashCodeResult, projectVersionEntities.hashCode());
  }

  /**
   * Method under test: {@link ProjectVersionEntities#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProjectVersionEntities projectVersionEntities = new ProjectVersionEntities("42", "42", "42", new ArrayList<>());

    // Act and Assert
    assertNotEquals(projectVersionEntities, new ProjectVersionEntities());
  }

  /**
   * Method under test: {@link ProjectVersionEntities#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<Entity> entities = new ArrayList<>();
    entities.add(mock(Entity.class));
    ProjectVersionEntities projectVersionEntities = new ProjectVersionEntities("42", "42", "42", entities);

    // Act and Assert
    assertNotEquals(projectVersionEntities, new ProjectVersionEntities());
  }

  /**
   * Method under test: {@link ProjectVersionEntities#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProjectVersionEntities(), null);
  }

  /**
   * Method under test: {@link ProjectVersionEntities#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProjectVersionEntities(), "Different type to ProjectVersionEntities");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectVersionEntities#ProjectVersionEntities()}
   *   <li>{@link ProjectVersionEntities#getEntities()}
   *   <li>{@link ProjectVersionEntities#isVersionedEntity()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ProjectVersionEntities actualProjectVersionEntities = new ProjectVersionEntities();
    List<Entity> actualEntities = actualProjectVersionEntities.getEntities();
    boolean actualIsVersionedEntityResult = actualProjectVersionEntities.isVersionedEntity();

    // Assert
    assertNull(actualProjectVersionEntities.getArtifactId());
    assertNull(actualProjectVersionEntities.getGroupId());
    assertNull(actualProjectVersionEntities.getVersionId());
    assertNull(actualEntities);
    assertFalse(actualIsVersionedEntityResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProjectVersionEntities#ProjectVersionEntities(String, String, String, List)}
   *   <li>{@link ProjectVersionEntities#getEntities()}
   *   <li>{@link ProjectVersionEntities#isVersionedEntity()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange
    ArrayList<Entity> entities = new ArrayList<>();

    // Act
    ProjectVersionEntities actualProjectVersionEntities = new ProjectVersionEntities("42", "42", "42", entities);
    List<Entity> actualEntities = actualProjectVersionEntities.getEntities();
    boolean actualIsVersionedEntityResult = actualProjectVersionEntities.isVersionedEntity();

    // Assert
    assertEquals("42", actualProjectVersionEntities.getArtifactId());
    assertEquals("42", actualProjectVersionEntities.getGroupId());
    assertEquals("42", actualProjectVersionEntities.getVersionId());
    assertFalse(actualIsVersionedEntityResult);
    assertTrue(actualEntities.isEmpty());
    assertSame(entities, actualEntities);
  }
}
