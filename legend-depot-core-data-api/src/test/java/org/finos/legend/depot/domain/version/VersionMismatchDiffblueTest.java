package org.finos.legend.depot.domain.version;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class VersionMismatchDiffblueTest {
  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>Then return {@link VersionMismatch#versionsNotInRepository} is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List); given 'foo'; then return versionsNotInRepository is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List)"})
  void testNewVersionMismatch_givenFoo_thenReturnVersionsNotInRepositoryIsArrayList() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    ArrayList<String> versionsNotInRepo = new ArrayList<>();
    versionsNotInRepo.add("foo");
    versionsNotInRepo.add("\"TestObjectForArrayListAddMethod\"");

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache,
            versionsNotInRepo);

    // Assert
    assertEquals(versionsNotInRepo, actualVersionMismatch.versionsNotInRepository);
  }

  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>Then return {@link VersionMismatch#versionsNotInRepository} is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List, List); given 'foo'; then return versionsNotInRepository is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List, List)"})
  void testNewVersionMismatch_givenFoo_thenReturnVersionsNotInRepositoryIsArrayList2() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    ArrayList<String> versionsNotInRepo = new ArrayList<>();
    versionsNotInRepo.add("foo");
    versionsNotInRepo.add("\"TestObjectForArrayListAddMethod\"");

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"finance-domain-model\"",
            versionsNotInCache,
            versionsNotInRepo,
            new ArrayList<>());

    // Assert
    assertEquals(versionsNotInRepo, actualVersionMismatch.versionsNotInRepository);
  }

  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>Then return {@link VersionMismatch#versionsNotInStore} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List); given 'foo'; then return versionsNotInStore is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List)"})
  void testNewVersionMismatch_givenFoo_thenReturnVersionsNotInStoreIsArrayList() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    versionsNotInCache.add("foo");
    versionsNotInCache.add("\"TestObjectForArrayListAddMethod\"");

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache,
            new ArrayList<>());

    // Assert
    assertEquals("\"finance-risk-modeling-project\"", actualVersionMismatch.projectId);
    assertEquals("\"legend-shared\"", actualVersionMismatch.artifactId);
    assertEquals("\"org.finos.legend.depot\"", actualVersionMismatch.groupId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInRepository.isEmpty());
    assertEquals(versionsNotInCache, actualVersionMismatch.versionsNotInStore);
  }

  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>Then return {@link VersionMismatch#versionsNotInStore} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List, List); given 'foo'; then return versionsNotInStore is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List, List)"})
  void testNewVersionMismatch_givenFoo_thenReturnVersionsNotInStoreIsArrayList2() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    versionsNotInCache.add("foo");
    versionsNotInCache.add("\"TestObjectForArrayListAddMethod\"");
    ArrayList<String> versionsNotInRepo = new ArrayList<>();

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"finance-domain-model\"",
            versionsNotInCache,
            versionsNotInRepo,
            new ArrayList<>());

    // Assert
    assertEquals("\"finance-domain-model\"", actualVersionMismatch.artifactId);
    assertEquals("\"finance-risk-modeling-project\"", actualVersionMismatch.projectId);
    assertEquals("\"org.finos.legend.depot\"", actualVersionMismatch.groupId);
    assertEquals(versionsNotInCache, actualVersionMismatch.versionsNotInStore);
  }

  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then return {@link VersionMismatch#errors} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List, List); given 'foo'; when ArrayList() add 'foo'; then return errors is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List, List)"})
  void testNewVersionMismatch_givenFoo_whenArrayListAddFoo_thenReturnErrorsIsArrayList() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    ArrayList<String> versionsNotInRepo = new ArrayList<>();

    ArrayList<String> errors = new ArrayList<>();
    errors.add("foo");
    errors.add("\"TestObjectForArrayListAddMethod\"");

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"finance-domain-model\"",
            versionsNotInCache,
            versionsNotInRepo,
            errors);

    // Assert
    assertEquals(errors, actualVersionMismatch.errors);
  }

  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}.
   *
   * <ul>
   *   <li>Then return {@link VersionMismatch#errors} size is one.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List, List); then return errors size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List, List)"})
  void testNewVersionMismatch_thenReturnErrorsSizeIsOne() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    ArrayList<String> versionsNotInRepo = new ArrayList<>();

    ArrayList<String> errors = new ArrayList<>();
    errors.add("\"TestObjectForArrayListAddMethod\"");

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"finance-domain-model\"",
            versionsNotInCache,
            versionsNotInRepo,
            errors);

    // Assert
    List<String> stringList = actualVersionMismatch.errors;
    assertEquals(1, stringList.size());
    assertEquals("\"TestObjectForArrayListAddMethod\"", stringList.get(0));
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
  }

  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List)}.
   *
   * <ul>
   *   <li>Then return {@link VersionMismatch#versionsNotInRepository} size is one.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List); then return versionsNotInRepository size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List)"})
  void testNewVersionMismatch_thenReturnVersionsNotInRepositorySizeIsOne() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    ArrayList<String> versionsNotInRepo = new ArrayList<>();
    versionsNotInRepo.add("\"TestObjectForArrayListAddMethod\"");

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache,
            versionsNotInRepo);

    // Assert
    List<String> stringList = actualVersionMismatch.versionsNotInRepository;
    assertEquals(1, stringList.size());
    assertEquals("\"TestObjectForArrayListAddMethod\"", stringList.get(0));
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
  }

  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}.
   *
   * <ul>
   *   <li>Then return {@link VersionMismatch#versionsNotInRepository} size is one.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List, List); then return versionsNotInRepository size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List, List)"})
  void testNewVersionMismatch_thenReturnVersionsNotInRepositorySizeIsOne2() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    ArrayList<String> versionsNotInRepo = new ArrayList<>();
    versionsNotInRepo.add("\"TestObjectForArrayListAddMethod\"");

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"finance-domain-model\"",
            versionsNotInCache,
            versionsNotInRepo,
            new ArrayList<>());

    // Assert
    List<String> stringList = actualVersionMismatch.versionsNotInRepository;
    assertEquals(1, stringList.size());
    assertEquals("\"TestObjectForArrayListAddMethod\"", stringList.get(0));
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
  }

  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List)}.
   *
   * <ul>
   *   <li>Then return {@link VersionMismatch#versionsNotInStore} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List); then return versionsNotInStore is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List)"})
  void testNewVersionMismatch_thenReturnVersionsNotInStoreIsArrayList() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    versionsNotInCache.add("\"TestObjectForArrayListAddMethod\"");

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache,
            new ArrayList<>());

    // Assert
    assertEquals("\"finance-risk-modeling-project\"", actualVersionMismatch.projectId);
    assertEquals("\"legend-shared\"", actualVersionMismatch.artifactId);
    assertEquals("\"org.finos.legend.depot\"", actualVersionMismatch.groupId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInRepository.isEmpty());
    assertEquals(versionsNotInCache, actualVersionMismatch.versionsNotInStore);
  }

  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}.
   *
   * <ul>
   *   <li>Then return {@link VersionMismatch#versionsNotInStore} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List, List); then return versionsNotInStore is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List, List)"})
  void testNewVersionMismatch_thenReturnVersionsNotInStoreIsArrayList2() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    versionsNotInCache.add("\"TestObjectForArrayListAddMethod\"");
    ArrayList<String> versionsNotInRepo = new ArrayList<>();

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"finance-domain-model\"",
            versionsNotInCache,
            versionsNotInRepo,
            new ArrayList<>());

    // Assert
    assertEquals("\"finance-domain-model\"", actualVersionMismatch.artifactId);
    assertEquals("\"finance-risk-modeling-project\"", actualVersionMismatch.projectId);
    assertEquals("\"org.finos.legend.depot\"", actualVersionMismatch.groupId);
    assertEquals(versionsNotInCache, actualVersionMismatch.versionsNotInStore);
  }

  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@link VersionMismatch#artifactId} is {@code "finance-domain-model"}.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List, List); when ArrayList(); then return artifactId is '\"finance-domain-model\"'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List, List)"})
  void testNewVersionMismatch_whenArrayList_thenReturnArtifactIdIsFinanceDomainModel() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    ArrayList<String> versionsNotInRepo = new ArrayList<>();

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"finance-domain-model\"",
            versionsNotInCache,
            versionsNotInRepo,
            new ArrayList<>());

    // Assert
    assertEquals("\"finance-domain-model\"", actualVersionMismatch.artifactId);
    assertEquals("\"finance-risk-modeling-project\"", actualVersionMismatch.projectId);
    assertEquals("\"org.finos.legend.depot\"", actualVersionMismatch.groupId);
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
  }

  /**
   * Test {@link VersionMismatch#VersionMismatch(String, String, String, List, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@link VersionMismatch#versionsNotInStore} Empty.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#VersionMismatch(String, String, String, List,
   * List)}
   */
  @Test
  @DisplayName(
      "Test new VersionMismatch(String, String, String, List, List); when ArrayList(); then return versionsNotInStore Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VersionMismatch.<init>(String, String, String, List, List)"})
  void testNewVersionMismatch_whenArrayList_thenReturnVersionsNotInStoreEmpty() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    // Act
    VersionMismatch actualVersionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache,
            new ArrayList<>());

    // Assert
    assertEquals("\"finance-risk-modeling-project\"", actualVersionMismatch.projectId);
    assertEquals("\"legend-shared\"", actualVersionMismatch.artifactId);
    assertEquals("\"org.finos.legend.depot\"", actualVersionMismatch.groupId);
    assertTrue(actualVersionMismatch.errors.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInRepository.isEmpty());
    assertTrue(actualVersionMismatch.versionsNotInStore.isEmpty());
  }

  /**
   * Test {@link VersionMismatch#equals(Object)}, and {@link VersionMismatch#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link VersionMismatch#equals(Object)}
   *   <li>{@link VersionMismatch#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VersionMismatch.equals(Object)", "int VersionMismatch.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    VersionMismatch versionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache,
            new ArrayList<>());
    ArrayList<String> versionsNotInCache2 = new ArrayList<>();
    VersionMismatch versionMismatch2 =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache2,
            new ArrayList<>());

    // Act and Assert
    assertEquals(versionMismatch, versionMismatch2);
    assertEquals(versionMismatch.hashCode(), versionMismatch2.hashCode());
  }

  /**
   * Test {@link VersionMismatch#equals(Object)}, and {@link VersionMismatch#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link VersionMismatch#equals(Object)}
   *   <li>{@link VersionMismatch#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VersionMismatch.equals(Object)", "int VersionMismatch.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    VersionMismatch versionMismatch =
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache,
            new ArrayList<>());

    // Act and Assert
    assertEquals(versionMismatch, versionMismatch);
    int expectedHashCodeResult = versionMismatch.hashCode();
    assertEquals(expectedHashCodeResult, versionMismatch.hashCode());
  }

  /**
   * Test {@link VersionMismatch#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VersionMismatch.equals(Object)", "int VersionMismatch.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();
    VersionMismatch versionMismatch =
        new VersionMismatch(
            "myproject",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache,
            new ArrayList<>());
    ArrayList<String> versionsNotInCache2 = new ArrayList<>();

    // Act and Assert
    assertNotEquals(
        versionMismatch,
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache2,
            new ArrayList<>()));
  }

  /**
   * Test {@link VersionMismatch#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VersionMismatch.equals(Object)", "int VersionMismatch.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    // Act and Assert
    assertNotEquals(
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache,
            new ArrayList<>()),
        null);
  }

  /**
   * Test {@link VersionMismatch#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link VersionMismatch#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VersionMismatch.equals(Object)", "int VersionMismatch.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> versionsNotInCache = new ArrayList<>();

    // Act and Assert
    assertNotEquals(
        new VersionMismatch(
            "\"finance-risk-modeling-project\"",
            "\"org.finos.legend.depot\"",
            "\"legend-shared\"",
            versionsNotInCache,
            new ArrayList<>()),
        "Different type to VersionMismatch");
  }
}
