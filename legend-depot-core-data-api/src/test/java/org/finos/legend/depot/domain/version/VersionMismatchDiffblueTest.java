package org.finos.legend.depot.domain.version;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class VersionMismatchDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionMismatch#equals(Object)}
   *   <li>{@link VersionMismatch#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    VersionMismatch versionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        new ArrayList<>());
    ArrayList<String> versionsNotInCache2 = new ArrayList<>();
    VersionMismatch versionMismatch2 = new VersionMismatch("myproject", "42", "42", versionsNotInCache2,
        new ArrayList<>());

    // Act and Assert
    assertEquals(versionMismatch, versionMismatch2);
    int expectedHashCodeResult = versionMismatch.hashCode();
    assertEquals(expectedHashCodeResult, versionMismatch2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionMismatch#equals(Object)}
   *   <li>{@link VersionMismatch#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    VersionMismatch versionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        new ArrayList<>());

    // Act and Assert
    assertEquals(versionMismatch, versionMismatch);
    int expectedHashCodeResult = versionMismatch.hashCode();
    assertEquals(expectedHashCodeResult, versionMismatch.hashCode());
  }

  /**
   * Method under test: {@link VersionMismatch#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    VersionMismatch versionMismatch = new VersionMismatch("42", "42", "42", versionsNotInCache, new ArrayList<>());
    ArrayList<String> versionsNotInCache2 = new ArrayList<>();

    // Act and Assert
    assertNotEquals(versionMismatch,
        new VersionMismatch("myproject", "42", "42", versionsNotInCache2, new ArrayList<>()));
  }

  /**
   * Method under test: {@link VersionMismatch#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    // Act and Assert
    assertNotEquals(new VersionMismatch("myproject", "42", "42", versionsNotInCache, new ArrayList<>()), null);
  }

  /**
   * Method under test: {@link VersionMismatch#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    // Act and Assert
    assertNotEquals(new VersionMismatch("myproject", "42", "42", versionsNotInCache, new ArrayList<>()),
        "Different type to VersionMismatch");
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List)}
   */
  @Test
  void testNewVersionMismatch() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        new ArrayList<>());

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInRepository.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List)}
   */
  @Test
  void testNewVersionMismatch2() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    versionsNotInCache.add("foo");

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        new ArrayList<>());

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    List<String> stringList = actualVersionMismatch.versionsNotInStore;
    assertEquals(1, stringList.size());
    assertEquals("foo", stringList.get(0));
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInRepository.isEmpty());
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List)}
   */
  @Test
  void testNewVersionMismatch3() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    versionsNotInCache.add("42");
    versionsNotInCache.add("foo");

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        new ArrayList<>());

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInRepository.isEmpty());
    assertEquals(versionsNotInCache, actualVersionMismatch.versionsNotInStore);
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List)}
   */
  @Test
  void testNewVersionMismatch4() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    ArrayList<String> versionsNotInRepo = new ArrayList<>();
    versionsNotInRepo.add("foo");

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        versionsNotInRepo);

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    List<String> stringList = actualVersionMismatch.versionsNotInRepository;
    assertEquals(1, stringList.size());
    assertEquals("foo", stringList.get(0));
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List)}
   */
  @Test
  void testNewVersionMismatch5() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    ArrayList<String> versionsNotInRepo = new ArrayList<>();
    versionsNotInRepo.add("42");
    versionsNotInRepo.add("foo");

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        versionsNotInRepo);

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
    assertEquals(versionsNotInRepo, actualVersionMismatch.versionsNotInRepository);
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}
   */
  @Test
  void testNewVersionMismatch6() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    ArrayList<String> versionsNotInRepo = new ArrayList<>();

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        versionsNotInRepo, new ArrayList<>());

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInRepository.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}
   */
  @Test
  void testNewVersionMismatch7() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    versionsNotInCache.add("foo");
    ArrayList<String> versionsNotInRepo = new ArrayList<>();

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        versionsNotInRepo, new ArrayList<>());

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    List<String> stringList = actualVersionMismatch.versionsNotInStore;
    assertEquals(1, stringList.size());
    assertEquals("foo", stringList.get(0));
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInRepository.isEmpty());
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}
   */
  @Test
  void testNewVersionMismatch8() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    versionsNotInCache.add("42");
    versionsNotInCache.add("foo");
    ArrayList<String> versionsNotInRepo = new ArrayList<>();

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        versionsNotInRepo, new ArrayList<>());

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInRepository.isEmpty());
    assertEquals(versionsNotInCache, actualVersionMismatch.versionsNotInStore);
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}
   */
  @Test
  void testNewVersionMismatch9() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    ArrayList<String> versionsNotInRepo = new ArrayList<>();
    versionsNotInRepo.add("foo");

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        versionsNotInRepo, new ArrayList<>());

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    List<String> stringList = actualVersionMismatch.versionsNotInRepository;
    assertEquals(1, stringList.size());
    assertEquals("foo", stringList.get(0));
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}
   */
  @Test
  void testNewVersionMismatch10() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    ArrayList<String> versionsNotInRepo = new ArrayList<>();
    versionsNotInRepo.add("42");
    versionsNotInRepo.add("foo");

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        versionsNotInRepo, new ArrayList<>());

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
    assertEquals(versionsNotInRepo, actualVersionMismatch.versionsNotInRepository);
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}
   */
  @Test
  void testNewVersionMismatch11() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    ArrayList<String> versionsNotInRepo = new ArrayList<>();

    ArrayList<String> errors = new ArrayList<>();
    errors.add("foo");

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        versionsNotInRepo, errors);

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    List<String> stringList = actualVersionMismatch.errors;
    assertEquals(1, stringList.size());
    assertEquals("foo", stringList.get(0));
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.versionsNotInRepository.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
  }

  /**
   * Method under test:
   * {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}
   */
  @Test
  void testNewVersionMismatch12() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    ArrayList<String> versionsNotInRepo = new ArrayList<>();

    ArrayList<String> errors = new ArrayList<>();
    errors.add("42");
    errors.add("foo");

    // Act
    VersionMismatch actualVersionMismatch = new VersionMismatch("myproject", "42", "42", versionsNotInCache,
        versionsNotInRepo, errors);

    // Assert
    assertEquals("42", actualVersionMismatch.artifactId);
    assertEquals("42", actualVersionMismatch.groupId);
    assertEquals("myproject", actualVersionMismatch.projectId);
    assertTrue(actualVersionMismatch.versionsNotInRepository.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
    assertEquals(errors, actualVersionMismatch.errors);
  }
}
